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

package com.liferay.portal.kernel.bi.rules;

import com.liferay.portal.kernel.util.AggregateClassLoader;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ExecutionRequestMessage.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 *
 */
public class ExecutionRequestMessage implements Serializable {

	public ExecutionRequestMessage(String domainName) {
		_domainName = domainName;
	}

	public ExecutionRequestMessage(RuleRetriever ruleRetriever) {
		_ruleRetriever = ruleRetriever;

		addClientClassLoader(Thread.currentThread().getContextClassLoader());
	}

	public void addClientClassLoader(ClassLoader clientClassLoader) {
		if (_clientClassLoader == null) {
			_clientClassLoader = clientClassLoader;
		}
		else {
			_clientClassLoader = new AggregateClassLoader(
				clientClassLoader, _clientClassLoader);
		}
	}

	public void addFact(Object fact) {
		_facts.add(fact);
	}

	public void addFacts(List<?> facts) {
		_facts.addAll(facts);
	}

	public ClassLoader getClientClassLoader() {
		return _clientClassLoader;
	}

	public String getDomainName() {
		return _domainName;
	}

	public List<?> getFacts() {
		return _facts;
	}

	public Query getQuery() {
		return _query;
	}

	public RuleRetriever getRuleRetriever() {
		return _ruleRetriever;
	}

	public void setQuery(Query query) {
		_query = query;
	}

	private ClassLoader _clientClassLoader;
	private String _domainName;
	private List<Object> _facts = new ArrayList<Object>();
	private Query _query;
	private RuleRetriever _ruleRetriever;

}