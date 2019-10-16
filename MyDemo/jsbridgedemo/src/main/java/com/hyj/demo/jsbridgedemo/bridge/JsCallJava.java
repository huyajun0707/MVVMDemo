package com.hyj.demo.jsbridgedemo.bridge;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * @author ： HuYajun <huyajun0707@gmail.com>
 * @version ： 1.0
 * @date ： 2019-10-15 17:35
 * @depiction ：
 */
public class JsCallJava {
    private HashMap<String, Method> mMethodsMap;
    private String mInjectedName;
    private String mPreloadInterfaceJS;
    private Gson mGson;

    public JsCallJava(String injectedName, Class injectedCls) {
        try {
            if (TextUtils.isEmpty(injectedName)) {
                throw new JsCallJavaException("injected name can not be null");
            }
            mInjectedName = injectedName;
            mMethodsMap = new HashMap<>();
            StringBuilder builder = new StringBuilder("javascript:(function(global){console.log(\"");
            builder.append(mInjectedName);
            builder.append(" initialization begin\");var native={queue:[],callback:function(){var args=Array.prototype.slice.call(arguments,0);var index=args.shift();var isPermanent=args.shift();this.queue[index].apply(this,args);if(!isPermanent){delete this.queue[index]}}};");
            for (Method method : injectedCls.getDeclaredMethods()) {
                String sign;
                if (method.getModifiers() != (Modifier.PUBLIC | Modifier.STATIC) || (sign = genJavaMethodSign(method)) == null) {
                    continue;
                }
                Log.d("---->1", sign+"----"+method.getName());
                mMethodsMap.put(sign, method);
                builder.append(String.format("native.%s=", method.getName()));
                Log.d("---->2", builder.toString());
            }

            builder.append("function(){var args=Array.prototype.slice.call(arguments,0);if(args.length<1){throw\"");
            builder.append(mInjectedName);
            builder.append(" call error, message:miss method name\"}var aTypes=[];for(var i=1;i<args.length;i++){var arg=args[i];var type=typeof arg;aTypes[aTypes.length]=type;if(type==\"function\"){var index=native.queue.length;native.queue[index]=arg;args[i]=index}}var res=JSON.parse(prompt(JSON.stringify({method:args.shift(),types:aTypes,args:args})));if(res.code!=200){throw\"");
            builder.append(mInjectedName);
            builder.append(" call error, code:\"+res.code+\", message:\"+res.result}return res.result};Object.getOwnPropertyNames(native).forEach(function(property){var original=native[property];if(typeof original===\"function\"&&property!==\"callback\"){native[property]=function(){return original.apply(native,[property].concat(Array.prototype.slice.call(arguments,0)))}}});global.");
            builder.append(mInjectedName);
            builder.append("=native;console.log(\"");
            builder.append(mInjectedName);
            builder.append(" initialization end\")})(window);");
            mPreloadInterfaceJS = builder.toString();
            Log.d("---->3", mPreloadInterfaceJS);
        } catch (JsCallJavaException e) {
            Log.e("---->", "js initialization error:" + e.getMessage());
        }
    }

    private String genJavaMethodSign(Method method) {
        String sign = method.getName();
        Class[] argsTypes = method.getParameterTypes();
        int len = argsTypes.length;
        if (len < 1 || argsTypes[0] != WebView.class) {
            Log.w("---->", "method(" + sign + ") must use webview to be first parameter, will be pass");
            return null;
        }
        for (int k = 1; k < len; k++) {
            Class cls = argsTypes[k];
            if (cls == String.class) {
                sign += "_S";
            } else if (cls == int.class || cls == long.class || cls == float.class || cls == double.class) {
                sign += "_N";
            } else if (cls == boolean.class) {
                sign += "_B";
            } else if (cls == JSONObject.class) {
                sign += "_O";
            } else if (cls == JsCallback.class) {
                sign += "_F";
            } else {
                sign += "_P";
            }
        }
        return sign;
    }

