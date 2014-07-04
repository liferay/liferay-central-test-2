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

package com.liferay.ip.geocoder.internal;

import com.liferay.ip.geocoder.IPGeocoder;
import com.liferay.ip.geocoder.IPInfo;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

import java.util.Map;

import org.apache.log4j.Logger;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

import org.tukaani.xz.XZInputStream;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
@Component(
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	name = "IPGeocoder",
	property = {
		"ip.geocoder.file.path=",
		"ip.geocoder.file.url=http://cdn.files.liferay.com/mirrors/" +
			"geolite.maxmind.com/download/geoip/database/GeoLiteCity.dat.xz"
	},
	service = IPGeocoder.class)
public class IPGeocoderImpl implements IPGeocoder {

	@Activate
	public void activate(Map<String, String> properties) {
		if (_lookupService != null) {
			return;
		}

		String filePath = properties.get("ip.geocoder.file.path");

		if ((filePath == null) || (filePath.equals(""))) {
			filePath =
				System.getProperty("java.io.tmpdir") +
					"/liferay/GeoIP/GeoIPCity.dat";
		}

		String fileURL = properties.get("ip.geocoder.file.url");

		try {
			File ipGeocoderFile = getIPGeocoderFile(
				filePath, fileURL, false);

			_lookupService = new LookupService(
				ipGeocoderFile, LookupService.GEOIP_MEMORY_CACHE);
		}
		catch (IOException ioe) {
			_logger.error(ioe.getMessage());
		}
	}

	@Deactivate
	public void deactivate(Map<String, String> properties) {
		_lookupService = null;
	}

	@Override
	public IPInfo getIPInfo(String ipAddress) {
		if (_lookupService != null) {
			Location location = _lookupService.getLocation(ipAddress);

			return new IPInfo(ipAddress, location);
		}
		else {
			return null;
		}
	}

	@Modified
	public void modified(final Map<String, String> properties) {
		if (properties.containsKey("ip.geocoder.file.path") ||
			properties.containsKey("ip.geocoder.file.url")) {

			_lookupService = null;

			activate(properties);
		}
	}

	protected File getIPGeocoderFile(
			String filePath, String fileURL, boolean forceDownload)
		throws IOException {

		File file = new File(filePath);

		if (file.exists() && !forceDownload) {
			return file;
		}

		synchronized (this) {
			if (_logger.isInfoEnabled()) {
				_logger.info(
					"Downloading Geolocation Data from " + fileURL);
			}

			URL url = new URL(fileURL);

			URLConnection con = url.openConnection();

			String tmpDir = System.getProperty("java.io.tmpdir");

			File tarFile = new File(
				tmpDir + "/liferay/GeoIP/GeoIPCity.dat.xz");

			write(tarFile, con.getInputStream());

			write(file, new XZInputStream(new FileInputStream(tarFile)));
		}

		return file;
	}

	private void mkdirsParentFile(File file) {
		File parentFile = file.getParentFile();

		if (parentFile == null) {
			return;
		}

		try {
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
		}
		catch (SecurityException se) {

			// We may have the permission to write a specific file without
			// having the permission to check if the parent file exists

		}
	}

	private void write(File file, InputStream is) throws IOException {
		mkdirsParentFile(file);

		BufferedInputStream bis = new BufferedInputStream(is);

		BufferedOutputStream bos = new BufferedOutputStream(
			new FileOutputStream(file));

		int i;

		while ((i = bis.read()) != -1) {
			bos.write(i);
		}

		bos.flush();
		bis.close();
	}

	private static Logger _logger = Logger.getLogger(IPGeocoderImpl.class);

	private static LookupService _lookupService;

}