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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="JournalStructureUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalStructureUtil {
	public static com.liferay.portlet.journal.model.JournalStructure create(
		com.liferay.portlet.journal.service.persistence.JournalStructurePK journalStructurePK) {
		return getPersistence().create(journalStructurePK);
	}

	public static com.liferay.portlet.journal.model.JournalStructure remove(
		com.liferay.portlet.journal.service.persistence.JournalStructurePK journalStructurePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchStructureException {
		ModelListener listener = _getListener();

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
		ModelListener listener = _getListener();

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
		ModelListener listener = _getListener();
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
		ModelListener listener = _getListener();
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
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByPrimaryKey(journalStructurePK);
	}

	public static com.liferay.portlet.journal.model.JournalStructure fetchByPrimaryKey(
		com.liferay.portlet.journal.service.persistence.JournalStructurePK journalStructurePK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(journalStructurePK);
	}

	public static java.util.List findByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List findByGroupId(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end);
	}

	public static java.util.List findByGroupId(long groupId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure[] findByGroupId_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalStructurePK journalStructurePK,
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByGroupId_PrevAndNext(journalStructurePK,
			groupId, obc);
	}

	public static java.util.List findByC_S(java.lang.String companyId,
		java.lang.String structureId) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_S(companyId, structureId);
	}

	public static java.util.List findByC_S(java.lang.String companyId,
		java.lang.String structureId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_S(companyId, structureId, begin, end);
	}

	public static java.util.List findByC_S(java.lang.String companyId,
		java.lang.String structureId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_S(companyId, structureId, begin, end,
			obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure findByC_S_First(
		java.lang.String companyId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByC_S_First(companyId, structureId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure findByC_S_Last(
		java.lang.String companyId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByC_S_Last(companyId, structureId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure[] findByC_S_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalStructurePK journalStructurePK,
		java.lang.String companyId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByC_S_PrevAndNext(journalStructurePK,
			companyId, structureId, obc);
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
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByC_S(java.lang.String companyId,
		java.lang.String structureId) throws com.liferay.portal.SystemException {
		getPersistence().removeByC_S(companyId, structureId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByC_S(java.lang.String companyId,
		java.lang.String structureId) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_S(companyId, structureId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static JournalStructurePersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(JournalStructurePersistence persistence) {
		_persistence = persistence;
	}

	private static JournalStructureUtil _getUtil() {
		if (_util == null) {
			_util = (JournalStructureUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _UTIL = JournalStructureUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.journal.model.JournalStructure"));
	private static Log _log = LogFactory.getLog(JournalStructureUtil.class);
	private static JournalStructureUtil _util;
	private JournalStructurePersistence _persistence;
}