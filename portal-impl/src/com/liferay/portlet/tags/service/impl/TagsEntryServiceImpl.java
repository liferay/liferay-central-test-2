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

package com.liferay.portlet.tags.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.tags.model.TagsEntry;
import com.liferay.portlet.tags.service.TagsEntryLocalServiceUtil;
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
 */
public class TagsEntryServiceImpl extends TagsEntryServiceBaseImpl {

	public TagsEntry addEntry(long plid, long groupId, String name)
		throws PortalException, SystemException {

		PortletPermissionUtil.check(
			getPermissionChecker(), plid, PortletKeys.TAGS_ADMIN,
			ActionKeys.ADD_ENTRY);

		return tagsEntryLocalService.addEntry(getUserId(), groupId, name);
	}

	public TagsEntry addEntry(long plid, long groupId, String name,
			String[] properties)
		throws PortalException, SystemException {

		PortletPermissionUtil.check(
			getPermissionChecker(), plid, PortletKeys.TAGS_ADMIN,
			ActionKeys.ADD_ENTRY);

		return tagsEntryLocalService.addEntry(
			getUserId(), groupId, name, properties);
	}

	public TagsEntry addEntry(
			long plid, long groupId, String name, String vocabularyName,
			String[] properties)
		throws PortalException, SystemException {

		PortletPermissionUtil.check(
				getPermissionChecker(), plid, PortletKeys.TAGS_ADMIN,
				ActionKeys.ADD_ENTRY);

		return tagsEntryLocalService.addEntry(
			getUserId(), groupId, name, vocabularyName, properties);
	}

	public TagsEntry addEntry(
			long plid, long groupId, String name,
			String vocabularyName, String[] properties,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		PortletPermissionUtil.check(
			getPermissionChecker(), plid, PortletKeys.TAGS_ADMIN,
			ActionKeys.ADD_ENTRY);

		return tagsEntryLocalService.addEntry(
			getUserId(), groupId, name, vocabularyName, properties,
			communityPermissions, guestPermissions);
	}

	public TagsEntry addEntry(
			long plid, long groupId, String parentEntryName, String name,
			String vocabularyName, String[] properties)
		throws PortalException, SystemException {

		PortletPermissionUtil.check(
			getPermissionChecker(), plid, PortletKeys.TAGS_ADMIN,
			ActionKeys.ADD_ENTRY);

		return tagsEntryLocalService.addEntry(
			getUserId(), groupId, parentEntryName, name, vocabularyName,
			properties);
	}

	public TagsEntry addEntry(
			long plid, long groupId, String parentEntryName, String name,
			String vocabularyName, String[] properties,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		PortletPermissionUtil.check(
			getPermissionChecker(), plid, PortletKeys.TAGS_ADMIN,
			ActionKeys.ADD_ENTRY);

		return tagsEntryLocalService.addEntry(
			getUserId(), groupId, parentEntryName, name, vocabularyName,
			properties, communityPermissions, guestPermissions);
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		TagsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		tagsEntryLocalService.deleteEntry(entryId);
	}

