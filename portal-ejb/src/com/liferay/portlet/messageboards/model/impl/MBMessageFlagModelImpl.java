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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPK;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.sql.Types;

/**
 * <a href="MBMessageFlagModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>MBMessageFlag</code> table in
 * the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.model.MBMessageFlag
 * @see com.liferay.portlet.messageboards.service.model.MBMessageFlagModel
 * @see com.liferay.portlet.messageboards.service.model.impl.MBMessageFlagImpl
 *
 */
public class MBMessageFlagModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "MBMessageFlag";
	public static Object[][] TABLE_COLUMNS = {
			{ "topicId", new Integer(Types.VARCHAR) },
			{ "messageId", new Integer(Types.VARCHAR) },
			{ "userId", new Integer(Types.VARCHAR) },
			{ "flag", new Integer(Types.VARCHAR) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBMessageFlag"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_TOPICID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBMessageFlag.topicId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_MESSAGEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBMessageFlag.messageId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBMessageFlag.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_FLAG = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBMessageFlag.flag"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.messageboards.model.MBMessageFlagModel"));

	public MBMessageFlagModelImpl() {
	}

	public MBMessageFlagPK getPrimaryKey() {
		return new MBMessageFlagPK(_topicId, _messageId, _userId);
	}

	public void setPrimaryKey(MBMessageFlagPK pk) {
		setTopicId(pk.topicId);
		setMessageId(pk.messageId);
		setUserId(pk.userId);
	}

	public String getTopicId() {
		return GetterUtil.getString(_topicId);
	}

	public void setTopicId(String topicId) {
		if (((topicId == null) && (_topicId != null)) ||
				((topicId != null) && (_topicId == null)) ||
				((topicId != null) && (_topicId != null) &&
				!topicId.equals(_topicId))) {
			if (!XSS_ALLOW_TOPICID) {
				topicId = XSSUtil.strip(topicId);
			}

			_topicId = topicId;
		}
	}

	public String getMessageId() {
		return GetterUtil.getString(_messageId);
	}

	public void setMessageId(String messageId) {
		if (((messageId == null) && (_messageId != null)) ||
				((messageId != null) && (_messageId == null)) ||
				((messageId != null) && (_messageId != null) &&
				!messageId.equals(_messageId))) {
			if (!XSS_ALLOW_MESSAGEID) {
				messageId = XSSUtil.strip(messageId);
			}

			_messageId = messageId;
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
		}
	}

	public String getFlag() {
		return GetterUtil.getString(_flag);
	}

	public void setFlag(String flag) {
		if (((flag == null) && (_flag != null)) ||
				((flag != null) && (_flag == null)) ||
				((flag != null) && (_flag != null) && !flag.equals(_flag))) {
			if (!XSS_ALLOW_FLAG) {
				flag = XSSUtil.strip(flag);
			}

			_flag = flag;
		}
	}

	public Object clone() {
		MBMessageFlagImpl clone = new MBMessageFlagImpl();
		clone.setTopicId(getTopicId());
		clone.setMessageId(getMessageId());
		clone.setUserId(getUserId());
		clone.setFlag(getFlag());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		MBMessageFlagImpl mbMessageFlag = (MBMessageFlagImpl)obj;
		MBMessageFlagPK pk = mbMessageFlag.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		MBMessageFlagImpl mbMessageFlag = null;

		try {
			mbMessageFlag = (MBMessageFlagImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		MBMessageFlagPK pk = mbMessageFlag.getPrimaryKey();

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

	private String _topicId;
	private String _messageId;
	private String _userId;
	private String _flag;
}