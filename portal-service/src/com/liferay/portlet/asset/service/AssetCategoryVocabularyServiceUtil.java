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
 * <a href="AssetCategoryVocabularyServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.asset.service.AssetCategoryVocabularyService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.asset.service.AssetCategoryVocabularyService
 *
 */
public class AssetCategoryVocabularyServiceUtil {
	public static com.liferay.portlet.asset.model.AssetCategoryVocabulary addCategoryVocabulary(
		java.lang.String name,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addCategoryVocabulary(name, serviceContext);
	}

	public static void deleteCategoryVocabulary(long categoryVocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteCategoryVocabulary(categoryVocabularyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryVocabulary> getCompanyCategoryVocabularies(
		long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getCompanyCategoryVocabularies(companyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryVocabulary> getGroupCategoryVocabularies(
		long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getGroupCategoryVocabularies(groupId);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryVocabulary getVocabulary(
		long categoryVocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getVocabulary(categoryVocabularyId);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryVocabulary updateCategoryVocabulary(
		long categoryVocabularyId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateCategoryVocabulary(categoryVocabularyId, name);
	}

	public static AssetCategoryVocabularyService getService() {
		if (_service == null) {
			throw new RuntimeException(
				"AssetCategoryVocabularyService is not set");
		}

		return _service;
	}

	public void setService(AssetCategoryVocabularyService service) {
		_service = service;
	}

	private static AssetCategoryVocabularyService _service;
}