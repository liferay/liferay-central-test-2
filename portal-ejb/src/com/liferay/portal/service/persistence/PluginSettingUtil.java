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
 * <a href="PluginSettingUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PluginSettingUtil {
	public static com.liferay.portal.model.PluginSetting create(
		long pluginSettingId) {
		return getPersistence().create(pluginSettingId);
	}

	public static com.liferay.portal.model.PluginSetting remove(
		long pluginSettingId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPluginSettingException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(pluginSettingId));
		}

		com.liferay.portal.model.PluginSetting pluginSetting = getPersistence()
																   .remove(pluginSettingId);

		if (listener != null) {
			listener.onAfterRemove(pluginSetting);
		}

		return pluginSetting;
	}

	public static com.liferay.portal.model.PluginSetting remove(
		com.liferay.portal.model.PluginSetting pluginSetting)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(pluginSetting);
		}

		pluginSetting = getPersistence().remove(pluginSetting);

		if (listener != null) {
			listener.onAfterRemove(pluginSetting);
		}

		return pluginSetting;
	}

	public static com.liferay.portal.model.PluginSetting update(
		com.liferay.portal.model.PluginSetting pluginSetting)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = pluginSetting.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(pluginSetting);
			}
			else {
				listener.onBeforeUpdate(pluginSetting);
			}
		}

		pluginSetting = getPersistence().update(pluginSetting);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(pluginSetting);
			}
			else {
				listener.onAfterUpdate(pluginSetting);
			}
		}

		return pluginSetting;
	}

	public static com.liferay.portal.model.PluginSetting update(
		com.liferay.portal.model.PluginSetting pluginSetting,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = pluginSetting.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(pluginSetting);
			}
			else {
				listener.onBeforeUpdate(pluginSetting);
			}
		}

		pluginSetting = getPersistence().update(pluginSetting, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(pluginSetting);
			}
			else {
				listener.onAfterUpdate(pluginSetting);
			}
		}

		return pluginSetting;
	}

	public static com.liferay.portal.model.PluginSetting findByPrimaryKey(
		long pluginSettingId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPluginSettingException {
		return getPersistence().findByPrimaryKey(pluginSettingId);
	}

	public static com.liferay.portal.model.PluginSetting fetchByPrimaryKey(
		long pluginSettingId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(pluginSettingId);
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

	public static com.liferay.portal.model.PluginSetting findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPluginSettingException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.PluginSetting findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPluginSettingException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.PluginSetting[] findByCompanyId_PrevAndNext(
		long pluginSettingId, java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPluginSettingException {
		return getPersistence().findByCompanyId_PrevAndNext(pluginSettingId,
			companyId, obc);
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

	public static PluginSettingPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(PluginSettingPersistence persistence) {
		_persistence = persistence;
	}

	private static PluginSettingUtil _getUtil() {
		if (_util == null) {
			_util = (PluginSettingUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = PluginSettingUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.PluginSetting"));
	private static Log _log = LogFactory.getLog(PluginSettingUtil.class);
	private static PluginSettingUtil _util;
	private PluginSettingPersistence _persistence;
}