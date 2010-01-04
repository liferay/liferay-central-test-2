package com.ext.portlet.reports.service;


/**
 * <a href="ReportsEntryServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ReportsEntryService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ReportsEntryService
 * @generated
 */
public class ReportsEntryServiceWrapper implements ReportsEntryService {
    private ReportsEntryService _reportsEntryService;

    public ReportsEntryServiceWrapper(ReportsEntryService reportsEntryService) {
        _reportsEntryService = reportsEntryService;
    }

    public ReportsEntryService getWrappedReportsEntryService() {
        return _reportsEntryService;
    }
}
