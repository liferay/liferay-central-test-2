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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.lar.BasePortletDataHandler;
import com.liferay.portal.lar.PortletDataContext;
import com.liferay.portal.lar.PortletDataException;
import com.liferay.portal.lar.PortletDataHandlerBoolean;
import com.liferay.portal.lar.PortletDataHandlerControl;
import com.liferay.portal.lar.PortletDataHandlerKeys;
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
import com.liferay.portlet.wiki.util.comparator.PageCreateDateComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * <a href="WikiPortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Jorge Ferrer
 * @author Marcellus Tavares
 */
public class WikiPortletDataHandlerImpl extends BasePortletDataHandler {

	public static void exportNode(
			PortletDataContext context, Element nodesEl, Element pagesEl,
			WikiNode node)
		throws PortalException, SystemException {

		if (context.isWithinDateRange(node.getModifiedDate())) {
			String path = getNodePath(context, node);

			if (context.isPathNotProcessed(path)) {
				Element nodeEl = nodesEl.addElement("node");

				nodeEl.addAttribute("path", path);

				node.setUserUuid(node.getUserUuid());

				context.addPermissions(WikiNode.class, node.getNodeId());

				context.addZipEntry(path, node);
			}
		}

		List<WikiPage> nodePages = WikiPageUtil.findByNodeId(
			node.getNodeId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new PageCreateDateComparator(true));

		for (WikiPage page : nodePages) {
			exportPage(context, nodesEl, pagesEl, page);
		}
	}

	public static void importNode(
			PortletDataContext context, Map<Long, Long> nodePKs, WikiNode node)
		throws Exception {

		long userId = context.getUserId(node.getUserUuid());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(node.getCreateDate());
		serviceContext.setModifiedDate(node.getModifiedDate());
		serviceContext.setScopeGroupId(context.getGroupId());

		WikiNode importedNode = null;

		if (context.getDataStrategy().equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

			WikiNode existingNode = WikiNodeUtil.fetchByUUID_G(
				node.getUuid(), context.getGroupId());

			String nodeName = PropsUtil.get(PropsKeys.WIKI_INITIAL_NODE_NAME);

			if ((existingNode == null) && node.getName().equals(nodeName)) {
				try {
					WikiNodeUtil.removeByG_N(
						context.getGroupId(), node.getName());
				}
				catch (NoSuchNodeException nsne) {
				}
			}

			if (existingNode == null) {
				importedNode = WikiNodeLocalServiceUtil.addNode(
					node.getUuid(), userId, node.getName(),
					node.getDescription(), serviceContext);
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
						context.getGroupId(), node.getName());
				}
				catch (NoSuchNodeException nsne) {
				}
			}

