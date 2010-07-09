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
 * @author Michael C. Han
 */
public enum ReportFormat {

	CSV("csv"), EXCEL("excel"), HTML("html"), PDF("pdf"), RTF("rtf"),
	TEXT("text"), XML("xml");

	public static ReportFormat parse(String value) {
		ReportFormat reportFormat = _reportFormats.get(value);

		if (reportFormat != null) {
			return reportFormat;
		}

		if (EXCEL.toString().equalsIgnoreCase(value)) {
			return EXCEL;
		}
		else if (HTML.toString().equalsIgnoreCase(value)) {
			return HTML;
		}
		else if (PDF.toString().equalsIgnoreCase(value)) {
			return PDF;
		}
		else {
			throw new IllegalArgumentException("Invalid format " + value);
		}
	}

	public String toString() {
		return _value;
	}

	private ReportFormat(String value) {
		_value = value;
	}

	private static final Map<String, ReportFormat> _reportFormats =
		new HashMap<String, ReportFormat>();

	static {
		_reportFormats.put(CSV.toString(), CSV);
		_reportFormats.put(EXCEL.toString(), EXCEL);
		_reportFormats.put(HTML.toString(), HTML);
		_reportFormats.put(PDF.toString(), PDF);
		_reportFormats.put(RTF.toString(), RTF);
		_reportFormats.put(TEXT.toString(), TEXT);
		_reportFormats.put(XML.toString(), XML);
	}

	private String _value;

}