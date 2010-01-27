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

package com.liferay.portlet.wiki.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermQueryFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.wiki.DuplicateNodeNameException;
import com.liferay.portlet.wiki.NodeNameException;
import com.liferay.portlet.wiki.importers.WikiImporter;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.base.WikiNodeLocalServiceBaseImpl;
import com.liferay.portlet.wiki.util.WikiIndexer;

import java.io.File;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <a href="WikiNodeLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Charles May
 * @author Raymond Augé
 */
public class WikiNodeLocalServiceImpl extends WikiNodeLocalServiceBaseImpl {

	public WikiNode addNode(
			long userId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addNode(null, userId, name, description, serviceContext);
	}

	public WikiNode addNode(
			String uuid, long userId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Node

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		Date now = new Date();

		validate(groupId, name);

		long nodeId = counterLocalService.increment();

		WikiNode node = wikiNodePersistence.create(nodeId);

		node.setUuid(uuid);
		node.setGroupId(groupId);
		node.setCompanyId(user.getCompanyId());
		node.setUserId(user.getUserId());
		node.setUserName(user.getFullName());
		node.setCreateDate(now);
		node.setModifiedDate(now);
		node.setName(name);
		node.setDescription(description);

		try {
			wikiNodePersistence.update(node, false);
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Add failed, fetch {groupId=" + groupId + ", name=" +
						name + "}");
			}

			node = wikiNodePersistence.fetchByG_N(groupId, name, false);

			if (node == null) {
				throw se;
			}

			return node;
		}

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addNodeResources(
				node, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addNodeResources(
				node, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		return node;
	}

	public void addNodeResources(
			long nodeId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		addNodeResources(node, addCommunityPermissions, addGuestPermissions);
	}

	public void addNodeResources(
			WikiNode node, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			node.getCompanyId(), node.getGroupId(),	node.getUserId(),
			WikiNode.class.getName(), node.getNodeId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addNodeResources(
			long nodeId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		addNodeResources(node, communityPermissions, guestPermissions);
	}

	public void addNodeResources(
			WikiNode node, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			node.getCompanyId(), node.getGroupId(),	node.getUserId(),
			WikiNode.class.getName(), node.getNodeId(), communityPermissions,
			guestPermissions);
	}

	public void deleteNode(long nodeId)
		throws PortalException, SystemException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		deleteNode(node);
	}

	public void deleteNode(WikiNode node)
		throws PortalException, SystemException {

		// Indexer

		try {
			WikiIndexer.deletePages(node.getCompanyId(), node.getNodeId());
		}
		catch (SearchException se) {
			_log.error("Deleting index " + node.getNodeId(), se);
		}

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			node.getCompanyId(), WikiNode.class.getName(), node.getNodeId());

		// Pages

		wikiPageLocalService.deletePages(node.getNodeId());

		// Resources

		resourceLocalService.deleteResource(
			node.getCompanyId(), WikiNode.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, node.getNodeId());

		// Node

