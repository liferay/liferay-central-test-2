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

import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.CompanyLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="CompanyLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portal.service.CompanyLocalService
 * @see com.liferay.portal.service.CompanyLocalServiceUtil
 * @see com.liferay.portal.service.ejb.CompanyLocalServiceEJB
 * @see com.liferay.portal.service.ejb.CompanyLocalServiceHome
 * @see com.liferay.portal.service.impl.CompanyLocalServiceImpl
 *
 */
public class CompanyLocalServiceEJBImpl implements CompanyLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return CompanyLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return CompanyLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portal.model.Company checkCompany(java.lang.String webId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return CompanyLocalServiceFactory.getTxImpl().checkCompany(webId);
	}

	public void checkCompanyKey(long companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		CompanyLocalServiceFactory.getTxImpl().checkCompanyKey(companyId);
	}

	public java.util.List getCompanies()
		throws com.liferay.portal.SystemException {
		return CompanyLocalServiceFactory.getTxImpl().getCompanies();
	}

	public com.liferay.portal.model.Company getCompanyById(long companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return CompanyLocalServiceFactory.getTxImpl().getCompanyById(companyId);
	}

	public com.liferay.portal.model.Company getCompanyByMx(java.lang.String mx)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return CompanyLocalServiceFactory.getTxImpl().getCompanyByMx(mx);
	}

	public com.liferay.portal.model.Company getCompanyByWebId(
		java.lang.String webId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return CompanyLocalServiceFactory.getTxImpl().getCompanyByWebId(webId);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String keywords) throws com.liferay.portal.SystemException {
		return CompanyLocalServiceFactory.getTxImpl().search(companyId, keywords);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String portletId, long groupId, java.lang.String type,
		java.lang.String keywords) throws com.liferay.portal.SystemException {
		return CompanyLocalServiceFactory.getTxImpl().search(companyId,
			portletId, groupId, type, keywords);
	}

	public com.liferay.portal.model.Company updateCompany(long companyId,
		java.lang.String portalURL, java.lang.String homeURL,
		java.lang.String mx, java.lang.String name, java.lang.String legalName,
		java.lang.String legalId, java.lang.String legalType,
		java.lang.String sicCode, java.lang.String tickerSymbol,
		java.lang.String industry, java.lang.String type, java.lang.String size)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return CompanyLocalServiceFactory.getTxImpl().updateCompany(companyId,
			portalURL, homeURL, mx, name, legalName, legalId, legalType,
			sicCode, tickerSymbol, industry, type, size);
	}

	public void updateDisplay(long companyId, java.lang.String languageId,
		java.lang.String timeZoneId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		CompanyLocalServiceFactory.getTxImpl().updateDisplay(companyId,
			languageId, timeZoneId);
	}

	public void updateLogo(long companyId, java.io.File file)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		CompanyLocalServiceFactory.getTxImpl().updateLogo(companyId, file);
	}

	public void updateSecurity(long companyId, java.lang.String authType,
		boolean autoLogin, boolean sendPassword, boolean strangers,
		boolean communityLogo)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		CompanyLocalServiceFactory.getTxImpl().updateSecurity(companyId,
			authType, autoLogin, sendPassword, strangers, communityLogo);
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