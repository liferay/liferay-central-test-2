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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.mirage.service;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.mirage.model.MirageTemplate;
import com.liferay.portal.mirage.model.MirageTemplateCriteria;
import com.liferay.portal.mirage.util.SmartFields;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.journal.DuplicateTemplateIdException;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.TemplateDescriptionException;
import com.liferay.portlet.journal.TemplateIdException;
import com.liferay.portlet.journal.TemplateNameException;
import com.liferay.portlet.journal.TemplateSmallImageNameException;
import com.liferay.portlet.journal.TemplateSmallImageSizeException;
import com.liferay.portlet.journal.TemplateXslException;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.journal.service.persistence.JournalTemplateFinderUtil;
import com.liferay.portlet.journal.service.persistence.JournalTemplateUtil;
import com.liferay.portlet.journal.util.JournalUtil;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.exception.TemplateNotFoundException;
import com.sun.portal.cms.mirage.exception.ValidationException;
import com.sun.portal.cms.mirage.model.custom.Category;
import com.sun.portal.cms.mirage.model.custom.ContentType;
import com.sun.portal.cms.mirage.model.custom.OptionalCriteria;
import com.sun.portal.cms.mirage.model.custom.Template;
import com.sun.portal.cms.mirage.model.custom.UpdateCriteria;
import com.sun.portal.cms.mirage.model.search.SearchCriteria;
import com.sun.portal.cms.mirage.service.custom.ContentTypeService;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.DocumentException;

/**
 * <a href="ContentTypeServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Prakash Reddy
 * @author Karthik Sudarshan
 *
 */
public class ContentTypeServiceImpl implements ContentTypeService {

