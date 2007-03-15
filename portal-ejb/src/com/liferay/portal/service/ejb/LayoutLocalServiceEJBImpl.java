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

package com.liferay.portal.service.ejb;

import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="LayoutLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is the EJB implementation of the service that is used when Liferay
 * is run inside a full J2EE container.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.LayoutLocalService
 * @see com.liferay.portal.service.LayoutLocalServiceUtil
 * @see com.liferay.portal.service.ejb.LayoutLocalServiceEJB
 * @see com.liferay.portal.service.ejb.LayoutLocalServiceHome
 * @see com.liferay.portal.service.impl.LayoutLocalServiceImpl
 *
 */
public class LayoutLocalServiceEJBImpl implements LayoutLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portal.model.Layout addLayout(long groupId,
		java.lang.String userId, boolean privateLayout,
		java.lang.String parentLayoutId, java.lang.String name,
		java.lang.String title, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().addLayout(groupId, userId,
			privateLayout, parentLayoutId, name, title, type, hidden,
			friendlyURL);
	}

	public void deleteLayout(java.lang.String layoutId, java.lang.String ownerId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalServiceFactory.getTxImpl().deleteLayout(layoutId, ownerId);
	}

	public void deleteLayout(com.liferay.portal.model.Layout layout,
		boolean updateLayoutSet)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalServiceFactory.getTxImpl().deleteLayout(layout,
			updateLayoutSet);
	}

	public void deleteLayouts(java.lang.String ownerId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalServiceFactory.getTxImpl().deleteLayouts(ownerId);
	}

	public byte[] exportLayouts(java.lang.String ownerId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().exportLayouts(ownerId);
	}

	public com.liferay.portal.model.Layout getFriendlyURLLayout(
		java.lang.String ownerId, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().getFriendlyURLLayout(ownerId,
			friendlyURL);
	}

	public com.liferay.portal.model.Layout getLayout(java.lang.String plid)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().getLayout(plid);
	}

	public com.liferay.portal.model.Layout getLayout(
		java.lang.String layoutId, java.lang.String ownerId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().getLayout(layoutId, ownerId);
	}

	public java.util.List getLayouts(java.lang.String ownerId)
		throws com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().getLayouts(ownerId);
	}

	public java.util.List getLayouts(java.lang.String ownerId,
		java.lang.String parentLayoutId)
		throws com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().getLayouts(ownerId,
			parentLayoutId);
	}

	public com.liferay.portal.model.LayoutReference[] getLayouts(
		java.lang.String companyId, java.lang.String portletId,
		java.lang.String prefsKey, java.lang.String prefsValue)
		throws com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().getLayouts(companyId,
			portletId, prefsKey, prefsValue);
	}

	public void importLayouts(java.lang.String userId,
		java.lang.String ownerId, java.io.File file)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalServiceFactory.getTxImpl().importLayouts(userId, ownerId,
			file);
	}

	public void importLayouts(java.lang.String userId,
		java.lang.String ownerId, java.io.InputStream is)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalServiceFactory.getTxImpl().importLayouts(userId, ownerId, is);
	}

	public void setLayouts(java.lang.String ownerId,
		java.lang.String parentLayoutId, java.lang.String[] layoutIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalServiceFactory.getTxImpl().setLayouts(ownerId,
			parentLayoutId, layoutIds);
	}

	public com.liferay.portal.model.Layout updateLayout(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String parentLayoutId, java.lang.String name,
		java.lang.String title, java.lang.String languageId,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().updateLayout(layoutId,
			ownerId, parentLayoutId, name, title, languageId, type, hidden,
			friendlyURL);
	}

	public com.liferay.portal.model.Layout updateLayout(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String parentLayoutId, java.lang.String name,
		java.lang.String title, java.lang.String languageId,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL,
		java.lang.Boolean iconImage, byte[] iconBytes)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().updateLayout(layoutId,
			ownerId, parentLayoutId, name, title, languageId, type, hidden,
			friendlyURL, iconImage, iconBytes);
	}

	public com.liferay.portal.model.Layout updateLayout(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String typeSettings)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().updateLayout(layoutId,
			ownerId, typeSettings);
	}

	public com.liferay.portal.model.Layout updateLookAndFeel(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String themeId, java.lang.String colorSchemeId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().updateLookAndFeel(layoutId,
			ownerId, themeId, colorSchemeId);
	}

	public com.liferay.portal.model.Layout updateName(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String name, java.lang.String languageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().updateName(layoutId,
			ownerId, name, languageId);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}