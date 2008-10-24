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

import com.liferay.portal.kernel.util.StringPool;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

/**
 * <a href="DynamicDataSourceAdvice.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 *
 */
public class DynamicDataSourceAdvice {

	public Object invoke(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		Signature signature = proceedingJoinPoint.getSignature();

		String methodName = signature.getName();

		if (isReadOnlyMethod(methodName)) {
			_dynamicDataSourceTargetSource.setOperation(Operation.READ_ONLY);
		}
		else {
			_dynamicDataSourceTargetSource.setOperation(
				Operation.TRANSACTIONAL);
		}

		StringBuilder sb = new StringBuilder();

		sb.append(signature.getDeclaringType().getName());
		sb.append(StringPool.PERIOD);
		sb.append(methodName);

		_dynamicDataSourceTargetSource.pushMethod(sb.toString());

		Object returnValue = null;

		try {
			returnValue = proceedingJoinPoint.proceed();
		}
		finally {
			_dynamicDataSourceTargetSource.popMethod();
		}

		return returnValue;
	}

	public boolean isReadOnlyMethod(String methodName) {
		if ((methodName.startsWith("get") || methodName.startsWith("search")) &&
			!methodName.startsWith("getPreferences") &&
			!methodName.startsWith("getResource")) {

			return true;
		}

		return false;
	}

	public void setDynamicDataSourceTargetSource(
		DynamicDataSourceTargetSource dynamicDataSourceTargetSource) {

		_dynamicDataSourceTargetSource = dynamicDataSourceTargetSource;
	}

	private DynamicDataSourceTargetSource _dynamicDataSourceTargetSource;

}