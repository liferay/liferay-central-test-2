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

import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.persistence.WikiNodeUtil;
import com.liferay.portlet.wiki.service.persistence.WikiPageUtil;
import com.liferay.portlet.wiki.util.WikiCacheThreadLocal;
import com.liferay.portlet.wiki.util.WikiCacheUtil;
import com.liferay.portlet.wiki.util.comparator.PageVersionComparator;

import java.io.InputStream;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Jorge Ferrer
 * @author Marcellus Tavares
 * @author Juan Fernández
 */
public class WikiPortletDataHandlerImpl extends BasePortletDataHandler {

	public static void exportNode(
			PortletDataContext context, Element nodesElement,
			Element pagesElement, WikiNode node)
		throws Exception {

		if (context.isWithinDateRange(node.getModifiedDate())) {
			String path = getNodePath(context, node);

			if (context.isPathNotProcessed(path)) {
				Element nodeElement = nodesElement.addElement("node");

				nodeElement.addAttribute("path", path);

				node.setUserUuid(node.getUserUuid());

				context.addPermissions(WikiNode.class, node.getNodeId());

				context.addZipEntry(path, node);
			}
		}

		List<WikiPage> pages = WikiPageUtil.findByN_S(
			node.getNodeId(), WorkflowConstants.STATUS_APPROVED,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new PageVersionComparator(true));

		for (WikiPage page : pages) {
			exportPage(context, nodesElement, pagesElement, page);
		}
	}

	public static void importNode(PortletDataContext context, WikiNode node)
		throws Exception {

		long userId = context.getUserId(node.getUserUuid());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(node.getCreateDate());
		serviceContext.setModifiedDate(node.getModifiedDate());
		serviceContext.setScopeGroupId(context.getScopeGroupId());

		WikiNode importedNode = null;

		if (context.isDataStrategyMirror()) {
			WikiNode existingNode = WikiNodeUtil.fetchByUUID_G(
				node.getUuid(), context.getScopeGroupId());

			String nodeName = PropsUtil.get(PropsKeys.WIKI_INITIAL_NODE_NAME);

			if ((existingNode == null) && node.getName().equals(nodeName)) {
				try {
					WikiNodeUtil.removeByG_N(
						context.getScopeGroupId(), node.getName());
				}
				catch (NoSuchNodeException nsne) {
				}
			}

			if (existingNode == null) {
				serviceContext.setUuid(node.getUuid());

				importedNode = WikiNodeLocalServiceUtil.addNode(
					userId, node.getName(), node.getDescription(),
					serviceContext);
			}
			else {
				importedNode = WikiNodeLocalServiceUtil.updateNode(
					existingNode.getNodeId(), node.getName(),
					node.getDescription(), serviceContext);
			}
		}
		else {
			String nodeName = PropsUtil.get(PropsKeys.WIKI_INITIAL_NODE_NAME);

			if (node.getName().equals(nodeName)) {
				try {
					WikiNodeUtil.removeByG_N(
						context.getScopeGroupId(), node.getName());
				}
				catch (NoSuchNodeException nsne) {
				}
			}

			importedNode = WikiNodeLocalServiceUtil.addNode(
				userId, node.getName(), node.getDescription(), serviceContext);
		}

		Map<Long, Long> nodePKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(WikiNode.class);

		nodePKs.put(node.getNodeId(), importedNode.getNodeId());

		context.importPermissions(
			WikiNode.class, node.getNodeId(), importedNode.getNodeId());
	}

