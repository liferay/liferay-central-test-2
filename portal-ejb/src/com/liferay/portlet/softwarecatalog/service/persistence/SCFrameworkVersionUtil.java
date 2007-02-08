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

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="SCFrameworkVersionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SCFrameworkVersionUtil {
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion create(
		long frameworkVersionId) {
		return getPersistence().create(frameworkVersionId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion remove(
		long frameworkVersionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(frameworkVersionId));
		}

		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion =
			getPersistence().remove(frameworkVersionId);

		if (listener != null) {
			listener.onAfterRemove(scFrameworkVersion);
		}

		return scFrameworkVersion;
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion remove(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(scFrameworkVersion);
		}

		scFrameworkVersion = getPersistence().remove(scFrameworkVersion);

		if (listener != null) {
			listener.onAfterRemove(scFrameworkVersion);
		}

		return scFrameworkVersion;
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion update(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = scFrameworkVersion.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(scFrameworkVersion);
			}
			else {
				listener.onBeforeUpdate(scFrameworkVersion);
			}
		}

		scFrameworkVersion = getPersistence().update(scFrameworkVersion);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(scFrameworkVersion);
			}
			else {
				listener.onAfterUpdate(scFrameworkVersion);
			}
		}

		return scFrameworkVersion;
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion update(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = scFrameworkVersion.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(scFrameworkVersion);
			}
			else {
				listener.onBeforeUpdate(scFrameworkVersion);
			}
		}

		scFrameworkVersion = getPersistence().update(scFrameworkVersion,
				saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(scFrameworkVersion);
			}
			else {
				listener.onAfterUpdate(scFrameworkVersion);
			}
		}

		return scFrameworkVersion;
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByPrimaryKey(
		long frameworkVersionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence().findByPrimaryKey(frameworkVersionId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion fetchByPrimaryKey(
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

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion[] findByGroupId_PrevAndNext(
		long frameworkVersionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
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

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion[] findByCompanyId_PrevAndNext(
		long frameworkVersionId, java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
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

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByG_A_First(
		long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence().findByG_A_First(groupId, active, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByG_A_Last(
		long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence().findByG_A_Last(groupId, active, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion[] findByG_A_PrevAndNext(
		long frameworkVersionId, long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
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

	public static SCFrameworkVersionPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(SCFrameworkVersionPersistence persistence) {
		_persistence = persistence;
	}

	private static SCFrameworkVersionUtil _getUtil() {
		if (_util == null) {
			_util = (SCFrameworkVersionUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = SCFrameworkVersionUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion"));
	private static Log _log = LogFactory.getLog(SCFrameworkVersionUtil.class);
	private static SCFrameworkVersionUtil _util;
	private SCFrameworkVersionPersistence _persistence;
}