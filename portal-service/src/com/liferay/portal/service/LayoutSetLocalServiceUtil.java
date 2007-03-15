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

package com.liferay.portal.service;

/**
 * <a href="LayoutSetLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portal.service.LayoutSetLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.LayoutSetLocalServiceFactory</code> is responsible
 * for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.LayoutSetLocalService
 * @see com.liferay.portal.service.LayoutSetLocalServiceFactory
 *
 */
public class LayoutSetLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		LayoutSetLocalService layoutSetLocalService = LayoutSetLocalServiceFactory.getService();

		return layoutSetLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		LayoutSetLocalService layoutSetLocalService = LayoutSetLocalServiceFactory.getService();

		return layoutSetLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portal.model.LayoutSet addLayoutSet(
		java.lang.String ownerId, java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutSetLocalService layoutSetLocalService = LayoutSetLocalServiceFactory.getService();

		return layoutSetLocalService.addLayoutSet(ownerId, companyId);
	}

	public static void deleteLayoutSet(java.lang.String ownerId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutSetLocalService layoutSetLocalService = LayoutSetLocalServiceFactory.getService();
		layoutSetLocalService.deleteLayoutSet(ownerId);
	}

	public static com.liferay.portal.model.LayoutSet getLayoutSet(
		java.lang.String ownerId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutSetLocalService layoutSetLocalService = LayoutSetLocalServiceFactory.getService();

		return layoutSetLocalService.getLayoutSet(ownerId);
	}

	public static com.liferay.portal.model.LayoutSet getLayoutSet(
		java.lang.String companyId, java.lang.String virtualHost)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutSetLocalService layoutSetLocalService = LayoutSetLocalServiceFactory.getService();

		return layoutSetLocalService.getLayoutSet(companyId, virtualHost);
	}

	public static void updateLogo(java.lang.String ownerId, boolean logo,
		java.io.File file)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutSetLocalService layoutSetLocalService = LayoutSetLocalServiceFactory.getService();
		layoutSetLocalService.updateLogo(ownerId, logo, file);
	}

	public static com.liferay.portal.model.LayoutSet updateLookAndFeel(
		java.lang.String ownerId, java.lang.String themeId,
		java.lang.String colorSchemeId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutSetLocalService layoutSetLocalService = LayoutSetLocalServiceFactory.getService();

		return layoutSetLocalService.updateLookAndFeel(ownerId, themeId,
			colorSchemeId);
	}

	public static com.liferay.portal.model.LayoutSet updatePageCount(
		java.lang.String ownerId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutSetLocalService layoutSetLocalService = LayoutSetLocalServiceFactory.getService();

		return layoutSetLocalService.updatePageCount(ownerId);
	}

	public static com.liferay.portal.model.LayoutSet updateVirtualHost(
		java.lang.String ownerId, java.lang.String virtualHost)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutSetLocalService layoutSetLocalService = LayoutSetLocalServiceFactory.getService();

		return layoutSetLocalService.updateVirtualHost(ownerId, virtualHost);
	}
}