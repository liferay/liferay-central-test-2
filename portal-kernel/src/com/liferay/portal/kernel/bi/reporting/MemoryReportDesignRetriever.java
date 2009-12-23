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

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;

import java.io.InputStream;

import java.util.Date;

/**
 * <a href="MemoryReportDesignRetriever.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class MemoryReportDesignRetriever implements ReportDesignRetriever {

	public MemoryReportDesignRetriever(
		String reportName, Date modifiedDate, byte[] bytes) {

		_reportName = reportName;
		_modifiedDate = modifiedDate;
		_bytes = bytes;
	}

	public InputStream getInputStream() {
		return new UnsyncByteArrayInputStream(_bytes);
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public String getReportName() {
		return _reportName;
	}

	private byte[] _bytes;
	private Date _modifiedDate;
	private String _reportName;

}