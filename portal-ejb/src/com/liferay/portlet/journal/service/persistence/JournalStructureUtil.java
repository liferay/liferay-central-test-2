/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="JournalStructureUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalStructureUtil {
	public static final String CLASS_NAME = JournalStructureUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.journal.model.JournalStructure"));

	public static com.liferay.portlet.journal.model.JournalStructure create(
		com.liferay.portlet.journal.service.persistence.JournalStructurePK journalStructurePK) {
		return getPersistence().create(journalStructurePK);
	}

	public static com.liferay.portlet.journal.model.JournalStructure remove(
		com.liferay.portlet.journal.service.persistence.JournalStructurePK journalStructurePK)
		throws com.liferay.portlet.journal.NoSuchStructureException, 
			com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(journalStructurePK));
		}

		com.liferay.portlet.journal.model.JournalStructure journalStructure = getPersistence()
																				  .remove(journalStructurePK);

		if (listener != null) {
			listener.onAfterRemove(journalStructure);
		}

		return journalStructure;
	}

	public static com.liferay.portlet.journal.model.JournalStructure remove(
		com.liferay.portlet.journal.model.JournalStructure journalStructure)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (listener != null) {
			listener.onBeforeRemove(journalStructure);
		}

		journalStructure = getPersistence().remove(journalStructure);

		if (listener != null) {
			listener.onAfterRemove(journalStructure);
		}

		return journalStructure;
	}

	public static com.liferay.portlet.journal.model.JournalStructure update(
		com.liferay.portlet.journal.model.JournalStructure journalStructure)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = journalStructure.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(journalStructure);
			}
			else {
				listener.onBeforeUpdate(journalStructure);
			}
		}

		journalStructure = getPersistence().update(journalStructure);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(journalStructure);
			}
			else {
				listener.onAfterUpdate(journalStructure);
			}
		}

		return journalStructure;
	}

	public static com.liferay.portlet.journal.model.JournalStructure update(
		com.liferay.portlet.journal.model.JournalStructure journalStructure,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = journalStructure.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(journalStructure);
			}
			else {
				listener.onBeforeUpdate(journalStructure);
			}
		}

		journalStructure = getPersistence().update(journalStructure,
				saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(journalStructure);
			}
			else {
				listener.onAfterUpdate(journalStructure);
			}
		}

		return journalStructure;
	}

	public static com.liferay.portlet.journal.model.JournalStructure findByPrimaryKey(
		com.liferay.portlet.journal.service.persistence.JournalStructurePK journalStructurePK)
		throws com.liferay.portlet.journal.NoSuchStructureException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(journalStructurePK);
	}

	public static com.liferay.portlet.journal.model.JournalStructure fetchByPrimaryKey(
		com.liferay.portlet.journal.service.persistence.JournalStructurePK journalStructurePK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(journalStructurePK);
	}

	public static java.util.List findByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List findByGroupId(java.lang.String groupId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end);
	}

	public static java.util.List findByGroupId(java.lang.String groupId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure findByGroupId_First(
		java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchStructureException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure findByGroupId_Last(
		java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchStructureException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure[] findByGroupId_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalStructurePK journalStructurePK,
		java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchStructureException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_PrevAndNext(journalStructurePK,
			groupId, obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static int countByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static JournalStructurePersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		JournalStructureUtil util = (JournalStructureUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(JournalStructurePersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(JournalStructureUtil.class);
	private JournalStructurePersistence _persistence;
}