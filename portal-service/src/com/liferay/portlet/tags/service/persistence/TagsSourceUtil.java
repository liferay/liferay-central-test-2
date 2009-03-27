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

/**
 * <a href="TagsSourceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsSourceUtil {
	public static void cacheResult(
		com.liferay.portlet.tags.model.TagsSource tagsSource) {
		getPersistence().cacheResult(tagsSource);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.tags.model.TagsSource> tagsSources) {
		getPersistence().cacheResult(tagsSources);
	}

	public static com.liferay.portlet.tags.model.TagsSource create(
		long sourceId) {
		return getPersistence().create(sourceId);
	}

	public static com.liferay.portlet.tags.model.TagsSource remove(
		long sourceId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchSourceException {
		return getPersistence().remove(sourceId);
	}

	public static com.liferay.portlet.tags.model.TagsSource remove(
		com.liferay.portlet.tags.model.TagsSource tagsSource)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(tagsSource);
	}

	/**
	 * @deprecated Use <code>update(TagsSource tagsSource, boolean merge)</code>.
	 */
	public static com.liferay.portlet.tags.model.TagsSource update(
		com.liferay.portlet.tags.model.TagsSource tagsSource)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(tagsSource);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        tagsSource the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when tagsSource is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.tags.model.TagsSource update(
		com.liferay.portlet.tags.model.TagsSource tagsSource, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(tagsSource, merge);
	}

	public static com.liferay.portlet.tags.model.TagsSource updateImpl(
		com.liferay.portlet.tags.model.TagsSource tagsSource, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(tagsSource, merge);
	}

	public static com.liferay.portlet.tags.model.TagsSource findByPrimaryKey(
		long sourceId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchSourceException {
		return getPersistence().findByPrimaryKey(sourceId);
	}

	public static com.liferay.portlet.tags.model.TagsSource fetchByPrimaryKey(
		long sourceId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(sourceId);
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

	public static java.util.List<com.liferay.portlet.tags.model.TagsSource> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsSource> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsSource> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static TagsSourcePersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(TagsSourcePersistence persistence) {
		_persistence = persistence;
	}

	private static TagsSourcePersistence _persistence;
}