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

	public com.liferay.portal.model.Layout addLayout(long userId, long groupId,
		boolean privateLayout, long parentLayoutId, java.lang.String name,
		java.lang.String title, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().addLayout(userId, groupId,
			privateLayout, parentLayoutId, name, title, type, hidden,
			friendlyURL);
	}

	public void deleteLayout(long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalServiceFactory.getTxImpl().deleteLayout(groupId,
			privateLayout, layoutId);
	}

	public void deleteLayout(com.liferay.portal.model.Layout layout,
		boolean updateLayoutSet)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalServiceFactory.getTxImpl().deleteLayout(layout,
			updateLayoutSet);
	}

	public void deleteLayouts(long groupId, boolean privateLayout)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalServiceFactory.getTxImpl().deleteLayouts(groupId,
			privateLayout);
	}

	public byte[] exportLayouts(long groupId, boolean privateLayout,
		java.util.Map parameterMap)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().exportLayouts(groupId,
			privateLayout, parameterMap);
	}

	public long getDefaultPlid(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().getDefaultPlid(groupId,
			privateLayout);
	}

	public com.liferay.portal.model.Layout getFriendlyURLLayout(long groupId,
		boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().getFriendlyURLLayout(groupId,
			privateLayout, friendlyURL);
	}

	public com.liferay.portal.model.Layout getLayout(long plid)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().getLayout(plid);
	}

	public com.liferay.portal.model.Layout getLayout(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().getLayout(groupId,
			privateLayout, layoutId);
	}

	public java.util.List getLayouts(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().getLayouts(groupId,
			privateLayout);
	}

	public java.util.List getLayouts(long groupId, boolean privateLayout,
		long parentLayoutId) throws com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().getLayouts(groupId,
			privateLayout, parentLayoutId);
	}

	public com.liferay.portal.model.LayoutReference[] getLayouts(
		long companyId, java.lang.String portletId, java.lang.String prefsKey,
		java.lang.String prefsValue) throws com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().getLayouts(companyId,
			portletId, prefsKey, prefsValue);
	}

	public void importLayouts(long userId, long groupId, boolean privateLayout,
		java.util.Map parameterMap, java.io.File file)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalServiceFactory.getTxImpl().importLayouts(userId, groupId,
			privateLayout, parameterMap, file);
	}

	public void importLayouts(long userId, long groupId, boolean privateLayout,
		java.util.Map parameterMap, java.io.InputStream is)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalServiceFactory.getTxImpl().importLayouts(userId, groupId,
			privateLayout, parameterMap, is);
	}

	public void setLayouts(long groupId, boolean privateLayout,
		long parentLayoutId, long[] layoutIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutLocalServiceFactory.getTxImpl().setLayouts(groupId,
			privateLayout, parentLayoutId, layoutIds);
	}

	public com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId,
		java.lang.String name, java.lang.String title,
		java.lang.String languageId, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().updateLayout(groupId,
			privateLayout, layoutId, parentLayoutId, name, title, languageId,
			type, hidden, friendlyURL);
	}

	public com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId,
		java.lang.String name, java.lang.String title,
		java.lang.String languageId, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL, java.lang.Boolean iconImage,
		byte[] iconBytes)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().updateLayout(groupId,
			privateLayout, layoutId, parentLayoutId, name, title, languageId,
			type, hidden, friendlyURL, iconImage, iconBytes);
	}

	public com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, java.lang.String typeSettings)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().updateLayout(groupId,
			privateLayout, layoutId, typeSettings);
	}

	public com.liferay.portal.model.Layout updateLookAndFeel(long groupId,
		boolean privateLayout, long layoutId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().updateLookAndFeel(groupId,
			privateLayout, layoutId, themeId, colorSchemeId, css, wapTheme);
	}

	public com.liferay.portal.model.Layout updateName(long groupId,
		boolean privateLayout, long layoutId, java.lang.String name,
		java.lang.String languageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().updateName(groupId,
			privateLayout, layoutId, name, languageId);
	}

	public com.liferay.portal.model.Layout updateParentLayoutId(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().updateParentLayoutId(groupId,
			privateLayout, layoutId, parentLayoutId);
	}

	public com.liferay.portal.model.Layout updatePriority(long groupId,
		boolean privateLayout, long layoutId, int priority)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutLocalServiceFactory.getTxImpl().updatePriority(groupId,
			privateLayout, layoutId, priority);
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