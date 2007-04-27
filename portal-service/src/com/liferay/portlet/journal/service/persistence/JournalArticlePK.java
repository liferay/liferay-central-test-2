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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

/**
 * <a href="JournalArticlePK.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalArticlePK implements Comparable, Serializable {
	public long companyId;
	public long groupId;
	public String articleId;
	public double version;

	public JournalArticlePK() {
	}

	public JournalArticlePK(long companyId, long groupId, String articleId,
		double version) {
		this.companyId = companyId;
		this.groupId = groupId;
		this.articleId = articleId;
		this.version = version;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
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

		JournalArticlePK pk = (JournalArticlePK)obj;
		int value = 0;

		if (companyId < pk.companyId) {
			value = -1;
		}
		else if (companyId > pk.companyId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (groupId < pk.groupId) {
			value = -1;
		}
		else if (groupId > pk.groupId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		value = articleId.compareTo(pk.articleId);

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

		JournalArticlePK pk = null;

		try {
			pk = (JournalArticlePK)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		if ((companyId == pk.companyId) && (groupId == pk.groupId) &&
				(articleId.equals(pk.articleId)) && (version == pk.version)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (String.valueOf(companyId) + String.valueOf(groupId) +
		String.valueOf(articleId) + String.valueOf(version)).hashCode();
	}

	public String toString() {
		StringMaker sm = new StringMaker();
		sm.append(StringPool.OPEN_CURLY_BRACE);
		sm.append("companyId");
		sm.append(StringPool.EQUAL);
		sm.append(companyId);
		sm.append(StringPool.COMMA);
		sm.append(StringPool.SPACE);
		sm.append("groupId");
		sm.append(StringPool.EQUAL);
		sm.append(groupId);
		sm.append(StringPool.COMMA);
		sm.append(StringPool.SPACE);
		sm.append("articleId");
		sm.append(StringPool.EQUAL);
		sm.append(articleId);
		sm.append(StringPool.COMMA);
		sm.append(StringPool.SPACE);
		sm.append("version");
		sm.append(StringPool.EQUAL);
		sm.append(version);
		sm.append(StringPool.CLOSE_CURLY_BRACE);

		return sm.toString();
	}
}