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

package com.liferay.portal.benchmark.generator.db.model;

/**
 * <a href="Role.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class Role {
	public Role(long roleId, long companyId, long classNameId, long classPK,
				String name, Scope roleType) {
		_roleId = roleId;
		_companyId = companyId;
		_classNameId = classNameId;
		_classPK = classPK;
		_name = name;
		_roleType = roleType;
	}

	public long getRoleId() {
		return _roleId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public String getName() {
		return _name;
	}

	public String getTitle() {
		return _title;
	}

	public String getDescription() {
		return _description;
	}

	public Scope getRoleType() {
		return _roleType;
	}

	public String getSubtype() {
		return _subtype;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setSubtype(String subtype) {
		_subtype = subtype;
	}

	private long _roleId;
	private long _companyId;
	private long _classNameId;
	private long _classPK;
	private String _name;
	private String _title;
	private String _description;
	private Scope _roleType;
	private String _subtype;	
}
