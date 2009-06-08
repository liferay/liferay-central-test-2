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

package com.liferay.portlet.asset.service.impl;

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
import com.liferay.portlet.asset.NoSuchTagException;
import com.liferay.portlet.asset.model.Asset;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetDisplay;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetType;
import com.liferay.portlet.asset.service.base.AssetLocalServiceBaseImpl;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.portlet.asset.util.AssetValidator;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.wiki.model.WikiPage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="AssetLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 *
 */
public class AssetLocalServiceImpl extends AssetLocalServiceBaseImpl {

	public void deleteAsset(long assetId)
		throws PortalException, SystemException {

		Asset asset = assetPersistence.findByPrimaryKey(assetId);

		deleteAsset(asset);
	}

	public void deleteAsset(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		Asset asset = assetPersistence.fetchByC_C(classNameId, classPK);

		if (asset != null) {
			deleteAsset(asset);
		}
	}

	public void deleteAsset(Asset asset) throws SystemException {
		assetPersistence.remove(asset);
	}

	public Asset getAsset(long assetId)
		throws PortalException, SystemException {

		return assetPersistence.findByPrimaryKey(assetId);
	}

	public Asset getAsset(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return assetPersistence.findByC_C(classNameId, classPK);
	}

	public AssetType[] getAssetTypes(String languageId) {
		AssetType[] assetTypes =
			new AssetType[AssetUtil.ASSET_TYPE_CLASS_NAMES.length];

		for (int i = 0; i < AssetUtil.ASSET_TYPE_CLASS_NAMES.length; i++) {
			assetTypes[i] = getAssetType(
				AssetUtil.ASSET_TYPE_CLASS_NAMES[i], languageId);
		}

		return assetTypes;
	}

	public List<Asset> getAssets(
			long[] tagIds, long[] notTagIds, boolean andOperator,
			boolean excludeZeroViewCount, int start, int end)
		throws SystemException {

		return getAssets(
			0, new long[0], tagIds, notTagIds, andOperator,
			excludeZeroViewCount, null, null, start, end);
	}

	public List<Asset> getAssets(
			long groupId, long[] classNameIds, long[] tagIds,
			long[] notTagIds, boolean andOperator,
			boolean excludeZeroViewCount, int start, int end)
		throws SystemException {

		return getAssets(
			groupId, classNameIds, tagIds, notTagIds, andOperator,
			excludeZeroViewCount, null, null, start, end);
	}

	public List<Asset> getAssets(
			long[] tagIds, long[] notTagIds, boolean andOperator,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate,
			int start, int end)
		throws SystemException {

		return getAssets(
			0, new long[0], tagIds, notTagIds, andOperator,
			excludeZeroViewCount, publishDate, expirationDate, start, end);
	}

	public List<Asset> getAssets(
			long groupId, long[] classNameIds, long[] tagIds,
			long[] notTagIds, boolean andOperator,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate,
			int start, int end)
		throws SystemException {

		if ((tagIds.length == 0) && (notTagIds.length == 0)) {
			return assetFinder.findAssets(
				groupId, classNameIds, null, null, null, null,
				excludeZeroViewCount, publishDate, expirationDate, start, end);
		}
		else if (andOperator) {
			return assetFinder.findByAndTagIds(
				groupId, classNameIds, tagIds, notTagIds, null, null, null,
				null, excludeZeroViewCount, publishDate, expirationDate, start,
				end);
		}
		else {
			return assetFinder.findByOrTagIds(
				groupId, classNameIds, tagIds, notTagIds, null, null, null,
				null, excludeZeroViewCount, publishDate, expirationDate, start,
				end);
		}
	}

	public List<Asset> getAssets(
			long[] tagIds, long[] notTagIds, boolean andOperator,
			String orderByCol1, String orderByCol2, String orderByType1,
			String orderByType2, boolean excludeZeroViewCount, Date publishDate,
			Date expirationDate, int start, int end)
		throws SystemException {

		return getAssets(
			0, new long[0], tagIds, notTagIds, andOperator,
			excludeZeroViewCount, publishDate, expirationDate, start, end);
	}

	public List<Asset> getAssets(
			long groupId, long[] classNameIds, long[] tagIds,
			long[] notTagIds, boolean andOperator, String orderByCol1,
			String orderByCol2, String orderByType1, String orderByType2,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate,
			int start, int end)
		throws SystemException {

		if ((tagIds.length == 0) && (notTagIds.length == 0)) {
			return assetFinder.findAssets(
				groupId, classNameIds, orderByCol1, orderByCol2, orderByType1,
				orderByType2, excludeZeroViewCount, publishDate, expirationDate,
				start, end);
		}
		else if (andOperator) {
			return assetFinder.findByAndTagIds(
				groupId, classNameIds, tagIds, notTagIds, orderByCol1,
				orderByCol2, orderByType1, orderByType2, excludeZeroViewCount,
				publishDate, expirationDate, start, end);
		}
		else {
			return assetFinder.findByOrTagIds(
				groupId, classNameIds, tagIds, notTagIds, orderByCol1,
				orderByCol2, orderByType1, orderByType2, excludeZeroViewCount,
				publishDate, expirationDate, start, end);
		}
	}

