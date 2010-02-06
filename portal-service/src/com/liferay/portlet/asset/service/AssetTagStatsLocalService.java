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
 * <a href="AssetTagStatsLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * {@link
 * com.liferay.portlet.asset.service.impl.AssetTagStatsLocalServiceImpl}}.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagStatsLocalServiceUtil
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface AssetTagStatsLocalService {
	public com.liferay.portlet.asset.model.AssetTagStats addAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetTagStats createAssetTagStats(
		long tagStatsId);

	public void deleteAssetTagStats(long tagStatsId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.asset.model.AssetTagStats getAssetTagStats(
		long tagStatsId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> getAssetTagStatses(
		int start, int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetTagStatsesCount()
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetTagStats updateAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetTagStats updateAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetTagStats addTagStats(
		long tagId, long classNameId) throws com.liferay.portal.SystemException;

	public void deleteTagStatsByClassNameId(long classNameId)
		throws com.liferay.portal.SystemException;

	public void deleteTagStatsByTagId(long tagId)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.asset.model.AssetTagStats getTagStats(
		long tagId, long classNameId) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetTagStats updateTagStats(
		long tagId, long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;
}