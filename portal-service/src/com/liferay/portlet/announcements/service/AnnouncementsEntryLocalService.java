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

package com.liferay.portlet.announcements.service;


/**
 * <a href="AnnouncementsEntryLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.liferay.portlet.announcements.service.impl.AnnouncementsEntryLocalServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.announcements.service.AnnouncementsEntryLocalServiceFactory
 * @see com.liferay.portlet.announcements.service.AnnouncementsEntryLocalServiceUtil
 *
 */
public interface AnnouncementsEntryLocalService {
	public com.liferay.portlet.announcements.model.AnnouncementsEntry addAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry)
		throws com.liferay.portal.SystemException;

	public void deleteAnnouncementsEntry(long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementsEntry updateAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.service.persistence.AnnouncementsEntryPersistence getAnnouncementsEntryPersistence();

	public void setAnnouncementsEntryPersistence(
		com.liferay.portlet.announcements.service.persistence.AnnouncementsEntryPersistence announcementsEntryPersistence);

	public com.liferay.portlet.announcements.service.persistence.AnnouncementsEntryFinder getAnnouncementsEntryFinder();

	public void setAnnouncementsEntryFinder(
		com.liferay.portlet.announcements.service.persistence.AnnouncementsEntryFinder announcementsEntryFinder);

	public com.liferay.portlet.announcements.service.persistence.AnnouncementsFlagPersistence getAnnouncementsFlagPersistence();

	public void setAnnouncementsFlagPersistence(
		com.liferay.portlet.announcements.service.persistence.AnnouncementsFlagPersistence announcementsFlagPersistence);

	public com.liferay.portal.service.persistence.CompanyPersistence getCompanyPersistence();

	public void setCompanyPersistence(
		com.liferay.portal.service.persistence.CompanyPersistence companyPersistence);

	public com.liferay.portal.service.persistence.ContactPersistence getContactPersistence();

	public void setContactPersistence(
		com.liferay.portal.service.persistence.ContactPersistence contactPersistence);

	public com.liferay.portal.service.persistence.GroupPersistence getGroupPersistence();

	public void setGroupPersistence(
		com.liferay.portal.service.persistence.GroupPersistence groupPersistence);

	public com.liferay.portal.service.persistence.GroupFinder getGroupFinder();

	public void setGroupFinder(
		com.liferay.portal.service.persistence.GroupFinder groupFinder);

	public com.liferay.portal.service.persistence.OrganizationPersistence getOrganizationPersistence();

	public void setOrganizationPersistence(
		com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence);

	public com.liferay.portal.service.persistence.OrganizationFinder getOrganizationFinder();

	public void setOrganizationFinder(
		com.liferay.portal.service.persistence.OrganizationFinder organizationFinder);

	public com.liferay.portal.service.persistence.ResourcePersistence getResourcePersistence();

	public void setResourcePersistence(
		com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence);

	public com.liferay.portal.service.persistence.ResourceFinder getResourceFinder();

	public void setResourceFinder(
		com.liferay.portal.service.persistence.ResourceFinder resourceFinder);

	public com.liferay.portal.service.persistence.RolePersistence getRolePersistence();

	public void setRolePersistence(
		com.liferay.portal.service.persistence.RolePersistence rolePersistence);

	public com.liferay.portal.service.persistence.RoleFinder getRoleFinder();

	public void setRoleFinder(
		com.liferay.portal.service.persistence.RoleFinder roleFinder);

	public com.liferay.portal.service.persistence.UserPersistence getUserPersistence();

	public void setUserPersistence(
		com.liferay.portal.service.persistence.UserPersistence userPersistence);

	public com.liferay.portal.service.persistence.UserFinder getUserFinder();

	public void setUserFinder(
		com.liferay.portal.service.persistence.UserFinder userFinder);

	public com.liferay.portal.service.persistence.UserGroupPersistence getUserGroupPersistence();

	public void setUserGroupPersistence(
		com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence);

	public com.liferay.portal.service.persistence.UserGroupFinder getUserGroupFinder();

	public void setUserGroupFinder(
		com.liferay.portal.service.persistence.UserGroupFinder userGroupFinder);

	public void afterPropertiesSet();

	public com.liferay.portlet.announcements.model.AnnouncementsEntry addEntry(
		long userId, long classNameId, long classPK, java.lang.String title,
		java.lang.String content, java.lang.String url, java.lang.String type,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, int priority, boolean alert)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void checkEntries()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteEntry(long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portlet.announcements.model.AnnouncementsEntry getEntry(
		long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long classNameId, long classPK, boolean alert, int begin, int end)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long userId, long classNameId, long[] classPKs, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue, int begin,
		int end)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long userId, java.util.LinkedHashMap<Long, long[]> scopes,
		boolean alert, int flagValue, int begin, int end)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long userId, java.util.LinkedHashMap<Long, long[]> scopes,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue, int begin,
		int end)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public int getEntriesCount(long classNameId, long classPK, boolean alert)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public int getEntriesCount(long userId, long classNameId, long[] classPKs,
		boolean alert, int flagValue)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public int getEntriesCount(long userId, long classNameId, long[] classPKs,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public int getEntriesCount(long userId,
		java.util.LinkedHashMap<Long, long[]> scopes, boolean alert,
		int flagValue)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public int getEntriesCount(long userId,
		java.util.LinkedHashMap<Long, long[]> scopes, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getUserEntries(
		long userId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public int getUserEntriesCount(long userId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementsEntry updateEntry(
		long entryId, java.lang.String title, java.lang.String content,
		java.lang.String url, java.lang.String type, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, int priority)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;
}