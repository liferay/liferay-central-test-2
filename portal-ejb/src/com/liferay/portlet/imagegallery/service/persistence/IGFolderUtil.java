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

package com.liferay.portlet.imagegallery.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="IGFolderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class IGFolderUtil {
	public static final String CLASS_NAME = IGFolderUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.imagegallery.model.IGFolder"));

	public static com.liferay.portlet.imagegallery.model.IGFolder create(
		java.lang.String folderId) {
		return getPersistence().create(folderId);
	}

	public static com.liferay.portlet.imagegallery.model.IGFolder remove(
		java.lang.String folderId)
		throws com.liferay.portlet.imagegallery.NoSuchFolderException, 
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
			listener.onBeforeRemove(findByPrimaryKey(folderId));
		}

		com.liferay.portlet.imagegallery.model.IGFolder igFolder = getPersistence()
																	   .remove(folderId);

		if (listener != null) {
			listener.onAfterRemove(igFolder);
		}

		return igFolder;
	}

	public static com.liferay.portlet.imagegallery.model.IGFolder update(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder)
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

		boolean isNew = igFolder.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(igFolder);
			}
			else {
				listener.onBeforeUpdate(igFolder);
			}
		}

		igFolder = getPersistence().update(igFolder);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(igFolder);
			}
			else {
				listener.onAfterUpdate(igFolder);
			}
		}

		return igFolder;
	}

	public static com.liferay.portlet.imagegallery.model.IGFolder findByPrimaryKey(
		java.lang.String folderId)
		throws com.liferay.portlet.imagegallery.NoSuchFolderException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(folderId);
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

	public static com.liferay.portlet.imagegallery.model.IGFolder findByGroupId_First(
		java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.imagegallery.NoSuchFolderException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGFolder findByGroupId_Last(
		java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.imagegallery.NoSuchFolderException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGFolder[] findByGroupId_PrevAndNext(
		java.lang.String folderId, java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.imagegallery.NoSuchFolderException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_PrevAndNext(folderId, groupId, obc);
	}

	public static java.util.List findByG_P(java.lang.String groupId,
		java.lang.String parentFolderId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_P(groupId, parentFolderId);
	}

	public static java.util.List findByG_P(java.lang.String groupId,
		java.lang.String parentFolderId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_P(groupId, parentFolderId, begin, end);
	}

	public static java.util.List findByG_P(java.lang.String groupId,
		java.lang.String parentFolderId, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_P(groupId, parentFolderId, begin, end,
			obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGFolder findByG_P_First(
		java.lang.String groupId, java.lang.String parentFolderId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.imagegallery.NoSuchFolderException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByG_P_First(groupId, parentFolderId, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGFolder findByG_P_Last(
		java.lang.String groupId, java.lang.String parentFolderId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.imagegallery.NoSuchFolderException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByG_P_Last(groupId, parentFolderId, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGFolder[] findByG_P_PrevAndNext(
		java.lang.String folderId, java.lang.String groupId,
		java.lang.String parentFolderId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.imagegallery.NoSuchFolderException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByG_P_PrevAndNext(folderId, groupId,
			parentFolderId, obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByG_P(java.lang.String groupId,
		java.lang.String parentFolderId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_P(groupId, parentFolderId);
	}

	public static int countByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByG_P(java.lang.String groupId,
		java.lang.String parentFolderId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_P(groupId, parentFolderId);
	}

	public static IGFolderPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		IGFolderUtil util = (IGFolderUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(IGFolderPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(IGFolderUtil.class);
	private IGFolderPersistence _persistence;
}