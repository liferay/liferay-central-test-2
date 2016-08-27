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

package com.liferay.dynamic.data.mapping.expression.model;

import aQute.bnd.annotation.ProviderType;

import java.util.List;

/**
 * @author Leonardo Barros
 */
@ProviderType
public class FunctionCallExpression extends Expression {

	public FunctionCallExpression(
		String functionName, List<Expression> parameters) {

		_functionName = functionName;
		_parameters = parameters;
	}

	public int getArity() {
		return _parameters.size();
	}

	public String getFunctionName() {
		return _functionName;
	}

	public List<Expression> getParameters() {
		return _parameters;
	}

	private final String _functionName;
	private final List<Expression> _parameters;

}