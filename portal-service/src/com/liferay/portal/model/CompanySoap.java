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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="CompanySoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CompanySoap implements Serializable {
	public static CompanySoap toSoapModel(Company model) {
		CompanySoap soapModel = new CompanySoap();
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setKey(model.getKey());
		soapModel.setPortalURL(model.getPortalURL());
		soapModel.setHomeURL(model.getHomeURL());
		soapModel.setMx(model.getMx());

		return soapModel;
	}

	public static CompanySoap[] toSoapModels(List models) {
		List soapModels = new ArrayList(models.size());

		for (int i = 0; i < models.size(); i++) {
			Company model = (Company)models.get(i);
			soapModels.add(toSoapModel(model));
		}

		return (CompanySoap[])soapModels.toArray(new CompanySoap[0]);
	}

	public CompanySoap() {
	}

	public String getPrimaryKey() {
		return _companyId;
	}

	public void setPrimaryKey(String pk) {
		setCompanyId(pk);
	}

	public String getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(String companyId) {
		_companyId = companyId;
	}

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	public String getPortalURL() {
		return _portalURL;
	}

	public void setPortalURL(String portalURL) {
		_portalURL = portalURL;
	}

	public String getHomeURL() {
		return _homeURL;
	}

	public void setHomeURL(String homeURL) {
		_homeURL = homeURL;
	}

	public String getMx() {
		return _mx;
	}

	public void setMx(String mx) {
		_mx = mx;
	}

	private String _companyId;
	private String _key;
	private String _portalURL;
	private String _homeURL;
	private String _mx;
}