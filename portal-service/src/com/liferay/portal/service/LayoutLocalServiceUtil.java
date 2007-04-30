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
 * <a href="LayoutLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portal.service.LayoutLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.LayoutLocalServiceFactory</code> is responsible
 * for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.LayoutLocalService
 * @see com.liferay.portal.service.LayoutLocalServiceFactory
 *
 */
public class LayoutLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portal.model.Layout addLayout(long groupId,
		long userId, boolean privateLayout, java.lang.String parentLayoutId,
		java.lang.String name, java.lang.String title, java.lang.String type,
		boolean hidden, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.addLayout(groupId, userId, privateLayout,
			parentLayoutId, name, title, type, hidden, friendlyURL);
	}

	public static void deleteLayout(java.lang.String layoutId,
		java.lang.String ownerId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();
		layoutLocalService.deleteLayout(layoutId, ownerId);
	}

	public static void deleteLayout(com.liferay.portal.model.Layout layout,
		boolean updateLayoutSet)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();
		layoutLocalService.deleteLayout(layout, updateLayoutSet);
	}

	public static void deleteLayouts(java.lang.String ownerId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();
		layoutLocalService.deleteLayouts(ownerId);
	}

	public static byte[] exportLayouts(java.lang.String ownerId,
		java.util.Map parameterMap)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.exportLayouts(ownerId, parameterMap);
	}

	public static com.liferay.portal.model.Layout getFriendlyURLLayout(
		java.lang.String ownerId, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.getFriendlyURLLayout(ownerId, friendlyURL);
	}

	public static com.liferay.portal.model.Layout getLayout(
		java.lang.String plid)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.getLayout(plid);
	}

	public static com.liferay.portal.model.Layout getLayout(
		java.lang.String layoutId, java.lang.String ownerId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.getLayout(layoutId, ownerId);
	}

	public static java.util.List getLayouts(java.lang.String ownerId)
		throws com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.getLayouts(ownerId);
	}

	public static java.util.List getLayouts(java.lang.String ownerId,
		java.lang.String parentLayoutId)
		throws com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.getLayouts(ownerId, parentLayoutId);
	}

	public static com.liferay.portal.model.LayoutReference[] getLayouts(
		long companyId, java.lang.String portletId, java.lang.String prefsKey,
		java.lang.String prefsValue) throws com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.getLayouts(companyId, portletId, prefsKey,
			prefsValue);
	}

	public static void importLayouts(long userId, java.lang.String ownerId,
		java.util.Map parameterMap, java.io.File file)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();
		layoutLocalService.importLayouts(userId, ownerId, parameterMap, file);
	}

	public static void importLayouts(long userId, java.lang.String ownerId,
		java.util.Map parameterMap, java.io.InputStream is)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();
		layoutLocalService.importLayouts(userId, ownerId, parameterMap, is);
	}

	public static void setLayouts(java.lang.String ownerId,
		java.lang.String parentLayoutId, java.lang.String[] layoutIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();
		layoutLocalService.setLayouts(ownerId, parentLayoutId, layoutIds);
	}

	public static com.liferay.portal.model.Layout updateLayout(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String parentLayoutId, java.lang.String name,
		java.lang.String title, java.lang.String languageId,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.updateLayout(layoutId, ownerId,
			parentLayoutId, name, title, languageId, type, hidden, friendlyURL);
	}

	public static com.liferay.portal.model.Layout updateLayout(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String parentLayoutId, java.lang.String name,
		java.lang.String title, java.lang.String languageId,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL,
		java.lang.Boolean iconImage, byte[] iconBytes)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.updateLayout(layoutId, ownerId,
			parentLayoutId, name, title, languageId, type, hidden, friendlyURL,
			iconImage, iconBytes);
	}

	public static com.liferay.portal.model.Layout updateLayout(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String typeSettings)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.updateLayout(layoutId, ownerId, typeSettings);
	}

	public static com.liferay.portal.model.Layout updateLookAndFeel(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String themeId, java.lang.String colorSchemeId,
		java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.updateLookAndFeel(layoutId, ownerId, themeId,
			colorSchemeId, css, wapTheme);
	}

	public static com.liferay.portal.model.Layout updateName(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String name, java.lang.String languageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.updateName(layoutId, ownerId, name, languageId);
	}

	public static com.liferay.portal.model.Layout updateParentLayoutId(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String parentLayoutId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.updateParentLayoutId(layoutId, ownerId,
			parentLayoutId);
	}

	public static com.liferay.portal.model.Layout updatePriority(
		java.lang.String layoutId, java.lang.String ownerId, int priority)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.updatePriority(layoutId, ownerId, priority);
	}
}