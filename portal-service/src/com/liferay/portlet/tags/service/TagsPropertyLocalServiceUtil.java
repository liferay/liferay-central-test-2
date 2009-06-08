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

package com.liferay.portlet.tags.service;


/**
 * <a href="TagsPropertyLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.tags.service.TagsPropertyLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.tags.service.TagsPropertyLocalService
 *
 */
public class TagsPropertyLocalServiceUtil {
	public static com.liferay.portlet.tags.model.TagsProperty addTagsProperty(
		com.liferay.portlet.tags.model.TagsProperty tagsProperty)
		throws com.liferay.portal.SystemException {
		return getService().addTagsProperty(tagsProperty);
	}

	public static com.liferay.portlet.tags.model.TagsProperty createTagsProperty(
		long propertyId) {
		return getService().createTagsProperty(propertyId);
	}

	public static void deleteTagsProperty(long propertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteTagsProperty(propertyId);
	}

	public static void deleteTagsProperty(
		com.liferay.portlet.tags.model.TagsProperty tagsProperty)
		throws com.liferay.portal.SystemException {
		getService().deleteTagsProperty(tagsProperty);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.tags.model.TagsProperty getTagsProperty(
		long propertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getTagsProperty(propertyId);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsProperty> getTagsProperties(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getTagsProperties(start, end);
	}

	public static int getTagsPropertiesCount()
		throws com.liferay.portal.SystemException {
		return getService().getTagsPropertiesCount();
	}

	public static com.liferay.portlet.tags.model.TagsProperty updateTagsProperty(
		com.liferay.portlet.tags.model.TagsProperty tagsProperty)
		throws com.liferay.portal.SystemException {
		return getService().updateTagsProperty(tagsProperty);
	}

	public static com.liferay.portlet.tags.model.TagsProperty updateTagsProperty(
		com.liferay.portlet.tags.model.TagsProperty tagsProperty, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateTagsProperty(tagsProperty, merge);
	}

	public static com.liferay.portlet.tags.model.TagsProperty addProperty(
		long userId, long entryId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addProperty(userId, entryId, key, value);
	}

	public static void deleteProperties(long entryId)
		throws com.liferay.portal.SystemException {
		getService().deleteProperties(entryId);
	}

	public static void deleteProperty(long propertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteProperty(propertyId);
	}

	public static void deleteProperty(
		com.liferay.portlet.tags.model.TagsProperty property)
		throws com.liferay.portal.SystemException {
		getService().deleteProperty(property);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsProperty> getProperties()
		throws com.liferay.portal.SystemException {
		return getService().getProperties();
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsProperty> getProperties(
		long entryId) throws com.liferay.portal.SystemException {
		return getService().getProperties(entryId);
	}

	public static com.liferay.portlet.tags.model.TagsProperty getProperty(
		long propertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getProperty(propertyId);
	}

	public static com.liferay.portlet.tags.model.TagsProperty getProperty(
		long entryId, java.lang.String key)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getProperty(entryId, key);
	}

	public static java.lang.String[] getPropertyKeys(long groupId)
		throws com.liferay.portal.SystemException {
		return getService().getPropertyKeys(groupId);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsProperty> getPropertyValues(
		long groupId, java.lang.String key)
		throws com.liferay.portal.SystemException {
		return getService().getPropertyValues(groupId, key);
	}

	public static com.liferay.portlet.tags.model.TagsProperty updateProperty(
		long propertyId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateProperty(propertyId, key, value);
	}

	public static TagsPropertyLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("TagsPropertyLocalService is not set");
		}

		return _service;
	}

	public void setService(TagsPropertyLocalService service) {
		_service = service;
	}

	private static TagsPropertyLocalService _service;
}