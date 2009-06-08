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

package com.liferay.counter.service.persistence;

import com.liferay.counter.model.Counter;
import com.liferay.counter.model.CounterRegister;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.LockMode;
import com.liferay.portal.kernel.dao.orm.ObjectNotFoundException;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.job.IntervalJob;
import com.liferay.portal.kernel.job.JobSchedulerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <a href="CounterPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Michael Young
 *
 */
public class CounterPersistence extends BasePersistenceImpl {

	public static int getCounterIncrement() {
		return _COUNTER_INCREMENT;
	}

	public void afterPropertiesSet() {
		JobSchedulerUtil.schedule(_connectionHeartbeatJob);
	}

	public synchronized Connection getConnection() throws Exception {
		if ((_connection == null) || _connection.isClosed()) {
			_connection = getDataSource().getConnection();

			_connection.setAutoCommit(true);
		}

		return _connection;
	}

	public void destroy() {
		JobSchedulerUtil.unschedule(_connectionHeartbeatJob);

		DataAccess.cleanUp(_connection);
	}

	public List<String> getNames() throws SystemException {
		Session session = null;

		try {
			Connection connection = getConnection();

			session = _sessionFactory.openNewSession(connection);

			List<String> list = new ArrayList<String>();

			Query q = session.createQuery(
				"SELECT counter FROM Counter counter");

			Iterator<Counter> itr = q.iterate();

			while (itr.hasNext()) {
				Counter counter = itr.next();

				list.add(counter.getName());
			}

			return ListUtil.sort(list);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			session.close();
		}
	}

	public long increment() throws SystemException {
		return increment(_NAME);
	}

	public long increment(String name) throws SystemException {
		return increment(name, _MINIMUM_INCREMENT_SIZE);
	}

	public long increment(String name, int size)
		throws SystemException {

		if (size < _MINIMUM_INCREMENT_SIZE) {
			size = _MINIMUM_INCREMENT_SIZE;
		}

		CounterRegister register = getCounterRegister(name);

		synchronized (register) {
			long newValue = register.getCurrentValue() + size;

			if (newValue > register.getRangeMax()) {
				Session session = null;

				try {
					Connection connection = getConnection();

					session = _sessionFactory.openNewSession(connection);

					Counter counter = (Counter)session.get(
						Counter.class, register.getName());

					newValue = counter.getCurrentId() + 1;

					long rangeMax =
						counter.getCurrentId() + register.getRangeSize();

					counter.setCurrentId(rangeMax);

					session.save(counter);
					session.flush();

					register.setCurrentValue(newValue);
					register.setRangeMax(rangeMax);
				}
				catch (Exception e) {
					throw processException(e);
				}
				finally {
					session.close();
				}
			}
			else {
				register.setCurrentValue(newValue);
			}

			return newValue;
		}
	}

	public void rename(String oldName, String newName)
		throws SystemException {

		CounterRegister register = getCounterRegister(oldName);

		synchronized (register) {
			if (_registerLookup.containsKey(newName)) {
				throw new SystemException(
					"Cannot rename " + oldName + " to " + newName);
			}

			Session session = null;

			try {
				Connection connection = getConnection();

				session = _sessionFactory.openNewSession(connection);

				Counter counter = (Counter)session.load(Counter.class, oldName);

				long currentId = counter.getCurrentId();

				session.delete(counter);

				counter = new Counter();

				counter.setName(newName);
				counter.setCurrentId(currentId);

				session.save(counter);

				session.flush();
			}
			catch (ObjectNotFoundException onfe) {
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				session.close();
			}

			register.setName(newName);

			_registerLookup.put(newName, register);
			_registerLookup.remove(oldName);
		}
	}

	public void reset(String name) throws SystemException {
		CounterRegister register = getCounterRegister(name);

		synchronized (register) {
			Session session = null;

			try {
				Connection connection = getConnection();

				session = _sessionFactory.openNewSession(connection);

				Counter counter = (Counter)session.load(Counter.class, name);

				session.delete(counter);

				session.flush();
			}
			catch (ObjectNotFoundException onfe) {
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				session.close();
			}

			_registerLookup.remove(name);
		}
	}

	public void reset(String name, long size) throws SystemException {
		CounterRegister register = createCounterRegister(name, size);

		synchronized (register) {
			_registerLookup.put(name, register);
		}
	}

	public void setConnectionHeartbeatJob(IntervalJob connectionHeartbeatJob) {
		_connectionHeartbeatJob = connectionHeartbeatJob;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}

	protected synchronized CounterRegister getCounterRegister(String name)
		throws SystemException {

		CounterRegister register = _registerLookup.get(name);

		if (register == null) {
			register = createCounterRegister(name);

			_registerLookup.put(name, register);
		}

		return register;
	}

	protected synchronized CounterRegister createCounterRegister(String name)
		throws SystemException {

		return createCounterRegister(name, -1);
	}

	protected synchronized CounterRegister createCounterRegister(
			String name, long size)
		throws SystemException {

		long rangeMin = 0;
		long rangeMax = 0;

		Session session = null;

		try {
			Connection connection = getConnection();

			session = _sessionFactory.openNewSession(connection);

			Counter counter = (Counter)session.get(
				Counter.class, name, LockMode.UPGRADE);

			if (counter == null) {
				rangeMin = _DEFAULT_CURRENT_ID;

				counter = new Counter();

				counter.setName(name);
			}
			else {
				rangeMin = counter.getCurrentId();
			}

			if (size >= _DEFAULT_CURRENT_ID) {
				rangeMin = size;
			}

			rangeMax = rangeMin + _COUNTER_INCREMENT;

			counter.setCurrentId(rangeMax);

			session.save(counter);
			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			session.close();
		}

		CounterRegister register = new CounterRegister(
			name, rangeMin, rangeMax, _COUNTER_INCREMENT);

		return register;
	}

	private static final int _DEFAULT_CURRENT_ID = 0;

	private static final int _MINIMUM_INCREMENT_SIZE = 1;

	private static final int _COUNTER_INCREMENT = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.COUNTER_INCREMENT), _MINIMUM_INCREMENT_SIZE);

	private static final String _NAME = Counter.class.getName();

	private static Map<String, CounterRegister> _registerLookup =
		new HashMap<String, CounterRegister>();

	private Connection _connection;
	private IntervalJob _connectionHeartbeatJob;
	private SessionFactory _sessionFactory;

}