/*
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

/**
 * @author Michael C. Han
 */
public class ModelEventMessage {

	public static final String DESTROYED = "destroyed";
	public static final String INITIALIZED = "initialized";

	public static ModelEventMessage initialized(BaseModel model) {
		return new ModelEventMessage(model, INITIALIZED);
	}

	public static ModelEventMessage destroyed(BaseModel model) {
		return new ModelEventMessage(model, DESTROYED);
	}

	public ModelEventMessage(BaseModel baseModel, String eventType) {
		_baseModel = baseModel;
		_eventType = eventType;
	}

	public String getEventType() {
		return _eventType;
	}

	public BaseModel getBaseModel() {
		return _baseModel;
	}

	private BaseModel _baseModel;
	private String _eventType;
}
