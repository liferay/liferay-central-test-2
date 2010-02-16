/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.journal.model.JournalStructure;

import java.util.List;

/**
 * <a href="JournalStructureUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalStructurePersistence
 * @see       JournalStructurePersistenceImpl
 * @generated
 */
public class JournalStructureUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static JournalStructure remove(JournalStructure journalStructure)
		throws SystemException {
		return getPersistence().remove(journalStructure);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static JournalStructure update(JournalStructure journalStructure,
		boolean merge) throws SystemException {
		return getPersistence().update(journalStructure, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.journal.model.JournalStructure journalStructure) {
		getPersistence().cacheResult(journalStructure);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.journal.model.JournalStructure> journalStructures) {
		getPersistence().cacheResult(journalStructures);
	}

	public static com.liferay.portlet.journal.model.JournalStructure create(
		long id) {
		return getPersistence().create(id);
	}

	public static com.liferay.portlet.journal.model.JournalStructure remove(
		long id)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().remove(id);
	}

	public static com.liferay.portlet.journal.model.JournalStructure updateImpl(
		com.liferay.portlet.journal.model.JournalStructure journalStructure,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(journalStructure, merge);
	}

	public static com.liferay.portlet.journal.model.JournalStructure findByPrimaryKey(
		long id)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByPrimaryKey(id);
	}

	public static com.liferay.portlet.journal.model.JournalStructure fetchByPrimaryKey(
		long id) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(id);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure[] findByUuid_PrevAndNext(
		long id, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByUuid_PrevAndNext(id, uuid, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.journal.model.JournalStructure fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.journal.model.JournalStructure fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure[] findByGroupId_PrevAndNext(
		long id, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByGroupId_PrevAndNext(id, groupId, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByStructureId(
		java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByStructureId(structureId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByStructureId(
		java.lang.String structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByStructureId(structureId, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByStructureId(
		java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByStructureId(structureId, start, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure findByStructureId_First(
		java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByStructureId_First(structureId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure findByStructureId_Last(
		java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByStructureId_Last(structureId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure[] findByStructureId_PrevAndNext(
		long id, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence()
				   .findByStructureId_PrevAndNext(id, structureId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure findByG_S(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByG_S(groupId, structureId);
	}

	public static com.liferay.portlet.journal.model.JournalStructure fetchByG_S(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_S(groupId, structureId);
	}

	public static com.liferay.portlet.journal.model.JournalStructure fetchByG_S(
		long groupId, java.lang.String structureId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_S(groupId, structureId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByG_P(
		long groupId, java.lang.String parentStructureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, parentStructureId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByG_P(
		long groupId, java.lang.String parentStructureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, parentStructureId, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByG_P(
		long groupId, java.lang.String parentStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P(groupId, parentStructureId, start, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure findByG_P_First(
		long groupId, java.lang.String parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByG_P_First(groupId, parentStructureId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure findByG_P_Last(
		long groupId, java.lang.String parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByG_P_Last(groupId, parentStructureId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalStructure[] findByG_P_PrevAndNext(
		long id, long groupId, java.lang.String parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence()
				   .findByG_P_PrevAndNext(id, groupId, parentStructureId, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByStructureId(java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByStructureId(structureId);
	}

	public static void removeByG_S(long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		getPersistence().removeByG_S(groupId, structureId);
	}

	public static void removeByG_P(long groupId,
		java.lang.String parentStructureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P(groupId, parentStructureId);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByStructureId(java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByStructureId(structureId);
	}

	public static int countByG_S(long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_S(groupId, structureId);
	}

	public static int countByG_P(long groupId,
		java.lang.String parentStructureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P(groupId, parentStructureId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static JournalStructurePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (JournalStructurePersistence)PortalBeanLocatorUtil.locate(JournalStructurePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(JournalStructurePersistence persistence) {
		_persistence = persistence;
	}

	private static JournalStructurePersistence _persistence;
}