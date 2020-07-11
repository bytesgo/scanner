/**
 * Copyright © 2016~2020 CRPC (coderhook@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bytesgo.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public interface ClassScanner {

	/**
	 * 获取指定包名中的所有类
	 * 
	 * @param packagePattern packagePattern
	 * @return Set
	 */
	Set<Class<?>> get(String packagePattern);

	/**
	 * 获取指定包名中指定注解的相关类
	 * 
	 * @param annotationClass annotationClass
	 * @return Set
	 */
	Set<Class<?>> getByAnnotation(Class<? extends Annotation> annotationClass);

	/**
	 * 获取指定包名中指定父类或接口的相关类
	 * 
	 * @param superClass superClass
	 * @return Set
	 */
	Set<Class<?>> getBySuperclass(Class<?> superClass);

	/**
	 * 获取一个类上方法名符合正则的所有的方法
	 * 
	 * @param clazz         clazz
	 * @param methodPattern methodPattern
	 * @return List
	 */
	List<Method> getMethod(Class<?> clazz, String methodPattern);

	/**
	 * 获取一个类上有期望Annotation的所有方法
	 * 
	 * @param clazz           clazz
	 * @param annotationClass annotationClass
	 * @return List
	 */
	List<Method> getMethodByAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass);

	/**
	 * 获取一个类及接口类上有期望Annotation的所有方法
	 * 
	 * @param clazz           clazz
	 * @param annotationClass annotationClass
	 * @return {@link Method }
	 */
	List<Method> getMethodByAnnotationInterface(Class<?> clazz, Class<? extends Annotation> annotationClass);
}
