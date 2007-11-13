package com.liferay.portlet.documentlibrary.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.util.JSONUtil;


public class GetFoldersAction extends JSONAction {

	public String getJSON(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		long groupId = ParamUtil.getLong(req, "groupId");
		long parentFolderId = ParamUtil.getLong(req, "folderId");

		List folders = DLFolderLocalServiceUtil.getFolders(groupId, parentFolderId);

		JSONArray jsonArray = toJSONArray(folders);

		return jsonArray.toString();
	}

	public static JSONObject toJSONObject(
			DLFolder model)
		throws SystemException, PortalException {

		JSONObject jsonObj = new JSONObject();
		JSONUtil.put(jsonObj, "folderId", model.getFolderId());
		JSONUtil.put(jsonObj, "groupId", model.getGroupId());
		JSONUtil.put(jsonObj, "companyId", model.getCompanyId());
		JSONUtil.put(jsonObj, "userId", model.getUserId());
		JSONUtil.put(jsonObj, "userName", model.getUserName());
		JSONUtil.put(jsonObj, "createDate", model.getCreateDate());
		JSONUtil.put(jsonObj, "modifiedDate", model.getModifiedDate());
		JSONUtil.put(jsonObj, "parentFolderId", model.getParentFolderId());
		JSONUtil.put(jsonObj, "name", model.getName());
		JSONUtil.put(jsonObj, "description", model.getDescription());
		JSONUtil.put(jsonObj, "lastPostDate", model.getLastPostDate());

		List subfolderIds = new ArrayList();

		subfolderIds.add(new Long(model.getFolderId()));

		DLFolderLocalServiceUtil.getSubfolderIds(
				subfolderIds, model.getGroupId(), model.getFolderId());

		JSONUtil.put(jsonObj, "subFoldersCount", subfolderIds.size() - 1);
		JSONUtil.put(jsonObj, "fileEntriesCount",
				DLFileEntryLocalServiceUtil.getFileEntriesAndShortcutsCount(
						subfolderIds));

		return jsonObj;
	}

	public static JSONArray toJSONArray(
			List models)
		throws SystemException, PortalException {

		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			DLFolder model = (DLFolder)models.get(i);

			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

}