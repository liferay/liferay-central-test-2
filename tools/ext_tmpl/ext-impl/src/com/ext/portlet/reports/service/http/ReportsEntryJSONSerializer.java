package com.ext.portlet.reports.service.http;

import com.ext.portlet.reports.model.ReportsEntry;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Date;
import java.util.List;


/**
 * <a href="ReportsEntryJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by {@link ReportsEntryServiceJSON} to translate objects.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.ext.portlet.reports.service.http.ReportsEntryServiceJSON
 * @generated
 */
public class ReportsEntryJSONSerializer {
    public static JSONObject toJSONObject(ReportsEntry model) {
        JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

        jsonObj.put("entryId", model.getEntryId());
        jsonObj.put("companyId", model.getCompanyId());
        jsonObj.put("userId", model.getUserId());
        jsonObj.put("userName", model.getUserName());

        Date createDate = model.getCreateDate();

        String createDateJSON = StringPool.BLANK;

        if (createDate != null) {
            createDateJSON = String.valueOf(createDate.getTime());
        }

        jsonObj.put("createDate", createDateJSON);

        Date modifiedDate = model.getModifiedDate();

        String modifiedDateJSON = StringPool.BLANK;

        if (modifiedDate != null) {
            modifiedDateJSON = String.valueOf(modifiedDate.getTime());
        }

        jsonObj.put("modifiedDate", modifiedDateJSON);
        jsonObj.put("name", model.getName());

        return jsonObj;
    }

    public static JSONArray toJSONArray(
        com.ext.portlet.reports.model.ReportsEntry[] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (ReportsEntry model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        com.ext.portlet.reports.model.ReportsEntry[][] models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (ReportsEntry[] model : models) {
            jsonArray.put(toJSONArray(model));
        }

        return jsonArray;
    }

    public static JSONArray toJSONArray(
        List<com.ext.portlet.reports.model.ReportsEntry> models) {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (ReportsEntry model : models) {
            jsonArray.put(toJSONObject(model));
        }

        return jsonArray;
    }
}
