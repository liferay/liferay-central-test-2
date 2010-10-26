/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either revision 2.1 of the License, or (at your option)
 * any later revision.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
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

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * The implementation of the revision local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portal.service.RevisionLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.portal.service.RevisionLocalServiceUtil} to access the revision local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.base.RevisionLocalServiceBaseImpl
 * @see com.liferay.portal.service.RevisionLocalServiceUtil
 */
public class LayoutRevisionLocalServiceImpl
	extends LayoutRevisionLocalServiceBaseImpl {

	public LayoutRevision addRevision(
			long branchId, long plid, long groupId, String name, String title,
			String description, String typeSettings, boolean iconImage,
			long iconImageId, String themeId, String colorSchemeId,
			String wapThemeId, String wapColorSchemeId, String css,
			boolean head, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userLocalService.getUserById(serviceContext.getUserId());

		long revisionId = counterLocalService.increment();

		LayoutRevision revision = layoutRevisionPersistence.create(revisionId);

		long parentRevisionId = LayoutRevisionConstants.DEFAULT_PARENT_REVISION_ID;

		LayoutRevision parentRevision = getParentRevision(
			branchId, plid, serviceContext);

		if (parentRevision != null) {
			parentRevisionId = parentRevision.getRevisionId();
		}

		Date now = new Date();

		revision.setGroupId(groupId);
		revision.setCompanyId(user.getCompanyId());
		revision.setUserId(user.getUserId());
		revision.setUserName(user.getFullName());
		revision.setCreateDate(serviceContext.getCreateDate(now));
		revision.setModifiedDate(serviceContext.getModifiedDate(now));
		revision.setBranchId(branchId);
		revision.setPlid(plid);
		revision.setParentRevisionId(parentRevisionId);
		revision.setHead(head);

		revision.setName(name);
		revision.setTitle(title);
		revision.setDescription(description);
		revision.setTypeSettings(typeSettings);

		if (iconImage) {
			revision.setIconImage(iconImage);
			revision.setIconImageId(iconImageId);
		}

		revision.setThemeId(themeId);
		revision.setColorSchemeId(colorSchemeId);
		revision.setWapThemeId(wapThemeId);
		revision.setWapColorSchemeId(wapColorSchemeId);
		revision.setCss(css);

		revision.setStatus(WorkflowConstants.STATUS_DRAFT);

		if (parentRevision != null) {
			copyPreferences(
				user.getCompanyId(), parentRevisionId, revision.getRevisionId(),
				serviceContext);
		}

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), groupId, user.getUserId(),
			Layout.class.getName(), revision.getRevisionId(), revision,
			serviceContext);

		return revision;
	}

	public void deleteRevision(long revisionId)
		throws PortalException, SystemException {

		LayoutRevision revision = layoutRevisionPersistence.findByPrimaryKey(revisionId);

		deleteRevision(revision);
	}

	public void deleteRevision(LayoutRevision revision)
		throws SystemException {

		List<PortletPreferences> preferences =
			portletPreferencesLocalService.getPortletPreferencesByPlid(
				revision.getRevisionId());

		for (PortletPreferences preference : preferences) {
			try {
				portletPreferencesLocalService.deletePortletPreferences(
					preference.getPortletPreferencesId());
			}
			catch (PortalException e) {
			}
		}

		layoutRevisionPersistence.remove(revision);
	}

	public void deleteRevisionsByBranch(long branchId)
		throws PortalException, SystemException {

		List<LayoutRevision> revisions = layoutRevisionPersistence.findByBranchId(
			branchId);

		for (LayoutRevision revision : revisions) {
			deleteRevision(revision);
		}
	}

	public void deleteRevisionsByLayout(long plid)
		throws PortalException, SystemException {

		for (LayoutRevision revision : getRevisions(plid)) {
			deleteRevision(revision);
		}
	}

	public void deleteRevisions(long branchId, long plid)
		throws PortalException, SystemException {

		for (LayoutRevision revision : getRevisions(branchId, plid)) {
			deleteRevision(revision);
		}
	}

	public LayoutRevision checkLatestRevision(
			long branchId, long plid, ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutRevision revision = null;
		List<LayoutRevision> revisions = Collections.EMPTY_LIST;

		LayoutBranch branch = layoutBranchPersistence.findByPrimaryKey(branchId);

		long revisionId = ParamUtil.getLong(serviceContext, "revisionId");

		if (revisionId > 0) {
			revision = layoutRevisionPersistence.findByPrimaryKey(revisionId);
		}

		if (revision == null) {
			revisions = layoutRevisionPersistence.findByB_P(branchId, plid, 0 , 1);

			if (!revisions.isEmpty()) {
				revision = revisions.get(0);
			}
		}

		if (revision == null) {
			Layout layout = layoutPersistence.findByPrimaryKey(plid);

			revision = layoutRevisionLocalService.addRevision(
				branch.getBranchId(), plid, layout.getGroupId(),
				layout.getName(), layout.getTitle(),
				layout.getDescription(), layout.getTypeSettings(),
				layout.getIconImage(), layout.getIconImageId(),
				layout.getThemeId(), layout.getColorSchemeId(),
				layout.getWapThemeId(), layout.getWapColorSchemeId(),
				layout.getCss(), false, serviceContext);

			if (!branch.isMaster()) {
				branch = layoutBranchLocalService.getMasterBranch(
					branch.getGroupId());

				revisions = layoutRevisionPersistence.findByB_P(
					branch.getBranchId(), plid, 0 , 1);

				if (revisions.isEmpty()) {
					layoutRevisionLocalService.addRevision(
						branch.getBranchId(), plid, layout.getGroupId(),
						layout.getName(), layout.getTitle(),
						layout.getDescription(), layout.getTypeSettings(),
						layout.getIconImage(), layout.getIconImageId(),
						layout.getThemeId(), layout.getColorSchemeId(),
						layout.getWapThemeId(), layout.getWapColorSchemeId(),
						layout.getCss(), false, serviceContext);
				}
			}
		}

		return revision;
	}

	public LayoutRevision getHeadRevision(long branchId, long plid)
		throws PortalException, SystemException {

		List<LayoutRevision> revisions = layoutRevisionPersistence.findByB_P_S(
			branchId, plid, WorkflowConstants.STATUS_APPROVED, 0, 1);

		if (!revisions.isEmpty()) {
			return revisions.get(0);
		}
		else {
			return null;
		}
	}

	public LayoutRevision getRevision(long revisionId)
		throws PortalException, SystemException {

		return layoutRevisionPersistence.findByPrimaryKey(revisionId);
	}

	public List<LayoutRevision> getRevisions(long plid)
		throws PortalException, SystemException {

		return layoutRevisionPersistence.findByPlid(plid);
	}

	public List<LayoutRevision> getRevisions(long branchId, long plid)
		throws PortalException, SystemException {

		return layoutRevisionPersistence.findByB_P(branchId, plid);
	}

	public List<LayoutRevision> getRevisions(long branchId, long plid, int status)
		throws PortalException, SystemException {

		return layoutRevisionPersistence.findByB_P_S(branchId, plid, status);
	}

	public LayoutRevision revertToRevision(long revisionId)
		throws PortalException, SystemException {

		LayoutRevision revision = layoutRevisionPersistence.findByPrimaryKey(revisionId);

		List<LayoutRevision> revisions = layoutRevisionPersistence.findByB_P(
			revision.getBranchId(), revision.getPlid());

		for (LayoutRevision curRevision : revisions) {
			if (curRevision.getRevisionId() > revision.getRevisionId()) {
				//deleteRevision(curRevision.getRevisionId());
			}
		}

		return revision;
	}

	public LayoutRevision updateRevision(
			long revisionId, String name, String title, String description,
			String typeSettings, boolean iconImage, long iconImageId,
			String themeId, String colorSchemeId, String wapThemeId,
			String wapColorSchemeId, String css, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(
			serviceContext.getUserId());

		LayoutRevision oldRevision = layoutRevisionPersistence.fetchByPrimaryKey(
			revisionId);

		Date now = new Date();

		LayoutRevision revision = layoutRevisionPersistence.create(revisionId);

		revision.setGroupId(oldRevision.getGroupId());
		revision.setCompanyId(oldRevision.getCompanyId());
		revision.setUserId(user.getUserId());
		revision.setUserName(user.getFullName());
		revision.setCreateDate(serviceContext.getCreateDate(now));
		revision.setModifiedDate(serviceContext.getModifiedDate(now));
		revision.setBranchId(oldRevision.getBranchId());
		revision.setPlid(oldRevision.getPlid());
		revision.setParentRevisionId(oldRevision.getRevisionId());
		revision.setHead(false);

		revision.setName(name);
		revision.setTitle(title);
		revision.setDescription(description);
		revision.setTypeSettings(typeSettings);

		if (iconImage) {
			revision.setIconImage(iconImage);
			revision.setIconImageId(iconImageId);
		}

		revision.setThemeId(themeId);
		revision.setColorSchemeId(colorSchemeId);
		revision.setWapThemeId(wapThemeId);
		revision.setWapColorSchemeId(wapColorSchemeId);
		revision.setCss(css);

		layoutRevisionPersistence.update(revision, false);

		copyPreferences(
			user.getCompanyId(), revision.getParentRevisionId(),
			revision.getRevisionId(), serviceContext);

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			revision.getCompanyId(), revision.getGroupId(),
			serviceContext.getUserId(), Layout.class.getName(),
			revision.getRevisionId(), revision, serviceContext);

		return revision;
	}

	public LayoutRevision updateStatus(
			long userId, long revisionId, int status,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		LayoutRevision revision = layoutRevisionPersistence.findByPrimaryKey(revisionId);

		revision.setModifiedDate(serviceContext.getModifiedDate(now));
		revision.setStatus(status);
		revision.setStatusByUserId(user.getUserId());
		revision.setStatusByUserName(user.getFullName());
		revision.setStatusDate(serviceContext.getModifiedDate(now));

		if (status == WorkflowConstants.STATUS_APPROVED) {
			revision.setHead(true);

			List<LayoutRevision> revisions = layoutRevisionPersistence.findByB_P(
				revision.getBranchId(), revision.getPlid());

			for (LayoutRevision curRevision : revisions) {
				if (curRevision.getRevisionId() != revision.getRevisionId()) {
					curRevision.setHead(false);

					layoutRevisionPersistence.update(curRevision, false);
				}
			}
		}
		else {
			revision.setHead(false);

			List<LayoutRevision> revisions = layoutRevisionPersistence.findByB_P_S(
				revision.getBranchId(), revision.getPlid(),
				WorkflowConstants.STATUS_APPROVED);

			for (LayoutRevision curRevision : revisions) {
				if (curRevision.getRevisionId() != revision.getRevisionId()) {
					curRevision.setHead(true);

					layoutRevisionPersistence.update(curRevision, false);

					break;
				}
			}
		}

		layoutRevisionPersistence.update(revision, false);

		return revision;
	}

	protected void copyPreferences(
			long companyId, long plid, long revisionId,
			ServiceContext serviceContext)
		throws SystemException {

		List<PortletPreferences> preferences =
			portletPreferencesLocalService.getPortletPreferencesByPlid(
				plid);

		for (PortletPreferences preference : preferences) {
			portletPreferencesLocalService.addPortletPreferences(
				companyId, preference.getOwnerId(), preference.getOwnerType(),
				revisionId, preference.getPortletId(), null,
				preference.getPreferences());
		}
	}

	protected LayoutRevision getParentRevision(
			long branchId, long plid, ServiceContext serviceContext)
		throws PortalException, SystemException  {

		long parentRevisionId = ParamUtil.getLong(
			serviceContext, "parentRevisionId");

		LayoutRevision parentRevision = null;

		if (parentRevisionId > 0) {
			parentRevision = layoutRevisionPersistence.findByPrimaryKey(
				parentRevisionId);
		}

		if (parentRevision == null) {
			List<LayoutRevision> revisions = layoutRevisionPersistence.findByB_P(
				branchId, plid, 0, 1);

			if (!revisions.isEmpty()) {
				parentRevision = revisions.get(0);
			}
		}

		return parentRevision;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutRevisionLocalServiceImpl.class);

}