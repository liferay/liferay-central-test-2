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

package com.liferay.portlet.tags.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.tags.model.TagsEntry;
import com.liferay.portlet.tags.model.TagsEntryConstants;
import com.liferay.portlet.tags.model.TagsVocabulary;
import com.liferay.portlet.tags.service.base.TagsEntryServiceBaseImpl;
import com.liferay.portlet.tags.service.permission.TagsEntryPermission;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="TagsEntryServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Alvaro del Castillo
 * @author Eduardo Lundgren
 * @author Bruno Farache
 *
 */
public class TagsEntryServiceImpl extends TagsEntryServiceBaseImpl {

	public TagsEntry addEntry(
			String parentEntryName, String name, String vocabularyName,
			String[] properties, ServiceContext serviceContext)
		throws PortalException, SystemException {

		long parentEntryId = TagsEntryConstants.DEFAULT_PARENT_ENTRY_ID;
		long groupId = serviceContext.getScopeGroupId();

		if (Validator.isNotNull(parentEntryName)) {
			TagsVocabulary vocabulary = tagsVocabularyPersistence.findByG_N(
				groupId, vocabularyName);

			TagsEntry parentEntry = tagsEntryLocalService.getEntry(
				groupId, parentEntryName, vocabulary.isFolksonomy());

			parentEntryId = parentEntry.getEntryId();
		}

		TagsEntryPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			parentEntryId, ActionKeys.ADD_ENTRY);

		return tagsEntryLocalService.addEntry(
			getUserId(), parentEntryName, name, vocabularyName, properties,
			serviceContext);
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		TagsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		tagsEntryLocalService.deleteEntry(entryId);
	}

	public List<TagsEntry> getEntries(String className, long classPK)
		throws PortalException, SystemException {

		return getEntries(tagsEntryLocalService.getEntries(className, classPK));
	}

	public List<TagsEntry> getEntries(
			long groupId, long classNameId, String name)
		throws PortalException, SystemException {

		return getEntries(
			tagsEntryLocalService.getEntries(groupId, classNameId, name));
	}

	public TagsEntry getEntry(long entryId)
		throws PortalException, SystemException {

		TagsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.VIEW);

		return tagsEntryLocalService.getEntry(entryId);
	}

	public List<TagsEntry> getGroupVocabularyEntries(
			long groupId, String vocabularyName)
		throws PortalException, SystemException {

		return getEntries(
			tagsEntryLocalService.getGroupVocabularyEntries(
				groupId, vocabularyName));
	}

	public List<TagsEntry> getGroupVocabularyEntries(
			long groupId, String parentEntryName, String vocabularyName)
		throws PortalException, SystemException {

		return getEntries(
			tagsEntryLocalService.getGroupVocabularyEntries(
				groupId, parentEntryName, vocabularyName));
	}

	public List<TagsEntry> getGroupVocabularyRootEntries(
			long groupId, String vocabularyName)
		throws PortalException, SystemException {

		return getEntries(
			tagsEntryLocalService.getGroupVocabularyRootEntries(
				groupId, vocabularyName));
	}

	public void mergeEntries(long fromEntryId, long toEntryId)
		throws PortalException, SystemException {

		TagsEntryPermission.check(
			getPermissionChecker(), fromEntryId, ActionKeys.VIEW);

		TagsEntryPermission.check(
			getPermissionChecker(), toEntryId, ActionKeys.UPDATE);

		tagsEntryLocalService.mergeEntries(fromEntryId, toEntryId);
	}

	public JSONArray search(
			long groupId, String name, String[] properties, int start, int end)
		throws SystemException {

		return tagsEntryLocalService.search(
			groupId, name, properties, start, end);
	}

	public TagsEntry updateEntry(
			long entryId, String parentEntryName, String name,
			String vocabularyName, String[] properties)
		throws PortalException, SystemException {

		TagsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.UPDATE);

		return tagsEntryLocalService.updateEntry(
			getUserId(), entryId, parentEntryName, name, vocabularyName,
			properties);
	}

	protected List<TagsEntry> getEntries(List<TagsEntry> entries)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		entries = ListUtil.copy(entries);

		Iterator<TagsEntry> itr = entries.iterator();

		while (itr.hasNext()) {
			TagsEntry entry = itr.next();

			if (!TagsEntryPermission.contains(
					permissionChecker, entry, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return entries;
	}

}