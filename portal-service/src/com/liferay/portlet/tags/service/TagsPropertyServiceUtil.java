/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
 * <a href="TagsPropertyServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsPropertyServiceUtil {
	public static com.liferay.portlet.tags.model.TagsProperty addProperty(
		long entryId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		TagsPropertyService tagsPropertyService = TagsPropertyServiceFactory.getService();

		return tagsPropertyService.addProperty(entryId, key, value);
	}

	public static com.liferay.portlet.tags.model.TagsProperty addProperty(
		java.lang.String userId, java.lang.String entryName,
		java.lang.String key, java.lang.String value)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		TagsPropertyService tagsPropertyService = TagsPropertyServiceFactory.getService();

		return tagsPropertyService.addProperty(userId, entryName, key, value);
	}

	public static void deleteProperty(long propertyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		TagsPropertyService tagsPropertyService = TagsPropertyServiceFactory.getService();
		tagsPropertyService.deleteProperty(propertyId);
	}

	public static java.util.List getProperties(long entryId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		TagsPropertyService tagsPropertyService = TagsPropertyServiceFactory.getService();

		return tagsPropertyService.getProperties(entryId);
	}

	public static java.util.List getPropertyValues(java.lang.String companyId,
		java.lang.String key)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		TagsPropertyService tagsPropertyService = TagsPropertyServiceFactory.getService();

		return tagsPropertyService.getPropertyValues(companyId, key);
	}

	public static com.liferay.portlet.tags.model.TagsProperty updateProperty(
		long propertyId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		TagsPropertyService tagsPropertyService = TagsPropertyServiceFactory.getService();

		return tagsPropertyService.updateProperty(propertyId, key, value);
	}
}