/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.DocumentUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.wiki.DuplicateNodeNameException;
import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.persistence.WikiNodeUtil;
import com.liferay.portlet.wiki.service.persistence.WikiPageFinderUtil;
import com.liferay.portlet.wiki.service.persistence.WikiPageUtil;
import com.liferay.util.MapUtil;
import com.liferay.util.xml.XMLFormatter;

import java.rmi.RemoteException;

import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * <a href="WikiPortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Jorge Ferrer
 *
 */
public class WikiPortletDataHandlerImpl implements PortletDataHandler {

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		try {

			// Nodes

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
			PortletPreferences prefs)
		throws PortletDataException {

		try {
			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("wiki-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			// Nodes

			Element nodesEl = root.addElement("nodes");

			Element pagesEl = root.addElement("pages");

			List<WikiNode> nodes = WikiNodeUtil.findByGroupId(
				context.getGroupId());

			for (WikiNode node : nodes) {
				exportNode(context, nodesEl, pagesEl, node);
			}

			return XMLFormatter.toString(doc);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {
			_nodesAndPages, _attachments, _comments, _tags
		};
	}

	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {
			_nodesAndPages, _attachments, _comments, _tags
		};
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs, String data)
		throws PortletDataException {

		try {
			Document doc = DocumentUtil.readDocumentFromXML(data);

			Element root = doc.getRootElement();

			// Nodes

			List<Element> nodeEls =
				root.element("nodes").elements("node");

			Map<Long, Long> nodePKs = context.getNewPrimaryKeysMap(
				WikiNode.class);

			for (Element el : nodeEls) {
				String path = el.attributeValue("path");

				if (context.isPathNotProcessed(path)) {
					WikiNode node =
						(WikiNode)context.getZipEntryAsObject(path);

					importNode(context, nodePKs, node);
				}
			}

			// Pages

			List<Element> pageEls =
				root.element("pages").elements("page");

			for (Element el : pageEls) {
				String path = el.attributeValue("path");

				if (context.isPathNotProcessed(path)) {
					WikiPage page =
						(WikiPage)context.getZipEntryAsObject(path);

					importPage(context, nodePKs, el, page);
				}
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public boolean isPublishToLiveByDefault() {
		return false;
	}

	protected void exportNode(
			PortletDataContext context, Element nodesEl, Element pagesEl,
			WikiNode node)
		throws PortalException, SystemException {

		if (context.isWithinDateRange(node.getModifiedDate())) {
			String path = getNodePath(context, node);

			nodesEl.addElement("node").addAttribute("path", path);

			if (context.isPathNotProcessed(path)) {
				node.setUserUuid(node.getUserUuid());

				context.addZipEntry(path, node);
			}
		}

		List<WikiPage> nodePages = WikiPageUtil.findByNodeId(
			node.getNodeId());

		for (WikiPage page : nodePages) {
			exportPage(context, nodesEl, pagesEl, page);
		}
	}

	protected void exportNode(
			PortletDataContext context, Element nodesEl, long nodeId)
		throws PortalException, SystemException {

		if (context.hasDateRange()) {
			WikiNode node = WikiNodeUtil.findByPrimaryKey(nodeId);

			String path = getNodePath(context, node);

			nodesEl.addElement("node").addAttribute("path", path);

			if (context.isPathNotProcessed(path)) {
				node.setUserUuid(node.getUserUuid());

				context.addZipEntry(path, node);
			}
		}
	}

	protected void exportPage(
			PortletDataContext context, Element nodesEl, Element pagesEl,
			WikiPage page)
		throws PortalException, SystemException {

		if (context.isWithinDateRange(page.getModifiedDate())) {
			String path = getPagePath(context, page);

			Element pageEl = pagesEl.addElement("page");
			pageEl.addAttribute("path", path);

			if (context.isPathNotProcessed(path)) {
				page.setUserUuid(page.getUserUuid());

				if (context.getBooleanParameter(_NAMESPACE, "comments")) {
					context.addComments(
						WikiPage.class,
						new Long(page.getResourcePrimKey()));
				}

				if (context.getBooleanParameter(_NAMESPACE, "tags")) {
					context.addTagsEntries(
						WikiPage.class,
						new Long(page.getResourcePrimKey()));
				}

				// Attachments

				if (context.getBooleanParameter(_NAMESPACE, "attachments") &&
						page.isHead()) {

					for (String attachment : page.getAttachmentsFiles()) {
						int lf = attachment.lastIndexOf(CharPool.FORWARD_SLASH);

						String attachementName = attachment.substring(
							(lf+1), attachment.length());

						String attachementPath = getPageAttachementBinPath(
							context, attachementName, page);

						Element attachementEl = pageEl.addElement("attachement");
						attachementEl.addAttribute("name", attachementName);
						attachementEl.addAttribute("bin-path", attachementPath);

						try {
							byte[] byteArray = DLServiceUtil.getFile(
								context.getCompanyId(), CompanyConstants.SYSTEM,
								attachment);

							context.addZipEntry(
								attachementPath, byteArray);
						}
						catch (RemoteException re) {
						}
					}

					page.setAttachmentsDir(page.getAttachmentsDir());
				}

				context.addZipEntry(path, page);
			}

			exportNode(context, nodesEl, page.getNodeId());
		}
	}

	protected void importNode(
			PortletDataContext context, Map<Long, Long> nodePKs, WikiNode node)
		throws Exception {

		long userId = context.getUserId(node.getUserUuid());
		long plid = context.getPlid();

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		WikiNode existingNode = null;

		if (context.getDataStrategy().equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

			existingNode = WikiNodeUtil.fetchByUUID_G(
				node.getUuid(), context.getGroupId());

			String nodeName = PropsUtil.get(PropsUtil.WIKI_INITIAL_NODE_NAME);

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
					node.getUuid(), userId, plid, node.getName(),
					node.getDescription(), addCommunityPermissions,
					addGuestPermissions);
			}
			else {
				existingNode = WikiNodeLocalServiceUtil.updateNode(
					existingNode.getNodeId(), node.getName(),
					node.getDescription());
			}
		}
		else {
			String nodeName = PropsUtil.get(PropsUtil.WIKI_INITIAL_NODE_NAME);

			if (node.getName().equals(nodeName)) {
				try {
					WikiNodeUtil.removeByG_N(
						context.getGroupId(), node.getName());
				}
				catch (NoSuchNodeException nsne) {
				}
			}

			existingNode = WikiNodeLocalServiceUtil.addNode(
				userId, plid, node.getName(), node.getDescription(),
				addCommunityPermissions, addGuestPermissions);
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

		String[] tagsEntries = null;

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			tagsEntries = context.getTagsEntries(
				WikiPage.class, page.getResourcePrimKey());
		}

		PortletPreferences prefs = null;

		ThemeDisplay themeDisplay = null;

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
						page.getContent(), page.getFormat(),
						page.getParentTitle(), page.getRedirectTitle(),
						tagsEntries, prefs, themeDisplay);
				}
				catch (NoSuchPageException nspe) {
					existingPage = WikiPageLocalServiceUtil.addPage(
						page.getUuid(), userId, nodeId, page.getTitle(),
						page.getVersion(), page.getContent(), page.getFormat(),
						page.getHead(), page.getParentTitle(),
						page.getRedirectTitle(), tagsEntries, prefs,
						themeDisplay);
				}
			}
			else {
				existingPage = WikiPageLocalServiceUtil.addPage(
					null, userId, nodeId, page.getTitle(), page.getVersion(),
					page.getContent(), page.getFormat(), page.getHead(),
					page.getParentTitle(), page.getRedirectTitle(), tagsEntries,
					prefs, themeDisplay);
			}

