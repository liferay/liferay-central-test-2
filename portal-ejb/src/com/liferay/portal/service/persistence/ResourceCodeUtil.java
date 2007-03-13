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
 * <a href="ResourceCodeUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ResourceCodeUtil {
	public static com.liferay.portal.model.ResourceCode create(long codeId) {
		return getPersistence().create(codeId);
	}

	public static com.liferay.portal.model.ResourceCode remove(long codeId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchResourceCodeException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(codeId));
		}

		com.liferay.portal.model.ResourceCode resourceCode = getPersistence()
																 .remove(codeId);

		if (listener != null) {
			listener.onAfterRemove(resourceCode);
		}

		return resourceCode;
	}

	public static com.liferay.portal.model.ResourceCode remove(
		com.liferay.portal.model.ResourceCode resourceCode)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(resourceCode);
		}

		resourceCode = getPersistence().remove(resourceCode);

		if (listener != null) {
			listener.onAfterRemove(resourceCode);
		}

		return resourceCode;
	}

	public static com.liferay.portal.model.ResourceCode update(
		com.liferay.portal.model.ResourceCode resourceCode)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = resourceCode.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(resourceCode);
			}
			else {
				listener.onBeforeUpdate(resourceCode);
			}
		}

		resourceCode = getPersistence().update(resourceCode);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(resourceCode);
			}
			else {
				listener.onAfterUpdate(resourceCode);
			}
		}

		return resourceCode;
	}

	public static com.liferay.portal.model.ResourceCode update(
		com.liferay.portal.model.ResourceCode resourceCode, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = resourceCode.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(resourceCode);
			}
			else {
				listener.onBeforeUpdate(resourceCode);
			}
		}

		resourceCode = getPersistence().update(resourceCode, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(resourceCode);
			}
			else {
				listener.onAfterUpdate(resourceCode);
			}
		}

		return resourceCode;
	}

	public static com.liferay.portal.model.ResourceCode findByPrimaryKey(
		long codeId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchResourceCodeException {
		return getPersistence().findByPrimaryKey(codeId);
	}

	public static com.liferay.portal.model.ResourceCode fetchByPrimaryKey(
		long codeId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(codeId);
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

	public static com.liferay.portal.model.ResourceCode findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchResourceCodeException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.ResourceCode findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchResourceCodeException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.ResourceCode[] findByCompanyId_PrevAndNext(
		long codeId, java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchResourceCodeException {
		return getPersistence().findByCompanyId_PrevAndNext(codeId, companyId,
			obc);
	}

	public static java.util.List findByName(java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByName(name);
	}

	public static java.util.List findByName(java.lang.String name, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByName(name, begin, end);
	}

	public static java.util.List findByName(java.lang.String name, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByName(name, begin, end, obc);
	}

	public static com.liferay.portal.model.ResourceCode findByName_First(
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchResourceCodeException {
		return getPersistence().findByName_First(name, obc);
	}

	public static com.liferay.portal.model.ResourceCode findByName_Last(
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchResourceCodeException {
		return getPersistence().findByName_Last(name, obc);
	}

	public static com.liferay.portal.model.ResourceCode[] findByName_PrevAndNext(
		long codeId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchResourceCodeException {
		return getPersistence().findByName_PrevAndNext(codeId, name, obc);
	}

	public static com.liferay.portal.model.ResourceCode findByC_N_S(
		java.lang.String companyId, java.lang.String name,
		java.lang.String scope)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchResourceCodeException {
		return getPersistence().findByC_N_S(companyId, name, scope);
	}

	public static com.liferay.portal.model.ResourceCode fetchByC_N_S(
		java.lang.String companyId, java.lang.String name,
		java.lang.String scope) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_N_S(companyId, name, scope);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
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

	public static void removeByName(java.lang.String name)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByName(name);
	}

	public static void removeByC_N_S(java.lang.String companyId,
		java.lang.String name, java.lang.String scope)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchResourceCodeException {
		getPersistence().removeByC_N_S(companyId, name, scope);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByName(java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByName(name);
	}

	public static int countByC_N_S(java.lang.String companyId,
		java.lang.String name, java.lang.String scope)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_N_S(companyId, name, scope);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static ResourceCodePersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(ResourceCodePersistence persistence) {
		_persistence = persistence;
	}

	private static ResourceCodeUtil _getUtil() {
		if (_util == null) {
			_util = (ResourceCodeUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = ResourceCodeUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.ResourceCode"));
	private static Log _log = LogFactory.getLog(ResourceCodeUtil.class);
	private static ResourceCodeUtil _util;
	private ResourceCodePersistence _persistence;
}