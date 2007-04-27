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
 * <a href="LayoutService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is <code>com.liferay.portal.service.impl.LayoutServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be accessed
 * remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.LayoutServiceFactory
 * @see com.liferay.portal.service.LayoutServiceUtil
 *
 */
public interface LayoutService {
	public com.liferay.portal.model.Layout addLayout(long groupId,
		boolean privateLayout, java.lang.String parentLayoutId,
		java.lang.String name, java.lang.String title, java.lang.String type,
		boolean hidden, java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void deleteLayout(java.lang.String layoutId, java.lang.String ownerId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public java.lang.String getLayoutName(java.lang.String layoutId,
		java.lang.String ownerId, java.lang.String languageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portal.model.LayoutReference[] getLayoutReferences(
		long companyId, java.lang.String portletId, java.lang.String prefsKey,
		java.lang.String prefsValue)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException;

	public byte[] exportLayouts(java.lang.String ownerId,
		java.util.Map parameterMap)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void importLayouts(java.lang.String ownerId,
		java.util.Map parameterMap, java.io.File file)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void setLayouts(java.lang.String ownerId,
		java.lang.String parentLayoutId, java.lang.String[] layoutIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portal.model.Layout updateLayout(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String parentLayoutId, java.lang.String name,
		java.lang.String title, java.lang.String languageId,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portal.model.Layout updateLayout(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String parentLayoutId, java.lang.String name,
		java.lang.String title, java.lang.String languageId,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL,
		java.lang.Boolean iconImage, byte[] iconBytes)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portal.model.Layout updateLayout(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String typeSettings)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portal.model.Layout updateLookAndFeel(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String themeId, java.lang.String colorSchemeId,
		java.lang.String css)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portal.model.Layout updateName(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String name, java.lang.String languageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portal.model.Layout updateParentLayoutId(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String parentLayoutId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portal.model.Layout updatePriority(
		java.lang.String layoutId, java.lang.String ownerId, int priority)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;
}