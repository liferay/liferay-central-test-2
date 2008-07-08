package com.ext.portlet.reports.service.base;

import com.ext.portlet.reports.service.ReportsEntryService;
import com.ext.portlet.reports.service.persistence.ReportsEntryPersistence;
import com.ext.portlet.reports.service.persistence.ReportsEntryUtil;

import com.liferay.portal.service.base.PrincipalBean;


public abstract class ReportsEntryServiceBaseImpl extends PrincipalBean
    implements ReportsEntryService {
    protected ReportsEntryPersistence reportsEntryPersistence;

    public ReportsEntryPersistence getReportsEntryPersistence() {
        return reportsEntryPersistence;
    }

    public void setReportsEntryPersistence(
        ReportsEntryPersistence reportsEntryPersistence) {
        this.reportsEntryPersistence = reportsEntryPersistence;
    }

    protected void init() {
        if (reportsEntryPersistence == null) {
            reportsEntryPersistence = ReportsEntryUtil.getPersistence();
        }
    }
}
