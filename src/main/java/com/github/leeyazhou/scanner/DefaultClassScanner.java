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
package com.github.leeyazhou.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;

import com.github.leeyazhou.scanner.filter.AbstractAnnotationClassFilter;
import com.github.leeyazhou.scanner.filter.AbstractAnnotationMethodFilter;
import com.github.leeyazhou.scanner.filter.AbstractClassFilter;
import com.github.leeyazhou.scanner.filter.AbstractPatternNameMethodFilter;
import com.github.leeyazhou.scanner.filter.AbstractSupperClassFilter;

public class DefaultClassScanner extends Scanner implements ClassScanner {
	private final String basePackage;
	private final ClassLoader classLoader;

	public DefaultClassScanner(ScannerBuilder builder) {
		super(builder);
		this.basePackage = builder.getBasePackage();
		this.classLoader = builder.getClassLoader();
	}

	@Override
	public Set<Class<?>> get(final String pattern) {
		return new AbstractClassFilter(basePackage, classLoader) {
			@Override
			public boolean filterCondition(Class<?> cls) {
				String className = cls.getName();
				String pkgName = className.substring(0, className.lastIndexOf("."));
				String patternStr = (null == pattern || pattern.isEmpty()) ? ".*" : pattern;
				return pkgName.startsWith(packageName) && pkgName.matches(patternStr);
			}
		}.getClassList();
	}

	@Override
	public Set<Class<?>> getByAnnotation(Class<? extends Annotation> annotationClass) {
		return new AbstractAnnotationClassFilter(basePackage, annotationClass, classLoader) {
			@Override
			public boolean filterCondition(Class<?> cls) {
				return cls.isAnnotationPresent(annotationClass);
			}
		}.getClassList();
	}

	@Override
	public Set<Class<?>> getBySuperclass(Class<?> superClass) {
		return new AbstractSupperClassFilter(basePackage, superClass, classLoader) {
			@Override
			public boolean filterCondition(Class<?> clazz) { // 这里去掉了内部类
				return superClass.isAssignableFrom(clazz) && !superClass.equals(clazz)
				    && !Modifier.isInterface(clazz.getModifiers()) && !Modifier.isAbstract(clazz.getModifiers())
				    && Modifier.isPublic(clazz.getModifiers());
				// !cls.getName().contains("$");
			}

		}.getClassList();
	}

	@Override
	public List<Method> getMethod(Class<?> clazz, final String methodPattern) {
		return new AbstractPatternNameMethodFilter(clazz, methodPattern) {

			@Override
			public boolean filterCondition(Method method) {
				return method.getName().matches(methodPattern);
			}
		}.getMethodList();
	}

	@Override
	public List<Method> getMethodByAnnotation(Class<?> clazz, Class<? extends Annotation> annotationType) {
		return new AbstractAnnotationMethodFilter(clazz, annotationType) {

			@Override
			public boolean filterCondition(Method method) {
				return method.isAnnotationPresent(annotationType);
			}
		}.getMethodList();
	}

	@Override
	public List<Method> getMethodByAnnotationInterface(Class<?> clazz, Class<? extends Annotation> annotationType) {
		return new AbstractAnnotationMethodFilter(clazz, annotationType) {

			@Override
			public boolean filterCondition(Method method) {
				if (method.isAnnotationPresent(annotationType)) {
					return true;
				}
				Class<?>[] cls = clazz.getInterfaces();
				for (Class<?> c : cls) {
					try {
						Method md = c.getDeclaredMethod(method.getName(), method.getParameterTypes());
						if (md.isAnnotationPresent(annotationType)) {
							return true;
						}
					} catch (NoSuchMethodException err) {
						err.printStackTrace();
					} catch (Exception err) {
						err.printStackTrace();
					}
				}
				return false;
			}
		}.getMethodList();
	}

}
