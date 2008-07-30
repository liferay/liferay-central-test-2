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
		return _service.addTagsVocabulary(tagsVocabulary);
	}

	public static void deleteTagsVocabulary(long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteTagsVocabulary(vocabularyId);
	}

	public static void deleteTagsVocabulary(
		com.liferay.portlet.tags.model.TagsVocabulary tagsVocabulary)
		throws com.liferay.portal.SystemException {
		_service.deleteTagsVocabulary(tagsVocabulary);
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

	public static com.liferay.portlet.tags.model.TagsVocabulary getTagsVocabulary(
		long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getTagsVocabulary(vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsVocabulary> getTagsVocabularies(
		int start, int end) throws com.liferay.portal.SystemException {
		return _service.getTagsVocabularies(start, end);
	}

	public static int getTagsVocabulariesCount()
		throws com.liferay.portal.SystemException {
		return _service.getTagsVocabulariesCount();
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary updateTagsVocabulary(
		com.liferay.portlet.tags.model.TagsVocabulary tagsVocabulary)
		throws com.liferay.portal.SystemException {
		return _service.updateTagsVocabulary(tagsVocabulary);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary addVocabulary(
		long userId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addVocabulary(userId, name);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary addVocabulary(
		long userId, java.lang.String name, boolean folksonomy)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addVocabulary(userId, name, folksonomy);
	}

	public static void deleteVocabulary(long userId, long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteVocabulary(userId, vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsVocabulary> getVocabularies(
		long companyId) throws com.liferay.portal.SystemException {
		return _service.getVocabularies(companyId);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsVocabulary> getVocabularies(
		long companyId, boolean folksonomy)
		throws com.liferay.portal.SystemException {
		return _service.getVocabularies(companyId, folksonomy);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary getVocabulary(
		long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getVocabulary(vocabularyId);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary getVocabulary(
		long companyId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getVocabulary(companyId, name);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary updateVocabulary(
		long vocabularyId, java.lang.String name, boolean folksonomy)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateVocabulary(vocabularyId, name, folksonomy);
	}

	public static TagsVocabularyLocalService getService() {
		return _service;
	}

	public void setService(TagsVocabularyLocalService service) {
		_service = service;
	}

	private static TagsVocabularyLocalService _service;
}