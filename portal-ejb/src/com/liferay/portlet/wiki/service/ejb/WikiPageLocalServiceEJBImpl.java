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

import com.liferay.portlet.wiki.service.spring.WikiPageLocalService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="WikiPageLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WikiPageLocalServiceEJBImpl implements WikiPageLocalService,
	SessionBean {
	public static final String CLASS_NAME = WikiPageLocalService.class.getName() +
		".transaction";

	public static WikiPageLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (WikiPageLocalService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portlet.wiki.model.WikiPage addPage(
		java.lang.String userId, java.lang.String nodeId, java.lang.String title)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addPage(userId, nodeId, title);
	}

	public void addPageResources(java.lang.String nodeId,
		java.lang.String title, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addPageResources(nodeId, title, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addPageResources(com.liferay.portlet.wiki.model.WikiNode node,
		com.liferay.portlet.wiki.model.WikiPage page,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addPageResources(node, page, addCommunityPermissions,
			addGuestPermissions);
	}

	public void deletePage(java.lang.String nodeId, java.lang.String title)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deletePage(nodeId, title);
	}

	public void deletePage(com.liferay.portlet.wiki.model.WikiPage page)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deletePage(page);
	}

	public void deletePages(java.lang.String nodeId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deletePages(nodeId);
	}

	public java.util.List getLinks(java.lang.String nodeId,
		java.lang.String title) throws com.liferay.portal.SystemException {
		return getService().getLinks(nodeId, title);
	}

	public java.util.List getOrphans(java.lang.String nodeId)
		throws com.liferay.portal.SystemException {
		return getService().getOrphans(nodeId);
	}

	public com.liferay.portlet.wiki.model.WikiPage getPage(
		java.lang.String nodeId, java.lang.String title)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getPage(nodeId, title);
	}

	public com.liferay.portlet.wiki.model.WikiPage getPage(
		java.lang.String nodeId, java.lang.String title, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getPage(nodeId, title, version);
	}

	public java.util.List getPages(java.lang.String nodeId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getService().getPages(nodeId, begin, end);
	}

	public java.util.List getPages(java.lang.String nodeId,
		java.lang.String title, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getService().getPages(nodeId, title, begin, end);
	}

	public java.util.List getPages(java.lang.String nodeId, boolean head,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getService().getPages(nodeId, head, begin, end);
	}

	public java.util.List getPages(java.lang.String nodeId,
		java.lang.String title, boolean head, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getService().getPages(nodeId, title, head, begin, end);
	}

	public int getPagesCount(java.lang.String nodeId)
		throws com.liferay.portal.SystemException {
		return getService().getPagesCount(nodeId);
	}

	public int getPagesCount(java.lang.String nodeId, java.lang.String title)
		throws com.liferay.portal.SystemException {
		return getService().getPagesCount(nodeId, title);
	}

	public int getPagesCount(java.lang.String nodeId, boolean head)
		throws com.liferay.portal.SystemException {
		return getService().getPagesCount(nodeId, head);
	}

	public int getPagesCount(java.lang.String nodeId, java.lang.String title,
		boolean head) throws com.liferay.portal.SystemException {
		return getService().getPagesCount(nodeId, title, head);
	}

	public java.util.List getRecentChanges(java.lang.String nodeId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getService().getRecentChanges(nodeId, begin, end);
	}

	public int getRecentChangesCount(java.lang.String nodeId)
		throws com.liferay.portal.SystemException {
		return getService().getRecentChangesCount(nodeId);
	}

	public com.liferay.portlet.wiki.model.WikiPage revertPage(
		java.lang.String userId, java.lang.String nodeId,
		java.lang.String title, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().revertPage(userId, nodeId, title, version);
	}

	public com.liferay.portlet.wiki.model.WikiPage updatePage(
		java.lang.String userId, java.lang.String nodeId,
		java.lang.String title, java.lang.String content,
		java.lang.String format)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updatePage(userId, nodeId, title, content, format);
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