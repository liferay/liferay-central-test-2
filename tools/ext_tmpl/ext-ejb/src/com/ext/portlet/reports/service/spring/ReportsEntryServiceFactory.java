package com.ext.portlet.reports.service.spring;

import com.liferay.portal.spring.util.SpringUtil;

import org.springframework.context.ApplicationContext;


public class ReportsEntryServiceFactory {
    public static final String CLASS_NAME = ReportsEntryServiceFactory.class.getName();
    private ReportsEntryService _service;

    public static ReportsEntryService getService() {
        ApplicationContext ctx = SpringUtil.getContext();
        ReportsEntryServiceFactory factory = (ReportsEntryServiceFactory) ctx.getBean(CLASS_NAME);

        return factory._service;
    }

    public void setService(ReportsEntryService service) {
        _service = service;
    }
}
