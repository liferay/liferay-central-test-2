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
 * <a href="CategoriesEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.categories.service.CategoriesEntryLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.categories.service.CategoriesEntryLocalService
 *
 */
public class CategoriesEntryLocalServiceUtil {
	public static com.liferay.portlet.categories.model.CategoriesEntry addCategoriesEntry(
		com.liferay.portlet.categories.model.CategoriesEntry categoriesEntry)
		throws com.liferay.portal.SystemException {
		return getService().addCategoriesEntry(categoriesEntry);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry createCategoriesEntry(
		long entryId) {
		return getService().createCategoriesEntry(entryId);
	}

	public static void deleteCategoriesEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteCategoriesEntry(entryId);
	}

	public static void deleteCategoriesEntry(
		com.liferay.portlet.categories.model.CategoriesEntry categoriesEntry)
		throws com.liferay.portal.SystemException {
		getService().deleteCategoriesEntry(categoriesEntry);
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

	public static com.liferay.portlet.categories.model.CategoriesEntry getCategoriesEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getCategoriesEntry(entryId);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> getCategoriesEntries(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getCategoriesEntries(start, end);
	}

	public static int getCategoriesEntriesCount()
		throws com.liferay.portal.SystemException {
		return getService().getCategoriesEntriesCount();
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry updateCategoriesEntry(
		com.liferay.portlet.categories.model.CategoriesEntry categoriesEntry)
		throws com.liferay.portal.SystemException {
		return getService().updateCategoriesEntry(categoriesEntry);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry updateCategoriesEntry(
		com.liferay.portlet.categories.model.CategoriesEntry categoriesEntry,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateCategoriesEntry(categoriesEntry, merge);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry addEntry(
		long userId, long vocabularyId, long parentEntryId,
		java.lang.String name, java.lang.String[] properties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addEntry(userId, vocabularyId, parentEntryId, name,
			properties, serviceContext);
	}

	public static void addEntryResources(
		com.liferay.portlet.categories.model.CategoriesEntry entry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addEntryResources(entry, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addEntryResources(
		com.liferay.portlet.categories.model.CategoriesEntry entry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addEntryResources(entry, communityPermissions, guestPermissions);
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteEntry(entryId);
	}

	public static void deleteEntry(
		com.liferay.portlet.categories.model.CategoriesEntry entry)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteEntry(entry);
	}

	public static void deleteVocabularyEntries(long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteVocabularyEntries(vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> getAssetEntries(
		long assetId) throws com.liferay.portal.SystemException {
		return getService().getAssetEntries(assetId);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> getEntries()
		throws com.liferay.portal.SystemException {
		return getService().getEntries();
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> getEntries(
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return getService().getEntries(className, classPK);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> getEntries(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getService().getEntries(classNameId, classPK);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry getEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getEntry(entryId);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> getChildEntries(
		long parentEntryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getChildEntries(parentEntryId);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> getRootVocabularyEntries(
		long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getRootVocabularyEntries(vocabularyId);
	}

	public static void mergeEntries(long fromEntryId, long toEntryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().mergeEntries(fromEntryId, toEntryId);
	}

	public static com.liferay.portal.kernel.json.JSONArray search(
		long groupId, java.lang.String name, java.lang.String[] properties,
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().search(groupId, name, properties, start, end);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry updateEntry(
		long userId, long entryId, long vocabularyId, long parentEntryId,
		java.lang.String name, java.lang.String[] properties)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateEntry(userId, entryId, vocabularyId, parentEntryId,
			name, properties);
	}

	public static CategoriesEntryLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("CategoriesEntryLocalService is not set");
		}

		return _service;
	}

	public void setService(CategoriesEntryLocalService service) {
		_service = service;
	}

	private static CategoriesEntryLocalService _service;
}