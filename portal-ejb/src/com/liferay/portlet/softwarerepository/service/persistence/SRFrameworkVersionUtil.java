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
 * <a href="SRFrameworkVersionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SRFrameworkVersionUtil {
	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion create(
		long frameworkVersionId) {
		return getPersistence().create(frameworkVersionId);
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion remove(
		long frameworkVersionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(frameworkVersionId));
		}

		com.liferay.portlet.softwarerepository.model.SRFrameworkVersion srFrameworkVersion =
			getPersistence().remove(frameworkVersionId);

		if (listener != null) {
			listener.onAfterRemove(srFrameworkVersion);
		}

		return srFrameworkVersion;
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion remove(
		com.liferay.portlet.softwarerepository.model.SRFrameworkVersion srFrameworkVersion)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(srFrameworkVersion);
		}

		srFrameworkVersion = getPersistence().remove(srFrameworkVersion);

		if (listener != null) {
			listener.onAfterRemove(srFrameworkVersion);
		}

		return srFrameworkVersion;
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion update(
		com.liferay.portlet.softwarerepository.model.SRFrameworkVersion srFrameworkVersion)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = srFrameworkVersion.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(srFrameworkVersion);
			}
			else {
				listener.onBeforeUpdate(srFrameworkVersion);
			}
		}

		srFrameworkVersion = getPersistence().update(srFrameworkVersion);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(srFrameworkVersion);
			}
			else {
				listener.onAfterUpdate(srFrameworkVersion);
			}
		}

		return srFrameworkVersion;
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion update(
		com.liferay.portlet.softwarerepository.model.SRFrameworkVersion srFrameworkVersion,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = srFrameworkVersion.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(srFrameworkVersion);
			}
			else {
				listener.onBeforeUpdate(srFrameworkVersion);
			}
		}

		srFrameworkVersion = getPersistence().update(srFrameworkVersion,
				saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(srFrameworkVersion);
			}
			else {
				listener.onAfterUpdate(srFrameworkVersion);
			}
		}

		return srFrameworkVersion;
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion findByPrimaryKey(
		long frameworkVersionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		return getPersistence().findByPrimaryKey(frameworkVersionId);
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion fetchByPrimaryKey(
		long frameworkVersionId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(frameworkVersionId);
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

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion[] findByGroupId_PrevAndNext(
		long frameworkVersionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		return getPersistence().findByGroupId_PrevAndNext(frameworkVersionId,
			groupId, obc);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion[] findByCompanyId_PrevAndNext(
		long frameworkVersionId, java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		return getPersistence().findByCompanyId_PrevAndNext(frameworkVersionId,
			companyId, obc);
	}

	public static java.util.List findByG_A(long groupId, boolean active)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_A(groupId, active);
	}

	public static java.util.List findByG_A(long groupId, boolean active,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByG_A(groupId, active, begin, end);
	}

	public static java.util.List findByG_A(long groupId, boolean active,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_A(groupId, active, begin, end, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion findByG_A_First(
		long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		return getPersistence().findByG_A_First(groupId, active, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion findByG_A_Last(
		long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		return getPersistence().findByG_A_Last(groupId, active, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersion[] findByG_A_PrevAndNext(
		long frameworkVersionId, long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		return getPersistence().findByG_A_PrevAndNext(frameworkVersionId,
			groupId, active, obc);
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

	public static void removeByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByG_A(long groupId, boolean active)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_A(groupId, active);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByG_A(long groupId, boolean active)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_A(groupId, active);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static SRFrameworkVersionPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(SRFrameworkVersionPersistence persistence) {
		_persistence = persistence;
	}

	private static SRFrameworkVersionUtil _getUtil() {
		if (_util == null) {
			_util = (SRFrameworkVersionUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = SRFrameworkVersionUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.softwarerepository.model.SRFrameworkVersion"));
	private static Log _log = LogFactory.getLog(SRFrameworkVersionUtil.class);
	private static SRFrameworkVersionUtil _util;
	private SRFrameworkVersionPersistence _persistence;
}