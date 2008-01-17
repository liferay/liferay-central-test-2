/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.journal.DuplicateSyndicatedFeedIdException;
import com.liferay.portlet.journal.NoSuchSyndicatedFeedException;
import com.liferay.portlet.journal.SyndicatedFeedContentFieldException;
import com.liferay.portlet.journal.SyndicatedFeedDescriptionException;
import com.liferay.portlet.journal.SyndicatedFeedIdException;
import com.liferay.portlet.journal.SyndicatedFeedNameException;
import com.liferay.portlet.journal.SyndicatedFeedTargetLayoutFriendlyUrlException;
import com.liferay.portlet.journal.SyndicatedFeedTargetPortletIdException;
import com.liferay.portlet.journal.model.JournalSyndicatedFeed;
import com.liferay.portlet.journal.service.JournalSyndicatedFeedServiceUtil;
import com.liferay.util.RSSUtil;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.UploadPortletRequest;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditSyndicatedFeedAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class EditSyndicatedFeedAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateSyndicatedFeed(req);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteSyndicatedFeeds(req);
			}

			sendRedirect(req, res);
		}
		catch (Exception e) {
			if (e instanceof NoSuchSyndicatedFeedException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				setForward(req, "portlet.journal.error");
			}
			else if (e instanceof DuplicateSyndicatedFeedIdException ||
					 e instanceof SyndicatedFeedDescriptionException ||
					 e instanceof SyndicatedFeedIdException ||
					 e instanceof SyndicatedFeedNameException ||
					 e instanceof SyndicatedFeedContentFieldException ||
					 e instanceof SyndicatedFeedTargetLayoutFriendlyUrlException ||
					 e instanceof SyndicatedFeedTargetPortletIdException) {

				SessionErrors.add(req, e.getClass().getName());
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(req, Constants.CMD);

			if (!cmd.equals(Constants.ADD)) {
				ActionUtil.getSyndicatedFeed(req);
			}
		}
		catch (NoSuchSyndicatedFeedException nssfe) {

			// Let this slide because the user can manually input a syndicated
			// feed id for a new syndicated feed that does not yet exist.

		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				return mapping.findForward("portlet.journal.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(req, "portlet.journal.edit_syndicated_feed"));
	}

	protected void deleteSyndicatedFeeds(ActionRequest req) throws Exception {
		long groupId = ParamUtil.getLong(req, "groupId");

		String[] deleteSyndicatedFeedIds = StringUtil.split(
			ParamUtil.getString(req, "deleteSyndicatedFeedIds"));

		for (int i = 0; i < deleteSyndicatedFeedIds.length; i++) {
			JournalSyndicatedFeedServiceUtil.deleteSyndicatedFeed(
				groupId, deleteSyndicatedFeedIds[i]);
		}
	}

	protected void updateSyndicatedFeed(ActionRequest req) throws Exception {
		String cmd = ParamUtil.getString(req, Constants.CMD);

		Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

		long groupId = ParamUtil.getLong(req, "groupId");

		String feedId = ParamUtil.getString(req, "feedId");
		boolean autoFeedId = ParamUtil.getBoolean(req, "autoFeedId");

		String name = ParamUtil.getString(req, "name");
		String description = ParamUtil.getString(req, "description");
		String type = ParamUtil.getString(req, "type");
		String structureId = ParamUtil.getString(req, "structureId");
		String templateId = ParamUtil.getString(req, "templateId");
		String rendererTemplateId =
			ParamUtil.getString(req, "rendererTemplateId");

		int delta = ParamUtil.getInteger(req, "delta");
		String orderByCol = ParamUtil.getString(req, "orderByCol");
		String orderByType = ParamUtil.getString(req, "orderByType");
		String targetLayoutFriendlyUrl =
			ParamUtil.getString(req, "targetLayoutFriendlyUrl");
		String targetPortletId = ParamUtil.getString(req, "targetPortletId");
		String contentField = ParamUtil.getString(req, "contentField");

		String feedType = RSSUtil.DEFAULT_TYPE;
		double feedVersion = RSSUtil.DEFAULT_VERSION;

		String feedTypeAndVersion =
			ParamUtil.getString(req, "feedTypeAndVersion");

		if (Validator.isNotNull(feedTypeAndVersion)) {
			String[] parts = feedTypeAndVersion.split(":");

			try {
				feedType = parts[0];
				feedVersion = Double.parseDouble(parts[1]);
			}
			catch (IndexOutOfBoundsException iobe) {
			}
			catch (NumberFormatException nfe) {
			}
		}
		else {
			feedType =
				ParamUtil.getString(req, "feedType", RSSUtil.DEFAULT_TYPE);
			feedVersion =
				ParamUtil.getDouble(
					req, "feedVersion", RSSUtil.DEFAULT_VERSION);
		}

		String[] communityPermissions = req.getParameterValues(
			"communityPermissions");
		String[] guestPermissions = req.getParameterValues(
			"guestPermissions");

		if (cmd.equals(Constants.ADD)) {

			// Add syndicated feed

			JournalSyndicatedFeedServiceUtil.addSyndicatedFeed(
				layout.getPlid(), feedId, autoFeedId, name, description,
				type, structureId, templateId, rendererTemplateId, delta,
				orderByCol, orderByType, targetLayoutFriendlyUrl,
				targetPortletId, contentField, feedType, feedVersion,
				communityPermissions, guestPermissions);
		}
		else {

			// Update syndicated feed

			JournalSyndicatedFeedServiceUtil.updateSyndicatedFeed(
				groupId, feedId, name, description, type, structureId,
				templateId, rendererTemplateId, delta, orderByCol, orderByType,
				targetLayoutFriendlyUrl, targetPortletId, contentField,
				feedType, feedVersion);
		}
	}

}
