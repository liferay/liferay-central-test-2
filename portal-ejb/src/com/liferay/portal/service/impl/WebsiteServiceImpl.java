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

package com.liferay.portal.service.impl;

import com.liferay.counter.service.spring.CounterServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.WebsiteURLException;
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.User;
import com.liferay.portal.model.Website;
import com.liferay.portal.service.persistence.WebsiteUtil;
import com.liferay.portal.service.spring.ListTypeServiceUtil;
import com.liferay.portal.service.spring.WebsiteService;
import com.liferay.util.Validator;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="WebsiteServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WebsiteServiceImpl
	extends PrincipalBean implements WebsiteService {

	// Business methods

	public Website addWebsite(
			String className, String classPK, String url, String typeId,
			boolean primary)
		throws PortalException, SystemException {

		User user = getUser();

		_validate(
			null, user.getCompanyId(), className, classPK, url, typeId,
			primary);

		String websiteId = Long.toString(CounterServiceUtil.increment(
			Website.class.getName()));

		Website website = WebsiteUtil.create(websiteId);

		Date now = new Date();

		website.setCompanyId(user.getCompanyId());
		website.setUserId(user.getUserId());
		website.setUserName(user.getFullName());
		website.setCreateDate(now);
		website.setModifiedDate(now);
		website.setClassName(className);
		website.setClassPK(classPK);
		website.setUrl(url);
		website.setTypeId(typeId);
		website.setPrimary(primary);

		WebsiteUtil.update(website);

		return website;
	}

	public void deleteWebsite(String websiteId)
		throws PortalException, SystemException {

		WebsiteUtil.remove(websiteId);
	}

	public Website getWebsite(String websiteId)
		throws PortalException, SystemException {

		return WebsiteUtil.findByPrimaryKey(websiteId);
	}

	public List getWebsites(String className, String classPK)
		throws PortalException, SystemException {

		return WebsiteUtil.findByC_C_C(
			getUser().getCompanyId(), className, classPK);
	}

	public Website updateWebsite(
			String websiteId, String url, String typeId, boolean primary)
		throws PortalException, SystemException {

		_validate(websiteId, null, null, null, url, typeId, primary);

		Website website = WebsiteUtil.findByPrimaryKey(websiteId);

		website.setModifiedDate(new Date());
		website.setUrl(url);
		website.setTypeId(typeId);
		website.setPrimary(primary);

		WebsiteUtil.update(website);

		return website;
	}

	// Private methods

	private void _validate(
			String websiteId, String companyId, String className,
			String classPK, String url, String typeId, boolean primary)
		throws PortalException, SystemException {

		if (Validator.isNull(url)) {
			throw new WebsiteURLException();
		}
		else {
			try {
				new URL(url);
			}
			catch (MalformedURLException murle) {
				throw new WebsiteURLException();
			}
		}

		if (websiteId != null) {
			Website website = WebsiteUtil.findByPrimaryKey(websiteId);

			companyId = website.getCompanyId();
			className = website.getClassName();
			classPK = website.getClassPK();
		}

		ListTypeServiceUtil.validate(typeId, className + ListType.WEBSITE);

		_validate(websiteId, companyId, className, classPK, primary);
	}

	private void _validate(
			String websiteId, String companyId, String className,
			String classPK, boolean primary)
		throws PortalException, SystemException {

		// Check to make sure there isn't another website with the same company
		// id, class name, and class pk that also has primary set to true

		if (primary) {
			Iterator itr = WebsiteUtil.findByC_C_C_P(
				companyId, className, classPK, primary).iterator();

			while (itr.hasNext()) {
				Website website = (Website)itr.next();

				if ((websiteId == null) ||
					(!website.getWebsiteId().equals(websiteId))) {

					website.setPrimary(false);

					WebsiteUtil.update(website);
				}
			}
		}
	}

}