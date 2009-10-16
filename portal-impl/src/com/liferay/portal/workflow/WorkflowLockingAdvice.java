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

import com.liferay.portal.kernel.concurrent.ReadWriteLockKey;
import com.liferay.portal.kernel.concurrent.ReadWriteLockRegistry;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;

import java.util.concurrent.locks.Lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * <a href="WorkflowLockingAdvice.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class WorkflowLockingAdvice {

	public Object invoke(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {
		MethodSignature methodSignature =
			(MethodSignature) proceedingJoinPoint.getSignature();
		String methodName = methodSignature.getMethod().getName();
		Object[] arguments = proceedingJoinPoint.getArgs();
		String workflowDefinitionName = null;
		int workflowDefinitionVersion = -1;
		boolean writeLock = false;
		if (methodName.equals(UNDEPLOY_METHOD_NAME)) {
			WorkflowDefinition workflowDefinition =
				(WorkflowDefinition) arguments[0];
			workflowDefinitionName =
				workflowDefinition.getWorkflowDefinitionName();
			workflowDefinitionVersion =
				workflowDefinition.getWorkflowDefinitionVersion();
			writeLock = true;
		}
		else if (methodName.equals(START_METHOD_NAME)) {
			workflowDefinitionName = (String) arguments[0];
			workflowDefinitionVersion = (Integer) arguments[1];
		}

		WorkflowDefinitionKey workflowDefinitionKey =
			new WorkflowDefinitionKey(
			workflowDefinitionName, workflowDefinitionVersion);
		ReadWriteLockKey<WorkflowDefinitionKey> readWriteLockKey =
			new ReadWriteLockKey<WorkflowDefinitionKey>(
				workflowDefinitionKey, writeLock);
		Lock lock = _readWriteLockRegistry.acquireLock(readWriteLockKey);
		try {
			lock.lock();
			return proceedingJoinPoint.proceed();
		}
		finally {
			lock.unlock();
			_readWriteLockRegistry.releaseLock(readWriteLockKey);
		}
	}

	public static final String UNDEPLOY_METHOD_NAME =
		"undeployWorkflowDefinition";
	public static final String START_METHOD_NAME = "startWorkflowInstance";

	private final ReadWriteLockRegistry _readWriteLockRegistry =
		new ReadWriteLockRegistry();

}