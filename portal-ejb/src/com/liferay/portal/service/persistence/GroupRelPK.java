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

import com.liferay.util.StringPool;

import java.io.Serializable;

/**
 * <a href="GroupRelPK.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class GroupRelPK implements Comparable, Serializable {
	public String groupId;
	public String className;
	public String classPK;

	public GroupRelPK() {
	}

	public GroupRelPK(String groupId, String className, String classPK) {
		this.groupId = groupId;
		this.className = className;
		this.classPK = classPK;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassPK() {
		return classPK;
	}

	public void setClassPK(String classPK) {
		this.classPK = classPK;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		GroupRelPK pk = (GroupRelPK)obj;
		int value = 0;
		value = groupId.compareTo(pk.groupId);

		if (value != 0) {
			return value;
		}

		value = className.compareTo(pk.className);

		if (value != 0) {
			return value;
		}

		value = classPK.compareTo(pk.classPK);

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		GroupRelPK pk = null;

		try {
			pk = (GroupRelPK)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		if ((groupId.equals(pk.groupId)) && (className.equals(pk.className)) &&
				(classPK.equals(pk.classPK))) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (groupId + className + classPK).hashCode();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(StringPool.OPEN_CURLY_BRACE);
		sb.append("groupId");
		sb.append(StringPool.EQUAL);
		sb.append(groupId);
		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("className");
		sb.append(StringPool.EQUAL);
		sb.append(className);
		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("classPK");
		sb.append(StringPool.EQUAL);
		sb.append(classPK);
		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}
}