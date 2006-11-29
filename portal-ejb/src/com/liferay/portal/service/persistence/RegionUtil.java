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
 * <a href="RegionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class RegionUtil {
	public static com.liferay.portal.model.Region create(
		java.lang.String regionId) {
		return getPersistence().create(regionId);
	}

	public static com.liferay.portal.model.Region remove(
		java.lang.String regionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchRegionException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(regionId));
		}

		com.liferay.portal.model.Region region = getPersistence().remove(regionId);

		if (listener != null) {
			listener.onAfterRemove(region);
		}

		return region;
	}

	public static com.liferay.portal.model.Region remove(
		com.liferay.portal.model.Region region)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(region);
		}

		region = getPersistence().remove(region);

		if (listener != null) {
			listener.onAfterRemove(region);
		}

		return region;
	}

	public static com.liferay.portal.model.Region update(
		com.liferay.portal.model.Region region)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = region.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(region);
			}
			else {
				listener.onBeforeUpdate(region);
			}
		}

		region = getPersistence().update(region);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(region);
			}
			else {
				listener.onAfterUpdate(region);
			}
		}

		return region;
	}

	public static com.liferay.portal.model.Region update(
		com.liferay.portal.model.Region region, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = region.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(region);
			}
			else {
				listener.onBeforeUpdate(region);
			}
		}

		region = getPersistence().update(region, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(region);
			}
			else {
				listener.onAfterUpdate(region);
			}
		}

		return region;
	}

	public static com.liferay.portal.model.Region findByPrimaryKey(
		java.lang.String regionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchRegionException {
		return getPersistence().findByPrimaryKey(regionId);
	}

	public static com.liferay.portal.model.Region fetchByPrimaryKey(
		java.lang.String regionId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(regionId);
	}

	public static java.util.List findByCountryId(java.lang.String countryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCountryId(countryId);
	}

	public static java.util.List findByCountryId(java.lang.String countryId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByCountryId(countryId, begin, end);
	}

	public static java.util.List findByCountryId(java.lang.String countryId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCountryId(countryId, begin, end, obc);
	}

	public static com.liferay.portal.model.Region findByCountryId_First(
		java.lang.String countryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchRegionException {
		return getPersistence().findByCountryId_First(countryId, obc);
	}

	public static com.liferay.portal.model.Region findByCountryId_Last(
		java.lang.String countryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchRegionException {
		return getPersistence().findByCountryId_Last(countryId, obc);
	}

	public static com.liferay.portal.model.Region[] findByCountryId_PrevAndNext(
		java.lang.String regionId, java.lang.String countryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchRegionException {
		return getPersistence().findByCountryId_PrevAndNext(regionId,
			countryId, obc);
	}

	public static java.util.List findByActive(boolean active)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByActive(active);
	}

	public static java.util.List findByActive(boolean active, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByActive(active, begin, end);
	}

	public static java.util.List findByActive(boolean active, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByActive(active, begin, end, obc);
	}

	public static com.liferay.portal.model.Region findByActive_First(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchRegionException {
		return getPersistence().findByActive_First(active, obc);
	}

	public static com.liferay.portal.model.Region findByActive_Last(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchRegionException {
		return getPersistence().findByActive_Last(active, obc);
	}

	public static com.liferay.portal.model.Region[] findByActive_PrevAndNext(
		java.lang.String regionId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchRegionException {
		return getPersistence().findByActive_PrevAndNext(regionId, active, obc);
	}

	public static java.util.List findByC_A(java.lang.String countryId,
		boolean active) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A(countryId, active);
	}

	public static java.util.List findByC_A(java.lang.String countryId,
		boolean active, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A(countryId, active, begin, end);
	}

	public static java.util.List findByC_A(java.lang.String countryId,
		boolean active, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A(countryId, active, begin, end, obc);
	}

	public static com.liferay.portal.model.Region findByC_A_First(
		java.lang.String countryId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchRegionException {
		return getPersistence().findByC_A_First(countryId, active, obc);
	}

	public static com.liferay.portal.model.Region findByC_A_Last(
		java.lang.String countryId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchRegionException {
		return getPersistence().findByC_A_Last(countryId, active, obc);
	}

	public static com.liferay.portal.model.Region[] findByC_A_PrevAndNext(
		java.lang.String regionId, java.lang.String countryId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchRegionException {
		return getPersistence().findByC_A_PrevAndNext(regionId, countryId,
			active, obc);
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

	public static void removeByCountryId(java.lang.String countryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCountryId(countryId);
	}

	public static void removeByActive(boolean active)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByActive(active);
	}

	public static void removeByC_A(java.lang.String countryId, boolean active)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_A(countryId, active);
	}

	public static int countByCountryId(java.lang.String countryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCountryId(countryId);
	}

	public static int countByActive(boolean active)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByActive(active);
	}

	public static int countByC_A(java.lang.String countryId, boolean active)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_A(countryId, active);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static RegionPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(RegionPersistence persistence) {
		_persistence = persistence;
	}

	private static RegionUtil _getUtil() {
		if (_util == null) {
			_util = (RegionUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = RegionUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.Region"));
	private static Log _log = LogFactory.getLog(RegionUtil.class);
	private static RegionUtil _util;
	private RegionPersistence _persistence;
}