			importedNode = WikiNodeLocalServiceUtil.addNode(
				userId, node.getName(), node.getDescription(), serviceContext);
		}

		nodePKs.put(node.getNodeId(), importedNode.getNodeId());

		context.importPermissions(
			WikiNode.class, node.getNodeId(), importedNode.getNodeId());
	}

	public static void importPage(
			PortletDataContext context, Map<Long, Long> nodePKs, Element pageEl,
			WikiPage page)
		throws Exception {

		long userId = context.getUserId(page.getUserUuid());
		long nodeId = MapUtil.getLong(
			nodePKs, page.getNodeId(), page.getNodeId());

		long[] assetCategoryIds = null;
		String[] assetTagNames = null;

		if (context.getBooleanParameter(_NAMESPACE, "categories")) {
			assetCategoryIds = context.getAssetCategoryIds(
				WikiPage.class, page.getResourcePrimKey());
		}

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
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

		WikiPage importedPage = null;

		try {
			WikiNodeUtil.findByPrimaryKey(nodeId);

			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

				WikiPage existingPage = WikiPageUtil.fetchByUUID_G(
					page.getUuid(), context.getGroupId());

				if (existingPage == null) {
					try {
						existingPage = WikiPageLocalServiceUtil.getPage(
							nodeId, page.getTitle());
					}
					catch (NoSuchPageException nspe) {
					}
				}

				if (existingPage == null) {
					importedPage = WikiPageLocalServiceUtil.addPage(
						page.getUuid(), userId, nodeId, page.getTitle(),
						page.getVersion(), page.getContent(), page.getSummary(),
						true, page.getFormat(), page.getHead(),
						page.getParentTitle(), page.getRedirectTitle(),
						serviceContext);
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
					null, userId, nodeId, page.getTitle(), page.getVersion(),
					page.getContent(), page.getSummary(), true,
					page.getFormat(), page.getHead(), page.getParentTitle(),
					page.getRedirectTitle(), serviceContext);
			}

			if (context.getBooleanParameter(_NAMESPACE, "attachments") &&
				page.isHead()) {

				List<Element> attachmentEls = pageEl.elements("attachment");

				List<ObjectValuePair<String, byte[]>> files =
					new ArrayList<ObjectValuePair<String, byte[]>>();

				for (Element attachmentEl : attachmentEls) {
					String name = attachmentEl.attributeValue("name");
					String binPath = attachmentEl.attributeValue("bin-path");

					byte[] bytes = context.getZipEntryAsByteArray(binPath);

					files.add(new ObjectValuePair<String, byte[]>(name, bytes));
				}

				if (files.size() > 0) {
					WikiPageLocalServiceUtil.addPageAttachments(
						nodeId, page.getTitle(), files);
				}
			}

			context.importPermissions(
				WikiPage.class, page.getResourcePrimKey(),
				importedPage.getResourcePrimKey());

			if (context.getBooleanParameter(_NAMESPACE, "comments") &&
				page.isHead()) {

				context.importComments(
					WikiPage.class, page.getResourcePrimKey(),
					importedPage.getResourcePrimKey(), context.getGroupId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
				context.importRatingsEntries(
					WikiPage.class, page.getResourcePrimKey(),
					importedPage.getResourcePrimKey());
			}
		}
		catch (NoSuchNodeException nsne) {
			_log.error("Could not find the node for page " + page.getPageId());
		}
	}

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			if (!context.addPrimaryKey(
					WikiPortletDataHandlerImpl.class, "deleteData")) {

				WikiNodeLocalServiceUtil.deleteNodes(context.getGroupId());
			}

			return null;
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
			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("wiki-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			Element nodesEl = root.addElement("nodes");
			Element pagesEl = root.addElement("pages");

			List<WikiNode> nodes = WikiNodeUtil.findByGroupId(
				context.getGroupId());

			for (WikiNode node : nodes) {
				exportNode(context, nodesEl, pagesEl, node);
			}

			context.addPermissions(
				"com.liferay.portlet.wiki", context.getGroupId());

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

				importNode(context, nodePKs, node);
			}

			List<Element> pageEls = root.element("pages").elements("page");

			for (Element pageEl : pageEls) {
				String path = pageEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				WikiPage page = (WikiPage)context.getZipEntryAsObject(path);

				importPage(context, nodePKs, pageEl, page);
			}

			context.importPermissions(
				"com.liferay.portlet.wiki", context.getSourceGroupId(),
				context.getGroupId());

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected static void exportNode(
			PortletDataContext context, Element nodesEl, long nodeId)
		throws PortalException, SystemException {

		if (!context.hasDateRange()) {
			return;
		}

		WikiNode node = WikiNodeUtil.findByPrimaryKey(nodeId);

		String path = getNodePath(context, node);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element nodeEl = nodesEl.addElement("node");

		nodeEl.addAttribute("path", path);

		node.setUserUuid(node.getUserUuid());

		context.addPermissions(WikiNode.class, node.getNodeId());

		context.addZipEntry(path, node);
	}

	protected static void exportPage(
			PortletDataContext context, Element nodesEl, Element pagesEl,
			WikiPage page)
		throws PortalException, SystemException {

		if (!context.isWithinDateRange(page.getModifiedDate())) {
			return;
		}

		String path = getPagePath(context, page);

		if (context.isPathNotProcessed(path)) {
			Element pageEl = pagesEl.addElement("page");

			pageEl.addAttribute("path", path);

			page.setUserUuid(page.getUserUuid());

			context.addPermissions(WikiPage.class, page.getResourcePrimKey());

			if (context.getBooleanParameter(_NAMESPACE, "categories")) {
				context.addAssetCategories(
					WikiPage.class, page.getResourcePrimKey());
			}

			if (context.getBooleanParameter(_NAMESPACE, "comments")) {
				context.addComments(WikiPage.class, page.getResourcePrimKey());
			}

			if (context.getBooleanParameter(_NAMESPACE, "tags")) {
				context.addAssetTags(WikiPage.class, page.getResourcePrimKey());
			}

			if (context.getBooleanParameter(_NAMESPACE, "attachments") &&
				page.isHead()) {

				for (String attachment : page.getAttachmentsFiles()) {
					int pos = attachment.lastIndexOf(StringPool.SLASH);

					String name = attachment.substring(pos + 1);
					String binPath = getPageAttachementBinPath(
						context, page, name);

					Element attachmentEl = pageEl.addElement("attachment");

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

		exportNode(context, nodesEl, page.getNodeId());
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

	private static final String _NAMESPACE = "wiki";

	private static final PortletDataHandlerBoolean _nodesAndPages =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "wikis-and-pages", true, true);

	private static final PortletDataHandlerBoolean _attachments =
		new PortletDataHandlerBoolean(_NAMESPACE, "attachments");

	private static final PortletDataHandlerBoolean _categories =
		new PortletDataHandlerBoolean(_NAMESPACE, "categories");

	private static final PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static final PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

	private static Log _log = LogFactoryUtil.getLog(
		WikiPortletDataHandlerImpl.class);

}