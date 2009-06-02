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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermQueryFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.tags.NoSuchEntryException;
import com.liferay.portlet.tags.model.TagsAsset;
import com.liferay.portlet.tags.model.TagsAssetDisplay;
import com.liferay.portlet.tags.model.TagsAssetType;
import com.liferay.portlet.tags.model.TagsEntry;
import com.liferay.portlet.tags.model.TagsEntryConstants;
import com.liferay.portlet.tags.service.base.TagsAssetLocalServiceBaseImpl;
import com.liferay.portlet.tags.util.TagsAssetValidator;
import com.liferay.portlet.tags.util.TagsUtil;
import com.liferay.portlet.wiki.model.WikiPage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="TagsAssetLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 *
 */
public class TagsAssetLocalServiceImpl extends TagsAssetLocalServiceBaseImpl {

	public void deleteAsset(long assetId)
		throws PortalException, SystemException {

		TagsAsset asset = tagsAssetPersistence.findByPrimaryKey(assetId);

		deleteAsset(asset);
	}

	public void deleteAsset(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		TagsAsset asset = tagsAssetPersistence.fetchByC_C(classNameId, classPK);

		if (asset != null) {
			deleteAsset(asset);
		}
	}

	public void deleteAsset(TagsAsset asset) throws SystemException {
		tagsAssetPersistence.remove(asset);
	}

	public TagsAsset getAsset(long assetId)
		throws PortalException, SystemException {

		return tagsAssetPersistence.findByPrimaryKey(assetId);
	}

	public TagsAsset getAsset(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return tagsAssetPersistence.findByC_C(classNameId, classPK);
	}

	public TagsAssetType[] getAssetTypes(String languageId) {
		TagsAssetType[] assetTypes =
			new TagsAssetType[TagsUtil.ASSET_TYPE_CLASS_NAMES.length];

		for (int i = 0; i < TagsUtil.ASSET_TYPE_CLASS_NAMES.length; i++) {
			assetTypes[i] = getAssetType(
				TagsUtil.ASSET_TYPE_CLASS_NAMES[i], languageId);
		}

		return assetTypes;
	}

	public List<TagsAsset> getAssets(
			long[] entryIds, long[] notEntryIds, boolean andOperator,
			boolean excludeZeroViewCount, int start, int end)
		throws SystemException {

		return getAssets(
			0, new long[0], entryIds, notEntryIds, andOperator,
			excludeZeroViewCount, null, null, start, end);
	}

	public List<TagsAsset> getAssets(
			long groupId, long[] classNameIds, long[] entryIds,
			long[] notEntryIds, boolean andOperator,
			boolean excludeZeroViewCount, int start, int end)
		throws SystemException {

		return getAssets(
			groupId, classNameIds, entryIds, notEntryIds, andOperator,
			excludeZeroViewCount, null, null, start, end);
	}

	public List<TagsAsset> getAssets(
			long[] entryIds, long[] notEntryIds, boolean andOperator,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate,
			int start, int end)
		throws SystemException {

		return getAssets(
			0, new long[0], entryIds, notEntryIds, andOperator,
			excludeZeroViewCount, publishDate, expirationDate, start, end);
	}

	public List<TagsAsset> getAssets(
			long groupId, long[] classNameIds, long[] entryIds,
			long[] notEntryIds, boolean andOperator,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate,
			int start, int end)
		throws SystemException {

		if ((entryIds.length == 0) && (notEntryIds.length == 0)) {
			return tagsAssetFinder.findAssets(
				groupId, classNameIds, null, null, null, null,
				excludeZeroViewCount, publishDate, expirationDate, start, end);
		}
		else if (andOperator) {
			return tagsAssetFinder.findByAndEntryIds(
				groupId, classNameIds, entryIds, notEntryIds, null, null, null,
				null, excludeZeroViewCount, publishDate, expirationDate, start,
				end);
		}
		else {
			return tagsAssetFinder.findByOrEntryIds(
				groupId, classNameIds, entryIds, notEntryIds, null, null, null,
				null, excludeZeroViewCount, publishDate, expirationDate, start,
				end);
		}
	}

