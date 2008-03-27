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
 * <a href="WebDAVPropsPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface WebDAVPropsPersistence {
	public com.liferay.portal.model.WebDAVProps create(long webDavPropsId);

	public com.liferay.portal.model.WebDAVProps remove(long webDavPropsId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchWebDAVPropsException;

	public com.liferay.portal.model.WebDAVProps remove(
		com.liferay.portal.model.WebDAVProps webDAVProps)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(WebDAVProps webDAVProps, boolean merge)</code>.
	 */
	public com.liferay.portal.model.WebDAVProps update(
		com.liferay.portal.model.WebDAVProps webDAVProps)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        webDAVProps the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when webDAVProps is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portal.model.WebDAVProps update(
		com.liferay.portal.model.WebDAVProps webDAVProps, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.WebDAVProps updateImpl(
		com.liferay.portal.model.WebDAVProps webDAVProps, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.WebDAVProps findByPrimaryKey(
		long webDavPropsId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchWebDAVPropsException;

	public com.liferay.portal.model.WebDAVProps fetchByPrimaryKey(
		long webDavPropsId) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.WebDAVProps findByC_C(long classNameId,
		long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchWebDAVPropsException;

	public com.liferay.portal.model.WebDAVProps fetchByC_C(long classNameId,
		long classPK) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.WebDAVProps> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.WebDAVProps> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.WebDAVProps> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.WebDAVProps> findAll(
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.WebDAVProps> findAll(
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchWebDAVPropsException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}