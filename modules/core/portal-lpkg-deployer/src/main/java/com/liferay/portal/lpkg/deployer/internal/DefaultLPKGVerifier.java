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

package com.liferay.portal.lpkg.deployer.internal;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.lpkg.deployer.LPKGVerifier;
import com.liferay.portal.lpkg.deployer.LPKGVerifyException;
import com.liferay.portal.target.platform.indexer.IndexValidator;
import com.liferay.portal.target.platform.indexer.IndexValidatorFactory;
import com.liferay.portal.target.platform.indexer.Indexer;
import com.liferay.portal.target.platform.indexer.IndexerFactory;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Version;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true)
public class DefaultLPKGVerifier implements LPKGVerifier {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public List<Bundle> verify(File lpkgFile) {
		try (ZipFile zipFile = new ZipFile(lpkgFile)) {
			ZipEntry zipEntry = zipFile.getEntry(
				"liferay-marketplace.properties");

			if (zipEntry == null) {
				throw new LPKGVerifyException(
					lpkgFile + " does not have liferay-marketplace.properties");
			}

			Properties properties = new Properties();

			try (InputStream inputStream = zipFile.getInputStream(zipEntry)) {
				properties.load(inputStream);
			}

			String symbolicName = properties.getProperty("title");

			if (Validator.isNull(symbolicName)) {
				throw new LPKGVerifyException(
					lpkgFile + " does not have a valid symbolic name");
			}

			Version version = null;

			String versionString = properties.getProperty("version");

			try {
				version = new Version(versionString);
			}
			catch (IllegalArgumentException iae) {
				throw new LPKGVerifyException(
					lpkgFile + " does not have a valid version: " +
						versionString,
					iae);
			}

			_doIndexValidation(lpkgFile, symbolicName, version);

			List<Bundle> oldBundles = new ArrayList<>();

			for (Bundle bundle : _bundleContext.getBundles()) {
				if (!symbolicName.equals(bundle.getSymbolicName())) {
					continue;
				}

				int value = version.compareTo(bundle.getVersion());

				if (value > 0) {
					oldBundles.add(bundle);
				}
				else if (value == 0) {
					String path = lpkgFile.getCanonicalPath();

					if (path.equals(bundle.getLocation())) {
						continue;
					}

					throw new LPKGVerifyException(
						"Existing LPKG bundle " + bundle + " has the same " +
							"symbolic name and version as LPKG file " +
								lpkgFile);
				}
				else {
					throw new LPKGVerifyException(
						"Existing LPKG bundle " + bundle +
							" is a newer version of LPKG file " + lpkgFile);
				}
			}

			return oldBundles;
		}
		catch (IOException ioe) {
			throw new LPKGVerifyException(ioe);
		}
	}

	private void _doIndexValidation(
		File lpkgFile, String symbolicName, Version version) {

		try {
			Indexer indexer = _indexerFactory.createLPKGIndexer(lpkgFile);

			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			indexer.index(unsyncByteArrayOutputStream);

			Path indexFilePath = Paths.get(
				PropsValues.MODULE_FRAMEWORK_BASE_DIR,
				Indexer.DIR_NAME_TARGET_PLATFORM,
				symbolicName + "-" + version + "-index.xml");

			Files.write(
				indexFilePath, unsyncByteArrayOutputStream.toByteArray());

			if (_log.isInfoEnabled()) {
				_log.info("Wrote index " + indexFilePath);
			}

			IndexValidator indexValidator = _indexValidatorFactory.create(
				_getTargetPlatformIndexURIs());

			long start = System.currentTimeMillis();

			try {
				List<String> messages = indexValidator.validate(
					Collections.singletonList(indexFilePath.toUri()));

				if (!messages.isEmpty()) {
					Files.delete(indexFilePath);

					StringBundler sb = new StringBundler(
						(messages.size() * 3) + 1);

					sb.append("LPKG validation failed with {");

					for (String message : messages) {
						sb.append("[");
						sb.append(message);
						sb.append("], ");
					}

					sb.setIndex(sb.index() - 1);

					sb.append("]}");

					throw new LPKGVerifyException(sb.toString());
				}
			}
			finally {
				if (_log.isInfoEnabled()) {
					long duration = System.currentTimeMillis() - start;

					_log.info(
						String.format(
							"LPKG validation time %02d:%02ds",
							MILLISECONDS.toMinutes(duration),
							MILLISECONDS.toSeconds(duration % Time.MINUTE)));
				}
			}
		}
		catch (LPKGVerifyException lpkgve) {
			throw lpkgve;
		}
		catch (Exception e) {
			throw new LPKGVerifyException(e);
		}
	}

	private List<URI> _getTargetPlatformIndexURIs() throws IOException {
		List<URI> uris = new ArrayList<>();

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				Paths.get(
					PropsValues.MODULE_FRAMEWORK_BASE_DIR,
					Indexer.DIR_NAME_TARGET_PLATFORM),
				"*.xml")) {

			Iterator<Path> iterator = directoryStream.iterator();

			while (iterator.hasNext()) {
				Path path = iterator.next();

				uris.add(path.toUri());
			}
		}

		return uris;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultLPKGVerifier.class);

	private BundleContext _bundleContext;

	@Reference
	private IndexerFactory _indexerFactory;

	@Reference
	private IndexValidatorFactory _indexValidatorFactory;

}