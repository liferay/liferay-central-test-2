// $ANTLR 2.7.6 (2005-12-22): "RenderRules.g" -> "RenderRulesTreeParser.java"$

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

package com.liferay.portal.security.permission.basicrulesengine;

import antlr.TreeParser;
import antlr.Token;
import antlr.collections.AST;
import antlr.RecognitionException;
import antlr.ANTLRException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.collections.impl.BitSet;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;


public class RenderRulesTreeParser extends antlr.TreeParser       implements RenderRulesLexerTokenTypes
 {

    private RenderRuleFactory factory;

    public void setRenderRuleFactory(RenderRuleFactory factory)
    {
        this.factory = factory;
    }
public RenderRulesTreeParser() {
	tokenNames = _tokenNames;
}

	public final RenderRule  eval(AST _t) throws RecognitionException {
		RenderRule r;
		
		AST eval_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST c = null;
		
		RenderRule a, b;
		r = null;
		
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case AND:
			{
				AST __t30 = _t;
				AST tmp1_AST_in = (AST)_t;
				match(_t,AND);
				_t = _t.getFirstChild();
				a=eval(_t);
				_t = _retTree;
				b=eval(_t);
				_t = _retTree;
				_t = __t30;
				_t = _t.getNextSibling();
				
				r = new AndRules(a, b);
				
				break;
			}
			case OR:
			{
				AST __t31 = _t;
				AST tmp2_AST_in = (AST)_t;
				match(_t,OR);
				_t = _t.getFirstChild();
				a=eval(_t);
				_t = _retTree;
				b=eval(_t);
				_t = _retTree;
				_t = __t31;
				_t = _t.getNextSibling();
				
				r = new OrRules(a, b);
				
				break;
			}
			case EQ:
			{
				AST __t32 = _t;
				AST tmp3_AST_in = (AST)_t;
				match(_t,EQ);
				_t = _t.getFirstChild();
				a=eval(_t);
				_t = _retTree;
				b=eval(_t);
				_t = _retTree;
				_t = __t32;
				_t = _t.getNextSibling();
				
				r = new EqualsRule(a, b);
				
				break;
			}
			case NOTEQ:
			{
				AST __t33 = _t;
				AST tmp4_AST_in = (AST)_t;
				match(_t,NOTEQ);
				_t = _t.getFirstChild();
				a=eval(_t);
				_t = _retTree;
				b=eval(_t);
				_t = _retTree;
				_t = __t33;
				_t = _t.getNextSibling();
				
				r = new NotEqualsRule(a, b);
				
				break;
			}
			case GT:
			{
				AST __t34 = _t;
				AST tmp5_AST_in = (AST)_t;
				match(_t,GT);
				_t = _t.getFirstChild();
				a=eval(_t);
				_t = _retTree;
				b=eval(_t);
				_t = _retTree;
				_t = __t34;
				_t = _t.getNextSibling();
				
				r = new GreaterThanRule(a, b);
				
				break;
			}
			case GTEQ:
			{
				AST __t35 = _t;
				AST tmp6_AST_in = (AST)_t;
				match(_t,GTEQ);
				_t = _t.getFirstChild();
				a=eval(_t);
				_t = _retTree;
				b=eval(_t);
				_t = _retTree;
				_t = __t35;
				_t = _t.getNextSibling();
				
				r = new GreaterThanEqualsRule(a, b);
				
				break;
			}
			case LT:
			{
				AST __t36 = _t;
				AST tmp7_AST_in = (AST)_t;
				match(_t,LT);
				_t = _t.getFirstChild();
				a=eval(_t);
				_t = _retTree;
				b=eval(_t);
				_t = _retTree;
				_t = __t36;
				_t = _t.getNextSibling();
				
				r = new LessThanRule(a, b);
				
				break;
			}
			case LTEQ:
			{
				AST __t37 = _t;
				AST tmp8_AST_in = (AST)_t;
				match(_t,LTEQ);
				_t = _t.getFirstChild();
				a=eval(_t);
				_t = _retTree;
				b=eval(_t);
				_t = _retTree;
				_t = __t37;
				_t = _t.getNextSibling();
				
				r = new LessThanEqualsRule(a, b);
				
				break;
			}
			case MATCHES:
			{
				AST __t38 = _t;
				AST tmp9_AST_in = (AST)_t;
				match(_t,MATCHES);
				_t = _t.getFirstChild();
				a=eval(_t);
				_t = _retTree;
				b=eval(_t);
				_t = _retTree;
				_t = __t38;
				_t = _t.getNextSibling();
				
				r = new MatchesRule(a, b);
				
				break;
			}
			case RULE:
			{
				c = (AST)_t;
				match(_t,RULE);
				_t = _t.getNextSibling();
				
				r = factory.createRule(c.getText());
				
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return r;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"RULE",
		"LEFT_PAREN",
		"RIGHT_PAREN",
		"AND",
		"OR",
		"EQ",
		"NOTEQ",
		"GT",
		"GTEQ",
		"LT",
		"LTEQ",
		"MATCHES",
		"WS"
	};
	
	}
	
