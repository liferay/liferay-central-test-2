package com.liferay.portlet.bi.reporting;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * <a href="InMemoryReportDesignRetriever.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class InMemoryReportDesignRetriever implements ReportDesignRetriever {

	public InMemoryReportDesignRetriever(String name, byte[] designFile) {
		_reportName = name;
		_designFile = designFile;

	}
	public String getReportName() {
		return _reportName;
	}

	public InputStream retrieve() {
		return new ByteArrayInputStream(_designFile);
	}

	private String _reportName;
	private byte[] _designFile;
}
