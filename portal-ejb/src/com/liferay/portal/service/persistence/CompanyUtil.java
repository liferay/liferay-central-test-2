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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="CompanyUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class CompanyUtil {
	public static com.liferay.portal.model.Company create(
		java.lang.String companyId) {
		return getPersistence().create(companyId);
	}

	public static com.liferay.portal.model.Company remove(
		java.lang.String companyId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchCompanyException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(companyId));
		}

		com.liferay.portal.model.Company company = getPersistence().remove(companyId);

		if (listener != null) {
			listener.onAfterRemove(company);
		}

		return company;
	}

	public static com.liferay.portal.model.Company remove(
		com.liferay.portal.model.Company company)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(company);
		}

		company = getPersistence().remove(company);

		if (listener != null) {
			listener.onAfterRemove(company);
		}

		return company;
	}

	public static com.liferay.portal.model.Company update(
		com.liferay.portal.model.Company company)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = company.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(company);
			}
			else {
				listener.onBeforeUpdate(company);
			}
		}

		company = getPersistence().update(company);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(company);
			}
			else {
				listener.onAfterUpdate(company);
			}
		}

		return company;
	}

	public static com.liferay.portal.model.Company update(
		com.liferay.portal.model.Company company, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = company.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(company);
			}
			else {
				listener.onBeforeUpdate(company);
			}
		}

		company = getPersistence().update(company, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(company);
			}
			else {
				listener.onAfterUpdate(company);
			}
		}

		return company;
	}

	public static com.liferay.portal.model.Company findByPrimaryKey(
		java.lang.String companyId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchCompanyException {
		return getPersistence().findByPrimaryKey(companyId);
	}

	public static com.liferay.portal.model.Company fetchByPrimaryKey(
		java.lang.String companyId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(companyId);
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

	public static void initDao() {
		getPersistence().initDao();
	}

	public static CompanyPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(CompanyPersistence persistence) {
		_persistence = persistence;
	}

	private static CompanyUtil _getUtil() {
		if (_util == null) {
			_util = (CompanyUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = CompanyUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.Company"));
	private static Log _log = LogFactory.getLog(CompanyUtil.class);
	private static CompanyUtil _util;
	private CompanyPersistence _persistence;
}