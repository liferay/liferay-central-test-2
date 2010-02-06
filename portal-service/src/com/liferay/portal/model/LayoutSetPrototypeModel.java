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
 * <a href="LayoutSetPrototypeModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the LayoutSetPrototype table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSetPrototype
 * @see       com.liferay.portal.model.impl.LayoutSetPrototypeImpl
 * @see       com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl
 * @generated
 */
public interface LayoutSetPrototypeModel extends BaseModel<LayoutSetPrototype> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getLayoutSetPrototypeId();

	public void setLayoutSetPrototypeId(long layoutSetPrototypeId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public String getName();

	public String getName(Locale locale);

	public String getName(Locale locale, boolean useDefault);

	public String getName(String languageId);

	public String getName(String languageId, boolean useDefault);

	public Map<Locale, String> getNameMap();

	public void setName(String name);

	public void setName(Locale locale, String name);

	public void setNameMap(Map<Locale, String> nameMap);

	public String getDescription();

	public void setDescription(String description);

	public String getSettings();

	public void setSettings(String settings);

	public boolean getActive();

	public boolean isActive();

	public void setActive(boolean active);

	public LayoutSetPrototype toEscapedModel();

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

	public int compareTo(LayoutSetPrototype layoutSetPrototype);

	public int hashCode();

	public String toString();

	public String toXmlString();
}