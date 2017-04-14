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

package com.liferay.portal.kernel.security.auth;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class FullNameGeneratorFactory {

	public static FullNameGenerator getInstance() {
		return _fullNameGenerator;
	}

	public void setFullNameGenerator(FullNameGenerator fullNameGenerator) {
		_fullNameGenerator = fullNameGenerator;
	}

	private FullNameGeneratorFactory() {
	}

	private static volatile FullNameGenerator _fullNameGenerator =
		ServiceProxyFactory.newServiceTrackedInstance(
			FullNameGenerator.class, FullNameGeneratorFactory.class,
			"_fullNameGenerator", true);

}