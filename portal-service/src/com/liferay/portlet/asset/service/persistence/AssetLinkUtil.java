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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.asset.model.AssetLink;

import java.util.List;

/**
 * <a href="AssetLinkUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetLinkPersistence
 * @see       AssetLinkPersistenceImpl
 * @generated
 */
public class AssetLinkUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(AssetLink)
	 */
	public static void clearCache(AssetLink assetLink) {
		getPersistence().clearCache(assetLink);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static AssetLink remove(AssetLink assetLink)
		throws SystemException {
		return getPersistence().remove(assetLink);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static AssetLink update(AssetLink assetLink, boolean merge)
		throws SystemException {
		return getPersistence().update(assetLink, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.asset.model.AssetLink assetLink) {
		getPersistence().cacheResult(assetLink);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetLink> assetLinks) {
		getPersistence().cacheResult(assetLinks);
	}

	public static com.liferay.portlet.asset.model.AssetLink create(long linkId) {
		return getPersistence().create(linkId);
	}

	public static com.liferay.portlet.asset.model.AssetLink remove(long linkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence().remove(linkId);
	}

	public static com.liferay.portlet.asset.model.AssetLink updateImpl(
		com.liferay.portlet.asset.model.AssetLink assetLink, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(assetLink, merge);
	}

	public static com.liferay.portlet.asset.model.AssetLink findByPrimaryKey(
		long linkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence().findByPrimaryKey(linkId);
	}

	public static com.liferay.portlet.asset.model.AssetLink fetchByPrimaryKey(
		long linkId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(linkId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1(
		long entryId1)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE1(entryId1);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1(
		long entryId1, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE1(entryId1, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1(
		long entryId1, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE1(entryId1, start, end, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetLink findByE1_First(
		long entryId1,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence().findByE1_First(entryId1, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetLink findByE1_Last(
		long entryId1,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence().findByE1_Last(entryId1, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetLink[] findByE1_PrevAndNext(
		long linkId, long entryId1,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE1_PrevAndNext(linkId, entryId1, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2(
		long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE2(entryId2);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2(
		long entryId2, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE2(entryId2, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2(
		long entryId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE2(entryId2, start, end, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetLink findByE2_First(
		long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence().findByE2_First(entryId2, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetLink findByE2_Last(
		long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence().findByE2_Last(entryId2, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetLink[] findByE2_PrevAndNext(
		long linkId, long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE2_PrevAndNext(linkId, entryId2, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE_E(
		long entryId1, long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE_E(entryId1, entryId2);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE_E(
		long entryId1, long entryId2, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE_E(entryId1, entryId2, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE_E(
		long entryId1, long entryId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByE_E(entryId1, entryId2, start, end, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetLink findByE_E_First(
		long entryId1, long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE_E_First(entryId1, entryId2, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetLink findByE_E_Last(
		long entryId1, long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE_E_Last(entryId1, entryId2, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetLink[] findByE_E_PrevAndNext(
		long linkId, long entryId1, long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE_E_PrevAndNext(linkId, entryId1, entryId2,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1_T(
		long entryId1, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE1_T(entryId1, type);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1_T(
		long entryId1, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE1_T(entryId1, type, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1_T(
		long entryId1, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByE1_T(entryId1, type, start, end, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetLink findByE1_T_First(
		long entryId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE1_T_First(entryId1, type, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetLink findByE1_T_Last(
		long entryId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE1_T_Last(entryId1, type, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetLink[] findByE1_T_PrevAndNext(
		long linkId, long entryId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE1_T_PrevAndNext(linkId, entryId1, type,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2_T(
		long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE2_T(entryId2, type);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2_T(
		long entryId2, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE2_T(entryId2, type, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2_T(
		long entryId2, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByE2_T(entryId2, type, start, end, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetLink findByE2_T_First(
		long entryId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE2_T_First(entryId2, type, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetLink findByE2_T_Last(
		long entryId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE2_T_Last(entryId2, type, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetLink[] findByE2_T_PrevAndNext(
		long linkId, long entryId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE2_T_PrevAndNext(linkId, entryId2, type,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE_E_T(
		long entryId1, long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE_E_T(entryId1, entryId2, type);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE_E_T(
		long entryId1, long entryId2, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE_E_T(entryId1, entryId2, type, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE_E_T(
		long entryId1, long entryId2, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByE_E_T(entryId1, entryId2, type, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetLink findByE_E_T_First(
		long entryId1, long entryId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE_E_T_First(entryId1, entryId2, type,
			orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetLink findByE_E_T_Last(
		long entryId1, long entryId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE_E_T_Last(entryId1, entryId2, type, orderByComparator);
	}

	public static com.liferay.portlet.asset.model.AssetLink[] findByE_E_T_PrevAndNext(
		long linkId, long entryId1, long entryId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE_E_T_PrevAndNext(linkId, entryId1, entryId2, type,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByE1(long entryId1)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByE1(entryId1);
	}

	public static void removeByE2(long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByE2(entryId2);
	}

	public static void removeByE_E(long entryId1, long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByE_E(entryId1, entryId2);
	}

	public static void removeByE1_T(long entryId1, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByE1_T(entryId1, type);
	}

	public static void removeByE2_T(long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByE2_T(entryId2, type);
	}

	public static void removeByE_E_T(long entryId1, long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByE_E_T(entryId1, entryId2, type);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByE1(long entryId1)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByE1(entryId1);
	}

	public static int countByE2(long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByE2(entryId2);
	}

	public static int countByE_E(long entryId1, long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByE_E(entryId1, entryId2);
	}

	public static int countByE1_T(long entryId1, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByE1_T(entryId1, type);
	}

	public static int countByE2_T(long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByE2_T(entryId2, type);
	}

	public static int countByE_E_T(long entryId1, long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByE_E_T(entryId1, entryId2, type);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static AssetLinkPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetLinkPersistence)PortalBeanLocatorUtil.locate(AssetLinkPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(AssetLinkPersistence persistence) {
		_persistence = persistence;
	}

	private static AssetLinkPersistence _persistence;
}