package com.ext.portlet.reports.service.ejb;

import com.ext.portlet.reports.service.spring.ReportsEntryService;

import com.liferay.portal.spring.util.SpringUtil;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;


public class ReportsEntryServiceEJBImpl implements ReportsEntryService,
    SessionBean {
    public static final String CLASS_NAME = ReportsEntryService.class.getName() +
        ".transaction";
    private SessionContext _sc;

    public static ReportsEntryService getService() {
        ApplicationContext ctx = SpringUtil.getContext();

        return (ReportsEntryService) ctx.getBean(CLASS_NAME);
    }

    public void ejbCreate() throws CreateException {
    }

    public void ejbRemove() {
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public SessionContext getSessionContext() {
        return _sc;
    }

    public void setSessionContext(SessionContext sc) {
        _sc = sc;
    }
}
