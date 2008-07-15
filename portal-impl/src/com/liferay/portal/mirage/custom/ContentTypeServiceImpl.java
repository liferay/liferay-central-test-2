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

package com.liferay.portal.mirage.custom;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.mirage.model.JournalMirageTemplate;
import com.liferay.portal.mirage.model.JournalStructureContentType;
import com.liferay.portal.mirage.model.OptionalJournalStructureCriteria;
import com.liferay.portal.mirage.model.OptionalJournalTemplateCriteria;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.WebDAVPropsLocalServiceUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.journal.DuplicateStructureIdException;
import com.liferay.portlet.journal.DuplicateTemplateIdException;
import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.RequiredStructureException;
import com.liferay.portlet.journal.StructureDescriptionException;
import com.liferay.portlet.journal.StructureIdException;
import com.liferay.portlet.journal.StructureNameException;
import com.liferay.portlet.journal.StructureXsdException;
import com.liferay.portlet.journal.TemplateDescriptionException;
import com.liferay.portlet.journal.TemplateIdException;
import com.liferay.portlet.journal.TemplateNameException;
import com.liferay.portlet.journal.TemplateSmallImageNameException;
import com.liferay.portlet.journal.TemplateSmallImageSizeException;
import com.liferay.portlet.journal.TemplateXslException;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalStructureImpl;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.journal.service.persistence.JournalStructureFinderUtil;
import com.liferay.portlet.journal.service.persistence.JournalStructureUtil;
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
import com.sun.portal.cms.mirage.model.search.SearchFieldValue;
import com.sun.portal.cms.mirage.service.custom.ContentTypeService;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="ContentTypeServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Prakash Reddy
 * @author Karthik Sudarshan
 *
 */
public class ContentTypeServiceImpl implements ContentTypeService {

