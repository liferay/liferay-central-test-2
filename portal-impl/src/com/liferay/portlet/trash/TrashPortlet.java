/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.trash;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

/**
 * @author Eudaldo Alonso
 */
public class TrashPortlet extends MVCPortlet {

	public void deleteEntries(ActionRequest actionRequest) throws Exception {
		long trashEntryId = ParamUtil.getLong(actionRequest, "trashEntryId");

		if (trashEntryId > 0) {
			TrashEntryServiceUtil.deleteEntry(trashEntryId);

			return;
		}

		long[] deleteEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteThrashEntryIds"), 0L);

		if (deleteEntryIds.length > 0) {
			for (int i = 0; i < deleteEntryIds.length; i++) {
				TrashEntryServiceUtil.deleteEntry(deleteEntryIds[i]);
			}

			return;
		}

		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		if (Validator.isNotNull(className) && (classPK > 0)) {
			TrashEntryServiceUtil.deleteEntry(className, classPK);
		}
	}

	public void emptyTrash(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(
			actionRequest, "groupId", themeDisplay.getScopeGroupId());

		TrashEntryServiceUtil.deleteEntries(groupId);
	}

	public List<ObjectValuePair<String, Long>> moveEntry(
			ActionRequest actionRequest)
		throws Exception {

		long containerModelId = ParamUtil.getLong(
			actionRequest, "containerModelId");
		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			className, actionRequest);

		TrashEntryServiceUtil.moveEntry(
			className, classPK, containerModelId, serviceContext);

