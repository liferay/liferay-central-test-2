/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lar.backgroundtask;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageTranslator;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class ExportBackgroundTaskStatusMessageTranslator
	implements BackgroundTaskStatusMessageTranslator {

	@Override
	public void translate(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		String messageType = message.getString("messageType");

		if (messageType.equals("portletExport")) {
			translatePortletExport(backgroundTaskStatus, message);
		}
		else if (messageType.equals("stagedModelExport")) {
			translateStagedModelExport(backgroundTaskStatus, message);
		}
	}

	protected synchronized void translatePortletExport(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		backgroundTaskStatus.clearAttributes();

		String portletId = message.getString("portletId");

		backgroundTaskStatus.setAttribute("portletId", portletId);

		Map<String, LongWrapper> modelAdditionCounters =
			(Map<String, LongWrapper>)message.get("modelAdditionCounters");

		backgroundTaskStatus.setAttribute(
			"allModelAdditionCounters",
			new HashMap<String, LongWrapper>(modelAdditionCounters));

		Map<String, LongWrapper> modelDeletionCounters =
			(Map<String, LongWrapper>)message.get("modelDeletionCounters");

		backgroundTaskStatus.setAttribute(
			"allModelDeletionCounters",
			new HashMap<String, LongWrapper>(modelDeletionCounters));
	}

	private synchronized void translateStagedModelExport(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		String portletId = (String)backgroundTaskStatus.getAttribute(
			"portletId");

		if (Validator.isNull(portletId)) {
			return;
		}

		String uuid = message.getString("uuid");

		backgroundTaskStatus.setAttribute("uuid", uuid);

		String stagedModelType = message.getString("stagedModelType");

		backgroundTaskStatus.setAttribute("stagedModelType", stagedModelType);

		Map<String, LongWrapper> modelAdditionCounters =
			(Map<String, LongWrapper>)message.get("modelAdditionCounters");

		backgroundTaskStatus.setAttribute(
			"currentModelAdditionCounters",
			new HashMap<String, LongWrapper>(modelAdditionCounters));

		Map<String, LongWrapper> modelDeletionCounters =
			(Map<String, LongWrapper>)message.get("modelDeletionCounters");

		backgroundTaskStatus.setAttribute(
			"currentModelDeletionCounters",
			new HashMap<String, LongWrapper>(modelDeletionCounters));
	}

}