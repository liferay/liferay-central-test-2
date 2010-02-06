/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.CompanyService;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupService;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.base.PrincipalBean;
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.asset.service.AssetCategoryLocalService;
import com.liferay.portlet.asset.service.AssetCategoryPropertyLocalService;
import com.liferay.portlet.asset.service.AssetCategoryPropertyService;
import com.liferay.portlet.asset.service.AssetCategoryService;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.asset.service.AssetEntryService;
import com.liferay.portlet.asset.service.AssetTagLocalService;
import com.liferay.portlet.asset.service.AssetTagPropertyLocalService;
import com.liferay.portlet.asset.service.AssetTagPropertyService;
import com.liferay.portlet.asset.service.AssetTagService;
import com.liferay.portlet.asset.service.AssetTagStatsLocalService;
import com.liferay.portlet.asset.service.AssetVocabularyLocalService;
import com.liferay.portlet.asset.service.AssetVocabularyService;
import com.liferay.portlet.asset.service.persistence.AssetCategoryFinder;
import com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyFinder;
import com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyPersistence;
import com.liferay.portlet.asset.service.persistence.AssetEntryFinder;
import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagFinder;
import com.liferay.portlet.asset.service.persistence.AssetTagPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagPropertyFinder;
import com.liferay.portlet.asset.service.persistence.AssetTagPropertyKeyFinder;
import com.liferay.portlet.asset.service.persistence.AssetTagPropertyPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagStatsPersistence;
import com.liferay.portlet.asset.service.persistence.AssetVocabularyPersistence;
import com.liferay.portlet.blogs.service.BlogsEntryLocalService;
import com.liferay.portlet.blogs.service.BlogsEntryService;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryFinder;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.portlet.bookmarks.service.BookmarksEntryService;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryFinder;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryPersistence;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderService;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence;
import com.liferay.portlet.journal.service.JournalArticleLocalService;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalService;
import com.liferay.portlet.journal.service.JournalArticleService;
import com.liferay.portlet.journal.service.persistence.JournalArticleFinder;
import com.liferay.portlet.journal.service.persistence.JournalArticlePersistence;
import com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBMessageService;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFinder;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.wiki.service.WikiPageLocalService;
import com.liferay.portlet.wiki.service.WikiPageResourceLocalService;
import com.liferay.portlet.wiki.service.WikiPageService;
import com.liferay.portlet.wiki.service.persistence.WikiPageFinder;
import com.liferay.portlet.wiki.service.persistence.WikiPagePersistence;
import com.liferay.portlet.wiki.service.persistence.WikiPageResourcePersistence;

