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

import com.liferay.portlet.tags.service.TagsPropertyLocalService;
import com.liferay.portlet.tags.service.TagsPropertyLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="TagsPropertyLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portlet.tags.service.TagsPropertyLocalService
 * @see com.liferay.portlet.tags.service.TagsPropertyLocalServiceUtil
 * @see com.liferay.portlet.tags.service.ejb.TagsPropertyLocalServiceEJB
 * @see com.liferay.portlet.tags.service.ejb.TagsPropertyLocalServiceHome
 * @see com.liferay.portlet.tags.service.impl.TagsPropertyLocalServiceImpl
 *
 */
public class TagsPropertyLocalServiceEJBImpl implements TagsPropertyLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return TagsPropertyLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return TagsPropertyLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.tags.model.TagsProperty addProperty(
		long userId, long entryId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return TagsPropertyLocalServiceFactory.getTxImpl().addProperty(userId,
			entryId, key, value);
	}

	public com.liferay.portlet.tags.model.TagsProperty addProperty(
		long userId, java.lang.String entryName, java.lang.String key,
		java.lang.String value)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return TagsPropertyLocalServiceFactory.getTxImpl().addProperty(userId,
			entryName, key, value);
	}

	public void deleteProperties(long entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsPropertyLocalServiceFactory.getTxImpl().deleteProperties(entryId);
	}

	public void deleteProperty(long propertyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsPropertyLocalServiceFactory.getTxImpl().deleteProperty(propertyId);
	}

	public void deleteProperty(
		com.liferay.portlet.tags.model.TagsProperty property)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsPropertyLocalServiceFactory.getTxImpl().deleteProperty(property);
	}

	public java.util.List getProperties()
		throws com.liferay.portal.SystemException {
		return TagsPropertyLocalServiceFactory.getTxImpl().getProperties();
	}

	public java.util.List getProperties(long entryId)
		throws com.liferay.portal.SystemException {
		return TagsPropertyLocalServiceFactory.getTxImpl().getProperties(entryId);
	}

	public com.liferay.portlet.tags.model.TagsProperty getProperty(
		long propertyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return TagsPropertyLocalServiceFactory.getTxImpl().getProperty(propertyId);
	}

	public com.liferay.portlet.tags.model.TagsProperty getProperty(
		long entryId, java.lang.String key)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return TagsPropertyLocalServiceFactory.getTxImpl().getProperty(entryId,
			key);
	}

	public java.lang.String[] getPropertyKeys(long companyId)
		throws com.liferay.portal.SystemException {
		return TagsPropertyLocalServiceFactory.getTxImpl().getPropertyKeys(companyId);
	}

	public java.util.List getPropertyValues(long companyId, java.lang.String key)
		throws com.liferay.portal.SystemException {
		return TagsPropertyLocalServiceFactory.getTxImpl().getPropertyValues(companyId,
			key);
	}

	public com.liferay.portlet.tags.model.TagsProperty updateProperty(
		long propertyId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return TagsPropertyLocalServiceFactory.getTxImpl().updateProperty(propertyId,
			key, value);
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