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

package com.liferay.portal.dao.shard;

import com.liferay.portal.NoSuchCompanyException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.impl.CompanyLocalServiceImpl;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.InputStream;

/**
 * <a href="ShardedCompanyLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Michael Young
 * @author Alexander Chow
 *
 */
public class ShardedCompanyLocalServiceImpl extends CompanyLocalServiceImpl {

	public Company addCompany(
			String webId, String virtualHost, String mx, String shardId)
		throws PortalException, SystemException {

		shardId = _shardSelector.getShardId(webId, virtualHost, mx, shardId);

		ShardUtil.pushCompanyService(shardId);

		try {
			return super.addCompany(webId, virtualHost, mx, shardId);
		}
		finally {
			ShardUtil.popCompanyService();
		}
	}

	public Company checkCompany(String webId, String mx)
		throws PortalException, SystemException {

		if (webId.equals(PropsValues.COMPANY_DEFAULT_WEB_ID)) {
			return checkCompany(webId, mx, PropsValues.SHARD_DEFAULT);
		}
		else {
			Company company = super.getCompanyByWebId(webId);

			return checkCompany(webId, mx, company.getShardId());
		}
	}

	public Company checkCompany(String webId, String mx, String shardId)
		throws PortalException, SystemException {

		try {
			Company company = super.getCompanyByWebId(webId);

			shardId = company.getShardId();
		}
		catch (NoSuchCompanyException nsce) {
		}

		ShardUtil.pushCompanyService(shardId);

		try {
			return super.checkCompany(webId, mx, shardId);
		}
		finally {
			ShardUtil.popCompanyService();
		}
	}

	public Company updateCompany(long companyId, String virtualHost, String mx,
			String homeURL, String name, String legalName, String legalId,
			String legalType, String sicCode, String tickerSymbol,
			String industry, String type, String size)
		throws PortalException,	SystemException {

		ShardUtil.pushCompanyService(companyId);

		try {
			return super.updateCompany(
				companyId, virtualHost, mx, homeURL, name, legalName, legalId,
				legalType, sicCode, tickerSymbol, industry, type, size);
		}
		finally {
			ShardUtil.popCompanyService();
		}
	}

	public Company updateCompany(long companyId, String virtualHost, String mx)
		throws PortalException, SystemException {

		ShardUtil.pushCompanyService(companyId);

		try {
			return super.updateCompany(companyId, virtualHost, mx);
		}
		finally {
			ShardUtil.popCompanyService();
		}
	}

	public void updateDisplay(
			long companyId, String languageId, String timeZoneId)
		throws PortalException, SystemException {

		ShardUtil.pushCompanyService(companyId);

		try {
			super.updateDisplay(companyId, languageId, timeZoneId);
		}
		finally {
			ShardUtil.popCompanyService();
		}
	}

	public void updateLogo(long companyId, byte[] bytes)
		throws PortalException, SystemException {

		ShardUtil.pushCompanyService(companyId);

		try {
			super.updateLogo(companyId, bytes);
		}
		finally {
			ShardUtil.popCompanyService();
		}
	}

	public void updateLogo(long companyId, File file)
		throws PortalException, SystemException {

		ShardUtil.pushCompanyService(companyId);

		try {
			super.updateLogo(companyId, file);
		}
		finally {
			ShardUtil.popCompanyService();
		}
	}

	public void updateLogo(long companyId, InputStream is)
		throws PortalException, SystemException {

		ShardUtil.pushCompanyService(companyId);

		try {
			super.updateLogo(companyId, is);
		}
		finally {
			ShardUtil.popCompanyService();
		}
	}

	public void updateSecurity(
			long companyId, String authType, boolean autoLogin,
			boolean sendPassword, boolean strangers, boolean strangersWithMx,
			boolean strangersVerify, boolean communityLogo)
		throws SystemException {

		ShardUtil.pushCompanyService(companyId);

		try {
			super.updateSecurity(
				companyId, authType, autoLogin, sendPassword, strangers,
				strangersWithMx, strangersVerify, communityLogo);
		}
		finally {
			ShardUtil.popCompanyService();
		}
	}

	private static Log _log =
		 LogFactoryUtil.getLog(ShardedCompanyLocalServiceImpl.class);

	private static ShardSelector _shardSelector;

	static {
		try {
			_shardSelector = (ShardSelector)
				Class.forName(PropsValues.SHARD_SELECTOR).newInstance();
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

}