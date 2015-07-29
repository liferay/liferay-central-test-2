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

package com.liferay.portal.search.internal;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.dummy.DummyIndexer;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.search.configuration.IndexerRegistryConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.ClassUtils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.configuration.IndexerRegistryConfiguration",
	immediate = true, service = IndexerRegistry.class
)
public class IndexerRegistryImpl implements IndexerRegistry {

	@Override
	public <T> Indexer<T> getIndexer(Class<T> clazz) {
		return getIndexer(clazz.getName());
	}

	@Override
	public <T> Indexer<T> getIndexer(String className) {
		Indexer<T> indexer = (Indexer<T>)_indexers.get(className);

		return proxyIndexer(indexer);
	}

	@Override
	public List<Indexer<?>> getIndexers() {
		List<Indexer<?>> indexers = new ArrayList<>(_indexers.values());

		return Collections.unmodifiableList(indexers);
	}

	@Override
	public <T> Indexer<T> nullSafeGetIndexer(Class<T> clazz) {
		return nullSafeGetIndexer(clazz.getName());
	}

	@Override
	public <T> Indexer<T> nullSafeGetIndexer(String className) {
		Indexer<T> indexer = getIndexer(className);

		if (indexer != null) {
			return indexer;
		}

		if (_log.isInfoEnabled()) {
			_log.info("No indexer found for " + className);
		}

		return (Indexer<T>)_dummyIndexer;
	}

	@Override
	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY, unbind = "unregister"
	)
	public void register(Indexer<?> indexer) {
		Class<?> clazz = indexer.getClass();

		_indexers.put(clazz.getName(), indexer);

		_indexers.put(indexer.getClassName(), indexer);
	}

	@Override
	public void unregister(Indexer<?> indexer) {
		Class<?> clazz = indexer.getClass();

		unregister(clazz.getName());
		unregister(indexer.getClassName());
	}

	@Override
	public void unregister(String className) {
		_indexers.remove(className);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_indexerRegistryConfiguration = Configurable.createConfigurable(
			IndexerRegistryConfiguration.class, properties);
	}

	protected <T> Indexer<T> proxyIndexer(Indexer<T> indexer) {
		if (indexer == null) {
			return null;
		}

		IndexerRequestBuffer indexerRequestBuffer = IndexerRequestBuffer.get();

		if ((indexerRequestBuffer == null) ||
			!_indexerRegistryConfiguration.buffered()) {

			return indexer;
		}

		Indexer<?> proxiedIndexer = _proxiedIndexers.get(
			indexer.getClassName());

		if (proxiedIndexer == null) {
			List interfaces = ClassUtils.getAllInterfaces(indexer.getClass());

			proxiedIndexer = (Indexer)ProxyUtil.newProxyInstance(
				PortalClassLoaderUtil.getClassLoader(),
				(Class[])interfaces.toArray(new Class[interfaces.size()]),
				new BufferedIndexerInvocationHandler(indexer));

			_proxiedIndexers.put(indexer.getClassName(), proxiedIndexer);
		}

		return (Indexer<T>)proxiedIndexer;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexerRegistryImpl.class);

	private final Indexer<?> _dummyIndexer = new DummyIndexer();
	private volatile IndexerRegistryConfiguration _indexerRegistryConfiguration;
	private final Map<String, Indexer<? extends Object>> _indexers =
		new ConcurrentHashMap<>();
	private final Map<String, Indexer<? extends Object>> _proxiedIndexers =
		new ConcurrentHashMap<>();

}