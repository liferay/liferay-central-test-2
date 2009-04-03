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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <a href="ReportRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class ReportRequest implements Serializable {
	public ReportRequest(
		ReportDesignRetriever retriever,
		String reportFormat,
		String imageStorageLocation, String imagePath) {
		_retriever = retriever;
		_reportFormat = ReportFormat.parse(reportFormat);
		_imageStorageLocation = imageStorageLocation;
		_imagePath = imagePath;
		_reportParameters = new HashMap<String, String>();
	}

	public String getAlternateDatasource() {
		return _alternateDatasource;
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
		return _retriever.getReportName();
	}

	public Map<String, String> getReportParameters() {
		return _reportParameters;
	}

	public ReportDesignRetriever getRetriever() {
		return _retriever;
	}

	public void setAlternateDatasource(String alternateDataSourceName) {
		_alternateDatasource = alternateDataSourceName;
	}

	public void setReportParameters(Map<String, String> parameters) {
		_reportParameters.putAll(parameters);
	}

	@Override
	public String toString() {
		return "ReportRequest{" +
			   "_reportName='" + getReportName() + '\'' +
			   ", _reportFormat=" + _reportFormat +
			   ", _imageStorageLocation='" + _imageStorageLocation + '\'' +
			   ", _imagePath='" + _imagePath + '\'' +
			   '}';
	}

	private String _alternateDatasource;
	private String _imagePath;
	private String _imageStorageLocation;
	private ReportFormat _reportFormat;
	private Map<String, String> _reportParameters;
	private ReportDesignRetriever _retriever;
}
