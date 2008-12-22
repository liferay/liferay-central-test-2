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

package com.liferay.portlet.wsrpproducer;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import com.sun.portal.wsrp.producer.portlets.AdminPortletConstants;
import com.sun.portal.wsrp.producer.portlets.beans.ProducerBean;
import com.sun.portal.wsrp.producer.portlets.handlers.ProducerHandler;
import com.sun.portal.wsrp.producer.portlets.handlers.ProducerHandlerException;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * <a href="WSRPProducerPortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Manish Gupta
 *
 */
public class WSRPProducerPortlet
	extends com.sun.portal.wsrp.producer.portlets.WSRPProducerAdminPortlet {

	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		if (Validator.isNotNull(actionRequest.getParameter("action"))) {
			int action = ParamUtil.getInteger(actionRequest, "action");

			if (action == ProducerPortletAction.GET_REGISTRATION_PROPERTIES) {
				getRegistrationProperties(actionRequest, actionResponse);
			}
			else if (
				action == ProducerPortletAction.ADD_REGISTRATION_PROPERTY) {
				addRegistrationProperty(actionRequest, actionResponse);
			}
			else if (
				action == ProducerPortletAction.SAVE_REGISTRATION_PROPERTY) {
				saveRegistrationProperty(actionRequest, actionResponse);
			}
			else if (
				action == ProducerPortletAction.REMOVE_REGISTRATION_PROPERTY) {
				removeRegistrationProperty(actionRequest, actionResponse);
			}
			else {
				super.processAction(actionRequest, actionResponse);
			}
		}

		setRenderParameters(actionRequest, actionResponse);
	}

	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException, IOException {

		if (Validator.isNotNull(renderRequest.getParameter("action"))) {
			int action = ParamUtil.getInteger(renderRequest, "action");

			if (action == ProducerPortletAction.GET_REGISTRATION_PROPERTIES) {
				try {
					ProducerBean producerData =
						obtainHandler(renderRequest).getDetails(
							renderRequest.getParameter(
								AdminPortletConstants.PRODUCERID));

					renderRequest.getPortletSession().setAttribute(
							"producerBean", producerData);
				}
				catch (Exception e) {
					renderRequest.setAttribute("PRODUCER_ADMIN_ERROR",
						"OPERATION_FAILED");
				}
			}
		}
		super.render(renderRequest, renderResponse);

	}

	protected void addRegistrationProperty(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		actionResponse.setRenderParameter(
			"action", ParamUtil.getString(actionRequest, "action"));

		actionResponse.setRenderParameter(
			AdminPortletConstants.PRODUCERID,
				ParamUtil.getString(
					actionRequest, AdminPortletConstants.PRODUCERID));
	}

	protected void getRegistrationProperties(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		actionResponse.setRenderParameter(
			"action", ParamUtil.getString(actionRequest, "action"));

		actionResponse.setRenderParameter(
			AdminPortletConstants.PRODUCERID,
				ParamUtil.getString(
					actionRequest, AdminPortletConstants.PRODUCERID));
	}

	protected void removeRegistrationProperty (
		ActionRequest actionRequest, ActionResponse actionResponse) {

		String producerId = ParamUtil.getString(
			actionRequest, AdminPortletConstants.PRODUCERID);

		String propertyName = ParamUtil.getString(
			actionRequest, "selectedRegistrationProperty");

		if (Validator.isNotNull(propertyName)) {
			try {
				ProducerHandler handler = obtainHandler(actionRequest);
				ProducerBean producerData =	handler.getDetails(producerId);

				List<String> regProperties =
						producerData.getRegistrationPropertyDescriptions();

				if (regProperties != null) {
					boolean changed = false;
					for (Iterator<String> iterator = regProperties.iterator();
						iterator.hasNext();) {

						if (iterator.next().startsWith(propertyName + "=")) {
							iterator.remove();
							changed = true;
						}
					}
					if (changed) {
						Map attributeMap = new HashMap();
						attributeMap.put(
							"RegistrationPropertyDescription", regProperties);

						handler.update(producerId, attributeMap);
					}
				}
			}
			catch (Exception e) {
				actionResponse.setRenderParameter(
					"PRODUCER_ADMIN_ERROR", "OPERATION_FAILED");
			}
		}
		actionResponse.setRenderParameter(
			"action", String.valueOf(
				ProducerPortletAction.GET_REGISTRATION_PROPERTIES));

		actionResponse.setRenderParameter(
			AdminPortletConstants.PRODUCERID, producerId);
	}

	protected void saveRegistrationProperty(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		String error_message = "OPERATION_FAILED";
		String producerId = ParamUtil.getString(actionRequest,
								AdminPortletConstants.PRODUCERID);

		String propertyName = ParamUtil.getString(
			actionRequest, "property_name");
		String propertyDesc = ParamUtil.getString(
			actionRequest, "property_desc");

		int next_action = ProducerPortletAction.GET_REGISTRATION_PROPERTIES;

		try {

			if (Validator.isNull(propertyName) ||
					Validator.isNull(propertyDesc)) {

				error_message = "INVALID_PROPERTY_DATA";
				throw new ProducerHandlerException();
			}

			ProducerHandler handler = obtainHandler(actionRequest);
			ProducerBean producerData =	handler.getDetails(producerId);

			List regProperties =
					producerData.getRegistrationPropertyDescriptions();

			if (regProperties == null) {
				regProperties = new ArrayList();
			}
			else {
				for (Iterator<String> iterator = regProperties.iterator();
						iterator.hasNext();) {

					if (iterator.next().startsWith(propertyName + "=")) {
						error_message = "DUPLICATE_PROPERTY_NAME";
						throw new ProducerHandlerException();
					}
				}
			}

			regProperties.add(propertyName + "=" + propertyDesc);

			Map attributeMap = new HashMap();
			attributeMap.put(
				"RegistrationPropertyDescription",	regProperties);

			handler.update(producerId, attributeMap);
		}
		catch (ProducerHandlerException e) {
			actionResponse.setRenderParameter(
				"PRODUCER_ADMIN_ERROR", error_message);

			next_action = ProducerPortletAction.ADD_REGISTRATION_PROPERTY;
		}

		actionResponse.setRenderParameter(
				"action", String.valueOf(next_action));

		actionResponse.setRenderParameter(
				AdminPortletConstants.PRODUCERID, producerId);
	}

	protected void setRenderParameters(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		String tabs1 = ParamUtil.getString(actionRequest, "tabs1");

		if (Validator.isNotNull(tabs1)) {
			actionResponse.setRenderParameter("tabs1", tabs1);
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			actionResponse.setRenderParameter("redirect", redirect);
		}
	}

}