	public int getAssetsCount(
			long[] tagIds, long[] notTagIds, boolean andOperator,
			boolean excludeZeroViewCount)
		throws SystemException {

		return getAssetsCount(
			0, new long[0], tagIds, notTagIds, andOperator,
			excludeZeroViewCount, null, null);
	}

	public int getAssetsCount(
			long groupId, long[] tagIds, long[] notTagIds,
			boolean andOperator, boolean excludeZeroViewCount)
		throws SystemException {

		return getAssetsCount(
			groupId, new long[0], tagIds, notTagIds, andOperator,
			excludeZeroViewCount, null, null);
	}

	public int getAssetsCount(
			long[] tagIds, long[] notTagIds, boolean andOperator,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate)
		throws SystemException {

		return getAssetsCount(
			0, new long[0], tagIds, notTagIds, andOperator,
			excludeZeroViewCount, publishDate, expirationDate);
	}

	public int getAssetsCount(
			long groupId, long[] classNameIds, long[] tagIds,
			long[] notTagIds, boolean andOperator,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate)
		throws SystemException {

		if ((tagIds.length == 0) && (notTagIds.length == 0)) {
			return assetFinder.countAssets(
				groupId, classNameIds, excludeZeroViewCount, publishDate,
				expirationDate);
		}
		else if (andOperator) {
			return assetFinder.countByAndTagIds(
				groupId, classNameIds, tagIds, notTagIds,
				excludeZeroViewCount, publishDate, expirationDate);
		}
		else {
			return assetFinder.countByOrTagIds(
				groupId, classNameIds, tagIds, notTagIds,
				excludeZeroViewCount, publishDate, expirationDate);
		}
	}

	public AssetDisplay[] getCompanyAssetDisplays(
			long companyId, int start, int end, String languageId)
		throws SystemException {

		return getAssetDisplays(
			getCompanyAssets(companyId, start, end), languageId);
	}

	public List<Asset> getCompanyAssets(long companyId, int start, int end)
		throws SystemException {

		return assetPersistence.findByCompanyId(companyId, start, end);
	}

	public int getCompanyAssetsCount(long companyId) throws SystemException {
		return assetPersistence.countByCompanyId(companyId);
	}

	public List<Asset> getTopViewedAssets(
			String className, boolean asc, int start, int end)
		throws SystemException {

		return getTopViewedAssets(new String[] {className}, asc, start, end);
	}

	public List<Asset> getTopViewedAssets(
			String[] className, boolean asc, int start, int end)
		throws SystemException {

		long[] classNameIds = new long[className.length];

		for (int i = 0; i < className.length; i++) {
			classNameIds[i] = PortalUtil.getClassNameId(className[i]);
		}

		return assetFinder.findByViewCount(classNameIds, asc, start, end);
	}

