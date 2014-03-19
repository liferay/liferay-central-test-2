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

package com.liferay.portal.language;

import com.liferay.portal.kernel.util.SetUtil;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Carlos Sierra Andrés
 */
public class CompositeResourceBundleTest {

	@Test
	public void testGetKeyFromFirstBundle() {
		ResourceBundle resourceBundleA = _createResourceBundle(
			"keyA", "valueA");
		ResourceBundle resourceBundleB = _createResourceBundle(
			"keyB", "valueB");

		CompositeResourceBundle compositeResourceBundle =
			new CompositeResourceBundle(resourceBundleA, resourceBundleB);

		Assert.assertEquals(
			"valueA", compositeResourceBundle.getString("keyA"));

		Assert.assertEquals(
			SetUtil.fromArray(new String[] {"keyA", "keyB"}),
			compositeResourceBundle.keySet());
	}

	@Test
	public void testGetKeyFromSecondBundle() {
		ResourceBundle resourceBundleA = _createResourceBundle(
			"keyA", "valueA");
		ResourceBundle resourceBundleB = _createResourceBundle(
			"keyB", "valueB");

		CompositeResourceBundle compositeResourceBundle =
			new CompositeResourceBundle(resourceBundleA, resourceBundleB);

		Assert.assertEquals(
			"valueB", compositeResourceBundle.getString("keyB"));
	}

	@Test
	public void testKeySet() {
		ResourceBundle resourceBundleA = _createResourceBundle(
			"keyA", "valueA");
		ResourceBundle resourceBundleB = _createResourceBundle(
			"keyB", "valueB");

		CompositeResourceBundle compositeResourceBundle =
			new CompositeResourceBundle(resourceBundleA, resourceBundleB);

		Assert.assertEquals(
			SetUtil.fromArray(new String[] {"keyA", "keyB"}),
			compositeResourceBundle.keySet());
	}

	private ResourceBundle _createResourceBundle(
		final String ... keysAndValues) {

		if ((keysAndValues.length % 2) != 0) {
			throw new RuntimeException(
				"You must provide values for all the keys");
		}

		return new ListResourceBundle() {

			@Override
			protected Object[][] getContents() {
				Object[][] contents = new Object[keysAndValues.length / 2][];

				for (int i = 0; i < contents.length; i++) {
					contents[i] = new Object[] {
						keysAndValues[i/2], keysAndValues[(i/2) + 1]};
				}

				return contents;
			}
		};
	}

}