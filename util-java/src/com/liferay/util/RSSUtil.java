/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.util;

import de.nava.informa.core.ChannelExporterIF;
import de.nava.informa.core.ChannelIF;
import de.nava.informa.exporters.RSS_0_91_Exporter;
import de.nava.informa.exporters.RSS_1_0_Exporter;
import de.nava.informa.exporters.RSS_2_0_Exporter;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="RSSUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class RSSUtil {

	public static final String ENCODING = "UTF-8";

	public static String export(ChannelIF channel, double version) {
		StringWriter writer = new StringWriter();

		try {
			ChannelExporterIF exporter = null;

			if (version == 0.91) {
				exporter = new RSS_0_91_Exporter(writer, ENCODING);
			}
			else if (version == 1.0) {
				exporter = new RSS_1_0_Exporter(writer, ENCODING);
			}
			else {
				exporter = new RSS_2_0_Exporter(writer, ENCODING);
			}

			exporter.write(channel);
		}
		catch (IOException ioe) {
			_log.error(ioe);
		}

		return writer.getBuffer().toString();
	}

	private static Log _log = LogFactory.getLog(RSSUtil.class);

}