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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

/**
 * <a href="DLFileRankPK.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DLFileRankPK implements Comparable, Serializable {
	public String companyId;
	public long userId;
	public String folderId;
	public String name;

	public DLFileRankPK() {
	}

	public DLFileRankPK(String companyId, long userId, String folderId,
		String name) {
		this.companyId = companyId;
		this.userId = userId;
		this.folderId = folderId;
		this.name = name;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		DLFileRankPK pk = (DLFileRankPK)obj;
		int value = 0;
		value = companyId.compareTo(pk.companyId);

		if (value != 0) {
			return value;
		}

		if (userId < pk.userId) {
			value = -1;
		}
		else if (userId > pk.userId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		value = folderId.compareTo(pk.folderId);

		if (value != 0) {
			return value;
		}

		value = name.compareTo(pk.name);

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		DLFileRankPK pk = null;

		try {
			pk = (DLFileRankPK)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		if ((companyId.equals(pk.companyId)) && (userId == pk.userId) &&
				(folderId.equals(pk.folderId)) && (name.equals(pk.name))) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (String.valueOf(companyId) + String.valueOf(userId) +
		String.valueOf(folderId) + String.valueOf(name)).hashCode();
	}

	public String toString() {
		StringMaker sm = new StringMaker();
		sm.append(StringPool.OPEN_CURLY_BRACE);
		sm.append("companyId");
		sm.append(StringPool.EQUAL);
		sm.append(companyId);
		sm.append(StringPool.COMMA);
		sm.append(StringPool.SPACE);
		sm.append("userId");
		sm.append(StringPool.EQUAL);
		sm.append(userId);
		sm.append(StringPool.COMMA);
		sm.append(StringPool.SPACE);
		sm.append("folderId");
		sm.append(StringPool.EQUAL);
		sm.append(folderId);
		sm.append(StringPool.COMMA);
		sm.append(StringPool.SPACE);
		sm.append("name");
		sm.append(StringPool.EQUAL);
		sm.append(name);
		sm.append(StringPool.CLOSE_CURLY_BRACE);

		return sm.toString();
	}
}