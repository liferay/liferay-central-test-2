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
 * <a href="JournalStructurePK.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalStructurePK implements Comparable, Serializable {
	public String companyId;
	public String groupId;
	public String structureId;

	public JournalStructurePK() {
	}

	public JournalStructurePK(String companyId, String groupId,
		String structureId) {
		this.companyId = companyId;
		this.groupId = groupId;
		this.structureId = structureId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getStructureId() {
		return structureId;
	}

	public void setStructureId(String structureId) {
		this.structureId = structureId;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		JournalStructurePK pk = (JournalStructurePK)obj;
		int value = 0;
		value = companyId.compareTo(pk.companyId);

		if (value != 0) {
			return value;
		}

		value = groupId.compareTo(pk.groupId);

		if (value != 0) {
			return value;
		}

		value = structureId.compareTo(pk.structureId);

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		JournalStructurePK pk = null;

		try {
			pk = (JournalStructurePK)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		if ((companyId.equals(pk.companyId)) && (groupId.equals(pk.groupId)) &&
				(structureId.equals(pk.structureId))) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (companyId + groupId + structureId).hashCode();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(StringPool.OPEN_CURLY_BRACE);
		sb.append("companyId");
		sb.append(StringPool.EQUAL);
		sb.append(companyId);
		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("groupId");
		sb.append(StringPool.EQUAL);
		sb.append(groupId);
		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("structureId");
		sb.append(StringPool.EQUAL);
		sb.append(structureId);
		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}
}