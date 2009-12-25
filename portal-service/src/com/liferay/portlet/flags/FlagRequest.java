/*
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.flags;

import com.liferay.portal.service.ServiceContext;

import java.io.Serializable;

/**
 * <a href="FlagRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class FlagRequest implements Serializable {

	public FlagRequest(
		String className, long classPK, String reporterEmailAddress,
		long reportedUserId, String contentTitle, String contentURL,
		String reason, ServiceContext serviceContext) {
		
		_className = className;
		_classPK = classPK;
		_reporterEmailAddress = reporterEmailAddress;
		_reportedUserId = reportedUserId;
		this.contentTitle = contentTitle;
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
		return contentTitle;
	}

	public String getContentURL() {
		return _contentURL;
	}

	public String getReason() {
		return _reason;
	}

	public ServiceContext getServiceContext() {
		return _serviceContext;
	}

	public String getReporterEmailAddress() {
		return _reporterEmailAddress;
	}

	public long getReportedUserId() {
		return _reportedUserId;
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
		this.contentTitle = contentTitle;
	}

	public void setContentURL(String contentURL) {
		_contentURL = contentURL;
	}

	public void setReason(String reason) {
		this._reason = reason;
	}

	public void setReporterEmailAddress(String reporterEmailAddress) {
		_reporterEmailAddress = reporterEmailAddress;
	}

	public void setReportedUserId(long reportedUserId) {
		_reportedUserId = reportedUserId;
	}

	public void setServiceContext(ServiceContext serviceContext) {
		_serviceContext = serviceContext;
	}

	@Override
	public String toString() {
		return "FlagRequest{" +
			"_className='" + _className + '\'' +
			", _classPK=" + _classPK +
			", _comments='" + _comments + '\'' +
			", contentTitle='" + contentTitle + '\'' +
			", _contentURL='" + _contentURL + '\'' +
			", _reason='" + _reason + '\'' +
			", _reporterEmailAddress='" + _reporterEmailAddress + '\'' +
			", _reportedUserId=" + _reportedUserId +
			", _serviceContext=" + _serviceContext +
			'}';
	}

	private String _className;
	private long _classPK;
	private String _comments;
	private String contentTitle;
	private String _contentURL;
	private String _reason;
	private String _reporterEmailAddress;
	private long _reportedUserId;
	private ServiceContext _serviceContext;
}
