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

package com.liferay.portal.atom;

import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.atom.AtomCollectionAdapterRegistry;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Igor Spasic
 */
public class AtomCollectionAdapterRegistryImpl implements
	AtomCollectionAdapterRegistry {

	public AtomCollectionAdapter getAtomCollectionAdapter(String className) {
		return _atomCollectionAdapters.get(className);
	}

	public List<AtomCollectionAdapter> getAtomCollectionAdapters() {
		return ListUtil.fromCollection(_atomCollectionAdapters.values());
	}

	public void register(
		String className, AtomCollectionAdapter atomCollectionAdapter) {

		_atomCollectionAdapters.put(className, atomCollectionAdapter);
	}

	public void unregister(String className) {
		_atomCollectionAdapters.remove(className);
	}

	private Map<String, AtomCollectionAdapter> _atomCollectionAdapters =
		new ConcurrentHashMap<String, AtomCollectionAdapter>();

}