	public List<TagsAsset> getAssets(
			long[] entryIds, long[] notEntryIds, boolean andOperator,
			String orderByCol1, String orderByCol2, String orderByType1,
			String orderByType2, boolean excludeZeroViewCount, Date publishDate,
			Date expirationDate, int start, int end)
		throws SystemException {

		return getAssets(
			0, new long[0], entryIds, notEntryIds, andOperator,
			excludeZeroViewCount, publishDate, expirationDate, start, end);
	}

	public List<TagsAsset> getAssets(
			long groupId, long[] classNameIds, long[] entryIds,
			long[] notEntryIds, boolean andOperator, String orderByCol1,
			String orderByCol2, String orderByType1, String orderByType2,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate,
			int start, int end)
		throws SystemException {

		if ((entryIds.length == 0) && (notEntryIds.length == 0)) {
			return tagsAssetFinder.findAssets(
				groupId, classNameIds, orderByCol1, orderByCol2, orderByType1,
				orderByType2, excludeZeroViewCount, publishDate, expirationDate,
				start, end);
		}
		else if (andOperator) {
			return tagsAssetFinder.findByAndEntryIds(
				groupId, classNameIds, entryIds, notEntryIds, orderByCol1,
				orderByCol2, orderByType1, orderByType2, excludeZeroViewCount,
				publishDate, expirationDate, start, end);
		}
		else {
			return tagsAssetFinder.findByOrEntryIds(
				groupId, classNameIds, entryIds, notEntryIds, orderByCol1,
				orderByCol2, orderByType1, orderByType2, excludeZeroViewCount,
				publishDate, expirationDate, start, end);
		}
	}

	public int getAssetsCount(
			long[] entryIds, long[] notEntryIds, boolean andOperator,
			boolean excludeZeroViewCount)
		throws SystemException {

		return getAssetsCount(
			0, new long[0], entryIds, notEntryIds, andOperator,
			excludeZeroViewCount, null, null);
	}

	public int getAssetsCount(
			long groupId, long[] entryIds, long[] notEntryIds,
			boolean andOperator, boolean excludeZeroViewCount)
		throws SystemException {

		return getAssetsCount(
			groupId, new long[0], entryIds, notEntryIds, andOperator,
			excludeZeroViewCount, null, null);
	}

	public int getAssetsCount(
			long[] entryIds, long[] notEntryIds, boolean andOperator,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate)
		throws SystemException {

		return getAssetsCount(
			0, new long[0], entryIds, notEntryIds, andOperator,
			excludeZeroViewCount, publishDate, expirationDate);
	}

	public int getAssetsCount(
			long groupId, long[] classNameIds, long[] entryIds,
			long[] notEntryIds, boolean andOperator,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate)
		throws SystemException {

		if ((entryIds.length == 0) && (notEntryIds.length == 0)) {
			return tagsAssetFinder.countAssets(
				groupId, classNameIds, excludeZeroViewCount, publishDate,
				expirationDate);
		}
		else if (andOperator) {
			return tagsAssetFinder.countByAndEntryIds(
				groupId, classNameIds, entryIds, notEntryIds,
				excludeZeroViewCount, publishDate, expirationDate);
		}
		else {
			return tagsAssetFinder.countByOrEntryIds(
				groupId, classNameIds, entryIds, notEntryIds,
				excludeZeroViewCount, publishDate, expirationDate);
		}
	}

	public TagsAssetDisplay[] getCompanyAssetDisplays(
			long companyId, int start, int end, String languageId)
		throws SystemException {

		return getAssetDisplays(
			getCompanyAssets(companyId, start, end), languageId);
	}

	public List<TagsAsset> getCompanyAssets(long companyId, int start, int end)
		throws SystemException {

		return tagsAssetPersistence.findByCompanyId(companyId, start, end);
	}

	public int getCompanyAssetsCount(long companyId) throws SystemException {
		return tagsAssetPersistence.countByCompanyId(companyId);
	}

	public List<TagsAsset> getTopViewedAssets(
			String className, boolean asc, int start, int end)
		throws SystemException {

		return getTopViewedAssets(new String[] {className}, asc, start, end);
	}

