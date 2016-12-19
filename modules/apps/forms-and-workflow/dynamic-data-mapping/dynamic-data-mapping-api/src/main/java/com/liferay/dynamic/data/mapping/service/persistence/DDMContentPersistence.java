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

package com.liferay.dynamic.data.mapping.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.exception.NoSuchContentException;
import com.liferay.dynamic.data.mapping.model.DDMContent;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the ddm content service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.mapping.service.persistence.impl.DDMContentPersistenceImpl
 * @see DDMContentUtil
 * @generated
 */
@ProviderType
public interface DDMContentPersistence extends BasePersistence<DDMContent> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMContentUtil} to access the ddm content persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the ddm contents where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching ddm contents
	*/
	public java.util.List<DDMContent> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the ddm contents where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of ddm contents
	* @param end the upper bound of the range of ddm contents (not inclusive)
	* @return the range of matching ddm contents
	*/
	public java.util.List<DDMContent> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the ddm contents where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of ddm contents
	* @param end the upper bound of the range of ddm contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ddm contents
	*/
	public java.util.List<DDMContent> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator);

	/**
	* Returns an ordered range of all the ddm contents where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of ddm contents
	* @param end the upper bound of the range of ddm contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ddm contents
	*/
	public java.util.List<DDMContent> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ddm content in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddm content
	* @throws NoSuchContentException if a matching ddm content could not be found
	*/
	public DDMContent findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator)
		throws NoSuchContentException;

	/**
	* Returns the first ddm content in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddm content, or <code>null</code> if a matching ddm content could not be found
	*/
	public DDMContent fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator);

	/**
	* Returns the last ddm content in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddm content
	* @throws NoSuchContentException if a matching ddm content could not be found
	*/
	public DDMContent findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator)
		throws NoSuchContentException;

	/**
	* Returns the last ddm content in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddm content, or <code>null</code> if a matching ddm content could not be found
	*/
	public DDMContent fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator);

	/**
	* Returns the ddm contents before and after the current ddm content in the ordered set where uuid = &#63;.
	*
	* @param contentId the primary key of the current ddm content
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ddm content
	* @throws NoSuchContentException if a ddm content with the primary key could not be found
	*/
	public DDMContent[] findByUuid_PrevAndNext(long contentId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator)
		throws NoSuchContentException;

	/**
	* Removes all the ddm contents where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of ddm contents where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching ddm contents
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the ddm content where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchContentException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching ddm content
	* @throws NoSuchContentException if a matching ddm content could not be found
	*/
	public DDMContent findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchContentException;

	/**
	* Returns the ddm content where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching ddm content, or <code>null</code> if a matching ddm content could not be found
	*/
	public DDMContent fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the ddm content where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching ddm content, or <code>null</code> if a matching ddm content could not be found
	*/
	public DDMContent fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the ddm content where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the ddm content that was removed
	*/
	public DDMContent removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchContentException;

	/**
	* Returns the number of ddm contents where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching ddm contents
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the ddm contents where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching ddm contents
	*/
	public java.util.List<DDMContent> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the ddm contents where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of ddm contents
	* @param end the upper bound of the range of ddm contents (not inclusive)
	* @return the range of matching ddm contents
	*/
	public java.util.List<DDMContent> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the ddm contents where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of ddm contents
	* @param end the upper bound of the range of ddm contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ddm contents
	*/
	public java.util.List<DDMContent> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator);

	/**
	* Returns an ordered range of all the ddm contents where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of ddm contents
	* @param end the upper bound of the range of ddm contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ddm contents
	*/
	public java.util.List<DDMContent> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ddm content in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddm content
	* @throws NoSuchContentException if a matching ddm content could not be found
	*/
	public DDMContent findByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator)
		throws NoSuchContentException;

	/**
	* Returns the first ddm content in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddm content, or <code>null</code> if a matching ddm content could not be found
	*/
	public DDMContent fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator);

	/**
	* Returns the last ddm content in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddm content
	* @throws NoSuchContentException if a matching ddm content could not be found
	*/
	public DDMContent findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator)
		throws NoSuchContentException;

	/**
	* Returns the last ddm content in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddm content, or <code>null</code> if a matching ddm content could not be found
	*/
	public DDMContent fetchByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator);

	/**
	* Returns the ddm contents before and after the current ddm content in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param contentId the primary key of the current ddm content
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ddm content
	* @throws NoSuchContentException if a ddm content with the primary key could not be found
	*/
	public DDMContent[] findByUuid_C_PrevAndNext(long contentId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator)
		throws NoSuchContentException;

	/**
	* Removes all the ddm contents where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of ddm contents where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching ddm contents
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the ddm contents where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching ddm contents
	*/
	public java.util.List<DDMContent> findByGroupId(long groupId);

	/**
	* Returns a range of all the ddm contents where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of ddm contents
	* @param end the upper bound of the range of ddm contents (not inclusive)
	* @return the range of matching ddm contents
	*/
	public java.util.List<DDMContent> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the ddm contents where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of ddm contents
	* @param end the upper bound of the range of ddm contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ddm contents
	*/
	public java.util.List<DDMContent> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator);

	/**
	* Returns an ordered range of all the ddm contents where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of ddm contents
	* @param end the upper bound of the range of ddm contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ddm contents
	*/
	public java.util.List<DDMContent> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ddm content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddm content
	* @throws NoSuchContentException if a matching ddm content could not be found
	*/
	public DDMContent findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator)
		throws NoSuchContentException;

	/**
	* Returns the first ddm content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddm content, or <code>null</code> if a matching ddm content could not be found
	*/
	public DDMContent fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator);

	/**
	* Returns the last ddm content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddm content
	* @throws NoSuchContentException if a matching ddm content could not be found
	*/
	public DDMContent findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator)
		throws NoSuchContentException;

	/**
	* Returns the last ddm content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddm content, or <code>null</code> if a matching ddm content could not be found
	*/
	public DDMContent fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator);

	/**
	* Returns the ddm contents before and after the current ddm content in the ordered set where groupId = &#63;.
	*
	* @param contentId the primary key of the current ddm content
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ddm content
	* @throws NoSuchContentException if a ddm content with the primary key could not be found
	*/
	public DDMContent[] findByGroupId_PrevAndNext(long contentId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator)
		throws NoSuchContentException;

	/**
	* Removes all the ddm contents where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of ddm contents where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching ddm contents
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the ddm contents where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching ddm contents
	*/
	public java.util.List<DDMContent> findByCompanyId(long companyId);

	/**
	* Returns a range of all the ddm contents where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of ddm contents
	* @param end the upper bound of the range of ddm contents (not inclusive)
	* @return the range of matching ddm contents
	*/
	public java.util.List<DDMContent> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the ddm contents where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of ddm contents
	* @param end the upper bound of the range of ddm contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ddm contents
	*/
	public java.util.List<DDMContent> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator);

	/**
	* Returns an ordered range of all the ddm contents where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of ddm contents
	* @param end the upper bound of the range of ddm contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ddm contents
	*/
	public java.util.List<DDMContent> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ddm content in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddm content
	* @throws NoSuchContentException if a matching ddm content could not be found
	*/
	public DDMContent findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator)
		throws NoSuchContentException;

	/**
	* Returns the first ddm content in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddm content, or <code>null</code> if a matching ddm content could not be found
	*/
	public DDMContent fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator);

	/**
	* Returns the last ddm content in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddm content
	* @throws NoSuchContentException if a matching ddm content could not be found
	*/
	public DDMContent findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator)
		throws NoSuchContentException;

	/**
	* Returns the last ddm content in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddm content, or <code>null</code> if a matching ddm content could not be found
	*/
	public DDMContent fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator);

	/**
	* Returns the ddm contents before and after the current ddm content in the ordered set where companyId = &#63;.
	*
	* @param contentId the primary key of the current ddm content
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ddm content
	* @throws NoSuchContentException if a ddm content with the primary key could not be found
	*/
	public DDMContent[] findByCompanyId_PrevAndNext(long contentId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator)
		throws NoSuchContentException;

	/**
	* Removes all the ddm contents where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of ddm contents where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching ddm contents
	*/
	public int countByCompanyId(long companyId);

	/**
	* Caches the ddm content in the entity cache if it is enabled.
	*
	* @param ddmContent the ddm content
	*/
	public void cacheResult(DDMContent ddmContent);

	/**
	* Caches the ddm contents in the entity cache if it is enabled.
	*
	* @param ddmContents the ddm contents
	*/
	public void cacheResult(java.util.List<DDMContent> ddmContents);

	/**
	* Creates a new ddm content with the primary key. Does not add the ddm content to the database.
	*
	* @param contentId the primary key for the new ddm content
	* @return the new ddm content
	*/
	public DDMContent create(long contentId);

	/**
	* Removes the ddm content with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param contentId the primary key of the ddm content
	* @return the ddm content that was removed
	* @throws NoSuchContentException if a ddm content with the primary key could not be found
	*/
	public DDMContent remove(long contentId) throws NoSuchContentException;

	public DDMContent updateImpl(DDMContent ddmContent);

	/**
	* Returns the ddm content with the primary key or throws a {@link NoSuchContentException} if it could not be found.
	*
	* @param contentId the primary key of the ddm content
	* @return the ddm content
	* @throws NoSuchContentException if a ddm content with the primary key could not be found
	*/
	public DDMContent findByPrimaryKey(long contentId)
		throws NoSuchContentException;

	/**
	* Returns the ddm content with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param contentId the primary key of the ddm content
	* @return the ddm content, or <code>null</code> if a ddm content with the primary key could not be found
	*/
	public DDMContent fetchByPrimaryKey(long contentId);

	@Override
	public java.util.Map<java.io.Serializable, DDMContent> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the ddm contents.
	*
	* @return the ddm contents
	*/
	public java.util.List<DDMContent> findAll();

	/**
	* Returns a range of all the ddm contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddm contents
	* @param end the upper bound of the range of ddm contents (not inclusive)
	* @return the range of ddm contents
	*/
	public java.util.List<DDMContent> findAll(int start, int end);

	/**
	* Returns an ordered range of all the ddm contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddm contents
	* @param end the upper bound of the range of ddm contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of ddm contents
	*/
	public java.util.List<DDMContent> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator);

	/**
	* Returns an ordered range of all the ddm contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddm contents
	* @param end the upper bound of the range of ddm contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of ddm contents
	*/
	public java.util.List<DDMContent> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMContent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the ddm contents from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of ddm contents.
	*
	* @return the number of ddm contents
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}