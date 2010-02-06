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

package com.liferay.portlet.asset.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="AssetTagStatsSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portlet.asset.service.http.AssetTagStatsServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.asset.service.http.AssetTagStatsServiceSoap
 * @generated
 */
public class AssetTagStatsSoap implements Serializable {
	public static AssetTagStatsSoap toSoapModel(AssetTagStats model) {
		AssetTagStatsSoap soapModel = new AssetTagStatsSoap();

		soapModel.setTagStatsId(model.getTagStatsId());
		soapModel.setTagId(model.getTagId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setAssetCount(model.getAssetCount());

		return soapModel;
	}

	public static AssetTagStatsSoap[] toSoapModels(AssetTagStats[] models) {
		AssetTagStatsSoap[] soapModels = new AssetTagStatsSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AssetTagStatsSoap[][] toSoapModels(AssetTagStats[][] models) {
		AssetTagStatsSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AssetTagStatsSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AssetTagStatsSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AssetTagStatsSoap[] toSoapModels(List<AssetTagStats> models) {
		List<AssetTagStatsSoap> soapModels = new ArrayList<AssetTagStatsSoap>(models.size());

		for (AssetTagStats model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AssetTagStatsSoap[soapModels.size()]);
	}

	public AssetTagStatsSoap() {
	}

	public long getPrimaryKey() {
		return _tagStatsId;
	}

	public void setPrimaryKey(long pk) {
		setTagStatsId(pk);
	}

	public long getTagStatsId() {
		return _tagStatsId;
	}

	public void setTagStatsId(long tagStatsId) {
		_tagStatsId = tagStatsId;
	}

	public long getTagId() {
		return _tagId;
	}

	public void setTagId(long tagId) {
		_tagId = tagId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public int getAssetCount() {
		return _assetCount;
	}

	public void setAssetCount(int assetCount) {
		_assetCount = assetCount;
	}

	private long _tagStatsId;
	private long _tagId;
	private long _classNameId;
	private int _assetCount;
}