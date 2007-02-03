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

package com.liferay.portlet.softwarerepository.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="SRLicenseUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SRLicenseUtil {
	public static com.liferay.portlet.softwarerepository.model.SRLicense create(
		long licenseId) {
		return getPersistence().create(licenseId);
	}

	public static com.liferay.portlet.softwarerepository.model.SRLicense remove(
		long licenseId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(licenseId));
		}

		com.liferay.portlet.softwarerepository.model.SRLicense srLicense = getPersistence()
																			   .remove(licenseId);

		if (listener != null) {
			listener.onAfterRemove(srLicense);
		}

		return srLicense;
	}

	public static com.liferay.portlet.softwarerepository.model.SRLicense remove(
		com.liferay.portlet.softwarerepository.model.SRLicense srLicense)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(srLicense);
		}

		srLicense = getPersistence().remove(srLicense);

		if (listener != null) {
			listener.onAfterRemove(srLicense);
		}

		return srLicense;
	}

	public static com.liferay.portlet.softwarerepository.model.SRLicense update(
		com.liferay.portlet.softwarerepository.model.SRLicense srLicense)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = srLicense.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(srLicense);
			}
			else {
				listener.onBeforeUpdate(srLicense);
			}
		}

		srLicense = getPersistence().update(srLicense);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(srLicense);
			}
			else {
				listener.onAfterUpdate(srLicense);
			}
		}

		return srLicense;
	}

	public static com.liferay.portlet.softwarerepository.model.SRLicense update(
		com.liferay.portlet.softwarerepository.model.SRLicense srLicense,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = srLicense.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(srLicense);
			}
			else {
				listener.onBeforeUpdate(srLicense);
			}
		}

		srLicense = getPersistence().update(srLicense, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(srLicense);
			}
			else {
				listener.onAfterUpdate(srLicense);
			}
		}

		return srLicense;
	}

	public static com.liferay.portlet.softwarerepository.model.SRLicense findByPrimaryKey(
		long licenseId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException {
		return getPersistence().findByPrimaryKey(licenseId);
	}

	public static com.liferay.portlet.softwarerepository.model.SRLicense fetchByPrimaryKey(
		long licenseId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(licenseId);
	}

	public static java.util.List findByActive(boolean active)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByActive(active);
	}

	public static java.util.List findByActive(boolean active, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByActive(active, begin, end);
	}

	public static java.util.List findByActive(boolean active, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByActive(active, begin, end, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRLicense findByActive_First(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException {
		return getPersistence().findByActive_First(active, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRLicense findByActive_Last(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException {
		return getPersistence().findByActive_Last(active, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRLicense[] findByActive_PrevAndNext(
		long licenseId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException {
		return getPersistence().findByActive_PrevAndNext(licenseId, active, obc);
	}

	public static java.util.List findByA_R(boolean active, boolean recommended)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByA_R(active, recommended);
	}

	public static java.util.List findByA_R(boolean active, boolean recommended,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByA_R(active, recommended, begin, end);
	}

	public static java.util.List findByA_R(boolean active, boolean recommended,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByA_R(active, recommended, begin, end, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRLicense findByA_R_First(
		boolean active, boolean recommended,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException {
		return getPersistence().findByA_R_First(active, recommended, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRLicense findByA_R_Last(
		boolean active, boolean recommended,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException {
		return getPersistence().findByA_R_Last(active, recommended, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRLicense[] findByA_R_PrevAndNext(
		long licenseId, boolean active, boolean recommended,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException {
		return getPersistence().findByA_R_PrevAndNext(licenseId, active,
			recommended, obc);
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

	public static void removeByActive(boolean active)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByActive(active);
	}

	public static void removeByA_R(boolean active, boolean recommended)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByA_R(active, recommended);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByActive(boolean active)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByActive(active);
	}

	public static int countByA_R(boolean active, boolean recommended)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByA_R(active, recommended);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static SRLicensePersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(SRLicensePersistence persistence) {
		_persistence = persistence;
	}

	private static SRLicenseUtil _getUtil() {
		if (_util == null) {
			_util = (SRLicenseUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = SRLicenseUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.softwarerepository.model.SRLicense"));
	private static Log _log = LogFactory.getLog(SRLicenseUtil.class);
	private static SRLicenseUtil _util;
	private SRLicensePersistence _persistence;
}