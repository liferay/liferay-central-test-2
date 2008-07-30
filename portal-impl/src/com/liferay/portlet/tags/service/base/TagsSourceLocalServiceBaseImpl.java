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

package com.liferay.portlet.tags.service.base;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.InitializingBean;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;

import com.liferay.portlet.tags.model.TagsSource;
import com.liferay.portlet.tags.service.TagsAssetLocalService;
import com.liferay.portlet.tags.service.TagsAssetService;
import com.liferay.portlet.tags.service.TagsEntryLocalService;
import com.liferay.portlet.tags.service.TagsEntryService;
import com.liferay.portlet.tags.service.TagsPropertyLocalService;
import com.liferay.portlet.tags.service.TagsPropertyService;
import com.liferay.portlet.tags.service.TagsSourceLocalService;
import com.liferay.portlet.tags.service.TagsVocabularyLocalService;
import com.liferay.portlet.tags.service.TagsVocabularyService;
import com.liferay.portlet.tags.service.persistence.TagsAssetFinder;
import com.liferay.portlet.tags.service.persistence.TagsAssetPersistence;
import com.liferay.portlet.tags.service.persistence.TagsEntryFinder;
import com.liferay.portlet.tags.service.persistence.TagsEntryPersistence;
import com.liferay.portlet.tags.service.persistence.TagsPropertyFinder;
import com.liferay.portlet.tags.service.persistence.TagsPropertyKeyFinder;
import com.liferay.portlet.tags.service.persistence.TagsPropertyPersistence;
import com.liferay.portlet.tags.service.persistence.TagsSourcePersistence;
import com.liferay.portlet.tags.service.persistence.TagsVocabularyPersistence;

import java.util.List;

