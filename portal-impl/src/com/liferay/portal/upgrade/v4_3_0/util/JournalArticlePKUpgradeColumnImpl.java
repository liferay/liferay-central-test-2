/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;

import java.sql.Types;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalArticlePKUpgradeColumnImpl extends PKUpgradeColumnImpl {

	public JournalArticlePKUpgradeColumnImpl(
		UpgradeColumn companyIdColumn, UpgradeColumn groupIdColumn) {

		super("id_", new Integer(Types.VARCHAR), false);

		_companyIdColumn = companyIdColumn;
		_groupIdColumn = groupIdColumn;
		_journalArticleIdMapper = ValueMapperFactoryUtil.getValueMapper();
	}

	public Object getNewValue(Object oldValue) throws Exception {
		_resourcePrimKey = null;

		Object newValue = super.getNewValue(oldValue);

		String companyId = (String)_companyIdColumn.getOldValue();
		Long oldGroupId = (Long)_groupIdColumn.getOldValue();
		Long newGroupId = (Long)_groupIdColumn.getNewValue();
		String articleId = (String)oldValue;

		String oldIdValue =
			"{companyId=" + companyId + ", groupId=" + oldGroupId +
				", articleId=" + articleId + ", version=1.0}";

		_resourcePrimKey = new Long(JournalArticleResourceLocalServiceUtil.
			getArticleResourcePrimKey(newGroupId.longValue(), articleId));

		_journalArticleIdMapper.mapValue(oldIdValue, _resourcePrimKey);

		return newValue;
	}

	public ValueMapper getValueMapper() {
		return _journalArticleIdMapper;
	}

	public Long getResourcePrimKey() {
		return _resourcePrimKey;
	}

	private UpgradeColumn _companyIdColumn;
	private UpgradeColumn _groupIdColumn;
	private ValueMapper _journalArticleIdMapper;
	private Long _resourcePrimKey;

}