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
 * <a href="MBDiscussionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBDiscussionUtil {
	public static com.liferay.portlet.messageboards.model.MBDiscussion create(
		long discussionId) {
		return getPersistence().create(discussionId);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion remove(
		long discussionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchDiscussionException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(discussionId));
		}

		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion = getPersistence()
																				.remove(discussionId);

		if (listener != null) {
			listener.onAfterRemove(mbDiscussion);
		}

		return mbDiscussion;
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion remove(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(mbDiscussion);
		}

		mbDiscussion = getPersistence().remove(mbDiscussion);

		if (listener != null) {
			listener.onAfterRemove(mbDiscussion);
		}

		return mbDiscussion;
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion update(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = mbDiscussion.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(mbDiscussion);
			}
			else {
				listener.onBeforeUpdate(mbDiscussion);
			}
		}

		mbDiscussion = getPersistence().update(mbDiscussion);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(mbDiscussion);
			}
			else {
				listener.onAfterUpdate(mbDiscussion);
			}
		}

		return mbDiscussion;
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion update(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = mbDiscussion.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(mbDiscussion);
			}
			else {
				listener.onBeforeUpdate(mbDiscussion);
			}
		}

		mbDiscussion = getPersistence().update(mbDiscussion, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(mbDiscussion);
			}
			else {
				listener.onAfterUpdate(mbDiscussion);
			}
		}

		return mbDiscussion;
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion findByPrimaryKey(
		long discussionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchDiscussionException {
		return getPersistence().findByPrimaryKey(discussionId);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion fetchByPrimaryKey(
		long discussionId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(discussionId);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion findByC_C(
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchDiscussionException {
		return getPersistence().findByC_C(className, classPK);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion fetchByC_C(
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_C(className, classPK);
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

	public static void removeByC_C(java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchDiscussionException {
		getPersistence().removeByC_C(className, classPK);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByC_C(java.lang.String className,
		java.lang.String classPK) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(className, classPK);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static MBDiscussionPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(MBDiscussionPersistence persistence) {
		_persistence = persistence;
	}

	private static MBDiscussionUtil _getUtil() {
		if (_util == null) {
			_util = (MBDiscussionUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = MBDiscussionUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.messageboards.model.MBDiscussion"));
	private static Log _log = LogFactory.getLog(MBDiscussionUtil.class);
	private static MBDiscussionUtil _util;
	private MBDiscussionPersistence _persistence;
}