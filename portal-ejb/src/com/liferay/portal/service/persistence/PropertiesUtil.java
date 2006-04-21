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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="PropertiesUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PropertiesUtil {
	public static final String CLASS_NAME = PropertiesUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.Properties"));

	public static com.liferay.portal.model.Properties create(
		com.liferay.portal.service.persistence.PropertiesPK propertiesPK) {
		return getPersistence().create(propertiesPK);
	}

	public static com.liferay.portal.model.Properties remove(
		com.liferay.portal.service.persistence.PropertiesPK propertiesPK)
		throws com.liferay.portal.NoSuchPropertiesException, 
			com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(propertiesPK));
		}

		com.liferay.portal.model.Properties properties = getPersistence()
															 .remove(propertiesPK);

		if (listener != null) {
			listener.onAfterRemove(properties);
		}

		return properties;
	}

	public static com.liferay.portal.model.Properties update(
		com.liferay.portal.model.Properties properties)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = properties.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(properties);
			}
			else {
				listener.onBeforeUpdate(properties);
			}
		}

		properties = getPersistence().update(properties);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(properties);
			}
			else {
				listener.onAfterUpdate(properties);
			}
		}

		return properties;
	}

	public static com.liferay.portal.model.Properties findByPrimaryKey(
		com.liferay.portal.service.persistence.PropertiesPK propertiesPK)
		throws com.liferay.portal.NoSuchPropertiesException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(propertiesPK);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end, obc);
	}

	public static com.liferay.portal.model.Properties findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPropertiesException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.Properties findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPropertiesException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.Properties[] findByCompanyId_PrevAndNext(
		com.liferay.portal.service.persistence.PropertiesPK propertiesPK,
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPropertiesException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_PrevAndNext(propertiesPK,
			companyId, obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static int countByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static PropertiesPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		PropertiesUtil util = (PropertiesUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(PropertiesPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(PropertiesUtil.class);
	private PropertiesPersistence _persistence;
}