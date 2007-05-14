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

	public static com.liferay.portal.model.Layout addLayout(long userId,
		long groupId, boolean privateLayout, long parentLayoutId,
		java.lang.String name, java.lang.String title, java.lang.String type,
		boolean hidden, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.addLayout(userId, groupId, privateLayout,
			parentLayoutId, name, title, type, hidden, friendlyURL);
	}

	public static void deleteLayout(long groupId, boolean privateLayout,
		long layoutId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();
		layoutLocalService.deleteLayout(groupId, privateLayout, layoutId);
	}

	public static void deleteLayout(com.liferay.portal.model.Layout layout,
		boolean updateLayoutSet)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();
		layoutLocalService.deleteLayout(layout, updateLayoutSet);
	}

	public static void deleteLayouts(long groupId, boolean privateLayout)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();
		layoutLocalService.deleteLayouts(groupId, privateLayout);
	}

	public static byte[] exportLayouts(long groupId, boolean privateLayout,
		java.util.Map parameterMap)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.exportLayouts(groupId, privateLayout,
			parameterMap);
	}

	public static long getDefaultPlid(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.getDefaultPlid(groupId, privateLayout);
	}

	public static com.liferay.portal.model.Layout getFriendlyURLLayout(
		long groupId, boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.getFriendlyURLLayout(groupId, privateLayout,
			friendlyURL);
	}

	public static com.liferay.portal.model.Layout getLayout(long plid)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.getLayout(plid);
	}

	public static com.liferay.portal.model.Layout getLayout(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.getLayout(groupId, privateLayout, layoutId);
	}

	public static java.util.List getLayouts(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.getLayouts(groupId, privateLayout);
	}

	public static java.util.List getLayouts(long groupId,
		boolean privateLayout, long parentLayoutId)
		throws com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.getLayouts(groupId, privateLayout,
			parentLayoutId);
	}

	public static com.liferay.portal.model.LayoutReference[] getLayouts(
		long companyId, java.lang.String portletId, java.lang.String prefsKey,
		java.lang.String prefsValue) throws com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.getLayouts(companyId, portletId, prefsKey,
			prefsValue);
	}

	public static void importLayouts(long userId, long groupId,
		boolean privateLayout, java.util.Map parameterMap, java.io.File file)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();
		layoutLocalService.importLayouts(userId, groupId, privateLayout,
			parameterMap, file);
	}

	public static void importLayouts(long userId, long groupId,
		boolean privateLayout, java.util.Map parameterMap,
		java.io.InputStream is)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();
		layoutLocalService.importLayouts(userId, groupId, privateLayout,
			parameterMap, is);
	}

	public static void setLayouts(long groupId, boolean privateLayout,
		long parentLayoutId, long[] layoutIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();
		layoutLocalService.setLayouts(groupId, privateLayout, parentLayoutId,
			layoutIds);
	}

	public static com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId,
		java.lang.String name, java.lang.String title,
		java.lang.String languageId, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.updateLayout(groupId, privateLayout,
			layoutId, parentLayoutId, name, title, languageId, type, hidden,
			friendlyURL);
	}

	public static com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId,
		java.lang.String name, java.lang.String title,
		java.lang.String languageId, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL, java.lang.Boolean iconImage,
		byte[] iconBytes)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.updateLayout(groupId, privateLayout,
			layoutId, parentLayoutId, name, title, languageId, type, hidden,
			friendlyURL, iconImage, iconBytes);
	}

	public static com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, java.lang.String typeSettings)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.updateLayout(groupId, privateLayout,
			layoutId, typeSettings);
	}

	public static com.liferay.portal.model.Layout updateLookAndFeel(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String themeId, java.lang.String colorSchemeId,
		java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.updateLookAndFeel(groupId, privateLayout,
			layoutId, themeId, colorSchemeId, css, wapTheme);
	}

	public static com.liferay.portal.model.Layout updateName(long groupId,
		boolean privateLayout, long layoutId, java.lang.String name,
		java.lang.String languageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.updateName(groupId, privateLayout, layoutId,
			name, languageId);
	}

	public static com.liferay.portal.model.Layout updateParentLayoutId(
		long groupId, boolean privateLayout, long layoutId, long parentLayoutId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.updateParentLayoutId(groupId, privateLayout,
			layoutId, parentLayoutId);
	}

	public static com.liferay.portal.model.Layout updatePriority(long groupId,
		boolean privateLayout, long layoutId, int priority)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalService layoutLocalService = LayoutLocalServiceFactory.getService();

		return layoutLocalService.updatePriority(groupId, privateLayout,
			layoutId, priority);
	}
}