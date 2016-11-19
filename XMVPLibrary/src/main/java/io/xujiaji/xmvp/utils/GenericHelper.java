/*
 * Copyright 2016 XuJiaji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.xujiaji.xmvp.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.xujiaji.xmvp.contracts.Contract;

/**
 * Created by qibin on 2015/11/15.
 */
public class GenericHelper {

    /**
     * 获取泛型类，也就是BasePresenter的子类
     * @param klass
     * @param <T>
     * @return
     */
    public static <T> Class<T> getViewClass(Class<?> klass) {
        Type type = klass.getGenericSuperclass();
        if(type == null || !(type instanceof ParameterizedType)) return null;
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] types = parameterizedType.getActualTypeArguments();
        if(types == null || types.length == 0) return null;
        return (Class<T>) types[0];
    }

    /**
     * 初始化presenter
     * @param obj
     * @param <T>
     * @return
     */
    public static  <T> T initPresenter(Object obj) {
        try {
            Class<?> currentClass = obj.getClass();
            Constructor construct = getViewClass(currentClass).getConstructor(getViewInterface(currentClass));
            return  (T) construct.newInstance(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("instance of presenter fail\n" +
                " Remind presenter need to extends BasePresenter");
    }


    /**
     * 获取继承"Contract.BaseView"的接口类
     * @param currentClass
     * @return
     */
    public static Class<?> getViewInterface(Class currentClass) {
        Class<?>[] classes = currentClass.getInterfaces();
        for (Class<?> c : classes) {
            if (c != Contract.BaseView.class) {
                if (getViewInterface(c) == Contract.BaseView.class) {
                    return c;
                }
            }
            return c;
        }
        throw new RuntimeException("no implement Contract.BaseView");
    }
}
