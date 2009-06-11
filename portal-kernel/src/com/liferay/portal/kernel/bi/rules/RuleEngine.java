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
 */
public interface RuleEngine {
	/**
	 * Add rules to a given domain.  If the domain exists, then 
	 *
	 * @param domainName
	 * @param retriever
	 * @throws RuleEngineException
	 */
	public void addRuleDomain(String domainName, RuleRetriever retriever)
		throws RuleEngineException;

	/**
	 * @param domainName
	 * @return true if rule base exists with the associated domain name
	 * @throws RuleEngineException
	 */
	public boolean containsRuleDomain(String domainName)
		throws RuleEngineException;

	/**
	 * Execute rules specified by the RuleRetriever with the appropriate facts
	 *
	 * @param retriever for the rules to be executed
	 * @param facts to insert into the rule
	 * @throws RuleEngineException
	 */
	public void execute(
		RuleRetriever retriever, List facts)
		throws RuleEngineException;


	/**
	 * Execute rules specified by the RuleRetriever with the appropriate facts
	 * Upon execution, query the working memory with the supplied query.
	 *
	 * @param retriever for the rules to be executed
	 * @param facts to insert into the rule
	 * @param query to execute after running rules
	 * @return results of the query
	 * @throws RuleEngineException
	 */
	public List execute(
		RuleRetriever retriever, List facts, String query)
		throws RuleEngineException;

	/**
	 * Execute rules specified by the RuleRetriever with the appropriate facts
	 * Upon execution, query the working memory with the supplied query and
	 * arguments
	 *
	 * @param retriever for the rules to be executed
	 * @param facts to insert into the rule
	 * @param query to execute after running rules
	 * @param queryArguments arguments for the query
	 * @return results of the query
	 * @throws RuleEngineException
	 */
	public List execute(
		RuleRetriever retriever, List facts,
		String query, Object[] queryArguments)
		throws RuleEngineException;

	/**
	 * Execute a set of rules using the associated domainName
	 *
	 * @param domainName for the rulebase to use
	 * @param facts to insert into the rule
	 * @throws RuleEngineException
	 */
	public void execute(String domainName, List facts)
		throws RuleEngineException;

	/**
	 * Execute rules specified by the RuleRetriever with the appropriate facts
	 *
	 * @param retriever for the rules to be executed
	 * @param facts to insert into the rule
	 * @return all the objects asserted into the working memory.
	 * @throws RuleEngineException
	 */
	public List executeWithResults(
		RuleRetriever retriever, List facts)
	throws RuleEngineException;

	/**
	 * Execute a set of rules using the associated domainName
	 *
	 * @param domainName for the rulebase to use
	 * @param facts to insert into the rule
	 * @return all the objects asserted into the working memory.
	 * @throws RuleEngineException
	 */
	public List executeWithResults(
		String domainName, List facts)
		throws RuleEngineException;

	/**
	 * Execute a set of rules using the associated domainName
	 *
	 * @param domainName for the rulebase to use
	 * @param facts to insert into the rule
	 * @param query to execute after completion of the execution
	 * @return the results of the query
	 * @throws RuleEngineException
	 */
	public List execute(
		String domainName, List facts, String query)
		throws RuleEngineException;

	/**
	 * Execute a set of rules using the associated domainName
	 *
	 * @param domainName for the rulebase to use
	 * @param facts to insert into the rule
	 * @param query to execute after completion of the execution
	 * @param queryArguments to pass to the query
	 * @return the results of the query
	 * @throws RuleEngineException
	 */
	public List execute(
		String domainName, List facts,
		String query, Object[] queryArguments)
		throws RuleEngineException;

	/**
	 * Remove the previously defined rules for a given domain
	 *
	 * @param domainName
	 * @throws RuleEngineException
	 */
	public void removeRuleDomain(String domainName)
		throws RuleEngineException;

	/**
	 * Updates the rules for a given rule domain
	 *
	 * @param domainName
	 * @param retriever
	 * @throws RuleEngineException
	 */
	public void updateRuleDomain(String domainName, RuleRetriever retriever)
		throws RuleEngineException;

	/**
	* Executes a rule from a rule file. ruleObject are the facts required to 
	* execute the rule
	*
	* @param ruleObject
	* @param ruleFileName
	 * TBD temporarily removed; may need to re-add, needs further
	  * clarification w/ Sun
	*/
//	public void executeRule(Object ruleObject,String ruleFileName)
//		throws RuleEngineException;

	/**
	* Executes rule from a rule file reader.ruleObject are the facts required to 
	* execute the rule
	*
	* @param ruleObject
	* @param ruleFileReader
	* TBD temporarily removed; may need to re-add, needs further
	 * clarification w/ Sun
	*/
//    public void executeRule(Object ruleObject,Reader ruleFileReader)
//    	throws RuleEngineException;

}
