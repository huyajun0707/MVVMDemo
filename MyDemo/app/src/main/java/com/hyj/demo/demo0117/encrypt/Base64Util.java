package com.hyj.demo.demo0117.encrypt;

import android.graphics.Bitmap;
import android.text.TextUtils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Base64Util {

    private static final byte[] Table;

    static {
        Table = new byte[128];

        for (int i = 0; i < 128; i++) {
            Table[i] = (byte) -1;
        }

        for (int i = 'A'; i <= 'Z'; i++) {
            Table[i] = (byte) (i - 'A');
        }

        for (int i = 'a'; i <= 'z'; i++) {
            Table[i] = (byte) (i - 'a' + 26);
        }

        for (int i = '0'; i <= '9'; i++) {
            Table[i] = (byte) (i - '0' + 52);
        }

        Table['+'] = 62;
        Table['/'] = 63;
    }

    public static String encode(byte[] data) {
        if (data != null) {
            byte[] bytes;
            int modulus = data.length % 3;
            if (modulus == 0) {
                bytes = new byte[(4 * data.length) / 3];
            } else {
                bytes = new byte[4 * ((data.length / 3) + 1)];
            }

            int dataLength = (data.length - modulus);
            int a1;
            int a2;
            int a3;

            for (int i = 0, j = 0; i < dataLength; i += 3, j += 4) {
                a1 = data[i] & 0xff;
                a2 = data[i + 1] & 0xff;
                a3 = data[i + 2] & 0xff;

                bytes[j] = Constant.Data.TABLE[(a1 >>> 2) & 0x3f];
                bytes[j + 1] = Constant.Data.TABLE[((a1 << 4) | (a2 >>> 4)) & 0x3f];
                bytes[j + 2] = Constant.Data.TABLE[((a2 << 2) | (a3 >>> 6)) & 0x3f];
                bytes[j + 3] = Constant.Data.TABLE[a3 & 0x3f];
            }

            int b1;
            int b2;
            int b3;
            int d1;
            int d2;

            switch (modulus) {
                case 0: /* nothing left to do */
                    break;

                case 1:
                    d1 = data[data.length - 1] & 0xff;
                    b1 = (d1 >>> 2) & 0x3f;
                    b2 = (d1 << 4) & 0x3f;

                    bytes[bytes.length - 4] = Constant.Data.TABLE[b1];
                    bytes[bytes.length - 3] = Constant.Data.TABLE[b2];
                    bytes[bytes.length - 2] = (byte) '=';
                    bytes[bytes.length - 1] = (byte) '=';

                    break;

                case 2:
                    d1 = data[data.length - 2] & 0xff;
                    d2 = data[data.length - 1] & 0xff;

                    b1 = (d1 >>> 2) & 0x3f;
                    b2 = ((d1 << 4) | (d2 >>> 4)) & 0x3f;
                    b3 = (d2 << 2) & 0x3f;

                    bytes[bytes.length - 4] = Constant.Data.TABLE[b1];
                    bytes[bytes.length - 3] = Constant.Data.TABLE[b2];
                    bytes[bytes.length - 2] = Constant.Data.TABLE[b3];
                    bytes[bytes.length - 1] = (byte) '=';

                    break;
            }
            return new String(bytes);
        } else {
            return null;
        }
    }

    public static String decodeMovieName(String rawData) {
        if (rawData.startsWith("base64-")) {
            byte[] rawBytes = rawData.substring(7).getBytes();
            try {
                return new String(decode(rawBytes),"UTF-8");
            } catch (Exception e) {
                return rawData;
            }
        } else {
            return rawData;
        }
    }


    public static byte[] decode(byte[] data) {
        byte[] bytes;
        byte b1;
        byte b2;
        byte b3;
        byte b4;

        data = discardNonBase64Bytes(data);

        if (data[data.length - 2] == '=') {
            bytes = new byte[(((data.length / 4) - 1) * 3) + 1];
        } else if (data[data.length - 1] == '=') {
            bytes = new byte[(((data.length / 4) - 1) * 3) + 2];
        } else {
            bytes = new byte[((data.length / 4) * 3)];
        }

        for (int i = 0, j = 0; i < (data.length - 4); i += 4, j += 3) {
            b1 = Table[data[i]];
            b2 = Table[data[i + 1]];
            b3 = Table[data[i + 2]];
            b4 = Table[data[i + 3]];

            bytes[j] = (byte) ((b1 << 2) | (b2 >> 4));
            bytes[j + 1] = (byte) ((b2 << 4) | (b3 >> 2));
            bytes[j + 2] = (byte) ((b3 << 6) | b4);
        }

        if (data[data.length - 2] == '=') {
            b1 = Table[data[data.length - 4]];
            b2 = Table[data[data.length - 3]];

            bytes[bytes.length - 1] = (byte) ((b1 << 2) | (b2 >> 4));
        } else if (data[data.length - 1] == '=') {
            b1 = Table[data[data.length - 4]];
            b2 = Table[data[data.length - 3]];
            b3 = Table[data[data.length - 2]];

            bytes[bytes.length - 2] = (byte) ((b1 << 2) | (b2 >> 4));
            bytes[bytes.length - 1] = (byte) ((b2 << 4) | (b3 >> 2));
        } else {
            b1 = Table[data[data.length - 4]];
            b2 = Table[data[data.length - 3]];
            b3 = Table[data[data.length - 2]];
            b4 = Table[data[data.length - 1]];

            bytes[bytes.length - 3] = (byte) ((b1 << 2) | (b2 >> 4));
            bytes[bytes.length - 2] = (byte) ((b2 << 4) | (b3 >> 2));
            bytes[bytes.length - 1] = (byte) ((b3 << 6) | b4);
        }

        return bytes;
    }

    public static byte[] decode(String data) {
        if (!TextUtils.isEmpty(data)) {
            byte[] bytes;
            byte b1;
            byte b2;
            byte b3;
            byte b4;
            data = discardNonBase64Chars(data);
            if (data.charAt(data.length() - 2) == '=') {
                bytes = new byte[(((data.length() / 4) - 1) * 3) + 1];
            } else if (data.charAt(data.length() - 1) == '=') {
                bytes = new byte[(((data.length() / 4) - 1) * 3) + 2];
            } else {
                bytes = new byte[((data.length() / 4) * 3)];
            }

            for (int i = 0, j = 0; i < (data.length() - 4); i += 4, j += 3) {
                b1 = Table[data.charAt(i)];
                b2 = Table[data.charAt(i + 1)];
                b3 = Table[data.charAt(i + 2)];
                b4 = Table[data.charAt(i + 3)];

                bytes[j] = (byte) ((b1 << 2) | (b2 >> 4));
                bytes[j + 1] = (byte) ((b2 << 4) | (b3 >> 2));
                bytes[j + 2] = (byte) ((b3 << 6) | b4);
            }

            if (data.charAt(data.length() - 2) == '=') {
                b1 = Table[data.charAt(data.length() - 4)];
                b2 = Table[data.charAt(data.length() - 3)];

                bytes[bytes.length - 1] = (byte) ((b1 << 2) | (b2 >> 4));
            } else if (data.charAt(data.length() - 1) == '=') {
                b1 = Table[data.charAt(data.length() - 4)];
                b2 = Table[data.charAt(data.length() - 3)];
                b3 = Table[data.charAt(data.length() - 2)];

                bytes[bytes.length - 2] = (byte) ((b1 << 2) | (b2 >> 4));
                bytes[bytes.length - 1] = (byte) ((b2 << 4) | (b3 >> 2));
            } else {
                b1 = Table[data.charAt(data.length() - 4)];
                b2 = Table[data.charAt(data.length() - 3)];
                b3 = Table[data.charAt(data.length() - 2)];
                b4 = Table[data.charAt(data.length() - 1)];

                bytes[bytes.length - 3] = (byte) ((b1 << 2) | (b2 >> 4));
                bytes[bytes.length - 2] = (byte) ((b2 << 4) | (b3 >> 2));
                bytes[bytes.length - 1] = (byte) ((b3 << 6) | b4);
            }
            return bytes;
        } else {
            return null;
        }
    }

    private static byte[] discardNonBase64Bytes(byte[] data) {
        byte[] temp = new byte[data.length];
        int bytesCopied = 0;

        for (int i = 0; i < data.length; i++) {
            if (isValidBase64Byte(data[i])) {
                temp[bytesCopied++] = data[i];
            }
        }

        byte[] newData = new byte[bytesCopied];

        System.arraycopy(temp, 0, newData, 0, bytesCopied);

        return newData;
    }

    private static String discardNonBase64Chars(String data) {
        StringBuffer sb = new StringBuffer();

        int length = data.length();

        for (int i = 0; i < length; i++) {
            if (isValidBase64Byte((byte) (data.charAt(i)))) {
                sb.append(data.charAt(i));
            }
        }

        return sb.toString();
    }

    private static boolean isValidBase64Byte(byte b) {
        if (b == '=') {
            return true;
        } else if ((b < 0) || (b >= 128)) {
            return false;
        } else if (Table[b] == -1) {
            return false;
        }
        return true;
    }

    public static String bitmapToBase64(Bitmap bitmap, int quality) {
        ByteArrayOutputStream outputStream = null;
        try {
            //            if (bitmap != null) {
            outputStream = new ByteArrayOutputStream();
            if (quality >= 0) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            } else {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            }

            outputStream.flush();
            outputStream.close();

            //            return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
            return Base64Util.encode(outputStream.toByteArray());
            //            } else {
            //                return null;
            //            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
