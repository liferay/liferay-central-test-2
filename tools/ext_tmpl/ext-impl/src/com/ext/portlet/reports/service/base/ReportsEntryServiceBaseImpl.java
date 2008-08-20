package com.ext.portlet.reports.service.base;

import com.ext.portlet.reports.service.ReportsEntryService;
import com.ext.portlet.reports.service.persistence.ReportsEntryPersistence;

import com.liferay.portal.kernel.bean.InitializingBean;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.service.base.PrincipalBean;


public abstract class ReportsEntryServiceBaseImpl extends PrincipalBean
    implements ReportsEntryService, InitializingBean {
    protected ReportsEntryPersistence reportsEntryPersistence;

    public ReportsEntryPersistence getReportsEntryPersistence() {
        return reportsEntryPersistence;
    }

    public void setReportsEntryPersistence(
        ReportsEntryPersistence reportsEntryPersistence) {
        this.reportsEntryPersistence = reportsEntryPersistence;
    }

    public void afterPropertiesSet() {
        if (reportsEntryPersistence == null) {
            reportsEntryPersistence = (ReportsEntryPersistence) PortalBeanLocatorUtil.locate(ReportsEntryPersistence.class.getName() +
                    ".impl");
        }
    }
}
