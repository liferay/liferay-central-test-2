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

package com.liferay.portal.kernel.bi.reporting;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gavin Wan
 */
public enum ReportDataSourceType {

	CSV("csv"), EMPTY("empty"), JDBC("jdbc"), PORTAL("portal"), XLS("xls"),
	XML("xml");

	public static ReportDataSourceType parse(String value) {
		ReportDataSourceType reportDataSourceType = _reportDataSourceTypes.get(
			value);

		if (reportDataSourceType != null) {
			return reportDataSourceType;
		}

		if (CSV.toString().equalsIgnoreCase(value)) {
			return CSV;
		}
		else if (EMPTY.toString().equalsIgnoreCase(value)) {
			return EMPTY;
		}
		else if (JDBC.toString().equalsIgnoreCase(value)) {
			return JDBC;
		}
		else if (PORTAL.toString().equalsIgnoreCase(value)) {
			return PORTAL;
		}
		else if (XLS.toString().equalsIgnoreCase(value)) {
			return XLS;
		}
		else if (XML.toString().equalsIgnoreCase(value)) {
			return XML;
		}
		else {
			throw new IllegalArgumentException(
				"Invalid data source type " + value);
		}
	}

	public String toString() {
		return _value;
	}

	private ReportDataSourceType(String value) {
		_value = value;
	}

	private static final Map<String, ReportDataSourceType>
		_reportDataSourceTypes = new HashMap<String, ReportDataSourceType>();

	static {
		_reportDataSourceTypes.put(CSV.toString(), CSV);
		_reportDataSourceTypes.put(EMPTY.toString(), EMPTY);
		_reportDataSourceTypes.put(JDBC.toString(), JDBC);
		_reportDataSourceTypes.put(PORTAL.toString(), PORTAL);
		_reportDataSourceTypes.put(XLS.toString(), XLS);
		_reportDataSourceTypes.put(XML.toString(), XML);
	}

	private String _value;

}