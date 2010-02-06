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

package com.liferay.portlet.asset.service;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.Isolation;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;

/**
 * <a href="AssetCategoryPropertyLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * {@link
 * com.liferay.portlet.asset.service.impl.AssetCategoryPropertyLocalServiceImpl}}.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetCategoryPropertyLocalServiceUtil
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface AssetCategoryPropertyLocalService {
	public com.liferay.portlet.asset.model.AssetCategoryProperty addAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetCategoryProperty createAssetCategoryProperty(
		long categoryPropertyId);

	public void deleteAssetCategoryProperty(long categoryPropertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.asset.model.AssetCategoryProperty getAssetCategoryProperty(
		long categoryPropertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getAssetCategoryProperties(
		int start, int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetCategoryPropertiesCount()
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetCategoryProperty updateAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetCategoryProperty updateAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetCategoryProperty addCategoryProperty(
		long userId, long categoryId, java.lang.String key,
		java.lang.String value)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteCategoryProperties(long entryId)
		throws com.liferay.portal.SystemException;

	public void deleteCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty categoryProperty)
		throws com.liferay.portal.SystemException;

	public void deleteCategoryProperty(long categoryPropertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryProperties()
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryProperties(
		long entryId) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.asset.model.AssetCategoryProperty getCategoryProperty(
		long categoryPropertyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.asset.model.AssetCategoryProperty getCategoryProperty(
		long categoryId, java.lang.String key)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryPropertyValues(
		long groupId, java.lang.String key)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetCategoryProperty updateCategoryProperty(
		long categoryPropertyId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;
}