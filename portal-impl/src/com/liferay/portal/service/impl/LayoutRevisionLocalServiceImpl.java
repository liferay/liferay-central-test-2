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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchLayoutRevisionException;
import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutRevisionConstants;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutRevisionLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class LayoutRevisionLocalServiceImpl
	extends LayoutRevisionLocalServiceBaseImpl {

	public LayoutRevision addLayoutRevision(
			long userId, long layoutBranchId, long parentLayoutRevisionId,
			boolean head, long plid, String name, String title,
			String description, String typeSettings, boolean iconImage,
			long iconImageId, String themeId, String colorSchemeId,
			String wapThemeId, String wapColorSchemeId, String css,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Layout revision

		User user = userLocalService.getUserById(userId);
		LayoutBranch layoutBranch = layoutBranchPersistence.findByPrimaryKey(
			layoutBranchId);
		parentLayoutRevisionId = getParentLayoutRevisionId(
			layoutBranchId, parentLayoutRevisionId, plid);
		Date now = new Date();

		long layoutRevisionId = counterLocalService.increment();

		LayoutRevision layoutRevision = layoutRevisionPersistence.create(
			layoutRevisionId);

		layoutRevision.setGroupId(layoutBranch.getGroupId());
		layoutRevision.setCompanyId(user.getCompanyId());
		layoutRevision.setUserId(user.getUserId());
		layoutRevision.setUserName(user.getFullName());
		layoutRevision.setCreateDate(serviceContext.getCreateDate(now));
		layoutRevision.setModifiedDate(serviceContext.getModifiedDate(now));
		layoutRevision.setLayoutBranchId(layoutBranchId);
		layoutRevision.setParentLayoutRevisionId(parentLayoutRevisionId);
		layoutRevision.setHead(head);
		layoutRevision.setPlid(plid);
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

		layoutRevisionPersistence.update(layoutRevision, false);

		// Portlet preferences

		if (parentLayoutRevisionId !=
				LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID) {

			copyPortletPreferences(layoutRevision, serviceContext);
		}

		// Workflow

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), layoutRevision.getGroupId(), user.getUserId(),
			Layout.class.getName(), layoutRevision.getLayoutRevisionId(),
			layoutRevision, serviceContext);

		return layoutRevision;
	}

	public LayoutRevision checkLatestLayoutRevision(
			long layoutRevisionId, long layoutBranchId, long plid,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutBranch layoutBranch = layoutBranchPersistence.findByPrimaryKey(
			layoutBranchId);

		LayoutRevision layoutRevision = null;

		if (layoutRevisionId > 0) {
			layoutRevision = layoutRevisionPersistence.fetchByPrimaryKey(
				layoutRevisionId);
		}

		List<LayoutRevision> layoutRevisions = null;

		if (layoutRevision == null) {
			layoutRevisions = layoutRevisionPersistence.findByL_P(
				layoutBranchId, plid, 0 , 1);

			if (!layoutRevisions.isEmpty()) {
				layoutRevision = layoutRevisions.get(0);
			}
		}

		if (layoutRevision != null) {
			return layoutRevision;
		}

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		layoutRevision = addLayoutRevision(
			layoutBranch.getUserId(), layoutBranch.getLayoutBranchId(),
			LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, false,
			plid, layout.getName(), layout.getTitle(), layout.getDescription(),
			layout.getTypeSettings(), layout.isIconImage(),
			layout.getIconImageId(), layout.getThemeId(),
			layout.getColorSchemeId(), layout.getWapThemeId(),
			layout.getWapColorSchemeId(), layout.getCss(), serviceContext);

		if (layoutBranch.isMaster()) {
			return layoutRevision;
		}

		layoutBranch = layoutBranchLocalService.getMasterLayoutBranch(
			layoutBranch.getGroupId());

		layoutRevisions = layoutRevisionPersistence.findByL_P(
			layoutBranch.getLayoutBranchId(), plid, 0 , 1);

		if (layoutRevisions.isEmpty()) {
			addLayoutRevision(
				layoutBranch.getUserId(), layoutBranch.getLayoutBranchId(),
				LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID,
				false, plid, layout.getName(), layout.getTitle(),
				layout.getDescription(), layout.getTypeSettings(),
				layout.isIconImage(), layout.getIconImageId(),
				layout.getThemeId(), layout.getColorSchemeId(),
				layout.getWapThemeId(), layout.getWapColorSchemeId(),
				layout.getCss(), serviceContext);
		}

		return layoutRevision;
	}

	public void deleteLayoutBranchLayoutRevisions(long layoutBranchId)
		throws PortalException, SystemException {

		List<LayoutRevision> layoutRevisions =
			layoutRevisionPersistence.findByLayoutBranchId(
				layoutBranchId);

		for (LayoutRevision layoutRevision : layoutRevisions) {
			deleteLayoutRevision(layoutRevision);
		}
	}

	public void deleteLayoutLayoutRevisions(long plid)
		throws PortalException, SystemException {

		for (LayoutRevision layoutRevision : getLayoutRevisions(plid)) {
			deleteLayoutRevision(layoutRevision);
		}
	}

	public void deleteLayoutRevision(LayoutRevision layoutRevision)
		throws PortalException, SystemException {

		List<PortletPreferences> portletPreferences =
			portletPreferencesLocalService.getPortletPreferencesByPlid(
				layoutRevision.getLayoutRevisionId());

		for (PortletPreferences portletPreference : portletPreferences) {
			try {
				portletPreferencesLocalService.deletePortletPreferences(
					portletPreference.getPortletPreferencesId());
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

		deleteLayoutRevision(layoutRevision);
	}

	public void deleteLayoutRevisions(long layoutBranchId, long plid)
		throws PortalException, SystemException {

		for (LayoutRevision layoutRevision : getLayoutRevisions(
				layoutBranchId, plid)) {

			deleteLayoutRevision(layoutRevision);
		}
	}

	public LayoutRevision getHeadLayoutRevision(long layoutBranchId, long plid)
		throws PortalException, SystemException {

		List<LayoutRevision> layoutRevisions =
			layoutRevisionPersistence.findByL_P_S(
				layoutBranchId, plid, WorkflowConstants.STATUS_APPROVED, 0, 1);

		if (!layoutRevisions.isEmpty()) {
			return layoutRevisions.get(0);
		}
		else {
			throw new NoSuchLayoutRevisionException();
		}
	}

	public LayoutRevision getLayoutRevision(long layoutRevisionId)
		throws PortalException, SystemException {

		return layoutRevisionPersistence.findByPrimaryKey(layoutRevisionId);
	}

	public List<LayoutRevision> getLayoutRevisions(long plid)
		throws SystemException {

		return layoutRevisionPersistence.findByPlid(plid);
	}

	public List<LayoutRevision> getLayoutRevisions(
			long layoutBranchId, long plid)
		throws SystemException {

		return layoutRevisionPersistence.findByL_P(layoutBranchId, plid);
	}

	public List<LayoutRevision> getLayoutRevisions(
			long layoutBranchId, long plid, int status)
		throws SystemException {

		return layoutRevisionPersistence.findByL_P_S(
			layoutBranchId, plid, status);
	}

	public LayoutRevision revertToLayoutRevision(long layoutRevisionId)
		throws PortalException, SystemException {

		LayoutRevision layoutRevision =
			layoutRevisionPersistence.findByPrimaryKey(layoutRevisionId);

		List<LayoutRevision> layoutRevisions =
			layoutRevisionPersistence.findByL_P(
				layoutRevision.getLayoutBranchId(), layoutRevision.getPlid());

		for (LayoutRevision curLayoutRevision : layoutRevisions) {
			if (curLayoutRevision.getLayoutRevisionId() >
					layoutRevision.getLayoutRevisionId()) {

				deleteLayoutRevision(curLayoutRevision.getLayoutRevisionId());
			}
		}

		return layoutRevision;
	}

	public LayoutRevision updateLayoutRevision(
			long userId, long layoutRevisionId, String name, String title,
			String description, String typeSettings, boolean iconImage,
			long iconImageId, String themeId, String colorSchemeId,
			String wapThemeId, String wapColorSchemeId, String css,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Layout revision

		User user = userPersistence.findByPrimaryKey(userId);
		LayoutRevision layoutRevision =
			layoutRevisionPersistence.findByPrimaryKey(layoutRevisionId);
		Date now = new Date();

		LayoutRevision newLayoutRevision = layoutRevisionPersistence.create(
			layoutRevisionId);

		newLayoutRevision.setGroupId(layoutRevision.getGroupId());
		newLayoutRevision.setCompanyId(layoutRevision.getCompanyId());
		newLayoutRevision.setUserId(user.getUserId());
		newLayoutRevision.setUserName(user.getFullName());
		newLayoutRevision.setCreateDate(serviceContext.getCreateDate(now));
		newLayoutRevision.setModifiedDate(serviceContext.getModifiedDate(now));
		newLayoutRevision.setLayoutBranchId(layoutRevision.getLayoutBranchId());
		newLayoutRevision.setParentLayoutRevisionId(
			layoutRevision.getLayoutRevisionId());
		newLayoutRevision.setHead(false);
		newLayoutRevision.setPlid(layoutRevision.getPlid());
		newLayoutRevision.setName(name);
		newLayoutRevision.setTitle(title);
		newLayoutRevision.setDescription(description);
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

		layoutRevisionPersistence.update(newLayoutRevision, false);

		// Portlet preferences

		copyPortletPreferences(newLayoutRevision, serviceContext);

		// Workflow

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			newLayoutRevision.getCompanyId(), newLayoutRevision.getGroupId(),
			userId, Layout.class.getName(),
			newLayoutRevision.getLayoutRevisionId(), newLayoutRevision,
			serviceContext);

		return newLayoutRevision;
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
					layoutRevision.getLayoutBranchId(),
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
					layoutRevision.getLayoutBranchId(),
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
			LayoutRevision layoutRevision, ServiceContext serviceContext)
		throws SystemException {

		List<PortletPreferences> portletPreferences =
			portletPreferencesLocalService.getPortletPreferencesByPlid(
				layoutRevision.getPlid());

		for (PortletPreferences portletPreference : portletPreferences) {
			portletPreferencesLocalService.addPortletPreferences(
				layoutRevision.getCompanyId(), portletPreference.getOwnerId(),
				portletPreference.getOwnerType(),
				layoutRevision.getLayoutRevisionId(),
				portletPreference.getPortletId(), null,
				portletPreference.getPreferences());
		}
	}

	protected long getParentLayoutRevisionId(
			long layoutBranchId, long parentLayoutRevisionId, long plid)
		throws SystemException {

		LayoutRevision parentLayoutRevision = null;

		if (parentLayoutRevisionId > 0) {
			parentLayoutRevision = layoutRevisionPersistence.fetchByPrimaryKey(
				parentLayoutRevisionId);
		}

		if (parentLayoutRevision == null) {
			List<LayoutRevision> layoutRevisions =
				layoutRevisionPersistence.findByL_P(
					layoutBranchId, plid, 0, 1);

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