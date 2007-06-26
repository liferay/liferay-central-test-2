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

package com.liferay.portal.model.impl;

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.sql.Types;

/**
 * <a href="ResourceModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>Resource_</code> table in the
 * database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.Resource
 * @see com.liferay.portal.service.model.ResourceModel
 * @see com.liferay.portal.service.model.impl.ResourceImpl
 *
 */
public class ResourceModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "Resource_";
	public static Object[][] TABLE_COLUMNS = {
			{ "resourceId", new Integer(Types.BIGINT) },
			{ "codeId", new Integer(Types.BIGINT) },
			{ "primKey", new Integer(Types.VARCHAR) }
		};
	public static String TABLE_SQL_CREATE = "create table Resource_ (resourceId LONG not null primary key,codeId LONG,primKey VARCHAR(300) null)";
	public static String TABLE_SQL_DROP = "drop table Resource_";
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Resource"), XSS_ALLOW);
	public static boolean XSS_ALLOW_PRIMKEY = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Resource.primKey"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ResourceModel"));

	public ResourceModelImpl() {
	}

	public long getPrimaryKey() {
		return _resourceId;
	}

	public void setPrimaryKey(long pk) {
		setResourceId(pk);
	}

	public long getResourceId() {
		return _resourceId;
	}

	public void setResourceId(long resourceId) {
		if (resourceId != _resourceId) {
			_resourceId = resourceId;
		}
	}

	public long getCodeId() {
		return _codeId;
	}

	public void setCodeId(long codeId) {
		if (codeId != _codeId) {
			_codeId = codeId;
		}
	}

	public String getPrimKey() {
		return GetterUtil.getString(_primKey);
	}

	public void setPrimKey(String primKey) {
		if (((primKey == null) && (_primKey != null)) ||
				((primKey != null) && (_primKey == null)) ||
				((primKey != null) && (_primKey != null) &&
				!primKey.equals(_primKey))) {
			if (!XSS_ALLOW_PRIMKEY) {
				primKey = XSSUtil.strip(primKey);
			}

			_primKey = primKey;
		}
	}

	public Object clone() {
		ResourceImpl clone = new ResourceImpl();
		clone.setResourceId(getResourceId());
		clone.setCodeId(getCodeId());
		clone.setPrimKey(getPrimKey());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		ResourceImpl resource = (ResourceImpl)obj;
		long pk = resource.getPrimaryKey();

		if (getPrimaryKey() < pk) {
			return -1;
		}
		else if (getPrimaryKey() > pk) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ResourceImpl resource = null;

		try {
			resource = (ResourceImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = resource.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	private long _resourceId;
	private long _codeId;
	private String _primKey;
}