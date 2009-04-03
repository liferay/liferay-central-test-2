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

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="ReportRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 *
 */
public class ReportRequest implements Serializable {

	public ReportRequest(
		ReportDesignRetriever reportDesignRetriever, String reportFormat,
		String imageStorageLocation, String imagePath) {

		_reportDesignRetriever = reportDesignRetriever;
		_reportFormat = ReportFormat.parse(reportFormat);
		_imageStorageLocation = imageStorageLocation;
		_imagePath = imagePath;
		_reportParameters = new HashMap<String, String>();
	}

	public String getAlternateDataSource() {
		return _alternateDataSource;
	}

	public ReportFormat getFormat() {
		return _reportFormat;
	}

	public String getImagePath() {
		return _imagePath;
	}

	public String getImageStorageLocation() {
		return _imageStorageLocation;
	}

	public String getReportName() {
		return _reportDesignRetriever.getReportName();
	}

	public Map<String, String> getReportParameters() {
		return _reportParameters;
	}

	public ReportDesignRetriever getRetriever() {
		return _reportDesignRetriever;
	}

	public void setAlternateDataSource(String alternateDataSourceName) {
		_alternateDataSource = alternateDataSourceName;
	}

	public void setReportParameters(Map<String, String> reportParameters) {
		_reportParameters.putAll(reportParameters);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{imageStorageLocation=");
		sb.append(_imageStorageLocation);
		sb.append(", imagePath=");
		sb.append(_imagePath);
		sb.append(", reportName=");
		sb.append(getReportName());
		sb.append(", reportFormat=");
		sb.append(_reportFormat);
		sb.append("}");

		return sb.toString();
	}

	private String _alternateDataSource;
	private String _imagePath;
	private String _imageStorageLocation;
	private ReportDesignRetriever _reportDesignRetriever;
	private ReportFormat _reportFormat;
	private Map<String, String> _reportParameters;

}