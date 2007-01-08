/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
 * <a href="LayoutServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LayoutServiceUtil {
	public static com.liferay.portal.model.Layout addLayout(long groupId,
		boolean privateLayout, java.lang.String parentLayoutId,
		java.lang.String name, java.lang.String title, java.lang.String type,
		boolean hidden, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		LayoutService layoutService = LayoutServiceFactory.getService();

		return layoutService.addLayout(groupId, privateLayout, parentLayoutId,
			name, title, type, hidden, friendlyURL);
	}

	public static void deleteLayout(java.lang.String layoutId,
		java.lang.String ownerId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		LayoutService layoutService = LayoutServiceFactory.getService();
		layoutService.deleteLayout(layoutId, ownerId);
	}

	public static java.lang.String getLayoutName(java.lang.String layoutId,
		java.lang.String ownerId, java.lang.String languageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		LayoutService layoutService = LayoutServiceFactory.getService();

		return layoutService.getLayoutName(layoutId, ownerId, languageId);
	}

	public static com.liferay.portal.model.LayoutReference[] getLayoutReferences(
		java.lang.String companyId, java.lang.String portletId,
		java.lang.String prefsKey, java.lang.String prefsValue)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		LayoutService layoutService = LayoutServiceFactory.getService();

		return layoutService.getLayoutReferences(companyId, portletId,
			prefsKey, prefsValue);
	}

	public static byte[] exportLayouts(java.lang.String ownerId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		LayoutService layoutService = LayoutServiceFactory.getService();

		return layoutService.exportLayouts(ownerId);
	}

	public static void importLayouts(java.lang.String ownerId, java.io.File file)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		LayoutService layoutService = LayoutServiceFactory.getService();
		layoutService.importLayouts(ownerId, file);
	}

	public static void setLayouts(java.lang.String ownerId,
		java.lang.String parentLayoutId, java.lang.String[] layoutIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		LayoutService layoutService = LayoutServiceFactory.getService();
		layoutService.setLayouts(ownerId, parentLayoutId, layoutIds);
	}

	public static com.liferay.portal.model.Layout updateLayout(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String parentLayoutId, java.lang.String name,
		java.lang.String title, java.lang.String languageId,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		LayoutService layoutService = LayoutServiceFactory.getService();

		return layoutService.updateLayout(layoutId, ownerId, parentLayoutId,
			name, title, languageId, type, hidden, friendlyURL);
	}

	public static com.liferay.portal.model.Layout updateLayout(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String typeSettings)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		LayoutService layoutService = LayoutServiceFactory.getService();

		return layoutService.updateLayout(layoutId, ownerId, typeSettings);
	}

	public static com.liferay.portal.model.Layout updateLookAndFeel(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String themeId, java.lang.String colorSchemeId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		LayoutService layoutService = LayoutServiceFactory.getService();

		return layoutService.updateLookAndFeel(layoutId, ownerId, themeId,
			colorSchemeId);
	}

	public static com.liferay.portal.model.Layout updateName(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String name, java.lang.String languageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		LayoutService layoutService = LayoutServiceFactory.getService();

		return layoutService.updateName(layoutId, ownerId, name, languageId);
	}
}