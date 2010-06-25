/**
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

package com.liferay.portal.spring.aop;

import com.liferay.portal.kernel.util.Validator;

/**
 * <a href="ChainableMethodAdviceInjector.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Shuyang Zhou
 */
public class ChainableMethodAdviceInjector {

	public void afterPropertiesSet() {
		if (Validator.isNull(_newChainableMethodAdvice)) {
			throw new IllegalArgumentException(
				"New ChainableMethodAdvice is null");
		}

		if (Validator.isNull(_parentChainableMethodAdvice)) {
			throw new IllegalArgumentException(
				"Parent ChainableMethodAdvice is null");
		}

		_newChainableMethodAdvice.nextMethodInterceptor =
			_parentChainableMethodAdvice.nextMethodInterceptor;
		_parentChainableMethodAdvice.nextMethodInterceptor =
			_newChainableMethodAdvice;
	}

	public void setNewChainableMethodAdvice(
		ChainableMethodAdvice newChainableMethodAdvice) {
		_newChainableMethodAdvice = newChainableMethodAdvice;
	}

	public void setParentChainableMethodAdvice(
		ChainableMethodAdvice parentChainableMethodAdvice) {
		_parentChainableMethodAdvice = parentChainableMethodAdvice;
	}

	private ChainableMethodAdvice _newChainableMethodAdvice;

	private ChainableMethodAdvice _parentChainableMethodAdvice;

}