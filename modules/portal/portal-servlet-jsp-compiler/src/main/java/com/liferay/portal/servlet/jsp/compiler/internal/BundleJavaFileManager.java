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

package com.liferay.portal.servlet.jsp.compiler.internal;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardLocation;

import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
public class BundleJavaFileManager
	extends ForwardingJavaFileManager<JavaFileManager> {

	public static final String OPT_VERBOSE = "-verbose";

	public BundleJavaFileManager(
		Bundle bundle, JavaFileManager javaFileManager, Logger logger,
		boolean verbose, ResourceResolver resourceResolver) {

		super(javaFileManager);

		_logger = logger;
		_verbose = verbose;
		_resourceResolver = resourceResolver;

		_bundleWiring = bundle.adapt(BundleWiring.class);

		for (BundleWire bundleWire : _bundleWiring.getRequiredWires(null)) {
			BundleWiring bundleWiring = bundleWire.getProviderWiring();

			if (!_bundleWirings.add(bundleWiring)) {
				continue;
			}

			Bundle currentBundle = bundleWiring.getBundle();

			if (currentBundle.getBundleId() == 0) {
				for (BundleCapability bundleCapability :
						bundleWiring.getCapabilities(
							BundleRevision.PACKAGE_NAMESPACE)) {

					Map<String, Object> attributes =
						bundleCapability.getAttributes();

					Object packageName = attributes.get(
						BundleRevision.PACKAGE_NAMESPACE);

					if (packageName != null) {
						_systemPackageNames.add(packageName);
					}
				}
			}
		}

		if (_verbose) {
			StringBundler sb = new StringBundler(_bundleWirings.size() * 4 + 6);

			sb.append("Bundle Java file manager for bundle ");
			sb.append(bundle.getSymbolicName());
			sb.append(StringPool.DASH);
			sb.append(bundle.getVersion());
			sb.append(" has dependent bundle wirings: ");

			for (BundleWiring bundleWiring : _bundleWirings) {
				Bundle currentBundle = bundleWiring.getBundle();

				sb.append(currentBundle.getSymbolicName());
				sb.append(StringPool.DASH);
				sb.append(currentBundle.getVersion());
				sb.append(StringPool.COMMA_AND_SPACE);
			}

			if (!_bundleWirings.isEmpty()) {
				sb.setIndex(sb.index() - 1);
			}

			_logger.log(Logger.LOG_INFO, sb.toString());
		}
	}

	public void addBundleWiring(BundleWiring bundleWiring) {
		_bundleWirings.add(bundleWiring);
	}

	@Override
	public ClassLoader getClassLoader(Location location) {
		if (location != StandardLocation.CLASS_PATH) {
			return fileManager.getClassLoader(location);
		}

		return _bundleWiring.getClassLoader();
	}

	@Override
	public String inferBinaryName(Location location, JavaFileObject file) {
		if ((location == StandardLocation.CLASS_PATH) &&
			(file instanceof BaseJavaFileObject)) {

			BaseJavaFileObject baseJavaFileObject = (BaseJavaFileObject)file;

			if (_verbose) {
				_logger.log(
					Logger.LOG_INFO,
					"Inferring binary name from " + baseJavaFileObject);
			}

			return baseJavaFileObject.getClassName();
		}

		return fileManager.inferBinaryName(location, file);
	}

	@Override
	public Iterable<JavaFileObject> list(
			Location location, String packageName, Set<Kind> kinds,
			boolean recurse)
		throws IOException {

		if ((location == StandardLocation.CLASS_PATH) && _verbose) {
			StringBundler sb = new StringBundler(9);

			sb.append("List for {kinds=");
			sb.append(kinds);
			sb.append(", location=");
			sb.append(location);
			sb.append(", packageName=");
			sb.append(packageName);
			sb.append(", recurse=");
			sb.append(recurse);
			sb.append(StringPool.CLOSE_CURLY_BRACE);

			_logger.log(Logger.LOG_INFO, sb.toString());
		}

		String packagePath = packageName.replace(
			CharPool.PERIOD, CharPool.SLASH);

		if (!packageName.startsWith("java.") &&
			(location == StandardLocation.CLASS_PATH)) {

			List<JavaFileObject> javaFileObjects = listFromDependencies(
				kinds, recurse, packagePath);

			if (!javaFileObjects.isEmpty() ||
				!_systemPackageNames.contains(packageName)) {

				return javaFileObjects;
			}
		}

		return fileManager.list(location, packagePath, kinds, recurse);
	}

	protected String getClassName(String resourceName) {
		if (resourceName.endsWith(".class")) {
			resourceName = resourceName.substring(0, resourceName.length() - 6);
		}

		return resourceName.replace(CharPool.SLASH, CharPool.PERIOD);
	}

	protected JavaFileObject getJavaFileObject(
		URL resourceURL, String resourceName) {

		String protocol = resourceURL.getProtocol();

		String className = getClassName(resourceName);

		if (protocol.equals("bundle") || protocol.equals("bundleresource")) {
			return new BundleJavaFileObject(className, resourceURL);
		}
		else if (protocol.equals("jar")) {
			try {
				return new JarJavaFileObject(
					className, resourceURL, resourceName);
			}
			catch (IOException ioe) {
				if (_verbose) {
					_logger.log(Logger.LOG_ERROR, ioe.getMessage(), ioe);
				}
			}
		}
		else if (protocol.equals("vfs")) {
			try {
				return new VfsJavaFileObject(
					className, resourceURL, resourceName);
			}
			catch (MalformedURLException murie) {
				if (_verbose) {
					_logger.log(Logger.LOG_ERROR, murie.getMessage(), murie);
				}
			}
		}

		return null;
	}

	protected void list(
		String packagePath, Kind kind, int options, BundleWiring bundleWiring,
		List<JavaFileObject> javaFileObjects) {

		Collection<String> resources = _resourceResolver.resolveResources(
			bundleWiring, packagePath, StringPool.STAR.concat(kind.extension),
			options);

		if ((resources == null) || resources.isEmpty()) {
			return;
		}

		for (String resourceName : resources) {
			URL resourceURL = _resourceResolver.getResource(
				bundleWiring, resourceName);

			JavaFileObject javaFileObject = getJavaFileObject(
				resourceURL, resourceName);

			if (javaFileObject == null) {
				if (_verbose) {
					_logger.log(
						Logger.LOG_INFO,
						"Unable to create Java file object for " + resourceURL);
				}

				continue;
			}

			if (_verbose) {
				_logger.log(Logger.LOG_INFO, "Created " + javaFileObject);
			}

			javaFileObjects.add(javaFileObject);
		}
	}

	protected List<JavaFileObject> listFromDependencies(
		Set<Kind> kinds, boolean recurse, String packagePath) {

		List<JavaFileObject> javaFileObjects = new ArrayList<>();

		int options = 0;

		if (recurse) {
			options = BundleWiring.LISTRESOURCES_RECURSE;
		}

		for (Kind kind : kinds) {
			if (kind.equals(Kind.CLASS)) {
				for (BundleWiring bundleWiring : _bundleWirings) {
					list(
						packagePath, kind, options, bundleWiring,
						javaFileObjects);
				}
			}

			if (javaFileObjects.isEmpty()) {
				list(
					packagePath, kind, options, _bundleWiring, javaFileObjects);
			}
		}

		return javaFileObjects;
	}

	private final BundleWiring _bundleWiring;
	private final Set<BundleWiring> _bundleWirings = new LinkedHashSet<>();
	private final Logger _logger;
	private final ResourceResolver _resourceResolver;
	private final Set<Object> _systemPackageNames = new HashSet<>();
	private final boolean _verbose;

}