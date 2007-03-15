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

package com.liferay.portlet.imagegallery.service.ejb;

import com.liferay.portlet.imagegallery.service.IGImageLocalService;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="IGImageLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portlet.imagegallery.service.IGImageLocalService
 * @see com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil
 * @see com.liferay.portlet.imagegallery.service.ejb.IGImageLocalServiceEJB
 * @see com.liferay.portlet.imagegallery.service.ejb.IGImageLocalServiceHome
 * @see com.liferay.portlet.imagegallery.service.impl.IGImageLocalServiceImpl
 *
 */
public class IGImageLocalServiceEJBImpl implements IGImageLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return IGImageLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return IGImageLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.imagegallery.model.IGImage addImage(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String description, java.io.File file,
		java.lang.String contentType, java.lang.String[] tagsEntries,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return IGImageLocalServiceFactory.getTxImpl().addImage(userId,
			folderId, description, file, contentType, tagsEntries,
			addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.imagegallery.model.IGImage addImage(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String description, java.io.File file,
		java.lang.String contentType, java.lang.String[] tagsEntries,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return IGImageLocalServiceFactory.getTxImpl().addImage(userId,
			folderId, description, file, contentType, tagsEntries,
			communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.imagegallery.model.IGImage addImage(
		java.lang.String userId, java.lang.String folderId,
		java.lang.String description, java.io.File file,
		java.lang.String contentType, java.lang.String[] tagsEntries,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return IGImageLocalServiceFactory.getTxImpl().addImage(userId,
			folderId, description, file, contentType, tagsEntries,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public void addImageResources(java.lang.String folderId,
		java.lang.String imageId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGImageLocalServiceFactory.getTxImpl().addImageResources(folderId,
			imageId, addCommunityPermissions, addGuestPermissions);
	}

	public void addImageResources(
		com.liferay.portlet.imagegallery.model.IGFolder folder,
		com.liferay.portlet.imagegallery.model.IGImage image,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGImageLocalServiceFactory.getTxImpl().addImageResources(folder, image,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addImageResources(java.lang.String folderId,
		java.lang.String imageId, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGImageLocalServiceFactory.getTxImpl().addImageResources(folderId,
			imageId, communityPermissions, guestPermissions);
	}

	public void addImageResources(
		com.liferay.portlet.imagegallery.model.IGFolder folder,
		com.liferay.portlet.imagegallery.model.IGImage image,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGImageLocalServiceFactory.getTxImpl().addImageResources(folder, image,
			communityPermissions, guestPermissions);
	}

	public void deleteImage(java.lang.String companyId, java.lang.String imageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGImageLocalServiceFactory.getTxImpl().deleteImage(companyId, imageId);
	}

	public void deleteImage(
		com.liferay.portlet.imagegallery.model.IGImage image)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGImageLocalServiceFactory.getTxImpl().deleteImage(image);
	}

	public void deleteImages(java.lang.String folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		IGImageLocalServiceFactory.getTxImpl().deleteImages(folderId);
	}

	public int getFoldersImagesCount(java.util.List folderIds)
		throws com.liferay.portal.SystemException {
		return IGImageLocalServiceFactory.getTxImpl().getFoldersImagesCount(folderIds);
	}

	public java.util.List getGroupImages(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return IGImageLocalServiceFactory.getTxImpl().getGroupImages(groupId,
			begin, end);
	}

	public java.util.List getGroupImages(long groupId, java.lang.String userId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return IGImageLocalServiceFactory.getTxImpl().getGroupImages(groupId,
			userId, begin, end);
	}

	public int getGroupImagesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return IGImageLocalServiceFactory.getTxImpl().getGroupImagesCount(groupId);
	}

	public int getGroupImagesCount(long groupId, java.lang.String userId)
		throws com.liferay.portal.SystemException {
		return IGImageLocalServiceFactory.getTxImpl().getGroupImagesCount(groupId,
			userId);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getImage(
		java.lang.String companyId, java.lang.String imageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return IGImageLocalServiceFactory.getTxImpl().getImage(companyId,
			imageId);
	}

	public java.util.List getImages(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		return IGImageLocalServiceFactory.getTxImpl().getImages(folderId);
	}

	public java.util.List getImages(java.lang.String folderId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return IGImageLocalServiceFactory.getTxImpl().getImages(folderId,
			begin, end);
	}

	public java.util.List getImages(java.lang.String folderId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return IGImageLocalServiceFactory.getTxImpl().getImages(folderId,
			begin, end, obc);
	}

	public int getImagesCount(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		return IGImageLocalServiceFactory.getTxImpl().getImagesCount(folderId);
	}

	public com.liferay.portlet.imagegallery.model.IGImage updateImage(
		java.lang.String companyId, java.lang.String imageId,
		java.lang.String folderId, java.lang.String description,
		java.io.File file, java.lang.String contentType,
		java.lang.String[] tagsEntries)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return IGImageLocalServiceFactory.getTxImpl().updateImage(companyId,
			imageId, folderId, description, file, contentType, tagsEntries);
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