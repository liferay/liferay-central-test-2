/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.sample.lar.plugin;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;

import java.util.Date;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LARPlugin.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Auge
 *
 */
public class LARPlugin implements PortletDataHandler {

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		try {
			long exportDate = System.currentTimeMillis();

			if (_log.isInfoEnabled()) {
				_log.info("Exporting LAR on " + new Date(exportDate));
			}

			prefs.setValue("last-export-date", String.valueOf(exportDate));

			prefs.store();

			String data =
				LARPlugin.class.getName() + " " + new Date(exportDate);

			if (_log.isInfoEnabled()) {
				_log.info("Exporting data " + data);
			}

			// Access the ZipWriter from the PortletDataContext

			ZipWriter zipWriter = context.getZipWriter();

			if (zipWriter != null) {
				if (_log.isInfoEnabled()) {
					_log.info("Writing to zip");
				}

				zipWriter.addEntry(
					portletId + "/README.txt", "test writing to zip");
			}

			return data;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletPreferences importData(PortletDataContext context,
			String portletId, PortletPreferences prefs, String data)
			throws PortletDataException {

		try {
			long importDate = System.currentTimeMillis();

			if (_log.isInfoEnabled()) {
				_log.info("Importing LAR on " + new Date(importDate));
			}

			prefs.setValue("last-import-date", String.valueOf(importDate));

			if (_log.isInfoEnabled()) {
				_log.info("Importing data " + data);
			}

			ZipReader zipReader = context.getZipReader();

			if (zipReader != null) {
				_log.info(
					"From README file:\n\n" +
						zipReader.getEntryAsString(portletId + "/README.txt"));
			}

			return prefs;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	private static Log _log = LogFactory.getLog(LARPlugin.class);

}