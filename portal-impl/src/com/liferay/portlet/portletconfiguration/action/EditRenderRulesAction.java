package com.liferay.portlet.portletconfiguration.action;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.WebKeys;


import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class EditRenderRulesAction extends EditConfigurationAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = getPortlet(actionRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				actionRequest, PrincipalException.class.getName());

			setForward(actionRequest, "portlet.portlet_configuration.error");
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.SAVE)) {

			updateRenderRules(actionRequest, portlet.getPortletId());

			sendRedirect(actionRequest, actionResponse);
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = getPortlet(renderRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				renderRequest, PrincipalException.class.getName());

			return mapping.findForward("portlet.portlet_configuration.error");
		}

		renderResponse.setTitle(getTitle(portlet, renderRequest));

		return mapping.findForward(getForward(
			renderRequest, "portlet.portlet_configuration.edit_render_rules"));
	}

	protected void updateRenderRules(ActionRequest actionRequest, 
		String portletId) throws PortalException, SystemException {

		Layout layout = (Layout)actionRequest.getAttribute(WebKeys.LAYOUT);

		Layout storedLayout = LayoutLocalServiceUtil.getLayout(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getLayoutId());

		UnicodeProperties layoutTypeSettingsProperties =
			layout.getTypeSettingsProperties();

		UnicodeProperties storedLayoutTypeSettingsProperties =
			storedLayout.getTypeSettingsProperties();

		boolean modified = false;

		for (String className: PropsValues.PORTLET_RENDER_RULES_EVALUATORS) {

			String key = portletId + "_" + className;

			String oldRules =
				layoutTypeSettingsProperties.getProperty(key);

			String newRules = ParamUtil.getString(actionRequest, className);

			if (oldRules != null) {
				modified = !oldRules.equals(newRules);
			}
			else if (newRules != null) {
				modified = !newRules.equals(oldRules);
			}

			if (modified) {

				layoutTypeSettingsProperties.setProperty(key, newRules);

				storedLayoutTypeSettingsProperties.setProperty(key, newRules);
			}
		}
		
		if (modified) {
			LayoutLocalServiceUtil.updateLayout(storedLayout);
		}
	}
}
