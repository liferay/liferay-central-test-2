/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.flags.messaging;

import com.liferay.portal.service.ServiceContext;

import java.io.Serializable;

/**
 * <a href="FlagsRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class FlagsRequest implements Serializable {

	public FlagsRequest() {
	}

	public FlagsRequest(
		String className, long classPK, String reporterEmailAddress,
		long reportedUserId, String contentTitle, String contentURL,
		String reason, ServiceContext serviceContext) {

		_className = className;
		_classPK = classPK;
		_reporterEmailAddress = reporterEmailAddress;
		_reportedUserId = reportedUserId;
		_contentTitle = contentTitle;
		_contentURL = contentURL;
		_reason = reason;
		_serviceContext = serviceContext;
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public String getComments() {
		return _comments;
	}

	public String getContentTitle() {
		return _contentTitle;
	}

	public String getContentURL() {
		return _contentURL;
	}

	public String getReason() {
		return _reason;
	}

	public long getReportedUserId() {
		return _reportedUserId;
	}

	public String getReporterEmailAddress() {
		return _reporterEmailAddress;
	}

	public ServiceContext getServiceContext() {
		return _serviceContext;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	public void setContentTitle(String contentTitle) {
		_contentTitle = contentTitle;
	}

	public void setContentURL(String contentURL) {
		_contentURL = contentURL;
	}

	public void setReason(String reason) {
		this._reason = reason;
	}

	public void setReportedUserId(long reportedUserId) {
		_reportedUserId = reportedUserId;
	}

	public void setReporterEmailAddress(String reporterEmailAddress) {
		_reporterEmailAddress = reporterEmailAddress;
	}

	public void setServiceContext(ServiceContext serviceContext) {
		_serviceContext = serviceContext;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{className=");
		sb.append(_className);
		sb.append(", classPK=");
		sb.append(_classPK);
		sb.append(", comments=");
		sb.append(_comments);
		sb.append(", contentTitle=");
		sb.append(_contentTitle);
		sb.append(", contentURL=");
		sb.append(_contentURL);
		sb.append(", reason=");
		sb.append(_reason);
		sb.append(", reportedUserId=");
		sb.append(_reportedUserId);
		sb.append(", reporterEmailAddress=");
		sb.append(_reporterEmailAddress);
		sb.append(", serviceContext=");
		sb.append(_serviceContext);
		sb.append("}");

		return sb.toString();
	}

	private String _className;
	private long _classPK;
	private String _comments;
	private String _contentTitle;
	private String _contentURL;
	private String _reason;
	private long _reportedUserId;
	private String _reporterEmailAddress;
	private ServiceContext _serviceContext;

}