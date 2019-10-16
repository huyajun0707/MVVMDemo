(function(global){
    console.log("myApp initialization begin");
    //在window身上添加了一个属性native,当加载到这个函数的时候，已经把这个native添加到全局了
    var native = {
        queue:[], callback:function(){
        var args = Array.prototype.slice.call(arguments, 0);  //把调用方法的参数截取出来，从第0位开始
        var index = args.shift();
        var isPermanent = args.shift();
        this.queue[index].apply(this, args);//传入参数arges到当前queue队列的index指向的方法中
        if (!isPermanent) {
            delete this.queue[index]
        }
    }
    };
    native.call = function() {
        var args = Array.prototype.slice.call(arguments, 0);
        if (args.length < 1) {
            throw"myApp call error, message:miss method name"
        }
        var aTypes = [];//取出所有参数的类型
        for (var i= 1; i < args.length; i++){
        var arg = args[i];
        var type =typeof arg;
        aTypes[aTypes.length] = type;
        if (type == "function") {
            var index = native.queue.length;
            native.queue[index] = arg;
            args[i] = index
        }
    }
        //调用方法并接收返回值
        var res = JSON.parse(prompt(JSON.stringify({
                method:args.shift(),
                types:aTypes,
                args:args
             })));
        if (res.code != 200) {
            throw"myApp call error, code:" + res.code + ", message:" + res.result
        }
        return res.result
    };

     //有时候，我们希望在该方法执行前插入一些其他的行为用来检查当前状态或是监测
        //代码行为，这就要用到拦截（Interception）或者叫注入（Injection）技术了
        /**
         * Object.getOwnPropertyName 返回一个数组，内容是指定对象的所有属性
         *
         * 其后遍历这个数组，分别做以下处理：
         * 1. 备份原始属性；
         * 2. 检查属性是否为 function（即方法）；
         * 3. 若是重新定义该方法，做你需要做的事情，之后 apply 原来的方法体。
         */

    Object.getOwnPropertyNames(native).forEach(function(property) {
        var original = native[property];
        if (typeof original === "function" && property !== "callback"){
        native[property] = function() {
            return original.apply(
                native,
                [property].concat(Array.prototype.slice.call(arguments, 0))
            )
        }
    }
    });
    global.myApp = native;
    console.log("myApp initialization end")
})(window);