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
 * <a href="PasswordPolicyUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PasswordPolicyUtil {
	public static com.liferay.portal.model.PasswordPolicy create(
		long passwordPolicyId) {
		return getPersistence().create(passwordPolicyId);
	}

	public static com.liferay.portal.model.PasswordPolicy remove(
		long passwordPolicyId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPasswordPolicyException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(passwordPolicyId));
		}

		com.liferay.portal.model.PasswordPolicy passwordPolicy = getPersistence()
																	 .remove(passwordPolicyId);

		if (listener != null) {
			listener.onAfterRemove(passwordPolicy);
		}

		return passwordPolicy;
	}

	public static com.liferay.portal.model.PasswordPolicy remove(
		com.liferay.portal.model.PasswordPolicy passwordPolicy)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(passwordPolicy);
		}

		passwordPolicy = getPersistence().remove(passwordPolicy);

		if (listener != null) {
			listener.onAfterRemove(passwordPolicy);
		}

		return passwordPolicy;
	}

	public static com.liferay.portal.model.PasswordPolicy update(
		com.liferay.portal.model.PasswordPolicy passwordPolicy)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = passwordPolicy.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(passwordPolicy);
			}
			else {
				listener.onBeforeUpdate(passwordPolicy);
			}
		}

		passwordPolicy = getPersistence().update(passwordPolicy);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(passwordPolicy);
			}
			else {
				listener.onAfterUpdate(passwordPolicy);
			}
		}

		return passwordPolicy;
	}

	public static com.liferay.portal.model.PasswordPolicy update(
		com.liferay.portal.model.PasswordPolicy passwordPolicy,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = passwordPolicy.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(passwordPolicy);
			}
			else {
				listener.onBeforeUpdate(passwordPolicy);
			}
		}

		passwordPolicy = getPersistence().update(passwordPolicy, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(passwordPolicy);
			}
			else {
				listener.onAfterUpdate(passwordPolicy);
			}
		}

		return passwordPolicy;
	}

	public static com.liferay.portal.model.PasswordPolicy findByPrimaryKey(
		long passwordPolicyId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence().findByPrimaryKey(passwordPolicyId);
	}

	public static com.liferay.portal.model.PasswordPolicy fetchByPrimaryKey(
		long passwordPolicyId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(passwordPolicyId);
	}

	public static com.liferay.portal.model.PasswordPolicy findByC_DP(
		long companyId, boolean defaultPolicy)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence().findByC_DP(companyId, defaultPolicy);
	}

	public static com.liferay.portal.model.PasswordPolicy fetchByC_DP(
		long companyId, boolean defaultPolicy)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_DP(companyId, defaultPolicy);
	}

	public static com.liferay.portal.model.PasswordPolicy findByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence().findByC_N(companyId, name);
	}

	public static com.liferay.portal.model.PasswordPolicy fetchByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_N(companyId, name);
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

	public static void removeByC_DP(long companyId, boolean defaultPolicy)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPasswordPolicyException {
		getPersistence().removeByC_DP(companyId, defaultPolicy);
	}

	public static void removeByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPasswordPolicyException {
		getPersistence().removeByC_N(companyId, name);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByC_DP(long companyId, boolean defaultPolicy)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_DP(companyId, defaultPolicy);
	}

	public static int countByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_N(companyId, name);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static PasswordPolicyPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(PasswordPolicyPersistence persistence) {
		_persistence = persistence;
	}

	private static PasswordPolicyUtil _getUtil() {
		if (_util == null) {
			_util = (PasswordPolicyUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = PasswordPolicyUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.PasswordPolicy"));
	private static Log _log = LogFactory.getLog(PasswordPolicyUtil.class);
	private static PasswordPolicyUtil _util;
	private PasswordPolicyPersistence _persistence;
}