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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.User;

import java.util.Set;

/**
 * <a href="UserHBM.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserHBM extends User {
	protected UserHBM() {
	}

	protected Set getGroups() {
		return _groups;
	}

	protected void setGroups(Set groups) {
		_groups = groups;
	}

	protected Set getOrgs() {
		return _orgs;
	}

	protected void setOrgs(Set orgs) {
		_orgs = orgs;
	}

	protected Set getPermissions() {
		return _permissions;
	}

	protected void setPermissions(Set permissions) {
		_permissions = permissions;
	}

	protected Set getRoles() {
		return _roles;
	}

	protected void setRoles(Set roles) {
		_roles = roles;
	}

	private Set _groups;
	private Set _orgs;
	private Set _permissions;
	private Set _roles;
}