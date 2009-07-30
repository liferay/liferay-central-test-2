package com.ext.portlet.reports.service;

/**
 * <a href="ReportsEntryServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ReportsEntryServiceUtil {
    private static ReportsEntryService _service;

    public static ReportsEntryService getService() {
        if (_service == null) {
            throw new RuntimeException("ReportsEntryService is not set");
        }

        return _service;
    }

    public void setService(ReportsEntryService service) {
        _service = service;
    }
}