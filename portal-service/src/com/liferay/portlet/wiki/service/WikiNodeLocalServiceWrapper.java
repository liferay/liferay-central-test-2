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

package com.liferay.portlet.wiki.service;


/**
 * <a href="WikiNodeLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link WikiNodeLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiNodeLocalService
 * @generated
 */
public class WikiNodeLocalServiceWrapper implements WikiNodeLocalService {
	public WikiNodeLocalServiceWrapper(
		WikiNodeLocalService wikiNodeLocalService) {
		_wikiNodeLocalService = wikiNodeLocalService;
	}

	public com.liferay.portlet.wiki.model.WikiNode addWikiNode(
		com.liferay.portlet.wiki.model.WikiNode wikiNode)
		throws com.liferay.portal.SystemException {
		return _wikiNodeLocalService.addWikiNode(wikiNode);
	}

	public com.liferay.portlet.wiki.model.WikiNode createWikiNode(long nodeId) {
		return _wikiNodeLocalService.createWikiNode(nodeId);
	}

	public void deleteWikiNode(long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_wikiNodeLocalService.deleteWikiNode(nodeId);
	}

	public void deleteWikiNode(com.liferay.portlet.wiki.model.WikiNode wikiNode)
		throws com.liferay.portal.SystemException {
		_wikiNodeLocalService.deleteWikiNode(wikiNode);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _wikiNodeLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _wikiNodeLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.wiki.model.WikiNode getWikiNode(long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _wikiNodeLocalService.getWikiNode(nodeId);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> getWikiNodes(
		int start, int end) throws com.liferay.portal.SystemException {
		return _wikiNodeLocalService.getWikiNodes(start, end);
	}

	public int getWikiNodesCount() throws com.liferay.portal.SystemException {
		return _wikiNodeLocalService.getWikiNodesCount();
	}

	public com.liferay.portlet.wiki.model.WikiNode updateWikiNode(
		com.liferay.portlet.wiki.model.WikiNode wikiNode)
		throws com.liferay.portal.SystemException {
		return _wikiNodeLocalService.updateWikiNode(wikiNode);
	}

	public com.liferay.portlet.wiki.model.WikiNode updateWikiNode(
		com.liferay.portlet.wiki.model.WikiNode wikiNode, boolean merge)
		throws com.liferay.portal.SystemException {
		return _wikiNodeLocalService.updateWikiNode(wikiNode, merge);
	}

	public com.liferay.portlet.wiki.model.WikiNode addNode(long userId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _wikiNodeLocalService.addNode(userId, name, description,
			serviceContext);
	}

	public com.liferay.portlet.wiki.model.WikiNode addNode(
		java.lang.String uuid, long userId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _wikiNodeLocalService.addNode(uuid, userId, name, description,
			serviceContext);
	}

	public void addNodeResources(long nodeId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_wikiNodeLocalService.addNodeResources(nodeId, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addNodeResources(com.liferay.portlet.wiki.model.WikiNode node,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_wikiNodeLocalService.addNodeResources(node, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addNodeResources(long nodeId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_wikiNodeLocalService.addNodeResources(nodeId, communityPermissions,
			guestPermissions);
	}

	public void addNodeResources(com.liferay.portlet.wiki.model.WikiNode node,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_wikiNodeLocalService.addNodeResources(node, communityPermissions,
			guestPermissions);
	}

	public void deleteNode(long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_wikiNodeLocalService.deleteNode(nodeId);
	}

	public void deleteNode(com.liferay.portlet.wiki.model.WikiNode node)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_wikiNodeLocalService.deleteNode(node);
	}

	public void deleteNodes(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_wikiNodeLocalService.deleteNodes(groupId);
	}

	public com.liferay.portlet.wiki.model.WikiNode getNode(long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _wikiNodeLocalService.getNode(nodeId);
	}

	public com.liferay.portlet.wiki.model.WikiNode getNode(long groupId,
		java.lang.String nodeName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _wikiNodeLocalService.getNode(groupId, nodeName);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> getNodes(
		long groupId) throws com.liferay.portal.SystemException {
		return _wikiNodeLocalService.getNodes(groupId);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> getNodes(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _wikiNodeLocalService.getNodes(groupId, start, end);
	}

	public int getNodesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return _wikiNodeLocalService.getNodesCount(groupId);
	}

	public void importPages(long userId, long nodeId,
		java.lang.String importer, java.io.File[] files,
		java.util.Map<String, String[]> options)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_wikiNodeLocalService.importPages(userId, nodeId, importer, files,
			options);
	}

	public void reindex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		_wikiNodeLocalService.reindex(ids);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, long userId, long[] nodeIds, java.lang.String keywords,
		int start, int end) throws com.liferay.portal.SystemException {
		return _wikiNodeLocalService.search(companyId, groupId, userId,
			nodeIds, keywords, start, end);
	}

	public void subscribeNode(long userId, long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_wikiNodeLocalService.subscribeNode(userId, nodeId);
	}

	public void unsubscribeNode(long userId, long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_wikiNodeLocalService.unsubscribeNode(userId, nodeId);
	}

	public com.liferay.portlet.wiki.model.WikiNode updateNode(long nodeId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _wikiNodeLocalService.updateNode(nodeId, name, description);
	}

	public WikiNodeLocalService getWrappedWikiNodeLocalService() {
		return _wikiNodeLocalService;
	}

	private WikiNodeLocalService _wikiNodeLocalService;
}