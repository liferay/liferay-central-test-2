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
 * <a href="WikiPageLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link WikiPageLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiPageLocalService
 * @generated
 */
public class WikiPageLocalServiceWrapper implements WikiPageLocalService {
	public WikiPageLocalServiceWrapper(
		WikiPageLocalService wikiPageLocalService) {
		_wikiPageLocalService = wikiPageLocalService;
	}

	public com.liferay.portlet.wiki.model.WikiPage addWikiPage(
		com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.addWikiPage(wikiPage);
	}

	public com.liferay.portlet.wiki.model.WikiPage createWikiPage(long pageId) {
		return _wikiPageLocalService.createWikiPage(pageId);
	}

	public void deleteWikiPage(long pageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.deleteWikiPage(pageId);
	}

	public void deleteWikiPage(com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.deleteWikiPage(wikiPage);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.wiki.model.WikiPage getWikiPage(long pageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getWikiPage(pageId);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getWikiPages(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getWikiPages(start, end);
	}

	public int getWikiPagesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getWikiPagesCount();
	}

	public com.liferay.portlet.wiki.model.WikiPage updateWikiPage(
		com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.updateWikiPage(wikiPage);
	}

	public com.liferay.portlet.wiki.model.WikiPage updateWikiPage(
		com.liferay.portlet.wiki.model.WikiPage wikiPage, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.updateWikiPage(wikiPage, merge);
	}

	public com.liferay.portlet.wiki.model.WikiPage addPage(long userId,
		long nodeId, java.lang.String title, java.lang.String content,
		java.lang.String summary, boolean minorEdit,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.addPage(userId, nodeId, title, content,
			summary, minorEdit, serviceContext);
	}

	public com.liferay.portlet.wiki.model.WikiPage addPage(
		java.lang.String uuid, long userId, long nodeId,
		java.lang.String title, double version, java.lang.String content,
		java.lang.String summary, boolean minorEdit, java.lang.String format,
		boolean head, java.lang.String parentTitle,
		java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.addPage(uuid, userId, nodeId, title,
			version, content, summary, minorEdit, format, head, parentTitle,
			redirectTitle, serviceContext);
	}

	public void addPageAttachments(long nodeId, java.lang.String title,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.addPageAttachments(nodeId, title, files);
	}

	public void addPageResources(long nodeId, java.lang.String title,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.addPageResources(nodeId, title,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addPageResources(com.liferay.portlet.wiki.model.WikiPage page,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.addPageResources(page, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addPageResources(long nodeId, java.lang.String title,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.addPageResources(nodeId, title,
			communityPermissions, guestPermissions);
	}

	public void addPageResources(com.liferay.portlet.wiki.model.WikiPage page,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.addPageResources(page, communityPermissions,
			guestPermissions);
	}

	public void changeParent(long userId, long nodeId, java.lang.String title,
		java.lang.String newParentTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.changeParent(userId, nodeId, title,
			newParentTitle, serviceContext);
	}

	public void deletePage(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.deletePage(nodeId, title);
	}

	public void deletePage(com.liferay.portlet.wiki.model.WikiPage page)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.deletePage(page);
	}

	public void deletePageAttachment(long nodeId, java.lang.String title,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.deletePageAttachment(nodeId, title, fileName);
	}

	public void deletePages(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.deletePages(nodeId);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getChildren(
		long nodeId, boolean head, java.lang.String parentTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getChildren(nodeId, head, parentTitle);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getIncomingLinks(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getIncomingLinks(nodeId, title);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getNoAssetPages()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getNoAssetPages();
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getOrphans(
		long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getOrphans(nodeId);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getOutgoingLinks(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getOutgoingLinks(nodeId, title);
	}

	public com.liferay.portlet.wiki.model.WikiPage getPage(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPage(resourcePrimKey);
	}

	public com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPage(nodeId, title);
	}

	public com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPage(nodeId, title, version);
	}

	public com.liferay.portlet.wiki.model.WikiPageDisplay getPageDisplay(
		long nodeId, java.lang.String title,
		javax.portlet.PortletURL viewPageURL,
		javax.portlet.PortletURL editPageURL,
		java.lang.String attachmentURLPrefix)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPageDisplay(nodeId, title, viewPageURL,
			editPageURL, attachmentURLPrefix);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPages(nodeId, start, end);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		java.lang.String format)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPages(format);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPages(nodeId, title, start, end);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPages(nodeId, title, start, end, obc);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, boolean head, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPages(nodeId, head, start, end);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, boolean head, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPages(nodeId, title, head, start, end);
	}

	public int getPagesCount(long nodeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPagesCount(nodeId);
	}

	public int getPagesCount(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPagesCount(nodeId, title);
	}

	public int getPagesCount(long nodeId, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPagesCount(nodeId, head);
	}

	public int getPagesCount(long nodeId, java.lang.String title, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPagesCount(nodeId, title, head);
	}

	public int getPagesCount(java.lang.String format)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPagesCount(format);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getRecentChanges(
		long nodeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getRecentChanges(nodeId, start, end);
	}

	public int getRecentChangesCount(long nodeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getRecentChangesCount(nodeId);
	}

	public void movePage(long userId, long nodeId, java.lang.String title,
		java.lang.String newTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.movePage(userId, nodeId, title, newTitle,
			serviceContext);
	}

	public void movePage(long userId, long nodeId, java.lang.String title,
		java.lang.String newTitle, boolean strict,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.movePage(userId, nodeId, title, newTitle, strict,
			serviceContext);
	}

	public com.liferay.portlet.wiki.model.WikiPage revertPage(long userId,
		long nodeId, java.lang.String title, double version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.revertPage(userId, nodeId, title, version,
			serviceContext);
	}

	public void subscribePage(long userId, long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.subscribePage(userId, nodeId, title);
	}

	public void unsubscribePage(long userId, long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.unsubscribePage(userId, nodeId, title);
	}

	public void updateAsset(long userId,
		com.liferay.portlet.wiki.model.WikiPage page, long[] assetCategoryIds,
		java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.updateAsset(userId, page, assetCategoryIds,
			assetTagNames);
	}

	public com.liferay.portlet.wiki.model.WikiPage updatePage(long userId,
		long nodeId, java.lang.String title, double version,
		java.lang.String content, java.lang.String summary, boolean minorEdit,
		java.lang.String format, java.lang.String parentTitle,
		java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.updatePage(userId, nodeId, title, version,
			content, summary, minorEdit, format, parentTitle, redirectTitle,
			serviceContext);
	}

	public com.liferay.portlet.wiki.model.WikiPage updateStatus(long userId,
		long resourcePrimKey, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.updateStatus(userId, resourcePrimKey,
			status);
	}

	public com.liferay.portlet.wiki.model.WikiPage updateStatus(long userId,
		com.liferay.portlet.wiki.model.WikiPage page, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.updateStatus(userId, page, status);
	}

	public void validateTitle(java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.validateTitle(title);
	}

	public WikiPageLocalService getWrappedWikiPageLocalService() {
		return _wikiPageLocalService;
	}

	private WikiPageLocalService _wikiPageLocalService;
}