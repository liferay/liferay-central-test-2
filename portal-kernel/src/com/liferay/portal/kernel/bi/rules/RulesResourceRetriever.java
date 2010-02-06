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

package com.liferay.portal.kernel.bi.rules;

import com.liferay.portal.kernel.resource.ResourceRetriever;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;

/**
 * <a href="RulesResourceRetriever.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class RulesResourceRetriever implements Serializable {

	public RulesResourceRetriever(ResourceRetriever resourceRetriever) {
		this(resourceRetriever, null);
	}

	public RulesResourceRetriever(
		ResourceRetriever resourceRetriever, String rulesLanguage) {

		if (resourceRetriever != null) {
			_resourceRetrievers.add(resourceRetriever);
		}

		_rulesLanguage = rulesLanguage;
	}

	public RulesResourceRetriever(String rulesLanguage) {
		this(null, rulesLanguage);
	}

	public void addResourceRetriever(ResourceRetriever resourceRetriever) {
		_resourceRetrievers.add(resourceRetriever);
	}

	public Set<ResourceRetriever> getResourceRetrievers() {
		return _resourceRetrievers;
	}

	public String getRulesLanguage() {
		return _rulesLanguage;
	}

	private Set<ResourceRetriever> _resourceRetrievers =
		new HashSet<ResourceRetriever>();
	private String _rulesLanguage;

}