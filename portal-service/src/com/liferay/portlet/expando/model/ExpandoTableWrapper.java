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

package com.liferay.portlet.expando.model;


/**
 * <a href="ExpandoTableSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ExpandoTable}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoTable
 * @generated
 */
public class ExpandoTableWrapper implements ExpandoTable {
	public ExpandoTableWrapper(ExpandoTable expandoTable) {
		_expandoTable = expandoTable;
	}

	public long getPrimaryKey() {
		return _expandoTable.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_expandoTable.setPrimaryKey(pk);
	}

	public long getTableId() {
		return _expandoTable.getTableId();
	}

	public void setTableId(long tableId) {
		_expandoTable.setTableId(tableId);
	}

	public long getCompanyId() {
		return _expandoTable.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_expandoTable.setCompanyId(companyId);
	}

	public java.lang.String getClassName() {
		return _expandoTable.getClassName();
	}

	public long getClassNameId() {
		return _expandoTable.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_expandoTable.setClassNameId(classNameId);
	}

	public java.lang.String getName() {
		return _expandoTable.getName();
	}

	public void setName(java.lang.String name) {
		_expandoTable.setName(name);
	}

	public com.liferay.portlet.expando.model.ExpandoTable toEscapedModel() {
		return _expandoTable.toEscapedModel();
	}

	public boolean isNew() {
		return _expandoTable.isNew();
	}

	public boolean setNew(boolean n) {
		return _expandoTable.setNew(n);
	}

	public boolean isCachedModel() {
		return _expandoTable.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_expandoTable.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _expandoTable.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_expandoTable.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _expandoTable.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _expandoTable.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_expandoTable.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _expandoTable.clone();
	}

	public int compareTo(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable) {
		return _expandoTable.compareTo(expandoTable);
	}

	public int hashCode() {
		return _expandoTable.hashCode();
	}

	public java.lang.String toString() {
		return _expandoTable.toString();
	}

	public java.lang.String toXmlString() {
		return _expandoTable.toXmlString();
	}

	public boolean isDefaultTable() {
		return _expandoTable.isDefaultTable();
	}

	public ExpandoTable getWrappedExpandoTable() {
		return _expandoTable;
	}

	private ExpandoTable _expandoTable;
}