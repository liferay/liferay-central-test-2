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

package com.liferay.portlet.expando.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterLocalServiceFactory;
import com.liferay.counter.service.CounterService;
import com.liferay.counter.service.CounterServiceFactory;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;

import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.service.ExpandoColumnLocalService;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceFactory;
import com.liferay.portlet.expando.service.ExpandoRowLocalService;
import com.liferay.portlet.expando.service.ExpandoRowLocalServiceFactory;
import com.liferay.portlet.expando.service.ExpandoTableLocalService;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceFactory;
import com.liferay.portlet.expando.service.ExpandoValueLocalService;
import com.liferay.portlet.expando.service.persistence.ExpandoColumnFinder;
import com.liferay.portlet.expando.service.persistence.ExpandoColumnFinderUtil;
import com.liferay.portlet.expando.service.persistence.ExpandoColumnPersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoColumnUtil;
import com.liferay.portlet.expando.service.persistence.ExpandoRowFinder;
import com.liferay.portlet.expando.service.persistence.ExpandoRowFinderUtil;
import com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoRowUtil;
import com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoTableUtil;
import com.liferay.portlet.expando.service.persistence.ExpandoValueFinder;
import com.liferay.portlet.expando.service.persistence.ExpandoValueFinderUtil;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoValueUtil;

import java.util.List;

/**
 * <a href="ExpandoValueLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class ExpandoValueLocalServiceBaseImpl
	implements ExpandoValueLocalService {
	public ExpandoValue addExpandoValue(ExpandoValue expandoValue)
		throws SystemException {
		expandoValue.setNew(true);

		return expandoValuePersistence.update(expandoValue, false);
	}

	public void deleteExpandoValue(long valueId)
		throws PortalException, SystemException {
		expandoValuePersistence.remove(valueId);
	}

	public void deleteExpandoValue(ExpandoValue expandoValue)
		throws SystemException {
		expandoValuePersistence.remove(expandoValue);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return expandoValuePersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return expandoValuePersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	public ExpandoValue getExpandoValue(long valueId)
		throws PortalException, SystemException {
		return expandoValuePersistence.findByPrimaryKey(valueId);
	}

	public ExpandoValue updateExpandoValue(ExpandoValue expandoValue)
		throws SystemException {
		expandoValue.setNew(false);

		return expandoValuePersistence.update(expandoValue, true);
	}

	public ExpandoColumnLocalService getExpandoColumnLocalService() {
		return expandoColumnLocalService;
	}

	public void setExpandoColumnLocalService(
		ExpandoColumnLocalService expandoColumnLocalService) {
		this.expandoColumnLocalService = expandoColumnLocalService;
	}

	public ExpandoColumnPersistence getExpandoColumnPersistence() {
		return expandoColumnPersistence;
	}

	public void setExpandoColumnPersistence(
		ExpandoColumnPersistence expandoColumnPersistence) {
		this.expandoColumnPersistence = expandoColumnPersistence;
	}

	public ExpandoColumnFinder getExpandoColumnFinder() {
		return expandoColumnFinder;
	}

	public void setExpandoColumnFinder(ExpandoColumnFinder expandoColumnFinder) {
		this.expandoColumnFinder = expandoColumnFinder;
	}

	public ExpandoRowLocalService getExpandoRowLocalService() {
		return expandoRowLocalService;
	}

	public void setExpandoRowLocalService(
		ExpandoRowLocalService expandoRowLocalService) {
		this.expandoRowLocalService = expandoRowLocalService;
	}

	public ExpandoRowPersistence getExpandoRowPersistence() {
		return expandoRowPersistence;
	}

	public void setExpandoRowPersistence(
		ExpandoRowPersistence expandoRowPersistence) {
		this.expandoRowPersistence = expandoRowPersistence;
	}

	public ExpandoRowFinder getExpandoRowFinder() {
		return expandoRowFinder;
	}

	public void setExpandoRowFinder(ExpandoRowFinder expandoRowFinder) {
		this.expandoRowFinder = expandoRowFinder;
	}

	public ExpandoTableLocalService getExpandoTableLocalService() {
		return expandoTableLocalService;
	}

	public void setExpandoTableLocalService(
		ExpandoTableLocalService expandoTableLocalService) {
		this.expandoTableLocalService = expandoTableLocalService;
	}

	public ExpandoTablePersistence getExpandoTablePersistence() {
		return expandoTablePersistence;
	}

	public void setExpandoTablePersistence(
		ExpandoTablePersistence expandoTablePersistence) {
		this.expandoTablePersistence = expandoTablePersistence;
	}

	public ExpandoValuePersistence getExpandoValuePersistence() {
		return expandoValuePersistence;
	}

	public void setExpandoValuePersistence(
		ExpandoValuePersistence expandoValuePersistence) {
		this.expandoValuePersistence = expandoValuePersistence;
	}

	public ExpandoValueFinder getExpandoValueFinder() {
		return expandoValueFinder;
	}

	public void setExpandoValueFinder(ExpandoValueFinder expandoValueFinder) {
		this.expandoValueFinder = expandoValueFinder;
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

	protected void init() {
		if (expandoColumnLocalService == null) {
			expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getImpl();
		}

		if (expandoColumnPersistence == null) {
			expandoColumnPersistence = ExpandoColumnUtil.getPersistence();
		}

		if (expandoColumnFinder == null) {
			expandoColumnFinder = ExpandoColumnFinderUtil.getFinder();
		}

		if (expandoRowLocalService == null) {
			expandoRowLocalService = ExpandoRowLocalServiceFactory.getImpl();
		}

		if (expandoRowPersistence == null) {
			expandoRowPersistence = ExpandoRowUtil.getPersistence();
		}

		if (expandoRowFinder == null) {
			expandoRowFinder = ExpandoRowFinderUtil.getFinder();
		}

		if (expandoTableLocalService == null) {
			expandoTableLocalService = ExpandoTableLocalServiceFactory.getImpl();
		}

		if (expandoTablePersistence == null) {
			expandoTablePersistence = ExpandoTableUtil.getPersistence();
		}

		if (expandoValuePersistence == null) {
			expandoValuePersistence = ExpandoValueUtil.getPersistence();
		}

		if (expandoValueFinder == null) {
			expandoValueFinder = ExpandoValueFinderUtil.getFinder();
		}

		if (counterLocalService == null) {
			counterLocalService = CounterLocalServiceFactory.getImpl();
		}

		if (counterService == null) {
			counterService = CounterServiceFactory.getImpl();
		}
	}

	protected ExpandoColumnLocalService expandoColumnLocalService;
	protected ExpandoColumnPersistence expandoColumnPersistence;
	protected ExpandoColumnFinder expandoColumnFinder;
	protected ExpandoRowLocalService expandoRowLocalService;
	protected ExpandoRowPersistence expandoRowPersistence;
	protected ExpandoRowFinder expandoRowFinder;
	protected ExpandoTableLocalService expandoTableLocalService;
	protected ExpandoTablePersistence expandoTablePersistence;
	protected ExpandoValuePersistence expandoValuePersistence;
	protected ExpandoValueFinder expandoValueFinder;
	protected CounterLocalService counterLocalService;
	protected CounterService counterService;
}