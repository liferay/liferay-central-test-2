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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="SCProductScreenshotUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SCProductScreenshotUtil {
	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot create(
		long productScreenshotId) {
		return getPersistence().create(productScreenshotId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot remove(
		long productScreenshotId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(productScreenshotId));
		}

		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot =
			getPersistence().remove(productScreenshotId);

		if (listener != null) {
			listener.onAfterRemove(scProductScreenshot);
		}

		return scProductScreenshot;
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot remove(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(scProductScreenshot);
		}

		scProductScreenshot = getPersistence().remove(scProductScreenshot);

		if (listener != null) {
			listener.onAfterRemove(scProductScreenshot);
		}

		return scProductScreenshot;
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot update(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = scProductScreenshot.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(scProductScreenshot);
			}
			else {
				listener.onBeforeUpdate(scProductScreenshot);
			}
		}

		scProductScreenshot = getPersistence().update(scProductScreenshot);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(scProductScreenshot);
			}
			else {
				listener.onAfterUpdate(scProductScreenshot);
			}
		}

		return scProductScreenshot;
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot update(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = scProductScreenshot.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(scProductScreenshot);
			}
			else {
				listener.onBeforeUpdate(scProductScreenshot);
			}
		}

		scProductScreenshot = getPersistence().update(scProductScreenshot,
				saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(scProductScreenshot);
			}
			else {
				listener.onAfterUpdate(scProductScreenshot);
			}
		}

		return scProductScreenshot;
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot findByPrimaryKey(
		long productScreenshotId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().findByPrimaryKey(productScreenshotId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot fetchByPrimaryKey(
		long productScreenshotId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(productScreenshotId);
	}

	public static java.util.List findByProductEntryId(long productEntryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByProductEntryId(productEntryId);
	}

	public static java.util.List findByProductEntryId(long productEntryId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByProductEntryId(productEntryId, begin, end);
	}

	public static java.util.List findByProductEntryId(long productEntryId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByProductEntryId(productEntryId, begin,
			end, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot findByProductEntryId_First(
		long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().findByProductEntryId_First(productEntryId, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot findByProductEntryId_Last(
		long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().findByProductEntryId_Last(productEntryId, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot[] findByProductEntryId_PrevAndNext(
		long productScreenshotId, long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().findByProductEntryId_PrevAndNext(productScreenshotId,
			productEntryId, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot findByP_P(
		long productEntryId, int priority)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().findByP_P(productEntryId, priority);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot fetchByP_P(
		long productEntryId, int priority)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByP_P(productEntryId, priority);
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

	public static void removeByProductEntryId(long productEntryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByProductEntryId(productEntryId);
	}

	public static void removeByP_P(long productEntryId, int priority)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		getPersistence().removeByP_P(productEntryId, priority);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByProductEntryId(long productEntryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByProductEntryId(productEntryId);
	}

	public static int countByP_P(long productEntryId, int priority)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByP_P(productEntryId, priority);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static SCProductScreenshotPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(SCProductScreenshotPersistence persistence) {
		_persistence = persistence;
	}

	private static SCProductScreenshotUtil _getUtil() {
		if (_util == null) {
			_util = (SCProductScreenshotUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = SCProductScreenshotUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.softwarecatalog.model.SCProductScreenshot"));
	private static Log _log = LogFactory.getLog(SCProductScreenshotUtil.class);
	private static SCProductScreenshotUtil _util;
	private SCProductScreenshotPersistence _persistence;
}