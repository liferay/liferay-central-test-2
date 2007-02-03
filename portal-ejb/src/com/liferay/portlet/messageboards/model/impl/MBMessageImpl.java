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

import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.util.BBCodeUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MBMessageImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBMessageImpl extends MBMessageModelImpl implements MBMessage {

	public static final String DEPRECATED_TOPIC_ID = "-1";

	public static final String DEFAULT_PARENT_MESSAGE_ID = "-1";

	public MBMessageImpl() {
	}

	public MBCategory getCategory() {
		if (getCategoryId().equals(CompanyImpl.SYSTEM)) {
			return null;
		}

		MBCategory category = null;

		try {
			category = MBCategoryLocalServiceUtil.getCategory(getCategoryId());
		}
		catch (Exception e) {
			category = new MBCategoryImpl();

			_log.error(e);
		}

		return category;
	}

	public boolean isRoot() {
		return Validator.equals(
			getParentMessageId(), DEFAULT_PARENT_MESSAGE_ID);
	}

	public boolean isReply() {
		return !isRoot();
	}

	public boolean isDiscussion() {
		return Validator.equals(getCategoryId(), CompanyImpl.SYSTEM);
	}

	public String getThreadAttachmentsDir() {
		return "messageboards/" + getThreadId();
	}

	public String getAttachmentsDir() {
		return "messageboards/" + getThreadId() + "/" + getMessageId();
	}

	public String getBody(boolean translated) {
		if (translated) {
			return BBCodeUtil.getHTML(getBody());
		}
		else {
			return getBody();
		}
	}

	private static Log _log = LogFactory.getLog(MBMessageImpl.class);

}