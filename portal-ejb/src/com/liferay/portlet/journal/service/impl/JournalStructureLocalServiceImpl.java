/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.counter.service.spring.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.DuplicateStructureIdException;
import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.RequiredStructureException;
import com.liferay.portlet.journal.StructureDescriptionException;
import com.liferay.portlet.journal.StructureIdException;
import com.liferay.portlet.journal.StructureNameException;
import com.liferay.portlet.journal.StructureXsdException;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.journal.service.persistence.JournalStructureFinder;
import com.liferay.portlet.journal.service.persistence.JournalStructurePK;
import com.liferay.portlet.journal.service.persistence.JournalStructureUtil;
import com.liferay.portlet.journal.service.persistence.JournalTemplateUtil;
import com.liferay.portlet.journal.service.spring.JournalStructureLocalService;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.dao.hibernate.OrderByComparator;

import java.io.IOException;
import java.io.StringReader;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="JournalStructureLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalStructureLocalServiceImpl
	implements JournalStructureLocalService {

	public JournalStructure addStructure(
			String userId, String structureId, boolean autoStructureId,
			String plid, String name, String description, String xsd,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		// Structure

		User user = UserUtil.findByPrimaryKey(userId);
		structureId = structureId.trim().toUpperCase();
		String groupId = PortalUtil.getPortletGroupId(plid);
		Date now = new Date();

		try {
			xsd = JournalUtil.formatXML(xsd);
		}
		catch (DocumentException de) {
			throw new StructureXsdException();
		}
		catch (IOException ioe) {
			throw new StructureXsdException();
		}

		validate(
			user.getCompanyId(), structureId, autoStructureId, name,
			description, xsd);

		if (autoStructureId) {
			structureId = Long.toString(CounterLocalServiceUtil.increment(
				JournalStructure.class.getName() + "." + user.getCompanyId()));
		}

		JournalStructurePK pk = new JournalStructurePK(
			user.getCompanyId(), structureId);

		JournalStructure structure = JournalStructureUtil.create(pk);

		structure.setGroupId(groupId);
		structure.setCompanyId(user.getCompanyId());
		structure.setUserId(user.getUserId());
		structure.setUserName(user.getFullName());
		structure.setCreateDate(now);
		structure.setModifiedDate(now);
		structure.setName(name);
		structure.setDescription(description);
		structure.setXsd(xsd);

		JournalStructureUtil.update(structure);

		// Resources

		addStructureResources(
			structure, addCommunityPermissions, addGuestPermissions);

		return structure;
	}

	public void addStructureResources(
			String companyId, String structureId,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		JournalStructure structure = JournalStructureUtil.findByPrimaryKey(
			new JournalStructurePK(companyId, structureId));

		addStructureResources(
			structure, addCommunityPermissions, addGuestPermissions);
	}

	public void addStructureResources(
			JournalStructure structure, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			structure.getCompanyId(), structure.getGroupId(),
			structure.getUserId(), JournalStructure.class.getName(),
			structure.getPrimaryKey().toString(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void checkNewLine(String companyId, String structureId)
		throws PortalException, SystemException {

		JournalStructure structure = JournalStructureUtil.findByPrimaryKey(
			new JournalStructurePK(companyId, structureId));

		String xsd = structure.getXsd();

		if ((xsd != null) && (xsd.indexOf("\\n") != -1)) {
			xsd = StringUtil.replace(
				xsd,
				new String[] {"\\n", "\\r"},
				new String[] {"\n", "\r"});

			structure.setXsd(xsd);

			JournalStructureUtil.update(structure);
		}
	}

	public void deleteStructure(String companyId, String structureId)
		throws PortalException, SystemException {

		structureId = structureId.trim().toUpperCase();

		JournalStructure structure = JournalStructureUtil.findByPrimaryKey(
			new JournalStructurePK(companyId, structureId));

		deleteStructure(structure);
	}

	public void deleteStructure(JournalStructure structure)
		throws PortalException, SystemException {

		if (JournalArticleUtil.countByC_S(
				structure.getCompanyId(), structure.getStructureId()) > 0) {

			throw new RequiredStructureException();
		}

		if (JournalTemplateUtil.countByC_S(
				structure.getCompanyId(), structure.getStructureId()) > 0) {

			throw new RequiredStructureException();
		}

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			structure.getCompanyId(), JournalStructure.class.getName(),
			Resource.TYPE_CLASS, Resource.SCOPE_INDIVIDUAL,
			structure.getPrimaryKey().toString());

		// Structure

		JournalStructureUtil.remove(structure.getPrimaryKey());
	}

	public JournalStructure getStructure(String companyId, String structureId)
		throws PortalException, SystemException {

		structureId = structureId.trim().toUpperCase();

		return JournalStructureUtil.findByPrimaryKey(
			new JournalStructurePK(companyId, structureId));
	}

	public List getStructures(String groupId) throws SystemException {
		return JournalStructureUtil.findByGroupId(groupId);
	}

	public List getStructures(String groupId, int begin, int end)
		throws SystemException {

		return JournalStructureUtil.findByGroupId(groupId, begin, end);
	}

	public int getStructuresCount(String groupId) throws SystemException {
		return JournalStructureUtil.countByGroupId(groupId);
	}

	public List search(
			String companyId, String structureId, String groupId, String name,
			String description, boolean andOperator, int begin, int end,
			OrderByComparator obc)
		throws SystemException {

		return JournalStructureFinder.findByC_S_G_N_D(
			companyId, structureId, groupId, name, description, andOperator,
			begin, end, obc);
	}

	public int searchCount(
			String companyId, String structureId, String groupId, String name,
			String description, boolean andOperator)
		throws SystemException {

		return JournalStructureFinder.countByC_S_G_N_D(
			companyId, structureId, groupId, name, description, andOperator);
	}

	public JournalStructure updateStructure(
			String companyId, String structureId, String name,
			String description, String xsd)
		throws PortalException, SystemException {

		structureId = structureId.trim().toUpperCase();

		try {
			xsd = JournalUtil.formatXML(xsd);
		}
		catch (DocumentException de) {
			throw new StructureXsdException();
		}
		catch (IOException ioe) {
			throw new StructureXsdException();
		}

		validate(name, description, xsd);

		JournalStructure structure = JournalStructureUtil.findByPrimaryKey(
			new JournalStructurePK(companyId, structureId));

		structure.setModifiedDate(new Date());
		structure.setName(name);
		structure.setDescription(description);
		structure.setXsd(xsd);

		JournalStructureUtil.update(structure);

		return structure;
	}

	protected void validate(
			String companyId, String structureId, boolean autoStructureId,
			String name, String description, String xsd)
		throws PortalException, SystemException {

		if (!autoStructureId) {
			if ((Validator.isNull(structureId)) ||
				(Validator.isNumber(structureId)) ||
				(structureId.indexOf(StringPool.SPACE) != -1)) {

				throw new StructureIdException();
			}

			try {
				JournalStructureUtil.findByPrimaryKey(
					new JournalStructurePK(companyId, structureId));

				throw new DuplicateStructureIdException();
			}
			catch (NoSuchStructureException nste) {
			}
		}

		validate(name, description, xsd);
	}

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

				List children = root.elements();

				if (children.size() == 0) {
					throw new StructureXsdException();
				}

				Set elNames = new HashSet();

				validate(children, elNames);
			}
			catch (Exception e) {
				throw new StructureXsdException();
			}
		}
	}

	protected void validate(List children, Set elNames) throws PortalException {
		Iterator itr = children.iterator();

		while (itr.hasNext()) {
			Element el = (Element)itr.next();

			String elName = el.attributeValue("name", StringPool.BLANK);
			String elType = el.attributeValue("type", StringPool.BLANK);

			if (Validator.isNull(elName) ||
				elName.startsWith(JournalStructure.RESERVED)) {

				throw new StructureXsdException();
			}
			else {
				char[] c = elName.toCharArray();

				for (int i = 0; i < c.length; i++) {
					if ((!Validator.isChar(c[i])) &&
						(!Validator.isDigit(c[i])) &&
						(c[i] != '_') && (c[i] != '-')) {

						throw new StructureXsdException();
					}
				}

				String completePath = elName;

				Element parent = el.getParent();

				while (!parent.isRootElement()) {
					completePath =
						parent.attributeValue("name", StringPool.BLANK) +
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

}