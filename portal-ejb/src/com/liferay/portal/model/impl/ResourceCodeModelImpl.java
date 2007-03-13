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
import com.liferay.portal.service.persistence.ResourceCodePK;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.sql.Types;

/**
 * <a href="ResourceCodeModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ResourceCodeModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "ResourceCode";
	public static Object[][] TABLE_COLUMNS = {
			{ "companyId", new Integer(Types.VARCHAR) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "scope", new Integer(Types.VARCHAR) },
			{ "code", new Integer(Types.BIGINT) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.ResourceCode"), XSS_ALLOW);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.ResourceCode.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_NAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.ResourceCode.name"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_SCOPE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.ResourceCode.scope"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ResourceCodeModel"));

	public ResourceCodeModelImpl() {
	}

	public ResourceCodePK getPrimaryKey() {
		return new ResourceCodePK(_companyId, _name, _scope);
	}

	public void setPrimaryKey(ResourceCodePK pk) {
		setCompanyId(pk.companyId);
		setName(pk.name);
		setScope(pk.scope);
	}

	public String getCompanyId() {
		return GetterUtil.getString(_companyId);
	}

	public void setCompanyId(String companyId) {
		if (((companyId == null) && (_companyId != null)) ||
				((companyId != null) && (_companyId == null)) ||
				((companyId != null) && (_companyId != null) &&
				!companyId.equals(_companyId))) {
			if (!XSS_ALLOW_COMPANYID) {
				companyId = XSSUtil.strip(companyId);
			}

			_companyId = companyId;
		}
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		if (((name == null) && (_name != null)) ||
				((name != null) && (_name == null)) ||
				((name != null) && (_name != null) && !name.equals(_name))) {
			if (!XSS_ALLOW_NAME) {
				name = XSSUtil.strip(name);
			}

			_name = name;
		}
	}

	public String getScope() {
		return GetterUtil.getString(_scope);
	}

	public void setScope(String scope) {
		if (((scope == null) && (_scope != null)) ||
				((scope != null) && (_scope == null)) ||
				((scope != null) && (_scope != null) && !scope.equals(_scope))) {
			if (!XSS_ALLOW_SCOPE) {
				scope = XSSUtil.strip(scope);
			}

			_scope = scope;
		}
	}

	public long getCode() {
		return _code;
	}

	public void setCode(long code) {
		if (code != _code) {
			_code = code;
		}
	}

	public Object clone() {
		ResourceCodeImpl clone = new ResourceCodeImpl();
		clone.setCompanyId(getCompanyId());
		clone.setName(getName());
		clone.setScope(getScope());
		clone.setCode(getCode());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		ResourceCodeImpl resourceCode = (ResourceCodeImpl)obj;
		ResourceCodePK pk = resourceCode.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ResourceCodeImpl resourceCode = null;

		try {
			resourceCode = (ResourceCodeImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		ResourceCodePK pk = resourceCode.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	private String _companyId;
	private String _name;
	private String _scope;
	private long _code;
}