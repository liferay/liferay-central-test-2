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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ShardSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portal.service.http.ShardServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portal.service.http.ShardServiceSoap
 * @generated
 */
public class ShardSoap implements Serializable {
	public static ShardSoap toSoapModel(Shard model) {
		ShardSoap soapModel = new ShardSoap();

		soapModel.setShardId(model.getShardId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setName(model.getName());

		return soapModel;
	}

	public static ShardSoap[] toSoapModels(Shard[] models) {
		ShardSoap[] soapModels = new ShardSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ShardSoap[][] toSoapModels(Shard[][] models) {
		ShardSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ShardSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ShardSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ShardSoap[] toSoapModels(List<Shard> models) {
		List<ShardSoap> soapModels = new ArrayList<ShardSoap>(models.size());

		for (Shard model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ShardSoap[soapModels.size()]);
	}

	public ShardSoap() {
	}

	public long getPrimaryKey() {
		return _shardId;
	}

	public void setPrimaryKey(long pk) {
		setShardId(pk);
	}

	public long getShardId() {
		return _shardId;
	}

	public void setShardId(long shardId) {
		_shardId = shardId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _shardId;
	private long _classNameId;
	private long _classPK;
	private String _name;
}