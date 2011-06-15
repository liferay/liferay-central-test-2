/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.base;

import com.liferay.counter.service.CounterLocalService;

import com.liferay.documentlibrary.service.DLLocalService;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.LockLocalService;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.WebDAVPropsLocalService;
import com.liferay.portal.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.service.persistence.LockPersistence;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.WebDAVPropsPersistence;
import com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence;

import com.liferay.portlet.asset.service.AssetLinkLocalService;
import com.liferay.portlet.asset.service.persistence.AssetLinkPersistence;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalService;
import com.liferay.portlet.documentlibrary.service.DLAppLocalService;
import com.liferay.portlet.documentlibrary.service.DLAppService;
import com.liferay.portlet.documentlibrary.service.DLContentLocalService;
import com.liferay.portlet.documentlibrary.service.DLDocumentMetadataSetLocalService;
import com.liferay.portlet.documentlibrary.service.DLDocumentTypeLocalService;
import com.liferay.portlet.documentlibrary.service.DLDocumentTypeService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryService;
import com.liferay.portlet.documentlibrary.service.DLFileRankLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderService;
import com.liferay.portlet.documentlibrary.service.persistence.DLContentFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLContentPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLDocumentMetadataSetPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLDocumentTypeFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLDocumentTypePersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence;
import com.liferay.portlet.expando.service.ExpandoValueLocalService;
import com.liferay.portlet.expando.service.ExpandoValueService;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;

import java.util.List;

import javax.sql.DataSource;

/**
 * The base implementation of the d l file entry local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.documentlibrary.service.impl.DLFileEntryLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFileEntryLocalServiceImpl
 * @see com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil
 * @generated
 */
