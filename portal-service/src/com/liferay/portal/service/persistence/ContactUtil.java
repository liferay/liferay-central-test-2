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

package com.liferay.portal.service.persistence;

/**
 * <a href="ContactUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ContactUtil {
	public static com.liferay.portal.model.Contact create(long contactId) {
		return getPersistence().create(contactId);
	}

	public static com.liferay.portal.model.Contact remove(long contactId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchContactException {
		return getPersistence().remove(contactId);
	}

	public static com.liferay.portal.model.Contact remove(
		com.liferay.portal.model.Contact contact)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(contact);
	}

	/**
	 * @deprecated Use <code>update(Contact contact, boolean merge)</code>.
	 */
	public static com.liferay.portal.model.Contact update(
		com.liferay.portal.model.Contact contact)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(contact);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        contact the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when contact is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portal.model.Contact update(
		com.liferay.portal.model.Contact contact, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(contact, merge);
	}

	public static com.liferay.portal.model.Contact updateImpl(
		com.liferay.portal.model.Contact contact, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(contact, merge);
	}

	public static com.liferay.portal.model.Contact findByPrimaryKey(
		long contactId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchContactException {
		return getPersistence().findByPrimaryKey(contactId);
	}

	public static com.liferay.portal.model.Contact fetchByPrimaryKey(
		long contactId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(contactId);
	}

	public static java.util.List<com.liferay.portal.model.Contact> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Contact> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Contact> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portal.model.Contact findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchContactException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.Contact findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchContactException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.Contact[] findByCompanyId_PrevAndNext(
		long contactId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchContactException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(contactId, companyId, obc);
	}

	public static java.util.List<com.liferay.portal.model.Contact> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.Contact> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findWithDynamicQuery(queryInitializer, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Contact> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Contact> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Contact> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static ContactPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(ContactPersistence persistence) {
		_persistence = persistence;
	}

	private static ContactUtil _getUtil() {
		if (_util == null) {
			_util = (ContactUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = ContactUtil.class.getName();
	private static ContactUtil _util;
	private ContactPersistence _persistence;
}