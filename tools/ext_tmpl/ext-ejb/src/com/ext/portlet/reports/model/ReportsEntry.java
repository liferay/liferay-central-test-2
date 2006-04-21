package com.ext.portlet.reports.model;

import java.util.Date;


public class ReportsEntry extends ReportsEntryModel {
    public ReportsEntry() {
    }

    public ReportsEntry(String entryId) {
        super(entryId);
    }

    public ReportsEntry(String entryId, String companyId, String userId,
        String userName, Date createDate, Date modifiedDate, String name) {
        super(entryId, companyId, userId, userName, createDate, modifiedDate,
            name);
    }
}
