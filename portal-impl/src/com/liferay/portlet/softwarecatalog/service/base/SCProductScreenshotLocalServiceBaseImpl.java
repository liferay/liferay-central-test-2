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

package com.liferay.portlet.softwarecatalog.service.base;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.service.ImageLocalService;
import com.liferay.portal.service.persistence.ImagePersistence;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;
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

public abstract class SCProductScreenshotLocalServiceBaseImpl
	implements SCProductScreenshotLocalService {
	public SCProductScreenshot addSCProductScreenshot(
		SCProductScreenshot scProductScreenshot) throws SystemException {
		scProductScreenshot.setNew(true);

		return scProductScreenshotPersistence.update(scProductScreenshot, false);
	}

	public SCProductScreenshot createSCProductScreenshot(
		long productScreenshotId) {
		return scProductScreenshotPersistence.create(productScreenshotId);
	}

	public void deleteSCProductScreenshot(long productScreenshotId)
		throws PortalException, SystemException {
		scProductScreenshotPersistence.remove(productScreenshotId);
	}

	public void deleteSCProductScreenshot(
		SCProductScreenshot scProductScreenshot) throws SystemException {
		scProductScreenshotPersistence.remove(scProductScreenshot);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return scProductScreenshotPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return scProductScreenshotPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	public SCProductScreenshot getSCProductScreenshot(long productScreenshotId)
		throws PortalException, SystemException {
		return scProductScreenshotPersistence.findByPrimaryKey(productScreenshotId);
	}

	public List<SCProductScreenshot> getSCProductScreenshots(int start, int end)
		throws SystemException {
		return scProductScreenshotPersistence.findAll(start, end);
	}

	public int getSCProductScreenshotsCount() throws SystemException {
		return scProductScreenshotPersistence.countAll();
	}

	public SCProductScreenshot updateSCProductScreenshot(
		SCProductScreenshot scProductScreenshot) throws SystemException {
		scProductScreenshot.setNew(false);

		return scProductScreenshotPersistence.update(scProductScreenshot, true);
	}

	public SCProductScreenshot updateSCProductScreenshot(
		SCProductScreenshot scProductScreenshot, boolean merge)
		throws SystemException {
		scProductScreenshot.setNew(false);

		return scProductScreenshotPersistence.update(scProductScreenshot, merge);
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

	public ImageLocalService getImageLocalService() {
		return imageLocalService;
	}

	public void setImageLocalService(ImageLocalService imageLocalService) {
		this.imageLocalService = imageLocalService;
	}

	public ImagePersistence getImagePersistence() {
		return imagePersistence;
	}

	public void setImagePersistence(ImagePersistence imagePersistence) {
		this.imagePersistence = imagePersistence;
	}

	protected void runSQL(String sql) throws SystemException {
		try {
			PortalUtil.runSQL(sql);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.SCLicenseLocalService.impl")
	protected SCLicenseLocalService scLicenseLocalService;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.SCLicenseService.impl")
	protected SCLicenseService scLicenseService;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCLicensePersistence.impl")
	protected SCLicensePersistence scLicensePersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionLocalService.impl")
	protected SCFrameworkVersionLocalService scFrameworkVersionLocalService;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionService.impl")
	protected SCFrameworkVersionService scFrameworkVersionService;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionPersistence.impl")
	protected SCFrameworkVersionPersistence scFrameworkVersionPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService.impl")
	protected SCProductEntryLocalService scProductEntryLocalService;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.SCProductEntryService.impl")
	protected SCProductEntryService scProductEntryService;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryPersistence.impl")
	protected SCProductEntryPersistence scProductEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalService.impl")
	protected SCProductScreenshotLocalService scProductScreenshotLocalService;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCProductScreenshotPersistence.impl")
	protected SCProductScreenshotPersistence scProductScreenshotPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalService.impl")
	protected SCProductVersionLocalService scProductVersionLocalService;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.SCProductVersionService.impl")
	protected SCProductVersionService scProductVersionService;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCProductVersionPersistence.impl")
	protected SCProductVersionPersistence scProductVersionPersistence;
	@BeanReference(name = "com.liferay.portal.service.ImageLocalService.impl")
	protected ImageLocalService imageLocalService;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence.impl")
	protected ImagePersistence imagePersistence;
}