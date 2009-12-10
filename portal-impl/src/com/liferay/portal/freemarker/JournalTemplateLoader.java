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

package com.liferay.portal.freemarker;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * <a href="JournalTemplateLoader.java.html"><i>View Source</i></a>
 *
 * @author Mika Koivisto
 */
public class JournalTemplateLoader extends FreeMarkerTemplateLoader {

	public Object findTemplateSource(String name)
		throws IOException {

		int pos = name.indexOf(JOURNAL_SEPARATOR + StringPool.SLASH);

		try {
			if (pos != -1) {
				int x = name.indexOf(StringPool.SLASH, pos);
				int y = name.indexOf(StringPool.SLASH, x + 1);
				int z = name.indexOf(StringPool.SLASH, y + 1);

				long companyId = GetterUtil.getLong(name.substring(x + 1, y));
				long groupId = GetterUtil.getLong(name.substring(y + 1, z));
				String templateId = name.substring(z + 1);

				if (_log.isDebugEnabled()) {
					_log.debug("Loading {companyId=" + companyId + ",groupId=" +
						groupId + ",templateId=" + templateId + "}");
				}

				JournalTemplate template =
					JournalTemplateLocalServiceUtil.getTemplate(
						groupId, templateId);

				return template;
			}
		}
		catch (PortalException pe) {
			return null;
		}
		catch (SystemException se) {
			throw new IOException(se);
		}

		return null;
	}

	public long getLastModified(Object source) {

		if (source instanceof JournalTemplate) {
			JournalTemplate template = (JournalTemplate) source;

			return template.getModifiedDate().getTime();

		}
		return -1;
	}

	public Reader getReader(Object source, String encoding)
		throws IOException {

		if (source instanceof JournalTemplate) {
			JournalTemplate template = (JournalTemplate) source;

			String buffer = template.getXsl();

			return new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(buffer.getBytes()), encoding));
		}

		return null;
	}

	private static Log _log =
		LogFactoryUtil.getLog(JournalTemplateLoader.class);

}