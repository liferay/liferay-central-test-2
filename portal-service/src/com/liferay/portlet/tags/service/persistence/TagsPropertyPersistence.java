/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tags.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * <a href="TagsPropertyPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface TagsPropertyPersistence extends BasePersistence {
	public void cacheResult(
		com.liferay.portlet.tags.model.TagsProperty tagsProperty);

	public void cacheResult(
		java.util.List<com.liferay.portlet.tags.model.TagsProperty> tagsProperties);

	public com.liferay.portlet.tags.model.TagsProperty create(long propertyId);

	public com.liferay.portlet.tags.model.TagsProperty remove(long propertyId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchPropertyException;

	public com.liferay.portlet.tags.model.TagsProperty remove(
		com.liferay.portlet.tags.model.TagsProperty tagsProperty)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(TagsProperty tagsProperty, boolean merge)</code>.
	 */
	public com.liferay.portlet.tags.model.TagsProperty update(
		com.liferay.portlet.tags.model.TagsProperty tagsProperty)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        tagsProperty the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when tagsProperty is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portlet.tags.model.TagsProperty update(
		com.liferay.portlet.tags.model.TagsProperty tagsProperty, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsProperty updateImpl(
		com.liferay.portlet.tags.model.TagsProperty tagsProperty, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsProperty findByPrimaryKey(
		long propertyId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchPropertyException;

	public com.liferay.portlet.tags.model.TagsProperty fetchByPrimaryKey(
		long propertyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsProperty> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsProperty> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsProperty> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsProperty findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchPropertyException;

	public com.liferay.portlet.tags.model.TagsProperty findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchPropertyException;

	public com.liferay.portlet.tags.model.TagsProperty[] findByCompanyId_PrevAndNext(
		long propertyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchPropertyException;

	public java.util.List<com.liferay.portlet.tags.model.TagsProperty> findByEntryId(
		long entryId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsProperty> findByEntryId(
		long entryId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsProperty> findByEntryId(
		long entryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsProperty findByEntryId_First(
		long entryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchPropertyException;

	public com.liferay.portlet.tags.model.TagsProperty findByEntryId_Last(
		long entryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchPropertyException;

	public com.liferay.portlet.tags.model.TagsProperty[] findByEntryId_PrevAndNext(
		long propertyId, long entryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchPropertyException;

	public java.util.List<com.liferay.portlet.tags.model.TagsProperty> findByC_K(
		long companyId, java.lang.String key)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsProperty> findByC_K(
		long companyId, java.lang.String key, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsProperty> findByC_K(
		long companyId, java.lang.String key, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsProperty findByC_K_First(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchPropertyException;

	public com.liferay.portlet.tags.model.TagsProperty findByC_K_Last(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchPropertyException;

	public com.liferay.portlet.tags.model.TagsProperty[] findByC_K_PrevAndNext(
		long propertyId, long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchPropertyException;

	public com.liferay.portlet.tags.model.TagsProperty findByE_K(long entryId,
		java.lang.String key)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchPropertyException;

	public com.liferay.portlet.tags.model.TagsProperty fetchByE_K(
		long entryId, java.lang.String key)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsProperty fetchByE_K(
		long entryId, java.lang.String key, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsProperty> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsProperty> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsProperty> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public void removeByEntryId(long entryId)
		throws com.liferay.portal.SystemException;

	public void removeByC_K(long companyId, java.lang.String key)
		throws com.liferay.portal.SystemException;

	public void removeByE_K(long entryId, java.lang.String key)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchPropertyException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public int countByEntryId(long entryId)
		throws com.liferay.portal.SystemException;

	public int countByC_K(long companyId, java.lang.String key)
		throws com.liferay.portal.SystemException;

	public int countByE_K(long entryId, java.lang.String key)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}