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

package com.liferay.portlet.wiki.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.persistence.WikiNodeUtil;
import com.liferay.portlet.wiki.util.WikiCacheThreadLocal;
import com.liferay.portlet.wiki.util.WikiCacheUtil;

import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Marcellus Tavares
 */
public class WikiDisplayPortletDataHandlerImpl extends BasePortletDataHandler {

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_nodesAndPages, _attachments, _categories, _comments, _tags
		};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_nodesAndPages, _attachments, _categories, _comments, _tags
		};
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws PortletDataException {

		WikiCacheThreadLocal.setClearCache(false);

		try {
			return super.importData(context, portletId, preferences, data);
		}
		finally {
			WikiCacheThreadLocal.setClearCache(true);
		}
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		preferences.setValue("title", StringPool.BLANK);
		preferences.setValue("node-id", StringPool.BLANK);

		return preferences;
	}

	protected String doExportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		long nodeId = GetterUtil.getLong(
			preferences.getValue("node-id", StringPool.BLANK));

		if (nodeId <= 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No node id found in preferences of portlet " + portletId);
			}

			return StringPool.BLANK;
		}

		String title = preferences.getValue("title", null);

		if (title == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No title found in preferences of portlet " + portletId);
			}

			return StringPool.BLANK;
		}

		WikiNode node = null;

		try {
			node = WikiNodeUtil.findByPrimaryKey(nodeId);

			return StringPool.BLANK;
		}
		catch (NoSuchNodeException nsne) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsne, nsne);
			}
		}

		context.addPermissions(
			"com.liferay.portlet.wiki", context.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("wiki-display-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(context.getScopeGroupId()));

		Element nodesElement = rootElement.addElement("nodes");
		Element pagesElement = rootElement.addElement("pages");

		WikiPortletDataHandlerImpl.exportNode(
			context, nodesElement, pagesElement, node);

		return document.formattedString();
	}

	protected PortletPreferences doImportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws Exception {

		context.importPermissions(
			"com.liferay.portlet.wiki", context.getSourceGroupId(),
			context.getScopeGroupId());

		if (Validator.isNull(data)) {
			return null;
		}

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element nodesElement = rootElement.element("nodes");

		for (Element nodeElement : nodesElement.elements("node")) {
			String path = nodeElement.attributeValue("path");

			if (!context.isPathNotProcessed(path)) {
				continue;
			}

			WikiNode node = (WikiNode)context.getZipEntryAsObject(path);

			WikiPortletDataHandlerImpl.importNode(context, node);
		}

		Element pagesElement = rootElement.element("pages");

		for (Element pageElement : pagesElement.elements("page")) {
			String path = pageElement.attributeValue("path");

			if (!context.isPathNotProcessed(path)) {
				continue;
			}

			WikiPage page = (WikiPage)context.getZipEntryAsObject(path);

			WikiPortletDataHandlerImpl.importPage(context, pageElement, page);
		}

		Map<Long, Long> nodePKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(WikiNode.class);

		for (long nodeId : nodePKs.values()) {
			WikiCacheUtil.clearCache(nodeId);
		}

		long nodeId = GetterUtil.getLong(
			preferences.getValue("node-id", StringPool.BLANK));

		if (nodeId > 0) {
			nodeId = MapUtil.getLong(nodePKs, nodeId, nodeId);

			preferences.setValue("node-id", String.valueOf(nodeId));
		}

		return preferences;
	}

	private static final String _NAMESPACE = "wiki";

	private static Log _log = LogFactoryUtil.getLog(
		WikiDisplayPortletDataHandlerImpl.class);

	private static PortletDataHandlerBoolean _attachments =
		new PortletDataHandlerBoolean(_NAMESPACE, "attachments");

	private static PortletDataHandlerBoolean _categories =
		new PortletDataHandlerBoolean(_NAMESPACE, "categories");

	private static PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static PortletDataHandlerBoolean _nodesAndPages =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "wikis-and-pages", true, true);

	private static PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

}