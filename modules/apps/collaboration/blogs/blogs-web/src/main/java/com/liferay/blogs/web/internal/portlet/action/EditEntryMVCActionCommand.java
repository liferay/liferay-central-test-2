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

package com.liferay.blogs.web.internal.portlet.action;

import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.blogs.exception.EntryContentException;
import com.liferay.blogs.exception.EntryCoverImageCropException;
import com.liferay.blogs.exception.EntryDescriptionException;
import com.liferay.blogs.exception.EntryDisplayDateException;
import com.liferay.blogs.exception.EntrySmallImageNameException;
import com.liferay.blogs.exception.EntrySmallImageScaleException;
import com.liferay.blogs.exception.EntryTitleException;
import com.liferay.blogs.exception.EntryUrlTitleException;
import com.liferay.blogs.exception.NoSuchEntryException;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.blogs.util.BlogsEntryAttachmentContentUpdater;
import com.liferay.blogs.util.BlogsEntryAttachmentFileEntryUtil;
import com.liferay.blogs.util.BlogsEntryImageSelectorHelper;
import com.liferay.blogs.web.constants.BlogsPortletKeys;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.friendly.url.exception.DuplicateFriendlyURLEntryException;
import com.liferay.portal.kernel.editor.EditorConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.blogs.BlogsEntryAttachmentFileEntryReference;
import com.liferay.trash.kernel.service.TrashEntryService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Brian Wing Shun Chan
 * @author Wilson S. Man
 * @author Thiago Moreira
 * @author Juan Fernández
 * @author Zsolt Berentey
 * @author Levente Hudák
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS,
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_ADMIN,
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_AGGREGATOR,
		"mvc.command.name=/blogs/edit_entry"
	},
	service = MVCActionCommand.class
)
public class EditEntryMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteEntries(
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		long[] deleteEntryIds = null;

		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		if (entryId > 0) {
			deleteEntryIds = new long[] {entryId};
		}
		else {
			deleteEntryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteEntryIds"), 0L);
		}

		List<TrashedModel> trashedModels = new ArrayList<>();

		for (long deleteEntryId : deleteEntryIds) {
			if (moveToTrash) {
				BlogsEntry entry = _blogsEntryService.moveEntryToTrash(
					deleteEntryId);

				trashedModels.add(entry);
			}
			else {
				_blogsEntryService.deleteEntry(deleteEntryId);
			}
		}

		if (moveToTrash && !trashedModels.isEmpty()) {
			Map<String, Object> data = new HashMap<>();

			data.put("trashedModels", trashedModels);

			addDeleteSuccessData(actionRequest, data);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			BlogsEntry entry = null;

			UploadException uploadException =
				(UploadException)actionRequest.getAttribute(
					WebKeys.UPLOAD_EXCEPTION);

			if (uploadException != null) {
				Throwable cause = uploadException.getCause();

				if (uploadException.isExceededFileSizeLimit()) {
					throw new FileSizeException(cause);
				}

				if (uploadException.isExceededLiferayFileItemSizeLimit()) {
					throw new LiferayFileItemException(cause);
				}

				if (uploadException.isExceededUploadRequestSizeLimit()) {
					throw new UploadRequestSizeException(cause);
				}

				throw new PortalException(cause);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				Callable<BlogsEntry> updateEntryCallable =
					new UpdateEntryCallable(actionRequest);

				entry = TransactionInvokerUtil.invoke(
					_transactionConfig, updateEntryCallable);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteEntries(actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteEntries(actionRequest, true);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restoreTrashEntries(actionRequest);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribe(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribe(actionRequest);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			String portletId = _http.getParameter(redirect, "p_p_id", false);

			int workflowAction = ParamUtil.getInteger(
				actionRequest, "workflowAction",
				WorkflowConstants.ACTION_SAVE_DRAFT);

			boolean ajax = ParamUtil.getBoolean(actionRequest, "ajax");

			if (ajax) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put(
					"attributeDataImageId",
					EditorConstants.ATTRIBUTE_DATA_IMAGE_ID);
				jsonObject.put("content", entry.getContent());
				jsonObject.put(
					"coverImageFileEntryId", entry.getCoverImageFileEntryId());
				jsonObject.put("entryId", entry.getEntryId());
				jsonObject.put("redirect", redirect);

				JSONPortletResponseUtil.writeJSON(
					actionRequest, actionResponse, jsonObject);

				return;
			}

			if ((entry != null) &&
				(workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT)) {

				redirect = getSaveAndContinueRedirect(
					actionRequest, entry, redirect);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				WindowState windowState = actionRequest.getWindowState();

				if (!windowState.equals(LiferayWindowState.POP_UP)) {
					sendRedirect(actionRequest, actionResponse, redirect);
				}
				else {
					redirect = _portal.escapeRedirect(redirect);

					if (Validator.isNotNull(redirect)) {
						if (cmd.equals(Constants.ADD) && (entry != null)) {
							String namespace = _portal.getPortletNamespace(
								portletId);

							redirect = _http.addParameter(
								redirect, namespace + "className",
								BlogsEntry.class.getName());
							redirect = _http.addParameter(
								redirect, namespace + "classPK",
								entry.getEntryId());
						}

						actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
					}
				}
			}
		}
		catch (AssetCategoryException | AssetTagException e) {
			SessionErrors.add(actionRequest, e.getClass(), e);

			actionResponse.setRenderParameter(
				"mvcRenderCommandName", "/blogs/edit_entry");

			hideDefaultSuccessMessage(actionRequest);
		}
		catch (DuplicateFriendlyURLEntryException | EntryContentException |
			   EntryCoverImageCropException | EntryDescriptionException |
			   EntryDisplayDateException | EntrySmallImageNameException |
			   EntrySmallImageScaleException | EntryTitleException |
			   EntryUrlTitleException | FileSizeException |
			   LiferayFileItemException | SanitizerException |
			   UploadRequestSizeException e) {

			SessionErrors.add(actionRequest, e.getClass());

			actionResponse.setRenderParameter(
				"mvcRenderCommandName", "/blogs/edit_entry");

			hideDefaultSuccessMessage(actionRequest);
		}
		catch (NoSuchEntryException | PrincipalException e) {
			SessionErrors.add(actionRequest, e.getClass());

			actionResponse.setRenderParameter("mvcPath", "/blogs/error.jsp");

			hideDefaultSuccessMessage(actionRequest);
		}
		catch (Throwable t) {
			_log.error(t, t);

			actionResponse.setRenderParameter("mvcPath", "/blogs/error.jsp");

			hideDefaultSuccessMessage(actionRequest);
		}
	}

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, BlogsEntry entry, String redirect)
		throws Exception {

		PortletConfig portletConfig = (PortletConfig)actionRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		LiferayPortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, portletConfig.getPortletName(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", "/blogs/edit_entry");

		portletURL.setParameter(Constants.CMD, Constants.UPDATE, false);
		portletURL.setParameter("redirect", redirect, false);
		portletURL.setParameter(
			"groupId", String.valueOf(entry.getGroupId()), false);
		portletURL.setParameter(
			"entryId", String.valueOf(entry.getEntryId()), false);
		portletURL.setWindowState(actionRequest.getWindowState());

		return portletURL.toString();
	}

	protected void restoreTrashEntries(ActionRequest actionRequest)
		throws Exception {

		long[] restoreTrashEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreTrashEntryIds"), 0L);

		for (long restoreTrashEntryId : restoreTrashEntryIds) {
			_trashEntryService.restoreEntry(restoreTrashEntryId);
		}
	}

	@Reference(policyOption = ReferencePolicyOption.GREEDY, unbind = "-")
	protected void setBlogsEntryAttachmentContentUpdater(
		BlogsEntryAttachmentContentUpdater blogsEntryAttachmentContentUpdater) {

		_blogsEntryAttachmentContentUpdater =
			blogsEntryAttachmentContentUpdater;
	}

	@Reference(unbind = "-")
	protected void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setBlogsEntryService(BlogsEntryService blogsEntryService) {
		_blogsEntryService = blogsEntryService;
	}

	@Reference(unbind = "-")
	protected void setTrashEntryService(TrashEntryService trashEntryService) {
		_trashEntryService = trashEntryService;
	}

	protected void subscribe(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_blogsEntryService.subscribe(themeDisplay.getScopeGroupId());
	}

	protected void unsubscribe(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_blogsEntryService.unsubscribe(themeDisplay.getScopeGroupId());
	}

	protected BlogsEntry updateEntry(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		String title = ParamUtil.getString(actionRequest, "title");
		String subtitle = ParamUtil.getString(actionRequest, "subtitle");
		String urlTitle = ParamUtil.getString(actionRequest, "urlTitle");

		String description = StringPool.BLANK;

		boolean customAbstract = ParamUtil.getBoolean(
			actionRequest, "customAbstract");

		if (customAbstract) {
			description = ParamUtil.getString(actionRequest, "description");

			if (Validator.isNull(description)) {
				throw new EntryDescriptionException();
			}
		}

		String content = ParamUtil.getString(actionRequest, "content");

		int displayDateMonth = ParamUtil.getInteger(
			actionRequest, "displayDateMonth");
		int displayDateDay = ParamUtil.getInteger(
			actionRequest, "displayDateDay");
		int displayDateYear = ParamUtil.getInteger(
			actionRequest, "displayDateYear");
		int displayDateHour = ParamUtil.getInteger(
			actionRequest, "displayDateHour");
		int displayDateMinute = ParamUtil.getInteger(
			actionRequest, "displayDateMinute");
		int displayDateAmPm = ParamUtil.getInteger(
			actionRequest, "displayDateAmPm");

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		boolean allowPingbacks = ParamUtil.getBoolean(
			actionRequest, "allowPingbacks");
		boolean allowTrackbacks = ParamUtil.getBoolean(
			actionRequest, "allowTrackbacks");
		String[] trackbacks = StringUtil.split(
			ParamUtil.getString(actionRequest, "trackbacks"));

		long coverImageFileEntryId = ParamUtil.getLong(
			actionRequest, "coverImageFileEntryId");
		String coverImageURL = ParamUtil.getString(
			actionRequest, "coverImageURL");
		String coverImageFileEntryCropRegion = ParamUtil.getString(
			actionRequest, "coverImageFileEntryCropRegion");

		String coverImageCaption = ParamUtil.getString(
			actionRequest, "coverImageCaption");

		long oldCoverImageId = 0;
		String oldCoverImageURL = StringPool.BLANK;
		long oldSmallImageId = 0;
		String oldSmallImageURL = StringPool.BLANK;

		if (entryId != 0) {
			BlogsEntry entry = _blogsEntryLocalService.getEntry(entryId);

			oldCoverImageId = entry.getCoverImageFileEntryId();
			oldCoverImageURL = entry.getCoverImageURL();
			oldSmallImageId = entry.getSmallImageId();
			oldSmallImageURL = entry.getSmallImageURL();
		}

		BlogsEntryImageSelectorHelper blogsEntryCoverImageSelectorHelper =
			new BlogsEntryImageSelectorHelper(
				coverImageFileEntryId, oldCoverImageId,
				coverImageFileEntryCropRegion, coverImageURL, oldCoverImageURL);

		ImageSelector coverImageImageSelector =
			blogsEntryCoverImageSelectorHelper.getImageSelector();

		long smallImageFileEntryId = ParamUtil.getLong(
			actionRequest, "smallImageFileEntryId");
		String smallImageURL = ParamUtil.getString(
			actionRequest, "smallImageURL");

		BlogsEntryImageSelectorHelper blogsEntrySmallImageSelectorHelper =
			new BlogsEntryImageSelectorHelper(
				smallImageFileEntryId, oldSmallImageId, StringPool.BLANK,
				smallImageURL, oldSmallImageURL);

		ImageSelector smallImageImageSelector =
			blogsEntrySmallImageSelectorHelper.getImageSelector();

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			BlogsEntry.class.getName(), actionRequest);

		BlogsEntry entry = null;
		List<BlogsEntryAttachmentFileEntryReference>
			blogsEntryAttachmentFileEntryReferences = null;

		if (entryId <= 0) {

			// Add entry

			entry = _blogsEntryService.addEntry(
				title, subtitle, urlTitle, description, content,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, allowPingbacks,
				allowTrackbacks, trackbacks, coverImageCaption,
				coverImageImageSelector, smallImageImageSelector,
				serviceContext);

			List<FileEntry> tempBlogsEntryAttachments =
				BlogsEntryAttachmentFileEntryUtil.
					getTempBlogsEntryAttachmentFileEntries(content);

			if (!tempBlogsEntryAttachments.isEmpty()) {
				Folder folder = _blogsEntryLocalService.addAttachmentsFolder(
					themeDisplay.getUserId(), entry.getGroupId());

				blogsEntryAttachmentFileEntryReferences =
					BlogsEntryAttachmentFileEntryUtil.
						addBlogsEntryAttachmentFileEntries(
							entry.getGroupId(), themeDisplay.getUserId(),
							entry.getEntryId(), folder.getFolderId(),
							tempBlogsEntryAttachments);

				content = _blogsEntryAttachmentContentUpdater.updateContent(
					content, blogsEntryAttachmentFileEntryReferences);

				entry.setContent(content);

				_blogsEntryLocalService.updateBlogsEntry(entry);
			}

			for (FileEntry tempBlogsEntryAttachment :
					tempBlogsEntryAttachments) {

				PortletFileRepositoryUtil.deletePortletFileEntry(
					tempBlogsEntryAttachment.getFileEntryId());
			}
		}
		else {

			// Update entry

			boolean sendEmailEntryUpdated = ParamUtil.getBoolean(
				actionRequest, "sendEmailEntryUpdated");

			serviceContext.setAttribute(
				"sendEmailEntryUpdated", sendEmailEntryUpdated);

			String emailEntryUpdatedComment = ParamUtil.getString(
				actionRequest, "emailEntryUpdatedComment");

			serviceContext.setAttribute(
				"emailEntryUpdatedComment", emailEntryUpdatedComment);

			entry = _blogsEntryLocalService.getEntry(entryId);

			List<FileEntry> tempBlogsEntryAttachmentFileEntries =
				BlogsEntryAttachmentFileEntryUtil.
					getTempBlogsEntryAttachmentFileEntries(content);

			if (!tempBlogsEntryAttachmentFileEntries.isEmpty()) {
				Folder folder = _blogsEntryLocalService.addAttachmentsFolder(
					themeDisplay.getUserId(), entry.getGroupId());

				blogsEntryAttachmentFileEntryReferences =
					BlogsEntryAttachmentFileEntryUtil.
						addBlogsEntryAttachmentFileEntries(
							entry.getGroupId(), themeDisplay.getUserId(),
							entry.getEntryId(), folder.getFolderId(),
							tempBlogsEntryAttachmentFileEntries);

				content = _blogsEntryAttachmentContentUpdater.updateContent(
					content, blogsEntryAttachmentFileEntryReferences);
			}

			entry = _blogsEntryService.updateEntry(
				entryId, title, subtitle, urlTitle, description, content,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, allowPingbacks,
				allowTrackbacks, trackbacks, coverImageCaption,
				coverImageImageSelector, smallImageImageSelector,
				serviceContext);

			for (FileEntry tempBlogsEntryAttachmentFileEntry :
					tempBlogsEntryAttachmentFileEntries) {

				PortletFileRepositoryUtil.deletePortletFileEntry(
					tempBlogsEntryAttachmentFileEntry.getFileEntryId());
			}
		}

		if (blogsEntryCoverImageSelectorHelper.isFileEntryTempFile()) {
			_blogsEntryLocalService.addOriginalImageFileEntry(
				themeDisplay.getUserId(), entry.getGroupId(),
				entry.getEntryId(), coverImageImageSelector);

			PortletFileRepositoryUtil.deletePortletFileEntry(
				coverImageFileEntryId);
		}

		if (blogsEntrySmallImageSelectorHelper.isFileEntryTempFile()) {
			_blogsEntryLocalService.addOriginalImageFileEntry(
				themeDisplay.getUserId(), entry.getGroupId(),
				entry.getEntryId(), smallImageImageSelector);

			PortletFileRepositoryUtil.deletePortletFileEntry(
				smallImageFileEntryId);
		}

		return entry;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditEntryMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	private BlogsEntryAttachmentContentUpdater
		_blogsEntryAttachmentContentUpdater;
	private BlogsEntryLocalService _blogsEntryLocalService;
	private BlogsEntryService _blogsEntryService;

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

	private TrashEntryService _trashEntryService;

	private class UpdateEntryCallable implements Callable<BlogsEntry> {

		@Override
		public BlogsEntry call() throws Exception {
			return updateEntry(_actionRequest);
		}

		private UpdateEntryCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}