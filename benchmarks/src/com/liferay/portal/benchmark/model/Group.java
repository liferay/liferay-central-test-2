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

package com.liferay.portal.benchmark.model;

/**
 * <a href="Group.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class Group {

	public Group(long groupId, long companyId, long creatorUserId,
				 long classNameId, long classPK) {
		_groupId = groupId;
		_companyId = companyId;
		_creatorUserId = creatorUserId;
		_classNameId = classNameId;
		_classPK = classPK;
		_active = true;
	}

	public long getGroupId() {
		return _groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getCreatorUserId() {
		return _creatorUserId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public long getParentGroupId() {
		return _parentGroupId;
	}

	public long getLiveGroupId() {
		return _liveGroupId;
	}

	public String getName() {
		return _name;
	}

	public String getDescription() {
		return _description;
	}

	public int getType() {
		return _type;
	}

	public String getTypeSettings() {
		return _typeSettings;
	}

	public String getFriendlyURL() {
		return _friendlyURL;
	}

	public boolean isActive() {
		return _active;
	}

	public void setParentGroupId(long parentGroupId) {
		_parentGroupId = parentGroupId;
	}

	public void setLiveGroupId(long liveGroupId) {
		_liveGroupId = liveGroupId;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setType(int type) {
		_type = type;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	public void setFriendlyURL(String friendlyURL) {
		_friendlyURL = friendlyURL;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	private long _groupId;
	private long _companyId;
	private long _creatorUserId;
	private long _classNameId;
	private long _classPK;
	private long _parentGroupId;
	private long _liveGroupId;
	private String _name = "";
	private String _description = "";
	private int _type;
	private String _typeSettings = "";
	private String _friendlyURL = "";
	private boolean _active;
}