package com.liferay.portlet.bi.reporting;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

/**
 * <a href="ReportEngine.java.html"><b><i>View Source</i></b></a>
 *
 * Interface abstracting the necessary methods for interfacing with various
 * reporting engine implementations (e.g. BIRT or JasperReports)
 *
 *
 * @author Michael C. Han
 */
public interface ReportEngine {
	void destroy();

	void execute(ReportRequest request, ReportResultContainer container)
		throws ReportGenerationException;

	void init(ServletContext context);

	Map<String, String> getEngineParameters();

	void registerDataSource(String name, DataSource source);

	void setDefaultDataSouce(DataSource ds);

	void setEngineParameters(Map<String, String> parameters);

	void setReportFormatExporterRepository(
		ReportFormatExporterRegistry exporters);

	void unregisterDataSource(String name);

}
