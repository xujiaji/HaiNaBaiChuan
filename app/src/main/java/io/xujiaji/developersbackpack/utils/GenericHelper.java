package io.xujiaji.developersbackpack.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
}
