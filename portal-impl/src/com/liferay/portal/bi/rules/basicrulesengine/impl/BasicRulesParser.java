// $ANTLR 2.7.6 (2005-12-22): "BasicRules.g" -> "BasicRulesParser.java"$

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

import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;

import antlr.collections.AST;
import antlr.collections.impl.BitSet;

public class BasicRulesParser extends antlr.LLkParser       implements BasicRulesLexerTokenTypes
 {

protected BasicRulesParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public BasicRulesParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected BasicRulesParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public BasicRulesParser(TokenStream lexer) {
  this(lexer,1);
}

public BasicRulesParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void expr() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expr_AST = null;

		try {      // for error handling
			orexp();
			astFactory.addASTChild(currentAST, returnAST);
			expr_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
		returnAST = expr_AST;
	}

	public final void orexp() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST orexp_AST = null;

		try {      // for error handling
			andexp();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop20:
			do {
				if ((LA(1)==OR)) {
					AST tmp10_AST = null;
					tmp10_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp10_AST);
					match(OR);
					andexp();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop20;
				}

			} while (true);
			}
			orexp_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
		returnAST = orexp_AST;
	}

	public final void andexp() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST andexp_AST = null;

		try {      // for error handling
			eqexp();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop23:
			do {
				if ((LA(1)==AND)) {
					AST tmp11_AST = null;
					tmp11_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp11_AST);
					match(AND);
					eqexp();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop23;
				}

			} while (true);
			}
			andexp_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
		returnAST = andexp_AST;
	}

	public final void eqexp() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST eqexp_AST = null;

		try {      // for error handling
			atom();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop27:
			do {
				if (((LA(1) >= EQ && LA(1) <= MATCHES))) {
					{
					switch ( LA(1)) {
					case EQ:
					{
						AST tmp12_AST = null;
						tmp12_AST = astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp12_AST);
						match(EQ);
						break;
					}
					case NOTEQ:
					{
						AST tmp13_AST = null;
						tmp13_AST = astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp13_AST);
						match(NOTEQ);
						break;
					}
					case GT:
					{
						AST tmp14_AST = null;
						tmp14_AST = astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp14_AST);
						match(GT);
						break;
					}
					case GTEQ:
					{
						AST tmp15_AST = null;
						tmp15_AST = astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp15_AST);
						match(GTEQ);
						break;
					}
					case LT:
					{
						AST tmp16_AST = null;
						tmp16_AST = astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp16_AST);
						match(LT);
						break;
					}
					case LTEQ:
					{
						AST tmp17_AST = null;
						tmp17_AST = astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp17_AST);
						match(LTEQ);
						break;
					}
					case MATCHES:
					{
						AST tmp18_AST = null;
						tmp18_AST = astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp18_AST);
						match(MATCHES);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					atom();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop27;
				}

			} while (true);
			}
			eqexp_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
		returnAST = eqexp_AST;
	}

	public final void atom() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST atom_AST = null;

		try {      // for error handling
			switch ( LA(1)) {
			case PART:
			{
				AST tmp19_AST = null;
				tmp19_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp19_AST);
				match(PART);
				atom_AST = (AST)currentAST.root;
				break;
			}
			case LEFT_PAREN:
			{
				match(LEFT_PAREN);
				expr();
				astFactory.addASTChild(currentAST, returnAST);
				match(RIGHT_PAREN);
				atom_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
		returnAST = atom_AST;
	}

	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"PART",
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

	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};

	private static final long[] mk_tokenSet_0() {
		long[] data = { 64L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 320L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 448L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 65472L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());

	}