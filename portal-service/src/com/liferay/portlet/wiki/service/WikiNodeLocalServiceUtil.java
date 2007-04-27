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
 * <a href="WikiNodeLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portlet.wiki.service.WikiNodeLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.wiki.service.WikiNodeLocalServiceFactory</code> is
 * responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.wiki.service.WikiNodeLocalService
 * @see com.liferay.portlet.wiki.service.WikiNodeLocalServiceFactory
 *
 */
public class WikiNodeLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		WikiNodeLocalService wikiNodeLocalService = WikiNodeLocalServiceFactory.getService();

		return wikiNodeLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		WikiNodeLocalService wikiNodeLocalService = WikiNodeLocalServiceFactory.getService();

		return wikiNodeLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portlet.wiki.model.WikiNode addNode(long userId,
		java.lang.String plid, java.lang.String name,
		java.lang.String description, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiNodeLocalService wikiNodeLocalService = WikiNodeLocalServiceFactory.getService();

		return wikiNodeLocalService.addNode(userId, plid, name, description,
			addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.wiki.model.WikiNode addNode(long userId,
		java.lang.String plid, java.lang.String name,
		java.lang.String description, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiNodeLocalService wikiNodeLocalService = WikiNodeLocalServiceFactory.getService();

		return wikiNodeLocalService.addNode(userId, plid, name, description,
			communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.wiki.model.WikiNode addNode(long userId,
		java.lang.String plid, java.lang.String name,
		java.lang.String description,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiNodeLocalService wikiNodeLocalService = WikiNodeLocalServiceFactory.getService();

		return wikiNodeLocalService.addNode(userId, plid, name, description,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public static void addNodeResources(java.lang.String nodeId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiNodeLocalService wikiNodeLocalService = WikiNodeLocalServiceFactory.getService();
		wikiNodeLocalService.addNodeResources(nodeId, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addNodeResources(
		com.liferay.portlet.wiki.model.WikiNode node,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiNodeLocalService wikiNodeLocalService = WikiNodeLocalServiceFactory.getService();
		wikiNodeLocalService.addNodeResources(node, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addNodeResources(java.lang.String nodeId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiNodeLocalService wikiNodeLocalService = WikiNodeLocalServiceFactory.getService();
		wikiNodeLocalService.addNodeResources(nodeId, communityPermissions,
			guestPermissions);
	}

	public static void addNodeResources(
		com.liferay.portlet.wiki.model.WikiNode node,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiNodeLocalService wikiNodeLocalService = WikiNodeLocalServiceFactory.getService();
		wikiNodeLocalService.addNodeResources(node, communityPermissions,
			guestPermissions);
	}

	public static void deleteNode(java.lang.String nodeId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiNodeLocalService wikiNodeLocalService = WikiNodeLocalServiceFactory.getService();
		wikiNodeLocalService.deleteNode(nodeId);
	}

	public static void deleteNode(com.liferay.portlet.wiki.model.WikiNode node)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiNodeLocalService wikiNodeLocalService = WikiNodeLocalServiceFactory.getService();
		wikiNodeLocalService.deleteNode(node);
	}

	public static void deleteNodes(long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiNodeLocalService wikiNodeLocalService = WikiNodeLocalServiceFactory.getService();
		wikiNodeLocalService.deleteNodes(groupId);
	}

	public static com.liferay.portlet.wiki.model.WikiNode getNode(
		java.lang.String nodeId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiNodeLocalService wikiNodeLocalService = WikiNodeLocalServiceFactory.getService();

		return wikiNodeLocalService.getNode(nodeId);
	}

	public static java.util.List getNodes(long groupId)
		throws com.liferay.portal.SystemException {
		WikiNodeLocalService wikiNodeLocalService = WikiNodeLocalServiceFactory.getService();

		return wikiNodeLocalService.getNodes(groupId);
	}

	public static java.util.List getNodes(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		WikiNodeLocalService wikiNodeLocalService = WikiNodeLocalServiceFactory.getService();

		return wikiNodeLocalService.getNodes(groupId, begin, end);
	}

	public static int getNodesCount(long groupId)
		throws com.liferay.portal.SystemException {
		WikiNodeLocalService wikiNodeLocalService = WikiNodeLocalServiceFactory.getService();

		return wikiNodeLocalService.getNodesCount(groupId);
	}

	public static void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		WikiNodeLocalService wikiNodeLocalService = WikiNodeLocalServiceFactory.getService();
		wikiNodeLocalService.reIndex(ids);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, java.lang.String[] nodeIds, java.lang.String keywords)
		throws com.liferay.portal.SystemException {
		WikiNodeLocalService wikiNodeLocalService = WikiNodeLocalServiceFactory.getService();

		return wikiNodeLocalService.search(companyId, groupId, nodeIds, keywords);
	}

	public static com.liferay.portlet.wiki.model.WikiNode updateNode(
		java.lang.String nodeId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiNodeLocalService wikiNodeLocalService = WikiNodeLocalServiceFactory.getService();

		return wikiNodeLocalService.updateNode(nodeId, name, description);
	}
}