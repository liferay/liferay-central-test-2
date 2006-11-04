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

package com.liferay.portal.service.ejb;

import com.liferay.portal.service.spring.WebsiteLocalService;
import com.liferay.portal.spring.util.SpringUtil;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="WebsiteLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WebsiteLocalServiceEJBImpl implements WebsiteLocalService,
	SessionBean {
	public static final String CLASS_NAME = WebsiteLocalService.class.getName() +
		".transaction";

	public static WebsiteLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (WebsiteLocalService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portal.model.Website addWebsite(
		java.lang.String userId, java.lang.String className,
		java.lang.String classPK, java.lang.String url,
		java.lang.String typeId, boolean primary)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addWebsite(userId, className, classPK, url, typeId,
			primary);
	}

	public void deleteWebsite(java.lang.String websiteId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteWebsite(websiteId);
	}

	public void deleteWebsites(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		getService().deleteWebsites(companyId, className, classPK);
	}

	public com.liferay.portal.model.Website getWebsite(
		java.lang.String websiteId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getWebsite(websiteId);
	}

	public java.util.List getWebsites(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return getService().getWebsites(companyId, className, classPK);
	}

	public com.liferay.portal.model.Website updateWebsite(
		java.lang.String websiteId, java.lang.String url,
		java.lang.String typeId, boolean primary)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updateWebsite(websiteId, url, typeId, primary);
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