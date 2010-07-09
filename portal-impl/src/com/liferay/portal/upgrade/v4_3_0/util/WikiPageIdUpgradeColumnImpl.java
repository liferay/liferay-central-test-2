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

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.kernel.upgrade.util.ValueMapperFactoryUtil;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class WikiPageIdUpgradeColumnImpl extends PKUpgradeColumnImpl {

	public WikiPageIdUpgradeColumnImpl(
		UpgradeColumn nodeIdColumn, UpgradeColumn titleColumn) {

		super("pageId", false);

		_nodeIdColumn = nodeIdColumn;
		_titleColumn = titleColumn;
		_wikiPageIdMapper = ValueMapperFactoryUtil.getValueMapper();
	}

	public Object getNewValue(Object oldValue) throws Exception {
		_resourcePrimKey = null;

		Object newValue = super.getNewValue(oldValue);

		Long oldNodeId = (Long)_nodeIdColumn.getOldValue();
		Long newNodeId = (Long)_nodeIdColumn.getNewValue();
		String title = (String)_titleColumn.getOldValue();

		String oldPageIdValue =
			"{nodeId=" + oldNodeId + ", title=" + title + ", version=1.0}";

		_resourcePrimKey = new Long(WikiPageResourceLocalServiceUtil.
			getPageResourcePrimKey(newNodeId.longValue(), title));

		_wikiPageIdMapper.mapValue(oldPageIdValue, _resourcePrimKey);

		return newValue;
	}

	public ValueMapper getValueMapper() {
		return _wikiPageIdMapper;
	}

	public Long getResourcePrimKey() {
		return _resourcePrimKey;
	}

	private UpgradeColumn _nodeIdColumn;
	private UpgradeColumn _titleColumn;
	private ValueMapper _wikiPageIdMapper;
	private Long _resourcePrimKey;

}