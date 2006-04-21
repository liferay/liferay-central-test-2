/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.FileNameException;
import com.liferay.documentlibrary.FileSizeException;
import com.liferay.documentlibrary.SourceFileNameException;
import com.liferay.lock.DuplicateLockException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.service.spring.DLFileEntryServiceUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.Http;
import com.liferay.util.ParamUtil;
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
 * <a href="EditFileEntryAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class EditFileEntryAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateFileEntry(req, res);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteFileEntry(req);
			}
			else if (cmd.equals(Constants.LOCK)) {
				lockFileEntry(req);
			}
			else if (cmd.equals(Constants.UNLOCK)) {
				unlockFileEntry(req);
			}

			sendRedirect(req, res);
		}
		catch (Exception e) {
			if (e != null &&
				e instanceof DuplicateLockException ||
				e instanceof NoSuchFileEntryException ||
				e instanceof NoSuchFolderException ||
				e instanceof PrincipalException) {

				if (e instanceof DuplicateLockException) {
					DuplicateLockException dle = (DuplicateLockException)e;

					SessionErrors.add(
						req, dle.getClass().getName(), dle.getLock());
				}
				else {
					SessionErrors.add(req, e.getClass().getName());
				}

				setForward(req, "portlet.document_library.error");
			}
			else if (e != null &&
					 e instanceof DuplicateFileException ||
					 e instanceof FileNameException ||
					 e instanceof FileSizeException ||
					 e instanceof SourceFileNameException) {

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
			ActionUtil.getFileEntry(req);
		}
		catch (Exception e) {
			if (e != null &&
				e instanceof NoSuchFileEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				return mapping.findForward("portlet.document_library.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(req, "portlet.document_library.edit_file_entry"));
	}

	protected void deleteFileEntry(ActionRequest req) throws Exception {
		String folderId = ParamUtil.getString(req, "folderId");
		String name = ParamUtil.getString(req, "name");
		double version = ParamUtil.getDouble(req, "version");

		DLFileEntryServiceUtil.deleteFileEntry(folderId, name, version);
	}

	protected void lockFileEntry(ActionRequest req) throws Exception {
		String folderId = ParamUtil.getString(req, "folderId");
		String name = ParamUtil.getString(req, "name");

		DLFileEntryServiceUtil.lockFileEntry(folderId, name);
	}

	protected void unlockFileEntry(ActionRequest req) throws Exception {
		String folderId = ParamUtil.getString(req, "folderId");
		String name = ParamUtil.getString(req, "name");

		DLFileEntryServiceUtil.unlockFileEntry(folderId, name);
	}

	protected void updateFileEntry(ActionRequest req, ActionResponse res)
		throws Exception {

		UploadPortletRequest uploadReq =
			PortalUtil.getUploadPortletRequest(req);

		String cmd = ParamUtil.getString(uploadReq, Constants.CMD);

		String folderId = ParamUtil.getString(uploadReq, "folderId");

		String title = ParamUtil.getString(uploadReq, "title");
		String description = ParamUtil.getString(uploadReq, "description");

		byte[] byteArray = FileUtil.getBytes(uploadReq.getFile("file"));

		String redirect = ParamUtil.getString(uploadReq, "fileEntryRedirect");

		if (cmd.equals(Constants.ADD)) {

			// Add file entry

			String fileName = uploadReq.getFileName("file");

			if (byteArray == null || byteArray.length == 0) {
				throw new FileSizeException();
			}

			boolean addCommunityPermissions = ParamUtil.getBoolean(
				uploadReq, "addCommunityPermissions");
			boolean addGuestPermissions = ParamUtil.getBoolean(
				uploadReq, "addGuestPermissions");

			DLFileEntryServiceUtil.addFileEntry(
				folderId, fileName, title, description, byteArray,
				addCommunityPermissions, addGuestPermissions);

			redirect += Http.encodeURL(fileName);
		}
		else {
			String name = ParamUtil.getString(uploadReq, "name");

			if (byteArray == null || byteArray.length == 0) {

				// Update file entry

				DLFileEntryServiceUtil.updateFileEntry(
					folderId, name, title, description);
			}
			else {

				// Update file version

				String sourceFileName = uploadReq.getFileName("file");

				DLFileEntryServiceUtil.updateFileEntry(
					folderId, name, sourceFileName, byteArray);
			}
		}

		res.sendRedirect(redirect);
	}

}