package io.xujiaji.hnbc.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.xujiaji.hnbc.contracts.Contract;

/**
 * Created by qibin on 2015/11/15.
 */
public class GenericHelper {

    public static <T> Class<T> getViewClass(Class<?> klass) {
        Type type = klass.getGenericSuperclass();
        if(type == null || !(type instanceof ParameterizedType)) return null;
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] types = parameterizedType.getActualTypeArguments();
        if(types == null || types.length == 0) return null;
        return (Class<T>) types[0];
    }

    public static  <T> T initPresenter(Object obj) {
        try {
            Class<?> currentClass = obj.getClass();
            LogUtil.e3(currentClass.toString());
            LogUtil.e3(getViewClass(currentClass).toString());
            LogUtil.e3(getViewInterface(currentClass).toString());
            Constructor construct = getViewClass(currentClass).getConstructor(getViewInterface(currentClass));
            return  (T) construct.newInstance(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("instance of presenter fail");
    }


    public static Class<?> getViewInterface(Class currentClass) {
        Class<?>[] classes = currentClass.getInterfaces();
        for (Class<?> c : classes) {
            Class<?>[] ccs = c.getInterfaces();
            for(Class<?> c2 : ccs) {
                if (c2 != Contract.BaseView.class) {
                    continue;
                }
                return c;
            }
        }
        throw new RuntimeException("no implement Contract.BaseView");
    }
}
