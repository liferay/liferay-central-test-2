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

import java.util.Map;

/**
 * <a href="EmptyDataSourceReportRequest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Gavin Wan
 * @author Brian Wing Shun Chan
 */
public class EmptyDataSourceReportRequest implements ReportRequest {

	public EmptyDataSourceReportRequest(
		ReportDesignRetriever reportDesignRetriever,
		Map<String, String> reportParameters, String reportFormat) {

		_reportDesignRetriever = reportDesignRetriever;
		_reportParameters = reportParameters;
		_reportFormat = ReportFormat.parse(reportFormat);
	}

	public ReportDesignRetriever getReportDesignRetriever() {
		return _reportDesignRetriever;
	}

	public ReportFormat getReportFormat() {
		return _reportFormat;
	}

	public Map<String, String> getReportParameters() {
		return _reportParameters;
	}

	public void setReportDesignRetriever(
		ReportDesignRetriever reportDesignRetriever) {

		_reportDesignRetriever = reportDesignRetriever;
	}

	public void setReportFormat(ReportFormat reportFormat) {
		_reportFormat = reportFormat;
	}

	public void setReportParameters(Map<String, String> reportParameters) {
		_reportParameters.putAll(reportParameters);
	}

	private ReportDesignRetriever _reportDesignRetriever;
	private ReportFormat _reportFormat;
	private Map<String, String> _reportParameters;

}