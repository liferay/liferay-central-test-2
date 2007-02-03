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

import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

/**
 * <a href="DLFileEntryPK.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DLFileEntryPK implements Comparable, Serializable {
	public String folderId;
	public String name;

	public DLFileEntryPK() {
	}

	public DLFileEntryPK(String folderId, String name) {
		this.folderId = folderId;
		this.name = name;
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

		DLFileEntryPK pk = (DLFileEntryPK)obj;
		int value = 0;
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

		DLFileEntryPK pk = null;

		try {
			pk = (DLFileEntryPK)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		if ((folderId.equals(pk.folderId)) && (name.equals(pk.name))) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (folderId + name).hashCode();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(StringPool.OPEN_CURLY_BRACE);
		sb.append("folderId");
		sb.append(StringPool.EQUAL);
		sb.append(folderId);
		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("name");
		sb.append(StringPool.EQUAL);
		sb.append(name);
		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}
}