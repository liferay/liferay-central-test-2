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
 * <a href="MBMessageUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBMessageUtil {
	public static com.liferay.portlet.messageboards.model.MBMessage create(
		com.liferay.portlet.messageboards.service.persistence.MBMessagePK mbMessagePK) {
		return getPersistence().create(mbMessagePK);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage remove(
		com.liferay.portlet.messageboards.service.persistence.MBMessagePK mbMessagePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchMessageException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(mbMessagePK));
		}

		com.liferay.portlet.messageboards.model.MBMessage mbMessage = getPersistence()
																		  .remove(mbMessagePK);

		if (listener != null) {
			listener.onAfterRemove(mbMessage);
		}

		return mbMessage;
	}

	public static com.liferay.portlet.messageboards.model.MBMessage remove(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(mbMessage);
		}

		mbMessage = getPersistence().remove(mbMessage);

		if (listener != null) {
			listener.onAfterRemove(mbMessage);
		}

		return mbMessage;
	}

	public static com.liferay.portlet.messageboards.model.MBMessage update(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = mbMessage.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(mbMessage);
			}
			else {
				listener.onBeforeUpdate(mbMessage);
			}
		}

		mbMessage = getPersistence().update(mbMessage);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(mbMessage);
			}
			else {
				listener.onAfterUpdate(mbMessage);
			}
		}

		return mbMessage;
	}

	public static com.liferay.portlet.messageboards.model.MBMessage update(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = mbMessage.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(mbMessage);
			}
			else {
				listener.onBeforeUpdate(mbMessage);
			}
		}

		mbMessage = getPersistence().update(mbMessage, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(mbMessage);
			}
			else {
				listener.onAfterUpdate(mbMessage);
			}
		}

		return mbMessage;
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByPrimaryKey(
		com.liferay.portlet.messageboards.service.persistence.MBMessagePK mbMessagePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByPrimaryKey(mbMessagePK);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage fetchByPrimaryKey(
		com.liferay.portlet.messageboards.service.persistence.MBMessagePK mbMessagePK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(mbMessagePK);
	}

	public static java.util.List findByCategoryId(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId(categoryId);
	}

	public static java.util.List findByCategoryId(java.lang.String categoryId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId(categoryId, begin, end);
	}

	public static java.util.List findByCategoryId(java.lang.String categoryId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId(categoryId, begin, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByCategoryId_First(
		java.lang.String categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByCategoryId_First(categoryId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByCategoryId_Last(
		java.lang.String categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByCategoryId_Last(categoryId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByCategoryId_PrevAndNext(
		com.liferay.portlet.messageboards.service.persistence.MBMessagePK mbMessagePK,
		java.lang.String categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByCategoryId_PrevAndNext(mbMessagePK,
			categoryId, obc);
	}

	public static java.util.List findByThreadId(java.lang.String threadId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByThreadId(threadId);
	}

	public static java.util.List findByThreadId(java.lang.String threadId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByThreadId(threadId, begin, end);
	}

	public static java.util.List findByThreadId(java.lang.String threadId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByThreadId(threadId, begin, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByThreadId_First(
		java.lang.String threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByThreadId_First(threadId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByThreadId_Last(
		java.lang.String threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByThreadId_Last(threadId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByThreadId_PrevAndNext(
		com.liferay.portlet.messageboards.service.persistence.MBMessagePK mbMessagePK,
		java.lang.String threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByThreadId_PrevAndNext(mbMessagePK,
			threadId, obc);
	}

	public static java.util.List findByC_T(java.lang.String categoryId,
		java.lang.String threadId) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_T(categoryId, threadId);
	}

	public static java.util.List findByC_T(java.lang.String categoryId,
		java.lang.String threadId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_T(categoryId, threadId, begin, end);
	}

	public static java.util.List findByC_T(java.lang.String categoryId,
		java.lang.String threadId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_T(categoryId, threadId, begin, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByC_T_First(
		java.lang.String categoryId, java.lang.String threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByC_T_First(categoryId, threadId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByC_T_Last(
		java.lang.String categoryId, java.lang.String threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByC_T_Last(categoryId, threadId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByC_T_PrevAndNext(
		com.liferay.portlet.messageboards.service.persistence.MBMessagePK mbMessagePK,
		java.lang.String categoryId, java.lang.String threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByC_T_PrevAndNext(mbMessagePK, categoryId,
			threadId, obc);
	}

	public static java.util.List findByT_P(java.lang.String threadId,
		java.lang.String parentMessageId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByT_P(threadId, parentMessageId);
	}

	public static java.util.List findByT_P(java.lang.String threadId,
		java.lang.String parentMessageId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByT_P(threadId, parentMessageId, begin, end);
	}

	public static java.util.List findByT_P(java.lang.String threadId,
		java.lang.String parentMessageId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByT_P(threadId, parentMessageId, begin,
			end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByT_P_First(
		java.lang.String threadId, java.lang.String parentMessageId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByT_P_First(threadId, parentMessageId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByT_P_Last(
		java.lang.String threadId, java.lang.String parentMessageId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByT_P_Last(threadId, parentMessageId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByT_P_PrevAndNext(
		com.liferay.portlet.messageboards.service.persistence.MBMessagePK mbMessagePK,
		java.lang.String threadId, java.lang.String parentMessageId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByT_P_PrevAndNext(mbMessagePK, threadId,
			parentMessageId, obc);
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

	public static void removeByCategoryId(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCategoryId(categoryId);
	}

	public static void removeByThreadId(java.lang.String threadId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByThreadId(threadId);
	}

	public static void removeByC_T(java.lang.String categoryId,
		java.lang.String threadId) throws com.liferay.portal.SystemException {
		getPersistence().removeByC_T(categoryId, threadId);
	}

	public static void removeByT_P(java.lang.String threadId,
		java.lang.String parentMessageId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByT_P(threadId, parentMessageId);
	}

	public static int countByCategoryId(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCategoryId(categoryId);
	}

	public static int countByThreadId(java.lang.String threadId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByThreadId(threadId);
	}

	public static int countByC_T(java.lang.String categoryId,
		java.lang.String threadId) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_T(categoryId, threadId);
	}

	public static int countByT_P(java.lang.String threadId,
		java.lang.String parentMessageId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByT_P(threadId, parentMessageId);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static MBMessagePersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(MBMessagePersistence persistence) {
		_persistence = persistence;
	}

	private static MBMessageUtil _getUtil() {
		if (_util == null) {
			_util = (MBMessageUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = MBMessageUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.messageboards.model.MBMessage"));
	private static Log _log = LogFactory.getLog(MBMessageUtil.class);
	private static MBMessageUtil _util;
	private MBMessagePersistence _persistence;
}