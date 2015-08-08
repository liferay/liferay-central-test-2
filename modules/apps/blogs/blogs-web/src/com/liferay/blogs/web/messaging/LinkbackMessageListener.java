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

package com.liferay.blogs.web.messaging;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.blogs.configuration.BlogsSystemConfiguration;
import com.liferay.blogs.web.constants.BlogsPortletKeys;
import com.liferay.portal.kernel.messaging.BaseSchedulerEntryMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.model.Portlet;
import com.liferay.portlet.blogs.linkback.LinkbackConsumer;
import com.liferay.portlet.blogs.linkback.LinkbackConsumerUtil;
import com.liferay.portlet.blogs.util.LinkbackProducerUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 * @author Tina Tian
 */
@Component(
	property = {"javax.portlet.name=" + BlogsPortletKeys.BLOGS},
	service = SchedulerEntry.class
)
public class LinkbackMessageListener extends BaseSchedulerEntryMessageListener {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		schedulerEntry.setTimeUnit(TimeUnit.MINUTE);
		schedulerEntry.setTriggerType(TriggerType.SIMPLE);

		_blogsSystemConfiguration = Configurable.createConfigurable(
			BlogsSystemConfiguration.class, properties);

		schedulerEntry.setTriggerValue(
			_blogsSystemConfiguration.linkbackJobInterval());
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		_linkbackConsumer.verifyNewTrackbacks();

		LinkbackProducerUtil.sendQueuedPingbacks();
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(target = "(javax.portlet.name=" + BlogsPortletKeys.BLOGS + ")")
	protected void setPortlet(Portlet portlet) {
	}

	private volatile BlogsSystemConfiguration _blogsSystemConfiguration;
	private final LinkbackConsumer _linkbackConsumer =
		LinkbackConsumerUtil.getLinkbackConsumer();

}