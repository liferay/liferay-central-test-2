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


/**
 * <a href="TagsVocabularyLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.tags.service.TagsVocabularyLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.tags.service.TagsVocabularyLocalService
 *
 */
public class TagsVocabularyLocalServiceUtil {
	public static com.liferay.portlet.tags.model.TagsVocabulary addTagsVocabulary(
		com.liferay.portlet.tags.model.TagsVocabulary tagsVocabulary)
		throws com.liferay.portal.SystemException {
		return getService().addTagsVocabulary(tagsVocabulary);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary createTagsVocabulary(
		long vocabularyId) {
		return getService().createTagsVocabulary(vocabularyId);
	}

	public static void deleteTagsVocabulary(long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteTagsVocabulary(vocabularyId);
	}

	public static void deleteTagsVocabulary(
		com.liferay.portlet.tags.model.TagsVocabulary tagsVocabulary)
		throws com.liferay.portal.SystemException {
		getService().deleteTagsVocabulary(tagsVocabulary);
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

	public static com.liferay.portlet.tags.model.TagsVocabulary getTagsVocabulary(
		long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getTagsVocabulary(vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsVocabulary> getTagsVocabularies(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getTagsVocabularies(start, end);
	}

	public static int getTagsVocabulariesCount()
		throws com.liferay.portal.SystemException {
		return getService().getTagsVocabulariesCount();
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary updateTagsVocabulary(
		com.liferay.portlet.tags.model.TagsVocabulary tagsVocabulary)
		throws com.liferay.portal.SystemException {
		return getService().updateTagsVocabulary(tagsVocabulary);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary updateTagsVocabulary(
		com.liferay.portlet.tags.model.TagsVocabulary tagsVocabulary,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateTagsVocabulary(tagsVocabulary, merge);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary addVocabulary(
		long userId, java.lang.String name, boolean folksonomy,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addVocabulary(userId, name, folksonomy, serviceContext);
	}

	public static void addVocabularyResources(
		com.liferay.portlet.tags.model.TagsVocabulary vocabulary,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addVocabularyResources(vocabulary, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addVocabularyResources(
		com.liferay.portlet.tags.model.TagsVocabulary vocabulary,
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
		com.liferay.portlet.tags.model.TagsVocabulary vocabulary)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteVocabulary(vocabulary);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsVocabulary> getCompanyVocabularies(
		long companyId, boolean folksonomy)
		throws com.liferay.portal.SystemException {
		return getService().getCompanyVocabularies(companyId, folksonomy);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsVocabulary> getGroupVocabularies(
		long groupId, boolean folksonomy)
		throws com.liferay.portal.SystemException {
		return getService().getGroupVocabularies(groupId, folksonomy);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary getGroupVocabulary(
		long groupId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getGroupVocabulary(groupId, name);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary getVocabulary(
		long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getVocabulary(vocabularyId);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary updateVocabulary(
		long vocabularyId, java.lang.String name, boolean folksonomy)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateVocabulary(vocabularyId, name, folksonomy);
	}

	public static TagsVocabularyLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("TagsVocabularyLocalService is not set");
		}

		return _service;
	}

	public void setService(TagsVocabularyLocalService service) {
		_service = service;
	}

	private static TagsVocabularyLocalService _service;
}