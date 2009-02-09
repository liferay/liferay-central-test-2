/*
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

package com.liferay.portal.benchmark.model;

import java.util.Date;

/**
 * <a href="Contact.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class Contact {

	public Contact(long contactId, long companyId, long userId, String userName,
				   String firstName, String lastName) {
		_contactId = contactId;
		_companyId = companyId;
		_userId = userId;
		_userName = userName;
		_firstName = firstName;
		_lastName = lastName;
		_createDate = new Date();
		_modifiedDate = new Date();
		_birthday = new Date();
	}

	public long getContactId() {
		return _contactId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public String getUserName() {
		return _userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public long getAccountId() {
		return _accountId;
	}

	public long getParentContactId() {
		return _parentContactId;
	}

	public String getFirstName() {
		return _firstName;
	}

	public String getMiddleName() {
		return _middleName;
	}

	public String getLastName() {
		return _lastName;
	}

	public int getPrefixId() {
		return _prefixId;
	}

	public int getSuffixId() {
		return _suffixId;
	}

	public boolean isMale() {
		return _male;
	}

	public Date getBirthday() {
		return _birthday;
	}

	public String getSmsSn() {
		return _smsSn;
	}

	public String getAimSn() {
		return _aimSn;
	}

	public String getFacebookSn() {
		return _facebookSn;
	}

	public String getIcqSn() {
		return _icqSn;
	}

	public String getJabberSn() {
		return _jabberSn;
	}

	public String getMsnSn() {
		return _msnSn;
	}

	public String getMySpaceSn() {
		return _mySpaceSn;
	}

	public String getSkypeSn() {
		return _skypeSn;
	}

	public String getTwitterSn() {
		return _twitterSn;
	}

	public String getYmSn() {
		return _ymSn;
	}

	public String getEmployeeStatusId() {
		return _employeeStatusId;
	}

	public String getEmployeeNumber() {
		return _employeeNumber;
	}

	public String getJobTitle() {
		return _jobTitle;
	}

	public String getJobClass() {
		return _jobClass;
	}

	public String getHoursOfOperation() {
		return _hoursOfOperation;
	}


	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setAccountId(long accountId) {
		_accountId = accountId;
	}

	public void setParentContactId(long parentContactId) {
		_parentContactId = parentContactId;
	}

	public void setFirstName(String firstName) {
		_firstName = firstName;
	}

	public void setMiddleName(String middleName) {
		_middleName = middleName;
	}

	public void setLastName(String lastName) {
		_lastName = lastName;
	}

	public void setPrefixId(int prefixId) {
		_prefixId = prefixId;
	}

	public void setSuffixId(int suffixId) {
		_suffixId = suffixId;
	}

	public void setMale(boolean male) {
		_male = male;
	}

	public void setBirthday(Date birthday) {
		_birthday = birthday;
	}

	public void setSmsSn(String smsSn) {
		_smsSn = smsSn;
	}

	public void setAimSn(String aimSn) {
		_aimSn = aimSn;
	}

	public void setFacebookSn(String facebookSn) {
		_facebookSn = facebookSn;
	}

	public void setIcqSn(String icqSn) {
		_icqSn = icqSn;
	}

	public void setJabberSn(String jabberSn) {
		_jabberSn = jabberSn;
	}

	public void setMsnSn(String msnSn) {
		_msnSn = msnSn;
	}

	public void setMySpaceSn(String mySpaceSn) {
		_mySpaceSn = mySpaceSn;
	}

	public void setSkypeSn(String skypeSn) {
		_skypeSn = skypeSn;
	}

	public void setTwitterSn(String twitterSn) {
		_twitterSn = twitterSn;
	}

	public void setYmSn(String ymSn) {
		_ymSn = ymSn;
	}

	public void setEmployeeStatusId(String employeeStatusId) {
		_employeeStatusId = employeeStatusId;
	}

	public void setEmployeeNumber(String employeeNumber) {
		_employeeNumber = employeeNumber;
	}

	public void setJobTitle(String jobTitle) {
		_jobTitle = jobTitle;
	}

	public void setJobClass(String jobClass) {
		_jobClass = jobClass;
	}

	public void setHoursOfOperation(String hoursOfOperation) {
		_hoursOfOperation = hoursOfOperation;
	}

	private long _contactId;
	private long _companyId;
	private long _userId;
	private String _userName = "";
	private Date _createDate;
	private Date _modifiedDate;
	private long _accountId = 0;
	private long _parentContactId = 0;
	private String _firstName = "";
	private String _middleName = "";
	private String _lastName = "";
	private int _prefixId = 0;
	private int _suffixId = 0;
	private boolean _male;
	private Date _birthday = new Date();
	private String _smsSn = "";
	private String _aimSn = "";
	private String _facebookSn = "";
	private String _icqSn = "";
	private String _jabberSn = "";
	private String _msnSn = "";
	private String _mySpaceSn = "";
	private String _skypeSn = "";
	private String _twitterSn = "";
	private String _ymSn = "";
	private String _employeeStatusId = "";
	private String _employeeNumber = "";
	private String _jobTitle = "";
	private String _jobClass = "";
	private String _hoursOfOperation = "";
}
