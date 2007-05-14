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

package com.liferay.portlet.wiki.service.ejb;

import com.liferay.portlet.wiki.service.WikiNodeLocalService;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="WikiNodeLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is the EJB implementation of the service that is used when Liferay
 * is run inside a full J2EE container.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.wiki.service.WikiNodeLocalService
 * @see com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil
 * @see com.liferay.portlet.wiki.service.ejb.WikiNodeLocalServiceEJB
 * @see com.liferay.portlet.wiki.service.ejb.WikiNodeLocalServiceHome
 * @see com.liferay.portlet.wiki.service.impl.WikiNodeLocalServiceImpl
 *
 */
public class WikiNodeLocalServiceEJBImpl implements WikiNodeLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return WikiNodeLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return WikiNodeLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.wiki.model.WikiNode addNode(long userId,
		long plid, java.lang.String name, java.lang.String description,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return WikiNodeLocalServiceFactory.getTxImpl().addNode(userId, plid,
			name, description, addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.wiki.model.WikiNode addNode(long userId,
		long plid, java.lang.String name, java.lang.String description,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return WikiNodeLocalServiceFactory.getTxImpl().addNode(userId, plid,
			name, description, communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.wiki.model.WikiNode addNode(long userId,
		long plid, java.lang.String name, java.lang.String description,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return WikiNodeLocalServiceFactory.getTxImpl().addNode(userId, plid,
			name, description, addCommunityPermissions, addGuestPermissions,
			communityPermissions, guestPermissions);
	}

	public void addNodeResources(long nodeId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiNodeLocalServiceFactory.getTxImpl().addNodeResources(nodeId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addNodeResources(com.liferay.portlet.wiki.model.WikiNode node,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiNodeLocalServiceFactory.getTxImpl().addNodeResources(node,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addNodeResources(long nodeId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiNodeLocalServiceFactory.getTxImpl().addNodeResources(nodeId,
			communityPermissions, guestPermissions);
	}

	public void addNodeResources(com.liferay.portlet.wiki.model.WikiNode node,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiNodeLocalServiceFactory.getTxImpl().addNodeResources(node,
			communityPermissions, guestPermissions);
	}

	public void deleteNode(long nodeId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiNodeLocalServiceFactory.getTxImpl().deleteNode(nodeId);
	}

	public void deleteNode(com.liferay.portlet.wiki.model.WikiNode node)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiNodeLocalServiceFactory.getTxImpl().deleteNode(node);
	}

	public void deleteNodes(long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		WikiNodeLocalServiceFactory.getTxImpl().deleteNodes(groupId);
	}

	public com.liferay.portlet.wiki.model.WikiNode getNode(long nodeId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return WikiNodeLocalServiceFactory.getTxImpl().getNode(nodeId);
	}

	public java.util.List getNodes(long groupId)
		throws com.liferay.portal.SystemException {
		return WikiNodeLocalServiceFactory.getTxImpl().getNodes(groupId);
	}

	public java.util.List getNodes(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return WikiNodeLocalServiceFactory.getTxImpl().getNodes(groupId, begin,
			end);
	}

	public int getNodesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return WikiNodeLocalServiceFactory.getTxImpl().getNodesCount(groupId);
	}

	public void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		WikiNodeLocalServiceFactory.getTxImpl().reIndex(ids);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, long[] nodeIds, java.lang.String keywords)
		throws com.liferay.portal.SystemException {
		return WikiNodeLocalServiceFactory.getTxImpl().search(companyId,
			groupId, nodeIds, keywords);
	}

	public com.liferay.portlet.wiki.model.WikiNode updateNode(long nodeId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return WikiNodeLocalServiceFactory.getTxImpl().updateNode(nodeId, name,
			description);
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