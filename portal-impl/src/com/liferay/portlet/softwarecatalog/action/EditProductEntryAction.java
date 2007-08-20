/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.action;

import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;
import com.liferay.portlet.softwarecatalog.ProductEntryImagesException;
import com.liferay.portlet.softwarecatalog.ProductEntryLicenseException;
import com.liferay.portlet.softwarecatalog.ProductEntryNameException;
import com.liferay.portlet.softwarecatalog.ProductEntryPageURLException;
import com.liferay.portlet.softwarecatalog.ProductEntryShortDescriptionException;
import com.liferay.portlet.softwarecatalog.ProductEntryTypeException;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryServiceUtil;
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
 * <a href="EditProductEntryAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class EditProductEntryAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateProductEntry(req);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteProductEntry(req);
			}

			sendRedirect(req, res);
		}
		catch (Exception e) {
			if (e instanceof NoSuchProductEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				setForward(req, "portlet.software_catalog.error");
			}
			else if (e instanceof ProductEntryImagesException ||
					 e instanceof ProductEntryNameException ||
					 e instanceof ProductEntryLicenseException ||
					 e instanceof ProductEntryPageURLException ||
					 e instanceof ProductEntryShortDescriptionException ||
					 e instanceof ProductEntryTypeException) {

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
			ActionUtil.getProductEntry(req);
		}
		catch (Exception e) {
			if (e instanceof NoSuchProductEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				return mapping.findForward("portlet.software_catalog.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(req, "portlet.software_catalog.edit_product_entry"));
	}

	protected void deleteProductEntry(ActionRequest req) throws Exception {
		long productEntryId = ParamUtil.getLong(req, "productEntryId");

		SCProductEntryServiceUtil.deleteProductEntry(productEntryId);
	}

	protected void updateProductEntry(ActionRequest req) throws Exception {
		Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

		long productEntryId = ParamUtil.getLong(req, "productEntryId");

		String name = ParamUtil.getString(req, "name");
		String type = ParamUtil.getString(req, "type");
		String shortDescription = ParamUtil.getString(req, "shortDescription");
		String longDescription = ParamUtil.getString(req, "longDescription");
		String pageURL = ParamUtil.getString(req, "pageURL");
		String repoGroupId = ParamUtil.getString(req, "repoGroupId");
		String repoArtifactId = ParamUtil.getString(req, "repoArtifactId");

		long[] licenseIds = ParamUtil.getLongValues(req, "licenses");

		String[] communityPermissions = req.getParameterValues(
			"communityPermissions");
		String[] guestPermissions = req.getParameterValues(
			"guestPermissions");

		if (productEntryId <= 0) {

			// Add product entry

			SCProductEntryServiceUtil.addProductEntry(
				layout.getPlid(), name, type, shortDescription, longDescription,
				pageURL, repoGroupId, repoArtifactId, licenseIds,
				communityPermissions, guestPermissions);
		}
		else {

			// Update product entry

			SCProductEntryServiceUtil.updateProductEntry(
				productEntryId, name, type, shortDescription, longDescription,
				pageURL, repoGroupId, repoArtifactId, licenseIds);
		}
	}

}