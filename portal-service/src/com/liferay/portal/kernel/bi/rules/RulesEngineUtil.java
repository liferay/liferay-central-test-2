/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.bi.rules;

import java.util.List;
import java.util.Map;

/**
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