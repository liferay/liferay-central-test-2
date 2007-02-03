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

package com.liferay.portlet.softwarerepository.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="SRProductVersionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SRProductVersionUtil {
	public static com.liferay.portlet.softwarerepository.model.SRProductVersion create(
		long productVersionId) {
		return getPersistence().create(productVersionId);
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductVersion remove(
		long productVersionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductVersionException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(productVersionId));
		}

		com.liferay.portlet.softwarerepository.model.SRProductVersion srProductVersion =
			getPersistence().remove(productVersionId);

		if (listener != null) {
			listener.onAfterRemove(srProductVersion);
		}

		return srProductVersion;
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductVersion remove(
		com.liferay.portlet.softwarerepository.model.SRProductVersion srProductVersion)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(srProductVersion);
		}

		srProductVersion = getPersistence().remove(srProductVersion);

		if (listener != null) {
			listener.onAfterRemove(srProductVersion);
		}

		return srProductVersion;
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductVersion update(
		com.liferay.portlet.softwarerepository.model.SRProductVersion srProductVersion)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = srProductVersion.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(srProductVersion);
			}
			else {
				listener.onBeforeUpdate(srProductVersion);
			}
		}

		srProductVersion = getPersistence().update(srProductVersion);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(srProductVersion);
			}
			else {
				listener.onAfterUpdate(srProductVersion);
			}
		}

		return srProductVersion;
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductVersion update(
		com.liferay.portlet.softwarerepository.model.SRProductVersion srProductVersion,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = srProductVersion.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(srProductVersion);
			}
			else {
				listener.onBeforeUpdate(srProductVersion);
			}
		}

		srProductVersion = getPersistence().update(srProductVersion,
				saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(srProductVersion);
			}
			else {
				listener.onAfterUpdate(srProductVersion);
			}
		}

		return srProductVersion;
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductVersion findByPrimaryKey(
		long productVersionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductVersionException {
		return getPersistence().findByPrimaryKey(productVersionId);
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductVersion fetchByPrimaryKey(
		long productVersionId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(productVersionId);
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

	public static com.liferay.portlet.softwarerepository.model.SRProductVersion findByProductEntryId_First(
		long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductVersionException {
		return getPersistence().findByProductEntryId_First(productEntryId, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductVersion findByProductEntryId_Last(
		long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductVersionException {
		return getPersistence().findByProductEntryId_Last(productEntryId, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductVersion[] findByProductEntryId_PrevAndNext(
		long productVersionId, long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductVersionException {
		return getPersistence().findByProductEntryId_PrevAndNext(productVersionId,
			productEntryId, obc);
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

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByProductEntryId(long productEntryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByProductEntryId(productEntryId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List getSRFrameworkVersions(long pk)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductVersionException {
		return getPersistence().getSRFrameworkVersions(pk);
	}

	public static java.util.List getSRFrameworkVersions(long pk, int begin,
		int end)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductVersionException {
		return getPersistence().getSRFrameworkVersions(pk, begin, end);
	}

	public static java.util.List getSRFrameworkVersions(long pk, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductVersionException {
		return getPersistence().getSRFrameworkVersions(pk, begin, end, obc);
	}

	public static int getSRFrameworkVersionsSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getSRFrameworkVersionsSize(pk);
	}

	public static boolean containsSRFrameworkVersion(long pk,
		long srFrameworkVersionPK) throws com.liferay.portal.SystemException {
		return getPersistence().containsSRFrameworkVersion(pk,
			srFrameworkVersionPK);
	}

	public static boolean containsSRFrameworkVersions(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsSRFrameworkVersions(pk);
	}

	public static void addSRFrameworkVersion(long pk, long srFrameworkVersionPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		getPersistence().addSRFrameworkVersion(pk, srFrameworkVersionPK);
	}

	public static void addSRFrameworkVersion(long pk,
		com.liferay.portlet.softwarerepository.model.SRFrameworkVersion srFrameworkVersion)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		getPersistence().addSRFrameworkVersion(pk, srFrameworkVersion);
	}

	public static void addSRFrameworkVersions(long pk,
		long[] srFrameworkVersionPKs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		getPersistence().addSRFrameworkVersions(pk, srFrameworkVersionPKs);
	}

	public static void addSRFrameworkVersions(long pk,
		java.util.List srFrameworkVersions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		getPersistence().addSRFrameworkVersions(pk, srFrameworkVersions);
	}

	public static void clearSRFrameworkVersions(long pk)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductVersionException {
		getPersistence().clearSRFrameworkVersions(pk);
	}

	public static void removeSRFrameworkVersion(long pk,
		long srFrameworkVersionPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		getPersistence().removeSRFrameworkVersion(pk, srFrameworkVersionPK);
	}

	public static void removeSRFrameworkVersion(long pk,
		com.liferay.portlet.softwarerepository.model.SRFrameworkVersion srFrameworkVersion)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		getPersistence().removeSRFrameworkVersion(pk, srFrameworkVersion);
	}

	public static void removeSRFrameworkVersions(long pk,
		long[] srFrameworkVersionPKs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		getPersistence().removeSRFrameworkVersions(pk, srFrameworkVersionPKs);
	}

	public static void removeSRFrameworkVersions(long pk,
		java.util.List srFrameworkVersions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		getPersistence().removeSRFrameworkVersions(pk, srFrameworkVersions);
	}

	public static void setSRFrameworkVersions(long pk,
		long[] srFrameworkVersionPKs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		getPersistence().setSRFrameworkVersions(pk, srFrameworkVersionPKs);
	}

	public static void setSRFrameworkVersions(long pk,
		java.util.List srFrameworkVersions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductVersionException, 
			com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException {
		getPersistence().setSRFrameworkVersions(pk, srFrameworkVersions);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static SRProductVersionPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(SRProductVersionPersistence persistence) {
		_persistence = persistence;
	}

	private static SRProductVersionUtil _getUtil() {
		if (_util == null) {
			_util = (SRProductVersionUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = SRProductVersionUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.softwarerepository.model.SRProductVersion"));
	private static Log _log = LogFactory.getLog(SRProductVersionUtil.class);
	private static SRProductVersionUtil _util;
	private SRProductVersionPersistence _persistence;
}