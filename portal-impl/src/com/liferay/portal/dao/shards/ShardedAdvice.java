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

package com.liferay.portal.dao.shards;

import com.liferay.counter.service.persistence.CounterPersistence;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.persistence.ClassNamePersistence;
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.ReleasePersistence;
import com.liferay.portal.util.PropsValues;

import java.util.Stack;

import javax.sql.DataSource;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * <a href="ShardedAdvice.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This is where the main logic for sharded data sources occurs.  Using AOP,
 * any calls to the persistence tier will need to pick a specific shard based on
 * the corresponding company ID of the caller.
 * </p>
 *
 * @author Michael Young
 * @author Alexander Chow
 *
 */
public class ShardedAdvice {

	/**
	 * Calls the given method for each available shard.  This method will always
	 * return null since it cannot combine the results of each method into one
	 * return.  Hence, any method that is invoked globally must not be expecting
	 * value in return except null.
	 */
	public Object invokeGlobally(ProceedingJoinPoint call) throws Throwable {
		_globalCall.set(new Object());

		try {
			if (_log.isInfoEnabled()) {
				_log.info("All shards invoked for " + _getSignature(call));
			}

			for (String shardId : PropsValues.SHARD_AVAILABLE) {
				_shardedDataSourceTargetSource.setDataSource(shardId);
				_shardedSessionFactoryTargetSource.setSessionFactory(shardId);

				call.proceed();
			}
		}
		finally {
			_globalCall.set(null);
		}

		return null;
	}

	/**
	 * Calls the given method with a company-specific shard with two exceptions:
	 * (1) the persistence table that is being used is used globally across all
	 * shards and is therefore only valid on the default shard or (2) a global
	 * invocation is being made.
	 */
	public Object invokePersistence(ProceedingJoinPoint call) throws Throwable {
		Object target = call.getTarget();

		// These tables are all managed by the default shard

		if (target instanceof ClassNamePersistence ||
			target instanceof CompanyPersistence ||
			target instanceof CounterPersistence ||
			target instanceof ReleasePersistence) {

			_shardedDataSourceTargetSource.setDataSource(
				PropsValues.SHARD_DEFAULT);
			_shardedSessionFactoryTargetSource.setSessionFactory(
				PropsValues.SHARD_DEFAULT);

			if (_log.isDebugEnabled()) {
				_log.debug("Using default shard for " + _getSignature(call));
			}

			return call.proceed();
		}

		// Do not specify shard ID if currently making a global call

		if (_globalCall.get() == null) {
			_setShardIdByCompany();

			String shardId = _getShardId();

			_shardedDataSourceTargetSource.setDataSource(shardId);
			_shardedSessionFactoryTargetSource.setSessionFactory(shardId);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Using shardId " + shardId + " for " + _getSignature(call));
			}

			Object retVal = call.proceed();

			return retVal;
		}
		else {
			return call.proceed();
		}
	}

	public void setShardedDataSourceTargetSource(
			ShardedDataSourceTargetSource shardedDataSourceTargetSource) {

		_shardedDataSourceTargetSource = shardedDataSourceTargetSource;
	}

	public void setShardedSessionFactoryTargetSource(
			ShardedSessionFactoryTargetSource shardedSessionFactoryTargetSource)
	{
		_shardedSessionFactoryTargetSource = shardedSessionFactoryTargetSource;
	}

	protected DataSource getDataSource() {
		return _shardedDataSourceTargetSource.getDataSource();
	}

	protected String popCompanyService() {
		return _getCompanyServiceStack().pop();
	}

	protected void pushCompanyService(String shardId) {
		_getCompanyServiceStack().push(shardId);
	}

	protected void pushCompanyService(long companyId) {
		try {
			Company company = CompanyLocalServiceUtil.getCompany(companyId);

			pushCompanyService(company.getShardId());
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	private Stack<String> _getCompanyServiceStack() {
		Stack<String> companyServiceStack = _companyServiceStack.get();

		if (companyServiceStack == null) {
			companyServiceStack = new Stack<String>();

			_companyServiceStack.set(companyServiceStack);
		}

		return companyServiceStack;
	}

	private String _getShardId() {
		return _shardId.get();
	}

	private String _getSignature(ProceedingJoinPoint call) {
		String methodName = StringUtil.extractLast(
			call.getTarget().getClass().getName(), StringPool.PERIOD);

		methodName += StringPool.PERIOD + call.getSignature().getName() + "()";

		return methodName;
	}

	private void _setShardId(String shardId) {
		_shardId.set(shardId);
	}

	private void _setShardIdByCompany() throws Throwable {
		Stack<String> companyServiceStack = _getCompanyServiceStack();

		if (companyServiceStack.isEmpty()) {
			long companyId = CompanyThreadLocal.getCompanyId();

			_setShardIdByCompanyId(companyId);
		}
		else {
			String shardId = companyServiceStack.peek();

			_setShardId(shardId);
		}
	}

	private void _setShardIdByCompanyId(long companyId)
		throws PortalException, SystemException {

		if (companyId == 0) {

			// PortalInstances have not been instantiated yet

			_setShardId(PropsValues.SHARD_DEFAULT);
		}
		else {
			Company company = CompanyLocalServiceUtil.getCompany(companyId);

			_setShardId(company.getShardId());
		}
	}

	private static ThreadLocal<Object> _globalCall = new ThreadLocal<Object>();

	private static ThreadLocal<String> _shardId = new ThreadLocal<String>() {
		protected String initialValue() {
			return PropsValues.SHARD_DEFAULT;
		}
	};

	private static ThreadLocal<Stack<String>> _companyServiceStack =
		new ThreadLocal<Stack<String>>();

	private ShardedDataSourceTargetSource _shardedDataSourceTargetSource;

	private ShardedSessionFactoryTargetSource
		_shardedSessionFactoryTargetSource;

	private static Log _log = LogFactoryUtil.getLog(ShardedAdvice.class);

}