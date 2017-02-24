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

package com.liferay.portal.workflow.kaleo.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.definition.Definition;
import com.liferay.portal.workflow.kaleo.exception.NoSuchDefinitionException;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionConstants;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.base.KaleoDefinitionLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class KaleoDefinitionLocalServiceImpl
	extends KaleoDefinitionLocalServiceBaseImpl {

	@Override
	public void activateKaleoDefinition(
			long kaleoDefinitionId, long startKaleoNodeId,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoDefinition kaleoDefinition =
			kaleoDefinitionPersistence.findByPrimaryKey(kaleoDefinitionId);

		deactivatePreviousKaleoDefinition(
			kaleoDefinition.getName(), serviceContext);

		kaleoDefinition.setStartKaleoNodeId(startKaleoNodeId);
		kaleoDefinition.setModifiedDate(new Date());
		kaleoDefinition.setActive(true);

		kaleoDefinitionPersistence.update(kaleoDefinition);

		// Kaleo definition version

		activateKaleoDefinitionVersion(
			kaleoDefinition.getName(), getVersion(kaleoDefinition.getVersion()),
			startKaleoNodeId, serviceContext);
	}

	@Override
	public void activateKaleoDefinition(
			long kaleoDefinitionId, ServiceContext serviceContext)
		throws PortalException {

		KaleoDefinition kaleoDefinition =
			kaleoDefinitionPersistence.findByPrimaryKey(kaleoDefinitionId);

		kaleoDefinition.setModifiedDate(new Date());
		kaleoDefinition.setActive(true);

		kaleoDefinitionPersistence.update(kaleoDefinition);

		// Kaleo definition version

		updateKaleoDefinitionVersionActive(
			kaleoDefinition.getName(), getVersion(kaleoDefinition.getVersion()),
			true, serviceContext);
	}

	@Override
	public void activateKaleoDefinition(
			String name, int version, ServiceContext serviceContext)
		throws PortalException {

		KaleoDefinition kaleoDefinition =
			kaleoDefinitionPersistence.findByC_N_V(
				serviceContext.getCompanyId(), name, version);

		kaleoDefinition.setModifiedDate(new Date());
		kaleoDefinition.setActive(true);

		kaleoDefinitionPersistence.update(kaleoDefinition);

		// Kaleo definition version

		updateKaleoDefinitionVersionActive(
			kaleoDefinition.getName(), getVersion(kaleoDefinition.getVersion()),
			true, serviceContext);
	}

	@Override
	public KaleoDefinition addKaleoDefinition(
			String name, String title, String description, String content,
			int version, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getGuestOrUserId());
		Date now = new Date();

		long kaleoDefinitionId = counterLocalService.increment();

		KaleoDefinition kaleoDefinition = kaleoDefinitionPersistence.create(
			kaleoDefinitionId);

		kaleoDefinition.setCompanyId(user.getCompanyId());
		kaleoDefinition.setUserId(user.getUserId());
		kaleoDefinition.setUserName(user.getFullName());
		kaleoDefinition.setCreateDate(now);
		kaleoDefinition.setModifiedDate(now);
		kaleoDefinition.setName(name);
		kaleoDefinition.setTitle(title);
		kaleoDefinition.setDescription(description);
		kaleoDefinition.setContent(content);
		kaleoDefinition.setVersion(version);
		kaleoDefinition.setActive(false);

		kaleoDefinitionPersistence.update(kaleoDefinition);

		// Kaleo definition version

		addKaleoDefinitionVersion(
			user, kaleoDefinition, KaleoDefinitionConstants.VERSION_DEFAULT,
			WorkflowConstants.STATUS_APPROVED);

		return kaleoDefinition;
	}

	@Override
	public void deactivateKaleoDefinition(
			String name, int version, ServiceContext serviceContext)
		throws PortalException {

		KaleoDefinition kaleoDefinition =
			kaleoDefinitionPersistence.findByC_N_V(
				serviceContext.getCompanyId(), name, version);

		kaleoDefinition.setModifiedDate(new Date());
		kaleoDefinition.setActive(false);

		kaleoDefinitionPersistence.update(kaleoDefinition);

		// Kaleo definition version

		updateKaleoDefinitionVersionActive(
			kaleoDefinition.getName(),
			String.valueOf(kaleoDefinition.getVersion()), false,
			serviceContext);
	}

	@Override
	public void deleteCompanyKaleoDefinitions(long companyId) {

		// Kaleo definitions

		kaleoDefinitionPersistence.removeByCompanyId(companyId);

		// Kaleo definition version

		kaleoDefinitionVersionPersistence.removeByCompanyId(companyId);

		// Kaleo condition

		kaleoConditionLocalService.deleteCompanyKaleoConditions(companyId);

		// Kaleo instances

		kaleoInstanceLocalService.deleteCompanyKaleoInstances(companyId);

		// Kaleo nodes

		kaleoNodeLocalService.deleteCompanyKaleoNodes(companyId);

		// Kaleo tasks

		kaleoTaskLocalService.deleteCompanyKaleoTasks(companyId);

		// Kaleo transitions

		kaleoTransitionLocalService.deleteCompanyKaleoTransitions(companyId);
	}

	@Override
	public void deleteKaleoDefinition(
			String name, int version, ServiceContext serviceContext)
		throws PortalException {

		// Kaleo definition

		KaleoDefinition kaleoDefinition = getKaleoDefinition(
			name, version, serviceContext);

		if (kaleoDefinition.isActive()) {
			throw new WorkflowException(
				"Cannot delete active workflow definition " +
					kaleoDefinition.getKaleoDefinitionId());
		}

		if (kaleoDefinition.hasIncompleteKaleoInstances()) {
			throw new WorkflowException(
				"Cannot delete incomplete workflow definition " +
					kaleoDefinition.getKaleoDefinitionId());
		}

		kaleoDefinitionPersistence.remove(kaleoDefinition);

		// Kaleo definition version

		List<KaleoDefinitionVersion> kaleoDefinitionVersions =
			kaleoDefinitionVersionPersistence.findByKaleoDefinitionId(
				kaleoDefinition.getKaleoDefinitionId());

		for (KaleoDefinitionVersion kaleoDefinitionVersion :
				kaleoDefinitionVersions) {

			kaleoDefinitionVersionPersistence.remove(kaleoDefinitionVersion);
		}

		// Kaleo condition

		kaleoConditionLocalService.deleteKaleoDefinitionKaleoCondition(
			kaleoDefinition.getKaleoDefinitionId());

		// Kaleo instances

		kaleoInstanceLocalService.deleteKaleoDefinitionKaleoInstances(
			kaleoDefinition.getKaleoDefinitionId());

		// Kaleo nodes

		kaleoNodeLocalService.deleteKaleoDefinitionKaleoNodes(
			kaleoDefinition.getKaleoDefinitionId());

		// Kaleo tasks

		kaleoTaskLocalService.deleteKaleoDefinitionKaleoTasks(
			kaleoDefinition.getKaleoDefinitionId());

		// Kaleo transitions

		kaleoTransitionLocalService.deleteKaleoDefinitionKaleoTransitions(
			kaleoDefinition.getKaleoDefinitionId());
	}

	@Override
	public KaleoDefinition fetchLatestKaleoDefinition(
			String name, ServiceContext serviceContext)
		throws PortalException {

		List<KaleoDefinition> kaleoDefinitions =
			kaleoDefinitionPersistence.findByC_N(
				serviceContext.getCompanyId(), name, 0, 1);

		if (kaleoDefinitions.isEmpty()) {
			return null;
		}

		return kaleoDefinitions.get(0);
	}

	@Override
	public KaleoDefinition getKaleoDefinition(
			String name, int version, ServiceContext serviceContext)
		throws PortalException {

		return kaleoDefinitionPersistence.findByC_N_V(
			serviceContext.getCompanyId(), name, version);
	}

	@Override
	public List<KaleoDefinition> getKaleoDefinitions(
		boolean active, int start, int end,
		OrderByComparator<KaleoDefinition> orderByComparator,
		ServiceContext serviceContext) {

		return kaleoDefinitionPersistence.findByC_A(
			serviceContext.getCompanyId(), active, start, end,
			orderByComparator);
	}

	@Override
	public List<KaleoDefinition> getKaleoDefinitions(
		int start, int end,
		OrderByComparator<KaleoDefinition> orderByComparator,
		ServiceContext serviceContext) {

		return kaleoDefinitionPersistence.findByCompanyId(
			serviceContext.getCompanyId(), start, end, orderByComparator);
	}

	@Override
	public List<KaleoDefinition> getKaleoDefinitions(
		String name, boolean active, int start, int end,
		OrderByComparator<KaleoDefinition> orderByComparator,
		ServiceContext serviceContext) {

		return kaleoDefinitionPersistence.findByC_N_A(
			serviceContext.getCompanyId(), name, active, start, end,
			orderByComparator);
	}

	@Override
	public List<KaleoDefinition> getKaleoDefinitions(
		String name, int start, int end,
		OrderByComparator<KaleoDefinition> orderByComparator,
		ServiceContext serviceContext) {

		return kaleoDefinitionPersistence.findByC_N(
			serviceContext.getCompanyId(), name, start, end, orderByComparator);
	}

	@Override
	public int getKaleoDefinitionsCount(
		boolean active, ServiceContext serviceContext) {

		return kaleoDefinitionPersistence.countByC_A(
			serviceContext.getCompanyId(), active);
	}

	@Override
	public int getKaleoDefinitionsCount(ServiceContext serviceContext) {
		return kaleoDefinitionPersistence.countByCompanyId(
			serviceContext.getCompanyId());
	}

	@Override
	public int getKaleoDefinitionsCount(
		String name, boolean active, ServiceContext serviceContext) {

		return kaleoDefinitionPersistence.countByC_N_A(
			serviceContext.getCompanyId(), name, active);
	}

	@Override
	public int getKaleoDefinitionsCount(
		String name, ServiceContext serviceContext) {

		return kaleoDefinitionPersistence.countByC_N(
			serviceContext.getCompanyId(), name);
	}

	@Override
	public KaleoDefinition getLatestKaleoDefinition(
			String name, ServiceContext serviceContext)
		throws PortalException {

		KaleoDefinition kaleoDefinition = fetchLatestKaleoDefinition(
			name, serviceContext);

		if (kaleoDefinition == null) {
			throw new NoSuchDefinitionException();
		}

		return kaleoDefinition;
	}

	@Override
	public KaleoDefinition incrementKaleoDefinition(
			Definition definition, String title, ServiceContext serviceContext)
		throws PortalException {

		KaleoDefinition kaleoDefinition = getLatestKaleoDefinition(
			definition.getName(), serviceContext);

		return updateKaleoDefinition(
			definition.getName(), title, definition.getDescription(),
			definition.getContent(), serviceContext, kaleoDefinition);
	}

	@Override
	public KaleoDefinition updateKaleoDefinition(
			String name, String title, String description, String content,
			ServiceContext serviceContext, KaleoDefinition kaleoDefinition)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getGuestOrUserId());

		kaleoDefinition.setName(name);
		kaleoDefinition.setTitle(title);
		kaleoDefinition.setDescription(description);
		kaleoDefinition.setContent(content);

		KaleoDefinitionVersion latestKaleoDefinitionVersion =
			kaleoDefinitionVersionLocalService.getLatestKaleoDefinitionVersion(
				kaleoDefinition.getKaleoDefinitionId());

		String version = getNextVersion(
			latestKaleoDefinitionVersion.getVersion(), true);

		kaleoDefinition.setVersion(getVersion(version));

		kaleoDefinition.setActive(false);

		kaleoDefinitionPersistence.update(kaleoDefinition);

		// Kaleo definition version

		addKaleoDefinitionVersion(
			user, kaleoDefinition, version, WorkflowConstants.STATUS_APPROVED);

		return kaleoDefinition;
	}

	@Override
	public KaleoDefinition updateTitle(
			String name, int version, String title,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoDefinition kaleoDefinition =
			kaleoDefinitionPersistence.findByC_N_V(
				serviceContext.getCompanyId(), name, version);

		kaleoDefinition.setTitle(title);

		kaleoDefinitionPersistence.update(kaleoDefinition);

		// Kaleo definition version

		updateKaleoDefinitionVersionTitle(
			kaleoDefinition.getName(),
			String.valueOf(kaleoDefinition.getVersion()), title,
			serviceContext);

		return kaleoDefinition;
	}

	protected void activateKaleoDefinitionVersion(
			String name, String version, long startKaleoNodeId,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoDefinitionVersion kaleoDefinitionVersion =
			kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
				name, version, serviceContext);

		kaleoDefinitionVersion.setStartKaleoNodeId(startKaleoNodeId);
		kaleoDefinitionVersion.setActive(true);

		kaleoDefinitionVersionPersistence.update(kaleoDefinitionVersion);
	}

	protected KaleoDefinitionVersion addKaleoDefinitionVersion(
		User user, KaleoDefinition kaleoDefinition, String version,
		int status) {

		long kaleoDefinitionVersionId = counterLocalService.increment();

		KaleoDefinitionVersion kaleoDefinitionVersion =
			kaleoDefinitionVersionPersistence.create(kaleoDefinitionVersionId);

		kaleoDefinitionVersion.setGroupId(kaleoDefinition.getGroupId());
		kaleoDefinitionVersion.setCompanyId(kaleoDefinition.getCompanyId());
		kaleoDefinitionVersion.setUserId(user.getUserId());
		kaleoDefinitionVersion.setUserName(user.getFullName());
		kaleoDefinitionVersion.setCreateDate(kaleoDefinition.getModifiedDate());
		kaleoDefinitionVersion.setKaleoDefinitionId(
			kaleoDefinition.getKaleoDefinitionId());
		kaleoDefinitionVersion.setName(kaleoDefinition.getName());
		kaleoDefinitionVersion.setTitle(kaleoDefinition.getTitle());
		kaleoDefinitionVersion.setDescription(
			kaleoDefinitionVersion.getDescription());
		kaleoDefinitionVersion.setContent(kaleoDefinition.getContent());
		kaleoDefinitionVersion.setVersion(version);
		kaleoDefinitionVersion.setActive(kaleoDefinitionVersion.getActive());
		kaleoDefinitionVersion.setStatus(status);
		kaleoDefinitionVersion.setStatusByUserId(user.getUserId());
		kaleoDefinitionVersion.setStatusByUserName(user.getFullName());
		kaleoDefinitionVersion.setStatusDate(kaleoDefinition.getModifiedDate());

		kaleoDefinitionVersionPersistence.update(kaleoDefinitionVersion);

		return kaleoDefinitionVersion;
	}

	protected void deactivatePreviousKaleoDefinition(
			String name, ServiceContext serviceContext)
		throws PortalException {

		KaleoDefinition previousKaleoDefinition = fetchLatestKaleoDefinition(
			name, serviceContext);

		if (previousKaleoDefinition == null) {
			return;
		}

		previousKaleoDefinition.setModifiedDate(new Date());
		previousKaleoDefinition.setActive(false);

		kaleoDefinitionPersistence.update(previousKaleoDefinition);

		// Kaleo definition version

		updateKaleoDefinitionVersionActive(
			name, getVersion(previousKaleoDefinition.getVersion()), false,
			serviceContext);
	}

	protected String getNextVersion(String version, boolean majorVersion) {
		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		if (majorVersion) {
			versionParts[0]++;
			versionParts[1] = 0;
		}
		else {
			versionParts[1]++;
		}

		return versionParts[0] + StringPool.PERIOD + versionParts[1];
	}

	protected String getVersion(int version) {
		return version + StringPool.PERIOD + 0;
	}

	protected int getVersion(String version) {
		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		return versionParts[0];
	}

	protected void updateKaleoDefinitionVersionActive(
			String name, String version, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoDefinitionVersion kaleoDefinitionVersion =
			kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
				name, version, serviceContext);

		kaleoDefinitionVersion.setActive(active);

		kaleoDefinitionVersionPersistence.update(kaleoDefinitionVersion);
	}

	protected void updateKaleoDefinitionVersionTitle(
			String name, String version, String title,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoDefinitionVersion kaleoDefinitionVersion =
			kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
				name, version, serviceContext);

		kaleoDefinitionVersion.setTitle(title);

		kaleoDefinitionVersionPersistence.update(kaleoDefinitionVersion);
	}

}