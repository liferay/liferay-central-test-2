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

package com.liferay.portal.spring.transaction;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * <a href="TransactionInterceptor.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class TransactionInterceptor extends TransactionAspectSupport
	implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object thisObject = invocation.getThis();
		Method method = invocation.getMethod();

		Class<?> targetClass = null;
		if (thisObject != null) {
			targetClass = thisObject.getClass();
		}

		TransactionAttribute transactionAttribute =
			getTransactionAttributeSource().getTransactionAttribute(
				method, targetClass);
		String joinpointIdentification =
			method.getDeclaringClass().getName().concat(".").concat(
				method.getName());

		TransactionInfo transactionInfo = createTransactionIfNecessary(
			getTransactionManager(), transactionAttribute,
			joinpointIdentification);

		Object returnValue = null;
		try {
			returnValue = invocation.proceed();
		}
		catch (Throwable ex) {
			completeTransactionAfterThrowing(transactionInfo, ex);
			throw ex;
		}
		finally {
			cleanupTransactionInfo(transactionInfo);
		}
		commitTransactionAfterReturning(transactionInfo);
		return returnValue;
	}

}