/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.journal.DuplicateStructureIdException;
import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.RequiredStructureException;
import com.liferay.portlet.journal.StructureDescriptionException;
import com.liferay.portlet.journal.StructureIdException;
import com.liferay.portlet.journal.StructureInheritanceException;
import com.liferay.portlet.journal.StructureNameException;
import com.liferay.portlet.journal.StructureXsdException;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.service.JournalStructureServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditStructureAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class EditStructureAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		JournalStructure structure = null;

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				structure = updateStructure(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteStructures(actionRequest);
			}

			if (Validator.isNotNull(cmd)) {
				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				if (structure != null) {
					boolean saveAndContinue = ParamUtil.getBoolean(
						actionRequest, "saveAndContinue");

					if (saveAndContinue) {
						redirect = getSaveAndContinueRedirect(
							portletConfig, actionRequest, structure, redirect);
					}
				}

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchStructureException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.journal.error");
			}
			else if (e instanceof DuplicateStructureIdException ||
					 e instanceof RequiredStructureException ||
					 e instanceof StructureDescriptionException ||
					 e instanceof StructureIdException ||
					 e instanceof StructureInheritanceException ||
					 e instanceof StructureNameException ||
					 e instanceof StructureXsdException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				if (e instanceof RequiredStructureException) {
					actionResponse.sendRedirect(
						ParamUtil.getString(actionRequest, "redirect"));
				}
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(renderRequest, Constants.CMD);

			if (!cmd.equals(Constants.ADD)) {
				ActionUtil.getStructure(renderRequest);
			}
		}
		catch (NoSuchStructureException nsse) {

			// Let this slide because the user can manually input a structure id
			// for a new structure that does not yet exist.

		}
		catch (Exception e) {
			if (//e instanceof NoSuchStructureException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.journal.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.journal.edit_structure"));
	}

	protected void deleteStructures(ActionRequest actionRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		String[] deleteStructureIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteStructureIds"));

		for (int i = 0; i < deleteStructureIds.length; i++) {
			JournalStructureServiceUtil.deleteStructure(
				groupId, deleteStructureIds[i]);

			JournalUtil.removeRecentStructure(
				actionRequest, deleteStructureIds[i]);
		}
	}

	protected String getSaveAndContinueRedirect(
			PortletConfig portletConfig, ActionRequest actionRequest,
			JournalStructure structure, String redirect)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String originalRedirect = ParamUtil.getString(
			actionRequest, "originalRedirect");

		PortletURLImpl portletURL = new PortletURLImpl(
			(ActionRequestImpl)actionRequest, portletConfig.getPortletName(),
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setWindowState(WindowState.MAXIMIZED);

		portletURL.setParameter("struts_action", "/journal/edit_structure");
		portletURL.setParameter(Constants.CMD, Constants.UPDATE, false);
		portletURL.setParameter("redirect", redirect, false);
		portletURL.setParameter("originalRedirect", originalRedirect, false);
		portletURL.setParameter(
			"groupId", String.valueOf(structure.getGroupId()), false);
		portletURL.setParameter(
			"structureId", structure.getStructureId(), false);

		return portletURL.toString();
	}

	protected JournalStructure updateStructure(ActionRequest actionRequest)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		String structureId = ParamUtil.getString(actionRequest, "structureId");
		boolean autoStructureId = ParamUtil.getBoolean(
			actionRequest, "autoStructureId");

		String parentStructureId = ParamUtil.getString(
			actionRequest, "parentStructureId");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String xsd = ParamUtil.getString(actionRequest, "xsd");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalStructure.class.getName(), actionRequest);

		JournalStructure structure = null;

		if (cmd.equals(Constants.ADD)) {

			// Add structure

			structure = JournalStructureServiceUtil.addStructure(
				groupId, structureId, autoStructureId, parentStructureId, name,
				description, xsd, serviceContext);
		}
		else {

			// Update structure

			structure = JournalStructureServiceUtil.updateStructure(
				groupId, structureId, parentStructureId, name, description,
				xsd, serviceContext);
		}

		// Recent structures

		JournalUtil.addRecentStructure(actionRequest, structure);

		return structure;
	}

}