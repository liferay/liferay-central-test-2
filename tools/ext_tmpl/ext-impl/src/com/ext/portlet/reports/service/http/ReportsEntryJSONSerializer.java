package com.ext.portlet.reports.service.http;

import com.ext.portlet.reports.model.ReportsEntry;

import com.liferay.portal.kernel.util.StringPool;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;


public class ReportsEntryJSONSerializer {
    public static JSONObject toJSONObject(ReportsEntry model) {
        JSONObject jsonObj = new JSONObject();
        String entryId = model.getEntryId();

        if (entryId == null) {
            jsonObj.put("entryId", StringPool.BLANK);
        } else {
            jsonObj.put("entryId", entryId.toString());
        }

        String companyId = model.getCompanyId();

        if (companyId == null) {
            jsonObj.put("companyId", StringPool.BLANK);
        } else {
            jsonObj.put("companyId", companyId.toString());
        }

        String userId = model.getUserId();

        if (userId == null) {
            jsonObj.put("userId", StringPool.BLANK);
        } else {
            jsonObj.put("userId", userId.toString());
        }

        String userName = model.getUserName();

        if (userName == null) {
            jsonObj.put("userName", StringPool.BLANK);
        } else {
            jsonObj.put("userName", userName.toString());
        }

        Date createDate = model.getCreateDate();

        if (createDate == null) {
            jsonObj.put("createDate", StringPool.BLANK);
        } else {
            jsonObj.put("createDate", createDate.toString());
        }

        Date modifiedDate = model.getModifiedDate();

        if (modifiedDate == null) {
            jsonObj.put("modifiedDate", StringPool.BLANK);
        } else {
            jsonObj.put("modifiedDate", modifiedDate.toString());
        }

        String name = model.getName();

        if (name == null) {
            jsonObj.put("name", StringPool.BLANK);
        } else {
            jsonObj.put("name", name.toString());
        }

        return jsonObj;
    }

    public static JSONArray toJSONArray(List models) {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < models.size(); i++) {
            ReportsEntry model = (ReportsEntry) models.get(i);
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }
}
