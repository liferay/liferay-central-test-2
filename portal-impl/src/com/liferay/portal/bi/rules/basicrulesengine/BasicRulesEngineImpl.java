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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.bi.rules.basicrulesengine;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import antlr.collections.AST;

import com.liferay.portal.bi.rules.basicrulesengine.impl.BasicRulesLexer;
import com.liferay.portal.bi.rules.basicrulesengine.impl.BasicRulesParser;
import com.liferay.portal.bi.rules.basicrulesengine.impl.BasicRulesTreeParser;
import com.liferay.portal.bi.rules.basicrulesengine.impl.Rule;
import com.liferay.portal.bi.rules.basicrulesengine.impl.RuleFactory;
import com.liferay.portal.kernel.bi.rules.Fact;
import com.liferay.portal.kernel.bi.rules.Query;
import com.liferay.portal.kernel.bi.rules.RulesEngine;
import com.liferay.portal.kernel.bi.rules.RulesEngineException;
import com.liferay.portal.kernel.bi.rules.RulesResourceRetriever;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resource.ResourceRetriever;
import com.liferay.portal.kernel.resource.StringResourceRetriever;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStreamReader;
import java.io.Reader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <a href="BasicRulesEngineImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 *
 */
public class BasicRulesEngineImpl implements RulesEngine {

	public void add(String domainName,
		RulesResourceRetriever rulesResourceRetriever,
		ClassLoader... clientClassLoaders) throws RulesEngineException {
		throw new RulesEngineException("Unsupported operation");
	}

	public boolean containsRuleDomain(String domainName)
		throws RulesEngineException {
		throw new RulesEngineException("Unsupported operation");
	}

	public void execute(RulesResourceRetriever rulesResourceRetriever,
		List<Fact<?>> facts, ClassLoader... clientClassLoaders)
		throws RulesEngineException {
		execute(rulesResourceRetriever, facts, null, clientClassLoaders);
	}

	public Map<String, ?> execute(RulesResourceRetriever rulesResourceRetriever,
		List<Fact<?>> facts, Query query, ClassLoader... clientClassLoaders)
		throws RulesEngineException {
		Set<ResourceRetriever> resourceRetrievers  =
			rulesResourceRetriever.getResourceRetrievers();

		if (resourceRetrievers == null || resourceRetrievers.isEmpty()) {
			throw new RulesEngineException("Empty or null resource retriever");
		}

		return execute(resourceRetrievers.iterator().next(), facts);
	}

	public void execute(String domainName, List<Fact<?>> facts,
		ClassLoader... clientClassLoaders) throws RulesEngineException {
		execute(new StringResourceRetriever(domainName), facts);
	}

	public Map<String, ?> execute(String domainName, List<Fact<?>> facts,
		Query query, ClassLoader... clientClassLoaders)
		throws RulesEngineException {
		return execute(new StringResourceRetriever(domainName), facts);
	}

	public void remove(String domainName) throws RulesEngineException {
		throw new RulesEngineException("Unsupported operation");
	}

	public void update(String domainName,
		RulesResourceRetriever rulesResourceRetriever,
		ClassLoader... clientClassLoaders) throws RulesEngineException {
		throw new RulesEngineException("Unsupported operation");
	}

	protected Map<String, ?> execute(ResourceRetriever resourceRetriever,
		List<Fact<?>> facts) throws RulesEngineException {
		if (Validator.isNull(facts) || facts.size() != 1 ||
			!(facts.get(0).getIdentifier().equals(BasicFact.IDENTIFIER))) {
			throw new RulesEngineException("Invalid facts");
		}

		BasicFact basicFact = (BasicFact)facts.get(0).getFactObject();

		Reader reader =
			new InputStreamReader(resourceRetriever.getInputStream());

		BasicRulesLexer lexer = new BasicRulesLexer(reader);

		BasicRulesParser parser = new BasicRulesParser(lexer);

		BasicRulesTreeParser tree = new BasicRulesTreeParser();

		tree.setRuleFactory(new RuleFactory());

		try {
			parser.expr();

			AST ast = parser.getAST();

			Rule rule = tree.evaluate(ast);

			boolean result = rule.evaluate(basicFact);

			basicFact.setQualified(result);
		}
		catch (TokenStreamException ex) {
			if (_log.isDebugEnabled()) {
				_log.debug(ex);
			}

			basicFact.setQualified(false);
		}
		catch (RecognitionException ex) {
			if (_log.isDebugEnabled()) {
				_log.debug(ex);
			}

			basicFact.setQualified(false);
		}

		Map<String, BasicFact> results = new HashMap<String, BasicFact>();

		results.put(BasicFact.IDENTIFIER, basicFact);

		return results;
	}

	private static Log _log =
		 LogFactoryUtil.getLog(BasicRulesEngineImpl.class);

}