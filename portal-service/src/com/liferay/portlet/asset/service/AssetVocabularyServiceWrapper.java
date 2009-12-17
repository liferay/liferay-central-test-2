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

package com.liferay.portlet.asset.service;


/**
 * <a href="AssetVocabularyServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AssetVocabularyService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetVocabularyService
 * @generated
 */
public class AssetVocabularyServiceWrapper implements AssetVocabularyService {
	public AssetVocabularyServiceWrapper(
		AssetVocabularyService assetVocabularyService) {
		_assetVocabularyService = assetVocabularyService;
	}

	public com.liferay.portlet.asset.model.AssetVocabulary addVocabulary(
		java.lang.String name,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetVocabularyService.addVocabulary(name, serviceContext);
	}

	public void deleteVocabulary(long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetVocabularyService.deleteVocabulary(vocabularyId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getCompanyVocabularies(
		long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetVocabularyService.getCompanyVocabularies(companyId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupsVocabularies(
		long[] groupIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetVocabularyService.getGroupsVocabularies(groupIds);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetVocabularyService.getGroupVocabularies(groupId);
	}

	public com.liferay.portlet.asset.model.AssetVocabulary getVocabulary(
		long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetVocabularyService.getVocabulary(vocabularyId);
	}

	public com.liferay.portlet.asset.model.AssetVocabulary updateVocabulary(
		long vocabularyId, java.lang.String name,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetVocabularyService.updateVocabulary(vocabularyId, name,
			serviceContext);
	}

	public AssetVocabularyService getWrappedAssetVocabularyService() {
		return _assetVocabularyService;
	}

	private AssetVocabularyService _assetVocabularyService;
}