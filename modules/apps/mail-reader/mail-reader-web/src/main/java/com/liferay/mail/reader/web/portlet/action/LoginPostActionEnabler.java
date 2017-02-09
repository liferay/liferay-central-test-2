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

package com.liferay.mail.reader.web.portlet.action;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true)
public class LoginPostActionEnabler {

	@Activate
	public void activate(ComponentContext componentContext) {
		componentContext.enableComponent(LoginPostAction.class.getName());
	}

	@Deactivate
	public void deactivate(ComponentContext componentContext) {
		componentContext.disableComponent(LoginPostAction.class.getName());
	}

	@Reference(
		target = "(destination.name=" + DestinationNames.MAIL_SYNCHRONIZER + ")"
	)
	protected void registerMessageListener(MessageListener messageListener) {
	}

}