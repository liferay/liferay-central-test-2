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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="MBTopicUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBTopicUtil {
	public static final String CLASS_NAME = MBTopicUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.messageboards.model.MBTopic"));

	public static com.liferay.portlet.messageboards.model.MBTopic create(
		java.lang.String topicId) {
		return getPersistence().create(topicId);
	}

	public static com.liferay.portlet.messageboards.model.MBTopic remove(
		java.lang.String topicId)
		throws com.liferay.portlet.messageboards.NoSuchTopicException, 
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
			listener.onBeforeRemove(findByPrimaryKey(topicId));
		}

		com.liferay.portlet.messageboards.model.MBTopic mbTopic = getPersistence()
																	  .remove(topicId);

		if (listener != null) {
			listener.onAfterRemove(mbTopic);
		}

		return mbTopic;
	}

	public static com.liferay.portlet.messageboards.model.MBTopic update(
		com.liferay.portlet.messageboards.model.MBTopic mbTopic)
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

		boolean isNew = mbTopic.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(mbTopic);
			}
			else {
				listener.onBeforeUpdate(mbTopic);
			}
		}

		mbTopic = getPersistence().update(mbTopic);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(mbTopic);
			}
			else {
				listener.onAfterUpdate(mbTopic);
			}
		}

		return mbTopic;
	}

	public static com.liferay.portlet.messageboards.model.MBTopic findByPrimaryKey(
		java.lang.String topicId)
		throws com.liferay.portlet.messageboards.NoSuchTopicException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(topicId);
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
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBTopic findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.messageboards.NoSuchTopicException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBTopic findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.messageboards.NoSuchTopicException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBTopic[] findByCompanyId_PrevAndNext(
		java.lang.String topicId, java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.messageboards.NoSuchTopicException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_PrevAndNext(topicId, companyId,
			obc);
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
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId(categoryId, begin, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBTopic findByCategoryId_First(
		java.lang.String categoryId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.messageboards.NoSuchTopicException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId_First(categoryId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBTopic findByCategoryId_Last(
		java.lang.String categoryId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.messageboards.NoSuchTopicException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId_Last(categoryId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBTopic[] findByCategoryId_PrevAndNext(
		java.lang.String topicId, java.lang.String categoryId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.messageboards.NoSuchTopicException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId_PrevAndNext(topicId,
			categoryId, obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByCategoryId(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCategoryId(categoryId);
	}

	public static int countByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByCategoryId(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCategoryId(categoryId);
	}

	public static MBTopicPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		MBTopicUtil util = (MBTopicUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(MBTopicPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(MBTopicUtil.class);
	private MBTopicPersistence _persistence;
}