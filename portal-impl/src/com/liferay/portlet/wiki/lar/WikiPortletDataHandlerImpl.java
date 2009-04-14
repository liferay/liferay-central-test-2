/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.wiki.lar;

import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
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
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.persistence.WikiNodeUtil;
import com.liferay.portlet.wiki.service.persistence.WikiPageFinderUtil;
import com.liferay.portlet.wiki.service.persistence.WikiPageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * <a href="WikiPortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Jorge Ferrer
 *
 */
public class WikiPortletDataHandlerImpl extends BasePortletDataHandler {

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

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected void exportNode(
			PortletDataContext context, Element nodesEl, Element pagesEl,
			WikiNode node)
		throws PortalException, SystemException {

		if (context.isWithinDateRange(node.getModifiedDate())) {
			String path = getNodePath(context, node);

			if (context.isPathNotProcessed(path)) {
				Element nodeEl = nodesEl.addElement("node");

				nodeEl.addAttribute("path", path);

				node.setUserUuid(node.getUserUuid());

				context.addZipEntry(path, node);
			}
		}

		List<WikiPage> nodePages = WikiPageUtil.findByNodeId(node.getNodeId());

		for (WikiPage page : nodePages) {
			exportPage(context, nodesEl, pagesEl, page);
		}
	}

	protected void exportNode(
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

		context.addZipEntry(path, node);
	}

	protected void exportPage(
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

			if (context.getBooleanParameter(_NAMESPACE, "categories")) {
				context.addTagsCategories(
					WikiPage.class, page.getResourcePrimKey());
			}

			if (context.getBooleanParameter(_NAMESPACE, "comments")) {
				context.addComments(WikiPage.class, page.getResourcePrimKey());
			}

			if (context.getBooleanParameter(_NAMESPACE, "tags")) {
				context.addTagsEntries(
					WikiPage.class, page.getResourcePrimKey());
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

	protected void importNode(
			PortletDataContext context, Map<Long, Long> nodePKs, WikiNode node)
		throws Exception {

		long userId = context.getUserId(node.getUserUuid());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(context.getGroupId());

		WikiNode existingNode = null;

		if (context.getDataStrategy().equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

			existingNode = WikiNodeUtil.fetchByUUID_G(
				node.getUuid(), context.getGroupId());

			String nodeName = PropsUtil.get(PropsKeys.WIKI_INITIAL_NODE_NAME);

			if (existingNode == null && node.getName().equals(nodeName)) {
				try {
					WikiNodeUtil.removeByG_N(
						context.getGroupId(), node.getName());
				}
				catch (NoSuchNodeException nsne) {
				}
			}

			if (existingNode == null) {
				existingNode = WikiNodeLocalServiceUtil.addNode(
					node.getUuid(), userId, node.getName(),
					node.getDescription(), serviceContext);
			}
			else {
				existingNode = WikiNodeLocalServiceUtil.updateNode(
					existingNode.getNodeId(), node.getName(),
					node.getDescription());
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

			existingNode = WikiNodeLocalServiceUtil.addNode(
				userId, node.getName(), node.getDescription(), serviceContext);
		}

		nodePKs.put(node.getNodeId(), existingNode.getNodeId());
	}

	protected void importPage(
			PortletDataContext context, Map<Long, Long> nodePKs, Element pageEl,
			WikiPage page)
		throws Exception {

		long userId = context.getUserId(page.getUserUuid());
		long nodeId = MapUtil.getLong(
			nodePKs, page.getNodeId(), page.getNodeId());

		String[] tagsCategories = null;
		String[] tagsEntries = null;

		if (context.getBooleanParameter(_NAMESPACE, "categories")) {
			tagsCategories = context.getTagsCategories(
				WikiPage.class, page.getResourcePrimKey());
		}

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			tagsEntries = context.getTagsEntries(
				WikiPage.class, page.getResourcePrimKey());
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setTagsCategories(tagsCategories);
		serviceContext.setTagsEntries(tagsEntries);

		WikiPage existingPage = null;

		try {
			WikiNodeUtil.findByPrimaryKey(nodeId);

			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

				try {
					existingPage = WikiPageFinderUtil.findByUuid_G(
						page.getUuid(), context.getGroupId());

					existingPage = WikiPageLocalServiceUtil.updatePage(
						userId, nodeId, existingPage.getTitle(), 0,
						page.getContent(), page.getSummary(), true,
						page.getFormat(), page.getParentTitle(),
						page.getRedirectTitle(), serviceContext);
				}
				catch (NoSuchPageException nspe) {
					try {
						WikiPage wikiPage = WikiPageLocalServiceUtil.getPage(
							nodeId, page.getTitle());

						existingPage = WikiPageLocalServiceUtil.updatePage(
							userId, nodeId, page.getTitle(),
							wikiPage.getVersion(), page.getContent(),
							page.getSummary(), page.isMinorEdit(),
							page.getFormat(), page.getParentTitle(),
							page.getRedirectTitle(), serviceContext);
					}
					catch (NoSuchPageException nspe) {
						existingPage = WikiPageLocalServiceUtil.addPage(
							page.getUuid(), userId, nodeId, page.getTitle(),
							page.getVersion(), page.getContent(),
							page.getSummary(), true, page.getFormat(),
							page.getHead(), page.getParentTitle(),
							page.getRedirectTitle(), serviceContext);
					}
				}
			}
			else {
				existingPage = WikiPageLocalServiceUtil.addPage(
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

			if (context.getBooleanParameter(_NAMESPACE, "comments")) {
				context.importComments(
					WikiPage.class, page.getResourcePrimKey(),
					existingPage.getResourcePrimKey(), context.getGroupId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
				context.importRatingsEntries(
					WikiPage.class, page.getResourcePrimKey(),
					existingPage.getResourcePrimKey());
			}
		}
		catch (NoSuchNodeException nsne) {
			_log.error("Could not find the node for page " + page.getPageId());
		}
	}

	protected String getNodePath(PortletDataContext context, WikiNode node) {
		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.WIKI));
		sb.append("/nodes/");
		sb.append(node.getNodeId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getPageAttachementBinPath(
		PortletDataContext context, WikiPage page, String attachment) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.WIKI));
		sb.append("/bin/");
		sb.append(page.getPageId());
		sb.append(StringPool.SLASH);
		sb.append(attachment);

		return sb.toString();
	}

	protected String getPagePath(PortletDataContext context, WikiPage page) {
		StringBuilder sb = new StringBuilder();

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

	private static Log _log =
		LogFactoryUtil.getLog(WikiPortletDataHandlerImpl.class);

}