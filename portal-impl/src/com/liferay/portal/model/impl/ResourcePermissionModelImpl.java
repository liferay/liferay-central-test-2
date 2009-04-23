/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.ResourcePermissionSoap;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

import java.io.Serializable;

import java.lang.StringBuilder;
import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ResourcePermissionModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>ResourcePermission</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.model.ResourcePermission
 * @see com.liferay.portal.model.ResourcePermissionModel
 * @see com.liferay.portal.model.impl.ResourcePermissionImpl
 *
 */
public class ResourcePermissionModelImpl extends BaseModelImpl<ResourcePermission> {
	public static final String TABLE_NAME = "ResourcePermission";
	public static final Object[][] TABLE_COLUMNS = {
			{ "resourcePermissionId", new Integer(Types.BIGINT) },
			

			{ "resourceId", new Integer(Types.BIGINT) },
			

			{ "roleId", new Integer(Types.BIGINT) },
			

			{ "actionIds", new Integer(Types.BIGINT) }
		};
	public static final String TABLE_SQL_CREATE = "create table ResourcePermission (resourcePermissionId LONG not null primary key,resourceId LONG,roleId LONG,actionIds LONG)";
	public static final String TABLE_SQL_DROP = "drop table ResourcePermission";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.ResourcePermission"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.ResourcePermission"),
			true);

	public static ResourcePermission toModel(ResourcePermissionSoap soapModel) {
		ResourcePermission model = new ResourcePermissionImpl();

		model.setResourcePermissionId(soapModel.getResourcePermissionId());
		model.setResourceId(soapModel.getResourceId());
		model.setRoleId(soapModel.getRoleId());
		model.setActionIds(soapModel.getActionIds());

		return model;
	}

	public static List<ResourcePermission> toModels(
		ResourcePermissionSoap[] soapModels) {
		List<ResourcePermission> models = new ArrayList<ResourcePermission>(soapModels.length);

		for (ResourcePermissionSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ResourcePermission"));

	public ResourcePermissionModelImpl() {
	}

	public long getPrimaryKey() {
		return _resourcePermissionId;
	}

	public void setPrimaryKey(long pk) {
		setResourcePermissionId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_resourcePermissionId);
	}

	public long getResourcePermissionId() {
		return _resourcePermissionId;
	}

	public void setResourcePermissionId(long resourcePermissionId) {
		_resourcePermissionId = resourcePermissionId;
	}

	public long getResourceId() {
		return _resourceId;
	}

	public void setResourceId(long resourceId) {
		_resourceId = resourceId;

		if (!_setOriginalResourceId) {
			_setOriginalResourceId = true;

			_originalResourceId = resourceId;
		}
	}

	public long getOriginalResourceId() {
		return _originalResourceId;
	}

	public long getRoleId() {
		return _roleId;
	}

	public void setRoleId(long roleId) {
		_roleId = roleId;

		if (!_setOriginalRoleId) {
			_setOriginalRoleId = true;

			_originalRoleId = roleId;
		}
	}

	public long getOriginalRoleId() {
		return _originalRoleId;
	}

	public long getActionIds() {
		return _actionIds;
	}

	public void setActionIds(long actionIds) {
		_actionIds = actionIds;
	}

	public ResourcePermission toEscapedModel() {
		if (isEscapedModel()) {
			return (ResourcePermission)this;
		}
		else {
			ResourcePermission model = new ResourcePermissionImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setResourcePermissionId(getResourcePermissionId());
			model.setResourceId(getResourceId());
			model.setRoleId(getRoleId());
			model.setActionIds(getActionIds());

			model = (ResourcePermission)Proxy.newProxyInstance(ResourcePermission.class.getClassLoader(),
					new Class[] { ResourcePermission.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(ResourcePermission.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		ResourcePermissionImpl clone = new ResourcePermissionImpl();

		clone.setResourcePermissionId(getResourcePermissionId());
		clone.setResourceId(getResourceId());
		clone.setRoleId(getRoleId());
		clone.setActionIds(getActionIds());

		return clone;
	}

	public int compareTo(ResourcePermission resourcePermission) {
		long pk = resourcePermission.getPrimaryKey();

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

		ResourcePermission resourcePermission = null;

		try {
			resourcePermission = (ResourcePermission)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = resourcePermission.getPrimaryKey();

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

	public String toHtmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<table class=\"lfr-table\">\n");

		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>resourcePermissionId</b></td><td>" +
			getResourcePermissionId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>resourceId</b></td><td>" +
			getResourceId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>roleId</b></td><td>" +
			getRoleId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>actionIds</b></td><td>" +
			getActionIds() + "</td></tr>\n");

		sb.append("</table>");

		return sb.toString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("com.liferay.portal.model.ResourcePermission (");

		sb.append("resourcePermissionId: " + getResourcePermissionId() + ", ");
		sb.append("resourceId: " + getResourceId() + ", ");
		sb.append("roleId: " + getRoleId() + ", ");
		sb.append("actionIds: " + getActionIds() + ", ");

		sb.append(")");

		return sb.toString();
	}

	private long _resourcePermissionId;
	private long _resourceId;
	private long _originalResourceId;
	private boolean _setOriginalResourceId;
	private long _roleId;
	private long _originalRoleId;
	private boolean _setOriginalRoleId;
	private long _actionIds;
	private transient ExpandoBridge _expandoBridge;
}