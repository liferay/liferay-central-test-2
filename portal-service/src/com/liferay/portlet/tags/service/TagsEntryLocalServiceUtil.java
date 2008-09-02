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

package com.liferay.portlet.tags.service;


/**
 * <a href="TagsEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.tags.service.TagsEntryLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.tags.service.TagsEntryLocalService
 *
 */
public class TagsEntryLocalServiceUtil {
	public static com.liferay.portlet.tags.model.TagsEntry addTagsEntry(
		com.liferay.portlet.tags.model.TagsEntry tagsEntry)
		throws com.liferay.portal.SystemException {
		return _service.addTagsEntry(tagsEntry);
	}

	public static void deleteTagsEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteTagsEntry(entryId);
	}

	public static void deleteTagsEntry(
		com.liferay.portlet.tags.model.TagsEntry tagsEntry)
		throws com.liferay.portal.SystemException {
		_service.deleteTagsEntry(tagsEntry);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _service.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _service.dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.tags.model.TagsEntry getTagsEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getTagsEntry(entryId);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> getTagsEntries(
		int start, int end) throws com.liferay.portal.SystemException {
		return _service.getTagsEntries(start, end);
	}

	public static int getTagsEntriesCount()
		throws com.liferay.portal.SystemException {
		return _service.getTagsEntriesCount();
	}

	public static com.liferay.portlet.tags.model.TagsEntry updateTagsEntry(
		com.liferay.portlet.tags.model.TagsEntry tagsEntry)
		throws com.liferay.portal.SystemException {
		return _service.updateTagsEntry(tagsEntry);
	}

	public static com.liferay.portlet.tags.model.TagsEntry addEntry(
		long userId, long groupId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addEntry(userId, groupId, name);
	}

	public static com.liferay.portlet.tags.model.TagsEntry addEntry(
		long userId, long groupId, java.lang.String name,
		java.lang.String[] properties)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addEntry(userId, groupId, name, properties);
	}

	public static com.liferay.portlet.tags.model.TagsEntry addEntry(
		long userId, long groupId, java.lang.String name,
		java.lang.String vocabularyName, java.lang.String[] properties)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addEntry(userId, groupId, name, vocabularyName,
			properties);
	}

	public static com.liferay.portlet.tags.model.TagsEntry addEntry(
		long userId, long groupId, java.lang.String parentEntryName,
		java.lang.String name, java.lang.String vocabularyName,
		java.lang.String[] properties)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addEntry(userId, groupId, parentEntryName, name,
			vocabularyName, properties);
	}

	public static void checkEntries(long userId, long groupId,
		java.lang.String[] names)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.checkEntries(userId, groupId, names);
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteEntry(entryId);
	}

	public static void deleteEntry(
		com.liferay.portlet.tags.model.TagsEntry entry)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteEntry(entry);
	}

	public static void deleteVocabularyEntries(long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteVocabularyEntries(vocabularyId);
	}

	public static boolean hasEntry(long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return _service.hasEntry(groupId, name);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> getAssetEntries(
		long assetId) throws com.liferay.portal.SystemException {
		return _service.getAssetEntries(assetId);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> getAssetEntries(
		long assetId, boolean folksonomy)
		throws com.liferay.portal.SystemException {
		return _service.getAssetEntries(assetId, folksonomy);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> getEntries()
		throws com.liferay.portal.SystemException {
		return _service.getEntries();
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> getEntries(
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return _service.getEntries(className, classPK);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> getEntries(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return _service.getEntries(classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> getEntries(
		java.lang.String className, long classPK, boolean folksonomy)
		throws com.liferay.portal.SystemException {
		return _service.getEntries(className, classPK, folksonomy);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> getEntries(
		long groupId, long classNameId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return _service.getEntries(groupId, classNameId, name);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> getEntries(
		long groupId, long classNameId, java.lang.String name, int start,
		int end) throws com.liferay.portal.SystemException {
		return _service.getEntries(groupId, classNameId, name, start, end);
	}

	public static int getEntriesSize(long groupId, long classNameId,
		java.lang.String name) throws com.liferay.portal.SystemException {
		return _service.getEntriesSize(groupId, classNameId, name);
	}

	public static com.liferay.portlet.tags.model.TagsEntry getEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getEntry(entryId);
	}

	public static com.liferay.portlet.tags.model.TagsEntry getEntry(
		long groupId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getEntry(groupId, name);
	}

	public static long[] getEntryIds(long groupId, java.lang.String[] names)
		throws com.liferay.portal.SystemException {
		return _service.getEntryIds(groupId, names);
	}

	public static java.lang.String[] getEntryNames()
		throws com.liferay.portal.SystemException {
		return _service.getEntryNames();
	}

	public static java.lang.String[] getEntryNames(java.lang.String className,
		long classPK) throws com.liferay.portal.SystemException {
		return _service.getEntryNames(className, classPK);
	}

	public static java.lang.String[] getEntryNames(long classNameId,
		long classPK) throws com.liferay.portal.SystemException {
		return _service.getEntryNames(classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> getGroupVocabularyEntries(
		long groupId, java.lang.String vocabularyName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getGroupVocabularyEntries(groupId, vocabularyName);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> getGroupVocabularyEntries(
		long groupId, java.lang.String parentEntryName,
		java.lang.String vocabularyName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getGroupVocabularyEntries(groupId, parentEntryName,
			vocabularyName);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> getGroupVocabularyRootEntries(
		long groupId, java.lang.String vocabularyName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getGroupVocabularyRootEntries(groupId, vocabularyName);
	}

	public static void mergeEntries(long fromEntryId, long toEntryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.mergeEntries(fromEntryId, toEntryId);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> search(
		long groupId, java.lang.String name, java.lang.String[] properties)
		throws com.liferay.portal.SystemException {
		return _service.search(groupId, name, properties);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> search(
		long groupId, java.lang.String name, java.lang.String[] properties,
		int start, int end) throws com.liferay.portal.SystemException {
		return _service.search(groupId, name, properties, start, end);
	}

	public static com.liferay.portal.kernel.json.JSONArray searchAutocomplete(
		long groupId, java.lang.String name, java.lang.String[] properties,
		int start, int end) throws com.liferay.portal.SystemException {
		return _service.searchAutocomplete(groupId, name, properties, start, end);
	}

	public static int searchCount(long groupId, java.lang.String name,
		java.lang.String[] properties)
		throws com.liferay.portal.SystemException {
		return _service.searchCount(groupId, name, properties);
	}

	public static com.liferay.portlet.tags.model.TagsEntry updateEntry(
		long entryId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateEntry(entryId, name);
	}

	public static com.liferay.portlet.tags.model.TagsEntry updateEntry(
		long entryId, java.lang.String parentEntryName, java.lang.String name,
		java.lang.String vocabularyName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateEntry(entryId, parentEntryName, name,
			vocabularyName);
	}

	public static com.liferay.portlet.tags.model.TagsEntry updateEntry(
		long userId, long entryId, java.lang.String name,
		java.lang.String[] properties)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateEntry(userId, entryId, name, properties);
	}

	public static com.liferay.portlet.tags.model.TagsEntry updateEntry(
		long userId, long entryId, java.lang.String parentEntryName,
		java.lang.String name, java.lang.String vocabularyName,
		java.lang.String[] properties)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateEntry(userId, entryId, parentEntryName, name,
			vocabularyName, properties);
	}

	public static TagsEntryLocalService getService() {
		return _service;
	}

	public void setService(TagsEntryLocalService service) {
		_service = service;
	}

	private static TagsEntryLocalService _service;
}