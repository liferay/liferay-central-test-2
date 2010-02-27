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

package com.liferay.portal.asset;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistry;
import com.liferay.portlet.asset.model.AssetRendererFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="AssetRendererFactoryRegistryImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Bruno Farache
 * @author Marcellus Tavares
 */
public class AssetRendererFactoryRegistryImpl
	implements AssetRendererFactoryRegistry {

	public AssetRendererFactory getAssetRendererFactoryByClassName(
		String className) {

		return _factoriesMapByClassName.get(className);
	}

	public AssetRendererFactory getAssetRendererFactoryByType(String type) {
		return _factoriesMapByClassType.get(type);
	}

	public List<AssetRendererFactory> getAssetRendererFactories() {
		return ListUtil.fromCollection(_factoriesMapByClassName.values());
	}

	public void register(AssetRendererFactory assetRendererFactory) {
		_factoriesMapByClassName.put(
			assetRendererFactory.getClassName(), assetRendererFactory);
		_factoriesMapByClassType.put(
			assetRendererFactory.getType(), assetRendererFactory);
	}

	public void unregister(AssetRendererFactory assetRendererFactory) {
		_factoriesMapByClassName.remove(assetRendererFactory.getClassName());
		_factoriesMapByClassType.remove(assetRendererFactory.getType());
	}

	private Map<String, AssetRendererFactory> _factoriesMapByClassName =
		new ConcurrentHashMap<String, AssetRendererFactory>();
	private Map<String, AssetRendererFactory> _factoriesMapByClassType =
		new ConcurrentHashMap<String, AssetRendererFactory>();

}