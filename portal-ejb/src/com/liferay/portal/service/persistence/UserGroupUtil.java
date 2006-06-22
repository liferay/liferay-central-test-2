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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="UserGroupUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserGroupUtil {
	public static final String CLASS_NAME = UserGroupUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.UserGroup"));

	public static com.liferay.portal.model.UserGroup create(
		java.lang.String userGroupId) {
		return getPersistence().create(userGroupId);
	}

	public static com.liferay.portal.model.UserGroup remove(
		java.lang.String userGroupId)
		throws com.liferay.portal.NoSuchUserGroupException, 
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
			listener.onBeforeRemove(findByPrimaryKey(userGroupId));
		}

		com.liferay.portal.model.UserGroup userGroup = getPersistence().remove(userGroupId);

		if (listener != null) {
			listener.onAfterRemove(userGroup);
		}

		return userGroup;
	}

	public static com.liferay.portal.model.UserGroup update(
		com.liferay.portal.model.UserGroup userGroup)
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

		boolean isNew = userGroup.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(userGroup);
			}
			else {
				listener.onBeforeUpdate(userGroup);
			}
		}

		userGroup = getPersistence().update(userGroup);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(userGroup);
			}
			else {
				listener.onAfterUpdate(userGroup);
			}
		}

		return userGroup;
	}

	public static java.util.List getUsers(java.lang.String pk)
		throws com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk);
	}

	public static java.util.List getUsers(java.lang.String pk, int begin,
		int end)
		throws com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk, begin, end);
	}

	public static java.util.List getUsers(java.lang.String pk, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk, begin, end, obc);
	}

	public static int getUsersSize(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getUsersSize(pk);
	}

	public static void setUsers(java.lang.String pk, java.lang.String[] pks)
		throws com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		getPersistence().setUsers(pk, pks);
	}

	public static void setUsers(java.lang.String pk, java.util.List users)
		throws com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		getPersistence().setUsers(pk, users);
	}

	public static boolean addUser(java.lang.String pk, java.lang.String userPK)
		throws com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().addUser(pk, userPK);
	}

	public static boolean addUser(java.lang.String pk,
		com.liferay.portal.model.User user)
		throws com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().addUser(pk, user);
	}

	public static boolean addUsers(java.lang.String pk,
		java.lang.String[] userPKs)
		throws com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().addUsers(pk, userPKs);
	}

	public static boolean addUsers(java.lang.String pk, java.util.List users)
		throws com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().addUsers(pk, users);
	}

	public static void clearUsers(java.lang.String pk)
		throws com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().clearUsers(pk);
	}

	public static boolean containsUser(java.lang.String pk,
		java.lang.String userPK) throws com.liferay.portal.SystemException {
		return getPersistence().containsUser(pk, userPK);
	}

	public static boolean containsUsers(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsUsers(pk);
	}

	public static boolean removeUser(java.lang.String pk,
		java.lang.String userPK)
		throws com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeUser(pk, userPK);
	}

	public static boolean removeUser(java.lang.String pk,
		com.liferay.portal.model.User user)
		throws com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeUser(pk, user);
	}

	public static boolean removeUsers(java.lang.String pk,
		java.lang.String[] userPKs)
		throws com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeUsers(pk, userPKs);
	}

	public static boolean removeUsers(java.lang.String pk, java.util.List users)
		throws com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeUsers(pk, users);
	}

	public static com.liferay.portal.model.UserGroup findByPrimaryKey(
		java.lang.String userGroupId)
		throws com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(userGroupId);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static UserGroupPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		UserGroupUtil util = (UserGroupUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(UserGroupPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(UserGroupUtil.class);
	private UserGroupPersistence _persistence;
}