package com.ext.portlet.reports.service.ejb;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;


public interface ReportsEntryServiceHome extends EJBHome {
    public ReportsEntryServiceEJB create()
        throws CreateException, RemoteException;
}
