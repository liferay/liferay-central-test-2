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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.staging.Staging;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutRevisionConstants;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutRevisionLocalServiceBaseImpl;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class LayoutRevisionLocalServiceImpl
	extends LayoutRevisionLocalServiceBaseImpl {

	public LayoutRevision addLayoutRevision(
			long userId, long layoutSetBranchId, long parentLayoutRevisionId,
			boolean head, long plid, boolean privateLayout, String name,
			String title, String description, String typeSettings,
			boolean iconImage, long iconImageId, String themeId,
			String colorSchemeId, String wapThemeId, String wapColorSchemeId,
			String css, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Layout revision

		User user = userLocalService.getUserById(userId);
		LayoutSetBranch layoutSetBranch =
			layoutSetBranchPersistence.findByPrimaryKey(layoutSetBranchId);
		parentLayoutRevisionId = getParentLayoutRevisionId(
			layoutSetBranchId, parentLayoutRevisionId, plid);
		Date now = new Date();

		long layoutRevisionId = counterLocalService.increment();

		LayoutRevision layoutRevision = layoutRevisionPersistence.create(
			layoutRevisionId);

		layoutRevision.setGroupId(layoutSetBranch.getGroupId());
		layoutRevision.setCompanyId(user.getCompanyId());
		layoutRevision.setUserId(user.getUserId());
		layoutRevision.setUserName(user.getFullName());
		layoutRevision.setCreateDate(serviceContext.getCreateDate(now));
		layoutRevision.setModifiedDate(serviceContext.getModifiedDate(now));
		layoutRevision.setLayoutSetBranchId(layoutSetBranchId);
		layoutRevision.setParentLayoutRevisionId(parentLayoutRevisionId);
		layoutRevision.setHead(head);
		layoutRevision.setPlid(plid);
		layoutRevision.setPrivateLayout(privateLayout);
		layoutRevision.setName(name);
		layoutRevision.setTitle(title);
		layoutRevision.setDescription(description);
		layoutRevision.setTypeSettings(typeSettings);

		if (iconImage) {
			layoutRevision.setIconImage(iconImage);
			layoutRevision.setIconImageId(iconImageId);
		}

		layoutRevision.setThemeId(themeId);
		layoutRevision.setColorSchemeId(colorSchemeId);
		layoutRevision.setWapThemeId(wapThemeId);
		layoutRevision.setWapColorSchemeId(wapColorSchemeId);
		layoutRevision.setCss(css);
		layoutRevision.setStatus(WorkflowConstants.STATUS_DRAFT);
		layoutRevision.setStatusDate(serviceContext.getModifiedDate(now));

		layoutRevision.setStatus(WorkflowConstants.STATUS_DRAFT);

		layoutRevisionPersistence.update(layoutRevision, false);

		// Portlet preferences

		if (parentLayoutRevisionId ==
				LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID) {

			parentLayoutRevisionId = layoutRevision.getPlid();
		}

		copyPortletPreferences(
			layoutRevision, parentLayoutRevisionId, serviceContext);

		// Workflow

		serviceContext.setWorkflowAction(WorkflowConstants.STATUS_DRAFT);

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), layoutRevision.getGroupId(), user.getUserId(),
			LayoutRevision.class.getName(),
			layoutRevision.getLayoutRevisionId(), layoutRevision,
			serviceContext);

		return layoutRevision;
	}

	public LayoutRevision checkLatestLayoutRevision(
			long layoutRevisionId, long layoutSetBranchId, long plid,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutSetBranch layoutSetBranch =
			layoutSetBranchPersistence.findByPrimaryKey(layoutSetBranchId);

		LayoutRevision layoutRevision = null;

		if (layoutRevisionId > 0) {
			layoutRevision = layoutRevisionPersistence.fetchByPrimaryKey(
				layoutRevisionId);
		}

		List<LayoutRevision> layoutRevisions = null;

		if (layoutRevision == null) {
			layoutRevisions = layoutRevisionPersistence.findByL_P(
				layoutSetBranchId, plid, 0 , 1);

			if (!layoutRevisions.isEmpty()) {
				layoutRevision = layoutRevisions.get(0);
			}
		}

		if (layoutRevision != null) {
			return layoutRevision;
		}

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		layoutRevision = addLayoutRevision(
			layoutSetBranch.getUserId(), layoutSetBranch.getLayoutSetBranchId(),
			LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, false,
			plid, layout.getPrivateLayout(), layout.getName(),
			layout.getTitle(), layout.getDescription(),
			layout.getTypeSettings(), layout.isIconImage(),
			layout.getIconImageId(), layout.getThemeId(),
			layout.getColorSchemeId(), layout.getWapThemeId(),
			layout.getWapColorSchemeId(), layout.getCss(), serviceContext);

		if (layoutSetBranch.isMaster()) {
			return layoutRevision;
		}

		layoutSetBranch = layoutSetBranchLocalService.getMasterLayoutSetBranch(
			layoutSetBranch.getGroupId(), layoutSetBranch.getPrivateLayout());

		layoutRevisions = layoutRevisionPersistence.findByL_P(
			layoutSetBranch.getLayoutSetBranchId(), plid, 0 , 1);

		if (layoutRevisions.isEmpty()) {
			addLayoutRevision(
				layoutSetBranch.getUserId(),
				layoutSetBranch.getLayoutSetBranchId(),
				LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID,
				false, plid, layout.getPrivateLayout(), layout.getName(),
				layout.getTitle(), layout.getDescription(),
				layout.getTypeSettings(), layout.isIconImage(),
				layout.getIconImageId(), layout.getThemeId(),
				layout.getColorSchemeId(), layout.getWapThemeId(),
				layout.getWapColorSchemeId(), layout.getCss(), serviceContext);
		}

		return layoutRevision;
	}

	public void deleteLayoutSetBranchLayoutRevisions(long layoutSetBranchId)
		throws PortalException, SystemException {

		List<LayoutRevision> layoutRevisions =
			layoutRevisionPersistence.findByLayoutSetBranchId(
				layoutSetBranchId);

		for (LayoutRevision layoutRevision : layoutRevisions) {
			layoutRevisionLocalService.deleteLayoutRevision(layoutRevision);
		}
	}

	public void deleteLayoutLayoutRevisions(long plid)
		throws PortalException, SystemException {

		for (LayoutRevision layoutRevision : getLayoutRevisions(plid)) {
			layoutRevisionLocalService.deleteLayoutRevision(layoutRevision);
		}
	}

	public void deleteLayoutRevision(LayoutRevision layoutRevision)
		throws PortalException, SystemException {

		if (layoutRevision.hasChildren()) {
			for (LayoutRevision curLayoutRevision :
					layoutRevision.getChildren()) {

				curLayoutRevision.setParentLayoutRevisionId(
					layoutRevision.getParentLayoutRevisionId());

				layoutRevisionPersistence.update(curLayoutRevision, false);
			}
		}

		List<PortletPreferences> portletPreferencesList =
			portletPreferencesLocalService.getPortletPreferencesByPlid(
				layoutRevision.getLayoutRevisionId());

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			try {
				portletPreferencesLocalService.deletePortletPreferences(
					portletPreferences.getPortletPreferencesId());
			}
			catch (NoSuchPortletPreferencesException nsppe) {
			}
		}

		layoutRevisionPersistence.remove(layoutRevision);
	}

	public void deleteLayoutRevision(long layoutRevisionId)
		throws PortalException, SystemException {

		LayoutRevision layoutRevision =
			layoutRevisionPersistence.findByPrimaryKey(layoutRevisionId);

		layoutRevisionLocalService.deleteLayoutRevision(layoutRevision);
	}

	public void deleteLayoutRevisions(long layoutSetBranchId, long plid)
		throws PortalException, SystemException {

		for (LayoutRevision layoutRevision : getLayoutRevisions(
				layoutSetBranchId, plid)) {

			layoutRevisionLocalService.deleteLayoutRevision(layoutRevision);
		}
	}

	public LayoutRevision getLayoutRevision(long layoutRevisionId)
		throws PortalException, SystemException {

		return layoutRevisionPersistence.findByPrimaryKey(layoutRevisionId);
	}

	public LayoutRevision getLayoutRevision(
			long layoutSetBranchId, long plid, boolean head)
		throws PortalException, SystemException {

		return layoutRevisionPersistence.findByL_H_P(
			layoutSetBranchId, head, plid);
	}

	public List<LayoutRevision> getLayoutRevisions(long plid)
		throws SystemException {

		return layoutRevisionPersistence.findByPlid(plid);
	}

	public List<LayoutRevision> getLayoutRevisions(
			long layoutSetBranchId, long plid)
		throws SystemException {

		return layoutRevisionPersistence.findByL_P(layoutSetBranchId, plid);
	}

	public List<LayoutRevision> getLayoutRevisions(
			long layoutSetBranchId, long plid, int status)
		throws SystemException {

		return layoutRevisionPersistence.findByL_P_S(
			layoutSetBranchId, plid, status);
	}

	public List<LayoutRevision> getLayoutRevisions(
			long layoutSetBranchId, long parentLayoutRevisionId, long plid)
		throws SystemException {

		return layoutRevisionPersistence.findByL_P_P(
			layoutSetBranchId, parentLayoutRevisionId, plid);
	}

	public List<LayoutRevision> getLayoutRevisions(
			long layoutSetBranchId, long parentLayoutRevision, long plid,
			int start, int end, OrderByComparator obc)
		throws SystemException {

		return layoutRevisionPersistence.findByL_P_P(
			layoutSetBranchId, parentLayoutRevision, plid, start, end, obc);
	}

	public int getLayoutRevisionsCount(
			long layoutSetBranchId, long parentLayoutRevision, long plid)
		throws SystemException {

		return layoutRevisionPersistence.countByL_P_P(
			layoutSetBranchId, parentLayoutRevision, plid);
	}

	public LayoutRevision updateLayoutRevision(
			long userId, long layoutRevisionId, String name, String title,
			String description, String keywords, String robots,
			String typeSettings, boolean iconImage, long iconImageId,
			String themeId, String colorSchemeId, String wapThemeId,
			String wapColorSchemeId, String css, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Layout revision

		User user = userPersistence.findByPrimaryKey(userId);

		LayoutRevision layoutRevision =
			layoutRevisionPersistence.findByPrimaryKey(layoutRevisionId);

		Date now = new Date();

		long newLayoutRevisionId = counterLocalService.increment();

		LayoutRevision newLayoutRevision = layoutRevisionPersistence.create(
			newLayoutRevisionId);

		newLayoutRevision.setGroupId(layoutRevision.getGroupId());
		newLayoutRevision.setCompanyId(layoutRevision.getCompanyId());
		newLayoutRevision.setUserId(user.getUserId());
		newLayoutRevision.setUserName(user.getFullName());
		newLayoutRevision.setCreateDate(serviceContext.getCreateDate(now));
		newLayoutRevision.setModifiedDate(serviceContext.getModifiedDate(now));
		newLayoutRevision.setLayoutSetBranchId(
			layoutRevision.getLayoutSetBranchId());
		newLayoutRevision.setParentLayoutRevisionId(
			layoutRevision.getLayoutRevisionId());
		newLayoutRevision.setHead(false);
		newLayoutRevision.setPlid(layoutRevision.getPlid());
		newLayoutRevision.setPrivateLayout(layoutRevision.getPrivateLayout());
		newLayoutRevision.setName(name);
		newLayoutRevision.setTitle(title);
		newLayoutRevision.setDescription(description);
		newLayoutRevision.setKeywords(keywords);
		newLayoutRevision.setRobots(robots);
		newLayoutRevision.setTypeSettings(typeSettings);

		if (iconImage) {
			newLayoutRevision.setIconImage(iconImage);
			newLayoutRevision.setIconImageId(iconImageId);
		}

		newLayoutRevision.setThemeId(themeId);
		newLayoutRevision.setColorSchemeId(colorSchemeId);
		newLayoutRevision.setWapThemeId(wapThemeId);
		newLayoutRevision.setWapColorSchemeId(wapColorSchemeId);
		newLayoutRevision.setCss(css);
		newLayoutRevision.setStatus(WorkflowConstants.STATUS_DRAFT);
		newLayoutRevision.setStatusDate(serviceContext.getModifiedDate(now));

		layoutRevisionPersistence.update(newLayoutRevision, false);

		// Portlet preferences

		copyPortletPreferences(
			newLayoutRevision, newLayoutRevision.getParentLayoutRevisionId(),
			serviceContext);

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				newLayoutRevision.getCompanyId(), userId,
				serviceContext.isSignedIn());

		portalPreferences.setValue(
			Staging.class.getName(), LayoutRevisionConstants.encodeKey(
				newLayoutRevision.getLayoutSetBranchId(),
				newLayoutRevision.getPlid()),
			String.valueOf(newLayoutRevision.getLayoutRevisionId()));

		// Workflow

		serviceContext.setWorkflowAction(WorkflowConstants.STATUS_DRAFT);

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			newLayoutRevision.getCompanyId(), newLayoutRevision.getGroupId(),
			userId, LayoutRevision.class.getName(),
			newLayoutRevision.getLayoutRevisionId(), newLayoutRevision,
			serviceContext);

		return newLayoutRevision;
	}

	public LayoutRevision updateMajor(long layoutRevisionId, boolean major)
		throws PortalException, SystemException {

		LayoutRevision layoutRevision =
			layoutRevisionPersistence.findByPrimaryKey(layoutRevisionId);

		long parentLayoutRevisionId =
			layoutRevision.getParentLayoutRevisionId();

		boolean forks = false;

		while (parentLayoutRevisionId !=
					LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID) {

			LayoutRevision parentLayoutRevision =
				layoutRevisionPersistence.findByPrimaryKey(
					parentLayoutRevisionId);

			if (parentLayoutRevision.isMajor()) {
				break;
			}

			parentLayoutRevisionId =
				parentLayoutRevision.getParentLayoutRevisionId();

			if (parentLayoutRevision.getChildren().size() > 1) {
				forks = true;
			}

			if (!forks) {
				layoutRevisionLocalService.deleteLayoutRevision(
					parentLayoutRevision);
			}
		}

		layoutRevision.setParentLayoutRevisionId(parentLayoutRevisionId);
		layoutRevision.setMajor(major);

		layoutRevisionPersistence.update(layoutRevision, false);

		return layoutRevision;
	}

	public LayoutRevision updateStatus(
			long userId, long layoutRevisionId, int status,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		LayoutRevision layoutRevision =
			layoutRevisionPersistence.findByPrimaryKey(layoutRevisionId);

		layoutRevision.setModifiedDate(serviceContext.getModifiedDate(now));
		layoutRevision.setStatus(status);
		layoutRevision.setStatusByUserId(user.getUserId());
		layoutRevision.setStatusByUserName(user.getFullName());
		layoutRevision.setStatusDate(serviceContext.getModifiedDate(now));

		if (status == WorkflowConstants.STATUS_APPROVED) {
			layoutRevision.setHead(true);

			List<LayoutRevision> layoutRevisions =
				layoutRevisionPersistence.findByL_P(
					layoutRevision.getLayoutSetBranchId(),
					layoutRevision.getPlid());

			for (LayoutRevision curLayoutRevision : layoutRevisions) {
				if (curLayoutRevision.getLayoutRevisionId() !=
						layoutRevision.getLayoutRevisionId()) {

					curLayoutRevision.setHead(false);

					layoutRevisionPersistence.update(curLayoutRevision, false);
				}
			}
		}
		else {
			layoutRevision.setHead(false);

			List<LayoutRevision> layoutRevisions =
				layoutRevisionPersistence.findByL_P_S(
					layoutRevision.getLayoutSetBranchId(),
					layoutRevision.getPlid(),
					WorkflowConstants.STATUS_APPROVED);

			for (LayoutRevision curLayoutRevision : layoutRevisions) {
				if (curLayoutRevision.getLayoutRevisionId() !=
						layoutRevision.getLayoutRevisionId()) {

					curLayoutRevision.setHead(true);

					layoutRevisionPersistence.update(curLayoutRevision, false);

					break;
				}
			}
		}

		layoutRevisionPersistence.update(layoutRevision, false);

		return layoutRevision;
	}

	protected void copyPortletPreferences(
			LayoutRevision layoutRevision, long parentLayoutRevisionId,
			ServiceContext serviceContext)
		throws SystemException {

		List<PortletPreferences> portletPreferencesList =
			portletPreferencesLocalService.getPortletPreferencesByPlid(
				parentLayoutRevisionId);

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			portletPreferencesLocalService.addPortletPreferences(
				layoutRevision.getCompanyId(), portletPreferences.getOwnerId(),
				portletPreferences.getOwnerType(),
				layoutRevision.getLayoutRevisionId(),
				portletPreferences.getPortletId(), null,
				portletPreferences.getPreferences());
		}
	}

	protected long getParentLayoutRevisionId(
			long layoutSetBranchId, long parentLayoutRevisionId, long plid)
		throws SystemException {

		LayoutRevision parentLayoutRevision = null;

		if (parentLayoutRevisionId > 0) {
			parentLayoutRevision = layoutRevisionPersistence.fetchByPrimaryKey(
				parentLayoutRevisionId);
		}

		if (parentLayoutRevision == null) {
			List<LayoutRevision> layoutRevisions =
				layoutRevisionPersistence.findByL_P(
					layoutSetBranchId, plid, 0, 1);

			if (!layoutRevisions.isEmpty()) {
				parentLayoutRevision = layoutRevisions.get(0);
			}
		}

		if (parentLayoutRevision != null) {
			return parentLayoutRevision.getParentLayoutRevisionId();
		}

		return LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID;
	}

}