/**
 * <a href="AssetEntryServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class AssetEntryServiceBaseImpl extends PrincipalBean
	implements AssetEntryService {
	public AssetCategoryLocalService getAssetCategoryLocalService() {
		return assetCategoryLocalService;
	}

	public void setAssetCategoryLocalService(
		AssetCategoryLocalService assetCategoryLocalService) {
		this.assetCategoryLocalService = assetCategoryLocalService;
	}

	public AssetCategoryService getAssetCategoryService() {
		return assetCategoryService;
	}

	public void setAssetCategoryService(
		AssetCategoryService assetCategoryService) {
		this.assetCategoryService = assetCategoryService;
	}

	public AssetCategoryPersistence getAssetCategoryPersistence() {
		return assetCategoryPersistence;
	}

	public void setAssetCategoryPersistence(
		AssetCategoryPersistence assetCategoryPersistence) {
		this.assetCategoryPersistence = assetCategoryPersistence;
	}

	public AssetCategoryFinder getAssetCategoryFinder() {
		return assetCategoryFinder;
	}

	public void setAssetCategoryFinder(AssetCategoryFinder assetCategoryFinder) {
		this.assetCategoryFinder = assetCategoryFinder;
	}

	public AssetCategoryPropertyLocalService getAssetCategoryPropertyLocalService() {
		return assetCategoryPropertyLocalService;
	}

	public void setAssetCategoryPropertyLocalService(
		AssetCategoryPropertyLocalService assetCategoryPropertyLocalService) {
		this.assetCategoryPropertyLocalService = assetCategoryPropertyLocalService;
	}

	public AssetCategoryPropertyService getAssetCategoryPropertyService() {
		return assetCategoryPropertyService;
	}

	public void setAssetCategoryPropertyService(
		AssetCategoryPropertyService assetCategoryPropertyService) {
		this.assetCategoryPropertyService = assetCategoryPropertyService;
	}

	public AssetCategoryPropertyPersistence getAssetCategoryPropertyPersistence() {
		return assetCategoryPropertyPersistence;
	}

	public void setAssetCategoryPropertyPersistence(
		AssetCategoryPropertyPersistence assetCategoryPropertyPersistence) {
		this.assetCategoryPropertyPersistence = assetCategoryPropertyPersistence;
	}

	public AssetCategoryPropertyFinder getAssetCategoryPropertyFinder() {
		return assetCategoryPropertyFinder;
	}

	public void setAssetCategoryPropertyFinder(
		AssetCategoryPropertyFinder assetCategoryPropertyFinder) {
		this.assetCategoryPropertyFinder = assetCategoryPropertyFinder;
	}

	public AssetEntryLocalService getAssetEntryLocalService() {
		return assetEntryLocalService;
	}

	public void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {
		this.assetEntryLocalService = assetEntryLocalService;
	}

	public AssetEntryService getAssetEntryService() {
		return assetEntryService;
	}

	public void setAssetEntryService(AssetEntryService assetEntryService) {
		this.assetEntryService = assetEntryService;
	}

	public AssetEntryPersistence getAssetEntryPersistence() {
		return assetEntryPersistence;
	}

	public void setAssetEntryPersistence(
		AssetEntryPersistence assetEntryPersistence) {
		this.assetEntryPersistence = assetEntryPersistence;
	}

	public AssetEntryFinder getAssetEntryFinder() {
		return assetEntryFinder;
	}

	public void setAssetEntryFinder(AssetEntryFinder assetEntryFinder) {
		this.assetEntryFinder = assetEntryFinder;
	}

	public AssetTagLocalService getAssetTagLocalService() {
		return assetTagLocalService;
	}

	public void setAssetTagLocalService(
		AssetTagLocalService assetTagLocalService) {
		this.assetTagLocalService = assetTagLocalService;
	}

	public AssetTagService getAssetTagService() {
		return assetTagService;
	}

	public void setAssetTagService(AssetTagService assetTagService) {
		this.assetTagService = assetTagService;
	}

	public AssetTagPersistence getAssetTagPersistence() {
		return assetTagPersistence;
	}

	public void setAssetTagPersistence(AssetTagPersistence assetTagPersistence) {
		this.assetTagPersistence = assetTagPersistence;
	}

	public AssetTagFinder getAssetTagFinder() {
		return assetTagFinder;
	}

	public void setAssetTagFinder(AssetTagFinder assetTagFinder) {
		this.assetTagFinder = assetTagFinder;
	}

	public AssetTagPropertyLocalService getAssetTagPropertyLocalService() {
		return assetTagPropertyLocalService;
	}

	public void setAssetTagPropertyLocalService(
		AssetTagPropertyLocalService assetTagPropertyLocalService) {
		this.assetTagPropertyLocalService = assetTagPropertyLocalService;
	}

	public AssetTagPropertyService getAssetTagPropertyService() {
		return assetTagPropertyService;
	}

	public void setAssetTagPropertyService(
		AssetTagPropertyService assetTagPropertyService) {
		this.assetTagPropertyService = assetTagPropertyService;
	}

	public AssetTagPropertyPersistence getAssetTagPropertyPersistence() {
		return assetTagPropertyPersistence;
	}

	public void setAssetTagPropertyPersistence(
		AssetTagPropertyPersistence assetTagPropertyPersistence) {
		this.assetTagPropertyPersistence = assetTagPropertyPersistence;
	}

	public AssetTagPropertyFinder getAssetTagPropertyFinder() {
		return assetTagPropertyFinder;
	}

	public void setAssetTagPropertyFinder(
		AssetTagPropertyFinder assetTagPropertyFinder) {
		this.assetTagPropertyFinder = assetTagPropertyFinder;
	}

	public AssetTagPropertyKeyFinder getAssetTagPropertyKeyFinder() {
		return assetTagPropertyKeyFinder;
	}

	public void setAssetTagPropertyKeyFinder(
		AssetTagPropertyKeyFinder assetTagPropertyKeyFinder) {
		this.assetTagPropertyKeyFinder = assetTagPropertyKeyFinder;
	}

	public AssetTagStatsLocalService getAssetTagStatsLocalService() {
		return assetTagStatsLocalService;
	}

	public void setAssetTagStatsLocalService(
		AssetTagStatsLocalService assetTagStatsLocalService) {
		this.assetTagStatsLocalService = assetTagStatsLocalService;
	}

	public AssetTagStatsPersistence getAssetTagStatsPersistence() {
		return assetTagStatsPersistence;
	}

	public void setAssetTagStatsPersistence(
		AssetTagStatsPersistence assetTagStatsPersistence) {
		this.assetTagStatsPersistence = assetTagStatsPersistence;
	}

	public AssetVocabularyLocalService getAssetVocabularyLocalService() {
		return assetVocabularyLocalService;
	}

	public void setAssetVocabularyLocalService(
		AssetVocabularyLocalService assetVocabularyLocalService) {
		this.assetVocabularyLocalService = assetVocabularyLocalService;
	}

	public AssetVocabularyService getAssetVocabularyService() {
		return assetVocabularyService;
	}

	public void setAssetVocabularyService(
		AssetVocabularyService assetVocabularyService) {
		this.assetVocabularyService = assetVocabularyService;
	}

	public AssetVocabularyPersistence getAssetVocabularyPersistence() {
		return assetVocabularyPersistence;
	}

	public void setAssetVocabularyPersistence(
		AssetVocabularyPersistence assetVocabularyPersistence) {
		this.assetVocabularyPersistence = assetVocabularyPersistence;
	}

	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	public CounterService getCounterService() {
		return counterService;
	}

	public void setCounterService(CounterService counterService) {
		this.counterService = counterService;
	}

	public CompanyLocalService getCompanyLocalService() {
		return companyLocalService;
	}

	public void setCompanyLocalService(CompanyLocalService companyLocalService) {
		this.companyLocalService = companyLocalService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public CompanyPersistence getCompanyPersistence() {
		return companyPersistence;
	}

	public void setCompanyPersistence(CompanyPersistence companyPersistence) {
		this.companyPersistence = companyPersistence;
	}

	public GroupLocalService getGroupLocalService() {
		return groupLocalService;
	}

	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public GroupPersistence getGroupPersistence() {
		return groupPersistence;
	}

	public void setGroupPersistence(GroupPersistence groupPersistence) {
		this.groupPersistence = groupPersistence;
	}

	public GroupFinder getGroupFinder() {
		return groupFinder;
	}

	public void setGroupFinder(GroupFinder groupFinder) {
		this.groupFinder = groupFinder;
	}

	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourcePersistence getResourcePersistence() {
		return resourcePersistence;
	}

	public void setResourcePersistence(ResourcePersistence resourcePersistence) {
		this.resourcePersistence = resourcePersistence;
	}

	public ResourceFinder getResourceFinder() {
		return resourceFinder;
	}

	public void setResourceFinder(ResourceFinder resourceFinder) {
		this.resourceFinder = resourceFinder;
	}

	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public UserFinder getUserFinder() {
		return userFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public BlogsEntryLocalService getBlogsEntryLocalService() {
		return blogsEntryLocalService;
	}

	public void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {
		this.blogsEntryLocalService = blogsEntryLocalService;
	}

	public BlogsEntryService getBlogsEntryService() {
		return blogsEntryService;
	}

	public void setBlogsEntryService(BlogsEntryService blogsEntryService) {
		this.blogsEntryService = blogsEntryService;
	}

	public BlogsEntryPersistence getBlogsEntryPersistence() {
		return blogsEntryPersistence;
	}

	public void setBlogsEntryPersistence(
		BlogsEntryPersistence blogsEntryPersistence) {
		this.blogsEntryPersistence = blogsEntryPersistence;
	}

	public BlogsEntryFinder getBlogsEntryFinder() {
		return blogsEntryFinder;
	}

	public void setBlogsEntryFinder(BlogsEntryFinder blogsEntryFinder) {
		this.blogsEntryFinder = blogsEntryFinder;
	}

	public BookmarksEntryLocalService getBookmarksEntryLocalService() {
		return bookmarksEntryLocalService;
	}

	public void setBookmarksEntryLocalService(
		BookmarksEntryLocalService bookmarksEntryLocalService) {
		this.bookmarksEntryLocalService = bookmarksEntryLocalService;
	}

	public BookmarksEntryService getBookmarksEntryService() {
		return bookmarksEntryService;
	}

	public void setBookmarksEntryService(
		BookmarksEntryService bookmarksEntryService) {
		this.bookmarksEntryService = bookmarksEntryService;
	}

	public BookmarksEntryPersistence getBookmarksEntryPersistence() {
		return bookmarksEntryPersistence;
	}

	public void setBookmarksEntryPersistence(
		BookmarksEntryPersistence bookmarksEntryPersistence) {
		this.bookmarksEntryPersistence = bookmarksEntryPersistence;
	}

	public BookmarksEntryFinder getBookmarksEntryFinder() {
		return bookmarksEntryFinder;
	}

	public void setBookmarksEntryFinder(
		BookmarksEntryFinder bookmarksEntryFinder) {
		this.bookmarksEntryFinder = bookmarksEntryFinder;
	}

	public DLFileEntryLocalService getDLFileEntryLocalService() {
		return dlFileEntryLocalService;
	}

	public void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {
		this.dlFileEntryLocalService = dlFileEntryLocalService;
	}

	public DLFileEntryService getDLFileEntryService() {
		return dlFileEntryService;
	}

	public void setDLFileEntryService(DLFileEntryService dlFileEntryService) {
		this.dlFileEntryService = dlFileEntryService;
	}

	public DLFileEntryPersistence getDLFileEntryPersistence() {
		return dlFileEntryPersistence;
	}

	public void setDLFileEntryPersistence(
		DLFileEntryPersistence dlFileEntryPersistence) {
		this.dlFileEntryPersistence = dlFileEntryPersistence;
	}

	public DLFileEntryFinder getDLFileEntryFinder() {
		return dlFileEntryFinder;
	}

	public void setDLFileEntryFinder(DLFileEntryFinder dlFileEntryFinder) {
		this.dlFileEntryFinder = dlFileEntryFinder;
	}

	public DLFolderLocalService getDLFolderLocalService() {
		return dlFolderLocalService;
	}

	public void setDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {
		this.dlFolderLocalService = dlFolderLocalService;
	}

	public DLFolderService getDLFolderService() {
		return dlFolderService;
	}

	public void setDLFolderService(DLFolderService dlFolderService) {
		this.dlFolderService = dlFolderService;
	}

	public DLFolderPersistence getDLFolderPersistence() {
		return dlFolderPersistence;
	}

	public void setDLFolderPersistence(DLFolderPersistence dlFolderPersistence) {
		this.dlFolderPersistence = dlFolderPersistence;
	}

	public DLFolderFinder getDLFolderFinder() {
		return dlFolderFinder;
	}

	public void setDLFolderFinder(DLFolderFinder dlFolderFinder) {
		this.dlFolderFinder = dlFolderFinder;
	}

	public JournalArticleLocalService getJournalArticleLocalService() {
		return journalArticleLocalService;
	}

	public void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {
		this.journalArticleLocalService = journalArticleLocalService;
	}

	public JournalArticleService getJournalArticleService() {
		return journalArticleService;
	}

	public void setJournalArticleService(
		JournalArticleService journalArticleService) {
		this.journalArticleService = journalArticleService;
	}

	public JournalArticlePersistence getJournalArticlePersistence() {
		return journalArticlePersistence;
	}

	public void setJournalArticlePersistence(
		JournalArticlePersistence journalArticlePersistence) {
		this.journalArticlePersistence = journalArticlePersistence;
	}

	public JournalArticleFinder getJournalArticleFinder() {
		return journalArticleFinder;
	}

	public void setJournalArticleFinder(
		JournalArticleFinder journalArticleFinder) {
		this.journalArticleFinder = journalArticleFinder;
	}

	public JournalArticleResourceLocalService getJournalArticleResourceLocalService() {
		return journalArticleResourceLocalService;
	}

	public void setJournalArticleResourceLocalService(
		JournalArticleResourceLocalService journalArticleResourceLocalService) {
		this.journalArticleResourceLocalService = journalArticleResourceLocalService;
	}

	public JournalArticleResourcePersistence getJournalArticleResourcePersistence() {
		return journalArticleResourcePersistence;
	}

	public void setJournalArticleResourcePersistence(
		JournalArticleResourcePersistence journalArticleResourcePersistence) {
		this.journalArticleResourcePersistence = journalArticleResourcePersistence;
	}

	public MBMessageLocalService getMBMessageLocalService() {
		return mbMessageLocalService;
	}

	public void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {
		this.mbMessageLocalService = mbMessageLocalService;
	}

	public MBMessageService getMBMessageService() {
		return mbMessageService;
	}

	public void setMBMessageService(MBMessageService mbMessageService) {
		this.mbMessageService = mbMessageService;
	}

	public MBMessagePersistence getMBMessagePersistence() {
		return mbMessagePersistence;
	}

	public void setMBMessagePersistence(
		MBMessagePersistence mbMessagePersistence) {
		this.mbMessagePersistence = mbMessagePersistence;
	}

	public MBMessageFinder getMBMessageFinder() {
		return mbMessageFinder;
	}

	public void setMBMessageFinder(MBMessageFinder mbMessageFinder) {
		this.mbMessageFinder = mbMessageFinder;
	}

	public WikiPageLocalService getWikiPageLocalService() {
		return wikiPageLocalService;
	}

	public void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {
		this.wikiPageLocalService = wikiPageLocalService;
	}

	public WikiPageService getWikiPageService() {
		return wikiPageService;
	}

	public void setWikiPageService(WikiPageService wikiPageService) {
		this.wikiPageService = wikiPageService;
	}

	public WikiPagePersistence getWikiPagePersistence() {
		return wikiPagePersistence;
	}

	public void setWikiPagePersistence(WikiPagePersistence wikiPagePersistence) {
		this.wikiPagePersistence = wikiPagePersistence;
	}

	public WikiPageFinder getWikiPageFinder() {
		return wikiPageFinder;
	}

	public void setWikiPageFinder(WikiPageFinder wikiPageFinder) {
		this.wikiPageFinder = wikiPageFinder;
	}

	public WikiPageResourceLocalService getWikiPageResourceLocalService() {
		return wikiPageResourceLocalService;
	}

	public void setWikiPageResourceLocalService(
		WikiPageResourceLocalService wikiPageResourceLocalService) {
		this.wikiPageResourceLocalService = wikiPageResourceLocalService;
	}

	public WikiPageResourcePersistence getWikiPageResourcePersistence() {
		return wikiPageResourcePersistence;
	}

	public void setWikiPageResourcePersistence(
		WikiPageResourcePersistence wikiPageResourcePersistence) {
		this.wikiPageResourcePersistence = wikiPageResourcePersistence;
	}

	protected void runSQL(String sql) throws SystemException {
		try {
			DB db = DBFactoryUtil.getDB();

			db.runSQL(sql);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(name = "com.liferay.portlet.asset.service.AssetCategoryLocalService")
	protected AssetCategoryLocalService assetCategoryLocalService;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetCategoryService")
	protected AssetCategoryService assetCategoryService;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence")
	protected AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryFinder")
	protected AssetCategoryFinder assetCategoryFinder;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetCategoryPropertyLocalService")
	protected AssetCategoryPropertyLocalService assetCategoryPropertyLocalService;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetCategoryPropertyService")
	protected AssetCategoryPropertyService assetCategoryPropertyService;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyPersistence")
	protected AssetCategoryPropertyPersistence assetCategoryPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyFinder")
	protected AssetCategoryPropertyFinder assetCategoryPropertyFinder;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetEntryLocalService")
	protected AssetEntryLocalService assetEntryLocalService;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetEntryService")
	protected AssetEntryService assetEntryService;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetEntryPersistence")
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetEntryFinder")
	protected AssetEntryFinder assetEntryFinder;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetTagLocalService")
	protected AssetTagLocalService assetTagLocalService;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetTagService")
	protected AssetTagService assetTagService;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPersistence")
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagFinder")
	protected AssetTagFinder assetTagFinder;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetTagPropertyLocalService")
	protected AssetTagPropertyLocalService assetTagPropertyLocalService;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetTagPropertyService")
	protected AssetTagPropertyService assetTagPropertyService;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPropertyPersistence")
	protected AssetTagPropertyPersistence assetTagPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPropertyFinder")
	protected AssetTagPropertyFinder assetTagPropertyFinder;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPropertyKeyFinder")
	protected AssetTagPropertyKeyFinder assetTagPropertyKeyFinder;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetTagStatsLocalService")
	protected AssetTagStatsLocalService assetTagStatsLocalService;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagStatsPersistence")
	protected AssetTagStatsPersistence assetTagStatsPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetVocabularyLocalService")
	protected AssetVocabularyLocalService assetVocabularyLocalService;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetVocabularyService")
	protected AssetVocabularyService assetVocabularyService;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetVocabularyPersistence")
	protected AssetVocabularyPersistence assetVocabularyPersistence;
	@BeanReference(name = "com.liferay.counter.service.CounterLocalService")
	protected CounterLocalService counterLocalService;
	@BeanReference(name = "com.liferay.counter.service.CounterService")
	protected CounterService counterService;
	@BeanReference(name = "com.liferay.portal.service.CompanyLocalService")
	protected CompanyLocalService companyLocalService;
	@BeanReference(name = "com.liferay.portal.service.CompanyService")
	protected CompanyService companyService;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence")
	protected CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.GroupLocalService")
	protected GroupLocalService groupLocalService;
	@BeanReference(name = "com.liferay.portal.service.GroupService")
	protected GroupService groupService;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupFinder")
	protected GroupFinder groupFinder;
	@BeanReference(name = "com.liferay.portal.service.ResourceLocalService")
	protected ResourceLocalService resourceLocalService;
	@BeanReference(name = "com.liferay.portal.service.ResourceService")
	protected ResourceService resourceService;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceFinder")
	protected ResourceFinder resourceFinder;
	@BeanReference(name = "com.liferay.portal.service.UserLocalService")
	protected UserLocalService userLocalService;
	@BeanReference(name = "com.liferay.portal.service.UserService")
	protected UserService userService;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserFinder")
	protected UserFinder userFinder;
	@BeanReference(name = "com.liferay.portlet.blogs.service.BlogsEntryLocalService")
	protected BlogsEntryLocalService blogsEntryLocalService;
	@BeanReference(name = "com.liferay.portlet.blogs.service.BlogsEntryService")
	protected BlogsEntryService blogsEntryService;
	@BeanReference(name = "com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence")
	protected BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.blogs.service.persistence.BlogsEntryFinder")
	protected BlogsEntryFinder blogsEntryFinder;
	@BeanReference(name = "com.liferay.portlet.bookmarks.service.BookmarksEntryLocalService")
	protected BookmarksEntryLocalService bookmarksEntryLocalService;
	@BeanReference(name = "com.liferay.portlet.bookmarks.service.BookmarksEntryService")
	protected BookmarksEntryService bookmarksEntryService;
	@BeanReference(name = "com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryPersistence")
	protected BookmarksEntryPersistence bookmarksEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryFinder")
	protected BookmarksEntryFinder bookmarksEntryFinder;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService")
	protected DLFileEntryLocalService dlFileEntryLocalService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileEntryService")
	protected DLFileEntryService dlFileEntryService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence")
	protected DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryFinder")
	protected DLFileEntryFinder dlFileEntryFinder;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFolderLocalService")
	protected DLFolderLocalService dlFolderLocalService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFolderService")
	protected DLFolderService dlFolderService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence")
	protected DLFolderPersistence dlFolderPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFolderFinder")
	protected DLFolderFinder dlFolderFinder;
	@BeanReference(name = "com.liferay.portlet.journal.service.JournalArticleLocalService")
	protected JournalArticleLocalService journalArticleLocalService;
	@BeanReference(name = "com.liferay.portlet.journal.service.JournalArticleService")
	protected JournalArticleService journalArticleService;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticlePersistence")
	protected JournalArticlePersistence journalArticlePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticleFinder")
	protected JournalArticleFinder journalArticleFinder;
	@BeanReference(name = "com.liferay.portlet.journal.service.JournalArticleResourceLocalService")
	protected JournalArticleResourceLocalService journalArticleResourceLocalService;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence")
	protected JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBMessageLocalService")
	protected MBMessageLocalService mbMessageLocalService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBMessageService")
	protected MBMessageService mbMessageService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence")
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessageFinder")
	protected MBMessageFinder mbMessageFinder;
	@BeanReference(name = "com.liferay.portlet.wiki.service.WikiPageLocalService")
	protected WikiPageLocalService wikiPageLocalService;
	@BeanReference(name = "com.liferay.portlet.wiki.service.WikiPageService")
	protected WikiPageService wikiPageService;
	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiPagePersistence")
	protected WikiPagePersistence wikiPagePersistence;
	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiPageFinder")
	protected WikiPageFinder wikiPageFinder;
	@BeanReference(name = "com.liferay.portlet.wiki.service.WikiPageResourceLocalService")
	protected WikiPageResourceLocalService wikiPageResourceLocalService;
	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiPageResourcePersistence")
	protected WikiPageResourcePersistence wikiPageResourcePersistence;
}