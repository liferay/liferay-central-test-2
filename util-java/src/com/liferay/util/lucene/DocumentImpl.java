/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.util.lucene;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Date;
import java.util.Map;

/**
 * <a href="DocumentImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DocumentImpl implements Document {

	public DocumentImpl(org.apache.lucene.document.Document doc) {
		_doc = doc;
	}

	public void add(Field field) {
	}

	public void addDate(String name, Date value) {
	}

	public void addFile(String name, InputStream is, String fileExt)
		throws IOException {
	}

	public void addFile(String name, byte[] bytes, String fileExt)
		throws IOException {
	}

	public void addFile(String name, File file, String fileExt)
		throws IOException {
	}

	public void addKeyword(String name, double value) {
	}

	public void addKeyword(String name, int value) {
	}

	public void addKeyword(String name, long value) {
	}

	public void addKeyword(String name, String value) {
	}

	public void addKeyword(String name, String[] values) {
	}

	public void addModifiedDate() {
	}

	public void addText(String name, long value) {
	}

	public void addText(String name, String value) {
	}

	public void addUID(String portletId, long field1) {
	}

	public void addUID(String portletId, Long field1) {
	}

	public void addUID(String portletId, String field1) {
	}

	public void addUID(String portletId, long field1, String field2) {
	}

	public void addUID(String portletId, Long field1, String field2) {
	}

	public void addUID(String portletId, String field1, String field2) {
	}

	public void addUID(
		String portletId, String field1, String field2, String field3) {
	}

	public String get(String name) {
		return  _doc.get(name);
	}

	public String[] getValues(String name) {
		return null;
	}

	public Map<String, Field> getFields() {
		return null;
	}

	private org.apache.lucene.document.Document _doc;

}