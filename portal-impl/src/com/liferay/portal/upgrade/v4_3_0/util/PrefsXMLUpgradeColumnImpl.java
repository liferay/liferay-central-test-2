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

import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public class PrefsXMLUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public PrefsXMLUpgradeColumnImpl(
		UpgradeColumn upgradePortletIdColumn, ValueMapper groupIdMapper,
		ValueMapper pollsQuestionIdMapper, ValueMapper wikiNodeIdMapper) {

		super("preferences");

		_upgradePortletIdColumn = upgradePortletIdColumn;
		_groupIdMapper = groupIdMapper;
		_pollsQuestionIdMapper = pollsQuestionIdMapper;
		_wikiNodeIdMapper = wikiNodeIdMapper;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String xml = (String)oldValue;

		String portletId = (String)_upgradePortletIdColumn.getOldValue();

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromDefaultXML(xml);

		processPreferences(portletId, portletPreferences);

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected void processPreferences(
			String portletId, PortletPreferences portletPreferences)
		throws Exception {

		// Portlet Setup

		String portletCSS = portletPreferences.getValue(
			"portlet-setup-css", null);

		if (Validator.isNotNull(portletCSS)) {
			portletPreferences.reset("portlet-setup-css");
		}

		// Journal Articles and Journal Content

		if (portletId.startsWith("62_INSTANCE_") ||
			portletId.startsWith("56_INSTANCE_")) {

			String groupId = portletPreferences.getValue("group-id", null);

			if (Validator.isNotNull(groupId)) {
				groupId = String.valueOf(_groupIdMapper.getNewValue(
					new Long(GetterUtil.getLong(groupId))));

				portletPreferences.setValue("group-id", groupId);
			}
		}

		// Polls Display

		else if (portletId.startsWith("59_INSTANCE_")) {
			String questionId = portletPreferences.getValue(
				"question-id", null);

			if (Validator.isNotNull(questionId)) {
				questionId = String.valueOf(_pollsQuestionIdMapper.getNewValue(
					new Long(GetterUtil.getLong(questionId))));

				portletPreferences.setValue("question-id", questionId);
			}
		}

		// Wiki Display

		else if (portletId.startsWith("54_INSTANCE_")) {
			String nodeId = portletPreferences.getValue("node-id", null);

			if (Validator.isNotNull(nodeId)) {
				nodeId = String.valueOf(_wikiNodeIdMapper.getNewValue(
					new Long(GetterUtil.getLong(nodeId))));

				portletPreferences.setValue("node-id", nodeId);
			}
		}
	}

	private ValueMapper _groupIdMapper;
	private ValueMapper _pollsQuestionIdMapper;
	private UpgradeColumn _upgradePortletIdColumn;
	private ValueMapper _wikiNodeIdMapper;

}