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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.mirage.custom.MirageServiceFactory;
import com.liferay.portal.mirage.model.JournalStructureContentType;
import com.liferay.portal.mirage.model.OptionalJournalStructureCriteria;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.impl.JournalStructureImpl;
import com.liferay.portlet.journal.service.base.JournalStructureLocalServiceBaseImpl;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.model.custom.Category;
import com.sun.portal.cms.mirage.model.custom.ContentType;
import com.sun.portal.cms.mirage.model.search.SearchCriteria;
import com.sun.portal.cms.mirage.model.search.SearchFieldValue;
import com.sun.portal.cms.mirage.service.custom.ContentTypeService;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="JournalStructureLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Prakash Reddy
 *
 */
public class JournalStructureLocalServiceImpl
	extends JournalStructureLocalServiceBaseImpl {

	public JournalStructure addStructure(
			long userId, String structureId, boolean autoStructureId, long plid,
			String name, String description, String xsd,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addStructure(
			null, userId, structureId, autoStructureId, plid, name,
				description, xsd, Boolean.valueOf(addCommunityPermissions),
					Boolean.valueOf(addGuestPermissions), null, null);
	}

	public JournalStructure addStructure(
			String uuid, long userId, String structureId,
			boolean autoStructureId, long plid, String name,
			String description, String xsd,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addStructure(
			uuid, userId, structureId, autoStructureId, plid, name,
				description, xsd, Boolean.valueOf(addCommunityPermissions),
					Boolean.valueOf(addGuestPermissions), null, null);
	}

	public JournalStructure addStructure(
			long userId, String structureId, boolean autoStructureId, long plid,
			String name, String description, String xsd,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addStructure(
			null, userId, structureId, autoStructureId, plid, name,
				description, xsd, null, null, communityPermissions,
					guestPermissions);
	}

	public JournalStructure addStructure(
			String uuid, long userId, String structureId,
			boolean autoStructureId, long plid, String name,
			String description, String xsd,	Boolean addCommunityPermissions,
			Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		long groupId = PortalUtil.getPortletGroupId(plid);

		return addStructureToGroup(
			uuid, userId, structureId, autoStructureId, groupId, name,
				description, xsd, addCommunityPermissions, addGuestPermissions,
					communityPermissions, guestPermissions);
	}

	public JournalStructure addStructureToGroup(
			String uuid, long userId, String structureId,
			boolean autoStructureId, long groupId, String name,
			String description, String xsd,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		JournalStructure addedStructure = null;
		try {

			// Populate the structure object with the method arguments

			JournalStructure structure = new JournalStructureImpl();
			structure.setUuid(uuid);
			structure.setUserId(userId);
			structure.setStructureId(structureId);
			structure.setGroupId(groupId);
			structure.setName(name);
			structure.setDescription(description);
			structure.setXsd(xsd);

			JournalStructureContentType type =
				new JournalStructureContentType(
					structure, communityPermissions, guestPermissions);

			JournalStructureContentType.CreationAttributes creationAttributes =
				type.new CreationAttributes();
			creationAttributes.setAutoStructureId(autoStructureId);
			creationAttributes.setAddCommunityPermissions(
				addCommunityPermissions);
			creationAttributes.setAddGuestPermissions(addGuestPermissions);
			type.setCreationAttributes(creationAttributes);

			// Get the Project Mirage service

			ContentTypeService ctService =
				MirageServiceFactory.getContentTypeService();
			ctService.createContentType(type);

			addedStructure = type.getStructure();
		}
		catch (CMSException ex) {
			_throwException(ex);
		}
		return addedStructure;
	}

	public void addStructureResources(
			long groupId, String structureId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		JournalStructure structure = getStructure(groupId, structureId);

		addStructureResources(
			structure, addCommunityPermissions, addGuestPermissions);
	}

	public void addStructureResources(
			JournalStructure structure, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			structure.getCompanyId(), structure.getGroupId(),
				structure.getUserId(), JournalStructure.class.getName(),
					structure.getId(), false, addCommunityPermissions,
						addGuestPermissions);
	}

	public void addStructureResources(
			long groupId, String structureId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		JournalStructure structure = getStructure(groupId, structureId);

		addStructureResources(
			structure, communityPermissions, guestPermissions);
	}

	public void addStructureResources(
			JournalStructure structure, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			structure.getCompanyId(), structure.getGroupId(),
				structure.getUserId(), JournalStructure.class.getName(),
					structure.getId(), communityPermissions, guestPermissions);
	}

	public void checkNewLine(long groupId, String structureId)
		throws PortalException, SystemException {

		JournalStructure structure = getStructure(groupId, structureId);

		String xsd = structure.getXsd();

		if ((xsd != null) && (xsd.indexOf("\\n") != -1)) {

			xsd = StringUtil.replace(xsd, new String[] {
				"\\n", "\\r"
			}, new String[] {
				"\n", "\r"
			});

			structure.setXsd(xsd);

			JournalStructureContentType contentType =
				new JournalStructureContentType(structure);
			ContentTypeService ctService =
				MirageServiceFactory.getContentTypeService();
			try {
				ctService.updateContentType(contentType, null);
			}
			catch (CMSException cmse) {
				_throwException(cmse);
			}
		}
	}

	public void deleteStructure(long groupId, String structureId)
		throws PortalException, SystemException {

		JournalStructure structure = new JournalStructureImpl();
		structure.setStructureId(structureId.trim().toUpperCase());
		structure.setGroupId(groupId);
		JournalStructureContentType type =
			new JournalStructureContentType(structure);
		ContentTypeService ctService =
			MirageServiceFactory.getContentTypeService();
		OptionalJournalStructureCriteria criteria =
			new OptionalJournalStructureCriteria(
				OptionalJournalStructureCriteria.FIND_BY_G_S);
		try {
			type =
				(JournalStructureContentType) ctService.getContentType(
					type, criteria);
		}
		catch (CMSException ex) {
			_throwException(ex);
		}

		deleteStructure(type.getStructure());
	}

	public void deleteStructure(JournalStructure structure)
		throws PortalException, SystemException {

		try {

			JournalStructureContentType type =
				new JournalStructureContentType(structure);
			ContentTypeService ctService =
				MirageServiceFactory.getContentTypeService();
			ctService.deleteContentType(type);
		}
		catch (CMSException ex) {
			_throwException(ex);
		}
	}

	public void deleteStructures(long groupId)
		throws PortalException, SystemException {

		for (JournalStructure structure : getStructures(groupId)) {

			deleteStructure(structure);
		}
	}

	public JournalStructure getStructure(long id)
		throws PortalException, SystemException {

		JournalStructure structure = new JournalStructureImpl();
		structure.setId(id);
		JournalStructureContentType type =
			new JournalStructureContentType(structure);
		OptionalJournalStructureCriteria criteria =
			new OptionalJournalStructureCriteria(
				OptionalJournalStructureCriteria.FIND_BY_PRIMARY_KEY);
		ContentTypeService ctService =
			MirageServiceFactory.getContentTypeService();
		try {
			type =
				(JournalStructureContentType) ctService.getContentType(
					type, criteria);
		}
		catch (CMSException ex) {
			_throwException(ex);
		}
		return type.getStructure();
	}

	public JournalStructure getStructure(long groupId, String structureId)
		throws PortalException, SystemException {

		JournalStructure structure = new JournalStructureImpl();
		structure.setGroupId(groupId);
		structure.setStructureId(structureId);
		JournalStructureContentType type =
			new JournalStructureContentType(structure);
		OptionalJournalStructureCriteria criteria =
			new OptionalJournalStructureCriteria(
				OptionalJournalStructureCriteria.FIND_BY_G_S);
		ContentTypeService ctService =
			MirageServiceFactory.getContentTypeService();
		try {
			type =
				(JournalStructureContentType) ctService.getContentType(
					type, criteria);
		}
		catch (CMSException ex) {
			_throwException(ex);
		}
		return type.getStructure();
	}

	public List<JournalStructure> getStructures()
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.FINDER,
				OptionalJournalStructureCriteria.FIND_ALL);

		criteria.setSearchFieldValues(searchFields);

		ContentTypeService ctService =
			MirageServiceFactory.getContentTypeService();
		try {
			List<ContentType> contentTypes =
				ctService.searchContentTypes(criteria);
			return _getStructuresFromContentTypes(contentTypes);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	public List<JournalStructure> getStructures(long groupId)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		String categoryId = String.valueOf(groupId);
		Category category = new Category(categoryId, categoryId);

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.FINDER,
				OptionalJournalStructureCriteria.FIND_BY_GROUP);

		criteria.setSearchFieldValues(searchFields);

		ContentTypeService ctService =
			MirageServiceFactory.getContentTypeService();
		try {
			List<ContentType> contentTypes =
				ctService.searchContentTypesByCategory(category, criteria);
			return _getStructuresFromContentTypes(contentTypes);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	public List<JournalStructure> getStructures(
			long groupId, int start, int end)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		String categoryId = String.valueOf(groupId);
		Category category = new Category(categoryId, categoryId);

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.FINDER,
				OptionalJournalStructureCriteria.FIND_BY_GROUP_LIMIT);

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.RANGE_START,
				String.valueOf(start));

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.RANGE_END,
				String.valueOf(end));

		criteria.setSearchFieldValues(searchFields);

		ContentTypeService ctService =
			MirageServiceFactory.getContentTypeService();
		try {
			List<ContentType> contentTypes =
				ctService.searchContentTypesByCategory(category, criteria);
			return _getStructuresFromContentTypes(contentTypes);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	public int getStructuresCount(long groupId)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		String categoryId = String.valueOf(groupId);
		Category category = new Category(categoryId, categoryId);

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.FINDER,
				OptionalJournalStructureCriteria.FIND_BY_GROUP);

		criteria.setSearchFieldValues(searchFields);

		ContentTypeService ctService =
			MirageServiceFactory.getContentTypeService();
		try {
			return ctService.contentTypeSearchCount(category, criteria);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	public List<JournalStructure> search(
			long companyId, long groupId, String keywords, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		String categoryId = String.valueOf(groupId);
		Category category = new Category(categoryId, categoryId);

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.FINDER,
				OptionalJournalStructureCriteria.FIND_BY_KEYWORDS);

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.COMPANY_ID,
				String.valueOf(companyId));

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.KEYWORDS, keywords);

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.RANGE_START,
				String.valueOf(start));

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.RANGE_END,
				String.valueOf(end));

		criteria.setSearchFieldValues(searchFields);
		criteria.setOrderByComparator(obc);

		ContentTypeService ctService =
			MirageServiceFactory.getContentTypeService();
		try {
			List<ContentType> contentTypes =
				ctService.searchContentTypesByCategory(category, criteria);
			return _getStructuresFromContentTypes(contentTypes);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	public List<JournalStructure> search(
			long companyId, long groupId, String structureId, String name,
			String description, boolean andOperator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		String categoryId = String.valueOf(groupId);
		Category category = new Category(categoryId, categoryId);

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.FINDER,
				OptionalJournalStructureCriteria.FIND_BY_C_G_S_N_D);

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.COMPANY_ID,
				String.valueOf(companyId));

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.STRUCTURE_ID,
				structureId);

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.NAME, name);

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.DESCRIPTION,
				description);

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.RANGE_START,
				String.valueOf(start));

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.RANGE_END,
				String.valueOf(end));

		criteria.setSearchFieldValues(searchFields);
		criteria.setOrderByComparator(obc);
		criteria.setMatchAnyOneField(!andOperator);

		ContentTypeService ctService =
			MirageServiceFactory.getContentTypeService();
		try {
			List<ContentType> contentTypes =
				ctService.searchContentTypesByCategory(category, criteria);
			return _getStructuresFromContentTypes(contentTypes);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	public int searchCount(long companyId, long groupId, String keywords)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		String categoryId = String.valueOf(groupId);
		Category category = new Category(categoryId, categoryId);

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.FINDER,
				OptionalJournalStructureCriteria.FIND_BY_KEYWORDS);

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.COMPANY_ID,
				String.valueOf(companyId));

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.KEYWORDS, keywords);

		criteria.setSearchFieldValues(searchFields);

		ContentTypeService ctService =
			MirageServiceFactory.getContentTypeService();
		try {
			return ctService.contentTypeSearchCount(category, criteria);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	public int searchCount(
			long companyId, long groupId, String structureId, String name,
			String description, boolean andOperator)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		String categoryId = String.valueOf(groupId);
		Category category = new Category(categoryId, categoryId);

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.FINDER,
				OptionalJournalStructureCriteria.FIND_BY_C_G_S_N_D);

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.COMPANY_ID,
				String.valueOf(companyId));

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.STRUCTURE_ID,
				structureId);

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.NAME, name);

		_addSearchField(
			searchFields, OptionalJournalStructureCriteria.DESCRIPTION,
				description);

		criteria.setSearchFieldValues(searchFields);
		criteria.setMatchAnyOneField(!andOperator);

		ContentTypeService ctService =
			MirageServiceFactory.getContentTypeService();
		try {
			return ctService.contentTypeSearchCount(category, criteria);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	public JournalStructure updateStructure(
			long groupId, String structureId, String name, String description,
			String xsd)
		throws PortalException, SystemException {

		// Populate the structure object with the method arguments

		JournalStructure structure = new JournalStructureImpl();
		structure.setGroupId(groupId);
		structure.setStructureId(structureId);
		structure.setName(name);
		structure.setDescription(description);
		structure.setXsd(xsd);

		JournalStructureContentType type =
			new JournalStructureContentType(structure);

		ContentTypeService ctService =
			MirageServiceFactory.getContentTypeService();
		try {
			ctService.updateContentType(type);
		}
		catch (CMSException ex) {
			_throwException(ex);
		}
		return type.getStructure();
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

	private List<JournalStructure> _getStructuresFromContentTypes(
		List<ContentType> contentTypes) {

		List<JournalStructure> structures = new ArrayList<JournalStructure>();
		for (ContentType contentType : contentTypes) {
			structures.add(
				((JournalStructureContentType) contentType).getStructure());
		}

		return structures;
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

}