package com.ext.portlet.reports.service.base;

import com.ext.portlet.reports.service.ReportsEntryService;
import com.ext.portlet.reports.service.persistence.ReportsEntryPersistence;

import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.service.base.PrincipalBean;


public abstract class ReportsEntryServiceBaseImpl extends PrincipalBean
    implements ReportsEntryService {
    @BeanReference(name = "com.ext.portlet.reports.service.ReportsEntryService.impl")
    protected ReportsEntryService reportsEntryService;
    @BeanReference(name = "com.ext.portlet.reports.service.persistence.ReportsEntryPersistence.impl")
    protected ReportsEntryPersistence reportsEntryPersistence;

    public ReportsEntryService getReportsEntryService() {
        return reportsEntryService;
    }

    public void setReportsEntryService(ReportsEntryService reportsEntryService) {
        this.reportsEntryService = reportsEntryService;
    }

    public ReportsEntryPersistence getReportsEntryPersistence() {
        return reportsEntryPersistence;
    }

    public void setReportsEntryPersistence(
        ReportsEntryPersistence reportsEntryPersistence) {
        this.reportsEntryPersistence = reportsEntryPersistence;
    }
}
