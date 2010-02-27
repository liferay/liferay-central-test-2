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

package com.liferay.portal.workflow;

import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortletKeys;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * <a href="WorkflowPermissionAdvice.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WorkflowPermissionAdvice {

	public Object invoke(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		String methodName = proceedingJoinPoint.getSignature().getName();
		Object[] arguments = proceedingJoinPoint.getArgs();

		if (methodName.equals(_ASSIGN_WORKFLOW_TASK_TO_USER_METHOD_NAME)) {
			long userId = (Long)arguments[0];

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if (permissionChecker.getUserId() != userId) {
				throw new PrincipalException();
			}

			PortletPermissionUtil.check(
				permissionChecker, PortletKeys.WORKFLOW_TASKS,
				ActionKeys.ASSIGN_USER_TASKS);
		}

		return proceedingJoinPoint.proceed();
	}

	private static final String _ASSIGN_WORKFLOW_TASK_TO_USER_METHOD_NAME =
		"assignWorkflowTaskToUser";

}