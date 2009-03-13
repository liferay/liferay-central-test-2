/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.service.CompanyServiceUtil;

/**
 * <a href="CompanyServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a JSON utility for the
 * <code>com.liferay.portal.service.CompanyServiceUtil</code>
 * service utility. The static methods of this class calls the same methods of
 * the service utility. However, the signatures are different because it is
 * difficult for JSON to support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to a
 * <code>com.liferay.portal.kernel.json.JSONArray</code>. If the method in the
 * service utility returns a <code>com.liferay.portal.model.Company</code>,
 * that is translated to a
 * <code>com.liferay.portal.kernel.json.JSONObject</code>. Methods that JSON
 * cannot safely use are skipped. The logic for the translation is encapsulated
 * in <code>com.liferay.portal.service.http.CompanyJSONSerializer</code>.
 * </p>
 *
 * <p>
 * This allows you to call the the backend services directly from JavaScript.
 * See <code>portal-web/docroot/html/portlet/tags_admin/unpacked.js</code> for a
 * reference of how that portlet uses the generated JavaScript in
 * <code>portal-web/docroot/html/js/service.js</code> to call the backend
 * services directly from JavaScript.
 * </p>
 *
 * <p>
 * The JSON utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.CompanyServiceUtil
 * @see com.liferay.portal.service.http.CompanyJSONSerializer
 *
 */
public class CompanyServiceJSON {
	public static JSONObject addCompany(java.lang.String webId,
		java.lang.String virtualHost, java.lang.String mx,
		java.lang.String shardName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portal.model.Company returnValue = CompanyServiceUtil.addCompany(webId,
				virtualHost, mx, shardName);

		return CompanyJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getCompanyById(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portal.model.Company returnValue = CompanyServiceUtil.getCompanyById(companyId);

		return CompanyJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getCompanyByLogoId(long logoId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portal.model.Company returnValue = CompanyServiceUtil.getCompanyByLogoId(logoId);

		return CompanyJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getCompanyByMx(java.lang.String mx)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portal.model.Company returnValue = CompanyServiceUtil.getCompanyByMx(mx);

		return CompanyJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getCompanyByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portal.model.Company returnValue = CompanyServiceUtil.getCompanyByVirtualHost(virtualHost);

		return CompanyJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getCompanyByWebId(java.lang.String webId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portal.model.Company returnValue = CompanyServiceUtil.getCompanyByWebId(webId);

		return CompanyJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateCompany(long companyId,
		java.lang.String virtualHost, java.lang.String mx)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portal.model.Company returnValue = CompanyServiceUtil.updateCompany(companyId,
				virtualHost, mx);

		return CompanyJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateCompany(long companyId,
		java.lang.String virtualHost, java.lang.String mx,
		java.lang.String homeURL, java.lang.String name,
		java.lang.String legalName, java.lang.String legalId,
		java.lang.String legalType, java.lang.String sicCode,
		java.lang.String tickerSymbol, java.lang.String industry,
		java.lang.String type, java.lang.String size)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portal.model.Company returnValue = CompanyServiceUtil.updateCompany(companyId,
				virtualHost, mx, homeURL, name, legalName, legalId, legalType,
				sicCode, tickerSymbol, industry, type, size);

		return CompanyJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateCompany(long companyId,
		java.lang.String virtualHost, java.lang.String mx,
		java.lang.String homeURL, java.lang.String name,
		java.lang.String legalName, java.lang.String legalId,
		java.lang.String legalType, java.lang.String sicCode,
		java.lang.String tickerSymbol, java.lang.String industry,
		java.lang.String type, java.lang.String size,
		java.lang.String languageId, java.lang.String timeZoneId,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		com.liferay.portal.kernel.util.UnicodeProperties properties)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portal.model.Company returnValue = CompanyServiceUtil.updateCompany(companyId,
				virtualHost, mx, homeURL, name, legalName, legalId, legalType,
				sicCode, tickerSymbol, industry, type, size, languageId,
				timeZoneId, addresses, emailAddresses, phones, websites,
				properties);

		return CompanyJSONSerializer.toJSONObject(returnValue);
	}

	public static void updateDisplay(long companyId,
		java.lang.String languageId, java.lang.String timeZoneId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		CompanyServiceUtil.updateDisplay(companyId, languageId, timeZoneId);
	}

	public static void updatePreferences(long companyId,
		com.liferay.portal.kernel.util.UnicodeProperties properties)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		CompanyServiceUtil.updatePreferences(companyId, properties);
	}

	public static void updateSecurity(long companyId,
		java.lang.String authType, boolean autoLogin, boolean sendPassword,
		boolean strangers, boolean strangersWithMx, boolean strangersVerify,
		boolean communityLogo)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		CompanyServiceUtil.updateSecurity(companyId, authType, autoLogin,
			sendPassword, strangers, strangersWithMx, strangersVerify,
			communityLogo);
	}
}