    public String getPreloadInterfaceJS() {
        return mPreloadInterfaceJS;
    }

    public String call(WebView webView, String jsonStr) {
        if (!TextUtils.isEmpty(jsonStr)) {
            try {
                JSONObject callJson = new JSONObject(jsonStr);
                String methodName = callJson.getString("method");
                JSONArray argsTypes = callJson.getJSONArray("types");
                JSONArray argsValues = callJson.getJSONArray("args");
                String sign = methodName;
                int length = argsTypes.length();
                Object[] values = new Object[length + 1];
                int numIndex = 0;
                String currType;

                values[0] = webView;

                for (int k = 0; k < length; k++) {
                    currType = argsTypes.optString(k);
                    if ("string".equals(currType)) {
                        sign += "_S";
                        values[k + 1] = argsValues.isNull(k) ? null : argsValues.getString(k);
                    } else if ("number".equals(currType)) {
                        sign += "_N";
                        numIndex = numIndex * 10 + k + 1;
                    } else if ("boolean".equals(currType)) {
                        sign += "_B";
                        values[k + 1] = argsValues.getBoolean(k);
                    } else if ("object".equals(currType)) {
                        sign += "_O";
                        values[k + 1] = argsValues.isNull(k) ? null : argsValues.getJSONObject(k);
                    } else if ("function".equals(currType)) {
                        sign += "_F";
                        values[k + 1] = new JsCallback(webView, mInjectedName, argsValues.getInt(k));
                    } else {
                        sign += "_P";
                    }
                }

                Method currMethod = mMethodsMap.get(sign);

                // 方法匹配失败
                if (currMethod == null) {
                    return getReturn(jsonStr, 500, "not found method(" + sign + ") with valid parameters");
                }
                // 数字类型细分匹配
                if (numIndex > 0) {
                    Class[] methodTypes = currMethod.getParameterTypes();
                    int currIndex;
                    Class currCls;
                    while (numIndex > 0) {
                        currIndex = numIndex - numIndex / 10 * 10;
                        currCls = methodTypes[currIndex];
                        if (currCls == int.class) {
                            values[currIndex] = argsValues.getInt(currIndex - 1);
                        } else if (currCls == long.class) {
                            //WARN: argsJson.getLong(k + defValue) will return a bigger incorrect number
                            values[currIndex] = Long.parseLong(argsValues.getString(currIndex - 1));
                        } else {
                            values[currIndex] = argsValues.getDouble(currIndex - 1);
                        }
                        numIndex /= 10;
                    }
                }

                return getReturn(jsonStr, 200, currMethod.invoke(null, values));
            } catch (Exception e) {
                //优先返回详细的错误信息
                if (e.getCause() != null) {
                    return getReturn(jsonStr, 500, "method execute error:" + e.getCause().getMessage());
                }
                return getReturn(jsonStr, 500, "method execute error:" + e.getMessage());
            }
        } else {
            return getReturn(jsonStr, 500, "call data empty");
        }
    }

    private String getReturn(String reqJson, int stateCode, Object result) {
        String insertRes;
        if (result == null) {
            insertRes = "null";
        } else if (result instanceof String) {
            result = ((String) result).replace("\"", "\\\"");
            insertRes = "\"" + result + "\"";
        } else if (!(result instanceof Integer)
                && !(result instanceof Long)
                && !(result instanceof Boolean)
                && !(result instanceof Float)
                && !(result instanceof Double)
                && !(result instanceof JSONObject)) {    // 非数字或者非字符串的构造对象类型都要序列化后再拼接
            if (mGson == null) {
                mGson = new Gson();
            }
            insertRes = mGson.toJson(result);
        } else {  //数字直接转化
            insertRes = String.valueOf(result);
        }
        String resStr = String.format(Constant.RETURN_RESULT_FORMAT, stateCode, insertRes);
        Log.d("---->", mInjectedName + " call json: " + reqJson + " result:" + resStr);
        return resStr;
    }
}
