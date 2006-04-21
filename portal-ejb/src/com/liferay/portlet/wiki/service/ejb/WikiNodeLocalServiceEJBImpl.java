/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.wiki.service.ejb;

import com.liferay.portal.spring.util.SpringUtil;

import com.liferay.portlet.wiki.service.spring.WikiNodeLocalService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="WikiNodeLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WikiNodeLocalServiceEJBImpl implements WikiNodeLocalService,
	SessionBean {
	public static final String CLASS_NAME = WikiNodeLocalService.class.getName() +
		".transaction";

	public static WikiNodeLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (WikiNodeLocalService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portlet.wiki.model.WikiNode addNode(
		java.lang.String userId, java.lang.String plid, java.lang.String name,
		java.lang.String description, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addNode(userId, plid, name, description,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addNodeResources(java.lang.String nodeId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addNodeResources(nodeId, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addNodeResources(com.liferay.portlet.wiki.model.WikiNode node,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addNodeResources(node, addCommunityPermissions,
			addGuestPermissions);
	}

	public void deleteNode(java.lang.String nodeId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteNode(nodeId);
	}

	public void deleteNode(com.liferay.portlet.wiki.model.WikiNode node)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteNode(node);
	}

	public void deleteNodes(java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteNodes(groupId);
	}

	public com.liferay.portlet.wiki.model.WikiNode getNode(
		java.lang.String nodeId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getNode(nodeId);
	}

	public java.util.List getNodes(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getService().getNodes(groupId);
	}

	public java.util.List getNodes(java.lang.String groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getService().getNodes(groupId, begin, end);
	}

	public int getNodesCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getService().getNodesCount(groupId);
	}

	public void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		getService().reIndex(ids);
	}

	public com.liferay.util.lucene.Hits search(java.lang.String companyId,
		java.lang.String groupId, java.lang.String[] nodeIds,
		java.lang.String keywords) throws com.liferay.portal.SystemException {
		return getService().search(companyId, groupId, nodeIds, keywords);
	}

	public com.liferay.portlet.wiki.model.WikiNode updateNode(
		java.lang.String nodeId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updateNode(nodeId, name, description);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}