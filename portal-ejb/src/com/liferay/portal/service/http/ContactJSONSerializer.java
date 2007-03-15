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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Contact;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * <a href="ContactJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portal.service.http.ContactServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.ContactServiceJSON
 *
 */
public class ContactJSONSerializer {
	public static JSONObject toJSONObject(Contact model) {
		JSONObject jsonObj = new JSONObject();
		String contactId = model.getContactId();

		if (contactId == null) {
			jsonObj.put("contactId", StringPool.BLANK);
		}
		else {
			jsonObj.put("contactId", contactId.toString());
		}

		String companyId = model.getCompanyId();

		if (companyId == null) {
			jsonObj.put("companyId", StringPool.BLANK);
		}
		else {
			jsonObj.put("companyId", companyId.toString());
		}

		String userId = model.getUserId();

		if (userId == null) {
			jsonObj.put("userId", StringPool.BLANK);
		}
		else {
			jsonObj.put("userId", userId.toString());
		}

		String userName = model.getUserName();

		if (userName == null) {
			jsonObj.put("userName", StringPool.BLANK);
		}
		else {
			jsonObj.put("userName", userName.toString());
		}

		Date createDate = model.getCreateDate();

		if (createDate == null) {
			jsonObj.put("createDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("createDate", createDate.toString());
		}

		Date modifiedDate = model.getModifiedDate();

		if (modifiedDate == null) {
			jsonObj.put("modifiedDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("modifiedDate", modifiedDate.toString());
		}

		String accountId = model.getAccountId();

		if (accountId == null) {
			jsonObj.put("accountId", StringPool.BLANK);
		}
		else {
			jsonObj.put("accountId", accountId.toString());
		}

		String parentContactId = model.getParentContactId();

		if (parentContactId == null) {
			jsonObj.put("parentContactId", StringPool.BLANK);
		}
		else {
			jsonObj.put("parentContactId", parentContactId.toString());
		}

		String firstName = model.getFirstName();

		if (firstName == null) {
			jsonObj.put("firstName", StringPool.BLANK);
		}
		else {
			jsonObj.put("firstName", firstName.toString());
		}

		String middleName = model.getMiddleName();

		if (middleName == null) {
			jsonObj.put("middleName", StringPool.BLANK);
		}
		else {
			jsonObj.put("middleName", middleName.toString());
		}

		String lastName = model.getLastName();

		if (lastName == null) {
			jsonObj.put("lastName", StringPool.BLANK);
		}
		else {
			jsonObj.put("lastName", lastName.toString());
		}

		String nickName = model.getNickName();

		if (nickName == null) {
			jsonObj.put("nickName", StringPool.BLANK);
		}
		else {
			jsonObj.put("nickName", nickName.toString());
		}

		jsonObj.put("prefixId", model.getPrefixId());
		jsonObj.put("suffixId", model.getSuffixId());
		jsonObj.put("male", model.getMale());

		Date birthday = model.getBirthday();

		if (birthday == null) {
			jsonObj.put("birthday", StringPool.BLANK);
		}
		else {
			jsonObj.put("birthday", birthday.toString());
		}

		String smsSn = model.getSmsSn();

		if (smsSn == null) {
			jsonObj.put("smsSn", StringPool.BLANK);
		}
		else {
			jsonObj.put("smsSn", smsSn.toString());
		}

		String aimSn = model.getAimSn();

		if (aimSn == null) {
			jsonObj.put("aimSn", StringPool.BLANK);
		}
		else {
			jsonObj.put("aimSn", aimSn.toString());
		}

		String icqSn = model.getIcqSn();

		if (icqSn == null) {
			jsonObj.put("icqSn", StringPool.BLANK);
		}
		else {
			jsonObj.put("icqSn", icqSn.toString());
		}

		String jabberSn = model.getJabberSn();

		if (jabberSn == null) {
			jsonObj.put("jabberSn", StringPool.BLANK);
		}
		else {
			jsonObj.put("jabberSn", jabberSn.toString());
		}

		String msnSn = model.getMsnSn();

		if (msnSn == null) {
			jsonObj.put("msnSn", StringPool.BLANK);
		}
		else {
			jsonObj.put("msnSn", msnSn.toString());
		}

		String skypeSn = model.getSkypeSn();

		if (skypeSn == null) {
			jsonObj.put("skypeSn", StringPool.BLANK);
		}
		else {
			jsonObj.put("skypeSn", skypeSn.toString());
		}

		String ymSn = model.getYmSn();

		if (ymSn == null) {
			jsonObj.put("ymSn", StringPool.BLANK);
		}
		else {
			jsonObj.put("ymSn", ymSn.toString());
		}

		String employeeStatusId = model.getEmployeeStatusId();

		if (employeeStatusId == null) {
			jsonObj.put("employeeStatusId", StringPool.BLANK);
		}
		else {
			jsonObj.put("employeeStatusId", employeeStatusId.toString());
		}

		String employeeNumber = model.getEmployeeNumber();

		if (employeeNumber == null) {
			jsonObj.put("employeeNumber", StringPool.BLANK);
		}
		else {
			jsonObj.put("employeeNumber", employeeNumber.toString());
		}

		String jobTitle = model.getJobTitle();

		if (jobTitle == null) {
			jsonObj.put("jobTitle", StringPool.BLANK);
		}
		else {
			jsonObj.put("jobTitle", jobTitle.toString());
		}

		String jobClass = model.getJobClass();

		if (jobClass == null) {
			jsonObj.put("jobClass", StringPool.BLANK);
		}
		else {
			jsonObj.put("jobClass", jobClass.toString());
		}

		String hoursOfOperation = model.getHoursOfOperation();

		if (hoursOfOperation == null) {
			jsonObj.put("hoursOfOperation", StringPool.BLANK);
		}
		else {
			jsonObj.put("hoursOfOperation", hoursOfOperation.toString());
		}

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			Contact model = (Contact)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}