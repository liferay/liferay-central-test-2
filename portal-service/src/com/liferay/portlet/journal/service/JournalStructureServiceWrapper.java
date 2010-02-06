/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
 * <a href="JournalStructureServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link JournalStructureService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalStructureService
 * @generated
 */
public class JournalStructureServiceWrapper implements JournalStructureService {
	public JournalStructureServiceWrapper(
		JournalStructureService journalStructureService) {
		_journalStructureService = journalStructureService;
	}

	public com.liferay.portlet.journal.model.JournalStructure addStructure(
		long groupId, java.lang.String structureId, boolean autoStructureId,
		java.lang.String parentStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalStructureService.addStructure(groupId, structureId,
			autoStructureId, parentStructureId, name, description, xsd,
			serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalStructure copyStructure(
		long groupId, java.lang.String oldStructureId,
		java.lang.String newStructureId, boolean autoStructureId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalStructureService.copyStructure(groupId, oldStructureId,
			newStructureId, autoStructureId);
	}

	public void deleteStructure(long groupId, java.lang.String structureId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalStructureService.deleteStructure(groupId, structureId);
	}

	public com.liferay.portlet.journal.model.JournalStructure getStructure(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalStructureService.getStructure(groupId, structureId);
	}

	public com.liferay.portlet.journal.model.JournalStructure updateStructure(
		long groupId, java.lang.String structureId,
		java.lang.String parentStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalStructureService.updateStructure(groupId, structureId,
			parentStructureId, name, description, xsd, serviceContext);
	}

	public JournalStructureService getWrappedJournalStructureService() {
		return _journalStructureService;
	}

	private JournalStructureService _journalStructureService;
}