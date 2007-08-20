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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portal.kernel.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="SCProductEntryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SCProductEntryUtil {
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry create(
		long productEntryId) {
		return getPersistence().create(productEntryId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry remove(
		long productEntryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(productEntryId));
		}

		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry = getPersistence()
																					  .remove(productEntryId);

		if (listener != null) {
			listener.onAfterRemove(scProductEntry);
		}

		return scProductEntry;
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry remove(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(scProductEntry);
		}

		scProductEntry = getPersistence().remove(scProductEntry);

		if (listener != null) {
			listener.onAfterRemove(scProductEntry);
		}

		return scProductEntry;
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry update(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = scProductEntry.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(scProductEntry);
			}
			else {
				listener.onBeforeUpdate(scProductEntry);
			}
		}

		scProductEntry = getPersistence().update(scProductEntry);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(scProductEntry);
			}
			else {
				listener.onAfterUpdate(scProductEntry);
			}
		}

		return scProductEntry;
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry update(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = scProductEntry.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(scProductEntry);
			}
			else {
				listener.onBeforeUpdate(scProductEntry);
			}
		}

		scProductEntry = getPersistence().update(scProductEntry, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(scProductEntry);
			}
			else {
				listener.onAfterUpdate(scProductEntry);
			}
		}

		return scProductEntry;
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry findByPrimaryKey(
		long productEntryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence().findByPrimaryKey(productEntryId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry fetchByPrimaryKey(
		long productEntryId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(productEntryId);
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

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry[] findByGroupId_PrevAndNext(
		long productEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence().findByGroupId_PrevAndNext(productEntryId,
			groupId, obc);
	}

	public static java.util.List findByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List findByCompanyId(long companyId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end);
	}

	public static java.util.List findByCompanyId(long companyId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry[] findByCompanyId_PrevAndNext(
		long productEntryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence().findByCompanyId_PrevAndNext(productEntryId,
			companyId, obc);
	}

	public static java.util.List findByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_U(groupId, userId);
	}

	public static java.util.List findByG_U(long groupId, long userId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByG_U(groupId, userId, begin, end);
	}

	public static java.util.List findByG_U(long groupId, long userId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_U(groupId, userId, begin, end, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence().findByG_U_First(groupId, userId, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence().findByG_U_Last(groupId, userId, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry[] findByG_U_PrevAndNext(
		long productEntryId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence().findByG_U_PrevAndNext(productEntryId, groupId,
			userId, obc);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer, begin,
			end);
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

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_U(groupId, userId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_U(groupId, userId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List getSCLicenses(long pk)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence().getSCLicenses(pk);
	}

	public static java.util.List getSCLicenses(long pk, int begin, int end)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence().getSCLicenses(pk, begin, end);
	}

	public static java.util.List getSCLicenses(long pk, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence().getSCLicenses(pk, begin, end, obc);
	}

	public static int getSCLicensesSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getSCLicensesSize(pk);
	}

	public static boolean containsSCLicense(long pk, long scLicensePK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsSCLicense(pk, scLicensePK);
	}

	public static boolean containsSCLicenses(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsSCLicenses(pk);
	}

	public static void addSCLicense(long pk, long scLicensePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException, 
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		getPersistence().addSCLicense(pk, scLicensePK);
	}

	public static void addSCLicense(long pk,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException, 
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		getPersistence().addSCLicense(pk, scLicense);
	}

	public static void addSCLicenses(long pk, long[] scLicensePKs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException, 
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		getPersistence().addSCLicenses(pk, scLicensePKs);
	}

	public static void addSCLicenses(long pk, java.util.List scLicenses)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException, 
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		getPersistence().addSCLicenses(pk, scLicenses);
	}

	public static void clearSCLicenses(long pk)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		getPersistence().clearSCLicenses(pk);
	}

	public static void removeSCLicense(long pk, long scLicensePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException, 
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		getPersistence().removeSCLicense(pk, scLicensePK);
	}

	public static void removeSCLicense(long pk,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException, 
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		getPersistence().removeSCLicense(pk, scLicense);
	}

	public static void removeSCLicenses(long pk, long[] scLicensePKs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException, 
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		getPersistence().removeSCLicenses(pk, scLicensePKs);
	}

	public static void removeSCLicenses(long pk, java.util.List scLicenses)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException, 
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		getPersistence().removeSCLicenses(pk, scLicenses);
	}

	public static void setSCLicenses(long pk, long[] scLicensePKs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException, 
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		getPersistence().setSCLicenses(pk, scLicensePKs);
	}

	public static void setSCLicenses(long pk, java.util.List scLicenses)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException, 
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		getPersistence().setSCLicenses(pk, scLicenses);
	}

	public static SCProductEntryPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(SCProductEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static SCProductEntryUtil _getUtil() {
		if (_util == null) {
			_util = (SCProductEntryUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = SCProductEntryUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.softwarecatalog.model.SCProductEntry"));
	private static Log _log = LogFactory.getLog(SCProductEntryUtil.class);
	private static SCProductEntryUtil _util;
	private SCProductEntryPersistence _persistence;
}