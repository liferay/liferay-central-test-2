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

package com.liferay.portlet.softwarerepository.action;

import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.softwarerepository.NoSuchProductEntryException;
import com.liferay.portlet.softwarerepository.service.SRProductEntryServiceUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.servlet.SessionErrors;

import javax.portlet.*;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditProductEntryAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Jorge Ferrer
 *
 */
public class EditProductEntryAction
	extends PortletAction {

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
			if (e instanceof NoSuchProductEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				setForward(req, "portlet.software_repository.error");
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
			ActionUtil.getProductEntry(req);
		}
		catch (Exception e) {
			if (e instanceof NoSuchProductEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				return mapping.findForward("portlet.software_repository.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(req, "portlet.software_repository.edit_product_entry"));
	}

	protected void deleteEntry(ActionRequest req) throws Exception {
		long productEntryId = ParamUtil.getLong(req, "productEntryId");

		SRProductEntryServiceUtil.deleteProductEntry(productEntryId);
	}

	protected void updateEntry(ActionRequest req) throws Exception {
		Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

		long productEntryId = ParamUtil.getLong(req, "productEntryId", -1);

		String repoArtifactId = ParamUtil.getString(req, "repoArtifactId");
		String repoGroupId = ParamUtil.getString(req, "repoGroupId");
		String name = ParamUtil.getString(req, "name");
		String type = ParamUtil.getString(req, "type");
		long[] licenseIds = ParamUtil.getLongValues(req,"licenses");
		String shortDescription = ParamUtil.getString(req, "shortDescription");
		String longDescription = ParamUtil.getString(req, "longDescription");
		String pageURL = ParamUtil.getString(req, "pageURL");

		if (productEntryId <= 0) {

			// Add entry

			SRProductEntryServiceUtil.addProductEntry(
				layout.getPlid(), repoArtifactId, repoGroupId, name, type,
				licenseIds, shortDescription, longDescription, pageURL);
		}
		else {

			// Update entry

			SRProductEntryServiceUtil.updateProductEntry(
				productEntryId, repoArtifactId, repoGroupId, name, licenseIds,
				shortDescription, longDescription, pageURL);
		}
	}

}