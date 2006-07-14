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

package com.liferay.portlet.mail.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="MailReceiptUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MailReceiptUtil {
	public static final String CLASS_NAME = MailReceiptUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.mail.model.MailReceipt"));

	public static com.liferay.portlet.mail.model.MailReceipt create(
		java.lang.String receiptId) {
		return getPersistence().create(receiptId);
	}

	public static com.liferay.portlet.mail.model.MailReceipt remove(
		java.lang.String receiptId)
		throws com.liferay.portlet.mail.NoSuchReceiptException, 
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
			listener.onBeforeRemove(findByPrimaryKey(receiptId));
		}

		com.liferay.portlet.mail.model.MailReceipt mailReceipt = getPersistence()
																	 .remove(receiptId);

		if (listener != null) {
			listener.onAfterRemove(mailReceipt);
		}

		return mailReceipt;
	}

	public static com.liferay.portlet.mail.model.MailReceipt update(
		com.liferay.portlet.mail.model.MailReceipt mailReceipt)
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

		boolean isNew = mailReceipt.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(mailReceipt);
			}
			else {
				listener.onBeforeUpdate(mailReceipt);
			}
		}

		mailReceipt = getPersistence().update(mailReceipt);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(mailReceipt);
			}
			else {
				listener.onAfterUpdate(mailReceipt);
			}
		}

		return mailReceipt;
	}

	public static com.liferay.portlet.mail.model.MailReceipt findByPrimaryKey(
		java.lang.String receiptId)
		throws com.liferay.portlet.mail.NoSuchReceiptException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(receiptId);
	}

	public static com.liferay.portlet.mail.model.MailReceipt findByPrimaryKey(
		java.lang.String receiptId, boolean throwNoSuchObjectException)
		throws com.liferay.portlet.mail.NoSuchReceiptException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(receiptId,
			throwNoSuchObjectException);
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

	public static com.liferay.portlet.mail.model.MailReceipt findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.mail.NoSuchReceiptException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.mail.model.MailReceipt findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.mail.NoSuchReceiptException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.mail.model.MailReceipt[] findByCompanyId_PrevAndNext(
		java.lang.String receiptId, java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.mail.NoSuchReceiptException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_PrevAndNext(receiptId,
			companyId, obc);
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

	public static com.liferay.portlet.mail.model.MailReceipt findByUserId_First(
		java.lang.String userId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.mail.NoSuchReceiptException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portlet.mail.model.MailReceipt findByUserId_Last(
		java.lang.String userId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.mail.NoSuchReceiptException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portlet.mail.model.MailReceipt[] findByUserId_PrevAndNext(
		java.lang.String receiptId, java.lang.String userId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.mail.NoSuchReceiptException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_PrevAndNext(receiptId, userId, obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static int countByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static MailReceiptPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		MailReceiptUtil util = (MailReceiptUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(MailReceiptPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(MailReceiptUtil.class);
	private MailReceiptPersistence _persistence;
}