package com.liferay.portlet.bi.reporting;

import java.io.Serializable;

/**
 * <a href="ReportException.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class ReportGenerationException
	extends Exception implements Serializable {
	public ReportGenerationException(String message) {
		super(message);
	}

	public ReportGenerationException(String message, Throwable cause) {
		super(message, cause);
	}
}