	public List<TagsAsset> getTopViewedAssets(
			String[] className, boolean asc, int start, int end)
		throws SystemException {

		long[] classNameIds = new long[className.length];

		for (int i = 0; i < className.length; i++) {
			classNameIds[i] = PortalUtil.getClassNameId(className[i]);
		}

		return tagsAssetFinder.findByViewCount(classNameIds, asc, start, end);
	}

	public TagsAsset incrementViewCounter(String className, long classPK)
		throws SystemException {

		if (classPK <= 0) {
			return null;
		}

		long classNameId = PortalUtil.getClassNameId(className);

		TagsAsset asset = tagsAssetPersistence.fetchByC_C(classNameId, classPK);

		if (asset != null) {
			asset.setViewCount(asset.getViewCount() + 1);

			tagsAssetPersistence.update(asset, false);
		}

		return asset;
	}

	public Hits search(
			long companyId, String portletId, String keywords, int start,
			int end)
		throws SystemException {

		try {
			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create();

			if (Validator.isNotNull(portletId)) {
				contextQuery.addRequiredTerm(Field.PORTLET_ID, portletId);
			}
			else {
				BooleanQuery portletIdsQuery = BooleanQueryFactoryUtil.create();

				for (String assetTypePortletId :
						TagsUtil.ASSET_TYPE_PORTLET_IDS) {

					TermQuery termQuery = TermQueryFactoryUtil.create(
						Field.PORTLET_ID, assetTypePortletId);

					portletIdsQuery.add(termQuery, BooleanClauseOccur.SHOULD);
				}

				contextQuery.add(portletIdsQuery, BooleanClauseOccur.MUST);
			}

			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

			if (Validator.isNotNull(keywords)) {
				searchQuery.addTerm(Field.TITLE, keywords);
				searchQuery.addTerm(Field.CONTENT, keywords);
				searchQuery.addTerm(Field.DESCRIPTION, keywords);
				searchQuery.addTerm(Field.PROPERTIES, keywords);
				searchQuery.addTerm(Field.TAGS_ENTRIES, keywords);
			}

			BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

			fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

			if (searchQuery.clauses().size() > 0) {
				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			return SearchEngineUtil.search(companyId, fullQuery, start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public TagsAssetDisplay[] searchAssetDisplays(
			long companyId, String portletId, String keywords,
			String languageId, int start, int end)
		throws SystemException {

		List<TagsAsset> assets = new ArrayList<TagsAsset>();

		Hits hits = search(companyId, portletId, keywords, start, end);

		List<Document> hitsList = hits.toList();

		for (Document doc : hitsList) {
			try {
				TagsAsset asset = getAsset(doc);

				if (asset != null) {
					assets.add(asset);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}
		}

		return getAssetDisplays(assets, languageId);
	}

	public int searchAssetDisplaysCount(
			long companyId, String portletId, String keywords,
			String languageId)
		throws SystemException {

		Hits hits = search(
			companyId, portletId, keywords, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		return hits.getLength();
	}

	public TagsAsset updateAsset(
			long userId, long groupId, String className, long classPK,
			String[] categoryNames, String[] entryNames)
		throws PortalException, SystemException {

		return updateAsset(
			userId, groupId, className, classPK, categoryNames, entryNames,
			true, null, null, null, null, null, null, null, null, null, 0, 0,
			null);
	}

	public TagsAsset updateAsset(
			long userId, long groupId, String className, long classPK,
			String[] categoryNames, String[] entryNames, boolean visible,
			Date startDate, Date endDate, Date publishDate, Date expirationDate,
			String mimeType, String title, String description, String summary,
			String url, int height, int width, Integer priority)
		throws PortalException, SystemException {

		return updateAsset(
			userId, groupId, className, classPK, categoryNames, entryNames,
			visible, startDate, endDate, publishDate, expirationDate, mimeType,
			title, description, summary, url, height, width, priority, true);
	}

	public TagsAsset updateAsset(
			long userId, long groupId, String className, long classPK,
			long[] categoryIds, String[] entryNames, boolean visible,
			Date startDate, Date endDate, Date publishDate, Date expirationDate,
			String mimeType, String title, String description, String summary,
			String url, int height, int width, Integer priority, boolean sync)
		throws PortalException, SystemException {

		return updateAsset(
			userId, groupId, className, classPK, categoryIds, null, entryNames,
			visible, startDate, endDate, publishDate, expirationDate, mimeType,
			title, description, summary, url, height, width, priority, sync);
	}

	public TagsAsset updateAsset(
			long userId, long groupId, String className, long classPK,
			String[] categoryNames, String[] entryNames, boolean visible,
			Date startDate, Date endDate, Date publishDate, Date expirationDate,
			String mimeType, String title, String description, String summary,
			String url, int height, int width, Integer priority, boolean sync)
		throws PortalException, SystemException {

		return updateAsset(
			userId, groupId, className, classPK, null, categoryNames,
			entryNames, visible, startDate, endDate, publishDate,
			expirationDate, mimeType, title, description, summary, url, height,
			width, priority, sync);
	}

	public TagsAsset updateAsset(
			long userId, long groupId, String className, long classPK,
			long[] categoryIds, String[] categoryNames, String[] entryNames,
			boolean visible, Date startDate, Date endDate, Date publishDate,
			Date expirationDate, String mimeType, String title,
			String description, String summary, String url, int height,
			int width, Integer priority, boolean sync)
		throws PortalException, SystemException {

		// Asset

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);

		if (entryNames == null) {
			entryNames = new String[0];
		}

		if (categoryNames == null) {
			categoryNames = new String[0];
		}

		title = StringUtil.shorten(title, 300, StringPool.BLANK);
		Date now = new Date();

		validate(className, entryNames);

		TagsAsset asset = tagsAssetPersistence.fetchByC_C(classNameId, classPK);

		if (asset == null) {
			long assetId = counterLocalService.increment();

			asset = tagsAssetPersistence.create(assetId);

			asset.setCompanyId(user.getCompanyId());
			asset.setUserId(user.getUserId());
			asset.setUserName(user.getFullName());
			asset.setCreateDate(now);
			asset.setClassNameId(classNameId);
			asset.setClassPK(classPK);
			asset.setVisible(visible);
			asset.setPublishDate(publishDate);
			asset.setExpirationDate(expirationDate);

			if (priority == null) {
				asset.setPriority(0);
			}

			asset.setViewCount(0);
		}

		asset.setGroupId(groupId);
		asset.setModifiedDate(now);
		asset.setVisible(visible);
		asset.setStartDate(startDate);
		asset.setEndDate(endDate);
		asset.setPublishDate(publishDate);
		asset.setExpirationDate(expirationDate);
		asset.setMimeType(mimeType);
		asset.setTitle(title);
		asset.setDescription(description);
		asset.setSummary(summary);
		asset.setUrl(url);
		asset.setHeight(height);
		asset.setWidth(width);

		if (priority != null) {
			asset.setPriority(priority.intValue());
		}

		// Entries

		List<TagsEntry> entries = new ArrayList<TagsEntry>(entryNames.length);

		for (int i = 0; i < entryNames.length; i++) {
			TagsEntry entry = null;

			try {
				entry = tagsEntryLocalService.getEntry(
					groupId, entryNames[i], TagsEntryConstants.FOLKSONOMY_TAG);
			}
			catch (NoSuchEntryException nsee) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setAddCommunityPermissions(true);
				serviceContext.setAddGuestPermissions(true);
				serviceContext.setScopeGroupId(groupId);

				entry = tagsEntryLocalService.addEntry(
					user.getUserId(), null, entryNames[i], null,
					PropsValues.TAGS_PROPERTIES_DEFAULT, serviceContext);
			}

			if (entry != null) {
				entries.add(entry);
			}
		}

		// Categories

		if (categoryNames != null) {
			for (int i = 0; i < categoryNames.length; i++) {
				try {
					TagsEntry entry = tagsEntryLocalService.getEntry(
						groupId, categoryNames[i],
						TagsEntryConstants.FOLKSONOMY_CATEGORY);

					entries.add(entry);
				}
				catch (NoSuchEntryException nsee) {
				}
			}
		}

		tagsAssetPersistence.setTagsEntries(asset.getAssetId(), entries);

		// Asset categories

		if (categoryIds != null) {
			tagsAssetPersistence.setAssetCategories(
				asset.getAssetId(), categoryIds);
		}

		// Update asset after entries so that asset listeners have access the
		// saved entries

		tagsAssetPersistence.update(asset, false);

		// Synchronize

		if (!sync) {
			return asset;
		}

		if (className.equals(BlogsEntry.class.getName())) {
			BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(classPK);

			entry.setTitle(title);

			blogsEntryPersistence.update(entry, false);
		}
		else if (className.equals(BookmarksEntry.class.getName())) {
			BookmarksEntry entry = bookmarksEntryPersistence.findByPrimaryKey(
				classPK);

			entry.setName(title);
			entry.setComments(description);
			entry.setUrl(url);

			bookmarksEntryPersistence.update(entry, false);
		}
		else if (className.equals(DLFileEntry.class.getName())) {
			DLFileEntry fileEntry = dlFileEntryPersistence.findByPrimaryKey(
				classPK);

			fileEntry.setTitle(title);
			fileEntry.setDescription(description);

			dlFileEntryPersistence.update(fileEntry, false);
		}
		else if (className.equals(JournalArticle.class.getName())) {
			JournalArticle article = journalArticlePersistence.findByPrimaryKey(
				classPK);

			article.setTitle(title);
			article.setDescription(description);

			journalArticlePersistence.update(article, false);
		}
		else if (className.equals(MBMessage.class.getName())) {
			MBMessage message = mbMessagePersistence.findByPrimaryKey(classPK);

			message.setSubject(title);

			mbMessagePersistence.update(message, false);
		}
		else if (className.equals(WikiPage.class.getName())) {
			WikiPage page = wikiPagePersistence.findByPrimaryKey(classPK);

			page.setTitle(title);

			wikiPagePersistence.update(page, false);
		}

		return asset;
	}

	public TagsAsset updateVisible(
			String className, long classPK, boolean visible)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		TagsAsset asset = tagsAssetPersistence.findByC_C(classNameId, classPK);

		asset.setVisible(visible);

		tagsAssetPersistence.update(asset, false);

		return asset;
	}

	public void validate(String className, String[] entryNames)
		throws PortalException {

		TagsAssetValidator validator = (TagsAssetValidator)InstancePool.get(
			PropsValues.TAGS_ASSET_VALIDATOR);

		validator.validate(className, entryNames);
	}

	protected TagsAsset getAsset(Document doc)
		throws PortalException, SystemException {

		String portletId = GetterUtil.getString(doc.get(Field.PORTLET_ID));

		if (portletId.equals(PortletKeys.BLOGS)) {
			long entryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			long classNameId = PortalUtil.getClassNameId(
				BlogsEntry.class.getName());
			long classPK = entryId;

			return tagsAssetPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.BOOKMARKS)) {
			long entryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			long classNameId = PortalUtil.getClassNameId(
				BookmarksEntry.class.getName());
			long classPK = entryId;

			return tagsAssetPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY)) {
			long folderId = GetterUtil.getLong(doc.get("repositoryId"));
			String name = doc.get("path");

			DLFileEntry fileEntry = dlFileEntryLocalService.getFileEntry(
				folderId, name);

			long classNameId = PortalUtil.getClassNameId(
				DLFileEntry.class.getName());
			long classPK = fileEntry.getFileEntryId();

			return tagsAssetPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.IMAGE_GALLERY)) {
			long imageId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			long classNameId = PortalUtil.getClassNameId(
				IGImage.class.getName());
			long classPK = imageId;

			return tagsAssetPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.JOURNAL)) {
			long groupId = GetterUtil.getLong(doc.get(Field.GROUP_ID));
			String articleId = doc.get(Field.ENTRY_CLASS_PK);
			//double version = GetterUtil.getDouble(doc.get("version"));

			long articleResourcePrimKey =
				journalArticleResourceLocalService.getArticleResourcePrimKey(
					groupId, articleId);

			long classNameId = PortalUtil.getClassNameId(
				JournalArticle.class.getName());
			long classPK = articleResourcePrimKey;

			return tagsAssetPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.MESSAGE_BOARDS)) {
			long messageId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			long classNameId = PortalUtil.getClassNameId(
				MBMessage.class.getName());
			long classPK = messageId;

			return tagsAssetPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.WIKI)) {
			long nodeId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));
			String title = doc.get(Field.TITLE);

			long pageResourcePrimKey =
				wikiPageResourceLocalService.getPageResourcePrimKey(
					nodeId, title);

			long classNameId = PortalUtil.getClassNameId(
				WikiPage.class.getName());
			long classPK = pageResourcePrimKey;

			return tagsAssetPersistence.findByC_C(classNameId, classPK);
		}

		return null;
	}

	protected TagsAssetDisplay[] getAssetDisplays(
			List<TagsAsset> assets, String languageId)
		throws SystemException {

		TagsAssetDisplay[] assetDisplays = new TagsAssetDisplay[assets.size()];

		for (int i = 0; i < assets.size(); i++) {
			TagsAsset asset = assets.get(i);

			String className = PortalUtil.getClassName(asset.getClassNameId());
			String portletId = PortalUtil.getClassNamePortletId(className);
			String portletTitle = PortalUtil.getPortletTitle(
				portletId, asset.getCompanyId(), languageId);

			List<TagsEntry> tagsEntriesList =
				tagsAssetPersistence.getTagsEntries(asset.getAssetId());

			String tagsEntries = ListUtil.toString(
				tagsEntriesList, "name", ", ");

			TagsAssetDisplay assetDisplay = new TagsAssetDisplay();

			assetDisplay.setAssetId(asset.getAssetId());
			assetDisplay.setCompanyId(asset.getCompanyId());
			assetDisplay.setUserId(asset.getUserId());
			assetDisplay.setUserName(asset.getUserName());
			assetDisplay.setCreateDate(asset.getCreateDate());
			assetDisplay.setModifiedDate(asset.getModifiedDate());
			assetDisplay.setClassNameId(asset.getClassNameId());
			assetDisplay.setClassName(className);
			assetDisplay.setClassPK(asset.getClassPK());
			assetDisplay.setPortletId(portletId);
			assetDisplay.setPortletTitle(portletTitle);
			assetDisplay.setStartDate(asset.getStartDate());
			assetDisplay.setEndDate(asset.getEndDate());
			assetDisplay.setPublishDate(asset.getPublishDate());
			assetDisplay.setExpirationDate(asset.getExpirationDate());
			assetDisplay.setMimeType(asset.getMimeType());
			assetDisplay.setTitle(asset.getTitle());
			assetDisplay.setDescription(asset.getDescription());
			assetDisplay.setSummary(asset.getSummary());
			assetDisplay.setUrl(asset.getUrl());
			assetDisplay.setHeight(asset.getHeight());
			assetDisplay.setWidth(asset.getWidth());
			assetDisplay.setPriority(asset.getPriority());
			assetDisplay.setViewCount(asset.getViewCount());
			assetDisplay.setTagsEntries(tagsEntries);

			assetDisplays[i] = assetDisplay;
		}

		return assetDisplays;
	}

	protected TagsAssetType getAssetType(String className, String languageId) {
		long companyId = PortalInstances.getDefaultCompanyId();

		long classNameId = PortalUtil.getClassNameId(className);

		String portletId = PortalUtil.getClassNamePortletId(className);
		String portletTitle = PortalUtil.getPortletTitle(
			portletId, companyId, languageId);

		TagsAssetType assetType = new TagsAssetType();

		assetType.setClassNameId(classNameId);
		assetType.setClassName(className);
		assetType.setPortletId(portletId);
		assetType.setPortletTitle(portletTitle);

		return assetType;
	}

	private static Log _log =
		LogFactoryUtil.getLog(TagsAssetLocalServiceImpl.class);

}