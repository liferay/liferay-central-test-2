/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset;

import com.liferay.portlet.asset.model.AssetRendererFactory;

import java.util.List;

/**
 * <a href="AssetRendererFactoryRegistryUtil.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Bruno Farache
 * @author Marcellus Tavares
 */
public class AssetRendererFactoryRegistryUtil {

	public static List<AssetRendererFactory> getAssetRendererFactories() {
		return getAssetRendererFactoryRegistry().getAssetRendererFactories();
	}

	public static AssetRendererFactory getAssetRendererFactoryByClassName(
		String className) {

		return getAssetRendererFactoryRegistry().
			getAssetRendererFactoryByClassName(className);
	}

	public static AssetRendererFactory getAssetRendererFactoryByType(
		String type) {

		return getAssetRendererFactoryRegistry().getAssetRendererFactoryByType(
			type);
	}

	public static AssetRendererFactoryRegistry
		getAssetRendererFactoryRegistry() {

		return _assetRendererFactoryRegistry;
	}

	public static void register(AssetRendererFactory assetRendererFactory) {
		getAssetRendererFactoryRegistry().register(assetRendererFactory);
	}

	public static void register(
		List<AssetRendererFactory> assetRendererFactories) {

		for (AssetRendererFactory assetRendererFactory :
				assetRendererFactories) {

			register(assetRendererFactory);
		}
	}

	public static void unregister(AssetRendererFactory assetRendererFactory) {
		getAssetRendererFactoryRegistry().unregister(assetRendererFactory);
	}

	public static void unregister(
		List<AssetRendererFactory> assetRendererFactories) {

		for (AssetRendererFactory assetRendererFactory :
				assetRendererFactories) {

			unregister(assetRendererFactory);
		}
	}

	public void setAssetRendererFactoryRegistry(
		AssetRendererFactoryRegistry assetRendererFactoryRegistry) {

		_assetRendererFactoryRegistry = assetRendererFactoryRegistry;
	}

	private static AssetRendererFactoryRegistry _assetRendererFactoryRegistry;

}