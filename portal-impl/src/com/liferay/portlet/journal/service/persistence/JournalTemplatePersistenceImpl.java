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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="JournalTemplatePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalTemplatePersistenceImpl extends BasePersistenceImpl
	implements JournalTemplatePersistence {
	public JournalTemplate create(long id) {
		JournalTemplate journalTemplate = new JournalTemplateImpl();

		journalTemplate.setNew(true);
		journalTemplate.setPrimaryKey(id);

		String uuid = PortalUUIDUtil.generate();

		journalTemplate.setUuid(uuid);

		return journalTemplate;
	}

	public JournalTemplate remove(long id)
		throws NoSuchTemplateException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalTemplate journalTemplate = (JournalTemplate)session.get(JournalTemplateImpl.class,
					new Long(id));

			if (journalTemplate == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No JournalTemplate exists with the primary key " +
						id);
				}

				throw new NoSuchTemplateException(
					"No JournalTemplate exists with the primary key " + id);
			}

			return remove(journalTemplate);
		}
		catch (NoSuchTemplateException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalTemplate remove(JournalTemplate journalTemplate)
		throws SystemException {
		for (ModelListener<JournalTemplate> listener : listeners) {
			listener.onBeforeRemove(journalTemplate);
		}

		journalTemplate = removeImpl(journalTemplate);

		for (ModelListener<JournalTemplate> listener : listeners) {
			listener.onAfterRemove(journalTemplate);
		}

		return journalTemplate;
	}

	protected JournalTemplate removeImpl(JournalTemplate journalTemplate)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(JournalTemplateImpl.class,
						journalTemplate.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(journalTemplate);

			session.flush();

			return journalTemplate;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(JournalTemplate.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(JournalTemplate journalTemplate, boolean merge)</code>.
	 */
	public JournalTemplate update(JournalTemplate journalTemplate)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(JournalTemplate journalTemplate) method. Use update(JournalTemplate journalTemplate, boolean merge) instead.");
		}

		return update(journalTemplate, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        journalTemplate the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when journalTemplate is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public JournalTemplate update(JournalTemplate journalTemplate, boolean merge)
		throws SystemException {
		boolean isNew = journalTemplate.isNew();

		for (ModelListener<JournalTemplate> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(journalTemplate);
			}
			else {
				listener.onBeforeUpdate(journalTemplate);
			}
		}

		journalTemplate = updateImpl(journalTemplate, merge);

		for (ModelListener<JournalTemplate> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(journalTemplate);
			}
			else {
				listener.onAfterUpdate(journalTemplate);
			}
		}

		return journalTemplate;
	}

	public JournalTemplate updateImpl(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate,
		boolean merge) throws SystemException {
		if (Validator.isNull(journalTemplate.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalTemplate.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, journalTemplate, merge);

			journalTemplate.setNew(false);

			return journalTemplate;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(JournalTemplate.class.getName());
		}
	}

	public JournalTemplate findByPrimaryKey(long id)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByPrimaryKey(id);

		if (journalTemplate == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No JournalTemplate exists with the primary key " +
					id);
			}

			throw new NoSuchTemplateException(
				"No JournalTemplate exists with the primary key " + id);
		}

		return journalTemplate;
	}

	public JournalTemplate fetchByPrimaryKey(long id) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (JournalTemplate)session.get(JournalTemplateImpl.class,
				new Long(id));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalTemplate> findByUuid(String uuid)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "findByUuid";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { uuid };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("templateId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				List<JournalTemplate> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<JournalTemplate>)result;
		}
	}

	public List<JournalTemplate> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<JournalTemplate> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "findByUuid";
		String[] finderParams = new String[] {
				String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("templateId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				List<JournalTemplate> list = (List<JournalTemplate>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<JournalTemplate>)result;
		}
	}

	public JournalTemplate findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchTemplateException, SystemException {
		List<JournalTemplate> list = findByUuid(uuid, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalTemplate exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchTemplateException, SystemException {
		int count = countByUuid(uuid);

		List<JournalTemplate> list = findByUuid(uuid, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalTemplate exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate[] findByUuid_PrevAndNext(long id, String uuid,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

			if (uuid == null) {
				query.append("uuid_ IS NULL");
			}
			else {
				query.append("uuid_ = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("templateId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalTemplate);

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = (JournalTemplate)objArray[0];
			array[1] = (JournalTemplate)objArray[1];
			array[2] = (JournalTemplate)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalTemplate findByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByUUID_G(uuid, groupId);

		if (journalTemplate == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalTemplate exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTemplateException(msg.toString());
		}

		return journalTemplate;
	}

	public JournalTemplate fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "fetchByUUID_G";
		String[] finderParams = new String[] {
				String.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" AND ");

				query.append("groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("templateId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<JournalTemplate> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<JournalTemplate> list = (List<JournalTemplate>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<JournalTemplate> findByGroupId(long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("templateId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<JournalTemplate> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<JournalTemplate>)result;
		}
	}

	public List<JournalTemplate> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<JournalTemplate> findByGroupId(long groupId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("templateId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<JournalTemplate> list = (List<JournalTemplate>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<JournalTemplate>)result;
		}
	}

	public JournalTemplate findByGroupId_First(long groupId,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		List<JournalTemplate> list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalTemplate exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate findByGroupId_Last(long groupId,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		int count = countByGroupId(groupId);

		List<JournalTemplate> list = findByGroupId(groupId, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalTemplate exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate[] findByGroupId_PrevAndNext(long id, long groupId,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

			query.append("groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("templateId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalTemplate);

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = (JournalTemplate)objArray[0];
			array[1] = (JournalTemplate)objArray[1];
			array[2] = (JournalTemplate)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalTemplate> findByTemplateId(String templateId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "findByTemplateId";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { templateId };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

				if (templateId == null) {
					query.append("templateId IS NULL");
				}
				else {
					query.append("templateId = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("templateId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (templateId != null) {
					qPos.add(templateId);
				}

				List<JournalTemplate> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<JournalTemplate>)result;
		}
	}

	public List<JournalTemplate> findByTemplateId(String templateId, int start,
		int end) throws SystemException {
		return findByTemplateId(templateId, start, end, null);
	}

	public List<JournalTemplate> findByTemplateId(String templateId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "findByTemplateId";
		String[] finderParams = new String[] {
				String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				templateId,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

				if (templateId == null) {
					query.append("templateId IS NULL");
				}
				else {
					query.append("templateId = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("templateId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (templateId != null) {
					qPos.add(templateId);
				}

				List<JournalTemplate> list = (List<JournalTemplate>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<JournalTemplate>)result;
		}
	}

	public JournalTemplate findByTemplateId_First(String templateId,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		List<JournalTemplate> list = findByTemplateId(templateId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalTemplate exists with the key {");

			msg.append("templateId=" + templateId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate findByTemplateId_Last(String templateId,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		int count = countByTemplateId(templateId);

		List<JournalTemplate> list = findByTemplateId(templateId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalTemplate exists with the key {");

			msg.append("templateId=" + templateId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate[] findByTemplateId_PrevAndNext(long id,
		String templateId, OrderByComparator obc)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		int count = countByTemplateId(templateId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

			if (templateId == null) {
				query.append("templateId IS NULL");
			}
			else {
				query.append("templateId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("templateId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (templateId != null) {
				qPos.add(templateId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalTemplate);

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = (JournalTemplate)objArray[0];
			array[1] = (JournalTemplate)objArray[1];
			array[2] = (JournalTemplate)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalTemplate findBySmallImageId(long smallImageId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchBySmallImageId(smallImageId);

		if (journalTemplate == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalTemplate exists with the key {");

			msg.append("smallImageId=" + smallImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTemplateException(msg.toString());
		}

		return journalTemplate;
	}

	public JournalTemplate fetchBySmallImageId(long smallImageId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "fetchBySmallImageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(smallImageId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

				query.append("smallImageId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("templateId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				List<JournalTemplate> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<JournalTemplate> list = (List<JournalTemplate>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public JournalTemplate findByG_T(long groupId, String templateId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByG_T(groupId, templateId);

		if (journalTemplate == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalTemplate exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("templateId=" + templateId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTemplateException(msg.toString());
		}

		return journalTemplate;
	}

	public JournalTemplate fetchByG_T(long groupId, String templateId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "fetchByG_T";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(groupId), templateId };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (templateId == null) {
					query.append("templateId IS NULL");
				}
				else {
					query.append("templateId = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("templateId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (templateId != null) {
					qPos.add(templateId);
				}

				List<JournalTemplate> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<JournalTemplate> list = (List<JournalTemplate>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<JournalTemplate> findByG_S(long groupId, String structureId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "findByG_S";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(groupId), structureId };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (structureId == null) {
					query.append("structureId IS NULL");
				}
				else {
					query.append("structureId = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("templateId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
				}

				List<JournalTemplate> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<JournalTemplate>)result;
		}
	}

	public List<JournalTemplate> findByG_S(long groupId, String structureId,
		int start, int end) throws SystemException {
		return findByG_S(groupId, structureId, start, end, null);
	}

	public List<JournalTemplate> findByG_S(long groupId, String structureId,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "findByG_S";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				structureId,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (structureId == null) {
					query.append("structureId IS NULL");
				}
				else {
					query.append("structureId = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("templateId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
				}

				List<JournalTemplate> list = (List<JournalTemplate>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<JournalTemplate>)result;
		}
	}

	public JournalTemplate findByG_S_First(long groupId, String structureId,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		List<JournalTemplate> list = findByG_S(groupId, structureId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalTemplate exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("structureId=" + structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate findByG_S_Last(long groupId, String structureId,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		int count = countByG_S(groupId, structureId);

		List<JournalTemplate> list = findByG_S(groupId, structureId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalTemplate exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("structureId=" + structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate[] findByG_S_PrevAndNext(long id, long groupId,
		String structureId, OrderByComparator obc)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		int count = countByG_S(groupId, structureId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

			query.append("groupId = ?");

			query.append(" AND ");

			if (structureId == null) {
				query.append("structureId IS NULL");
			}
			else {
				query.append("structureId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("templateId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (structureId != null) {
				qPos.add(structureId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalTemplate);

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = (JournalTemplate)objArray[0];
			array[1] = (JournalTemplate)objArray[1];
			array[2] = (JournalTemplate)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalTemplate> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalTemplate> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<JournalTemplate> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.journal.model.JournalTemplate ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("templateId ASC");
				}

				Query q = session.createQuery(query.toString());

				List<JournalTemplate> list = null;

				if (obc == null) {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<JournalTemplate>)result;
		}
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (JournalTemplate journalTemplate : findByUuid(uuid)) {
			remove(journalTemplate);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByUUID_G(uuid, groupId);

		remove(journalTemplate);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (JournalTemplate journalTemplate : findByGroupId(groupId)) {
			remove(journalTemplate);
		}
	}

	public void removeByTemplateId(String templateId) throws SystemException {
		for (JournalTemplate journalTemplate : findByTemplateId(templateId)) {
			remove(journalTemplate);
		}
	}

	public void removeBySmallImageId(long smallImageId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findBySmallImageId(smallImageId);

		remove(journalTemplate);
	}

	public void removeByG_T(long groupId, String templateId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByG_T(groupId, templateId);

		remove(journalTemplate);
	}

	public void removeByG_S(long groupId, String structureId)
		throws SystemException {
		for (JournalTemplate journalTemplate : findByG_S(groupId, structureId)) {
			remove(journalTemplate);
		}
	}

	public void removeAll() throws SystemException {
		for (JournalTemplate journalTemplate : findAll()) {
			remove(journalTemplate);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "countByUuid";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { uuid };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "countByUUID_G";
		String[] finderParams = new String[] {
				String.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" AND ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "countByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByTemplateId(String templateId) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "countByTemplateId";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { templateId };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

				if (templateId == null) {
					query.append("templateId IS NULL");
				}
				else {
					query.append("templateId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (templateId != null) {
					qPos.add(templateId);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countBySmallImageId(long smallImageId) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "countBySmallImageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(smallImageId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

				query.append("smallImageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByG_T(long groupId, String templateId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "countByG_T";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(groupId), templateId };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (templateId == null) {
					query.append("templateId IS NULL");
				}
				else {
					query.append("templateId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (templateId != null) {
					qPos.add(templateId);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByG_S(long groupId, String structureId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "countByG_S";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(groupId), structureId };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (structureId == null) {
					query.append("structureId IS NULL");
				}
				else {
					query.append("structureId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = JournalTemplateModelImpl.CACHE_ENABLED;
		String finderClassName = JournalTemplate.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.journal.model.JournalTemplate");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.journal.model.JournalTemplate")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JournalTemplate>> listenersList = new ArrayList<ModelListener<JournalTemplate>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JournalTemplate>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticlePersistence.impl")
	protected com.liferay.portlet.journal.service.persistence.JournalArticlePersistence journalArticlePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticleImagePersistence.impl")
	protected com.liferay.portlet.journal.service.persistence.JournalArticleImagePersistence journalArticleImagePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence.impl")
	protected com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence.impl")
	protected com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence journalContentSearchPersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalFeedPersistence.impl")
	protected com.liferay.portlet.journal.service.persistence.JournalFeedPersistence journalFeedPersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalStructurePersistence.impl")
	protected com.liferay.portlet.journal.service.persistence.JournalStructurePersistence journalStructurePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence.impl")
	protected com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence journalTemplatePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence.impl")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence.impl")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	private static Log _log = LogFactoryUtil.getLog(JournalTemplatePersistenceImpl.class);
}