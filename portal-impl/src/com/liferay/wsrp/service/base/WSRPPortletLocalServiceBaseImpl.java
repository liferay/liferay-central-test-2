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

package com.liferay.wsrp.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;

import com.liferay.wsrp.model.WSRPPortlet;
import com.liferay.wsrp.service.WSRPConfiguredProducerLocalService;
import com.liferay.wsrp.service.WSRPPortletLocalService;
import com.liferay.wsrp.service.persistence.WSRPConfiguredProducerPersistence;
import com.liferay.wsrp.service.persistence.WSRPPortletPersistence;

import java.util.List;

/**
 * <a href="WSRPPortletLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class WSRPPortletLocalServiceBaseImpl
	implements WSRPPortletLocalService {
	public WSRPPortlet addWSRPPortlet(WSRPPortlet wsrpPortlet)
		throws SystemException {
		wsrpPortlet.setNew(true);

		return wsrpPortletPersistence.update(wsrpPortlet, false);
	}

	public WSRPPortlet createWSRPPortlet(long wsrpPortletId) {
		return wsrpPortletPersistence.create(wsrpPortletId);
	}

	public void deleteWSRPPortlet(long wsrpPortletId)
		throws PortalException, SystemException {
		wsrpPortletPersistence.remove(wsrpPortletId);
	}

	public void deleteWSRPPortlet(WSRPPortlet wsrpPortlet)
		throws SystemException {
		wsrpPortletPersistence.remove(wsrpPortlet);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return wsrpPortletPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return wsrpPortletPersistence.findWithDynamicQuery(dynamicQuery, start,
			end);
	}

	public WSRPPortlet getWSRPPortlet(long wsrpPortletId)
		throws PortalException, SystemException {
		return wsrpPortletPersistence.findByPrimaryKey(wsrpPortletId);
	}

	public List<WSRPPortlet> getWSRPPortlets(int start, int end)
		throws SystemException {
		return wsrpPortletPersistence.findAll(start, end);
	}

	public int getWSRPPortletsCount() throws SystemException {
		return wsrpPortletPersistence.countAll();
	}

	public WSRPPortlet updateWSRPPortlet(WSRPPortlet wsrpPortlet)
		throws SystemException {
		wsrpPortlet.setNew(false);

		return wsrpPortletPersistence.update(wsrpPortlet, true);
	}

	public WSRPConfiguredProducerLocalService getWSRPConfiguredProducerLocalService() {
		return wsrpConfiguredProducerLocalService;
	}

	public void setWSRPConfiguredProducerLocalService(
		WSRPConfiguredProducerLocalService wsrpConfiguredProducerLocalService) {
		this.wsrpConfiguredProducerLocalService = wsrpConfiguredProducerLocalService;
	}

	public WSRPConfiguredProducerPersistence getWSRPConfiguredProducerPersistence() {
		return wsrpConfiguredProducerPersistence;
	}

	public void setWSRPConfiguredProducerPersistence(
		WSRPConfiguredProducerPersistence wsrpConfiguredProducerPersistence) {
		this.wsrpConfiguredProducerPersistence = wsrpConfiguredProducerPersistence;
	}

	public WSRPPortletLocalService getWSRPPortletLocalService() {
		return wsrpPortletLocalService;
	}

	public void setWSRPPortletLocalService(
		WSRPPortletLocalService wsrpPortletLocalService) {
		this.wsrpPortletLocalService = wsrpPortletLocalService;
	}

	public WSRPPortletPersistence getWSRPPortletPersistence() {
		return wsrpPortletPersistence;
	}

	public void setWSRPPortletPersistence(
		WSRPPortletPersistence wsrpPortletPersistence) {
		this.wsrpPortletPersistence = wsrpPortletPersistence;
	}

	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	public CounterService getCounterService() {
		return counterService;
	}

	public void setCounterService(CounterService counterService) {
		this.counterService = counterService;
	}

	@javax.annotation.Resource(name = "com.liferay.wsrp.service.WSRPConfiguredProducerLocalService.impl")
	protected WSRPConfiguredProducerLocalService wsrpConfiguredProducerLocalService;
	@javax.annotation.Resource(name = "com.liferay.wsrp.service.persistence.WSRPConfiguredProducerPersistence.impl")
	protected WSRPConfiguredProducerPersistence wsrpConfiguredProducerPersistence;
	@javax.annotation.Resource(name = "com.liferay.wsrp.service.WSRPPortletLocalService.impl")
	protected WSRPPortletLocalService wsrpPortletLocalService;
	@javax.annotation.Resource(name = "com.liferay.wsrp.service.persistence.WSRPPortletPersistence.impl")
	protected WSRPPortletPersistence wsrpPortletPersistence;
	@javax.annotation.Resource(name = "com.liferay.counter.service.CounterLocalService.impl")
	protected CounterLocalService counterLocalService;
	@javax.annotation.Resource(name = "com.liferay.counter.service.CounterService.impl")
	protected CounterService counterService;
}