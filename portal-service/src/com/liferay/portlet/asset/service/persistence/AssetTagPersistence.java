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

import com.liferay.portlet.asset.model.AssetTag;

/**
 * The persistence interface for the asset tag service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagPersistenceImpl
 * @see AssetTagUtil
 * @generated
 */
public interface AssetTagPersistence extends BasePersistence<AssetTag> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetTagUtil} to access the asset tag persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the asset tag in the entity cache if it is enabled.
	*
	* @param assetTag the asset tag to cache
	*/
	public void cacheResult(com.liferay.portlet.asset.model.AssetTag assetTag);

	/**
	* Caches the asset tags in the entity cache if it is enabled.
	*
	* @param assetTags the asset tags to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags);

	/**
	* Creates a new asset tag with the primary key. Does not add the asset tag to the database.
	*
	* @param tagId the primary key for the new asset tag
	* @return the new asset tag
	*/
	public com.liferay.portlet.asset.model.AssetTag create(long tagId);

	/**
	* Removes the asset tag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tagId the primary key of the asset tag to remove
	* @return the asset tag that was removed
	* @throws com.liferay.portlet.asset.NoSuchTagException if a asset tag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTag remove(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagException;

	public com.liferay.portlet.asset.model.AssetTag updateImpl(
		com.liferay.portlet.asset.model.AssetTag assetTag, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the asset tag with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchTagException} if it could not be found.
	*
	* @param tagId the primary key of the asset tag to find
	* @return the asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a asset tag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTag findByPrimaryKey(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagException;

	/**
	* Finds the asset tag with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param tagId the primary key of the asset tag to find
	* @return the asset tag, or <code>null</code> if a asset tag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTag fetchByPrimaryKey(
		long tagId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the asset tags where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching asset tags
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the asset tags where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of asset tags to return
	* @param end the upper bound of the range of asset tags to return (not inclusive)
	* @return the range of matching asset tags
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the asset tags where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of asset tags to return
	* @param end the upper bound of the range of asset tags to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching asset tags
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first asset tag in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a matching asset tag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTag findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagException;

	/**
	* Finds the last asset tag in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a matching asset tag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTag findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagException;

	/**
	* Finds the asset tags before and after the current asset tag in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tagId the primary key of the current asset tag
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a asset tag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTag[] findByGroupId_PrevAndNext(
		long tagId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagException;

	/**
	* Filters by the user's permissions and finds all the asset tags where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching asset tags that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds a range of all the asset tags where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of asset tags to return
	* @param end the upper bound of the range of asset tags to return (not inclusive)
	* @return the range of matching asset tags that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds an ordered range of all the asset tags where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of asset tags to return
	* @param end the upper bound of the range of asset tags to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching asset tags that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the asset tags.
	*
	* @return the asset tags
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the asset tags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of asset tags to return
	* @param end the upper bound of the range of asset tags to return (not inclusive)
	* @return the range of asset tags
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the asset tags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of asset tags to return
	* @param end the upper bound of the range of asset tags to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of asset tags
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the asset tags where groupId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the asset tags from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the asset tags where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching asset tags
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the asset tags where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching asset tags that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the asset tags.
	*
	* @return the number of asset tags
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets all the asset entries associated with the asset tag.
	*
	* @param pk the primary key of the asset tag to get the associated asset entries for
	* @return the asset entries associated with the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets a range of all the asset entries associated with the asset tag.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the asset tag to get the associated asset entries for
	* @param start the lower bound of the range of asset tags to return
	* @param end the upper bound of the range of asset tags to return (not inclusive)
	* @return the range of asset entries associated with the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets an ordered range of all the asset entries associated with the asset tag.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the asset tag to get the associated asset entries for
	* @param start the lower bound of the range of asset tags to return
	* @param end the upper bound of the range of asset tags to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of asset entries associated with the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets the number of asset entries associated with the asset tag.
	*
	* @param pk the primary key of the asset tag to get the number of associated asset entries for
	* @return the number of asset entries associated with the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public int getAssetEntriesSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Determines if the asset entry is associated with the asset tag.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPK the primary key of the asset entry
	* @return <code>true</code> if the asset entry is associated with the asset tag; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsAssetEntry(long pk, long assetEntryPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Determines if the asset tag has any asset entries associated with it.
	*
	* @param pk the primary key of the asset tag to check for associations with asset entries
	* @return <code>true</code> if the asset tag has any asset entries associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsAssetEntries(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPK the primary key of the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public void addAssetEntry(long pk, long assetEntryPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntry the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public void addAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPKs the primary keys of the asset entries
	* @throws SystemException if a system exception occurred
	*/
	public void addAssetEntries(long pk, long[] assetEntryPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntries the asset entries
	* @throws SystemException if a system exception occurred
	*/
	public void addAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Clears all associations between the asset tag and its asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag to clear the associated asset entries from
	* @throws SystemException if a system exception occurred
	*/
	public void clearAssetEntries(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPK the primary key of the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public void removeAssetEntry(long pk, long assetEntryPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntry the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public void removeAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPKs the primary keys of the asset entries
	* @throws SystemException if a system exception occurred
	*/
	public void removeAssetEntries(long pk, long[] assetEntryPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntries the asset entries
	* @throws SystemException if a system exception occurred
	*/
	public void removeAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the asset entries associated with the asset tag, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag to set the associations for
	* @param assetEntryPKs the primary keys of the asset entries to be associated with the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public void setAssetEntries(long pk, long[] assetEntryPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the asset entries associated with the asset tag, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag to set the associations for
	* @param assetEntries the asset entries to be associated with the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public void setAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws com.liferay.portal.kernel.exception.SystemException;
}