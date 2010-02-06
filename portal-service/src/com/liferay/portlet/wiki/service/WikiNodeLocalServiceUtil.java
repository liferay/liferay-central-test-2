/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
 * <a href="WikiNodeLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link WikiNodeLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiNodeLocalService
 * @generated
 */
public class WikiNodeLocalServiceUtil {
	public static com.liferay.portlet.wiki.model.WikiNode addWikiNode(
		com.liferay.portlet.wiki.model.WikiNode wikiNode)
		throws com.liferay.portal.SystemException {
		return getService().addWikiNode(wikiNode);
	}

	public static com.liferay.portlet.wiki.model.WikiNode createWikiNode(
		long nodeId) {
		return getService().createWikiNode(nodeId);
	}

	public static void deleteWikiNode(long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteWikiNode(nodeId);
	}

	public static void deleteWikiNode(
		com.liferay.portlet.wiki.model.WikiNode wikiNode)
		throws com.liferay.portal.SystemException {
		getService().deleteWikiNode(wikiNode);
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

	public static com.liferay.portlet.wiki.model.WikiNode getWikiNode(
		long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getWikiNode(nodeId);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiNode> getWikiNodes(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getWikiNodes(start, end);
	}

	public static int getWikiNodesCount()
		throws com.liferay.portal.SystemException {
		return getService().getWikiNodesCount();
	}

	public static com.liferay.portlet.wiki.model.WikiNode updateWikiNode(
		com.liferay.portlet.wiki.model.WikiNode wikiNode)
		throws com.liferay.portal.SystemException {
		return getService().updateWikiNode(wikiNode);
	}

	public static com.liferay.portlet.wiki.model.WikiNode updateWikiNode(
		com.liferay.portlet.wiki.model.WikiNode wikiNode, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateWikiNode(wikiNode, merge);
	}

	public static com.liferay.portlet.wiki.model.WikiNode addNode(long userId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addNode(userId, name, description, serviceContext);
	}

	public static com.liferay.portlet.wiki.model.WikiNode addNode(
		java.lang.String uuid, long userId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addNode(uuid, userId, name, description, serviceContext);
	}

	public static void addNodeResources(long nodeId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addNodeResources(nodeId, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addNodeResources(
		com.liferay.portlet.wiki.model.WikiNode node,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addNodeResources(node, addCommunityPermissions, addGuestPermissions);
	}

	public static void addNodeResources(long nodeId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addNodeResources(nodeId, communityPermissions, guestPermissions);
	}

	public static void addNodeResources(
		com.liferay.portlet.wiki.model.WikiNode node,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addNodeResources(node, communityPermissions, guestPermissions);
	}

	public static void deleteNode(long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteNode(nodeId);
	}

	public static void deleteNode(com.liferay.portlet.wiki.model.WikiNode node)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteNode(node);
	}

	public static void deleteNodes(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteNodes(groupId);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiNode> getCompanyNodes(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getCompanyNodes(companyId, start, end);
	}

	public static int getCompanyNodesCount(long companyId)
		throws com.liferay.portal.SystemException {
		return getService().getCompanyNodesCount(companyId);
	}

	public static com.liferay.portlet.wiki.model.WikiNode getNode(long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getNode(nodeId);
	}

	public static com.liferay.portlet.wiki.model.WikiNode getNode(
		long groupId, java.lang.String nodeName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getNode(groupId, nodeName);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiNode> getNodes(
		long groupId) throws com.liferay.portal.SystemException {
		return getService().getNodes(groupId);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiNode> getNodes(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getNodes(groupId, start, end);
	}

	public static int getNodesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return getService().getNodesCount(groupId);
	}

	public static void importPages(long userId, long nodeId,
		java.lang.String importer, java.io.File[] files,
		java.util.Map<String, String[]> options)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().importPages(userId, nodeId, importer, files, options);
	}

	public static void subscribeNode(long userId, long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().subscribeNode(userId, nodeId);
	}

	public static void unsubscribeNode(long userId, long nodeId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().unsubscribeNode(userId, nodeId);
	}

	public static com.liferay.portlet.wiki.model.WikiNode updateNode(
		long nodeId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateNode(nodeId, name, description);
	}

	public static WikiNodeLocalService getService() {
		if (_service == null) {
			_service = (WikiNodeLocalService)PortalBeanLocatorUtil.locate(WikiNodeLocalService.class.getName());
		}

		return _service;
	}

	public void setService(WikiNodeLocalService service) {
		_service = service;
	}

	private static WikiNodeLocalService _service;
}