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
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.addWikiNode(wikiNode);
	}

	public com.liferay.portlet.wiki.model.WikiNode createWikiNode(long nodeId) {
		return _wikiNodeLocalService.createWikiNode(nodeId);
	}

	public void deleteWikiNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.deleteWikiNode(nodeId);
	}

	public void deleteWikiNode(com.liferay.portlet.wiki.model.WikiNode wikiNode)
		throws com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.deleteWikiNode(wikiNode);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.wiki.model.WikiNode getWikiNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getWikiNode(nodeId);
	}

	public com.liferay.portlet.wiki.model.WikiNode getWikiNodeByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getWikiNodeByUuidAndGroupId(uuid, groupId);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> getWikiNodes(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getWikiNodes(start, end);
	}

	public int getWikiNodesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getWikiNodesCount();
	}

	public com.liferay.portlet.wiki.model.WikiNode updateWikiNode(
		com.liferay.portlet.wiki.model.WikiNode wikiNode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.updateWikiNode(wikiNode);
	}

	public com.liferay.portlet.wiki.model.WikiNode updateWikiNode(
		com.liferay.portlet.wiki.model.WikiNode wikiNode, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.updateWikiNode(wikiNode, merge);
	}

	public com.liferay.portlet.wiki.model.WikiNode addNode(long userId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.addNode(userId, name, description,
			serviceContext);
	}

	public com.liferay.portlet.wiki.model.WikiNode addNode(
		java.lang.String uuid, long userId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.addNode(uuid, userId, name, description,
			serviceContext);
	}

	public void addNodeResources(long nodeId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.addNodeResources(nodeId, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addNodeResources(com.liferay.portlet.wiki.model.WikiNode node,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.addNodeResources(node, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addNodeResources(long nodeId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.addNodeResources(nodeId, communityPermissions,
			guestPermissions);
	}

	public void addNodeResources(com.liferay.portlet.wiki.model.WikiNode node,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.addNodeResources(node, communityPermissions,
			guestPermissions);
	}

	public void deleteNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.deleteNode(nodeId);
	}

	public void deleteNode(com.liferay.portlet.wiki.model.WikiNode node)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.deleteNode(node);
	}

	public void deleteNodes(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.deleteNodes(groupId);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> getCompanyNodes(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getCompanyNodes(companyId, start, end);
	}

	public int getCompanyNodesCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getCompanyNodesCount(companyId);
	}

	public com.liferay.portlet.wiki.model.WikiNode getNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getNode(nodeId);
	}

	public com.liferay.portlet.wiki.model.WikiNode getNode(long groupId,
		java.lang.String nodeName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getNode(groupId, nodeName);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> getNodes(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getNodes(groupId);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> getNodes(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getNodes(groupId, start, end);
	}

	public int getNodesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getNodesCount(groupId);
	}

	public void importPages(long userId, long nodeId,
		java.lang.String importer, java.io.File[] files,
		java.util.Map<String, String[]> options)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.importPages(userId, nodeId, importer, files,
			options);
	}

	public void subscribeNode(long userId, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.subscribeNode(userId, nodeId);
	}

	public void unsubscribeNode(long userId, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.unsubscribeNode(userId, nodeId);
	}

	public com.liferay.portlet.wiki.model.WikiNode updateNode(long nodeId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.updateNode(nodeId, name, description,
			serviceContext);
	}

	public WikiNodeLocalService getWrappedWikiNodeLocalService() {
		return _wikiNodeLocalService;
	}

	private WikiNodeLocalService _wikiNodeLocalService;
}