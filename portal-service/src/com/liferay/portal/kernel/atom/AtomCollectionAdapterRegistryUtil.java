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

package com.liferay.portal.kernel.atom;

import java.util.List;

/**
 * @author Igor Spasic
 */
public class AtomCollectionAdapterRegistryUtil {

	public static AtomCollectionAdapter getAtomCollectionAdapter(
		Class<?> clazz) {

		return getAtomCollectionAdapterRegistry().
			getAtomCollectionAdapter(clazz.getName());
	}

	public static AtomCollectionAdapter getAtomCollectionAdapter(
		String className) {

		return getAtomCollectionAdapterRegistry().
			getAtomCollectionAdapter(className);
	}

	public static AtomCollectionAdapterRegistry
		getAtomCollectionAdapterRegistry() {

		return _atomCollectionAdapterRegistry;
	}

	public static List<AtomCollectionAdapter> getAtomCollectionAdapters() {
		return getAtomCollectionAdapterRegistry().getAtomCollectionAdapters();
	}

	public static void register(
		String className, AtomCollectionAdapter atomCollectionAdapter) {

		getAtomCollectionAdapterRegistry().register(
			className, atomCollectionAdapter);
	}

	public static void register(AtomCollectionAdapter atomCollectionAdapter) {

		getAtomCollectionAdapterRegistry().register(
			atomCollectionAdapter.getClass().getName(), atomCollectionAdapter);
	}

	public static void unregister(String className) {
		getAtomCollectionAdapterRegistry().unregister(className);
	}

	public void setAtomCollectionAdapterRegistry(
		AtomCollectionAdapterRegistry atomCollectionAdapterRegistry) {

		_atomCollectionAdapterRegistry = atomCollectionAdapterRegistry;
	}

	private static AtomCollectionAdapterRegistry _atomCollectionAdapterRegistry;

}