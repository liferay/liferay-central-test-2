/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portlet.journal.service.JournalStructureLocalService;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceFactory;

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
	public com.liferay.portlet.journal.model.JournalStructure addStructure(
		java.lang.String userId, java.lang.String structureId,
		boolean autoStructureId, java.lang.String plid, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalStructureLocalServiceFactory.getTxImpl().addStructure(userId,
			structureId, autoStructureId, plid, name, description, xsd,
			addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.journal.model.JournalStructure addStructure(
		java.lang.String userId, java.lang.String structureId,
		boolean autoStructureId, java.lang.String plid, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalStructureLocalServiceFactory.getTxImpl().addStructure(userId,
			structureId, autoStructureId, plid, name, description, xsd,
			communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.journal.model.JournalStructure addStructure(
		java.lang.String userId, java.lang.String structureId,
		boolean autoStructureId, java.lang.String plid, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalStructureLocalServiceFactory.getTxImpl().addStructure(userId,
			structureId, autoStructureId, plid, name, description, xsd,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public void addStructureResources(java.lang.String companyId,
		java.lang.String groupId, java.lang.String structureId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalStructureLocalServiceFactory.getTxImpl().addStructureResources(companyId,
			groupId, structureId, addCommunityPermissions, addGuestPermissions);
	}

	public void addStructureResources(
		com.liferay.portlet.journal.model.JournalStructure structure,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalStructureLocalServiceFactory.getTxImpl().addStructureResources(structure,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addStructureResources(java.lang.String companyId,
		java.lang.String groupId, java.lang.String structureId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalStructureLocalServiceFactory.getTxImpl().addStructureResources(companyId,
			groupId, structureId, communityPermissions, guestPermissions);
	}

	public void addStructureResources(
		com.liferay.portlet.journal.model.JournalStructure structure,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalStructureLocalServiceFactory.getTxImpl().addStructureResources(structure,
			communityPermissions, guestPermissions);
	}

	public void checkNewLine(java.lang.String companyId,
		java.lang.String groupId, java.lang.String structureId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalStructureLocalServiceFactory.getTxImpl().checkNewLine(companyId,
			groupId, structureId);
	}

	public void deleteStructure(java.lang.String companyId,
		java.lang.String groupId, java.lang.String structureId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalStructureLocalServiceFactory.getTxImpl().deleteStructure(companyId,
			groupId, structureId);
	}

	public void deleteStructure(
		com.liferay.portlet.journal.model.JournalStructure structure)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalStructureLocalServiceFactory.getTxImpl().deleteStructure(structure);
	}

	public com.liferay.portlet.journal.model.JournalStructure getStructure(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String structureId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalStructureLocalServiceFactory.getTxImpl().getStructure(companyId,
			groupId, structureId);
	}

	public java.util.List getStructures(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return JournalStructureLocalServiceFactory.getTxImpl().getStructures(groupId);
	}

	public java.util.List getStructures(java.lang.String groupId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return JournalStructureLocalServiceFactory.getTxImpl().getStructures(groupId,
			begin, end);
	}

	public int getStructuresCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return JournalStructureLocalServiceFactory.getTxImpl()
												  .getStructuresCount(groupId);
	}

	public java.util.List search(java.lang.String companyId,
		java.lang.String structureId, java.lang.String groupId,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return JournalStructureLocalServiceFactory.getTxImpl().search(companyId,
			structureId, groupId, name, description, andOperator, begin, end,
			obc);
	}

	public int searchCount(java.lang.String companyId,
		java.lang.String structureId, java.lang.String groupId,
		java.lang.String name, java.lang.String description, boolean andOperator)
		throws com.liferay.portal.SystemException {
		return JournalStructureLocalServiceFactory.getTxImpl().searchCount(companyId,
			structureId, groupId, name, description, andOperator);
	}

	public com.liferay.portlet.journal.model.JournalStructure updateStructure(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalStructureLocalServiceFactory.getTxImpl().updateStructure(companyId,
			groupId, structureId, name, description, xsd);
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