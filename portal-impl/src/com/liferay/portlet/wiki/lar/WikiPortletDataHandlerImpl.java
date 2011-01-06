/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.documentlibrary.service.DLLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.documentlibrary.lar.DLPortletDataHandlerImpl;
import com.liferay.portlet.imagegallery.lar.IGPortletDataHandlerImpl;
import com.liferay.portlet.journal.lar.JournalPortletDataHandlerImpl;
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
			PortletDataContext portletDataContext, Element nodesElement,
			Element pagesElement, WikiNode node)
		throws Exception {

		if (portletDataContext.isWithinDateRange(node.getModifiedDate())) {
			String path = getNodePath(portletDataContext, node);

			if (portletDataContext.isPathNotProcessed(path)) {
				Element nodeElement = nodesElement.addElement("node");

				nodeElement.addAttribute("path", path);

				node.setUserUuid(node.getUserUuid());

				portletDataContext.addPermissions(
					WikiNode.class, node.getNodeId());

				portletDataContext.addZipEntry(path, node);
			}
		}

		List<WikiPage> pages = WikiPageUtil.findByN_S(
			node.getNodeId(), WorkflowConstants.STATUS_APPROVED,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new PageVersionComparator(true));

		Element dlFoldersElement = pagesElement.addElement("dl-folders");
		Element dlFileEntriesElement = pagesElement.addElement(
				"dl-file-entries");
		Element dlFileRanksElement = pagesElement.addElement("dl-file-ranks");
		Element igFoldersElement = pagesElement.addElement("ig-folders");
		Element igImagesElement = pagesElement.addElement("ig-images");

		for (WikiPage page : pages) {
			exportPage(
				portletDataContext, nodesElement, pagesElement,
				dlFoldersElement, dlFileEntriesElement, dlFileRanksElement,
				igFoldersElement, igImagesElement, page, true);
		}
	}

	public static void importNode(
			PortletDataContext portletDataContext, WikiNode node)
		throws Exception {

		long userId = portletDataContext.getUserId(node.getUserUuid());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(node.getCreateDate());
		serviceContext.setModifiedDate(node.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		WikiNode importedNode = null;

		if (portletDataContext.isDataStrategyMirror()) {
			WikiNode existingNode = WikiNodeUtil.fetchByUUID_G(
				node.getUuid(), portletDataContext.getScopeGroupId());

			String nodeName = PropsUtil.get(PropsKeys.WIKI_INITIAL_NODE_NAME);

			if ((existingNode == null) && node.getName().equals(nodeName)) {
				try {
					WikiNodeUtil.removeByG_N(
						portletDataContext.getScopeGroupId(), node.getName());
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
						portletDataContext.getScopeGroupId(), node.getName());
				}
				catch (NoSuchNodeException nsne) {
				}
			}

			importedNode = WikiNodeLocalServiceUtil.addNode(
				userId, node.getName(), node.getDescription(), serviceContext);
		}

		Map<Long, Long> nodePKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				WikiNode.class);

		nodePKs.put(node.getNodeId(), importedNode.getNodeId());

		portletDataContext.importPermissions(
			WikiNode.class, node.getNodeId(), importedNode.getNodeId());
	}

	public static void importPage(
			PortletDataContext portletDataContext, Element pageElement,
			WikiPage page)
		throws Exception {

		long userId = portletDataContext.getUserId(page.getUserUuid());

		Map<Long, Long> nodePKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				WikiNode.class);

		long nodeId = MapUtil.getLong(
			nodePKs, page.getNodeId(), page.getNodeId());

		String content = page.getContent();

		content = JournalPortletDataHandlerImpl.importDLFileEntries(
			portletDataContext, pageElement, content);
		content = JournalPortletDataHandlerImpl.importIGImages(
			portletDataContext, pageElement, content);

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		content = StringUtil.replace(
			content, "@data_handler_group_friendly_url@",
			group.getFriendlyURL());

		content = JournalPortletDataHandlerImpl.importLinksToLayout(
			portletDataContext, content);

		page.setContent(content);

		long[] assetCategoryIds = null;
		String[] assetTagNames = null;

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "categories") &&
			page.isHead()) {

			assetCategoryIds = portletDataContext.getAssetCategoryIds(
				WikiPage.class, page.getResourcePrimKey());
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "tags") &&
			page.isHead()) {

			assetTagNames = portletDataContext.getAssetTagNames(
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

		if (portletDataContext.isDataStrategyMirror()) {
			WikiPage existingPage = WikiPageUtil.fetchByUUID_G(
				page.getUuid(), portletDataContext.getScopeGroupId());

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

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "attachments") &&
			page.isHead()) {

			for (Element attachmentElement :
					pageElement.elements("attachment")) {

				String name = attachmentElement.attributeValue("name");
				String binPath = attachmentElement.attributeValue("bin-path");

				InputStream inputStream =
					portletDataContext.getZipEntryAsInputStream(binPath);

				WikiPageLocalServiceUtil.addPageAttachment(
					importedPage.getCompanyId(),
					importedPage.getAttachmentsDir(),
					importedPage.getModifiedDate(), name, inputStream);
			}
		}

		if (page.isHead()) {
			portletDataContext.importPermissions(
				WikiPage.class, page.getResourcePrimKey(),
				importedPage.getResourcePrimKey());
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "comments") &&
			page.isHead()) {

			portletDataContext.importComments(
				WikiPage.class, page.getResourcePrimKey(),
				importedPage.getResourcePrimKey(),
				portletDataContext.getScopeGroupId());
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "ratings") &&
			page.isHead()) {

			portletDataContext.importRatingsEntries(
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
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws PortletDataException {

		WikiCacheThreadLocal.setClearCache(false);

		try {
			return super.importData(
				portletDataContext, portletId, portletPreferences, data);
		}
		finally {
			WikiCacheThreadLocal.setClearCache(true);
		}
	}

	protected static void exportNode(
			PortletDataContext portletDataContext, Element nodesElement,
			long nodeId)
		throws Exception {

		if (!portletDataContext.hasDateRange()) {
			return;
		}

		WikiNode node = WikiNodeUtil.findByPrimaryKey(nodeId);

		String path = getNodePath(portletDataContext, node);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element nodeElement = nodesElement.addElement("node");

		nodeElement.addAttribute("path", path);

		node.setUserUuid(node.getUserUuid());

		portletDataContext.addPermissions(WikiNode.class, node.getNodeId());

		portletDataContext.addZipEntry(path, node);
	}

	protected static void exportPage(
			PortletDataContext portletDataContext, Element nodesElement,
			Element pagesElement, Element dlFoldersElement,
			Element dlFileEntriesElement, Element dlFileRanksElement,
			Element igFoldersElement, Element igImagesElement,
			WikiPage page, boolean checkDateRange)
		throws Exception {

		if (!portletDataContext.isWithinDateRange(page.getModifiedDate())) {
			return;
		}

		String path = getPagePath(portletDataContext, page);

		// Clone this page to make sure changes to its content are never
		// persisted

		page = (WikiPage)page.clone();

		Element pageElement = (Element)pagesElement.selectSingleNode(
				"//page[@path='".concat(path).concat("']"));

		if (portletDataContext.isPathNotProcessed(path)) {

			if (pageElement == null) {
				pageElement = pagesElement.addElement("page");
			}

			pageElement.addAttribute("path", path);

			page.setUserUuid(page.getUserUuid());

			String imagePath = getPageImagePath(portletDataContext, page);

			pageElement.addAttribute("image-path", imagePath);

			String content = page.getContent();

			content = JournalPortletDataHandlerImpl.exportDLFileEntries(
				portletDataContext, dlFoldersElement, dlFileEntriesElement,
				dlFileRanksElement, pageElement, content, checkDateRange);
			content = JournalPortletDataHandlerImpl.exportIGImages(
				portletDataContext, igFoldersElement, igImagesElement,
				pageElement, content, checkDateRange);
			content = JournalPortletDataHandlerImpl.exportLayoutFriendlyURLs(
				portletDataContext, content);
			content = JournalPortletDataHandlerImpl.exportLinksToLayout(
				portletDataContext, content);

			page.setContent(content);

			portletDataContext.addPermissions(
				WikiPage.class, page.getResourcePrimKey());

			if (portletDataContext.getBooleanParameter(
					_NAMESPACE, "categories") &&
				page.isHead()) {

				portletDataContext.addAssetCategories(
					WikiPage.class, page.getResourcePrimKey());
			}

			if (portletDataContext.getBooleanParameter(
					_NAMESPACE, "comments") &&
				page.isHead()) {

				portletDataContext.addComments(
					WikiPage.class, page.getResourcePrimKey());
			}

			if (portletDataContext.getBooleanParameter(_NAMESPACE, "ratings") &&
				page.isHead()) {

				portletDataContext.addRatingsEntries(
					WikiPage.class, page.getResourcePrimKey());
			}

			if (portletDataContext.getBooleanParameter(_NAMESPACE, "tags") &&
				page.isHead()) {

				portletDataContext.addAssetTags(
					WikiPage.class, page.getResourcePrimKey());
			}

			if (portletDataContext.getBooleanParameter(
					_NAMESPACE, "attachments") &&
				page.isHead()) {

				for (String attachment : page.getAttachmentsFiles()) {
					int pos = attachment.lastIndexOf(CharPool.SLASH);

					String name = attachment.substring(pos + 1);
					String binPath = getPageAttachementBinPath(
						portletDataContext, page, name);

					Element attachmentEl = pageElement.addElement("attachment");

					attachmentEl.addAttribute("name", name);
					attachmentEl.addAttribute("bin-path", binPath);

					byte[] bytes = DLLocalServiceUtil.getFile(
						portletDataContext.getCompanyId(),
						CompanyConstants.SYSTEM, attachment);

					portletDataContext.addZipEntry(binPath, bytes);
				}

				page.setAttachmentsDir(page.getAttachmentsDir());
			}

			portletDataContext.addZipEntry(path, page);
		}

		exportNode(portletDataContext, nodesElement, page.getNodeId());
	}

	protected static String getNodePath(
		PortletDataContext portletDataContext, WikiNode node) {

		StringBundler sb = new StringBundler(4);

		sb.append(portletDataContext.getPortletPath(PortletKeys.WIKI));
		sb.append("/nodes/");
		sb.append(node.getNodeId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getPageAttachementBinPath(
		PortletDataContext portletDataContext, WikiPage page,
		String attachment) {

		StringBundler sb = new StringBundler(5);

		sb.append(portletDataContext.getPortletPath(PortletKeys.WIKI));
		sb.append("/bin/");
		sb.append(page.getPageId());
		sb.append(StringPool.SLASH);
		sb.append(attachment);

		return sb.toString();
	}

	protected static String getPageImagePath(
			PortletDataContext portletDataContext, WikiPage page)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append(portletDataContext.getPortletPath(PortletKeys.WIKI));
		sb.append("/page/");
		sb.append(page.getUuid());
		sb.append(StringPool.SLASH);
		sb.append(page.getVersion());
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected static String getPagePath(
		PortletDataContext portletDataContext, WikiPage page) {

		StringBundler sb = new StringBundler(4);

		sb.append(portletDataContext.getPortletPath(PortletKeys.WIKI));
		sb.append("/pages/");
		sb.append(page.getPageId());
		sb.append(".xml");

		return sb.toString();
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (!portletDataContext.addPrimaryKey(
				WikiPortletDataHandlerImpl.class, "deleteData")) {

			WikiNodeLocalServiceUtil.deleteNodes(
				portletDataContext.getScopeGroupId());
		}

		return null;
	}

	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.wiki", portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("wiki-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		Element nodesElement = rootElement.addElement("nodes");
		Element pagesElement = rootElement.addElement("pages");

		List<WikiNode> nodes = WikiNodeUtil.findByGroupId(
			portletDataContext.getScopeGroupId());

		for (WikiNode node : nodes) {
			exportNode(portletDataContext, nodesElement, pagesElement, node);
		}

		return document.formattedString();
	}

	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.wiki", portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element nodesElement = rootElement.element("nodes");

		for (Element nodeElement : nodesElement.elements("node")) {
			String path = nodeElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			WikiNode node = (WikiNode)portletDataContext.getZipEntryAsObject(
				path);

			importNode(portletDataContext, node);
		}

		Element pagesElement = rootElement.element("pages");

		Element dlFoldersElement = pagesElement.element("dl-folders");

		List<Element> dlFolderElements = dlFoldersElement.elements("folder");

		for (Element dlFolderElement : dlFolderElements) {
			DLPortletDataHandlerImpl.importFolder(
				portletDataContext, dlFolderElement);
		}

		Element dlFileEntriesElement = pagesElement.element("dl-file-entries");

		List<Element> dlFileEntryElements = dlFileEntriesElement.elements(
			"file-entry");

		for (Element dlFileEntryElement : dlFileEntryElements) {
			DLPortletDataHandlerImpl.importFileEntry(
				portletDataContext, dlFileEntryElement);
		}

		Element dlFileRanksElement = pagesElement.element("dl-file-ranks");

		List<Element> dlFileRankElements = dlFileRanksElement.elements(
			"file-rank");

		for (Element dlFileRankElement : dlFileRankElements) {
			DLPortletDataHandlerImpl.importFileRank(
				portletDataContext, dlFileRankElement);
		}

		Element igFoldersElement = pagesElement.element("ig-folders");

		List<Element> igFolderElements = igFoldersElement.elements("folder");

		for (Element igFolderElement : igFolderElements) {
			IGPortletDataHandlerImpl.importFolder(
				portletDataContext, igFolderElement);
		}

		Element igImagesElement = pagesElement.element("ig-images");

		List<Element> igImageElements = igImagesElement.elements("image");

		for (Element igImageElement : igImageElements) {
			IGPortletDataHandlerImpl.importImage(
				portletDataContext, igImageElement);
		}

		for (Element pageElement : pagesElement.elements("page")) {
			String path = pageElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			WikiPage page = (WikiPage)portletDataContext.getZipEntryAsObject(
				path);

			importPage(portletDataContext, pageElement, page);
		}

		Map<Long, Long> nodePKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				WikiNode.class);

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