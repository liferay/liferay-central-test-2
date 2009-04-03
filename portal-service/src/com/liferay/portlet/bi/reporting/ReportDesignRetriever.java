package com.liferay.portlet.bi.reporting;

import java.io.InputStream;

/**
 * <a href="ReportDesignRetriever.java.html"><b><i>View Source</i></b></a>
 *
 * Interface encapsulating logic for retrieving a report definition file.
 * Report definitions may come from files on the file system, in the classpath,
 * or in the Liferay Document Library.
 *
 * @see ContextClassloaderReportDesignRetriever
 * @see com.liferay.portlet.bi.reporting.servlet.ServletContextReportDesignRetriever
 * @author Michael C. Han
 */
public interface ReportDesignRetriever {
	String getReportName();
	InputStream retrieve();
}
