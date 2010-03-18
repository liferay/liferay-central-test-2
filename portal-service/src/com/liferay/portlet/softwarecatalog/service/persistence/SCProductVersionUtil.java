/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.softwarecatalog.model.SCProductVersion;

import java.util.List;

/**
 * <a href="SCProductVersionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductVersionPersistence
 * @see       SCProductVersionPersistenceImpl
 * @generated
 */
public class SCProductVersionUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static SCProductVersion remove(SCProductVersion scProductVersion)
		throws SystemException {
		return getPersistence().remove(scProductVersion);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static SCProductVersion update(SCProductVersion scProductVersion,
		boolean merge) throws SystemException {
		return getPersistence().update(scProductVersion, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion) {
		getPersistence().cacheResult(scProductVersion);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions) {
		getPersistence().cacheResult(scProductVersions);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion create(
		long productVersionId) {
		return getPersistence().create(productVersionId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion remove(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException {
		return getPersistence().remove(productVersionId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(scProductVersion, merge);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion findByPrimaryKey(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException {
		return getPersistence().findByPrimaryKey(productVersionId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion fetchByPrimaryKey(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(productVersionId);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> findByProductEntryId(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByProductEntryId(productEntryId);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> findByProductEntryId(
		long productEntryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByProductEntryId(productEntryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> findByProductEntryId(
		long productEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByProductEntryId(productEntryId, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion findByProductEntryId_First(
		long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException {
		return getPersistence()
				   .findByProductEntryId_First(productEntryId, orderByComparator);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion findByProductEntryId_Last(
		long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException {
		return getPersistence()
				   .findByProductEntryId_Last(productEntryId, orderByComparator);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion[] findByProductEntryId_PrevAndNext(
		long productVersionId, long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException {
		return getPersistence()
				   .findByProductEntryId_PrevAndNext(productVersionId,
			productEntryId, orderByComparator);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion findByDirectDownloadURL(
		java.lang.String directDownloadURL)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException {
		return getPersistence().findByDirectDownloadURL(directDownloadURL);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion fetchByDirectDownloadURL(
		java.lang.String directDownloadURL)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByDirectDownloadURL(directDownloadURL);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersion fetchByDirectDownloadURL(
		java.lang.String directDownloadURL, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByDirectDownloadURL(directDownloadURL,
			retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByProductEntryId(long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByProductEntryId(productEntryId);
	}

	public static void removeByDirectDownloadURL(
		java.lang.String directDownloadURL)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException {
		getPersistence().removeByDirectDownloadURL(directDownloadURL);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByProductEntryId(long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByProductEntryId(productEntryId);
	}

	public static int countByDirectDownloadURL(
		java.lang.String directDownloadURL)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByDirectDownloadURL(directDownloadURL);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getSCFrameworkVersions(pk);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getSCFrameworkVersions(pk, start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .getSCFrameworkVersions(pk, start, end, orderByComparator);
	}

	public static int getSCFrameworkVersionsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getSCFrameworkVersionsSize(pk);
	}

	public static boolean containsSCFrameworkVersion(long pk,
		long scFrameworkVersionPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .containsSCFrameworkVersion(pk, scFrameworkVersionPK);
	}

	public static boolean containsSCFrameworkVersions(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsSCFrameworkVersions(pk);
	}

	public static void addSCFrameworkVersion(long pk, long scFrameworkVersionPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addSCFrameworkVersion(pk, scFrameworkVersionPK);
	}

	public static void addSCFrameworkVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addSCFrameworkVersion(pk, scFrameworkVersion);
	}

	public static void addSCFrameworkVersions(long pk,
		long[] scFrameworkVersionPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addSCFrameworkVersions(pk, scFrameworkVersionPKs);
	}

	public static void addSCFrameworkVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addSCFrameworkVersions(pk, scFrameworkVersions);
	}

	public static void clearSCFrameworkVersions(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearSCFrameworkVersions(pk);
	}

	public static void removeSCFrameworkVersion(long pk,
		long scFrameworkVersionPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeSCFrameworkVersion(pk, scFrameworkVersionPK);
	}

	public static void removeSCFrameworkVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeSCFrameworkVersion(pk, scFrameworkVersion);
	}

	public static void removeSCFrameworkVersions(long pk,
		long[] scFrameworkVersionPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeSCFrameworkVersions(pk, scFrameworkVersionPKs);
	}

	public static void removeSCFrameworkVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeSCFrameworkVersions(pk, scFrameworkVersions);
	}

	public static void setSCFrameworkVersions(long pk,
		long[] scFrameworkVersionPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setSCFrameworkVersions(pk, scFrameworkVersionPKs);
	}

	public static void setSCFrameworkVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setSCFrameworkVersions(pk, scFrameworkVersions);
	}

	public static SCProductVersionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SCProductVersionPersistence)PortalBeanLocatorUtil.locate(SCProductVersionPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(SCProductVersionPersistence persistence) {
		_persistence = persistence;
	}

	private static SCProductVersionPersistence _persistence;
}