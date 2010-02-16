/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
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

import java.util.List;

/**
 * <a href="SCProductVersionLocalServiceBaseImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class SCProductVersionLocalServiceBaseImpl
	implements SCProductVersionLocalService {
	public SCProductVersion addSCProductVersion(
		SCProductVersion scProductVersion) throws SystemException {
		scProductVersion.setNew(true);

		return scProductVersionPersistence.update(scProductVersion, false);
	}

	public SCProductVersion createSCProductVersion(long productVersionId) {
		return scProductVersionPersistence.create(productVersionId);
	}

	public void deleteSCProductVersion(long productVersionId)
		throws PortalException, SystemException {
		scProductVersionPersistence.remove(productVersionId);
	}

	public void deleteSCProductVersion(SCProductVersion scProductVersion)
		throws SystemException {
		scProductVersionPersistence.remove(scProductVersion);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return scProductVersionPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return scProductVersionPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	public SCProductVersion getSCProductVersion(long productVersionId)
		throws PortalException, SystemException {
		return scProductVersionPersistence.findByPrimaryKey(productVersionId);
	}

	public List<SCProductVersion> getSCProductVersions(int start, int end)
		throws SystemException {
		return scProductVersionPersistence.findAll(start, end);
	}

	public int getSCProductVersionsCount() throws SystemException {
		return scProductVersionPersistence.countAll();
	}

	public SCProductVersion updateSCProductVersion(
		SCProductVersion scProductVersion) throws SystemException {
		scProductVersion.setNew(false);

		return scProductVersionPersistence.update(scProductVersion, true);
	}

	public SCProductVersion updateSCProductVersion(
		SCProductVersion scProductVersion, boolean merge)
		throws SystemException {
		scProductVersion.setNew(false);

		return scProductVersionPersistence.update(scProductVersion, merge);
	}

	public SCLicenseLocalService getSCLicenseLocalService() {
		return scLicenseLocalService;
	}

	public void setSCLicenseLocalService(
		SCLicenseLocalService scLicenseLocalService) {
		this.scLicenseLocalService = scLicenseLocalService;
	}

	public SCLicenseService getSCLicenseService() {
		return scLicenseService;
	}

	public void setSCLicenseService(SCLicenseService scLicenseService) {
		this.scLicenseService = scLicenseService;
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

	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourcePersistence getResourcePersistence() {
		return resourcePersistence;
	}

	public void setResourcePersistence(ResourcePersistence resourcePersistence) {
		this.resourcePersistence = resourcePersistence;
	}

	public ResourceFinder getResourceFinder() {
		return resourceFinder;
	}

	public void setResourceFinder(ResourceFinder resourceFinder) {
		this.resourceFinder = resourceFinder;
	}

	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public UserFinder getUserFinder() {
		return userFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	protected void runSQL(String sql) throws SystemException {
		try {
			DB db = DBFactoryUtil.getDB();

			db.runSQL(sql);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.SCLicenseLocalService")
	protected SCLicenseLocalService scLicenseLocalService;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.SCLicenseService")
	protected SCLicenseService scLicenseService;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCLicensePersistence")
	protected SCLicensePersistence scLicensePersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionLocalService")
	protected SCFrameworkVersionLocalService scFrameworkVersionLocalService;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionService")
	protected SCFrameworkVersionService scFrameworkVersionService;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionPersistence")
	protected SCFrameworkVersionPersistence scFrameworkVersionPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService")
	protected SCProductEntryLocalService scProductEntryLocalService;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.SCProductEntryService")
	protected SCProductEntryService scProductEntryService;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryPersistence")
	protected SCProductEntryPersistence scProductEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalService")
	protected SCProductScreenshotLocalService scProductScreenshotLocalService;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCProductScreenshotPersistence")
	protected SCProductScreenshotPersistence scProductScreenshotPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalService")
	protected SCProductVersionLocalService scProductVersionLocalService;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.SCProductVersionService")
	protected SCProductVersionService scProductVersionService;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCProductVersionPersistence")
	protected SCProductVersionPersistence scProductVersionPersistence;
	@BeanReference(name = "com.liferay.counter.service.CounterLocalService")
	protected CounterLocalService counterLocalService;
	@BeanReference(name = "com.liferay.counter.service.CounterService")
	protected CounterService counterService;
	@BeanReference(name = "com.liferay.portal.service.ResourceLocalService")
	protected ResourceLocalService resourceLocalService;
	@BeanReference(name = "com.liferay.portal.service.ResourceService")
	protected ResourceService resourceService;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceFinder")
	protected ResourceFinder resourceFinder;
	@BeanReference(name = "com.liferay.portal.service.UserLocalService")
	protected UserLocalService userLocalService;
	@BeanReference(name = "com.liferay.portal.service.UserService")
	protected UserService userService;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserFinder")
	protected UserFinder userFinder;
}