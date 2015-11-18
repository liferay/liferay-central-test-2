/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.wiki.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseService;

/**
 * Provides the remote service interface for WikiPage. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageServiceUtil
 * @see com.liferay.wiki.service.base.WikiPageServiceBaseImpl
 * @see com.liferay.wiki.service.impl.WikiPageServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=wiki", "json.web.service.context.path=WikiPage"}, service = WikiPageService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface WikiPageService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WikiPageServiceUtil} to access the wiki page remote service. Add custom service methods to {@link com.liferay.wiki.service.impl.WikiPageServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.wiki.model.WikiPage addPage(long nodeId,
		java.lang.String title, java.lang.String content,
		java.lang.String summary, boolean minorEdit, java.lang.String format,
		java.lang.String parentTitle, java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.wiki.model.WikiPage addPage(long nodeId,
		java.lang.String title, java.lang.String content,
		java.lang.String summary, boolean minorEdit,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.portal.kernel.repository.model.FileEntry addPageAttachment(
		long nodeId, java.lang.String title, java.lang.String fileName,
		java.io.File file, java.lang.String mimeType) throws PortalException;

	public com.liferay.portal.kernel.repository.model.FileEntry addPageAttachment(
		long nodeId, java.lang.String title, java.lang.String fileName,
		java.io.InputStream inputStream, java.lang.String mimeType)
		throws PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> addPageAttachments(
		long nodeId, java.lang.String title,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs)
		throws PortalException;

	public com.liferay.portal.kernel.repository.model.FileEntry addTempFileEntry(
		long nodeId, java.lang.String folderName, java.lang.String fileName,
		java.io.InputStream inputStream, java.lang.String mimeType)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0 replaced by {@link #addTempFileEntry(long,
	String, String, InputStream, String)}
	*/
	@java.lang.Deprecated
	public void addTempPageAttachment(long nodeId, java.lang.String fileName,
		java.lang.String tempFolderName, java.io.InputStream inputStream,
		java.lang.String mimeType) throws PortalException;

	public void changeParent(long nodeId, java.lang.String title,
		java.lang.String newParentTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public void copyPageAttachments(long templateNodeId,
		java.lang.String templateTitle, long nodeId, java.lang.String title)
		throws PortalException;

	public void deletePage(long nodeId, java.lang.String title)
		throws PortalException;

	/**
	* @deprecated As of 6.2.0 replaced by {@link #discardDraft(long, String,
	double)}
	*/
	@java.lang.Deprecated
	public void deletePage(long nodeId, java.lang.String title, double version)
		throws PortalException;

	public void deletePageAttachment(long nodeId, java.lang.String title,
		java.lang.String fileName) throws PortalException;

	public void deletePageAttachments(long nodeId, java.lang.String title)
		throws PortalException;

	public void deleteTempFileEntry(long nodeId, java.lang.String folderName,
		java.lang.String fileName) throws PortalException;

	public void deleteTrashPageAttachments(long nodeId, java.lang.String title)
		throws PortalException;

	public void discardDraft(long nodeId, java.lang.String title, double version)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.wiki.model.WikiPage fetchPage(long nodeId,
		java.lang.String title, double version) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.wiki.model.WikiPage> getChildren(
		long groupId, long nodeId, boolean head, java.lang.String parentTitle)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.wiki.model.WikiPage getDraftPage(long nodeId,
		java.lang.String title) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.wiki.model.WikiPage> getNodePages(
		long nodeId, int max) throws PortalException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getNodePagesRSS(long, int,
	String, double, String, String, String, String)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getNodePagesRSS(long nodeId, int max,
		java.lang.String type, double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getNodePagesRSS(long nodeId, int max,
		java.lang.String type, double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		java.lang.String attachmentURLPrefix) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.wiki.model.WikiPage> getOrphans(
		long groupId, long nodeId) throws PortalException;

	public com.liferay.wiki.model.WikiPage getPage(long groupId, long nodeId,
		java.lang.String title) throws PortalException;

	public com.liferay.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title) throws PortalException;

	public com.liferay.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, java.lang.Boolean head)
		throws PortalException;

	public com.liferay.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, double version) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.wiki.model.WikiPage> getPages(
		long groupId, long nodeId, boolean head, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiPage> obc)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.wiki.model.WikiPage> getPages(
		long groupId, long nodeId, boolean head, long userId,
		boolean includeOwner, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiPage> obc)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.wiki.model.WikiPage> getPages(
		long groupId, long userId, long nodeId, int status, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long groupId, long nodeId, boolean head)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long groupId, long nodeId, boolean head,
		long userId, boolean includeOwner, int status)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long groupId, long userId, long nodeId, int status)
		throws PortalException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getPagesRSS(long, String,
	int, String, double, String, String, String, String, Locale)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getPagesRSS(long companyId, long nodeId,
		java.lang.String title, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL, java.util.Locale locale)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getPagesRSS(long nodeId, java.lang.String title,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL, java.lang.String attachmentURLPrefix,
		java.util.Locale locale) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.wiki.model.WikiPage> getRecentChanges(
		long groupId, long nodeId, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRecentChangesCount(long groupId, long nodeId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getTempFileNames(long nodeId,
		java.lang.String folderName) throws PortalException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #renamePage(long, String,
	String, ServiceContext)}
	*/
	@java.lang.Deprecated
	public void movePage(long nodeId, java.lang.String title,
		java.lang.String newTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.portal.kernel.repository.model.FileEntry movePageAttachmentToTrash(
		long nodeId, java.lang.String title, java.lang.String fileName)
		throws PortalException;

	public com.liferay.wiki.model.WikiPage movePageToTrash(long nodeId,
		java.lang.String title) throws PortalException;

	public com.liferay.wiki.model.WikiPage movePageToTrash(long nodeId,
		java.lang.String title, double version) throws PortalException;

	public void renamePage(long nodeId, java.lang.String title,
		java.lang.String newTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public void restorePageAttachmentFromTrash(long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws PortalException;

	public void restorePageFromTrash(long resourcePrimKey)
		throws PortalException;

	public com.liferay.wiki.model.WikiPage revertPage(long nodeId,
		java.lang.String title, double version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public void subscribePage(long nodeId, java.lang.String title)
		throws PortalException;

	public void unsubscribePage(long nodeId, java.lang.String title)
		throws PortalException;

	public com.liferay.wiki.model.WikiPage updatePage(long nodeId,
		java.lang.String title, double version, java.lang.String content,
		java.lang.String summary, boolean minorEdit, java.lang.String format,
		java.lang.String parentTitle, java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;
}