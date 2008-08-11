/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.messaging;

/**
 * <a href="EmailReaderRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Thiago Moreira
 */
public class EmailReaderRequest {

	public long getCategoryId() {

		return _categoryId;
	}

	public long getCompanyId() {

		return _companyId;
	}

	public String getMailInPassword() {

		return _mailInPassword;
	}

	public String getMailInProtocol() {

		return _mailInProtocol;
	}

	public String getMailInServerName() {

		return _mailInServerName;
	}

	public Integer getMailInServerPort() {

		return _mailInServerPort;
	}

	public String getMailInUserName() {

		return _mailInUserName;
	}

	public Boolean getMailInUseSSL() {

		return _mailInUseSSL;
	}

	public String getMailOutPassword() {

		return _mailOutPassword;
	}

	public String getMailOutServerName() {

		return _mailOutServerName;
	}

	public Integer getMailOutServerPort() {

		return _mailOutServerPort;
	}

	public Boolean getMailOutConfigured() {

		return _mailOutConfigured;
	}

	public String getMailOutUserName() {

		return _mailOutUserName;
	}

	public Boolean getMailOutUseSSL() {

		return _mailOutUseSSL;
	}

	public long getUserId() {

		return _userId;
	}

	public void setCategoryId(long id) {

		_categoryId = id;
	}

	public void setCompanyId(long id) {

		_companyId = id;
	}

	public void setMailInPassword(String mailInPassword) {

		_mailInPassword = mailInPassword;
	}

	public void setMailInProtocol(String mailInProtocol) {

		_mailInProtocol = mailInProtocol;
	}

	public void setMailInServerName(String mailInServerName) {

		_mailInServerName = mailInServerName;
	}

	public void setMailInServerPort(Integer mailInServerPort) {

		_mailInServerPort = mailInServerPort;
	}

	public void setMailInUserName(String mailInUserName) {

		_mailInUserName = mailInUserName;
	}

	public void setMailInUseSSL(Boolean mailInUseSSL) {

		_mailInUseSSL = mailInUseSSL;
	}

	public void setMailOutPassword(String mailOutPassword) {

		_mailOutPassword = mailOutPassword;
	}

	public void setMailOutServerName(String mailOutServerName) {

		_mailOutServerName = mailOutServerName;
	}

	public void setMailOutServerPort(Integer mailOutServerPort) {

		_mailOutServerPort = mailOutServerPort;
	}

	public void setMailOutConfigured(Boolean mailOutConfigured) {

		_mailOutConfigured = mailOutConfigured;
	}

	public void setMailOutUserName(String mailOutUserName) {

		_mailOutUserName = mailOutUserName;
	}

	public void setMailOutUseSSL(Boolean mailOutUseSSL) {

		_mailOutUseSSL = mailOutUseSSL;
	}

	public void setUserId(long id) {

		_userId = id;
	}

	private long _categoryId;
	private long _companyId;
	private String _mailInPassword;
	private String _mailInProtocol;
	private String _mailInServerName;
	private Integer _mailInServerPort;
	private String _mailInUserName;
	private Boolean _mailInUseSSL;
	private String _mailOutPassword;
	private String _mailOutServerName;
	private Integer _mailOutServerPort;
	private Boolean _mailOutConfigured;
	private String _mailOutUserName;
	private Boolean _mailOutUseSSL;
	private long _userId;

}