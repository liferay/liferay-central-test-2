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

package com.liferay.portlet.wiki.service.persistence;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

/**
 * <a href="WikiPagePK.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class WikiPagePK implements Comparable, Serializable {
	public String nodeId;
	public String title;
	public double version;

	public WikiPagePK() {
	}

	public WikiPagePK(String nodeId, String title, double version) {
		this.nodeId = nodeId;
		this.title = title;
		this.version = version;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		WikiPagePK pk = (WikiPagePK)obj;
		int value = 0;
		value = nodeId.compareTo(pk.nodeId);

		if (value != 0) {
			return value;
		}

		value = title.compareTo(pk.title);

		if (value != 0) {
			return value;
		}

		if (version < pk.version) {
			value = -1;
		}
		else if (version > pk.version) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		WikiPagePK pk = null;

		try {
			pk = (WikiPagePK)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		if ((nodeId.equals(pk.nodeId)) && (title.equals(pk.title)) &&
				(version == pk.version)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (nodeId + title + version).hashCode();
	}

	public String toString() {
		StringMaker sm = new StringMaker();
		sm.append(StringPool.OPEN_CURLY_BRACE);
		sm.append("nodeId");
		sm.append(StringPool.EQUAL);
		sm.append(nodeId);
		sm.append(StringPool.COMMA);
		sm.append(StringPool.SPACE);
		sm.append("title");
		sm.append(StringPool.EQUAL);
		sm.append(title);
		sm.append(StringPool.COMMA);
		sm.append(StringPool.SPACE);
		sm.append("version");
		sm.append(StringPool.EQUAL);
		sm.append(version);
		sm.append(StringPool.CLOSE_CURLY_BRACE);

		return sm.toString();
	}
}