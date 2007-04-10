/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchPluginSettingException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.PluginSetting;
import com.liferay.portal.model.impl.PluginSettingImpl;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="PluginSettingPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PluginSettingPersistence extends BasePersistence {
	public PluginSetting create(long pluginSettingId) {
		PluginSetting pluginSetting = new PluginSettingImpl();
		pluginSetting.setNew(true);
		pluginSetting.setPrimaryKey(pluginSettingId);

		return pluginSetting;
	}

	public PluginSetting remove(long pluginSettingId)
		throws NoSuchPluginSettingException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PluginSetting pluginSetting = (PluginSetting)session.get(PluginSettingImpl.class,
					new Long(pluginSettingId));

			if (pluginSetting == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No PluginSetting exists with the primary key " +
						pluginSettingId);
				}

				throw new NoSuchPluginSettingException(
					"No PluginSetting exists with the primary key " +
					pluginSettingId);
			}

			return remove(pluginSetting);
		}
		catch (NoSuchPluginSettingException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PluginSetting remove(PluginSetting pluginSetting)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(pluginSetting);
			session.flush();

			return pluginSetting;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.PluginSetting update(
		com.liferay.portal.model.PluginSetting pluginSetting)
		throws SystemException {
		return update(pluginSetting, false);
	}

	public com.liferay.portal.model.PluginSetting update(
		com.liferay.portal.model.PluginSetting pluginSetting,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(pluginSetting);
			}
			else {
				if (pluginSetting.isNew()) {
					session.save(pluginSetting);
				}
			}

			session.flush();
			pluginSetting.setNew(false);

			return pluginSetting;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PluginSetting findByPrimaryKey(long pluginSettingId)
		throws NoSuchPluginSettingException, SystemException {
		PluginSetting pluginSetting = fetchByPrimaryKey(pluginSettingId);

		if (pluginSetting == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No PluginSetting exists with the primary key " +
					pluginSettingId);
			}

			throw new NoSuchPluginSettingException(
				"No PluginSetting exists with the primary key " +
				pluginSettingId);
		}

		return pluginSetting;
	}

	public PluginSetting fetchByPrimaryKey(long pluginSettingId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (PluginSetting)session.get(PluginSettingImpl.class,
				new Long(pluginSettingId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.PluginSetting WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCompanyId(String companyId, int begin, int end)
		throws SystemException {
		return findByCompanyId(companyId, begin, end, null);
	}

	public List findByCompanyId(String companyId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.PluginSetting WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PluginSetting findByCompanyId_First(String companyId,
		OrderByComparator obc)
		throws NoSuchPluginSettingException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No PluginSetting exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPluginSettingException(msg.toString());
		}
		else {
			return (PluginSetting)list.get(0);
		}
	}

	public PluginSetting findByCompanyId_Last(String companyId,
		OrderByComparator obc)
		throws NoSuchPluginSettingException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No PluginSetting exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPluginSettingException(msg.toString());
		}
		else {
			return (PluginSetting)list.get(0);
		}
	}

	public PluginSetting[] findByCompanyId_PrevAndNext(long pluginSettingId,
		String companyId, OrderByComparator obc)
		throws NoSuchPluginSettingException, SystemException {
		PluginSetting pluginSetting = findByPrimaryKey(pluginSettingId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.PluginSetting WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					pluginSetting);
			PluginSetting[] array = new PluginSettingImpl[3];
			array[0] = (PluginSetting)objArray[0];
			array[1] = (PluginSetting)objArray[1];
			array[2] = (PluginSetting)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PluginSetting findByC_I_T(String companyId, String pluginId,
		String pluginType) throws NoSuchPluginSettingException, SystemException {
		PluginSetting pluginSetting = fetchByC_I_T(companyId, pluginId,
				pluginType);

		if (pluginSetting == null) {
			StringMaker msg = new StringMaker();
			msg.append("No PluginSetting exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("pluginId=");
			msg.append(pluginId);
			msg.append(", ");
			msg.append("pluginType=");
			msg.append(pluginType);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPluginSettingException(msg.toString());
		}

		return pluginSetting;
	}

	public PluginSetting fetchByC_I_T(String companyId, String pluginId,
		String pluginType) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.PluginSetting WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (pluginId == null) {
				query.append("pluginId IS NULL");
			}
			else {
				query.append("pluginId = ?");
			}

			query.append(" AND ");

			if (pluginType == null) {
				query.append("pluginType IS NULL");
			}
			else {
				query.append("pluginType = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (pluginId != null) {
				q.setString(queryPos++, pluginId);
			}

			if (pluginType != null) {
				q.setString(queryPos++, pluginType);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			PluginSetting pluginSetting = (PluginSetting)list.get(0);

			return pluginSetting;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer,
		int begin, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);
			query.setLimit(begin, end);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List findAll(int begin, int end) throws SystemException {
		return findAll(begin, end, null);
	}

	public List findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.PluginSetting ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByCompanyId(String companyId) throws SystemException {
		Iterator itr = findByCompanyId(companyId).iterator();

		while (itr.hasNext()) {
			PluginSetting pluginSetting = (PluginSetting)itr.next();
			remove(pluginSetting);
		}
	}

	public void removeByC_I_T(String companyId, String pluginId,
		String pluginType) throws NoSuchPluginSettingException, SystemException {
		PluginSetting pluginSetting = findByC_I_T(companyId, pluginId,
				pluginType);
		remove(pluginSetting);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((PluginSetting)itr.next());
		}
	}

	public int countByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.PluginSetting WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_I_T(String companyId, String pluginId, String pluginType)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.PluginSetting WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (pluginId == null) {
				query.append("pluginId IS NULL");
			}
			else {
				query.append("pluginId = ?");
			}

			query.append(" AND ");

			if (pluginType == null) {
				query.append("pluginType IS NULL");
			}
			else {
				query.append("pluginType = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (pluginId != null) {
				q.setString(queryPos++, pluginId);
			}

			if (pluginType != null) {
				q.setString(queryPos++, pluginType);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.PluginSetting");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(PluginSettingPersistence.class);
}