/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.spring.annotation;

import com.liferay.portal.kernel.annotation.BeanReference;

import java.beans.PropertyDescriptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

public class BeanReferenceCallback
	implements ReflectionUtils.FieldCallback, ReflectionUtils.MethodCallback {

	public BeanReferenceCallback(
		BeanFactory beanFactory, InjectionMetadata injectionMetadata,
		Class<?> clazz) {

		_beanFactory = beanFactory;
		_injectionMetadata = injectionMetadata;
		_class = clazz;
	}

	public void doWith(Field field) {
		if (!field.isAnnotationPresent(BeanReference.class)) {
			return;
		}

		if (Modifier.isStatic(field.getModifiers())) {
			throw new IllegalStateException(
				"@BeanReference annotation is not supported on static fields");
		}

		_injectionMetadata.addInjectedField(
			new BeanReferenceElement(_beanFactory, field, null));
	}

	public void doWith(Method method) {
		if (!method.isAnnotationPresent(BeanReference.class) ||
			!method.equals(ClassUtils.getMostSpecificMethod(method, _class))) {

			return;
		}

		if (Modifier.isStatic(method.getModifiers())) {
			throw new IllegalStateException(
				"@BeanReference annotation is not supported on static methods");
		}

		Class<?>[] parameterTypes = method.getParameterTypes();

		if (parameterTypes.length != 1) {
			throw new IllegalStateException(
				"@BeanReference annotation requires a single argument method " +
					method);
		}

		PropertyDescriptor propertyDescriptor = BeanUtils.findPropertyForMethod(
			method);

		_injectionMetadata.addInjectedMethod(
			new BeanReferenceElement(_beanFactory, method, propertyDescriptor));
	}

	private BeanFactory _beanFactory;
	private InjectionMetadata _injectionMetadata;
	private Class<?> _class;

}