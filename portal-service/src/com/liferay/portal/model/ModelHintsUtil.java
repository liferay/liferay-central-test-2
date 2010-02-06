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

package com.liferay.portal.model;

import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

/**
 * <a href="ModelHintsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ModelHintsUtil {

	public static Map<String, String> getDefaultHints(String model) {
		return getModelHints().getDefaultHints(model);
	}

	public static Element getFieldsEl(String model, String field) {
		return getModelHints().getFieldsEl(model, field);
	}

	public static ModelHints getModelHints() {
		return _modelHints;
	}

	public static List<String> getModels() {
		return getModelHints().getModels();
	}

	public static String getType(String model, String field) {
		return getModelHints().getType(model, field);
	}

	public static Map<String, String> getHints(String model, String field) {
		return getModelHints().getHints(model, field);
	}

	public static boolean isLocalized(String model, String field) {
		return getModelHints().isLocalized(model, field);
	}

	public static void read(ClassLoader classLoader, String source)
		throws Exception {

		getModelHints().read(classLoader, source);
	}

	public static String trimString(String model, String field, String value) {
		return getModelHints().trimString(model, field, value);
	}

	public void setModelHints(ModelHints modelHints) {
		_modelHints = modelHints;
	}

	private static ModelHints _modelHints;

}