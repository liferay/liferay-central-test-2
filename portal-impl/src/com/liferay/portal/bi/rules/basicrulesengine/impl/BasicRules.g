header {
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

package com.liferay.portal.bi.rules.basicrulesengine.impl;
}

class BasicRulesLexer extends Lexer;

options { k=2; }

PART: ('a'..'z' | 'A'..'Z' | '0'..'9' | '.' | '*' | '/' | ':')+;

// Grouping
LEFT_PAREN: '(';
RIGHT_PAREN: ')';

//Logical Operators
AND: "&&";
OR: "||";

//Comparison operators
EQ: "==";
NOTEQ: "!=";
GT: '>';
GTEQ: ">=";
LT: '<';
LTEQ: "<=";
MATCHES: "~=";

WS  : (' ' | '\t' | '\r' | '\n') { $setType(Token.SKIP); };

class BasicRulesParser extends Parser;

options {buildAST=true; }

expr
    : orexp
    ;

orexp
	: andexp (OR^ andexp)*
	;

andexp
	: eqexp (AND^ eqexp)*
	;

eqexp
	: atom((EQ^|NOTEQ^|GT^|GTEQ^|LT^|LTEQ^|MATCHES^) atom)*
	;

atom
	: PART
    | LEFT_PAREN! expr RIGHT_PAREN!
    ;

class BasicRulesTreeParser extends TreeParser;

{
    private RuleFactory factory;

    public void setRuleFactory(RuleFactory factory)
    {
        this.factory = factory;
    }
}

evaluate returns [Rule r]
{
    Rule a, b;
    r = null;
}
    : #(AND a=evaluate b=evaluate) {
        r = new AndRules(a, b);
    }
    | #(OR a=evaluate b=evaluate) {
        r = new OrRules(a, b);
    }
    | #(EQ a=evaluate b=evaluate) {
        r = new Equals(a, b);
    }
	| #(NOTEQ a=evaluate b=evaluate) {
        r = new NotEquals(a, b);
    }
	| #(GT a=evaluate b=evaluate) {
        r = new GreaterThan(a, b);
    }
	| #(GTEQ a=evaluate b=evaluate) {
        r = new GreaterThanEquals(a, b);
    }
	| #(LT a=evaluate b=evaluate) {
        r = new LessThan(a, b);
    }
	| #(LTEQ a=evaluate b=evaluate) {
        r = new LessThanEquals(a, b);
    }
	| #(MATCHES a=evaluate b=evaluate) {
        r = new Matches(a, b);
    }
    | c:PART {
        r = factory.createRule(c.getText());
    }
    ;
