package com.ext.portlet.reports.service.persistence;

import com.liferay.util.dao.hibernate.Transformer;


public class ReportsEntryHBMUtil implements Transformer {
    private static ReportsEntryHBMUtil _instance = new ReportsEntryHBMUtil();

    public static com.ext.portlet.reports.model.ReportsEntry model(
        ReportsEntryHBM reportsEntryHBM) {
        return (com.ext.portlet.reports.model.ReportsEntry) reportsEntryHBM;
    }

    public static ReportsEntryHBMUtil getInstance() {
        return _instance;
    }

    public Comparable transform(Object obj) {
        return model((ReportsEntryHBM) obj);
    }
}
