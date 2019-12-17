package com.uplooking.SerializedLambda;


import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class BeanUtils {
    private static Map<Class, SerializedLambda> CLASS_LAMDBA_CACHE = new ConcurrentHashMap<>();

    /***
     * 转换方法引用为属性名
     * @param fn
     * @return
     */
    public static <T> String convertToFieldName(IGetter<T> fn) {
//        SerializedLambda lambda = getSerializedLambda(fn);
        com.uplooking.SerializedLambda.SerializedLambda  lambda = resolve(fn);
        String methodName = lambda.getImplMethodName();
        String prefix = null;
        if (methodName.startsWith("get")) {
            prefix = "get";
        } else if (methodName.startsWith("is")) {
            prefix = "is";
        }
        if (prefix == null) {
            //log.warn("无效的getter方法: " + methodName);
        }
        // 截取get/is之后的字符串并转换首字母为小写（S为diboot项目的字符串工具类，可自行实现）
        return toLowerCaseFirstOne(methodName.replace(prefix, ""));
    }

    public static <T, U> String convertToFieldName(ISetter<T, U> fn) {
        SerializedLambda lambda = getSerializedLambda(fn);
        String methodName = lambda.getImplMethodName();
        if (!methodName.startsWith("set")) {
            //log.warn("无效的setter方法：" + methodName);
        }
        return toLowerCaseFirstOne(methodName.replace("set", ""));
    }

    /**
     * 关键在于这个方法
     */
    public static SerializedLambda getSerializedLambda(Serializable fn) {
        SerializedLambda lambda = CLASS_LAMDBA_CACHE.get(fn.getClass());
        if (lambda == null) {
            try {
                Method method = fn.getClass().getDeclaredMethod("writeReplace");
                method.setAccessible(Boolean.TRUE);
                lambda = (SerializedLambda) method.invoke(fn);
                CLASS_LAMDBA_CACHE.put(fn.getClass(), lambda);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lambda;
    }


    //首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    //首字母转大写
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    public static <T> com.uplooking.SerializedLambda.SerializedLambda resolve(IGetter<T> lambda) {
        if (!lambda.getClass().isSynthetic()) {
            throw new RuntimeException("该方法仅能传入 lambda 表达式产生的合成类");
        }
        //字节流-->对象流
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(serialize(lambda))) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                Class<?> clazz = super.resolveClass(objectStreamClass);
                return clazz == java.lang.invoke.SerializedLambda.class ? com.uplooking.SerializedLambda.SerializedLambda.class : clazz;
            }
        }) {
            return (com.uplooking.SerializedLambda.SerializedLambda ) objIn.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException("This is impossible to happen", e);
        }
    }

    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        //将对象->对象流
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to serialize object of type: " + object.getClass(), ex);
        }
        return baos.toByteArray();
    }


    //通过函数式编程获取方法名

    /**
     * 总结：3,2,2
     * 步骤：
     * 1.定义一个函数是接口继承Serializable
     *  反射方式
     * 2.Method method = fn.getClass().getDeclaredMethod("writeReplace");
     *   SerializedLambda lambda = (SerializedLambda) method.invoke(fn);
     * 但是mysqlPlus中是使用（对象流）
     *  1.自定义一个SerializedLambda
     *  2.函数对象->对象流-->对象(强转)
     *
     * 3. String methodName = lambda.getImplMethodName();
     */
    public static void main(String[] args) {
        String getName = BeanUtils.convertToFieldName(Person::getId);
        System.out.println(getName);
        String setName = BeanUtils.convertToFieldName(Person::getName);
        System.out.println(setName);
    }
}

