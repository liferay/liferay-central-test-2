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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.DuplicateSyndicatedFeedIdException;
import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.NoSuchSyndicatedFeedException;
import com.liferay.portlet.journal.SyndicatedFeedContentFieldException;
import com.liferay.portlet.journal.SyndicatedFeedDescriptionException;
import com.liferay.portlet.journal.SyndicatedFeedIdException;
import com.liferay.portlet.journal.SyndicatedFeedNameException;
import com.liferay.portlet.journal.SyndicatedFeedTargetLayoutFriendlyUrlException;
import com.liferay.portlet.journal.SyndicatedFeedTargetPortletIdException;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalSyndicatedFeed;
import com.liferay.portlet.journal.model.impl.JournalSyndicatedFeedImpl;
import com.liferay.portlet.journal.service.base.JournalSyndicatedFeedLocalServiceBaseImpl;
import com.liferay.util.RSSUtil;

import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;

/**
 * <a href="JournalSyndicatedFeedLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalSyndicatedFeedLocalServiceImpl
	extends JournalSyndicatedFeedLocalServiceBaseImpl {

	public JournalSyndicatedFeed addSyndicatedFeed(
			long userId, long plid, String feedId, boolean autoFeedId,
			String name, String description, String type, String structureId,
			String templateId, String rendererTemplateId, int delta,
			String orderByCol, String orderByType,
			String targetLayoutFriendlyUrl, String targetPortletId,
			String contentField, String feedType, double feedVersion,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addSyndicatedFeed(
			null, userId, plid, feedId, autoFeedId, name, description, type,
			structureId, templateId, rendererTemplateId, delta, orderByCol,
			orderByType, targetLayoutFriendlyUrl, targetPortletId, contentField,
			feedType, feedVersion, Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public JournalSyndicatedFeed addSyndicatedFeed(
			long userId, long plid, String feedId, boolean autoFeedId,
			String name, String description, String type,
			String structureId, String templateId, String rendererTemplateId,
			int delta, String orderByCol, String orderByType,
			String targetLayoutFriendlyUrl, String targetPortletId,
			String contentField, String feedType, double feedVersion,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addSyndicatedFeed(
			null, userId, plid, feedId, autoFeedId, name, description, type,
			structureId, templateId, rendererTemplateId, delta, orderByCol,
			orderByType, targetLayoutFriendlyUrl, targetPortletId, contentField,
			feedType, feedVersion, null, null, communityPermissions,
			guestPermissions);
	}

	public JournalSyndicatedFeed addSyndicatedFeed(
			String uuid, long userId, long plid, String feedId,
			boolean autoFeedId, String name, String description, String type,
			String structureId, String templateId, String rendererTemplateId,
			int delta, String orderByCol, String orderByType,
			String targetLayoutFriendlyUrl, String targetPortletId,
			String contentField, String feedType, double feedVersion,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addSyndicatedFeed(
			uuid, userId, plid, feedId, autoFeedId, name, description, type,
			structureId, templateId, rendererTemplateId, delta, orderByCol,
			orderByType, targetLayoutFriendlyUrl, targetPortletId, contentField,
			feedType, feedVersion, Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public JournalSyndicatedFeed addSyndicatedFeed(
			String uuid, long userId, long plid, String feedId,
			boolean autoFeedId, String name, String description, String type,
			String structureId, String templateId, String rendererTemplateId,
			int delta, String orderByCol, String orderByType,
			String targetLayoutFriendlyUrl, String targetPortletId,
			String contentField, String feedType, double feedVersion,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addSyndicatedFeed(
			uuid, userId, plid, feedId, autoFeedId, name, description, type,
			structureId, templateId, rendererTemplateId, delta, orderByCol,
			orderByType,targetLayoutFriendlyUrl, targetPortletId, contentField,
			feedType, feedVersion, null, null, communityPermissions,
			guestPermissions);
	}

	public JournalSyndicatedFeed addSyndicatedFeed(
			String uuid, long userId, long plid, String feedId,
			boolean autoFeedId, String name, String description, String type,
			String structureId, String templateId, String rendererTemplateId,
			int delta, String orderByCol, String orderByType,
			String targetLayoutFriendlyUrl, String targetPortletId,
			String contentField, String feedType, double feedVersion,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		long groupId = PortalUtil.getPortletGroupId(plid);

		return addSyndicatedFeedToGroup(
			uuid, userId, groupId, feedId, autoFeedId, name, description, type,
			structureId, templateId, rendererTemplateId, delta, orderByCol,
			orderByType, targetLayoutFriendlyUrl, targetPortletId, contentField,
			feedType, feedVersion, addCommunityPermissions, addGuestPermissions,
			communityPermissions, guestPermissions);
	}

	public JournalSyndicatedFeed addSyndicatedFeedToGroup(
			String uuid, long userId, long groupId, String feedId,
			boolean autoFeedId, String name, String description, String type,
			String structureId, String templateId, String rendererTemplateId,
			int delta, String orderByCol, String orderByType,
			String targetLayoutFriendlyUrl, String targetPortletId,
			String contentField, String feedType, double feedVersion,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		// SyndicatedFeed

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		validate(
			groupId, feedId, autoFeedId, name, description, structureId,
			contentField, targetLayoutFriendlyUrl);

		long id = counterLocalService.increment();

		JournalSyndicatedFeed synFeed =
			journalSyndicatedFeedPersistence.create(id);

		synFeed.setUuid(uuid);
		synFeed.setGroupId(groupId);
		synFeed.setCompanyId(user.getCompanyId());
		synFeed.setUserId(user.getUserId());
		synFeed.setUserName(user.getFullName());
		synFeed.setCreateDate(now);
		synFeed.setModifiedDate(now);
		synFeed.setFeedId(feedId);
		synFeed.setName(name);
		synFeed.setDescription(description);
		synFeed.setType(type);
		synFeed.setStructureId(structureId);
		synFeed.setTemplateId(templateId);
		synFeed.setRendererTemplateId(rendererTemplateId);
		synFeed.setDelta(delta);
		synFeed.setOrderByCol(orderByCol);
		synFeed.setOrderByType(orderByType);
		synFeed.setTargetLayoutFriendlyUrl(targetLayoutFriendlyUrl);
		synFeed.setTargetPortletId(targetPortletId);
		synFeed.setContentField(contentField);

		if (Validator.isNull(feedType)) {
			synFeed.setFeedType(RSSUtil.DEFAULT_TYPE);
			synFeed.setFeedVersion(RSSUtil.DEFAULT_VERSION);
		}
		else {
			synFeed.setFeedType(feedType);
			synFeed.setFeedVersion(feedVersion);
		}

		journalSyndicatedFeedPersistence.update(synFeed);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addSyndicatedFeedResources(
				synFeed, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addSyndicatedFeedResources(
				synFeed, communityPermissions, guestPermissions);
		}

		return synFeed;
	}

	public void addSyndicatedFeedResources(
			long synFeedId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		JournalSyndicatedFeed synFeed =
			journalSyndicatedFeedPersistence.findByPrimaryKey(synFeedId);

		addSyndicatedFeedResources(
			synFeed, addCommunityPermissions, addGuestPermissions);
	}

	public void addSyndicatedFeedResources(
			JournalSyndicatedFeed synFeed, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			synFeed.getCompanyId(), synFeed.getGroupId(),
			synFeed.getUserId(), JournalSyndicatedFeed.class.getName(),
			synFeed.getId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addSyndicatedFeedResources(
			long synFeedId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		JournalSyndicatedFeed synFeed =
			journalSyndicatedFeedPersistence.findByPrimaryKey(synFeedId);

		addSyndicatedFeedResources(
			synFeed, communityPermissions, guestPermissions);
	}

	public void addSyndicatedFeedResources(
			JournalSyndicatedFeed synFeed, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			synFeed.getCompanyId(), synFeed.getGroupId(),
			synFeed.getUserId(), JournalSyndicatedFeed.class.getName(),
			synFeed.getId(), communityPermissions, guestPermissions);
	}

	public void deleteSyndicatedFeed(long synFeedId)
		throws PortalException, SystemException {

		JournalSyndicatedFeed synFeed =
			journalSyndicatedFeedPersistence.findByPrimaryKey(synFeedId);

		deleteSyndicatedFeed(synFeed);
	}

	public void deleteSyndicatedFeed(long groupId, String feedId)
		throws PortalException, SystemException {

		JournalSyndicatedFeed synFeed =
			journalSyndicatedFeedPersistence.findByG_F(groupId, feedId);

		deleteSyndicatedFeed(synFeed);
	}

	public void deleteSyndicatedFeed(JournalSyndicatedFeed synFeed)
		throws PortalException, SystemException {

		// Resources

		resourceLocalService.deleteResource(
			synFeed.getCompanyId(), JournalSyndicatedFeed.class.getName(),
			ResourceImpl.SCOPE_INDIVIDUAL, synFeed.getId());

		// Template

		journalSyndicatedFeedPersistence.remove(synFeed.getPrimaryKey());
	}

	public JournalSyndicatedFeed getSyndicatedFeed(long synFeedId)
		throws PortalException, SystemException {

		return journalSyndicatedFeedPersistence.findByPrimaryKey(synFeedId);
	}

	public JournalSyndicatedFeed getSyndicatedFeed(long groupId, String feedId)
		throws PortalException, SystemException {

		return journalSyndicatedFeedPersistence.findByG_F(groupId, feedId);
	}

	public List getSyndicatedFeeds() throws SystemException {
		return journalSyndicatedFeedPersistence.findAll();
	}

	public List getSyndicatedFeeds(long groupId) throws SystemException {
		return journalSyndicatedFeedPersistence.findByGroupId(groupId);
	}

	public List getSyndicatedFeeds(long groupId, int begin, int end)
		throws SystemException {

		return journalSyndicatedFeedPersistence.findByGroupId(
			groupId, begin, end);
	}

	public int getSyndicatedFeedsCount(long groupId) throws SystemException {
		return journalSyndicatedFeedPersistence.countByGroupId(groupId);
	}

	public List search(
			long companyId, long groupId, String keywords, int begin, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalSyndicatedFeedFinder.findByKeywords(
			companyId, groupId, keywords, begin, end, obc);
	}

	public List search(
			long companyId, long groupId, String feedId, String name,
			String description, boolean andOperator, int begin, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalSyndicatedFeedFinder.findByC_G_F_N_D(
			companyId, groupId, feedId, name, description, andOperator, begin,
			end, obc);
	}

	public int searchCount(
			long companyId, long groupId, String keywords)
		throws SystemException {

		return journalSyndicatedFeedFinder.countByKeywords(
			companyId, groupId, keywords);
	}

	public int searchCount(
			long companyId, long groupId, String feedId, String name,
			String description, boolean andOperator)
		throws SystemException {

		return journalSyndicatedFeedFinder.countByC_G_F_N_D(
			companyId, groupId, feedId, name, description, andOperator);
	}

	public JournalSyndicatedFeed updateSyndicatedFeed(
			long groupId, String feedId, String name, String description,
			String type, String structureId, String templateId,
			String rendererTemplateId, int delta, String orderByCol,
			String orderByType, String targetLayoutFriendlyUrl,
			String targetPortletId, String contentField, String feedType,
			double feedVersion)
		throws PortalException, SystemException{

		// SyndicatedFeed

		validate(
			groupId, name, description, structureId, contentField,
			targetLayoutFriendlyUrl);

		JournalSyndicatedFeed synFeed =
			journalSyndicatedFeedPersistence.findByG_F(groupId, feedId);

		synFeed.setModifiedDate(new Date());
		synFeed.setName(name);
		synFeed.setDescription(description);
		synFeed.setType(type);
		synFeed.setStructureId(structureId);
		synFeed.setTemplateId(templateId);
		synFeed.setRendererTemplateId(rendererTemplateId);
		synFeed.setDelta(delta);
		synFeed.setOrderByCol(orderByCol);
		synFeed.setOrderByType(orderByType);
		synFeed.setTargetLayoutFriendlyUrl(targetLayoutFriendlyUrl);
		synFeed.setTargetPortletId(targetPortletId);
		synFeed.setContentField(contentField);

		if (Validator.isNull(feedType)) {
			synFeed.setFeedType(RSSUtil.DEFAULT_TYPE);
			synFeed.setFeedVersion(RSSUtil.DEFAULT_VERSION);
		}
		else {
			synFeed.setFeedType(feedType);
			synFeed.setFeedVersion(feedVersion);
		}

		journalSyndicatedFeedPersistence.update(synFeed);

		return synFeed;
	}

	protected void validate(
			long groupId, String feedId, boolean autoFeedId, String name,
			String description, String structureId, String contentField,
			String targetLayoutFriendlyUrl)
		throws PortalException, SystemException {

		if (!autoFeedId) {
			if ((Validator.isNull(feedId)) ||
				(Validator.isNumber(feedId)) ||
				(feedId.indexOf(StringPool.SPACE) != -1)) {

				throw new SyndicatedFeedIdException();
			}

			try {
				journalSyndicatedFeedPersistence.findByG_F(groupId, feedId);

				throw new DuplicateSyndicatedFeedIdException();
			}
			catch (NoSuchSyndicatedFeedException nssfe) {
			}
		}

		validate(
			groupId, name, description, structureId, contentField,
			targetLayoutFriendlyUrl);
	}

	protected void validate(
			long groupId, String name, String description, String structureId,
			String contentField, String targetLayoutFriendlyUrl)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new SyndicatedFeedNameException();
		}

		if (Validator.isNull(description)) {
			throw new SyndicatedFeedDescriptionException();
		}

		if (!isValidStructureField(groupId, structureId, contentField)) {
			throw new SyndicatedFeedContentFieldException();
		}

		if (Validator.isNull(targetLayoutFriendlyUrl)) {
			throw new SyndicatedFeedTargetLayoutFriendlyUrlException();
		}
	}

	protected boolean isValidStructureField(
		long groupId, String structureId, String contentField) {

		if (contentField.equals(
				JournalSyndicatedFeedImpl.ARTICLE_DESCRIPTION) ||
			contentField.equals(
				JournalSyndicatedFeedImpl.RENDERED_ARTICLE)) {
			return true;
		}
		else {
			try {
				JournalStructure structure =
					journalStructurePersistence.findByG_S(
						groupId, structureId);

				Document doc = PortalUtil.readDocumentFromXML(
					structure.getXsd());

				XPath xpathSelector = DocumentHelper.createXPath(
					"//dynamic-element[@name='"+ contentField + "']");

				Element el = (Element)xpathSelector.selectSingleNode(doc);

		    	if (el != null) {
		    		return true;
		    	}
			}
			catch (NoSuchStructureException nsse) {
			}
			catch (SystemException se) {
			}
			catch (DocumentException de) {
			}
		}

		return false;
	}

}