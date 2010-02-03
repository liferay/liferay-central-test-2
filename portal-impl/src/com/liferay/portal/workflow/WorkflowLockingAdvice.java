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

package com.liferay.portal.workflow;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.service.LockLocalServiceUtil;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * <a href="WorkflowLockingAdvice.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class WorkflowLockingAdvice {

	public Object invoke(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		String methodName = proceedingJoinPoint.getSignature().getName();
		Object[] arguments = proceedingJoinPoint.getArgs();

		if (methodName.equals(_START_WORKFLOW_INSTANCE_METHOD_NAME)) {
			String workflowDefinitionName = (String)arguments[1];
			Integer workflowDefinitionVersion = (Integer)arguments[2];

			String className = WorkflowDefinition.class.getName();
			String key = _encodeKey(
				workflowDefinitionName, workflowDefinitionVersion);

			if (LockLocalServiceUtil.isLocked(className, key)) {
				throw new WorkflowException(
					"Workflow definition name " + workflowDefinitionName +
						" and version " + workflowDefinitionVersion +
							" is being undeployed");
			}

			return proceedingJoinPoint.proceed();
		}
		else if (!methodName.equals(
					_UNDEPLOY_WORKFLOW_DEFINITION_METHOD_NAME)) {

			return proceedingJoinPoint.proceed();
		}

		long userId = (Long)arguments[0];
		String name = (String)arguments[1];
		Integer version = (Integer)arguments[2];

		String className = WorkflowDefinition.class.getName();
		String key = _encodeKey(name, version);

		if (LockLocalServiceUtil.isLocked(className, key)) {
			throw new WorkflowException(
				"Workflow definition name " + name + " and version " + version +
					" is being undeployed");
		}

		try {
			LockLocalServiceUtil.lock(
				userId, className, key, String.valueOf(userId), false,
				Time.HOUR);

			return proceedingJoinPoint.proceed();
		}
		finally {
			LockLocalServiceUtil.unlock(className, key);
		}
	}

	private String _encodeKey(String name, int version) {
		StringBundler sb = new StringBundler(3);

		sb.append(name);
		sb.append(StringPool.POUND);
		sb.append(version);

		return sb.toString();
	}

	private static final String _START_WORKFLOW_INSTANCE_METHOD_NAME =
		"startWorkflowInstance";

	private static final String _UNDEPLOY_WORKFLOW_DEFINITION_METHOD_NAME =
		"undeployWorkflowDefinition";

}