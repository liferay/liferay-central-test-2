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

package com.liferay.portlet.asset.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.service.AssetCategoryLocalService;
import com.liferay.portlet.asset.service.AssetCategoryPropertyLocalService;
import com.liferay.portlet.asset.service.AssetCategoryPropertyService;
import com.liferay.portlet.asset.service.AssetCategoryService;
import com.liferay.portlet.asset.service.AssetCategoryVocabularyLocalService;
import com.liferay.portlet.asset.service.AssetCategoryVocabularyService;
import com.liferay.portlet.asset.service.persistence.AssetCategoryFinder;
import com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyFinder;
import com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyPersistence;
import com.liferay.portlet.asset.service.persistence.AssetCategoryVocabularyPersistence;

import java.util.List;

/**
 * <a href="AssetCategoryPropertyLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class AssetCategoryPropertyLocalServiceBaseImpl
	implements AssetCategoryPropertyLocalService {
	public AssetCategoryProperty addAssetCategoryProperty(
		AssetCategoryProperty assetCategoryProperty) throws SystemException {
		assetCategoryProperty.setNew(true);

		return assetCategoryPropertyPersistence.update(assetCategoryProperty,
			false);
	}

	public AssetCategoryProperty createAssetCategoryProperty(
		long categoryPropertyId) {
		return assetCategoryPropertyPersistence.create(categoryPropertyId);
	}

	public void deleteAssetCategoryProperty(long categoryPropertyId)
		throws PortalException, SystemException {
		assetCategoryPropertyPersistence.remove(categoryPropertyId);
	}

	public void deleteAssetCategoryProperty(
		AssetCategoryProperty assetCategoryProperty) throws SystemException {
		assetCategoryPropertyPersistence.remove(assetCategoryProperty);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return assetCategoryPropertyPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return assetCategoryPropertyPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	public AssetCategoryProperty getAssetCategoryProperty(
		long categoryPropertyId) throws PortalException, SystemException {
		return assetCategoryPropertyPersistence.findByPrimaryKey(categoryPropertyId);
	}

	public List<AssetCategoryProperty> getAssetCategoryProperties(int start,
		int end) throws SystemException {
		return assetCategoryPropertyPersistence.findAll(start, end);
	}

	public int getAssetCategoryPropertiesCount() throws SystemException {
		return assetCategoryPropertyPersistence.countAll();
	}

	public AssetCategoryProperty updateAssetCategoryProperty(
		AssetCategoryProperty assetCategoryProperty) throws SystemException {
		assetCategoryProperty.setNew(false);

		return assetCategoryPropertyPersistence.update(assetCategoryProperty,
			true);
	}

	public AssetCategoryProperty updateAssetCategoryProperty(
		AssetCategoryProperty assetCategoryProperty, boolean merge)
		throws SystemException {
		assetCategoryProperty.setNew(false);

		return assetCategoryPropertyPersistence.update(assetCategoryProperty,
			merge);
	}

	public AssetCategoryLocalService getAssetCategoryLocalService() {
		return assetCategoryLocalService;
	}

	public void setAssetCategoryLocalService(
		AssetCategoryLocalService assetCategoryLocalService) {
		this.assetCategoryLocalService = assetCategoryLocalService;
	}

	public AssetCategoryService getAssetCategoryService() {
		return assetCategoryService;
	}

	public void setAssetCategoryService(
		AssetCategoryService assetCategoryService) {
		this.assetCategoryService = assetCategoryService;
	}

	public AssetCategoryPersistence getAssetCategoryPersistence() {
		return assetCategoryPersistence;
	}

	public void setAssetCategoryPersistence(
		AssetCategoryPersistence assetCategoryPersistence) {
		this.assetCategoryPersistence = assetCategoryPersistence;
	}

	public AssetCategoryFinder getAssetCategoryFinder() {
		return assetCategoryFinder;
	}

	public void setAssetCategoryFinder(AssetCategoryFinder assetCategoryFinder) {
		this.assetCategoryFinder = assetCategoryFinder;
	}

	public AssetCategoryPropertyLocalService getAssetCategoryPropertyLocalService() {
		return assetCategoryPropertyLocalService;
	}

	public void setAssetCategoryPropertyLocalService(
		AssetCategoryPropertyLocalService assetCategoryPropertyLocalService) {
		this.assetCategoryPropertyLocalService = assetCategoryPropertyLocalService;
	}

	public AssetCategoryPropertyService getAssetCategoryPropertyService() {
		return assetCategoryPropertyService;
	}

	public void setAssetCategoryPropertyService(
		AssetCategoryPropertyService assetCategoryPropertyService) {
		this.assetCategoryPropertyService = assetCategoryPropertyService;
	}

	public AssetCategoryPropertyPersistence getAssetCategoryPropertyPersistence() {
		return assetCategoryPropertyPersistence;
	}

	public void setAssetCategoryPropertyPersistence(
		AssetCategoryPropertyPersistence assetCategoryPropertyPersistence) {
		this.assetCategoryPropertyPersistence = assetCategoryPropertyPersistence;
	}

	public AssetCategoryPropertyFinder getAssetCategoryPropertyFinder() {
		return assetCategoryPropertyFinder;
	}

	public void setAssetCategoryPropertyFinder(
		AssetCategoryPropertyFinder assetCategoryPropertyFinder) {
		this.assetCategoryPropertyFinder = assetCategoryPropertyFinder;
	}

	public AssetCategoryVocabularyLocalService getAssetCategoryVocabularyLocalService() {
		return assetCategoryVocabularyLocalService;
	}

	public void setAssetCategoryVocabularyLocalService(
		AssetCategoryVocabularyLocalService assetCategoryVocabularyLocalService) {
		this.assetCategoryVocabularyLocalService = assetCategoryVocabularyLocalService;
	}

	public AssetCategoryVocabularyService getAssetCategoryVocabularyService() {
		return assetCategoryVocabularyService;
	}

	public void setAssetCategoryVocabularyService(
		AssetCategoryVocabularyService assetCategoryVocabularyService) {
		this.assetCategoryVocabularyService = assetCategoryVocabularyService;
	}

	public AssetCategoryVocabularyPersistence getAssetCategoryVocabularyPersistence() {
		return assetCategoryVocabularyPersistence;
	}

	public void setAssetCategoryVocabularyPersistence(
		AssetCategoryVocabularyPersistence assetCategoryVocabularyPersistence) {
		this.assetCategoryVocabularyPersistence = assetCategoryVocabularyPersistence;
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
			PortalUtil.runSQL(sql);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(name = "com.liferay.portlet.asset.service.AssetCategoryLocalService.impl")
	protected AssetCategoryLocalService assetCategoryLocalService;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetCategoryService.impl")
	protected AssetCategoryService assetCategoryService;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence.impl")
	protected AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryFinder.impl")
	protected AssetCategoryFinder assetCategoryFinder;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetCategoryPropertyLocalService.impl")
	protected AssetCategoryPropertyLocalService assetCategoryPropertyLocalService;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetCategoryPropertyService.impl")
	protected AssetCategoryPropertyService assetCategoryPropertyService;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyPersistence.impl")
	protected AssetCategoryPropertyPersistence assetCategoryPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyFinder.impl")
	protected AssetCategoryPropertyFinder assetCategoryPropertyFinder;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetCategoryVocabularyLocalService.impl")
	protected AssetCategoryVocabularyLocalService assetCategoryVocabularyLocalService;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetCategoryVocabularyService.impl")
	protected AssetCategoryVocabularyService assetCategoryVocabularyService;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryVocabularyPersistence.impl")
	protected AssetCategoryVocabularyPersistence assetCategoryVocabularyPersistence;
	@BeanReference(name = "com.liferay.counter.service.CounterLocalService.impl")
	protected CounterLocalService counterLocalService;
	@BeanReference(name = "com.liferay.counter.service.CounterService.impl")
	protected CounterService counterService;
	@BeanReference(name = "com.liferay.portal.service.UserLocalService.impl")
	protected UserLocalService userLocalService;
	@BeanReference(name = "com.liferay.portal.service.UserService.impl")
	protected UserService userService;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserFinder.impl")
	protected UserFinder userFinder;
}