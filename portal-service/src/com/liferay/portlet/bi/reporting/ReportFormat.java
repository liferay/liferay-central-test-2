/*
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

package com.liferay.portlet.bi.reporting;

/**
 * <a href="ReportFormat.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public enum ReportFormat {
	HTML("html"),
	EXCEL("excel"),
	PDF("pdf");

	public static ReportFormat parse(String value) {
		if (HTML.toString().equals(value)) {
			return HTML;
		}
		if (EXCEL.toString().equals(value)) {
			return EXCEL;
		}
		if (PDF.toString().equals(value)) {
			return PDF;
		}
		throw new IllegalArgumentException("Invalid format: " + value);
	}

	@Override
	public String toString() {
		return _value;
	}

	private ReportFormat(String value) {
		_value = value;
	}

	private String _value;
}
