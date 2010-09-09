/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.social;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zsolt Berentey
 */
public class SocialEquityDataSet {

	public SocialEquityDataSet(int type) {
		_type = type;
	}

	public void addValue(int actionDate, String date, long value) {
		_values.add(new DataSetValue(actionDate, date, value));
	}

	public int getType() {
		return _type;
	}

	public List<DataSetValue> getValues() {
		return _values;
	}

	private int _type;
	private List<DataSetValue> _values =
		new ArrayList<SocialEquityDataSet.DataSetValue>();

	public class DataSetValue {

		public DataSetValue(int actionDate, String date, long value) {
			_actionDate = actionDate;
			_date = date;
			_value = value;
		}

		public int getActionDate() {
			return _actionDate;
		}

		public String getDate() {
			return _date;
		}

		public long getValue() {
			return _value;
		}

		private int _actionDate;
		private String _date;
		private long _value;

	}

}