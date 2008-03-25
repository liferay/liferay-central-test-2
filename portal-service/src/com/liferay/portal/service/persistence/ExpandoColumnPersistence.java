/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * <a href="ExpandoColumnPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface ExpandoColumnPersistence {
	public com.liferay.portal.model.ExpandoColumn create(long columnId);

	public com.liferay.portal.model.ExpandoColumn remove(long columnId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoColumnException;

	public com.liferay.portal.model.ExpandoColumn remove(
		com.liferay.portal.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ExpandoColumn update(
		com.liferay.portal.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ExpandoColumn update(
		com.liferay.portal.model.ExpandoColumn expandoColumn, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ExpandoColumn updateImpl(
		com.liferay.portal.model.ExpandoColumn expandoColumn, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ExpandoColumn findByPrimaryKey(
		long columnId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoColumnException;

	public com.liferay.portal.model.ExpandoColumn fetchByPrimaryKey(
		long columnId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoColumn> findByClassNameId(
		long classNameId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoColumn> findByClassNameId(
		long classNameId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoColumn> findByClassNameId(
		long classNameId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ExpandoColumn findByClassNameId_First(
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoColumnException;

	public com.liferay.portal.model.ExpandoColumn findByClassNameId_Last(
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoColumnException;

	public com.liferay.portal.model.ExpandoColumn[] findByClassNameId_PrevAndNext(
		long columnId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoColumnException;

	public com.liferay.portal.model.ExpandoColumn findByC_N(long classNameId,
		java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoColumnException;

	public com.liferay.portal.model.ExpandoColumn fetchByC_N(long classNameId,
		java.lang.String name) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoColumn> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoColumn> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoColumn> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoColumn> findAll(
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoColumn> findAll(
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByClassNameId(long classNameId)
		throws com.liferay.portal.SystemException;

	public void removeByC_N(long classNameId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoColumnException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByClassNameId(long classNameId)
		throws com.liferay.portal.SystemException;

	public int countByC_N(long classNameId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}