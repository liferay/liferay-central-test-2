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

import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.util.aspectj.AspectJUtil;

import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author Tina Tian
 * @author Brian Wing Shun Chan
 */
public class SchedulerProxyAdvice {

	public Object invoke(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		Object[] arguments = proceedingJoinPoint.getArgs();

		fixArguments(proceedingJoinPoint);

		return proceedingJoinPoint.proceed(arguments);
	}

	protected String fixArgumentMaxLength(String argument, int maxLength) {
		if (argument == null) {
			return null;
		}

		if (argument.length() > maxLength) {
			argument = argument.substring(0, maxLength);
		}

		return argument;
	}

	protected void fixArguments(ProceedingJoinPoint proceedingJoinPoint)
		throws Exception {

		Object[] arguments = proceedingJoinPoint.getArgs();

		if (arguments.length == 0) {
			return;
		}

		Method method = AspectJUtil.getMethod(proceedingJoinPoint);

		TypeVariable<Method>[] typeParameters = method.getTypeParameters();

		for (int i = 0; i < typeParameters.length; i++) {
			TypeVariable<Method> typeParameter = typeParameters[i];

			String typeParameterName = typeParameter.getName();

			if (typeParameterName.equals("groupName")) {
				String groupName = (String)arguments[i];

				arguments[i] = fixArgumentMaxLength(
					groupName, SchedulerEngine.GROUP_NAME_MAX_LENGTH);
			}
			else if (typeParameterName.equals("jobName")) {
				String jobName = (String)arguments[i];

				arguments[i] = fixArgumentMaxLength(
					jobName, SchedulerEngine.JOB_NAME_MAX_LENGTH);
			}
		}
	}

}