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


/**
 * <a href="CountrySoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link Country}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Country
 * @generated
 */
public class CountryWrapper implements Country {
	public CountryWrapper(Country country) {
		_country = country;
	}

	public long getPrimaryKey() {
		return _country.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_country.setPrimaryKey(pk);
	}

	public long getCountryId() {
		return _country.getCountryId();
	}

	public void setCountryId(long countryId) {
		_country.setCountryId(countryId);
	}

	public java.lang.String getName() {
		return _country.getName();
	}

	public void setName(java.lang.String name) {
		_country.setName(name);
	}

	public java.lang.String getA2() {
		return _country.getA2();
	}

	public void setA2(java.lang.String a2) {
		_country.setA2(a2);
	}

	public java.lang.String getA3() {
		return _country.getA3();
	}

	public void setA3(java.lang.String a3) {
		_country.setA3(a3);
	}

	public java.lang.String getNumber() {
		return _country.getNumber();
	}

	public void setNumber(java.lang.String number) {
		_country.setNumber(number);
	}

	public java.lang.String getIdd() {
		return _country.getIdd();
	}

	public void setIdd(java.lang.String idd) {
		_country.setIdd(idd);
	}

	public boolean getActive() {
		return _country.getActive();
	}

	public boolean isActive() {
		return _country.isActive();
	}

	public void setActive(boolean active) {
		_country.setActive(active);
	}

	public com.liferay.portal.model.Country toEscapedModel() {
		return _country.toEscapedModel();
	}

	public boolean isNew() {
		return _country.isNew();
	}

	public boolean setNew(boolean n) {
		return _country.setNew(n);
	}

	public boolean isCachedModel() {
		return _country.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_country.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _country.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_country.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _country.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _country.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_country.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _country.clone();
	}

	public int compareTo(com.liferay.portal.model.Country country) {
		return _country.compareTo(country);
	}

	public int hashCode() {
		return _country.hashCode();
	}

	public java.lang.String toString() {
		return _country.toString();
	}

	public java.lang.String toXmlString() {
		return _country.toXmlString();
	}

	public Country getWrappedCountry() {
		return _country;
	}

	private Country _country;
}