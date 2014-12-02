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

package com.liferay.sync.engine.documentlibrary.event;

import com.liferay.sync.engine.documentlibrary.handler.Handler;
import com.liferay.sync.engine.documentlibrary.handler.MoveFolderHandler;

import java.util.Map;

/**
 * @author Shinn Lok
 */
public class MoveFolderEvent extends BaseEvent {

	public MoveFolderEvent(long syncAccountId, Map<String, Object> parameters) {
		super(syncAccountId, _URL_PATH, parameters);

		_handler = new MoveFolderHandler(this);
	}

	@Override
	public Handler<Void> getHandler() {
		return _handler;
	}

	@Override
	protected void processRequest() throws Exception {
		processAsynchronousRequest();
	}

	private static final String _URL_PATH =
		"/sync-web.syncdlobject/move-folder";

	private final Handler<Void> _handler;

}