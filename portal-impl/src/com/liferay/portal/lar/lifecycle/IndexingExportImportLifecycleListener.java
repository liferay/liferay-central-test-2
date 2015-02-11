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
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleConstants;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleEvent;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.model.User;

import java.io.Serializable;

import java.util.List;

/**
 * @author Mate Thurzo
 */
public class IndexingExportImportLifecycleListener
	implements ExportImportLifecycleListener {

	@Override
	public boolean isParallel() {
		return true;
	}

	@Override
	public void onExportImportLifecycleEvent(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception {

		boolean layoutImportInProcess =
			exportImportLifecycleEvent.getProcessFlag(
				ExportImportLifecycleConstants.
					PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS);
		boolean portletImportInProcess =
			exportImportLifecycleEvent.getProcessFlag(
				ExportImportLifecycleConstants.
					PROCESS_FLAG_PORTLET_IMPORT_IN_PROCESS);

		if (((exportImportLifecycleEvent.getCode() !=
				ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_SUCCEEDED) ||
			 !layoutImportInProcess) &&
			((exportImportLifecycleEvent.getCode() !=
				ExportImportLifecycleConstants.
					EVENT_PORTLET_IMPORT_SUCCEEDED) ||
			 !portletImportInProcess)) {

			return;
		}

		List<Serializable> attributes =
			exportImportLifecycleEvent.getAttributes();

		if (ListUtil.isEmpty(attributes)) {
			return;
		}

		PortletDataContext portletDataContext =
			(PortletDataContext)attributes.get(0);

		if (portletDataContext == null) {
			return;
		}

		long userId = 0;

		if (attributes.size() == 2) {
			userId = GetterUtil.getLong(attributes.get(1));
		}

		reindex(portletDataContext, userId);
	}

	protected void reindex(PortletDataContext portletDataContext, long userId) {
		boolean importPermissions = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.PERMISSIONS);

		if (importPermissions) {
			if (userId > 0) {
				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					User.class);

				try {
					indexer.reindex(userId);
				}
				catch (SearchException se) {
					if (_log.isDebugEnabled()) {
						_log.debug("Unable to reindex user " + userId, se);
					}
				}
			}
		}

		Indexer portletDataContextIndexer = IndexerRegistryUtil.getIndexer(
			PortletDataContext.class);

		try {
			portletDataContextIndexer.reindex(portletDataContext);
		}
		catch (SearchException se) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to reindex portlet data context", se);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexingExportImportLifecycleListener.class);

}