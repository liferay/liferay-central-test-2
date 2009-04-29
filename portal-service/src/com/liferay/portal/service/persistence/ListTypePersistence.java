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

package com.liferay.portal.service.persistence;

/**
 * <a href="ListTypePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface ListTypePersistence extends BasePersistence {
	public void cacheResult(com.liferay.portal.model.ListType listType);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.ListType> listTypes);

	public void clearCache();

	public com.liferay.portal.model.ListType create(int listTypeId);

	public com.liferay.portal.model.ListType remove(int listTypeId)
		throws com.liferay.portal.NoSuchListTypeException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.ListType remove(
		com.liferay.portal.model.ListType listType)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(ListType listType, boolean merge)</code>.
	 */
	public com.liferay.portal.model.ListType update(
		com.liferay.portal.model.ListType listType)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        listType the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when listType is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portal.model.ListType update(
		com.liferay.portal.model.ListType listType, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ListType updateImpl(
		com.liferay.portal.model.ListType listType, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ListType findByPrimaryKey(int listTypeId)
		throws com.liferay.portal.NoSuchListTypeException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.ListType fetchByPrimaryKey(int listTypeId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ListType> findByType(
		java.lang.String type) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ListType> findByType(
		java.lang.String type, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ListType> findByType(
		java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ListType findByType_First(
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchListTypeException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.ListType findByType_Last(
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchListTypeException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.ListType[] findByType_PrevAndNext(
		int listTypeId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchListTypeException,
			com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ListType> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ListType> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ListType> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByType(java.lang.String type)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByType(java.lang.String type)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}