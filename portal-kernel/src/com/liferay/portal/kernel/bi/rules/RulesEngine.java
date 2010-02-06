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

import com.liferay.portal.kernel.messaging.proxy.ExecutingClassLoaders;
import com.liferay.portal.kernel.messaging.proxy.MessagingProxy;
import com.liferay.portal.kernel.messaging.proxy.ProxyMode;

import java.util.List;
import java.util.Map;

/**
 * <a href="RulesEngine.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Vihang Pathak
 */
public interface RulesEngine {

	@MessagingProxy(mode = ProxyMode.SYNC)
	public void add(
			String domainName, RulesResourceRetriever RulesResourceRetriever,
			@ExecutingClassLoaders ClassLoader... clientClassLoaders)
		throws RulesEngineException;

	@MessagingProxy(mode = ProxyMode.SYNC)
	public boolean containsRuleDomain(String domainName)
		throws RulesEngineException;

	@MessagingProxy(mode = ProxyMode.ASYNC)
	public void execute(
			RulesResourceRetriever RulesResourceRetriever, List<Fact<?>> facts,
			@ExecutingClassLoaders ClassLoader... clientClassLoaders)
		throws RulesEngineException;

	@MessagingProxy(mode = ProxyMode.SYNC)
	public Map<String, ?> execute(
			RulesResourceRetriever RulesResourceRetriever, List<Fact<?>> facts,
			Query query,
			@ExecutingClassLoaders ClassLoader... clientClassLoaders)
		throws RulesEngineException;

	@MessagingProxy(mode = ProxyMode.ASYNC)
	public void execute(
			String domainName, List<Fact<?>> facts,
			@ExecutingClassLoaders ClassLoader... clientClassLoaders)
		throws RulesEngineException;

	@MessagingProxy(mode = ProxyMode.SYNC)
	public Map<String, ?> execute(
			String domainName, List<Fact<?>> facts, Query query,
			@ExecutingClassLoaders ClassLoader... clientClassLoaders)
		throws RulesEngineException;

	@MessagingProxy(mode = ProxyMode.SYNC)
	public void remove(String domainName) throws RulesEngineException;

	@MessagingProxy(mode = ProxyMode.SYNC)
	public void update(
			String domainName, RulesResourceRetriever RulesResourceRetriever,
			@ExecutingClassLoaders ClassLoader... clientClassLoaders)
		throws RulesEngineException;

}