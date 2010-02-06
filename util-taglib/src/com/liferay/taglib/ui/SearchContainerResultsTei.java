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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * <a href="SearchContainerResultsTei.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class SearchContainerResultsTei extends TagExtraInfo {

	public VariableInfo[] getVariableInfo(TagData data) {
		String resultsVar = data.getAttributeString("resultsVar");

		if (Validator.isNull(resultsVar)) {
			resultsVar = SearchContainerResultsTag.DEFAULT_RESULTS_VAR;
		}

		String totalVar = data.getAttributeString("totalVar");

		if (Validator.isNull(totalVar)) {
			totalVar = SearchContainerResultsTag.DEFAULT_TOTAL_VAR;
		}

		return new VariableInfo[] {
			new VariableInfo(
				resultsVar, List.class.getName(), true, VariableInfo.AT_BEGIN),
			new VariableInfo(
				totalVar, Integer.class.getName(), true, VariableInfo.AT_BEGIN)
		};
	}

}