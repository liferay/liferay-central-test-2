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

import java.util.List;

/**
 * <a href="RuleEngine.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Vihang Pathak
 *
 */
public interface RuleEngine {

	public void add(String domainName, RuleRetriever ruleRetriever)
		throws RuleEngineException;

	public void execute(RuleRetriever ruleRetriever, List<?> facts)
		throws RuleEngineException;

	public List<?> execute(
			RuleRetriever ruleRetriever, List<?> facts, String queryString)
		throws RuleEngineException;

	public List<?> execute(
			RuleRetriever ruleRetriever, List<?> facts, String queryString,
			Object[] queryArguments)
		throws RuleEngineException;

	public void execute(String domainName, List<?> facts)
		throws RuleEngineException;

	public List<?> execute(String domainName, List<?> facts, String queryString)
		throws RuleEngineException;

	public List<?> execute(
			String domainName, List<?> facts, String queryString,
			Object[] queryArguments)
		throws RuleEngineException;

	public List<?> executeWithResults(
			RuleRetriever ruleRetriever, List<?> facts)
		throws RuleEngineException;

	public List<?> executeWithResults(String domainName, List<?> facts)
		throws RuleEngineException;

	public void remove(String domainName) throws RuleEngineException;

	public void update(String domainName, RuleRetriever ruleRetriever)
		throws RuleEngineException;

}