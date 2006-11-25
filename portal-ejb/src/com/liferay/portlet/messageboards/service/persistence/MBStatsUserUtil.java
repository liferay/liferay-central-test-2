/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MBStatsUserUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBStatsUserUtil {
	public static com.liferay.portlet.messageboards.model.MBStatsUser create(
		com.liferay.portlet.messageboards.service.persistence.MBStatsUserPK mbStatsUserPK) {
		return getPersistence().create(mbStatsUserPK);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser remove(
		com.liferay.portlet.messageboards.service.persistence.MBStatsUserPK mbStatsUserPK)
		throws com.liferay.portlet.messageboards.NoSuchStatsUserException, 
			com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(mbStatsUserPK));
		}

		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser = getPersistence()
																			  .remove(mbStatsUserPK);

		if (listener != null) {
			listener.onAfterRemove(mbStatsUser);
		}

		return mbStatsUser;
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser remove(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(mbStatsUser);
		}

		mbStatsUser = getPersistence().remove(mbStatsUser);

		if (listener != null) {
			listener.onAfterRemove(mbStatsUser);
		}

		return mbStatsUser;
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser update(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = mbStatsUser.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(mbStatsUser);
			}
			else {
				listener.onBeforeUpdate(mbStatsUser);
			}
		}

		mbStatsUser = getPersistence().update(mbStatsUser);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(mbStatsUser);
			}
			else {
				listener.onAfterUpdate(mbStatsUser);
			}
		}

		return mbStatsUser;
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser update(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = mbStatsUser.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(mbStatsUser);
			}
			else {
				listener.onBeforeUpdate(mbStatsUser);
			}
		}

		mbStatsUser = getPersistence().update(mbStatsUser, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(mbStatsUser);
			}
			else {
				listener.onAfterUpdate(mbStatsUser);
			}
		}

		return mbStatsUser;
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser findByPrimaryKey(
		com.liferay.portlet.messageboards.service.persistence.MBStatsUserPK mbStatsUserPK)
		throws com.liferay.portlet.messageboards.NoSuchStatsUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(mbStatsUserPK);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser fetchByPrimaryKey(
		com.liferay.portlet.messageboards.service.persistence.MBStatsUserPK mbStatsUserPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(mbStatsUserPK);
	}

	public static java.util.List findByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List findByGroupId(java.lang.String groupId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end);
	}

	public static java.util.List findByGroupId(java.lang.String groupId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser findByGroupId_First(
		java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.messageboards.NoSuchStatsUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser findByGroupId_Last(
		java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.messageboards.NoSuchStatsUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser[] findByGroupId_PrevAndNext(
		com.liferay.portlet.messageboards.service.persistence.MBStatsUserPK mbStatsUserPK,
		java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.messageboards.NoSuchStatsUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_PrevAndNext(mbStatsUserPK,
			groupId, obc);
	}

	public static java.util.List findByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List findByUserId(java.lang.String userId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end);
	}

	public static java.util.List findByUserId(java.lang.String userId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser findByUserId_First(
		java.lang.String userId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.messageboards.NoSuchStatsUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser findByUserId_Last(
		java.lang.String userId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.messageboards.NoSuchStatsUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser[] findByUserId_PrevAndNext(
		com.liferay.portlet.messageboards.service.persistence.MBStatsUserPK mbStatsUserPK,
		java.lang.String userId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.messageboards.NoSuchStatsUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_PrevAndNext(mbStatsUserPK, userId,
			obc);
	}

	public static java.util.List findByG_M(java.lang.String groupId,
		int messageCount) throws com.liferay.portal.SystemException {
		return getPersistence().findByG_M(groupId, messageCount);
	}

	public static java.util.List findByG_M(java.lang.String groupId,
		int messageCount, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_M(groupId, messageCount, begin, end);
	}

	public static java.util.List findByG_M(java.lang.String groupId,
		int messageCount, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_M(groupId, messageCount, begin, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser findByG_M_First(
		java.lang.String groupId, int messageCount,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.messageboards.NoSuchStatsUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByG_M_First(groupId, messageCount, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser findByG_M_Last(
		java.lang.String groupId, int messageCount,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.messageboards.NoSuchStatsUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByG_M_Last(groupId, messageCount, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser[] findByG_M_PrevAndNext(
		com.liferay.portlet.messageboards.service.persistence.MBStatsUserPK mbStatsUserPK,
		java.lang.String groupId, int messageCount,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.messageboards.NoSuchStatsUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByG_M_PrevAndNext(mbStatsUserPK, groupId,
			messageCount, obc);
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
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByG_M(java.lang.String groupId, int messageCount)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_M(groupId, messageCount);
	}

	public static int countByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByG_M(java.lang.String groupId, int messageCount)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_M(groupId, messageCount);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static MBStatsUserPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(MBStatsUserPersistence persistence) {
		_persistence = persistence;
	}

	private static MBStatsUserUtil _getUtil() {
		if (_util == null) {
			_util = (MBStatsUserUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = MBStatsUserUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.messageboards.model.MBStatsUser"));
	private static Log _log = LogFactory.getLog(MBStatsUserUtil.class);
	private static MBStatsUserUtil _util;
	private MBStatsUserPersistence _persistence;
}