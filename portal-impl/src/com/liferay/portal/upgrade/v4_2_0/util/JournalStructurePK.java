/**
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

package com.liferay.portal.upgrade.v4_2_0.util;

import com.liferay.portal.kernel.util.StringPool;

/**
 * <a href="JournalStructurePK.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Ed Shin
 *
 */
public class JournalStructurePK {

	public String companyId;
	public String groupId;
	public String structureId;

	public JournalStructurePK(String companyId, String groupId,
		String structureId) {
		this.companyId = companyId;
		this.groupId = groupId;
		this.structureId = structureId;
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