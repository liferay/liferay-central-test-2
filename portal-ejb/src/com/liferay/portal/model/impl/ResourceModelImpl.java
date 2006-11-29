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

package com.liferay.portal.model.impl;

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

/**
 * <a href="ResourceModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ResourceModelImpl extends BaseModelImpl {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Resource"), XSS_ALLOW);
	public static boolean XSS_ALLOW_RESOURCEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Resource.resourceId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Resource.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_NAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Resource.name"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TYPEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Resource.typeId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_SCOPE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Resource.scope"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_PRIMKEY = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Resource.primKey"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ResourceModel"));

	public ResourceModelImpl() {
	}

	public String getPrimaryKey() {
		return _resourceId;
	}

	public void setPrimaryKey(String pk) {
		setResourceId(pk);
	}

	public String getResourceId() {
		return GetterUtil.getString(_resourceId);
	}

	public void setResourceId(String resourceId) {
		if (((resourceId == null) && (_resourceId != null)) ||
				((resourceId != null) && (_resourceId == null)) ||
				((resourceId != null) && (_resourceId != null) &&
				!resourceId.equals(_resourceId))) {
			if (!XSS_ALLOW_RESOURCEID) {
				resourceId = XSSUtil.strip(resourceId);
			}

			_resourceId = resourceId;
		}
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

	public String getTypeId() {
		return GetterUtil.getString(_typeId);
	}

	public void setTypeId(String typeId) {
		if (((typeId == null) && (_typeId != null)) ||
				((typeId != null) && (_typeId == null)) ||
				((typeId != null) && (_typeId != null) &&
				!typeId.equals(_typeId))) {
			if (!XSS_ALLOW_TYPEID) {
				typeId = XSSUtil.strip(typeId);
			}

			_typeId = typeId;
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
		clone.setCompanyId(getCompanyId());
		clone.setName(getName());
		clone.setTypeId(getTypeId());
		clone.setScope(getScope());
		clone.setPrimKey(getPrimKey());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		ResourceImpl resource = (ResourceImpl)obj;
		String pk = resource.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
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

		String pk = resource.getPrimaryKey();

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

	private String _resourceId;
	private String _companyId;
	private String _name;
	private String _typeId;
	private String _scope;
	private String _primKey;
}