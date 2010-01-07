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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.util.InitUtil;
import com.liferay.portlet.translator.model.Translation;
import com.liferay.portlet.translator.util.TranslationWebCacheItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * <a href="LangBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LangBuilder {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		if (args.length == 2) {
			new LangBuilder(args[0], args[1], null);
		}
		else if (args.length == 3) {
			new LangBuilder(args[0], args[1], args[2]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public LangBuilder(String langDir, String langFile, String langCode) {
		try {
			_langDir = langDir;
			_langFile = langFile;

			File renameKeysFile = new File(_langDir + "/rename.properties");

			if (renameKeysFile.exists()) {
				_renameKeys = PropertiesUtil.load(
					FileUtil.read(renameKeysFile));
			}

			String content = _orderProps(
				new File(_langDir + "/" + _langFile + ".properties"));

			if (Validator.isNotNull(langCode) && !langCode.startsWith("$")) {
				_createProps(content, langCode);
			}
			else {
				_createProps(content, "ar"); // Arabic
				_createProps(content, "eu"); // Basque
				_createProps(content, "bg"); // Bulgarian
				_createProps(content, "ca"); // Catalan
				_createProps(content, "zh_CN"); // Chinese (China)
				_createProps(content, "zh_TW"); // Chinese (Taiwan)
				_createProps(content, "cs"); // Czech
				_createProps(content, "nl"); // Dutch
				_createProps(content, "fi"); // Finnish
				_createProps(content, "fr"); // French
				_createProps(content, "gl"); // Galician
				_createProps(content, "de"); // German
				_createProps(content, "el"); // Greek
				_createProps(content, "hu"); // Hungarian
				_createProps(content, "it"); // Italian
				_createProps(content, "ja"); // Japanese
				_createProps(content, "ko"); // Korean
				_createProps(content, "nb"); // Norwegian Bokmål
				_createProps(content, "fa"); // Persian
				_createProps(content, "pl"); // Polish
				_createProps(content, "pt_BR"); // Brazilian Portuguese
				_createProps(content, "pt_PT"); // Portuguese
				_createProps(content, "ru"); // Russian
				_createProps(content, "sk"); // Slovak
				_createProps(content, "es"); // Spanish
				_createProps(content, "sv"); // Swedish
				_createProps(content, "tr"); // Turkish
				_createProps(content, "vi"); // Vietnamese
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _createProps(String content, String languageId)
		throws IOException {

		File propsFile = new File(
			_langDir + "/" + _langFile + "_" + languageId + ".properties");

		Properties props = new Properties();

		if (propsFile.exists()) {
			props.load(new FileInputStream(propsFile));
		}

		File nativePropsFile = new File(
			_langDir + "/" + _langFile + "_" + languageId +
				".properties.native");

		Properties nativeProps = new Properties();

		if (nativePropsFile.exists()) {
			nativeProps.load(new FileInputStream(nativePropsFile));
		}

		String translationId = "en_" + languageId;

		if (translationId.equals("en_pt_BR")) {
			translationId = "en_pt";
		}
		else if (translationId.equals("en_pt_PT")) {
			translationId = "en_pt";
		}
		else if (translationId.equals("en_zh_CN")) {
			translationId = "en_zh";
		}
		else if (translationId.equals("en_zh_TW")) {
			translationId = "en_zt";
		}

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(content));
		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			new FileWriter(nativePropsFile));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			line = line.trim();

			int pos = line.indexOf("=");

			if (pos != -1) {
				String key = line.substring(0, pos);
				String value = line.substring(pos + 1, line.length());

				String nativeValue = nativeProps.getProperty(key);
				String translatedText = props.getProperty(key);

				if ((nativeValue == null) && (translatedText == null) &&
					(_renameKeys != null)) {

					String renameKey = _renameKeys.getProperty(key);

					if (renameKey != null) {
						nativeValue = nativeProps.getProperty(renameKey);
						translatedText = props.getProperty(renameKey);
					}
				}

				if ((translatedText != null) &&
					((translatedText.indexOf("Babel Fish") != -1) ||
					 (translatedText.indexOf("Yahoo! - 999") != -1))) {

					translatedText = "";
				}
				else if (nativeValue != null) {
					if (nativeValue.endsWith(_AUTOMATIC_COPY)) {
						translatedText += _AUTOMATIC_COPY;
					}
					else if (nativeValue.endsWith(_AUTOMATIC_TRANSLATION)) {
						translatedText += _AUTOMATIC_TRANSLATION;
					}
				}

				if ((translatedText == null) || translatedText.equals("")) {
					if (line.indexOf("{") != -1 || line.indexOf("<") != -1) {
						translatedText = value + _AUTOMATIC_COPY;
					}
					else if (key.equals("lang.dir")) {
						translatedText = "ltr";
					}
					else if (key.equals("lang.line.begin")) {
						translatedText = "left";
					}
					else if (key.equals("lang.line.end")) {
						translatedText = "right";
					}
					else if (translationId.equals("en_el") &&
							 (key.equals("enabled") || key.equals("on") ||
							  key.equals("on-date"))) {

						translatedText = "";
					}
					else if (translationId.equals("en_es") &&
							 key.equals("am")) {

						translatedText = "";
					}
					else if (translationId.equals("en_it") &&
							 key.equals("am")) {

						translatedText = "";
					}
					else if (translationId.equals("en_ja") &&
							 (key.equals("any") || key.equals("anytime") ||
							  key.equals("down") || key.equals("on") ||
							  key.equals("on-date") || key.equals("the"))) {

						translatedText = "";
					}
					else if (translationId.equals("en_ko") &&
							 key.equals("the")) {

						translatedText = "";
					}
					else {
						translatedText = _translate(
							translationId, key, value, 0);
					}
				}

				if (Validator.isNotNull(translatedText)) {
					if ((translatedText.indexOf("Babel Fish") != -1) ||
						(translatedText.indexOf("Yahoo! - 999") != -1)) {

						throw new IOException(
							"IP was blocked because of over usage. Please " +
								"use another IP.");
					}

					if (translatedText.indexOf("&#39;") != -1) {
						translatedText = StringUtil.replace(
							translatedText, "&#39;", "\'");
					}

					unsyncBufferedWriter.write(key + "=" + translatedText);

					unsyncBufferedWriter.newLine();
					unsyncBufferedWriter.flush();
				}
				else if (nativeProps.containsKey(key)) {
					unsyncBufferedWriter.write(key + "=");

					unsyncBufferedWriter.newLine();
					unsyncBufferedWriter.flush();
				}
			}
			else {
				unsyncBufferedWriter.write(line);

				unsyncBufferedWriter.newLine();
				unsyncBufferedWriter.flush();
			}
		}

		unsyncBufferedReader.close();
		unsyncBufferedWriter.close();
	}

	private String _orderProps(File propsFile) throws IOException {
		String content = FileUtil.read(propsFile);

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(content));
		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			new FileWriter(propsFile));

		Set<String> messages = new TreeSet<String>();

		boolean begin = false;

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			int pos = line.indexOf("=");

			if (pos != -1) {
				String key = line.substring(0, pos);
				String value = line.substring(pos + 1, line.length());

				messages.add(key + "=" + value);
			}
			else {
				if (begin == true && line.equals("")) {
					_sortAndWrite(unsyncBufferedWriter, messages);
				}

				if (line.equals("")) {
					begin = !begin;
				}

				unsyncBufferedWriter.write(line);
				unsyncBufferedWriter.newLine();
			}

			unsyncBufferedWriter.flush();
		}

		if (messages.size() > 0) {
			_sortAndWrite(unsyncBufferedWriter, messages);
		}

		unsyncBufferedReader.close();
		unsyncBufferedWriter.close();

		return FileUtil.read(propsFile);
	}

	private void _sortAndWrite(
			UnsyncBufferedWriter unsyncBufferedWriter, Set<String> messages)
		throws IOException {

		String[] messagesArray = messages.toArray(new String[messages.size()]);

		for (int i = 0; i < messagesArray.length; i++) {
			unsyncBufferedWriter.write(messagesArray[i]);
			unsyncBufferedWriter.newLine();
		}

		messages.clear();
	}

	private String _translate(
		String translationId, String key, String fromText, int limit) {

		if (translationId.equals("en_ar") ||
			translationId.equals("en_eu") ||
			translationId.equals("en_bg") ||
			translationId.equals("en_ca") ||
			translationId.equals("en_cs") ||
			translationId.equals("en_fi") ||
			translationId.equals("en_gl") ||
			translationId.equals("en_hu") ||
			translationId.equals("en_nb") ||
			translationId.equals("en_fa") ||
			translationId.equals("en_pl") ||
			translationId.equals("en_ru") ||
			translationId.equals("en_sk") ||
			translationId.equals("en_sv") ||
			translationId.equals("en_tr") ||
			translationId.equals("en_vi")) {

			// Automatic translator does not support Arabic, Basque, Bulgarian,
			// Catalan, Czech, Finnish, Galician, Hungarian, Norwegian Bokmål,
			// Persian, Polish, Russian, Slovak, Swedish, Turkish, or Vietnamese

			return null;
		}

		// Limit the number of retries to 3

		if (limit == 3) {
			return null;
		}

		String toText = null;

		try {
			System.out.println(
				"Translating " + translationId + " " + key + " " + fromText);

			WebCacheItem wci = new TranslationWebCacheItem(
				translationId, fromText);

			Translation translation = (Translation)wci.convert("");

			toText = translation.getToText();

			if ((toText != null) &&
				(toText.indexOf("Babel Fish") != -1)) {

				toText = null;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Keep trying

		if (toText == null) {
			return _translate(translationId, key, fromText, ++limit);
		}

		if (Validator.isNotNull(toText)) {
			toText += _AUTOMATIC_TRANSLATION;
		}

		return toText;
	}

	private static final String _AUTOMATIC_COPY = " (Automatic Copy)";

	private static final String _AUTOMATIC_TRANSLATION =
		" (Automatic Translation)";

	private String _langDir;
	private String _langFile;
	private Properties _renameKeys;

}