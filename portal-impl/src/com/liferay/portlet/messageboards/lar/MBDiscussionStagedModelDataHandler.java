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

package com.liferay.portlet.messageboards.lar;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Layout;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.messageboards.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class MBDiscussionStagedModelDataHandler
	extends BaseStagedModelDataHandler<MBDiscussion> {

	public static final String[] CLASS_NAMES = {MBDiscussion.class.getName()};

	@Override
	public void deleteStagedModel(MBDiscussion discussion) {
		MBDiscussionLocalServiceUtil.deleteMBDiscussion(discussion);
	}

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {

		MBDiscussion discussion = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (discussion != null) {
			deleteStagedModel(discussion);
		}
	}

	@Override
	public MBDiscussion fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return MBDiscussionLocalServiceUtil.fetchMBDiscussionByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<MBDiscussion> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return MBDiscussionLocalServiceUtil.getMBDiscussionsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<MBDiscussion>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(MBDiscussion discussion) {
		try {
			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
				discussion.getClassName(), discussion.getClassPK());

			return assetEntry.getTitleCurrentValue();
		}
		catch (Exception e) {
			return discussion.getUuid();
		}
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, MBDiscussion discussion)
		throws Exception {

		Element discussionElement = portletDataContext.getExportDataElement(
			discussion);

		portletDataContext.addClassedModel(
			discussionElement, ExportImportPathUtil.getModelPath(discussion),
			discussion);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, MBDiscussion discussion)
		throws Exception {

		long userId = portletDataContext.getUserId(discussion.getUserUuid());

		String className = discussion.getClassName();

		Map<Long, Long> relatedClassPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(className);

		long newClassPK = MapUtil.getLong(
			relatedClassPKs, discussion.getClassPK(), discussion.getClassPK());

		MBDiscussion existingDiscussion =
			MBDiscussionLocalServiceUtil.fetchDiscussion(
				discussion.getClassName(), newClassPK);

		if (existingDiscussion == null) {
			if (className.equals(Layout.class.getName()) &&
				PropsValues.LAYOUT_COMMENTS_ENABLED) {

				MBMessage rootMessage =
					MBMessageLocalServiceUtil.addDiscussionMessage(
						userId, discussion.getUserName(),
						portletDataContext.getScopeGroupId(), className,
						newClassPK, WorkflowConstants.ACTION_PUBLISH);

				existingDiscussion =
					MBDiscussionLocalServiceUtil.getThreadDiscussion(
						rootMessage.getThreadId());
			}
			else {
				throw new NoSuchDiscussionException();
			}
		}

		Map<Long, Long> discussionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBDiscussion.class);

		discussionIds.put(
			discussion.getDiscussionId(), existingDiscussion.getDiscussionId());

		Map<Long, Long> threadIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBThread.class);

		threadIds.put(
			discussion.getThreadId(), existingDiscussion.getThreadId());
	}

}