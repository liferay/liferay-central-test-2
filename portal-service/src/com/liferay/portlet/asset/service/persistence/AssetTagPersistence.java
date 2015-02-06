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

package com.liferay.portlet.asset.service.persistence;

import aQute.bnd.annotation.ProviderType;

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
 * @see com.liferay.portlet.asset.service.persistence.impl.AssetTagPersistenceImpl
 * @see AssetTagUtil
 * @generated
 */
@ProviderType
public interface AssetTagPersistence extends BasePersistence<AssetTag> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetTagUtil} to access the asset tag persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the asset tags where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching asset tags
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByGroupId(
		long groupId);

	/**
	* Returns a range of all the asset tags where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the asset tags where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator);

	/**
	* Returns the first asset tag in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a matching asset tag could not be found
	*/
	public com.liferay.portlet.asset.model.AssetTag findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException;

	/**
	* Returns the first asset tag in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	public com.liferay.portlet.asset.model.AssetTag fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator);

	/**
	* Returns the last asset tag in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a matching asset tag could not be found
	*/
	public com.liferay.portlet.asset.model.AssetTag findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException;

	/**
	* Returns the last asset tag in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	public com.liferay.portlet.asset.model.AssetTag fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator);

	/**
	* Returns the asset tags before and after the current asset tag in the ordered set where groupId = &#63;.
	*
	* @param tagId the primary key of the current asset tag
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a asset tag with the primary key could not be found
	*/
	public com.liferay.portlet.asset.model.AssetTag[] findByGroupId_PrevAndNext(
		long tagId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException;

	/**
	* Returns all the asset tags that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching asset tags that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> filterFindByGroupId(
		long groupId);

	/**
	* Returns a range of all the asset tags that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the asset tags that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator);

	/**
	* Returns the asset tags before and after the current asset tag in the ordered set of asset tags that the user has permission to view where groupId = &#63;.
	*
	* @param tagId the primary key of the current asset tag
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a asset tag with the primary key could not be found
	*/
	public com.liferay.portlet.asset.model.AssetTag[] filterFindByGroupId_PrevAndNext(
		long tagId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException;

	/**
	* Returns all the asset tags that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the matching asset tags that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> filterFindByGroupId(
		long[] groupIds);

	/**
	* Returns a range of all the asset tags that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> filterFindByGroupId(
		long[] groupIds, int start, int end);

	/**
	* Returns an ordered range of all the asset tags that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> filterFindByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator);

	/**
	* Returns all the asset tags where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @return the matching asset tags
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByGroupId(
		long[] groupIds);

	/**
	* Returns a range of all the asset tags where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByGroupId(
		long[] groupIds, int start, int end);

	/**
	* Returns an ordered range of all the asset tags where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator);

	/**
	* Removes all the asset tags where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of asset tags where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching asset tags
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of asset tags where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching asset tags
	*/
	public int countByGroupId(long[] groupIds);

	/**
	* Returns the number of asset tags that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching asset tags that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns the number of asset tags that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching asset tags that the user has permission to view
	*/
	public int filterCountByGroupId(long[] groupIds);

	/**
	* Returns the asset tag where groupId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchTagException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a matching asset tag could not be found
	*/
	public com.liferay.portlet.asset.model.AssetTag findByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.portlet.asset.NoSuchTagException;

	/**
	* Returns the asset tag where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	public com.liferay.portlet.asset.model.AssetTag fetchByG_N(long groupId,
		java.lang.String name);

	/**
	* Returns the asset tag where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	public com.liferay.portlet.asset.model.AssetTag fetchByG_N(long groupId,
		java.lang.String name, boolean retrieveFromCache);

	/**
	* Removes the asset tag where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the asset tag that was removed
	*/
	public com.liferay.portlet.asset.model.AssetTag removeByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.portlet.asset.NoSuchTagException;

	/**
	* Returns the number of asset tags where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching asset tags
	*/
	public int countByG_N(long groupId, java.lang.String name);

	/**
	* Returns all the asset tags where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching asset tags
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByG_LikeN(
		long groupId, java.lang.String name);

	/**
	* Returns a range of all the asset tags where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByG_LikeN(
		long groupId, java.lang.String name, int start, int end);

	/**
	* Returns an ordered range of all the asset tags where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByG_LikeN(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator);

	/**
	* Returns the first asset tag in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a matching asset tag could not be found
	*/
	public com.liferay.portlet.asset.model.AssetTag findByG_LikeN_First(
		long groupId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException;

	/**
	* Returns the first asset tag in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	public com.liferay.portlet.asset.model.AssetTag fetchByG_LikeN_First(
		long groupId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator);

	/**
	* Returns the last asset tag in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a matching asset tag could not be found
	*/
	public com.liferay.portlet.asset.model.AssetTag findByG_LikeN_Last(
		long groupId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException;

	/**
	* Returns the last asset tag in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	public com.liferay.portlet.asset.model.AssetTag fetchByG_LikeN_Last(
		long groupId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator);

	/**
	* Returns the asset tags before and after the current asset tag in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param tagId the primary key of the current asset tag
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a asset tag with the primary key could not be found
	*/
	public com.liferay.portlet.asset.model.AssetTag[] findByG_LikeN_PrevAndNext(
		long tagId, long groupId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException;

	/**
	* Returns all the asset tags that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching asset tags that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> filterFindByG_LikeN(
		long groupId, java.lang.String name);

	/**
	* Returns a range of all the asset tags that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> filterFindByG_LikeN(
		long groupId, java.lang.String name, int start, int end);

	/**
	* Returns an ordered range of all the asset tags that the user has permissions to view where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> filterFindByG_LikeN(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator);

	/**
	* Returns the asset tags before and after the current asset tag in the ordered set of asset tags that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param tagId the primary key of the current asset tag
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a asset tag with the primary key could not be found
	*/
	public com.liferay.portlet.asset.model.AssetTag[] filterFindByG_LikeN_PrevAndNext(
		long tagId, long groupId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException;

	/**
	* Returns all the asset tags that the user has permission to view where groupId = any &#63; and name LIKE &#63;.
	*
	* @param groupIds the group IDs
	* @param name the name
	* @return the matching asset tags that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> filterFindByG_LikeN(
		long[] groupIds, java.lang.String name);

	/**
	* Returns a range of all the asset tags that the user has permission to view where groupId = any &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param name the name
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> filterFindByG_LikeN(
		long[] groupIds, java.lang.String name, int start, int end);

	/**
	* Returns an ordered range of all the asset tags that the user has permission to view where groupId = any &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param name the name
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> filterFindByG_LikeN(
		long[] groupIds, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator);

	/**
	* Returns all the asset tags where groupId = any &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param name the name
	* @return the matching asset tags
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByG_LikeN(
		long[] groupIds, java.lang.String name);

	/**
	* Returns a range of all the asset tags where groupId = any &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param name the name
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByG_LikeN(
		long[] groupIds, java.lang.String name, int start, int end);

	/**
	* Returns an ordered range of all the asset tags where groupId = any &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param name the name
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByG_LikeN(
		long[] groupIds, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator);

	/**
	* Removes all the asset tags where groupId = &#63; and name LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	*/
	public void removeByG_LikeN(long groupId, java.lang.String name);

	/**
	* Returns the number of asset tags where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching asset tags
	*/
	public int countByG_LikeN(long groupId, java.lang.String name);

	/**
	* Returns the number of asset tags where groupId = any &#63; and name LIKE &#63;.
	*
	* @param groupIds the group IDs
	* @param name the name
	* @return the number of matching asset tags
	*/
	public int countByG_LikeN(long[] groupIds, java.lang.String name);

	/**
	* Returns the number of asset tags that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching asset tags that the user has permission to view
	*/
	public int filterCountByG_LikeN(long groupId, java.lang.String name);

	/**
	* Returns the number of asset tags that the user has permission to view where groupId = any &#63; and name LIKE &#63;.
	*
	* @param groupIds the group IDs
	* @param name the name
	* @return the number of matching asset tags that the user has permission to view
	*/
	public int filterCountByG_LikeN(long[] groupIds, java.lang.String name);

	/**
	* Caches the asset tag in the entity cache if it is enabled.
	*
	* @param assetTag the asset tag
	*/
	public void cacheResult(com.liferay.portlet.asset.model.AssetTag assetTag);

	/**
	* Caches the asset tags in the entity cache if it is enabled.
	*
	* @param assetTags the asset tags
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
	* @param tagId the primary key of the asset tag
	* @return the asset tag that was removed
	* @throws com.liferay.portlet.asset.NoSuchTagException if a asset tag with the primary key could not be found
	*/
	public com.liferay.portlet.asset.model.AssetTag remove(long tagId)
		throws com.liferay.portlet.asset.NoSuchTagException;

	public com.liferay.portlet.asset.model.AssetTag updateImpl(
		com.liferay.portlet.asset.model.AssetTag assetTag);

	/**
	* Returns the asset tag with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchTagException} if it could not be found.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a asset tag with the primary key could not be found
	*/
	public com.liferay.portlet.asset.model.AssetTag findByPrimaryKey(long tagId)
		throws com.liferay.portlet.asset.NoSuchTagException;

	/**
	* Returns the asset tag with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag, or <code>null</code> if a asset tag with the primary key could not be found
	*/
	public com.liferay.portlet.asset.model.AssetTag fetchByPrimaryKey(
		long tagId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portlet.asset.model.AssetTag> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the asset tags.
	*
	* @return the asset tags
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findAll();

	/**
	* Returns a range of all the asset tags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of asset tags
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the asset tags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset tags
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator);

	/**
	* Removes all the asset tags from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of asset tags.
	*
	* @return the number of asset tags
	*/
	public int countAll();

	/**
	* Returns the primaryKeys of asset entries associated with the asset tag.
	*
	* @param pk the primary key of the asset tag
	* @return long[] of the primaryKeys of asset entries associated with the asset tag
	*/
	public long[] getAssetEntryPrimaryKeys(long pk);

	/**
	* Returns all the asset entries associated with the asset tag.
	*
	* @param pk the primary key of the asset tag
	* @return the asset entries associated with the asset tag
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk);

	/**
	* Returns a range of all the asset entries associated with the asset tag.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset tag
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of asset entries associated with the asset tag
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the asset entries associated with the asset tag.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset tag
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset entries associated with the asset tag
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetEntry> orderByComparator);

	/**
	* Returns the number of asset entries associated with the asset tag.
	*
	* @param pk the primary key of the asset tag
	* @return the number of asset entries associated with the asset tag
	*/
	public int getAssetEntriesSize(long pk);

	/**
	* Returns <code>true</code> if the asset entry is associated with the asset tag.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPK the primary key of the asset entry
	* @return <code>true</code> if the asset entry is associated with the asset tag; <code>false</code> otherwise
	*/
	public boolean containsAssetEntry(long pk, long assetEntryPK);

	/**
	* Returns <code>true</code> if the asset tag has any asset entries associated with it.
	*
	* @param pk the primary key of the asset tag to check for associations with asset entries
	* @return <code>true</code> if the asset tag has any asset entries associated with it; <code>false</code> otherwise
	*/
	public boolean containsAssetEntries(long pk);

	/**
	* Adds an association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPK the primary key of the asset entry
	*/
	public void addAssetEntry(long pk, long assetEntryPK);

	/**
	* Adds an association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntry the asset entry
	*/
	public void addAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry);

	/**
	* Adds an association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPKs the primary keys of the asset entries
	*/
	public void addAssetEntries(long pk, long[] assetEntryPKs);

	/**
	* Adds an association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntries the asset entries
	*/
	public void addAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries);

	/**
	* Clears all associations between the asset tag and its asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag to clear the associated asset entries from
	*/
	public void clearAssetEntries(long pk);

	/**
	* Removes the association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPK the primary key of the asset entry
	*/
	public void removeAssetEntry(long pk, long assetEntryPK);

	/**
	* Removes the association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntry the asset entry
	*/
	public void removeAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry);

	/**
	* Removes the association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPKs the primary keys of the asset entries
	*/
	public void removeAssetEntries(long pk, long[] assetEntryPKs);

	/**
	* Removes the association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntries the asset entries
	*/
	public void removeAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries);

	/**
	* Sets the asset entries associated with the asset tag, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPKs the primary keys of the asset entries to be associated with the asset tag
	*/
	public void setAssetEntries(long pk, long[] assetEntryPKs);

	/**
	* Sets the asset entries associated with the asset tag, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntries the asset entries to be associated with the asset tag
	*/
	public void setAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries);
}