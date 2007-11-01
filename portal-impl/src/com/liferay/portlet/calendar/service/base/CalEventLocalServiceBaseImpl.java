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

package com.liferay.portlet.calendar.service.base;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;

import com.liferay.portlet.calendar.service.CalEventLocalService;
import com.liferay.portlet.calendar.service.persistence.CalEventFinder;
import com.liferay.portlet.calendar.service.persistence.CalEventFinderUtil;
import com.liferay.portlet.calendar.service.persistence.CalEventPersistence;
import com.liferay.portlet.calendar.service.persistence.CalEventUtil;

import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * <a href="CalEventLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class CalEventLocalServiceBaseImpl
	implements CalEventLocalService, InitializingBean {
	public List dynamicQuery(DynamicQueryInitializer queryInitializer)
		throws SystemException {
		return CalEventUtil.findWithDynamicQuery(queryInitializer);
	}

	public List dynamicQuery(DynamicQueryInitializer queryInitializer,
		int begin, int end) throws SystemException {
		return CalEventUtil.findWithDynamicQuery(queryInitializer, begin, end);
	}

	public CalEventPersistence getCalEventPersistence() {
		return calEventPersistence;
	}

	public void setCalEventPersistence(CalEventPersistence calEventPersistence) {
		this.calEventPersistence = calEventPersistence;
	}

	public CalEventFinder getCalEventFinder() {
		return calEventFinder;
	}

	public void setCalEventFinder(CalEventFinder calEventFinder) {
		this.calEventFinder = calEventFinder;
	}

	public void afterPropertiesSet() {
		if (calEventPersistence == null) {
			calEventPersistence = CalEventUtil.getPersistence();
		}

		if (calEventFinder == null) {
			calEventFinder = CalEventFinderUtil.getFinder();
		}
	}

	protected CalEventPersistence calEventPersistence;
	protected CalEventFinder calEventFinder;
}