		return getEntryOVPs(className, classPK);
	}

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			List<ObjectValuePair<String, Long>> entryOVPs = null;

			if (cmd.equals(Constants.CHECK)) {
				JSONObject jsonObject = ActionUtil.checkEntry(actionRequest);

				writeJSON(actionRequest, actionResponse, jsonObject);

				return;
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteEntries(actionRequest);
			}
			else if (cmd.equals(Constants.EMPTY_TRASH)) {
				emptyTrash(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE)) {
				entryOVPs = moveEntry(actionRequest);
			}
			else if (cmd.equals(Constants.RENAME)) {
				entryOVPs = restoreRename(actionRequest);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				entryOVPs = restoreEntries(actionRequest);
			}
			else if (cmd.equals(Constants.OVERRIDE)) {
				entryOVPs = restoreOverride(actionRequest);
			}

			if (cmd.equals(Constants.RENAME) || cmd.equals(Constants.RESTORE) ||
				cmd.equals(Constants.OVERRIDE) || cmd.equals(Constants.MOVE)) {

				addRestoreData(actionRequest, entryOVPs);
			}

			String redirect = PortalUtil.escapeRedirect(
				ParamUtil.getString(actionRequest, "redirect"));

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			if (e instanceof RestoreEntryException) {
				RestoreEntryException ree = (RestoreEntryException)e;

				SessionErrors.add(actionRequest, ree.getClass(), ree);
			}
			else if (e instanceof TrashPermissionException) {
				TrashPermissionException tpe = (TrashPermissionException)e;

				SessionErrors.add(actionRequest, tpe.getClass(), tpe);
			}
			else {
				SessionErrors.add(actionRequest, e.getClass());
			}

			WindowState windowState = actionRequest.getWindowState();

			if (windowState.equals(LiferayWindowState.EXCLUSIVE) ||
				windowState.equals(LiferayWindowState.POP_UP)) {

				sendRedirect(actionRequest, actionResponse);
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.trash.view"));
	}

	public List<ObjectValuePair<String, Long>> restoreEntries(
			ActionRequest actionRequest)
		throws Exception {

		long trashEntryId = ParamUtil.getLong(actionRequest, "trashEntryId");

		if (trashEntryId > 0) {
			TrashEntry entry = TrashEntryServiceUtil.restoreEntry(trashEntryId);

			return getEntryOVPs(entry.getClassName(), entry.getClassPK());
		}

		long[] restoreEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreTrashEntryIds"), 0L);

		List<ObjectValuePair<String, Long>> entryOVPs = new ArrayList<>();

		for (long restoreEntryId : restoreEntryIds) {
			TrashEntry entry = TrashEntryServiceUtil.restoreEntry(
				restoreEntryId);

			entryOVPs.addAll(
				getEntryOVPs(entry.getClassName(), entry.getClassPK()));
		}

		return entryOVPs;
	}

	public List<ObjectValuePair<String, Long>> restoreOverride(
			ActionRequest actionRequest)
		throws Exception {

		long trashEntryId = ParamUtil.getLong(actionRequest, "trashEntryId");

		long duplicateEntryId = ParamUtil.getLong(
			actionRequest, "duplicateEntryId");

		TrashEntry entry = TrashEntryServiceUtil.restoreEntry(
			trashEntryId, duplicateEntryId, null);

		return getEntryOVPs(entry.getClassName(), entry.getClassPK());
	}

	public List<ObjectValuePair<String, Long>> restoreRename(
			ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long trashEntryId = ParamUtil.getLong(actionRequest, "trashEntryId");

		String newName = ParamUtil.getString(actionRequest, "newName");

		if (Validator.isNull(newName)) {
			String oldName = ParamUtil.getString(actionRequest, "oldName");

			newName = TrashUtil.getNewName(themeDisplay, null, 0, oldName);
		}

		TrashEntry entry = TrashEntryServiceUtil.restoreEntry(
			trashEntryId, 0, newName);

		return getEntryOVPs(entry.getClassName(), entry.getClassPK());
	}

	protected void addRestoreData(
			ActionRequest actionRequest,
			List<ObjectValuePair<String, Long>> entryOVPs)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if ((entryOVPs == null) || (entryOVPs.size() <= 0)) {
			return;
		}

		List<String> restoreClassNames = new ArrayList<>();
		List<String> restoreEntryLinks = new ArrayList<>();
		List<String> restoreEntryMessages = new ArrayList<>();
		List<String> restoreLinks = new ArrayList<>();
		List<String> restoreMessages = new ArrayList<>();

		for (int i = 0; i < entryOVPs.size(); i++) {
			ObjectValuePair<String, Long> entryOVP = entryOVPs.get(i);

			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(entryOVP.getKey());

			String restoreEntryLink = trashHandler.getRestoreContainedModelLink(
				actionRequest, entryOVP.getValue());
			String restoreLink = trashHandler.getRestoreContainerModelLink(
				actionRequest, entryOVP.getValue());
			String restoreMessage = trashHandler.getRestoreMessage(
				actionRequest, entryOVP.getValue());

			if (Validator.isNull(restoreLink) ||
				Validator.isNull(restoreMessage)) {

				continue;
			}

			restoreClassNames.add(trashHandler.getClassName());
			restoreEntryLinks.add(restoreEntryLink);

			TrashRenderer trashRenderer = trashHandler.getTrashRenderer(
				entryOVP.getValue());

			String restoreEntryTitle = trashRenderer.getTitle(
				themeDisplay.getLocale());

			restoreEntryMessages.add(restoreEntryTitle);

			restoreLinks.add(restoreLink);
			restoreMessages.add(restoreMessage);
		}

		Map<String, List<String>> data = new HashMap<>();

		data.put("restoreClassNames", restoreClassNames);
		data.put("restoreEntryLinks", restoreEntryLinks);
		data.put("restoreEntryMessages", restoreEntryMessages);
		data.put("restoreLinks", restoreLinks);
		data.put("restoreMessages", restoreMessages);

		SessionMessages.add(
			actionRequest,
			PortalUtil.getPortletId(actionRequest) +
				SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA, data);

		hideDefaultSuccessMessage(actionRequest);
	}

	protected List<ObjectValuePair<String, Long>> getEntryOVPs(
		String className, long classPK) {

		List<ObjectValuePair<String, Long>> entryOVPs = new ArrayList<>();

		ObjectValuePair<String, Long> entryOVP = new ObjectValuePair<>(
			className, classPK);

		entryOVPs.add(entryOVP);

		return entryOVPs;
	}

}