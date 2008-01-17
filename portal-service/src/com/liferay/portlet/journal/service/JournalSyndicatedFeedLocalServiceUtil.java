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

package com.liferay.portlet.journal.service;


/**
 * <a href="JournalSyndicatedFeedLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.journal.service.JournalSyndicatedFeedLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.journal.service.JournalSyndicatedFeedLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.service.JournalSyndicatedFeedLocalService
 * @see com.liferay.portlet.journal.service.JournalSyndicatedFeedLocalServiceFactory
 *
 */
public class JournalSyndicatedFeedLocalServiceUtil {
	public static com.liferay.portlet.journal.model.JournalSyndicatedFeed addJournalSyndicatedFeed(
		com.liferay.portlet.journal.model.JournalSyndicatedFeed model)
		throws com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.addJournalSyndicatedFeed(model);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.dynamicQuery(queryInitializer,
			begin, end);
	}

	public static com.liferay.portlet.journal.model.JournalSyndicatedFeed updateJournalSyndicatedFeed(
		com.liferay.portlet.journal.model.JournalSyndicatedFeed model)
		throws com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.updateJournalSyndicatedFeed(model);
	}

	public static com.liferay.portlet.journal.service.persistence.JournalArticlePersistence getJournalArticlePersistence() {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getJournalArticlePersistence();
	}

	public static void setJournalArticlePersistence(
		com.liferay.portlet.journal.service.persistence.JournalArticlePersistence journalArticlePersistence) {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.setJournalArticlePersistence(journalArticlePersistence);
	}

	public static com.liferay.portlet.journal.service.persistence.JournalArticleFinder getJournalArticleFinder() {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getJournalArticleFinder();
	}

	public static void setJournalArticleFinder(
		com.liferay.portlet.journal.service.persistence.JournalArticleFinder journalArticleFinder) {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.setJournalArticleFinder(journalArticleFinder);
	}

	public static com.liferay.portlet.journal.service.persistence.JournalArticleImagePersistence getJournalArticleImagePersistence() {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getJournalArticleImagePersistence();
	}

	public static void setJournalArticleImagePersistence(
		com.liferay.portlet.journal.service.persistence.JournalArticleImagePersistence journalArticleImagePersistence) {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.setJournalArticleImagePersistence(journalArticleImagePersistence);
	}

	public static com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence getJournalArticleResourcePersistence() {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getJournalArticleResourcePersistence();
	}

	public static void setJournalArticleResourcePersistence(
		com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence journalArticleResourcePersistence) {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.setJournalArticleResourcePersistence(journalArticleResourcePersistence);
	}

	public static com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence getJournalContentSearchPersistence() {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getJournalContentSearchPersistence();
	}

	public static void setJournalContentSearchPersistence(
		com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence journalContentSearchPersistence) {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.setJournalContentSearchPersistence(journalContentSearchPersistence);
	}

	public static com.liferay.portlet.journal.service.persistence.JournalStructurePersistence getJournalStructurePersistence() {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getJournalStructurePersistence();
	}

	public static void setJournalStructurePersistence(
		com.liferay.portlet.journal.service.persistence.JournalStructurePersistence journalStructurePersistence) {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.setJournalStructurePersistence(journalStructurePersistence);
	}

	public static com.liferay.portlet.journal.service.persistence.JournalStructureFinder getJournalStructureFinder() {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getJournalStructureFinder();
	}

	public static void setJournalStructureFinder(
		com.liferay.portlet.journal.service.persistence.JournalStructureFinder journalStructureFinder) {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.setJournalStructureFinder(journalStructureFinder);
	}

	public static com.liferay.portlet.journal.service.persistence.JournalSyndicatedFeedPersistence getJournalSyndicatedFeedPersistence() {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getJournalSyndicatedFeedPersistence();
	}

	public static void setJournalSyndicatedFeedPersistence(
		com.liferay.portlet.journal.service.persistence.JournalSyndicatedFeedPersistence journalSyndicatedFeedPersistence) {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.setJournalSyndicatedFeedPersistence(journalSyndicatedFeedPersistence);
	}

	public static com.liferay.portlet.journal.service.persistence.JournalSyndicatedFeedFinder getJournalSyndicatedFeedFinder() {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getJournalSyndicatedFeedFinder();
	}

	public static void setJournalSyndicatedFeedFinder(
		com.liferay.portlet.journal.service.persistence.JournalSyndicatedFeedFinder journalSyndicatedFeedFinder) {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.setJournalSyndicatedFeedFinder(journalSyndicatedFeedFinder);
	}

	public static com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence getJournalTemplatePersistence() {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getJournalTemplatePersistence();
	}

	public static void setJournalTemplatePersistence(
		com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence journalTemplatePersistence) {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.setJournalTemplatePersistence(journalTemplatePersistence);
	}

	public static com.liferay.portlet.journal.service.persistence.JournalTemplateFinder getJournalTemplateFinder() {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getJournalTemplateFinder();
	}

	public static void setJournalTemplateFinder(
		com.liferay.portlet.journal.service.persistence.JournalTemplateFinder journalTemplateFinder) {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.setJournalTemplateFinder(journalTemplateFinder);
	}

	public static com.liferay.portal.service.persistence.ResourcePersistence getResourcePersistence() {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getResourcePersistence();
	}

	public static void setResourcePersistence(
		com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence) {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.setResourcePersistence(resourcePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourceFinder getResourceFinder() {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getResourceFinder();
	}

	public static void setResourceFinder(
		com.liferay.portal.service.persistence.ResourceFinder resourceFinder) {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.setResourceFinder(resourceFinder);
	}

	public static com.liferay.portal.service.persistence.UserPersistence getUserPersistence() {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getUserPersistence();
	}

	public static void setUserPersistence(
		com.liferay.portal.service.persistence.UserPersistence userPersistence) {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.setUserPersistence(userPersistence);
	}

	public static com.liferay.portal.service.persistence.UserFinder getUserFinder() {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getUserFinder();
	}

	public static void setUserFinder(
		com.liferay.portal.service.persistence.UserFinder userFinder) {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.setUserFinder(userFinder);
	}

	public static void afterPropertiesSet() {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.afterPropertiesSet();
	}

	public static com.liferay.portlet.journal.model.JournalSyndicatedFeed addSyndicatedFeed(
		long userId, long plid, java.lang.String feedId, boolean autoFeedId,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.lang.String rendererTemplateId,
		int delta, java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.addSyndicatedFeed(userId,
			plid, feedId, autoFeedId, name, description, type, structureId,
			templateId, rendererTemplateId, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion, addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalSyndicatedFeed addSyndicatedFeed(
		long userId, long plid, java.lang.String feedId, boolean autoFeedId,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.lang.String rendererTemplateId,
		int delta, java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.addSyndicatedFeed(userId,
			plid, feedId, autoFeedId, name, description, type, structureId,
			templateId, rendererTemplateId, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion, communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalSyndicatedFeed addSyndicatedFeed(
		java.lang.String uuid, long userId, long plid, java.lang.String feedId,
		boolean autoFeedId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String rendererTemplateId, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.addSyndicatedFeed(uuid,
			userId, plid, feedId, autoFeedId, name, description, type,
			structureId, templateId, rendererTemplateId, delta, orderByCol,
			orderByType, targetLayoutFriendlyUrl, targetPortletId,
			contentField, feedType, feedVersion, addCommunityPermissions,
			addGuestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalSyndicatedFeed addSyndicatedFeed(
		java.lang.String uuid, long userId, long plid, java.lang.String feedId,
		boolean autoFeedId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String rendererTemplateId, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.addSyndicatedFeed(uuid,
			userId, plid, feedId, autoFeedId, name, description, type,
			structureId, templateId, rendererTemplateId, delta, orderByCol,
			orderByType, targetLayoutFriendlyUrl, targetPortletId,
			contentField, feedType, feedVersion, communityPermissions,
			guestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalSyndicatedFeed addSyndicatedFeed(
		java.lang.String uuid, long userId, long plid, java.lang.String feedId,
		boolean autoFeedId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String rendererTemplateId, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.addSyndicatedFeed(uuid,
			userId, plid, feedId, autoFeedId, name, description, type,
			structureId, templateId, rendererTemplateId, delta, orderByCol,
			orderByType, targetLayoutFriendlyUrl, targetPortletId,
			contentField, feedType, feedVersion, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalSyndicatedFeed addSyndicatedFeedToGroup(
		java.lang.String uuid, long userId, long groupId,
		java.lang.String feedId, boolean autoFeedId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String rendererTemplateId, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.addSyndicatedFeedToGroup(uuid,
			userId, groupId, feedId, autoFeedId, name, description, type,
			structureId, templateId, rendererTemplateId, delta, orderByCol,
			orderByType, targetLayoutFriendlyUrl, targetPortletId,
			contentField, feedType, feedVersion, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public static void addSyndicatedFeedResources(long synFeedId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.addSyndicatedFeedResources(synFeedId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addSyndicatedFeedResources(
		com.liferay.portlet.journal.model.JournalSyndicatedFeed synFeed,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.addSyndicatedFeedResources(synFeed,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addSyndicatedFeedResources(long synFeedId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.addSyndicatedFeedResources(synFeedId,
			communityPermissions, guestPermissions);
	}

	public static void addSyndicatedFeedResources(
		com.liferay.portlet.journal.model.JournalSyndicatedFeed synFeed,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.addSyndicatedFeedResources(synFeed,
			communityPermissions, guestPermissions);
	}

	public static void deleteSyndicatedFeed(long synFeedId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.deleteSyndicatedFeed(synFeedId);
	}

	public static void deleteSyndicatedFeed(long groupId,
		java.lang.String feedId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.deleteSyndicatedFeed(groupId, feedId);
	}

	public static void deleteSyndicatedFeed(
		com.liferay.portlet.journal.model.JournalSyndicatedFeed synFeed)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		journalSyndicatedFeedLocalService.deleteSyndicatedFeed(synFeed);
	}

	public static com.liferay.portlet.journal.model.JournalSyndicatedFeed getSyndicatedFeed(
		long synFeedId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getSyndicatedFeed(synFeedId);
	}

	public static com.liferay.portlet.journal.model.JournalSyndicatedFeed getSyndicatedFeed(
		long groupId, java.lang.String feedId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getSyndicatedFeed(groupId,
			feedId);
	}

	public static java.util.List getSyndicatedFeeds()
		throws com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getSyndicatedFeeds();
	}

	public static java.util.List getSyndicatedFeeds(long groupId)
		throws com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getSyndicatedFeeds(groupId);
	}

	public static java.util.List getSyndicatedFeeds(long groupId, int begin,
		int end) throws com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getSyndicatedFeeds(groupId,
			begin, end);
	}

	public static int getSyndicatedFeedsCount(long groupId)
		throws com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.getSyndicatedFeedsCount(groupId);
	}

	public static java.util.List search(long companyId, long groupId,
		java.lang.String keywords, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.search(companyId, groupId,
			keywords, begin, end, obc);
	}

	public static java.util.List search(long companyId, long groupId,
		java.lang.String feedId, java.lang.String name,
		java.lang.String description, boolean andOperator, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.search(companyId, groupId,
			feedId, name, description, andOperator, begin, end, obc);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String keywords) throws com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.searchCount(companyId,
			groupId, keywords);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String feedId, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.searchCount(companyId,
			groupId, feedId, name, description, andOperator);
	}

	public static com.liferay.portlet.journal.model.JournalSyndicatedFeed updateSyndicatedFeed(
		long groupId, java.lang.String feedId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String rendererTemplateId, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalSyndicatedFeedLocalService journalSyndicatedFeedLocalService = JournalSyndicatedFeedLocalServiceFactory.getService();

		return journalSyndicatedFeedLocalService.updateSyndicatedFeed(groupId,
			feedId, name, description, type, structureId, templateId,
			rendererTemplateId, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion);
	}
}