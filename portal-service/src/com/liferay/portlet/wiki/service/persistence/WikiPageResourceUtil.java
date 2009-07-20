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

package com.liferay.portlet.wiki.service.persistence;

public class WikiPageResourceUtil {
	public static void cacheResult(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource) {
		getPersistence().cacheResult(wikiPageResource);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> wikiPageResources) {
		getPersistence().cacheResult(wikiPageResources);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource create(
		long resourcePrimKey) {
		return getPersistence().create(resourcePrimKey);
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource remove(
		long resourcePrimKey)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageResourceException {
		return getPersistence().remove(resourcePrimKey);
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource remove(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(wikiPageResource);
	}

	/**
	 * @deprecated Use <code>update(WikiPageResource wikiPageResource, boolean merge)</code>.
	 */
	public static com.liferay.portlet.wiki.model.WikiPageResource update(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(wikiPageResource);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        wikiPageResource the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when wikiPageResource is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.wiki.model.WikiPageResource update(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(wikiPageResource, merge);
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource updateImpl(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(wikiPageResource, merge);
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource findByPrimaryKey(
		long resourcePrimKey)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageResourceException {
		return getPersistence().findByPrimaryKey(resourcePrimKey);
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource fetchByPrimaryKey(
		long resourcePrimKey) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(resourcePrimKey);
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource findByN_T(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageResourceException {
		return getPersistence().findByN_T(nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource fetchByN_T(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByN_T(nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource fetchByN_T(
		long nodeId, java.lang.String title, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByN_T(nodeId, title, retrieveFromCache);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByN_T(long nodeId, java.lang.String title)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageResourceException {
		getPersistence().removeByN_T(nodeId, title);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByN_T(long nodeId, java.lang.String title)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByN_T(nodeId, title);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static WikiPageResourcePersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(WikiPageResourcePersistence persistence) {
		_persistence = persistence;
	}

	private static WikiPageResourcePersistence _persistence;
}