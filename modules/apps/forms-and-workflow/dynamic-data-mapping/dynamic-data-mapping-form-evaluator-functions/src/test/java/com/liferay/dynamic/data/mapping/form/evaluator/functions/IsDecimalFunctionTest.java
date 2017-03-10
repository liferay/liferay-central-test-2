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

package com.liferay.dynamic.data.mapping.form.evaluator.functions;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class IsDecimalFunctionTest {

	@Test
	public void testEvaluateFalse() throws Exception {
		IsDecimalFunction isDecimalFunction = new IsDecimalFunction();

		Assert.assertFalse((Boolean)isDecimalFunction.evaluate("simple text"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEvaluateInvalid() throws Exception {
		IsDecimalFunction isDecimalFunction = new IsDecimalFunction();

		isDecimalFunction.evaluate("test", "test2");
	}

	@Test
	public void testEvaluateTrue() throws Exception {
		IsDecimalFunction isDecimalFunction = new IsDecimalFunction();

		Assert.assertTrue((Boolean)isDecimalFunction.evaluate("3"));
		Assert.assertTrue((Boolean)isDecimalFunction.evaluate("4.76"));
		Assert.assertTrue((Boolean)isDecimalFunction.evaluate("-50.67"));
	}

}