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
import com.liferay.counter.model.CounterHolder;
import com.liferay.counter.model.CounterRegister;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.concurrent.CompeteLatch;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.ObjectNotFoundException;
import com.liferay.portal.model.Dummy;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="CounterPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Michael Young
 * @author Shuyang Zhou
 */
public class CounterPersistenceImpl
	extends BasePersistenceImpl<Dummy> implements CounterPersistence {

	public List<String> getNames() throws SystemException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			ps = connection.prepareStatement(_SQL_SELECT_NAMES);

			rs = ps.executeQuery();

			List<String> list = new ArrayList<String>();

			while (rs.next()) {
				list.add(rs.getString(1));
			}

			return list;
		}
		catch (SQLException sqle) {
			throw processException(sqle);
		}
		finally {
			DataAccess.cleanUp(connection, ps, rs);
		}
	}

	public long increment() throws SystemException {
		return increment(_NAME);
	}

	public long increment(String name) throws SystemException {
		return increment(name, _MINIMUM_INCREMENT_SIZE);
	}

	public long increment(String name, int size) throws SystemException {
		if (size < _MINIMUM_INCREMENT_SIZE) {
			size = _MINIMUM_INCREMENT_SIZE;
		}

		CounterRegister register = getCounterRegister(name);

		return competeIncrement(register, size);
	}

	public void rename(String oldName, String newName) throws SystemException {
		CounterRegister register = getCounterRegister(oldName);

		synchronized (register) {
			if (_registerLookup.containsKey(newName)) {
				throw new SystemException(
					"Cannot rename " + oldName + " to " + newName);
			}

			Connection connection = null;
			PreparedStatement ps = null;

			try {
				connection = getConnection();

				ps = connection.prepareStatement(_SQL_UPDATE_NAME_BY_NAME);

				ps.setString(1, newName);
				ps.setString(2, oldName);

				ps.executeUpdate();
			}
			catch (ObjectNotFoundException onfe) {
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				DataAccess.cleanUp(connection, ps);
			}

			register.setName(newName);

			_registerLookup.put(newName, register);
			_registerLookup.remove(oldName);
		}
	}

	public void reset(String name) throws SystemException {
		CounterRegister register = getCounterRegister(name);

		synchronized (register) {
			Connection connection = null;
			PreparedStatement ps = null;

			try {
				connection = getConnection();

				ps = connection.prepareStatement(_SQL_DELETE_BY_NAME);

				ps.setString(1, name);

				ps.executeUpdate();
			}
			catch (ObjectNotFoundException onfe) {
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				DataAccess.cleanUp(connection, ps);
			}

			_registerLookup.remove(name);
		}
	}

	public void reset(String name, long size) throws SystemException {
		CounterRegister register = createCounterRegister(name, size);

		_registerLookup.put(name, register);
	}

	protected CounterRegister createCounterRegister(String name)
		throws SystemException {

		return createCounterRegister(name, -1);
	}

	protected CounterRegister createCounterRegister(String name, long size)
		throws SystemException {

		long rangeMin = -1;
		long rangeMax = -1;

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			ps = connection.prepareStatement(_SQL_SELECT_ID_BY_NAME);

			ps.setString(1, name);

			rs = ps.executeQuery();

			if (rs.next()) {
				rangeMin = rs.getLong(1);
				rangeMax = rangeMin + PropsValues.COUNTER_INCREMENT;

				rs.close();
				ps.close();

				ps = connection.prepareStatement(_SQL_UPDATE_ID_BY_NAME);

				ps.setLong(1, rangeMax);
				ps.setString(2, name);
			}
			else {
				rangeMin = _DEFAULT_CURRENT_ID;
				rangeMax = rangeMin + PropsValues.COUNTER_INCREMENT;

				rs.close();
				ps.close();

				ps = connection.prepareStatement(_SQL_INSERT);

				ps.setString(1, name);
				ps.setLong(2, rangeMax);
			}

			ps.executeUpdate();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			DataAccess.cleanUp(connection, ps, rs);
		}

		if (size > rangeMin) {
			rangeMin = size;
		}

		CounterRegister register = new CounterRegister(
			name, rangeMin, rangeMax, PropsValues.COUNTER_INCREMENT);

		return register;
	}

	protected Connection getConnection() throws SQLException {
		Connection connection = getDataSource().getConnection();

		connection.setAutoCommit(true);

		return connection;
	}

	protected CounterRegister getCounterRegister(String name)
		throws SystemException {

		CounterRegister register = _registerLookup.get(name);

		if (register != null) {
			return register;
		}
		else {
			synchronized (_registerLookup) {

				// Double check

				register = _registerLookup.get(name);

				if (register == null) {
					register = createCounterRegister(name);

					_registerLookup.put(name, register);
				}

				return register;
			}
		}
	}

	private long competeIncrement(CounterRegister register, int size)
		throws SystemException {

		CounterHolder holder = register.getCounterHolder();

		// Try to use the fast path

		long newValue = holder.addAndGet(size);

		if (newValue <= holder.getRangeMax()) {
			return newValue;
		}

		// Use the slow path

		CompeteLatch latch = register.getCompeteLatch();

		if (!latch.compete()) {

			// Loser thread has to wait for the winner thread to finish its job

			latch.await();

			// Compete again

			return competeIncrement(register, size);
		}

		// Winner thread

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			// Double check

			holder = register.getCounterHolder();
			newValue = holder.addAndGet(size);

			if (newValue > holder.getRangeMax()) {
				connection = getConnection();

				ps = connection.prepareStatement(_SQL_SELECT_ID_BY_NAME);

				ps.setString(1, register.getName());

				rs = ps.executeQuery();

				rs.next();

				long currentId = rs.getLong(1);

				newValue = currentId + 1;
				long rangeMax = currentId + register.getRangeSize();

				rs.close();
				ps.close();

				ps = connection.prepareStatement(_SQL_UPDATE_ID_BY_NAME);

				ps.setLong(1, rangeMax);
				ps.setString(2, register.getName());

				ps.executeUpdate();

				register.setCounterHolder(
					new CounterHolder(newValue, rangeMax));
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			DataAccess.cleanUp(connection, ps, rs);

			// Winner thread opens the latch so that loser threads can continue

			latch.done();
		}

		return newValue;
	}

	private static final int _DEFAULT_CURRENT_ID = 0;

	private static final int _MINIMUM_INCREMENT_SIZE = 1;

	private static final String _NAME = Counter.class.getName();

	private static final String _SQL_DELETE_BY_NAME =
		"delete from Counter where name = ?";

	private static final String _SQL_INSERT =
		"insert into Counter(name, currentId) values (?, ?)";

	private static final String _SQL_SELECT_ID_BY_NAME =
		"select currentId from Counter where name = ?";

	private static final String _SQL_SELECT_NAMES =
		"select name from Counter order by name asc";

	private static final String _SQL_UPDATE_ID_BY_NAME =
		"update Counter set currentId = ? where name = ?";

	private static final String _SQL_UPDATE_NAME_BY_NAME =
		"update Counter set name = ? where name = ?";

	private static final Map<String, CounterRegister> _registerLookup =
		new ConcurrentHashMap<String, CounterRegister>();

}