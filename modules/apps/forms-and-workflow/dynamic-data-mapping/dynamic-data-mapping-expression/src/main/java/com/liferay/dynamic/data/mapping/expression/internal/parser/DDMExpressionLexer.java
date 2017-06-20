// Generated from DDMExpression.g4 by ANTLR 4.3

/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.dynamic.data.mapping.expression.internal.parser;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

/**
 * @author Brian Wing Shun Chan
 */
@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DDMExpressionLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IntegerLiteral=1, FloatingPointLiteral=2, DecimalFloatingPointLiteral=3, 
		AND=4, COMMA=5, DIV=6, EQ=7, FALSE=8, GE=9, GT=10, LE=11, LPAREN=12, LT=13, 
		MINUS=14, MULT=15, NEQ=16, NOT=17, OR=18, PLUS=19, RPAREN=20, STRING=21, 
		TRUE=22, IDENTIFIER=23, WS=24;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'", "'\\u0017'", "'\\u0018'"
	};
	public static final String[] ruleNames = {
		"IntegerLiteral", "FloatingPointLiteral", "DecimalFloatingPointLiteral", 
		"AND", "COMMA", "DIV", "EQ", "FALSE", "GE", "GT", "LE", "LPAREN", "LT", 
		"MINUS", "MULT", "NEQ", "NOT", "OR", "PLUS", "RPAREN", "STRING", "TRUE", 
		"IDENTIFIER", "WS", "Digits", "Digit", "ExponentIndicator", "ExponentPart", 
		"NameChar", "NameStartChar", "SignedInteger", "Sign"
	};


	public DDMExpressionLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DDMExpression.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\32\u00e7\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\3\2\3\2\3\3\3\3\3\4\3\4\3\4\5\4K\n\4\3\4\5\4N\n\4\3\4\3\4\3\4"+
		"\5\4S\n\4\3\4\3\4\3\4\5\4X\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5"+
		"c\n\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\5\bl\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\5\tx\n\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\16\3"+
		"\16\3\17\3\17\3\20\3\20\3\21\3\21\3\21\3\21\5\21\u008e\n\21\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\5\22\u0096\n\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\5\23\u009f\n\23\3\24\3\24\3\25\3\25\3\26\3\26\7\26\u00a7\n\26\f\26\16"+
		"\26\u00aa\13\26\3\26\3\26\3\26\7\26\u00af\n\26\f\26\16\26\u00b2\13\26"+
		"\3\26\5\26\u00b5\n\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u00bf"+
		"\n\27\3\30\3\30\7\30\u00c3\n\30\f\30\16\30\u00c6\13\30\3\31\6\31\u00c9"+
		"\n\31\r\31\16\31\u00ca\3\31\3\31\3\32\6\32\u00d0\n\32\r\32\16\32\u00d1"+
		"\3\33\3\33\3\34\3\34\3\35\3\35\3\35\3\36\3\36\5\36\u00dd\n\36\3\37\3\37"+
		"\3 \5 \u00e2\n \3 \3 \3!\3!\2\2\"\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31"+
		"\61\32\63\2\65\2\67\29\2;\2=\2?\2A\2\3\2\n\4\2$$^^\4\2))^^\5\2\13\f\16"+
		"\17\"\"\3\2\62;\4\2GGgg\6\2\62;\u00b9\u00b9\u0302\u0371\u2041\u2042\20"+
		"\2C\\aac|\u00c2\u00d8\u00da\u00f8\u00fa\u0301\u0372\u037f\u0381\u2001"+
		"\u200e\u200f\u2072\u2191\u2c02\u2ff1\u3003\ud801\uf902\ufdd1\ufdf2\uffff"+
		"\4\2--//\u00f6\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3"+
		"\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2"+
		"\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3"+
		"\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2"+
		"\2\2\2/\3\2\2\2\2\61\3\2\2\2\3C\3\2\2\2\5E\3\2\2\2\7W\3\2\2\2\tb\3\2\2"+
		"\2\13d\3\2\2\2\rf\3\2\2\2\17k\3\2\2\2\21w\3\2\2\2\23y\3\2\2\2\25|\3\2"+
		"\2\2\27~\3\2\2\2\31\u0081\3\2\2\2\33\u0083\3\2\2\2\35\u0085\3\2\2\2\37"+
		"\u0087\3\2\2\2!\u008d\3\2\2\2#\u0095\3\2\2\2%\u009e\3\2\2\2\'\u00a0\3"+
		"\2\2\2)\u00a2\3\2\2\2+\u00b4\3\2\2\2-\u00be\3\2\2\2/\u00c0\3\2\2\2\61"+
		"\u00c8\3\2\2\2\63\u00cf\3\2\2\2\65\u00d3\3\2\2\2\67\u00d5\3\2\2\29\u00d7"+
		"\3\2\2\2;\u00dc\3\2\2\2=\u00de\3\2\2\2?\u00e1\3\2\2\2A\u00e5\3\2\2\2C"+
		"D\5\63\32\2D\4\3\2\2\2EF\5\7\4\2F\6\3\2\2\2GH\5\63\32\2HJ\7\60\2\2IK\5"+
		"\63\32\2JI\3\2\2\2JK\3\2\2\2KM\3\2\2\2LN\59\35\2ML\3\2\2\2MN\3\2\2\2N"+
		"X\3\2\2\2OP\7\60\2\2PR\5\63\32\2QS\59\35\2RQ\3\2\2\2RS\3\2\2\2SX\3\2\2"+
		"\2TU\5\63\32\2UV\59\35\2VX\3\2\2\2WG\3\2\2\2WO\3\2\2\2WT\3\2\2\2X\b\3"+
		"\2\2\2YZ\7(\2\2Zc\7(\2\2[c\7(\2\2\\]\7c\2\2]^\7p\2\2^c\7f\2\2_`\7C\2\2"+
		"`a\7P\2\2ac\7F\2\2bY\3\2\2\2b[\3\2\2\2b\\\3\2\2\2b_\3\2\2\2c\n\3\2\2\2"+
		"de\7.\2\2e\f\3\2\2\2fg\7\61\2\2g\16\3\2\2\2hi\7?\2\2il\7?\2\2jl\7?\2\2"+
		"kh\3\2\2\2kj\3\2\2\2l\20\3\2\2\2mn\7h\2\2no\7c\2\2op\7n\2\2pq\7u\2\2q"+
		"x\7g\2\2rs\7H\2\2st\7C\2\2tu\7N\2\2uv\7U\2\2vx\7G\2\2wm\3\2\2\2wr\3\2"+
		"\2\2x\22\3\2\2\2yz\7@\2\2z{\7?\2\2{\24\3\2\2\2|}\7@\2\2}\26\3\2\2\2~\177"+
		"\7>\2\2\177\u0080\7?\2\2\u0080\30\3\2\2\2\u0081\u0082\7*\2\2\u0082\32"+
		"\3\2\2\2\u0083\u0084\7>\2\2\u0084\34\3\2\2\2\u0085\u0086\7/\2\2\u0086"+
		"\36\3\2\2\2\u0087\u0088\7,\2\2\u0088 \3\2\2\2\u0089\u008a\7#\2\2\u008a"+
		"\u008e\7?\2\2\u008b\u008c\7>\2\2\u008c\u008e\7@\2\2\u008d\u0089\3\2\2"+
		"\2\u008d\u008b\3\2\2\2\u008e\"\3\2\2\2\u008f\u0090\7p\2\2\u0090\u0091"+
		"\7q\2\2\u0091\u0096\7v\2\2\u0092\u0093\7P\2\2\u0093\u0094\7Q\2\2\u0094"+
		"\u0096\7V\2\2\u0095\u008f\3\2\2\2\u0095\u0092\3\2\2\2\u0096$\3\2\2\2\u0097"+
		"\u0098\7~\2\2\u0098\u009f\7~\2\2\u0099\u009f\7~\2\2\u009a\u009b\7q\2\2"+
		"\u009b\u009f\7t\2\2\u009c\u009d\7Q\2\2\u009d\u009f\7T\2\2\u009e\u0097"+
		"\3\2\2\2\u009e\u0099\3\2\2\2\u009e\u009a\3\2\2\2\u009e\u009c\3\2\2\2\u009f"+
		"&\3\2\2\2\u00a0\u00a1\7-\2\2\u00a1(\3\2\2\2\u00a2\u00a3\7+\2\2\u00a3*"+
		"\3\2\2\2\u00a4\u00a8\7$\2\2\u00a5\u00a7\n\2\2\2\u00a6\u00a5\3\2\2\2\u00a7"+
		"\u00aa\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00ab\3\2"+
		"\2\2\u00aa\u00a8\3\2\2\2\u00ab\u00b5\7$\2\2\u00ac\u00b0\7)\2\2\u00ad\u00af"+
		"\n\3\2\2\u00ae\u00ad\3\2\2\2\u00af\u00b2\3\2\2\2\u00b0\u00ae\3\2\2\2\u00b0"+
		"\u00b1\3\2\2\2\u00b1\u00b3\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b3\u00b5\7)"+
		"\2\2\u00b4\u00a4\3\2\2\2\u00b4\u00ac\3\2\2\2\u00b5,\3\2\2\2\u00b6\u00b7"+
		"\7v\2\2\u00b7\u00b8\7t\2\2\u00b8\u00b9\7w\2\2\u00b9\u00bf\7g\2\2\u00ba"+
		"\u00bb\7V\2\2\u00bb\u00bc\7T\2\2\u00bc\u00bd\7W\2\2\u00bd\u00bf\7G\2\2"+
		"\u00be\u00b6\3\2\2\2\u00be\u00ba\3\2\2\2\u00bf.\3\2\2\2\u00c0\u00c4\5"+
		"=\37\2\u00c1\u00c3\5;\36\2\u00c2\u00c1\3\2\2\2\u00c3\u00c6\3\2\2\2\u00c4"+
		"\u00c2\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5\60\3\2\2\2\u00c6\u00c4\3\2\2"+
		"\2\u00c7\u00c9\t\4\2\2\u00c8\u00c7\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00c8"+
		"\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00cd\b\31\2\2"+
		"\u00cd\62\3\2\2\2\u00ce\u00d0\5\65\33\2\u00cf\u00ce\3\2\2\2\u00d0\u00d1"+
		"\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\64\3\2\2\2\u00d3"+
		"\u00d4\t\5\2\2\u00d4\66\3\2\2\2\u00d5\u00d6\t\6\2\2\u00d68\3\2\2\2\u00d7"+
		"\u00d8\5\67\34\2\u00d8\u00d9\5? \2\u00d9:\3\2\2\2\u00da\u00dd\5=\37\2"+
		"\u00db\u00dd\t\7\2\2\u00dc\u00da\3\2\2\2\u00dc\u00db\3\2\2\2\u00dd<\3"+
		"\2\2\2\u00de\u00df\t\b\2\2\u00df>\3\2\2\2\u00e0\u00e2\5A!\2\u00e1\u00e0"+
		"\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e4\5\63\32\2"+
		"\u00e4@\3\2\2\2\u00e5\u00e6\t\t\2\2\u00e6B\3\2\2\2\26\2JMRWbkw\u008d\u0095"+
		"\u009e\u00a8\u00b0\u00b4\u00be\u00c4\u00ca\u00d1\u00dc\u00e1\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
