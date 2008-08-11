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

package com.liferay.portlet.messageboards.service.persistence;

/**
 * <a href="MBMailingUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBMailingUtil {
	public static com.liferay.portlet.messageboards.model.MBMailing create(
		long mailingId) {
		return getPersistence().create(mailingId);
	}

	public static com.liferay.portlet.messageboards.model.MBMailing remove(
		long mailingId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingException {
		return getPersistence().remove(mailingId);
	}

	public static com.liferay.portlet.messageboards.model.MBMailing remove(
		com.liferay.portlet.messageboards.model.MBMailing mbMailing)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(mbMailing);
	}

	/**
	 * @deprecated Use <code>update(MBMailing mbMailing, boolean merge)</code>.
	 */
	public static com.liferay.portlet.messageboards.model.MBMailing update(
		com.liferay.portlet.messageboards.model.MBMailing mbMailing)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(mbMailing);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        mbMailing the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when mbMailing is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.messageboards.model.MBMailing update(
		com.liferay.portlet.messageboards.model.MBMailing mbMailing,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(mbMailing, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBMailing updateImpl(
		com.liferay.portlet.messageboards.model.MBMailing mbMailing,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(mbMailing, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBMailing findByPrimaryKey(
		long mailingId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingException {
		return getPersistence().findByPrimaryKey(mailingId);
	}

	public static com.liferay.portlet.messageboards.model.MBMailing fetchByPrimaryKey(
		long mailingId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(mailingId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailing> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailing> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailing> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMailing findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMailing findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMailing[] findByUuid_PrevAndNext(
		long mailingId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingException {
		return getPersistence().findByUuid_PrevAndNext(mailingId, uuid, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMailing findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.messageboards.model.MBMailing fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.messageboards.model.MBMailing findByCategoryId(
		long categoryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingException {
		return getPersistence().findByCategoryId(categoryId);
	}

	public static com.liferay.portlet.messageboards.model.MBMailing fetchByCategoryId(
		long categoryId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByCategoryId(categoryId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailing> findByActive(
		boolean active) throws com.liferay.portal.SystemException {
		return getPersistence().findByActive(active);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailing> findByActive(
		boolean active, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByActive(active, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailing> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByActive(active, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMailing findByActive_First(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingException {
		return getPersistence().findByActive_First(active, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMailing findByActive_Last(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingException {
		return getPersistence().findByActive_Last(active, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMailing[] findByActive_PrevAndNext(
		long mailingId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingException {
		return getPersistence().findByActive_PrevAndNext(mailingId, active, obc);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailing> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailing> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailing> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	public static void removeByCategoryId(long categoryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingException {
		getPersistence().removeByCategoryId(categoryId);
	}

	public static void removeByActive(boolean active)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByActive(active);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	public static int countByCategoryId(long categoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCategoryId(categoryId);
	}

	public static int countByActive(boolean active)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByActive(active);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void registerListener(
		com.liferay.portal.model.ModelListener listener) {
		getPersistence().registerListener(listener);
	}

	public static void unregisterListener(
		com.liferay.portal.model.ModelListener listener) {
		getPersistence().unregisterListener(listener);
	}

	public static MBMailingPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(MBMailingPersistence persistence) {
		_persistence = persistence;
	}

	private static MBMailingPersistence _persistence;
}