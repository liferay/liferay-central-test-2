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

package com.liferay.portlet.pageratings.lar;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.util.PortletKeys;
import com.liferay.util.MapUtil;

import java.util.Map;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PageRatingsPortletDataHandlerImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Bruno Farache
 *
 */
public class PageRatingsPortletDataHandlerImpl implements PortletDataHandler {

	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {_enableExport};
	}

	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException{

		return new PortletDataHandlerControl[] {_enableImport};
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		Map parameterMap = context.getParameterMap();

		boolean exportData = MapUtil.getBoolean(
			parameterMap, _EXPORT_PAGE_RATINGS_DATA);
		boolean staging = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.STAGING);

		if (_log.isDebugEnabled()) {
			if (exportData) {
				_log.debug("Exporting data is enabled");
			}
			else {
				_log.debug("Exporting data is disabled");
			}
		}

		if (!exportData && !staging) {
			return null;
		}

		try {
			context.addRatingsEntries(
				Layout.class, new Long(context.getPlid()));

			return String.valueOf(context.getPlid());
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs, String data)
		throws PortletDataException {

		Map parameterMap = context.getParameterMap();

		boolean importData = MapUtil.getBoolean(
			parameterMap, _IMPORT_PAGE_RATINGS_DATA);
		boolean staging = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.STAGING);

		if (_log.isDebugEnabled()) {
			if (importData) {
				_log.debug("Importing data is enabled");
			}
			else {
				_log.debug("Importing data is disabled");
			}
		}

		if (!importData && !staging) {
			return null;
		}

		try {
			context.importRatingsEntries(
				Layout.class, data, new Long(context.getPlid()));

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	private static final String _EXPORT_PAGE_RATINGS_DATA =
		"export-" + PortletKeys.PAGE_RATINGS + "-data";

	private static final String _IMPORT_PAGE_RATINGS_DATA =
		"import-" + PortletKeys.PAGE_RATINGS + "-data";

	private static final PortletDataHandlerBoolean _enableExport =
		new PortletDataHandlerBoolean(_EXPORT_PAGE_RATINGS_DATA, true, null);

	private static final PortletDataHandlerBoolean _enableImport =
		new PortletDataHandlerBoolean(_IMPORT_PAGE_RATINGS_DATA, true, null);

	private static Log _log =
		LogFactory.getLog(PageRatingsPortletDataHandlerImpl.class);

}