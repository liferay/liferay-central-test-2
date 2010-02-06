/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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