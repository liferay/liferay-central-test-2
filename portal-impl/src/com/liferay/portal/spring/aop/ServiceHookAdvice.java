/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.aop;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InitialThreadLocal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * <a href="ServiceHookAdvice.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ServiceHookAdvice {

	public static Object getService(String className) {
		return _services.get(className);
	}

	public static void setService(String className, Object service) {
		if (_log.isDebugEnabled()) {
			if (service == null) {
				_log.debug("Remove service hook " + className);
			}
			else {
				_log.debug(
					"Add service hook " + className + " " +
						service.getClass().getName());
			}
		}

		if (service == null) {
			_services.remove(className);
		}
		else {
			_services.put(className, service);
		}
	}

	public Object invoke(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		if (_immediatelyProceed.get()) {

			// If the thread local variable to immedately proceed is set to
			// true, then immediately proceed to prevent an infinite loop

			_immediatelyProceed.set(Boolean.FALSE);

			return proceedingJoinPoint.proceed();
		}

		MethodSignature methodSignature =
			(MethodSignature)proceedingJoinPoint.getSignature();

		String className = methodSignature.getDeclaringTypeName();

		Object service = _services.get(className);

		if (service == null) {
			return proceedingJoinPoint.proceed();
		}

		_immediatelyProceed.set(Boolean.TRUE);

		try {
			Method method = methodSignature.getMethod();

			return method.invoke(service, proceedingJoinPoint.getArgs());
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
		finally {
			_immediatelyProceed.remove();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ServiceHookAdvice.class);

	private static ThreadLocal<Boolean> _immediatelyProceed =
		new InitialThreadLocal<Boolean>(false);
	private static Map<String, Object> _services =
		new ConcurrentHashMap<String, Object>();

}