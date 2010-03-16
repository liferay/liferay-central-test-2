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

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.asset.model.AssetLink;

/**
 * <a href="AssetLinkPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetLinkPersistenceImpl
 * @see       AssetLinkUtil
 * @generated
 */
public interface AssetLinkPersistence extends BasePersistence<AssetLink> {
	public void cacheResult(com.liferay.portlet.asset.model.AssetLink assetLink);

	public void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetLink> assetLinks);

	public com.liferay.portlet.asset.model.AssetLink create(long linkId);

	public com.liferay.portlet.asset.model.AssetLink remove(long linkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public com.liferay.portlet.asset.model.AssetLink updateImpl(
		com.liferay.portlet.asset.model.AssetLink assetLink, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetLink findByPrimaryKey(
		long linkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public com.liferay.portlet.asset.model.AssetLink fetchByPrimaryKey(
		long linkId) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1(
		long entryId1)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1(
		long entryId1, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1(
		long entryId1, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetLink findByE1_First(
		long entryId1, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public com.liferay.portlet.asset.model.AssetLink findByE1_Last(
		long entryId1, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public com.liferay.portlet.asset.model.AssetLink[] findByE1_PrevAndNext(
		long linkId, long entryId1,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2(
		long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2(
		long entryId2, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2(
		long entryId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetLink findByE2_First(
		long entryId2, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public com.liferay.portlet.asset.model.AssetLink findByE2_Last(
		long entryId2, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public com.liferay.portlet.asset.model.AssetLink[] findByE2_PrevAndNext(
		long linkId, long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE_E(
		long entryId1, long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE_E(
		long entryId1, long entryId2, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE_E(
		long entryId1, long entryId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetLink findByE_E_First(
		long entryId1, long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public com.liferay.portlet.asset.model.AssetLink findByE_E_Last(
		long entryId1, long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public com.liferay.portlet.asset.model.AssetLink[] findByE_E_PrevAndNext(
		long linkId, long entryId1, long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1_T(
		long entryId1, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1_T(
		long entryId1, int typeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1_T(
		long entryId1, int typeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetLink findByE1_T_First(
		long entryId1, int typeId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public com.liferay.portlet.asset.model.AssetLink findByE1_T_Last(
		long entryId1, int typeId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public com.liferay.portlet.asset.model.AssetLink[] findByE1_T_PrevAndNext(
		long linkId, long entryId1, int typeId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2_T(
		long entryId2, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2_T(
		long entryId2, int typeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2_T(
		long entryId2, int typeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetLink findByE2_T_First(
		long entryId2, int typeId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public com.liferay.portlet.asset.model.AssetLink findByE2_T_Last(
		long entryId2, int typeId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public com.liferay.portlet.asset.model.AssetLink[] findByE2_T_PrevAndNext(
		long linkId, long entryId2, int typeId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE_E_T(
		long entryId1, long entryId2, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE_E_T(
		long entryId1, long entryId2, int typeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE_E_T(
		long entryId1, long entryId2, int typeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetLink findByE_E_T_First(
		long entryId1, long entryId2, int typeId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public com.liferay.portlet.asset.model.AssetLink findByE_E_T_Last(
		long entryId1, long entryId2, int typeId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public com.liferay.portlet.asset.model.AssetLink[] findByE_E_T_PrevAndNext(
		long linkId, long entryId1, long entryId2, int typeId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByE1(long entryId1)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByE2(long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByE_E(long entryId1, long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByE1_T(long entryId1, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByE2_T(long entryId2, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByE_E_T(long entryId1, long entryId2, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByE1(long entryId1)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByE2(long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByE_E(long entryId1, long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByE1_T(long entryId1, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByE2_T(long entryId2, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByE_E_T(long entryId1, long entryId2, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}