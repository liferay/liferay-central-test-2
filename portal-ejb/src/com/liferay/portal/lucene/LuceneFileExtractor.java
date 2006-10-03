/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.lucene;

import com.liferay.util.StringPool;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.core.PropertyId;
import org.apache.jackrabbit.core.query.HTMLTextFilter;
import org.apache.jackrabbit.core.query.MsExcelTextFilter;
import org.apache.jackrabbit.core.query.MsPowerPointTextFilter;
import org.apache.jackrabbit.core.query.MsWordTextFilter;
import org.apache.jackrabbit.core.query.OpenOfficeTextFilter;
import org.apache.jackrabbit.core.query.PdfTextFilter;
import org.apache.jackrabbit.core.query.RTFTextFilter;
import org.apache.jackrabbit.core.query.TextFilter;
import org.apache.jackrabbit.core.query.XMLTextFilter;
import org.apache.jackrabbit.core.query.lucene.TextPlainTextFilter;
import org.apache.jackrabbit.core.state.PropertyState;
import org.apache.jackrabbit.core.value.InternalValue;
import org.apache.lucene.document.Field;

/**
 * <a href="LuceneFileExtractor.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LuceneFileExtractor {

	public Field getFile(String field, InputStream is, String fileExt) {
		String text = null;

		try {
			fileExt = fileExt.toLowerCase();

			PropertyId id = null;
			PropertyState state = new PropertyState(id, 1, true);
			InternalValue value = InternalValue.create(is);

			state.setValues(new InternalValue[] {value});

			TextFilter filter = null;

			if (fileExt.equals(".doc")) {
				filter = new MsWordTextFilter();
			}
			else if (fileExt.equals(".htm") || fileExt.equals(".html")) {
				filter = new HTMLTextFilter();
			}
			else if (fileExt.equals(".odp") || fileExt.equals(".ods") ||
					 fileExt.equals(".odt")) {

				filter = new OpenOfficeTextFilter();
			}
			else if (fileExt.equals(".pdf")) {
				filter = new PdfTextFilter();
			}
			else if (fileExt.equals(".ppt")) {
				filter = new MsPowerPointTextFilter();
			}
			else if (fileExt.equals(".rtf")) {
				filter = new RTFTextFilter();
			}
			else if (fileExt.equals(".txt")) {
				filter = new TextPlainTextFilter();
			}
			else if (fileExt.equals(".xls")) {
				filter = new MsExcelTextFilter();
			}
			else if (fileExt.equals(".xml")) {
				filter = new XMLTextFilter();
			}

			if (filter != null) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Using filter " + filter.getClass().getName() +
							" for extension " + fileExt);
				}

				StringBuffer sb = new StringBuffer();

				Map fields = filter.doFilter(
					state, System.getProperty("encoding"));

				Iterator itr = fields.keySet().iterator();

				while (itr.hasNext()) {
					String key = (String)itr.next();

					Reader reader = (Reader)fields.get(key);

					int i;

					while ((i = reader.read()) != -1) {
						sb.append((char) i);
					}

					reader.close();
				}

				text = sb.toString();
			}
			else {
				if (_log.isInfoEnabled()) {
					_log.info("No filter found for extension " + fileExt);
				}
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Filter returned text:\n\n" + text);
		}

		if (text == null) {
			text = StringPool.BLANK;
		}

		return LuceneFields.getText(field, text);
	}

	public Field getFile(String field, byte[] byteArray, String fileExt)
		throws IOException {

		InputStream in = new BufferedInputStream(
			new ByteArrayInputStream(byteArray));

		return getFile(field, in, fileExt);
	}

	public Field getFile(String field, File file, String fileExt)
		throws IOException {

		InputStream in = new FileInputStream(file);

		return getFile(field, in, fileExt);
	}

	private static Log _log = LogFactory.getLog(LuceneFileExtractor.class);

}