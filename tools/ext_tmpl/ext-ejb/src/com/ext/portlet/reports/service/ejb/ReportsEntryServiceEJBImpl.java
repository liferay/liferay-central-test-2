package com.ext.portlet.reports.service.ejb;

import com.ext.portlet.reports.service.ReportsEntryService;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;


public class ReportsEntryServiceEJBImpl implements ReportsEntryService,
    SessionBean {
    private SessionContext _sc;

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
