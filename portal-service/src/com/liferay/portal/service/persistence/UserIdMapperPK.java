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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

/**
 * <a href="UserIdMapperPK.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserIdMapperPK implements Comparable, Serializable {
	public String userId;
	public String type;

	public UserIdMapperPK() {
	}

	public UserIdMapperPK(String userId, String type) {
		this.userId = userId;
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		UserIdMapperPK pk = (UserIdMapperPK)obj;
		int value = 0;
		value = userId.compareTo(pk.userId);

		if (value != 0) {
			return value;
		}

		value = type.compareTo(pk.type);

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		UserIdMapperPK pk = null;

		try {
			pk = (UserIdMapperPK)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		if ((userId.equals(pk.userId)) && (type.equals(pk.type))) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (userId + type).hashCode();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(StringPool.OPEN_CURLY_BRACE);
		sb.append("userId");
		sb.append(StringPool.EQUAL);
		sb.append(userId);
		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("type");
		sb.append(StringPool.EQUAL);
		sb.append(type);
		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}
}