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

package com.liferay.portal.workflow.kaleo.runtime.internal.assignment;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.kaleo.KaleoTaskAssignmentFactory;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.assignment.TaskAssignmentSelector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {"assignee.class.name=com.liferay.portal.kernel.model.Role"},
	service = TaskAssignmentSelector.class
)
public class GroupAwareRoleTaskAssignmentSelector
	implements TaskAssignmentSelector {

	@Override
	public Collection<KaleoTaskAssignment> calculateTaskAssignments(
			KaleoTaskAssignment kaleoTaskAssignment,
			ExecutionContext executionContext)
		throws PortalException {

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		Role role = _roleLocalService.getRole(
			kaleoTaskAssignment.getAssigneeClassPK());

		return createKaleoTaskAssigments(kaleoInstanceToken.getGroupId(), role);
	}

	protected List<KaleoTaskAssignment> createKaleoTaskAssigments(
			long groupId, Role role)
		throws PortalException {

		List<Group> groups = new ArrayList<>();

		if (groupId != WorkflowConstants.DEFAULT_GROUP_ID) {
			Group group = _groupLocalService.getGroup(groupId);

			if (group.isOrganization()) {
				groups.addAll(getAncestorOrganizationGroups(group));
			}

			if (group.isSite()) {
				groups.addAll(getAncestorGroups(group));
			}

			if (group.isLayout()) {
				Group parentGroup = _groupLocalService.getGroup(
					group.getParentGroupId());

				groups.add(parentGroup);
			}

			groups.add(group);
		}

		return createKaleoTaskAssigments(role, groups);
	}

	protected List<KaleoTaskAssignment> createKaleoTaskAssigments(
			Role role, List<Group> groups)
		throws PortalException {

		List<KaleoTaskAssignment> kaleoTaskAssignments = new ArrayList<>();

		for (Group group : groups) {
			if (isValidAssignment(role, group)) {
				kaleoTaskAssignments.add(
					createKaleoTaskAssignment(role, group.getGroupId()));
			}
		}

		return kaleoTaskAssignments;
	}

	protected KaleoTaskAssignment createKaleoTaskAssignment(
		Role role, long groupId) {

		KaleoTaskAssignment kaleoTaskAssignment =
			_kaleoTaskAssignmentFactory.createKaleoTaskAssignment();

		kaleoTaskAssignment.setAssigneeClassName(Role.class.getName());
		kaleoTaskAssignment.setAssigneeClassPK(role.getRoleId());
		kaleoTaskAssignment.setGroupId(groupId);

		return kaleoTaskAssignment;
	}

	protected List<Group> getAncestorGroups(Group group)
		throws PortalException {

		List<Group> groups = new ArrayList<>();

		for (Group ancestorGroup : group.getAncestors()) {
			groups.add(ancestorGroup);
		}

		return groups;
	}

	protected List<Group> getAncestorOrganizationGroups(Group group)
		throws PortalException {

		List<Group> groups = new ArrayList<>();

		Organization organization = _organizationLocalService.getOrganization(
			group.getClassPK());

		for (Organization ancestorOrganization : organization.getAncestors()) {
			groups.add(ancestorOrganization.getGroup());
		}

		return groups;
	}

	protected boolean isValidAssignment(Role role, Group group)
		throws PortalException {

		if (role.getType() == RoleConstants.TYPE_REGULAR) {
			return true;
		}
		else if ((group != null) && group.isOrganization() &&
				 (role.getType() == RoleConstants.TYPE_ORGANIZATION)) {

			return true;
		}
		else if ((group != null) && group.isSite() &&
				 (role.getType() == RoleConstants.TYPE_SITE)) {

			return true;
		}

		return false;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private KaleoTaskAssignmentFactory _kaleoTaskAssignmentFactory;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}