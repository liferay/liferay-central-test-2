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

package com.liferay.portlet.tags.service;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.Isolation;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;

/**
 * <a href="TagsEntryLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.liferay.portlet.tags.service.impl.TagsEntryLocalServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.tags.service.TagsEntryLocalServiceUtil
 *
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface TagsEntryLocalService {
	public com.liferay.portlet.tags.model.TagsEntry addTagsEntry(
		com.liferay.portlet.tags.model.TagsEntry tagsEntry)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsEntry createTagsEntry(
		long entryId);

	public void deleteTagsEntry(long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteTagsEntry(
		com.liferay.portlet.tags.model.TagsEntry tagsEntry)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.tags.model.TagsEntry getTagsEntry(long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> getTagsEntries(
		int start, int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getTagsEntriesCount() throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsEntry updateTagsEntry(
		com.liferay.portlet.tags.model.TagsEntry tagsEntry)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsEntry updateTagsEntry(
		com.liferay.portlet.tags.model.TagsEntry tagsEntry, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsEntry addEntry(long userId,
		java.lang.String parentEntryName, java.lang.String name,
		java.lang.String vocabularyName, java.lang.String[] properties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void addEntryResources(
		com.liferay.portlet.tags.model.TagsEntry entry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void addEntryResources(
		com.liferay.portlet.tags.model.TagsEntry entry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void checkEntries(long userId, long groupId, java.lang.String[] names)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteEntry(com.liferay.portlet.tags.model.TagsEntry entry)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteVocabularyEntries(long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasEntry(long groupId, java.lang.String name,
		boolean folksonomy)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> getAssetEntries(
		long assetId, boolean folksonomy)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> getEntries()
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> getEntries(
		boolean folksonomy) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> getEntries(
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> getEntries(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> getEntries(
		java.lang.String className, long classPK, boolean folksonomy)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> getEntries(
		long classNameId, long classPK, boolean folksonomy)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> getEntries(
		long groupId, long classNameId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> getEntries(
		long groupId, long classNameId, java.lang.String name, int start,
		int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getEntriesSize(long groupId, long classNameId,
		java.lang.String name) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.tags.model.TagsEntry getEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.tags.model.TagsEntry getEntry(long groupId,
		java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.tags.model.TagsEntry getEntry(long groupId,
		java.lang.String name, boolean folksonomy)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getEntryIds(long groupId, java.lang.String[] names)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getEntryIds(long groupId, java.lang.String[] names,
		boolean folksonomy)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getEntryNames()
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getEntryNames(java.lang.String className,
		long classPK) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getEntryNames(long classNameId, long classPK)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getEntryNames(boolean folksonomy)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getEntryNames(java.lang.String className,
		long classPK, boolean folksonomy)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getEntryNames(long classNameId, long classPK,
		boolean folksonomy) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> getGroupVocabularyEntries(
		long groupId, java.lang.String vocabularyName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> getGroupVocabularyEntries(
		long groupId, java.lang.String parentEntryName,
		java.lang.String vocabularyName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> getGroupVocabularyRootEntries(
		long groupId, java.lang.String vocabularyName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void mergeEntries(long fromEntryId, long toEntryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.json.JSONArray search(long groupId,
		java.lang.String name, java.lang.String[] properties, int start, int end)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsEntry updateEntry(long userId,
		long entryId, java.lang.String parentEntryName, java.lang.String name,
		java.lang.String vocabularyName, java.lang.String[] properties)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;
}