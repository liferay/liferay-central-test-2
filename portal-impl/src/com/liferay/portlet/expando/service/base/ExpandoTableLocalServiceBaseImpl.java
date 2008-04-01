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
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;

import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.service.ExpandoColumnLocalService;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceFactory;
import com.liferay.portlet.expando.service.ExpandoColumnService;
import com.liferay.portlet.expando.service.ExpandoColumnServiceFactory;
import com.liferay.portlet.expando.service.ExpandoRowLocalService;
import com.liferay.portlet.expando.service.ExpandoRowLocalServiceFactory;
import com.liferay.portlet.expando.service.ExpandoRowService;
import com.liferay.portlet.expando.service.ExpandoRowServiceFactory;
import com.liferay.portlet.expando.service.ExpandoTableLocalService;
import com.liferay.portlet.expando.service.ExpandoValueLocalService;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceFactory;
import com.liferay.portlet.expando.service.ExpandoValueService;
import com.liferay.portlet.expando.service.ExpandoValueServiceFactory;
import com.liferay.portlet.expando.service.persistence.ExpandoColumnPersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoColumnUtil;
import com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoRowUtil;
import com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoTableUtil;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoValueUtil;

import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * <a href="ExpandoTableLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class ExpandoTableLocalServiceBaseImpl
	implements ExpandoTableLocalService, InitializingBean {
	public ExpandoTable addExpandoTable(ExpandoTable expandoTable)
		throws SystemException {
		expandoTable.setNew(true);

		return expandoTablePersistence.update(expandoTable, false);
	}

	public void deleteExpandoTable(long tableId)
		throws PortalException, SystemException {
		expandoTablePersistence.remove(tableId);
	}

	public void deleteExpandoTable(ExpandoTable expandoTable)
		throws PortalException, SystemException {
		expandoTablePersistence.remove(expandoTable);
	}

	public List<ExpandoTable> dynamicQuery(
		DynamicQueryInitializer queryInitializer) throws SystemException {
		return expandoTablePersistence.findWithDynamicQuery(queryInitializer);
	}

	public List<ExpandoTable> dynamicQuery(
		DynamicQueryInitializer queryInitializer, int begin, int end)
		throws SystemException {
		return expandoTablePersistence.findWithDynamicQuery(queryInitializer,
			begin, end);
	}

	public ExpandoTable updateExpandoTable(ExpandoTable expandoTable)
		throws SystemException {
		expandoTable.setNew(false);

		return expandoTablePersistence.update(expandoTable, true);
	}

	public ExpandoColumnLocalService getExpandoColumnLocalService() {
		return expandoColumnLocalService;
	}

	public void setExpandoColumnLocalService(
		ExpandoColumnLocalService expandoColumnLocalService) {
		this.expandoColumnLocalService = expandoColumnLocalService;
	}

	public ExpandoColumnService getExpandoColumnService() {
		return expandoColumnService;
	}

	public void setExpandoColumnService(
		ExpandoColumnService expandoColumnService) {
		this.expandoColumnService = expandoColumnService;
	}

	public ExpandoColumnPersistence getExpandoColumnPersistence() {
		return expandoColumnPersistence;
	}

	public void setExpandoColumnPersistence(
		ExpandoColumnPersistence expandoColumnPersistence) {
		this.expandoColumnPersistence = expandoColumnPersistence;
	}

	public ExpandoRowLocalService getExpandoRowLocalService() {
		return expandoRowLocalService;
	}

	public void setExpandoRowLocalService(
		ExpandoRowLocalService expandoRowLocalService) {
		this.expandoRowLocalService = expandoRowLocalService;
	}

	public ExpandoRowService getExpandoRowService() {
		return expandoRowService;
	}

	public void setExpandoRowService(ExpandoRowService expandoRowService) {
		this.expandoRowService = expandoRowService;
	}

	public ExpandoRowPersistence getExpandoRowPersistence() {
		return expandoRowPersistence;
	}

	public void setExpandoRowPersistence(
		ExpandoRowPersistence expandoRowPersistence) {
		this.expandoRowPersistence = expandoRowPersistence;
	}

	public ExpandoTablePersistence getExpandoTablePersistence() {
		return expandoTablePersistence;
	}

	public void setExpandoTablePersistence(
		ExpandoTablePersistence expandoTablePersistence) {
		this.expandoTablePersistence = expandoTablePersistence;
	}

	public ExpandoValueLocalService getExpandoValueLocalService() {
		return expandoValueLocalService;
	}

	public void setExpandoValueLocalService(
		ExpandoValueLocalService expandoValueLocalService) {
		this.expandoValueLocalService = expandoValueLocalService;
	}

	public ExpandoValueService getExpandoValueService() {
		return expandoValueService;
	}

	public void setExpandoValueService(ExpandoValueService expandoValueService) {
		this.expandoValueService = expandoValueService;
	}

	public ExpandoValuePersistence getExpandoValuePersistence() {
		return expandoValuePersistence;
	}

	public void setExpandoValuePersistence(
		ExpandoValuePersistence expandoValuePersistence) {
		this.expandoValuePersistence = expandoValuePersistence;
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

	public void afterPropertiesSet() {
		if (expandoColumnLocalService == null) {
			expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getImpl();
		}

		if (expandoColumnService == null) {
			expandoColumnService = ExpandoColumnServiceFactory.getImpl();
		}

		if (expandoColumnPersistence == null) {
			expandoColumnPersistence = ExpandoColumnUtil.getPersistence();
		}

		if (expandoRowLocalService == null) {
			expandoRowLocalService = ExpandoRowLocalServiceFactory.getImpl();
		}

		if (expandoRowService == null) {
			expandoRowService = ExpandoRowServiceFactory.getImpl();
		}

		if (expandoRowPersistence == null) {
			expandoRowPersistence = ExpandoRowUtil.getPersistence();
		}

		if (expandoTablePersistence == null) {
			expandoTablePersistence = ExpandoTableUtil.getPersistence();
		}

		if (expandoValueLocalService == null) {
			expandoValueLocalService = ExpandoValueLocalServiceFactory.getImpl();
		}

		if (expandoValueService == null) {
			expandoValueService = ExpandoValueServiceFactory.getImpl();
		}

		if (expandoValuePersistence == null) {
			expandoValuePersistence = ExpandoValueUtil.getPersistence();
		}

		if (counterLocalService == null) {
			counterLocalService = CounterLocalServiceFactory.getImpl();
		}

		if (counterService == null) {
			counterService = CounterServiceFactory.getImpl();
		}
	}

	protected ExpandoColumnLocalService expandoColumnLocalService;
	protected ExpandoColumnService expandoColumnService;
	protected ExpandoColumnPersistence expandoColumnPersistence;
	protected ExpandoRowLocalService expandoRowLocalService;
	protected ExpandoRowService expandoRowService;
	protected ExpandoRowPersistence expandoRowPersistence;
	protected ExpandoTablePersistence expandoTablePersistence;
	protected ExpandoValueLocalService expandoValueLocalService;
	protected ExpandoValueService expandoValueService;
	protected ExpandoValuePersistence expandoValuePersistence;
	protected CounterLocalService counterLocalService;
	protected CounterService counterService;
}