	public void addTemplateToContentType(
			Template mirageJournalTemplate, ContentType contentType)
		throws CMSException, ValidationException {

		MirageTemplate mirageTemplate = (MirageTemplate)mirageJournalTemplate;

		JournalTemplate template = mirageTemplate.getTemplate();

		String uuid = template.getUuid();
		long userId = template.getUserId();
		String templateId = template.getTemplateId();
		boolean autoTemplateId = mirageTemplate.isAutoTemplateId();
		long groupId = template.getGroupId();
		String structureId = template.getStructureId();
		String name = template.getName();
		String description = template.getDescription();
		String xsl = template.getXsl();
		boolean formatXsl = mirageTemplate.isFormatXsl();
		String langType = template.getLangType();
		boolean cacheable = template.isCacheable();
		boolean smallImage = template.getSmallImage();
		String smallImageURL = template.getSmallImageURL();
		File smallFile = mirageTemplate.getSmallFile();

		// Template

		try {
			User user = UserUtil.findByPrimaryKey(userId);

			templateId = templateId.trim().toUpperCase();

			Date now = new Date();

			try {
				if (formatXsl) {
					if (langType.equals(JournalTemplateImpl.LANG_TYPE_VM)) {
						xsl = JournalUtil.formatVM(xsl);
					}
					else {
						xsl = JournalUtil.formatXML(xsl);
					}
				}
			}
			catch (DocumentException de) {
				throw new TemplateXslException();
			}
			catch (IOException ioe) {
				throw new TemplateXslException();
			}

			byte[] smallBytes = null;

			try {
				smallBytes = FileUtil.getBytes(smallFile);
			}
			catch (IOException ioe) {
			}

			validate(
				groupId, templateId, autoTemplateId, name, description, xsl,
				smallImage, smallImageURL, smallFile, smallBytes);

			if (autoTemplateId) {
				templateId = String.valueOf(
					CounterLocalServiceUtil.increment());
			}

			long id = CounterLocalServiceUtil.increment();

			template = JournalTemplateUtil.create(id);

			template.setUuid(uuid);
			template.setGroupId(groupId);
			template.setCompanyId(user.getCompanyId());
			template.setUserId(user.getUserId());
			template.setUserName(user.getFullName());
			template.setCreateDate(now);
			template.setModifiedDate(now);
			template.setTemplateId(templateId);
			template.setStructureId(structureId);
			template.setName(name);
			template.setDescription(description);
			template.setXsl(xsl);
			template.setLangType(langType);
			template.setCacheable(cacheable);
			template.setSmallImage(smallImage);
			template.setSmallImageId(CounterLocalServiceUtil.increment());
			template.setSmallImageURL(smallImageURL);

			JournalTemplateUtil.update(template, false);

			// Small image

			saveImages(
				smallImage, template.getSmallImageId(), smallFile, smallBytes);

			mirageTemplate.setTemplate(template);
		}
		catch (PortalException pe) {
			throw new CMSException(pe.getMessage(), pe);
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
	}

	public void assignDefaultTemplate(
		ContentType contentType, Template template) {

		throw new UnsupportedOperationException();
	}

	public boolean checkContentTypeExists(String contentTypeUUID) {
		throw new UnsupportedOperationException();
	}

	public void checkOutTemplate(Template template, ContentType contentType) {
		throw new UnsupportedOperationException();
	}

	public int contentTypeSearchCount(
		Category category, SearchCriteria searchCriteria) {

		throw new UnsupportedOperationException();
	}

	public void createContentType(ContentType contentType) {
		throw new UnsupportedOperationException();
	}

	public void deleteContentType(ContentType contentType) {
		throw new UnsupportedOperationException();
	}

	public void deleteTemplateOfContentType(
			ContentType contentType, Template mirageJournalTemplate)
		throws CMSException {

		MirageTemplate mirageTemplate = (MirageTemplate)mirageJournalTemplate;

		JournalTemplate template = mirageTemplate.getTemplate();

		try {
			JournalTemplateUtil.remove(template.getPrimaryKey());
		}
		catch (PortalException pe) {
			throw new CMSException(pe.getMessage(), pe);
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
	}

	public void deleteTemplatesOfContentType(
		ContentType contentType, Template[] templatesToBeDeleted) {

		throw new UnsupportedOperationException();
	}

	public List<Template> getAllVersionsOfTemplate(
		Template template, ContentType contentType) {

		throw new UnsupportedOperationException();
	}

	public List<String> getAvailableContentTypeNames(Category category) {
		throw new UnsupportedOperationException();
	}

	public List<ContentType> getAvailableContentTypes(Category category) {
		throw new UnsupportedOperationException();
	}

	public ContentType getContentType(ContentType contentType) {
		throw new UnsupportedOperationException();
	}

	public ContentType getContentType(
		ContentType contentType, OptionalCriteria optionalCriteria) {

		throw new UnsupportedOperationException();
	}

	public ContentType getContentTypeByNameAndCategory(
		String contentTypeName, Category category) {

		throw new UnsupportedOperationException();
	}

	public ContentType getContentTypeByUUID(String contentTypeUUID) {
		throw new UnsupportedOperationException();
	}

	public Template getLatestVersionOfTemplate(
		Template template, ContentType contentType) {

		throw new UnsupportedOperationException();
	}

	public Template getTemplate(
			Template mirageJournalTemplate, OptionalCriteria optionalCriteria)
		throws TemplateNotFoundException {

		MirageTemplate mirageTemplate = (MirageTemplate)mirageJournalTemplate;

		JournalTemplate template = mirageTemplate.getTemplate();

		MirageTemplateCriteria criteria =
			(MirageTemplateCriteria)optionalCriteria;

		String query = criteria.getString(MirageTemplateCriteria.QUERY);

		if (query == null) {
			return null;
		}

		try {
			if (query.equals(MirageTemplateCriteria.FIND_BY_PRIMARY_KEY)) {
				mirageTemplate.setTemplate(
					JournalTemplateUtil.findByPrimaryKey(template.getId()));
			}
			else if (query.equals(
				MirageTemplateCriteria.FIND_BY_SMALL_IMAGE_ID)) {
				mirageTemplate.setTemplate(
					JournalTemplateUtil.findBySmallImageId(
						template.getSmallImageId()));
			}
			else if (query.equals(MirageTemplateCriteria.FIND_BY_G_T)) {
				mirageTemplate.setTemplate(JournalTemplateUtil.findByG_T(
					template.getGroupId(), template.getTemplateId()));
			}
		}
		catch (PortalException pe) {
			throw new TemplateNotFoundException(
				pe.getMessage(), pe.getMessage(), pe);
		}
		catch (SystemException se) {
			throw new TemplateNotFoundException(
				se.getMessage(), se.getMessage(), se);
		}

		return mirageTemplate;
	}

	public List<Template> getTemplates(
			ContentType contentType, Template mirageJournalTemplate,
			OptionalCriteria optionalCriteria)
		throws TemplateNotFoundException {

		List<Template> templates = new ArrayList<Template>();

		MirageTemplateCriteria criteria =
			(MirageTemplateCriteria)optionalCriteria;

		String query = criteria.getString(MirageTemplateCriteria.QUERY);

		if (query == null) {
			return null;
		}

		try {
			if (query.equals(MirageTemplateCriteria.FIND_BY_G_S)) {
				MirageTemplate mirageTemplate =
					(MirageTemplate)mirageJournalTemplate;

				JournalTemplate template = mirageTemplate.getTemplate();

				templates = getTemplates(
					template.getGroupId(), template.getStructureId());
			}
			else if (query.equals(
				MirageTemplateCriteria.FIND_BY_G_S_WITH_LIMIT)) {
				MirageTemplate mirageTemplate =
					(MirageTemplate)mirageJournalTemplate;

				JournalTemplate template = mirageTemplate.getTemplate();

				int start = criteria.getInt(MirageTemplateCriteria.RANGE_START);
				int end = criteria.getInt(MirageTemplateCriteria.RANGE_END);

				templates = getTemplates(
					template.getGroupId(), template.getStructureId(), start,
					end);
			}
			else if (query.equals(MirageTemplateCriteria.FIND_ALL)){
				templates = getAllTemplates();
			}
			else if (query.equals(MirageTemplateCriteria.FIND_BY_GROUP_ID)) {
				MirageTemplate mirageTemplate =
					(MirageTemplate)mirageJournalTemplate;

				JournalTemplate template = mirageTemplate.getTemplate();

				templates = getTemplates(template.getGroupId());
			}
			else if (query.equals(
				MirageTemplateCriteria.FIND_BY_GROUP_ID_WITH_LIMIT)) {
				MirageTemplate mirageTemplate =
					(MirageTemplate)mirageJournalTemplate;

				JournalTemplate template = mirageTemplate.getTemplate();

				int start = criteria.getInt(MirageTemplateCriteria.RANGE_START);
				int end = criteria.getInt(MirageTemplateCriteria.RANGE_END);

				templates = getTemplates(
					template.getGroupId(), start, end);
			}
			else if (query.equals(MirageTemplateCriteria.FIND_BY_TEMPLATE_ID)) {
				MirageTemplate mirageTemplate =
					(MirageTemplate)mirageJournalTemplate;

				JournalTemplate template = mirageTemplate.getTemplate();

				templates = getTemplates(template.getTemplateId());
			}
		}
		catch (SystemException se) {
			throw new TemplateNotFoundException(
				se.getMessage(), se.getMessage(), se);
		}

		return templates;
	}

	public int getTemplatesCount(
			ContentType contentType, Template mirageJournalTemplate,
			OptionalCriteria optionalCriteria)
		throws CMSException {

		int count = -1;

		MirageTemplateCriteria criteria =
			(MirageTemplateCriteria)optionalCriteria;

		String query = criteria.getString(MirageTemplateCriteria.QUERY);

		if (query == null) {
			return count;
		}

		try {
			if (query.equals(MirageTemplateCriteria.COUNT_BY_G_S)) {
				MirageTemplate mirageTemplate =
					(MirageTemplate)mirageJournalTemplate;

				JournalTemplate template = mirageTemplate.getTemplate();

				count = JournalTemplateUtil.countByG_S(
					template.getGroupId(), template.getStructureId());
			}
			else if (query.equals(MirageTemplateCriteria.COUNT_BY_GROUP_ID)) {
				MirageTemplate mirageTemplate =
					(MirageTemplate)mirageJournalTemplate;

				JournalTemplate template = mirageTemplate.getTemplate();

				count = JournalTemplateUtil.countByGroupId(
					template.getGroupId());
			}
			else if (query.equals(
				MirageTemplateCriteria.ARTICLE_COUNT_BY_G_T)) {
				MirageTemplate mirageTemplate =
					(MirageTemplate)mirageJournalTemplate;

				JournalTemplate template = mirageTemplate.getTemplate();

				count = JournalArticleUtil.countByG_T(
					template.getGroupId(), template.getTemplateId());
			}
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se.getMessage(), se);
		}

		return count;
	}

	public Template getTemplateWithUUID(String templateUUID) {
		throw new UnsupportedOperationException();
	}

	public boolean isContentTypeEditable(String contentTypeUUID) {
		throw new UnsupportedOperationException();
	}

	public void revertChangesTemplateForTemplate(
		Template template, ContentType contentType) {

		throw new UnsupportedOperationException();
	}

	public void saveNewVersionOfTemplate(
		Template template, ContentType contentType) {

		throw new UnsupportedOperationException();
	}

	public List<ContentType> searchContentTypes(SearchCriteria searchCriteria) {
		throw new UnsupportedOperationException();
	}

	public List<ContentType> searchContentTypesByCategory(
		Category category, SearchCriteria searchCriteria) {

		throw new UnsupportedOperationException();
	}

	public List<Template> searchTemplates(SearchCriteria searchCriteria)
		throws CMSException {

		try {
			List<JournalTemplate> journalTemplates = null;

			SmartFields smartFields = new SmartFields(
				searchCriteria.getSearchFieldValues());

			String query = smartFields.getString(MirageTemplateCriteria.QUERY);
			long companyId = smartFields.getLong(
				MirageTemplateCriteria.COMPANY_ID);
			long groupId = smartFields.getLong(MirageTemplateCriteria.GROUP_ID);
			String structureId = smartFields.getString(
				MirageTemplateCriteria.STRUCTURE_ID);
			String structureIdComparator = smartFields.getString(
				MirageTemplateCriteria.STRUCTURE_ID_COMPARATOR);
			int start = smartFields.getInteger(
				MirageTemplateCriteria.RANGE_START);
			int end = smartFields.getInteger(MirageTemplateCriteria.RANGE_END);
			OrderByComparator obc =
				(OrderByComparator)searchCriteria.getOrderByComparator();

			if (query.equals(MirageTemplateCriteria.FIND_BY_KEYWORDS)) {
				String keywords = smartFields.getString(
					MirageTemplateCriteria.KEYWORDS);

				journalTemplates = JournalTemplateFinderUtil.findByKeywords(
					companyId, groupId, keywords, structureId,
					structureIdComparator, start, end, obc);
			}
			else if (query.equals(MirageTemplateCriteria.FIND_BY_C_G_T_S_N_D)) {
				String templateId = smartFields.getString(
					MirageTemplateCriteria.TEMPLATE_ID);
				String name = smartFields.getString(
					MirageTemplateCriteria.NAME);
				String description = smartFields.getString(
					MirageTemplateCriteria.DESCRIPTION);
				boolean andOperator = searchCriteria.isMatchAnyOneField();

				journalTemplates = JournalTemplateFinderUtil.findByC_G_T_S_N_D(
					companyId, groupId, templateId, structureId,
					structureIdComparator, name, description, andOperator,
					start, end, obc);
			}

			return getTemplatesFromJournalTemplates(journalTemplates);
		}
		catch (SystemException se) {
			throw new TemplateNotFoundException(
				se.getMessage(), se.getMessage(), se);
		}
	}

	public int searchTemplatesCount(SearchCriteria searchCriteria)
		throws CMSException {

		try {
			int count = -1;

			SmartFields smartFields = new SmartFields(
				searchCriteria.getSearchFieldValues());

			String query = smartFields.getString(MirageTemplateCriteria.QUERY);
			long companyId = smartFields.getLong(
				MirageTemplateCriteria.COMPANY_ID);
			long groupId = smartFields.getLong(MirageTemplateCriteria.GROUP_ID);
			String structureId = smartFields.getString(
				MirageTemplateCriteria.STRUCTURE_ID);
			String structureIdComparator = smartFields.getString(
				MirageTemplateCriteria.STRUCTURE_ID_COMPARATOR);

			if (query.equals(MirageTemplateCriteria.COUNT_BY_KEYWORDS)) {
				String keywords = smartFields.getString(
					MirageTemplateCriteria.KEYWORDS);

				count = JournalTemplateFinderUtil.countByKeywords(
					companyId, groupId, keywords, structureId,
					structureIdComparator);
			}
			else if (query.equals(
				MirageTemplateCriteria.COUNT_BY_C_G_T_S_N_D)) {
				String templateId = smartFields.getString(
					MirageTemplateCriteria.TEMPLATE_ID);
				String name = smartFields.getString(
					MirageTemplateCriteria.NAME);
				String description = smartFields.getString(
					MirageTemplateCriteria.DESCRIPTION);
				boolean andOperator = searchCriteria.isMatchAnyOneField();

				count = JournalTemplateFinderUtil.countByC_G_T_S_N_D(
					companyId, groupId, templateId, structureId,
					structureIdComparator, name, description, andOperator);
			}

			return count;
		}
		catch (SystemException se) {
			throw new TemplateNotFoundException(
				se.getMessage(), se.getMessage(), se);
		}
	}

	public List<Template> searchTemplatesOfContentType(
			ContentType contentType, SearchCriteria criteria)
		throws CMSException {

		return null;
	}

	public void unassignDefaultTemplate(ContentType contentType) {
		throw new UnsupportedOperationException();
	}

	public void updateCategoryOfContentType(ContentType contentType) {
		throw new UnsupportedOperationException();
	}

	public void updateContentType(ContentType contentType) {
		throw new UnsupportedOperationException();
	}

	public void updateContentType(
		ContentType contentType, UpdateCriteria updateCriteria) {

		throw new UnsupportedOperationException();
	}

	public void updateTemplateOfContentType(
			Template mirageJournalTemplate, ContentType contentType)
		throws CMSException {

		MirageTemplate mirageTemplate = (MirageTemplate)mirageJournalTemplate;

		JournalTemplate template = mirageTemplate.getTemplate();

		long groupId = template.getGroupId();
		String templateId = template.getTemplateId();
		String structureId = template.getStructureId();
		String name = template.getName();
		String description = template.getDescription();
		String xsl = template.getXsl();
		boolean formatXsl = mirageTemplate.isFormatXsl();
		String langType = template.getLangType();
		boolean cacheable = template.isCacheable();
		boolean smallImage = template.getSmallImage();
		String smallImageURL = template.getSmallImageURL();
		File smallFile = mirageTemplate.getSmallFile();

		// Template

		try {
			templateId = templateId.trim().toUpperCase();

			try {
				if (formatXsl) {
					if (langType.equals(JournalTemplateImpl.LANG_TYPE_VM)) {
						xsl = JournalUtil.formatVM(xsl);
					}
					else {
						xsl = JournalUtil.formatXML(xsl);
					}
				}
			}
			catch (DocumentException de) {
				throw new TemplateXslException();
			}
			catch (IOException ioe) {
				throw new TemplateXslException();
			}

			byte[] smallBytes = null;

			try {
				smallBytes = FileUtil.getBytes(smallFile);
			}
			catch (IOException ioe) {
			}

			validate(
				name, description, xsl, smallImage, smallImageURL, smallFile,
				smallBytes);

			template = JournalTemplateUtil.findByG_T(groupId, templateId);

			template.setModifiedDate(new Date());

			if (Validator.isNull(template.getStructureId()) &&
				Validator.isNotNull(structureId)) {

				// Allow users to set the structure if and only if it currently
				// does not have one. Otherwise, you can have bad data because
				// there may be an existing article that has chosen to use a
				// structure and template combination that no longer exists.

				template.setStructureId(structureId);
			}

			template.setName(name);
			template.setDescription(description);
			template.setXsl(xsl);
			template.setLangType(langType);
			template.setCacheable(cacheable);
			template.setSmallImage(smallImage);
			template.setSmallImageURL(smallImageURL);

			JournalTemplateUtil.update(template, false);

			// Small image

			saveImages(
				smallImage, template.getSmallImageId(), smallFile,
				smallBytes);

			mirageTemplate.setTemplate(template);
		}
		catch (PortalException pe) {
			throw new CMSException(pe.getMessage(), pe);
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
	}

	public void updateTemplateOfContentType(
			Template mirageJournalTemplate, ContentType contentType,
			UpdateCriteria criteria)
		throws CMSException {

		try {
			MirageTemplate mirageTemplate =
				(MirageTemplate)mirageJournalTemplate;

			JournalTemplate template = mirageTemplate.getTemplate();

			JournalTemplateUtil.update(template, false);
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
	}

	public boolean validateTemplate(
		Template template, ContentType contentType) {

		throw new UnsupportedOperationException();
	}

	protected List<Template> getAllTemplates() throws SystemException {
		List<JournalTemplate> templates = JournalTemplateUtil.findAll();

		return getTemplatesFromJournalTemplates(templates);
	}

	protected List<Template> getTemplates(long groupId) throws SystemException {
		List<JournalTemplate> templates =
			JournalTemplateUtil.findByGroupId(groupId);

		return getTemplatesFromJournalTemplates(templates);
	}

	protected List<Template> getTemplates(long groupId, int start, int end)
		throws SystemException {

		List<JournalTemplate> templates =
			JournalTemplateUtil.findByGroupId(groupId, start, end);

		return getTemplatesFromJournalTemplates(templates);
	}

	protected List<Template> getTemplates(long groupId, String structureId)
		throws SystemException {

		List<JournalTemplate> templates =
			JournalTemplateUtil.findByG_S(groupId, structureId);

		return getTemplatesFromJournalTemplates(templates);
	}

	protected List<Template> getTemplates(
			long groupId, String structureId, int start, int end)
		throws SystemException {

		List<JournalTemplate> templates =
			JournalTemplateUtil.findByG_S(groupId, structureId, start, end);

		return getTemplatesFromJournalTemplates(templates);
	}

	protected List<Template> getTemplates(String templateId)
		throws SystemException {

		List<JournalTemplate> templates =
			JournalTemplateUtil.findByTemplateId(templateId);

		return getTemplatesFromJournalTemplates(templates);
	}

	protected List<Template> getTemplatesFromJournalTemplates(
		List<JournalTemplate> journalTemplates) {

		if (journalTemplates == null) {
			return null;
		}

		List<Template> templates =
			new ArrayList<Template>(journalTemplates.size());

		for (JournalTemplate template : journalTemplates) {
			templates.add(new MirageTemplate(template));
		}

		return templates;
	}

	protected void saveImages(
			boolean smallImage, long smallImageId, File smallFile,
			byte[] smallBytes)
		throws PortalException, SystemException {

		if (smallImage) {
			if ((smallFile != null) && (smallBytes != null)) {
				ImageLocalServiceUtil.updateImage(smallImageId, smallBytes);
			}
		}
		else {
			ImageLocalServiceUtil.deleteImage(smallImageId);
		}
	}

	protected void validate(
			long groupId, String templateId, boolean autoTemplateId,
			String name, String description, String xsl, boolean smallImage,
			String smallImageURL, File smallFile, byte[] smallBytes)
		throws PortalException, SystemException {

		if (!autoTemplateId) {
			if ((Validator.isNull(templateId)) ||
					(Validator.isNumber(templateId)) ||
						(templateId.indexOf(StringPool.SPACE) != -1)) {

				throw new TemplateIdException();
			}

			try {
				JournalTemplateUtil.findByG_T(groupId, templateId);

				throw new DuplicateTemplateIdException();
			}
			catch (NoSuchTemplateException nste) {
			}
		}

		validate(
			name, description, xsl, smallImage, smallImageURL, smallFile,
			smallBytes);
	}

	protected void validate(
			String name, String description, String xsl, boolean smallImage,
			String smallImageURL, File smallFile, byte[] smallBytes)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new TemplateNameException();
		}
		else if (Validator.isNull(description)) {
			throw new TemplateDescriptionException();
		}
		else if (Validator.isNull(xsl)) {
			throw new TemplateXslException();
		}

		String[] imageExtensions =
			PropsUtil.getArray(PropsKeys.JOURNAL_IMAGE_EXTENSIONS);

		if (smallImage && Validator.isNull(smallImageURL) &&
				smallFile != null && smallBytes != null) {

			String smallImageName = smallFile.getName();

			if (smallImageName != null) {
				boolean validSmallImageExtension = false;

				for (int i = 0; i < imageExtensions.length; i++) {
					if (StringPool.STAR.equals(imageExtensions[i]) ||
							StringUtil.endsWith(
								smallImageName, imageExtensions[i])) {

						validSmallImageExtension = true;

						break;
					}
				}

				if (!validSmallImageExtension) {
					throw new TemplateSmallImageNameException(smallImageName);
				}
			}

			long smallImageMaxSize =
				GetterUtil.getLong(
						PropsUtil.get(PropsKeys.JOURNAL_IMAGE_SMALL_MAX_SIZE));

			if ((smallImageMaxSize > 0) &&
					((smallBytes == null) ||
						(smallBytes.length > smallImageMaxSize))) {

				throw new TemplateSmallImageSizeException();
			}
		}
	}

}