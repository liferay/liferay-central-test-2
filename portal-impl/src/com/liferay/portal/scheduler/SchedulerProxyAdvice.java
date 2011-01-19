/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.scheduler;

import com.liferay.portal.kernel.scheduler.StringLimiter;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.util.aspectj.AspectJUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author Tina Tian
 */
public class SchedulerProxyAdvice {

	public Object invoke(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		Object[] arguments = proceedingJoinPoint.getArgs();

		if (arguments.length != 0) {
			_handleAnnotations(proceedingJoinPoint, arguments);
		}

		return proceedingJoinPoint.proceed(arguments);
	}

	private void _doHanldeAnnotation(
		Annotation annotation, Object[] arguments, int argumentIndex) {

		Object argument = arguments[argumentIndex];

		if (argument == null) {
			return;
		}

		String stringArgument = (String)argument;

		int maxLength = ((StringLimiter)annotation).maxLength();

		if (stringArgument.length() > maxLength) {
			arguments[argumentIndex] = stringArgument.substring(0, maxLength);
		}
	}

	private String _getMethodUUID(Method method) {
		Class<?>[] parameterTypes = method.getParameterTypes();

		StringBundler sb = new StringBundler(2 * parameterTypes.length + 1);

		sb.append(method.getName());
		sb.append("_");

		for (int i = 0; i < parameterTypes.length; i++) {
			sb.append(parameterTypes[i].getName());
			
			if (i != parameterTypes.length - 1) {
				sb.append("_");
			}
		}

		return sb.toString();
	}

	private void _handleAnnotations(
			ProceedingJoinPoint proceedingJoinPoint, Object[] arguments)
		throws Throwable {

		Method method = AspectJUtil.getMethod(proceedingJoinPoint);
		Annotation[][] annotationArrays = method.getParameterAnnotations();

		String methodUUID = _getMethodUUID(method);

		int[] indexes = _annotationCache.get(methodUUID);

		if (indexes == null) {
			indexes = new int[arguments.length];

			for (int i = 0; i < annotationArrays.length; i++) {
				Annotation[] annotationArray = annotationArrays[i];

				if (annotationArray.length <= 0) {
					continue;
				}

				for (int j = 0; j < annotationArray.length; j++) {
					Annotation annotation = annotationArray[j];
					if ((annotation.annotationType() == StringLimiter.class) &&
						(arguments[i] instanceof String)) {

						indexes[i] = j + 1;
						break;
					}
				}
			}
		}

		for (int i = 0; i < indexes.length; i++) {
			int annotationIndex = indexes[i];

			if (annotationIndex <= 0) {
				continue;
			}

			Annotation annotation = annotationArrays[i][annotationIndex - 1];

			_doHanldeAnnotation(annotation, arguments, i);
		}
	}

	private Map<String, int[]> _annotationCache =
		new ConcurrentHashMap<String, int[]>();

}