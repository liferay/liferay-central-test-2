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

package com.liferay.portlet.categories.service;


/**
 * <a href="CategoriesVocabularyLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.categories.service.CategoriesVocabularyLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.categories.service.CategoriesVocabularyLocalService
 *
 */
public class CategoriesVocabularyLocalServiceUtil {
	public static com.liferay.portlet.categories.model.CategoriesVocabulary addCategoriesVocabulary(
		com.liferay.portlet.categories.model.CategoriesVocabulary categoriesVocabulary)
		throws com.liferay.portal.SystemException {
		return getService().addCategoriesVocabulary(categoriesVocabulary);
	}

	public static com.liferay.portlet.categories.model.CategoriesVocabulary createCategoriesVocabulary(
		long vocabularyId) {
		return getService().createCategoriesVocabulary(vocabularyId);
	}

	public static void deleteCategoriesVocabulary(long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteCategoriesVocabulary(vocabularyId);
	}

	public static void deleteCategoriesVocabulary(
		com.liferay.portlet.categories.model.CategoriesVocabulary categoriesVocabulary)
		throws com.liferay.portal.SystemException {
		getService().deleteCategoriesVocabulary(categoriesVocabulary);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.categories.model.CategoriesVocabulary getCategoriesVocabulary(
		long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getCategoriesVocabulary(vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesVocabulary> getCategoriesVocabularies(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getCategoriesVocabularies(start, end);
	}

	public static int getCategoriesVocabulariesCount()
		throws com.liferay.portal.SystemException {
		return getService().getCategoriesVocabulariesCount();
	}

	public static com.liferay.portlet.categories.model.CategoriesVocabulary updateCategoriesVocabulary(
		com.liferay.portlet.categories.model.CategoriesVocabulary categoriesVocabulary)
		throws com.liferay.portal.SystemException {
		return getService().updateCategoriesVocabulary(categoriesVocabulary);
	}

	public static com.liferay.portlet.categories.model.CategoriesVocabulary updateCategoriesVocabulary(
		com.liferay.portlet.categories.model.CategoriesVocabulary categoriesVocabulary,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService()
				   .updateCategoriesVocabulary(categoriesVocabulary, merge);
	}

	public static com.liferay.portlet.categories.model.CategoriesVocabulary addVocabulary(
		long userId, java.lang.String name,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addVocabulary(userId, name, serviceContext);
	}

	public static void addVocabularyResources(
		com.liferay.portlet.categories.model.CategoriesVocabulary vocabulary,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addVocabularyResources(vocabulary, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addVocabularyResources(
		com.liferay.portlet.categories.model.CategoriesVocabulary vocabulary,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addVocabularyResources(vocabulary, communityPermissions,
			guestPermissions);
	}

	public static void deleteVocabulary(long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteVocabulary(vocabularyId);
	}

	public static void deleteVocabulary(
		com.liferay.portlet.categories.model.CategoriesVocabulary vocabulary)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteVocabulary(vocabulary);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesVocabulary> getCompanyVocabularies(
		long companyId) throws com.liferay.portal.SystemException {
		return getService().getCompanyVocabularies(companyId);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesVocabulary> getGroupVocabularies(
		long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getGroupVocabularies(groupId);
	}

	public static com.liferay.portlet.categories.model.CategoriesVocabulary getGroupVocabulary(
		long groupId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getGroupVocabulary(groupId, name);
	}

	public static com.liferay.portlet.categories.model.CategoriesVocabulary getVocabulary(
		long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getVocabulary(vocabularyId);
	}

	public static com.liferay.portlet.categories.model.CategoriesVocabulary updateVocabulary(
		long vocabularyId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateVocabulary(vocabularyId, name);
	}

	public static CategoriesVocabularyLocalService getService() {
		if (_service == null) {
			throw new RuntimeException(
				"CategoriesVocabularyLocalService is not set");
		}

		return _service;
	}

	public void setService(CategoriesVocabularyLocalService service) {
		_service = service;
	}

	private static CategoriesVocabularyLocalService _service;
}