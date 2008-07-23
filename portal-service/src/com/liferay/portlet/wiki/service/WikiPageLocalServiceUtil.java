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
 * This class provides static methods for the
 * <code>com.liferay.portlet.wiki.service.WikiPageLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.wiki.service.WikiPageLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.wiki.service.WikiPageLocalService
 * @see com.liferay.portlet.wiki.service.WikiPageLocalServiceFactory
 *
 */
public class WikiPageLocalServiceUtil {
	public static com.liferay.portlet.wiki.model.WikiPage addWikiPage(
		com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.addWikiPage(wikiPage);
	}

	public static void deleteWikiPage(long pageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		wikiPageLocalService.deleteWikiPage(pageId);
	}

	public static void deleteWikiPage(
		com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		wikiPageLocalService.deleteWikiPage(wikiPage);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getWikiPage(
		long pageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getWikiPage(pageId);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getWikiPages(
		int start, int end) throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getWikiPages(start, end);
	}

	public static int getWikiPagesCount()
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getWikiPagesCount();
	}

	public static com.liferay.portlet.wiki.model.WikiPage updateWikiPage(
		com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.updateWikiPage(wikiPage);
	}

	public static com.liferay.portlet.wiki.model.WikiPage addPage(long userId,
		long nodeId, java.lang.String title, java.lang.String content,
		java.lang.String summary, boolean minorEdit,
		javax.portlet.PortletPreferences prefs,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.addPage(userId, nodeId, title, content,
			summary, minorEdit, prefs, themeDisplay);
	}

	public static com.liferay.portlet.wiki.model.WikiPage addPage(
		java.lang.String uuid, long userId, long nodeId,
		java.lang.String title, double version, java.lang.String content,
		java.lang.String summary, boolean minorEdit, java.lang.String format,
		boolean head, java.lang.String parentTitle,
		java.lang.String redirectTitle, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.addPage(uuid, userId, nodeId, title,
			version, content, summary, minorEdit, format, head, parentTitle,
			redirectTitle, tagsEntries, prefs, themeDisplay);
	}

	public static void addPageAttachments(long nodeId, java.lang.String title,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		wikiPageLocalService.addPageAttachments(nodeId, title, files);
	}

	public static void addPageResources(long nodeId, java.lang.String title,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		wikiPageLocalService.addPageResources(nodeId, title,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addPageResources(
		com.liferay.portlet.wiki.model.WikiNode node,
		com.liferay.portlet.wiki.model.WikiPage page,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		wikiPageLocalService.addPageResources(node, page,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addPageResources(long nodeId, java.lang.String title,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		wikiPageLocalService.addPageResources(nodeId, title,
			communityPermissions, guestPermissions);
	}

	public static void addPageResources(
		com.liferay.portlet.wiki.model.WikiNode node,
		com.liferay.portlet.wiki.model.WikiPage page,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		wikiPageLocalService.addPageResources(node, page, communityPermissions,
			guestPermissions);
	}

	public static void changeParent(long userId, long nodeId,
		java.lang.String title, java.lang.String newParentTitle,
		javax.portlet.PortletPreferences prefs,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		wikiPageLocalService.changeParent(userId, nodeId, title,
			newParentTitle, prefs, themeDisplay);
	}

	public static void deletePage(long nodeId, java.lang.String title)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		wikiPageLocalService.deletePage(nodeId, title);
	}

	public static void deletePage(com.liferay.portlet.wiki.model.WikiPage page)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		wikiPageLocalService.deletePage(page);
	}

	public static void deletePageAttachment(long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		wikiPageLocalService.deletePageAttachment(nodeId, title, fileName);
	}

	public static void deletePages(long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		wikiPageLocalService.deletePages(nodeId);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getChildren(
		long nodeId, boolean head, java.lang.String parentTitle)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getChildren(nodeId, head, parentTitle);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getIncomingLinks(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getIncomingLinks(nodeId, title);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getNoAssetPages()
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getNoAssetPages();
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getOrphans(
		long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getOrphans(nodeId);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getOutgoingLinks(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getOutgoingLinks(nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPage(nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPage(nodeId, title, version);
	}

	public static com.liferay.portlet.wiki.model.WikiPageDisplay getPageDisplay(
		long nodeId, java.lang.String title,
		javax.portlet.PortletURL viewPageURL,
		javax.portlet.PortletURL editPageURL,
		java.lang.String attachmentURLPrefix)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPageDisplay(nodeId, title, viewPageURL,
			editPageURL, attachmentURLPrefix);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, int start, int end)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPages(nodeId, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		java.lang.String format) throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPages(format);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, int start, int end)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPages(nodeId, title, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPages(nodeId, title, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, boolean head, int start, int end)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPages(nodeId, head, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, boolean head, int start, int end)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPages(nodeId, title, head, start, end);
	}

	public static int getPagesCount(long nodeId)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPagesCount(nodeId);
	}

	public static int getPagesCount(long nodeId, java.lang.String title)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPagesCount(nodeId, title);
	}

	public static int getPagesCount(long nodeId, boolean head)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPagesCount(nodeId, head);
	}

	public static int getPagesCount(long nodeId, java.lang.String title,
		boolean head) throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPagesCount(nodeId, title, head);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getRecentChanges(
		long nodeId, int start, int end)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getRecentChanges(nodeId, start, end);
	}

	public static int getRecentChangesCount(long nodeId)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getRecentChangesCount(nodeId);
	}

	public static void movePage(long userId, long nodeId,
		java.lang.String title, java.lang.String newTitle,
		javax.portlet.PortletPreferences prefs,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		wikiPageLocalService.movePage(userId, nodeId, title, newTitle, prefs,
			themeDisplay);
	}

	public static void movePage(long userId, long nodeId,
		java.lang.String title, java.lang.String newTitle, boolean strict,
		javax.portlet.PortletPreferences prefs,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		wikiPageLocalService.movePage(userId, nodeId, title, newTitle, strict,
			prefs, themeDisplay);
	}

	public static com.liferay.portlet.wiki.model.WikiPage revertPage(
		long userId, long nodeId, java.lang.String title, double version,
		javax.portlet.PortletPreferences prefs,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.revertPage(userId, nodeId, title, version,
			prefs, themeDisplay);
	}

	public static void subscribePage(long userId, long nodeId,
		java.lang.String title)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		wikiPageLocalService.subscribePage(userId, nodeId, title);
	}

	public static void unsubscribePage(long userId, long nodeId,
		java.lang.String title)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		wikiPageLocalService.unsubscribePage(userId, nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPage updatePage(
		long userId, long nodeId, java.lang.String title, double version,
		java.lang.String content, java.lang.String summary, boolean minorEdit,
		java.lang.String format, java.lang.String parentTitle,
		java.lang.String redirectTitle, java.lang.String[] tagsEntries,
		javax.portlet.PortletPreferences prefs,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.updatePage(userId, nodeId, title, version,
			content, summary, minorEdit, format, parentTitle, redirectTitle,
			tagsEntries, prefs, themeDisplay);
	}

	public static void updateTagsAsset(long userId,
		com.liferay.portlet.wiki.model.WikiPage page,
		java.lang.String[] tagsEntries)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		wikiPageLocalService.updateTagsAsset(userId, page, tagsEntries);
	}

	public static void validateTitle(java.lang.String title)
		throws com.liferay.portal.PortalException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		wikiPageLocalService.validateTitle(title);
	}
}