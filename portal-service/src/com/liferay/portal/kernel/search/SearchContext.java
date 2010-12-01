/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.dao.orm.QueryUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class SearchContext implements Serializable {

	public long[] getAssetCategoryIds() {
		return _assetCategoryIds;
	}

	public String[] getAssetTagNames() {
		return _assetTagNames;
	}

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

	public long[] getGroupIds() {
		return _groupIds;
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

	public String[] getPortletIds() {
		return _portletIds;
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

	public boolean isAndSearch(){
		return _andSearch;
	}

	public boolean isIncludeLiveGroups() {
		return _includeLiveGroups;
	}

	public boolean isIncludeStagingGroups() {
		return _includeStagingGroups;
	}

	public boolean isScopeStrict() {
		return _scopeStrict;
	}

	public void setAndSearch(boolean andSearch) {
		_andSearch = andSearch;
	}

	public void setAssetCategoryIds(long[] assetCategoryIds) {
		_assetCategoryIds = assetCategoryIds;
	}

	public void setAssetTagNames(String[] assetTagNames) {
		_assetTagNames = assetTagNames;
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

	public void setGroupIds(long[] groupIds) {
		_groupIds = groupIds;
	}

	public void setIncludeLiveGroups(boolean includeLiveGroups) {
		_includeLiveGroups = includeLiveGroups;
	}

	public void setIncludeStagingGroups(boolean includeStagingGroups) {
		_includeStagingGroups = includeStagingGroups;
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

	public void setPortletIds(String[] portletIds) {
		_portletIds = portletIds;
	}

	public void setScopeStrict(boolean scopeStrict) {
		_scopeStrict = scopeStrict;
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

	private boolean _andSearch;
	private long[] _assetCategoryIds;
	private String[] _assetTagNames;
	private Map<String, Serializable> _attributes;
	private BooleanClause[] _booleanClauses;
	private long[] _categoryIds;
	private long _companyId;
	private int _end = QueryUtil.ALL_POS;
	private long[] _folderIds;
	private long[] _groupIds;
	private boolean _includeLiveGroups = true;
	private boolean _includeStagingGroups = true;
	private String _keywords;
	private long[] _nodeIds;
	private long _ownerUserId;
	private String[] _portletIds;
	private boolean _scopeStrict = true;
	private Sort[] _sorts;
	private int _start = QueryUtil.ALL_POS;
	private long _userId;

}