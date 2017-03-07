/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.dynamic.data.mapping.data.provider;

/**
 * @author Leonardo Barros
 */
public class DDMDataProviderRequest {

	public DDMDataProviderRequest(
		DDMDataProviderContext ddmDataProviderContext) {

		_ddmDataProviderContext = ddmDataProviderContext;
	}

	public DDMDataProviderContext getDDMDataProviderContext() {
		return _ddmDataProviderContext;
	}

	public String getEndValue() {
		return _endValue;
	}

	public String getStartValue() {
		return _startValue;
	}

	public void setEndValue(String endValue) {
		_endValue = endValue;
	}

	public void setStartValue(String startValue) {
		_startValue = startValue;
	}

	private final DDMDataProviderContext _ddmDataProviderContext;
	private String _endValue;
	private String _startValue;

}