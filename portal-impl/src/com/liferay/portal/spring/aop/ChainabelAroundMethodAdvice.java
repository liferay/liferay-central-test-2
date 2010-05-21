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

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * <a href="ChainabelAroundMethodAdvice.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public abstract class ChainabelAroundMethodAdvice implements MethodInterceptor {

	public abstract Object before(MethodInvocation methodInvocation)
		throws Throwable;
	public abstract void afterReturning(
		MethodInvocation MethodInvocation, Object result) throws Throwable;
	public abstract void afterThrowing(
			MethodInvocation MethodInvocation, Throwable throwable)
		throws Throwable;

	final public Object invoke(MethodInvocation methodInvocation)
		throws Throwable {

		Object returnValue = before(methodInvocation);
		if (returnValue != null) {
			return returnValue;
		}
		try {
			if (_nextMethodInterceptor != null) {
				returnValue = _nextMethodInterceptor.invoke(methodInvocation);
			}
			else {
				returnValue = methodInvocation.proceed();
			}
			afterReturning(methodInvocation, returnValue);
		}catch(Throwable throwable) {
			afterThrowing(methodInvocation, throwable);
			throw throwable;
		}
		return returnValue;
	}

	public void setNextMethodInterceptor(
		MethodInterceptor nextMethodInterceptor) {
		_nextMethodInterceptor = nextMethodInterceptor;
	}

	private MethodInterceptor _nextMethodInterceptor;

}