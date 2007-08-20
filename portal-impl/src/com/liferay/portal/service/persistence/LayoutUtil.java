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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portal.kernel.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LayoutUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LayoutUtil {
	public static com.liferay.portal.model.Layout create(long plid) {
		return getPersistence().create(plid);
	}

	public static com.liferay.portal.model.Layout remove(long plid)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(plid));
		}

		com.liferay.portal.model.Layout layout = getPersistence().remove(plid);

		if (listener != null) {
			listener.onAfterRemove(layout);
		}

		return layout;
	}

	public static com.liferay.portal.model.Layout remove(
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(layout);
		}

		layout = getPersistence().remove(layout);

		if (listener != null) {
			listener.onAfterRemove(layout);
		}

		return layout;
	}

	public static com.liferay.portal.model.Layout update(
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = layout.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(layout);
			}
			else {
				listener.onBeforeUpdate(layout);
			}
		}

		layout = getPersistence().update(layout);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(layout);
			}
			else {
				listener.onAfterUpdate(layout);
			}
		}

		return layout;
	}

	public static com.liferay.portal.model.Layout update(
		com.liferay.portal.model.Layout layout, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = layout.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(layout);
			}
			else {
				listener.onBeforeUpdate(layout);
			}
		}

		layout = getPersistence().update(layout, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(layout);
			}
			else {
				listener.onAfterUpdate(layout);
			}
		}

		return layout;
	}

	public static com.liferay.portal.model.Layout findByPrimaryKey(long plid)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException {
		return getPersistence().findByPrimaryKey(plid);
	}

	public static com.liferay.portal.model.Layout fetchByPrimaryKey(long plid)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(plid);
	}

	public static java.util.List findByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_P(groupId, privateLayout);
	}

	public static java.util.List findByG_P(long groupId, boolean privateLayout,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByG_P(groupId, privateLayout, begin, end);
	}

	public static java.util.List findByG_P(long groupId, boolean privateLayout,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_P(groupId, privateLayout, begin, end,
			obc);
	}

	public static com.liferay.portal.model.Layout findByG_P_First(
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException {
		return getPersistence().findByG_P_First(groupId, privateLayout, obc);
	}

	public static com.liferay.portal.model.Layout findByG_P_Last(long groupId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException {
		return getPersistence().findByG_P_Last(groupId, privateLayout, obc);
	}

	public static com.liferay.portal.model.Layout[] findByG_P_PrevAndNext(
		long plid, long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException {
		return getPersistence().findByG_P_PrevAndNext(plid, groupId,
			privateLayout, obc);
	}

	public static com.liferay.portal.model.Layout findByG_P_L(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException {
		return getPersistence().findByG_P_L(groupId, privateLayout, layoutId);
	}

	public static com.liferay.portal.model.Layout fetchByG_P_L(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByG_P_L(groupId, privateLayout, layoutId);
	}

	public static java.util.List findByG_P_P(long groupId,
		boolean privateLayout, long parentLayoutId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_P_P(groupId, privateLayout,
			parentLayoutId);
	}

	public static java.util.List findByG_P_P(long groupId,
		boolean privateLayout, long parentLayoutId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_P_P(groupId, privateLayout,
			parentLayoutId, begin, end);
	}

	public static java.util.List findByG_P_P(long groupId,
		boolean privateLayout, long parentLayoutId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_P_P(groupId, privateLayout,
			parentLayoutId, begin, end, obc);
	}

	public static com.liferay.portal.model.Layout findByG_P_P_First(
		long groupId, boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException {
		return getPersistence().findByG_P_P_First(groupId, privateLayout,
			parentLayoutId, obc);
	}

	public static com.liferay.portal.model.Layout findByG_P_P_Last(
		long groupId, boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException {
		return getPersistence().findByG_P_P_Last(groupId, privateLayout,
			parentLayoutId, obc);
	}

	public static com.liferay.portal.model.Layout[] findByG_P_P_PrevAndNext(
		long plid, long groupId, boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException {
		return getPersistence().findByG_P_P_PrevAndNext(plid, groupId,
			privateLayout, parentLayoutId, obc);
	}

	public static com.liferay.portal.model.Layout findByG_P_F(long groupId,
		boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException {
		return getPersistence().findByG_P_F(groupId, privateLayout, friendlyURL);
	}

	public static com.liferay.portal.model.Layout fetchByG_P_F(long groupId,
		boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByG_P_F(groupId, privateLayout, friendlyURL);
	}

	public static com.liferay.portal.model.Layout findByG_P_DLF(long groupId,
		boolean privateLayout, long dlFolderId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException {
		return getPersistence().findByG_P_DLF(groupId, privateLayout, dlFolderId);
	}

	public static com.liferay.portal.model.Layout fetchByG_P_DLF(long groupId,
		boolean privateLayout, long dlFolderId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByG_P_DLF(groupId, privateLayout,
			dlFolderId);
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

	public static void removeByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_P(groupId, privateLayout);
	}

	public static void removeByG_P_L(long groupId, boolean privateLayout,
		long layoutId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException {
		getPersistence().removeByG_P_L(groupId, privateLayout, layoutId);
	}

	public static void removeByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) throws com.liferay.portal.SystemException {
		getPersistence().removeByG_P_P(groupId, privateLayout, parentLayoutId);
	}

	public static void removeByG_P_F(long groupId, boolean privateLayout,
		java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException {
		getPersistence().removeByG_P_F(groupId, privateLayout, friendlyURL);
	}

	public static void removeByG_P_DLF(long groupId, boolean privateLayout,
		long dlFolderId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException {
		getPersistence().removeByG_P_DLF(groupId, privateLayout, dlFolderId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_P(groupId, privateLayout);
	}

	public static int countByG_P_L(long groupId, boolean privateLayout,
		long layoutId) throws com.liferay.portal.SystemException {
		return getPersistence().countByG_P_L(groupId, privateLayout, layoutId);
	}

	public static int countByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) throws com.liferay.portal.SystemException {
		return getPersistence().countByG_P_P(groupId, privateLayout,
			parentLayoutId);
	}

	public static int countByG_P_F(long groupId, boolean privateLayout,
		java.lang.String friendlyURL) throws com.liferay.portal.SystemException {
		return getPersistence().countByG_P_F(groupId, privateLayout, friendlyURL);
	}

	public static int countByG_P_DLF(long groupId, boolean privateLayout,
		long dlFolderId) throws com.liferay.portal.SystemException {
		return getPersistence().countByG_P_DLF(groupId, privateLayout,
			dlFolderId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static LayoutPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(LayoutPersistence persistence) {
		_persistence = persistence;
	}

	private static LayoutUtil _getUtil() {
		if (_util == null) {
			_util = (LayoutUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = LayoutUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.Layout"));
	private static Log _log = LogFactory.getLog(LayoutUtil.class);
	private static LayoutUtil _util;
	private LayoutPersistence _persistence;
}