			if (context.getBooleanParameter(_NAMESPACE, "attachments") &&
				page.isHead()) {

				List<Element> attachementEls = pageEl.elements("attachement");

				List<ObjectValuePair<String, byte[]>> files =
					new ArrayList<ObjectValuePair<String, byte[]>>();

				for (Element attachementEl : attachementEls) {
					String name = attachementEl.attributeValue("name");
					String attachementPath = attachementEl.attributeValue(
						"bin-path");

					byte[] bytes = context.getZipEntryAsByteArray(
						attachementPath);

					files.add(new ObjectValuePair<String, byte[]>(name, bytes));
				}

				if (files.size() > 0) {
					WikiPageLocalServiceUtil.addPageAttachments(
						nodeId, page.getTitle(), files);
				}
			}

			if (context.getBooleanParameter(_NAMESPACE, "comments")) {
				context.importComments(
					WikiPage.class, new Long(page.getResourcePrimKey()),
					new Long(existingPage.getResourcePrimKey()),
					context.getGroupId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
				context.importRatingsEntries(
					WikiPage.class, new Long(page.getResourcePrimKey()),
					new Long(existingPage.getResourcePrimKey()));
			}
		}
		catch (NoSuchNodeException nsne) {
			_log.error(
				"Could not find the node for page " +
					page.getPageId());
		}
	}

	protected String getNodePath(
			PortletDataContext context, WikiNode node) {
		return context.getPortletPath(PortletKeys.WIKI) +
			"/nodes/" + node.getNodeId() + ".xml";
	}

	protected String getPageAttachementBinPath(
			PortletDataContext context, String attachment, WikiPage page) {
		return context.getPortletPath(PortletKeys.WIKI) +
			"/bin/" + page.getPageId() + CharPool.FORWARD_SLASH +
				attachment;
	}

	protected String getPagePath(
			PortletDataContext context, WikiPage page) {
		return context.getPortletPath(PortletKeys.WIKI) +
			"/pages/" + page.getPageId() + ".xml";
	}

	private static final String _NAMESPACE = "wiki";

	private static final PortletDataHandlerBoolean _nodesAndPages =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "wikis-and-pages", true, true);

	private static final PortletDataHandlerBoolean _attachments =
		new PortletDataHandlerBoolean(_NAMESPACE, "attachments");

	private static final PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static final PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

	private static Log _log =
		LogFactory.getLog(WikiPortletDataHandlerImpl.class);

}