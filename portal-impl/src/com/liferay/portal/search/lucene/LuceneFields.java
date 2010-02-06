/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Date;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Field;

/**
 * <a href="LuceneFields.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LuceneFields {

	public static String getUID(String portletId, long field1) {
		return getUID(portletId, String.valueOf(field1));
	}

	public static String getUID(String portletId, Long field1) {
		return getUID(portletId, field1.longValue());
	}

	public static String getUID(String portletId, String field1) {
		return getUID(portletId, field1, null);
	}

	public static String getUID(
		String portletId, long field1, String field2) {

		return getUID(portletId, String.valueOf(field1), field2);
	}

	public static String getUID(
		String portletId, Long field1, String field2) {

		return getUID(portletId, field1.longValue(), field2);
	}

	public static String getUID(
		String portletId, String field1, String field2) {

		return getUID(portletId, field1, field2, null);
	}

	public static String getUID(
		String portletId, String field1, String field2, String field3) {

		String uid = portletId + _UID_PORTLET + field1;

		if (field2 != null) {
			uid += _UID_FIELD + field2;
		}

		if (field3 != null) {
			uid += _UID_FIELD + field3;
		}

		return uid;
	}

	public static Field getDate(String field) {
		return getDate(field, new Date());
	}

	public static Field getDate(String field, Date date) {
		if (date == null) {
			return getDate(field);
		}
		else {
			return new Field(
				field,
				DateTools.dateToString(date, DateTools.Resolution.SECOND),
				Field.Store.YES, Field.Index.NOT_ANALYZED);
		}
	}

	public static Field getFile(String field, InputStream is, String fileExt) {
		LuceneFileExtractor fileExtractor =
			(LuceneFileExtractor)InstancePool.get(
				PropsValues.LUCENE_FILE_EXTRACTOR);

		return fileExtractor.getFile(field, is, fileExt);
	}

	public static Field getFile(String field, byte[] bytes, String fileExt) {
		LuceneFileExtractor fileExtractor =
			(LuceneFileExtractor)InstancePool.get(
				PropsValues.LUCENE_FILE_EXTRACTOR);

		return fileExtractor.getFile(field, bytes, fileExt);
	}

	public static Field getFile(String field, File file, String fileExt)
		throws IOException {

		LuceneFileExtractor fileExtractor =
			(LuceneFileExtractor)InstancePool.get(
				PropsValues.LUCENE_FILE_EXTRACTOR);

		return fileExtractor.getFile(field, file, fileExt);
	}

	public static Field getKeyword(String field, double keyword) {
		return getKeyword(field, String.valueOf(keyword));
	}

	public static Field getKeyword(String field, long keyword) {
		return getKeyword(field, String.valueOf(keyword));
	}

	public static Field getKeyword(String field, Long keyword) {
		return getKeyword(field, keyword.longValue());
	}

	public static Field getKeyword(String field, String keyword) {
		//keyword = KeywordsUtil.escape(keyword);

		Field fieldObj = new Field(
			field, keyword, Field.Store.YES, Field.Index.NOT_ANALYZED);

		//fieldObj.setBoost(0);

		return fieldObj;
	}

	public static Field getText(String field, String text) {
		return new Field(field, text, Field.Store.YES, Field.Index.ANALYZED);
	}

	public static Field getText(String field, StringBuilder sb) {
		return getText(field, sb.toString());
	}

	private static final String _UID_PORTLET = "_PORTLET_";

	private static final String _UID_FIELD = "_FIELD_";

}