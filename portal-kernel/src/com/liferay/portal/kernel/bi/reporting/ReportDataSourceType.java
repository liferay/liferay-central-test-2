/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.bi.reporting;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="ReportDataSourceType.java.html"><b><i>View Source</i></b></a>
 *
 * @author Gavin Wan
 */
public enum ReportDataSourceType {

	JASPER_CSV("jasper_csv"), JASPER_EMPTY("jasper_empty"),
	JASPER_XML("jasper_xml"), JDBC("jdbc"), PORTAL("portal");

	public static ReportDataSourceType parse(String value) {
		ReportDataSourceType reportDataSourceType = _reportDataSourceTypes.get(
			value);

		if (reportDataSourceType != null) {
			return reportDataSourceType;
		}

		if (JASPER_CSV.toString().equalsIgnoreCase(value)) {
			return JASPER_CSV;
		}
		else if (JASPER_EMPTY.toString().equalsIgnoreCase(value)) {
			return JASPER_EMPTY;
		}
		else if (JASPER_XML.toString().equalsIgnoreCase(value)) {
			return JASPER_XML;
		}
		else if (JDBC.toString().equalsIgnoreCase(value)) {
			return JDBC;
		}
		else if (PORTAL.toString().equalsIgnoreCase(value)) {
			return PORTAL;
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
		_reportDataSourceTypes.put(JASPER_CSV.toString(), JASPER_CSV);
		_reportDataSourceTypes.put(JASPER_EMPTY.toString(), JASPER_EMPTY);
		_reportDataSourceTypes.put(JASPER_XML.toString(), JASPER_XML);
		_reportDataSourceTypes.put(JDBC.toString(), JDBC);
		_reportDataSourceTypes.put(PORTAL.toString(), PORTAL);
	}

	private String _value;

}