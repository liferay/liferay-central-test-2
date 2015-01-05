/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.sitesadmin.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;

import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class StagedGroupStagedModelDataHandler
	extends BaseStagedModelDataHandler<StagedGroup> {

	public static final String[] CLASS_NAMES = {StagedGroup.class.getName()};

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {

		throw new UnsupportedOperationException();
	}

	@Override
	public StagedGroup fetchStagedModelByUuidAndCompanyId(
		String uuid, long companyId) {

		return null;
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(StagedGroup stagedGroup) {
		return stagedGroup.getName();
	}

	@Override
	public void importMissingReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		if ((groupId == 0) || groupIds.containsKey(groupId)) {
			return;
		}

		Group existingGroup = fetchExistingGroup(
			portletDataContext, referenceElement);

		groupIds.put(groupId, existingGroup.getGroupId());
	}

	@Override
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		if ((groupId == 0) || groupIds.containsKey(groupId)) {
			return true;
		}

		Group existingGroup = fetchExistingGroup(
			portletDataContext, referenceElement);

		if (existingGroup == null) {
			return false;
		}

		groupIds.put(groupId, existingGroup.getGroupId());

		return true;
	}

	@Override
	protected void doExportStagedModel(
		PortletDataContext portletDataContext, StagedGroup stagedGroup) {
	}

	@Override
	protected void doImportStagedModel(
		PortletDataContext portletDataContext, StagedGroup stagedGroup) {
	}

	protected Group fetchExistingGroup(
		PortletDataContext portletDataContext, Element referenceElement) {

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));
		long liveGroupId = GetterUtil.getLong(
			referenceElement.attributeValue("live-group-id"));

		if ((groupId == 0) || (liveGroupId == 0)) {
			return null;
		}

		return fetchExistingGroup(portletDataContext, groupId, liveGroupId);
	}

	protected Group fetchExistingGroup(
		PortletDataContext portletDataContext, long groupId, long liveGroupId) {

		Group liveGroup = GroupLocalServiceUtil.fetchGroup(liveGroupId);

		if (liveGroup != null) {
			return liveGroup;
		}

		long existingGroupId = portletDataContext.getScopeGroupId();

		if (groupId == portletDataContext.getSourceCompanyGroupId()) {
			existingGroupId = portletDataContext.getCompanyGroupId();
		}
		else if (groupId == portletDataContext.getSourceGroupId()) {
			existingGroupId = portletDataContext.getGroupId();
		}

		// During remote staging, valid mappings are found when the reference's
		// group is properly staged. During local staging, valid mappings are
		// found when the references do not change between staging and live.

		return GroupLocalServiceUtil.fetchGroup(existingGroupId);
	}

}