	public void addStructureResources(
			JournalStructure structure, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
				structure.getCompanyId(), structure.getGroupId(),
					structure.getUserId(), JournalStructure.class.getName(),
						structure.getId(), communityPermissions,
							guestPermissions);
	}

	public void addTemplateToContentType(
			Template mirageTemplate, ContentType contentType)
		throws CMSException, ValidationException {

		JournalMirageTemplate journalMirageTemplate =
			(JournalMirageTemplate) mirageTemplate;
		JournalTemplate template = journalMirageTemplate.getTemplate();
		JournalMirageTemplate.CreationAttributes creationAttributes =
			journalMirageTemplate.getCreationAttributes();

		boolean formatXsl = creationAttributes.isFormatXsl();
		boolean autoTemplateId = creationAttributes.isAutoTemplateId();
		File smallFile = creationAttributes.getSmallFile();

		String templateId = template.getTemplateId();
		long userId = template.getUserId();
		String langType = template.getLangType();
		String xsl = template.getXsl();
		long groupId = template.getGroupId();
		String name = template.getName();
		String description = template.getDescription();
		boolean smallImage = template.getSmallImage();
		String smallImageURL = template.getSmallImageURL();
		String uuid = template.getUuid();
		String structureId = template.getStructureId();
		boolean cacheable = template.isCacheable();

		try {

			// Template

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

			JournalTemplate newTemplate = JournalTemplateUtil.create(id);

			newTemplate.setUuid(uuid);
			newTemplate.setGroupId(groupId);
			newTemplate.setCompanyId(user.getCompanyId());
			newTemplate.setUserId(user.getUserId());
			newTemplate.setUserName(user.getFullName());
			newTemplate.setCreateDate(now);
			newTemplate.setModifiedDate(now);
			newTemplate.setTemplateId(templateId);
			newTemplate.setStructureId(structureId);
			newTemplate.setName(name);
			newTemplate.setDescription(description);
			newTemplate.setXsl(xsl);
			newTemplate.setLangType(langType);
			newTemplate.setCacheable(cacheable);
			newTemplate.setSmallImage(smallImage);
			newTemplate.setSmallImageId(CounterLocalServiceUtil.increment());
			newTemplate.setSmallImageURL(smallImageURL);

			JournalTemplateUtil.update(newTemplate, false);

			// Small image

			saveImages(
				smallImage, newTemplate.getSmallImageId(), smallFile,
					smallBytes);
			journalMirageTemplate.setTemplate(newTemplate);
		}
		catch (PortalException ex) {
			_log.error(ex.getMessage(), ex);
			throw new CMSException(ex.getMessage(), ex);
		}
		catch (SystemException ex) {
			_log.error(ex.getMessage(), ex);
			throw new CMSException(ex.getMessage(), ex);
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
			Category category, SearchCriteria searchCriteria)
		throws CMSException {

		int result = 0;
		List<SearchFieldValue> searchFields =
			searchCriteria.getSearchFieldValues();

		try {
			String finder =
				_findFieldValue(
					searchFields, OptionalJournalStructureCriteria.FINDER);

			if (OptionalJournalStructureCriteria.FIND_BY_GROUP.equals(finder)) {

				long groupId = Long.parseLong(category.getUuid());
				result = _getStructuresCount(groupId);

			}
			else if (OptionalJournalStructureCriteria.FIND_BY_KEYWORDS
															.equals(finder)) {

				long groupId = Long.parseLong(category.getUuid());
				String companyId =
					_findFieldValue(
						searchFields,
							OptionalJournalStructureCriteria.COMPANY_ID);
				String keywords =
					_findFieldValue(
						searchFields,
							OptionalJournalStructureCriteria.KEYWORDS);
				result =
					_searchCount(Long.parseLong(companyId), groupId, keywords);

			}
			else if (OptionalJournalStructureCriteria.FIND_BY_C_G_S_N_D
															.equals(finder)) {

				long groupId = Long.parseLong(category.getUuid());
				boolean andOperator = !searchCriteria.isMatchAnyOneField();
				String companyId =
					_findFieldValue(
						searchFields,
							OptionalJournalStructureCriteria.COMPANY_ID);
				String structureId =
					_findFieldValue(
						searchFields,
							OptionalJournalStructureCriteria.STRUCTURE_ID);
				String name =
					_findFieldValue(
						searchFields, OptionalJournalStructureCriteria.NAME);
				String description =
					_findFieldValue(
							searchFields,
								OptionalJournalStructureCriteria.DESCRIPTION);
				result =
					_searchCount(
						Long.parseLong(companyId), groupId, structureId,
							name, description, andOperator);
			}

		}
		catch (SystemException ex) {
			throw new CMSException(ex.getMessage(), ex);
		}

		return result;
	}

	public void createContentType(ContentType contentType)
		throws CMSException {

		JournalStructureContentType type =
			(JournalStructureContentType) contentType;

		JournalStructure structure = type.getStructure();
		JournalStructureContentType.CreationAttributes creationAttributes =
			type.getCreationAttributes();

		try {
			User user = UserUtil.findByPrimaryKey(structure.getUserId());

			String structureId = structure.getStructureId();
			structureId = structureId.trim().toUpperCase();
			Date now = new Date();
			String xsd = structure.getXsd();
			try {
				xsd = JournalUtil.formatXML(xsd);
			}
			catch (DocumentException de) {
				throw new StructureXsdException();
			}
			catch (IOException ioe) {
				throw new StructureXsdException();
			}

			long groupId = structure.getGroupId();
			boolean autoStructureId = creationAttributes.isAutoStructureId();

			String name = structure.getName();
			String description = structure.getDescription();
			String uuid = structure.getUuid();

			validate(
				groupId, structureId, autoStructureId, name, description, xsd);

			if (autoStructureId) {
				structureId = String.valueOf(
								CounterLocalServiceUtil.increment());
			}

			long id = CounterLocalServiceUtil.increment();

			JournalStructure newStructure =
				JournalStructureUtil.create(id);

			newStructure.setUuid(uuid);
			newStructure.setGroupId(groupId);
			newStructure.setCompanyId(user.getCompanyId());
			newStructure.setUserId(user.getUserId());
			newStructure.setUserName(user.getFullName());
			newStructure.setCreateDate(now);
			newStructure.setModifiedDate(now);
			newStructure.setStructureId(structureId);
			newStructure.setName(name);
			newStructure.setDescription(description);
			newStructure.setXsd(xsd);

			JournalStructureUtil.update(newStructure, false);

			// Resources

			Boolean addCommunityPermissions =
				creationAttributes.getAddCommunityPermissions();
			Boolean addGuestPermissions =
				creationAttributes.getAddGuestPermissions();

			if ((addCommunityPermissions != null) &&
								(addGuestPermissions != null)) {

				addStructureResources(
						newStructure, addCommunityPermissions.booleanValue(),
							addGuestPermissions.booleanValue());
			}
			else {
				addStructureResources(
						newStructure, type.getCommunityPermissions(),
							type.getGuestPermissions());
			}
		}
		catch (PortalException ex) {
			_log.error(ex.getMessage(), ex);
			throw new CMSException(ex.getMessage(), ex);
		}
		catch (SystemException ex) {
			_log.error(ex.getMessage(), ex);
			throw new CMSException(ex.getMessage(), ex);
		}

	}

	public void deleteContentType(ContentType contentType)
		throws CMSException {

		JournalStructureContentType type =
			(JournalStructureContentType) contentType;
		JournalStructure structure = type.getStructure();
		try {
			if (JournalArticleUtil.countByG_S(
					structure.getGroupId(), structure.getStructureId()) > 0) {

				throw new RequiredStructureException();
			}

			if (JournalTemplateUtil.countByG_S(
					structure.getGroupId(), structure.getStructureId()) > 0) {

				throw new RequiredStructureException();
			}

			// WebDAVProps

			WebDAVPropsLocalServiceUtil.deleteWebDAVProps(
				JournalStructure.class.getName(), structure.getPrimaryKey());

			// Resources

			ResourceLocalServiceUtil.deleteResource(
				structure.getCompanyId(), JournalStructure.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL, structure.getId());

			// Structure
			JournalStructureUtil.remove(structure.getPrimaryKey());
		}
		catch (PortalException ex) {
			_log.error(ex.getMessage(), ex);
			throw new CMSException(ex.getMessage(), ex);
		}
		catch (SystemException ex) {
			_log.error(ex.getMessage(), ex);
			throw new CMSException(ex.getMessage(), ex);
		}

	}

	public void deleteTemplateOfContentType(
			ContentType contentType, Template mirageTemplate)
		throws CMSException {

		JournalMirageTemplate journalMirageTemplate =
			(JournalMirageTemplate) mirageTemplate;
		JournalTemplate template = journalMirageTemplate.getTemplate();
		try {
			JournalTemplateUtil.remove(template.getPrimaryKey());
		}
		catch (PortalException ex) {
			_log.error(ex.getMessage(), ex);
			throw new CMSException(ex.getMessage(), ex);
		}
		catch (SystemException ex) {
			_log.error(ex.getMessage(), ex);
			throw new CMSException(ex.getMessage(), ex);
		}
		return;
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

	public ContentType getContentType(ContentType contentType)
		throws CMSException {

		return getContentType(contentType, null);
	}

	public ContentType getContentType(
			ContentType contentType, OptionalCriteria optionalCriteria)
		throws CMSException {

		JournalStructureContentType type =
			(JournalStructureContentType) contentType;
		JournalStructure structure = type.getStructure();

		try {
			OptionalJournalStructureCriteria criteria =
				(OptionalJournalStructureCriteria) optionalCriteria;
			String finder =
				criteria.getOptions().get(
					OptionalJournalStructureCriteria.FINDER);
			if (OptionalJournalStructureCriteria.FIND_BY_PRIMARY_KEY
					.equals(finder)) {
				structure = _getStructure(structure.getId());
			}
			else if (OptionalJournalStructureCriteria.FIND_BY_G_S
															.equals(finder)) {
				structure = _getStructure(
								structure.getGroupId(),
									structure.getStructureId());
			}
			type.setStructure(structure);
		}
		catch (PortalException ex) {
			_log.error(ex.getMessage(), ex);
			throw new CMSException(ex.getMessage(), ex);
		}
		catch (SystemException ex) {
			_log.error(ex.getMessage(), ex);
			throw new CMSException(ex.getMessage(), ex);
		}

		return type;
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
			Template mirageTemplate, OptionalCriteria optionalCriteria)
		throws TemplateNotFoundException {

		JournalMirageTemplate journalMirageTemplate =
			(JournalMirageTemplate) mirageTemplate;
		JournalTemplate template = journalMirageTemplate.getTemplate();
		OptionalJournalTemplateCriteria criteria =
			(OptionalJournalTemplateCriteria) optionalCriteria;
		String finder =
			criteria.getOptions().get(OptionalJournalTemplateCriteria.FINDER);
		if (finder == null) {
			return null;
		}
		try {
			if (OptionalJournalTemplateCriteria.FIND_BY_PRIMARY_KEY
					.equals(finder)) {
				journalMirageTemplate.setTemplate(
					_getJournalTemplate(template.getId()));
			}
			else if (OptionalJournalTemplateCriteria
							.FIND_BY_SMALL_IMAGE_ID.equals(finder)) {
				journalMirageTemplate.setTemplate(
					_getJournalTemplateForSmallImageId(
									template.getSmallImageId()));
			}
			else if (OptionalJournalTemplateCriteria
							.FIND_BY_GROUP_AND_TEMPLATE_ID.equals(finder)) {
				journalMirageTemplate.setTemplate(
					_getJournalTemplateForGroupAndTemplateId(
						template.getGroupId(), template.getTemplateId()));
			}
		}
		catch (PortalException ex) {
			_log.error(ex.getMessage(), ex);
			throw new TemplateNotFoundException(
				ex.getMessage(), ex.getMessage(), ex);
		}
		catch (SystemException ex) {
			_log.error(ex.getMessage(), ex);
			throw new TemplateNotFoundException(
				ex.getMessage(), ex.getMessage(), ex);
		}
		return journalMirageTemplate;
	}

	public List<Template> getTemplates(
			ContentType contentType, Template mirageTemplate,
			OptionalCriteria optionalCriteria)
		throws TemplateNotFoundException {

		List<Template> templates = new ArrayList<Template>();
		OptionalJournalTemplateCriteria criteria =
			(OptionalJournalTemplateCriteria) optionalCriteria;
		String finder =
			criteria.getOptions().get(OptionalJournalTemplateCriteria.FINDER);
		if (finder == null) {
			return null;
		}
		try {
			if (OptionalJournalTemplateCriteria
					.FIND_BY_GROUP_AND_STRUCTURE.equals(finder)) {
				JournalMirageTemplate journalMirageTemplate =
					(JournalMirageTemplate) mirageTemplate;
				JournalTemplate template = journalMirageTemplate.getTemplate();
				templates =
					_getJournalTemplates(
						template.getGroupId(), template.getStructureId());
			}
			else if (OptionalJournalTemplateCriteria
							.FIND_BY_GROUP_AND_STRUCTURE_WITH_LIMIT
								.equals(finder)) {
				JournalMirageTemplate journalMirageTemplate =
					(JournalMirageTemplate) mirageTemplate;
				JournalTemplate template = journalMirageTemplate.getTemplate();
				int start =
					Integer.parseInt(criteria.getOptions().get(
						OptionalJournalTemplateCriteria.RANGE_START));
				int end =
					Integer.parseInt(criteria.getOptions().get(
						OptionalJournalTemplateCriteria.RANGE_END));
				templates =
					_getJournalTemplates(
						template.getGroupId(), template.getStructureId(),
							start, end);
			}
			else if (OptionalJournalTemplateCriteria.FIND_ALL.equals(finder)){
				templates = _getAllJournalTemplates();
			}
			else if (OptionalJournalTemplateCriteria
							.FIND_BY_GROUP_ID.equals(finder)) {
				JournalMirageTemplate journalMirageTemplate =
					(JournalMirageTemplate) mirageTemplate;
				JournalTemplate template = journalMirageTemplate.getTemplate();
				templates = _getJournalTemplates(template.getGroupId());
			}
			else if (OptionalJournalTemplateCriteria
							.FIND_BY_GROUP_ID_WITH_LIMIT.equals(finder)) {
				JournalMirageTemplate journalMirageTemplate =
					(JournalMirageTemplate) mirageTemplate;
				JournalTemplate template = journalMirageTemplate.getTemplate();
				int start =
					Integer.parseInt(criteria.getOptions().get(
						OptionalJournalTemplateCriteria.RANGE_START));
				int end =
					Integer.parseInt(criteria.getOptions().get(
						OptionalJournalTemplateCriteria.RANGE_END));
				templates =
					_getJournalTemplates(template.getGroupId(), start, end);
			}
			else if (OptionalJournalTemplateCriteria
							.FIND_BY_TEMPLATE_ID.equals(finder)) {
				JournalMirageTemplate journalMirageTemplate =
					(JournalMirageTemplate) mirageTemplate;
				JournalTemplate template = journalMirageTemplate.getTemplate();
				templates = _getJournalTemplates(template.getTemplateId());
			}
		}
		catch (SystemException ex) {
			_log.error(ex.getMessage(), ex);
			throw new TemplateNotFoundException(
				ex.getMessage(), ex.getMessage(), ex);
		}
		return templates;
	}

	public int getTemplatesCount(
			ContentType contentType, Template mirageTemplate,
			OptionalCriteria optionalCriteria)
		throws CMSException {

		int count = -1;
		OptionalJournalTemplateCriteria criteria =
			(OptionalJournalTemplateCriteria) optionalCriteria;
		String finder = criteria.getOptions().get(
							OptionalJournalTemplateCriteria.FINDER);
		if (finder == null) {
			return count;
		}
		try {
			if (OptionalJournalTemplateCriteria
					.COUNT_BY_GROUP_AND_STRUCTURE.equals(finder)) {
				JournalMirageTemplate journalMirageTemplate =
					(JournalMirageTemplate) mirageTemplate;
				JournalTemplate template = journalMirageTemplate.getTemplate();
				count =
					_getJournalTemplatesCount(
						template.getGroupId(), template.getStructureId());

			}
			else if (OptionalJournalTemplateCriteria
							.COUNT_BY_GROUP_ID.equals(finder)) {
				JournalMirageTemplate journalMirageTemplate =
					(JournalMirageTemplate) mirageTemplate;
				JournalTemplate template = journalMirageTemplate.getTemplate();
				count = _getJournalTemplatesCount(template.getGroupId());

			}
			else if (OptionalJournalTemplateCriteria
							.ARTICLE_COUNT_BY_GROUP_AND_TEMPLATE
													.equals(finder)) {
				JournalMirageTemplate journalMirageTemplate =
					(JournalMirageTemplate) mirageTemplate;
				JournalTemplate template = journalMirageTemplate.getTemplate();
				count =
					_getArticleCount(
						template.getGroupId(), template.getTemplateId());
			}
		}
		catch (SystemException ex) {
			_log.error(ex.getMessage(), ex);
			throw new CMSException(ex.getMessage(), ex.getMessage(), ex);
		}
		return count;
	}

	public Template getTemplateWithUUID(String templateUUID)
		throws TemplateNotFoundException {

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

	public List<ContentType> searchContentTypes(SearchCriteria searchCriteria)
		throws CMSException {

		List<ContentType> contentTypes = new ArrayList<ContentType>();

		try {
			List<JournalStructure> structures = null;
			String finder =
				_findFieldValue(
					searchCriteria.getSearchFieldValues(),
						OptionalJournalStructureCriteria.FINDER);

			if (OptionalJournalStructureCriteria.FIND_ALL.equals(finder)) {
				structures = _getStructures();
			}
			_populateContentTypes(contentTypes, structures);
		}
		catch (SystemException ex) {
			throw new CMSException(ex.getMessage(), ex);
		}

		return contentTypes;
	}

	public List<ContentType> searchContentTypesByCategory(
			Category category, SearchCriteria searchCriteria)
		throws CMSException {

		List<ContentType> contentTypes = new ArrayList<ContentType>();
		List<SearchFieldValue> searchFields =
			searchCriteria.getSearchFieldValues();

		try {
			List<JournalStructure> structures = null;
			String finder =
				_findFieldValue(
					searchFields, OptionalJournalStructureCriteria.FINDER);

			if (OptionalJournalStructureCriteria.FIND_BY_GROUP.equals(finder)) {

				long groupId = Long.parseLong(category.getUuid());
				structures = _getStructures(groupId);

			}
			else if (OptionalJournalStructureCriteria
							.FIND_BY_GROUP_LIMIT.equals(finder)) {

				long groupId = Long.parseLong(category.getUuid());
				String start =
					_findFieldValue(
						searchFields,
							OptionalJournalStructureCriteria.RANGE_START);
				String end =
					_findFieldValue(
						searchFields,
							OptionalJournalStructureCriteria.RANGE_END);
				structures =
					_getStructures(
						groupId, Integer.parseInt(start),
							Integer.parseInt(end));

			}
			else if (OptionalJournalStructureCriteria
							.FIND_BY_KEYWORDS.equals(finder)) {

				long groupId = Long.parseLong(category.getUuid());
				OrderByComparator obc =
					(OrderByComparator) searchCriteria.getOrderByComparator();
				String companyId =
					_findFieldValue(
						searchFields,
							OptionalJournalStructureCriteria.COMPANY_ID);
				String keywords =
					_findFieldValue(
						searchFields,
							OptionalJournalStructureCriteria.KEYWORDS);
				String start =
					_findFieldValue(
						searchFields,
							OptionalJournalStructureCriteria.RANGE_START);
				String end =
					_findFieldValue(
						searchFields,
							OptionalJournalStructureCriteria.RANGE_END);
				structures =
					_search(
						Long.parseLong(companyId), groupId, keywords,
							Integer.parseInt(start), Integer.parseInt(end),
								obc);

			}
			else if (OptionalJournalStructureCriteria
							.FIND_BY_C_G_S_N_D.equals(finder)) {

				long groupId = Long.parseLong(category.getUuid());
				OrderByComparator obc =
					(OrderByComparator) searchCriteria.getOrderByComparator();
				boolean andOperator = !searchCriteria.isMatchAnyOneField();
				String companyId =
					_findFieldValue(
						searchFields,
							OptionalJournalStructureCriteria.COMPANY_ID);
				String structureId =
					_findFieldValue(
						searchFields,
							OptionalJournalStructureCriteria.STRUCTURE_ID);
				String name =
					_findFieldValue(
						searchFields, OptionalJournalStructureCriteria.NAME);
				String description =
					_findFieldValue(
						searchFields,
							OptionalJournalStructureCriteria.DESCRIPTION);
				String start =
					_findFieldValue(
						searchFields,
							OptionalJournalStructureCriteria.RANGE_START);
				String end =
					_findFieldValue(
						searchFields,
							OptionalJournalStructureCriteria.RANGE_END);
				structures =
					_search(
						Long.parseLong(companyId), groupId, structureId, name,
							description, andOperator, Integer.parseInt(start),
								Integer.parseInt(end), obc);
			}

			_populateContentTypes(contentTypes, structures);

		}
		catch (SystemException ex) {
			throw new CMSException(ex.getMessage(), ex);
		}

		return contentTypes;
	}

	public List<Template> searchTemplates(SearchCriteria searchCriteria)
		throws CMSException {

		try {
			List<JournalTemplate> journalTemplates = null;
			String finder =
				_findFieldValue(
					searchCriteria.getSearchFieldValues(),
						OptionalJournalTemplateCriteria.FINDER);
			long companyId =
				Long.parseLong(_findFieldValue(
					searchCriteria.getSearchFieldValues(),
						OptionalJournalTemplateCriteria.COMPANY_ID));
			long groupId =
				Long.parseLong(_findFieldValue(
					searchCriteria.getSearchFieldValues(),
						OptionalJournalTemplateCriteria.GROUP_ID));
			String structureId =
				_findFieldValue(
					searchCriteria.getSearchFieldValues(),
						OptionalJournalTemplateCriteria.STRUCTURE_ID);
			String structureIdComparator =
				_findFieldValue(
					searchCriteria.getSearchFieldValues(),
						OptionalJournalTemplateCriteria
							.STRUCTURE_ID_COMPARATOR);
			int start =
				Integer.parseInt(_findFieldValue(
					searchCriteria.getSearchFieldValues(),
						OptionalJournalTemplateCriteria.RANGE_START));
			int end =
				Integer.parseInt(_findFieldValue(
					searchCriteria.getSearchFieldValues(),
						OptionalJournalTemplateCriteria.RANGE_END));
			OrderByComparator obc =
				(OrderByComparator) searchCriteria.getOrderByComparator();
			if (OptionalJournalTemplateCriteria
					.FIND_BY_KEYWORDS.equals(finder)) {

				String keywords =
					_findFieldValue(
						searchCriteria.getSearchFieldValues(),
							OptionalJournalTemplateCriteria.KEYWORDS);

				journalTemplates =
					_searchJournalTemplates(
						companyId, groupId, keywords, structureId,
							structureIdComparator, start, end, obc);

			}
			else if (OptionalJournalTemplateCriteria.SEARCH.equals(finder)) {
				String templateId =
					_findFieldValue(
						searchCriteria.getSearchFieldValues(),
							OptionalJournalTemplateCriteria.TEMPLATE_ID);
				String name =
					_findFieldValue(
						searchCriteria.getSearchFieldValues(),
							OptionalJournalTemplateCriteria.NAME);
				String description =
					_findFieldValue(
						searchCriteria.getSearchFieldValues(),
							OptionalJournalTemplateCriteria.DESCRIPTION);
				boolean andOperator = searchCriteria.isMatchAnyOneField();

				journalTemplates =
					_searchJournalTemplates(
						companyId, groupId, templateId, structureId,
							structureIdComparator, name, description,
								andOperator, start, end, obc);
			}
			return _getTemplatesFromJournalTemplates(journalTemplates);
		}
		catch (SystemException ex) {
			_log.error(ex.getMessage(), ex);
			throw new TemplateNotFoundException(
				ex.getMessage(), ex.getMessage(), ex);
		}
	}

	public int searchTemplatesCount(SearchCriteria searchCriteria)
		throws CMSException {

		try {
			int count = -1;
			String finder =
				_findFieldValue(
					searchCriteria.getSearchFieldValues(),
						OptionalJournalTemplateCriteria.FINDER);
			long companyId =
				Long.parseLong(_findFieldValue(
					searchCriteria.getSearchFieldValues(),
						OptionalJournalTemplateCriteria.COMPANY_ID));
			long groupId =
				Long.parseLong(_findFieldValue(
					searchCriteria.getSearchFieldValues(),
						OptionalJournalTemplateCriteria.GROUP_ID));
			String structureId =
				_findFieldValue(
					searchCriteria.getSearchFieldValues(),
						OptionalJournalTemplateCriteria.STRUCTURE_ID);
			String structureIdComparator =
				_findFieldValue(
					searchCriteria.getSearchFieldValues(),
						OptionalJournalTemplateCriteria
							.STRUCTURE_ID_COMPARATOR);
			if (OptionalJournalTemplateCriteria
							.COUNT_BY_KEYWORDS.equals(finder)) {

				String keywords =
					_findFieldValue(
						searchCriteria.getSearchFieldValues(),
							OptionalJournalTemplateCriteria.KEYWORDS);

				count =
					_searchJournalTemplatesCount(
						companyId, groupId, keywords, structureId,
							structureIdComparator);

			}
			else if (OptionalJournalTemplateCriteria
							.SEARCH_COUNT.equals(finder)) {
				String templateId =
					_findFieldValue(
						searchCriteria.getSearchFieldValues(),
							OptionalJournalTemplateCriteria.TEMPLATE_ID);
				String name =
					_findFieldValue(
						searchCriteria.getSearchFieldValues(),
							OptionalJournalTemplateCriteria.NAME);
				String description =
					_findFieldValue(
						searchCriteria.getSearchFieldValues(),
							OptionalJournalTemplateCriteria.DESCRIPTION);
				boolean andOperator = searchCriteria.isMatchAnyOneField();

				count =
					_searchJournalTemplatesCount(
						companyId, groupId, templateId, structureId,
							structureIdComparator, name, description,
								andOperator);
			}
			return count;
		}
		catch (SystemException ex) {
			_log.error(ex.getMessage(), ex);
			throw new TemplateNotFoundException(
				ex.getMessage(), ex.getMessage(), ex);
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

	public void updateContentType(ContentType contentType)
		throws CMSException {

		JournalStructureContentType type =
			(JournalStructureContentType) contentType;
		JournalStructure structure = type.getStructure();
		try {
			String structureId =
				structure.getStructureId().trim().toUpperCase();
			String xsd = structure.getXsd();
			try {
				xsd = JournalUtil.formatXML(xsd);
			}
			catch (DocumentException de) {
				throw new StructureXsdException();
			}
			catch (IOException ioe) {
				throw new StructureXsdException();
			}

			String name = structure.getName();
			String description = structure.getDescription();

			validate(name, description, xsd);

			long groupId = structure.getGroupId();
			JournalStructure persistedStructure =
				JournalStructureUtil.findByG_S(groupId, structureId);

			persistedStructure.setModifiedDate(new Date());
			persistedStructure.setName(name);
			persistedStructure.setDescription(description);
			persistedStructure.setXsd(xsd);

			JournalStructureUtil.update(persistedStructure, false);
		}
		catch (PortalException ex) {
			_log.error(ex.getMessage(), ex);
			throw new CMSException(ex.getMessage(), ex);
		}
		catch (SystemException ex) {
			_log.error(ex.getMessage(), ex);
			throw new CMSException(ex.getMessage(), ex);
		}

	}

	public void updateContentType(
			ContentType contentType, UpdateCriteria updateCriteria)
		throws CMSException {

		try {
			JournalStructureContentType type =
				(JournalStructureContentType) contentType;
			JournalStructure structure = type.getStructure();
			JournalStructureUtil.update(structure, false);
		}
		catch (SystemException se) {
			_log.error(se.getMessage(), se);
			throw new CMSException(se.getMessage(), se);
		}
	}

	public void updateTemplateOfContentType(
			Template mirageTemplate, ContentType contentType)
		throws CMSException {

		JournalMirageTemplate journalMirageTemplate =
			(JournalMirageTemplate) mirageTemplate;
		JournalTemplate template = journalMirageTemplate.getTemplate();
		JournalMirageTemplate.UpdateAttributes updateAttributes =
			journalMirageTemplate.getUpdateAttributes();

		boolean formatXsl = updateAttributes.isFormatXsl();
		File smallFile = updateAttributes.getSmallFile();

		String templateId = template.getTemplateId();
		String langType = template.getLangType();
		String xsl = template.getXsl();
		String name = template.getName();
		String description = template.getDescription();
		boolean smallImage = template.getSmallImage();
		String smallImageURL = template.getSmallImageURL();
		long groupId = template.getGroupId();
		String structureId = template.getStructureId();
		boolean cacheable = template.isCacheable();

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

			JournalTemplate existingTemplate =
				JournalTemplateUtil.findByG_T(groupId, templateId);

			existingTemplate.setModifiedDate(new Date());

			if (Validator.isNull(existingTemplate.getStructureId()) &&
					Validator.isNotNull(structureId)) {

				// Allow users to set the structure if and only if it currently
				// does not have one. Otherwise, you can have bad data because
				// there may be an existing article that has chosen to use a
				// structure and template combination that no longer exists.

				existingTemplate.setStructureId(structureId);
			}

			existingTemplate.setName(name);
			existingTemplate.setDescription(description);
			existingTemplate.setXsl(xsl);
			existingTemplate.setLangType(langType);
			existingTemplate.setCacheable(cacheable);
			existingTemplate.setSmallImage(smallImage);
			existingTemplate.setSmallImageURL(smallImageURL);

			JournalTemplateUtil.update(existingTemplate, false);

			// Small image

			saveImages(
				smallImage, existingTemplate.getSmallImageId(), smallFile,
				smallBytes);

			journalMirageTemplate.setTemplate(existingTemplate);
		}
		catch (PortalException pe) {
			_log.error(pe.getMessage(), pe);
			throw new CMSException(pe.getMessage(), pe);
		}
		catch (SystemException se) {
			_log.error(se.getMessage(), se);
			throw new CMSException(se.getMessage(), se);
		}
	}

	public void updateTemplateOfContentType(
			Template mirageTemplate, ContentType contentType,
			UpdateCriteria criteria)
		throws CMSException {

		try {
			JournalMirageTemplate journalMirageTemplate =
				(JournalMirageTemplate) mirageTemplate;
			JournalTemplate template = journalMirageTemplate.getTemplate();
			JournalTemplateUtil.update(template, false);
		}
		catch (SystemException se) {
			_log.error(se.getMessage(), se);
			throw new CMSException(se.getMessage(), se);
		}
	}

	public boolean validateTemplate(
		Template template, ContentType contentType) {

		throw new UnsupportedOperationException();
	}

	protected void addStructureResources(
			JournalStructure structure, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			structure.getCompanyId(), structure.getGroupId(),
				structure.getUserId(), JournalStructure.class.getName(),
					structure.getId(), false, addCommunityPermissions,
						addGuestPermissions);
	}

	protected void saveImages(
			boolean smallImage, long smallImageId,
			File smallFile, byte[] smallBytes)
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

	@SuppressWarnings("unchecked")
	protected void validate(List<Element> children, Set<String> elNames)
		throws PortalException {

		for (Element el : children) {
			String elName = el.attributeValue("name", StringPool.BLANK);
			String elType = el.attributeValue("type", StringPool.BLANK);

			if (Validator.isNull(elName) ||
					elName.startsWith(JournalStructureImpl.RESERVED)) {

				throw new StructureXsdException();
			}
			else {
				char[] c = elName.toCharArray();

				for (int i = 0; i < c.length; i++) {
					if ((!Validator.isChar(c[i])) &&
							(!Validator.isDigit(c[i])) &&
								(c[i] != CharPool.DASH) &&
									(c[i] != CharPool.UNDERLINE)) {

						throw new StructureXsdException();
					}
				}

				String completePath = elName;

				Element parent = el.getParent();

				while (!parent.isRootElement()) {
					completePath =
						parent.attributeValue(
							"name", StringPool.BLANK) +
								StringPool.SLASH + completePath;

					parent = parent.getParent();
				}

				String elNameLowerCase = completePath.toLowerCase();

				if (elNames.contains(elNameLowerCase)) {
					throw new StructureXsdException();
				}
				else {
					elNames.add(elNameLowerCase);
				}
			}

			if (Validator.isNull(elType)) {
				throw new StructureXsdException();
			}

			validate(el.elements(), elNames);
		}
	}

	protected void validate(
			long groupId, String structureId, boolean autoStructureId,
			String name, String description, String xsd)
		throws PortalException, SystemException {

		if (!autoStructureId) {
			if ((Validator.isNull(structureId)) ||
					(Validator.isNumber(structureId)) ||
						(structureId.indexOf(StringPool.SPACE) != -1)) {

				throw new StructureIdException();
			}

			try {
				JournalStructureUtil.findByG_S(groupId, structureId);

				throw new DuplicateStructureIdException();
			}
			catch (NoSuchStructureException nste) {
			}
		}

		validate(name, description, xsd);
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

	@SuppressWarnings("unchecked")
	protected void validate(String name, String description, String xsd)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new StructureNameException();
		}
		else if (Validator.isNull(description)) {
			throw new StructureDescriptionException();
		}

		if (Validator.isNull(xsd)) {
			throw new StructureXsdException();
		}
		else {
			try {
				SAXReader reader = new SAXReader();

				Document doc = reader.read(new StringReader(xsd));

				Element root = doc.getRootElement();

				List<Element> children = root.elements();

				if (children.size() == 0) {
					throw new StructureXsdException();
				}

				Set<String> elNames = new HashSet<String>();

				validate(children, elNames);
			}
			catch (Exception e) {
				throw new StructureXsdException();
			}
		}
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

	private String _findFieldValue(
		List<SearchFieldValue> fieldValues, String fieldName) {

		String result = null;
		for (SearchFieldValue searchField : fieldValues) {
			if (searchField.getFieldName().equals(fieldName)) {
				result = searchField.getFieldValue();
				break;
			}
		}
		return result;
	}

	private List<Template> _getAllJournalTemplates()
		throws SystemException {

		List<JournalTemplate> journalTemplates =
			JournalTemplateUtil.findAll();
		return _getTemplatesFromJournalTemplates(journalTemplates);
	}

	private int _getArticleCount(long groupId, String templateId)
		throws SystemException {

		return JournalArticleUtil.countByG_T(groupId, templateId);
	}

	private JournalTemplate _getJournalTemplate(long id)
		throws PortalException, SystemException {

		return JournalTemplateUtil.findByPrimaryKey(id);
	}

	private JournalTemplate _getJournalTemplateForGroupAndTemplateId(
			long groupId, String templateId)
		throws PortalException, SystemException {

		return JournalTemplateUtil.findByG_T(groupId, templateId);
	}

	private JournalTemplate _getJournalTemplateForSmallImageId(
			long smallImageId)
		throws PortalException, SystemException {

		return JournalTemplateUtil.findBySmallImageId(smallImageId);
	}

	private List<Template> _getJournalTemplates(long groupId)
		throws SystemException {

		List<JournalTemplate> journalTemplates =
			JournalTemplateUtil.findByGroupId(groupId);
		return _getTemplatesFromJournalTemplates(journalTemplates);
	}

	private List<Template> _getJournalTemplates(
			long groupId, int start, int end)
		throws SystemException {

		List<JournalTemplate> journalTemplates =
			JournalTemplateUtil.findByGroupId(groupId, start, end);
		return _getTemplatesFromJournalTemplates(journalTemplates);
	}

	private List<Template> _getJournalTemplates(
			long groupId, String structureId)
		throws SystemException {

		List<JournalTemplate> journalTemplates =
			JournalTemplateUtil.findByG_S(groupId, structureId);
		return _getTemplatesFromJournalTemplates(journalTemplates);
	}

	private List<Template> _getJournalTemplates(
			long groupId, String structureId, int start, int end)
		throws SystemException {

		List<JournalTemplate> journalTemplates =
			JournalTemplateUtil.findByG_S(
				groupId, structureId, start, end);
		return _getTemplatesFromJournalTemplates(journalTemplates);
	}

	private List<Template> _getJournalTemplates(String templateId)
		throws SystemException {

		List<JournalTemplate> journalTemplates =
			JournalTemplateUtil.findByTemplateId(templateId);
		return _getTemplatesFromJournalTemplates(journalTemplates);
	}

	private int _getJournalTemplatesCount(long groupId)
		throws SystemException {

		return JournalTemplateUtil.countByGroupId(groupId);
	}

	private int _getJournalTemplatesCount(long groupId, String structureId)
		throws SystemException {

		return JournalTemplateUtil.countByG_S(groupId, structureId);
	}

	private JournalStructure _getStructure(long id)
		throws PortalException, SystemException {

		return JournalStructureUtil.findByPrimaryKey(id);
	}

	private JournalStructure _getStructure(long groupId, String structureId)
		throws PortalException, SystemException {

		structureId = structureId.trim().toUpperCase();

		if (groupId == 0) {
			_log.error("No group id was passed for " + structureId +
				". Group id is " +
					"required since 4.2.0. Please update all custom code and " +
						"data that references structures without a group id.");

			List<JournalStructure> structures =
				JournalStructureUtil.findByStructureId(structureId);

			if (structures.size() == 0) {
				throw new NoSuchStructureException(
					"No JournalStructure exists with the structure id " +
						structureId);
			}
			else {
				return structures.get(0);
			}
		}
		else {
			return JournalStructureUtil.findByG_S(groupId, structureId);
		}
	}

	private List<JournalStructure> _getStructures()
		throws SystemException {

		return JournalStructureUtil.findAll();
	}

	private List<JournalStructure> _getStructures(long groupId)
		throws SystemException {

		return JournalStructureUtil.findByGroupId(groupId);
	}

	private List<JournalStructure> _getStructures(
		long groupId, int start, int end)
		throws SystemException {

		return JournalStructureUtil.findByGroupId(groupId, start, end);
	}

	private int _getStructuresCount(long groupId)
		throws SystemException {

		return JournalStructureUtil.countByGroupId(groupId);
	}

	private List<Template> _getTemplatesFromJournalTemplates(
		List<JournalTemplate> journalTemplates) {

		if (journalTemplates == null) {
			return null;
		}
		List<Template> templates =
			new ArrayList<Template>(journalTemplates.size());
		for (JournalTemplate template : journalTemplates) {
			templates.add(new JournalMirageTemplate(template));
		}
		return templates;
	}

	private void _populateContentTypes(
		List<ContentType> contentTypes, List<JournalStructure> structures) {

		if (structures == null) {
			return;
		}

		for (JournalStructure structure : structures) {
			contentTypes.add(new JournalStructureContentType(structure));
		}
	}

	private List<JournalStructure> _search(
			long companyId, long groupId, String keywords, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return JournalStructureFinderUtil.findByKeywords(
			companyId, groupId, keywords, start, end, obc);
	}

	private List<JournalStructure> _search(
			long companyId, long groupId, String structureId, String name,
			String description, boolean andOperator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return JournalStructureFinderUtil.findByC_G_S_N_D(
			companyId, groupId, structureId, name, description, andOperator,
				start, end, obc);
	}

	private int _searchCount(long companyId, long groupId, String keywords)
		throws SystemException {

		return JournalStructureFinderUtil.countByKeywords(
			companyId, groupId, keywords);
	}

	private int _searchCount(
			long companyId, long groupId, String structureId, String name,
			String description, boolean andOperator)
		throws SystemException {

		return JournalStructureFinderUtil.countByC_G_S_N_D(
			companyId, groupId, structureId, name, description, andOperator);
	}

	private List<JournalTemplate> _searchJournalTemplates(
			long companyId, long groupId, String keywords, String structureId,
			String structureIdComparator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return JournalTemplateFinderUtil.findByKeywords(
			companyId, groupId, keywords, structureId, structureIdComparator,
				start, end, obc);
	}

	private List<JournalTemplate> _searchJournalTemplates(
			long companyId, long groupId, String templateId, String structureId,
			String structureIdComparator, String name, String description,
			boolean andOperator, int start, int end, OrderByComparator obc)
		throws SystemException {

		return JournalTemplateFinderUtil.findByC_G_T_S_N_D(
			companyId, groupId, templateId, structureId, structureIdComparator,
				name, description, andOperator, start, end, obc);
	}

	private int _searchJournalTemplatesCount(
			long companyId, long groupId, String keywords, String structureId,
			String structureIdComparator)
		throws SystemException {

		return JournalTemplateFinderUtil.countByKeywords(
			companyId, groupId, keywords, structureId, structureIdComparator);
	}

	private int _searchJournalTemplatesCount(
			long companyId, long groupId, String templateId, String structureId,
			String structureIdComparator, String name, String description,
			boolean andOperator)
		throws SystemException {

		return JournalTemplateFinderUtil.countByC_G_T_S_N_D(
			companyId, groupId, templateId, structureId, structureIdComparator,
				name, description, andOperator);
	}

	private static final Log _log =
		LogFactory.getLog(ContentTypeServiceImpl.class);

}