/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portlet.wiki.service.WikiPageLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.wiki.service.WikiPageLocalServiceFactory</code> is
 * responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.wiki.service.WikiPageLocalService
 * @see com.liferay.portlet.wiki.service.WikiPageLocalServiceFactory
 *
 */
public class WikiPageLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portlet.wiki.model.WikiPage addPage(long userId,
		java.lang.String nodeId, java.lang.String title)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.addPage(userId, nodeId, title);
	}

	public static void addPageResources(java.lang.String nodeId,
		java.lang.String title, boolean addCommunityPermissions,
		boolean addGuestPermissions)
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

	public static void addPageResources(java.lang.String nodeId,
		java.lang.String title, java.lang.String[] communityPermissions,
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

	public static void deletePage(java.lang.String nodeId,
		java.lang.String title)
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

	public static void deletePages(java.lang.String nodeId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();
		wikiPageLocalService.deletePages(nodeId);
	}

	public static java.util.List getLinks(java.lang.String nodeId,
		java.lang.String title) throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getLinks(nodeId, title);
	}

	public static java.util.List getOrphans(java.lang.String nodeId)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getOrphans(nodeId);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPage(
		java.lang.String nodeId, java.lang.String title)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPage(nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPage(
		java.lang.String nodeId, java.lang.String title, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPage(nodeId, title, version);
	}

	public static java.util.List getPages(java.lang.String nodeId, int begin,
		int end) throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPages(nodeId, begin, end);
	}

	public static java.util.List getPages(java.lang.String nodeId,
		java.lang.String title, int begin, int end)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPages(nodeId, title, begin, end);
	}

	public static java.util.List getPages(java.lang.String nodeId,
		boolean head, int begin, int end)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPages(nodeId, head, begin, end);
	}

	public static java.util.List getPages(java.lang.String nodeId,
		java.lang.String title, boolean head, int begin, int end)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPages(nodeId, title, head, begin, end);
	}

	public static int getPagesCount(java.lang.String nodeId)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPagesCount(nodeId);
	}

	public static int getPagesCount(java.lang.String nodeId,
		java.lang.String title) throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPagesCount(nodeId, title);
	}

	public static int getPagesCount(java.lang.String nodeId, boolean head)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPagesCount(nodeId, head);
	}

	public static int getPagesCount(java.lang.String nodeId,
		java.lang.String title, boolean head)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getPagesCount(nodeId, title, head);
	}

	public static java.util.List getRecentChanges(java.lang.String nodeId,
		int begin, int end) throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getRecentChanges(nodeId, begin, end);
	}

	public static int getRecentChangesCount(java.lang.String nodeId)
		throws com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.getRecentChangesCount(nodeId);
	}

	public static com.liferay.portlet.wiki.model.WikiPage revertPage(
		long userId, java.lang.String nodeId, java.lang.String title,
		double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.revertPage(userId, nodeId, title, version);
	}

	public static com.liferay.portlet.wiki.model.WikiPage updatePage(
		long userId, java.lang.String nodeId, java.lang.String title,
		java.lang.String content, java.lang.String format,
		java.lang.String[] tagsEntries)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiPageLocalService wikiPageLocalService = WikiPageLocalServiceFactory.getService();

		return wikiPageLocalService.updatePage(userId, nodeId, title, content,
			format, tagsEntries);
	}
}