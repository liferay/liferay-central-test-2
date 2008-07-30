/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.kernel.bean.InitializingBean;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.service.base.PrincipalBean;

import com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionLocalService;
import com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionService;
import com.liferay.portlet.softwarecatalog.service.SCLicenseLocalService;
import com.liferay.portlet.softwarecatalog.service.SCLicenseService;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryService;
import com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalService;
import com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalService;
import com.liferay.portlet.softwarecatalog.service.SCProductVersionService;
import com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionPersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCLicensePersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryPersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductScreenshotPersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductVersionPersistence;

/**
 * <a href="SCLicenseServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class SCLicenseServiceBaseImpl extends PrincipalBean
	implements SCLicenseService, InitializingBean {
	public SCLicenseLocalService getSCLicenseLocalService() {
		return scLicenseLocalService;
	}

	public void setSCLicenseLocalService(
		SCLicenseLocalService scLicenseLocalService) {
		this.scLicenseLocalService = scLicenseLocalService;
	}

	public SCLicensePersistence getSCLicensePersistence() {
		return scLicensePersistence;
	}

	public void setSCLicensePersistence(
		SCLicensePersistence scLicensePersistence) {
		this.scLicensePersistence = scLicensePersistence;
	}

	public SCFrameworkVersionLocalService getSCFrameworkVersionLocalService() {
		return scFrameworkVersionLocalService;
	}

	public void setSCFrameworkVersionLocalService(
		SCFrameworkVersionLocalService scFrameworkVersionLocalService) {
		this.scFrameworkVersionLocalService = scFrameworkVersionLocalService;
	}

	public SCFrameworkVersionService getSCFrameworkVersionService() {
		return scFrameworkVersionService;
	}

	public void setSCFrameworkVersionService(
		SCFrameworkVersionService scFrameworkVersionService) {
		this.scFrameworkVersionService = scFrameworkVersionService;
	}

	public SCFrameworkVersionPersistence getSCFrameworkVersionPersistence() {
		return scFrameworkVersionPersistence;
	}

	public void setSCFrameworkVersionPersistence(
		SCFrameworkVersionPersistence scFrameworkVersionPersistence) {
		this.scFrameworkVersionPersistence = scFrameworkVersionPersistence;
	}

	public SCProductEntryLocalService getSCProductEntryLocalService() {
		return scProductEntryLocalService;
	}

	public void setSCProductEntryLocalService(
		SCProductEntryLocalService scProductEntryLocalService) {
		this.scProductEntryLocalService = scProductEntryLocalService;
	}

	public SCProductEntryService getSCProductEntryService() {
		return scProductEntryService;
	}

	public void setSCProductEntryService(
		SCProductEntryService scProductEntryService) {
		this.scProductEntryService = scProductEntryService;
	}

	public SCProductEntryPersistence getSCProductEntryPersistence() {
		return scProductEntryPersistence;
	}

	public void setSCProductEntryPersistence(
		SCProductEntryPersistence scProductEntryPersistence) {
		this.scProductEntryPersistence = scProductEntryPersistence;
	}

	public SCProductScreenshotLocalService getSCProductScreenshotLocalService() {
		return scProductScreenshotLocalService;
	}

	public void setSCProductScreenshotLocalService(
		SCProductScreenshotLocalService scProductScreenshotLocalService) {
		this.scProductScreenshotLocalService = scProductScreenshotLocalService;
	}

	public SCProductScreenshotPersistence getSCProductScreenshotPersistence() {
		return scProductScreenshotPersistence;
	}

	public void setSCProductScreenshotPersistence(
		SCProductScreenshotPersistence scProductScreenshotPersistence) {
		this.scProductScreenshotPersistence = scProductScreenshotPersistence;
	}

	public SCProductVersionLocalService getSCProductVersionLocalService() {
		return scProductVersionLocalService;
	}

	public void setSCProductVersionLocalService(
		SCProductVersionLocalService scProductVersionLocalService) {
		this.scProductVersionLocalService = scProductVersionLocalService;
	}

	public SCProductVersionService getSCProductVersionService() {
		return scProductVersionService;
	}

	public void setSCProductVersionService(
		SCProductVersionService scProductVersionService) {
		this.scProductVersionService = scProductVersionService;
	}

	public SCProductVersionPersistence getSCProductVersionPersistence() {
		return scProductVersionPersistence;
	}

	public void setSCProductVersionPersistence(
		SCProductVersionPersistence scProductVersionPersistence) {
		this.scProductVersionPersistence = scProductVersionPersistence;
	}

	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	public CounterService getCounterService() {
		return counterService;
	}

	public void setCounterService(CounterService counterService) {
		this.counterService = counterService;
	}

	public void afterPropertiesSet() {
		if (scLicenseLocalService == null) {
			scLicenseLocalService = (SCLicenseLocalService)PortalBeanLocatorUtil.locate(SCLicenseLocalService.class.getName() +
					".impl");
		}

		if (scLicensePersistence == null) {
			scLicensePersistence = (SCLicensePersistence)PortalBeanLocatorUtil.locate(SCLicensePersistence.class.getName() +
					".impl");
		}

		if (scFrameworkVersionLocalService == null) {
			scFrameworkVersionLocalService = (SCFrameworkVersionLocalService)PortalBeanLocatorUtil.locate(SCFrameworkVersionLocalService.class.getName() +
					".impl");
		}

		if (scFrameworkVersionService == null) {
			scFrameworkVersionService = (SCFrameworkVersionService)PortalBeanLocatorUtil.locate(SCFrameworkVersionService.class.getName() +
					".impl");
		}

		if (scFrameworkVersionPersistence == null) {
			scFrameworkVersionPersistence = (SCFrameworkVersionPersistence)PortalBeanLocatorUtil.locate(SCFrameworkVersionPersistence.class.getName() +
					".impl");
		}

		if (scProductEntryLocalService == null) {
			scProductEntryLocalService = (SCProductEntryLocalService)PortalBeanLocatorUtil.locate(SCProductEntryLocalService.class.getName() +
					".impl");
		}

		if (scProductEntryService == null) {
			scProductEntryService = (SCProductEntryService)PortalBeanLocatorUtil.locate(SCProductEntryService.class.getName() +
					".impl");
		}

		if (scProductEntryPersistence == null) {
			scProductEntryPersistence = (SCProductEntryPersistence)PortalBeanLocatorUtil.locate(SCProductEntryPersistence.class.getName() +
					".impl");
		}

		if (scProductScreenshotLocalService == null) {
			scProductScreenshotLocalService = (SCProductScreenshotLocalService)PortalBeanLocatorUtil.locate(SCProductScreenshotLocalService.class.getName() +
					".impl");
		}

		if (scProductScreenshotPersistence == null) {
			scProductScreenshotPersistence = (SCProductScreenshotPersistence)PortalBeanLocatorUtil.locate(SCProductScreenshotPersistence.class.getName() +
					".impl");
		}

		if (scProductVersionLocalService == null) {
			scProductVersionLocalService = (SCProductVersionLocalService)PortalBeanLocatorUtil.locate(SCProductVersionLocalService.class.getName() +
					".impl");
		}

		if (scProductVersionService == null) {
			scProductVersionService = (SCProductVersionService)PortalBeanLocatorUtil.locate(SCProductVersionService.class.getName() +
					".impl");
		}

		if (scProductVersionPersistence == null) {
			scProductVersionPersistence = (SCProductVersionPersistence)PortalBeanLocatorUtil.locate(SCProductVersionPersistence.class.getName() +
					".impl");
		}

		if (counterLocalService == null) {
			counterLocalService = (CounterLocalService)PortalBeanLocatorUtil.locate(CounterLocalService.class.getName() +
					".impl");
		}

		if (counterService == null) {
			counterService = (CounterService)PortalBeanLocatorUtil.locate(CounterService.class.getName() +
					".impl");
		}
	}

	protected SCLicenseLocalService scLicenseLocalService;
	protected SCLicensePersistence scLicensePersistence;
	protected SCFrameworkVersionLocalService scFrameworkVersionLocalService;
	protected SCFrameworkVersionService scFrameworkVersionService;
	protected SCFrameworkVersionPersistence scFrameworkVersionPersistence;
	protected SCProductEntryLocalService scProductEntryLocalService;
	protected SCProductEntryService scProductEntryService;
	protected SCProductEntryPersistence scProductEntryPersistence;
	protected SCProductScreenshotLocalService scProductScreenshotLocalService;
	protected SCProductScreenshotPersistence scProductScreenshotPersistence;
	protected SCProductVersionLocalService scProductVersionLocalService;
	protected SCProductVersionService scProductVersionService;
	protected SCProductVersionPersistence scProductVersionPersistence;
	protected CounterLocalService counterLocalService;
	protected CounterService counterService;
}