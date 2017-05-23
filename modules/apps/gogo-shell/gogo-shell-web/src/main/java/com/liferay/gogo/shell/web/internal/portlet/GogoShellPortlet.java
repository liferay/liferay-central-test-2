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

package com.liferay.gogo.shell.web.internal.portlet;

import com.liferay.gogo.shell.web.internal.constants.GogoShellPortletKeys;
import com.liferay.gogo.shell.web.internal.constants.GogoShellWebKeys;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintStream;

import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.felix.service.command.CommandProcessor;
import org.apache.felix.service.command.CommandSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Gregory Amerson
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-gogo-shell",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.render-weight=50",
		"javax.portlet.display-name=Gogo Shell",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + GogoShellPortletKeys.GOGO_SHELL,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class GogoShellPortlet extends MVCPortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		initCommandSession(renderRequest);

		CommandSession commandSession = _getSessionAttribute(
			renderRequest, CommandSession.class,
			GogoShellWebKeys.COMMAND_SESSION);

		SessionMessages.add(
			renderRequest, "prompt", commandSession.get("prompt"));

		super.doView(renderRequest, renderResponse);
	}

	public void executeCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String command = ParamUtil.getString(actionRequest, "command");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		initCommandSession(actionRequest);

		CommandSession commandSession = _getSessionAttribute(
			actionRequest, CommandSession.class,
			GogoShellWebKeys.COMMAND_SESSION);

		UnsyncByteArrayOutputStream outputUnsyncByteArrayOutputStream =
			_getSessionAttribute(
				actionRequest, UnsyncByteArrayOutputStream.class,
				GogoShellWebKeys.COMMAND_SESSION_OUTPUT_STREAM);
		UnsyncByteArrayOutputStream errorUnsyncByteArrayOutputStream =
			_getSessionAttribute(
				actionRequest, UnsyncByteArrayOutputStream.class,
				GogoShellWebKeys.COMMAND_SESSION_ERROR_STREAM);
		PrintStream outputPrintStream = _getSessionAttribute(
			actionRequest, PrintStream.class,
			GogoShellWebKeys.COMMAND_SESSION_OUTPUT_PRINT_STREAM);
		PrintStream errorPrintStream = _getSessionAttribute(
			actionRequest, PrintStream.class,
			GogoShellWebKeys.COMMAND_SESSION_ERROR_PRINT_STREAM);

		try {
			SessionMessages.add(actionRequest, "command", command);

			checkCommand(command, themeDisplay);

			commandSession.execute(command);

			outputPrintStream.flush();
			errorPrintStream.flush();

			SessionMessages.add(
				actionRequest, "commandOutput",
				outputUnsyncByteArrayOutputStream.toString());

			String errorContent = errorUnsyncByteArrayOutputStream.toString();

			if (Validator.isNotNull(errorContent)) {
				throw new Exception(errorContent);
			}
		}
		catch (Exception e) {
			hideDefaultErrorMessage(actionRequest);

			SessionErrors.add(actionRequest, "gogo", e);
		}
		finally {
			outputUnsyncByteArrayOutputStream.reset();
			errorUnsyncByteArrayOutputStream.reset();
		}
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		checkOmniAdmin();

		super.processAction(actionRequest, actionResponse);
	}

	protected void checkCommand(String command, ThemeDisplay themeDisplay)
		throws Exception {

		if (StringUtil.startsWith(command, "exit")) {
			ResourceBundle resourceBundle =
				_resourceBundleLoader.loadResourceBundle(
					themeDisplay.getLocale());

			throw new Exception(
				LanguageUtil.format(
					resourceBundle, "the-command-x-is-not-supported", command));
		}
	}

	protected void checkOmniAdmin() throws PortletException {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			PrincipalException principalException =
				new PrincipalException.MustBeOmniadmin(permissionChecker);

			throw new PortletException(principalException);
		}
	}

	protected void initCommandSession(PortletRequest portletRequest) {
		PortletSession portletSession = portletRequest.getPortletSession();

		Object commandSessionAttribute = portletSession.getAttribute(
			GogoShellWebKeys.COMMAND_SESSION);

		if (commandSessionAttribute instanceof CommandSession) {
			return;
		}

		UnsyncByteArrayOutputStream outputUnsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();
		UnsyncByteArrayOutputStream errorUnsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		PrintStream outputPrintStream = new PrintStream(
			outputUnsyncByteArrayOutputStream);
		PrintStream errorPrintStream = new PrintStream(
			errorUnsyncByteArrayOutputStream);

		CommandSession commandSession = _commandProcessor.createSession(
			null, outputPrintStream, errorPrintStream);

		commandSession.put("prompt", "g!");

		portletSession.setAttribute(
			GogoShellWebKeys.COMMAND_SESSION, commandSession);

		portletSession.setAttribute(
			GogoShellWebKeys.COMMAND_SESSION_ERROR_PRINT_STREAM,
			errorPrintStream);
		portletSession.setAttribute(
			GogoShellWebKeys.COMMAND_SESSION_ERROR_STREAM,
			errorUnsyncByteArrayOutputStream);
		portletSession.setAttribute(
			GogoShellWebKeys.COMMAND_SESSION_OUTPUT_PRINT_STREAM,
			outputPrintStream);
		portletSession.setAttribute(
			GogoShellWebKeys.COMMAND_SESSION_OUTPUT_STREAM,
			outputUnsyncByteArrayOutputStream);
	}

	private static <T> T _getSessionAttribute(
		PortletRequest portletRequest, Class<T> clazz, String name) {

		PortletSession portletSession = portletRequest.getPortletSession();

		Object sessionAttribute = portletSession.getAttribute(name);

		if (sessionAttribute != null) {
			return clazz.cast(sessionAttribute);
		}

		return null;
	}

	@Reference
	private CommandProcessor _commandProcessor;

	@Reference
	private Portal _portal;

	@Reference(target = "(bundle.symbolic.name=com.liferay.gogo.shell.web)")
	private ResourceBundleLoader _resourceBundleLoader;

}