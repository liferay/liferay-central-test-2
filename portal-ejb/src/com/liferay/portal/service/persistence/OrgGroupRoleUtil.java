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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="OrgGroupRoleUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class OrgGroupRoleUtil {
	public static com.liferay.portal.model.OrgGroupRole create(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK) {
		return getPersistence().create(orgGroupRolePK);
	}

	public static com.liferay.portal.model.OrgGroupRole remove(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrgGroupRoleException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(orgGroupRolePK));
		}

		com.liferay.portal.model.OrgGroupRole orgGroupRole = getPersistence()
																 .remove(orgGroupRolePK);

		if (listener != null) {
			listener.onAfterRemove(orgGroupRole);
		}

		return orgGroupRole;
	}

	public static com.liferay.portal.model.OrgGroupRole remove(
		com.liferay.portal.model.OrgGroupRole orgGroupRole)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(orgGroupRole);
		}

		orgGroupRole = getPersistence().remove(orgGroupRole);

		if (listener != null) {
			listener.onAfterRemove(orgGroupRole);
		}

		return orgGroupRole;
	}

	public static com.liferay.portal.model.OrgGroupRole update(
		com.liferay.portal.model.OrgGroupRole orgGroupRole)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = orgGroupRole.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(orgGroupRole);
			}
			else {
				listener.onBeforeUpdate(orgGroupRole);
			}
		}

		orgGroupRole = getPersistence().update(orgGroupRole);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(orgGroupRole);
			}
			else {
				listener.onAfterUpdate(orgGroupRole);
			}
		}

		return orgGroupRole;
	}

	public static com.liferay.portal.model.OrgGroupRole update(
		com.liferay.portal.model.OrgGroupRole orgGroupRole, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = orgGroupRole.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(orgGroupRole);
			}
			else {
				listener.onBeforeUpdate(orgGroupRole);
			}
		}

		orgGroupRole = getPersistence().update(orgGroupRole, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(orgGroupRole);
			}
			else {
				listener.onAfterUpdate(orgGroupRole);
			}
		}

		return orgGroupRole;
	}

	public static com.liferay.portal.model.OrgGroupRole findByPrimaryKey(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrgGroupRoleException {
		return getPersistence().findByPrimaryKey(orgGroupRolePK);
	}

	public static com.liferay.portal.model.OrgGroupRole fetchByPrimaryKey(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(orgGroupRolePK);
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

	public static com.liferay.portal.model.OrgGroupRole findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrgGroupRoleException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portal.model.OrgGroupRole findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrgGroupRoleException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portal.model.OrgGroupRole[] findByGroupId_PrevAndNext(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK,
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrgGroupRoleException {
		return getPersistence().findByGroupId_PrevAndNext(orgGroupRolePK,
			groupId, obc);
	}

	public static java.util.List findByRoleId(long roleId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByRoleId(roleId);
	}

	public static java.util.List findByRoleId(long roleId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByRoleId(roleId, begin, end);
	}

	public static java.util.List findByRoleId(long roleId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByRoleId(roleId, begin, end, obc);
	}

	public static com.liferay.portal.model.OrgGroupRole findByRoleId_First(
		long roleId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrgGroupRoleException {
		return getPersistence().findByRoleId_First(roleId, obc);
	}

	public static com.liferay.portal.model.OrgGroupRole findByRoleId_Last(
		long roleId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrgGroupRoleException {
		return getPersistence().findByRoleId_Last(roleId, obc);
	}

	public static com.liferay.portal.model.OrgGroupRole[] findByRoleId_PrevAndNext(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK,
		long roleId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrgGroupRoleException {
		return getPersistence().findByRoleId_PrevAndNext(orgGroupRolePK,
			roleId, obc);
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

	public static void removeByRoleId(long roleId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByRoleId(roleId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByRoleId(long roleId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByRoleId(roleId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static OrgGroupRolePersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(OrgGroupRolePersistence persistence) {
		_persistence = persistence;
	}

	private static OrgGroupRoleUtil _getUtil() {
		if (_util == null) {
			_util = (OrgGroupRoleUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = OrgGroupRoleUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.OrgGroupRole"));
	private static Log _log = LogFactory.getLog(OrgGroupRoleUtil.class);
	private static OrgGroupRoleUtil _util;
	private OrgGroupRolePersistence _persistence;
}