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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MBBanUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBBanUtil {
	public static com.liferay.portlet.messageboards.model.MBBan create(
		java.lang.String banId) {
		return getPersistence().create(banId);
	}

	public static com.liferay.portlet.messageboards.model.MBBan remove(
		java.lang.String banId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchBanException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(banId));
		}

		com.liferay.portlet.messageboards.model.MBBan mbBan = getPersistence()
																  .remove(banId);

		if (listener != null) {
			listener.onAfterRemove(mbBan);
		}

		return mbBan;
	}

	public static com.liferay.portlet.messageboards.model.MBBan remove(
		com.liferay.portlet.messageboards.model.MBBan mbBan)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(mbBan);
		}

		mbBan = getPersistence().remove(mbBan);

		if (listener != null) {
			listener.onAfterRemove(mbBan);
		}

		return mbBan;
	}

	public static com.liferay.portlet.messageboards.model.MBBan update(
		com.liferay.portlet.messageboards.model.MBBan mbBan)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = mbBan.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(mbBan);
			}
			else {
				listener.onBeforeUpdate(mbBan);
			}
		}

		mbBan = getPersistence().update(mbBan);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(mbBan);
			}
			else {
				listener.onAfterUpdate(mbBan);
			}
		}

		return mbBan;
	}

	public static com.liferay.portlet.messageboards.model.MBBan update(
		com.liferay.portlet.messageboards.model.MBBan mbBan,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = mbBan.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(mbBan);
			}
			else {
				listener.onBeforeUpdate(mbBan);
			}
		}

		mbBan = getPersistence().update(mbBan, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(mbBan);
			}
			else {
				listener.onAfterUpdate(mbBan);
			}
		}

		return mbBan;
	}

	public static com.liferay.portlet.messageboards.model.MBBan findByPrimaryKey(
		java.lang.String banId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByPrimaryKey(banId);
	}

	public static com.liferay.portlet.messageboards.model.MBBan fetchByPrimaryKey(
		java.lang.String banId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(banId);
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

	public static com.liferay.portlet.messageboards.model.MBBan findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBBan findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBBan[] findByGroupId_PrevAndNext(
		java.lang.String banId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByGroupId_PrevAndNext(banId, groupId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBBan findByG_B(
		long groupId, java.lang.String banUserId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByG_B(groupId, banUserId);
	}

	public static com.liferay.portlet.messageboards.model.MBBan fetchByG_B(
		long groupId, java.lang.String banUserId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByG_B(groupId, banUserId);
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

	public static void removeByG_B(long groupId, java.lang.String banUserId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchBanException {
		getPersistence().removeByG_B(groupId, banUserId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByG_B(long groupId, java.lang.String banUserId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_B(groupId, banUserId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static MBBanPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(MBBanPersistence persistence) {
		_persistence = persistence;
	}

	private static MBBanUtil _getUtil() {
		if (_util == null) {
			_util = (MBBanUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = MBBanUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.messageboards.model.MBBan"));
	private static Log _log = LogFactory.getLog(MBBanUtil.class);
	private static MBBanUtil _util;
	private MBBanPersistence _persistence;
}