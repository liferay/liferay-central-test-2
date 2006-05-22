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

package com.liferay.portlet.mail.model;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="MailReceiptModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MailReceiptModel extends BaseModel {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.mail.model.MailReceipt"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_RECEIPTID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.mail.model.MailReceipt.receiptId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.mail.model.MailReceipt.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.mail.model.MailReceipt.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_RECIPIENTNAME = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.mail.model.MailReceipt.recipientName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_RECIPIENTADDRESS = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.mail.model.MailReceipt.recipientAddress"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_SUBJECT = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.mail.model.MailReceipt.subject"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.mail.model.MailReceiptModel"));

	public MailReceiptModel() {
	}

	public String getPrimaryKey() {
		return _receiptId;
	}

	public void setPrimaryKey(String pk) {
		setReceiptId(pk);
	}

	public String getReceiptId() {
		return GetterUtil.getString(_receiptId);
	}

	public void setReceiptId(String receiptId) {
		if (((receiptId == null) && (_receiptId != null)) ||
				((receiptId != null) && (_receiptId == null)) ||
				((receiptId != null) && (_receiptId != null) &&
				!receiptId.equals(_receiptId))) {
			if (!XSS_ALLOW_RECEIPTID) {
				receiptId = XSSUtil.strip(receiptId);
			}

			_receiptId = receiptId;
			setModified(true);
		}
	}

	public String getCompanyId() {
		return GetterUtil.getString(_companyId);
	}

	public void setCompanyId(String companyId) {
		if (((companyId == null) && (_companyId != null)) ||
				((companyId != null) && (_companyId == null)) ||
				((companyId != null) && (_companyId != null) &&
				!companyId.equals(_companyId))) {
			if (!XSS_ALLOW_COMPANYID) {
				companyId = XSSUtil.strip(companyId);
			}

			_companyId = companyId;
			setModified(true);
		}
	}

	public String getUserId() {
		return GetterUtil.getString(_userId);
	}

	public void setUserId(String userId) {
		if (((userId == null) && (_userId != null)) ||
				((userId != null) && (_userId == null)) ||
				((userId != null) && (_userId != null) &&
				!userId.equals(_userId))) {
			if (!XSS_ALLOW_USERID) {
				userId = XSSUtil.strip(userId);
			}

			_userId = userId;
			setModified(true);
		}
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		if (((createDate == null) && (_createDate != null)) ||
				((createDate != null) && (_createDate == null)) ||
				((createDate != null) && (_createDate != null) &&
				!createDate.equals(_createDate))) {
			_createDate = createDate;
			setModified(true);
		}
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		if (((modifiedDate == null) && (_modifiedDate != null)) ||
				((modifiedDate != null) && (_modifiedDate == null)) ||
				((modifiedDate != null) && (_modifiedDate != null) &&
				!modifiedDate.equals(_modifiedDate))) {
			_modifiedDate = modifiedDate;
			setModified(true);
		}
	}

	public String getRecipientName() {
		return GetterUtil.getString(_recipientName);
	}

	public void setRecipientName(String recipientName) {
		if (((recipientName == null) && (_recipientName != null)) ||
				((recipientName != null) && (_recipientName == null)) ||
				((recipientName != null) && (_recipientName != null) &&
				!recipientName.equals(_recipientName))) {
			if (!XSS_ALLOW_RECIPIENTNAME) {
				recipientName = XSSUtil.strip(recipientName);
			}

			_recipientName = recipientName;
			setModified(true);
		}
	}

	public String getRecipientAddress() {
		return GetterUtil.getString(_recipientAddress);
	}

	public void setRecipientAddress(String recipientAddress) {
		if (((recipientAddress == null) && (_recipientAddress != null)) ||
				((recipientAddress != null) && (_recipientAddress == null)) ||
				((recipientAddress != null) && (_recipientAddress != null) &&
				!recipientAddress.equals(_recipientAddress))) {
			if (!XSS_ALLOW_RECIPIENTADDRESS) {
				recipientAddress = XSSUtil.strip(recipientAddress);
			}

			_recipientAddress = recipientAddress;
			setModified(true);
		}
	}

	public String getSubject() {
		return GetterUtil.getString(_subject);
	}

	public void setSubject(String subject) {
		if (((subject == null) && (_subject != null)) ||
				((subject != null) && (_subject == null)) ||
				((subject != null) && (_subject != null) &&
				!subject.equals(_subject))) {
			if (!XSS_ALLOW_SUBJECT) {
				subject = XSSUtil.strip(subject);
			}

			_subject = subject;
			setModified(true);
		}
	}

	public Date getSentDate() {
		return _sentDate;
	}

	public void setSentDate(Date sentDate) {
		if (((sentDate == null) && (_sentDate != null)) ||
				((sentDate != null) && (_sentDate == null)) ||
				((sentDate != null) && (_sentDate != null) &&
				!sentDate.equals(_sentDate))) {
			_sentDate = sentDate;
			setModified(true);
		}
	}

	public int getReadCount() {
		return _readCount;
	}

	public void setReadCount(int readCount) {
		if (readCount != _readCount) {
			_readCount = readCount;
			setModified(true);
		}
	}

	public Date getFirstReadDate() {
		return _firstReadDate;
	}

	public void setFirstReadDate(Date firstReadDate) {
		if (((firstReadDate == null) && (_firstReadDate != null)) ||
				((firstReadDate != null) && (_firstReadDate == null)) ||
				((firstReadDate != null) && (_firstReadDate != null) &&
				!firstReadDate.equals(_firstReadDate))) {
			_firstReadDate = firstReadDate;
			setModified(true);
		}
	}

	public Date getLastReadDate() {
		return _lastReadDate;
	}

	public void setLastReadDate(Date lastReadDate) {
		if (((lastReadDate == null) && (_lastReadDate != null)) ||
				((lastReadDate != null) && (_lastReadDate == null)) ||
				((lastReadDate != null) && (_lastReadDate != null) &&
				!lastReadDate.equals(_lastReadDate))) {
			_lastReadDate = lastReadDate;
			setModified(true);
		}
	}

	public Object clone() {
		MailReceipt clone = new MailReceipt();
		clone.setReceiptId(getReceiptId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setRecipientName(getRecipientName());
		clone.setRecipientAddress(getRecipientAddress());
		clone.setSubject(getSubject());
		clone.setSentDate(getSentDate());
		clone.setReadCount(getReadCount());
		clone.setFirstReadDate(getFirstReadDate());
		clone.setLastReadDate(getLastReadDate());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		MailReceipt mailReceipt = (MailReceipt)obj;
		int value = 0;
		value = getCreateDate().compareTo(mailReceipt.getCreateDate());
		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		MailReceipt mailReceipt = null;

		try {
			mailReceipt = (MailReceipt)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = mailReceipt.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	private String _receiptId;
	private String _companyId;
	private String _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private String _recipientName;
	private String _recipientAddress;
	private String _subject;
	private Date _sentDate;
	private int _readCount;
	private Date _firstReadDate;
	private Date _lastReadDate;
}