		wikiNodePersistence.remove(node);
	}

	public void deleteNodes(long groupId)
		throws PortalException, SystemException {

		Iterator<WikiNode> itr = wikiNodePersistence.findByGroupId(
			groupId).iterator();

		while (itr.hasNext()) {
			WikiNode node = itr.next();

			deleteNode(node);
		}
	}

	public WikiNode getNode(long nodeId)
		throws PortalException, SystemException {

		return wikiNodePersistence.findByPrimaryKey(nodeId);
	}

	public WikiNode getNode(long groupId, String nodeName)
		throws PortalException, SystemException {

		return wikiNodePersistence.findByG_N(groupId, nodeName);
	}

	public List<WikiNode> getNodes(long groupId) throws SystemException {
		return wikiNodePersistence.findByGroupId(groupId);
	}

	public List<WikiNode> getNodes(long groupId, int start, int end)
		throws SystemException {

		return wikiNodePersistence.findByGroupId(groupId, start, end);
	}

	public int getNodesCount(long groupId) throws SystemException {
		return wikiNodePersistence.countByGroupId(groupId);
	}

	public void importPages(
			long userId, long nodeId, String importer, File[] files,
			Map<String, String[]> options)
		throws PortalException, SystemException {

		WikiNode node = getNode(nodeId);

		getWikiImporter(importer).importPages(userId, node, files, options);
	}

	public void reindex(String[] ids) throws SystemException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		long companyId = GetterUtil.getLong(ids[0]);

		try {
			reindexNodes(companyId);
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public Hits search(
			long companyId, long groupId, long userId, long[] nodeIds,
			String keywords, int start, int end)
		throws SystemException {

		try {
			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create();

			contextQuery.addRequiredTerm(
				Field.PORTLET_ID, WikiIndexer.PORTLET_ID);

			if (groupId > 0) {
				Group group = groupLocalService.getGroup(groupId);

				if (group.isLayout()) {
					contextQuery.addRequiredTerm(Field.SCOPE_GROUP_ID, groupId);

					groupId = group.getParentGroupId();
				}

				contextQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			}

			if ((nodeIds != null) && (nodeIds.length > 0)) {
				BooleanQuery nodeIdsQuery = BooleanQueryFactoryUtil.create();

				for (long nodeId : nodeIds) {
					if (userId > 0) {
						try {
							wikiNodeService.getNode(nodeId);
						}
						catch (Exception e) {
							continue;
						}
					}

					TermQuery termQuery = TermQueryFactoryUtil.create(
						"nodeId", nodeId);

					nodeIdsQuery.add(termQuery, BooleanClauseOccur.SHOULD);
				}

				contextQuery.add(nodeIdsQuery, BooleanClauseOccur.MUST);
			}

			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

			if (Validator.isNotNull(keywords)) {
				searchQuery.addTerm(Field.TITLE, keywords);
				searchQuery.addTerm(Field.CONTENT, keywords);
				searchQuery.addTerm(Field.ASSET_TAG_NAMES, keywords, true);
			}

			BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

			fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

			if (searchQuery.clauses().size() > 0) {
				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			return SearchEngineUtil.search(
				companyId, groupId, userId, WikiPage.class.getName(), fullQuery,
				start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public void subscribeNode(long userId, long nodeId)
		throws PortalException, SystemException {

		subscriptionLocalService.addSubscription(
			userId, WikiNode.class.getName(), nodeId);
	}

	public void unsubscribeNode(long userId, long nodeId)
		throws PortalException, SystemException {

		subscriptionLocalService.deleteSubscription(
			userId, WikiNode.class.getName(), nodeId);
	}

	public WikiNode updateNode(long nodeId, String name, String description)
		throws PortalException, SystemException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		validate(nodeId, node.getGroupId(), name);

		node.setModifiedDate(new Date());
		node.setName(name);
		node.setDescription(description);

		wikiNodePersistence.update(node, false);

		return node;
	}

	protected WikiImporter getWikiImporter(String importer)
		throws SystemException {

		WikiImporter wikiImporter = _wikiImporters.get(importer);

		if (wikiImporter == null) {
			String importerClass = PropsUtil.get(
				PropsKeys.WIKI_IMPORTERS_CLASS, new Filter(importer));

			if (importerClass != null) {
				wikiImporter = (WikiImporter)InstancePool.get(importerClass);

				_wikiImporters.put(importer, wikiImporter);
			}

			if (importer == null) {
				throw new SystemException(
					"Unable to instantiate wiki importer class " +
						importerClass);
			}
		}

		return wikiImporter;
	}

	protected void reindexNodes(long companyId) throws SystemException {
		int nodeCount = wikiNodePersistence.countByCompanyId(companyId);

		int nodePages = nodeCount / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= nodePages; i++) {
			int nodeStart = (i * Indexer.DEFAULT_INTERVAL);
			int nodeEnd = nodeStart + Indexer.DEFAULT_INTERVAL;

			reindexNodes(companyId, nodeStart, nodeEnd);
		}
	}

	protected void reindexNodes(long companyId, int nodeStart, int nodeEnd)
		throws SystemException {

		List<WikiNode> nodes = wikiNodePersistence.findByCompanyId(
			companyId, nodeStart, nodeEnd);

		for (WikiNode node : nodes) {
			long nodeId = node.getNodeId();

			int pageCount = wikiPagePersistence.countByN_H(nodeId, true);

			int pagePages = pageCount / Indexer.DEFAULT_INTERVAL;

			for (int i = 0; i <= pagePages; i++) {
				int pageStart = (i * Indexer.DEFAULT_INTERVAL);
				int pageEnd = pageStart + Indexer.DEFAULT_INTERVAL;

				reindexPages(nodeId, pageStart, pageEnd);
			}
		}
	}

	protected void reindexPages(long nodeId, int pageStart, int pageEnd)
		throws SystemException {

		List<WikiPage> pages = wikiPagePersistence.findByN_H(
			nodeId, true, pageStart, pageEnd);

		for (WikiPage page : pages) {
			wikiPageLocalService.reindex(page);
		}
	}

	protected void validate(long groupId, String name)
		throws PortalException, SystemException {

		validate(0, groupId, name);
	}

	protected void validate(long nodeId, long groupId, String name)
		throws PortalException, SystemException {

		if (name.equalsIgnoreCase("tag")) {
			throw new NodeNameException(name + " is reserved");
		}

		if (!Validator.isName(name)) {
			throw new NodeNameException();
		}

		WikiNode node = wikiNodePersistence.fetchByG_N(groupId, name);

		if ((node != null) && (node.getNodeId() != nodeId)) {
			throw new DuplicateNodeNameException();
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(WikiNodeLocalServiceImpl.class);

	private Map<String, WikiImporter> _wikiImporters =
		new HashMap<String, WikiImporter>();

}