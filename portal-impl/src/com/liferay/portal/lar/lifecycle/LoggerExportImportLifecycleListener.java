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

package com.liferay.portal.lar.lifecycle;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleEvent;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.StagedModel;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class LoggerExportImportLifecycleListener
	extends BaseExportImportLifecycleListener {

	@Override
	public boolean isParallel() {
		return false;
	}

	@Override
	public void onExportImportLifecycleEvent(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception {

		if (!_log.isDebugEnabled()) {
			return;
		}

		super.onExportImportLifecycleEvent(exportImportLifecycleEvent);
	}

	@Override
	protected void onLayoutExportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {

		_log.debug(
			"Layout export failed for group " + portletDataContext.getGroupId(),
			throwable);
	}

	@Override
	protected void onLayoutExportStarted(PortletDataContext portletDataContext)
		throws Exception {

		_log.debug(
			"Layout export started for group " +
				portletDataContext.getGroupId());
	}

	@Override
	protected void onLayoutExportSucceeded(
			PortletDataContext portletDataContext)
		throws Exception {

		_log.debug(
			"Layout export succeeded for group " +
				portletDataContext.getGroupId());
	}

	@Override
	protected void onLayoutImportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {

		_log.debug(
			"Layout import failed for group " + portletDataContext.getGroupId(),
			throwable);
	}

	@Override
	protected void onLayoutImportStarted(PortletDataContext portletDataContext)
		throws Exception {

		_log.debug(
			"Layout import started for group " +
				portletDataContext.getGroupId());
	}

	@Override
	protected void onLayoutImportSucceeded(
			PortletDataContext portletDataContext)
		throws Exception {

		_log.debug(
			"Layout import succeeded for group " +
				portletDataContext.getGroupId());
	}

	@Override
	protected void onLayoutLocalPublicationFailed(
			ExportImportConfiguration exportImportConfiguration,
			Throwable throwable)
		throws Exception {

		_log.debug(
			"Layout publication failed for group " +
				exportImportConfiguration.getGroupId(),
			throwable);
	}

	@Override
	protected void onLayoutLocalPublicationStarted(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		_log.debug(
			"Layout publication started for group " +
				exportImportConfiguration.getGroupId());
	}

	@Override
	protected void onLayoutLocalPublicationSucceeded(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		_log.debug(
			"Layout publication succeeded for group " +
				exportImportConfiguration.getGroupId());
	}

	@Override
	protected void onLayoutRemotePublicationFailed(
			ExportImportConfiguration exportImportConfiguration,
			Throwable throwable)
		throws Exception {

		_log.debug(
			"Layout remote publication failed for group " +
				exportImportConfiguration.getGroupId(),
			throwable);
	}

	@Override
	protected void onLayoutRemotePublicationStarted(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		_log.debug(
			"Layout publication started for group " +
				exportImportConfiguration.getGroupId());
	}

	@Override
	protected void onLayoutRemotePublicationSucceeded(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		_log.debug(
			"Layout remote publication succeeded for group " +
				exportImportConfiguration.getGroupId());
	}

	@Override
	protected void onPortletExportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {

		_log.debug(
			"Portlet export failed for portlet " +
				portletDataContext.getPortletId(),
			throwable);
	}

	@Override
	protected void onPortletExportStarted(PortletDataContext portletDataContext)
		throws Exception {

		_log.debug(
			"Portlet export started for portlet " +
				portletDataContext.getPortletId());
	}

	@Override
	protected void onPortletExportSucceeded(
			PortletDataContext portletDataContext)
		throws Exception {

		_log.debug(
			"Portlet export succeeded for portlet " +
				portletDataContext.getPortletId());
	}

	@Override
	protected void onPortletImportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {

		_log.debug(
			"Portlet import failed for portlet " +
				portletDataContext.getPortletId(),
			throwable);
	}

	@Override
	protected void onPortletImportStarted(PortletDataContext portletDataContext)
		throws Exception {

		_log.debug(
			"Portlet import started for portlet " +
				portletDataContext.getPortletId());
	}

	@Override
	protected void onPortletImportSucceeded(
			PortletDataContext portletDataContext)
		throws Exception {

		_log.debug(
			"Portlet import succeeded for portlet " +
				portletDataContext.getPortletId());
	}

	@Override
	protected void onPortletPublicationFailed(
			Map<String, Serializable> taskContextMap, Throwable throwable)
		throws Exception {

		_log.debug(
			"Portlet publication failed for portlet " +
				taskContextMap.get("portletId"),
			throwable);
	}

	@Override
	protected void onPortletPublicationStarted(
			Map<String, Serializable> taskContextMap)
		throws Exception {

		_log.debug(
			"Portlet publication started for portlet " +
				taskContextMap.get("portletId"));
	}

	@Override
	protected void onPortletPublicationSucceeded(
			Map<String, Serializable> taskContextMap)
		throws Exception {

		_log.debug(
			"Portlet publication succeeded for portlet " +
				taskContextMap.get("portletId"));
	}

	@Override
	protected void onStagedModelExportFailed(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Throwable throwable)
		throws Exception {

		_log.debug(
			"Staged model " + stagedModel.getStagedModelType() +
				" export failed",
			throwable);
	}

	@Override
	protected void onStagedModelExportStarted(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {

		_log.debug(
			"Staged model " + stagedModel.getStagedModelType() +
				" export started");
	}

	@Override
	protected void onStagedModelExportSucceeded(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {

		_log.debug(
			"Staged model " + stagedModel.getStagedModelType() +
				" export succeeded");
	}

	@Override
	protected void onStagedModelImportFailed(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Throwable throwable)
		throws Exception {

		_log.debug(
			"Staged model " + stagedModel.getStagedModelType() +
				" import failed",
			throwable);
	}

	@Override
	protected void onStagedModelImportStarted(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {

		_log.debug(
			"Staged model " + stagedModel.getStagedModelType() +
				" import started");
	}

	@Override
	protected void onStagedModelImportSucceeded(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {

		_log.debug(
			"Staged model " + stagedModel.getStagedModelType() +
				" import succeeded");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LoggerExportImportLifecycleListener.class);

}