/**
 * <a href="TagsSourceLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class TagsSourceLocalServiceBaseImpl
	implements TagsSourceLocalService, InitializingBean {
	public TagsSource addTagsSource(TagsSource tagsSource)
		throws SystemException {
		tagsSource.setNew(true);

		return tagsSourcePersistence.update(tagsSource, false);
	}

	public void deleteTagsSource(long sourceId)
		throws PortalException, SystemException {
		tagsSourcePersistence.remove(sourceId);
	}

	public void deleteTagsSource(TagsSource tagsSource)
		throws SystemException {
		tagsSourcePersistence.remove(tagsSource);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return tagsSourcePersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return tagsSourcePersistence.findWithDynamicQuery(dynamicQuery, start,
			end);
	}

	public TagsSource getTagsSource(long sourceId)
		throws PortalException, SystemException {
		return tagsSourcePersistence.findByPrimaryKey(sourceId);
	}

	public List<TagsSource> getTagsSources(int start, int end)
		throws SystemException {
		return tagsSourcePersistence.findAll(start, end);
	}

	public int getTagsSourcesCount() throws SystemException {
		return tagsSourcePersistence.countAll();
	}

	public TagsSource updateTagsSource(TagsSource tagsSource)
		throws SystemException {
		tagsSource.setNew(false);

		return tagsSourcePersistence.update(tagsSource, true);
	}

	public TagsAssetLocalService getTagsAssetLocalService() {
		return tagsAssetLocalService;
	}

	public void setTagsAssetLocalService(
		TagsAssetLocalService tagsAssetLocalService) {
		this.tagsAssetLocalService = tagsAssetLocalService;
	}

	public TagsAssetService getTagsAssetService() {
		return tagsAssetService;
	}

	public void setTagsAssetService(TagsAssetService tagsAssetService) {
		this.tagsAssetService = tagsAssetService;
	}

	public TagsAssetPersistence getTagsAssetPersistence() {
		return tagsAssetPersistence;
	}

	public void setTagsAssetPersistence(
		TagsAssetPersistence tagsAssetPersistence) {
		this.tagsAssetPersistence = tagsAssetPersistence;
	}

	public TagsAssetFinder getTagsAssetFinder() {
		return tagsAssetFinder;
	}

	public void setTagsAssetFinder(TagsAssetFinder tagsAssetFinder) {
		this.tagsAssetFinder = tagsAssetFinder;
	}

	public TagsEntryLocalService getTagsEntryLocalService() {
		return tagsEntryLocalService;
	}

	public void setTagsEntryLocalService(
		TagsEntryLocalService tagsEntryLocalService) {
		this.tagsEntryLocalService = tagsEntryLocalService;
	}

	public TagsEntryService getTagsEntryService() {
		return tagsEntryService;
	}

	public void setTagsEntryService(TagsEntryService tagsEntryService) {
		this.tagsEntryService = tagsEntryService;
	}

	public TagsEntryPersistence getTagsEntryPersistence() {
		return tagsEntryPersistence;
	}

	public void setTagsEntryPersistence(
		TagsEntryPersistence tagsEntryPersistence) {
		this.tagsEntryPersistence = tagsEntryPersistence;
	}

	public TagsEntryFinder getTagsEntryFinder() {
		return tagsEntryFinder;
	}

	public void setTagsEntryFinder(TagsEntryFinder tagsEntryFinder) {
		this.tagsEntryFinder = tagsEntryFinder;
	}

	public TagsPropertyLocalService getTagsPropertyLocalService() {
		return tagsPropertyLocalService;
	}

	public void setTagsPropertyLocalService(
		TagsPropertyLocalService tagsPropertyLocalService) {
		this.tagsPropertyLocalService = tagsPropertyLocalService;
	}

	public TagsPropertyService getTagsPropertyService() {
		return tagsPropertyService;
	}

	public void setTagsPropertyService(TagsPropertyService tagsPropertyService) {
		this.tagsPropertyService = tagsPropertyService;
	}

	public TagsPropertyPersistence getTagsPropertyPersistence() {
		return tagsPropertyPersistence;
	}

	public void setTagsPropertyPersistence(
		TagsPropertyPersistence tagsPropertyPersistence) {
		this.tagsPropertyPersistence = tagsPropertyPersistence;
	}

	public TagsPropertyFinder getTagsPropertyFinder() {
		return tagsPropertyFinder;
	}

	public void setTagsPropertyFinder(TagsPropertyFinder tagsPropertyFinder) {
		this.tagsPropertyFinder = tagsPropertyFinder;
	}

	public TagsPropertyKeyFinder getTagsPropertyKeyFinder() {
		return tagsPropertyKeyFinder;
	}

	public void setTagsPropertyKeyFinder(
		TagsPropertyKeyFinder tagsPropertyKeyFinder) {
		this.tagsPropertyKeyFinder = tagsPropertyKeyFinder;
	}

	public TagsSourcePersistence getTagsSourcePersistence() {
		return tagsSourcePersistence;
	}

	public void setTagsSourcePersistence(
		TagsSourcePersistence tagsSourcePersistence) {
		this.tagsSourcePersistence = tagsSourcePersistence;
	}

	public TagsVocabularyLocalService getTagsVocabularyLocalService() {
		return tagsVocabularyLocalService;
	}

	public void setTagsVocabularyLocalService(
		TagsVocabularyLocalService tagsVocabularyLocalService) {
		this.tagsVocabularyLocalService = tagsVocabularyLocalService;
	}

	public TagsVocabularyService getTagsVocabularyService() {
		return tagsVocabularyService;
	}

	public void setTagsVocabularyService(
		TagsVocabularyService tagsVocabularyService) {
		this.tagsVocabularyService = tagsVocabularyService;
	}

	public TagsVocabularyPersistence getTagsVocabularyPersistence() {
		return tagsVocabularyPersistence;
	}

	public void setTagsVocabularyPersistence(
		TagsVocabularyPersistence tagsVocabularyPersistence) {
		this.tagsVocabularyPersistence = tagsVocabularyPersistence;
	}

	public void afterPropertiesSet() {
		if (tagsAssetLocalService == null) {
			tagsAssetLocalService = (TagsAssetLocalService)PortalBeanLocatorUtil.locate(TagsAssetLocalService.class.getName() +
					".impl");
		}

		if (tagsAssetService == null) {
			tagsAssetService = (TagsAssetService)PortalBeanLocatorUtil.locate(TagsAssetService.class.getName() +
					".impl");
		}

		if (tagsAssetPersistence == null) {
			tagsAssetPersistence = (TagsAssetPersistence)PortalBeanLocatorUtil.locate(TagsAssetPersistence.class.getName() +
					".impl");
		}

		if (tagsAssetFinder == null) {
			tagsAssetFinder = (TagsAssetFinder)PortalBeanLocatorUtil.locate(TagsAssetFinder.class.getName() +
					".impl");
		}

		if (tagsEntryLocalService == null) {
			tagsEntryLocalService = (TagsEntryLocalService)PortalBeanLocatorUtil.locate(TagsEntryLocalService.class.getName() +
					".impl");
		}

		if (tagsEntryService == null) {
			tagsEntryService = (TagsEntryService)PortalBeanLocatorUtil.locate(TagsEntryService.class.getName() +
					".impl");
		}

		if (tagsEntryPersistence == null) {
			tagsEntryPersistence = (TagsEntryPersistence)PortalBeanLocatorUtil.locate(TagsEntryPersistence.class.getName() +
					".impl");
		}

		if (tagsEntryFinder == null) {
			tagsEntryFinder = (TagsEntryFinder)PortalBeanLocatorUtil.locate(TagsEntryFinder.class.getName() +
					".impl");
		}

		if (tagsPropertyLocalService == null) {
			tagsPropertyLocalService = (TagsPropertyLocalService)PortalBeanLocatorUtil.locate(TagsPropertyLocalService.class.getName() +
					".impl");
		}

		if (tagsPropertyService == null) {
			tagsPropertyService = (TagsPropertyService)PortalBeanLocatorUtil.locate(TagsPropertyService.class.getName() +
					".impl");
		}

		if (tagsPropertyPersistence == null) {
			tagsPropertyPersistence = (TagsPropertyPersistence)PortalBeanLocatorUtil.locate(TagsPropertyPersistence.class.getName() +
					".impl");
		}

		if (tagsPropertyFinder == null) {
			tagsPropertyFinder = (TagsPropertyFinder)PortalBeanLocatorUtil.locate(TagsPropertyFinder.class.getName() +
					".impl");
		}

		if (tagsPropertyKeyFinder == null) {
			tagsPropertyKeyFinder = (TagsPropertyKeyFinder)PortalBeanLocatorUtil.locate(TagsPropertyKeyFinder.class.getName() +
					".impl");
		}

		if (tagsSourcePersistence == null) {
			tagsSourcePersistence = (TagsSourcePersistence)PortalBeanLocatorUtil.locate(TagsSourcePersistence.class.getName() +
					".impl");
		}

		if (tagsVocabularyLocalService == null) {
			tagsVocabularyLocalService = (TagsVocabularyLocalService)PortalBeanLocatorUtil.locate(TagsVocabularyLocalService.class.getName() +
					".impl");
		}

		if (tagsVocabularyService == null) {
			tagsVocabularyService = (TagsVocabularyService)PortalBeanLocatorUtil.locate(TagsVocabularyService.class.getName() +
					".impl");
		}

		if (tagsVocabularyPersistence == null) {
			tagsVocabularyPersistence = (TagsVocabularyPersistence)PortalBeanLocatorUtil.locate(TagsVocabularyPersistence.class.getName() +
					".impl");
		}
	}

	protected TagsAssetLocalService tagsAssetLocalService;
	protected TagsAssetService tagsAssetService;
	protected TagsAssetPersistence tagsAssetPersistence;
	protected TagsAssetFinder tagsAssetFinder;
	protected TagsEntryLocalService tagsEntryLocalService;
	protected TagsEntryService tagsEntryService;
	protected TagsEntryPersistence tagsEntryPersistence;
	protected TagsEntryFinder tagsEntryFinder;
	protected TagsPropertyLocalService tagsPropertyLocalService;
	protected TagsPropertyService tagsPropertyService;
	protected TagsPropertyPersistence tagsPropertyPersistence;
	protected TagsPropertyFinder tagsPropertyFinder;
	protected TagsPropertyKeyFinder tagsPropertyKeyFinder;
	protected TagsSourcePersistence tagsSourcePersistence;
	protected TagsVocabularyLocalService tagsVocabularyLocalService;
	protected TagsVocabularyService tagsVocabularyService;
	protected TagsVocabularyPersistence tagsVocabularyPersistence;
}