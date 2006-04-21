package com.ext.portlet.reports.service.persistence;

import com.liferay.util.dao.hibernate.Transformer;


public class ReportsEntryHBMUtil implements Transformer {
    private static ReportsEntryHBMUtil _instance = new ReportsEntryHBMUtil();

    public static com.ext.portlet.reports.model.ReportsEntry model(
        ReportsEntryHBM reportsEntryHBM) {
        return model(reportsEntryHBM, true);
    }

    public static com.ext.portlet.reports.model.ReportsEntry model(
        ReportsEntryHBM reportsEntryHBM, boolean checkPool) {
        com.ext.portlet.reports.model.ReportsEntry reportsEntry = null;

        if (checkPool) {
            reportsEntry = ReportsEntryPool.get(reportsEntryHBM.getPrimaryKey());
        }

        if (reportsEntry == null) {
            reportsEntry = new com.ext.portlet.reports.model.ReportsEntry(reportsEntryHBM.getEntryId(),
                    reportsEntryHBM.getCompanyId(),
                    reportsEntryHBM.getUserId(), reportsEntryHBM.getUserName(),
                    reportsEntryHBM.getCreateDate(),
                    reportsEntryHBM.getModifiedDate(), reportsEntryHBM.getName());
            ReportsEntryPool.put(reportsEntry.getPrimaryKey(), reportsEntry);
        }

        return reportsEntry;
    }

    public static ReportsEntryHBMUtil getInstance() {
        return _instance;
    }

    public Comparable transform(Object obj) {
        return model((ReportsEntryHBM) obj);
    }
}
