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

package com.liferay.portal.kernel.bi.reporting.request;

import com.liferay.portal.kernel.bi.reporting.ReportDesignRetriever;
import com.liferay.portal.kernel.bi.reporting.ReportFormat;
import com.liferay.portal.kernel.bi.reporting.ReportRequest;

import java.io.Serializable;
import java.util.Map;

/**
 * <a href="JDBCDataSourceReportRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Gavin Wan
 */
public class JDBCDataSourceReportRequest 
	implements ReportRequest, Serializable {

	public JDBCDataSourceReportRequest(
		ReportDesignRetriever reportDesignRetriever,
		Map<String, String> reportParameters, String reportFormat,
		String dataSourceDriver, String dataSourceURL,
		String dataSourceUserName, String dataSourcePassword) {

		_reportDesignRetriever = reportDesignRetriever;
		_reportParameters = reportParameters;
		_reportFormat = ReportFormat.parse(reportFormat);
		_dataSourceDriver = dataSourceDriver;
		_dataSourceURL = dataSourceURL;
		_dataSourceUserName = dataSourceUserName;
		_dataSourcePassword = dataSourcePassword;

	}

	public String getDataSourceDriver() {

		return _dataSourceDriver;
	}

	public void setDataSourceDriver(String sourceDriver) {

		_dataSourceDriver = sourceDriver;
	}

	public String getDataSourcePassword() {

		return _dataSourcePassword;
	}

	public void setDataSourcePassword(String sourcePassword) {

		_dataSourcePassword = sourcePassword;
	}

	public String getDataSourceURL() {

		return _dataSourceURL;
	}

	public void setDataSourceURL(String sourceURL) {

		_dataSourceURL = sourceURL;
	}

	public String getDataSourceUserName() {

		return _dataSourceUserName;
	}

	public void setDataSourceUserName(String sourceUserName) {

		_dataSourceUserName = sourceUserName;
	}

	public ReportDesignRetriever getReportDesignRetriever() {

		return _reportDesignRetriever;
	}

	public void setReportDesignRetriever(
		ReportDesignRetriever designRetriever) {

		_reportDesignRetriever = designRetriever;
	}

	public ReportFormat getReportFormat() {

		return _reportFormat;
	}

	public void setReportFormat(ReportFormat format) {

		_reportFormat = format;
	}

	public Map<String, String> getReportParameters() {

		return _reportParameters;
	}

	public void setReportParameters(Map<String, String> reportParameters) {

		_reportParameters.putAll(reportParameters);
	}

	private String _dataSourceDriver;
	private String _dataSourcePassword;
	private String _dataSourceURL;
	private String _dataSourceUserName;
	private ReportDesignRetriever _reportDesignRetriever;
	private ReportFormat _reportFormat;
	private Map<String, String> _reportParameters;
}