	public List<TagsEntry> getEntries(String className, long classPK)
		throws SystemException, PrincipalException, PortalException {

		List<TagsEntry> tagsEntries =
			tagsEntryLocalService.getEntries(className, classPK);

		Iterator<TagsEntry> itr = tagsEntries.iterator();

		while (itr.hasNext()) {
			TagsEntry tagEntry = itr.next();

			if (!TagsEntryPermission.contains(
					getPermissionChecker(), tagEntry, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return tagsEntries;
	}

	public List<TagsEntry> getEntries(
			long groupId, long classNameId, String name)
		throws SystemException, PrincipalException, PortalException {

		List<TagsEntry> tagsEntries =
			tagsEntryLocalService.getEntries(groupId, classNameId, name);

		Iterator<TagsEntry> itr = tagsEntries.iterator();

		while (itr.hasNext()) {
			TagsEntry tagEntry = itr.next();

			if (!TagsEntryPermission.contains(
					getPermissionChecker(), tagEntry, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return tagsEntries;
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

		List<TagsEntry> tagsEntries =
			tagsEntryLocalService.getGroupVocabularyEntries(
				groupId, vocabularyName);

		Iterator<TagsEntry> itr = tagsEntries.iterator();

		while (itr.hasNext()) {
			TagsEntry tagEntry = itr.next();

			if (!TagsEntryPermission.contains(
					getPermissionChecker(), tagEntry, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return tagsEntries;
	}

	public List<TagsEntry> getGroupVocabularyEntries(
			long groupId, String parentEntryName, String vocabularyName)
		throws PortalException, SystemException {

		List<TagsEntry> tagsEntries =
			tagsEntryLocalService.getGroupVocabularyEntries(
				groupId, parentEntryName, vocabularyName);

		Iterator<TagsEntry> itr = tagsEntries.iterator();

		while (itr.hasNext()) {
			TagsEntry tagEntry = itr.next();

			if (!TagsEntryPermission.contains(
					getPermissionChecker(), tagEntry, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return tagsEntries;
	}

	public List<TagsEntry> getGroupVocabularyRootEntries(
			long groupId, String vocabularyName)
		throws PortalException, SystemException {

		List<TagsEntry> tagsEntries =
			tagsEntryLocalService.getGroupVocabularyRootEntries(
					groupId, vocabularyName);

		Iterator<TagsEntry> itr = tagsEntries.iterator();

		while (itr.hasNext()) {
			TagsEntry tagEntry = itr.next();

			if (!TagsEntryPermission.contains(
					getPermissionChecker(), tagEntry, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return tagsEntries;
	}

	public void mergeEntries(long fromEntryId, long toEntryId)
		throws PortalException, SystemException {

		TagsEntryPermission.check(
			getPermissionChecker(), fromEntryId, ActionKeys.VIEW);

		TagsEntryPermission.check(
			getPermissionChecker(), toEntryId, ActionKeys.UPDATE);

		tagsEntryLocalService.mergeEntries(fromEntryId, toEntryId);
	}

	public List<TagsEntry> search(
			long groupId, String name, String[] properties)
		throws SystemException {

		return tagsEntryLocalService.search(groupId, name, properties);
	}

	public List<TagsEntry> search(
		long groupId, String name, String[] properties, int start, int end)
		throws SystemException {

		return tagsEntryLocalService.search(
			groupId, name, properties, start, end);
	}

	public JSONArray searchAutocomplete(
			long groupId, String name, String[] properties, int start, int end)
		throws SystemException {

		return tagsEntryLocalService.searchAutocomplete(
			groupId, name, properties, start, end);
	}

	public int searchCount(long groupId, String name, String[] properties)
		throws SystemException {

		return tagsEntryLocalService.searchCount(groupId, name, properties);
	}

	public TagsEntry updateEntry(long entryId, String name)
		throws PortalException, SystemException {

		TagsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.UPDATE);

		return tagsEntryLocalService.updateEntry(entryId, name);
	}

	public TagsEntry updateEntry(
			long entryId, String parentEntryName, String name,
			String vocabularyName)
		throws PortalException, SystemException {

		TagsEntry entry = TagsEntryLocalServiceUtil.getEntry(entryId);

		TagsEntryPermission.check(
			getPermissionChecker(), name, entry.getGroupId(),
			ActionKeys.UPDATE);

		return tagsEntryLocalService.updateEntry(
			entryId, parentEntryName, name, vocabularyName);
	}

	public TagsEntry updateEntry(long entryId, String name, String[] properties)
		throws PortalException, SystemException {

		TagsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.UPDATE);

		return tagsEntryLocalService.updateEntry(
			getUserId(), entryId, name, properties);
	}

	public TagsEntry updateEntry(
			long entryId, String parentEntryName, String name,
			String vocabularyName, String[] properties)
		throws PortalException, SystemException {

		TagsEntry entry = TagsEntryLocalServiceUtil.getEntry(entryId);

		TagsEntryPermission.check(
			getPermissionChecker(), entry.getEntryId(), ActionKeys.UPDATE);

		return tagsEntryLocalService.updateEntry(
			getUserId(), entryId, parentEntryName, name, vocabularyName,
			properties);
	}

}