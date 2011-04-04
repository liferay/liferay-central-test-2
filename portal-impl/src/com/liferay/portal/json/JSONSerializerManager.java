/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.json;

import com.liferay.portal.json.serializer.BaseModelJSONSerializer;
import com.liferay.portal.json.serializer.DefaultJSONSerializer;
import com.liferay.portal.json.serializer.RepositoryModelJSONSerializer;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.repository.model.RepositoryModel;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.BaseModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Spasic
 */
public class JSONSerializerManager {

	public JSONSerializer lookup(Object object) {

		Class type = object.getClass();

		JSONSerializer serializer = _serializerMap.get(type);

		if (serializer != null) {
			return serializer;
		}

		serializer = _processAnnotation(type);

		if (serializer == null) {
			serializer = _processNearByClass(type);
		}

		if (serializer == null) {
			serializer = _processBaseModel(object);
		}

		if (serializer == null) {
			serializer = _defaultJSONSerializer;
		}

		_serializerMap.put(type, serializer);

		return serializer;
	}

	private JSONSerializer _createJSONSerializer(Class jsonSerializerClass) {

		if (jsonSerializerClass == null) {
			return null;
		}

		try {
			return (JSONSerializer)jsonSerializerClass.newInstance();
		}
		catch (Exception e) {
			return null;
		}
	}

	private Class _loadClass(String className) {
		Class loadedClass = null;

		ClassLoader classLoader =
			Thread.currentThread().getContextClassLoader();

		try {
			loadedClass = classLoader.loadClass(className);
		}
		catch (ClassNotFoundException e) {
			return null;
		}

		return loadedClass;
	}

	private JSONSerializer _processAnnotation(Class type) {

		JSON jsonAnnotation = (JSON) type.getAnnotation(JSON.class);

		if (jsonAnnotation != null) {

			Class<? extends JSONSerializer> jsonSerializerClass =
				jsonAnnotation.serializer();

			return _createJSONSerializer(jsonSerializerClass);
		}

		return null;
	}

	private JSONSerializer _processBaseModel(Object object) {
		boolean isRepositoryModel = false;

		Class type = object.getClass();

		if (object instanceof RepositoryModel) {
			isRepositoryModel = true;

			RepositoryModel repositoryModel = ((RepositoryModel) object);

			object = repositoryModel.getModel();
			type = repositoryModel.getModelClass();
		}

		if (!(object instanceof BaseModel)) {
			return null;
		}

		String serializerClassName = StringUtil.replace(
			object.getClass().getName(),
			new String[] {".model.impl.", "Impl"},
			new String[] {".service.http.", "JSONSerializer"});

		if (_loadClass(serializerClassName) == null) {
			return null;
		}

		if (isRepositoryModel) {
			return new RepositoryModelJSONSerializer(serializerClassName, type);
		}
		else {
			return new BaseModelJSONSerializer(serializerClassName, type);
		}
	}

	private JSONSerializer _processNearByClass(Class type) {
		String jsonSerializerClassName = type.getName();

		jsonSerializerClassName += "JSONSerializer";

		Class jsonSerializerClass = _loadClass(jsonSerializerClassName);

		if (jsonSerializerClass == null) {
			return null;
		}

		return _createJSONSerializer(jsonSerializerClass);
	}

	private JSONSerializer _defaultJSONSerializer = new DefaultJSONSerializer();

	private Map<Class, JSONSerializer> _serializerMap =
		new HashMap<Class, JSONSerializer>();

}