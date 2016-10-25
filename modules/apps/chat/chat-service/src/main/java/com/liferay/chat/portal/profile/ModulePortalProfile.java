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

package com.liferay.chat.portal.profile;

import com.liferay.chat.configuration.ChatGroupServiceConfigurationBeanDeclaration;
import com.liferay.chat.internal.events.LoginPostAction;
import com.liferay.chat.internal.events.SessionDestroyAction;
import com.liferay.chat.internal.model.listener.UserModelListener;
import com.liferay.chat.internal.poller.ChatPollerProcessor;
import com.liferay.chat.jabber.JabberImpl;
import com.liferay.chat.jabber.JabberUtil;
import com.liferay.chat.util.BuddyFinderUtil;
import com.liferay.chat.util.DefaultBuddyFinderImpl;
import com.liferay.portal.profile.BaseDSModulePortalProfile;
import com.liferay.portal.profile.PortalProfile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Kenji Heigel
 */
@Component(immediate = true, service = PortalProfile.class)
public class ModulePortalProfile extends BaseDSModulePortalProfile {

	@Activate
	public void activate(ComponentContext componentContext) {
		Set<String> supportedPortalProfileNames = new HashSet<String>(
			Arrays.asList(
				PortalProfile.PORTAL_PROFILE_NAME_CE,
				PortalProfile.PORTAL_PROFILE_NAME_DXP));

		init(
			componentContext, supportedPortalProfileNames,
			BuddyFinderUtil.class.getName(),
			ChatGroupServiceConfigurationBeanDeclaration.class.getName(),
			ChatPollerProcessor.class.getName(),
			DefaultBuddyFinderImpl.class.getName(), JabberImpl.class.getName(),
			JabberUtil.class.getName(), LoginPostAction.class.getName(),
			SessionDestroyAction.class.getName(),
			UserModelListener.class.getName());
	}

}