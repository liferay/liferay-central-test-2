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