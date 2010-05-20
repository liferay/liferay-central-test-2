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

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * <a href="WikiDisplayPortletDataHandlerImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Marcellus Tavares
 */
public class WikiDisplayPortletDataHandlerImpl extends BasePortletDataHandler {

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			preferences.setValue("title", StringPool.BLANK);
			preferences.setValue("node-id", StringPool.BLANK);

			return preferences;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			long nodeId = GetterUtil.getLong(
				preferences.getValue("node-id", StringPool.BLANK));

			if (nodeId <= 0) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No node id found in preferences of portlet " +
							portletId);
				}

				return StringPool.BLANK;
			}

			String title = preferences.getValue("title", null);

			if (title == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No title found in preferences of portlet " +
							portletId);
				}

				return StringPool.BLANK;
			}

			WikiNode node = null;

			try {
				node = WikiNodeUtil.findByPrimaryKey(nodeId);
			}
			catch (NoSuchNodeException nsne) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsne);
				}
			}

			if (node == null) {
				return StringPool.BLANK;
			}

			context.addPermissions(
				"com.liferay.portlet.wiki", context.getGroupId());

			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("wiki-display-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			Element nodesEl = root.addElement("nodes");
			Element pagesEl = root.addElement("pages");

			WikiPortletDataHandlerImpl.exportNode(
				context, nodesEl, pagesEl, node);

			return doc.formattedString();
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

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

		try {
			context.importPermissions(
				"com.liferay.portlet.wiki", context.getSourceGroupId(),
				context.getGroupId());

			if (Validator.isNull(data)) {
				return null;
			}

			Document doc = SAXReaderUtil.read(data);

			Element root = doc.getRootElement();

			List<Element> nodeEls = root.element("nodes").elements("node");

			Map<Long, Long> nodePKs =
				(Map<Long, Long>)context.getNewPrimaryKeysMap(WikiNode.class);

			for (Element nodeEl : nodeEls) {
				String path = nodeEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				WikiNode node = (WikiNode)context.getZipEntryAsObject(path);

				WikiPortletDataHandlerImpl.importNode(context, nodePKs, node);
			}

			List<Element> pageEls = root.element("pages").elements("page");

			for (Element pageEl : pageEls) {
				String path = pageEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				WikiPage page = (WikiPage)context.getZipEntryAsObject(path);

				WikiPortletDataHandlerImpl.importPage(
					context, nodePKs, pageEl, page);
			}

			long nodeId = GetterUtil.getLong(
				preferences.getValue("node-id", StringPool.BLANK));

			if (nodeId > 0) {
				nodeId = MapUtil.getLong(nodePKs, nodeId, nodeId);

				preferences.setValue("node-id", String.valueOf(nodeId));
			}

			return preferences;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
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