	public static void importPage(
			PortletDataContext context, Element pageElement, WikiPage page)
		throws Exception {

		long userId = context.getUserId(page.getUserUuid());

		Map<Long, Long> nodePKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(WikiNode.class);

		long nodeId = MapUtil.getLong(
			nodePKs, page.getNodeId(), page.getNodeId());

		long[] assetCategoryIds = null;
		String[] assetTagNames = null;

		if (context.getBooleanParameter(_NAMESPACE, "categories") &&
			page.isHead()) {

			assetCategoryIds = context.getAssetCategoryIds(
				WikiPage.class, page.getResourcePrimKey());
		}

		if (context.getBooleanParameter(_NAMESPACE, "tags") &&
			page.isHead()) {

			assetTagNames = context.getAssetTagNames(
				WikiPage.class, page.getResourcePrimKey());
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setCreateDate(page.getCreateDate());
		serviceContext.setModifiedDate(page.getModifiedDate());

		if (page.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		WikiPage importedPage = null;

		if (context.isDataStrategyMirror()) {
			WikiPage existingPage = WikiPageUtil.fetchByUUID_G(
				page.getUuid(), context.getScopeGroupId());

			if (existingPage == null) {
				try {
					existingPage = WikiPageLocalServiceUtil.getPage(
						nodeId, page.getTitle());
				}
				catch (NoSuchPageException nspe) {
				}
			}

			if (existingPage == null) {
				serviceContext.setUuid(page.getUuid());

				importedPage = WikiPageLocalServiceUtil.addPage(
					userId, nodeId, page.getTitle(), page.getVersion(),
					page.getContent(), page.getSummary(), true,
					page.getFormat(), page.getHead(), page.getParentTitle(),
					page.getRedirectTitle(), serviceContext);
			}
			else {
				importedPage = WikiPageLocalServiceUtil.updatePage(
					userId, nodeId, existingPage.getTitle(), 0,
					page.getContent(), page.getSummary(), true,
					page.getFormat(), page.getParentTitle(),
					page.getRedirectTitle(), serviceContext);
			}
		}
		else {
			importedPage = WikiPageLocalServiceUtil.addPage(
				userId, nodeId, page.getTitle(), page.getVersion(),
				page.getContent(), page.getSummary(), true, page.getFormat(),
				page.getHead(), page.getParentTitle(), page.getRedirectTitle(),
				serviceContext);
		}

		if (context.getBooleanParameter(_NAMESPACE, "attachments") &&
			page.isHead()) {

			for (Element attachmentElement :
					pageElement.elements("attachment")) {

				String name = attachmentElement.attributeValue("name");
				String binPath = attachmentElement.attributeValue("bin-path");

				InputStream inputStream = context.getZipEntryAsInputStream(
					binPath);

				WikiPageLocalServiceUtil.addPageAttachment(
					importedPage.getCompanyId(),
					importedPage.getAttachmentsDir(),
					importedPage.getModifiedDate(), name, inputStream);
			}
		}

		if (page.isHead()) {
			context.importPermissions(
				WikiPage.class, page.getResourcePrimKey(),
				importedPage.getResourcePrimKey());
		}

		if (context.getBooleanParameter(_NAMESPACE, "comments") &&
			page.isHead()) {

			context.importComments(
				WikiPage.class, page.getResourcePrimKey(),
				importedPage.getResourcePrimKey(), context.getScopeGroupId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "ratings") &&
			page.isHead()) {

			context.importRatingsEntries(
				WikiPage.class, page.getResourcePrimKey(),
				importedPage.getResourcePrimKey());
		}
	}

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_nodesAndPages, _attachments, _categories, _comments, _ratings,
			_tags
		};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_nodesAndPages, _attachments, _categories, _comments, _ratings,
			_tags
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

	protected static void exportNode(
			PortletDataContext context, Element nodesElement, long nodeId)
		throws Exception {

		if (!context.hasDateRange()) {
			return;
		}

		WikiNode node = WikiNodeUtil.findByPrimaryKey(nodeId);

		String path = getNodePath(context, node);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element nodeElement = nodesElement.addElement("node");

		nodeElement.addAttribute("path", path);

		node.setUserUuid(node.getUserUuid());

		context.addPermissions(WikiNode.class, node.getNodeId());

		context.addZipEntry(path, node);
	}

	protected static void exportPage(
			PortletDataContext context, Element nodesElement,
			Element pagesElement, WikiPage page)
		throws Exception {

		if (!context.isWithinDateRange(page.getModifiedDate())) {
			return;
		}

		String path = getPagePath(context, page);

		if (context.isPathNotProcessed(path)) {
			Element pageElement = pagesElement.addElement("page");

			pageElement.addAttribute("path", path);

			page.setUserUuid(page.getUserUuid());

			context.addPermissions(WikiPage.class, page.getResourcePrimKey());

			if (context.getBooleanParameter(_NAMESPACE, "categories") &&
				page.isHead()) {

				context.addAssetCategories(
					WikiPage.class, page.getResourcePrimKey());
			}

			if (context.getBooleanParameter(_NAMESPACE, "comments") &&
				page.isHead()) {

				context.addComments(WikiPage.class, page.getResourcePrimKey());
			}

			if (context.getBooleanParameter(_NAMESPACE, "ratings") &&
				page.isHead()) {

				context.addRatingsEntries(
					WikiPage.class, page.getResourcePrimKey());
			}

			if (context.getBooleanParameter(_NAMESPACE, "tags") &&
				page.isHead()) {

				context.addAssetTags(WikiPage.class, page.getResourcePrimKey());
			}

			if (context.getBooleanParameter(_NAMESPACE, "attachments") &&
				page.isHead()) {

				for (String attachment : page.getAttachmentsFiles()) {
					int pos = attachment.lastIndexOf(StringPool.SLASH);

					String name = attachment.substring(pos + 1);
					String binPath = getPageAttachementBinPath(
						context, page, name);

					Element attachmentEl = pageElement.addElement("attachment");

					attachmentEl.addAttribute("name", name);
					attachmentEl.addAttribute("bin-path", binPath);

					byte[] bytes = DLServiceUtil.getFile(
						context.getCompanyId(), CompanyConstants.SYSTEM,
						attachment);

					context.addZipEntry(binPath, bytes);
				}

				page.setAttachmentsDir(page.getAttachmentsDir());
			}

			context.addZipEntry(path, page);
		}

		exportNode(context, nodesElement, page.getNodeId());
	}

	protected static String getNodePath(
		PortletDataContext context, WikiNode node) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.WIKI));
		sb.append("/nodes/");
		sb.append(node.getNodeId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getPageAttachementBinPath(
		PortletDataContext context, WikiPage page, String attachment) {

		StringBundler sb = new StringBundler(5);

		sb.append(context.getPortletPath(PortletKeys.WIKI));
		sb.append("/bin/");
		sb.append(page.getPageId());
		sb.append(StringPool.SLASH);
		sb.append(attachment);

		return sb.toString();
	}

	protected static String getPagePath(
		PortletDataContext context, WikiPage page) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.WIKI));
		sb.append("/pages/");
		sb.append(page.getPageId());
		sb.append(".xml");

		return sb.toString();
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		if (!context.addPrimaryKey(
				WikiPortletDataHandlerImpl.class, "deleteData")) {

			WikiNodeLocalServiceUtil.deleteNodes(context.getScopeGroupId());
		}

		return null;
	}

	protected String doExportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		context.addPermissions(
			"com.liferay.portlet.wiki", context.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("wiki-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(context.getScopeGroupId()));

		Element nodesElement = rootElement.addElement("nodes");
		Element pagesElement = rootElement.addElement("pages");

		List<WikiNode> nodes = WikiNodeUtil.findByGroupId(
			context.getScopeGroupId());

		for (WikiNode node : nodes) {
			exportNode(context, nodesElement, pagesElement, node);
		}

		return document.formattedString();
	}

	protected PortletPreferences doImportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws Exception {

		context.importPermissions(
			"com.liferay.portlet.wiki", context.getSourceGroupId(),
			context.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element nodesElement = rootElement.element("nodes");

		for (Element nodeElement : nodesElement.elements("node")) {
			String path = nodeElement.attributeValue("path");

			if (!context.isPathNotProcessed(path)) {
				continue;
			}

			WikiNode node = (WikiNode)context.getZipEntryAsObject(path);

			importNode(context, node);
		}

		Element pagesElement = rootElement.element("pages");

		for (Element pageElement : pagesElement.elements("page")) {
			String path = pageElement.attributeValue("path");

			if (!context.isPathNotProcessed(path)) {
				continue;
			}

			WikiPage page = (WikiPage)context.getZipEntryAsObject(path);

			importPage(context, pageElement, page);
		}

		Map<Long, Long> nodePKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(WikiNode.class);

		for (long nodeId : nodePKs.values()) {
			WikiCacheUtil.clearCache(nodeId);
		}

		return null;
	}

	private static final String _NAMESPACE = "wiki";

	private static PortletDataHandlerBoolean _attachments =
		new PortletDataHandlerBoolean(_NAMESPACE, "attachments");

	private static PortletDataHandlerBoolean _categories =
		new PortletDataHandlerBoolean(_NAMESPACE, "categories");

	private static PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static PortletDataHandlerBoolean _nodesAndPages =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "wikis-and-pages", true, true);

	private static PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

}