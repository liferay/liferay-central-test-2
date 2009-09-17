/*
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

package com.liferay.portal.kernel.messaging.proxy;

import com.liferay.portal.kernel.util.AggregateClassLoader;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <a href="MultiClassLoaderServiceRequest.java.html"><b><i>View Source</i></b></a>
 *
 * A MultiClassLoaderAwareServiceRequest determines if a service request
 * includes additional classloaders as part of its execution.
 *
 * If the service method contains ClassLoaders parameters annotated with
 * the ExecutingClassLoader annotation, then this ServiceRequest will
 * override the Thread's current context classloader prior to execution.
 *
 * The request will reset the classloader to the original classloader upon
 * completion.
 *
 * @author Michael C. Han
 */
public class MultiClassLoaderAwareServiceRequest extends ProxyRequest {
	public MultiClassLoaderAwareServiceRequest(
			Method method, Object[] arguments)
		throws Exception {

		super(method, arguments);

		ClassLoader[] classLoaders = inspectForClassLoaders(method);

		if (classLoaders != null) {
			_clientClassLoaders = classLoaders[0];

			if (classLoaders.length > 1) {
				int count = classLoaders.length;
				int index = 1;

				do {
					_clientClassLoaders = new AggregateClassLoader(
							classLoaders[index], _clientClassLoaders);

					index++;

				}
				while (count < index);
			}
		}
	}

	public Object execute(Object object) throws Exception {
		ClassLoader preInvocationClassLoader = null;

		if (_clientClassLoaders != null) {
			preInvocationClassLoader =
					Thread.currentThread().getContextClassLoader();

			AggregateClassLoader invocationClassLoader =
					new AggregateClassLoader(
							_clientClassLoaders, preInvocationClassLoader);

			Thread.currentThread().setContextClassLoader(invocationClassLoader);
		}

		try {
			return super.execute(object);
		}
		catch (InvocationTargetException e) {
			throw new Exception(e.getTargetException());
		}
		finally {
			if (preInvocationClassLoader != null) {
				Thread.currentThread().setContextClassLoader(
						preInvocationClassLoader);
			}
		}
	}

	protected ClassLoader[] inspectForClassLoaders(Method method)
		throws Exception {

		Annotation[][] annotationsArray = method.getParameterAnnotations();

		if ((annotationsArray == null) || (annotationsArray.length == 0)) {
			return null;
		}

		for (int i = 0; i < annotationsArray.length; i++) {
			Annotation[] annotations = annotationsArray[i];

			if ((annotations == null) || (annotations.length == 0)) {
				continue;
			}

			for (Annotation annotation : annotations) {
				if (ExecutingClassLoaders.class.isAssignableFrom(
						annotation.annotationType())) {

					return (ClassLoader[])getArguments()[i];
				}
			}
		}

		return null;
	}

	private ClassLoader _clientClassLoaders;
}
