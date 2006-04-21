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

package com.liferay.portlet.journal.service.ejb;

import com.liferay.portal.spring.util.SpringUtil;

import com.liferay.portlet.journal.service.spring.JournalStructureLocalService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="JournalStructureLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalStructureLocalServiceEJBImpl
	implements JournalStructureLocalService, SessionBean {
	public static final String CLASS_NAME = JournalStructureLocalService.class.getName() +
		".transaction";

	public static JournalStructureLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (JournalStructureLocalService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portlet.journal.model.JournalStructure addStructure(
		java.lang.String userId, java.lang.String structureId,
		boolean autoStructureId, java.lang.String plid, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addStructure(userId, structureId, autoStructureId,
			plid, name, description, xsd, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addStructureResources(java.lang.String companyId,
		java.lang.String structureId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addStructureResources(companyId, structureId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addStructureResources(
		com.liferay.portlet.journal.model.JournalStructure structure,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addStructureResources(structure, addCommunityPermissions,
			addGuestPermissions);
	}

	public void checkNewLine(java.lang.String companyId,
		java.lang.String structureId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().checkNewLine(companyId, structureId);
	}

	public void deleteStructure(java.lang.String companyId,
		java.lang.String structureId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteStructure(companyId, structureId);
	}

	public void deleteStructure(
		com.liferay.portlet.journal.model.JournalStructure structure)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteStructure(structure);
	}

	public com.liferay.portlet.journal.model.JournalStructure getStructure(
		java.lang.String companyId, java.lang.String structureId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getStructure(companyId, structureId);
	}

	public java.util.List getStructures(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getService().getStructures(groupId);
	}

	public java.util.List getStructures(java.lang.String groupId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getService().getStructures(groupId, begin, end);
	}

	public int getStructuresCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getService().getStructuresCount(groupId);
	}

	public java.util.List search(java.lang.String companyId,
		java.lang.String structureId, java.lang.String groupId,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService().search(companyId, structureId, groupId, name,
			description, andOperator, begin, end, obc);
	}

	public int searchCount(java.lang.String companyId,
		java.lang.String structureId, java.lang.String groupId,
		java.lang.String name, java.lang.String description, boolean andOperator)
		throws com.liferay.portal.SystemException {
		return getService().searchCount(companyId, structureId, groupId, name,
			description, andOperator);
	}

	public com.liferay.portlet.journal.model.JournalStructure updateStructure(
		java.lang.String companyId, java.lang.String structureId,
		java.lang.String name, java.lang.String description,
		java.lang.String xsd)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updateStructure(companyId, structureId, name,
			description, xsd);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}