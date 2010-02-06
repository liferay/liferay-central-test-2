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

package com.liferay.portlet.enterpriseadmin.action;

import com.liferay.portal.AddressCityException;
import com.liferay.portal.AddressStreetException;
import com.liferay.portal.AddressZipException;
import com.liferay.portal.DuplicateOrganizationException;
import com.liferay.portal.EmailAddressException;
import com.liferay.portal.NoSuchCountryException;
import com.liferay.portal.NoSuchListTypeException;
import com.liferay.portal.NoSuchOrganizationException;
import com.liferay.portal.NoSuchRegionException;
import com.liferay.portal.OrganizationNameException;
import com.liferay.portal.OrganizationParentException;
import com.liferay.portal.PhoneNumberException;
import com.liferay.portal.RequiredOrganizationException;
import com.liferay.portal.WebsiteURLException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.OrganizationServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.communities.util.CommunitiesUtil;
import com.liferay.portlet.enterpriseadmin.util.EnterpriseAdminUtil;
import com.liferay.util.LocalizationUtil;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditOrganizationAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 * @author Jorge Ferrer
 */
public class EditOrganizationAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			Organization organization = null;

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				organization = updateOrganization(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteOrganizations(actionRequest);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (organization != null) {
				redirect = HttpUtil.setParameter(
					redirect, actionResponse.getNamespace() + "organizationId",
					organization.getOrganizationId());
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			if (e instanceof NoSuchOrganizationException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.enterprise_admin.error");
			}
			else if (e instanceof AddressCityException ||
					 e instanceof AddressStreetException ||
					 e instanceof AddressZipException ||
					 e instanceof DuplicateOrganizationException ||
					 e instanceof EmailAddressException ||
					 e instanceof NoSuchCountryException ||
					 e instanceof NoSuchListTypeException ||
					 e instanceof NoSuchRegionException ||
					 e instanceof OrganizationNameException ||
					 e instanceof OrganizationParentException ||
					 e instanceof PhoneNumberException ||
					 e instanceof RequiredOrganizationException ||
					 e instanceof WebsiteURLException) {

				if (e instanceof NoSuchListTypeException) {
					NoSuchListTypeException nslte = (NoSuchListTypeException)e;

					SessionErrors.add(
						actionRequest,
						e.getClass().getName() + nslte.getType());
				}
				else {
					SessionErrors.add(actionRequest, e.getClass().getName());
				}

				if (e instanceof RequiredOrganizationException) {
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
			ActionUtil.getOrganization(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchOrganizationException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.enterprise_admin.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(getForward(
			renderRequest, "portlet.enterprise_admin.edit_organization"));
	}

	protected void deleteOrganizations(ActionRequest actionRequest)
		throws Exception {

		long[] deleteOrganizationIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteOrganizationIds"), 0L);

		for (int i = 0; i < deleteOrganizationIds.length; i++) {
			OrganizationServiceUtil.deleteOrganization(
				deleteOrganizationIds[i]);
		}
	}

	protected Organization updateOrganization(ActionRequest actionRequest)
		throws Exception {

		long organizationId = ParamUtil.getLong(
			actionRequest, "organizationId");

		long parentOrganizationId = ParamUtil.getLong(
			actionRequest, "parentOrganizationSearchContainerPrimaryKeys",
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);
		String name = ParamUtil.getString(actionRequest, "name");
		boolean recursable = ParamUtil.getBoolean(actionRequest, "recursable");
		int statusId = ParamUtil.getInteger(actionRequest, "statusId");
		String type = ParamUtil.getString(actionRequest, "type");
		long regionId = ParamUtil.getLong(actionRequest, "regionId");
		long countryId = ParamUtil.getLong(actionRequest, "countryId");
		String comments = ParamUtil.getString(actionRequest, "comments");
		List<Address> addresses = EnterpriseAdminUtil.getAddresses(
			actionRequest);
		List<EmailAddress> emailAddresses =
			EnterpriseAdminUtil.getEmailAddresses(actionRequest);
		List<OrgLabor> orgLabors = EnterpriseAdminUtil.getOrgLabors(
			actionRequest);
		List<Phone> phones = EnterpriseAdminUtil.getPhones(actionRequest);
		List<Website> websites = EnterpriseAdminUtil.getWebsites(actionRequest);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Organization.class.getName(), actionRequest);

		Organization organization = null;

		if (organizationId <= 0) {

			// Add organization

			organization = OrganizationServiceUtil.addOrganization(
				parentOrganizationId, name, type, recursable, regionId,
				countryId, statusId, comments, addresses, emailAddresses,
				orgLabors, phones, websites, serviceContext);
		}
		else {

			// Update organization

			organization = OrganizationServiceUtil.updateOrganization(
				organizationId, parentOrganizationId, name, type,
				recursable, regionId, countryId, statusId, comments, addresses,
				emailAddresses, orgLabors, phones, websites, serviceContext);

			boolean deleteLogo = ParamUtil.getBoolean(
				actionRequest, "deleteLogo");

			if (deleteLogo) {
				OrganizationServiceUtil.deleteLogo(
					organization.getOrganizationId());
			}
		}

		// Layout set prototypes

		long publicLayoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "publicLayoutSetPrototypeId");
		long privateLayoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "privateLayoutSetPrototypeId");

		CommunitiesUtil.applyLayoutSetPrototypes(
			organization.getGroup(), publicLayoutSetPrototypeId,
			privateLayoutSetPrototypeId);

		// Reminder queries

		String reminderQueries = actionRequest.getParameter("reminderQueries");

		PortletPreferences preferences = organization.getPreferences();

		preferences.setValue("reminderQueries", reminderQueries);

		LocalizationUtil.setLocalizedPreferencesValues(
			actionRequest, preferences, "reminderQueries");

		preferences.store();

		return organization;
	}

}