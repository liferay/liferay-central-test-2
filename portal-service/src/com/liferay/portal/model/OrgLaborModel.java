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

/**
 * <a href="OrgLaborModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the OrgLabor table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrgLabor
 * @see       com.liferay.portal.model.impl.OrgLaborImpl
 * @see       com.liferay.portal.model.impl.OrgLaborModelImpl
 * @generated
 */
public interface OrgLaborModel extends BaseModel<OrgLabor> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getOrgLaborId();

	public void setOrgLaborId(long orgLaborId);

	public long getOrganizationId();

	public void setOrganizationId(long organizationId);

	public int getTypeId();

	public void setTypeId(int typeId);

	public int getSunOpen();

	public void setSunOpen(int sunOpen);

	public int getSunClose();

	public void setSunClose(int sunClose);

	public int getMonOpen();

	public void setMonOpen(int monOpen);

	public int getMonClose();

	public void setMonClose(int monClose);

	public int getTueOpen();

	public void setTueOpen(int tueOpen);

	public int getTueClose();

	public void setTueClose(int tueClose);

	public int getWedOpen();

	public void setWedOpen(int wedOpen);

	public int getWedClose();

	public void setWedClose(int wedClose);

	public int getThuOpen();

	public void setThuOpen(int thuOpen);

	public int getThuClose();

	public void setThuClose(int thuClose);

	public int getFriOpen();

	public void setFriOpen(int friOpen);

	public int getFriClose();

	public void setFriClose(int friClose);

	public int getSatOpen();

	public void setSatOpen(int satOpen);

	public int getSatClose();

	public void setSatClose(int satClose);

	public OrgLabor toEscapedModel();

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

	public int compareTo(OrgLabor orgLabor);

	public int hashCode();

	public String toString();

	public String toXmlString();
}