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
import com.liferay.portal.mirage.model.MirageTemplate;
import com.liferay.portal.mirage.model.MirageTemplateCriteria;
import com.liferay.portal.mirage.service.MirageServiceFactory;
import com.liferay.portal.mirage.util.ExceptionTranslator;
import com.liferay.portal.mirage.util.SmartCriteria;
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
import com.sun.portal.cms.mirage.service.custom.ContentTypeService;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

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
			smallImageURL, smallFile, Boolean.valueOf(addCommunityPermissions),
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
			smallImageURL, smallFile, Boolean.valueOf(addCommunityPermissions),
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
			name, description, xsl, formatXsl, langType, cacheable, smallImage,
			smallImageURL, smallFile, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
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

		template.setUuid(uuid);
		template.setUserId(userId);
		template.setTemplateId(templateId);
		template.setGroupId(groupId);
		template.setStructureId(structureId);
		template.setName(name);
		template.setDescription(description);
		template.setXsl(xsl);
		template.setLangType(langType);
		template.setCacheable(cacheable);
		template.setSmallImage(smallImage);
		template.setSmallImageURL(smallImageURL);

		MirageTemplate mirageTemplate = new MirageTemplate(template);

		mirageTemplate.setAutoTemplateId(autoTemplateId);
		mirageTemplate.setFormatXsl(formatXsl);
		mirageTemplate.setSmallFile(smallFile);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();

		try {
			contentTypeService.addTemplateToContentType(mirageTemplate, null);
		}
		catch (CMSException cmse) {
			ExceptionTranslator.translate(cmse);
		}
		catch (ValidationException ve) {

			// Validation Exception will not be thrown
			// as no Template checking is added

		}

		// Get the new Template from the MirageTemplate object

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

		JournalTemplate template = journalTemplatePersistence.findByG_T(
			groupId, templateId);

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

		JournalTemplate template = journalTemplatePersistence.findByG_T(
			groupId, templateId);

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

		JournalTemplate template = getJournalTemplate(groupId, templateId);

		String xsl = template.getXsl();

		if ((xsl != null) && (xsl.indexOf("\\n") != -1)) {
			xsl = StringUtil.replace(
				xsl,
				new String[] {"\\n", "\\r"},
				new String[] {"\n", "\r"});

			template.setXsl(xsl);

			MirageTemplate mirageTemplate = new MirageTemplate(template);

			ContentTypeService contentTypeService =
				MirageServiceFactory.getContentTypeService();

			try {
				contentTypeService.updateTemplateOfContentType(
					mirageTemplate, null, null);
			}
			catch (ValidationException ve) {

				// Ignore this as CMSException is thrown

			}
			catch (CMSException cmse) {
				ExceptionTranslator.translate(cmse);
			}
		}
	}

	public void deleteTemplate(long groupId, String templateId)
		throws PortalException, SystemException {

		templateId = templateId.trim().toUpperCase();

		JournalTemplate template = getJournalTemplate(groupId, templateId);

		deleteTemplate(template);
	}

	public void deleteTemplate(JournalTemplate template)
		throws PortalException, SystemException {

		int articleCount = 0;

		MirageTemplate mirageTemplate = new MirageTemplate(template);

		MirageTemplateCriteria criteria = new MirageTemplateCriteria(
			MirageTemplateCriteria.ARTICLE_COUNT_BY_G_T);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();

		try {
			articleCount = contentTypeService.getTemplatesCount(
				null, mirageTemplate, criteria);
		}
		catch (CMSException cmse) {
			throw (SystemException)cmse.getCause();
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
				null, mirageTemplate);
		}
		catch (CMSException cmse) {
			ExceptionTranslator.translate(cmse);
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

		MirageTemplate mirageTemplate = new MirageTemplate(journalTemplate);

		MirageTemplateCriteria criteria = new MirageTemplateCriteria(
			MirageTemplateCriteria.FIND_BY_G_S);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();

		List<Template> templates = null;

		try {
			templates = contentTypeService.getTemplates(
				null, mirageTemplate, criteria);
		}
		catch (TemplateNotFoundException tnfe) {
			throw (SystemException)tnfe.getCause();
		}

		if (templates == null) {
			return null;
		}

		List<JournalTemplate> journalTemplates =
			new ArrayList<JournalTemplate>(templates.size());

		for (Template template : templates) {
			journalTemplates.add(((MirageTemplate)template).getTemplate());
		}

		return journalTemplates;
	}

	public List<JournalTemplate> getStructureTemplates(
			long groupId, String structureId, int start, int end)
		throws SystemException {

		JournalTemplate journalTemplate = new JournalTemplateImpl();

		journalTemplate.setGroupId(groupId);
		journalTemplate.setStructureId(structureId);

		MirageTemplate mirageTemplate = new MirageTemplate(journalTemplate);

		MirageTemplateCriteria criteria = new MirageTemplateCriteria(
			MirageTemplateCriteria.FIND_BY_G_S_WITH_LIMIT);

		criteria.setValue(MirageTemplateCriteria.RANGE_START, start);
		criteria.setValue(MirageTemplateCriteria.RANGE_END, end);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();

		List<Template> templates = null;

		try {
			templates = contentTypeService.getTemplates(
				null, mirageTemplate, criteria);
		}
		catch (TemplateNotFoundException tnfe) {
			throw (SystemException)tnfe.getCause();
		}

		if (templates == null) {
			return null;
		}

		List<JournalTemplate> journalTemplates =
			new ArrayList<JournalTemplate>(templates.size());

		for (Template template : templates) {
			journalTemplates.add(((MirageTemplate) template).getTemplate());
		}

		return journalTemplates;
	}

	public int getStructureTemplatesCount(long groupId, String structureId)
		throws SystemException {

		JournalTemplate journalTemplate = new JournalTemplateImpl();

		journalTemplate.setGroupId(groupId);
		journalTemplate.setStructureId(structureId);

		MirageTemplate mirageTemplate = new MirageTemplate(journalTemplate);

		MirageTemplateCriteria criteria = new MirageTemplateCriteria(
			MirageTemplateCriteria.COUNT_BY_G_S);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();

		int count = -1;

		try {
			count = contentTypeService.getTemplatesCount(
				null, mirageTemplate, criteria);
		}
		catch (CMSException cmse) {
			throw (SystemException)cmse.getCause();
		}

		return count;
	}

	public JournalTemplate getTemplate(long id)
		throws PortalException, SystemException {

		JournalTemplate template = new JournalTemplateImpl();

		template.setId(id);

		MirageTemplate mirageTemplate = new MirageTemplate(template);

		MirageTemplateCriteria criteria = new MirageTemplateCriteria(
			MirageTemplateCriteria.FIND_BY_PRIMARY_KEY);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();

		try {
			mirageTemplate = (MirageTemplate)contentTypeService.getTemplate(
				mirageTemplate, criteria);
		}
		catch (TemplateNotFoundException tnfe) {
			ExceptionTranslator.translate(tnfe);
		}

		return mirageTemplate.getTemplate();
	}

	public JournalTemplate getTemplate(long groupId, String templateId)
		throws PortalException, SystemException {

		templateId = GetterUtil.getString(templateId).toUpperCase();

		if (groupId == 0) {
			_log.error(
				"No group id was passed for " + templateId + ". Group id is " +
					"required since 4.2.0. Please update all custom code and " +
						"data that references templates without a group id.");

			List<JournalTemplate> templates =
				getTemplates(templateId);

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
			return getJournalTemplate(groupId, templateId);
		}
	}

	public JournalTemplate getTemplateBySmallImageId(long smallImageId)
		throws PortalException, SystemException {

		JournalTemplate template = new JournalTemplateImpl();

		template.setSmallImageId(smallImageId);

		MirageTemplate mirageTemplate = new MirageTemplate(template);

		MirageTemplateCriteria criteria = new MirageTemplateCriteria(
			MirageTemplateCriteria.FIND_BY_SMALL_IMAGE_ID);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();

		try {
			mirageTemplate = (MirageTemplate)contentTypeService.getTemplate(
				mirageTemplate, criteria);
		}
		catch (TemplateNotFoundException tnfe) {
			ExceptionTranslator.translate(tnfe);
		}

		return mirageTemplate.getTemplate();
	}

	public List<JournalTemplate> getTemplates()
		throws SystemException {

		MirageTemplateCriteria criteria = new MirageTemplateCriteria(
			MirageTemplateCriteria.FIND_ALL);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();

		List<Template> templates = null;

		try {
			templates = contentTypeService.getTemplates(null, null, criteria);
		}
		catch (TemplateNotFoundException tnfe) {
			throw (SystemException)tnfe.getCause();
		}

		if (templates == null) {
			return null;
		}

		List<JournalTemplate> journalTemplates =
			new ArrayList<JournalTemplate>(templates.size());

		for (Template template : templates) {
			journalTemplates.add(((MirageTemplate)template).getTemplate());
		}

		return journalTemplates;
	}

	public List<JournalTemplate> getTemplates(long groupId)
		throws SystemException {

		JournalTemplate journalTemplate = new JournalTemplateImpl();

		journalTemplate.setGroupId(groupId);

		MirageTemplate mirageTemplate = new MirageTemplate(journalTemplate);

		MirageTemplateCriteria criteria = new MirageTemplateCriteria(
			MirageTemplateCriteria.FIND_BY_GROUP_ID);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();

		List<Template> templates = null;

		try {
			templates = contentTypeService.getTemplates(
				null, mirageTemplate, criteria);
		}
		catch (TemplateNotFoundException tnfe) {
			throw (SystemException)tnfe.getCause();
		}

		if (templates == null) {
			return null;
		}

		List<JournalTemplate> journalTemplates =
			new ArrayList<JournalTemplate>(templates.size());

		for (Template template : templates) {
			journalTemplates.add(((MirageTemplate)template).getTemplate());
		}

		return journalTemplates;
	}

	public List<JournalTemplate> getTemplates(long groupId, int start, int end)
		throws SystemException {

		JournalTemplate journalTemplate = new JournalTemplateImpl();

		journalTemplate.setGroupId(groupId);

		MirageTemplate mirageTemplate = new MirageTemplate(journalTemplate);

		MirageTemplateCriteria criteria = new MirageTemplateCriteria(
			MirageTemplateCriteria.FIND_BY_GROUP_ID_WITH_LIMIT);

		criteria.setValue(MirageTemplateCriteria.RANGE_START, start);
		criteria.setValue(MirageTemplateCriteria.RANGE_END, end);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();

		List<Template> templates = null;

		try {
			templates = contentTypeService.getTemplates(
				null, mirageTemplate, criteria);
		}
		catch (TemplateNotFoundException tnfe) {
			throw (SystemException)tnfe.getCause();
		}

		if (templates == null) {
			return null;
		}

		List<JournalTemplate> journalTemplates =
			new ArrayList<JournalTemplate>(templates.size());

		for (Template template : templates) {
			journalTemplates.add(((MirageTemplate)template).getTemplate());
		}

		return journalTemplates;
	}

	public int getTemplatesCount(long groupId)
		throws SystemException {

		JournalTemplate journalTemplate = new JournalTemplateImpl();

		journalTemplate.setGroupId(groupId);

		MirageTemplate mirageTemplate = new MirageTemplate(journalTemplate);

		MirageTemplateCriteria criteria = new MirageTemplateCriteria(
			MirageTemplateCriteria.COUNT_BY_GROUP_ID);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();

		int count = -1;

		try {
			count = contentTypeService.getTemplatesCount(
				null, mirageTemplate, criteria);
		}
		catch (CMSException cmse) {
			throw (SystemException)cmse.getCause();
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

		SmartCriteria criteria = new SmartCriteria();

		criteria.add(MirageTemplateCriteria.QUERY,
			MirageTemplateCriteria.FIND_BY_KEYWORDS);
		criteria.add(MirageTemplateCriteria.COMPANY_ID, companyId);
		criteria.add(MirageTemplateCriteria.GROUP_ID, groupId);
		criteria.add(MirageTemplateCriteria.KEYWORDS, keywords);
		criteria.add(MirageTemplateCriteria.STRUCTURE_ID, structureId);
		criteria.add(MirageTemplateCriteria.STRUCTURE_ID_COMPARATOR,
			structureIdComparator);
		criteria.add(MirageTemplateCriteria.RANGE_START, start);
		criteria.add(MirageTemplateCriteria.RANGE_END, end);

		criteria.setOrderByComparator(obc);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();

		List<Template> templates = null;

		try {
			templates = contentTypeService.searchTemplates(
				criteria.getCriteria());
		}
		catch (CMSException cmse) {
			throw (SystemException)cmse.getCause();
		}

		return getTemplates(templates);
	}

	public List<JournalTemplate> search(
			long companyId, long groupId, String templateId, String structureId,
			String structureIdComparator, String name, String description,
			boolean andOperator, int start, int end, OrderByComparator obc)
		throws SystemException {

		SmartCriteria criteria = new SmartCriteria();
		criteria.add(MirageTemplateCriteria.QUERY,
			MirageTemplateCriteria.FIND_BY_C_G_T_S_N_D);
		criteria.add(MirageTemplateCriteria.COMPANY_ID, companyId);
		criteria.add(MirageTemplateCriteria.GROUP_ID, groupId);
		criteria.add(MirageTemplateCriteria.TEMPLATE_ID, templateId);
		criteria.add(MirageTemplateCriteria.STRUCTURE_ID, structureId);
		criteria.add(MirageTemplateCriteria.STRUCTURE_ID_COMPARATOR,
			structureIdComparator);
		criteria.add(MirageTemplateCriteria.NAME, name);
		criteria.add(MirageTemplateCriteria.DESCRIPTION, description);
		criteria.add(MirageTemplateCriteria.RANGE_START, start);
		criteria.add(MirageTemplateCriteria.RANGE_END, end);

		criteria.setAndOperator(andOperator);
		criteria.setOrderByComparator(obc);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();

		List<Template> templates = null;

		try {
			templates = contentTypeService.searchTemplates(
				criteria.getCriteria());
		}
		catch (CMSException cmse) {
			throw (SystemException)cmse.getCause();
		}

		return getTemplates(templates);
	}

	public int searchCount(
			long companyId, long groupId, String keywords, String structureId,
			String structureIdComparator)
		throws SystemException {

		SmartCriteria criteria = new SmartCriteria();

		criteria.add(MirageTemplateCriteria.QUERY,
			MirageTemplateCriteria.COUNT_BY_KEYWORDS);
		criteria.add(MirageTemplateCriteria.COMPANY_ID, companyId);
		criteria.add(MirageTemplateCriteria.GROUP_ID, groupId);
		criteria.add(MirageTemplateCriteria.KEYWORDS, keywords);
		criteria.add(MirageTemplateCriteria.STRUCTURE_ID, structureId);
		criteria.add(MirageTemplateCriteria.STRUCTURE_ID_COMPARATOR,
			structureIdComparator);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();

		try {
			return contentTypeService.searchTemplatesCount(
				criteria.getCriteria());
		}
		catch (CMSException cmse) {
			throw (SystemException)cmse.getCause();
		}
	}

	public int searchCount(
			long companyId, long groupId, String templateId, String structureId,
			String structureIdComparator, String name, String description,
			boolean andOperator)
		throws SystemException {

		SmartCriteria criteria = new SmartCriteria();

		criteria.add(MirageTemplateCriteria.QUERY,
			MirageTemplateCriteria.COUNT_BY_C_G_T_S_N_D);
		criteria.add(MirageTemplateCriteria.COMPANY_ID, companyId);
		criteria.add(MirageTemplateCriteria.GROUP_ID, groupId);
		criteria.add(MirageTemplateCriteria.TEMPLATE_ID, templateId);
		criteria.add(MirageTemplateCriteria.STRUCTURE_ID, structureId);
		criteria.add(MirageTemplateCriteria.STRUCTURE_ID_COMPARATOR,
			structureIdComparator);
		criteria.add(MirageTemplateCriteria.NAME, name);
		criteria.add(MirageTemplateCriteria.DESCRIPTION, description);

		criteria.setAndOperator(andOperator);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();

		try {
			return contentTypeService.searchTemplatesCount(
				criteria.getCriteria());
		}
		catch (CMSException cmse) {
			throw (SystemException)cmse.getCause();
		}
	}

	public JournalTemplate updateTemplate(
			long groupId, String templateId, String structureId, String name,
			String description, String xsl, boolean formatXsl, String langType,
			boolean cacheable, boolean smallImage, String smallImageURL,
			File smallFile)
		throws PortalException, SystemException {

		JournalTemplate template = new JournalTemplateImpl();

		template.setGroupId(groupId);
		template.setTemplateId(templateId);
		template.setStructureId(structureId);
		template.setName(name);
		template.setDescription(description);
		template.setXsl(xsl);
		template.setLangType(langType);
		template.setCacheable(cacheable);
		template.setSmallImage(smallImage);
		template.setSmallImageURL(smallImageURL);

		MirageTemplate mirageTemplate = new MirageTemplate(template);

		mirageTemplate.setFormatXsl(formatXsl);
		mirageTemplate.setSmallFile(smallFile);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();

		try {
			contentTypeService.updateTemplateOfContentType(
				mirageTemplate, null);
		}
		catch (ValidationException ex) {

			// Ignore this, as the exceptions are wrapped as CMSException

		}
		catch (CMSException cmse) {
			ExceptionTranslator.translate(cmse);
		}

		return mirageTemplate.getTemplate();
	}

	protected JournalTemplate getJournalTemplate(
			long groupId, String templateId)
		throws PortalException, SystemException {

		JournalTemplate template = new JournalTemplateImpl();

		template.setGroupId(groupId);
		template.setTemplateId(templateId);

		MirageTemplate mirageTemplate = new MirageTemplate(template);

		MirageTemplateCriteria criteria = new MirageTemplateCriteria(
			MirageTemplateCriteria.FIND_BY_G_T);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();

		try {
			mirageTemplate = (MirageTemplate)contentTypeService.getTemplate(
				mirageTemplate, criteria);
		}
		catch (TemplateNotFoundException tnfe) {
			ExceptionTranslator.translate(tnfe);
		}

		return mirageTemplate.getTemplate();
	}

	protected List<JournalTemplate> getTemplates(List<Template> templates) {
		if (templates == null) {
			return null;
		}

		List<JournalTemplate> journalTemplates =
			new ArrayList<JournalTemplate>(templates.size());

		for (Template template : templates) {
			journalTemplates.add(((MirageTemplate) template).getTemplate());
		}

		return journalTemplates;
	}

	protected List<JournalTemplate> getTemplates(String templateId)
		throws SystemException {

		JournalTemplate journalTemplate = new JournalTemplateImpl();

		journalTemplate.setTemplateId(templateId);

		MirageTemplate mirageTemplate = new MirageTemplate(journalTemplate);

		MirageTemplateCriteria criteria = new MirageTemplateCriteria(
			MirageTemplateCriteria.FIND_BY_TEMPLATE_ID);

		ContentTypeService contentTypeService =
			MirageServiceFactory.getContentTypeService();

		List<Template> templates = null;

		try {
			templates = contentTypeService.getTemplates(
				null, mirageTemplate, criteria);
		}
		catch (TemplateNotFoundException tnfe) {
			throw (SystemException)tnfe.getCause();
		}

		if (templates == null) {
			return null;
		}

		List<JournalTemplate> journalTemplates =
			new ArrayList<JournalTemplate>(templates.size());

		for (Template template : templates) {
			journalTemplates.add(((MirageTemplate)template).getTemplate());
		}

		return journalTemplates;
	}

	private static final Log _log =
		LogFactory.getLog(JournalTemplateLocalServiceImpl.class);

}