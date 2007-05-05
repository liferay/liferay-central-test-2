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

package com.liferay.portlet.blogs.service.ejb;

import com.liferay.portlet.blogs.service.BlogsEntryLocalService;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="BlogsEntryLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portlet.blogs.service.BlogsEntryLocalService
 * @see com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil
 * @see com.liferay.portlet.blogs.service.ejb.BlogsEntryLocalServiceEJB
 * @see com.liferay.portlet.blogs.service.ejb.BlogsEntryLocalServiceHome
 * @see com.liferay.portlet.blogs.service.impl.BlogsEntryLocalServiceImpl
 *
 */
public class BlogsEntryLocalServiceEJBImpl implements BlogsEntryLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return BlogsEntryLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return BlogsEntryLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry addEntry(long userId,
		java.lang.String plid, long categoryId, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		java.lang.String[] tagsEntries, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BlogsEntryLocalServiceFactory.getTxImpl().addEntry(userId, plid,
			categoryId, title, content, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute, tagsEntries,
			addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry addEntry(long userId,
		java.lang.String plid, long categoryId, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		java.lang.String[] tagsEntries,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BlogsEntryLocalServiceFactory.getTxImpl().addEntry(userId, plid,
			categoryId, title, content, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute, tagsEntries,
			communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry addEntry(long userId,
		java.lang.String plid, long categoryId, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		java.lang.String[] tagsEntries,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BlogsEntryLocalServiceFactory.getTxImpl().addEntry(userId, plid,
			categoryId, title, content, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute, tagsEntries,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public void addEntryResources(long entryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsEntryLocalServiceFactory.getTxImpl().addEntryResources(entryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsEntryLocalServiceFactory.getTxImpl().addEntryResources(entry,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(long entryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsEntryLocalServiceFactory.getTxImpl().addEntryResources(entryId,
			communityPermissions, guestPermissions);
	}

	public void addEntryResources(
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsEntryLocalServiceFactory.getTxImpl().addEntryResources(entry,
			communityPermissions, guestPermissions);
	}

	public void deleteEntries(long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsEntryLocalServiceFactory.getTxImpl().deleteEntries(groupId);
	}

	public void deleteEntry(long entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsEntryLocalServiceFactory.getTxImpl().deleteEntry(entryId);
	}

	public void deleteEntry(com.liferay.portlet.blogs.model.BlogsEntry entry)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsEntryLocalServiceFactory.getTxImpl().deleteEntry(entry);
	}

	public int getCategoriesEntriesCount(java.util.List categoryIds)
		throws com.liferay.portal.SystemException {
		return BlogsEntryLocalServiceFactory.getTxImpl()
											.getCategoriesEntriesCount(categoryIds);
	}

	public java.util.List getEntries(long categoryId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return BlogsEntryLocalServiceFactory.getTxImpl().getEntries(categoryId,
			begin, end);
	}

	public int getEntriesCount(long categoryId)
		throws com.liferay.portal.SystemException {
		return BlogsEntryLocalServiceFactory.getTxImpl().getEntriesCount(categoryId);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry getEntry(long entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BlogsEntryLocalServiceFactory.getTxImpl().getEntry(entryId);
	}

	public java.util.List getGroupEntries(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return BlogsEntryLocalServiceFactory.getTxImpl().getGroupEntries(groupId,
			begin, end);
	}

	public int getGroupEntriesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return BlogsEntryLocalServiceFactory.getTxImpl().getGroupEntriesCount(groupId);
	}

	public java.lang.String getGroupEntriesRSS(long groupId, int begin,
		int end, java.lang.String type, double version, java.lang.String url)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BlogsEntryLocalServiceFactory.getTxImpl().getGroupEntriesRSS(groupId,
			begin, end, type, version, url);
	}

	public void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalServiceFactory.getTxImpl().reIndex(ids);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, long userId, long[] categoryIds, java.lang.String keywords)
		throws com.liferay.portal.SystemException {
		return BlogsEntryLocalServiceFactory.getTxImpl().search(companyId,
			groupId, userId, categoryIds, keywords);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry updateEntry(long userId,
		long entryId, long categoryId, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		java.lang.String[] tagsEntries)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BlogsEntryLocalServiceFactory.getTxImpl().updateEntry(userId,
			entryId, categoryId, title, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, tagsEntries);
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