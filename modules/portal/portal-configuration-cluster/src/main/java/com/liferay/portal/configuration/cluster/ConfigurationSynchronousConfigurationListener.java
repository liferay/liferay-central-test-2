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

package com.liferay.portal.configuration.cluster;

import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;

import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationEvent;
import org.osgi.service.cm.SynchronousConfigurationListener;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(immediate = true)
public class ConfigurationSynchronousConfigurationListener
	implements SynchronousConfigurationListener {

	@Override
	public void configurationEvent(ConfigurationEvent configurationEvent) {
		if (ConfigurationThreadLocal.isLocalUpdate()) {
			return;
		}

		Message message = new Message();

		message.setDestinationName(DestinationNames.CONFIGURATION);

		String factoryPid = configurationEvent.getFactoryPid();

		if (factoryPid != null) {
			message.put(ConfigurationAdmin.SERVICE_FACTORYPID, factoryPid);
		}
		else {
			message.put(Constants.SERVICE_PID, configurationEvent.getPid());
		}

		message.put("configuration.event.type", configurationEvent.getType());

		_clusterLink.sendMulticastMessage(message, Priority.LEVEL10);
	}

	@Reference
	protected void setClusterLink(ClusterLink clusterLink) {
		_clusterLink = clusterLink;
	}

	@Reference(
		target = "(destination.name=" + DestinationNames.CONFIGURATION + ")",
		unbind = "-"
	)
	protected void setDestination(Destination destination) {
	}

	private ClusterLink _clusterLink;

}