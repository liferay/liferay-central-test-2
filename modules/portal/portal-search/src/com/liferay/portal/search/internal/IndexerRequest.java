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

package com.liferay.portal.search.internal;

import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ClassedModel;

import java.lang.reflect.Method;

/**
 * @author Michael C. Han
 */
public class IndexerRequest {

	public IndexerRequest(
		Method method, ClassedModel classedModel, Indexer<?> indexer) {

		_indexer = indexer;

		_methodHandler = new MethodHandler(method, classedModel);
		_modelClass = classedModel.getModelClass();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof IndexerRequest)) {
			return false;
		}

		IndexerRequest indexerRequest = (IndexerRequest)object;

		if (Validator.equals(_indexer, indexerRequest._indexer) &&
			Validator.equals(_modelClass, indexerRequest._modelClass) &&
			Validator.equals(
				_methodHandler, indexerRequest._methodHandler)) {

			return true;
		}

		return false;
	}

	public void execute() throws Exception {
		_methodHandler.invoke(_indexer);
	}

	@Override
	public int hashCode() {
		int result = _indexer.hashCode();

		result = 31 * result + _methodHandler.getMethodKey().hashCode();

		result = 31 * result + _modelClass.hashCode();

		Object[] arguments = _methodHandler.getArguments();

		for (Object argument : arguments) {
			result = 31 * result + argument.hashCode();
		}

		return result;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{indexer=");
		sb.append(ClassUtil.getClassName(_indexer));
		sb.append(", methodHandler=");
		sb.append(_methodHandler);
		sb.append(", modelClass=");
		sb.append(_modelClass);
		sb.append("}");

		return sb.toString();
	}

	private final Indexer<?> _indexer;
	private final MethodHandler _methodHandler;
	private final Class<?> _modelClass;

}