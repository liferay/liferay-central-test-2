/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.dao.jdbc.aop;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Stack;

import javax.sql.DataSource;

import org.springframework.aop.TargetSource;

/**
 * <a href="DynamicDataSourceTargetSource.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 *
 */
public class DynamicDataSourceTargetSource implements TargetSource {
	
	public Stack<String> getMethodStack() {
		Stack<String> methodStack = _methodStackThreadLocal.get();

		if (methodStack == null) {
			methodStack = new Stack<String>();
			_methodStackThreadLocal.set(methodStack);
		}

		return methodStack;
	}

	public Operation getOperation() {
		Operation operation = _operationTypeThreadLocal.get();

		if (operation == null) {
			operation = Operation.TRANSACTIONAL;
			_operationTypeThreadLocal.set(operation);
		}

		return operation;
	}

	public Object getTarget() throws Exception {
		Operation operationType = getOperation();

		if (operationType == Operation.READ_ONLY) {
			if (_log.isTraceEnabled()) {
				_log.trace("Returning Read-Only Data Source");
			}
			return _readOnlyDataSource;
		}
		else {
			if (_log.isTraceEnabled()) {
				_log.trace("Returning Transactional Data Source");
			}
			return _transactionalDataSource;
		}
	}

	public Class<DataSource> getTargetClass() {
		return DataSource.class;
	}

	public boolean isStatic() {
		return false;
	}

	public void releaseTarget(Object target) throws Exception {
	}

	public String popMethod() {
		Stack<String> methodStack = getMethodStack();

		String method = methodStack.pop();

		setOperation(Operation.TRANSACTIONAL);

		return method;
	}

	public void pushMethod(String method) {
		Stack<String> methodStack = getMethodStack();

		methodStack.push(method);
	}

	public void setOperation(Operation operation) {
		if (_log.isDebugEnabled()) {
			_log.debug("Method stack " + getMethodStack());
		}

		if (!inOperation()) {
			_operationTypeThreadLocal.set(operation);
		}
	}

	public void setReadOnlyDataSource(DataSource readOnlyDataSource) {
		_readOnlyDataSource = readOnlyDataSource;
	}

	public void setTransactionalDataSource(DataSource transactionalDataSource) {
		_transactionalDataSource = transactionalDataSource;
	}

	protected boolean inOperation() {
		Stack<String> methodStack = getMethodStack();

		return !methodStack.empty();
	}

	private static ThreadLocal<Stack<String>> _methodStackThreadLocal =
		new ThreadLocal<Stack<String>>();

	private static ThreadLocal<Operation> _operationTypeThreadLocal =
		new ThreadLocal<Operation>();

	private static Log _log =
		LogFactoryUtil.getLog(DynamicDataSourceTargetSource.class);

	private DataSource _readOnlyDataSource;
	private DataSource _transactionalDataSource;

}