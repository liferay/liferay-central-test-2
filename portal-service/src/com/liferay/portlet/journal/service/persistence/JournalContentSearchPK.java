/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

/**
 * <a href="JournalContentSearchPK.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalContentSearchPK implements Comparable, Serializable {
	public String portletId;
	public String layoutId;
	public String ownerId;
	public String articleId;

	public JournalContentSearchPK() {
	}

	public JournalContentSearchPK(String portletId, String layoutId,
		String ownerId, String articleId) {
		this.portletId = portletId;
		this.layoutId = layoutId;
		this.ownerId = ownerId;
		this.articleId = articleId;
	}

	public String getPortletId() {
		return portletId;
	}

	public void setPortletId(String portletId) {
		this.portletId = portletId;
	}

	public String getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		JournalContentSearchPK pk = (JournalContentSearchPK)obj;
		int value = 0;
		value = portletId.compareTo(pk.portletId);

		if (value != 0) {
			return value;
		}

		value = layoutId.compareTo(pk.layoutId);

		if (value != 0) {
			return value;
		}

		value = ownerId.compareTo(pk.ownerId);

		if (value != 0) {
			return value;
		}

		value = articleId.compareTo(pk.articleId);

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		JournalContentSearchPK pk = null;

		try {
			pk = (JournalContentSearchPK)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		if ((portletId.equals(pk.portletId)) && (layoutId.equals(pk.layoutId)) &&
				(ownerId.equals(pk.ownerId)) &&
				(articleId.equals(pk.articleId))) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (portletId + layoutId + ownerId + articleId).hashCode();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(StringPool.OPEN_CURLY_BRACE);
		sb.append("portletId");
		sb.append(StringPool.EQUAL);
		sb.append(portletId);
		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("layoutId");
		sb.append(StringPool.EQUAL);
		sb.append(layoutId);
		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("ownerId");
		sb.append(StringPool.EQUAL);
		sb.append(ownerId);
		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("articleId");
		sb.append(StringPool.EQUAL);
		sb.append(articleId);
		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}
}