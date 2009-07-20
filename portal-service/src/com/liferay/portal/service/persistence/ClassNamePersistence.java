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

public interface ClassNamePersistence extends BasePersistence {
	public void cacheResult(com.liferay.portal.model.ClassName className);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.ClassName> classNames);

	public void clearCache();

	public com.liferay.portal.model.ClassName create(long classNameId);

	public com.liferay.portal.model.ClassName remove(long classNameId)
		throws com.liferay.portal.NoSuchClassNameException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.ClassName remove(
		com.liferay.portal.model.ClassName className)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(ClassName className, boolean merge)</code>.
	 */
	public com.liferay.portal.model.ClassName update(
		com.liferay.portal.model.ClassName className)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        className the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when className is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portal.model.ClassName update(
		com.liferay.portal.model.ClassName className, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ClassName updateImpl(
		com.liferay.portal.model.ClassName className, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ClassName findByPrimaryKey(long classNameId)
		throws com.liferay.portal.NoSuchClassNameException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.ClassName fetchByPrimaryKey(
		long classNameId) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ClassName findByValue(
		java.lang.String value)
		throws com.liferay.portal.NoSuchClassNameException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.ClassName fetchByValue(
		java.lang.String value) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ClassName fetchByValue(
		java.lang.String value, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ClassName> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ClassName> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ClassName> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByValue(java.lang.String value)
		throws com.liferay.portal.NoSuchClassNameException,
			com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByValue(java.lang.String value)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}