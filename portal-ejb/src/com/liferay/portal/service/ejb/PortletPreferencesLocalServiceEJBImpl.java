/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portal.service.spring.PortletPreferencesLocalService;
import com.liferay.portal.spring.util.SpringUtil;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="PortletPreferencesLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PortletPreferencesLocalServiceEJBImpl
	implements PortletPreferencesLocalService, SessionBean {
	public static final String CLASS_NAME = PortletPreferencesLocalService.class.getName() +
		".transaction";

	public static PortletPreferencesLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (PortletPreferencesLocalService)ctx.getBean(CLASS_NAME);
	}

	public void deleteAllByLayout(
		com.liferay.portal.service.persistence.LayoutPK pk)
		throws com.liferay.portal.SystemException {
		getService().deleteAllByLayout(pk);
	}

	public void deleteAllByUser(java.lang.String ownerId)
		throws com.liferay.portal.SystemException {
		getService().deleteAllByUser(ownerId);
	}

	public javax.portlet.PortletPreferences getDefaultPreferences(
		java.lang.String companyId, java.lang.String portletId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getDefaultPreferences(companyId, portletId);
	}

	public com.liferay.portal.model.PortletPreferences getPortletPreferences(
		com.liferay.portal.service.persistence.PortletPreferencesPK pk)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getPortletPreferences(pk);
	}

	public javax.portlet.PortletPreferences getPreferences(
		java.lang.String companyId,
		com.liferay.portal.service.persistence.PortletPreferencesPK pk)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getPreferences(companyId, pk);
	}

	public com.liferay.portal.model.PortletPreferences updatePreferences(
		com.liferay.portal.service.persistence.PortletPreferencesPK pk,
		com.liferay.portlet.PortletPreferencesImpl prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updatePreferences(pk, prefs);
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