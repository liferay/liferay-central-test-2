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

package com.liferay.portal.sharepoint.dws;

import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.User;

/**
 * <a href="MemberResponseElement.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class MemberResponseElement implements ResponseElement {

	public MemberResponseElement(User user, boolean member) {
		_id = user.getScreenName();
		_name = user.getFullName();
		_loginName = user.getScreenName();
		_email = user.getEmailAddress();
		_domainGroup = false;
		_member = member;
		_siteAdmin = false;
	}

	public void addElement(Element rootEl) {
		String user = "User";

		if (_member) {
			user = "Member";
		}

		Element el = rootEl.addElement(user);

		el.addElement("ID").setText(_id);
		el.addElement("Name").setText(_name);
		el.addElement("LoginName").setText(_loginName);
		el.addElement("Email").setText(_email);
		el.addElement("IsDomainGroup").setText(String.valueOf(_domainGroup));
		el.addElement("IsSiteAdmin").setText(String.valueOf(_siteAdmin));
	}

	private String _id;
	private String _name;
	private String _loginName;
	private String _email;
	private boolean _domainGroup;
	private boolean _member;
	private boolean _siteAdmin;

}