/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

/**
 * <a href="RoleModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the Role_ table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Role
 * @see       com.liferay.portal.model.impl.RoleImpl
 * @see       com.liferay.portal.model.impl.RoleModelImpl
 * @generated
 */
public interface RoleModel extends BaseModel<Role> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getRoleId();

	public void setRoleId(long roleId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public String getClassName();

	public long getClassNameId();

	public void setClassNameId(long classNameId);

	public long getClassPK();

	public void setClassPK(long classPK);

	public String getName();

	public void setName(String name);

	public String getTitle();

	public String getTitle(Locale locale);

	public String getTitle(Locale locale, boolean useDefault);

	public String getTitle(String languageId);

	public String getTitle(String languageId, boolean useDefault);

	public Map<Locale, String> getTitleMap();

	public void setTitle(String title);

	public void setTitle(Locale locale, String title);

	public void setTitleMap(Map<Locale, String> titleMap);

	public String getDescription();

	public void setDescription(String description);

	public int getType();

	public void setType(int type);

	public String getSubtype();

	public void setSubtype(String subtype);

	public Role toEscapedModel();

	public boolean isNew();

	public boolean setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(Role role);

	public int hashCode();

	public String toString();

	public String toXmlString();
}