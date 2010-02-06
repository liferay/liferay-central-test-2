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
 * <a href="ExpandoRowSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ExpandoRow}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoRow
 * @generated
 */
public class ExpandoRowWrapper implements ExpandoRow {
	public ExpandoRowWrapper(ExpandoRow expandoRow) {
		_expandoRow = expandoRow;
	}

	public long getPrimaryKey() {
		return _expandoRow.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_expandoRow.setPrimaryKey(pk);
	}

	public long getRowId() {
		return _expandoRow.getRowId();
	}

	public void setRowId(long rowId) {
		_expandoRow.setRowId(rowId);
	}

	public long getCompanyId() {
		return _expandoRow.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_expandoRow.setCompanyId(companyId);
	}

	public long getTableId() {
		return _expandoRow.getTableId();
	}

	public void setTableId(long tableId) {
		_expandoRow.setTableId(tableId);
	}

	public long getClassPK() {
		return _expandoRow.getClassPK();
	}

	public void setClassPK(long classPK) {
		_expandoRow.setClassPK(classPK);
	}

	public com.liferay.portlet.expando.model.ExpandoRow toEscapedModel() {
		return _expandoRow.toEscapedModel();
	}

	public boolean isNew() {
		return _expandoRow.isNew();
	}

	public boolean setNew(boolean n) {
		return _expandoRow.setNew(n);
	}

	public boolean isCachedModel() {
		return _expandoRow.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_expandoRow.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _expandoRow.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_expandoRow.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _expandoRow.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _expandoRow.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_expandoRow.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _expandoRow.clone();
	}

	public int compareTo(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow) {
		return _expandoRow.compareTo(expandoRow);
	}

	public int hashCode() {
		return _expandoRow.hashCode();
	}

	public java.lang.String toString() {
		return _expandoRow.toString();
	}

	public java.lang.String toXmlString() {
		return _expandoRow.toXmlString();
	}

	public ExpandoRow getWrappedExpandoRow() {
		return _expandoRow;
	}

	private ExpandoRow _expandoRow;
}