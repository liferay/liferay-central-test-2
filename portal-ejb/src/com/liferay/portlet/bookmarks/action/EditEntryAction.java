/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.action;

import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.Constants;
import com.liferay.portlet.bookmarks.EntryURLException;
import com.liferay.portlet.bookmarks.NoSuchEntryException;
import com.liferay.portlet.bookmarks.NoSuchFolderException;
import com.liferay.portlet.bookmarks.service.spring.BookmarksEntryServiceUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.SessionErrors;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditEntryAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class EditEntryAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateEntry(req);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteEntry(req);
			}

			sendRedirect(req, res);
		}
		catch (Exception e) {
			if (e instanceof NoSuchEntryException ||
				e instanceof NoSuchFolderException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				setForward(req, "portlet.bookmarks.error");
			}
			else if (e instanceof EntryURLException) {
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
			ActionUtil.getEntry(req);
		}
		catch (Exception e) {
			if (e instanceof NoSuchEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				return mapping.findForward("portlet.bookmarks.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(req, "portlet.bookmarks.edit_entry"));
	}

	protected void deleteEntry(ActionRequest req) throws Exception {
		String entryId = ParamUtil.getString(req, "entryId");

		BookmarksEntryServiceUtil.deleteEntry(entryId);
	}

	protected void updateEntry(ActionRequest req) throws Exception {
		String entryId = ParamUtil.getString(req, "entryId");

		String folderId = ParamUtil.getString(req, "folderId");
		String name = ParamUtil.getString(req, "name");
		String url = ParamUtil.getString(req, "url");
		String comments = ParamUtil.getString(req, "comments");

		boolean addCommunityPermissions = ParamUtil.getBoolean(
			req, "addCommunityPermissions");
		boolean addGuestPermissions = ParamUtil.getBoolean(
			req, "addGuestPermissions");

		if (Validator.isNull(entryId)) {

			// Add entry

			BookmarksEntryServiceUtil.addEntry(
				folderId, name, url, comments, addCommunityPermissions,
				addGuestPermissions);
		}
		else {

			// Update entry

			BookmarksEntryServiceUtil.updateEntry(
				entryId, folderId, name, url, comments);
		}
	}

}