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
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleConstants;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleEvent;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleListener;
import com.liferay.portal.kernel.util.TransientValue;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.StagedModel;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public abstract class BaseExportImportLifecycleListener
	implements ExportImportLifecycleListener {

	@Override
	public abstract boolean isParallel();

	@Override
	public void onExportImportLifecycleEvent(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception {

		List<Serializable> attributes =
			exportImportLifecycleEvent.getAttributes();

		int code = exportImportLifecycleEvent.getCode();

		if (code == ExportImportLifecycleConstants.EVENT_LAYOUT_EXPORT_FAILED) {
			onLayoutExportFailed(
				getPortletDataContextAttribute(attributes),
				getThrowableAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_LAYOUT_EXPORT_STARTED) {

			onLayoutExportStarted(getPortletDataContextAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_LAYOUT_EXPORT_SUCCEEDED) {

			onLayoutExportSucceeded(getPortletDataContextAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_LAYOUT_IMPORT_FAILED) {

			onLayoutImportFailed(
				getPortletDataContextAttribute(attributes),
				getThrowableAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_LAYOUT_IMPORT_STARTED) {

			onLayoutImportStarted(getPortletDataContextAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_LAYOUT_IMPORT_SUCCEEDED) {

			onLayoutImportSucceeded(getPortletDataContextAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PORTLET_EXPORT_FAILED) {

			onPortletExportFailed(
				getPortletDataContextAttribute(attributes),
				getThrowableAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PORTLET_EXPORT_STARTED) {

			onPortletExportStarted(getPortletDataContextAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PORTLET_EXPORT_SUCCEEDED) {

			onPortletExportSucceeded(
				getPortletDataContextAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PORTLET_IMPORT_FAILED) {

			onPortletImportFailed(
				getPortletDataContextAttribute(attributes),
				getThrowableAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PORTLET_IMPORT_STARTED) {

			onPortletImportStarted(getPortletDataContextAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PORTLET_IMPORT_SUCCEEDED) {

			onPortletImportSucceeded(
				getPortletDataContextAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_LAYOUT_LOCAL_FAILED) {

			onLayoutLocalPublicationFailed(
				getExportImportConfigurationAttribute(attributes),
				getThrowableAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_LAYOUT_LOCAL_STARTED) {

			onLayoutLocalPublicationStarted(
				getExportImportConfigurationAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_LAYOUT_LOCAL_SUCCEEDED) {

			onLayoutLocalPublicationSucceeded(
				getExportImportConfigurationAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_LAYOUT_REMOTE_FAILED) {

			onLayoutRemotePublicationFailed(
				getExportImportConfigurationAttribute(attributes),
				getThrowableAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_LAYOUT_REMOTE_STARTED) {

			onLayoutRemotePublicationStarted(
				getExportImportConfigurationAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_LAYOUT_REMOTE_SUCCEEDED) {

			onLayoutRemotePublicationSucceeded(
				getExportImportConfigurationAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_PORTLET_LOCAL_FAILED) {

			onPortletPublicationFailed(
				getTaskContextMapAttribute(attributes),
				getThrowableAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_PORTLET_LOCAL_STARTED) {

			onPortletPublicationStarted(getTaskContextMapAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_PORTLET_LOCAL_SUCCEEDED) {

			onPortletPublicationSucceeded(
				getTaskContextMapAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_STAGED_MODEL_EXPORT_FAILED) {

			onStagedModelExportFailed(
				getPortletDataContextAttribute(attributes),
				getStagedModelAttribute(attributes),
				getThrowableAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_STAGED_MODEL_EXPORT_STARTED) {

			onStagedModelExportStarted(
				getPortletDataContextAttribute(attributes),
				getStagedModelAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_STAGED_MODEL_EXPORT_SUCCEEDED) {

			onStagedModelExportSucceeded(
				getPortletDataContextAttribute(attributes),
				getStagedModelAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_STAGED_MODEL_IMPORT_FAILED) {

			onStagedModelImportFailed(
				getPortletDataContextAttribute(attributes),
				getStagedModelAttribute(attributes),
				getThrowableAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_STAGED_MODEL_IMPORT_STARTED) {

			onStagedModelImportStarted(
				getPortletDataContextAttribute(attributes),
				getStagedModelAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_STAGED_MODEL_IMPORT_SUCCEEDED) {

			onStagedModelImportSucceeded(
				getPortletDataContextAttribute(attributes),
				getStagedModelAttribute(attributes));
		}
	}

	protected <T> T getAttributeByType(
		List<Serializable> attributes, Class<T> clazz) {

		for (Serializable attribute : attributes) {
			if (clazz.isInstance(attribute)) {
				return clazz.cast(attribute);
			}
		}

		return null;
	}

	protected ExportImportConfiguration getExportImportConfigurationAttribute(
		List<Serializable> attributes) {

		return getAttributeByType(attributes, ExportImportConfiguration.class);
	}

	protected PortletDataContext getPortletDataContextAttribute(
		List<Serializable> attributes) {

		return getAttributeByType(attributes, PortletDataContext.class);
	}

	protected StagedModel getStagedModelAttribute(
		List<Serializable> attributes) {

		TransientValue<Object> transientValue = getAttributeByType(
			attributes, TransientValue.class);

		Object value = transientValue.getValue();

		if (value instanceof StagedModel) {
			return (StagedModel)value;
		}

		return null;
	}

	protected Map<String, Serializable> getTaskContextMapAttribute(
		List<Serializable> attributes) {

		return getAttributeByType(attributes, Map.class);
	}

	protected Throwable getThrowableAttribute(List<Serializable> attributes) {
		return getAttributeByType(attributes, Throwable.class);
	}

	protected void onLayoutExportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {
	}

	protected void onLayoutExportStarted(PortletDataContext portletDataContext)
		throws Exception {
	}

	protected void onLayoutExportSucceeded(
			PortletDataContext portletDataContext)
		throws Exception {
	}

	protected void onLayoutImportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {
	}

	protected void onLayoutImportStarted(PortletDataContext portletDataContext)
		throws Exception {
	}

	protected void onLayoutImportSucceeded(
			PortletDataContext portletDataContext)
		throws Exception {
	}

	protected void onLayoutLocalPublicationFailed(
			ExportImportConfiguration exportImportConfiguration,
			Throwable throwable)
		throws Exception {
	}

	protected void onLayoutLocalPublicationStarted(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {
	}

	protected void onLayoutLocalPublicationSucceeded(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {
	}

	protected void onLayoutRemotePublicationFailed(
			ExportImportConfiguration exportImportConfiguration,
			Throwable throwable)
		throws Exception {
	}

	protected void onLayoutRemotePublicationStarted(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {
	}

	protected void onLayoutRemotePublicationSucceeded(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {
	}

	protected void onPortletExportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {
	}

	protected void onPortletExportStarted(PortletDataContext portletDataContext)
		throws Exception {
	}

	protected void onPortletExportSucceeded(
			PortletDataContext portletDataContext)
		throws Exception {
	}

	protected void onPortletImportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {
	}

	protected void onPortletImportStarted(PortletDataContext portletDataContext)
		throws Exception {
	}

	protected void onPortletImportSucceeded(
			PortletDataContext portletDataContext)
		throws Exception {
	}

	protected void onPortletPublicationFailed(
			Map<String, Serializable> taskContextMap, Throwable throwable)
		throws Exception {
	}

	protected void onPortletPublicationStarted(
			Map<String, Serializable> taskContextMap)
		throws Exception {
	}

	protected void onPortletPublicationSucceeded(
			Map<String, Serializable> taskContextMap)
		throws Exception {
	}

	protected void onStagedModelExportFailed(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Throwable throwable)
		throws Exception {
	}

	protected void onStagedModelExportStarted(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {
	}

	protected void onStagedModelExportSucceeded(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {
	}

	protected void onStagedModelImportFailed(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Throwable throwable)
		throws Exception {
	}

	protected void onStagedModelImportStarted(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {
	}

	protected void onStagedModelImportSucceeded(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {
	}

}