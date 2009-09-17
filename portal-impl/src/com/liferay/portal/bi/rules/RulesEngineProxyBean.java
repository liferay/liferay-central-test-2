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

package com.liferay.portal.bi.rules;

import com.liferay.portal.kernel.bi.rules.Query;
import com.liferay.portal.kernel.bi.rules.RulesEngine;
import com.liferay.portal.kernel.bi.rules.RulesEngineException;
import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.resource.ResourceRetriever;

import java.util.List;

/**
 * <a href="RulesEngineProxyBean.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class RulesEngineProxyBean extends BaseProxyBean implements RulesEngine {

	public void add(String domainName, ResourceRetriever resourceRetriever)
		throws RulesEngineException {

		throw new UnsupportedOperationException();
	}

	public void execute(
			ResourceRetriever resourceRetriever, List<?> facts,
			ClassLoader... clientClassLoaders)
		throws RulesEngineException {

		throw new UnsupportedOperationException();
	}

	public List<?> execute(
			ResourceRetriever resourceRetriever, List<?> facts, Query query,
			ClassLoader... clientClassLoaders)
		throws RulesEngineException {

		throw new UnsupportedOperationException();
	}

	public void execute(
			String domainName, List<?> facts, ClassLoader... clientClassLoaders)
		throws RulesEngineException {

		throw new UnsupportedOperationException();
	}

	public List<?> execute(
			String domainName, List<?> facts, Query query,
			ClassLoader... clientClassLoaders)
		throws RulesEngineException {

		throw new UnsupportedOperationException();
	}

	public void remove(String domainName) throws RulesEngineException {
		throw new UnsupportedOperationException();
	}

	public void update(String domainName, ResourceRetriever resourceRetriever)
		throws RulesEngineException {

		throw new UnsupportedOperationException();
	}

}