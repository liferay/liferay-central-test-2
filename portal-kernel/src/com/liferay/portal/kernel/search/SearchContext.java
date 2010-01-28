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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.dao.orm.QueryUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * <a href="SearchContext.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SearchContext implements Serializable {

	public Serializable getAttribute(String name) {
		return _attributes.get(name);
	}

	public Map<String, Serializable> getAttributes() {
		return _attributes;
	}

	public BooleanClause[] getBooleanClauses() {
		return _booleanClauses;
	}

	public long[] getCategoryIds() {
		return _categoryIds;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public int getEnd() {
		return _end;
	}

	public long[] getFolderIds() {
		return _folderIds;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getKeywords() {
		return _keywords;
	}

	public long[] getNodeIds() {
		return _nodeIds;
	}

	public long getOwnerUserId() {
		return _ownerUserId;
	}

	public Sort[] getSorts() {
		return _sorts;
	}

	public int getStart() {
		return _start;
	}

	public long getUserId() {
		return _userId;
	}

	public void setAttribute(String name, Serializable value) {
		_attributes.put(name, value);
	}

	public void setAttributes(Map<String, Serializable> attributes) {
		_attributes = attributes;
	}

	public void setBooleanClauses(BooleanClause[] booleanClauses) {
		_booleanClauses = booleanClauses;
	}

	public void setCategoryIds(long[] categoryIds) {
		_categoryIds = categoryIds;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setEnd(int end) {
		_end = end;
	}

	public void setFolderIds(long[] folderIds) {
		_folderIds = folderIds;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setKeywords(String keywords) {
		_keywords = keywords;
	}

	public void setNodeIds(long[] nodeIds) {
		_nodeIds = nodeIds;
	}

	public void setOwnerUserId(long ownerUserId) {
		_ownerUserId = ownerUserId;
	}

	public void setSorts(Sort[] sorts) {
		_sorts = sorts;
	}

	public void setStart(int start) {
		_start = start;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	private Map<String, Serializable> _attributes;
	private BooleanClause[] _booleanClauses;
	private long[] _categoryIds;
	private long _companyId;
	private int _end = QueryUtil.ALL_POS;
	private long[] _folderIds;
	private long _groupId;
	private String _keywords;
	private long[] _nodeIds;
	private long _ownerUserId;
	private Sort[] _sorts;
	private int _start = QueryUtil.ALL_POS;
	private long _userId;

}