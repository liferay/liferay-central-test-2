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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="WikiPageLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link WikiPageLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiPageLocalService
 * @generated
 */
public class WikiPageLocalServiceUtil {
	public static com.liferay.portlet.wiki.model.WikiPage addWikiPage(
		com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws com.liferay.portal.SystemException {
		return getService().addWikiPage(wikiPage);
	}

	public static com.liferay.portlet.wiki.model.WikiPage createWikiPage(
		long pageId) {
		return getService().createWikiPage(pageId);
	}

	public static void deleteWikiPage(long pageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteWikiPage(pageId);
	}

	public static void deleteWikiPage(
		com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws com.liferay.portal.SystemException {
		getService().deleteWikiPage(wikiPage);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getWikiPage(
		long pageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getWikiPage(pageId);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getWikiPages(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getWikiPages(start, end);
	}

	public static int getWikiPagesCount()
		throws com.liferay.portal.SystemException {
		return getService().getWikiPagesCount();
	}

	public static com.liferay.portlet.wiki.model.WikiPage updateWikiPage(
		com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws com.liferay.portal.SystemException {
		return getService().updateWikiPage(wikiPage);
	}

	public static com.liferay.portlet.wiki.model.WikiPage updateWikiPage(
		com.liferay.portlet.wiki.model.WikiPage wikiPage, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateWikiPage(wikiPage, merge);
	}

	public static com.liferay.portlet.wiki.model.WikiPage addPage(long userId,
		long nodeId, java.lang.String title, java.lang.String content,
		java.lang.String summary, boolean minorEdit,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addPage(userId, nodeId, title, content, summary, minorEdit,
			serviceContext);
	}

	public static com.liferay.portlet.wiki.model.WikiPage addPage(
		java.lang.String uuid, long userId, long nodeId,
		java.lang.String title, double version, java.lang.String content,
		java.lang.String summary, boolean minorEdit, java.lang.String format,
		boolean head, java.lang.String parentTitle,
		java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addPage(uuid, userId, nodeId, title, version, content,
			summary, minorEdit, format, head, parentTitle, redirectTitle,
			serviceContext);
	}

	public static void addPageAttachments(long nodeId, java.lang.String title,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().addPageAttachments(nodeId, title, files);
	}

	public static void addPageResources(long nodeId, java.lang.String title,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addPageResources(nodeId, title, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addPageResources(
		com.liferay.portlet.wiki.model.WikiPage page,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addPageResources(page, addCommunityPermissions, addGuestPermissions);
	}

	public static void addPageResources(long nodeId, java.lang.String title,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addPageResources(nodeId, title, communityPermissions,
			guestPermissions);
	}

	public static void addPageResources(
		com.liferay.portlet.wiki.model.WikiPage page,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addPageResources(page, communityPermissions, guestPermissions);
	}

	public static void changeParent(long userId, long nodeId,
		java.lang.String title, java.lang.String newParentTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.changeParent(userId, nodeId, title, newParentTitle, serviceContext);
	}

	public static void deletePage(long nodeId, java.lang.String title)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deletePage(nodeId, title);
	}

	public static void deletePage(com.liferay.portlet.wiki.model.WikiPage page)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deletePage(page);
	}

	public static void deletePageAttachment(long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deletePageAttachment(nodeId, title, fileName);
	}

	public static void deletePages(long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deletePages(nodeId);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getChildren(
		long nodeId, boolean head, java.lang.String parentTitle)
		throws com.liferay.portal.SystemException {
		return getService().getChildren(nodeId, head, parentTitle);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getIncomingLinks(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getIncomingLinks(nodeId, title);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getNoAssetPages()
		throws com.liferay.portal.SystemException {
		return getService().getNoAssetPages();
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getOrphans(
		long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getOrphans(nodeId);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getOutgoingLinks(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getOutgoingLinks(nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPage(
		long resourcePrimKey)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getPage(resourcePrimKey);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getPage(nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getPage(nodeId, title, version);
	}

	public static com.liferay.portlet.wiki.model.WikiPageDisplay getPageDisplay(
		long nodeId, java.lang.String title,
		javax.portlet.PortletURL viewPageURL,
		javax.portlet.PortletURL editPageURL,
		java.lang.String attachmentURLPrefix)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .getPageDisplay(nodeId, title, viewPageURL, editPageURL,
			attachmentURLPrefix);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getPages(nodeId, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		java.lang.String format) throws com.liferay.portal.SystemException {
		return getService().getPages(format);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getPages(nodeId, title, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService().getPages(nodeId, title, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, boolean head, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getPages(nodeId, head, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, boolean head, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getPages(nodeId, title, head, start, end);
	}

	public static int getPagesCount(long nodeId)
		throws com.liferay.portal.SystemException {
		return getService().getPagesCount(nodeId);
	}

	public static int getPagesCount(long nodeId, java.lang.String title)
		throws com.liferay.portal.SystemException {
		return getService().getPagesCount(nodeId, title);
	}

	public static int getPagesCount(long nodeId, boolean head)
		throws com.liferay.portal.SystemException {
		return getService().getPagesCount(nodeId, head);
	}

	public static int getPagesCount(long nodeId, java.lang.String title,
		boolean head) throws com.liferay.portal.SystemException {
		return getService().getPagesCount(nodeId, title, head);
	}

	public static int getPagesCount(java.lang.String format)
		throws com.liferay.portal.SystemException {
		return getService().getPagesCount(format);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getRecentChanges(
		long nodeId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getRecentChanges(nodeId, start, end);
	}

	public static int getRecentChangesCount(long nodeId)
		throws com.liferay.portal.SystemException {
		return getService().getRecentChangesCount(nodeId);
	}

	public static void movePage(long userId, long nodeId,
		java.lang.String title, java.lang.String newTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().movePage(userId, nodeId, title, newTitle, serviceContext);
	}

	public static void movePage(long userId, long nodeId,
		java.lang.String title, java.lang.String newTitle, boolean strict,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.movePage(userId, nodeId, title, newTitle, strict, serviceContext);
	}

	public static com.liferay.portlet.wiki.model.WikiPage revertPage(
		long userId, long nodeId, java.lang.String title, double version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .revertPage(userId, nodeId, title, version, serviceContext);
	}

	public static void subscribePage(long userId, long nodeId,
		java.lang.String title)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().subscribePage(userId, nodeId, title);
	}

	public static void unsubscribePage(long userId, long nodeId,
		java.lang.String title)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().unsubscribePage(userId, nodeId, title);
	}

	public static void updateAsset(long userId,
		com.liferay.portlet.wiki.model.WikiPage page, long[] assetCategoryIds,
		java.lang.String[] assetTagNames)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().updateAsset(userId, page, assetCategoryIds, assetTagNames);
	}

	public static com.liferay.portlet.wiki.model.WikiPage updatePage(
		long userId, long nodeId, java.lang.String title, double version,
		java.lang.String content, java.lang.String summary, boolean minorEdit,
		java.lang.String format, java.lang.String parentTitle,
		java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updatePage(userId, nodeId, title, version, content,
			summary, minorEdit, format, parentTitle, redirectTitle,
			serviceContext);
	}

	public static com.liferay.portlet.wiki.model.WikiPage updateStatus(
		long userId, long resourcePrimKey, int status)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateStatus(userId, resourcePrimKey, status);
	}

	public static com.liferay.portlet.wiki.model.WikiPage updateStatus(
		long userId, com.liferay.portlet.wiki.model.WikiPage page, int status)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateStatus(userId, page, status);
	}

	public static void validateTitle(java.lang.String title)
		throws com.liferay.portal.PortalException {
		getService().validateTitle(title);
	}

	public static WikiPageLocalService getService() {
		if (_service == null) {
			_service = (WikiPageLocalService)PortalBeanLocatorUtil.locate(WikiPageLocalService.class.getName());
		}

		return _service;
	}

	public void setService(WikiPageLocalService service) {
		_service = service;
	}

	private static WikiPageLocalService _service;
}