	public Asset incrementViewCounter(String className, long classPK)
		throws SystemException {

		if (classPK <= 0) {
			return null;
		}

		long classNameId = PortalUtil.getClassNameId(className);

		Asset asset = assetPersistence.fetchByC_C(classNameId, classPK);

		if (asset != null) {
			asset.setViewCount(asset.getViewCount() + 1);

			assetPersistence.update(asset, false);
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
						AssetUtil.ASSET_TYPE_PORTLET_IDS) {

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
				searchQuery.addTerm(Field.ASSET_TAG_NAMES, keywords);
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

	public AssetDisplay[] searchAssetDisplays(
			long companyId, String portletId, String keywords,
			String languageId, int start, int end)
		throws SystemException {

		List<Asset> assets = new ArrayList<Asset>();

		Hits hits = search(companyId, portletId, keywords, start, end);

		List<Document> hitsList = hits.toList();

		for (Document doc : hitsList) {
			try {
				Asset asset = getAsset(doc);

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

	public Asset updateAsset(
			long userId, long groupId, String className, long classPK,
			long[] categoryIds, String[] tagNames)
		throws PortalException, SystemException {

		return updateAsset(
			userId, groupId, className, classPK, categoryIds, tagNames,
			true, null, null, null, null, null, null, null, null, null, 0, 0,
			null, false);
	}

	public Asset updateAsset(
			long userId, long groupId, String className, long classPK,
			long[] categoryIds, String[] tagNames, boolean visible,
			Date startDate, Date endDate, Date publishDate, Date expirationDate,
			String mimeType, String title, String description, String summary,
			String url, int height, int width, Integer priority, boolean sync)
		throws PortalException, SystemException {

		// Asset

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);

		title = StringUtil.shorten(title, 300, StringPool.BLANK);
		Date now = new Date();

		validate(className, tagNames);

		Asset asset = assetPersistence.fetchByC_C(classNameId, classPK);

		if (asset == null) {
			long assetId = counterLocalService.increment();

			asset = assetPersistence.create(assetId);

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

		// Categories

		if (categoryIds != null) {
			assetPersistence.setAssetCategories(
				asset.getAssetId(), categoryIds);
		}

		// Tags

		if (tagNames != null) {
			List<AssetTag> tags = new ArrayList<AssetTag>(tagNames.length);

			for (String tagName : tagNames) {
				AssetTag tag = null;

				try {
					tag = assetTagLocalService.getTag(groupId, tagName);
				}
				catch (NoSuchTagException nste) {
					ServiceContext serviceContext = new ServiceContext();

					serviceContext.setAddCommunityPermissions(true);
					serviceContext.setAddGuestPermissions(true);
					serviceContext.setScopeGroupId(groupId);

					tag = assetTagLocalService.addTag(
						user.getUserId(), tagName,
						PropsValues.ASSET_TAGS_PROPERTIES_DEFAULT,
						serviceContext);
				}

				if (tag != null) {
					tags.add(tag);
				}
			}

			assetPersistence.setAssetTags(asset.getAssetId(), tags);
		}

		// Update asset after tags so that asset listeners have access the
		// saved tags

		if ((categoryIds != null) || (tagNames != null)) {
			assetPersistence.update(asset, false);
		}

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

	public Asset updateVisible(
			String className, long classPK, boolean visible)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		Asset asset = assetPersistence.findByC_C(classNameId, classPK);

		asset.setVisible(visible);

		assetPersistence.update(asset, false);

		return asset;
	}

	public void validate(String className, String[] tagNames)
		throws PortalException {

		if (tagNames == null) {
			return;
		}

		AssetValidator validator = (AssetValidator)InstancePool.get(
			PropsValues.ASSET_VALIDATOR);

		validator.validate(className, tagNames);
	}

	protected Asset getAsset(Document doc)
		throws PortalException, SystemException {

		String portletId = GetterUtil.getString(doc.get(Field.PORTLET_ID));

		if (portletId.equals(PortletKeys.BLOGS)) {
			long entryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			long classNameId = PortalUtil.getClassNameId(
				BlogsEntry.class.getName());
			long classPK = entryId;

			return assetPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.BOOKMARKS)) {
			long entryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			long classNameId = PortalUtil.getClassNameId(
				BookmarksEntry.class.getName());
			long classPK = entryId;

			return assetPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY)) {
			long folderId = GetterUtil.getLong(doc.get("repositoryId"));
			String name = doc.get("path");

			DLFileEntry fileEntry = dlFileEntryLocalService.getFileEntry(
				folderId, name);

			long classNameId = PortalUtil.getClassNameId(
				DLFileEntry.class.getName());
			long classPK = fileEntry.getFileEntryId();

			return assetPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.IMAGE_GALLERY)) {
			long imageId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			long classNameId = PortalUtil.getClassNameId(
				IGImage.class.getName());
			long classPK = imageId;

			return assetPersistence.findByC_C(classNameId, classPK);
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

			return assetPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.MESSAGE_BOARDS)) {
			long messageId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			long classNameId = PortalUtil.getClassNameId(
				MBMessage.class.getName());
			long classPK = messageId;

			return assetPersistence.findByC_C(classNameId, classPK);
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

			return assetPersistence.findByC_C(classNameId, classPK);
		}

		return null;
	}

	protected AssetDisplay[] getAssetDisplays(
			List<Asset> assets, String languageId)
		throws SystemException {

		AssetDisplay[] assetDisplays = new AssetDisplay[assets.size()];

		for (int i = 0; i < assets.size(); i++) {
			Asset asset = assets.get(i);

			String className = PortalUtil.getClassName(asset.getClassNameId());
			String portletId = PortalUtil.getClassNamePortletId(className);
			String portletTitle = PortalUtil.getPortletTitle(
				portletId, asset.getCompanyId(), languageId);

			List<AssetCategory> categoryList =
				assetPersistence.getAssetCategories(asset.getAssetId());

			String categoryIdsString = ListUtil.toString(
				categoryList, "assetCategoryId", StringPool.COMMA);
			long[] categoryIds = StringUtil.split(
				categoryIdsString, StringPool.COMMA, 0L);

			List<AssetTag> tagList = assetPersistence.getAssetTags(
				asset.getAssetId());

			String tagNames = ListUtil.toString(tagList, "name", ", ");

			AssetDisplay assetDisplay = new AssetDisplay();

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
			assetDisplay.setCategoryIds(categoryIds);
			assetDisplay.setTagNames(tagNames);

			assetDisplays[i] = assetDisplay;
		}

		return assetDisplays;
	}

	protected AssetType getAssetType(String className, String languageId) {
		long companyId = PortalInstances.getDefaultCompanyId();

		long classNameId = PortalUtil.getClassNameId(className);

		String portletId = PortalUtil.getClassNamePortletId(className);
		String portletTitle = PortalUtil.getPortletTitle(
			portletId, companyId, languageId);

		AssetType assetType = new AssetType();

		assetType.setClassNameId(classNameId);
		assetType.setClassName(className);
		assetType.setPortletId(portletId);
		assetType.setPortletTitle(portletTitle);

		return assetType;
	}

	private static Log _log =
		LogFactoryUtil.getLog(AssetLocalServiceImpl.class);

}