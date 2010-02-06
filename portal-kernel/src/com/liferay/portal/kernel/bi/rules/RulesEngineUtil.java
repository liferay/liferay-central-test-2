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

import java.util.List;
import java.util.Map;

/**
 * <a href="RulesEngineUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class RulesEngineUtil {

	public static void add(
			String domainName, RulesResourceRetriever RulesResourceRetriever)
		throws RulesEngineException {

		_rulesEngine.add(domainName, RulesResourceRetriever);
	}

	public static void add(
			String domainName, RulesResourceRetriever RulesResourceRetriever,
			ClassLoader... classloaders)
		throws RulesEngineException {

		_rulesEngine.add(domainName, RulesResourceRetriever, classloaders);
	}

	public static boolean containsRuleDomain(String domainName)
		throws RulesEngineException {

		return _rulesEngine.containsRuleDomain(domainName);
	}

	public static void execute(
			RulesResourceRetriever RulesResourceRetriever, List<Fact<?>> facts)
		throws RulesEngineException {

		_rulesEngine.execute(RulesResourceRetriever, facts);
	}

	public static void execute(
			RulesResourceRetriever RulesResourceRetriever, List<Fact<?>> facts,
			ClassLoader... classloaders)
		throws RulesEngineException {

		_rulesEngine.execute(RulesResourceRetriever, facts, classloaders);
	}

	public static Map<String, ?> execute(
			RulesResourceRetriever RulesResourceRetriever, List<Fact<?>> facts,
			Query query)
		throws RulesEngineException {

		return _rulesEngine.execute(RulesResourceRetriever, facts, query);
	}

	public static Map<String, ?> execute(
			RulesResourceRetriever RulesResourceRetriever, List<Fact<?>> facts,
			Query query, ClassLoader... classloaders)
		throws RulesEngineException {

		return _rulesEngine.execute(
			RulesResourceRetriever, facts, query, classloaders);
	}

	public static void execute(String domainName, List<Fact<?>> facts)
		throws RulesEngineException {

		_rulesEngine.execute(domainName, facts);
	}

	public static void execute(
			String domainName, List<Fact<?>> facts, ClassLoader... classloaders)
		throws RulesEngineException {

		_rulesEngine.execute(domainName, facts, classloaders);
	}

	public static Map<String, ?> execute(
			String domainName, List<Fact<?>> facts, Query query)
		throws RulesEngineException {

		return _rulesEngine.execute(domainName, facts, query);
	}

	public static Map<String, ?> execute(
			String domainName, List<Fact<?>> facts, Query query,
			ClassLoader... classloaders)
		throws RulesEngineException {

		return _rulesEngine.execute(domainName, facts, query, classloaders);
	}

	public static void remove(String domainName) throws RulesEngineException {
		_rulesEngine.remove(domainName);
	}

	public static void update(
			String domainName, RulesResourceRetriever RulesResourceRetriever)
		throws RulesEngineException {

		_rulesEngine.update(domainName, RulesResourceRetriever);
	}

	public static void update(
			String domainName, RulesResourceRetriever RulesResourceRetriever,
			ClassLoader... classloaders)
		throws RulesEngineException {

		_rulesEngine.update(domainName, RulesResourceRetriever, classloaders);
	}

	public void setRulesEngine(RulesEngine rulesEngine) {
		_rulesEngine = rulesEngine;
	}

	private static RulesEngine _rulesEngine;

}