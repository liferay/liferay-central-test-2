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

package com.liferay.portlet.enterpriseadmin.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletRequest;

/**
 * <a href="UserDisplayTerms.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UserDisplayTerms extends DisplayTerms {

	public static final String FIRST_NAME = "firstName";

	public static final String MIDDLE_NAME = "middleName";

	public static final String LAST_NAME = "lastName";

	public static final String SCREEN_NAME = "screenName";

	public static final String EMAIL_ADDRESS = "emailAddress";

	public static final String ACTIVE = "active";

	public static final String ORGANIZATION_ID = "organizationId";

	public static final String ROLE_ID = "roleId";

	public static final String USER_GROUP_ID = "userGroupId";

	public UserDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		firstName = ParamUtil.getString(portletRequest, FIRST_NAME);
		middleName = ParamUtil.getString(portletRequest, MIDDLE_NAME);
		lastName = ParamUtil.getString(portletRequest, LAST_NAME);
		screenName = ParamUtil.getString(portletRequest, SCREEN_NAME);
		emailAddress = ParamUtil.getString(portletRequest, EMAIL_ADDRESS);

		String activeString = ParamUtil.getString(portletRequest, ACTIVE);

		if (Validator.isNotNull(activeString)) {
			active = GetterUtil.getBoolean(activeString);
		}

		organizationId = ParamUtil.getLong(portletRequest, ORGANIZATION_ID);
		roleId = ParamUtil.getLong(portletRequest, ROLE_ID);
		userGroupId = ParamUtil.getLong(portletRequest, USER_GROUP_ID);
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public Boolean getActive() {
		return active;
	}

	public boolean hasActive() {
		if (active == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean isActive() {
		if (active == null) {
			return true;
		}

		return active.booleanValue();
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public long getOrganizationId() {
		return organizationId;
	}

	public long getRoleId() {
		return roleId;
	}

	public long getUserGroupId() {
		return userGroupId;
	}

	protected String firstName;
	protected String middleName;
	protected String lastName;
	protected String screenName;
	protected String emailAddress;
	protected Boolean active;
	protected long organizationId;
	protected long roleId;
	protected long userGroupId;

}