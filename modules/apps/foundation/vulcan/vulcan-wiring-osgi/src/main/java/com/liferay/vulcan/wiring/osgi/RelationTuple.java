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

package com.liferay.vulcan.wiring.osgi;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
public class RelationTuple<T, S> {

	public RelationTuple(
		String name, Class<S> type, Function<T, Optional<S>> objectFunction) {

		_name = name;
		_type = type;
		_objectFunction = objectFunction;
	}

	public String getName() {
		return _name;
	}

	public Function<T, Optional<S>> getObjectFunction() {
		return _objectFunction;
	}

	public Class<S> getType() {
		return _type;
	}

	private final String _name;
	private final Function<T, Optional<S>> _objectFunction;
	private final Class<S> _type;

}