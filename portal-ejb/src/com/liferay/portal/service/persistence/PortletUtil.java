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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PortletUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletUtil {
	public static com.liferay.portal.model.Portlet create(
		com.liferay.portal.service.persistence.PortletPK portletPK) {
		return getPersistence().create(portletPK);
	}

	public static com.liferay.portal.model.Portlet remove(
		com.liferay.portal.service.persistence.PortletPK portletPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(portletPK));
		}

		com.liferay.portal.model.Portlet portlet = getPersistence().remove(portletPK);

		if (listener != null) {
			listener.onAfterRemove(portlet);
		}

		return portlet;
	}

	public static com.liferay.portal.model.Portlet remove(
		com.liferay.portal.model.Portlet portlet)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(portlet);
		}

		portlet = getPersistence().remove(portlet);

		if (listener != null) {
			listener.onAfterRemove(portlet);
		}

		return portlet;
	}

	public static com.liferay.portal.model.Portlet update(
		com.liferay.portal.model.Portlet portlet)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = portlet.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(portlet);
			}
			else {
				listener.onBeforeUpdate(portlet);
			}
		}

		portlet = getPersistence().update(portlet);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(portlet);
			}
			else {
				listener.onAfterUpdate(portlet);
			}
		}

		return portlet;
	}

	public static com.liferay.portal.model.Portlet update(
		com.liferay.portal.model.Portlet portlet, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = portlet.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(portlet);
			}
			else {
				listener.onBeforeUpdate(portlet);
			}
		}

		portlet = getPersistence().update(portlet, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(portlet);
			}
			else {
				listener.onAfterUpdate(portlet);
			}
		}

		return portlet;
	}

	public static com.liferay.portal.model.Portlet findByPrimaryKey(
		com.liferay.portal.service.persistence.PortletPK portletPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletException {
		return getPersistence().findByPrimaryKey(portletPK);
	}

	public static com.liferay.portal.model.Portlet fetchByPrimaryKey(
		com.liferay.portal.service.persistence.PortletPK portletPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(portletPK);
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
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end, obc);
	}

	public static com.liferay.portal.model.Portlet findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.Portlet findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.Portlet[] findByCompanyId_PrevAndNext(
		com.liferay.portal.service.persistence.PortletPK portletPK,
		java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletException {
		return getPersistence().findByCompanyId_PrevAndNext(portletPK,
			companyId, obc);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.util.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.util.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer, begin,
			end);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static PortletPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(PortletPersistence persistence) {
		_persistence = persistence;
	}

	private static PortletUtil _getUtil() {
		if (_util == null) {
			_util = (PortletUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _UTIL = PortletUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.Portlet"));
	private static Log _log = LogFactory.getLog(PortletUtil.class);
	private static PortletUtil _util;
	private PortletPersistence _persistence;
}