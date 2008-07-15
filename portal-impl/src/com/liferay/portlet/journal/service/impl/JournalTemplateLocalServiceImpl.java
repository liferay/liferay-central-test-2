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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.mirage.custom.MirageServiceFactory;
import com.liferay.portal.mirage.model.JournalMirageTemplate;
import com.liferay.portal.mirage.model.OptionalJournalTemplateCriteria;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.RequiredTemplateException;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.portlet.journal.service.base.JournalTemplateLocalServiceBaseImpl;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.exception.TemplateNotFoundException;
import com.sun.portal.cms.mirage.exception.ValidationException;
import com.sun.portal.cms.mirage.model.custom.Template;
import com.sun.portal.cms.mirage.model.search.SearchCriteria;
import com.sun.portal.cms.mirage.model.search.SearchFieldValue;
import com.sun.portal.cms.mirage.service.custom.ContentTypeService;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="JournalTemplateLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @author Karthik Sudarshan
 *
 */
public class JournalTemplateLocalServiceImpl
	extends JournalTemplateLocalServiceBaseImpl {

	public JournalTemplate addTemplate(
			long userId, String templateId, boolean autoTemplateId, long plid,
			String structureId, String name, String description, String xsl,
			boolean formatXsl, String langType, boolean cacheable,
			boolean smallImage, String smallImageURL, File smallFile,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addTemplate(
			null, userId, templateId, autoTemplateId, plid, structureId, name,
				description, xsl, formatXsl, langType, cacheable, smallImage,
					smallImageURL, smallFile,
						Boolean.valueOf(addCommunityPermissions),
							Boolean.valueOf(addGuestPermissions), null, null);
	}

	public JournalTemplate addTemplate(
			String uuid, long userId, String templateId, boolean autoTemplateId,
			long plid, String structureId, String name, String description,
			String xsl, boolean formatXsl, String langType, boolean cacheable,
			boolean smallImage, String smallImageURL, File smallFile,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addTemplate(
			uuid, userId, templateId, autoTemplateId, plid, structureId, name,
				description, xsl, formatXsl, langType, cacheable, smallImage,
					smallImageURL, smallFile,
						Boolean.valueOf(addCommunityPermissions),
							Boolean.valueOf(addGuestPermissions), null, null);
	}

	public JournalTemplate addTemplate(
			long userId, String templateId, boolean autoTemplateId, long plid,
			String structureId, String name, String description, String xsl,
			boolean formatXsl, String langType, boolean cacheable,
			boolean smallImage, String smallImageURL, File smallFile,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addTemplate(
			null, userId, templateId, autoTemplateId, plid, structureId, name,
				description, xsl, formatXsl, langType, cacheable, smallImage,
					smallImageURL, smallFile, null, null, communityPermissions,
						guestPermissions);
	}

	public JournalTemplate addTemplate(
			String uuid, long userId, String templateId, boolean autoTemplateId,
			long plid, String structureId, String name, String description,
			String xsl, boolean formatXsl, String langType, boolean cacheable,
			boolean smallImage, String smallImageURL, File smallFile,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		long groupId = PortalUtil.getPortletGroupId(plid);

		return addTemplateToGroup(
			uuid, userId, templateId, autoTemplateId, groupId, structureId,
				name, description, xsl, formatXsl, langType, cacheable,
					smallImage, smallImageURL, smallFile,
						addCommunityPermissions, addGuestPermissions,
							communityPermissions, guestPermissions);
	}

	public JournalTemplate addTemplateToGroup(
			String uuid, long userId, String templateId, boolean autoTemplateId,
			long groupId, String structureId, String name, String description,
			String xsl, boolean formatXsl, String langType, boolean cacheable,
			boolean smallImage, String smallImageURL, File smallFile,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		JournalTemplate template = new JournalTemplateImpl();
		JournalMirageTemplate mirageTemplate =
			new JournalMirageTemplate(template);

		JournalMirageTemplate.CreationAttributes creationAttributes =
			mirageTemplate.new CreationAttributes(
				formatXsl, autoTemplateId, smallFile);
		mirageTemplate.setCreationAttributes(creationAttributes);

		template.setTemplateId(templateId);
		template.setUserId(userId);
		template.setLangType(langType);
		template.setXsl(xsl);
		template.setGroupId(groupId);
		template.setName(name);
		template.setDescription(description);
		template.setSmallImage(smallImage);
		template.setSmallImageURL(smallImageURL);
		template.setUuid(uuid);
		template.setStructureId(structureId);
		template.setCacheable(cacheable);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();
		try {
			contentTypeService.addTemplateToContentType(mirageTemplate, null);
		}
		catch (CMSException cmse) {
			_throwException(cmse);
		}
		catch (ValidationException ve) {

			// Validation Exception will not be thrown
			// as no Template checking is added

		}

		// Get the new Template from the JournalMirageTemplate object

		template = mirageTemplate.getTemplate();

		// Resources

		if ((addCommunityPermissions != null) &&
				(addGuestPermissions != null)) {

			addTemplateResources(
				template, addCommunityPermissions.booleanValue(),
					addGuestPermissions.booleanValue());
		}
		else {
			addTemplateResources(
				template, communityPermissions, guestPermissions);
		}

		return template;
	}

	public void addTemplateResources(
			long groupId, String templateId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		JournalTemplate template =
			journalTemplatePersistence.findByG_T(groupId, templateId);

		addTemplateResources(
			template, addCommunityPermissions, addGuestPermissions);
	}

	public void addTemplateResources(
			JournalTemplate template, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			template.getCompanyId(), template.getGroupId(),
				template.getUserId(), JournalTemplate.class.getName(),
					template.getId(), false, addCommunityPermissions,
						addGuestPermissions);
	}

	public void addTemplateResources(
			long groupId, String templateId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		JournalTemplate template =
			journalTemplatePersistence.findByG_T(groupId, templateId);

		addTemplateResources(template, communityPermissions, guestPermissions);
	}

	public void addTemplateResources(
			JournalTemplate template, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			template.getCompanyId(), template.getGroupId(),
				template.getUserId(), JournalTemplate.class.getName(),
					template.getId(), communityPermissions, guestPermissions);
	}

	public void checkNewLine(long groupId, String templateId)
		throws PortalException, SystemException {

		JournalTemplate template = _getTemplate(groupId, templateId);

		String xsl = template.getXsl();

		if ((xsl != null) && (xsl.indexOf("\\n") != -1)) {
			xsl = StringUtil.replace(xsl, new String[] {
				"\\n", "\\r"
			}, new String[] {
				"\n", "\r"
			});

			template.setXsl(xsl);

			JournalMirageTemplate journalMirageTemplate =
				new JournalMirageTemplate(template);
			ContentTypeService contentTypeService =
				MirageServiceFactory.getContentTypeService();

			try {
				contentTypeService.updateTemplateOfContentType(
					journalMirageTemplate, null, null);
			}
			catch (ValidationException ve) {

				// Ignore this as CMSException is thrown

			}
			catch (CMSException cmse) {
				_throwException(cmse);
			}
		}
	}

	public void deleteTemplate(long groupId, String templateId)
		throws PortalException, SystemException {

		templateId = templateId.trim().toUpperCase();

		JournalTemplate template = _getTemplate(groupId, templateId);

		deleteTemplate(template);
	}

	public void deleteTemplate(JournalTemplate template)
		throws PortalException, SystemException {

		int articleCount = 0;

		JournalMirageTemplate journalMirageTemplate =
			new JournalMirageTemplate(template);
		OptionalJournalTemplateCriteria criteria =
			new OptionalJournalTemplateCriteria(
				OptionalJournalTemplateCriteria
					.ARTICLE_COUNT_BY_GROUP_AND_TEMPLATE);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();
		try {
			articleCount =
				contentTypeService.getTemplatesCount(
					null, journalMirageTemplate, criteria);
		}
		catch (CMSException cmse) {
			_throwSystemException(cmse);
		}

		if (articleCount > 0) {
			throw new RequiredTemplateException();
		}

		// Small image

		imageLocalService.deleteImage(template.getSmallImageId());

		// WebDAVProps

		webDAVPropsLocalService.deleteWebDAVProps(
			JournalTemplate.class.getName(), template.getPrimaryKey());

		// Resources

		resourceLocalService.deleteResource(
			template.getCompanyId(), JournalTemplate.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL, template.getId());

		try {
			contentTypeService.deleteTemplateOfContentType(
				null, journalMirageTemplate);
		}
		catch (CMSException cmse) {
			_throwException(cmse);
		}
	}

	public void deleteTemplates(long groupId)
		throws PortalException, SystemException {

		List<JournalTemplate> templates = getTemplates(groupId);
		for (JournalTemplate template : templates) {
			deleteTemplate(template);
		}
	}

	public List<JournalTemplate> getStructureTemplates(
			long groupId, String structureId)
		throws SystemException {

		JournalTemplate journalTemplate = new JournalTemplateImpl();
		journalTemplate.setGroupId(groupId);
		journalTemplate.setStructureId(structureId);

		JournalMirageTemplate journalMirageTemplate =
			new JournalMirageTemplate(journalTemplate);
		OptionalJournalTemplateCriteria criteria =
			new OptionalJournalTemplateCriteria(
				OptionalJournalTemplateCriteria.FIND_BY_GROUP_AND_STRUCTURE);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();
		List<Template> templates = null;
		try {
			templates =
				contentTypeService.getTemplates(
					null, journalMirageTemplate, criteria);
		}
		catch (TemplateNotFoundException tnfe) {
			_throwSystemException(tnfe);
		}
		if (templates == null) {
			return null;
		}
		List<JournalTemplate> journalTemplates =
			new ArrayList<JournalTemplate>(templates.size());
		for (Template template : templates) {
			journalTemplates.add(
				((JournalMirageTemplate) template).getTemplate());
		}
		return journalTemplates;
	}

	public List<JournalTemplate> getStructureTemplates(
			long groupId, String structureId, int start, int end)
		throws SystemException {

		JournalTemplate journalTemplate = new JournalTemplateImpl();
		journalTemplate.setGroupId(groupId);
		journalTemplate.setStructureId(structureId);

		JournalMirageTemplate journalMirageTemplate =
			new JournalMirageTemplate(journalTemplate);
		OptionalJournalTemplateCriteria criteria =
			new OptionalJournalTemplateCriteria(
				OptionalJournalTemplateCriteria.
					FIND_BY_GROUP_AND_STRUCTURE_WITH_LIMIT);
		criteria.getOptions().put(
			OptionalJournalTemplateCriteria.RANGE_START,
			Integer.toString(start));
		criteria.getOptions().put(
			OptionalJournalTemplateCriteria.RANGE_END, Integer.toString(end));

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();
		List<Template> templates = null;
		try {
			templates =
				contentTypeService.getTemplates(
					null, journalMirageTemplate, criteria);
		}
		catch (TemplateNotFoundException tnfe) {
			_throwSystemException(tnfe);
		}
		if (templates == null) {
			return null;
		}
		List<JournalTemplate> journalTemplates =
			new ArrayList<JournalTemplate>(templates.size());
		for (Template template : templates) {
			journalTemplates.add(
				((JournalMirageTemplate) template).getTemplate());
		}
		return journalTemplates;
	}

	public int getStructureTemplatesCount(long groupId, String structureId)
		throws SystemException {

		JournalTemplate journalTemplate = new JournalTemplateImpl();
		journalTemplate.setGroupId(groupId);
		journalTemplate.setStructureId(structureId);

		JournalMirageTemplate journalMirageTemplate =
			new JournalMirageTemplate(journalTemplate);
		OptionalJournalTemplateCriteria criteria =
			new OptionalJournalTemplateCriteria(
				OptionalJournalTemplateCriteria.COUNT_BY_GROUP_AND_STRUCTURE);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();
		int count = -1;
		try {
			count =
				contentTypeService.getTemplatesCount(
					null, journalMirageTemplate, criteria);
		}
		catch (CMSException cmse) {
			_throwSystemException(cmse);
		}
		return count;
	}

	public JournalTemplate getTemplate(long id)
		throws PortalException, SystemException {

		JournalTemplate template = new JournalTemplateImpl();
		template.setId(id);
		JournalMirageTemplate journalMirageTemplate =
			new JournalMirageTemplate(template);
		OptionalJournalTemplateCriteria criteria =
			new OptionalJournalTemplateCriteria(
				OptionalJournalTemplateCriteria.FIND_BY_PRIMARY_KEY);
		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();
		try {
			journalMirageTemplate =
				(JournalMirageTemplate) contentTypeService.getTemplate(
					journalMirageTemplate, criteria);
		}
		catch (TemplateNotFoundException tnfe) {
			_throwException(tnfe);
		}
		return journalMirageTemplate.getTemplate();
	}

	public JournalTemplate getTemplate(long groupId, String templateId)
		throws PortalException, SystemException {

		templateId = GetterUtil.getString(templateId).toUpperCase();

		if (groupId == 0) {
			_log.error("No group id was passed for " + templateId +
				". Group id is " +
					"required since 4.2.0. Please update all custom code and " +
						"data that references templates without a group id.");

			List<JournalTemplate> templates =
				_getTemplatesByTemplateId(templateId);

			if (templates.size() == 0) {
				throw new NoSuchTemplateException(
					"No JournalTemplate exists with the template id " +
						templateId);
			}
			else {
				return templates.get(0);
			}
		}
		else {
			return _getTemplate(groupId, templateId);
		}
	}

	public JournalTemplate getTemplateBySmallImageId(long smallImageId)
		throws PortalException, SystemException {

		JournalTemplate template = new JournalTemplateImpl();
		template.setSmallImageId(smallImageId);
		JournalMirageTemplate journalMirageTemplate =
			new JournalMirageTemplate(template);
		OptionalJournalTemplateCriteria criteria =
			new OptionalJournalTemplateCriteria(
				OptionalJournalTemplateCriteria.FIND_BY_SMALL_IMAGE_ID);
		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();
		try {
			journalMirageTemplate =
				(JournalMirageTemplate) contentTypeService.getTemplate(
					journalMirageTemplate, criteria);
		}
		catch (TemplateNotFoundException tnfe) {
			_throwException(tnfe);
		}
		return journalMirageTemplate.getTemplate();
	}

	public List<JournalTemplate> getTemplates()
		throws SystemException {

		OptionalJournalTemplateCriteria criteria =
			new OptionalJournalTemplateCriteria(
				OptionalJournalTemplateCriteria.FIND_ALL);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();
		List<Template> templates = null;
		try {
			templates = contentTypeService.getTemplates(null, null, criteria);
		}
		catch (TemplateNotFoundException tnfe) {
			_throwSystemException(tnfe);
		}
		if (templates == null) {
			return null;
		}
		List<JournalTemplate> journalTemplates =
			new ArrayList<JournalTemplate>(templates.size());
		for (Template template : templates) {
			journalTemplates.add(
				((JournalMirageTemplate) template).getTemplate());
		}
		return journalTemplates;
	}

	public List<JournalTemplate> getTemplates(long groupId)
		throws SystemException {

		JournalTemplate journalTemplate = new JournalTemplateImpl();
		journalTemplate.setGroupId(groupId);

		JournalMirageTemplate journalMirageTemplate =
			new JournalMirageTemplate(journalTemplate);
		OptionalJournalTemplateCriteria criteria =
			new OptionalJournalTemplateCriteria(
				OptionalJournalTemplateCriteria.FIND_BY_GROUP_ID);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();
		List<Template> templates = null;
		try {
			templates =
				contentTypeService.getTemplates(
					null, journalMirageTemplate, criteria);
		}
		catch (TemplateNotFoundException tnfe) {
			_throwSystemException(tnfe);
		}
		if (templates == null) {
			return null;
		}
		List<JournalTemplate> journalTemplates =
			new ArrayList<JournalTemplate>(templates.size());
		for (Template template : templates) {
			journalTemplates.add(
				((JournalMirageTemplate) template).getTemplate());
		}
		return journalTemplates;
	}

	public List<JournalTemplate> getTemplates(long groupId, int start, int end)
		throws SystemException {

		JournalTemplate journalTemplate = new JournalTemplateImpl();
		journalTemplate.setGroupId(groupId);

		JournalMirageTemplate journalMirageTemplate =
			new JournalMirageTemplate(journalTemplate);
		OptionalJournalTemplateCriteria criteria =
			new OptionalJournalTemplateCriteria(
				OptionalJournalTemplateCriteria.FIND_BY_GROUP_ID_WITH_LIMIT);

		criteria.getOptions().put(
			OptionalJournalTemplateCriteria.RANGE_START,
				Integer.toString(start));
		criteria.getOptions().put(
			OptionalJournalTemplateCriteria.RANGE_END, Integer.toString(end));
		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();
		List<Template> templates = null;
		try {
			templates =
				contentTypeService.getTemplates(
					null, journalMirageTemplate, criteria);
		}
		catch (TemplateNotFoundException tnfe) {
			_throwSystemException(tnfe);
		}
		if (templates == null) {
			return null;
		}
		List<JournalTemplate> journalTemplates =
			new ArrayList<JournalTemplate>(templates.size());
		for (Template template : templates) {
			journalTemplates.add(
				((JournalMirageTemplate) template).getTemplate());
		}
		return journalTemplates;
	}

	public int getTemplatesCount(long groupId)
		throws SystemException {

		JournalTemplate journalTemplate = new JournalTemplateImpl();
		journalTemplate.setGroupId(groupId);

		JournalMirageTemplate journalMirageTemplate =
			new JournalMirageTemplate(journalTemplate);
		OptionalJournalTemplateCriteria criteria =
			new OptionalJournalTemplateCriteria(
				OptionalJournalTemplateCriteria.COUNT_BY_GROUP_ID);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();
		int count = -1;
		try {
			count =
				contentTypeService.getTemplatesCount(
					null, journalMirageTemplate, criteria);
		}
		catch (CMSException cmse) {
			_throwSystemException(cmse);
		}
		return count;
	}

	public boolean hasTemplate(long groupId, String templateId)
		throws SystemException {

		try {
			getTemplate(groupId, templateId);

			return true;
		}
		catch (PortalException pe) {
			return false;
		}
	}

	public List<JournalTemplate> search(
			long companyId, long groupId, String keywords, String structureId,
			String structureIdComparator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		Map<String, String> nameValues = new HashMap<String, String>();
		nameValues.put(
			OptionalJournalTemplateCriteria.FINDER,
				OptionalJournalTemplateCriteria.FIND_BY_KEYWORDS);
		nameValues.put(
			OptionalJournalTemplateCriteria.COMPANY_ID,
				Long.toString(companyId));
		nameValues.put(
			OptionalJournalTemplateCriteria.GROUP_ID, Long.toString(groupId));
		nameValues.put(OptionalJournalTemplateCriteria.KEYWORDS, keywords);
		nameValues.put(
			OptionalJournalTemplateCriteria.STRUCTURE_ID, structureId);
		nameValues.put(
			OptionalJournalTemplateCriteria.STRUCTURE_ID_COMPARATOR,
				structureIdComparator);
		nameValues.put(
			OptionalJournalTemplateCriteria.RANGE_START,
				Integer.toString(start));
		nameValues.put(
			OptionalJournalTemplateCriteria.RANGE_END, Integer.toString(end));

		_addSearchFields(searchFields, nameValues);

		criteria.setSearchFieldValues(searchFields);
		criteria.setOrderByComparator(obc);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();
		List<Template> templates = null;
		try {
			templates = contentTypeService.searchTemplates(criteria);
		}
		catch (CMSException cmse) {
			_throwSystemException(cmse);
		}
		return _getJournalTemplatesFromTemplates(templates);
	}

	public List<JournalTemplate> search(
			long companyId, long groupId, String templateId, String structureId,
			String structureIdComparator, String name, String description,
			boolean andOperator, int start, int end, OrderByComparator obc)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		Map<String, String> nameValues = new HashMap<String, String>();
		nameValues.put(
			OptionalJournalTemplateCriteria.FINDER,
				OptionalJournalTemplateCriteria.SEARCH);
		nameValues.put(
			OptionalJournalTemplateCriteria.COMPANY_ID,
				Long.toString(companyId));
		nameValues.put(
			OptionalJournalTemplateCriteria.GROUP_ID, Long.toString(groupId));
		nameValues.put(OptionalJournalTemplateCriteria.TEMPLATE_ID, templateId);
		nameValues.put(
			OptionalJournalTemplateCriteria.STRUCTURE_ID, structureId);
		nameValues.put(
			OptionalJournalTemplateCriteria.STRUCTURE_ID_COMPARATOR,
			structureIdComparator);
		nameValues.put(OptionalJournalTemplateCriteria.NAME, name);
		nameValues.put(
			OptionalJournalTemplateCriteria.DESCRIPTION,
				description);
		nameValues.put(
			OptionalJournalTemplateCriteria.RANGE_START,
			Integer.toString(start));
		nameValues.put(
			OptionalJournalTemplateCriteria.RANGE_END, Integer.toString(end));

		_addSearchFields(searchFields, nameValues);

		criteria.setSearchFieldValues(searchFields);
		criteria.setOrderByComparator(obc);
		criteria.setMatchAnyOneField(andOperator);
		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();
		List<Template> templates = null;
		try {
			templates = contentTypeService.searchTemplates(criteria);
		}
		catch (CMSException cmse) {
			_throwSystemException(cmse);
		}
		return _getJournalTemplatesFromTemplates(templates);
	}

	public int searchCount(
			long companyId, long groupId, String keywords, String structureId,
			String structureIdComparator)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		Map<String, String> nameValues = new HashMap<String, String>();
		nameValues.put(
			OptionalJournalTemplateCriteria.FINDER,
				OptionalJournalTemplateCriteria.COUNT_BY_KEYWORDS);
		nameValues.put(
			OptionalJournalTemplateCriteria.COMPANY_ID,
				Long.toString(companyId));
		nameValues.put(
			OptionalJournalTemplateCriteria.GROUP_ID, Long.toString(groupId));
		nameValues.put(OptionalJournalTemplateCriteria.KEYWORDS, keywords);
		nameValues.put(
			OptionalJournalTemplateCriteria.STRUCTURE_ID, structureId);
		nameValues.put(
			OptionalJournalTemplateCriteria.STRUCTURE_ID_COMPARATOR,
				structureIdComparator);

		_addSearchFields(searchFields, nameValues);

		criteria.setSearchFieldValues(searchFields);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();
		int count = -1;
		try {
			count = contentTypeService.searchTemplatesCount(criteria);
		}
		catch (CMSException cmse) {
			_throwSystemException(cmse);
		}
		return count;
	}

	public int searchCount(
			long companyId, long groupId, String templateId, String structureId,
			String structureIdComparator, String name, String description,
			boolean andOperator)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		Map<String, String> nameValues = new HashMap<String, String>();
		nameValues.put(
			OptionalJournalTemplateCriteria.FINDER,
				OptionalJournalTemplateCriteria.SEARCH_COUNT);
		nameValues.put(
			OptionalJournalTemplateCriteria.COMPANY_ID,
				Long.toString(companyId));
		nameValues.put(
			OptionalJournalTemplateCriteria.GROUP_ID, Long.toString(groupId));
		nameValues.put(OptionalJournalTemplateCriteria.TEMPLATE_ID, templateId);
		nameValues.put(
			OptionalJournalTemplateCriteria.STRUCTURE_ID, structureId);
		nameValues.put(
			OptionalJournalTemplateCriteria.STRUCTURE_ID_COMPARATOR,
				structureIdComparator);
		nameValues.put(OptionalJournalTemplateCriteria.NAME, name);
		nameValues.put(
			OptionalJournalTemplateCriteria.DESCRIPTION,
				description);

		_addSearchFields(searchFields, nameValues);

		criteria.setSearchFieldValues(searchFields);
		criteria.setMatchAnyOneField(andOperator);
		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();
		int count = -1;
		try {
			count = contentTypeService.searchTemplatesCount(criteria);
		}
		catch (CMSException cmse) {
			_throwSystemException(cmse);
		}
		return count;
	}

	public JournalTemplate updateTemplate(
			long groupId, String templateId, String structureId, String name,
			String description, String xsl, boolean formatXsl, String langType,
			boolean cacheable, boolean smallImage, String smallImageURL,
			File smallFile)
		throws PortalException, SystemException {

		JournalTemplate template = new JournalTemplateImpl();

		template.setTemplateId(templateId);
		template.setLangType(langType);
		template.setXsl(xsl);
		template.setName(name);
		template.setDescription(description);
		template.setSmallImage(smallImage);
		template.setSmallImageURL(smallImageURL);
		template.setGroupId(groupId);
		template.setStructureId(structureId);
		template.setCacheable(cacheable);

		JournalMirageTemplate journalMirageTemplate =
			new JournalMirageTemplate(template);
		JournalMirageTemplate.UpdateAttributes updateAttributes =
			journalMirageTemplate.new UpdateAttributes(formatXsl, smallFile);

		journalMirageTemplate.setUpdateAttributes(updateAttributes);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();
		try {
			contentTypeService.updateTemplateOfContentType(
				journalMirageTemplate, null);
		}
		catch (ValidationException ex) {

			// Ignore this, as the exceptions are wrapped as CMSException

		}
		catch (CMSException cmse) {
			_throwException(cmse);
		}

		template = journalMirageTemplate.getTemplate();
		return template;
	}

	private void _addSearchField(
		List<SearchFieldValue> fieldList, String fieldName, String fieldValue) {

		SearchFieldValue searchField = new SearchFieldValue();
		searchField.setFieldName(fieldName);
		searchField.setFieldValues(new String[] {
			fieldValue
		});
		fieldList.add(searchField);
	}

	private void _addSearchFields(
		List<SearchFieldValue> fieldList, Map<String, String> nameValues) {

		Iterator<String> iter = nameValues.keySet().iterator();
		while (iter.hasNext()) {
			String name = iter.next();
			String value = nameValues.get(name);
			_addSearchField(fieldList, name, value);
		}
	}

	private List<JournalTemplate> _getJournalTemplatesFromTemplates(
		List<Template> templates) {

		if (templates == null) {
			return null;
		}
		List<JournalTemplate> journalTemplates =
			new ArrayList<JournalTemplate>(templates.size());
		for (Template template : templates) {
			journalTemplates.add(
				((JournalMirageTemplate) template).getTemplate());
		}
		return journalTemplates;
	}

	private JournalTemplate _getTemplate(long groupId, String templateId)
		throws PortalException, SystemException {

		JournalTemplate template = new JournalTemplateImpl();
		template.setGroupId(groupId);
		template.setTemplateId(templateId);
		JournalMirageTemplate journalMirageTemplate =
			new JournalMirageTemplate(template);
		OptionalJournalTemplateCriteria criteria =
			new OptionalJournalTemplateCriteria(
				OptionalJournalTemplateCriteria.FIND_BY_GROUP_AND_TEMPLATE_ID);
		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();
		try {
			journalMirageTemplate =
				(JournalMirageTemplate) contentTypeService.getTemplate(
					journalMirageTemplate, criteria);
		}
		catch (TemplateNotFoundException tnfe) {
			_throwException(tnfe);
		}
		return journalMirageTemplate.getTemplate();
	}

	private List<JournalTemplate> _getTemplatesByTemplateId(String templateId)
		throws SystemException {

		JournalTemplate journalTemplate = new JournalTemplateImpl();
		journalTemplate.setTemplateId(templateId);

		JournalMirageTemplate journalMirageTemplate =
			new JournalMirageTemplate(journalTemplate);
		OptionalJournalTemplateCriteria criteria =
			new OptionalJournalTemplateCriteria(
				OptionalJournalTemplateCriteria.FIND_BY_TEMPLATE_ID);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();
		List<Template> templates = null;
		try {
			templates =
				contentTypeService.getTemplates(
					null, journalMirageTemplate, criteria);
		}
		catch (TemplateNotFoundException tnfe) {
			_throwSystemException(tnfe);
		}
		if (templates == null) {
			return null;
		}
		List<JournalTemplate> journalTemplates =
			new ArrayList<JournalTemplate>(templates.size());
		for (Template template : templates) {
			journalTemplates.add(
				((JournalMirageTemplate) template).getTemplate());
		}
		return journalTemplates;
	}

	private void _throwException(CMSException ex)
		throws PortalException, SystemException {

		Throwable cause = ex.getCause();
		if (cause != null) {
			if (cause instanceof PortalException) {
				throw (PortalException) cause;
			}
			else if (cause instanceof SystemException) {
				throw (SystemException) cause;
			}
		}
	}

	private void _throwSystemException(CMSException ex)
		throws SystemException {

		Throwable cause = ex.getCause();
		if (cause != null) {
			if (cause instanceof SystemException) {
				throw (SystemException) cause;
			}
		}
	}

	private static final Log _log =
		LogFactory.getLog(JournalTemplateLocalServiceImpl.class);

}