public abstract class DLFileEntryLocalServiceBaseImpl
	implements DLFileEntryLocalService, IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil} to access the d l file entry local service.
	 */

	/**
	 * Adds the d l file entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileEntry the d l file entry
	 * @return the d l file entry that was added
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry addDLFileEntry(DLFileEntry dlFileEntry)
		throws SystemException {
		dlFileEntry.setNew(true);

		dlFileEntry = dlFileEntryPersistence.update(dlFileEntry, false);

		Indexer indexer = IndexerRegistryUtil.getIndexer(getModelClassName());

		if (indexer != null) {
			try {
				indexer.reindex(dlFileEntry);
			}
			catch (SearchException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(se, se);
				}
			}
		}

		return dlFileEntry;
	}

	/**
	 * Creates a new d l file entry with the primary key. Does not add the d l file entry to the database.
	 *
	 * @param fileEntryId the primary key for the new d l file entry
	 * @return the new d l file entry
	 */
	public DLFileEntry createDLFileEntry(long fileEntryId) {
		return dlFileEntryPersistence.create(fileEntryId);
	}

	/**
	 * Deletes the d l file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileEntryId the primary key of the d l file entry
	 * @throws PortalException if a d l file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteDLFileEntry(long fileEntryId)
		throws PortalException, SystemException {
		DLFileEntry dlFileEntry = dlFileEntryPersistence.remove(fileEntryId);

		Indexer indexer = IndexerRegistryUtil.getIndexer(getModelClassName());

		if (indexer != null) {
			try {
				indexer.delete(dlFileEntry);
			}
			catch (SearchException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(se, se);
				}
			}
		}
	}

	/**
	 * Deletes the d l file entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileEntry the d l file entry
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteDLFileEntry(DLFileEntry dlFileEntry)
		throws SystemException {
		dlFileEntryPersistence.remove(dlFileEntry);

		Indexer indexer = IndexerRegistryUtil.getIndexer(getModelClassName());

		if (indexer != null) {
			try {
				indexer.delete(dlFileEntry);
			}
			catch (SearchException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(se, se);
				}
			}
		}
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return dlFileEntryPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return dlFileEntryPersistence.findWithDynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return dlFileEntryPersistence.findWithDynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	public long dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return dlFileEntryPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the d l file entry with the primary key.
	 *
	 * @param fileEntryId the primary key of the d l file entry
	 * @return the d l file entry
	 * @throws PortalException if a d l file entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry getDLFileEntry(long fileEntryId)
		throws PortalException, SystemException {
		return dlFileEntryPersistence.findByPrimaryKey(fileEntryId);
	}

	/**
	 * Returns the d l file entry with the UUID in the group.
	 *
	 * @param uuid the UUID of d l file entry
	 * @param groupId the group id of the d l file entry
	 * @return the d l file entry
	 * @throws PortalException if a d l file entry with the UUID in the group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry getDLFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException, SystemException {
		return dlFileEntryPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the d l file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d l file entries
	 * @param end the upper bound of the range of d l file entries (not inclusive)
	 * @return the range of d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntry> getDLFileEntries(int start, int end)
		throws SystemException {
		return dlFileEntryPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of d l file entries.
	 *
	 * @return the number of d l file entries
	 * @throws SystemException if a system exception occurred
	 */
	public int getDLFileEntriesCount() throws SystemException {
		return dlFileEntryPersistence.countAll();
	}

	/**
	 * Updates the d l file entry in the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileEntry the d l file entry
	 * @return the d l file entry that was updated
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry updateDLFileEntry(DLFileEntry dlFileEntry)
		throws SystemException {
		return updateDLFileEntry(dlFileEntry, true);
	}

	/**
	 * Updates the d l file entry in the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileEntry the d l file entry
	 * @param merge whether to merge the d l file entry with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	 * @return the d l file entry that was updated
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntry updateDLFileEntry(DLFileEntry dlFileEntry, boolean merge)
		throws SystemException {
		dlFileEntry.setNew(false);

		dlFileEntry = dlFileEntryPersistence.update(dlFileEntry, merge);

		Indexer indexer = IndexerRegistryUtil.getIndexer(getModelClassName());

		if (indexer != null) {
			try {
				indexer.reindex(dlFileEntry);
			}
			catch (SearchException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(se, se);
				}
			}
		}

		return dlFileEntry;
	}

	/**
	 * Returns the d l app local service.
	 *
	 * @return the d l app local service
	 */
	public DLAppLocalService getDLAppLocalService() {
		return dlAppLocalService;
	}

	/**
	 * Sets the d l app local service.
	 *
	 * @param dlAppLocalService the d l app local service
	 */
	public void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		this.dlAppLocalService = dlAppLocalService;
	}

	/**
	 * Returns the d l app remote service.
	 *
	 * @return the d l app remote service
	 */
	public DLAppService getDLAppService() {
		return dlAppService;
	}

	/**
	 * Sets the d l app remote service.
	 *
	 * @param dlAppService the d l app remote service
	 */
	public void setDLAppService(DLAppService dlAppService) {
		this.dlAppService = dlAppService;
	}

	/**
	 * Returns the d l app helper local service.
	 *
	 * @return the d l app helper local service
	 */
	public DLAppHelperLocalService getDLAppHelperLocalService() {
		return dlAppHelperLocalService;
	}

	/**
	 * Sets the d l app helper local service.
	 *
	 * @param dlAppHelperLocalService the d l app helper local service
	 */
	public void setDLAppHelperLocalService(
		DLAppHelperLocalService dlAppHelperLocalService) {
		this.dlAppHelperLocalService = dlAppHelperLocalService;
	}

	/**
	 * Returns the d l content local service.
	 *
	 * @return the d l content local service
	 */
	public DLContentLocalService getDLContentLocalService() {
		return dlContentLocalService;
	}

	/**
	 * Sets the d l content local service.
	 *
	 * @param dlContentLocalService the d l content local service
	 */
	public void setDLContentLocalService(
		DLContentLocalService dlContentLocalService) {
		this.dlContentLocalService = dlContentLocalService;
	}

	/**
	 * Returns the d l content persistence.
	 *
	 * @return the d l content persistence
	 */
	public DLContentPersistence getDLContentPersistence() {
		return dlContentPersistence;
	}

	/**
	 * Sets the d l content persistence.
	 *
	 * @param dlContentPersistence the d l content persistence
	 */
	public void setDLContentPersistence(
		DLContentPersistence dlContentPersistence) {
		this.dlContentPersistence = dlContentPersistence;
	}

	/**
	 * Returns the d l content finder.
	 *
	 * @return the d l content finder
	 */
	public DLContentFinder getDLContentFinder() {
		return dlContentFinder;
	}

	/**
	 * Sets the d l content finder.
	 *
	 * @param dlContentFinder the d l content finder
	 */
	public void setDLContentFinder(DLContentFinder dlContentFinder) {
		this.dlContentFinder = dlContentFinder;
	}

	/**
	 * Returns the d l document metadata set local service.
	 *
	 * @return the d l document metadata set local service
	 */
	public DLDocumentMetadataSetLocalService getDLDocumentMetadataSetLocalService() {
		return dlDocumentMetadataSetLocalService;
	}

	/**
	 * Sets the d l document metadata set local service.
	 *
	 * @param dlDocumentMetadataSetLocalService the d l document metadata set local service
	 */
	public void setDLDocumentMetadataSetLocalService(
		DLDocumentMetadataSetLocalService dlDocumentMetadataSetLocalService) {
		this.dlDocumentMetadataSetLocalService = dlDocumentMetadataSetLocalService;
	}

	/**
	 * Returns the d l document metadata set persistence.
	 *
	 * @return the d l document metadata set persistence
	 */
	public DLDocumentMetadataSetPersistence getDLDocumentMetadataSetPersistence() {
		return dlDocumentMetadataSetPersistence;
	}

	/**
	 * Sets the d l document metadata set persistence.
	 *
	 * @param dlDocumentMetadataSetPersistence the d l document metadata set persistence
	 */
	public void setDLDocumentMetadataSetPersistence(
		DLDocumentMetadataSetPersistence dlDocumentMetadataSetPersistence) {
		this.dlDocumentMetadataSetPersistence = dlDocumentMetadataSetPersistence;
	}

	/**
	 * Returns the d l document type local service.
	 *
	 * @return the d l document type local service
	 */
	public DLDocumentTypeLocalService getDLDocumentTypeLocalService() {
		return dlDocumentTypeLocalService;
	}

	/**
	 * Sets the d l document type local service.
	 *
	 * @param dlDocumentTypeLocalService the d l document type local service
	 */
	public void setDLDocumentTypeLocalService(
		DLDocumentTypeLocalService dlDocumentTypeLocalService) {
		this.dlDocumentTypeLocalService = dlDocumentTypeLocalService;
	}

	/**
	 * Returns the d l document type remote service.
	 *
	 * @return the d l document type remote service
	 */
	public DLDocumentTypeService getDLDocumentTypeService() {
		return dlDocumentTypeService;
	}

	/**
	 * Sets the d l document type remote service.
	 *
	 * @param dlDocumentTypeService the d l document type remote service
	 */
	public void setDLDocumentTypeService(
		DLDocumentTypeService dlDocumentTypeService) {
		this.dlDocumentTypeService = dlDocumentTypeService;
	}

	/**
	 * Returns the d l document type persistence.
	 *
	 * @return the d l document type persistence
	 */
	public DLDocumentTypePersistence getDLDocumentTypePersistence() {
		return dlDocumentTypePersistence;
	}

	/**
	 * Sets the d l document type persistence.
	 *
	 * @param dlDocumentTypePersistence the d l document type persistence
	 */
	public void setDLDocumentTypePersistence(
		DLDocumentTypePersistence dlDocumentTypePersistence) {
		this.dlDocumentTypePersistence = dlDocumentTypePersistence;
	}

	/**
	 * Returns the d l document type finder.
	 *
	 * @return the d l document type finder
	 */
	public DLDocumentTypeFinder getDLDocumentTypeFinder() {
		return dlDocumentTypeFinder;
	}

	/**
	 * Sets the d l document type finder.
	 *
	 * @param dlDocumentTypeFinder the d l document type finder
	 */
	public void setDLDocumentTypeFinder(
		DLDocumentTypeFinder dlDocumentTypeFinder) {
		this.dlDocumentTypeFinder = dlDocumentTypeFinder;
	}

	/**
	 * Returns the d l file entry local service.
	 *
	 * @return the d l file entry local service
	 */
	public DLFileEntryLocalService getDLFileEntryLocalService() {
		return dlFileEntryLocalService;
	}

	/**
	 * Sets the d l file entry local service.
	 *
	 * @param dlFileEntryLocalService the d l file entry local service
	 */
	public void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {
		this.dlFileEntryLocalService = dlFileEntryLocalService;
	}

	/**
	 * Returns the d l file entry remote service.
	 *
	 * @return the d l file entry remote service
	 */
	public DLFileEntryService getDLFileEntryService() {
		return dlFileEntryService;
	}

	/**
	 * Sets the d l file entry remote service.
	 *
	 * @param dlFileEntryService the d l file entry remote service
	 */
	public void setDLFileEntryService(DLFileEntryService dlFileEntryService) {
		this.dlFileEntryService = dlFileEntryService;
	}

	/**
	 * Returns the d l file entry persistence.
	 *
	 * @return the d l file entry persistence
	 */
	public DLFileEntryPersistence getDLFileEntryPersistence() {
		return dlFileEntryPersistence;
	}

	/**
	 * Sets the d l file entry persistence.
	 *
	 * @param dlFileEntryPersistence the d l file entry persistence
	 */
	public void setDLFileEntryPersistence(
		DLFileEntryPersistence dlFileEntryPersistence) {
		this.dlFileEntryPersistence = dlFileEntryPersistence;
	}

	/**
	 * Returns the d l file entry finder.
	 *
	 * @return the d l file entry finder
	 */
	public DLFileEntryFinder getDLFileEntryFinder() {
		return dlFileEntryFinder;
	}

	/**
	 * Sets the d l file entry finder.
	 *
	 * @param dlFileEntryFinder the d l file entry finder
	 */
	public void setDLFileEntryFinder(DLFileEntryFinder dlFileEntryFinder) {
		this.dlFileEntryFinder = dlFileEntryFinder;
	}

	/**
	 * Returns the d l file rank local service.
	 *
	 * @return the d l file rank local service
	 */
	public DLFileRankLocalService getDLFileRankLocalService() {
		return dlFileRankLocalService;
	}

	/**
	 * Sets the d l file rank local service.
	 *
	 * @param dlFileRankLocalService the d l file rank local service
	 */
	public void setDLFileRankLocalService(
		DLFileRankLocalService dlFileRankLocalService) {
		this.dlFileRankLocalService = dlFileRankLocalService;
	}

	/**
	 * Returns the d l file rank persistence.
	 *
	 * @return the d l file rank persistence
	 */
	public DLFileRankPersistence getDLFileRankPersistence() {
		return dlFileRankPersistence;
	}

	/**
	 * Sets the d l file rank persistence.
	 *
	 * @param dlFileRankPersistence the d l file rank persistence
	 */
	public void setDLFileRankPersistence(
		DLFileRankPersistence dlFileRankPersistence) {
		this.dlFileRankPersistence = dlFileRankPersistence;
	}

	/**
	 * Returns the d l file rank finder.
	 *
	 * @return the d l file rank finder
	 */
	public DLFileRankFinder getDLFileRankFinder() {
		return dlFileRankFinder;
	}

	/**
	 * Sets the d l file rank finder.
	 *
	 * @param dlFileRankFinder the d l file rank finder
	 */
	public void setDLFileRankFinder(DLFileRankFinder dlFileRankFinder) {
		this.dlFileRankFinder = dlFileRankFinder;
	}

	/**
	 * Returns the d l file shortcut local service.
	 *
	 * @return the d l file shortcut local service
	 */
	public DLFileShortcutLocalService getDLFileShortcutLocalService() {
		return dlFileShortcutLocalService;
	}

	/**
	 * Sets the d l file shortcut local service.
	 *
	 * @param dlFileShortcutLocalService the d l file shortcut local service
	 */
	public void setDLFileShortcutLocalService(
		DLFileShortcutLocalService dlFileShortcutLocalService) {
		this.dlFileShortcutLocalService = dlFileShortcutLocalService;
	}

	/**
	 * Returns the d l file shortcut remote service.
	 *
	 * @return the d l file shortcut remote service
	 */
	public DLFileShortcutService getDLFileShortcutService() {
		return dlFileShortcutService;
	}

	/**
	 * Sets the d l file shortcut remote service.
	 *
	 * @param dlFileShortcutService the d l file shortcut remote service
	 */
	public void setDLFileShortcutService(
		DLFileShortcutService dlFileShortcutService) {
		this.dlFileShortcutService = dlFileShortcutService;
	}

	/**
	 * Returns the d l file shortcut persistence.
	 *
	 * @return the d l file shortcut persistence
	 */
	public DLFileShortcutPersistence getDLFileShortcutPersistence() {
		return dlFileShortcutPersistence;
	}

	/**
	 * Sets the d l file shortcut persistence.
	 *
	 * @param dlFileShortcutPersistence the d l file shortcut persistence
	 */
	public void setDLFileShortcutPersistence(
		DLFileShortcutPersistence dlFileShortcutPersistence) {
		this.dlFileShortcutPersistence = dlFileShortcutPersistence;
	}

	/**
	 * Returns the d l file version persistence.
	 *
	 * @return the d l file version persistence
	 */
	public DLFileVersionPersistence getDLFileVersionPersistence() {
		return dlFileVersionPersistence;
	}

	/**
	 * Sets the d l file version persistence.
	 *
	 * @param dlFileVersionPersistence the d l file version persistence
	 */
	public void setDLFileVersionPersistence(
		DLFileVersionPersistence dlFileVersionPersistence) {
		this.dlFileVersionPersistence = dlFileVersionPersistence;
	}

	/**
	 * Returns the d l folder local service.
	 *
	 * @return the d l folder local service
	 */
	public DLFolderLocalService getDLFolderLocalService() {
		return dlFolderLocalService;
	}

	/**
	 * Sets the d l folder local service.
	 *
	 * @param dlFolderLocalService the d l folder local service
	 */
	public void setDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {
		this.dlFolderLocalService = dlFolderLocalService;
	}

	/**
	 * Returns the d l folder remote service.
	 *
	 * @return the d l folder remote service
	 */
	public DLFolderService getDLFolderService() {
		return dlFolderService;
	}

	/**
	 * Sets the d l folder remote service.
	 *
	 * @param dlFolderService the d l folder remote service
	 */
	public void setDLFolderService(DLFolderService dlFolderService) {
		this.dlFolderService = dlFolderService;
	}

	/**
	 * Returns the d l folder persistence.
	 *
	 * @return the d l folder persistence
	 */
	public DLFolderPersistence getDLFolderPersistence() {
		return dlFolderPersistence;
	}

	/**
	 * Sets the d l folder persistence.
	 *
	 * @param dlFolderPersistence the d l folder persistence
	 */
	public void setDLFolderPersistence(DLFolderPersistence dlFolderPersistence) {
		this.dlFolderPersistence = dlFolderPersistence;
	}

	/**
	 * Returns the d l folder finder.
	 *
	 * @return the d l folder finder
	 */
	public DLFolderFinder getDLFolderFinder() {
		return dlFolderFinder;
	}

	/**
	 * Sets the d l folder finder.
	 *
	 * @param dlFolderFinder the d l folder finder
	 */
	public void setDLFolderFinder(DLFolderFinder dlFolderFinder) {
		this.dlFolderFinder = dlFolderFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the d l local service.
	 *
	 * @return the d l local service
	 */
	public DLLocalService getDLLocalService() {
		return dlLocalService;
	}

	/**
	 * Sets the d l local service.
	 *
	 * @param dlLocalService the d l local service
	 */
	public void setDLLocalService(DLLocalService dlLocalService) {
		this.dlLocalService = dlLocalService;
	}

	/**
	 * Returns the lock local service.
	 *
	 * @return the lock local service
	 */
	public LockLocalService getLockLocalService() {
		return lockLocalService;
	}

	/**
	 * Sets the lock local service.
	 *
	 * @param lockLocalService the lock local service
	 */
	public void setLockLocalService(LockLocalService lockLocalService) {
		this.lockLocalService = lockLocalService;
	}

	/**
	 * Returns the lock persistence.
	 *
	 * @return the lock persistence
	 */
	public LockPersistence getLockPersistence() {
		return lockPersistence;
	}

	/**
	 * Sets the lock persistence.
	 *
	 * @param lockPersistence the lock persistence
	 */
	public void setLockPersistence(LockPersistence lockPersistence) {
		this.lockPersistence = lockPersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the resource remote service.
	 *
	 * @return the resource remote service
	 */
	public ResourceService getResourceService() {
		return resourceService;
	}

	/**
	 * Sets the resource remote service.
	 *
	 * @param resourceService the resource remote service
	 */
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	/**
	 * Returns the resource persistence.
	 *
	 * @return the resource persistence
	 */
	public ResourcePersistence getResourcePersistence() {
		return resourcePersistence;
	}

	/**
	 * Sets the resource persistence.
	 *
	 * @param resourcePersistence the resource persistence
	 */
	public void setResourcePersistence(ResourcePersistence resourcePersistence) {
		this.resourcePersistence = resourcePersistence;
	}

	/**
	 * Returns the resource finder.
	 *
	 * @return the resource finder
	 */
	public ResourceFinder getResourceFinder() {
		return resourceFinder;
	}

	/**
	 * Sets the resource finder.
	 *
	 * @param resourceFinder the resource finder
	 */
	public void setResourceFinder(ResourceFinder resourceFinder) {
		this.resourceFinder = resourceFinder;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Returns the user finder.
	 *
	 * @return the user finder
	 */
	public UserFinder getUserFinder() {
		return userFinder;
	}

	/**
	 * Sets the user finder.
	 *
	 * @param userFinder the user finder
	 */
	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	/**
	 * Returns the web d a v props local service.
	 *
	 * @return the web d a v props local service
	 */
	public WebDAVPropsLocalService getWebDAVPropsLocalService() {
		return webDAVPropsLocalService;
	}

	/**
	 * Sets the web d a v props local service.
	 *
	 * @param webDAVPropsLocalService the web d a v props local service
	 */
	public void setWebDAVPropsLocalService(
		WebDAVPropsLocalService webDAVPropsLocalService) {
		this.webDAVPropsLocalService = webDAVPropsLocalService;
	}

	/**
	 * Returns the web d a v props persistence.
	 *
	 * @return the web d a v props persistence
	 */
	public WebDAVPropsPersistence getWebDAVPropsPersistence() {
		return webDAVPropsPersistence;
	}

	/**
	 * Sets the web d a v props persistence.
	 *
	 * @param webDAVPropsPersistence the web d a v props persistence
	 */
	public void setWebDAVPropsPersistence(
		WebDAVPropsPersistence webDAVPropsPersistence) {
		this.webDAVPropsPersistence = webDAVPropsPersistence;
	}

	/**
	 * Returns the workflow instance link local service.
	 *
	 * @return the workflow instance link local service
	 */
	public WorkflowInstanceLinkLocalService getWorkflowInstanceLinkLocalService() {
		return workflowInstanceLinkLocalService;
	}

	/**
	 * Sets the workflow instance link local service.
	 *
	 * @param workflowInstanceLinkLocalService the workflow instance link local service
	 */
	public void setWorkflowInstanceLinkLocalService(
		WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService) {
		this.workflowInstanceLinkLocalService = workflowInstanceLinkLocalService;
	}

	/**
	 * Returns the workflow instance link persistence.
	 *
	 * @return the workflow instance link persistence
	 */
	public WorkflowInstanceLinkPersistence getWorkflowInstanceLinkPersistence() {
		return workflowInstanceLinkPersistence;
	}

	/**
	 * Sets the workflow instance link persistence.
	 *
	 * @param workflowInstanceLinkPersistence the workflow instance link persistence
	 */
	public void setWorkflowInstanceLinkPersistence(
		WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence) {
		this.workflowInstanceLinkPersistence = workflowInstanceLinkPersistence;
	}

	/**
	 * Returns the asset link local service.
	 *
	 * @return the asset link local service
	 */
	public AssetLinkLocalService getAssetLinkLocalService() {
		return assetLinkLocalService;
	}

	/**
	 * Sets the asset link local service.
	 *
	 * @param assetLinkLocalService the asset link local service
	 */
	public void setAssetLinkLocalService(
		AssetLinkLocalService assetLinkLocalService) {
		this.assetLinkLocalService = assetLinkLocalService;
	}

	/**
	 * Returns the asset link persistence.
	 *
	 * @return the asset link persistence
	 */
	public AssetLinkPersistence getAssetLinkPersistence() {
		return assetLinkPersistence;
	}

	/**
	 * Sets the asset link persistence.
	 *
	 * @param assetLinkPersistence the asset link persistence
	 */
	public void setAssetLinkPersistence(
		AssetLinkPersistence assetLinkPersistence) {
		this.assetLinkPersistence = assetLinkPersistence;
	}

	/**
	 * Returns the expando value local service.
	 *
	 * @return the expando value local service
	 */
	public ExpandoValueLocalService getExpandoValueLocalService() {
		return expandoValueLocalService;
	}

	/**
	 * Sets the expando value local service.
	 *
	 * @param expandoValueLocalService the expando value local service
	 */
	public void setExpandoValueLocalService(
		ExpandoValueLocalService expandoValueLocalService) {
		this.expandoValueLocalService = expandoValueLocalService;
	}

	/**
	 * Returns the expando value remote service.
	 *
	 * @return the expando value remote service
	 */
	public ExpandoValueService getExpandoValueService() {
		return expandoValueService;
	}

	/**
	 * Sets the expando value remote service.
	 *
	 * @param expandoValueService the expando value remote service
	 */
	public void setExpandoValueService(ExpandoValueService expandoValueService) {
		this.expandoValueService = expandoValueService;
	}

	/**
	 * Returns the expando value persistence.
	 *
	 * @return the expando value persistence
	 */
	public ExpandoValuePersistence getExpandoValuePersistence() {
		return expandoValuePersistence;
	}

	/**
	 * Sets the expando value persistence.
	 *
	 * @param expandoValuePersistence the expando value persistence
	 */
	public void setExpandoValuePersistence(
		ExpandoValuePersistence expandoValuePersistence) {
		this.expandoValuePersistence = expandoValuePersistence;
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	protected Class<?> getModelClass() {
		return DLFileEntry.class;
	}

	protected String getModelClassName() {
		return DLFileEntry.class.getName();
	}

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = dlFileEntryPersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = DLAppLocalService.class)
	protected DLAppLocalService dlAppLocalService;
	@BeanReference(type = DLAppService.class)
	protected DLAppService dlAppService;
	@BeanReference(type = DLAppHelperLocalService.class)
	protected DLAppHelperLocalService dlAppHelperLocalService;
	@BeanReference(type = DLContentLocalService.class)
	protected DLContentLocalService dlContentLocalService;
	@BeanReference(type = DLContentPersistence.class)
	protected DLContentPersistence dlContentPersistence;
	@BeanReference(type = DLContentFinder.class)
	protected DLContentFinder dlContentFinder;
	@BeanReference(type = DLDocumentMetadataSetLocalService.class)
	protected DLDocumentMetadataSetLocalService dlDocumentMetadataSetLocalService;
	@BeanReference(type = DLDocumentMetadataSetPersistence.class)
	protected DLDocumentMetadataSetPersistence dlDocumentMetadataSetPersistence;
	@BeanReference(type = DLDocumentTypeLocalService.class)
	protected DLDocumentTypeLocalService dlDocumentTypeLocalService;
	@BeanReference(type = DLDocumentTypeService.class)
	protected DLDocumentTypeService dlDocumentTypeService;
	@BeanReference(type = DLDocumentTypePersistence.class)
	protected DLDocumentTypePersistence dlDocumentTypePersistence;
	@BeanReference(type = DLDocumentTypeFinder.class)
	protected DLDocumentTypeFinder dlDocumentTypeFinder;
	@BeanReference(type = DLFileEntryLocalService.class)
	protected DLFileEntryLocalService dlFileEntryLocalService;
	@BeanReference(type = DLFileEntryService.class)
	protected DLFileEntryService dlFileEntryService;
	@BeanReference(type = DLFileEntryPersistence.class)
	protected DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(type = DLFileEntryFinder.class)
	protected DLFileEntryFinder dlFileEntryFinder;
	@BeanReference(type = DLFileRankLocalService.class)
	protected DLFileRankLocalService dlFileRankLocalService;
	@BeanReference(type = DLFileRankPersistence.class)
	protected DLFileRankPersistence dlFileRankPersistence;
	@BeanReference(type = DLFileRankFinder.class)
	protected DLFileRankFinder dlFileRankFinder;
	@BeanReference(type = DLFileShortcutLocalService.class)
	protected DLFileShortcutLocalService dlFileShortcutLocalService;
	@BeanReference(type = DLFileShortcutService.class)
	protected DLFileShortcutService dlFileShortcutService;
	@BeanReference(type = DLFileShortcutPersistence.class)
	protected DLFileShortcutPersistence dlFileShortcutPersistence;
	@BeanReference(type = DLFileVersionPersistence.class)
	protected DLFileVersionPersistence dlFileVersionPersistence;
	@BeanReference(type = DLFolderLocalService.class)
	protected DLFolderLocalService dlFolderLocalService;
	@BeanReference(type = DLFolderService.class)
	protected DLFolderService dlFolderService;
	@BeanReference(type = DLFolderPersistence.class)
	protected DLFolderPersistence dlFolderPersistence;
	@BeanReference(type = DLFolderFinder.class)
	protected DLFolderFinder dlFolderFinder;
	@BeanReference(type = CounterLocalService.class)
	protected CounterLocalService counterLocalService;
	@BeanReference(type = DLLocalService.class)
	protected DLLocalService dlLocalService;
	@BeanReference(type = LockLocalService.class)
	protected LockLocalService lockLocalService;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = ResourceLocalService.class)
	protected ResourceLocalService resourceLocalService;
	@BeanReference(type = ResourceService.class)
	protected ResourceService resourceService;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = ResourceFinder.class)
	protected ResourceFinder resourceFinder;
	@BeanReference(type = UserLocalService.class)
	protected UserLocalService userLocalService;
	@BeanReference(type = UserService.class)
	protected UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	@BeanReference(type = WebDAVPropsLocalService.class)
	protected WebDAVPropsLocalService webDAVPropsLocalService;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WorkflowInstanceLinkLocalService.class)
	protected WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	@BeanReference(type = AssetLinkLocalService.class)
	protected AssetLinkLocalService assetLinkLocalService;
	@BeanReference(type = AssetLinkPersistence.class)
	protected AssetLinkPersistence assetLinkPersistence;
	@BeanReference(type = ExpandoValueLocalService.class)
	protected ExpandoValueLocalService expandoValueLocalService;
	@BeanReference(type = ExpandoValueService.class)
	protected ExpandoValueService expandoValueService;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	private static Log _log = LogFactoryUtil.getLog(DLFileEntryLocalServiceBaseImpl.class);
	private String _beanIdentifier;
}