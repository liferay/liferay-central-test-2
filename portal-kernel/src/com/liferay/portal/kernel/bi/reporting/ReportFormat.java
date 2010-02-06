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

package com.liferay.portal.kernel.bi.reporting;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="ReportFormat.java.html"><b><i>View Source</i></b></a>
 *
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