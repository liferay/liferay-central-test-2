/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tags.service.ejb;

import com.liferay.portlet.tags.service.TagsEntryLocalService;
import com.liferay.portlet.tags.service.TagsEntryLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="TagsEntryLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is the EJB implementation of the service that is used when Liferay
 * is run inside a full J2EE container.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.tags.service.TagsEntryLocalService
 * @see com.liferay.portlet.tags.service.TagsEntryLocalServiceUtil
 * @see com.liferay.portlet.tags.service.ejb.TagsEntryLocalServiceEJB
 * @see com.liferay.portlet.tags.service.ejb.TagsEntryLocalServiceHome
 * @see com.liferay.portlet.tags.service.impl.TagsEntryLocalServiceImpl
 *
 */
public class TagsEntryLocalServiceEJBImpl implements TagsEntryLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return TagsEntryLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return TagsEntryLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.tags.model.TagsEntry addEntry(long userId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return TagsEntryLocalServiceFactory.getTxImpl().addEntry(userId, name);
	}

	public com.liferay.portlet.tags.model.TagsEntry addEntry(long userId,
		java.lang.String name, java.lang.String[] properties)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return TagsEntryLocalServiceFactory.getTxImpl().addEntry(userId, name,
			properties);
	}

	public void deleteEntry(long entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsEntryLocalServiceFactory.getTxImpl().deleteEntry(entryId);
	}

	public void deleteEntry(com.liferay.portlet.tags.model.TagsEntry entry)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsEntryLocalServiceFactory.getTxImpl().deleteEntry(entry);
	}

	public boolean hasEntry(long companyId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return TagsEntryLocalServiceFactory.getTxImpl().hasEntry(companyId, name);
	}

	public java.util.List getEntries()
		throws com.liferay.portal.SystemException {
		return TagsEntryLocalServiceFactory.getTxImpl().getEntries();
	}

	public java.util.List getEntries(java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return TagsEntryLocalServiceFactory.getTxImpl().getEntries(className,
			classPK);
	}

	public com.liferay.portlet.tags.model.TagsEntry getEntry(long entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return TagsEntryLocalServiceFactory.getTxImpl().getEntry(entryId);
	}

	public com.liferay.portlet.tags.model.TagsEntry getEntry(long companyId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return TagsEntryLocalServiceFactory.getTxImpl().getEntry(companyId, name);
	}

	public long[] getEntryIds(long companyId, java.lang.String[] names)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return TagsEntryLocalServiceFactory.getTxImpl().getEntryIds(companyId,
			names);
	}

	public java.util.List search(long companyId, java.lang.String name,
		java.lang.String[] properties)
		throws com.liferay.portal.SystemException {
		return TagsEntryLocalServiceFactory.getTxImpl().search(companyId, name,
			properties);
	}

	public java.util.List search(long companyId, java.lang.String name,
		java.lang.String[] properties, int begin, int end)
		throws com.liferay.portal.SystemException {
		return TagsEntryLocalServiceFactory.getTxImpl().search(companyId, name,
			properties, begin, end);
	}

	public com.liferay.portal.kernel.json.JSONArrayWrapper searchAutocomplete(
		long companyId, java.lang.String name, java.lang.String[] properties,
		int begin, int end) throws com.liferay.portal.SystemException {
		return TagsEntryLocalServiceFactory.getTxImpl().searchAutocomplete(companyId,
			name, properties, begin, end);
	}

	public int searchCount(long companyId, java.lang.String name,
		java.lang.String[] properties)
		throws com.liferay.portal.SystemException {
		return TagsEntryLocalServiceFactory.getTxImpl().searchCount(companyId,
			name, properties);
	}

	public com.liferay.portlet.tags.model.TagsEntry updateEntry(long entryId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return TagsEntryLocalServiceFactory.getTxImpl().updateEntry(entryId,
			name);
	}

	public com.liferay.portlet.tags.model.TagsEntry updateEntry(long userId,
		long entryId, java.lang.String name, java.lang.String[] properties)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return TagsEntryLocalServiceFactory.getTxImpl().updateEntry(userId,
			entryId, name, properties);
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