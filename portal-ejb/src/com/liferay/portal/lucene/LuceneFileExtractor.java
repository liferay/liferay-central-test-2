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

import com.liferay.util.GetterUtil;
import com.liferay.util.poi.XLSTextStripper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Field;

import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

import org.textmining.text.extraction.WordExtractor;

/**
 * <a href="LuceneFileExtractor.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LuceneFileExtractor {

	public Field getFile(String field, InputStream is, String fileExt) {
		fileExt = fileExt.toLowerCase();

		Reader reader = new BufferedReader(new InputStreamReader(is));

		String text = null;

		try {
			if (fileExt.equals(".doc")) {
				try {
					WordExtractor wordExtractor = new WordExtractor();

					text = wordExtractor.extractText(is);
					text = GetterUtil.getString(text);

					/*WordDocument wordDocument = new WordDocument(is);

					StringWriter stringWriter = new StringWriter();

					wordDocument.writeAllText(stringWriter);

					text = stringWriter.toString();

					stringWriter.close();*/
				}
				catch (Exception e1) {
					_log.error(e1.getMessage());
				}
			}
			else if (fileExt.equals(".htm") || fileExt.equals(".html")) {
				try {
					DefaultStyledDocument dsd = new DefaultStyledDocument();

					HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
					htmlEditorKit.read(reader, dsd, 0);

					text = dsd.getText(0, dsd.getLength());
				}
				catch (Exception e) {
					_log.error(e);
				}
			}
			else if (fileExt.equals(".pdf")) {
				PDDocument pdDoc= null;
				StringWriter stringWriter = null;

				try {
					PDFParser parser = new PDFParser(is);

					parser.parse();

					pdDoc = parser.getPDDocument();
					stringWriter = new StringWriter();

					PDFTextStripper stripper = new PDFTextStripper();

					stripper.setLineSeparator("\n");
					stripper.writeText(pdDoc, stringWriter);

					text = stringWriter.toString();
				}
				catch (Exception e) {
					_log.error(e);
				}
				finally {
					try {
						stringWriter.close();
					}
					catch (Exception e) {
					}

					try {
						pdDoc.close();
					}
					catch (Exception e) {
					}
				}
			}
			else if (fileExt.equals(".rtf")) {
				try {
					DefaultStyledDocument dsd = new DefaultStyledDocument();

					RTFEditorKit rtfEditorKit = new RTFEditorKit();
					rtfEditorKit.read(reader, dsd, 0);

					text = dsd.getText(0, dsd.getLength());
				}
				catch (Exception e) {
					_log.error(e);
				}
			}
			else if (fileExt.equals(".xls")) {
				try {
					XLSTextStripper stripper = new XLSTextStripper(is);

					text = stripper.getText();
				}
				catch (Exception e) {
					_log.error(e);
				}
			}

			if (text != null) {
				return new Field(
					field, text, Field.Store.YES, Field.Index.TOKENIZED);
			}
			else {
				return new Field(field, reader);
			}
		}
		finally {
			if (text != null) {
				try {
					reader.close();
				}
				catch (Exception e) {
				}

				try {
					is.close();
				}
				catch (Exception e) {
				}
			}
		}

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