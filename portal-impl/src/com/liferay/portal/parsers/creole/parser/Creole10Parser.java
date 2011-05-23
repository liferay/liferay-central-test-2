// $ANTLR 3.0.1 /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g 2011-05-20 10:07:16

/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.parsers.creole.parser;

import com.liferay.portal.parsers.creole.ast.ASTNode;

import com.liferay.portal.parsers.creole.ast.BoldTextNode;
import com.liferay.portal.parsers.creole.ast.CollectionNode;
import com.liferay.portal.parsers.creole.ast.ForcedEndOfLineNode;
import com.liferay.portal.parsers.creole.ast.FormattedTextNode;
import com.liferay.portal.parsers.creole.ast.HeadingNode;
import com.liferay.portal.parsers.creole.ast.HorizontalNode;
import com.liferay.portal.parsers.creole.ast.ImageNode;
import com.liferay.portal.parsers.creole.ast.ItalicTextNode;
import com.liferay.portal.parsers.creole.ast.LineNode;
import com.liferay.portal.parsers.creole.ast.NoWikiSectionNode;
import com.liferay.portal.parsers.creole.ast.OrderedListItemNode;
import com.liferay.portal.parsers.creole.ast.OrderedListNode;
import com.liferay.portal.parsers.creole.ast.ParagraphNode;
import com.liferay.portal.parsers.creole.ast.ScapedNode;
import com.liferay.portal.parsers.creole.ast.UnformattedTextNode;
import com.liferay.portal.parsers.creole.ast.UnorderedListItemNode;
import com.liferay.portal.parsers.creole.ast.UnorderedListNode;
import com.liferay.portal.parsers.creole.ast.WikiPageNode;
import com.liferay.portal.parsers.creole.ast.link.InterwikiLinkNode;
import com.liferay.portal.parsers.creole.ast.link.LinkNode;
import com.liferay.portal.parsers.creole.ast.table.TableCellNode;
import com.liferay.portal.parsers.creole.ast.table.TableDataNode;
import com.liferay.portal.parsers.creole.ast.table.TableHeaderNode;
import com.liferay.portal.parsers.creole.ast.table.TableNode;

/**
* This is a generated file from Creole10.g. DO NOT MODIFY THIS FILE MANUALLY!!
* If needed, modify the grammar and rerun the ant generation task
* (ant build-creole-parser)
*/

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class Creole10Parser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "FORCED_END_OF_LINE", "HEADING_SECTION", "HORIZONTAL_SECTION", "LIST_ITEM", "LIST_ITEM_PART", "NOWIKI_SECTION", "SCAPE_NODE", "TEXT_NODE", "UNORDERED_LIST", "UNFORMATTED_TEXT", "WIKI", "NEWLINE", "POUND", "STAR", "EQUAL", "PIPE", "ITAL", "LINK_OPEN", "IMAGE_OPEN", "NOWIKI_OPEN", "EXTENSION", "FORCED_LINEBREAK", "ESCAPE", "NOWIKI_BLOCK_CLOSE", "NOWIKI_CLOSE", "LINK_CLOSE", "IMAGE_CLOSE", "BLANKS", "DASH", "CR", "LF", "SPACE", "TABULATOR", "COLON_SLASH", "SLASH", "INSIGNIFICANT_CHAR", "':'", "'C'", "'2'", "'D'", "'o'", "'k'", "'u'", "'W'", "'i'", "'F'", "'l'", "'c'", "'r'", "'G'", "'g'", "'e'", "'J'", "'S'", "'P'", "'M'", "'a'", "'t'", "'b'", "'d'", "'n'", "'O'", "'m'", "'s'", "'h'", "'p'", "'R'", "'x'", "'T'", "'y'", "'U'", "'X'"
	};
	public static final int BLANKS=31;
	public static final int INSIGNIFICANT_CHAR=39;
	public static final int FORCED_LINEBREAK=25;
	public static final int UNORDERED_LIST=12;
	public static final int STAR=17;
	public static final int DASH=32;
	public static final int POUND=16;
	public static final int HEADING_SECTION=5;
	public static final int NOWIKI_OPEN=23;
	public static final int FORCED_END_OF_LINE=4;
	public static final int HORIZONTAL_SECTION=6;
	public static final int NOWIKI_BLOCK_CLOSE=27;
	public static final int UNFORMATTED_TEXT=13;
	public static final int NOWIKI_SECTION=9;
	public static final int SPACE=35;
	public static final int NOWIKI_CLOSE=28;
	public static final int IMAGE_OPEN=22;
	public static final int ITAL=20;
	public static final int EOF=-1;
	public static final int COLON_SLASH=37;
	public static final int LIST_ITEM=7;
	public static final int TEXT_NODE=11;
	public static final int WIKI=14;
	public static final int SLASH=38;
	public static final int ESCAPE=26;
	public static final int NEWLINE=15;
	public static final int SCAPE_NODE=10;
	public static final int IMAGE_CLOSE=30;
	public static final int EQUAL=18;
	public static final int TABULATOR=36;
	public static final int LINK_CLOSE=29;
	public static final int LIST_ITEM_PART=8;
	public static final int PIPE=19;
	public static final int LINK_OPEN=21;
	public static final int CR=33;
	public static final int EXTENSION=24;
	public static final int LF=34;
	protected static class CountLevel_scope {
		int level;
		String currentMarkup;
		String groups;
	}
	protected Stack CountLevel_stack = new Stack();

		public Creole10Parser(TokenStream input) {
			super(input);
			ruleMemo = new HashMap[116+1];
		 }

	public String[] getTokenNames() { return tokenNames; }
	public String getGrammarFileName() { return "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g"; }

		protected static final String GROUPING_SEPARATOR = "-";

		private WikiPageNode _wikipage = null;

		public WikiPageNode getWikiPageNode() {
			if (_wikipage == null)
				throw new IllegalStateException("No succesful parsing process");

			return _wikipage;
		}


	// $ANTLR start wikipage
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:110:1: wikipage : ( whitespaces )? p= paragraphs EOF ;
	public final void wikipage() throws RecognitionException {
		CollectionNode p = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:111:2: ( ( whitespaces )? p= paragraphs EOF )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:111:4: ( whitespaces )? p= paragraphs EOF
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:111:4: ( whitespaces )?
			int alt1=2;
			int LA1_0 = input.LA(1);

			if ( (LA1_0==NEWLINE||LA1_0==BLANKS) ) {
				alt1=1;
			}
			switch (alt1) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:111:6: whitespaces
					{
					pushFollow(FOLLOW_whitespaces_in_wikipage114);
					whitespaces();
					_fsp--;
					if (failed) return ;

					}
					break;

			}

			pushFollow(FOLLOW_paragraphs_in_wikipage122);
			p=paragraphs();
			_fsp--;
			if (failed) return ;
			if ( backtracking==0 ) {
			   _wikipage = new WikiPageNode(p);
			}
			match(input,EOF,FOLLOW_EOF_in_wikipage127); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end wikipage

	// $ANTLR start paragraphs
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:113:1: paragraphs returns [CollectionNode sections = new CollectionNode()] : (p= paragraph )* ;
	public final CollectionNode paragraphs() throws RecognitionException {
		CollectionNode sections =  new CollectionNode();

		ASTNode p = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:114:2: ( (p= paragraph )* )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:114:4: (p= paragraph )*
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:114:4: (p= paragraph )*
			loop2:
			do {
				int alt2=2;
				int LA2_0 = input.LA(1);

				if ( ((LA2_0>=FORCED_END_OF_LINE && LA2_0<=WIKI)||(LA2_0>=POUND && LA2_0<=75)) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:114:5: p= paragraph
					{
					pushFollow(FOLLOW_paragraph_in_paragraphs145);
					p=paragraph();
					_fsp--;
					if (failed) return sections;
					if ( backtracking==0 ) {
					  sections.add(p);
					}

					}
					break;

				default :
					break loop2;
				}
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return sections;
	}
	// $ANTLR end paragraphs

	// $ANTLR start paragraph
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:116:1: paragraph returns [ASTNode node = null] : (n= nowiki_block | blanks paragraph_separator | ( blanks )? (h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph ) ( paragraph_separator )? );
	public final ASTNode paragraph() throws RecognitionException {
		ASTNode node =  null;

		NoWikiSectionNode n = null;

		ASTNode h = null;

		ASTNode hn = null;

		UnorderedListNode lu = null;

		OrderedListNode lo = null;

		TableNode t = null;

		ParagraphNode tp = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:117:2: (n= nowiki_block | blanks paragraph_separator | ( blanks )? (h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph ) ( paragraph_separator )? )
			int alt6=3;
			switch ( input.LA(1) ) {
			case NOWIKI_OPEN:
				{
				int LA6_1 = input.LA(2);

				if ( ((LA6_1>=FORCED_END_OF_LINE && LA6_1<=WIKI)||(LA6_1>=POUND && LA6_1<=75)) ) {
					alt6=3;
				}
				else if ( (LA6_1==NEWLINE) ) {
					alt6=1;
				}
				else {
					if (backtracking>0) {failed=true; return node;}
					NoViableAltException nvae =
						new NoViableAltException("116:1: paragraph returns [ASTNode node = null] : (n= nowiki_block | blanks paragraph_separator | ( blanks )? (h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph ) ( paragraph_separator )? );", 6, 1, input);

					throw nvae;
				}
				}
				break;
			case BLANKS:
				{
				switch ( input.LA(2) ) {
				case NEWLINE:
					{
					alt6=2;
					}
					break;
				case EOF:
					{
					alt6=2;
					}
					break;
				case FORCED_END_OF_LINE:
				case HEADING_SECTION:
				case HORIZONTAL_SECTION:
				case LIST_ITEM:
				case LIST_ITEM_PART:
				case NOWIKI_SECTION:
				case SCAPE_NODE:
				case TEXT_NODE:
				case UNORDERED_LIST:
				case UNFORMATTED_TEXT:
				case WIKI:
				case POUND:
				case STAR:
				case EQUAL:
				case PIPE:
				case ITAL:
				case LINK_OPEN:
				case IMAGE_OPEN:
				case NOWIKI_OPEN:
				case EXTENSION:
				case FORCED_LINEBREAK:
				case ESCAPE:
				case NOWIKI_BLOCK_CLOSE:
				case NOWIKI_CLOSE:
				case LINK_CLOSE:
				case IMAGE_CLOSE:
				case BLANKS:
				case DASH:
				case CR:
				case LF:
				case SPACE:
				case TABULATOR:
				case COLON_SLASH:
				case SLASH:
				case INSIGNIFICANT_CHAR:
				case 40:
				case 41:
				case 42:
				case 43:
				case 44:
				case 45:
				case 46:
				case 47:
				case 48:
				case 49:
				case 50:
				case 51:
				case 52:
				case 53:
				case 54:
				case 55:
				case 56:
				case 57:
				case 58:
				case 59:
				case 60:
				case 61:
				case 62:
				case 63:
				case 64:
				case 65:
				case 66:
				case 67:
				case 68:
				case 69:
				case 70:
				case 71:
				case 72:
				case 73:
				case 74:
				case 75:
					{
					alt6=3;
					}
					break;
				default:
					if (backtracking>0) {failed=true; return node;}
					NoViableAltException nvae =
						new NoViableAltException("116:1: paragraph returns [ASTNode node = null] : (n= nowiki_block | blanks paragraph_separator | ( blanks )? (h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph ) ( paragraph_separator )? );", 6, 2, input);

					throw nvae;
				}

				}
				break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case STAR:
			case EQUAL:
			case PIPE:
			case ITAL:
			case LINK_OPEN:
			case IMAGE_OPEN:
			case EXTENSION:
			case FORCED_LINEBREAK:
			case ESCAPE:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case INSIGNIFICANT_CHAR:
			case 40:
			case 41:
			case 42:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
				{
				alt6=3;
				}
				break;
			default:
				if (backtracking>0) {failed=true; return node;}
				NoViableAltException nvae =
					new NoViableAltException("116:1: paragraph returns [ASTNode node = null] : (n= nowiki_block | blanks paragraph_separator | ( blanks )? (h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph ) ( paragraph_separator )? );", 6, 0, input);

				throw nvae;
			}

			switch (alt6) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:117:4: n= nowiki_block
					{
					pushFollow(FOLLOW_nowiki_block_in_paragraph166);
					n=nowiki_block();
					_fsp--;
					if (failed) return node;
					if ( backtracking==0 ) {
					   node = n;
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:118:4: blanks paragraph_separator
					{
					pushFollow(FOLLOW_blanks_in_paragraph173);
					blanks();
					_fsp--;
					if (failed) return node;
					pushFollow(FOLLOW_paragraph_separator_in_paragraph176);
					paragraph_separator();
					_fsp--;
					if (failed) return node;

					}
					break;
				case 3 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:119:4: ( blanks )? (h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph ) ( paragraph_separator )?
					{
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:119:4: ( blanks )?
					int alt3=2;
					int LA3_0 = input.LA(1);

					if ( (LA3_0==BLANKS) ) {
						alt3=1;
					}
					switch (alt3) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:119:6: blanks
							{
							pushFollow(FOLLOW_blanks_in_paragraph183);
							blanks();
							_fsp--;
							if (failed) return node;

							}
							break;

					}

					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:120:4: (h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph )
					int alt4=6;
					switch ( input.LA(1) ) {
					case EQUAL:
						{
						alt4=1;
						}
						break;
					case DASH:
						{
						int LA4_2 = input.LA(2);

						if ( ( input.LA(1) == DASH && input.LA(2) == DASH &&
										input.LA(3) == DASH && input.LA(4) == DASH ) ) {
							alt4=2;
						}
						else if ( (true) ) {
							alt4=6;
						}
						else {
							if (backtracking>0) {failed=true; return node;}
							NoViableAltException nvae =
								new NoViableAltException("120:4: (h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph )", 4, 2, input);

							throw nvae;
						}
						}
						break;
					case STAR:
						{
						int LA4_3 = input.LA(2);

						if ( (!( input.LA(1) != STAR || (input.LA(1) == STAR && input.LA(2) == STAR) )) ) {
							alt4=3;
						}
						else if ( ( input.LA(1) != STAR || (input.LA(1) == STAR && input.LA(2) == STAR) ) ) {
							alt4=6;
						}
						else {
							if (backtracking>0) {failed=true; return node;}
							NoViableAltException nvae =
								new NoViableAltException("120:4: (h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph )", 4, 3, input);

							throw nvae;
						}
						}
						break;
					case POUND:
						{
						alt4=4;
						}
						break;
					case PIPE:
						{
						alt4=5;
						}
						break;
					case FORCED_END_OF_LINE:
					case HEADING_SECTION:
					case HORIZONTAL_SECTION:
					case LIST_ITEM:
					case LIST_ITEM_PART:
					case NOWIKI_SECTION:
					case SCAPE_NODE:
					case TEXT_NODE:
					case UNORDERED_LIST:
					case UNFORMATTED_TEXT:
					case WIKI:
					case ITAL:
					case LINK_OPEN:
					case IMAGE_OPEN:
					case NOWIKI_OPEN:
					case EXTENSION:
					case FORCED_LINEBREAK:
					case ESCAPE:
					case NOWIKI_BLOCK_CLOSE:
					case NOWIKI_CLOSE:
					case LINK_CLOSE:
					case IMAGE_CLOSE:
					case BLANKS:
					case CR:
					case LF:
					case SPACE:
					case TABULATOR:
					case COLON_SLASH:
					case SLASH:
					case INSIGNIFICANT_CHAR:
					case 40:
					case 41:
					case 42:
					case 43:
					case 44:
					case 45:
					case 46:
					case 47:
					case 48:
					case 49:
					case 50:
					case 51:
					case 52:
					case 53:
					case 54:
					case 55:
					case 56:
					case 57:
					case 58:
					case 59:
					case 60:
					case 61:
					case 62:
					case 63:
					case 64:
					case 65:
					case 66:
					case 67:
					case 68:
					case 69:
					case 70:
					case 71:
					case 72:
					case 73:
					case 74:
					case 75:
						{
						alt4=6;
						}
						break;
					default:
						if (backtracking>0) {failed=true; return node;}
						NoViableAltException nvae =
							new NoViableAltException("120:4: (h= heading | {...}?hn= horizontalrule | lu= list_unord | lo= list_ord | t= table | tp= text_paragraph )", 4, 0, input);

						throw nvae;
					}

					switch (alt4) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:120:6: h= heading
							{
							pushFollow(FOLLOW_heading_in_paragraph197);
							h=heading();
							_fsp--;
							if (failed) return node;
							if ( backtracking==0 ) {
							   node = h;
							}

							}
							break;
						case 2 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:121:6: {...}?hn= horizontalrule
							{
							if ( !( input.LA(1) == DASH && input.LA(2) == DASH &&
											input.LA(3) == DASH && input.LA(4) == DASH ) ) {
								if (backtracking>0) {failed=true; return node;}
								throw new FailedPredicateException(input, "paragraph", " input.LA(1) == DASH && input.LA(2) == DASH &&\n\t\t\t\tinput.LA(3) == DASH && input.LA(4) == DASH ");
							}
							pushFollow(FOLLOW_horizontalrule_in_paragraph216);
							hn=horizontalrule();
							_fsp--;
							if (failed) return node;
							if ( backtracking==0 ) {
							  node = hn;
							}

							}
							break;
						case 3 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:124:6: lu= list_unord
							{
							pushFollow(FOLLOW_list_unord_in_paragraph228);
							lu=list_unord();
							_fsp--;
							if (failed) return node;
							if ( backtracking==0 ) {
							  node = lu;
							}

							}
							break;
						case 4 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:125:6: lo= list_ord
							{
							pushFollow(FOLLOW_list_ord_in_paragraph241);
							lo=list_ord();
							_fsp--;
							if (failed) return node;
							if ( backtracking==0 ) {
							  node = lo;
							}

							}
							break;
						case 5 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:126:6: t= table
							{
							pushFollow(FOLLOW_table_in_paragraph254);
							t=table();
							_fsp--;
							if (failed) return node;
							if ( backtracking==0 ) {
							   node = t;
							}

							}
							break;
						case 6 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:127:6: tp= text_paragraph
							{
							pushFollow(FOLLOW_text_paragraph_in_paragraph267);
							tp=text_paragraph();
							_fsp--;
							if (failed) return node;
							if ( backtracking==0 ) {
							  node = tp;
							}

							}
							break;

					}

					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:128:7: ( paragraph_separator )?
					int alt5=2;
					int LA5_0 = input.LA(1);

					if ( (LA5_0==NEWLINE) ) {
						alt5=1;
					}
					else if ( (LA5_0==EOF) ) {
						int LA5_2 = input.LA(2);

						if ( (LA5_2==EOF) ) {
							int LA5_4 = input.LA(3);

							if ( (LA5_4==EOF) ) {
								alt5=1;
							}
						}
						else if ( ((LA5_2>=FORCED_END_OF_LINE && LA5_2<=WIKI)||(LA5_2>=POUND && LA5_2<=75)) ) {
							alt5=1;
						}
					}
					switch (alt5) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:128:9: paragraph_separator
							{
							pushFollow(FOLLOW_paragraph_separator_in_paragraph280);
							paragraph_separator();
							_fsp--;
							if (failed) return node;

							}
							break;

					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return node;
	}
	// $ANTLR end paragraph

	// $ANTLR start text_paragraph
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:135:1: text_paragraph returns [ ParagraphNode paragraph = new ParagraphNode() ] : (tl= text_line | ( NOWIKI_OPEN ~ ( NEWLINE ) )=>nw= nowiki_inline (te= text_element )* text_lineseparator )+ ;
	public final ParagraphNode text_paragraph() throws RecognitionException {
		ParagraphNode paragraph =  new ParagraphNode();

		LineNode tl = null;

		NoWikiSectionNode nw = null;

		ASTNode te = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:136:2: ( (tl= text_line | ( NOWIKI_OPEN ~ ( NEWLINE ) )=>nw= nowiki_inline (te= text_element )* text_lineseparator )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:136:4: (tl= text_line | ( NOWIKI_OPEN ~ ( NEWLINE ) )=>nw= nowiki_inline (te= text_element )* text_lineseparator )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:136:4: (tl= text_line | ( NOWIKI_OPEN ~ ( NEWLINE ) )=>nw= nowiki_inline (te= text_element )* text_lineseparator )+
			int cnt8=0;
			loop8:
			do {
				int alt8=3;
				switch ( input.LA(1) ) {
				case NOWIKI_OPEN:
					{
					int LA8_2 = input.LA(2);

					if ( (synpred1()) ) {
						alt8=2;
					}

					}
					break;
				case BLANKS:
					{
					alt8=1;
					}
					break;
				case DASH:
					{
					alt8=1;
					}
					break;
				case STAR:
					{
					int LA8_5 = input.LA(2);

					if ( ( input.LA(1) != STAR || (input.LA(1) == STAR && input.LA(2) == STAR) ) ) {
						alt8=1;
					}

					}
					break;
				case ITAL:
					{
					int LA8_6 = input.LA(2);

					if ( ( input.LA(1) != STAR || (input.LA(1) == STAR && input.LA(2) == STAR) ) ) {
						alt8=1;
					}

					}
					break;
				case FORCED_END_OF_LINE:
				case HEADING_SECTION:
				case HORIZONTAL_SECTION:
				case LIST_ITEM:
				case LIST_ITEM_PART:
				case NOWIKI_SECTION:
				case SCAPE_NODE:
				case TEXT_NODE:
				case UNORDERED_LIST:
				case UNFORMATTED_TEXT:
				case WIKI:
				case NOWIKI_BLOCK_CLOSE:
				case NOWIKI_CLOSE:
				case LINK_CLOSE:
				case IMAGE_CLOSE:
				case CR:
				case LF:
				case SPACE:
				case TABULATOR:
				case COLON_SLASH:
				case SLASH:
				case INSIGNIFICANT_CHAR:
				case 40:
				case 41:
				case 42:
				case 43:
				case 44:
				case 45:
				case 46:
				case 47:
				case 48:
				case 49:
				case 50:
				case 51:
				case 52:
				case 53:
				case 54:
				case 55:
				case 56:
				case 57:
				case 58:
				case 59:
				case 60:
				case 61:
				case 62:
				case 63:
				case 64:
				case 65:
				case 66:
				case 67:
				case 68:
				case 69:
				case 70:
				case 71:
				case 72:
				case 73:
				case 74:
				case 75:
					{
					alt8=1;
					}
					break;
				case FORCED_LINEBREAK:
					{
					alt8=1;
					}
					break;
				case ESCAPE:
					{
					alt8=1;
					}
					break;
				case LINK_OPEN:
					{
					alt8=1;
					}
					break;
				case IMAGE_OPEN:
					{
					alt8=1;
					}
					break;
				case EXTENSION:
					{
					alt8=1;
					}
					break;

				}

				switch (alt8) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:136:6: tl= text_line
					{
					pushFollow(FOLLOW_text_line_in_text_paragraph308);
					tl=text_line();
					_fsp--;
					if (failed) return paragraph;
					if ( backtracking==0 ) {
						paragraph.addChildNode(tl);
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:137:5: ( NOWIKI_OPEN ~ ( NEWLINE ) )=>nw= nowiki_inline (te= text_element )* text_lineseparator
					{
					pushFollow(FOLLOW_nowiki_inline_in_text_paragraph340);
					nw=nowiki_inline();
					_fsp--;
					if (failed) return paragraph;
					if ( backtracking==0 ) {
					  paragraph.addChildNode(nw);
					}
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:138:63: (te= text_element )*
					loop7:
					do {
						int alt7=2;
						int LA7_0 = input.LA(1);

						if ( ((LA7_0>=FORCED_END_OF_LINE && LA7_0<=WIKI)||(LA7_0>=POUND && LA7_0<=75)) ) {
							alt7=1;
						}

						switch (alt7) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:138:65: te= text_element
							{
							pushFollow(FOLLOW_text_element_in_text_paragraph351);
							te=text_element();
							_fsp--;
							if (failed) return paragraph;
							if ( backtracking==0 ) {
							  paragraph.addChildNode(te);
							}

							}
							break;

						default :
							break loop7;
						}
					} while (true);

					pushFollow(FOLLOW_text_lineseparator_in_text_paragraph360);
					text_lineseparator();
					_fsp--;
					if (failed) return paragraph;

					}
					break;

				default :
					if ( cnt8 >= 1 ) break loop8;
					if (backtracking>0) {failed=true; return paragraph;}
						EarlyExitException eee =
							new EarlyExitException(8, input);
						throw eee;
				}
				cnt8++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return paragraph;
	}
	// $ANTLR end text_paragraph

	// $ANTLR start text_line
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:141:1: text_line returns [LineNode line = new LineNode()] : first= text_firstelement (element= text_element )* text_lineseparator ;
	public final LineNode text_line() throws RecognitionException {
		LineNode line =  new LineNode();

		ASTNode first = null;

		ASTNode element = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:142:2: (first= text_firstelement (element= text_element )* text_lineseparator )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:142:4: first= text_firstelement (element= text_element )* text_lineseparator
			{
			pushFollow(FOLLOW_text_firstelement_in_text_line383);
			first=text_firstelement();
			_fsp--;
			if (failed) return line;
			if ( backtracking==0 ) {
			  line.addChildNode(first);
			}
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:142:67: (element= text_element )*
			loop9:
			do {
				int alt9=2;
				int LA9_0 = input.LA(1);

				if ( ((LA9_0>=FORCED_END_OF_LINE && LA9_0<=WIKI)||(LA9_0>=POUND && LA9_0<=75)) ) {
					alt9=1;
				}

				switch (alt9) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:142:69: element= text_element
					{
					pushFollow(FOLLOW_text_element_in_text_line394);
					element=text_element();
					_fsp--;
					if (failed) return line;
					if ( backtracking==0 ) {
					  line.addChildNode(element);
					}

					}
					break;

				default :
					break loop9;
				}
			} while (true);

			pushFollow(FOLLOW_text_lineseparator_in_text_line403);
			text_lineseparator();
			_fsp--;
			if (failed) return line;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return line;
	}
	// $ANTLR end text_line

	// $ANTLR start text_firstelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:144:1: text_firstelement returns [ASTNode item = null] : ({...}?tf= text_formattedelement | tu= text_first_unformattedelement );
	public final ASTNode text_firstelement() throws RecognitionException {
		ASTNode item =  null;

		FormattedTextNode tf = null;

		ASTNode tu = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:145:2: ({...}?tf= text_formattedelement | tu= text_first_unformattedelement )
			int alt10=2;
			int LA10_0 = input.LA(1);

			if ( (LA10_0==STAR||LA10_0==ITAL) ) {
				alt10=1;
			}
			else if ( ((LA10_0>=FORCED_END_OF_LINE && LA10_0<=WIKI)||(LA10_0>=LINK_OPEN && LA10_0<=IMAGE_OPEN)||(LA10_0>=EXTENSION && LA10_0<=75)) ) {
				alt10=2;
			}
			else {
				if (backtracking>0) {failed=true; return item;}
				NoViableAltException nvae =
					new NoViableAltException("144:1: text_firstelement returns [ASTNode item = null] : ({...}?tf= text_formattedelement | tu= text_first_unformattedelement );", 10, 0, input);

				throw nvae;
			}
			switch (alt10) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:145:4: {...}?tf= text_formattedelement
					{
					if ( !( input.LA(1) != STAR || (input.LA(1) == STAR && input.LA(2) == STAR) ) ) {
						if (backtracking>0) {failed=true; return item;}
						throw new FailedPredicateException(input, "text_firstelement", " input.LA(1) != STAR || (input.LA(1) == STAR && input.LA(2) == STAR) ");
					}
					pushFollow(FOLLOW_text_formattedelement_in_text_firstelement425);
					tf=text_formattedelement();
					_fsp--;
					if (failed) return item;
					if ( backtracking==0 ) {
					   item = tf;
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:147:4: tu= text_first_unformattedelement
					{
					pushFollow(FOLLOW_text_first_unformattedelement_in_text_firstelement436);
					tu=text_first_unformattedelement();
					_fsp--;
					if (failed) return item;
					if ( backtracking==0 ) {
					   item = tu;
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return item;
	}
	// $ANTLR end text_firstelement

	// $ANTLR start text_formattedelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:149:1: text_formattedelement returns [FormattedTextNode item = null] : ( ital_markup ic= text_italcontent ( ( NEWLINE )? ital_markup )? | bold_markup bc= text_boldcontent ( ( NEWLINE )? bold_markup )? );
	public final FormattedTextNode text_formattedelement() throws RecognitionException {
		FormattedTextNode item =  null;

		CollectionNode ic = null;

		CollectionNode bc = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:150:2: ( ital_markup ic= text_italcontent ( ( NEWLINE )? ital_markup )? | bold_markup bc= text_boldcontent ( ( NEWLINE )? bold_markup )? )
			int alt15=2;
			int LA15_0 = input.LA(1);

			if ( (LA15_0==ITAL) ) {
				alt15=1;
			}
			else if ( (LA15_0==STAR) ) {
				alt15=2;
			}
			else {
				if (backtracking>0) {failed=true; return item;}
				NoViableAltException nvae =
					new NoViableAltException("149:1: text_formattedelement returns [FormattedTextNode item = null] : ( ital_markup ic= text_italcontent ( ( NEWLINE )? ital_markup )? | bold_markup bc= text_boldcontent ( ( NEWLINE )? bold_markup )? );", 15, 0, input);

				throw nvae;
			}
			switch (alt15) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:150:4: ital_markup ic= text_italcontent ( ( NEWLINE )? ital_markup )?
					{
					pushFollow(FOLLOW_ital_markup_in_text_formattedelement452);
					ital_markup();
					_fsp--;
					if (failed) return item;
					pushFollow(FOLLOW_text_italcontent_in_text_formattedelement458);
					ic=text_italcontent();
					_fsp--;
					if (failed) return item;
					if ( backtracking==0 ) {
					   item = new ItalicTextNode(ic);
					}
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:150:81: ( ( NEWLINE )? ital_markup )?
					int alt12=2;
					int LA12_0 = input.LA(1);

					if ( (LA12_0==NEWLINE) ) {
						int LA12_1 = input.LA(2);

						if ( (LA12_1==ITAL) ) {
							alt12=1;
						}
					}
					else if ( (LA12_0==ITAL) ) {
						alt12=1;
					}
					switch (alt12) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:150:83: ( NEWLINE )? ital_markup
							{
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:150:83: ( NEWLINE )?
							int alt11=2;
							int LA11_0 = input.LA(1);

							if ( (LA11_0==NEWLINE) ) {
								alt11=1;
							}
							switch (alt11) {
								case 1 :
									// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:150:85: NEWLINE
									{
									match(input,NEWLINE,FOLLOW_NEWLINE_in_text_formattedelement467); if (failed) return item;

									}
									break;

							}

							pushFollow(FOLLOW_ital_markup_in_text_formattedelement473);
							ital_markup();
							_fsp--;
							if (failed) return item;

							}
							break;

					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:151:4: bold_markup bc= text_boldcontent ( ( NEWLINE )? bold_markup )?
					{
					pushFollow(FOLLOW_bold_markup_in_text_formattedelement481);
					bold_markup();
					_fsp--;
					if (failed) return item;
					pushFollow(FOLLOW_text_boldcontent_in_text_formattedelement488);
					bc=text_boldcontent();
					_fsp--;
					if (failed) return item;
					if ( backtracking==0 ) {
					  item = new BoldTextNode(bc);
					}
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:151:79: ( ( NEWLINE )? bold_markup )?
					int alt14=2;
					int LA14_0 = input.LA(1);

					if ( (LA14_0==NEWLINE) ) {
						int LA14_1 = input.LA(2);

						if ( (LA14_1==STAR) ) {
							int LA14_4 = input.LA(3);

							if ( (LA14_4==STAR) ) {
								alt14=1;
							}
						}
					}
					else if ( (LA14_0==STAR) ) {
						int LA14_2 = input.LA(2);

						if ( (LA14_2==STAR) ) {
							alt14=1;
						}
					}
					switch (alt14) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:151:81: ( NEWLINE )? bold_markup
							{
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:151:81: ( NEWLINE )?
							int alt13=2;
							int LA13_0 = input.LA(1);

							if ( (LA13_0==NEWLINE) ) {
								alt13=1;
							}
							switch (alt13) {
								case 1 :
									// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:151:83: NEWLINE
									{
									match(input,NEWLINE,FOLLOW_NEWLINE_in_text_formattedelement497); if (failed) return item;

									}
									break;

							}

							pushFollow(FOLLOW_bold_markup_in_text_formattedelement503);
							bold_markup();
							_fsp--;
							if (failed) return item;

							}
							break;

					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return item;
	}
	// $ANTLR end text_formattedelement

	// $ANTLR start text_boldcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:153:1: text_boldcontent returns [ CollectionNode text = new CollectionNode() ] : ( ( NEWLINE )? (p= text_boldcontentpart )* | EOF );
	public final CollectionNode text_boldcontent() throws RecognitionException {
		CollectionNode text =  new CollectionNode();

		FormattedTextNode p = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:154:2: ( ( NEWLINE )? (p= text_boldcontentpart )* | EOF )
			int alt18=2;
			int LA18_0 = input.LA(1);

			if ( ((LA18_0>=FORCED_END_OF_LINE && LA18_0<=75)) ) {
				alt18=1;
			}
			else if ( (LA18_0==EOF) ) {
				alt18=1;
			}
			else {
				if (backtracking>0) {failed=true; return text;}
				NoViableAltException nvae =
					new NoViableAltException("153:1: text_boldcontent returns [ CollectionNode text = new CollectionNode() ] : ( ( NEWLINE )? (p= text_boldcontentpart )* | EOF );", 18, 0, input);

				throw nvae;
			}
			switch (alt18) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:154:4: ( NEWLINE )? (p= text_boldcontentpart )*
					{
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:154:4: ( NEWLINE )?
					int alt16=2;
					int LA16_0 = input.LA(1);

					if ( (LA16_0==NEWLINE) ) {
						alt16=1;
					}
					switch (alt16) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:154:6: NEWLINE
							{
							match(input,NEWLINE,FOLLOW_NEWLINE_in_text_boldcontent522); if (failed) return text;

							}
							break;

					}

					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:154:18: (p= text_boldcontentpart )*
					loop17:
					do {
						int alt17=2;
						switch ( input.LA(1) ) {
						case STAR:
							{
							int LA17_2 = input.LA(2);

							if ( ( input.LA(2) != STAR ) ) {
								alt17=1;
							}

							}
							break;
						case FORCED_END_OF_LINE:
						case HEADING_SECTION:
						case HORIZONTAL_SECTION:
						case LIST_ITEM:
						case LIST_ITEM_PART:
						case NOWIKI_SECTION:
						case SCAPE_NODE:
						case TEXT_NODE:
						case UNORDERED_LIST:
						case UNFORMATTED_TEXT:
						case WIKI:
						case POUND:
						case EQUAL:
						case PIPE:
						case NOWIKI_BLOCK_CLOSE:
						case NOWIKI_CLOSE:
						case LINK_CLOSE:
						case IMAGE_CLOSE:
						case BLANKS:
						case DASH:
						case CR:
						case LF:
						case SPACE:
						case TABULATOR:
						case COLON_SLASH:
						case SLASH:
						case INSIGNIFICANT_CHAR:
						case 40:
						case 41:
						case 42:
						case 43:
						case 44:
						case 45:
						case 46:
						case 47:
						case 48:
						case 49:
						case 50:
						case 51:
						case 52:
						case 53:
						case 54:
						case 55:
						case 56:
						case 57:
						case 58:
						case 59:
						case 60:
						case 61:
						case 62:
						case 63:
						case 64:
						case 65:
						case 66:
						case 67:
						case 68:
						case 69:
						case 70:
						case 71:
						case 72:
						case 73:
						case 74:
						case 75:
							{
							alt17=1;
							}
							break;
						case FORCED_LINEBREAK:
							{
							alt17=1;
							}
							break;
						case ESCAPE:
							{
							alt17=1;
							}
							break;
						case LINK_OPEN:
							{
							alt17=1;
							}
							break;
						case IMAGE_OPEN:
							{
							alt17=1;
							}
							break;
						case EXTENSION:
							{
							alt17=1;
							}
							break;
						case NOWIKI_OPEN:
							{
							alt17=1;
							}
							break;
						case ITAL:
							{
							alt17=1;
							}
							break;

						}

						switch (alt17) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:154:20: p= text_boldcontentpart
							{
							pushFollow(FOLLOW_text_boldcontentpart_in_text_boldcontent534);
							p=text_boldcontentpart();
							_fsp--;
							if (failed) return text;
							if ( backtracking==0 ) {
							   text.add(p);
							}

							}
							break;

						default :
							break loop17;
						}
					} while (true);

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:155:4: EOF
					{
					match(input,EOF,FOLLOW_EOF_in_text_boldcontent545); if (failed) return text;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return text;
	}
	// $ANTLR end text_boldcontent

	// $ANTLR start text_italcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:157:1: text_italcontent returns [ CollectionNode text = new CollectionNode() ] : ( ( NEWLINE )? (p= text_italcontentpart )* | EOF );
	public final CollectionNode text_italcontent() throws RecognitionException {
		CollectionNode text =  new CollectionNode();

		FormattedTextNode p = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:158:2: ( ( NEWLINE )? (p= text_italcontentpart )* | EOF )
			int alt21=2;
			int LA21_0 = input.LA(1);

			if ( ((LA21_0>=FORCED_END_OF_LINE && LA21_0<=75)) ) {
				alt21=1;
			}
			else if ( (LA21_0==EOF) ) {
				alt21=1;
			}
			else {
				if (backtracking>0) {failed=true; return text;}
				NoViableAltException nvae =
					new NoViableAltException("157:1: text_italcontent returns [ CollectionNode text = new CollectionNode() ] : ( ( NEWLINE )? (p= text_italcontentpart )* | EOF );", 21, 0, input);

				throw nvae;
			}
			switch (alt21) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:158:4: ( NEWLINE )? (p= text_italcontentpart )*
					{
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:158:4: ( NEWLINE )?
					int alt19=2;
					int LA19_0 = input.LA(1);

					if ( (LA19_0==NEWLINE) ) {
						alt19=1;
					}
					switch (alt19) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:158:6: NEWLINE
							{
							match(input,NEWLINE,FOLLOW_NEWLINE_in_text_italcontent561); if (failed) return text;

							}
							break;

					}

					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:158:18: (p= text_italcontentpart )*
					loop20:
					do {
						int alt20=2;
						switch ( input.LA(1) ) {
						case STAR:
							{
							alt20=1;
							}
							break;
						case FORCED_END_OF_LINE:
						case HEADING_SECTION:
						case HORIZONTAL_SECTION:
						case LIST_ITEM:
						case LIST_ITEM_PART:
						case NOWIKI_SECTION:
						case SCAPE_NODE:
						case TEXT_NODE:
						case UNORDERED_LIST:
						case UNFORMATTED_TEXT:
						case WIKI:
						case POUND:
						case EQUAL:
						case PIPE:
						case NOWIKI_BLOCK_CLOSE:
						case NOWIKI_CLOSE:
						case LINK_CLOSE:
						case IMAGE_CLOSE:
						case BLANKS:
						case DASH:
						case CR:
						case LF:
						case SPACE:
						case TABULATOR:
						case COLON_SLASH:
						case SLASH:
						case INSIGNIFICANT_CHAR:
						case 40:
						case 41:
						case 42:
						case 43:
						case 44:
						case 45:
						case 46:
						case 47:
						case 48:
						case 49:
						case 50:
						case 51:
						case 52:
						case 53:
						case 54:
						case 55:
						case 56:
						case 57:
						case 58:
						case 59:
						case 60:
						case 61:
						case 62:
						case 63:
						case 64:
						case 65:
						case 66:
						case 67:
						case 68:
						case 69:
						case 70:
						case 71:
						case 72:
						case 73:
						case 74:
						case 75:
							{
							alt20=1;
							}
							break;
						case FORCED_LINEBREAK:
							{
							alt20=1;
							}
							break;
						case ESCAPE:
							{
							alt20=1;
							}
							break;
						case LINK_OPEN:
							{
							alt20=1;
							}
							break;
						case IMAGE_OPEN:
							{
							alt20=1;
							}
							break;
						case EXTENSION:
							{
							alt20=1;
							}
							break;
						case NOWIKI_OPEN:
							{
							alt20=1;
							}
							break;

						}

						switch (alt20) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:158:20: p= text_italcontentpart
							{
							pushFollow(FOLLOW_text_italcontentpart_in_text_italcontent573);
							p=text_italcontentpart();
							_fsp--;
							if (failed) return text;
							if ( backtracking==0 ) {
							   text.add(p);
							}

							}
							break;

						default :
							break loop20;
						}
					} while (true);

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:159:4: EOF
					{
					match(input,EOF,FOLLOW_EOF_in_text_italcontent584); if (failed) return text;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return text;
	}
	// $ANTLR end text_italcontent

	// $ANTLR start text_element
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:161:1: text_element returns [ASTNode item = null] : ( onestar tu1= text_unformattedelement | tu2= text_unformattedelement onestar | tf= text_formattedelement );
	public final ASTNode text_element() throws RecognitionException {
		ASTNode item =  null;

		ASTNode tu1 = null;

		ASTNode tu2 = null;

		FormattedTextNode tf = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:162:2: ( onestar tu1= text_unformattedelement | tu2= text_unformattedelement onestar | tf= text_formattedelement )
			int alt22=3;
			switch ( input.LA(1) ) {
			case STAR:
				{
				int LA22_1 = input.LA(2);

				if ( ( input.LA(2) != STAR ) ) {
					alt22=1;
				}
				else if ( (true) ) {
					alt22=3;
				}
				else {
					if (backtracking>0) {failed=true; return item;}
					NoViableAltException nvae =
						new NoViableAltException("161:1: text_element returns [ASTNode item = null] : ( onestar tu1= text_unformattedelement | tu2= text_unformattedelement onestar | tf= text_formattedelement );", 22, 1, input);

					throw nvae;
				}
				}
				break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case EQUAL:
			case PIPE:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case IMAGE_CLOSE:
			case BLANKS:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case INSIGNIFICANT_CHAR:
			case 40:
			case 41:
			case 42:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
				{
				alt22=1;
				}
				break;
			case FORCED_LINEBREAK:
				{
				alt22=1;
				}
				break;
			case ESCAPE:
				{
				alt22=1;
				}
				break;
			case LINK_OPEN:
				{
				alt22=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt22=1;
				}
				break;
			case EXTENSION:
				{
				alt22=1;
				}
				break;
			case NOWIKI_OPEN:
				{
				alt22=1;
				}
				break;
			case ITAL:
				{
				alt22=3;
				}
				break;
			default:
				if (backtracking>0) {failed=true; return item;}
				NoViableAltException nvae =
					new NoViableAltException("161:1: text_element returns [ASTNode item = null] : ( onestar tu1= text_unformattedelement | tu2= text_unformattedelement onestar | tf= text_formattedelement );", 22, 0, input);

				throw nvae;
			}

			switch (alt22) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:162:4: onestar tu1= text_unformattedelement
					{
					pushFollow(FOLLOW_onestar_in_text_element599);
					onestar();
					_fsp--;
					if (failed) return item;
					pushFollow(FOLLOW_text_unformattedelement_in_text_element606);
					tu1=text_unformattedelement();
					_fsp--;
					if (failed) return item;
					if ( backtracking==0 ) {
					   item = tu1;
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:163:4: tu2= text_unformattedelement onestar
					{
					pushFollow(FOLLOW_text_unformattedelement_in_text_element617);
					tu2=text_unformattedelement();
					_fsp--;
					if (failed) return item;
					pushFollow(FOLLOW_onestar_in_text_element620);
					onestar();
					_fsp--;
					if (failed) return item;
					if ( backtracking==0 ) {
					   item = tu2;
					}

					}
					break;
				case 3 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:164:4: tf= text_formattedelement
					{
					pushFollow(FOLLOW_text_formattedelement_in_text_element631);
					tf=text_formattedelement();
					_fsp--;
					if (failed) return item;
					if ( backtracking==0 ) {
					   item = tf;
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return item;
	}
	// $ANTLR end text_element

	// $ANTLR start text_boldcontentpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:167:1: text_boldcontentpart returns [FormattedTextNode node = null] : ( ital_markup t= text_bolditalcontent ( ital_markup )? | tf= text_formattedcontent );
	public final FormattedTextNode text_boldcontentpart() throws RecognitionException {
		FormattedTextNode node =  null;

		ASTNode t = null;

		CollectionNode tf = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:168:2: ( ital_markup t= text_bolditalcontent ( ital_markup )? | tf= text_formattedcontent )
			int alt24=2;
			int LA24_0 = input.LA(1);

			if ( (LA24_0==ITAL) ) {
				alt24=1;
			}
			else if ( ((LA24_0>=FORCED_END_OF_LINE && LA24_0<=WIKI)||(LA24_0>=POUND && LA24_0<=PIPE)||(LA24_0>=LINK_OPEN && LA24_0<=75)) ) {
				alt24=2;
			}
			else {
				if (backtracking>0) {failed=true; return node;}
				NoViableAltException nvae =
					new NoViableAltException("167:1: text_boldcontentpart returns [FormattedTextNode node = null] : ( ital_markup t= text_bolditalcontent ( ital_markup )? | tf= text_formattedcontent );", 24, 0, input);

				throw nvae;
			}
			switch (alt24) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:168:4: ital_markup t= text_bolditalcontent ( ital_markup )?
					{
					pushFollow(FOLLOW_ital_markup_in_text_boldcontentpart648);
					ital_markup();
					_fsp--;
					if (failed) return node;
					pushFollow(FOLLOW_text_bolditalcontent_in_text_boldcontentpart655);
					t=text_bolditalcontent();
					_fsp--;
					if (failed) return node;
					if ( backtracking==0 ) {
					  node = new ItalicTextNode(t);
					}
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:168:84: ( ital_markup )?
					int alt23=2;
					int LA23_0 = input.LA(1);

					if ( (LA23_0==ITAL) ) {
						alt23=1;
					}
					switch (alt23) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:168:86: ital_markup
							{
							pushFollow(FOLLOW_ital_markup_in_text_boldcontentpart662);
							ital_markup();
							_fsp--;
							if (failed) return node;

							}
							break;

					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:169:4: tf= text_formattedcontent
					{
					pushFollow(FOLLOW_text_formattedcontent_in_text_boldcontentpart674);
					tf=text_formattedcontent();
					_fsp--;
					if (failed) return node;
					if ( backtracking==0 ) {
					  node = new FormattedTextNode(tf);
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return node;
	}
	// $ANTLR end text_boldcontentpart

	// $ANTLR start text_italcontentpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:171:1: text_italcontentpart returns [FormattedTextNode node = null] : ( bold_markup t= text_bolditalcontent ( bold_markup )? | tf= text_formattedcontent );
	public final FormattedTextNode text_italcontentpart() throws RecognitionException {
		FormattedTextNode node =  null;

		ASTNode t = null;

		CollectionNode tf = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:172:2: ( bold_markup t= text_bolditalcontent ( bold_markup )? | tf= text_formattedcontent )
			int alt26=2;
			int LA26_0 = input.LA(1);

			if ( (LA26_0==STAR) ) {
				int LA26_1 = input.LA(2);

				if ( (LA26_1==STAR) ) {
					alt26=1;
				}
				else if ( ((LA26_1>=FORCED_END_OF_LINE && LA26_1<=WIKI)||LA26_1==POUND||(LA26_1>=EQUAL && LA26_1<=PIPE)||(LA26_1>=LINK_OPEN && LA26_1<=75)) ) {
					alt26=2;
				}
				else {
					if (backtracking>0) {failed=true; return node;}
					NoViableAltException nvae =
						new NoViableAltException("171:1: text_italcontentpart returns [FormattedTextNode node = null] : ( bold_markup t= text_bolditalcontent ( bold_markup )? | tf= text_formattedcontent );", 26, 1, input);

					throw nvae;
				}
			}
			else if ( ((LA26_0>=FORCED_END_OF_LINE && LA26_0<=WIKI)||LA26_0==POUND||(LA26_0>=EQUAL && LA26_0<=PIPE)||(LA26_0>=LINK_OPEN && LA26_0<=75)) ) {
				alt26=2;
			}
			else {
				if (backtracking>0) {failed=true; return node;}
				NoViableAltException nvae =
					new NoViableAltException("171:1: text_italcontentpart returns [FormattedTextNode node = null] : ( bold_markup t= text_bolditalcontent ( bold_markup )? | tf= text_formattedcontent );", 26, 0, input);

				throw nvae;
			}
			switch (alt26) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:172:4: bold_markup t= text_bolditalcontent ( bold_markup )?
					{
					pushFollow(FOLLOW_bold_markup_in_text_italcontentpart690);
					bold_markup();
					_fsp--;
					if (failed) return node;
					pushFollow(FOLLOW_text_bolditalcontent_in_text_italcontentpart697);
					t=text_bolditalcontent();
					_fsp--;
					if (failed) return node;
					if ( backtracking==0 ) {
					   node = new BoldTextNode(t);
					}
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:172:82: ( bold_markup )?
					int alt25=2;
					int LA25_0 = input.LA(1);

					if ( (LA25_0==STAR) ) {
						int LA25_1 = input.LA(2);

						if ( (LA25_1==STAR) ) {
							alt25=1;
						}
					}
					switch (alt25) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:172:84: bold_markup
							{
							pushFollow(FOLLOW_bold_markup_in_text_italcontentpart703);
							bold_markup();
							_fsp--;
							if (failed) return node;

							}
							break;

					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:173:4: tf= text_formattedcontent
					{
					pushFollow(FOLLOW_text_formattedcontent_in_text_italcontentpart715);
					tf=text_formattedcontent();
					_fsp--;
					if (failed) return node;
					if ( backtracking==0 ) {
					  node = new FormattedTextNode(tf);
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return node;
	}
	// $ANTLR end text_italcontentpart

	// $ANTLR start text_bolditalcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:175:1: text_bolditalcontent returns [ASTNode items = null] : ( ( NEWLINE )? (tf= text_formattedcontent )? | EOF );
	public final ASTNode text_bolditalcontent() throws RecognitionException {
		ASTNode items =  null;

		CollectionNode tf = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:176:2: ( ( NEWLINE )? (tf= text_formattedcontent )? | EOF )
			int alt29=2;
			int LA29_0 = input.LA(1);

			if ( ((LA29_0>=FORCED_END_OF_LINE && LA29_0<=75)) ) {
				alt29=1;
			}
			else if ( (LA29_0==EOF) ) {
				alt29=1;
			}
			else {
				if (backtracking>0) {failed=true; return items;}
				NoViableAltException nvae =
					new NoViableAltException("175:1: text_bolditalcontent returns [ASTNode items = null] : ( ( NEWLINE )? (tf= text_formattedcontent )? | EOF );", 29, 0, input);

				throw nvae;
			}
			switch (alt29) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:176:4: ( NEWLINE )? (tf= text_formattedcontent )?
					{
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:176:4: ( NEWLINE )?
					int alt27=2;
					int LA27_0 = input.LA(1);

					if ( (LA27_0==NEWLINE) ) {
						alt27=1;
					}
					switch (alt27) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:176:6: NEWLINE
							{
							match(input,NEWLINE,FOLLOW_NEWLINE_in_text_bolditalcontent733); if (failed) return items;

							}
							break;

					}

					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:176:18: (tf= text_formattedcontent )?
					int alt28=2;
					switch ( input.LA(1) ) {
						case STAR:
							{
							int LA28_1 = input.LA(2);

							if ( ( input.LA(2) != STAR ) ) {
								alt28=1;
							}
							}
							break;
						case FORCED_END_OF_LINE:
						case HEADING_SECTION:
						case HORIZONTAL_SECTION:
						case LIST_ITEM:
						case LIST_ITEM_PART:
						case NOWIKI_SECTION:
						case SCAPE_NODE:
						case TEXT_NODE:
						case UNORDERED_LIST:
						case UNFORMATTED_TEXT:
						case WIKI:
						case POUND:
						case EQUAL:
						case PIPE:
						case NOWIKI_BLOCK_CLOSE:
						case NOWIKI_CLOSE:
						case LINK_CLOSE:
						case IMAGE_CLOSE:
						case BLANKS:
						case DASH:
						case CR:
						case LF:
						case SPACE:
						case TABULATOR:
						case COLON_SLASH:
						case SLASH:
						case INSIGNIFICANT_CHAR:
						case 40:
						case 41:
						case 42:
						case 43:
						case 44:
						case 45:
						case 46:
						case 47:
						case 48:
						case 49:
						case 50:
						case 51:
						case 52:
						case 53:
						case 54:
						case 55:
						case 56:
						case 57:
						case 58:
						case 59:
						case 60:
						case 61:
						case 62:
						case 63:
						case 64:
						case 65:
						case 66:
						case 67:
						case 68:
						case 69:
						case 70:
						case 71:
						case 72:
						case 73:
						case 74:
						case 75:
							{
							alt28=1;
							}
							break;
						case FORCED_LINEBREAK:
							{
							alt28=1;
							}
							break;
						case ESCAPE:
							{
							alt28=1;
							}
							break;
						case LINK_OPEN:
							{
							alt28=1;
							}
							break;
						case IMAGE_OPEN:
							{
							alt28=1;
							}
							break;
						case EXTENSION:
							{
							alt28=1;
							}
							break;
						case NOWIKI_OPEN:
							{
							alt28=1;
							}
							break;
					}

					switch (alt28) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:176:20: tf= text_formattedcontent
							{
							pushFollow(FOLLOW_text_formattedcontent_in_text_bolditalcontent744);
							tf=text_formattedcontent();
							_fsp--;
							if (failed) return items;
							if ( backtracking==0 ) {
							  items = tf;
							}

							}
							break;

					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:177:4: EOF
					{
					match(input,EOF,FOLLOW_EOF_in_text_bolditalcontent754); if (failed) return items;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return items;
	}
	// $ANTLR end text_bolditalcontent

	// $ANTLR start text_formattedcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:179:1: text_formattedcontent returns [CollectionNode items = new CollectionNode ()] : onestar (t= text_unformattedelement onestar ( text_linebreak )? )+ ;
	public final CollectionNode text_formattedcontent() throws RecognitionException {
		CollectionNode items =  new CollectionNode ();

		ASTNode t = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:180:2: ( onestar (t= text_unformattedelement onestar ( text_linebreak )? )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:180:4: onestar (t= text_unformattedelement onestar ( text_linebreak )? )+
			{
			pushFollow(FOLLOW_onestar_in_text_formattedcontent768);
			onestar();
			_fsp--;
			if (failed) return items;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:180:13: (t= text_unformattedelement onestar ( text_linebreak )? )+
			int cnt31=0;
			loop31:
			do {
				int alt31=2;
				switch ( input.LA(1) ) {
				case FORCED_END_OF_LINE:
				case HEADING_SECTION:
				case HORIZONTAL_SECTION:
				case LIST_ITEM:
				case LIST_ITEM_PART:
				case NOWIKI_SECTION:
				case SCAPE_NODE:
				case TEXT_NODE:
				case UNORDERED_LIST:
				case UNFORMATTED_TEXT:
				case WIKI:
				case POUND:
				case EQUAL:
				case PIPE:
				case NOWIKI_BLOCK_CLOSE:
				case NOWIKI_CLOSE:
				case LINK_CLOSE:
				case IMAGE_CLOSE:
				case BLANKS:
				case DASH:
				case CR:
				case LF:
				case SPACE:
				case TABULATOR:
				case COLON_SLASH:
				case SLASH:
				case INSIGNIFICANT_CHAR:
				case 40:
				case 41:
				case 42:
				case 43:
				case 44:
				case 45:
				case 46:
				case 47:
				case 48:
				case 49:
				case 50:
				case 51:
				case 52:
				case 53:
				case 54:
				case 55:
				case 56:
				case 57:
				case 58:
				case 59:
				case 60:
				case 61:
				case 62:
				case 63:
				case 64:
				case 65:
				case 66:
				case 67:
				case 68:
				case 69:
				case 70:
				case 71:
				case 72:
				case 73:
				case 74:
				case 75:
					{
					alt31=1;
					}
					break;
				case FORCED_LINEBREAK:
					{
					alt31=1;
					}
					break;
				case ESCAPE:
					{
					alt31=1;
					}
					break;
				case LINK_OPEN:
					{
					alt31=1;
					}
					break;
				case IMAGE_OPEN:
					{
					alt31=1;
					}
					break;
				case EXTENSION:
					{
					alt31=1;
					}
					break;
				case NOWIKI_OPEN:
					{
					alt31=1;
					}
					break;

				}

				switch (alt31) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:180:15: t= text_unformattedelement onestar ( text_linebreak )?
					{
					pushFollow(FOLLOW_text_unformattedelement_in_text_formattedcontent777);
					t=text_unformattedelement();
					_fsp--;
					if (failed) return items;
					if ( backtracking==0 ) {
					  items.add(t);
					}
					pushFollow(FOLLOW_onestar_in_text_formattedcontent782);
					onestar();
					_fsp--;
					if (failed) return items;
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:180:81: ( text_linebreak )?
					int alt30=2;
					int LA30_0 = input.LA(1);

					if ( (LA30_0==NEWLINE) ) {
						int LA30_1 = input.LA(2);

						if ( ( input.LA(2) != DASH && input.LA(2) != POUND &&
								input.LA(2) != EQUAL && input.LA(2) != NEWLINE ) ) {
							alt30=1;
						}
					}
					else if ( (LA30_0==EOF) ) {
						int LA30_2 = input.LA(2);

						if ( ( input.LA(2) != DASH && input.LA(2) != POUND &&
								input.LA(2) != EQUAL && input.LA(2) != NEWLINE ) ) {
							alt30=1;
						}
					}
					switch (alt30) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:180:83: text_linebreak
							{
							pushFollow(FOLLOW_text_linebreak_in_text_formattedcontent787);
							text_linebreak();
							_fsp--;
							if (failed) return items;

							}
							break;

					}

					}
					break;

				default :
					if ( cnt31 >= 1 ) break loop31;
					if (backtracking>0) {failed=true; return items;}
						EarlyExitException eee =
							new EarlyExitException(31, input);
						throw eee;
				}
				cnt31++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return items;
	}
	// $ANTLR end text_formattedcontent

	// $ANTLR start text_linebreak
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:182:1: text_linebreak : {...}? text_lineseparator ;
	public final void text_linebreak() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:183:2: ({...}? text_lineseparator )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:183:4: {...}? text_lineseparator
			{
			if ( !( input.LA(2) != DASH && input.LA(2) != POUND &&
					input.LA(2) != EQUAL && input.LA(2) != NEWLINE ) ) {
				if (backtracking>0) {failed=true; return ;}
				throw new FailedPredicateException(input, "text_linebreak", " input.LA(2) != DASH && input.LA(2) != POUND && \n\t\tinput.LA(2) != EQUAL && input.LA(2) != NEWLINE ");
			}
			pushFollow(FOLLOW_text_lineseparator_in_text_linebreak807);
			text_lineseparator();
			_fsp--;
			if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end text_linebreak

	// $ANTLR start text_inlineelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:187:1: text_inlineelement returns [ASTNode element = null ] : (tf= text_first_inlineelement | nwi= nowiki_inline );
	public final ASTNode text_inlineelement() throws RecognitionException {
		ASTNode element =  null;

		ASTNode tf = null;

		NoWikiSectionNode nwi = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:188:2: (tf= text_first_inlineelement | nwi= nowiki_inline )
			int alt32=2;
			int LA32_0 = input.LA(1);

			if ( ((LA32_0>=LINK_OPEN && LA32_0<=IMAGE_OPEN)||LA32_0==EXTENSION) ) {
				alt32=1;
			}
			else if ( (LA32_0==NOWIKI_OPEN) ) {
				alt32=2;
			}
			else {
				if (backtracking>0) {failed=true; return element;}
				NoViableAltException nvae =
					new NoViableAltException("187:1: text_inlineelement returns [ASTNode element = null ] : (tf= text_first_inlineelement | nwi= nowiki_inline );", 32, 0, input);

				throw nvae;
			}
			switch (alt32) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:188:4: tf= text_first_inlineelement
					{
					pushFollow(FOLLOW_text_first_inlineelement_in_text_inlineelement825);
					tf=text_first_inlineelement();
					_fsp--;
					if (failed) return element;
					if ( backtracking==0 ) {
					  element = tf;
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:189:4: nwi= nowiki_inline
					{
					pushFollow(FOLLOW_nowiki_inline_in_text_inlineelement836);
					nwi=nowiki_inline();
					_fsp--;
					if (failed) return element;
					if ( backtracking==0 ) {
					  element = nwi;
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return element;
	}
	// $ANTLR end text_inlineelement

	// $ANTLR start text_first_inlineelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:191:1: text_first_inlineelement returns [ASTNode element = null] : (l= link | i= image | e= extension );
	public final ASTNode text_first_inlineelement() throws RecognitionException {
		ASTNode element =  null;

		LinkNode l = null;

		ImageNode i = null;

		ASTNode e = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:192:2: (l= link | i= image | e= extension )
			int alt33=3;
			switch ( input.LA(1) ) {
			case LINK_OPEN:
				{
				alt33=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt33=2;
				}
				break;
			case EXTENSION:
				{
				alt33=3;
				}
				break;
			default:
				if (backtracking>0) {failed=true; return element;}
				NoViableAltException nvae =
					new NoViableAltException("191:1: text_first_inlineelement returns [ASTNode element = null] : (l= link | i= image | e= extension );", 33, 0, input);

				throw nvae;
			}

			switch (alt33) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:193:3: l= link
					{
					pushFollow(FOLLOW_link_in_text_first_inlineelement859);
					l=link();
					_fsp--;
					if (failed) return element;
					if ( backtracking==0 ) {
					  element = l;
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:194:4: i= image
					{
					pushFollow(FOLLOW_image_in_text_first_inlineelement870);
					i=image();
					_fsp--;
					if (failed) return element;
					if ( backtracking==0 ) {
					  element = i;
					}

					}
					break;
				case 3 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:195:4: e= extension
					{
					pushFollow(FOLLOW_extension_in_text_first_inlineelement879);
					e=extension();
					_fsp--;
					if (failed) return element;
					if ( backtracking==0 ) {
					  element = e;
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return element;
	}
	// $ANTLR end text_first_inlineelement

	// $ANTLR start text_first_unformattedelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:197:1: text_first_unformattedelement returns [ASTNode item = null] : (tfu= text_first_unformatted | tfi= text_first_inlineelement );
	public final ASTNode text_first_unformattedelement() throws RecognitionException {
		ASTNode item =  null;

		CollectionNode tfu = null;

		ASTNode tfi = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:198:2: (tfu= text_first_unformatted | tfi= text_first_inlineelement )
			int alt34=2;
			int LA34_0 = input.LA(1);

			if ( ((LA34_0>=FORCED_END_OF_LINE && LA34_0<=WIKI)||(LA34_0>=FORCED_LINEBREAK && LA34_0<=75)) ) {
				alt34=1;
			}
			else if ( ((LA34_0>=LINK_OPEN && LA34_0<=IMAGE_OPEN)||LA34_0==EXTENSION) ) {
				alt34=2;
			}
			else {
				if (backtracking>0) {failed=true; return item;}
				NoViableAltException nvae =
					new NoViableAltException("197:1: text_first_unformattedelement returns [ASTNode item = null] : (tfu= text_first_unformatted | tfi= text_first_inlineelement );", 34, 0, input);

				throw nvae;
			}
			switch (alt34) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:198:4: tfu= text_first_unformatted
					{
					pushFollow(FOLLOW_text_first_unformatted_in_text_first_unformattedelement899);
					tfu=text_first_unformatted();
					_fsp--;
					if (failed) return item;
					if ( backtracking==0 ) {
					  item = new UnformattedTextNode(tfu);
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:199:4: tfi= text_first_inlineelement
					{
					pushFollow(FOLLOW_text_first_inlineelement_in_text_first_unformattedelement910);
					tfi=text_first_inlineelement();
					_fsp--;
					if (failed) return item;
					if ( backtracking==0 ) {
					   item = tfi;
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return item;
	}
	// $ANTLR end text_first_unformattedelement

	// $ANTLR start text_first_unformatted
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:201:1: text_first_unformatted returns [CollectionNode items = new CollectionNode()] : (t= text_first_unformmatted_text | ( forced_linebreak | e= escaped )+ );
	public final CollectionNode text_first_unformatted() throws RecognitionException {
		CollectionNode items =  new CollectionNode();

		String t = null;

		ScapedNode e = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:202:2: (t= text_first_unformmatted_text | ( forced_linebreak | e= escaped )+ )
			int alt36=2;
			int LA36_0 = input.LA(1);

			if ( ((LA36_0>=FORCED_END_OF_LINE && LA36_0<=WIKI)||(LA36_0>=NOWIKI_BLOCK_CLOSE && LA36_0<=75)) ) {
				alt36=1;
			}
			else if ( ((LA36_0>=FORCED_LINEBREAK && LA36_0<=ESCAPE)) ) {
				alt36=2;
			}
			else {
				if (backtracking>0) {failed=true; return items;}
				NoViableAltException nvae =
					new NoViableAltException("201:1: text_first_unformatted returns [CollectionNode items = new CollectionNode()] : (t= text_first_unformmatted_text | ( forced_linebreak | e= escaped )+ );", 36, 0, input);

				throw nvae;
			}
			switch (alt36) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:202:6: t= text_first_unformmatted_text
					{
					pushFollow(FOLLOW_text_first_unformmatted_text_in_text_first_unformatted932);
					t=text_first_unformmatted_text();
					_fsp--;
					if (failed) return items;
					if ( backtracking==0 ) {
					  items.add(new UnformattedTextNode(t));
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:203:5: ( forced_linebreak | e= escaped )+
					{
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:203:5: ( forced_linebreak | e= escaped )+
					int cnt35=0;
					loop35:
					do {
						int alt35=3;
						int LA35_0 = input.LA(1);

						if ( (LA35_0==FORCED_LINEBREAK) ) {
							alt35=1;
						}
						else if ( (LA35_0==ESCAPE) ) {
							int LA35_3 = input.LA(2);

							if ( (LA35_3==STAR) ) {
								alt35=2;
							}
							else if ( ((LA35_3>=FORCED_END_OF_LINE && LA35_3<=POUND)||(LA35_3>=EQUAL && LA35_3<=75)) ) {
								alt35=2;
							}

						}

						switch (alt35) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:203:6: forced_linebreak
							{
							pushFollow(FOLLOW_forced_linebreak_in_text_first_unformatted941);
							forced_linebreak();
							_fsp--;
							if (failed) return items;
							if ( backtracking==0 ) {
							   items.add(new ForcedEndOfLineNode());
							}

							}
							break;
						case 2 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:204:5: e= escaped
							{
							pushFollow(FOLLOW_escaped_in_text_first_unformatted953);
							e=escaped();
							_fsp--;
							if (failed) return items;
							if ( backtracking==0 ) {
							  items.add(e);
							}

							}
							break;

						default :
							if ( cnt35 >= 1 ) break loop35;
							if (backtracking>0) {failed=true; return items;}
								EarlyExitException eee =
									new EarlyExitException(35, input);
								throw eee;
						}
						cnt35++;
					} while (true);

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return items;
	}
	// $ANTLR end text_first_unformatted

	// $ANTLR start text_first_unformmatted_text
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:207:1: text_first_unformmatted_text returns [String text = new String()] : (c=~ ( POUND | STAR | EQUAL | PIPE | ITAL | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ ;
	public final String text_first_unformmatted_text() throws RecognitionException {
		String text =  new String();

		Token c=null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:208:2: ( (c=~ ( POUND | STAR | EQUAL | PIPE | ITAL | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:209:3: (c=~ ( POUND | STAR | EQUAL | PIPE | ITAL | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:209:3: (c=~ ( POUND | STAR | EQUAL | PIPE | ITAL | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
			int cnt37=0;
			loop37:
			do {
				int alt37=2;
				int LA37_0 = input.LA(1);

				if ( ((LA37_0>=FORCED_END_OF_LINE && LA37_0<=WIKI)||(LA37_0>=NOWIKI_BLOCK_CLOSE && LA37_0<=75)) ) {
					alt37=1;
				}

				switch (alt37) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:209:4: c=~ ( POUND | STAR | EQUAL | PIPE | ITAL | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF )
					{
					c=(Token)input.LT(1);
					if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=75) ) {
						input.consume();
						errorRecovery=false;failed=false;
					}
					else {
						if (backtracking>0) {failed=true; return text;}
						MismatchedSetException mse =
							new MismatchedSetException(null,input);
						recoverFromMismatchedSet(input,mse,FOLLOW_set_in_text_first_unformmatted_text982);	throw mse;
					}

					if ( backtracking==0 ) {
					  text += c.getText();
					}

					}
					break;

				default :
					if ( cnt37 >= 1 ) break loop37;
					if (backtracking>0) {failed=true; return text;}
						EarlyExitException eee =
							new EarlyExitException(37, input);
						throw eee;
				}
				cnt37++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return text;
	}
	// $ANTLR end text_first_unformmatted_text

	// $ANTLR start text_unformattedelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:223:1: text_unformattedelement returns [ASTNode contents = null] : (text= text_unformatted | ti= text_inlineelement );
	public final ASTNode text_unformattedelement() throws RecognitionException {
		ASTNode contents =  null;

		CollectionNode text = null;

		ASTNode ti = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:224:2: (text= text_unformatted | ti= text_inlineelement )
			int alt38=2;
			int LA38_0 = input.LA(1);

			if ( ((LA38_0>=FORCED_END_OF_LINE && LA38_0<=WIKI)||LA38_0==POUND||(LA38_0>=EQUAL && LA38_0<=PIPE)||(LA38_0>=FORCED_LINEBREAK && LA38_0<=75)) ) {
				alt38=1;
			}
			else if ( ((LA38_0>=LINK_OPEN && LA38_0<=EXTENSION)) ) {
				alt38=2;
			}
			else {
				if (backtracking>0) {failed=true; return contents;}
				NoViableAltException nvae =
					new NoViableAltException("223:1: text_unformattedelement returns [ASTNode contents = null] : (text= text_unformatted | ti= text_inlineelement );", 38, 0, input);

				throw nvae;
			}
			switch (alt38) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:224:4: text= text_unformatted
					{
					pushFollow(FOLLOW_text_unformatted_in_text_unformattedelement1097);
					text=text_unformatted();
					_fsp--;
					if (failed) return contents;
					if ( backtracking==0 ) {
					   contents = text;
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:225:4: ti= text_inlineelement
					{
					pushFollow(FOLLOW_text_inlineelement_in_text_unformattedelement1108);
					ti=text_inlineelement();
					_fsp--;
					if (failed) return contents;
					if ( backtracking==0 ) {
					   contents = ti;
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return contents;
	}
	// $ANTLR end text_unformattedelement

	// $ANTLR start text_unformatted
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:228:1: text_unformatted returns [CollectionNode items = new CollectionNode()] : (contents= text_unformated_text | ( forced_linebreak | e= escaped )+ );
	public final CollectionNode text_unformatted() throws RecognitionException {
		CollectionNode items =  new CollectionNode();

		String contents = null;

		ScapedNode e = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:229:2: (contents= text_unformated_text | ( forced_linebreak | e= escaped )+ )
			int alt40=2;
			int LA40_0 = input.LA(1);

			if ( ((LA40_0>=FORCED_END_OF_LINE && LA40_0<=WIKI)||LA40_0==POUND||(LA40_0>=EQUAL && LA40_0<=PIPE)||(LA40_0>=NOWIKI_BLOCK_CLOSE && LA40_0<=75)) ) {
				alt40=1;
			}
			else if ( ((LA40_0>=FORCED_LINEBREAK && LA40_0<=ESCAPE)) ) {
				alt40=2;
			}
			else {
				if (backtracking>0) {failed=true; return items;}
				NoViableAltException nvae =
					new NoViableAltException("228:1: text_unformatted returns [CollectionNode items = new CollectionNode()] : (contents= text_unformated_text | ( forced_linebreak | e= escaped )+ );", 40, 0, input);

				throw nvae;
			}
			switch (alt40) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:229:5: contents= text_unformated_text
					{
					pushFollow(FOLLOW_text_unformated_text_in_text_unformatted1130);
					contents=text_unformated_text();
					_fsp--;
					if (failed) return items;
					if ( backtracking==0 ) {
					  items.add(new UnformattedTextNode(contents));
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:230:5: ( forced_linebreak | e= escaped )+
					{
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:230:5: ( forced_linebreak | e= escaped )+
					int cnt39=0;
					loop39:
					do {
						int alt39=3;
						int LA39_0 = input.LA(1);

						if ( (LA39_0==FORCED_LINEBREAK) ) {
							alt39=1;
						}
						else if ( (LA39_0==ESCAPE) ) {
							alt39=2;
						}

						switch (alt39) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:230:6: forced_linebreak
							{
							pushFollow(FOLLOW_forced_linebreak_in_text_unformatted1139);
							forced_linebreak();
							_fsp--;
							if (failed) return items;
							if ( backtracking==0 ) {
							   items.add(new ForcedEndOfLineNode());
							}

							}
							break;
						case 2 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:231:5: e= escaped
							{
							pushFollow(FOLLOW_escaped_in_text_unformatted1151);
							e=escaped();
							_fsp--;
							if (failed) return items;
							if ( backtracking==0 ) {
							  items.add(e);
							}

							}
							break;

						default :
							if ( cnt39 >= 1 ) break loop39;
							if (backtracking>0) {failed=true; return items;}
								EarlyExitException eee =
									new EarlyExitException(39, input);
								throw eee;
						}
						cnt39++;
					} while (true);

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return items;
	}
	// $ANTLR end text_unformatted

	// $ANTLR start text_unformated_text
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:234:1: text_unformated_text returns [String text = new String()] : (c=~ ( ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ ;
	public final String text_unformated_text() throws RecognitionException {
		String text =  new String();

		Token c=null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:235:1: ( (c=~ ( ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:236:2: (c=~ ( ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:236:2: (c=~ ( ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
			int cnt41=0;
			loop41:
			do {
				int alt41=2;
				int LA41_0 = input.LA(1);

				if ( ((LA41_0>=FORCED_END_OF_LINE && LA41_0<=WIKI)||LA41_0==POUND||(LA41_0>=EQUAL && LA41_0<=PIPE)||(LA41_0>=NOWIKI_BLOCK_CLOSE && LA41_0<=75)) ) {
					alt41=1;
				}

				switch (alt41) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:236:3: c=~ ( ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF )
					{
					c=(Token)input.LT(1);
					if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||input.LA(1)==POUND||(input.LA(1)>=EQUAL && input.LA(1)<=PIPE)||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=75) ) {
						input.consume();
						errorRecovery=false;failed=false;
					}
					else {
						if (backtracking>0) {failed=true; return text;}
						MismatchedSetException mse =
							new MismatchedSetException(null,input);
						recoverFromMismatchedSet(input,mse,FOLLOW_set_in_text_unformated_text1177);	throw mse;
					}

					if ( backtracking==0 ) {
					   text += c.getText();
					}

					}
					break;

				default :
					if ( cnt41 >= 1 ) break loop41;
					if (backtracking>0) {failed=true; return text;}
						EarlyExitException eee =
							new EarlyExitException(41, input);
						throw eee;
				}
				cnt41++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return text;
	}
	// $ANTLR end text_unformated_text

	protected static class heading_scope {
		int nestedLevel;
		String text;
	}
	protected Stack heading_stack = new Stack();

	// $ANTLR start heading
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:250:1: heading returns [ASTNode header] : heading_markup heading_content ( heading_markup )? ( blanks )? paragraph_separator ;
	public final ASTNode heading() throws RecognitionException {
		heading_stack.push(new heading_scope());
		ASTNode header = null;

				((heading_scope)heading_stack.peek()).text = new String();

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:258:2: ( heading_markup heading_content ( heading_markup )? ( blanks )? paragraph_separator )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:258:4: heading_markup heading_content ( heading_markup )? ( blanks )? paragraph_separator
			{
			pushFollow(FOLLOW_heading_markup_in_heading1281);
			heading_markup();
			_fsp--;
			if (failed) return header;
			if ( backtracking==0 ) {
			  ((heading_scope)heading_stack.peek()).nestedLevel++;
			}
			pushFollow(FOLLOW_heading_content_in_heading1286);
			heading_content();
			_fsp--;
			if (failed) return header;
			if ( backtracking==0 ) {
			   header = new HeadingNode(((heading_scope)heading_stack.peek()).text,((heading_scope)heading_stack.peek()).nestedLevel);
			}
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:258:133: ( heading_markup )?
			int alt42=2;
			int LA42_0 = input.LA(1);

			if ( (LA42_0==EQUAL) ) {
				alt42=1;
			}
			switch (alt42) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:258:135: heading_markup
					{
					pushFollow(FOLLOW_heading_markup_in_heading1293);
					heading_markup();
					_fsp--;
					if (failed) return header;

					}
					break;

			}

			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:258:154: ( blanks )?
			int alt43=2;
			int LA43_0 = input.LA(1);

			if ( (LA43_0==BLANKS) ) {
				alt43=1;
			}
			switch (alt43) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:258:156: blanks
					{
					pushFollow(FOLLOW_blanks_in_heading1301);
					blanks();
					_fsp--;
					if (failed) return header;

					}
					break;

			}

			pushFollow(FOLLOW_paragraph_separator_in_heading1308);
			paragraph_separator();
			_fsp--;
			if (failed) return header;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			heading_stack.pop();
		}
		return header;
	}
	// $ANTLR end heading

	// $ANTLR start heading_content
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:261:1: heading_content : ( heading_markup heading_content ( heading_markup )? | heading_text );
	public final void heading_content() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:262:2: ( heading_markup heading_content ( heading_markup )? | heading_text )
			int alt45=2;
			int LA45_0 = input.LA(1);

			if ( (LA45_0==EQUAL) ) {
				alt45=1;
			}
			else if ( ((LA45_0>=FORCED_END_OF_LINE && LA45_0<=WIKI)||(LA45_0>=POUND && LA45_0<=STAR)||(LA45_0>=PIPE && LA45_0<=75)) ) {
				alt45=2;
			}
			else {
				if (backtracking>0) {failed=true; return ;}
				NoViableAltException nvae =
					new NoViableAltException("261:1: heading_content : ( heading_markup heading_content ( heading_markup )? | heading_text );", 45, 0, input);

				throw nvae;
			}
			switch (alt45) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:262:4: heading_markup heading_content ( heading_markup )?
					{
					pushFollow(FOLLOW_heading_markup_in_heading_content1318);
					heading_markup();
					_fsp--;
					if (failed) return ;
					if ( backtracking==0 ) {
					  ((heading_scope)heading_stack.peek()).nestedLevel++;
					}
					pushFollow(FOLLOW_heading_content_in_heading_content1323);
					heading_content();
					_fsp--;
					if (failed) return ;
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:262:64: ( heading_markup )?
					int alt44=2;
					int LA44_0 = input.LA(1);

					if ( (LA44_0==EQUAL) ) {
						alt44=1;
					}
					switch (alt44) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:262:66: heading_markup
							{
							pushFollow(FOLLOW_heading_markup_in_heading_content1328);
							heading_markup();
							_fsp--;
							if (failed) return ;

							}
							break;

					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:263:4: heading_text
					{
					pushFollow(FOLLOW_heading_text_in_heading_content1336);
					heading_text();
					_fsp--;
					if (failed) return ;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end heading_content

	// $ANTLR start heading_text
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:266:1: heading_text : ( (contents=~ ( EQUAL | ESCAPE | NEWLINE | EOF ) | symbol= escaped ) )+ ;
	public final void heading_text() throws RecognitionException {
		Token contents=null;
		ScapedNode symbol = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:267:2: ( ( (contents=~ ( EQUAL | ESCAPE | NEWLINE | EOF ) | symbol= escaped ) )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:268:3: ( (contents=~ ( EQUAL | ESCAPE | NEWLINE | EOF ) | symbol= escaped ) )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:268:3: ( (contents=~ ( EQUAL | ESCAPE | NEWLINE | EOF ) | symbol= escaped ) )+
			int cnt47=0;
			loop47:
			do {
				int alt47=2;
				int LA47_0 = input.LA(1);

				if ( ((LA47_0>=FORCED_END_OF_LINE && LA47_0<=WIKI)||(LA47_0>=POUND && LA47_0<=STAR)||(LA47_0>=PIPE && LA47_0<=75)) ) {
					alt47=1;
				}

				switch (alt47) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:268:6: (contents=~ ( EQUAL | ESCAPE | NEWLINE | EOF ) | symbol= escaped )
					{
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:268:6: (contents=~ ( EQUAL | ESCAPE | NEWLINE | EOF ) | symbol= escaped )
					int alt46=2;
					int LA46_0 = input.LA(1);

					if ( ((LA46_0>=FORCED_END_OF_LINE && LA46_0<=WIKI)||(LA46_0>=POUND && LA46_0<=STAR)||(LA46_0>=PIPE && LA46_0<=FORCED_LINEBREAK)||(LA46_0>=NOWIKI_BLOCK_CLOSE && LA46_0<=75)) ) {
						alt46=1;
					}
					else if ( (LA46_0==ESCAPE) ) {
						alt46=2;
					}
					else {
						if (backtracking>0) {failed=true; return ;}
						NoViableAltException nvae =
							new NoViableAltException("268:6: (contents=~ ( EQUAL | ESCAPE | NEWLINE | EOF ) | symbol= escaped )", 46, 0, input);

						throw nvae;
					}
					switch (alt46) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:268:7: contents=~ ( EQUAL | ESCAPE | NEWLINE | EOF )
							{
							contents=(Token)input.LT(1);
							if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=STAR)||(input.LA(1)>=PIPE && input.LA(1)<=FORCED_LINEBREAK)||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=75) ) {
								input.consume();
								errorRecovery=false;failed=false;
							}
							else {
								if (backtracking>0) {failed=true; return ;}
								MismatchedSetException mse =
									new MismatchedSetException(null,input);
								recoverFromMismatchedSet(input,mse,FOLLOW_set_in_heading_text1357);	throw mse;
							}

							if ( backtracking==0 ) {
							   ((heading_scope)heading_stack.peek()).text += contents.getText();
							}

							}
							break;
						case 2 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:269:6: symbol= escaped
							{
							pushFollow(FOLLOW_escaped_in_heading_text1388);
							symbol=escaped();
							_fsp--;
							if (failed) return ;
							if ( backtracking==0 ) {
							   ((heading_scope)heading_stack.peek()).text += symbol.getContent();
							}

							}
							break;

					}

					}
					break;

				default :
					if ( cnt47 >= 1 ) break loop47;
					if (backtracking>0) {failed=true; return ;}
						EarlyExitException eee =
							new EarlyExitException(47, input);
						throw eee;
				}
				cnt47++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end heading_text

	// $ANTLR start list_ord
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:274:1: list_ord returns [OrderedListNode orderedList = new OrderedListNode()] : (elem= list_ordelem )+ ( end_of_list )? ;
	public final OrderedListNode list_ord() throws RecognitionException {
		OrderedListNode orderedList =  new OrderedListNode();

		ASTNode elem = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:275:2: ( (elem= list_ordelem )+ ( end_of_list )? )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:275:4: (elem= list_ordelem )+ ( end_of_list )?
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:275:4: (elem= list_ordelem )+
			int cnt48=0;
			loop48:
			do {
				int alt48=2;
				int LA48_0 = input.LA(1);

				if ( (LA48_0==POUND) ) {
					alt48=1;
				}

				switch (alt48) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:275:6: elem= list_ordelem
					{
					pushFollow(FOLLOW_list_ordelem_in_list_ord1422);
					elem=list_ordelem();
					_fsp--;
					if (failed) return orderedList;
					if ( backtracking==0 ) {
					   orderedList.addChildNode(elem);
					}

					}
					break;

				default :
					if ( cnt48 >= 1 ) break loop48;
					if (backtracking>0) {failed=true; return orderedList;}
						EarlyExitException eee =
							new EarlyExitException(48, input);
						throw eee;
				}
				cnt48++;
			} while (true);

			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:275:74: ( end_of_list )?
			int alt49=2;
			int LA49_0 = input.LA(1);

			if ( (LA49_0==NEWLINE) ) {
				alt49=1;
			}
			else if ( (LA49_0==EOF) ) {
				alt49=1;
			}
			switch (alt49) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:275:76: end_of_list
					{
					pushFollow(FOLLOW_end_of_list_in_list_ord1432);
					end_of_list();
					_fsp--;
					if (failed) return orderedList;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return orderedList;
	}
	// $ANTLR end list_ord

	// $ANTLR start list_ordelem
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:277:1: list_ordelem returns [ASTNode item = null] : om= list_ordelem_markup elem= list_elem ;
	public final ASTNode list_ordelem() throws RecognitionException {
		CountLevel_stack.push(new CountLevel_scope());

		ASTNode item =  null;

		list_ordelem_markup_return om = null;

		CollectionNode elem = null;


				((CountLevel_scope)CountLevel_stack.peek()).level = 0;
				((CountLevel_scope)CountLevel_stack.peek()).groups = new String();

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:283:2: (om= list_ordelem_markup elem= list_elem )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:283:4: om= list_ordelem_markup elem= list_elem
			{
			pushFollow(FOLLOW_list_ordelem_markup_in_list_ordelem1465);
			om=list_ordelem_markup();
			_fsp--;
			if (failed) return item;
			if ( backtracking==0 ) {
			  ++((CountLevel_scope)CountLevel_stack.peek()).level; ((CountLevel_scope)CountLevel_stack.peek()).currentMarkup = input.toString(om.start,om.stop); ((CountLevel_scope)CountLevel_stack.peek()).groups += input.toString(om.start,om.stop);
			}
			pushFollow(FOLLOW_list_elem_in_list_ordelem1473);
			elem=list_elem();
			_fsp--;
			if (failed) return item;
			if ( backtracking==0 ) {
			   item = new OrderedListItemNode(((CountLevel_scope)CountLevel_stack.peek()).level, elem);
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			CountLevel_stack.pop();

		}
		return item;
	}
	// $ANTLR end list_ordelem

	// $ANTLR start list_unord
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:286:1: list_unord returns [UnorderedListNode unorderedList = new UnorderedListNode()] : (elem= list_unordelem )+ ( end_of_list )? ;
	public final UnorderedListNode list_unord() throws RecognitionException {
		UnorderedListNode unorderedList =  new UnorderedListNode();

		UnorderedListItemNode elem = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:287:2: ( (elem= list_unordelem )+ ( end_of_list )? )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:287:4: (elem= list_unordelem )+ ( end_of_list )?
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:287:4: (elem= list_unordelem )+
			int cnt50=0;
			loop50:
			do {
				int alt50=2;
				int LA50_0 = input.LA(1);

				if ( (LA50_0==STAR) ) {
					alt50=1;
				}

				switch (alt50) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:287:6: elem= list_unordelem
					{
					pushFollow(FOLLOW_list_unordelem_in_list_unord1497);
					elem=list_unordelem();
					_fsp--;
					if (failed) return unorderedList;
					if ( backtracking==0 ) {
					   unorderedList.addChildNode(elem);
					}

					}
					break;

				default :
					if ( cnt50 >= 1 ) break loop50;
					if (backtracking>0) {failed=true; return unorderedList;}
						EarlyExitException eee =
							new EarlyExitException(50, input);
						throw eee;
				}
				cnt50++;
			} while (true);

			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:287:77: ( end_of_list )?
			int alt51=2;
			int LA51_0 = input.LA(1);

			if ( (LA51_0==NEWLINE) ) {
				alt51=1;
			}
			else if ( (LA51_0==EOF) ) {
				alt51=1;
			}
			switch (alt51) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:287:79: end_of_list
					{
					pushFollow(FOLLOW_end_of_list_in_list_unord1507);
					end_of_list();
					_fsp--;
					if (failed) return unorderedList;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return unorderedList;
	}
	// $ANTLR end list_unord

	// $ANTLR start list_unordelem
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:289:1: list_unordelem returns [UnorderedListItemNode item = null] : um= list_unordelem_markup elem= list_elem ;
	public final UnorderedListItemNode list_unordelem() throws RecognitionException {
		CountLevel_stack.push(new CountLevel_scope());

		UnorderedListItemNode item =  null;

		list_unordelem_markup_return um = null;

		CollectionNode elem = null;


				((CountLevel_scope)CountLevel_stack.peek()).level = 0;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:294:2: (um= list_unordelem_markup elem= list_elem )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:294:4: um= list_unordelem_markup elem= list_elem
			{
			pushFollow(FOLLOW_list_unordelem_markup_in_list_unordelem1540);
			um=list_unordelem_markup();
			_fsp--;
			if (failed) return item;
			if ( backtracking==0 ) {
			  ++((CountLevel_scope)CountLevel_stack.peek()).level; ((CountLevel_scope)CountLevel_stack.peek()).currentMarkup = input.toString(um.start,um.stop);((CountLevel_scope)CountLevel_stack.peek()).groups += input.toString(um.start,um.stop);
			}
			pushFollow(FOLLOW_list_elem_in_list_unordelem1547);
			elem=list_elem();
			_fsp--;
			if (failed) return item;
			if ( backtracking==0 ) {
			   item = new UnorderedListItemNode(((CountLevel_scope)CountLevel_stack.peek()).level, elem);
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			CountLevel_stack.pop();

		}
		return item;
	}
	// $ANTLR end list_unordelem

	// $ANTLR start list_elem
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:296:1: list_elem returns [CollectionNode items = null] : (m= list_elem_markup )* c= list_elemcontent list_elemseparator ;
	public final CollectionNode list_elem() throws RecognitionException {
		CollectionNode items =  null;

		list_elem_markup_return m = null;

		CollectionNode c = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:297:2: ( (m= list_elem_markup )* c= list_elemcontent list_elemseparator )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:297:4: (m= list_elem_markup )* c= list_elemcontent list_elemseparator
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:297:4: (m= list_elem_markup )*
			loop52:
			do {
				int alt52=2;
				int LA52_0 = input.LA(1);

				if ( (LA52_0==STAR) ) {
					alt52=1;
				}
				else if ( (LA52_0==POUND) ) {
					alt52=1;
				}

				switch (alt52) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:297:6: m= list_elem_markup
					{
					pushFollow(FOLLOW_list_elem_markup_in_list_elem1572);
					m=list_elem_markup();
					_fsp--;
					if (failed) return items;
					if ( backtracking==0 ) {

					  						 ++((CountLevel_scope)CountLevel_stack.peek()).level;
					  						 if (!input.toString(m.start,m.stop).equals(((CountLevel_scope)CountLevel_stack.peek()).currentMarkup)) {
					  				((CountLevel_scope)CountLevel_stack.peek()).groups+= GROUPING_SEPARATOR;
					  						 }
					  						 ((CountLevel_scope)CountLevel_stack.peek()).groups+= input.toString(m.start,m.stop);
					  						 ((CountLevel_scope)CountLevel_stack.peek()).currentMarkup = input.toString(m.start,m.stop);

					}

					}
					break;

				default :
					break loop52;
				}
			} while (true);

			pushFollow(FOLLOW_list_elemcontent_in_list_elem1583);
			c=list_elemcontent();
			_fsp--;
			if (failed) return items;
			if ( backtracking==0 ) {
			  items = c;
			}
			pushFollow(FOLLOW_list_elemseparator_in_list_elem1588);
			list_elemseparator();
			_fsp--;
			if (failed) return items;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return items;
	}
	// $ANTLR end list_elem

	public static class list_elem_markup_return extends ParserRuleReturnScope {
	};

	// $ANTLR start list_elem_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:306:1: list_elem_markup : ( list_ordelem_markup | list_unordelem_markup );
	public final list_elem_markup_return list_elem_markup() throws RecognitionException {
		list_elem_markup_return retval = new list_elem_markup_return();
		retval.start = input.LT(1);

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:307:2: ( list_ordelem_markup | list_unordelem_markup )
			int alt53=2;
			int LA53_0 = input.LA(1);

			if ( (LA53_0==POUND) ) {
				alt53=1;
			}
			else if ( (LA53_0==STAR) ) {
				alt53=2;
			}
			else {
				if (backtracking>0) {failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("306:1: list_elem_markup : ( list_ordelem_markup | list_unordelem_markup );", 53, 0, input);

				throw nvae;
			}
			switch (alt53) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:307:4: list_ordelem_markup
					{
					pushFollow(FOLLOW_list_ordelem_markup_in_list_elem_markup1598);
					list_ordelem_markup();
					_fsp--;
					if (failed) return retval;

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:308:4: list_unordelem_markup
					{
					pushFollow(FOLLOW_list_unordelem_markup_in_list_elem_markup1603);
					list_unordelem_markup();
					_fsp--;
					if (failed) return retval;

					}
					break;

			}
			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return retval;
	}
	// $ANTLR end list_elem_markup

	// $ANTLR start list_elemcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:310:1: list_elemcontent returns [CollectionNode items = new CollectionNode()] : onestar (part= list_elemcontentpart onestar )* ;
	public final CollectionNode list_elemcontent() throws RecognitionException {
		CollectionNode items =  new CollectionNode();

		ASTNode part = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:311:2: ( onestar (part= list_elemcontentpart onestar )* )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:311:4: onestar (part= list_elemcontentpart onestar )*
			{
			pushFollow(FOLLOW_onestar_in_list_elemcontent1617);
			onestar();
			_fsp--;
			if (failed) return items;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:311:13: (part= list_elemcontentpart onestar )*
			loop54:
			do {
				int alt54=2;
				int LA54_0 = input.LA(1);

				if ( ((LA54_0>=FORCED_END_OF_LINE && LA54_0<=WIKI)||(LA54_0>=POUND && LA54_0<=75)) ) {
					alt54=1;
				}

				switch (alt54) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:311:15: part= list_elemcontentpart onestar
					{
					pushFollow(FOLLOW_list_elemcontentpart_in_list_elemcontent1626);
					part=list_elemcontentpart();
					_fsp--;
					if (failed) return items;
					if ( backtracking==0 ) {
					   items.add(part);
					}
					pushFollow(FOLLOW_onestar_in_list_elemcontent1631);
					onestar();
					_fsp--;
					if (failed) return items;

					}
					break;

				default :
					break loop54;
				}
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return items;
	}
	// $ANTLR end list_elemcontent

	// $ANTLR start list_elemcontentpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:313:1: list_elemcontentpart returns [ASTNode node = null] : (tuf= text_unformattedelement | tf= list_formatted_elem );
	public final ASTNode list_elemcontentpart() throws RecognitionException {
		ASTNode node =  null;

		ASTNode tuf = null;

		CollectionNode tf = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:314:2: (tuf= text_unformattedelement | tf= list_formatted_elem )
			int alt55=2;
			int LA55_0 = input.LA(1);

			if ( ((LA55_0>=FORCED_END_OF_LINE && LA55_0<=WIKI)||LA55_0==POUND||(LA55_0>=EQUAL && LA55_0<=PIPE)||(LA55_0>=LINK_OPEN && LA55_0<=75)) ) {
				alt55=1;
			}
			else if ( (LA55_0==STAR||LA55_0==ITAL) ) {
				alt55=2;
			}
			else {
				if (backtracking>0) {failed=true; return node;}
				NoViableAltException nvae =
					new NoViableAltException("313:1: list_elemcontentpart returns [ASTNode node = null] : (tuf= text_unformattedelement | tf= list_formatted_elem );", 55, 0, input);

				throw nvae;
			}
			switch (alt55) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:314:4: tuf= text_unformattedelement
					{
					pushFollow(FOLLOW_text_unformattedelement_in_list_elemcontentpart1652);
					tuf=text_unformattedelement();
					_fsp--;
					if (failed) return node;
					if ( backtracking==0 ) {
					   node = new UnformattedTextNode(tuf);
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:315:4: tf= list_formatted_elem
					{
					pushFollow(FOLLOW_list_formatted_elem_in_list_elemcontentpart1663);
					tf=list_formatted_elem();
					_fsp--;
					if (failed) return node;
					if ( backtracking==0 ) {
					   node = new FormattedTextNode(tf);
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return node;
	}
	// $ANTLR end list_elemcontentpart

	// $ANTLR start list_formatted_elem
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:317:1: list_formatted_elem returns [CollectionNode contents = new CollectionNode()] : ( bold_markup onestar (boldContents= list_boldcontentpart onestar )* ( bold_markup )? | ital_markup onestar (italContents= list_italcontentpart onestar )* ( ital_markup )? );
	public final CollectionNode list_formatted_elem() throws RecognitionException {
		CollectionNode contents =  new CollectionNode();

		ASTNode boldContents = null;

		ASTNode italContents = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:318:2: ( bold_markup onestar (boldContents= list_boldcontentpart onestar )* ( bold_markup )? | ital_markup onestar (italContents= list_italcontentpart onestar )* ( ital_markup )? )
			int alt60=2;
			int LA60_0 = input.LA(1);

			if ( (LA60_0==STAR) ) {
				alt60=1;
			}
			else if ( (LA60_0==ITAL) ) {
				alt60=2;
			}
			else {
				if (backtracking>0) {failed=true; return contents;}
				NoViableAltException nvae =
					new NoViableAltException("317:1: list_formatted_elem returns [CollectionNode contents = new CollectionNode()] : ( bold_markup onestar (boldContents= list_boldcontentpart onestar )* ( bold_markup )? | ital_markup onestar (italContents= list_italcontentpart onestar )* ( ital_markup )? );", 60, 0, input);

				throw nvae;
			}
			switch (alt60) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:318:4: bold_markup onestar (boldContents= list_boldcontentpart onestar )* ( bold_markup )?
					{
					pushFollow(FOLLOW_bold_markup_in_list_formatted_elem1680);
					bold_markup();
					_fsp--;
					if (failed) return contents;
					pushFollow(FOLLOW_onestar_in_list_formatted_elem1683);
					onestar();
					_fsp--;
					if (failed) return contents;
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:318:26: (boldContents= list_boldcontentpart onestar )*
					loop56:
					do {
						int alt56=2;
						switch ( input.LA(1) ) {
						case FORCED_END_OF_LINE:
						case HEADING_SECTION:
						case HORIZONTAL_SECTION:
						case LIST_ITEM:
						case LIST_ITEM_PART:
						case NOWIKI_SECTION:
						case SCAPE_NODE:
						case TEXT_NODE:
						case UNORDERED_LIST:
						case UNFORMATTED_TEXT:
						case WIKI:
						case POUND:
						case EQUAL:
						case PIPE:
						case NOWIKI_BLOCK_CLOSE:
						case NOWIKI_CLOSE:
						case LINK_CLOSE:
						case IMAGE_CLOSE:
						case BLANKS:
						case DASH:
						case CR:
						case LF:
						case SPACE:
						case TABULATOR:
						case COLON_SLASH:
						case SLASH:
						case INSIGNIFICANT_CHAR:
						case 40:
						case 41:
						case 42:
						case 43:
						case 44:
						case 45:
						case 46:
						case 47:
						case 48:
						case 49:
						case 50:
						case 51:
						case 52:
						case 53:
						case 54:
						case 55:
						case 56:
						case 57:
						case 58:
						case 59:
						case 60:
						case 61:
						case 62:
						case 63:
						case 64:
						case 65:
						case 66:
						case 67:
						case 68:
						case 69:
						case 70:
						case 71:
						case 72:
						case 73:
						case 74:
						case 75:
							{
							alt56=1;
							}
							break;
						case FORCED_LINEBREAK:
							{
							alt56=1;
							}
							break;
						case ESCAPE:
							{
							alt56=1;
							}
							break;
						case LINK_OPEN:
							{
							alt56=1;
							}
							break;
						case IMAGE_OPEN:
							{
							alt56=1;
							}
							break;
						case EXTENSION:
							{
							alt56=1;
							}
							break;
						case NOWIKI_OPEN:
							{
							alt56=1;
							}
							break;
						case ITAL:
							{
							alt56=1;
							}
							break;

						}

						switch (alt56) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:318:28: boldContents= list_boldcontentpart onestar
							{
							pushFollow(FOLLOW_list_boldcontentpart_in_list_formatted_elem1692);
							boldContents=list_boldcontentpart();
							_fsp--;
							if (failed) return contents;
							if ( backtracking==0 ) {
							   contents.add(new BoldTextNode(boldContents));
							}
							pushFollow(FOLLOW_onestar_in_list_formatted_elem1697);
							onestar();
							_fsp--;
							if (failed) return contents;

							}
							break;

						default :
							break loop56;
						}
					} while (true);

					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:319:3: ( bold_markup )?
					int alt57=2;
					int LA57_0 = input.LA(1);

					if ( (LA57_0==STAR) ) {
						int LA57_1 = input.LA(2);

						if ( (LA57_1==STAR) ) {
							alt57=1;
						}
					}
					switch (alt57) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:319:5: bold_markup
							{
							pushFollow(FOLLOW_bold_markup_in_list_formatted_elem1706);
							bold_markup();
							_fsp--;
							if (failed) return contents;

							}
							break;

					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:320:4: ital_markup onestar (italContents= list_italcontentpart onestar )* ( ital_markup )?
					{
					pushFollow(FOLLOW_ital_markup_in_list_formatted_elem1714);
					ital_markup();
					_fsp--;
					if (failed) return contents;
					pushFollow(FOLLOW_onestar_in_list_formatted_elem1717);
					onestar();
					_fsp--;
					if (failed) return contents;
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:320:26: (italContents= list_italcontentpart onestar )*
					loop58:
					do {
						int alt58=2;
						switch ( input.LA(1) ) {
						case STAR:
							{
							alt58=1;
							}
							break;
						case FORCED_END_OF_LINE:
						case HEADING_SECTION:
						case HORIZONTAL_SECTION:
						case LIST_ITEM:
						case LIST_ITEM_PART:
						case NOWIKI_SECTION:
						case SCAPE_NODE:
						case TEXT_NODE:
						case UNORDERED_LIST:
						case UNFORMATTED_TEXT:
						case WIKI:
						case POUND:
						case EQUAL:
						case PIPE:
						case NOWIKI_BLOCK_CLOSE:
						case NOWIKI_CLOSE:
						case LINK_CLOSE:
						case IMAGE_CLOSE:
						case BLANKS:
						case DASH:
						case CR:
						case LF:
						case SPACE:
						case TABULATOR:
						case COLON_SLASH:
						case SLASH:
						case INSIGNIFICANT_CHAR:
						case 40:
						case 41:
						case 42:
						case 43:
						case 44:
						case 45:
						case 46:
						case 47:
						case 48:
						case 49:
						case 50:
						case 51:
						case 52:
						case 53:
						case 54:
						case 55:
						case 56:
						case 57:
						case 58:
						case 59:
						case 60:
						case 61:
						case 62:
						case 63:
						case 64:
						case 65:
						case 66:
						case 67:
						case 68:
						case 69:
						case 70:
						case 71:
						case 72:
						case 73:
						case 74:
						case 75:
							{
							alt58=1;
							}
							break;
						case FORCED_LINEBREAK:
							{
							alt58=1;
							}
							break;
						case ESCAPE:
							{
							alt58=1;
							}
							break;
						case LINK_OPEN:
							{
							alt58=1;
							}
							break;
						case IMAGE_OPEN:
							{
							alt58=1;
							}
							break;
						case EXTENSION:
							{
							alt58=1;
							}
							break;
						case NOWIKI_OPEN:
							{
							alt58=1;
							}
							break;

						}

						switch (alt58) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:320:28: italContents= list_italcontentpart onestar
							{
							pushFollow(FOLLOW_list_italcontentpart_in_list_formatted_elem1726);
							italContents=list_italcontentpart();
							_fsp--;
							if (failed) return contents;
							if ( backtracking==0 ) {
							   contents.add(new ItalicTextNode(italContents));
							}
							pushFollow(FOLLOW_onestar_in_list_formatted_elem1731);
							onestar();
							_fsp--;
							if (failed) return contents;

							}
							break;

						default :
							break loop58;
						}
					} while (true);

					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:321:3: ( ital_markup )?
					int alt59=2;
					int LA59_0 = input.LA(1);

					if ( (LA59_0==ITAL) ) {
						alt59=1;
					}
					switch (alt59) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:321:5: ital_markup
							{
							pushFollow(FOLLOW_ital_markup_in_list_formatted_elem1740);
							ital_markup();
							_fsp--;
							if (failed) return contents;

							}
							break;

					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return contents;
	}
	// $ANTLR end list_formatted_elem

	protected static class list_boldcontentpart_scope {
		List<ASTNode> elements;
	}
	protected Stack list_boldcontentpart_stack = new Stack();

	// $ANTLR start list_boldcontentpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:324:1: list_boldcontentpart returns [ASTNode contents = null] : ( ital_markup c= list_bolditalcontent ( ital_markup )? | (t= text_unformattedelement )+ );
	public final ASTNode list_boldcontentpart() throws RecognitionException {
		list_boldcontentpart_stack.push(new list_boldcontentpart_scope());
		ASTNode contents =  null;

		ASTNode c = null;

		ASTNode t = null;


			((list_boldcontentpart_scope)list_boldcontentpart_stack.peek()).elements = new ArrayList<ASTNode>();

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:331:2: ( ital_markup c= list_bolditalcontent ( ital_markup )? | (t= text_unformattedelement )+ )
			int alt63=2;
			int LA63_0 = input.LA(1);

			if ( (LA63_0==ITAL) ) {
				alt63=1;
			}
			else if ( ((LA63_0>=FORCED_END_OF_LINE && LA63_0<=WIKI)||LA63_0==POUND||(LA63_0>=EQUAL && LA63_0<=PIPE)||(LA63_0>=LINK_OPEN && LA63_0<=75)) ) {
				alt63=2;
			}
			else {
				if (backtracking>0) {failed=true; return contents;}
				NoViableAltException nvae =
					new NoViableAltException("324:1: list_boldcontentpart returns [ASTNode contents = null] : ( ital_markup c= list_bolditalcontent ( ital_markup )? | (t= text_unformattedelement )+ );", 63, 0, input);

				throw nvae;
			}
			switch (alt63) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:331:4: ital_markup c= list_bolditalcontent ( ital_markup )?
					{
					pushFollow(FOLLOW_ital_markup_in_list_boldcontentpart1766);
					ital_markup();
					_fsp--;
					if (failed) return contents;
					pushFollow(FOLLOW_list_bolditalcontent_in_list_boldcontentpart1773);
					c=list_bolditalcontent();
					_fsp--;
					if (failed) return contents;
					if ( backtracking==0 ) {
					  contents = new ItalicTextNode(c);
					}
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:331:86: ( ital_markup )?
					int alt61=2;
					int LA61_0 = input.LA(1);

					if ( (LA61_0==ITAL) ) {
						alt61=1;
					}
					switch (alt61) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:331:88: ital_markup
							{
							pushFollow(FOLLOW_ital_markup_in_list_boldcontentpart1780);
							ital_markup();
							_fsp--;
							if (failed) return contents;

							}
							break;

					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:332:4: (t= text_unformattedelement )+
					{
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:332:4: (t= text_unformattedelement )+
					int cnt62=0;
					loop62:
					do {
						int alt62=2;
						switch ( input.LA(1) ) {
						case FORCED_END_OF_LINE:
						case HEADING_SECTION:
						case HORIZONTAL_SECTION:
						case LIST_ITEM:
						case LIST_ITEM_PART:
						case NOWIKI_SECTION:
						case SCAPE_NODE:
						case TEXT_NODE:
						case UNORDERED_LIST:
						case UNFORMATTED_TEXT:
						case WIKI:
						case POUND:
						case EQUAL:
						case PIPE:
						case NOWIKI_BLOCK_CLOSE:
						case NOWIKI_CLOSE:
						case LINK_CLOSE:
						case IMAGE_CLOSE:
						case BLANKS:
						case DASH:
						case CR:
						case LF:
						case SPACE:
						case TABULATOR:
						case COLON_SLASH:
						case SLASH:
						case INSIGNIFICANT_CHAR:
						case 40:
						case 41:
						case 42:
						case 43:
						case 44:
						case 45:
						case 46:
						case 47:
						case 48:
						case 49:
						case 50:
						case 51:
						case 52:
						case 53:
						case 54:
						case 55:
						case 56:
						case 57:
						case 58:
						case 59:
						case 60:
						case 61:
						case 62:
						case 63:
						case 64:
						case 65:
						case 66:
						case 67:
						case 68:
						case 69:
						case 70:
						case 71:
						case 72:
						case 73:
						case 74:
						case 75:
							{
							alt62=1;
							}
							break;
						case FORCED_LINEBREAK:
							{
							alt62=1;
							}
							break;
						case ESCAPE:
							{
							alt62=1;
							}
							break;
						case LINK_OPEN:
							{
							alt62=1;
							}
							break;
						case IMAGE_OPEN:
							{
							alt62=1;
							}
							break;
						case EXTENSION:
							{
							alt62=1;
							}
							break;
						case NOWIKI_OPEN:
							{
							alt62=1;
							}
							break;

						}

						switch (alt62) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:332:6: t= text_unformattedelement
							{
							pushFollow(FOLLOW_text_unformattedelement_in_list_boldcontentpart1795);
							t=text_unformattedelement();
							_fsp--;
							if (failed) return contents;
							if ( backtracking==0 ) {
							   ((list_boldcontentpart_scope)list_boldcontentpart_stack.peek()).elements.add(t);
							}

							}
							break;

						default :
							if ( cnt62 >= 1 ) break loop62;
							if (backtracking>0) {failed=true; return contents;}
								EarlyExitException eee =
									new EarlyExitException(62, input);
								throw eee;
						}
						cnt62++;
					} while (true);

					if ( backtracking==0 ) {
					  contents = new CollectionNode(((list_boldcontentpart_scope)list_boldcontentpart_stack.peek()).elements);
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			list_boldcontentpart_stack.pop();
		}
		return contents;
	}
	// $ANTLR end list_boldcontentpart

	// $ANTLR start list_bolditalcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:336:1: list_bolditalcontent returns [ASTNode text = null] : (t= text_unformattedelement )+ ;
	public final ASTNode list_bolditalcontent() throws RecognitionException {
		ASTNode text =  null;

		ASTNode t = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:337:2: ( (t= text_unformattedelement )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:337:4: (t= text_unformattedelement )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:337:4: (t= text_unformattedelement )+
			int cnt64=0;
			loop64:
			do {
				int alt64=2;
				switch ( input.LA(1) ) {
				case FORCED_END_OF_LINE:
				case HEADING_SECTION:
				case HORIZONTAL_SECTION:
				case LIST_ITEM:
				case LIST_ITEM_PART:
				case NOWIKI_SECTION:
				case SCAPE_NODE:
				case TEXT_NODE:
				case UNORDERED_LIST:
				case UNFORMATTED_TEXT:
				case WIKI:
				case POUND:
				case EQUAL:
				case PIPE:
				case NOWIKI_BLOCK_CLOSE:
				case NOWIKI_CLOSE:
				case LINK_CLOSE:
				case IMAGE_CLOSE:
				case BLANKS:
				case DASH:
				case CR:
				case LF:
				case SPACE:
				case TABULATOR:
				case COLON_SLASH:
				case SLASH:
				case INSIGNIFICANT_CHAR:
				case 40:
				case 41:
				case 42:
				case 43:
				case 44:
				case 45:
				case 46:
				case 47:
				case 48:
				case 49:
				case 50:
				case 51:
				case 52:
				case 53:
				case 54:
				case 55:
				case 56:
				case 57:
				case 58:
				case 59:
				case 60:
				case 61:
				case 62:
				case 63:
				case 64:
				case 65:
				case 66:
				case 67:
				case 68:
				case 69:
				case 70:
				case 71:
				case 72:
				case 73:
				case 74:
				case 75:
					{
					alt64=1;
					}
					break;
				case FORCED_LINEBREAK:
					{
					alt64=1;
					}
					break;
				case ESCAPE:
					{
					alt64=1;
					}
					break;
				case LINK_OPEN:
					{
					alt64=1;
					}
					break;
				case IMAGE_OPEN:
					{
					alt64=1;
					}
					break;
				case EXTENSION:
					{
					alt64=1;
					}
					break;
				case NOWIKI_OPEN:
					{
					alt64=1;
					}
					break;

				}

				switch (alt64) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:337:6: t= text_unformattedelement
					{
					pushFollow(FOLLOW_text_unformattedelement_in_list_bolditalcontent1827);
					t=text_unformattedelement();
					_fsp--;
					if (failed) return text;
					if ( backtracking==0 ) {
					   text = t;
					}

					}
					break;

				default :
					if ( cnt64 >= 1 ) break loop64;
					if (backtracking>0) {failed=true; return text;}
						EarlyExitException eee =
							new EarlyExitException(64, input);
						throw eee;
				}
				cnt64++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return text;
	}
	// $ANTLR end list_bolditalcontent

	protected static class list_italcontentpart_scope {
		List<ASTNode> elements;
	}
	protected Stack list_italcontentpart_stack = new Stack();

	// $ANTLR start list_italcontentpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:340:1: list_italcontentpart returns [ASTNode contents = null] : ( bold_markup c= list_bolditalcontent ( bold_markup )? | (t= text_unformattedelement )+ );
	public final ASTNode list_italcontentpart() throws RecognitionException {
		list_italcontentpart_stack.push(new list_italcontentpart_scope());
		ASTNode contents =  null;

		ASTNode c = null;

		ASTNode t = null;


			((list_italcontentpart_scope)list_italcontentpart_stack.peek()).elements = new ArrayList<ASTNode>();

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:347:2: ( bold_markup c= list_bolditalcontent ( bold_markup )? | (t= text_unformattedelement )+ )
			int alt67=2;
			int LA67_0 = input.LA(1);

			if ( (LA67_0==STAR) ) {
				alt67=1;
			}
			else if ( ((LA67_0>=FORCED_END_OF_LINE && LA67_0<=WIKI)||LA67_0==POUND||(LA67_0>=EQUAL && LA67_0<=PIPE)||(LA67_0>=LINK_OPEN && LA67_0<=75)) ) {
				alt67=2;
			}
			else {
				if (backtracking>0) {failed=true; return contents;}
				NoViableAltException nvae =
					new NoViableAltException("340:1: list_italcontentpart returns [ASTNode contents = null] : ( bold_markup c= list_bolditalcontent ( bold_markup )? | (t= text_unformattedelement )+ );", 67, 0, input);

				throw nvae;
			}
			switch (alt67) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:347:4: bold_markup c= list_bolditalcontent ( bold_markup )?
					{
					pushFollow(FOLLOW_bold_markup_in_list_italcontentpart1857);
					bold_markup();
					_fsp--;
					if (failed) return contents;
					pushFollow(FOLLOW_list_bolditalcontent_in_list_italcontentpart1864);
					c=list_bolditalcontent();
					_fsp--;
					if (failed) return contents;
					if ( backtracking==0 ) {
					   contents = new BoldTextNode(c);
					}
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:347:86: ( bold_markup )?
					int alt65=2;
					int LA65_0 = input.LA(1);

					if ( (LA65_0==STAR) ) {
						int LA65_1 = input.LA(2);

						if ( (LA65_1==STAR) ) {
							alt65=1;
						}
					}
					switch (alt65) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:347:88: bold_markup
							{
							pushFollow(FOLLOW_bold_markup_in_list_italcontentpart1871);
							bold_markup();
							_fsp--;
							if (failed) return contents;

							}
							break;

					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:348:4: (t= text_unformattedelement )+
					{
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:348:4: (t= text_unformattedelement )+
					int cnt66=0;
					loop66:
					do {
						int alt66=2;
						switch ( input.LA(1) ) {
						case FORCED_END_OF_LINE:
						case HEADING_SECTION:
						case HORIZONTAL_SECTION:
						case LIST_ITEM:
						case LIST_ITEM_PART:
						case NOWIKI_SECTION:
						case SCAPE_NODE:
						case TEXT_NODE:
						case UNORDERED_LIST:
						case UNFORMATTED_TEXT:
						case WIKI:
						case POUND:
						case EQUAL:
						case PIPE:
						case NOWIKI_BLOCK_CLOSE:
						case NOWIKI_CLOSE:
						case LINK_CLOSE:
						case IMAGE_CLOSE:
						case BLANKS:
						case DASH:
						case CR:
						case LF:
						case SPACE:
						case TABULATOR:
						case COLON_SLASH:
						case SLASH:
						case INSIGNIFICANT_CHAR:
						case 40:
						case 41:
						case 42:
						case 43:
						case 44:
						case 45:
						case 46:
						case 47:
						case 48:
						case 49:
						case 50:
						case 51:
						case 52:
						case 53:
						case 54:
						case 55:
						case 56:
						case 57:
						case 58:
						case 59:
						case 60:
						case 61:
						case 62:
						case 63:
						case 64:
						case 65:
						case 66:
						case 67:
						case 68:
						case 69:
						case 70:
						case 71:
						case 72:
						case 73:
						case 74:
						case 75:
							{
							alt66=1;
							}
							break;
						case FORCED_LINEBREAK:
							{
							alt66=1;
							}
							break;
						case ESCAPE:
							{
							alt66=1;
							}
							break;
						case LINK_OPEN:
							{
							alt66=1;
							}
							break;
						case IMAGE_OPEN:
							{
							alt66=1;
							}
							break;
						case EXTENSION:
							{
							alt66=1;
							}
							break;
						case NOWIKI_OPEN:
							{
							alt66=1;
							}
							break;

						}

						switch (alt66) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:348:6: t= text_unformattedelement
							{
							pushFollow(FOLLOW_text_unformattedelement_in_list_italcontentpart1885);
							t=text_unformattedelement();
							_fsp--;
							if (failed) return contents;
							if ( backtracking==0 ) {
							   ((list_italcontentpart_scope)list_italcontentpart_stack.peek()).elements.add(t);
							}

							}
							break;

						default :
							if ( cnt66 >= 1 ) break loop66;
							if (backtracking>0) {failed=true; return contents;}
								EarlyExitException eee =
									new EarlyExitException(66, input);
								throw eee;
						}
						cnt66++;
					} while (true);

					if ( backtracking==0 ) {
					   contents = new CollectionNode(((list_italcontentpart_scope)list_italcontentpart_stack.peek()).elements);
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			list_italcontentpart_stack.pop();
		}
		return contents;
	}
	// $ANTLR end list_italcontentpart

	// $ANTLR start table
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:352:1: table returns [TableNode table = new TableNode()] : (tr= table_row )+ ;
	public final TableNode table() throws RecognitionException {
		TableNode table =  new TableNode();

		CollectionNode tr = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:353:2: ( (tr= table_row )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:353:4: (tr= table_row )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:353:4: (tr= table_row )+
			int cnt68=0;
			loop68:
			do {
				int alt68=2;
				int LA68_0 = input.LA(1);

				if ( (LA68_0==PIPE) ) {
					alt68=1;
				}

				switch (alt68) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:353:6: tr= table_row
					{
					pushFollow(FOLLOW_table_row_in_table1915);
					tr=table_row();
					_fsp--;
					if (failed) return table;
					if ( backtracking==0 ) {
					  table.addChildNode(tr);
					}

					}
					break;

				default :
					if ( cnt68 >= 1 ) break loop68;
					if (backtracking>0) {failed=true; return table;}
						EarlyExitException eee =
							new EarlyExitException(68, input);
						throw eee;
				}
				cnt68++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return table;
	}
	// $ANTLR end table

	// $ANTLR start table_row
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:355:1: table_row returns [CollectionNode row = new CollectionNode()] : (tc= table_cell )+ table_rowseparator ;
	public final CollectionNode table_row() throws RecognitionException {
		CollectionNode row =  new CollectionNode();

		TableCellNode tc = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:356:2: ( (tc= table_cell )+ table_rowseparator )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:356:4: (tc= table_cell )+ table_rowseparator
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:356:4: (tc= table_cell )+
			int cnt69=0;
			loop69:
			do {
				int alt69=2;
				int LA69_0 = input.LA(1);

				if ( (LA69_0==PIPE) ) {
					alt69=1;
				}

				switch (alt69) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:356:6: tc= table_cell
					{
					pushFollow(FOLLOW_table_cell_in_table_row1941);
					tc=table_cell();
					_fsp--;
					if (failed) return row;
					if ( backtracking==0 ) {
					   row.add(tc);
					}

					}
					break;

				default :
					if ( cnt69 >= 1 ) break loop69;
					if (backtracking>0) {failed=true; return row;}
						EarlyExitException eee =
							new EarlyExitException(69, input);
						throw eee;
				}
				cnt69++;
			} while (true);

			pushFollow(FOLLOW_table_rowseparator_in_table_row1949);
			table_rowseparator();
			_fsp--;
			if (failed) return row;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return row;
	}
	// $ANTLR end table_row

	// $ANTLR start table_cell
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:358:1: table_cell returns [TableCellNode cell = null] : ({...}?th= table_headercell | tc= table_normalcell );
	public final TableCellNode table_cell() throws RecognitionException {
		TableCellNode cell =  null;

		TableHeaderNode th = null;

		TableDataNode tc = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:359:2: ({...}?th= table_headercell | tc= table_normalcell )
			int alt70=2;
			int LA70_0 = input.LA(1);

			if ( (LA70_0==PIPE) ) {
				int LA70_1 = input.LA(2);

				if ( (LA70_1==EQUAL) ) {
					int LA70_2 = input.LA(3);

					if ( ( input.LA(2) == EQUAL ) ) {
						alt70=1;
					}
					else if ( (true) ) {
						alt70=2;
					}
					else {
						if (backtracking>0) {failed=true; return cell;}
						NoViableAltException nvae =
							new NoViableAltException("358:1: table_cell returns [TableCellNode cell = null] : ({...}?th= table_headercell | tc= table_normalcell );", 70, 2, input);

						throw nvae;
					}
				}
				else if ( (LA70_1==EOF||(LA70_1>=FORCED_END_OF_LINE && LA70_1<=STAR)||(LA70_1>=PIPE && LA70_1<=75)) ) {
					alt70=2;
				}
				else {
					if (backtracking>0) {failed=true; return cell;}
					NoViableAltException nvae =
						new NoViableAltException("358:1: table_cell returns [TableCellNode cell = null] : ({...}?th= table_headercell | tc= table_normalcell );", 70, 1, input);

					throw nvae;
				}
			}
			else {
				if (backtracking>0) {failed=true; return cell;}
				NoViableAltException nvae =
					new NoViableAltException("358:1: table_cell returns [TableCellNode cell = null] : ({...}?th= table_headercell | tc= table_normalcell );", 70, 0, input);

				throw nvae;
			}
			switch (alt70) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:359:4: {...}?th= table_headercell
					{
					if ( !( input.LA(2) == EQUAL ) ) {
						if (backtracking>0) {failed=true; return cell;}
						throw new FailedPredicateException(input, "table_cell", " input.LA(2) == EQUAL ");
					}
					pushFollow(FOLLOW_table_headercell_in_table_cell1970);
					th=table_headercell();
					_fsp--;
					if (failed) return cell;
					if ( backtracking==0 ) {
					  cell = th;
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:360:4: tc= table_normalcell
					{
					pushFollow(FOLLOW_table_normalcell_in_table_cell1981);
					tc=table_normalcell();
					_fsp--;
					if (failed) return cell;
					if ( backtracking==0 ) {
					  cell = tc;
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return cell;
	}
	// $ANTLR end table_cell

	// $ANTLR start table_headercell
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:362:1: table_headercell returns [TableHeaderNode header = null] : table_headercell_markup tc= table_cellcontent ;
	public final TableHeaderNode table_headercell() throws RecognitionException {
		TableHeaderNode header =  null;

		CollectionNode tc = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:363:2: ( table_headercell_markup tc= table_cellcontent )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:363:4: table_headercell_markup tc= table_cellcontent
			{
			pushFollow(FOLLOW_table_headercell_markup_in_table_headercell1997);
			table_headercell_markup();
			_fsp--;
			if (failed) return header;
			pushFollow(FOLLOW_table_cellcontent_in_table_headercell2004);
			tc=table_cellcontent();
			_fsp--;
			if (failed) return header;
			if ( backtracking==0 ) {
			  header = new TableHeaderNode(tc);
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return header;
	}
	// $ANTLR end table_headercell

	// $ANTLR start table_normalcell
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:365:1: table_normalcell returns [TableDataNode cell = null] : table_cell_markup tc= table_cellcontent ;
	public final TableDataNode table_normalcell() throws RecognitionException {
		TableDataNode cell =  null;

		CollectionNode tc = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:366:2: ( table_cell_markup tc= table_cellcontent )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:366:4: table_cell_markup tc= table_cellcontent
			{
			pushFollow(FOLLOW_table_cell_markup_in_table_normalcell2020);
			table_cell_markup();
			_fsp--;
			if (failed) return cell;
			pushFollow(FOLLOW_table_cellcontent_in_table_normalcell2027);
			tc=table_cellcontent();
			_fsp--;
			if (failed) return cell;
			if ( backtracking==0 ) {
			   cell = new TableDataNode(tc);
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return cell;
	}
	// $ANTLR end table_normalcell

	// $ANTLR start table_cellcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:368:1: table_cellcontent returns [CollectionNode items = new CollectionNode()] : onestar (tcp= table_cellcontentpart onestar )* ;
	public final CollectionNode table_cellcontent() throws RecognitionException {
		CollectionNode items =  new CollectionNode();

		ASTNode tcp = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:369:2: ( onestar (tcp= table_cellcontentpart onestar )* )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:369:4: onestar (tcp= table_cellcontentpart onestar )*
			{
			pushFollow(FOLLOW_onestar_in_table_cellcontent2043);
			onestar();
			_fsp--;
			if (failed) return items;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:369:13: (tcp= table_cellcontentpart onestar )*
			loop71:
			do {
				int alt71=2;
				int LA71_0 = input.LA(1);

				if ( ((LA71_0>=FORCED_END_OF_LINE && LA71_0<=WIKI)||(LA71_0>=POUND && LA71_0<=EQUAL)||(LA71_0>=ITAL && LA71_0<=75)) ) {
					alt71=1;
				}

				switch (alt71) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:369:15: tcp= table_cellcontentpart onestar
					{
					pushFollow(FOLLOW_table_cellcontentpart_in_table_cellcontent2052);
					tcp=table_cellcontentpart();
					_fsp--;
					if (failed) return items;
					if ( backtracking==0 ) {
					  items.add(tcp);
					}
					pushFollow(FOLLOW_onestar_in_table_cellcontent2057);
					onestar();
					_fsp--;
					if (failed) return items;

					}
					break;

				default :
					break loop71;
				}
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return items;
	}
	// $ANTLR end table_cellcontent

	// $ANTLR start table_cellcontentpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:371:1: table_cellcontentpart returns [ASTNode node = null] : (tf= table_formattedelement | tu= table_unformattedelement );
	public final ASTNode table_cellcontentpart() throws RecognitionException {
		ASTNode node =  null;

		ASTNode tf = null;

		ASTNode tu = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:372:2: (tf= table_formattedelement | tu= table_unformattedelement )
			int alt72=2;
			int LA72_0 = input.LA(1);

			if ( (LA72_0==STAR||LA72_0==ITAL) ) {
				alt72=1;
			}
			else if ( ((LA72_0>=FORCED_END_OF_LINE && LA72_0<=WIKI)||LA72_0==POUND||LA72_0==EQUAL||(LA72_0>=LINK_OPEN && LA72_0<=75)) ) {
				alt72=2;
			}
			else {
				if (backtracking>0) {failed=true; return node;}
				NoViableAltException nvae =
					new NoViableAltException("371:1: table_cellcontentpart returns [ASTNode node = null] : (tf= table_formattedelement | tu= table_unformattedelement );", 72, 0, input);

				throw nvae;
			}
			switch (alt72) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:372:4: tf= table_formattedelement
					{
					pushFollow(FOLLOW_table_formattedelement_in_table_cellcontentpart2078);
					tf=table_formattedelement();
					_fsp--;
					if (failed) return node;
					if ( backtracking==0 ) {
					  node =tf;
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:373:4: tu= table_unformattedelement
					{
					pushFollow(FOLLOW_table_unformattedelement_in_table_cellcontentpart2089);
					tu=table_unformattedelement();
					_fsp--;
					if (failed) return node;
					if ( backtracking==0 ) {
					  node =tu;
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return node;
	}
	// $ANTLR end table_cellcontentpart

	// $ANTLR start table_formattedelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:375:1: table_formattedelement returns [ASTNode content = null] : ( ital_markup (tic= table_italcontent )? ( ital_markup )? | bold_markup (tbc= table_boldcontent )? ( bold_markup )? );
	public final ASTNode table_formattedelement() throws RecognitionException {
		ASTNode content =  null;

		CollectionNode tic = null;

		CollectionNode tbc = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:376:2: ( ital_markup (tic= table_italcontent )? ( ital_markup )? | bold_markup (tbc= table_boldcontent )? ( bold_markup )? )
			int alt77=2;
			int LA77_0 = input.LA(1);

			if ( (LA77_0==ITAL) ) {
				alt77=1;
			}
			else if ( (LA77_0==STAR) ) {
				alt77=2;
			}
			else {
				if (backtracking>0) {failed=true; return content;}
				NoViableAltException nvae =
					new NoViableAltException("375:1: table_formattedelement returns [ASTNode content = null] : ( ital_markup (tic= table_italcontent )? ( ital_markup )? | bold_markup (tbc= table_boldcontent )? ( bold_markup )? );", 77, 0, input);

				throw nvae;
			}
			switch (alt77) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:376:4: ital_markup (tic= table_italcontent )? ( ital_markup )?
					{
					pushFollow(FOLLOW_ital_markup_in_table_formattedelement2105);
					ital_markup();
					_fsp--;
					if (failed) return content;
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:376:18: (tic= table_italcontent )?
					int alt73=2;
					switch ( input.LA(1) ) {
						case STAR:
							{
							alt73=1;
							}
							break;
						case FORCED_END_OF_LINE:
						case HEADING_SECTION:
						case HORIZONTAL_SECTION:
						case LIST_ITEM:
						case LIST_ITEM_PART:
						case NOWIKI_SECTION:
						case SCAPE_NODE:
						case TEXT_NODE:
						case UNORDERED_LIST:
						case UNFORMATTED_TEXT:
						case WIKI:
						case POUND:
						case EQUAL:
						case NOWIKI_BLOCK_CLOSE:
						case NOWIKI_CLOSE:
						case LINK_CLOSE:
						case IMAGE_CLOSE:
						case BLANKS:
						case DASH:
						case CR:
						case LF:
						case SPACE:
						case TABULATOR:
						case COLON_SLASH:
						case SLASH:
						case INSIGNIFICANT_CHAR:
						case 40:
						case 41:
						case 42:
						case 43:
						case 44:
						case 45:
						case 46:
						case 47:
						case 48:
						case 49:
						case 50:
						case 51:
						case 52:
						case 53:
						case 54:
						case 55:
						case 56:
						case 57:
						case 58:
						case 59:
						case 60:
						case 61:
						case 62:
						case 63:
						case 64:
						case 65:
						case 66:
						case 67:
						case 68:
						case 69:
						case 70:
						case 71:
						case 72:
						case 73:
						case 74:
						case 75:
							{
							alt73=1;
							}
							break;
						case FORCED_LINEBREAK:
							{
							alt73=1;
							}
							break;
						case ESCAPE:
							{
							alt73=1;
							}
							break;
						case LINK_OPEN:
							{
							alt73=1;
							}
							break;
						case IMAGE_OPEN:
							{
							alt73=1;
							}
							break;
						case EXTENSION:
							{
							alt73=1;
							}
							break;
						case NOWIKI_OPEN:
							{
							alt73=1;
							}
							break;
						case EOF:
							{
							alt73=1;
							}
							break;
					}

					switch (alt73) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:376:20: tic= table_italcontent
							{
							pushFollow(FOLLOW_table_italcontent_in_table_formattedelement2115);
							tic=table_italcontent();
							_fsp--;
							if (failed) return content;
							if ( backtracking==0 ) {
							   content = new ItalicTextNode(tic);
							}

							}
							break;

					}

					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:376:94: ( ital_markup )?
					int alt74=2;
					int LA74_0 = input.LA(1);

					if ( (LA74_0==ITAL) ) {
						alt74=1;
					}
					switch (alt74) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:376:96: ital_markup
							{
							pushFollow(FOLLOW_ital_markup_in_table_formattedelement2124);
							ital_markup();
							_fsp--;
							if (failed) return content;

							}
							break;

					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:377:4: bold_markup (tbc= table_boldcontent )? ( bold_markup )?
					{
					pushFollow(FOLLOW_bold_markup_in_table_formattedelement2132);
					bold_markup();
					_fsp--;
					if (failed) return content;
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:377:16: (tbc= table_boldcontent )?
					int alt75=2;
					switch ( input.LA(1) ) {
						case STAR:
							{
							int LA75_1 = input.LA(2);

							if ( ( input.LA(2) != STAR ) ) {
								alt75=1;
							}
							}
							break;
						case FORCED_END_OF_LINE:
						case HEADING_SECTION:
						case HORIZONTAL_SECTION:
						case LIST_ITEM:
						case LIST_ITEM_PART:
						case NOWIKI_SECTION:
						case SCAPE_NODE:
						case TEXT_NODE:
						case UNORDERED_LIST:
						case UNFORMATTED_TEXT:
						case WIKI:
						case POUND:
						case EQUAL:
						case NOWIKI_BLOCK_CLOSE:
						case NOWIKI_CLOSE:
						case LINK_CLOSE:
						case IMAGE_CLOSE:
						case BLANKS:
						case DASH:
						case CR:
						case LF:
						case SPACE:
						case TABULATOR:
						case COLON_SLASH:
						case SLASH:
						case INSIGNIFICANT_CHAR:
						case 40:
						case 41:
						case 42:
						case 43:
						case 44:
						case 45:
						case 46:
						case 47:
						case 48:
						case 49:
						case 50:
						case 51:
						case 52:
						case 53:
						case 54:
						case 55:
						case 56:
						case 57:
						case 58:
						case 59:
						case 60:
						case 61:
						case 62:
						case 63:
						case 64:
						case 65:
						case 66:
						case 67:
						case 68:
						case 69:
						case 70:
						case 71:
						case 72:
						case 73:
						case 74:
						case 75:
							{
							alt75=1;
							}
							break;
						case FORCED_LINEBREAK:
							{
							alt75=1;
							}
							break;
						case ESCAPE:
							{
							alt75=1;
							}
							break;
						case LINK_OPEN:
							{
							alt75=1;
							}
							break;
						case IMAGE_OPEN:
							{
							alt75=1;
							}
							break;
						case EXTENSION:
							{
							alt75=1;
							}
							break;
						case NOWIKI_OPEN:
							{
							alt75=1;
							}
							break;
						case ITAL:
							{
							alt75=1;
							}
							break;
						case EOF:
							{
							alt75=1;
							}
							break;
					}

					switch (alt75) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:377:18: tbc= table_boldcontent
							{
							pushFollow(FOLLOW_table_boldcontent_in_table_formattedelement2139);
							tbc=table_boldcontent();
							_fsp--;
							if (failed) return content;
							if ( backtracking==0 ) {
							  content = new BoldTextNode(tbc);
							}

							}
							break;

					}

					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:377:88: ( bold_markup )?
					int alt76=2;
					int LA76_0 = input.LA(1);

					if ( (LA76_0==STAR) ) {
						int LA76_1 = input.LA(2);

						if ( (LA76_1==STAR) ) {
							alt76=1;
						}
					}
					switch (alt76) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:377:90: bold_markup
							{
							pushFollow(FOLLOW_bold_markup_in_table_formattedelement2149);
							bold_markup();
							_fsp--;
							if (failed) return content;

							}
							break;

					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return content;
	}
	// $ANTLR end table_formattedelement

	// $ANTLR start table_boldcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:379:1: table_boldcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (tb= table_boldcontentpart onestar )+ | EOF );
	public final CollectionNode table_boldcontent() throws RecognitionException {
		CollectionNode items =  new CollectionNode();

		ASTNode tb = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:380:2: ( onestar (tb= table_boldcontentpart onestar )+ | EOF )
			int alt79=2;
			int LA79_0 = input.LA(1);

			if ( ((LA79_0>=FORCED_END_OF_LINE && LA79_0<=WIKI)||(LA79_0>=POUND && LA79_0<=EQUAL)||(LA79_0>=ITAL && LA79_0<=75)) ) {
				alt79=1;
			}
			else if ( (LA79_0==EOF) ) {
				alt79=2;
			}
			else {
				if (backtracking>0) {failed=true; return items;}
				NoViableAltException nvae =
					new NoViableAltException("379:1: table_boldcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (tb= table_boldcontentpart onestar )+ | EOF );", 79, 0, input);

				throw nvae;
			}
			switch (alt79) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:380:4: onestar (tb= table_boldcontentpart onestar )+
					{
					pushFollow(FOLLOW_onestar_in_table_boldcontent2166);
					onestar();
					_fsp--;
					if (failed) return items;
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:380:13: (tb= table_boldcontentpart onestar )+
					int cnt78=0;
					loop78:
					do {
						int alt78=2;
						switch ( input.LA(1) ) {
						case ITAL:
							{
							alt78=1;
							}
							break;
						case FORCED_END_OF_LINE:
						case HEADING_SECTION:
						case HORIZONTAL_SECTION:
						case LIST_ITEM:
						case LIST_ITEM_PART:
						case NOWIKI_SECTION:
						case SCAPE_NODE:
						case TEXT_NODE:
						case UNORDERED_LIST:
						case UNFORMATTED_TEXT:
						case WIKI:
						case POUND:
						case EQUAL:
						case NOWIKI_BLOCK_CLOSE:
						case NOWIKI_CLOSE:
						case LINK_CLOSE:
						case IMAGE_CLOSE:
						case BLANKS:
						case DASH:
						case CR:
						case LF:
						case SPACE:
						case TABULATOR:
						case COLON_SLASH:
						case SLASH:
						case INSIGNIFICANT_CHAR:
						case 40:
						case 41:
						case 42:
						case 43:
						case 44:
						case 45:
						case 46:
						case 47:
						case 48:
						case 49:
						case 50:
						case 51:
						case 52:
						case 53:
						case 54:
						case 55:
						case 56:
						case 57:
						case 58:
						case 59:
						case 60:
						case 61:
						case 62:
						case 63:
						case 64:
						case 65:
						case 66:
						case 67:
						case 68:
						case 69:
						case 70:
						case 71:
						case 72:
						case 73:
						case 74:
						case 75:
							{
							alt78=1;
							}
							break;
						case FORCED_LINEBREAK:
							{
							alt78=1;
							}
							break;
						case ESCAPE:
							{
							alt78=1;
							}
							break;
						case LINK_OPEN:
							{
							alt78=1;
							}
							break;
						case IMAGE_OPEN:
							{
							alt78=1;
							}
							break;
						case EXTENSION:
							{
							alt78=1;
							}
							break;
						case NOWIKI_OPEN:
							{
							alt78=1;
							}
							break;

						}

						switch (alt78) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:380:15: tb= table_boldcontentpart onestar
							{
							pushFollow(FOLLOW_table_boldcontentpart_in_table_boldcontent2175);
							tb=table_boldcontentpart();
							_fsp--;
							if (failed) return items;
							if ( backtracking==0 ) {
							   items.add(tb);
							}
							pushFollow(FOLLOW_onestar_in_table_boldcontent2180);
							onestar();
							_fsp--;
							if (failed) return items;

							}
							break;

						default :
							if ( cnt78 >= 1 ) break loop78;
							if (backtracking>0) {failed=true; return items;}
								EarlyExitException eee =
									new EarlyExitException(78, input);
								throw eee;
						}
						cnt78++;
					} while (true);

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:381:4: EOF
					{
					match(input,EOF,FOLLOW_EOF_in_table_boldcontent2188); if (failed) return items;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return items;
	}
	// $ANTLR end table_boldcontent

	// $ANTLR start table_italcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:383:1: table_italcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (ti= table_italcontentpart onestar )+ | EOF );
	public final CollectionNode table_italcontent() throws RecognitionException {
		CollectionNode items =  new CollectionNode();

		ASTNode ti = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:384:2: ( onestar (ti= table_italcontentpart onestar )+ | EOF )
			int alt81=2;
			int LA81_0 = input.LA(1);

			if ( ((LA81_0>=FORCED_END_OF_LINE && LA81_0<=WIKI)||(LA81_0>=POUND && LA81_0<=EQUAL)||(LA81_0>=LINK_OPEN && LA81_0<=75)) ) {
				alt81=1;
			}
			else if ( (LA81_0==EOF) ) {
				alt81=2;
			}
			else {
				if (backtracking>0) {failed=true; return items;}
				NoViableAltException nvae =
					new NoViableAltException("383:1: table_italcontent returns [CollectionNode items = new CollectionNode()] : ( onestar (ti= table_italcontentpart onestar )+ | EOF );", 81, 0, input);

				throw nvae;
			}
			switch (alt81) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:384:4: onestar (ti= table_italcontentpart onestar )+
					{
					pushFollow(FOLLOW_onestar_in_table_italcontent2202);
					onestar();
					_fsp--;
					if (failed) return items;
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:384:13: (ti= table_italcontentpart onestar )+
					int cnt80=0;
					loop80:
					do {
						int alt80=2;
						switch ( input.LA(1) ) {
						case STAR:
							{
							alt80=1;
							}
							break;
						case FORCED_END_OF_LINE:
						case HEADING_SECTION:
						case HORIZONTAL_SECTION:
						case LIST_ITEM:
						case LIST_ITEM_PART:
						case NOWIKI_SECTION:
						case SCAPE_NODE:
						case TEXT_NODE:
						case UNORDERED_LIST:
						case UNFORMATTED_TEXT:
						case WIKI:
						case POUND:
						case EQUAL:
						case NOWIKI_BLOCK_CLOSE:
						case NOWIKI_CLOSE:
						case LINK_CLOSE:
						case IMAGE_CLOSE:
						case BLANKS:
						case DASH:
						case CR:
						case LF:
						case SPACE:
						case TABULATOR:
						case COLON_SLASH:
						case SLASH:
						case INSIGNIFICANT_CHAR:
						case 40:
						case 41:
						case 42:
						case 43:
						case 44:
						case 45:
						case 46:
						case 47:
						case 48:
						case 49:
						case 50:
						case 51:
						case 52:
						case 53:
						case 54:
						case 55:
						case 56:
						case 57:
						case 58:
						case 59:
						case 60:
						case 61:
						case 62:
						case 63:
						case 64:
						case 65:
						case 66:
						case 67:
						case 68:
						case 69:
						case 70:
						case 71:
						case 72:
						case 73:
						case 74:
						case 75:
							{
							alt80=1;
							}
							break;
						case FORCED_LINEBREAK:
							{
							alt80=1;
							}
							break;
						case ESCAPE:
							{
							alt80=1;
							}
							break;
						case LINK_OPEN:
							{
							alt80=1;
							}
							break;
						case IMAGE_OPEN:
							{
							alt80=1;
							}
							break;
						case EXTENSION:
							{
							alt80=1;
							}
							break;
						case NOWIKI_OPEN:
							{
							alt80=1;
							}
							break;

						}

						switch (alt80) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:384:15: ti= table_italcontentpart onestar
							{
							pushFollow(FOLLOW_table_italcontentpart_in_table_italcontent2211);
							ti=table_italcontentpart();
							_fsp--;
							if (failed) return items;
							if ( backtracking==0 ) {
							   items.add(ti);
							}
							pushFollow(FOLLOW_onestar_in_table_italcontent2216);
							onestar();
							_fsp--;
							if (failed) return items;

							}
							break;

						default :
							if ( cnt80 >= 1 ) break loop80;
							if (backtracking>0) {failed=true; return items;}
								EarlyExitException eee =
									new EarlyExitException(80, input);
								throw eee;
						}
						cnt80++;
					} while (true);

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:385:4: EOF
					{
					match(input,EOF,FOLLOW_EOF_in_table_italcontent2224); if (failed) return items;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return items;
	}
	// $ANTLR end table_italcontent

	// $ANTLR start table_boldcontentpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:387:1: table_boldcontentpart returns [ASTNode node = null] : (tf= table_formattedcontent | ital_markup tb= table_bolditalcontent ( ital_markup )? );
	public final ASTNode table_boldcontentpart() throws RecognitionException {
		ASTNode node =  null;

		CollectionNode tf = null;

		CollectionNode tb = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:388:2: (tf= table_formattedcontent | ital_markup tb= table_bolditalcontent ( ital_markup )? )
			int alt83=2;
			int LA83_0 = input.LA(1);

			if ( ((LA83_0>=FORCED_END_OF_LINE && LA83_0<=WIKI)||LA83_0==POUND||LA83_0==EQUAL||(LA83_0>=LINK_OPEN && LA83_0<=75)) ) {
				alt83=1;
			}
			else if ( (LA83_0==ITAL) ) {
				alt83=2;
			}
			else {
				if (backtracking>0) {failed=true; return node;}
				NoViableAltException nvae =
					new NoViableAltException("387:1: table_boldcontentpart returns [ASTNode node = null] : (tf= table_formattedcontent | ital_markup tb= table_bolditalcontent ( ital_markup )? );", 83, 0, input);

				throw nvae;
			}
			switch (alt83) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:388:4: tf= table_formattedcontent
					{
					pushFollow(FOLLOW_table_formattedcontent_in_table_boldcontentpart2242);
					tf=table_formattedcontent();
					_fsp--;
					if (failed) return node;
					if ( backtracking==0 ) {
					  node = tf;
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:389:4: ital_markup tb= table_bolditalcontent ( ital_markup )?
					{
					pushFollow(FOLLOW_ital_markup_in_table_boldcontentpart2249);
					ital_markup();
					_fsp--;
					if (failed) return node;
					pushFollow(FOLLOW_table_bolditalcontent_in_table_boldcontentpart2256);
					tb=table_bolditalcontent();
					_fsp--;
					if (failed) return node;
					if ( backtracking==0 ) {
					   node = new ItalicTextNode(tb);
					}
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:389:92: ( ital_markup )?
					int alt82=2;
					int LA82_0 = input.LA(1);

					if ( (LA82_0==ITAL) ) {
						alt82=1;
					}
					switch (alt82) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:389:94: ital_markup
							{
							pushFollow(FOLLOW_ital_markup_in_table_boldcontentpart2263);
							ital_markup();
							_fsp--;
							if (failed) return node;

							}
							break;

					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return node;
	}
	// $ANTLR end table_boldcontentpart

	// $ANTLR start table_italcontentpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:391:1: table_italcontentpart returns [ASTNode node = null] : ( bold_markup tb= table_bolditalcontent ( bold_markup )? | tf= table_formattedcontent );
	public final ASTNode table_italcontentpart() throws RecognitionException {
		ASTNode node =  null;

		CollectionNode tb = null;

		CollectionNode tf = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:392:2: ( bold_markup tb= table_bolditalcontent ( bold_markup )? | tf= table_formattedcontent )
			int alt85=2;
			int LA85_0 = input.LA(1);

			if ( (LA85_0==STAR) ) {
				alt85=1;
			}
			else if ( ((LA85_0>=FORCED_END_OF_LINE && LA85_0<=WIKI)||LA85_0==POUND||LA85_0==EQUAL||(LA85_0>=LINK_OPEN && LA85_0<=75)) ) {
				alt85=2;
			}
			else {
				if (backtracking>0) {failed=true; return node;}
				NoViableAltException nvae =
					new NoViableAltException("391:1: table_italcontentpart returns [ASTNode node = null] : ( bold_markup tb= table_bolditalcontent ( bold_markup )? | tf= table_formattedcontent );", 85, 0, input);

				throw nvae;
			}
			switch (alt85) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:392:4: bold_markup tb= table_bolditalcontent ( bold_markup )?
					{
					pushFollow(FOLLOW_bold_markup_in_table_italcontentpart2280);
					bold_markup();
					_fsp--;
					if (failed) return node;
					pushFollow(FOLLOW_table_bolditalcontent_in_table_italcontentpart2287);
					tb=table_bolditalcontent();
					_fsp--;
					if (failed) return node;
					if ( backtracking==0 ) {
					  node = new BoldTextNode(tb);
					}
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:392:88: ( bold_markup )?
					int alt84=2;
					int LA84_0 = input.LA(1);

					if ( (LA84_0==STAR) ) {
						int LA84_1 = input.LA(2);

						if ( (LA84_1==STAR) ) {
							alt84=1;
						}
					}
					switch (alt84) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:392:90: bold_markup
							{
							pushFollow(FOLLOW_bold_markup_in_table_italcontentpart2294);
							bold_markup();
							_fsp--;
							if (failed) return node;

							}
							break;

					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:393:4: tf= table_formattedcontent
					{
					pushFollow(FOLLOW_table_formattedcontent_in_table_italcontentpart2306);
					tf=table_formattedcontent();
					_fsp--;
					if (failed) return node;
					if ( backtracking==0 ) {
					   node = tf;
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return node;
	}
	// $ANTLR end table_italcontentpart

	// $ANTLR start table_bolditalcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:395:1: table_bolditalcontent returns [CollectionNode elements = null] : ( onestar (tfc= table_formattedcontent onestar )? | EOF );
	public final CollectionNode table_bolditalcontent() throws RecognitionException {
		CollectionNode elements =  null;

		CollectionNode tfc = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:396:2: ( onestar (tfc= table_formattedcontent onestar )? | EOF )
			int alt87=2;
			int LA87_0 = input.LA(1);

			if ( ((LA87_0>=FORCED_END_OF_LINE && LA87_0<=EQUAL)||(LA87_0>=ITAL && LA87_0<=75)) ) {
				alt87=1;
			}
			else if ( (LA87_0==EOF||LA87_0==PIPE) ) {
				alt87=1;
			}
			else {
				if (backtracking>0) {failed=true; return elements;}
				NoViableAltException nvae =
					new NoViableAltException("395:1: table_bolditalcontent returns [CollectionNode elements = null] : ( onestar (tfc= table_formattedcontent onestar )? | EOF );", 87, 0, input);

				throw nvae;
			}
			switch (alt87) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:396:4: onestar (tfc= table_formattedcontent onestar )?
					{
					pushFollow(FOLLOW_onestar_in_table_bolditalcontent2322);
					onestar();
					_fsp--;
					if (failed) return elements;
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:396:13: (tfc= table_formattedcontent onestar )?
					int alt86=2;
					switch ( input.LA(1) ) {
						case FORCED_END_OF_LINE:
						case HEADING_SECTION:
						case HORIZONTAL_SECTION:
						case LIST_ITEM:
						case LIST_ITEM_PART:
						case NOWIKI_SECTION:
						case SCAPE_NODE:
						case TEXT_NODE:
						case UNORDERED_LIST:
						case UNFORMATTED_TEXT:
						case WIKI:
						case POUND:
						case EQUAL:
						case NOWIKI_BLOCK_CLOSE:
						case NOWIKI_CLOSE:
						case LINK_CLOSE:
						case IMAGE_CLOSE:
						case BLANKS:
						case DASH:
						case CR:
						case LF:
						case SPACE:
						case TABULATOR:
						case COLON_SLASH:
						case SLASH:
						case INSIGNIFICANT_CHAR:
						case 40:
						case 41:
						case 42:
						case 43:
						case 44:
						case 45:
						case 46:
						case 47:
						case 48:
						case 49:
						case 50:
						case 51:
						case 52:
						case 53:
						case 54:
						case 55:
						case 56:
						case 57:
						case 58:
						case 59:
						case 60:
						case 61:
						case 62:
						case 63:
						case 64:
						case 65:
						case 66:
						case 67:
						case 68:
						case 69:
						case 70:
						case 71:
						case 72:
						case 73:
						case 74:
						case 75:
							{
							alt86=1;
							}
							break;
						case FORCED_LINEBREAK:
							{
							alt86=1;
							}
							break;
						case ESCAPE:
							{
							alt86=1;
							}
							break;
						case LINK_OPEN:
							{
							alt86=1;
							}
							break;
						case IMAGE_OPEN:
							{
							alt86=1;
							}
							break;
						case EXTENSION:
							{
							alt86=1;
							}
							break;
						case NOWIKI_OPEN:
							{
							alt86=1;
							}
							break;
					}

					switch (alt86) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:396:15: tfc= table_formattedcontent onestar
							{
							pushFollow(FOLLOW_table_formattedcontent_in_table_bolditalcontent2331);
							tfc=table_formattedcontent();
							_fsp--;
							if (failed) return elements;
							if ( backtracking==0 ) {
							   elements = tfc;
							}
							pushFollow(FOLLOW_onestar_in_table_bolditalcontent2336);
							onestar();
							_fsp--;
							if (failed) return elements;

							}
							break;

					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:397:4: EOF
					{
					match(input,EOF,FOLLOW_EOF_in_table_bolditalcontent2344); if (failed) return elements;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return elements;
	}
	// $ANTLR end table_bolditalcontent

	// $ANTLR start table_formattedcontent
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:399:1: table_formattedcontent returns [CollectionNode elements = new CollectionNode()] : (tu= table_unformattedelement )+ ;
	public final CollectionNode table_formattedcontent() throws RecognitionException {
		CollectionNode elements =  new CollectionNode();

		ASTNode tu = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:400:2: ( (tu= table_unformattedelement )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:400:4: (tu= table_unformattedelement )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:400:4: (tu= table_unformattedelement )+
			int cnt88=0;
			loop88:
			do {
				int alt88=2;
				switch ( input.LA(1) ) {
				case FORCED_END_OF_LINE:
				case HEADING_SECTION:
				case HORIZONTAL_SECTION:
				case LIST_ITEM:
				case LIST_ITEM_PART:
				case NOWIKI_SECTION:
				case SCAPE_NODE:
				case TEXT_NODE:
				case UNORDERED_LIST:
				case UNFORMATTED_TEXT:
				case WIKI:
				case POUND:
				case EQUAL:
				case NOWIKI_BLOCK_CLOSE:
				case NOWIKI_CLOSE:
				case LINK_CLOSE:
				case IMAGE_CLOSE:
				case BLANKS:
				case DASH:
				case CR:
				case LF:
				case SPACE:
				case TABULATOR:
				case COLON_SLASH:
				case SLASH:
				case INSIGNIFICANT_CHAR:
				case 40:
				case 41:
				case 42:
				case 43:
				case 44:
				case 45:
				case 46:
				case 47:
				case 48:
				case 49:
				case 50:
				case 51:
				case 52:
				case 53:
				case 54:
				case 55:
				case 56:
				case 57:
				case 58:
				case 59:
				case 60:
				case 61:
				case 62:
				case 63:
				case 64:
				case 65:
				case 66:
				case 67:
				case 68:
				case 69:
				case 70:
				case 71:
				case 72:
				case 73:
				case 74:
				case 75:
					{
					alt88=1;
					}
					break;
				case FORCED_LINEBREAK:
					{
					alt88=1;
					}
					break;
				case ESCAPE:
					{
					alt88=1;
					}
					break;
				case LINK_OPEN:
					{
					alt88=1;
					}
					break;
				case IMAGE_OPEN:
					{
					alt88=1;
					}
					break;
				case EXTENSION:
					{
					alt88=1;
					}
					break;
				case NOWIKI_OPEN:
					{
					alt88=1;
					}
					break;

				}

				switch (alt88) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:400:6: tu= table_unformattedelement
					{
					pushFollow(FOLLOW_table_unformattedelement_in_table_formattedcontent2364);
					tu=table_unformattedelement();
					_fsp--;
					if (failed) return elements;
					if ( backtracking==0 ) {
					   elements.add(tu);
					}

					}
					break;

				default :
					if ( cnt88 >= 1 ) break loop88;
					if (backtracking>0) {failed=true; return elements;}
						EarlyExitException eee =
							new EarlyExitException(88, input);
						throw eee;
				}
				cnt88++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return elements;
	}
	// $ANTLR end table_formattedcontent

	// $ANTLR start table_unformattedelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:402:1: table_unformattedelement returns [ASTNode content = null] : (tu= table_unformatted | ti= table_inlineelement );
	public final ASTNode table_unformattedelement() throws RecognitionException {
		ASTNode content =  null;

		CollectionNode tu = null;

		ASTNode ti = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:403:2: (tu= table_unformatted | ti= table_inlineelement )
			int alt89=2;
			int LA89_0 = input.LA(1);

			if ( ((LA89_0>=FORCED_END_OF_LINE && LA89_0<=WIKI)||LA89_0==POUND||LA89_0==EQUAL||(LA89_0>=FORCED_LINEBREAK && LA89_0<=75)) ) {
				alt89=1;
			}
			else if ( ((LA89_0>=LINK_OPEN && LA89_0<=EXTENSION)) ) {
				alt89=2;
			}
			else {
				if (backtracking>0) {failed=true; return content;}
				NoViableAltException nvae =
					new NoViableAltException("402:1: table_unformattedelement returns [ASTNode content = null] : (tu= table_unformatted | ti= table_inlineelement );", 89, 0, input);

				throw nvae;
			}
			switch (alt89) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:403:4: tu= table_unformatted
					{
					pushFollow(FOLLOW_table_unformatted_in_table_unformattedelement2387);
					tu=table_unformatted();
					_fsp--;
					if (failed) return content;
					if ( backtracking==0 ) {
					  content = new UnformattedTextNode(tu);
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:404:4: ti= table_inlineelement
					{
					pushFollow(FOLLOW_table_inlineelement_in_table_unformattedelement2399);
					ti=table_inlineelement();
					_fsp--;
					if (failed) return content;
					if ( backtracking==0 ) {
					  content = ti;
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return content;
	}
	// $ANTLR end table_unformattedelement

	// $ANTLR start table_inlineelement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:406:1: table_inlineelement returns [ASTNode element = null] : (l= link | i= image | e= extension | nw= nowiki_inline );
	public final ASTNode table_inlineelement() throws RecognitionException {
		ASTNode element =  null;

		LinkNode l = null;

		ImageNode i = null;

		ASTNode e = null;

		NoWikiSectionNode nw = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:407:2: (l= link | i= image | e= extension | nw= nowiki_inline )
			int alt90=4;
			switch ( input.LA(1) ) {
			case LINK_OPEN:
				{
				alt90=1;
				}
				break;
			case IMAGE_OPEN:
				{
				alt90=2;
				}
				break;
			case EXTENSION:
				{
				alt90=3;
				}
				break;
			case NOWIKI_OPEN:
				{
				alt90=4;
				}
				break;
			default:
				if (backtracking>0) {failed=true; return element;}
				NoViableAltException nvae =
					new NoViableAltException("406:1: table_inlineelement returns [ASTNode element = null] : (l= link | i= image | e= extension | nw= nowiki_inline );", 90, 0, input);

				throw nvae;
			}

			switch (alt90) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:407:4: l= link
					{
					pushFollow(FOLLOW_link_in_table_inlineelement2420);
					l=link();
					_fsp--;
					if (failed) return element;
					if ( backtracking==0 ) {
					  element = l;
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:408:4: i= image
					{
					pushFollow(FOLLOW_image_in_table_inlineelement2430);
					i=image();
					_fsp--;
					if (failed) return element;
					if ( backtracking==0 ) {
					  element = i;
					}

					}
					break;
				case 3 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:409:4: e= extension
					{
					pushFollow(FOLLOW_extension_in_table_inlineelement2441);
					e=extension();
					_fsp--;
					if (failed) return element;
					if ( backtracking==0 ) {
					  element = e;
					}

					}
					break;
				case 4 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:410:4: nw= nowiki_inline
					{
					pushFollow(FOLLOW_nowiki_inline_in_table_inlineelement2451);
					nw=nowiki_inline();
					_fsp--;
					if (failed) return element;
					if ( backtracking==0 ) {
					  element = nw;
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return element;
	}
	// $ANTLR end table_inlineelement

	// $ANTLR start table_unformatted
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:412:1: table_unformatted returns [CollectionNode text = new CollectionNode()] : (t= table_unformatted_text | ( forced_linebreak | e= escaped )+ );
	public final CollectionNode table_unformatted() throws RecognitionException {
		CollectionNode text =  new CollectionNode();

		String t = null;

		ScapedNode e = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:413:2: (t= table_unformatted_text | ( forced_linebreak | e= escaped )+ )
			int alt92=2;
			int LA92_0 = input.LA(1);

			if ( ((LA92_0>=FORCED_END_OF_LINE && LA92_0<=WIKI)||LA92_0==POUND||LA92_0==EQUAL||(LA92_0>=NOWIKI_BLOCK_CLOSE && LA92_0<=75)) ) {
				alt92=1;
			}
			else if ( ((LA92_0>=FORCED_LINEBREAK && LA92_0<=ESCAPE)) ) {
				alt92=2;
			}
			else {
				if (backtracking>0) {failed=true; return text;}
				NoViableAltException nvae =
					new NoViableAltException("412:1: table_unformatted returns [CollectionNode text = new CollectionNode()] : (t= table_unformatted_text | ( forced_linebreak | e= escaped )+ );", 92, 0, input);

				throw nvae;
			}
			switch (alt92) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:413:5: t= table_unformatted_text
					{
					pushFollow(FOLLOW_table_unformatted_text_in_table_unformatted2473);
					t=table_unformatted_text();
					_fsp--;
					if (failed) return text;
					if ( backtracking==0 ) {
					   text.add(new UnformattedTextNode(t));
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:414:5: ( forced_linebreak | e= escaped )+
					{
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:414:5: ( forced_linebreak | e= escaped )+
					int cnt91=0;
					loop91:
					do {
						int alt91=3;
						int LA91_0 = input.LA(1);

						if ( (LA91_0==FORCED_LINEBREAK) ) {
							alt91=1;
						}
						else if ( (LA91_0==ESCAPE) ) {
							alt91=2;
						}

						switch (alt91) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:414:6: forced_linebreak
							{
							pushFollow(FOLLOW_forced_linebreak_in_table_unformatted2482);
							forced_linebreak();
							_fsp--;
							if (failed) return text;
							if ( backtracking==0 ) {
							  text.add(new ForcedEndOfLineNode());
							}

							}
							break;
						case 2 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:415:5: e= escaped
							{
							pushFollow(FOLLOW_escaped_in_table_unformatted2495);
							e=escaped();
							_fsp--;
							if (failed) return text;
							if ( backtracking==0 ) {
							  text.add(e);
							}

							}
							break;

						default :
							if ( cnt91 >= 1 ) break loop91;
							if (backtracking>0) {failed=true; return text;}
								EarlyExitException eee =
									new EarlyExitException(91, input);
								throw eee;
						}
						cnt91++;
					} while (true);

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return text;
	}
	// $ANTLR end table_unformatted

	// $ANTLR start table_unformatted_text
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:418:1: table_unformatted_text returns [String text = new String()] : (c=~ ( PIPE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ ;
	public final String table_unformatted_text() throws RecognitionException {
		String text =  new String();

		Token c=null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:419:2: ( (c=~ ( PIPE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:419:4: (c=~ ( PIPE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:419:4: (c=~ ( PIPE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
			int cnt93=0;
			loop93:
			do {
				int alt93=2;
				int LA93_0 = input.LA(1);

				if ( ((LA93_0>=FORCED_END_OF_LINE && LA93_0<=WIKI)||LA93_0==POUND||LA93_0==EQUAL||(LA93_0>=NOWIKI_BLOCK_CLOSE && LA93_0<=75)) ) {
					alt93=1;
				}

				switch (alt93) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:419:6: c=~ ( PIPE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF )
					{
					c=(Token)input.LT(1);
					if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||input.LA(1)==POUND||input.LA(1)==EQUAL||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=75) ) {
						input.consume();
						errorRecovery=false;failed=false;
					}
					else {
						if (backtracking>0) {failed=true; return text;}
						MismatchedSetException mse =
							new MismatchedSetException(null,input);
						recoverFromMismatchedSet(input,mse,FOLLOW_set_in_table_unformatted_text2521);	throw mse;
					}

					if ( backtracking==0 ) {
					  text += c.getText();
					}

					}
					break;

				default :
					if ( cnt93 >= 1 ) break loop93;
					if (backtracking>0) {failed=true; return text;}
						EarlyExitException eee =
							new EarlyExitException(93, input);
						throw eee;
				}
				cnt93++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return text;
	}
	// $ANTLR end table_unformatted_text

	// $ANTLR start nowiki_block
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:433:1: nowiki_block returns [NoWikiSectionNode nowikiNode] : nowikiblock_open_markup contents= nowiki_block_contents nowikiblock_close_markup paragraph_separator ;
	public final NoWikiSectionNode nowiki_block() throws RecognitionException {
		NoWikiSectionNode nowikiNode = null;

		nowiki_block_contents_return contents = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:434:2: ( nowikiblock_open_markup contents= nowiki_block_contents nowikiblock_close_markup paragraph_separator )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:434:4: nowikiblock_open_markup contents= nowiki_block_contents nowikiblock_close_markup paragraph_separator
			{
			pushFollow(FOLLOW_nowikiblock_open_markup_in_nowiki_block2618);
			nowikiblock_open_markup();
			_fsp--;
			if (failed) return nowikiNode;
			pushFollow(FOLLOW_nowiki_block_contents_in_nowiki_block2625);
			contents=nowiki_block_contents();
			_fsp--;
			if (failed) return nowikiNode;
			if ( backtracking==0 ) {
			  nowikiNode = new NoWikiSectionNode(input.toString(contents.start,contents.stop));
			}
			pushFollow(FOLLOW_nowikiblock_close_markup_in_nowiki_block2631);
			nowikiblock_close_markup();
			_fsp--;
			if (failed) return nowikiNode;
			pushFollow(FOLLOW_paragraph_separator_in_nowiki_block2634);
			paragraph_separator();
			_fsp--;
			if (failed) return nowikiNode;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return nowikiNode;
	}
	// $ANTLR end nowiki_block

	// $ANTLR start nowikiblock_open_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:438:1: nowikiblock_open_markup : nowiki_open_markup newline ;
	public final void nowikiblock_open_markup() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:439:2: ( nowiki_open_markup newline )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:439:4: nowiki_open_markup newline
			{
			pushFollow(FOLLOW_nowiki_open_markup_in_nowikiblock_open_markup2647);
			nowiki_open_markup();
			_fsp--;
			if (failed) return ;
			pushFollow(FOLLOW_newline_in_nowikiblock_open_markup2650);
			newline();
			_fsp--;
			if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end nowikiblock_open_markup

	// $ANTLR start nowikiblock_close_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:442:1: nowikiblock_close_markup : NOWIKI_BLOCK_CLOSE ;
	public final void nowikiblock_close_markup() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:443:2: ( NOWIKI_BLOCK_CLOSE )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:443:4: NOWIKI_BLOCK_CLOSE
			{
			match(input,NOWIKI_BLOCK_CLOSE,FOLLOW_NOWIKI_BLOCK_CLOSE_in_nowikiblock_close_markup2662); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end nowikiblock_close_markup

	// $ANTLR start nowiki_inline
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:446:1: nowiki_inline returns [NoWikiSectionNode nowiki = null] : nowiki_open_markup t= nowiki_inline_contents nowiki_close_markup ;
	public final NoWikiSectionNode nowiki_inline() throws RecognitionException {
		NoWikiSectionNode nowiki =  null;

		String t = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:447:2: ( nowiki_open_markup t= nowiki_inline_contents nowiki_close_markup )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:447:4: nowiki_open_markup t= nowiki_inline_contents nowiki_close_markup
			{
			pushFollow(FOLLOW_nowiki_open_markup_in_nowiki_inline2677);
			nowiki_open_markup();
			_fsp--;
			if (failed) return nowiki;
			pushFollow(FOLLOW_nowiki_inline_contents_in_nowiki_inline2684);
			t=nowiki_inline_contents();
			_fsp--;
			if (failed) return nowiki;
			pushFollow(FOLLOW_nowiki_close_markup_in_nowiki_inline2689);
			nowiki_close_markup();
			_fsp--;
			if (failed) return nowiki;
			if ( backtracking==0 ) {
			  nowiki = new NoWikiSectionNode(t);
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return nowiki;
	}
	// $ANTLR end nowiki_inline

	public static class nowiki_block_contents_return extends ParserRuleReturnScope {
		public String contents = new String();
	};

	// $ANTLR start nowiki_block_contents
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:451:1: nowiki_block_contents returns [String contents = new String()] : (c=~ ( NOWIKI_BLOCK_CLOSE | EOF ) )* ;
	public final nowiki_block_contents_return nowiki_block_contents() throws RecognitionException {
		nowiki_block_contents_return retval = new nowiki_block_contents_return();
		retval.start = input.LT(1);

		Token c=null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:452:2: ( (c=~ ( NOWIKI_BLOCK_CLOSE | EOF ) )* )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:452:4: (c=~ ( NOWIKI_BLOCK_CLOSE | EOF ) )*
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:452:4: (c=~ ( NOWIKI_BLOCK_CLOSE | EOF ) )*
			loop94:
			do {
				int alt94=2;
				int LA94_0 = input.LA(1);

				if ( ((LA94_0>=FORCED_END_OF_LINE && LA94_0<=ESCAPE)||(LA94_0>=NOWIKI_CLOSE && LA94_0<=75)) ) {
					alt94=1;
				}

				switch (alt94) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:452:5: c=~ ( NOWIKI_BLOCK_CLOSE | EOF )
					{
					c=(Token)input.LT(1);
					if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=ESCAPE)||(input.LA(1)>=NOWIKI_CLOSE && input.LA(1)<=75) ) {
						input.consume();
						errorRecovery=false;failed=false;
					}
					else {
						if (backtracking>0) {failed=true; return retval;}
						MismatchedSetException mse =
							new MismatchedSetException(null,input);
						recoverFromMismatchedSet(input,mse,FOLLOW_set_in_nowiki_block_contents2710);	throw mse;
					}

					if ( backtracking==0 ) {
					  retval.contents += c.getText();
					}

					}
					break;

				default :
					break loop94;
				}
			} while (true);

			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return retval;
	}
	// $ANTLR end nowiki_block_contents

	// $ANTLR start nowiki_inline_contents
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:455:1: nowiki_inline_contents returns [String text = new String()] : (c=~ ( NOWIKI_CLOSE | NEWLINE | EOF ) )* ;
	public final String nowiki_inline_contents() throws RecognitionException {
		String text =  new String();

		Token c=null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:456:2: ( (c=~ ( NOWIKI_CLOSE | NEWLINE | EOF ) )* )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:456:4: (c=~ ( NOWIKI_CLOSE | NEWLINE | EOF ) )*
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:456:4: (c=~ ( NOWIKI_CLOSE | NEWLINE | EOF ) )*
			loop95:
			do {
				int alt95=2;
				int LA95_0 = input.LA(1);

				if ( ((LA95_0>=FORCED_END_OF_LINE && LA95_0<=WIKI)||(LA95_0>=POUND && LA95_0<=NOWIKI_BLOCK_CLOSE)||(LA95_0>=LINK_CLOSE && LA95_0<=75)) ) {
					alt95=1;
				}

				switch (alt95) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:456:5: c=~ ( NOWIKI_CLOSE | NEWLINE | EOF )
					{
					c=(Token)input.LT(1);
					if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=NOWIKI_BLOCK_CLOSE)||(input.LA(1)>=LINK_CLOSE && input.LA(1)<=75) ) {
						input.consume();
						errorRecovery=false;failed=false;
					}
					else {
						if (backtracking>0) {failed=true; return text;}
						MismatchedSetException mse =
							new MismatchedSetException(null,input);
						recoverFromMismatchedSet(input,mse,FOLLOW_set_in_nowiki_inline_contents2744);	throw mse;
					}

					if ( backtracking==0 ) {
					   text +=c.getText();
					}

					}
					break;

				default :
					break loop95;
				}
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return text;
	}
	// $ANTLR end nowiki_inline_contents

	// $ANTLR start horizontalrule
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:462:1: horizontalrule returns [ASTNode horizontal = null] : horizontalrule_markup ( blanks )? paragraph_separator ;
	public final ASTNode horizontalrule() throws RecognitionException {
		ASTNode horizontal =  null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:463:2: ( horizontalrule_markup ( blanks )? paragraph_separator )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:463:4: horizontalrule_markup ( blanks )? paragraph_separator
			{
			pushFollow(FOLLOW_horizontalrule_markup_in_horizontalrule2779);
			horizontalrule_markup();
			_fsp--;
			if (failed) return horizontal;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:463:27: ( blanks )?
			int alt96=2;
			int LA96_0 = input.LA(1);

			if ( (LA96_0==BLANKS) ) {
				alt96=1;
			}
			switch (alt96) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:463:29: blanks
					{
					pushFollow(FOLLOW_blanks_in_horizontalrule2784);
					blanks();
					_fsp--;
					if (failed) return horizontal;

					}
					break;

			}

			pushFollow(FOLLOW_paragraph_separator_in_horizontalrule2790);
			paragraph_separator();
			_fsp--;
			if (failed) return horizontal;
			if ( backtracking==0 ) {
			  horizontal = new HorizontalNode();
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return horizontal;
	}
	// $ANTLR end horizontalrule

	// $ANTLR start link
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:470:1: link returns [LinkNode link = null] : link_open_markup a= link_address ( link_description_markup d= link_description )? link_close_markup ;
	public final LinkNode link() throws RecognitionException {
		LinkNode link =  null;

		LinkNode a = null;

		CollectionNode d = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:471:2: ( link_open_markup a= link_address ( link_description_markup d= link_description )? link_close_markup )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:471:4: link_open_markup a= link_address ( link_description_markup d= link_description )? link_close_markup
			{
			pushFollow(FOLLOW_link_open_markup_in_link2812);
			link_open_markup();
			_fsp--;
			if (failed) return link;
			pushFollow(FOLLOW_link_address_in_link2818);
			a=link_address();
			_fsp--;
			if (failed) return link;
			if ( backtracking==0 ) {
			  link = a;
			}
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:471:59: ( link_description_markup d= link_description )?
			int alt97=2;
			int LA97_0 = input.LA(1);

			if ( (LA97_0==PIPE) ) {
				alt97=1;
			}
			switch (alt97) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:471:60: link_description_markup d= link_description
					{
					pushFollow(FOLLOW_link_description_markup_in_link2824);
					link_description_markup();
					_fsp--;
					if (failed) return link;
					pushFollow(FOLLOW_link_description_in_link2834);
					d=link_description();
					_fsp--;
					if (failed) return link;
					if ( backtracking==0 ) {
					  link.setAltNode(d);
					}

					}
					break;

			}

			pushFollow(FOLLOW_link_close_markup_in_link2842);
			link_close_markup();
			_fsp--;
			if (failed) return link;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return link;
	}
	// $ANTLR end link

	// $ANTLR start link_address
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );
	public final LinkNode link_address() throws RecognitionException {
		LinkNode link = null;

		InterwikiLinkNode li = null;

		String p = null;

		String lu = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:475:2: (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri )
			int alt98=2;
			switch ( input.LA(1) ) {
			case 41:
				{
				int LA98_1 = input.LA(2);

				if ( (LA98_1==42) ) {
					int LA98_16 = input.LA(3);

					if ( (LA98_16==40) ) {
						int LA98_34 = input.LA(4);

						if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
							alt98=1;
						}
						else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

							throw nvae;
						}
					}
					else if ( ((LA98_16>=FORCED_END_OF_LINE && LA98_16<=WIKI)||(LA98_16>=POUND && LA98_16<=INSIGNIFICANT_CHAR)||(LA98_16>=41 && LA98_16<=75)) ) {
						alt98=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 16, input);

						throw nvae;
					}
				}
				else if ( ((LA98_1>=FORCED_END_OF_LINE && LA98_1<=WIKI)||(LA98_1>=POUND && LA98_1<=41)||(LA98_1>=43 && LA98_1<=75)) ) {
					alt98=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 1, input);

					throw nvae;
				}
				}
				break;
			case 43:
				{
				int LA98_2 = input.LA(2);

				if ( (LA98_2==44) ) {
					int LA98_17 = input.LA(3);

					if ( (LA98_17==45) ) {
						int LA98_35 = input.LA(4);

						if ( (LA98_35==46) ) {
							int LA98_55 = input.LA(5);

							if ( (LA98_55==47) ) {
								int LA98_74 = input.LA(6);

								if ( (LA98_74==48) ) {
									int LA98_93 = input.LA(7);

									if ( (LA98_93==45) ) {
										int LA98_109 = input.LA(8);

										if ( (LA98_109==48) ) {
											int LA98_120 = input.LA(9);

											if ( (LA98_120==40) ) {
												int LA98_34 = input.LA(10);

												if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
													alt98=1;
												}
												else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
													alt98=2;
												}
												else {
													if (backtracking>0) {failed=true; return link;}
													NoViableAltException nvae =
														new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

													throw nvae;
												}
											}
											else if ( ((LA98_120>=FORCED_END_OF_LINE && LA98_120<=WIKI)||(LA98_120>=POUND && LA98_120<=INSIGNIFICANT_CHAR)||(LA98_120>=41 && LA98_120<=75)) ) {
												alt98=2;
											}
											else {
												if (backtracking>0) {failed=true; return link;}
												NoViableAltException nvae =
													new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 120, input);

												throw nvae;
											}
										}
										else if ( ((LA98_109>=FORCED_END_OF_LINE && LA98_109<=WIKI)||(LA98_109>=POUND && LA98_109<=47)||(LA98_109>=49 && LA98_109<=75)) ) {
											alt98=2;
										}
										else {
											if (backtracking>0) {failed=true; return link;}
											NoViableAltException nvae =
												new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 109, input);

											throw nvae;
										}
									}
									else if ( ((LA98_93>=FORCED_END_OF_LINE && LA98_93<=WIKI)||(LA98_93>=POUND && LA98_93<=44)||(LA98_93>=46 && LA98_93<=75)) ) {
										alt98=2;
									}
									else {
										if (backtracking>0) {failed=true; return link;}
										NoViableAltException nvae =
											new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 93, input);

										throw nvae;
									}
								}
								else if ( ((LA98_74>=FORCED_END_OF_LINE && LA98_74<=WIKI)||(LA98_74>=POUND && LA98_74<=47)||(LA98_74>=49 && LA98_74<=75)) ) {
									alt98=2;
								}
								else {
									if (backtracking>0) {failed=true; return link;}
									NoViableAltException nvae =
										new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 74, input);

									throw nvae;
								}
							}
							else if ( ((LA98_55>=FORCED_END_OF_LINE && LA98_55<=WIKI)||(LA98_55>=POUND && LA98_55<=46)||(LA98_55>=48 && LA98_55<=75)) ) {
								alt98=2;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
									new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 55, input);

								throw nvae;
							}
						}
						else if ( ((LA98_35>=FORCED_END_OF_LINE && LA98_35<=WIKI)||(LA98_35>=POUND && LA98_35<=45)||(LA98_35>=47 && LA98_35<=75)) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 35, input);

							throw nvae;
						}
					}
					else if ( ((LA98_17>=FORCED_END_OF_LINE && LA98_17<=WIKI)||(LA98_17>=POUND && LA98_17<=44)||(LA98_17>=46 && LA98_17<=75)) ) {
						alt98=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 17, input);

						throw nvae;
					}
				}
				else if ( ((LA98_2>=FORCED_END_OF_LINE && LA98_2<=WIKI)||(LA98_2>=POUND && LA98_2<=43)||(LA98_2>=45 && LA98_2<=75)) ) {
					alt98=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 2, input);

					throw nvae;
				}
				}
				break;
			case 49:
				{
				int LA98_3 = input.LA(2);

				if ( (LA98_3==50) ) {
					int LA98_18 = input.LA(3);

					if ( (LA98_18==48) ) {
						int LA98_36 = input.LA(4);

						if ( (LA98_36==51) ) {
							int LA98_56 = input.LA(5);

							if ( (LA98_56==45) ) {
								int LA98_75 = input.LA(6);

								if ( (LA98_75==52) ) {
									int LA98_94 = input.LA(7);

									if ( (LA98_94==40) ) {
										int LA98_34 = input.LA(8);

										if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
											alt98=1;
										}
										else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
											alt98=2;
										}
										else {
											if (backtracking>0) {failed=true; return link;}
											NoViableAltException nvae =
												new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

											throw nvae;
										}
									}
									else if ( ((LA98_94>=FORCED_END_OF_LINE && LA98_94<=WIKI)||(LA98_94>=POUND && LA98_94<=INSIGNIFICANT_CHAR)||(LA98_94>=41 && LA98_94<=75)) ) {
										alt98=2;
									}
									else {
										if (backtracking>0) {failed=true; return link;}
										NoViableAltException nvae =
											new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 94, input);

										throw nvae;
									}
								}
								else if ( ((LA98_75>=FORCED_END_OF_LINE && LA98_75<=WIKI)||(LA98_75>=POUND && LA98_75<=51)||(LA98_75>=53 && LA98_75<=75)) ) {
									alt98=2;
								}
								else {
									if (backtracking>0) {failed=true; return link;}
									NoViableAltException nvae =
										new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 75, input);

									throw nvae;
								}
							}
							else if ( ((LA98_56>=FORCED_END_OF_LINE && LA98_56<=WIKI)||(LA98_56>=POUND && LA98_56<=44)||(LA98_56>=46 && LA98_56<=75)) ) {
								alt98=2;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
									new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 56, input);

								throw nvae;
							}
						}
						else if ( ((LA98_36>=FORCED_END_OF_LINE && LA98_36<=WIKI)||(LA98_36>=POUND && LA98_36<=50)||(LA98_36>=52 && LA98_36<=75)) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 36, input);

							throw nvae;
						}
					}
					else if ( ((LA98_18>=FORCED_END_OF_LINE && LA98_18<=WIKI)||(LA98_18>=POUND && LA98_18<=47)||(LA98_18>=49 && LA98_18<=75)) ) {
						alt98=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 18, input);

						throw nvae;
					}
				}
				else if ( ((LA98_3>=FORCED_END_OF_LINE && LA98_3<=WIKI)||(LA98_3>=POUND && LA98_3<=49)||(LA98_3>=51 && LA98_3<=75)) ) {
					alt98=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 3, input);

					throw nvae;
				}
				}
				break;
			case 53:
				{
				int LA98_4 = input.LA(2);

				if ( (LA98_4==44) ) {
					int LA98_19 = input.LA(3);

					if ( (LA98_19==44) ) {
						int LA98_37 = input.LA(4);

						if ( (LA98_37==54) ) {
							int LA98_57 = input.LA(5);

							if ( (LA98_57==50) ) {
								int LA98_76 = input.LA(6);

								if ( (LA98_76==55) ) {
									int LA98_95 = input.LA(7);

									if ( (LA98_95==40) ) {
										int LA98_34 = input.LA(8);

										if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
											alt98=1;
										}
										else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
											alt98=2;
										}
										else {
											if (backtracking>0) {failed=true; return link;}
											NoViableAltException nvae =
												new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

											throw nvae;
										}
									}
									else if ( ((LA98_95>=FORCED_END_OF_LINE && LA98_95<=WIKI)||(LA98_95>=POUND && LA98_95<=INSIGNIFICANT_CHAR)||(LA98_95>=41 && LA98_95<=75)) ) {
										alt98=2;
									}
									else {
										if (backtracking>0) {failed=true; return link;}
										NoViableAltException nvae =
											new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 95, input);

										throw nvae;
									}
								}
								else if ( ((LA98_76>=FORCED_END_OF_LINE && LA98_76<=WIKI)||(LA98_76>=POUND && LA98_76<=54)||(LA98_76>=56 && LA98_76<=75)) ) {
									alt98=2;
								}
								else {
									if (backtracking>0) {failed=true; return link;}
									NoViableAltException nvae =
										new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 76, input);

									throw nvae;
								}
							}
							else if ( ((LA98_57>=FORCED_END_OF_LINE && LA98_57<=WIKI)||(LA98_57>=POUND && LA98_57<=49)||(LA98_57>=51 && LA98_57<=75)) ) {
								alt98=2;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
									new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 57, input);

								throw nvae;
							}
						}
						else if ( ((LA98_37>=FORCED_END_OF_LINE && LA98_37<=WIKI)||(LA98_37>=POUND && LA98_37<=53)||(LA98_37>=55 && LA98_37<=75)) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 37, input);

							throw nvae;
						}
					}
					else if ( ((LA98_19>=FORCED_END_OF_LINE && LA98_19<=WIKI)||(LA98_19>=POUND && LA98_19<=43)||(LA98_19>=45 && LA98_19<=75)) ) {
						alt98=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 19, input);

						throw nvae;
					}
				}
				else if ( ((LA98_4>=FORCED_END_OF_LINE && LA98_4<=WIKI)||(LA98_4>=POUND && LA98_4<=43)||(LA98_4>=45 && LA98_4<=75)) ) {
					alt98=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 4, input);

					throw nvae;
				}
				}
				break;
			case 56:
				{
				int LA98_5 = input.LA(2);

				if ( (LA98_5==57) ) {
					int LA98_20 = input.LA(3);

					if ( (LA98_20==58) ) {
						int LA98_38 = input.LA(4);

						if ( (LA98_38==47) ) {
							int LA98_58 = input.LA(5);

							if ( (LA98_58==48) ) {
								int LA98_77 = input.LA(6);

								if ( (LA98_77==45) ) {
									int LA98_96 = input.LA(7);

									if ( (LA98_96==48) ) {
										int LA98_110 = input.LA(8);

										if ( (LA98_110==40) ) {
											int LA98_34 = input.LA(9);

											if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
												alt98=1;
											}
											else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
												alt98=2;
											}
											else {
												if (backtracking>0) {failed=true; return link;}
												NoViableAltException nvae =
													new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

												throw nvae;
											}
										}
										else if ( ((LA98_110>=FORCED_END_OF_LINE && LA98_110<=WIKI)||(LA98_110>=POUND && LA98_110<=INSIGNIFICANT_CHAR)||(LA98_110>=41 && LA98_110<=75)) ) {
											alt98=2;
										}
										else {
											if (backtracking>0) {failed=true; return link;}
											NoViableAltException nvae =
												new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 110, input);

											throw nvae;
										}
									}
									else if ( ((LA98_96>=FORCED_END_OF_LINE && LA98_96<=WIKI)||(LA98_96>=POUND && LA98_96<=47)||(LA98_96>=49 && LA98_96<=75)) ) {
										alt98=2;
									}
									else {
										if (backtracking>0) {failed=true; return link;}
										NoViableAltException nvae =
											new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 96, input);

										throw nvae;
									}
								}
								else if ( ((LA98_77>=FORCED_END_OF_LINE && LA98_77<=WIKI)||(LA98_77>=POUND && LA98_77<=44)||(LA98_77>=46 && LA98_77<=75)) ) {
									alt98=2;
								}
								else {
									if (backtracking>0) {failed=true; return link;}
									NoViableAltException nvae =
										new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 77, input);

									throw nvae;
								}
							}
							else if ( ((LA98_58>=FORCED_END_OF_LINE && LA98_58<=WIKI)||(LA98_58>=POUND && LA98_58<=47)||(LA98_58>=49 && LA98_58<=75)) ) {
								alt98=2;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
									new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 58, input);

								throw nvae;
							}
						}
						else if ( ((LA98_38>=FORCED_END_OF_LINE && LA98_38<=WIKI)||(LA98_38>=POUND && LA98_38<=46)||(LA98_38>=48 && LA98_38<=75)) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 38, input);

							throw nvae;
						}
					}
					else if ( ((LA98_20>=FORCED_END_OF_LINE && LA98_20<=WIKI)||(LA98_20>=POUND && LA98_20<=57)||(LA98_20>=59 && LA98_20<=75)) ) {
						alt98=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 20, input);

						throw nvae;
					}
				}
				else if ( ((LA98_5>=FORCED_END_OF_LINE && LA98_5<=WIKI)||(LA98_5>=POUND && LA98_5<=56)||(LA98_5>=58 && LA98_5<=75)) ) {
					alt98=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 5, input);

					throw nvae;
				}
				}
				break;
			case 59:
				{
				switch ( input.LA(2) ) {
				case 44:
					{
					int LA98_21 = input.LA(3);

					if ( (LA98_21==48) ) {
						int LA98_39 = input.LA(4);

						if ( (LA98_39==64) ) {
							int LA98_59 = input.LA(5);

							if ( (LA98_59==59) ) {
								int LA98_78 = input.LA(6);

								if ( (LA98_78==44) ) {
									int LA98_97 = input.LA(7);

									if ( (LA98_97==48) ) {
										int LA98_111 = input.LA(8);

										if ( (LA98_111==64) ) {
											int LA98_121 = input.LA(9);

											if ( (LA98_121==40) ) {
												int LA98_34 = input.LA(10);

												if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
													alt98=1;
												}
												else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
													alt98=2;
												}
												else {
													if (backtracking>0) {failed=true; return link;}
													NoViableAltException nvae =
														new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

													throw nvae;
												}
											}
											else if ( ((LA98_121>=FORCED_END_OF_LINE && LA98_121<=WIKI)||(LA98_121>=POUND && LA98_121<=INSIGNIFICANT_CHAR)||(LA98_121>=41 && LA98_121<=75)) ) {
												alt98=2;
											}
											else {
												if (backtracking>0) {failed=true; return link;}
												NoViableAltException nvae =
													new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 121, input);

												throw nvae;
											}
										}
										else if ( ((LA98_111>=FORCED_END_OF_LINE && LA98_111<=WIKI)||(LA98_111>=POUND && LA98_111<=63)||(LA98_111>=65 && LA98_111<=75)) ) {
											alt98=2;
										}
										else {
											if (backtracking>0) {failed=true; return link;}
											NoViableAltException nvae =
												new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 111, input);

											throw nvae;
										}
									}
									else if ( ((LA98_97>=FORCED_END_OF_LINE && LA98_97<=WIKI)||(LA98_97>=POUND && LA98_97<=47)||(LA98_97>=49 && LA98_97<=75)) ) {
										alt98=2;
									}
									else {
										if (backtracking>0) {failed=true; return link;}
										NoViableAltException nvae =
											new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 97, input);

										throw nvae;
									}
								}
								else if ( ((LA98_78>=FORCED_END_OF_LINE && LA98_78<=WIKI)||(LA98_78>=POUND && LA98_78<=43)||(LA98_78>=45 && LA98_78<=75)) ) {
									alt98=2;
								}
								else {
									if (backtracking>0) {failed=true; return link;}
									NoViableAltException nvae =
										new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 78, input);

									throw nvae;
								}
							}
							else if ( ((LA98_59>=FORCED_END_OF_LINE && LA98_59<=WIKI)||(LA98_59>=POUND && LA98_59<=58)||(LA98_59>=60 && LA98_59<=75)) ) {
								alt98=2;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
									new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 59, input);

								throw nvae;
							}
						}
						else if ( ((LA98_39>=FORCED_END_OF_LINE && LA98_39<=WIKI)||(LA98_39>=POUND && LA98_39<=63)||(LA98_39>=65 && LA98_39<=75)) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 39, input);

							throw nvae;
						}
					}
					else if ( ((LA98_21>=FORCED_END_OF_LINE && LA98_21<=WIKI)||(LA98_21>=POUND && LA98_21<=47)||(LA98_21>=49 && LA98_21<=75)) ) {
						alt98=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 21, input);

						throw nvae;
					}
					}
					break;
				case 55:
					{
					switch ( input.LA(3) ) {
					case 60:
						{
						int LA98_40 = input.LA(4);

						if ( (LA98_40==61) ) {
							int LA98_60 = input.LA(5);

							if ( (LA98_60==62) ) {
								int LA98_79 = input.LA(6);

								if ( (LA98_79==60) ) {
									int LA98_98 = input.LA(7);

									if ( (LA98_98==50) ) {
										int LA98_112 = input.LA(8);

										if ( (LA98_112==50) ) {
											int LA98_122 = input.LA(9);

											if ( (LA98_122==40) ) {
												int LA98_34 = input.LA(10);

												if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
													alt98=1;
												}
												else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
													alt98=2;
												}
												else {
													if (backtracking>0) {failed=true; return link;}
													NoViableAltException nvae =
														new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

													throw nvae;
												}
											}
											else if ( ((LA98_122>=FORCED_END_OF_LINE && LA98_122<=WIKI)||(LA98_122>=POUND && LA98_122<=INSIGNIFICANT_CHAR)||(LA98_122>=41 && LA98_122<=75)) ) {
												alt98=2;
											}
											else {
												if (backtracking>0) {failed=true; return link;}
												NoViableAltException nvae =
													new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 122, input);

												throw nvae;
											}
										}
										else if ( ((LA98_112>=FORCED_END_OF_LINE && LA98_112<=WIKI)||(LA98_112>=POUND && LA98_112<=49)||(LA98_112>=51 && LA98_112<=75)) ) {
											alt98=2;
										}
										else {
											if (backtracking>0) {failed=true; return link;}
											NoViableAltException nvae =
												new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 112, input);

											throw nvae;
										}
									}
									else if ( ((LA98_98>=FORCED_END_OF_LINE && LA98_98<=WIKI)||(LA98_98>=POUND && LA98_98<=49)||(LA98_98>=51 && LA98_98<=75)) ) {
										alt98=2;
									}
									else {
										if (backtracking>0) {failed=true; return link;}
										NoViableAltException nvae =
											new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 98, input);

										throw nvae;
									}
								}
								else if ( ((LA98_79>=FORCED_END_OF_LINE && LA98_79<=WIKI)||(LA98_79>=POUND && LA98_79<=59)||(LA98_79>=61 && LA98_79<=75)) ) {
									alt98=2;
								}
								else {
									if (backtracking>0) {failed=true; return link;}
									NoViableAltException nvae =
										new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 79, input);

									throw nvae;
								}
							}
							else if ( ((LA98_60>=FORCED_END_OF_LINE && LA98_60<=WIKI)||(LA98_60>=POUND && LA98_60<=61)||(LA98_60>=63 && LA98_60<=75)) ) {
								alt98=2;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
									new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 60, input);

								throw nvae;
							}
						}
						else if ( ((LA98_40>=FORCED_END_OF_LINE && LA98_40<=WIKI)||(LA98_40>=POUND && LA98_40<=60)||(LA98_40>=62 && LA98_40<=75)) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 40, input);

							throw nvae;
						}
						}
						break;
					case 63:
						{
						int LA98_41 = input.LA(4);

						if ( (LA98_41==48) ) {
							int LA98_61 = input.LA(5);

							if ( (LA98_61==60) ) {
								int LA98_80 = input.LA(6);

								if ( (LA98_80==47) ) {
									int LA98_99 = input.LA(7);

									if ( (LA98_99==48) ) {
										int LA98_113 = input.LA(8);

										if ( (LA98_113==45) ) {
											int LA98_123 = input.LA(9);

											if ( (LA98_123==48) ) {
												int LA98_129 = input.LA(10);

												if ( (LA98_129==40) ) {
													int LA98_34 = input.LA(11);

													if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
														alt98=1;
													}
													else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
														alt98=2;
													}
													else {
														if (backtracking>0) {failed=true; return link;}
														NoViableAltException nvae =
															new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

														throw nvae;
													}
												}
												else if ( ((LA98_129>=FORCED_END_OF_LINE && LA98_129<=WIKI)||(LA98_129>=POUND && LA98_129<=INSIGNIFICANT_CHAR)||(LA98_129>=41 && LA98_129<=75)) ) {
													alt98=2;
												}
												else {
													if (backtracking>0) {failed=true; return link;}
													NoViableAltException nvae =
														new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 129, input);

													throw nvae;
												}
											}
											else if ( ((LA98_123>=FORCED_END_OF_LINE && LA98_123<=WIKI)||(LA98_123>=POUND && LA98_123<=47)||(LA98_123>=49 && LA98_123<=75)) ) {
												alt98=2;
											}
											else {
												if (backtracking>0) {failed=true; return link;}
												NoViableAltException nvae =
													new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 123, input);

												throw nvae;
											}
										}
										else if ( ((LA98_113>=FORCED_END_OF_LINE && LA98_113<=WIKI)||(LA98_113>=POUND && LA98_113<=44)||(LA98_113>=46 && LA98_113<=75)) ) {
											alt98=2;
										}
										else {
											if (backtracking>0) {failed=true; return link;}
											NoViableAltException nvae =
												new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 113, input);

											throw nvae;
										}
									}
									else if ( ((LA98_99>=FORCED_END_OF_LINE && LA98_99<=WIKI)||(LA98_99>=POUND && LA98_99<=47)||(LA98_99>=49 && LA98_99<=75)) ) {
										alt98=2;
									}
									else {
										if (backtracking>0) {failed=true; return link;}
										NoViableAltException nvae =
											new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 99, input);

										throw nvae;
									}
								}
								else if ( ((LA98_80>=FORCED_END_OF_LINE && LA98_80<=WIKI)||(LA98_80>=POUND && LA98_80<=46)||(LA98_80>=48 && LA98_80<=75)) ) {
									alt98=2;
								}
								else {
									if (backtracking>0) {failed=true; return link;}
									NoViableAltException nvae =
										new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 80, input);

									throw nvae;
								}
							}
							else if ( ((LA98_61>=FORCED_END_OF_LINE && LA98_61<=WIKI)||(LA98_61>=POUND && LA98_61<=59)||(LA98_61>=61 && LA98_61<=75)) ) {
								alt98=2;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
									new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 61, input);

								throw nvae;
							}
						}
						else if ( ((LA98_41>=FORCED_END_OF_LINE && LA98_41<=WIKI)||(LA98_41>=POUND && LA98_41<=47)||(LA98_41>=49 && LA98_41<=75)) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 41, input);

							throw nvae;
						}
						}
						break;
					case FORCED_END_OF_LINE:
					case HEADING_SECTION:
					case HORIZONTAL_SECTION:
					case LIST_ITEM:
					case LIST_ITEM_PART:
					case NOWIKI_SECTION:
					case SCAPE_NODE:
					case TEXT_NODE:
					case UNORDERED_LIST:
					case UNFORMATTED_TEXT:
					case WIKI:
					case POUND:
					case STAR:
					case EQUAL:
					case PIPE:
					case ITAL:
					case LINK_OPEN:
					case IMAGE_OPEN:
					case NOWIKI_OPEN:
					case EXTENSION:
					case FORCED_LINEBREAK:
					case ESCAPE:
					case NOWIKI_BLOCK_CLOSE:
					case NOWIKI_CLOSE:
					case LINK_CLOSE:
					case IMAGE_CLOSE:
					case BLANKS:
					case DASH:
					case CR:
					case LF:
					case SPACE:
					case TABULATOR:
					case COLON_SLASH:
					case SLASH:
					case INSIGNIFICANT_CHAR:
					case 40:
					case 41:
					case 42:
					case 43:
					case 44:
					case 45:
					case 46:
					case 47:
					case 48:
					case 49:
					case 50:
					case 51:
					case 52:
					case 53:
					case 54:
					case 55:
					case 56:
					case 57:
					case 58:
					case 59:
					case 61:
					case 62:
					case 64:
					case 65:
					case 66:
					case 67:
					case 68:
					case 69:
					case 70:
					case 71:
					case 72:
					case 73:
					case 74:
					case 75:
						{
						alt98=2;
						}
						break;
					default:
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 22, input);

						throw nvae;
					}

					}
					break;
				case FORCED_END_OF_LINE:
				case HEADING_SECTION:
				case HORIZONTAL_SECTION:
				case LIST_ITEM:
				case LIST_ITEM_PART:
				case NOWIKI_SECTION:
				case SCAPE_NODE:
				case TEXT_NODE:
				case UNORDERED_LIST:
				case UNFORMATTED_TEXT:
				case WIKI:
				case POUND:
				case STAR:
				case EQUAL:
				case PIPE:
				case ITAL:
				case LINK_OPEN:
				case IMAGE_OPEN:
				case NOWIKI_OPEN:
				case EXTENSION:
				case FORCED_LINEBREAK:
				case ESCAPE:
				case NOWIKI_BLOCK_CLOSE:
				case NOWIKI_CLOSE:
				case LINK_CLOSE:
				case IMAGE_CLOSE:
				case BLANKS:
				case DASH:
				case CR:
				case LF:
				case SPACE:
				case TABULATOR:
				case COLON_SLASH:
				case SLASH:
				case INSIGNIFICANT_CHAR:
				case 40:
				case 41:
				case 42:
				case 43:
				case 45:
				case 46:
				case 47:
				case 48:
				case 49:
				case 50:
				case 51:
				case 52:
				case 53:
				case 54:
				case 56:
				case 57:
				case 58:
				case 59:
				case 60:
				case 61:
				case 62:
				case 63:
				case 64:
				case 65:
				case 66:
				case 67:
				case 68:
				case 69:
				case 70:
				case 71:
				case 72:
				case 73:
				case 74:
				case 75:
					{
					alt98=2;
					}
					break;
				default:
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 6, input);

					throw nvae;
				}

				}
				break;
			case 65:
				{
				switch ( input.LA(2) ) {
				case 68:
					{
					int LA98_23 = input.LA(3);

					if ( (LA98_23==60) ) {
						int LA98_42 = input.LA(4);

						if ( (LA98_42==64) ) {
							int LA98_62 = input.LA(5);

							if ( (LA98_62==60) ) {
								int LA98_81 = input.LA(6);

								if ( (LA98_81==40) ) {
									int LA98_34 = input.LA(7);

									if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
										alt98=1;
									}
									else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
										alt98=2;
									}
									else {
										if (backtracking>0) {failed=true; return link;}
										NoViableAltException nvae =
											new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

										throw nvae;
									}
								}
								else if ( ((LA98_81>=FORCED_END_OF_LINE && LA98_81<=WIKI)||(LA98_81>=POUND && LA98_81<=INSIGNIFICANT_CHAR)||(LA98_81>=41 && LA98_81<=75)) ) {
									alt98=2;
								}
								else {
									if (backtracking>0) {failed=true; return link;}
									NoViableAltException nvae =
										new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 81, input);

									throw nvae;
								}
							}
							else if ( ((LA98_62>=FORCED_END_OF_LINE && LA98_62<=WIKI)||(LA98_62>=POUND && LA98_62<=59)||(LA98_62>=61 && LA98_62<=75)) ) {
								alt98=2;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
									new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 62, input);

								throw nvae;
							}
						}
						else if ( ((LA98_42>=FORCED_END_OF_LINE && LA98_42<=WIKI)||(LA98_42>=POUND && LA98_42<=63)||(LA98_42>=65 && LA98_42<=75)) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 42, input);

							throw nvae;
						}
					}
					else if ( ((LA98_23>=FORCED_END_OF_LINE && LA98_23<=WIKI)||(LA98_23>=POUND && LA98_23<=59)||(LA98_23>=61 && LA98_23<=75)) ) {
						alt98=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 23, input);

						throw nvae;
					}
					}
					break;
				case 63:
					{
					int LA98_24 = input.LA(3);

					if ( (LA98_24==63) ) {
						int LA98_43 = input.LA(4);

						if ( (LA98_43==66) ) {
							int LA98_63 = input.LA(5);

							if ( (LA98_63==46) ) {
								int LA98_82 = input.LA(6);

								if ( (LA98_82==67) ) {
									int LA98_100 = input.LA(7);

									if ( (LA98_100==55) ) {
										int LA98_114 = input.LA(8);

										if ( (LA98_114==40) ) {
											int LA98_34 = input.LA(9);

											if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
												alt98=1;
											}
											else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
												alt98=2;
											}
											else {
												if (backtracking>0) {failed=true; return link;}
												NoViableAltException nvae =
													new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

												throw nvae;
											}
										}
										else if ( ((LA98_114>=FORCED_END_OF_LINE && LA98_114<=WIKI)||(LA98_114>=POUND && LA98_114<=INSIGNIFICANT_CHAR)||(LA98_114>=41 && LA98_114<=75)) ) {
											alt98=2;
										}
										else {
											if (backtracking>0) {failed=true; return link;}
											NoViableAltException nvae =
												new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 114, input);

											throw nvae;
										}
									}
									else if ( ((LA98_100>=FORCED_END_OF_LINE && LA98_100<=WIKI)||(LA98_100>=POUND && LA98_100<=54)||(LA98_100>=56 && LA98_100<=75)) ) {
										alt98=2;
									}
									else {
										if (backtracking>0) {failed=true; return link;}
										NoViableAltException nvae =
											new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 100, input);

										throw nvae;
									}
								}
								else if ( ((LA98_82>=FORCED_END_OF_LINE && LA98_82<=WIKI)||(LA98_82>=POUND && LA98_82<=66)||(LA98_82>=68 && LA98_82<=75)) ) {
									alt98=2;
								}
								else {
									if (backtracking>0) {failed=true; return link;}
									NoViableAltException nvae =
										new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 82, input);

									throw nvae;
								}
							}
							else if ( ((LA98_63>=FORCED_END_OF_LINE && LA98_63<=WIKI)||(LA98_63>=POUND && LA98_63<=45)||(LA98_63>=47 && LA98_63<=75)) ) {
								alt98=2;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
									new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 63, input);

								throw nvae;
							}
						}
						else if ( ((LA98_43>=FORCED_END_OF_LINE && LA98_43<=WIKI)||(LA98_43>=POUND && LA98_43<=65)||(LA98_43>=67 && LA98_43<=75)) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 43, input);

							throw nvae;
						}
					}
					else if ( ((LA98_24>=FORCED_END_OF_LINE && LA98_24<=WIKI)||(LA98_24>=POUND && LA98_24<=62)||(LA98_24>=64 && LA98_24<=75)) ) {
						alt98=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 24, input);

						throw nvae;
					}
					}
					break;
				case FORCED_END_OF_LINE:
				case HEADING_SECTION:
				case HORIZONTAL_SECTION:
				case LIST_ITEM:
				case LIST_ITEM_PART:
				case NOWIKI_SECTION:
				case SCAPE_NODE:
				case TEXT_NODE:
				case UNORDERED_LIST:
				case UNFORMATTED_TEXT:
				case WIKI:
				case POUND:
				case STAR:
				case EQUAL:
				case PIPE:
				case ITAL:
				case LINK_OPEN:
				case IMAGE_OPEN:
				case NOWIKI_OPEN:
				case EXTENSION:
				case FORCED_LINEBREAK:
				case ESCAPE:
				case NOWIKI_BLOCK_CLOSE:
				case NOWIKI_CLOSE:
				case LINK_CLOSE:
				case IMAGE_CLOSE:
				case BLANKS:
				case DASH:
				case CR:
				case LF:
				case SPACE:
				case TABULATOR:
				case COLON_SLASH:
				case SLASH:
				case INSIGNIFICANT_CHAR:
				case 40:
				case 41:
				case 42:
				case 43:
				case 44:
				case 45:
				case 46:
				case 47:
				case 48:
				case 49:
				case 50:
				case 51:
				case 52:
				case 53:
				case 54:
				case 55:
				case 56:
				case 57:
				case 58:
				case 59:
				case 60:
				case 61:
				case 62:
				case 64:
				case 65:
				case 66:
				case 67:
				case 69:
				case 70:
				case 71:
				case 72:
				case 73:
				case 74:
				case 75:
					{
					alt98=2;
					}
					break;
				default:
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 7, input);

					throw nvae;
				}

				}
				break;
			case 58:
				{
				switch ( input.LA(2) ) {
				case 66:
					{
					int LA98_25 = input.LA(3);

					if ( (LA98_25==47) ) {
						int LA98_44 = input.LA(4);

						if ( (LA98_44==48) ) {
							int LA98_64 = input.LA(5);

							if ( (LA98_64==45) ) {
								int LA98_83 = input.LA(6);

								if ( (LA98_83==48) ) {
									int LA98_101 = input.LA(7);

									if ( ((LA98_101>=FORCED_END_OF_LINE && LA98_101<=WIKI)||(LA98_101>=POUND && LA98_101<=INSIGNIFICANT_CHAR)||(LA98_101>=41 && LA98_101<=75)) ) {
										alt98=2;
									}
									else if ( (LA98_101==40) ) {
										int LA98_34 = input.LA(8);

										if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
											alt98=1;
										}
										else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
											alt98=2;
										}
										else {
											if (backtracking>0) {failed=true; return link;}
											NoViableAltException nvae =
												new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

											throw nvae;
										}
									}
									else {
										if (backtracking>0) {failed=true; return link;}
										NoViableAltException nvae =
											new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 101, input);

										throw nvae;
									}
								}
								else if ( ((LA98_83>=FORCED_END_OF_LINE && LA98_83<=WIKI)||(LA98_83>=POUND && LA98_83<=47)||(LA98_83>=49 && LA98_83<=75)) ) {
									alt98=2;
								}
								else {
									if (backtracking>0) {failed=true; return link;}
									NoViableAltException nvae =
										new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 83, input);

									throw nvae;
								}
							}
							else if ( ((LA98_64>=FORCED_END_OF_LINE && LA98_64<=WIKI)||(LA98_64>=POUND && LA98_64<=44)||(LA98_64>=46 && LA98_64<=75)) ) {
								alt98=2;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
									new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 64, input);

								throw nvae;
							}
						}
						else if ( ((LA98_44>=FORCED_END_OF_LINE && LA98_44<=WIKI)||(LA98_44>=POUND && LA98_44<=47)||(LA98_44>=49 && LA98_44<=75)) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 44, input);

							throw nvae;
						}
					}
					else if ( ((LA98_25>=FORCED_END_OF_LINE && LA98_25<=WIKI)||(LA98_25>=POUND && LA98_25<=46)||(LA98_25>=48 && LA98_25<=75)) ) {
						alt98=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 25, input);

						throw nvae;
					}
					}
					break;
				case 46:
					{
					switch ( input.LA(3) ) {
					case 52:
						{
						int LA98_45 = input.LA(4);

						if ( (LA98_45==69) ) {
							int LA98_65 = input.LA(5);

							if ( (LA98_65==50) ) {
								int LA98_84 = input.LA(6);

								if ( (LA98_84==55) ) {
									int LA98_102 = input.LA(7);

									if ( (LA98_102==47) ) {
										int LA98_115 = input.LA(8);

										if ( (LA98_115==48) ) {
											int LA98_124 = input.LA(9);

											if ( (LA98_124==45) ) {
												int LA98_130 = input.LA(10);

												if ( (LA98_130==48) ) {
													int LA98_133 = input.LA(11);

													if ( (LA98_133==40) ) {
														int LA98_34 = input.LA(12);

														if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
															alt98=1;
														}
														else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
															alt98=2;
														}
														else {
															if (backtracking>0) {failed=true; return link;}
															NoViableAltException nvae =
																new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

															throw nvae;
														}
													}
													else if ( ((LA98_133>=FORCED_END_OF_LINE && LA98_133<=WIKI)||(LA98_133>=POUND && LA98_133<=INSIGNIFICANT_CHAR)||(LA98_133>=41 && LA98_133<=75)) ) {
														alt98=2;
													}
													else {
														if (backtracking>0) {failed=true; return link;}
														NoViableAltException nvae =
															new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 133, input);

														throw nvae;
													}
												}
												else if ( ((LA98_130>=FORCED_END_OF_LINE && LA98_130<=WIKI)||(LA98_130>=POUND && LA98_130<=47)||(LA98_130>=49 && LA98_130<=75)) ) {
													alt98=2;
												}
												else {
													if (backtracking>0) {failed=true; return link;}
													NoViableAltException nvae =
														new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 130, input);

													throw nvae;
												}
											}
											else if ( ((LA98_124>=FORCED_END_OF_LINE && LA98_124<=WIKI)||(LA98_124>=POUND && LA98_124<=44)||(LA98_124>=46 && LA98_124<=75)) ) {
												alt98=2;
											}
											else {
												if (backtracking>0) {failed=true; return link;}
												NoViableAltException nvae =
													new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 124, input);

												throw nvae;
											}
										}
										else if ( ((LA98_115>=FORCED_END_OF_LINE && LA98_115<=WIKI)||(LA98_115>=POUND && LA98_115<=47)||(LA98_115>=49 && LA98_115<=75)) ) {
											alt98=2;
										}
										else {
											if (backtracking>0) {failed=true; return link;}
											NoViableAltException nvae =
												new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 115, input);

											throw nvae;
										}
									}
									else if ( ((LA98_102>=FORCED_END_OF_LINE && LA98_102<=WIKI)||(LA98_102>=POUND && LA98_102<=46)||(LA98_102>=48 && LA98_102<=75)) ) {
										alt98=2;
									}
									else {
										if (backtracking>0) {failed=true; return link;}
										NoViableAltException nvae =
											new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 102, input);

										throw nvae;
									}
								}
								else if ( ((LA98_84>=FORCED_END_OF_LINE && LA98_84<=WIKI)||(LA98_84>=POUND && LA98_84<=54)||(LA98_84>=56 && LA98_84<=75)) ) {
									alt98=2;
								}
								else {
									if (backtracking>0) {failed=true; return link;}
									NoViableAltException nvae =
										new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 84, input);

									throw nvae;
								}
							}
							else if ( ((LA98_65>=FORCED_END_OF_LINE && LA98_65<=WIKI)||(LA98_65>=POUND && LA98_65<=49)||(LA98_65>=51 && LA98_65<=75)) ) {
								alt98=2;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
									new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 65, input);

								throw nvae;
							}
						}
						else if ( ((LA98_45>=FORCED_END_OF_LINE && LA98_45<=WIKI)||(LA98_45>=POUND && LA98_45<=68)||(LA98_45>=70 && LA98_45<=75)) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 45, input);

							throw nvae;
						}
						}
						break;
					case 45:
						{
						int LA98_46 = input.LA(4);

						if ( (LA98_46==48) ) {
							int LA98_66 = input.LA(5);

							if ( (LA98_66==47) ) {
								int LA98_85 = input.LA(6);

								if ( (LA98_85==48) ) {
									int LA98_103 = input.LA(7);

									if ( (LA98_103==45) ) {
										int LA98_116 = input.LA(8);

										if ( (LA98_116==48) ) {
											int LA98_125 = input.LA(9);

											if ( ((LA98_125>=FORCED_END_OF_LINE && LA98_125<=WIKI)||(LA98_125>=POUND && LA98_125<=INSIGNIFICANT_CHAR)||(LA98_125>=41 && LA98_125<=75)) ) {
												alt98=2;
											}
											else if ( (LA98_125==40) ) {
												int LA98_34 = input.LA(10);

												if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
													alt98=1;
												}
												else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
													alt98=2;
												}
												else {
													if (backtracking>0) {failed=true; return link;}
													NoViableAltException nvae =
														new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

													throw nvae;
												}
											}
											else {
												if (backtracking>0) {failed=true; return link;}
												NoViableAltException nvae =
													new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 125, input);

												throw nvae;
											}
										}
										else if ( ((LA98_116>=FORCED_END_OF_LINE && LA98_116<=WIKI)||(LA98_116>=POUND && LA98_116<=47)||(LA98_116>=49 && LA98_116<=75)) ) {
											alt98=2;
										}
										else {
											if (backtracking>0) {failed=true; return link;}
											NoViableAltException nvae =
												new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 116, input);

											throw nvae;
										}
									}
									else if ( ((LA98_103>=FORCED_END_OF_LINE && LA98_103<=WIKI)||(LA98_103>=POUND && LA98_103<=44)||(LA98_103>=46 && LA98_103<=75)) ) {
										alt98=2;
									}
									else {
										if (backtracking>0) {failed=true; return link;}
										NoViableAltException nvae =
											new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 103, input);

										throw nvae;
									}
								}
								else if ( ((LA98_85>=FORCED_END_OF_LINE && LA98_85<=WIKI)||(LA98_85>=POUND && LA98_85<=47)||(LA98_85>=49 && LA98_85<=75)) ) {
									alt98=2;
								}
								else {
									if (backtracking>0) {failed=true; return link;}
									NoViableAltException nvae =
										new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 85, input);

									throw nvae;
								}
							}
							else if ( ((LA98_66>=FORCED_END_OF_LINE && LA98_66<=WIKI)||(LA98_66>=POUND && LA98_66<=46)||(LA98_66>=48 && LA98_66<=75)) ) {
								alt98=2;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
									new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 66, input);

								throw nvae;
							}
						}
						else if ( ((LA98_46>=FORCED_END_OF_LINE && LA98_46<=WIKI)||(LA98_46>=POUND && LA98_46<=47)||(LA98_46>=49 && LA98_46<=75)) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 46, input);

							throw nvae;
						}
						}
						break;
					case FORCED_END_OF_LINE:
					case HEADING_SECTION:
					case HORIZONTAL_SECTION:
					case LIST_ITEM:
					case LIST_ITEM_PART:
					case NOWIKI_SECTION:
					case SCAPE_NODE:
					case TEXT_NODE:
					case UNORDERED_LIST:
					case UNFORMATTED_TEXT:
					case WIKI:
					case POUND:
					case STAR:
					case EQUAL:
					case PIPE:
					case ITAL:
					case LINK_OPEN:
					case IMAGE_OPEN:
					case NOWIKI_OPEN:
					case EXTENSION:
					case FORCED_LINEBREAK:
					case ESCAPE:
					case NOWIKI_BLOCK_CLOSE:
					case NOWIKI_CLOSE:
					case LINK_CLOSE:
					case IMAGE_CLOSE:
					case BLANKS:
					case DASH:
					case CR:
					case LF:
					case SPACE:
					case TABULATOR:
					case COLON_SLASH:
					case SLASH:
					case INSIGNIFICANT_CHAR:
					case 40:
					case 41:
					case 42:
					case 43:
					case 44:
					case 46:
					case 47:
					case 48:
					case 49:
					case 50:
					case 51:
					case 53:
					case 54:
					case 55:
					case 56:
					case 57:
					case 58:
					case 59:
					case 60:
					case 61:
					case 62:
					case 63:
					case 64:
					case 65:
					case 66:
					case 67:
					case 68:
					case 69:
					case 70:
					case 71:
					case 72:
					case 73:
					case 74:
					case 75:
						{
						alt98=2;
						}
						break;
					default:
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 26, input);

						throw nvae;
					}

					}
					break;
				case FORCED_END_OF_LINE:
				case HEADING_SECTION:
				case HORIZONTAL_SECTION:
				case LIST_ITEM:
				case LIST_ITEM_PART:
				case NOWIKI_SECTION:
				case SCAPE_NODE:
				case TEXT_NODE:
				case UNORDERED_LIST:
				case UNFORMATTED_TEXT:
				case WIKI:
				case POUND:
				case STAR:
				case EQUAL:
				case PIPE:
				case ITAL:
				case LINK_OPEN:
				case IMAGE_OPEN:
				case NOWIKI_OPEN:
				case EXTENSION:
				case FORCED_LINEBREAK:
				case ESCAPE:
				case NOWIKI_BLOCK_CLOSE:
				case NOWIKI_CLOSE:
				case LINK_CLOSE:
				case IMAGE_CLOSE:
				case BLANKS:
				case DASH:
				case CR:
				case LF:
				case SPACE:
				case TABULATOR:
				case COLON_SLASH:
				case SLASH:
				case INSIGNIFICANT_CHAR:
				case 40:
				case 41:
				case 42:
				case 43:
				case 44:
				case 45:
				case 47:
				case 48:
				case 49:
				case 50:
				case 51:
				case 52:
				case 53:
				case 54:
				case 55:
				case 56:
				case 57:
				case 58:
				case 59:
				case 60:
				case 61:
				case 62:
				case 63:
				case 64:
				case 65:
				case 67:
				case 68:
				case 69:
				case 70:
				case 71:
				case 72:
				case 73:
				case 74:
				case 75:
					{
					alt98=2;
					}
					break;
				default:
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 8, input);

					throw nvae;
				}

				}
				break;
			case 70:
				{
				int LA98_9 = input.LA(2);

				if ( (LA98_9==60) ) {
					int LA98_27 = input.LA(3);

					if ( (LA98_27==63) ) {
						int LA98_47 = input.LA(4);

						if ( (LA98_47==55) ) {
							int LA98_67 = input.LA(5);

							if ( (LA98_67==44) ) {
								int LA98_86 = input.LA(6);

								if ( (LA98_86==71) ) {
									int LA98_104 = input.LA(7);

									if ( ((LA98_104>=FORCED_END_OF_LINE && LA98_104<=WIKI)||(LA98_104>=POUND && LA98_104<=INSIGNIFICANT_CHAR)||(LA98_104>=41 && LA98_104<=75)) ) {
										alt98=2;
									}
									else if ( (LA98_104==40) ) {
										int LA98_34 = input.LA(8);

										if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
											alt98=1;
										}
										else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
											alt98=2;
										}
										else {
											if (backtracking>0) {failed=true; return link;}
											NoViableAltException nvae =
												new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

											throw nvae;
										}
									}
									else {
										if (backtracking>0) {failed=true; return link;}
										NoViableAltException nvae =
											new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 104, input);

										throw nvae;
									}
								}
								else if ( ((LA98_86>=FORCED_END_OF_LINE && LA98_86<=WIKI)||(LA98_86>=POUND && LA98_86<=70)||(LA98_86>=72 && LA98_86<=75)) ) {
									alt98=2;
								}
								else {
									if (backtracking>0) {failed=true; return link;}
									NoViableAltException nvae =
										new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 86, input);

									throw nvae;
								}
							}
							else if ( ((LA98_67>=FORCED_END_OF_LINE && LA98_67<=WIKI)||(LA98_67>=POUND && LA98_67<=43)||(LA98_67>=45 && LA98_67<=75)) ) {
								alt98=2;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
									new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 67, input);

								throw nvae;
							}
						}
						else if ( ((LA98_47>=FORCED_END_OF_LINE && LA98_47<=WIKI)||(LA98_47>=POUND && LA98_47<=54)||(LA98_47>=56 && LA98_47<=75)) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 47, input);

							throw nvae;
						}
					}
					else if ( ((LA98_27>=FORCED_END_OF_LINE && LA98_27<=WIKI)||(LA98_27>=POUND && LA98_27<=62)||(LA98_27>=64 && LA98_27<=75)) ) {
						alt98=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 27, input);

						throw nvae;
					}
				}
				else if ( ((LA98_9>=FORCED_END_OF_LINE && LA98_9<=WIKI)||(LA98_9>=POUND && LA98_9<=59)||(LA98_9>=61 && LA98_9<=75)) ) {
					alt98=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 9, input);

					throw nvae;
				}
				}
				break;
			case 57:
				{
				int LA98_10 = input.LA(2);

				if ( (LA98_10==64) ) {
					int LA98_28 = input.LA(3);

					if ( (LA98_28==48) ) {
						int LA98_48 = input.LA(4);

						if ( (LA98_48==69) ) {
							int LA98_68 = input.LA(5);

							if ( (LA98_68==57) ) {
								int LA98_87 = input.LA(6);

								if ( (LA98_87==64) ) {
									int LA98_105 = input.LA(7);

									if ( (LA98_105==60) ) {
										int LA98_117 = input.LA(8);

										if ( (LA98_117==69) ) {
											int LA98_126 = input.LA(9);

											if ( (LA98_126==40) ) {
												int LA98_34 = input.LA(10);

												if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
													alt98=1;
												}
												else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
													alt98=2;
												}
												else {
													if (backtracking>0) {failed=true; return link;}
													NoViableAltException nvae =
														new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

													throw nvae;
												}
											}
											else if ( ((LA98_126>=FORCED_END_OF_LINE && LA98_126<=WIKI)||(LA98_126>=POUND && LA98_126<=INSIGNIFICANT_CHAR)||(LA98_126>=41 && LA98_126<=75)) ) {
												alt98=2;
											}
											else {
												if (backtracking>0) {failed=true; return link;}
												NoViableAltException nvae =
													new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 126, input);

												throw nvae;
											}
										}
										else if ( ((LA98_117>=FORCED_END_OF_LINE && LA98_117<=WIKI)||(LA98_117>=POUND && LA98_117<=68)||(LA98_117>=70 && LA98_117<=75)) ) {
											alt98=2;
										}
										else {
											if (backtracking>0) {failed=true; return link;}
											NoViableAltException nvae =
												new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 117, input);

											throw nvae;
										}
									}
									else if ( ((LA98_105>=FORCED_END_OF_LINE && LA98_105<=WIKI)||(LA98_105>=POUND && LA98_105<=59)||(LA98_105>=61 && LA98_105<=75)) ) {
										alt98=2;
									}
									else {
										if (backtracking>0) {failed=true; return link;}
										NoViableAltException nvae =
											new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 105, input);

										throw nvae;
									}
								}
								else if ( ((LA98_87>=FORCED_END_OF_LINE && LA98_87<=WIKI)||(LA98_87>=POUND && LA98_87<=63)||(LA98_87>=65 && LA98_87<=75)) ) {
									alt98=2;
								}
								else {
									if (backtracking>0) {failed=true; return link;}
									NoViableAltException nvae =
										new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 87, input);

									throw nvae;
								}
							}
							else if ( ((LA98_68>=FORCED_END_OF_LINE && LA98_68<=WIKI)||(LA98_68>=POUND && LA98_68<=56)||(LA98_68>=58 && LA98_68<=75)) ) {
								alt98=2;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
									new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 68, input);

								throw nvae;
							}
						}
						else if ( ((LA98_48>=FORCED_END_OF_LINE && LA98_48<=WIKI)||(LA98_48>=POUND && LA98_48<=68)||(LA98_48>=70 && LA98_48<=75)) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 48, input);

							throw nvae;
						}
					}
					else if ( ((LA98_28>=FORCED_END_OF_LINE && LA98_28<=WIKI)||(LA98_28>=POUND && LA98_28<=47)||(LA98_28>=49 && LA98_28<=75)) ) {
						alt98=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 28, input);

						throw nvae;
					}
				}
				else if ( ((LA98_10>=FORCED_END_OF_LINE && LA98_10<=WIKI)||(LA98_10>=POUND && LA98_10<=63)||(LA98_10>=65 && LA98_10<=75)) ) {
					alt98=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 10, input);

					throw nvae;
				}
				}
				break;
			case 72:
				{
				switch ( input.LA(2) ) {
				case 48:
					{
					int LA98_29 = input.LA(3);

					if ( (LA98_29==63) ) {
						int LA98_49 = input.LA(4);

						if ( (LA98_49==63) ) {
							int LA98_69 = input.LA(5);

							if ( (LA98_69==50) ) {
								int LA98_88 = input.LA(6);

								if ( (LA98_88==73) ) {
									int LA98_106 = input.LA(7);

									if ( (LA98_106==47) ) {
										int LA98_118 = input.LA(8);

										if ( (LA98_118==48) ) {
											int LA98_127 = input.LA(9);

											if ( (LA98_127==45) ) {
												int LA98_131 = input.LA(10);

												if ( (LA98_131==48) ) {
													int LA98_134 = input.LA(11);

													if ( (LA98_134==40) ) {
														int LA98_34 = input.LA(12);

														if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
															alt98=1;
														}
														else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
															alt98=2;
														}
														else {
															if (backtracking>0) {failed=true; return link;}
															NoViableAltException nvae =
																new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

															throw nvae;
														}
													}
													else if ( ((LA98_134>=FORCED_END_OF_LINE && LA98_134<=WIKI)||(LA98_134>=POUND && LA98_134<=INSIGNIFICANT_CHAR)||(LA98_134>=41 && LA98_134<=75)) ) {
														alt98=2;
													}
													else {
														if (backtracking>0) {failed=true; return link;}
														NoViableAltException nvae =
															new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 134, input);

														throw nvae;
													}
												}
												else if ( ((LA98_131>=FORCED_END_OF_LINE && LA98_131<=WIKI)||(LA98_131>=POUND && LA98_131<=47)||(LA98_131>=49 && LA98_131<=75)) ) {
													alt98=2;
												}
												else {
													if (backtracking>0) {failed=true; return link;}
													NoViableAltException nvae =
														new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 131, input);

													throw nvae;
												}
											}
											else if ( ((LA98_127>=FORCED_END_OF_LINE && LA98_127<=WIKI)||(LA98_127>=POUND && LA98_127<=44)||(LA98_127>=46 && LA98_127<=75)) ) {
												alt98=2;
											}
											else {
												if (backtracking>0) {failed=true; return link;}
												NoViableAltException nvae =
													new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 127, input);

												throw nvae;
											}
										}
										else if ( ((LA98_118>=FORCED_END_OF_LINE && LA98_118<=WIKI)||(LA98_118>=POUND && LA98_118<=47)||(LA98_118>=49 && LA98_118<=75)) ) {
											alt98=2;
										}
										else {
											if (backtracking>0) {failed=true; return link;}
											NoViableAltException nvae =
												new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 118, input);

											throw nvae;
										}
									}
									else if ( ((LA98_106>=FORCED_END_OF_LINE && LA98_106<=WIKI)||(LA98_106>=POUND && LA98_106<=46)||(LA98_106>=48 && LA98_106<=75)) ) {
										alt98=2;
									}
									else {
										if (backtracking>0) {failed=true; return link;}
										NoViableAltException nvae =
											new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 106, input);

										throw nvae;
									}
								}
								else if ( ((LA98_88>=FORCED_END_OF_LINE && LA98_88<=WIKI)||(LA98_88>=POUND && LA98_88<=72)||(LA98_88>=74 && LA98_88<=75)) ) {
									alt98=2;
								}
								else {
									if (backtracking>0) {failed=true; return link;}
									NoViableAltException nvae =
										new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 88, input);

									throw nvae;
								}
							}
							else if ( ((LA98_69>=FORCED_END_OF_LINE && LA98_69<=WIKI)||(LA98_69>=POUND && LA98_69<=49)||(LA98_69>=51 && LA98_69<=75)) ) {
								alt98=2;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
									new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 69, input);

								throw nvae;
							}
						}
						else if ( ((LA98_49>=FORCED_END_OF_LINE && LA98_49<=WIKI)||(LA98_49>=POUND && LA98_49<=62)||(LA98_49>=64 && LA98_49<=75)) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 49, input);

							throw nvae;
						}
					}
					else if ( ((LA98_29>=FORCED_END_OF_LINE && LA98_29<=WIKI)||(LA98_29>=POUND && LA98_29<=62)||(LA98_29>=64 && LA98_29<=75)) ) {
						alt98=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 29, input);

						throw nvae;
					}
					}
					break;
				case 47:
					{
					int LA98_30 = input.LA(3);

					if ( (LA98_30==48) ) {
						int LA98_50 = input.LA(4);

						if ( (LA98_50==45) ) {
							int LA98_70 = input.LA(5);

							if ( (LA98_70==48) ) {
								int LA98_89 = input.LA(6);

								if ( (LA98_89==40) ) {
									int LA98_34 = input.LA(7);

									if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
										alt98=1;
									}
									else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
										alt98=2;
									}
									else {
										if (backtracking>0) {failed=true; return link;}
										NoViableAltException nvae =
											new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

										throw nvae;
									}
								}
								else if ( ((LA98_89>=FORCED_END_OF_LINE && LA98_89<=WIKI)||(LA98_89>=POUND && LA98_89<=INSIGNIFICANT_CHAR)||(LA98_89>=41 && LA98_89<=75)) ) {
									alt98=2;
								}
								else {
									if (backtracking>0) {failed=true; return link;}
									NoViableAltException nvae =
										new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 89, input);

									throw nvae;
								}
							}
							else if ( ((LA98_70>=FORCED_END_OF_LINE && LA98_70<=WIKI)||(LA98_70>=POUND && LA98_70<=47)||(LA98_70>=49 && LA98_70<=75)) ) {
								alt98=2;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
									new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 70, input);

								throw nvae;
							}
						}
						else if ( ((LA98_50>=FORCED_END_OF_LINE && LA98_50<=WIKI)||(LA98_50>=POUND && LA98_50<=44)||(LA98_50>=46 && LA98_50<=75)) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 50, input);

							throw nvae;
						}
					}
					else if ( ((LA98_30>=FORCED_END_OF_LINE && LA98_30<=WIKI)||(LA98_30>=POUND && LA98_30<=47)||(LA98_30>=49 && LA98_30<=75)) ) {
						alt98=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 30, input);

						throw nvae;
					}
					}
					break;
				case FORCED_END_OF_LINE:
				case HEADING_SECTION:
				case HORIZONTAL_SECTION:
				case LIST_ITEM:
				case LIST_ITEM_PART:
				case NOWIKI_SECTION:
				case SCAPE_NODE:
				case TEXT_NODE:
				case UNORDERED_LIST:
				case UNFORMATTED_TEXT:
				case WIKI:
				case POUND:
				case STAR:
				case EQUAL:
				case PIPE:
				case ITAL:
				case LINK_OPEN:
				case IMAGE_OPEN:
				case NOWIKI_OPEN:
				case EXTENSION:
				case FORCED_LINEBREAK:
				case ESCAPE:
				case NOWIKI_BLOCK_CLOSE:
				case NOWIKI_CLOSE:
				case LINK_CLOSE:
				case IMAGE_CLOSE:
				case BLANKS:
				case DASH:
				case CR:
				case LF:
				case SPACE:
				case TABULATOR:
				case COLON_SLASH:
				case SLASH:
				case INSIGNIFICANT_CHAR:
				case 40:
				case 41:
				case 42:
				case 43:
				case 44:
				case 45:
				case 46:
				case 49:
				case 50:
				case 51:
				case 52:
				case 53:
				case 54:
				case 55:
				case 56:
				case 57:
				case 58:
				case 59:
				case 60:
				case 61:
				case 62:
				case 63:
				case 64:
				case 65:
				case 66:
				case 67:
				case 68:
				case 69:
				case 70:
				case 71:
				case 72:
				case 73:
				case 74:
				case 75:
					{
					alt98=2;
					}
					break;
				default:
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 11, input);

					throw nvae;
				}

				}
				break;
			case 74:
				{
				int LA98_12 = input.LA(2);

				if ( (LA98_12==67) ) {
					int LA98_31 = input.LA(3);

					if ( (LA98_31==55) ) {
						int LA98_51 = input.LA(4);

						if ( (LA98_51==66) ) {
							int LA98_71 = input.LA(5);

							if ( (LA98_71==44) ) {
								int LA98_90 = input.LA(6);

								if ( (LA98_90==63) ) {
									int LA98_107 = input.LA(7);

									if ( ((LA98_107>=FORCED_END_OF_LINE && LA98_107<=WIKI)||(LA98_107>=POUND && LA98_107<=INSIGNIFICANT_CHAR)||(LA98_107>=41 && LA98_107<=75)) ) {
										alt98=2;
									}
									else if ( (LA98_107==40) ) {
										int LA98_34 = input.LA(8);

										if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
											alt98=1;
										}
										else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
											alt98=2;
										}
										else {
											if (backtracking>0) {failed=true; return link;}
											NoViableAltException nvae =
												new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

											throw nvae;
										}
									}
									else {
										if (backtracking>0) {failed=true; return link;}
										NoViableAltException nvae =
											new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 107, input);

										throw nvae;
									}
								}
								else if ( ((LA98_90>=FORCED_END_OF_LINE && LA98_90<=WIKI)||(LA98_90>=POUND && LA98_90<=62)||(LA98_90>=64 && LA98_90<=75)) ) {
									alt98=2;
								}
								else {
									if (backtracking>0) {failed=true; return link;}
									NoViableAltException nvae =
										new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 90, input);

									throw nvae;
								}
							}
							else if ( ((LA98_71>=FORCED_END_OF_LINE && LA98_71<=WIKI)||(LA98_71>=POUND && LA98_71<=43)||(LA98_71>=45 && LA98_71<=75)) ) {
								alt98=2;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
									new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 71, input);

								throw nvae;
							}
						}
						else if ( ((LA98_51>=FORCED_END_OF_LINE && LA98_51<=WIKI)||(LA98_51>=POUND && LA98_51<=65)||(LA98_51>=67 && LA98_51<=75)) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 51, input);

							throw nvae;
						}
					}
					else if ( ((LA98_31>=FORCED_END_OF_LINE && LA98_31<=WIKI)||(LA98_31>=POUND && LA98_31<=54)||(LA98_31>=56 && LA98_31<=75)) ) {
						alt98=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 31, input);

						throw nvae;
					}
				}
				else if ( ((LA98_12>=FORCED_END_OF_LINE && LA98_12<=WIKI)||(LA98_12>=POUND && LA98_12<=66)||(LA98_12>=68 && LA98_12<=75)) ) {
					alt98=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 12, input);

					throw nvae;
				}
				}
				break;
			case 47:
				{
				int LA98_13 = input.LA(2);

				if ( (LA98_13==48) ) {
					int LA98_32 = input.LA(3);

					if ( (LA98_32==45) ) {
						int LA98_52 = input.LA(4);

						if ( (LA98_52==48) ) {
							int LA98_72 = input.LA(5);

							if ( (LA98_72==69) ) {
								int LA98_91 = input.LA(6);

								if ( (LA98_91==55) ) {
									int LA98_108 = input.LA(7);

									if ( (LA98_108==63) ) {
										int LA98_119 = input.LA(8);

										if ( (LA98_119==48) ) {
											int LA98_128 = input.LA(9);

											if ( (LA98_128==60) ) {
												int LA98_132 = input.LA(10);

												if ( (LA98_132==40) ) {
													int LA98_34 = input.LA(11);

													if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
														alt98=1;
													}
													else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
														alt98=2;
													}
													else {
														if (backtracking>0) {failed=true; return link;}
														NoViableAltException nvae =
															new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

														throw nvae;
													}
												}
												else if ( ((LA98_132>=FORCED_END_OF_LINE && LA98_132<=WIKI)||(LA98_132>=POUND && LA98_132<=INSIGNIFICANT_CHAR)||(LA98_132>=41 && LA98_132<=75)) ) {
													alt98=2;
												}
												else {
													if (backtracking>0) {failed=true; return link;}
													NoViableAltException nvae =
														new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 132, input);

													throw nvae;
												}
											}
											else if ( ((LA98_128>=FORCED_END_OF_LINE && LA98_128<=WIKI)||(LA98_128>=POUND && LA98_128<=59)||(LA98_128>=61 && LA98_128<=75)) ) {
												alt98=2;
											}
											else {
												if (backtracking>0) {failed=true; return link;}
												NoViableAltException nvae =
													new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 128, input);

												throw nvae;
											}
										}
										else if ( ((LA98_119>=FORCED_END_OF_LINE && LA98_119<=WIKI)||(LA98_119>=POUND && LA98_119<=47)||(LA98_119>=49 && LA98_119<=75)) ) {
											alt98=2;
										}
										else {
											if (backtracking>0) {failed=true; return link;}
											NoViableAltException nvae =
												new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 119, input);

											throw nvae;
										}
									}
									else if ( ((LA98_108>=FORCED_END_OF_LINE && LA98_108<=WIKI)||(LA98_108>=POUND && LA98_108<=62)||(LA98_108>=64 && LA98_108<=75)) ) {
										alt98=2;
									}
									else {
										if (backtracking>0) {failed=true; return link;}
										NoViableAltException nvae =
											new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 108, input);

										throw nvae;
									}
								}
								else if ( ((LA98_91>=FORCED_END_OF_LINE && LA98_91<=WIKI)||(LA98_91>=POUND && LA98_91<=54)||(LA98_91>=56 && LA98_91<=75)) ) {
									alt98=2;
								}
								else {
									if (backtracking>0) {failed=true; return link;}
									NoViableAltException nvae =
										new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 91, input);

									throw nvae;
								}
							}
							else if ( ((LA98_72>=FORCED_END_OF_LINE && LA98_72<=WIKI)||(LA98_72>=POUND && LA98_72<=68)||(LA98_72>=70 && LA98_72<=75)) ) {
								alt98=2;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
									new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 72, input);

								throw nvae;
							}
						}
						else if ( ((LA98_52>=FORCED_END_OF_LINE && LA98_52<=WIKI)||(LA98_52>=POUND && LA98_52<=47)||(LA98_52>=49 && LA98_52<=75)) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 52, input);

							throw nvae;
						}
					}
					else if ( ((LA98_32>=FORCED_END_OF_LINE && LA98_32<=WIKI)||(LA98_32>=POUND && LA98_32<=44)||(LA98_32>=46 && LA98_32<=75)) ) {
						alt98=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 32, input);

						throw nvae;
					}
				}
				else if ( ((LA98_13>=FORCED_END_OF_LINE && LA98_13<=WIKI)||(LA98_13>=POUND && LA98_13<=47)||(LA98_13>=49 && LA98_13<=75)) ) {
					alt98=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 13, input);

					throw nvae;
				}
				}
				break;
			case 75:
				{
				int LA98_14 = input.LA(2);

				if ( (LA98_14==47) ) {
					int LA98_33 = input.LA(3);

					if ( (LA98_33==48) ) {
						int LA98_53 = input.LA(4);

						if ( (LA98_53==45) ) {
							int LA98_73 = input.LA(5);

							if ( (LA98_73==48) ) {
								int LA98_92 = input.LA(6);

								if ( ((LA98_92>=FORCED_END_OF_LINE && LA98_92<=WIKI)||(LA98_92>=POUND && LA98_92<=INSIGNIFICANT_CHAR)||(LA98_92>=41 && LA98_92<=75)) ) {
									alt98=2;
								}
								else if ( (LA98_92==40) ) {
									int LA98_34 = input.LA(7);

									if ( ((LA98_34>=FORCED_END_OF_LINE && LA98_34<=WIKI)||(LA98_34>=POUND && LA98_34<=EQUAL)||(LA98_34>=ITAL && LA98_34<=NOWIKI_CLOSE)||(LA98_34>=IMAGE_CLOSE && LA98_34<=75)) ) {
										alt98=1;
									}
									else if ( (LA98_34==PIPE||LA98_34==LINK_CLOSE) ) {
										alt98=2;
									}
									else {
										if (backtracking>0) {failed=true; return link;}
										NoViableAltException nvae =
											new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 34, input);

										throw nvae;
									}
								}
								else {
									if (backtracking>0) {failed=true; return link;}
									NoViableAltException nvae =
										new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 92, input);

									throw nvae;
								}
							}
							else if ( ((LA98_73>=FORCED_END_OF_LINE && LA98_73<=WIKI)||(LA98_73>=POUND && LA98_73<=47)||(LA98_73>=49 && LA98_73<=75)) ) {
								alt98=2;
							}
							else {
								if (backtracking>0) {failed=true; return link;}
								NoViableAltException nvae =
									new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 73, input);

								throw nvae;
							}
						}
						else if ( ((LA98_53>=FORCED_END_OF_LINE && LA98_53<=WIKI)||(LA98_53>=POUND && LA98_53<=44)||(LA98_53>=46 && LA98_53<=75)) ) {
							alt98=2;
						}
						else {
							if (backtracking>0) {failed=true; return link;}
							NoViableAltException nvae =
								new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 53, input);

							throw nvae;
						}
					}
					else if ( ((LA98_33>=FORCED_END_OF_LINE && LA98_33<=WIKI)||(LA98_33>=POUND && LA98_33<=47)||(LA98_33>=49 && LA98_33<=75)) ) {
						alt98=2;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						NoViableAltException nvae =
							new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 33, input);

						throw nvae;
					}
				}
				else if ( ((LA98_14>=FORCED_END_OF_LINE && LA98_14<=WIKI)||(LA98_14>=POUND && LA98_14<=46)||(LA98_14>=48 && LA98_14<=75)) ) {
					alt98=2;
				}
				else {
					if (backtracking>0) {failed=true; return link;}
					NoViableAltException nvae =
						new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 14, input);

					throw nvae;
				}
				}
				break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case STAR:
			case EQUAL:
			case ITAL:
			case LINK_OPEN:
			case IMAGE_OPEN:
			case NOWIKI_OPEN:
			case EXTENSION:
			case FORCED_LINEBREAK:
			case ESCAPE:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case IMAGE_CLOSE:
			case BLANKS:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case INSIGNIFICANT_CHAR:
			case 40:
			case 42:
			case 44:
			case 45:
			case 46:
			case 48:
			case 50:
			case 51:
			case 52:
			case 54:
			case 55:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 66:
			case 67:
			case 68:
			case 69:
			case 71:
			case 73:
				{
				alt98=2;
				}
				break;
			default:
				if (backtracking>0) {failed=true; return link;}
				NoViableAltException nvae =
					new NoViableAltException("474:1: link_address returns [LinkNode link =null] : (li= link_interwiki_uri ':' p= link_interwiki_pagename | lu= link_uri );", 98, 0, input);

				throw nvae;
			}

			switch (alt98) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:475:4: li= link_interwiki_uri ':' p= link_interwiki_pagename
					{
					pushFollow(FOLLOW_link_interwiki_uri_in_link_address2861);
					li=link_interwiki_uri();
					_fsp--;
					if (failed) return link;
					match(input,40,FOLLOW_40_in_link_address2864); if (failed) return link;
					pushFollow(FOLLOW_link_interwiki_pagename_in_link_address2871);
					p=link_interwiki_pagename();
					_fsp--;
					if (failed) return link;
					if ( backtracking==0 ) {

					  						li.setUri(p);
					  						link = li;

					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:479:4: lu= link_uri
					{
					pushFollow(FOLLOW_link_uri_in_link_address2882);
					lu=link_uri();
					_fsp--;
					if (failed) return link;
					if ( backtracking==0 ) {
					  link = new LinkNode(lu);
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return link;
	}
	// $ANTLR end link_address

	// $ANTLR start link_interwiki_uri
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:481:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );
	public final InterwikiLinkNode link_interwiki_uri() throws RecognitionException {
		InterwikiLinkNode interwiki =  null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:482:2: ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' )
			int alt99=20;
			switch ( input.LA(1) ) {
			case 41:
				{
				alt99=1;
				}
				break;
			case 43:
				{
				alt99=2;
				}
				break;
			case 49:
				{
				alt99=3;
				}
				break;
			case 53:
				{
				alt99=4;
				}
				break;
			case 56:
				{
				alt99=5;
				}
				break;
			case 59:
				{
				int LA99_6 = input.LA(2);

				if ( (LA99_6==55) ) {
					int LA99_15 = input.LA(3);

					if ( (LA99_15==63) ) {
						alt99=7;
					}
					else if ( (LA99_15==60) ) {
						alt99=6;
					}
					else {
						if (backtracking>0) {failed=true; return interwiki;}
						NoViableAltException nvae =
							new NoViableAltException("481:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 99, 15, input);

						throw nvae;
					}
				}
				else if ( (LA99_6==44) ) {
					alt99=8;
				}
				else {
					if (backtracking>0) {failed=true; return interwiki;}
					NoViableAltException nvae =
						new NoViableAltException("481:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 99, 6, input);

					throw nvae;
				}
				}
				break;
			case 65:
				{
				int LA99_7 = input.LA(2);

				if ( (LA99_7==68) ) {
					alt99=10;
				}
				else if ( (LA99_7==63) ) {
					alt99=9;
				}
				else {
					if (backtracking>0) {failed=true; return interwiki;}
					NoViableAltException nvae =
						new NoViableAltException("481:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 99, 7, input);

					throw nvae;
				}
				}
				break;
			case 58:
				{
				int LA99_8 = input.LA(2);

				if ( (LA99_8==46) ) {
					int LA99_19 = input.LA(3);

					if ( (LA99_19==52) ) {
						alt99=13;
					}
					else if ( (LA99_19==45) ) {
						alt99=12;
					}
					else {
						if (backtracking>0) {failed=true; return interwiki;}
						NoViableAltException nvae =
							new NoViableAltException("481:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 99, 19, input);

						throw nvae;
					}
				}
				else if ( (LA99_8==66) ) {
					alt99=11;
				}
				else {
					if (backtracking>0) {failed=true; return interwiki;}
					NoViableAltException nvae =
						new NoViableAltException("481:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 99, 8, input);

					throw nvae;
				}
				}
				break;
			case 70:
				{
				alt99=14;
				}
				break;
			case 57:
				{
				alt99=15;
				}
				break;
			case 72:
				{
				int LA99_11 = input.LA(2);

				if ( (LA99_11==47) ) {
					alt99=17;
				}
				else if ( (LA99_11==48) ) {
					alt99=16;
				}
				else {
					if (backtracking>0) {failed=true; return interwiki;}
					NoViableAltException nvae =
						new NoViableAltException("481:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 99, 11, input);

					throw nvae;
				}
				}
				break;
			case 74:
				{
				alt99=18;
				}
				break;
			case 47:
				{
				alt99=19;
				}
				break;
			case 75:
				{
				alt99=20;
				}
				break;
			default:
				if (backtracking>0) {failed=true; return interwiki;}
				NoViableAltException nvae =
					new NoViableAltException("481:1: link_interwiki_uri returns [InterwikiLinkNode interwiki = null] : ( 'C' '2' | 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i' | 'F' 'l' 'i' 'c' 'k' 'r' | 'G' 'o' 'o' 'g' 'l' 'e' | 'J' 'S' 'P' 'W' 'i' 'k' 'i' | 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l' | 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i' | 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n' | 'O' 'd' 'd' 'm' 'u' 's' 'e' | 'O' 'h' 'a' 'n' 'a' | 'P' 'm' 'W' 'i' 'k' 'i' | 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i' | 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i' | 'R' 'a' 'd' 'e' 'o' 'x' | 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p' | 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i' | 'T' 'W' 'i' 'k' 'i' | 'U' 's' 'e' 'm' 'o' 'd' | 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a' | 'X' 'W' 'i' 'k' 'i' );", 99, 0, input);

				throw nvae;
			}

			switch (alt99) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:482:4: 'C' '2'
					{
					match(input,41,FOLLOW_41_in_link_interwiki_uri2898); if (failed) return interwiki;
					match(input,42,FOLLOW_42_in_link_interwiki_uri2900); if (failed) return interwiki;

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:483:4: 'D' 'o' 'k' 'u' 'W' 'i' 'k' 'i'
					{
					match(input,43,FOLLOW_43_in_link_interwiki_uri2905); if (failed) return interwiki;
					match(input,44,FOLLOW_44_in_link_interwiki_uri2907); if (failed) return interwiki;
					match(input,45,FOLLOW_45_in_link_interwiki_uri2909); if (failed) return interwiki;
					match(input,46,FOLLOW_46_in_link_interwiki_uri2911); if (failed) return interwiki;
					match(input,47,FOLLOW_47_in_link_interwiki_uri2913); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri2915); if (failed) return interwiki;
					match(input,45,FOLLOW_45_in_link_interwiki_uri2917); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri2919); if (failed) return interwiki;

					}
					break;
				case 3 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:484:4: 'F' 'l' 'i' 'c' 'k' 'r'
					{
					match(input,49,FOLLOW_49_in_link_interwiki_uri2924); if (failed) return interwiki;
					match(input,50,FOLLOW_50_in_link_interwiki_uri2926); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri2928); if (failed) return interwiki;
					match(input,51,FOLLOW_51_in_link_interwiki_uri2930); if (failed) return interwiki;
					match(input,45,FOLLOW_45_in_link_interwiki_uri2932); if (failed) return interwiki;
					match(input,52,FOLLOW_52_in_link_interwiki_uri2934); if (failed) return interwiki;

					}
					break;
				case 4 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:485:4: 'G' 'o' 'o' 'g' 'l' 'e'
					{
					match(input,53,FOLLOW_53_in_link_interwiki_uri2939); if (failed) return interwiki;
					match(input,44,FOLLOW_44_in_link_interwiki_uri2941); if (failed) return interwiki;
					match(input,44,FOLLOW_44_in_link_interwiki_uri2943); if (failed) return interwiki;
					match(input,54,FOLLOW_54_in_link_interwiki_uri2945); if (failed) return interwiki;
					match(input,50,FOLLOW_50_in_link_interwiki_uri2947); if (failed) return interwiki;
					match(input,55,FOLLOW_55_in_link_interwiki_uri2949); if (failed) return interwiki;

					}
					break;
				case 5 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:486:4: 'J' 'S' 'P' 'W' 'i' 'k' 'i'
					{
					match(input,56,FOLLOW_56_in_link_interwiki_uri2954); if (failed) return interwiki;
					match(input,57,FOLLOW_57_in_link_interwiki_uri2956); if (failed) return interwiki;
					match(input,58,FOLLOW_58_in_link_interwiki_uri2958); if (failed) return interwiki;
					match(input,47,FOLLOW_47_in_link_interwiki_uri2960); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri2962); if (failed) return interwiki;
					match(input,45,FOLLOW_45_in_link_interwiki_uri2964); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri2966); if (failed) return interwiki;

					}
					break;
				case 6 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:487:4: 'M' 'e' 'a' 't' 'b' 'a' 'l' 'l'
					{
					match(input,59,FOLLOW_59_in_link_interwiki_uri2971); if (failed) return interwiki;
					match(input,55,FOLLOW_55_in_link_interwiki_uri2973); if (failed) return interwiki;
					match(input,60,FOLLOW_60_in_link_interwiki_uri2975); if (failed) return interwiki;
					match(input,61,FOLLOW_61_in_link_interwiki_uri2977); if (failed) return interwiki;
					match(input,62,FOLLOW_62_in_link_interwiki_uri2979); if (failed) return interwiki;
					match(input,60,FOLLOW_60_in_link_interwiki_uri2981); if (failed) return interwiki;
					match(input,50,FOLLOW_50_in_link_interwiki_uri2983); if (failed) return interwiki;
					match(input,50,FOLLOW_50_in_link_interwiki_uri2985); if (failed) return interwiki;

					}
					break;
				case 7 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:488:4: 'M' 'e' 'd' 'i' 'a' 'W' 'i' 'k' 'i'
					{
					match(input,59,FOLLOW_59_in_link_interwiki_uri2990); if (failed) return interwiki;
					match(input,55,FOLLOW_55_in_link_interwiki_uri2992); if (failed) return interwiki;
					match(input,63,FOLLOW_63_in_link_interwiki_uri2994); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri2996); if (failed) return interwiki;
					match(input,60,FOLLOW_60_in_link_interwiki_uri2998); if (failed) return interwiki;
					match(input,47,FOLLOW_47_in_link_interwiki_uri3000); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3002); if (failed) return interwiki;
					match(input,45,FOLLOW_45_in_link_interwiki_uri3004); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3006); if (failed) return interwiki;

					}
					break;
				case 8 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:489:4: 'M' 'o' 'i' 'n' 'M' 'o' 'i' 'n'
					{
					match(input,59,FOLLOW_59_in_link_interwiki_uri3011); if (failed) return interwiki;
					match(input,44,FOLLOW_44_in_link_interwiki_uri3013); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3015); if (failed) return interwiki;
					match(input,64,FOLLOW_64_in_link_interwiki_uri3017); if (failed) return interwiki;
					match(input,59,FOLLOW_59_in_link_interwiki_uri3019); if (failed) return interwiki;
					match(input,44,FOLLOW_44_in_link_interwiki_uri3021); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3023); if (failed) return interwiki;
					match(input,64,FOLLOW_64_in_link_interwiki_uri3025); if (failed) return interwiki;

					}
					break;
				case 9 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:490:4: 'O' 'd' 'd' 'm' 'u' 's' 'e'
					{
					match(input,65,FOLLOW_65_in_link_interwiki_uri3030); if (failed) return interwiki;
					match(input,63,FOLLOW_63_in_link_interwiki_uri3032); if (failed) return interwiki;
					match(input,63,FOLLOW_63_in_link_interwiki_uri3034); if (failed) return interwiki;
					match(input,66,FOLLOW_66_in_link_interwiki_uri3036); if (failed) return interwiki;
					match(input,46,FOLLOW_46_in_link_interwiki_uri3038); if (failed) return interwiki;
					match(input,67,FOLLOW_67_in_link_interwiki_uri3040); if (failed) return interwiki;
					match(input,55,FOLLOW_55_in_link_interwiki_uri3042); if (failed) return interwiki;

					}
					break;
				case 10 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:491:4: 'O' 'h' 'a' 'n' 'a'
					{
					match(input,65,FOLLOW_65_in_link_interwiki_uri3047); if (failed) return interwiki;
					match(input,68,FOLLOW_68_in_link_interwiki_uri3049); if (failed) return interwiki;
					match(input,60,FOLLOW_60_in_link_interwiki_uri3051); if (failed) return interwiki;
					match(input,64,FOLLOW_64_in_link_interwiki_uri3053); if (failed) return interwiki;
					match(input,60,FOLLOW_60_in_link_interwiki_uri3055); if (failed) return interwiki;

					}
					break;
				case 11 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:492:4: 'P' 'm' 'W' 'i' 'k' 'i'
					{
					match(input,58,FOLLOW_58_in_link_interwiki_uri3060); if (failed) return interwiki;
					match(input,66,FOLLOW_66_in_link_interwiki_uri3062); if (failed) return interwiki;
					match(input,47,FOLLOW_47_in_link_interwiki_uri3064); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3066); if (failed) return interwiki;
					match(input,45,FOLLOW_45_in_link_interwiki_uri3068); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3070); if (failed) return interwiki;

					}
					break;
				case 12 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:493:4: 'P' 'u' 'k' 'i' 'W' 'i' 'k' 'i'
					{
					match(input,58,FOLLOW_58_in_link_interwiki_uri3075); if (failed) return interwiki;
					match(input,46,FOLLOW_46_in_link_interwiki_uri3077); if (failed) return interwiki;
					match(input,45,FOLLOW_45_in_link_interwiki_uri3079); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3081); if (failed) return interwiki;
					match(input,47,FOLLOW_47_in_link_interwiki_uri3083); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3085); if (failed) return interwiki;
					match(input,45,FOLLOW_45_in_link_interwiki_uri3087); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3089); if (failed) return interwiki;

					}
					break;
				case 13 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:494:4: 'P' 'u' 'r' 'p' 'l' 'e' 'W' 'i' 'k' 'i'
					{
					match(input,58,FOLLOW_58_in_link_interwiki_uri3094); if (failed) return interwiki;
					match(input,46,FOLLOW_46_in_link_interwiki_uri3096); if (failed) return interwiki;
					match(input,52,FOLLOW_52_in_link_interwiki_uri3098); if (failed) return interwiki;
					match(input,69,FOLLOW_69_in_link_interwiki_uri3100); if (failed) return interwiki;
					match(input,50,FOLLOW_50_in_link_interwiki_uri3102); if (failed) return interwiki;
					match(input,55,FOLLOW_55_in_link_interwiki_uri3104); if (failed) return interwiki;
					match(input,47,FOLLOW_47_in_link_interwiki_uri3106); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3108); if (failed) return interwiki;
					match(input,45,FOLLOW_45_in_link_interwiki_uri3110); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3112); if (failed) return interwiki;

					}
					break;
				case 14 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:495:4: 'R' 'a' 'd' 'e' 'o' 'x'
					{
					match(input,70,FOLLOW_70_in_link_interwiki_uri3117); if (failed) return interwiki;
					match(input,60,FOLLOW_60_in_link_interwiki_uri3119); if (failed) return interwiki;
					match(input,63,FOLLOW_63_in_link_interwiki_uri3121); if (failed) return interwiki;
					match(input,55,FOLLOW_55_in_link_interwiki_uri3123); if (failed) return interwiki;
					match(input,44,FOLLOW_44_in_link_interwiki_uri3125); if (failed) return interwiki;
					match(input,71,FOLLOW_71_in_link_interwiki_uri3127); if (failed) return interwiki;

					}
					break;
				case 15 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:496:4: 'S' 'n' 'i' 'p' 'S' 'n' 'a' 'p'
					{
					match(input,57,FOLLOW_57_in_link_interwiki_uri3132); if (failed) return interwiki;
					match(input,64,FOLLOW_64_in_link_interwiki_uri3134); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3136); if (failed) return interwiki;
					match(input,69,FOLLOW_69_in_link_interwiki_uri3138); if (failed) return interwiki;
					match(input,57,FOLLOW_57_in_link_interwiki_uri3140); if (failed) return interwiki;
					match(input,64,FOLLOW_64_in_link_interwiki_uri3142); if (failed) return interwiki;
					match(input,60,FOLLOW_60_in_link_interwiki_uri3144); if (failed) return interwiki;
					match(input,69,FOLLOW_69_in_link_interwiki_uri3146); if (failed) return interwiki;

					}
					break;
				case 16 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:497:4: 'T' 'i' 'd' 'd' 'l' 'y' 'W' 'i' 'k' 'i'
					{
					match(input,72,FOLLOW_72_in_link_interwiki_uri3151); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3153); if (failed) return interwiki;
					match(input,63,FOLLOW_63_in_link_interwiki_uri3155); if (failed) return interwiki;
					match(input,63,FOLLOW_63_in_link_interwiki_uri3157); if (failed) return interwiki;
					match(input,50,FOLLOW_50_in_link_interwiki_uri3159); if (failed) return interwiki;
					match(input,73,FOLLOW_73_in_link_interwiki_uri3161); if (failed) return interwiki;
					match(input,47,FOLLOW_47_in_link_interwiki_uri3163); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3165); if (failed) return interwiki;
					match(input,45,FOLLOW_45_in_link_interwiki_uri3167); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3169); if (failed) return interwiki;

					}
					break;
				case 17 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:498:4: 'T' 'W' 'i' 'k' 'i'
					{
					match(input,72,FOLLOW_72_in_link_interwiki_uri3174); if (failed) return interwiki;
					match(input,47,FOLLOW_47_in_link_interwiki_uri3176); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3178); if (failed) return interwiki;
					match(input,45,FOLLOW_45_in_link_interwiki_uri3180); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3182); if (failed) return interwiki;

					}
					break;
				case 18 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:499:4: 'U' 's' 'e' 'm' 'o' 'd'
					{
					match(input,74,FOLLOW_74_in_link_interwiki_uri3187); if (failed) return interwiki;
					match(input,67,FOLLOW_67_in_link_interwiki_uri3189); if (failed) return interwiki;
					match(input,55,FOLLOW_55_in_link_interwiki_uri3191); if (failed) return interwiki;
					match(input,66,FOLLOW_66_in_link_interwiki_uri3193); if (failed) return interwiki;
					match(input,44,FOLLOW_44_in_link_interwiki_uri3195); if (failed) return interwiki;
					match(input,63,FOLLOW_63_in_link_interwiki_uri3197); if (failed) return interwiki;

					}
					break;
				case 19 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:500:4: 'W' 'i' 'k' 'i' 'p' 'e' 'd' 'i' 'a'
					{
					match(input,47,FOLLOW_47_in_link_interwiki_uri3202); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3204); if (failed) return interwiki;
					match(input,45,FOLLOW_45_in_link_interwiki_uri3206); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3208); if (failed) return interwiki;
					match(input,69,FOLLOW_69_in_link_interwiki_uri3210); if (failed) return interwiki;
					match(input,55,FOLLOW_55_in_link_interwiki_uri3212); if (failed) return interwiki;
					match(input,63,FOLLOW_63_in_link_interwiki_uri3214); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3216); if (failed) return interwiki;
					match(input,60,FOLLOW_60_in_link_interwiki_uri3218); if (failed) return interwiki;

					}
					break;
				case 20 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:501:4: 'X' 'W' 'i' 'k' 'i'
					{
					match(input,75,FOLLOW_75_in_link_interwiki_uri3223); if (failed) return interwiki;
					match(input,47,FOLLOW_47_in_link_interwiki_uri3225); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3227); if (failed) return interwiki;
					match(input,45,FOLLOW_45_in_link_interwiki_uri3229); if (failed) return interwiki;
					match(input,48,FOLLOW_48_in_link_interwiki_uri3231); if (failed) return interwiki;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return interwiki;
	}
	// $ANTLR end link_interwiki_uri

	// $ANTLR start link_interwiki_pagename
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:503:1: link_interwiki_pagename returns [String text = new String()] : (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+ ;
	public final String link_interwiki_pagename() throws RecognitionException {
		String text =  new String();

		Token c=null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:504:2: ( (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:504:4: (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:504:4: (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+
			int cnt100=0;
			loop100:
			do {
				int alt100=2;
				int LA100_0 = input.LA(1);

				if ( ((LA100_0>=FORCED_END_OF_LINE && LA100_0<=WIKI)||(LA100_0>=POUND && LA100_0<=EQUAL)||(LA100_0>=ITAL && LA100_0<=NOWIKI_CLOSE)||(LA100_0>=IMAGE_CLOSE && LA100_0<=75)) ) {
					alt100=1;
				}

				switch (alt100) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:504:6: c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF )
					{
					c=(Token)input.LT(1);
					if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=EQUAL)||(input.LA(1)>=ITAL && input.LA(1)<=NOWIKI_CLOSE)||(input.LA(1)>=IMAGE_CLOSE && input.LA(1)<=75) ) {
						input.consume();
						errorRecovery=false;failed=false;
					}
					else {
						if (backtracking>0) {failed=true; return text;}
						MismatchedSetException mse =
							new MismatchedSetException(null,input);
						recoverFromMismatchedSet(input,mse,FOLLOW_set_in_link_interwiki_pagename3251);	throw mse;
					}

					if ( backtracking==0 ) {
					   text += c.getText();
					}

					}
					break;

				default :
					if ( cnt100 >= 1 ) break loop100;
					if (backtracking>0) {failed=true; return text;}
						EarlyExitException eee =
							new EarlyExitException(100, input);
						throw eee;
				}
				cnt100++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return text;
	}
	// $ANTLR end link_interwiki_pagename

	// $ANTLR start link_description
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:506:1: link_description returns [CollectionNode node = new CollectionNode()] : (l= link_descriptionpart | i= image )+ ;
	public final CollectionNode link_description() throws RecognitionException {
		CollectionNode node =  new CollectionNode();

		ASTNode l = null;

		ImageNode i = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:507:2: ( (l= link_descriptionpart | i= image )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:507:4: (l= link_descriptionpart | i= image )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:507:4: (l= link_descriptionpart | i= image )+
			int cnt101=0;
			loop101:
			do {
				int alt101=3;
				int LA101_0 = input.LA(1);

				if ( ((LA101_0>=FORCED_END_OF_LINE && LA101_0<=WIKI)||(LA101_0>=POUND && LA101_0<=ITAL)||(LA101_0>=FORCED_LINEBREAK && LA101_0<=NOWIKI_CLOSE)||(LA101_0>=IMAGE_CLOSE && LA101_0<=75)) ) {
					alt101=1;
				}
				else if ( (LA101_0==IMAGE_OPEN) ) {
					alt101=2;
				}

				switch (alt101) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:507:6: l= link_descriptionpart
					{
					pushFollow(FOLLOW_link_descriptionpart_in_link_description3294);
					l=link_descriptionpart();
					_fsp--;
					if (failed) return node;
					if ( backtracking==0 ) {
					  node.add(l);
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:508:5: i= image
					{
					pushFollow(FOLLOW_image_in_link_description3306);
					i=image();
					_fsp--;
					if (failed) return node;
					if ( backtracking==0 ) {
					  node.add(i);
					}

					}
					break;

				default :
					if ( cnt101 >= 1 ) break loop101;
					if (backtracking>0) {failed=true; return node;}
						EarlyExitException eee =
							new EarlyExitException(101, input);
						throw eee;
				}
				cnt101++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return node;
	}
	// $ANTLR end link_description

	protected static class link_descriptionpart_scope {
		CollectionNode element;
	}
	protected Stack link_descriptionpart_stack = new Stack();

	// $ANTLR start link_descriptionpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:510:1: link_descriptionpart returns [ASTNode text = null] : ( bold_markup onestar (lb= link_bold_descriptionpart onestar )+ bold_markup | ital_markup onestar (li= link_ital_descriptionpart onestar )+ ital_markup | onestar (t= link_descriptiontext onestar )+ );
	public final ASTNode link_descriptionpart() throws RecognitionException {
		link_descriptionpart_stack.push(new link_descriptionpart_scope());
		ASTNode text =  null;

		ASTNode lb = null;

		ASTNode li = null;

		CollectionNode t = null;


			((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element = new CollectionNode();

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:517:2: ( bold_markup onestar (lb= link_bold_descriptionpart onestar )+ bold_markup | ital_markup onestar (li= link_ital_descriptionpart onestar )+ ital_markup | onestar (t= link_descriptiontext onestar )+ )
			int alt105=3;
			switch ( input.LA(1) ) {
			case STAR:
				{
				int LA105_1 = input.LA(2);

				if ( (LA105_1==STAR) ) {
					alt105=1;
				}
				else if ( ((LA105_1>=FORCED_END_OF_LINE && LA105_1<=WIKI)||LA105_1==POUND||(LA105_1>=EQUAL && LA105_1<=PIPE)||(LA105_1>=FORCED_LINEBREAK && LA105_1<=NOWIKI_CLOSE)||(LA105_1>=IMAGE_CLOSE && LA105_1<=75)) ) {
					alt105=3;
				}
				else {
					if (backtracking>0) {failed=true; return text;}
					NoViableAltException nvae =
						new NoViableAltException("510:1: link_descriptionpart returns [ASTNode text = null] : ( bold_markup onestar (lb= link_bold_descriptionpart onestar )+ bold_markup | ital_markup onestar (li= link_ital_descriptionpart onestar )+ ital_markup | onestar (t= link_descriptiontext onestar )+ );", 105, 1, input);

					throw nvae;
				}
				}
				break;
			case ITAL:
				{
				alt105=2;
				}
				break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case EQUAL:
			case PIPE:
			case FORCED_LINEBREAK:
			case ESCAPE:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case IMAGE_CLOSE:
			case BLANKS:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case INSIGNIFICANT_CHAR:
			case 40:
			case 41:
			case 42:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
				{
				alt105=3;
				}
				break;
			default:
				if (backtracking>0) {failed=true; return text;}
				NoViableAltException nvae =
					new NoViableAltException("510:1: link_descriptionpart returns [ASTNode text = null] : ( bold_markup onestar (lb= link_bold_descriptionpart onestar )+ bold_markup | ital_markup onestar (li= link_ital_descriptionpart onestar )+ ital_markup | onestar (t= link_descriptiontext onestar )+ );", 105, 0, input);

				throw nvae;
			}

			switch (alt105) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:517:4: bold_markup onestar (lb= link_bold_descriptionpart onestar )+ bold_markup
					{
					pushFollow(FOLLOW_bold_markup_in_link_descriptionpart3331);
					bold_markup();
					_fsp--;
					if (failed) return text;
					pushFollow(FOLLOW_onestar_in_link_descriptionpart3334);
					onestar();
					_fsp--;
					if (failed) return text;
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:517:25: (lb= link_bold_descriptionpart onestar )+
					int cnt102=0;
					loop102:
					do {
						int alt102=2;
						int LA102_0 = input.LA(1);

						if ( ((LA102_0>=FORCED_END_OF_LINE && LA102_0<=WIKI)||LA102_0==POUND||(LA102_0>=EQUAL && LA102_0<=ITAL)||(LA102_0>=FORCED_LINEBREAK && LA102_0<=NOWIKI_CLOSE)||(LA102_0>=IMAGE_CLOSE && LA102_0<=75)) ) {
							alt102=1;
						}

						switch (alt102) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:517:27: lb= link_bold_descriptionpart onestar
							{
							pushFollow(FOLLOW_link_bold_descriptionpart_in_link_descriptionpart3342);
							lb=link_bold_descriptionpart();
							_fsp--;
							if (failed) return text;
							if ( backtracking==0 ) {
							  ((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element.add(lb);
							}
							pushFollow(FOLLOW_onestar_in_link_descriptionpart3347);
							onestar();
							_fsp--;
							if (failed) return text;

							}
							break;

						default :
							if ( cnt102 >= 1 ) break loop102;
							if (backtracking>0) {failed=true; return text;}
								EarlyExitException eee =
									new EarlyExitException(102, input);
								throw eee;
						}
						cnt102++;
					} while (true);

					if ( backtracking==0 ) {
					  text = new BoldTextNode(((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element);
					}
					pushFollow(FOLLOW_bold_markup_in_link_descriptionpart3357);
					bold_markup();
					_fsp--;
					if (failed) return text;

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:519:4: ital_markup onestar (li= link_ital_descriptionpart onestar )+ ital_markup
					{
					pushFollow(FOLLOW_ital_markup_in_link_descriptionpart3362);
					ital_markup();
					_fsp--;
					if (failed) return text;
					pushFollow(FOLLOW_onestar_in_link_descriptionpart3365);
					onestar();
					_fsp--;
					if (failed) return text;
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:519:26: (li= link_ital_descriptionpart onestar )+
					int cnt103=0;
					loop103:
					do {
						int alt103=2;
						int LA103_0 = input.LA(1);

						if ( ((LA103_0>=FORCED_END_OF_LINE && LA103_0<=WIKI)||(LA103_0>=POUND && LA103_0<=PIPE)||(LA103_0>=FORCED_LINEBREAK && LA103_0<=NOWIKI_CLOSE)||(LA103_0>=IMAGE_CLOSE && LA103_0<=75)) ) {
							alt103=1;
						}

						switch (alt103) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:519:28: li= link_ital_descriptionpart onestar
							{
							pushFollow(FOLLOW_link_ital_descriptionpart_in_link_descriptionpart3374);
							li=link_ital_descriptionpart();
							_fsp--;
							if (failed) return text;
							if ( backtracking==0 ) {
							  ((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element.add(li);
							}
							pushFollow(FOLLOW_onestar_in_link_descriptionpart3379);
							onestar();
							_fsp--;
							if (failed) return text;

							}
							break;

						default :
							if ( cnt103 >= 1 ) break loop103;
							if (backtracking>0) {failed=true; return text;}
								EarlyExitException eee =
									new EarlyExitException(103, input);
								throw eee;
						}
						cnt103++;
					} while (true);

					if ( backtracking==0 ) {
					  text = new ItalicTextNode(((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element);
					}
					pushFollow(FOLLOW_ital_markup_in_link_descriptionpart3388);
					ital_markup();
					_fsp--;
					if (failed) return text;

					}
					break;
				case 3 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:521:4: onestar (t= link_descriptiontext onestar )+
					{
					pushFollow(FOLLOW_onestar_in_link_descriptionpart3393);
					onestar();
					_fsp--;
					if (failed) return text;
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:521:13: (t= link_descriptiontext onestar )+
					int cnt104=0;
					loop104:
					do {
						int alt104=2;
						switch ( input.LA(1) ) {
						case FORCED_END_OF_LINE:
						case HEADING_SECTION:
						case HORIZONTAL_SECTION:
						case LIST_ITEM:
						case LIST_ITEM_PART:
						case NOWIKI_SECTION:
						case SCAPE_NODE:
						case TEXT_NODE:
						case UNORDERED_LIST:
						case UNFORMATTED_TEXT:
						case WIKI:
						case POUND:
						case EQUAL:
						case PIPE:
						case NOWIKI_BLOCK_CLOSE:
						case NOWIKI_CLOSE:
						case IMAGE_CLOSE:
						case BLANKS:
						case DASH:
						case CR:
						case LF:
						case SPACE:
						case TABULATOR:
						case COLON_SLASH:
						case SLASH:
						case INSIGNIFICANT_CHAR:
						case 40:
						case 41:
						case 42:
						case 43:
						case 44:
						case 45:
						case 46:
						case 47:
						case 48:
						case 49:
						case 50:
						case 51:
						case 52:
						case 53:
						case 54:
						case 55:
						case 56:
						case 57:
						case 58:
						case 59:
						case 60:
						case 61:
						case 62:
						case 63:
						case 64:
						case 65:
						case 66:
						case 67:
						case 68:
						case 69:
						case 70:
						case 71:
						case 72:
						case 73:
						case 74:
						case 75:
							{
							alt104=1;
							}
							break;
						case FORCED_LINEBREAK:
							{
							alt104=1;
							}
							break;
						case ESCAPE:
							{
							alt104=1;
							}
							break;

						}

						switch (alt104) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:521:15: t= link_descriptiontext onestar
							{
							pushFollow(FOLLOW_link_descriptiontext_in_link_descriptionpart3402);
							t=link_descriptiontext();
							_fsp--;
							if (failed) return text;
							pushFollow(FOLLOW_onestar_in_link_descriptionpart3405);
							onestar();
							_fsp--;
							if (failed) return text;
							if ( backtracking==0 ) {
							  ((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element.add(t);
							}

							}
							break;

						default :
							if ( cnt104 >= 1 ) break loop104;
							if (backtracking>0) {failed=true; return text;}
								EarlyExitException eee =
									new EarlyExitException(104, input);
								throw eee;
						}
						cnt104++;
					} while (true);

					if ( backtracking==0 ) {
					  text = new UnformattedTextNode(((link_descriptionpart_scope)link_descriptionpart_stack.peek()).element);
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			link_descriptionpart_stack.pop();
		}
		return text;
	}
	// $ANTLR end link_descriptionpart

	// $ANTLR start link_bold_descriptionpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:523:1: link_bold_descriptionpart returns [ASTNode text = null] : ( ital_markup t= link_boldital_description ital_markup | ld= link_descriptiontext );
	public final ASTNode link_bold_descriptionpart() throws RecognitionException {
		ASTNode text =  null;

		CollectionNode t = null;

		CollectionNode ld = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:524:2: ( ital_markup t= link_boldital_description ital_markup | ld= link_descriptiontext )
			int alt106=2;
			int LA106_0 = input.LA(1);

			if ( (LA106_0==ITAL) ) {
				alt106=1;
			}
			else if ( ((LA106_0>=FORCED_END_OF_LINE && LA106_0<=WIKI)||LA106_0==POUND||(LA106_0>=EQUAL && LA106_0<=PIPE)||(LA106_0>=FORCED_LINEBREAK && LA106_0<=NOWIKI_CLOSE)||(LA106_0>=IMAGE_CLOSE && LA106_0<=75)) ) {
				alt106=2;
			}
			else {
				if (backtracking>0) {failed=true; return text;}
				NoViableAltException nvae =
					new NoViableAltException("523:1: link_bold_descriptionpart returns [ASTNode text = null] : ( ital_markup t= link_boldital_description ital_markup | ld= link_descriptiontext );", 106, 0, input);

				throw nvae;
			}
			switch (alt106) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:524:4: ital_markup t= link_boldital_description ital_markup
					{
					pushFollow(FOLLOW_ital_markup_in_link_bold_descriptionpart3425);
					ital_markup();
					_fsp--;
					if (failed) return text;
					pushFollow(FOLLOW_link_boldital_description_in_link_bold_descriptionpart3432);
					t=link_boldital_description();
					_fsp--;
					if (failed) return text;
					if ( backtracking==0 ) {
					  text = new ItalicTextNode(t);
					}
					pushFollow(FOLLOW_ital_markup_in_link_bold_descriptionpart3437);
					ital_markup();
					_fsp--;
					if (failed) return text;

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:525:4: ld= link_descriptiontext
					{
					pushFollow(FOLLOW_link_descriptiontext_in_link_bold_descriptionpart3446);
					ld=link_descriptiontext();
					_fsp--;
					if (failed) return text;
					if ( backtracking==0 ) {
					  text =ld;
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return text;
	}
	// $ANTLR end link_bold_descriptionpart

	// $ANTLR start link_ital_descriptionpart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:527:1: link_ital_descriptionpart returns [ASTNode text = null] : ( bold_markup td= link_boldital_description bold_markup | t= link_descriptiontext );
	public final ASTNode link_ital_descriptionpart() throws RecognitionException {
		ASTNode text =  null;

		CollectionNode td = null;

		CollectionNode t = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:528:2: ( bold_markup td= link_boldital_description bold_markup | t= link_descriptiontext )
			int alt107=2;
			int LA107_0 = input.LA(1);

			if ( (LA107_0==STAR) ) {
				alt107=1;
			}
			else if ( ((LA107_0>=FORCED_END_OF_LINE && LA107_0<=WIKI)||LA107_0==POUND||(LA107_0>=EQUAL && LA107_0<=PIPE)||(LA107_0>=FORCED_LINEBREAK && LA107_0<=NOWIKI_CLOSE)||(LA107_0>=IMAGE_CLOSE && LA107_0<=75)) ) {
				alt107=2;
			}
			else {
				if (backtracking>0) {failed=true; return text;}
				NoViableAltException nvae =
					new NoViableAltException("527:1: link_ital_descriptionpart returns [ASTNode text = null] : ( bold_markup td= link_boldital_description bold_markup | t= link_descriptiontext );", 107, 0, input);

				throw nvae;
			}
			switch (alt107) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:528:4: bold_markup td= link_boldital_description bold_markup
					{
					pushFollow(FOLLOW_bold_markup_in_link_ital_descriptionpart3462);
					bold_markup();
					_fsp--;
					if (failed) return text;
					pushFollow(FOLLOW_link_boldital_description_in_link_ital_descriptionpart3469);
					td=link_boldital_description();
					_fsp--;
					if (failed) return text;
					pushFollow(FOLLOW_bold_markup_in_link_ital_descriptionpart3472);
					bold_markup();
					_fsp--;
					if (failed) return text;
					if ( backtracking==0 ) {
					  text = new BoldTextNode(td);
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:529:4: t= link_descriptiontext
					{
					pushFollow(FOLLOW_link_descriptiontext_in_link_ital_descriptionpart3483);
					t=link_descriptiontext();
					_fsp--;
					if (failed) return text;
					if ( backtracking==0 ) {
					  text = t;
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return text;
	}
	// $ANTLR end link_ital_descriptionpart

	// $ANTLR start link_boldital_description
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:531:1: link_boldital_description returns [CollectionNode text = new CollectionNode()] : onestar (t= link_descriptiontext onestar )+ ;
	public final CollectionNode link_boldital_description() throws RecognitionException {
		CollectionNode text =  new CollectionNode();

		CollectionNode t = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:532:2: ( onestar (t= link_descriptiontext onestar )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:532:4: onestar (t= link_descriptiontext onestar )+
			{
			pushFollow(FOLLOW_onestar_in_link_boldital_description3499);
			onestar();
			_fsp--;
			if (failed) return text;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:532:13: (t= link_descriptiontext onestar )+
			int cnt108=0;
			loop108:
			do {
				int alt108=2;
				int LA108_0 = input.LA(1);

				if ( ((LA108_0>=FORCED_END_OF_LINE && LA108_0<=WIKI)||LA108_0==POUND||(LA108_0>=EQUAL && LA108_0<=PIPE)||(LA108_0>=FORCED_LINEBREAK && LA108_0<=NOWIKI_CLOSE)||(LA108_0>=IMAGE_CLOSE && LA108_0<=75)) ) {
					alt108=1;
				}

				switch (alt108) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:532:15: t= link_descriptiontext onestar
					{
					pushFollow(FOLLOW_link_descriptiontext_in_link_boldital_description3508);
					t=link_descriptiontext();
					_fsp--;
					if (failed) return text;
					pushFollow(FOLLOW_onestar_in_link_boldital_description3511);
					onestar();
					_fsp--;
					if (failed) return text;
					if ( backtracking==0 ) {

					  					for(ASTNode item:t.getNodes()) {
					  						text.add(item);
					  					}

					}

					}
					break;

				default :
					if ( cnt108 >= 1 ) break loop108;
					if (backtracking>0) {failed=true; return text;}
						EarlyExitException eee =
							new EarlyExitException(108, input);
						throw eee;
				}
				cnt108++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return text;
	}
	// $ANTLR end link_boldital_description

	// $ANTLR start link_descriptiontext
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:538:1: link_descriptiontext returns [CollectionNode text = new CollectionNode()] : (t= link_descriptiontext_simple | ( forced_linebreak | e= escaped )+ );
	public final CollectionNode link_descriptiontext() throws RecognitionException {
		CollectionNode text =  new CollectionNode();

		String t = null;

		ScapedNode e = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:539:2: (t= link_descriptiontext_simple | ( forced_linebreak | e= escaped )+ )
			int alt110=2;
			int LA110_0 = input.LA(1);

			if ( ((LA110_0>=FORCED_END_OF_LINE && LA110_0<=WIKI)||LA110_0==POUND||(LA110_0>=EQUAL && LA110_0<=PIPE)||(LA110_0>=NOWIKI_BLOCK_CLOSE && LA110_0<=NOWIKI_CLOSE)||(LA110_0>=IMAGE_CLOSE && LA110_0<=75)) ) {
				alt110=1;
			}
			else if ( ((LA110_0>=FORCED_LINEBREAK && LA110_0<=ESCAPE)) ) {
				alt110=2;
			}
			else {
				if (backtracking>0) {failed=true; return text;}
				NoViableAltException nvae =
					new NoViableAltException("538:1: link_descriptiontext returns [CollectionNode text = new CollectionNode()] : (t= link_descriptiontext_simple | ( forced_linebreak | e= escaped )+ );", 110, 0, input);

				throw nvae;
			}
			switch (alt110) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:539:5: t= link_descriptiontext_simple
					{
					pushFollow(FOLLOW_link_descriptiontext_simple_in_link_descriptiontext3534);
					t=link_descriptiontext_simple();
					_fsp--;
					if (failed) return text;
					if ( backtracking==0 ) {
					   text.add(new UnformattedTextNode(t));
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:540:5: ( forced_linebreak | e= escaped )+
					{
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:540:5: ( forced_linebreak | e= escaped )+
					int cnt109=0;
					loop109:
					do {
						int alt109=3;
						int LA109_0 = input.LA(1);

						if ( (LA109_0==FORCED_LINEBREAK) ) {
							alt109=1;
						}
						else if ( (LA109_0==ESCAPE) ) {
							alt109=2;
						}

						switch (alt109) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:540:7: forced_linebreak
							{
							pushFollow(FOLLOW_forced_linebreak_in_link_descriptiontext3544);
							forced_linebreak();
							_fsp--;
							if (failed) return text;
							if ( backtracking==0 ) {
							  text.add(new ForcedEndOfLineNode());
							}

							}
							break;
						case 2 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:541:5: e= escaped
							{
							pushFollow(FOLLOW_escaped_in_link_descriptiontext3556);
							e=escaped();
							_fsp--;
							if (failed) return text;
							if ( backtracking==0 ) {
							  text.add(e);
							}

							}
							break;

						default :
							if ( cnt109 >= 1 ) break loop109;
							if (backtracking>0) {failed=true; return text;}
								EarlyExitException eee =
									new EarlyExitException(109, input);
								throw eee;
						}
						cnt109++;
					} while (true);

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return text;
	}
	// $ANTLR end link_descriptiontext

	// $ANTLR start link_descriptiontext_simple
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:543:1: link_descriptiontext_simple returns [String text = new String()] : (c=~ ( LINK_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ ;
	public final String link_descriptiontext_simple() throws RecognitionException {
		String text =  new String();

		Token c=null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:544:2: ( (c=~ ( LINK_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:544:4: (c=~ ( LINK_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:544:4: (c=~ ( LINK_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF ) )+
			int cnt111=0;
			loop111:
			do {
				int alt111=2;
				int LA111_0 = input.LA(1);

				if ( ((LA111_0>=FORCED_END_OF_LINE && LA111_0<=WIKI)||LA111_0==POUND||(LA111_0>=EQUAL && LA111_0<=PIPE)||(LA111_0>=NOWIKI_BLOCK_CLOSE && LA111_0<=NOWIKI_CLOSE)||(LA111_0>=IMAGE_CLOSE && LA111_0<=75)) ) {
					alt111=1;
				}

				switch (alt111) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:544:6: c=~ ( LINK_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | ESCAPE | NEWLINE | EOF )
					{
					c=(Token)input.LT(1);
					if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||input.LA(1)==POUND||(input.LA(1)>=EQUAL && input.LA(1)<=PIPE)||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=NOWIKI_CLOSE)||(input.LA(1)>=IMAGE_CLOSE && input.LA(1)<=75) ) {
						input.consume();
						errorRecovery=false;failed=false;
					}
					else {
						if (backtracking>0) {failed=true; return text;}
						MismatchedSetException mse =
							new MismatchedSetException(null,input);
						recoverFromMismatchedSet(input,mse,FOLLOW_set_in_link_descriptiontext_simple3581);	throw mse;
					}

					if ( backtracking==0 ) {
					   text += c.getText();
					}

					}
					break;

				default :
					if ( cnt111 >= 1 ) break loop111;
					if (backtracking>0) {failed=true; return text;}
						EarlyExitException eee =
							new EarlyExitException(111, input);
						throw eee;
				}
				cnt111++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return text;
	}
	// $ANTLR end link_descriptiontext_simple

	// $ANTLR start link_uri
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:556:1: link_uri returns [String text = new String()] : (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+ ;
	public final String link_uri() throws RecognitionException {
		String text =  new String();

		Token c=null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:557:2: ( (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:557:4: (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:557:4: (c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF ) )+
			int cnt112=0;
			loop112:
			do {
				int alt112=2;
				int LA112_0 = input.LA(1);

				if ( ((LA112_0>=FORCED_END_OF_LINE && LA112_0<=WIKI)||(LA112_0>=POUND && LA112_0<=EQUAL)||(LA112_0>=ITAL && LA112_0<=NOWIKI_CLOSE)||(LA112_0>=IMAGE_CLOSE && LA112_0<=75)) ) {
					alt112=1;
				}

				switch (alt112) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:557:6: c=~ ( PIPE | LINK_CLOSE | NEWLINE | EOF )
					{
					c=(Token)input.LT(1);
					if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=EQUAL)||(input.LA(1)>=ITAL && input.LA(1)<=NOWIKI_CLOSE)||(input.LA(1)>=IMAGE_CLOSE && input.LA(1)<=75) ) {
						input.consume();
						errorRecovery=false;failed=false;
					}
					else {
						if (backtracking>0) {failed=true; return text;}
						MismatchedSetException mse =
							new MismatchedSetException(null,input);
						recoverFromMismatchedSet(input,mse,FOLLOW_set_in_link_uri3682);	throw mse;
					}

					if ( backtracking==0 ) {
					  text += c.getText();
					}

					}
					break;

				default :
					if ( cnt112 >= 1 ) break loop112;
					if (backtracking>0) {failed=true; return text;}
						EarlyExitException eee =
							new EarlyExitException(112, input);
						throw eee;
				}
				cnt112++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return text;
	}
	// $ANTLR end link_uri

	// $ANTLR start image
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:564:1: image returns [ImageNode image = new ImageNode()] : image_open_markup uri= image_uri (alt= image_alternative )? image_close_markup ;
	public final ImageNode image() throws RecognitionException {
		ImageNode image =  new ImageNode();

		String uri = null;

		CollectionNode alt = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:565:2: ( image_open_markup uri= image_uri (alt= image_alternative )? image_close_markup )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:565:4: image_open_markup uri= image_uri (alt= image_alternative )? image_close_markup
			{
			pushFollow(FOLLOW_image_open_markup_in_image3723);
			image_open_markup();
			_fsp--;
			if (failed) return image;
			pushFollow(FOLLOW_image_uri_in_image3729);
			uri=image_uri();
			_fsp--;
			if (failed) return image;
			if ( backtracking==0 ) {
			  image.setUri(uri);
			}
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:565:67: (alt= image_alternative )?
			int alt113=2;
			int LA113_0 = input.LA(1);

			if ( (LA113_0==PIPE) ) {
				alt113=1;
			}
			switch (alt113) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:565:69: alt= image_alternative
					{
					pushFollow(FOLLOW_image_alternative_in_image3739);
					alt=image_alternative();
					_fsp--;
					if (failed) return image;
					if ( backtracking==0 ) {
					  image.setAltNode(alt);
					}

					}
					break;

			}

			pushFollow(FOLLOW_image_close_markup_in_image3751);
			image_close_markup();
			_fsp--;
			if (failed) return image;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return image;
	}
	// $ANTLR end image

	// $ANTLR start image_uri
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:568:1: image_uri returns [String link = new String()] : (c=~ ( PIPE | IMAGE_CLOSE | NEWLINE | EOF ) )+ ;
	public final String image_uri() throws RecognitionException {
		String link =  new String();

		Token c=null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:569:2: ( (c=~ ( PIPE | IMAGE_CLOSE | NEWLINE | EOF ) )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:569:4: (c=~ ( PIPE | IMAGE_CLOSE | NEWLINE | EOF ) )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:569:4: (c=~ ( PIPE | IMAGE_CLOSE | NEWLINE | EOF ) )+
			int cnt114=0;
			loop114:
			do {
				int alt114=2;
				int LA114_0 = input.LA(1);

				if ( ((LA114_0>=FORCED_END_OF_LINE && LA114_0<=WIKI)||(LA114_0>=POUND && LA114_0<=EQUAL)||(LA114_0>=ITAL && LA114_0<=LINK_CLOSE)||(LA114_0>=BLANKS && LA114_0<=75)) ) {
					alt114=1;
				}

				switch (alt114) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:569:5: c=~ ( PIPE | IMAGE_CLOSE | NEWLINE | EOF )
					{
					c=(Token)input.LT(1);
					if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=EQUAL)||(input.LA(1)>=ITAL && input.LA(1)<=LINK_CLOSE)||(input.LA(1)>=BLANKS && input.LA(1)<=75) ) {
						input.consume();
						errorRecovery=false;failed=false;
					}
					else {
						if (backtracking>0) {failed=true; return link;}
						MismatchedSetException mse =
							new MismatchedSetException(null,input);
						recoverFromMismatchedSet(input,mse,FOLLOW_set_in_image_uri3770);	throw mse;
					}

					if ( backtracking==0 ) {
					  link += c.getText();
					}

					}
					break;

				default :
					if ( cnt114 >= 1 ) break loop114;
					if (backtracking>0) {failed=true; return link;}
						EarlyExitException eee =
							new EarlyExitException(114, input);
						throw eee;
				}
				cnt114++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return link;
	}
	// $ANTLR end image_uri

	// $ANTLR start image_alternative
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:571:1: image_alternative returns [CollectionNode alternative = new CollectionNode()] : image_alternative_markup (p= image_alternativepart )+ ;
	public final CollectionNode image_alternative() throws RecognitionException {
		CollectionNode alternative =  new CollectionNode();

		ASTNode p = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:572:2: ( image_alternative_markup (p= image_alternativepart )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:572:4: image_alternative_markup (p= image_alternativepart )+
			{
			pushFollow(FOLLOW_image_alternative_markup_in_image_alternative3805);
			image_alternative_markup();
			_fsp--;
			if (failed) return alternative;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:572:30: (p= image_alternativepart )+
			int cnt115=0;
			loop115:
			do {
				int alt115=2;
				int LA115_0 = input.LA(1);

				if ( ((LA115_0>=FORCED_END_OF_LINE && LA115_0<=WIKI)||(LA115_0>=POUND && LA115_0<=ITAL)||(LA115_0>=FORCED_LINEBREAK && LA115_0<=LINK_CLOSE)||(LA115_0>=BLANKS && LA115_0<=75)) ) {
					alt115=1;
				}

				switch (alt115) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:572:32: p= image_alternativepart
					{
					pushFollow(FOLLOW_image_alternativepart_in_image_alternative3814);
					p=image_alternativepart();
					_fsp--;
					if (failed) return alternative;
					if ( backtracking==0 ) {
					  alternative.add(p);
					}

					}
					break;

				default :
					if ( cnt115 >= 1 ) break loop115;
					if (backtracking>0) {failed=true; return alternative;}
						EarlyExitException eee =
							new EarlyExitException(115, input);
						throw eee;
				}
				cnt115++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return alternative;
	}
	// $ANTLR end image_alternative

	protected static class image_alternativepart_scope {
		CollectionNode elements;
	}
	protected Stack image_alternativepart_stack = new Stack();

	// $ANTLR start image_alternativepart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:574:1: image_alternativepart returns [ASTNode item = null] : ( bold_markup onestar (t1= image_bold_alternativepart onestar )+ bold_markup | ital_markup onestar (t2= image_ital_alternativepart onestar )+ ital_markup | onestar (t3= image_alternativetext onestar )+ );
	public final ASTNode image_alternativepart() throws RecognitionException {
		image_alternativepart_stack.push(new image_alternativepart_scope());
		ASTNode item =  null;

		ASTNode t1 = null;

		ASTNode t2 = null;

		CollectionNode t3 = null;


		   ((image_alternativepart_scope)image_alternativepart_stack.peek()).elements = new CollectionNode();

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:581:2: ( bold_markup onestar (t1= image_bold_alternativepart onestar )+ bold_markup | ital_markup onestar (t2= image_ital_alternativepart onestar )+ ital_markup | onestar (t3= image_alternativetext onestar )+ )
			int alt119=3;
			switch ( input.LA(1) ) {
			case STAR:
				{
				int LA119_1 = input.LA(2);

				if ( (LA119_1==STAR) ) {
					alt119=1;
				}
				else if ( ((LA119_1>=FORCED_END_OF_LINE && LA119_1<=WIKI)||LA119_1==POUND||(LA119_1>=EQUAL && LA119_1<=PIPE)||(LA119_1>=FORCED_LINEBREAK && LA119_1<=LINK_CLOSE)||(LA119_1>=BLANKS && LA119_1<=75)) ) {
					alt119=3;
				}
				else {
					if (backtracking>0) {failed=true; return item;}
					NoViableAltException nvae =
						new NoViableAltException("574:1: image_alternativepart returns [ASTNode item = null] : ( bold_markup onestar (t1= image_bold_alternativepart onestar )+ bold_markup | ital_markup onestar (t2= image_ital_alternativepart onestar )+ ital_markup | onestar (t3= image_alternativetext onestar )+ );", 119, 1, input);

					throw nvae;
				}
				}
				break;
			case ITAL:
				{
				alt119=2;
				}
				break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case EQUAL:
			case PIPE:
			case FORCED_LINEBREAK:
			case ESCAPE:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case LINK_CLOSE:
			case BLANKS:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case INSIGNIFICANT_CHAR:
			case 40:
			case 41:
			case 42:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
				{
				alt119=3;
				}
				break;
			default:
				if (backtracking>0) {failed=true; return item;}
				NoViableAltException nvae =
					new NoViableAltException("574:1: image_alternativepart returns [ASTNode item = null] : ( bold_markup onestar (t1= image_bold_alternativepart onestar )+ bold_markup | ital_markup onestar (t2= image_ital_alternativepart onestar )+ ital_markup | onestar (t3= image_alternativetext onestar )+ );", 119, 0, input);

				throw nvae;
			}

			switch (alt119) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:581:4: bold_markup onestar (t1= image_bold_alternativepart onestar )+ bold_markup
					{
					pushFollow(FOLLOW_bold_markup_in_image_alternativepart3840);
					bold_markup();
					_fsp--;
					if (failed) return item;
					pushFollow(FOLLOW_onestar_in_image_alternativepart3843);
					onestar();
					_fsp--;
					if (failed) return item;
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:581:26: (t1= image_bold_alternativepart onestar )+
					int cnt116=0;
					loop116:
					do {
						int alt116=2;
						int LA116_0 = input.LA(1);

						if ( (LA116_0==STAR) ) {
							int LA116_1 = input.LA(2);

							if ( ((LA116_1>=FORCED_END_OF_LINE && LA116_1<=WIKI)||LA116_1==POUND||(LA116_1>=EQUAL && LA116_1<=PIPE)||(LA116_1>=FORCED_LINEBREAK && LA116_1<=LINK_CLOSE)||(LA116_1>=BLANKS && LA116_1<=75)) ) {
								alt116=1;
							}

						}
						else if ( ((LA116_0>=FORCED_END_OF_LINE && LA116_0<=WIKI)||LA116_0==POUND||(LA116_0>=EQUAL && LA116_0<=ITAL)||(LA116_0>=FORCED_LINEBREAK && LA116_0<=LINK_CLOSE)||(LA116_0>=BLANKS && LA116_0<=75)) ) {
							alt116=1;
						}

						switch (alt116) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:581:28: t1= image_bold_alternativepart onestar
							{
							pushFollow(FOLLOW_image_bold_alternativepart_in_image_alternativepart3852);
							t1=image_bold_alternativepart();
							_fsp--;
							if (failed) return item;
							if ( backtracking==0 ) {
							  ((image_alternativepart_scope)image_alternativepart_stack.peek()).elements.add(t1);
							}
							pushFollow(FOLLOW_onestar_in_image_alternativepart3857);
							onestar();
							_fsp--;
							if (failed) return item;

							}
							break;

						default :
							if ( cnt116 >= 1 ) break loop116;
							if (backtracking>0) {failed=true; return item;}
								EarlyExitException eee =
									new EarlyExitException(116, input);
								throw eee;
						}
						cnt116++;
					} while (true);

					pushFollow(FOLLOW_bold_markup_in_image_alternativepart3864);
					bold_markup();
					_fsp--;
					if (failed) return item;
					if ( backtracking==0 ) {
					  item = new BoldTextNode(((image_alternativepart_scope)image_alternativepart_stack.peek()).elements);
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:583:4: ital_markup onestar (t2= image_ital_alternativepart onestar )+ ital_markup
					{
					pushFollow(FOLLOW_ital_markup_in_image_alternativepart3871);
					ital_markup();
					_fsp--;
					if (failed) return item;
					pushFollow(FOLLOW_onestar_in_image_alternativepart3874);
					onestar();
					_fsp--;
					if (failed) return item;
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:583:26: (t2= image_ital_alternativepart onestar )+
					int cnt117=0;
					loop117:
					do {
						int alt117=2;
						int LA117_0 = input.LA(1);

						if ( ((LA117_0>=FORCED_END_OF_LINE && LA117_0<=WIKI)||(LA117_0>=POUND && LA117_0<=PIPE)||(LA117_0>=FORCED_LINEBREAK && LA117_0<=LINK_CLOSE)||(LA117_0>=BLANKS && LA117_0<=75)) ) {
							alt117=1;
						}

						switch (alt117) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:583:29: t2= image_ital_alternativepart onestar
							{
							pushFollow(FOLLOW_image_ital_alternativepart_in_image_alternativepart3884);
							t2=image_ital_alternativepart();
							_fsp--;
							if (failed) return item;
							if ( backtracking==0 ) {
							  ((image_alternativepart_scope)image_alternativepart_stack.peek()).elements.add(t2);
							}
							pushFollow(FOLLOW_onestar_in_image_alternativepart3889);
							onestar();
							_fsp--;
							if (failed) return item;

							}
							break;

						default :
							if ( cnt117 >= 1 ) break loop117;
							if (backtracking>0) {failed=true; return item;}
								EarlyExitException eee =
									new EarlyExitException(117, input);
								throw eee;
						}
						cnt117++;
					} while (true);

					pushFollow(FOLLOW_ital_markup_in_image_alternativepart3896);
					ital_markup();
					_fsp--;
					if (failed) return item;
					if ( backtracking==0 ) {
					  item = new ItalicTextNode(((image_alternativepart_scope)image_alternativepart_stack.peek()).elements);
					}

					}
					break;
				case 3 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:585:4: onestar (t3= image_alternativetext onestar )+
					{
					pushFollow(FOLLOW_onestar_in_image_alternativepart3903);
					onestar();
					_fsp--;
					if (failed) return item;
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:585:13: (t3= image_alternativetext onestar )+
					int cnt118=0;
					loop118:
					do {
						int alt118=2;
						int LA118_0 = input.LA(1);

						if ( ((LA118_0>=FORCED_END_OF_LINE && LA118_0<=WIKI)||LA118_0==POUND||(LA118_0>=EQUAL && LA118_0<=PIPE)||(LA118_0>=ESCAPE && LA118_0<=LINK_CLOSE)||(LA118_0>=BLANKS && LA118_0<=75)) ) {
							alt118=1;
						}
						else if ( (LA118_0==FORCED_LINEBREAK) ) {
							alt118=1;
						}

						switch (alt118) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:585:15: t3= image_alternativetext onestar
							{
							pushFollow(FOLLOW_image_alternativetext_in_image_alternativepart3910);
							t3=image_alternativetext();
							_fsp--;
							if (failed) return item;
							if ( backtracking==0 ) {

							  					for(ASTNode n: t3.getNodes()) {
							  					   ((image_alternativepart_scope)image_alternativepart_stack.peek()).elements.add(n);
							  					 }

							}
							pushFollow(FOLLOW_onestar_in_image_alternativepart3915);
							onestar();
							_fsp--;
							if (failed) return item;

							}
							break;

						default :
							if ( cnt118 >= 1 ) break loop118;
							if (backtracking>0) {failed=true; return item;}
								EarlyExitException eee =
									new EarlyExitException(118, input);
								throw eee;
						}
						cnt118++;
					} while (true);

					if ( backtracking==0 ) {
					  item =new UnformattedTextNode(((image_alternativepart_scope)image_alternativepart_stack.peek()).elements);
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			image_alternativepart_stack.pop();
		}
		return item;
	}
	// $ANTLR end image_alternativepart

	protected static class image_bold_alternativepart_scope {
		CollectionNode elements;
	}
	protected Stack image_bold_alternativepart_stack = new Stack();

	// $ANTLR start image_bold_alternativepart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:591:1: image_bold_alternativepart returns [ASTNode text = null] : ( ital_markup t= link_boldital_description ital_markup | onestar (i= image_alternativetext onestar )+ );
	public final ASTNode image_bold_alternativepart() throws RecognitionException {
		image_bold_alternativepart_stack.push(new image_bold_alternativepart_scope());
		ASTNode text =  null;

		CollectionNode t = null;

		CollectionNode i = null;


		   ((image_bold_alternativepart_scope)image_bold_alternativepart_stack.peek()).elements = new CollectionNode();

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:598:2: ( ital_markup t= link_boldital_description ital_markup | onestar (i= image_alternativetext onestar )+ )
			int alt121=2;
			int LA121_0 = input.LA(1);

			if ( (LA121_0==ITAL) ) {
				alt121=1;
			}
			else if ( ((LA121_0>=FORCED_END_OF_LINE && LA121_0<=WIKI)||(LA121_0>=POUND && LA121_0<=PIPE)||(LA121_0>=FORCED_LINEBREAK && LA121_0<=LINK_CLOSE)||(LA121_0>=BLANKS && LA121_0<=75)) ) {
				alt121=2;
			}
			else {
				if (backtracking>0) {failed=true; return text;}
				NoViableAltException nvae =
					new NoViableAltException("591:1: image_bold_alternativepart returns [ASTNode text = null] : ( ital_markup t= link_boldital_description ital_markup | onestar (i= image_alternativetext onestar )+ );", 121, 0, input);

				throw nvae;
			}
			switch (alt121) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:598:4: ital_markup t= link_boldital_description ital_markup
					{
					pushFollow(FOLLOW_ital_markup_in_image_bold_alternativepart3941);
					ital_markup();
					_fsp--;
					if (failed) return text;
					pushFollow(FOLLOW_link_boldital_description_in_image_bold_alternativepart3948);
					t=link_boldital_description();
					_fsp--;
					if (failed) return text;
					if ( backtracking==0 ) {
					  text = new ItalicTextNode(t);
					}
					pushFollow(FOLLOW_ital_markup_in_image_bold_alternativepart3953);
					ital_markup();
					_fsp--;
					if (failed) return text;

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:599:4: onestar (i= image_alternativetext onestar )+
					{
					pushFollow(FOLLOW_onestar_in_image_bold_alternativepart3958);
					onestar();
					_fsp--;
					if (failed) return text;
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:599:13: (i= image_alternativetext onestar )+
					int cnt120=0;
					loop120:
					do {
						int alt120=2;
						int LA120_0 = input.LA(1);

						if ( ((LA120_0>=FORCED_END_OF_LINE && LA120_0<=WIKI)||LA120_0==POUND||(LA120_0>=EQUAL && LA120_0<=PIPE)||(LA120_0>=ESCAPE && LA120_0<=LINK_CLOSE)||(LA120_0>=BLANKS && LA120_0<=75)) ) {
							alt120=1;
						}
						else if ( (LA120_0==FORCED_LINEBREAK) ) {
							alt120=1;
						}

						switch (alt120) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:599:15: i= image_alternativetext onestar
							{
							pushFollow(FOLLOW_image_alternativetext_in_image_bold_alternativepart3967);
							i=image_alternativetext();
							_fsp--;
							if (failed) return text;
							pushFollow(FOLLOW_onestar_in_image_bold_alternativepart3970);
							onestar();
							_fsp--;
							if (failed) return text;
							if ( backtracking==0 ) {

							  					for(ASTNode item:i.getNodes()) {
							  						((image_ital_alternativepart_scope)image_ital_alternativepart_stack.peek()).elements.add(item);
							  					}

							}

							}
							break;

						default :
							if ( cnt120 >= 1 ) break loop120;
							if (backtracking>0) {failed=true; return text;}
								EarlyExitException eee =
									new EarlyExitException(120, input);
								throw eee;
						}
						cnt120++;
					} while (true);

					if ( backtracking==0 ) {
					  text = new UnformattedTextNode(((image_bold_alternativepart_scope)image_bold_alternativepart_stack.peek()).elements);
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			image_bold_alternativepart_stack.pop();
		}
		return text;
	}
	// $ANTLR end image_bold_alternativepart

	protected static class image_ital_alternativepart_scope {
		CollectionNode elements;
	}
	protected Stack image_ital_alternativepart_stack = new Stack();

	// $ANTLR start image_ital_alternativepart
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:606:1: image_ital_alternativepart returns [ASTNode text = null] : ( bold_markup t= link_boldital_description bold_markup | onestar (i= image_alternativetext onestar )+ );
	public final ASTNode image_ital_alternativepart() throws RecognitionException {
		image_ital_alternativepart_stack.push(new image_ital_alternativepart_scope());
		ASTNode text =  null;

		CollectionNode t = null;

		CollectionNode i = null;


		   ((image_ital_alternativepart_scope)image_ital_alternativepart_stack.peek()).elements = new CollectionNode();

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:613:2: ( bold_markup t= link_boldital_description bold_markup | onestar (i= image_alternativetext onestar )+ )
			int alt123=2;
			int LA123_0 = input.LA(1);

			if ( (LA123_0==STAR) ) {
				int LA123_1 = input.LA(2);

				if ( (LA123_1==STAR) ) {
					alt123=1;
				}
				else if ( ((LA123_1>=FORCED_END_OF_LINE && LA123_1<=WIKI)||LA123_1==POUND||(LA123_1>=EQUAL && LA123_1<=PIPE)||(LA123_1>=FORCED_LINEBREAK && LA123_1<=LINK_CLOSE)||(LA123_1>=BLANKS && LA123_1<=75)) ) {
					alt123=2;
				}
				else {
					if (backtracking>0) {failed=true; return text;}
					NoViableAltException nvae =
						new NoViableAltException("606:1: image_ital_alternativepart returns [ASTNode text = null] : ( bold_markup t= link_boldital_description bold_markup | onestar (i= image_alternativetext onestar )+ );", 123, 1, input);

					throw nvae;
				}
			}
			else if ( ((LA123_0>=FORCED_END_OF_LINE && LA123_0<=WIKI)||LA123_0==POUND||(LA123_0>=EQUAL && LA123_0<=PIPE)||(LA123_0>=FORCED_LINEBREAK && LA123_0<=LINK_CLOSE)||(LA123_0>=BLANKS && LA123_0<=75)) ) {
				alt123=2;
			}
			else {
				if (backtracking>0) {failed=true; return text;}
				NoViableAltException nvae =
					new NoViableAltException("606:1: image_ital_alternativepart returns [ASTNode text = null] : ( bold_markup t= link_boldital_description bold_markup | onestar (i= image_alternativetext onestar )+ );", 123, 0, input);

				throw nvae;
			}
			switch (alt123) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:613:4: bold_markup t= link_boldital_description bold_markup
					{
					pushFollow(FOLLOW_bold_markup_in_image_ital_alternativepart3999);
					bold_markup();
					_fsp--;
					if (failed) return text;
					pushFollow(FOLLOW_link_boldital_description_in_image_ital_alternativepart4006);
					t=link_boldital_description();
					_fsp--;
					if (failed) return text;
					if ( backtracking==0 ) {
					  text = new BoldTextNode(t);
					}
					pushFollow(FOLLOW_bold_markup_in_image_ital_alternativepart4011);
					bold_markup();
					_fsp--;
					if (failed) return text;

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:614:4: onestar (i= image_alternativetext onestar )+
					{
					pushFollow(FOLLOW_onestar_in_image_ital_alternativepart4016);
					onestar();
					_fsp--;
					if (failed) return text;
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:614:13: (i= image_alternativetext onestar )+
					int cnt122=0;
					loop122:
					do {
						int alt122=2;
						int LA122_0 = input.LA(1);

						if ( ((LA122_0>=FORCED_END_OF_LINE && LA122_0<=WIKI)||LA122_0==POUND||(LA122_0>=EQUAL && LA122_0<=PIPE)||(LA122_0>=ESCAPE && LA122_0<=LINK_CLOSE)||(LA122_0>=BLANKS && LA122_0<=75)) ) {
							alt122=1;
						}
						else if ( (LA122_0==FORCED_LINEBREAK) ) {
							alt122=1;
						}

						switch (alt122) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:614:14: i= image_alternativetext onestar
							{
							pushFollow(FOLLOW_image_alternativetext_in_image_ital_alternativepart4025);
							i=image_alternativetext();
							_fsp--;
							if (failed) return text;
							pushFollow(FOLLOW_onestar_in_image_ital_alternativepart4028);
							onestar();
							_fsp--;
							if (failed) return text;
							if ( backtracking==0 ) {

							  					for(ASTNode item:i.getNodes()) {
							  						((image_ital_alternativepart_scope)image_ital_alternativepart_stack.peek()).elements.add(item);
							  					}

							}

							}
							break;

						default :
							if ( cnt122 >= 1 ) break loop122;
							if (backtracking>0) {failed=true; return text;}
								EarlyExitException eee =
									new EarlyExitException(122, input);
								throw eee;
						}
						cnt122++;
					} while (true);

					if ( backtracking==0 ) {
					  text = new UnformattedTextNode(((image_ital_alternativepart_scope)image_ital_alternativepart_stack.peek()).elements);
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			image_ital_alternativepart_stack.pop();
		}
		return text;
	}
	// $ANTLR end image_ital_alternativepart

	// $ANTLR start image_boldital_alternative
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:620:1: image_boldital_alternative returns [CollectionNode text = new CollectionNode()] : onestar (i= image_alternativetext onestar )+ ;
	public final CollectionNode image_boldital_alternative() throws RecognitionException {
		CollectionNode text =  new CollectionNode();

		CollectionNode i = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:621:2: ( onestar (i= image_alternativetext onestar )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:621:4: onestar (i= image_alternativetext onestar )+
			{
			pushFollow(FOLLOW_onestar_in_image_boldital_alternative4049);
			onestar();
			_fsp--;
			if (failed) return text;
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:621:13: (i= image_alternativetext onestar )+
			int cnt124=0;
			loop124:
			do {
				int alt124=2;
				int LA124_0 = input.LA(1);

				if ( ((LA124_0>=FORCED_END_OF_LINE && LA124_0<=WIKI)||LA124_0==POUND||(LA124_0>=EQUAL && LA124_0<=PIPE)||(LA124_0>=FORCED_LINEBREAK && LA124_0<=LINK_CLOSE)||(LA124_0>=BLANKS && LA124_0<=75)) ) {
					alt124=1;
				}

				switch (alt124) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:621:15: i= image_alternativetext onestar
					{
					pushFollow(FOLLOW_image_alternativetext_in_image_boldital_alternative4058);
					i=image_alternativetext();
					_fsp--;
					if (failed) return text;
					pushFollow(FOLLOW_onestar_in_image_boldital_alternative4061);
					onestar();
					_fsp--;
					if (failed) return text;
					if ( backtracking==0 ) {

					  					for(ASTNode item:i.getNodes()) {
					  						text.add(item);
					  					}

					}

					}
					break;

				default :
					if ( cnt124 >= 1 ) break loop124;
					if (backtracking>0) {failed=true; return text;}
						EarlyExitException eee =
							new EarlyExitException(124, input);
						throw eee;
				}
				cnt124++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return text;
	}
	// $ANTLR end image_boldital_alternative

	// $ANTLR start image_alternativetext
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:627:1: image_alternativetext returns [CollectionNode items = new CollectionNode()] : (contents= image_alternative_simple_text | ( forced_linebreak )+ );
	public final CollectionNode image_alternativetext() throws RecognitionException {
		CollectionNode items =  new CollectionNode();

		String contents = null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:628:2: (contents= image_alternative_simple_text | ( forced_linebreak )+ )
			int alt126=2;
			int LA126_0 = input.LA(1);

			if ( ((LA126_0>=FORCED_END_OF_LINE && LA126_0<=WIKI)||LA126_0==POUND||(LA126_0>=EQUAL && LA126_0<=PIPE)||(LA126_0>=ESCAPE && LA126_0<=LINK_CLOSE)||(LA126_0>=BLANKS && LA126_0<=75)) ) {
				alt126=1;
			}
			else if ( (LA126_0==FORCED_LINEBREAK) ) {
				alt126=2;
			}
			else {
				if (backtracking>0) {failed=true; return items;}
				NoViableAltException nvae =
					new NoViableAltException("627:1: image_alternativetext returns [CollectionNode items = new CollectionNode()] : (contents= image_alternative_simple_text | ( forced_linebreak )+ );", 126, 0, input);

				throw nvae;
			}
			switch (alt126) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:628:4: contents= image_alternative_simple_text
					{
					pushFollow(FOLLOW_image_alternative_simple_text_in_image_alternativetext4084);
					contents=image_alternative_simple_text();
					_fsp--;
					if (failed) return items;
					if ( backtracking==0 ) {
					  items.add(new UnformattedTextNode(contents));
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:629:4: ( forced_linebreak )+
					{
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:629:4: ( forced_linebreak )+
					int cnt125=0;
					loop125:
					do {
						int alt125=2;
						int LA125_0 = input.LA(1);

						if ( (LA125_0==FORCED_LINEBREAK) ) {
							alt125=1;
						}

						switch (alt125) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:629:5: forced_linebreak
							{
							pushFollow(FOLLOW_forced_linebreak_in_image_alternativetext4092);
							forced_linebreak();
							_fsp--;
							if (failed) return items;
							if ( backtracking==0 ) {
							  items.add(new ForcedEndOfLineNode());
							}

							}
							break;

						default :
							if ( cnt125 >= 1 ) break loop125;
							if (backtracking>0) {failed=true; return items;}
								EarlyExitException eee =
									new EarlyExitException(125, input);
								throw eee;
						}
						cnt125++;
					} while (true);

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return items;
	}
	// $ANTLR end image_alternativetext

	// $ANTLR start image_alternative_simple_text
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:632:1: image_alternative_simple_text returns [String text = new String()] : (c=~ ( IMAGE_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | NEWLINE | EOF ) )+ ;
	public final String image_alternative_simple_text() throws RecognitionException {
		String text =  new String();

		Token c=null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:633:2: ( (c=~ ( IMAGE_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | NEWLINE | EOF ) )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:634:2: (c=~ ( IMAGE_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | NEWLINE | EOF ) )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:634:2: (c=~ ( IMAGE_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | NEWLINE | EOF ) )+
			int cnt127=0;
			loop127:
			do {
				int alt127=2;
				int LA127_0 = input.LA(1);

				if ( ((LA127_0>=FORCED_END_OF_LINE && LA127_0<=WIKI)||LA127_0==POUND||(LA127_0>=EQUAL && LA127_0<=PIPE)||(LA127_0>=ESCAPE && LA127_0<=LINK_CLOSE)||(LA127_0>=BLANKS && LA127_0<=75)) ) {
					alt127=1;
				}

				switch (alt127) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:634:4: c=~ ( IMAGE_CLOSE | ITAL | STAR | LINK_OPEN | IMAGE_OPEN | NOWIKI_OPEN | EXTENSION | FORCED_LINEBREAK | NEWLINE | EOF )
					{
					c=(Token)input.LT(1);
					if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||input.LA(1)==POUND||(input.LA(1)>=EQUAL && input.LA(1)<=PIPE)||(input.LA(1)>=ESCAPE && input.LA(1)<=LINK_CLOSE)||(input.LA(1)>=BLANKS && input.LA(1)<=75) ) {
						input.consume();
						errorRecovery=false;failed=false;
					}
					else {
						if (backtracking>0) {failed=true; return text;}
						MismatchedSetException mse =
							new MismatchedSetException(null,input);
						recoverFromMismatchedSet(input,mse,FOLLOW_set_in_image_alternative_simple_text4119);	throw mse;
					}

					if ( backtracking==0 ) {
					  text += c.getText();
					}

					}
					break;

				default :
					if ( cnt127 >= 1 ) break loop127;
					if (backtracking>0) {failed=true; return text;}
						EarlyExitException eee =
							new EarlyExitException(127, input);
						throw eee;
				}
				cnt127++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return text;
	}
	// $ANTLR end image_alternative_simple_text

	// $ANTLR start extension
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:648:1: extension returns [ASTNode node = null] : extension_markup extension_handler blanks extension_statement extension_markup ;
	public final ASTNode extension() throws RecognitionException {
		ASTNode node =  null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:649:2: ( extension_markup extension_handler blanks extension_statement extension_markup )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:649:4: extension_markup extension_handler blanks extension_statement extension_markup
			{
			pushFollow(FOLLOW_extension_markup_in_extension4212);
			extension_markup();
			_fsp--;
			if (failed) return node;
			pushFollow(FOLLOW_extension_handler_in_extension4215);
			extension_handler();
			_fsp--;
			if (failed) return node;
			pushFollow(FOLLOW_blanks_in_extension4218);
			blanks();
			_fsp--;
			if (failed) return node;
			pushFollow(FOLLOW_extension_statement_in_extension4221);
			extension_statement();
			_fsp--;
			if (failed) return node;
			pushFollow(FOLLOW_extension_markup_in_extension4225);
			extension_markup();
			_fsp--;
			if (failed) return node;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return node;
	}
	// $ANTLR end extension

	// $ANTLR start extension_handler
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:652:1: extension_handler : (~ ( EXTENSION | BLANKS | ESCAPE | NEWLINE | EOF ) | escaped )+ ;
	public final void extension_handler() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:653:2: ( (~ ( EXTENSION | BLANKS | ESCAPE | NEWLINE | EOF ) | escaped )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:653:4: (~ ( EXTENSION | BLANKS | ESCAPE | NEWLINE | EOF ) | escaped )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:653:4: (~ ( EXTENSION | BLANKS | ESCAPE | NEWLINE | EOF ) | escaped )+
			int cnt128=0;
			loop128:
			do {
				int alt128=3;
				int LA128_0 = input.LA(1);

				if ( ((LA128_0>=FORCED_END_OF_LINE && LA128_0<=WIKI)||(LA128_0>=POUND && LA128_0<=NOWIKI_OPEN)||LA128_0==FORCED_LINEBREAK||(LA128_0>=NOWIKI_BLOCK_CLOSE && LA128_0<=IMAGE_CLOSE)||(LA128_0>=DASH && LA128_0<=75)) ) {
					alt128=1;
				}
				else if ( (LA128_0==ESCAPE) ) {
					alt128=2;
				}

				switch (alt128) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:653:5: ~ ( EXTENSION | BLANKS | ESCAPE | NEWLINE | EOF )
					{
					if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=NOWIKI_OPEN)||input.LA(1)==FORCED_LINEBREAK||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=IMAGE_CLOSE)||(input.LA(1)>=DASH && input.LA(1)<=75) ) {
						input.consume();
						errorRecovery=false;failed=false;
					}
					else {
						if (backtracking>0) {failed=true; return ;}
						MismatchedSetException mse =
							new MismatchedSetException(null,input);
						recoverFromMismatchedSet(input,mse,FOLLOW_set_in_extension_handler4236);	throw mse;
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:653:64: escaped
					{
					pushFollow(FOLLOW_escaped_in_extension_handler4269);
					escaped();
					_fsp--;
					if (failed) return ;

					}
					break;

				default :
					if ( cnt128 >= 1 ) break loop128;
					if (backtracking>0) {failed=true; return ;}
						EarlyExitException eee =
							new EarlyExitException(128, input);
						throw eee;
				}
				cnt128++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end extension_handler

	// $ANTLR start extension_statement
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:655:1: extension_statement : (~ ( EXTENSION | ESCAPE | EOF ) | escaped )* ;
	public final void extension_statement() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:656:2: ( (~ ( EXTENSION | ESCAPE | EOF ) | escaped )* )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:656:4: (~ ( EXTENSION | ESCAPE | EOF ) | escaped )*
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:656:4: (~ ( EXTENSION | ESCAPE | EOF ) | escaped )*
			loop129:
			do {
				int alt129=3;
				int LA129_0 = input.LA(1);

				if ( ((LA129_0>=FORCED_END_OF_LINE && LA129_0<=NOWIKI_OPEN)||LA129_0==FORCED_LINEBREAK||(LA129_0>=NOWIKI_BLOCK_CLOSE && LA129_0<=75)) ) {
					alt129=1;
				}
				else if ( (LA129_0==ESCAPE) ) {
					alt129=2;
				}

				switch (alt129) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:656:5: ~ ( EXTENSION | ESCAPE | EOF )
					{
					if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=NOWIKI_OPEN)||input.LA(1)==FORCED_LINEBREAK||(input.LA(1)>=NOWIKI_BLOCK_CLOSE && input.LA(1)<=75) ) {
						input.consume();
						errorRecovery=false;failed=false;
					}
					else {
						if (backtracking>0) {failed=true; return ;}
						MismatchedSetException mse =
							new MismatchedSetException(null,input);
						recoverFromMismatchedSet(input,mse,FOLLOW_set_in_extension_statement4283);	throw mse;
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:656:41: escaped
					{
					pushFollow(FOLLOW_escaped_in_extension_statement4304);
					escaped();
					_fsp--;
					if (failed) return ;

					}
					break;

				default :
					break loop129;
				}
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end extension_statement

	// $ANTLR start onestar
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:660:1: onestar : ( ({...}? ( STAR )? ) | );
	public final void onestar() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:661:2: ( ({...}? ( STAR )? ) | )
			int alt131=2;
			switch ( input.LA(1) ) {
			case STAR:
				{
				int LA131_1 = input.LA(2);

				if ( ( input.LA(2) != STAR ) ) {
					alt131=1;
				}
				else if ( (true) ) {
					alt131=2;
				}
				else {
					if (backtracking>0) {failed=true; return ;}
					NoViableAltException nvae =
						new NoViableAltException("660:1: onestar : ( ({...}? ( STAR )? ) | );", 131, 1, input);

					throw nvae;
				}
				}
				break;
			case FORCED_END_OF_LINE:
			case HEADING_SECTION:
			case HORIZONTAL_SECTION:
			case LIST_ITEM:
			case LIST_ITEM_PART:
			case NOWIKI_SECTION:
			case SCAPE_NODE:
			case TEXT_NODE:
			case UNORDERED_LIST:
			case UNFORMATTED_TEXT:
			case WIKI:
			case POUND:
			case EQUAL:
			case NOWIKI_BLOCK_CLOSE:
			case NOWIKI_CLOSE:
			case BLANKS:
			case DASH:
			case CR:
			case LF:
			case SPACE:
			case TABULATOR:
			case COLON_SLASH:
			case SLASH:
			case INSIGNIFICANT_CHAR:
			case 40:
			case 41:
			case 42:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
				{
				int LA131_2 = input.LA(2);

				if ( ( input.LA(2) != STAR ) ) {
					alt131=1;
				}
				else if ( (true) ) {
					alt131=2;
				}
				else {
					if (backtracking>0) {failed=true; return ;}
					NoViableAltException nvae =
						new NoViableAltException("660:1: onestar : ( ({...}? ( STAR )? ) | );", 131, 2, input);

					throw nvae;
				}
				}
				break;
			case FORCED_LINEBREAK:
				{
				int LA131_3 = input.LA(2);

				if ( ( input.LA(2) != STAR ) ) {
					alt131=1;
				}
				else if ( (true) ) {
					alt131=2;
				}
				else {
					if (backtracking>0) {failed=true; return ;}
					NoViableAltException nvae =
						new NoViableAltException("660:1: onestar : ( ({...}? ( STAR )? ) | );", 131, 3, input);

					throw nvae;
				}
				}
				break;
			case ESCAPE:
				{
				int LA131_4 = input.LA(2);

				if ( ( input.LA(2) != STAR ) ) {
					alt131=1;
				}
				else if ( (true) ) {
					alt131=2;
				}
				else {
					if (backtracking>0) {failed=true; return ;}
					NoViableAltException nvae =
						new NoViableAltException("660:1: onestar : ( ({...}? ( STAR )? ) | );", 131, 4, input);

					throw nvae;
				}
				}
				break;
			case LINK_OPEN:
				{
				int LA131_5 = input.LA(2);

				if ( ( input.LA(2) != STAR ) ) {
					alt131=1;
				}
				else if ( (true) ) {
					alt131=2;
				}
				else {
					if (backtracking>0) {failed=true; return ;}
					NoViableAltException nvae =
						new NoViableAltException("660:1: onestar : ( ({...}? ( STAR )? ) | );", 131, 5, input);

					throw nvae;
				}
				}
				break;
			case IMAGE_OPEN:
				{
				int LA131_6 = input.LA(2);

				if ( ( input.LA(2) != STAR ) ) {
					alt131=1;
				}
				else if ( (true) ) {
					alt131=2;
				}
				else {
					if (backtracking>0) {failed=true; return ;}
					NoViableAltException nvae =
						new NoViableAltException("660:1: onestar : ( ({...}? ( STAR )? ) | );", 131, 6, input);

					throw nvae;
				}
				}
				break;
			case EXTENSION:
				{
				int LA131_7 = input.LA(2);

				if ( ( input.LA(2) != STAR ) ) {
					alt131=1;
				}
				else if ( (true) ) {
					alt131=2;
				}
				else {
					if (backtracking>0) {failed=true; return ;}
					NoViableAltException nvae =
						new NoViableAltException("660:1: onestar : ( ({...}? ( STAR )? ) | );", 131, 7, input);

					throw nvae;
				}
				}
				break;
			case NOWIKI_OPEN:
				{
				int LA131_8 = input.LA(2);

				if ( ( input.LA(2) != STAR ) ) {
					alt131=1;
				}
				else if ( (true) ) {
					alt131=2;
				}
				else {
					if (backtracking>0) {failed=true; return ;}
					NoViableAltException nvae =
						new NoViableAltException("660:1: onestar : ( ({...}? ( STAR )? ) | );", 131, 8, input);

					throw nvae;
				}
				}
				break;
			case NEWLINE:
				{
				int LA131_9 = input.LA(2);

				if ( ((( input.LA(2) != STAR && input.LA(2) != DASH && input.LA(2) != POUND &&
						input.LA(2) != EQUAL && input.LA(2) != NEWLINE )|| input.LA(2) != STAR )) ) {
					alt131=1;
				}
				else if ( (true) ) {
					alt131=2;
				}
				else {
					if (backtracking>0) {failed=true; return ;}
					NoViableAltException nvae =
						new NoViableAltException("660:1: onestar : ( ({...}? ( STAR )? ) | );", 131, 9, input);

					throw nvae;
				}
				}
				break;
			case EOF:
				{
				int LA131_10 = input.LA(2);

				if ( ((( input.LA(2) != STAR && input.LA(2) != DASH && input.LA(2) != POUND &&
						input.LA(2) != EQUAL && input.LA(2) != NEWLINE )|| input.LA(2) != STAR )) ) {
					alt131=1;
				}
				else if ( (true) ) {
					alt131=2;
				}
				else {
					if (backtracking>0) {failed=true; return ;}
					NoViableAltException nvae =
						new NoViableAltException("660:1: onestar : ( ({...}? ( STAR )? ) | );", 131, 10, input);

					throw nvae;
				}
				}
				break;
			case ITAL:
				{
				int LA131_11 = input.LA(2);

				if ( ( input.LA(2) != STAR ) ) {
					alt131=1;
				}
				else if ( (true) ) {
					alt131=2;
				}
				else {
					if (backtracking>0) {failed=true; return ;}
					NoViableAltException nvae =
						new NoViableAltException("660:1: onestar : ( ({...}? ( STAR )? ) | );", 131, 11, input);

					throw nvae;
				}
				}
				break;
			case PIPE:
				{
				int LA131_12 = input.LA(2);

				if ( ((( input.LA(2) != STAR && input.LA(2) == EQUAL )|| input.LA(2) != STAR )) ) {
					alt131=1;
				}
				else if ( (true) ) {
					alt131=2;
				}
				else {
					if (backtracking>0) {failed=true; return ;}
					NoViableAltException nvae =
						new NoViableAltException("660:1: onestar : ( ({...}? ( STAR )? ) | );", 131, 12, input);

					throw nvae;
				}
				}
				break;
			case LINK_CLOSE:
				{
				int LA131_13 = input.LA(2);

				if ( ( input.LA(2) != STAR ) ) {
					alt131=1;
				}
				else if ( (true) ) {
					alt131=2;
				}
				else {
					if (backtracking>0) {failed=true; return ;}
					NoViableAltException nvae =
						new NoViableAltException("660:1: onestar : ( ({...}? ( STAR )? ) | );", 131, 13, input);

					throw nvae;
				}
				}
				break;
			case IMAGE_CLOSE:
				{
				int LA131_14 = input.LA(2);

				if ( ( input.LA(2) != STAR ) ) {
					alt131=1;
				}
				else if ( (true) ) {
					alt131=2;
				}
				else {
					if (backtracking>0) {failed=true; return ;}
					NoViableAltException nvae =
						new NoViableAltException("660:1: onestar : ( ({...}? ( STAR )? ) | );", 131, 14, input);

					throw nvae;
				}
				}
				break;
			default:
				if (backtracking>0) {failed=true; return ;}
				NoViableAltException nvae =
					new NoViableAltException("660:1: onestar : ( ({...}? ( STAR )? ) | );", 131, 0, input);

				throw nvae;
			}

			switch (alt131) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:661:4: ({...}? ( STAR )? )
					{
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:661:4: ({...}? ( STAR )? )
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:661:6: {...}? ( STAR )?
					{
					if ( !( input.LA(2) != STAR ) ) {
						if (backtracking>0) {failed=true; return ;}
						throw new FailedPredicateException(input, "onestar", " input.LA(2) != STAR ");
					}
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:661:32: ( STAR )?
					int alt130=2;
					int LA130_0 = input.LA(1);

					if ( (LA130_0==STAR) ) {
						alt130=1;
					}
					switch (alt130) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:661:34: STAR
							{
							match(input,STAR,FOLLOW_STAR_in_onestar4326); if (failed) return ;

							}
							break;

					}

					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:663:2:
					{
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end onestar

	// $ANTLR start escaped
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:664:1: escaped returns [ScapedNode scaped = new ScapedNode()] : ( ESCAPE STAR STAR | ESCAPE c= . );
	public final ScapedNode escaped() throws RecognitionException {
		ScapedNode scaped =  new ScapedNode();

		Token c=null;

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:665:2: ( ESCAPE STAR STAR | ESCAPE c= . )
			int alt132=2;
			int LA132_0 = input.LA(1);

			if ( (LA132_0==ESCAPE) ) {
				int LA132_1 = input.LA(2);

				if ( (LA132_1==STAR) ) {
					int LA132_2 = input.LA(3);

					if ( (LA132_2==STAR) ) {
						alt132=1;
					}
					else if ( (LA132_2==EOF||(LA132_2>=FORCED_END_OF_LINE && LA132_2<=POUND)||(LA132_2>=EQUAL && LA132_2<=75)) ) {
						alt132=2;
					}
					else {
						if (backtracking>0) {failed=true; return scaped;}
						NoViableAltException nvae =
							new NoViableAltException("664:1: escaped returns [ScapedNode scaped = new ScapedNode()] : ( ESCAPE STAR STAR | ESCAPE c= . );", 132, 2, input);

						throw nvae;
					}
				}
				else if ( ((LA132_1>=FORCED_END_OF_LINE && LA132_1<=POUND)||(LA132_1>=EQUAL && LA132_1<=75)) ) {
					alt132=2;
				}
				else {
					if (backtracking>0) {failed=true; return scaped;}
					NoViableAltException nvae =
						new NoViableAltException("664:1: escaped returns [ScapedNode scaped = new ScapedNode()] : ( ESCAPE STAR STAR | ESCAPE c= . );", 132, 1, input);

					throw nvae;
				}
			}
			else {
				if (backtracking>0) {failed=true; return scaped;}
				NoViableAltException nvae =
					new NoViableAltException("664:1: escaped returns [ScapedNode scaped = new ScapedNode()] : ( ESCAPE STAR STAR | ESCAPE c= . );", 132, 0, input);

				throw nvae;
			}
			switch (alt132) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:665:4: ESCAPE STAR STAR
					{
					match(input,ESCAPE,FOLLOW_ESCAPE_in_escaped4348); if (failed) return scaped;
					match(input,STAR,FOLLOW_STAR_in_escaped4351); if (failed) return scaped;
					match(input,STAR,FOLLOW_STAR_in_escaped4354); if (failed) return scaped;
					if ( backtracking==0 ) {
					   scaped.setContent("**") ;
					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:666:4: ESCAPE c= .
					{
					match(input,ESCAPE,FOLLOW_ESCAPE_in_escaped4361); if (failed) return scaped;
					c=(Token)input.LT(1);
					matchAny(input); if (failed) return scaped;
					if ( backtracking==0 ) {
					   scaped.setContent(c.getText()) ;
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return scaped;
	}
	// $ANTLR end escaped

	// $ANTLR start paragraph_separator
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:669:1: paragraph_separator : ( ( newline )+ | EOF );
	public final void paragraph_separator() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:670:2: ( ( newline )+ | EOF )
			int alt134=2;
			int LA134_0 = input.LA(1);

			if ( (LA134_0==NEWLINE) ) {
				alt134=1;
			}
			else if ( (LA134_0==EOF) ) {
				alt134=2;
			}
			else {
				if (backtracking>0) {failed=true; return ;}
				NoViableAltException nvae =
					new NoViableAltException("669:1: paragraph_separator : ( ( newline )+ | EOF );", 134, 0, input);

				throw nvae;
			}
			switch (alt134) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:670:4: ( newline )+
					{
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:670:4: ( newline )+
					int cnt133=0;
					loop133:
					do {
						int alt133=2;
						int LA133_0 = input.LA(1);

						if ( (LA133_0==NEWLINE) ) {
							alt133=1;
						}

						switch (alt133) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:670:6: newline
							{
							pushFollow(FOLLOW_newline_in_paragraph_separator4385);
							newline();
							_fsp--;
							if (failed) return ;

							}
							break;

						default :
							if ( cnt133 >= 1 ) break loop133;
							if (backtracking>0) {failed=true; return ;}
								EarlyExitException eee =
									new EarlyExitException(133, input);
								throw eee;
						}
						cnt133++;
					} while (true);

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:671:4: EOF
					{
					match(input,EOF,FOLLOW_EOF_in_paragraph_separator4393); if (failed) return ;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end paragraph_separator

	// $ANTLR start whitespaces
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:673:1: whitespaces : ( blanks | newline )+ ;
	public final void whitespaces() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:674:2: ( ( blanks | newline )+ )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:674:4: ( blanks | newline )+
			{
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:674:4: ( blanks | newline )+
			int cnt135=0;
			loop135:
			do {
				int alt135=3;
				int LA135_0 = input.LA(1);

				if ( (LA135_0==BLANKS) ) {
					alt135=1;
				}
				else if ( (LA135_0==NEWLINE) ) {
					alt135=2;
				}

				switch (alt135) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:674:6: blanks
					{
					pushFollow(FOLLOW_blanks_in_whitespaces4405);
					blanks();
					_fsp--;
					if (failed) return ;

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:674:15: newline
					{
					pushFollow(FOLLOW_newline_in_whitespaces4409);
					newline();
					_fsp--;
					if (failed) return ;

					}
					break;

				default :
					if ( cnt135 >= 1 ) break loop135;
					if (backtracking>0) {failed=true; return ;}
						EarlyExitException eee =
							new EarlyExitException(135, input);
						throw eee;
				}
				cnt135++;
			} while (true);

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end whitespaces

	// $ANTLR start blanks
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:676:1: blanks : BLANKS ;
	public final void blanks() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:677:2: ( BLANKS )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:677:4: BLANKS
			{
			match(input,BLANKS,FOLLOW_BLANKS_in_blanks4422); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end blanks

	// $ANTLR start text_lineseparator
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:679:1: text_lineseparator : ( newline ( blanks )? | EOF );
	public final void text_lineseparator() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:680:2: ( newline ( blanks )? | EOF )
			int alt137=2;
			int LA137_0 = input.LA(1);

			if ( (LA137_0==NEWLINE) ) {
				alt137=1;
			}
			else if ( (LA137_0==EOF) ) {
				alt137=2;
			}
			else {
				if (backtracking>0) {failed=true; return ;}
				NoViableAltException nvae =
					new NoViableAltException("679:1: text_lineseparator : ( newline ( blanks )? | EOF );", 137, 0, input);

				throw nvae;
			}
			switch (alt137) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:680:4: newline ( blanks )?
					{
					pushFollow(FOLLOW_newline_in_text_lineseparator4432);
					newline();
					_fsp--;
					if (failed) return ;
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:680:13: ( blanks )?
					int alt136=2;
					int LA136_0 = input.LA(1);

					if ( (LA136_0==BLANKS) ) {
						alt136=1;
					}
					switch (alt136) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:680:15: blanks
							{
							pushFollow(FOLLOW_blanks_in_text_lineseparator4437);
							blanks();
							_fsp--;
							if (failed) return ;

							}
							break;

					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:681:4: EOF
					{
					match(input,EOF,FOLLOW_EOF_in_text_lineseparator4445); if (failed) return ;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end text_lineseparator

	// $ANTLR start newline
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:683:1: newline : NEWLINE ;
	public final void newline() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:684:2: ( NEWLINE )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:684:4: NEWLINE
			{
			match(input,NEWLINE,FOLLOW_NEWLINE_in_newline4455); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end newline

	// $ANTLR start bold_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:686:1: bold_markup : STAR STAR ;
	public final void bold_markup() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:687:2: ( STAR STAR )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:687:4: STAR STAR
			{
			match(input,STAR,FOLLOW_STAR_in_bold_markup4465); if (failed) return ;
			match(input,STAR,FOLLOW_STAR_in_bold_markup4468); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end bold_markup

	// $ANTLR start ital_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:689:1: ital_markup : ITAL ;
	public final void ital_markup() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:690:2: ( ITAL )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:690:4: ITAL
			{
			match(input,ITAL,FOLLOW_ITAL_in_ital_markup4478); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end ital_markup

	// $ANTLR start heading_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:692:1: heading_markup : EQUAL ;
	public final void heading_markup() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:693:2: ( EQUAL )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:693:4: EQUAL
			{
			match(input,EQUAL,FOLLOW_EQUAL_in_heading_markup4488); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end heading_markup

	public static class list_ordelem_markup_return extends ParserRuleReturnScope {
	};

	// $ANTLR start list_ordelem_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:695:1: list_ordelem_markup : POUND ;
	public final list_ordelem_markup_return list_ordelem_markup() throws RecognitionException {
		list_ordelem_markup_return retval = new list_ordelem_markup_return();
		retval.start = input.LT(1);

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:696:2: ( POUND )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:696:4: POUND
			{
			match(input,POUND,FOLLOW_POUND_in_list_ordelem_markup4498); if (failed) return retval;

			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return retval;
	}
	// $ANTLR end list_ordelem_markup

	public static class list_unordelem_markup_return extends ParserRuleReturnScope {
	};

	// $ANTLR start list_unordelem_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:698:1: list_unordelem_markup : STAR ;
	public final list_unordelem_markup_return list_unordelem_markup() throws RecognitionException {
		list_unordelem_markup_return retval = new list_unordelem_markup_return();
		retval.start = input.LT(1);

		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:699:2: ( STAR )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:699:4: STAR
			{
			match(input,STAR,FOLLOW_STAR_in_list_unordelem_markup4508); if (failed) return retval;

			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return retval;
	}
	// $ANTLR end list_unordelem_markup

	// $ANTLR start list_elemseparator
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:701:1: list_elemseparator : ( newline ( blanks )? | EOF );
	public final void list_elemseparator() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:702:2: ( newline ( blanks )? | EOF )
			int alt139=2;
			int LA139_0 = input.LA(1);

			if ( (LA139_0==NEWLINE) ) {
				alt139=1;
			}
			else if ( (LA139_0==EOF) ) {
				alt139=2;
			}
			else {
				if (backtracking>0) {failed=true; return ;}
				NoViableAltException nvae =
					new NoViableAltException("701:1: list_elemseparator : ( newline ( blanks )? | EOF );", 139, 0, input);

				throw nvae;
			}
			switch (alt139) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:702:4: newline ( blanks )?
					{
					pushFollow(FOLLOW_newline_in_list_elemseparator4518);
					newline();
					_fsp--;
					if (failed) return ;
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:702:13: ( blanks )?
					int alt138=2;
					int LA138_0 = input.LA(1);

					if ( (LA138_0==BLANKS) ) {
						alt138=1;
					}
					switch (alt138) {
						case 1 :
							// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:702:15: blanks
							{
							pushFollow(FOLLOW_blanks_in_list_elemseparator4523);
							blanks();
							_fsp--;
							if (failed) return ;

							}
							break;

					}

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:703:4: EOF
					{
					match(input,EOF,FOLLOW_EOF_in_list_elemseparator4531); if (failed) return ;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end list_elemseparator

	// $ANTLR start end_of_list
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:705:1: end_of_list : ( newline | EOF );
	public final void end_of_list() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:706:2: ( newline | EOF )
			int alt140=2;
			int LA140_0 = input.LA(1);

			if ( (LA140_0==NEWLINE) ) {
				alt140=1;
			}
			else if ( (LA140_0==EOF) ) {
				alt140=2;
			}
			else {
				if (backtracking>0) {failed=true; return ;}
				NoViableAltException nvae =
					new NoViableAltException("705:1: end_of_list : ( newline | EOF );", 140, 0, input);

				throw nvae;
			}
			switch (alt140) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:706:4: newline
					{
					pushFollow(FOLLOW_newline_in_end_of_list4541);
					newline();
					_fsp--;
					if (failed) return ;

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:707:4: EOF
					{
					match(input,EOF,FOLLOW_EOF_in_end_of_list4546); if (failed) return ;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end end_of_list

	// $ANTLR start table_cell_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:709:1: table_cell_markup : PIPE ;
	public final void table_cell_markup() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:710:2: ( PIPE )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:710:4: PIPE
			{
			match(input,PIPE,FOLLOW_PIPE_in_table_cell_markup4556); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end table_cell_markup

	// $ANTLR start table_headercell_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:712:1: table_headercell_markup : PIPE EQUAL ;
	public final void table_headercell_markup() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:713:2: ( PIPE EQUAL )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:713:4: PIPE EQUAL
			{
			match(input,PIPE,FOLLOW_PIPE_in_table_headercell_markup4566); if (failed) return ;
			match(input,EQUAL,FOLLOW_EQUAL_in_table_headercell_markup4569); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end table_headercell_markup

	// $ANTLR start table_rowseparator
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:715:1: table_rowseparator : ( newline | EOF );
	public final void table_rowseparator() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:716:2: ( newline | EOF )
			int alt141=2;
			int LA141_0 = input.LA(1);

			if ( (LA141_0==NEWLINE) ) {
				alt141=1;
			}
			else if ( (LA141_0==EOF) ) {
				alt141=2;
			}
			else {
				if (backtracking>0) {failed=true; return ;}
				NoViableAltException nvae =
					new NoViableAltException("715:1: table_rowseparator : ( newline | EOF );", 141, 0, input);

				throw nvae;
			}
			switch (alt141) {
				case 1 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:716:4: newline
					{
					pushFollow(FOLLOW_newline_in_table_rowseparator4579);
					newline();
					_fsp--;
					if (failed) return ;

					}
					break;
				case 2 :
					// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:717:4: EOF
					{
					match(input,EOF,FOLLOW_EOF_in_table_rowseparator4584); if (failed) return ;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end table_rowseparator

	// $ANTLR start nowiki_open_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:719:1: nowiki_open_markup : NOWIKI_OPEN ;
	public final void nowiki_open_markup() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:720:2: ( NOWIKI_OPEN )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:720:4: NOWIKI_OPEN
			{
			match(input,NOWIKI_OPEN,FOLLOW_NOWIKI_OPEN_in_nowiki_open_markup4594); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end nowiki_open_markup

	// $ANTLR start nowiki_close_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:722:1: nowiki_close_markup : NOWIKI_CLOSE ;
	public final void nowiki_close_markup() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:723:2: ( NOWIKI_CLOSE )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:723:4: NOWIKI_CLOSE
			{
			match(input,NOWIKI_CLOSE,FOLLOW_NOWIKI_CLOSE_in_nowiki_close_markup4604); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end nowiki_close_markup

	// $ANTLR start horizontalrule_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:725:1: horizontalrule_markup : DASH DASH DASH DASH ;
	public final void horizontalrule_markup() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:726:2: ( DASH DASH DASH DASH )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:726:4: DASH DASH DASH DASH
			{
			match(input,DASH,FOLLOW_DASH_in_horizontalrule_markup4614); if (failed) return ;
			match(input,DASH,FOLLOW_DASH_in_horizontalrule_markup4617); if (failed) return ;
			match(input,DASH,FOLLOW_DASH_in_horizontalrule_markup4620); if (failed) return ;
			match(input,DASH,FOLLOW_DASH_in_horizontalrule_markup4623); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end horizontalrule_markup

	// $ANTLR start link_open_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:728:1: link_open_markup : LINK_OPEN ;
	public final void link_open_markup() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:729:2: ( LINK_OPEN )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:729:4: LINK_OPEN
			{
			match(input,LINK_OPEN,FOLLOW_LINK_OPEN_in_link_open_markup4633); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end link_open_markup

	// $ANTLR start link_close_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:731:1: link_close_markup : LINK_CLOSE ;
	public final void link_close_markup() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:732:2: ( LINK_CLOSE )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:732:4: LINK_CLOSE
			{
			match(input,LINK_CLOSE,FOLLOW_LINK_CLOSE_in_link_close_markup4643); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end link_close_markup

	// $ANTLR start link_description_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:734:1: link_description_markup : PIPE ;
	public final void link_description_markup() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:735:2: ( PIPE )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:735:4: PIPE
			{
			match(input,PIPE,FOLLOW_PIPE_in_link_description_markup4653); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end link_description_markup

	// $ANTLR start image_open_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:737:1: image_open_markup : IMAGE_OPEN ;
	public final void image_open_markup() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:738:2: ( IMAGE_OPEN )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:738:4: IMAGE_OPEN
			{
			match(input,IMAGE_OPEN,FOLLOW_IMAGE_OPEN_in_image_open_markup4663); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end image_open_markup

	// $ANTLR start image_close_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:740:1: image_close_markup : IMAGE_CLOSE ;
	public final void image_close_markup() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:741:2: ( IMAGE_CLOSE )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:741:4: IMAGE_CLOSE
			{
			match(input,IMAGE_CLOSE,FOLLOW_IMAGE_CLOSE_in_image_close_markup4673); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end image_close_markup

	// $ANTLR start image_alternative_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:743:1: image_alternative_markup : PIPE ;
	public final void image_alternative_markup() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:744:2: ( PIPE )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:744:4: PIPE
			{
			match(input,PIPE,FOLLOW_PIPE_in_image_alternative_markup4683); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end image_alternative_markup

	// $ANTLR start extension_markup
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:746:1: extension_markup : EXTENSION ;
	public final void extension_markup() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:747:2: ( EXTENSION )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:747:4: EXTENSION
			{
			match(input,EXTENSION,FOLLOW_EXTENSION_in_extension_markup4693); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end extension_markup

	// $ANTLR start forced_linebreak
	// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:749:1: forced_linebreak : FORCED_LINEBREAK ;
	public final void forced_linebreak() throws RecognitionException {
		try {
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:750:2: ( FORCED_LINEBREAK )
			// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:750:4: FORCED_LINEBREAK
			{
			match(input,FORCED_LINEBREAK,FOLLOW_FORCED_LINEBREAK_in_forced_linebreak4703); if (failed) return ;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
		}
		return ;
	}
	// $ANTLR end forced_linebreak

	// $ANTLR start synpred1
	public final void synpred1_fragment() throws RecognitionException {
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:137:5: ( NOWIKI_OPEN ~ ( NEWLINE ) )
		// /home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g:137:7: NOWIKI_OPEN ~ ( NEWLINE )
		{
		match(input,NOWIKI_OPEN,FOLLOW_NOWIKI_OPEN_in_synpred1318); if (failed) return ;
		if ( (input.LA(1)>=FORCED_END_OF_LINE && input.LA(1)<=WIKI)||(input.LA(1)>=POUND && input.LA(1)<=75) ) {
			input.consume();
			errorRecovery=false;failed=false;
		}
		else {
			if (backtracking>0) {failed=true; return ;}
			MismatchedSetException mse =
				new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_synpred1321);	throw mse;
		}

		}
	}
	// $ANTLR end synpred1

	public final boolean synpred1() {
		backtracking++;
		int start = input.mark();
		try {
			synpred1_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !failed;
		input.rewind(start);
		backtracking--;
		failed=false;
		return success;
	}



	public static final BitSet FOLLOW_whitespaces_in_wikipage114 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_paragraphs_in_wikipage122 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_EOF_in_wikipage127 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_paragraph_in_paragraphs145 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_nowiki_block_in_paragraph166 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_blanks_in_paragraph173 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_paragraph_separator_in_paragraph176 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_blanks_in_paragraph183 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_heading_in_paragraph197 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_horizontalrule_in_paragraph216 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_list_unord_in_paragraph228 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_list_ord_in_paragraph241 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_table_in_paragraph254 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_text_paragraph_in_paragraph267 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_paragraph_separator_in_paragraph280 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_line_in_text_paragraph308 = new BitSet(new long[]{0xFFFFFFFFFFF27FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_nowiki_inline_in_text_paragraph340 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_text_element_in_text_paragraph351 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_text_lineseparator_in_text_paragraph360 = new BitSet(new long[]{0xFFFFFFFFFFF27FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_text_firstelement_in_text_line383 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_text_element_in_text_line394 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_text_lineseparator_in_text_line403 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_formattedelement_in_text_firstelement425 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_first_unformattedelement_in_text_firstelement436 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ital_markup_in_text_formattedelement452 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_text_italcontent_in_text_formattedelement458 = new BitSet(new long[]{0x0000000000108002L});
	public static final BitSet FOLLOW_NEWLINE_in_text_formattedelement467 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_ital_markup_in_text_formattedelement473 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_bold_markup_in_text_formattedelement481 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_text_boldcontent_in_text_formattedelement488 = new BitSet(new long[]{0x0000000000028002L});
	public static final BitSet FOLLOW_NEWLINE_in_text_formattedelement497 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_bold_markup_in_text_formattedelement503 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NEWLINE_in_text_boldcontent522 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_text_boldcontentpart_in_text_boldcontent534 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_EOF_in_text_boldcontent545 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NEWLINE_in_text_italcontent561 = new BitSet(new long[]{0xFFFFFFFFFFEF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_text_italcontentpart_in_text_italcontent573 = new BitSet(new long[]{0xFFFFFFFFFFEF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_EOF_in_text_italcontent584 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_text_element599 = new BitSet(new long[]{0xFFFFFFFFFFED7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_text_unformattedelement_in_text_element606 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_unformattedelement_in_text_element617 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_onestar_in_text_element620 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_formattedelement_in_text_element631 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ital_markup_in_text_boldcontentpart648 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_text_bolditalcontent_in_text_boldcontentpart655 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_ital_markup_in_text_boldcontentpart662 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_formattedcontent_in_text_boldcontentpart674 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_bold_markup_in_text_italcontentpart690 = new BitSet(new long[]{0xFFFFFFFFFFEFFFF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_text_bolditalcontent_in_text_italcontentpart697 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_bold_markup_in_text_italcontentpart703 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_formattedcontent_in_text_italcontentpart715 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NEWLINE_in_text_bolditalcontent733 = new BitSet(new long[]{0xFFFFFFFFFFEF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_text_formattedcontent_in_text_bolditalcontent744 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EOF_in_text_bolditalcontent754 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_text_formattedcontent768 = new BitSet(new long[]{0xFFFFFFFFFFED7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_text_unformattedelement_in_text_formattedcontent777 = new BitSet(new long[]{0xFFFFFFFFFFEFFFF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_text_formattedcontent782 = new BitSet(new long[]{0xFFFFFFFFFFEDFFF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_text_linebreak_in_text_formattedcontent787 = new BitSet(new long[]{0xFFFFFFFFFFED7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_text_lineseparator_in_text_linebreak807 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_first_inlineelement_in_text_inlineelement825 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_nowiki_inline_in_text_inlineelement836 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_link_in_text_first_inlineelement859 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_image_in_text_first_inlineelement870 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_extension_in_text_first_inlineelement879 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_first_unformatted_in_text_first_unformattedelement899 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_first_inlineelement_in_text_first_unformattedelement910 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_first_unformmatted_text_in_text_first_unformatted932 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_forced_linebreak_in_text_first_unformatted941 = new BitSet(new long[]{0x0000000006000002L});
	public static final BitSet FOLLOW_escaped_in_text_first_unformatted953 = new BitSet(new long[]{0x0000000006000002L});
	public static final BitSet FOLLOW_set_in_text_first_unformmatted_text982 = new BitSet(new long[]{0xFFFFFFFFF8007FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_text_unformatted_in_text_unformattedelement1097 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_inlineelement_in_text_unformattedelement1108 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_unformated_text_in_text_unformatted1130 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_forced_linebreak_in_text_unformatted1139 = new BitSet(new long[]{0x0000000006000002L});
	public static final BitSet FOLLOW_escaped_in_text_unformatted1151 = new BitSet(new long[]{0x0000000006000002L});
	public static final BitSet FOLLOW_set_in_text_unformated_text1177 = new BitSet(new long[]{0xFFFFFFFFF80D7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_heading_markup_in_heading1281 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_heading_content_in_heading1286 = new BitSet(new long[]{0x0000000080048000L});
	public static final BitSet FOLLOW_heading_markup_in_heading1293 = new BitSet(new long[]{0x0000000080008000L});
	public static final BitSet FOLLOW_blanks_in_heading1301 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_paragraph_separator_in_heading1308 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_heading_markup_in_heading_content1318 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_heading_content_in_heading_content1323 = new BitSet(new long[]{0x0000000000040002L});
	public static final BitSet FOLLOW_heading_markup_in_heading_content1328 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_heading_text_in_heading_content1336 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_heading_text1357 = new BitSet(new long[]{0xFFFFFFFFFFFB7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_escaped_in_heading_text1388 = new BitSet(new long[]{0xFFFFFFFFFFFB7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_list_ordelem_in_list_ord1422 = new BitSet(new long[]{0x0000000000018002L});
	public static final BitSet FOLLOW_end_of_list_in_list_ord1432 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_list_ordelem_markup_in_list_ordelem1465 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_list_elem_in_list_ordelem1473 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_list_unordelem_in_list_unord1497 = new BitSet(new long[]{0x0000000000028002L});
	public static final BitSet FOLLOW_end_of_list_in_list_unord1507 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_list_unordelem_markup_in_list_unordelem1540 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_list_elem_in_list_unordelem1547 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_list_elem_markup_in_list_elem1572 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_list_elemcontent_in_list_elem1583 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_list_elemseparator_in_list_elem1588 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_list_ordelem_markup_in_list_elem_markup1598 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_list_unordelem_markup_in_list_elem_markup1603 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_list_elemcontent1617 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_list_elemcontentpart_in_list_elemcontent1626 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_list_elemcontent1631 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_text_unformattedelement_in_list_elemcontentpart1652 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_list_formatted_elem_in_list_elemcontentpart1663 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_bold_markup_in_list_formatted_elem1680 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_list_formatted_elem1683 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_list_boldcontentpart_in_list_formatted_elem1692 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_list_formatted_elem1697 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_bold_markup_in_list_formatted_elem1706 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ital_markup_in_list_formatted_elem1714 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_list_formatted_elem1717 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_list_italcontentpart_in_list_formatted_elem1726 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_list_formatted_elem1731 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_ital_markup_in_list_formatted_elem1740 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ital_markup_in_list_boldcontentpart1766 = new BitSet(new long[]{0xFFFFFFFFFFED7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_list_bolditalcontent_in_list_boldcontentpart1773 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_ital_markup_in_list_boldcontentpart1780 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_unformattedelement_in_list_boldcontentpart1795 = new BitSet(new long[]{0xFFFFFFFFFFED7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_text_unformattedelement_in_list_bolditalcontent1827 = new BitSet(new long[]{0xFFFFFFFFFFED7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_bold_markup_in_list_italcontentpart1857 = new BitSet(new long[]{0xFFFFFFFFFFED7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_list_bolditalcontent_in_list_italcontentpart1864 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_bold_markup_in_list_italcontentpart1871 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_text_unformattedelement_in_list_italcontentpart1885 = new BitSet(new long[]{0xFFFFFFFFFFED7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_table_row_in_table1915 = new BitSet(new long[]{0x0000000000080002L});
	public static final BitSet FOLLOW_table_cell_in_table_row1941 = new BitSet(new long[]{0x0000000000088000L});
	public static final BitSet FOLLOW_table_rowseparator_in_table_row1949 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_headercell_in_table_cell1970 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_normalcell_in_table_cell1981 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_headercell_markup_in_table_headercell1997 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_table_cellcontent_in_table_headercell2004 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_cell_markup_in_table_normalcell2020 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_table_cellcontent_in_table_normalcell2027 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_table_cellcontent2043 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_table_cellcontentpart_in_table_cellcontent2052 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_table_cellcontent2057 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_table_formattedelement_in_table_cellcontentpart2078 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_unformattedelement_in_table_cellcontentpart2089 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ital_markup_in_table_formattedelement2105 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_table_italcontent_in_table_formattedelement2115 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_ital_markup_in_table_formattedelement2124 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_bold_markup_in_table_formattedelement2132 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_table_boldcontent_in_table_formattedelement2139 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_bold_markup_in_table_formattedelement2149 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_table_boldcontent2166 = new BitSet(new long[]{0xFFFFFFFFFFF57FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_table_boldcontentpart_in_table_boldcontent2175 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_table_boldcontent2180 = new BitSet(new long[]{0xFFFFFFFFFFF57FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_EOF_in_table_boldcontent2188 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_table_italcontent2202 = new BitSet(new long[]{0xFFFFFFFFFFE77FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_table_italcontentpart_in_table_italcontent2211 = new BitSet(new long[]{0xFFFFFFFFFFE77FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_table_italcontent2216 = new BitSet(new long[]{0xFFFFFFFFFFE77FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_EOF_in_table_italcontent2224 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_formattedcontent_in_table_boldcontentpart2242 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ital_markup_in_table_boldcontentpart2249 = new BitSet(new long[]{0xFFFFFFFFFFF77FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_table_bolditalcontent_in_table_boldcontentpart2256 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_ital_markup_in_table_boldcontentpart2263 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_bold_markup_in_table_italcontentpart2280 = new BitSet(new long[]{0xFFFFFFFFFFE77FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_table_bolditalcontent_in_table_italcontentpart2287 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_bold_markup_in_table_italcontentpart2294 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_formattedcontent_in_table_italcontentpart2306 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_table_bolditalcontent2322 = new BitSet(new long[]{0xFFFFFFFFFFE57FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_table_formattedcontent_in_table_bolditalcontent2331 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_onestar_in_table_bolditalcontent2336 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EOF_in_table_bolditalcontent2344 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_unformattedelement_in_table_formattedcontent2364 = new BitSet(new long[]{0xFFFFFFFFFFE57FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_table_unformatted_in_table_unformattedelement2387 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_inlineelement_in_table_unformattedelement2399 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_link_in_table_inlineelement2420 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_image_in_table_inlineelement2430 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_extension_in_table_inlineelement2441 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_nowiki_inline_in_table_inlineelement2451 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_table_unformatted_text_in_table_unformatted2473 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_forced_linebreak_in_table_unformatted2482 = new BitSet(new long[]{0x0000000006000002L});
	public static final BitSet FOLLOW_escaped_in_table_unformatted2495 = new BitSet(new long[]{0x0000000006000002L});
	public static final BitSet FOLLOW_set_in_table_unformatted_text2521 = new BitSet(new long[]{0xFFFFFFFFF8057FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_nowikiblock_open_markup_in_nowiki_block2618 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_nowiki_block_contents_in_nowiki_block2625 = new BitSet(new long[]{0x0000000008000000L});
	public static final BitSet FOLLOW_nowikiblock_close_markup_in_nowiki_block2631 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_paragraph_separator_in_nowiki_block2634 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_nowiki_open_markup_in_nowikiblock_open_markup2647 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_newline_in_nowikiblock_open_markup2650 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NOWIKI_BLOCK_CLOSE_in_nowikiblock_close_markup2662 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_nowiki_open_markup_in_nowiki_inline2677 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_nowiki_inline_contents_in_nowiki_inline2684 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_nowiki_close_markup_in_nowiki_inline2689 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_nowiki_block_contents2710 = new BitSet(new long[]{0xFFFFFFFFF7FFFFF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_set_in_nowiki_inline_contents2744 = new BitSet(new long[]{0xFFFFFFFFEFFF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_horizontalrule_markup_in_horizontalrule2779 = new BitSet(new long[]{0x0000000080008000L});
	public static final BitSet FOLLOW_blanks_in_horizontalrule2784 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_paragraph_separator_in_horizontalrule2790 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_link_open_markup_in_link2812 = new BitSet(new long[]{0xFFFFFFFFDFF77FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_link_address_in_link2818 = new BitSet(new long[]{0x0000000020080000L});
	public static final BitSet FOLLOW_link_description_markup_in_link2824 = new BitSet(new long[]{0xFFFFFFFFDE5F7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_link_description_in_link2834 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_link_close_markup_in_link2842 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_link_interwiki_uri_in_link_address2861 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_40_in_link_address2864 = new BitSet(new long[]{0xFFFFFFFFDFF77FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_link_interwiki_pagename_in_link_address2871 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_link_uri_in_link_address2882 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_41_in_link_interwiki_uri2898 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_42_in_link_interwiki_uri2900 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_43_in_link_interwiki_uri2905 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_link_interwiki_uri2907 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_link_interwiki_uri2909 = new BitSet(new long[]{0x0000400000000000L});
	public static final BitSet FOLLOW_46_in_link_interwiki_uri2911 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_link_interwiki_uri2913 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri2915 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_link_interwiki_uri2917 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri2919 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_49_in_link_interwiki_uri2924 = new BitSet(new long[]{0x0004000000000000L});
	public static final BitSet FOLLOW_50_in_link_interwiki_uri2926 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri2928 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_link_interwiki_uri2930 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_link_interwiki_uri2932 = new BitSet(new long[]{0x0010000000000000L});
	public static final BitSet FOLLOW_52_in_link_interwiki_uri2934 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_53_in_link_interwiki_uri2939 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_link_interwiki_uri2941 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_link_interwiki_uri2943 = new BitSet(new long[]{0x0040000000000000L});
	public static final BitSet FOLLOW_54_in_link_interwiki_uri2945 = new BitSet(new long[]{0x0004000000000000L});
	public static final BitSet FOLLOW_50_in_link_interwiki_uri2947 = new BitSet(new long[]{0x0080000000000000L});
	public static final BitSet FOLLOW_55_in_link_interwiki_uri2949 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_56_in_link_interwiki_uri2954 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_57_in_link_interwiki_uri2956 = new BitSet(new long[]{0x0400000000000000L});
	public static final BitSet FOLLOW_58_in_link_interwiki_uri2958 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_link_interwiki_uri2960 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri2962 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_link_interwiki_uri2964 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri2966 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_59_in_link_interwiki_uri2971 = new BitSet(new long[]{0x0080000000000000L});
	public static final BitSet FOLLOW_55_in_link_interwiki_uri2973 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_60_in_link_interwiki_uri2975 = new BitSet(new long[]{0x2000000000000000L});
	public static final BitSet FOLLOW_61_in_link_interwiki_uri2977 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_62_in_link_interwiki_uri2979 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_60_in_link_interwiki_uri2981 = new BitSet(new long[]{0x0004000000000000L});
	public static final BitSet FOLLOW_50_in_link_interwiki_uri2983 = new BitSet(new long[]{0x0004000000000000L});
	public static final BitSet FOLLOW_50_in_link_interwiki_uri2985 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_59_in_link_interwiki_uri2990 = new BitSet(new long[]{0x0080000000000000L});
	public static final BitSet FOLLOW_55_in_link_interwiki_uri2992 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_63_in_link_interwiki_uri2994 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri2996 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_60_in_link_interwiki_uri2998 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_link_interwiki_uri3000 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3002 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_link_interwiki_uri3004 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3006 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_59_in_link_interwiki_uri3011 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_link_interwiki_uri3013 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3015 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_64_in_link_interwiki_uri3017 = new BitSet(new long[]{0x0800000000000000L});
	public static final BitSet FOLLOW_59_in_link_interwiki_uri3019 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_link_interwiki_uri3021 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3023 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_64_in_link_interwiki_uri3025 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_65_in_link_interwiki_uri3030 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_63_in_link_interwiki_uri3032 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_63_in_link_interwiki_uri3034 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_66_in_link_interwiki_uri3036 = new BitSet(new long[]{0x0000400000000000L});
	public static final BitSet FOLLOW_46_in_link_interwiki_uri3038 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
	public static final BitSet FOLLOW_67_in_link_interwiki_uri3040 = new BitSet(new long[]{0x0080000000000000L});
	public static final BitSet FOLLOW_55_in_link_interwiki_uri3042 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_65_in_link_interwiki_uri3047 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
	public static final BitSet FOLLOW_68_in_link_interwiki_uri3049 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_60_in_link_interwiki_uri3051 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_64_in_link_interwiki_uri3053 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_60_in_link_interwiki_uri3055 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_58_in_link_interwiki_uri3060 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_66_in_link_interwiki_uri3062 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_link_interwiki_uri3064 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3066 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_link_interwiki_uri3068 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3070 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_58_in_link_interwiki_uri3075 = new BitSet(new long[]{0x0000400000000000L});
	public static final BitSet FOLLOW_46_in_link_interwiki_uri3077 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_link_interwiki_uri3079 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3081 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_link_interwiki_uri3083 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3085 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_link_interwiki_uri3087 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3089 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_58_in_link_interwiki_uri3094 = new BitSet(new long[]{0x0000400000000000L});
	public static final BitSet FOLLOW_46_in_link_interwiki_uri3096 = new BitSet(new long[]{0x0010000000000000L});
	public static final BitSet FOLLOW_52_in_link_interwiki_uri3098 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
	public static final BitSet FOLLOW_69_in_link_interwiki_uri3100 = new BitSet(new long[]{0x0004000000000000L});
	public static final BitSet FOLLOW_50_in_link_interwiki_uri3102 = new BitSet(new long[]{0x0080000000000000L});
	public static final BitSet FOLLOW_55_in_link_interwiki_uri3104 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_link_interwiki_uri3106 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3108 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_link_interwiki_uri3110 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3112 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_70_in_link_interwiki_uri3117 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_60_in_link_interwiki_uri3119 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_63_in_link_interwiki_uri3121 = new BitSet(new long[]{0x0080000000000000L});
	public static final BitSet FOLLOW_55_in_link_interwiki_uri3123 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_link_interwiki_uri3125 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
	public static final BitSet FOLLOW_71_in_link_interwiki_uri3127 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_57_in_link_interwiki_uri3132 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_64_in_link_interwiki_uri3134 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3136 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
	public static final BitSet FOLLOW_69_in_link_interwiki_uri3138 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_57_in_link_interwiki_uri3140 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_64_in_link_interwiki_uri3142 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_60_in_link_interwiki_uri3144 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
	public static final BitSet FOLLOW_69_in_link_interwiki_uri3146 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_72_in_link_interwiki_uri3151 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3153 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_63_in_link_interwiki_uri3155 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_63_in_link_interwiki_uri3157 = new BitSet(new long[]{0x0004000000000000L});
	public static final BitSet FOLLOW_50_in_link_interwiki_uri3159 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
	public static final BitSet FOLLOW_73_in_link_interwiki_uri3161 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_link_interwiki_uri3163 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3165 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_link_interwiki_uri3167 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3169 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_72_in_link_interwiki_uri3174 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_link_interwiki_uri3176 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3178 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_link_interwiki_uri3180 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3182 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_74_in_link_interwiki_uri3187 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
	public static final BitSet FOLLOW_67_in_link_interwiki_uri3189 = new BitSet(new long[]{0x0080000000000000L});
	public static final BitSet FOLLOW_55_in_link_interwiki_uri3191 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_66_in_link_interwiki_uri3193 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_link_interwiki_uri3195 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_63_in_link_interwiki_uri3197 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_47_in_link_interwiki_uri3202 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3204 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_link_interwiki_uri3206 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3208 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
	public static final BitSet FOLLOW_69_in_link_interwiki_uri3210 = new BitSet(new long[]{0x0080000000000000L});
	public static final BitSet FOLLOW_55_in_link_interwiki_uri3212 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_63_in_link_interwiki_uri3214 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3216 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_60_in_link_interwiki_uri3218 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_75_in_link_interwiki_uri3223 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_link_interwiki_uri3225 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3227 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_link_interwiki_uri3229 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_link_interwiki_uri3231 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_link_interwiki_pagename3251 = new BitSet(new long[]{0xFFFFFFFFDFF77FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_link_descriptionpart_in_link_description3294 = new BitSet(new long[]{0xFFFFFFFFDE5F7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_image_in_link_description3306 = new BitSet(new long[]{0xFFFFFFFFDE5F7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_bold_markup_in_link_descriptionpart3331 = new BitSet(new long[]{0xFFFFFFFFDE1F7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_link_descriptionpart3334 = new BitSet(new long[]{0xFFFFFFFFDE1D7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_link_bold_descriptionpart_in_link_descriptionpart3342 = new BitSet(new long[]{0xFFFFFFFFDE1F7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_link_descriptionpart3347 = new BitSet(new long[]{0xFFFFFFFFDE1F7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_bold_markup_in_link_descriptionpart3357 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ital_markup_in_link_descriptionpart3362 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_link_descriptionpart3365 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_link_ital_descriptionpart_in_link_descriptionpart3374 = new BitSet(new long[]{0xFFFFFFFFDE1F7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_link_descriptionpart3379 = new BitSet(new long[]{0xFFFFFFFFDE1F7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_ital_markup_in_link_descriptionpart3388 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_link_descriptionpart3393 = new BitSet(new long[]{0xFFFFFFFFDE0D7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_link_descriptiontext_in_link_descriptionpart3402 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_link_descriptionpart3405 = new BitSet(new long[]{0xFFFFFFFFDE0D7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_ital_markup_in_link_bold_descriptionpart3425 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_link_boldital_description_in_link_bold_descriptionpart3432 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_ital_markup_in_link_bold_descriptionpart3437 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_link_descriptiontext_in_link_bold_descriptionpart3446 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_bold_markup_in_link_ital_descriptionpart3462 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_link_boldital_description_in_link_ital_descriptionpart3469 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_bold_markup_in_link_ital_descriptionpart3472 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_link_descriptiontext_in_link_ital_descriptionpart3483 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_link_boldital_description3499 = new BitSet(new long[]{0xFFFFFFFFDE0D7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_link_descriptiontext_in_link_boldital_description3508 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_link_boldital_description3511 = new BitSet(new long[]{0xFFFFFFFFDE0D7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_link_descriptiontext_simple_in_link_descriptiontext3534 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_forced_linebreak_in_link_descriptiontext3544 = new BitSet(new long[]{0x0000000006000002L});
	public static final BitSet FOLLOW_escaped_in_link_descriptiontext3556 = new BitSet(new long[]{0x0000000006000002L});
	public static final BitSet FOLLOW_set_in_link_descriptiontext_simple3581 = new BitSet(new long[]{0xFFFFFFFFD80D7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_set_in_link_uri3682 = new BitSet(new long[]{0xFFFFFFFFDFF77FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_image_open_markup_in_image3723 = new BitSet(new long[]{0xFFFFFFFFBFF77FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_image_uri_in_image3729 = new BitSet(new long[]{0x0000000040080000L});
	public static final BitSet FOLLOW_image_alternative_in_image3739 = new BitSet(new long[]{0x0000000040000000L});
	public static final BitSet FOLLOW_image_close_markup_in_image3751 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_image_uri3770 = new BitSet(new long[]{0xFFFFFFFFBFF77FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_image_alternative_markup_in_image_alternative3805 = new BitSet(new long[]{0xFFFFFFFFBE1F7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_image_alternativepart_in_image_alternative3814 = new BitSet(new long[]{0xFFFFFFFFBE1F7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_bold_markup_in_image_alternativepart3840 = new BitSet(new long[]{0x0000000000120000L});
	public static final BitSet FOLLOW_onestar_in_image_alternativepart3843 = new BitSet(new long[]{0xFFFFFFFFBE1F7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_image_bold_alternativepart_in_image_alternativepart3852 = new BitSet(new long[]{0x0000000000120000L});
	public static final BitSet FOLLOW_onestar_in_image_alternativepart3857 = new BitSet(new long[]{0xFFFFFFFFBE1F7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_bold_markup_in_image_alternativepart3864 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ital_markup_in_image_alternativepart3871 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_onestar_in_image_alternativepart3874 = new BitSet(new long[]{0xFFFFFFFFBE0F7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_image_ital_alternativepart_in_image_alternativepart3884 = new BitSet(new long[]{0x0000000000120000L});
	public static final BitSet FOLLOW_onestar_in_image_alternativepart3889 = new BitSet(new long[]{0xFFFFFFFFBE1F7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_ital_markup_in_image_alternativepart3896 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_image_alternativepart3903 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_image_alternativetext_in_image_alternativepart3910 = new BitSet(new long[]{0xFFFFFFFFBE0F7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_image_alternativepart3915 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_ital_markup_in_image_bold_alternativepart3941 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_link_boldital_description_in_image_bold_alternativepart3948 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_ital_markup_in_image_bold_alternativepart3953 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_image_bold_alternativepart3958 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_image_alternativetext_in_image_bold_alternativepart3967 = new BitSet(new long[]{0xFFFFFFFFBE0F7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_image_bold_alternativepart3970 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_bold_markup_in_image_ital_alternativepart3999 = new BitSet(new long[]{0xFFFFFFFFDE0F7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_link_boldital_description_in_image_ital_alternativepart4006 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_bold_markup_in_image_ital_alternativepart4011 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_onestar_in_image_ital_alternativepart4016 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_image_alternativetext_in_image_ital_alternativepart4025 = new BitSet(new long[]{0xFFFFFFFFBE0F7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_image_ital_alternativepart4028 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_image_boldital_alternative4049 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_image_alternativetext_in_image_boldital_alternative4058 = new BitSet(new long[]{0xFFFFFFFFBE0F7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_onestar_in_image_boldital_alternative4061 = new BitSet(new long[]{0xFFFFFFFFBE0D7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_image_alternative_simple_text_in_image_alternativetext4084 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_forced_linebreak_in_image_alternativetext4092 = new BitSet(new long[]{0x0000000002000002L});
	public static final BitSet FOLLOW_set_in_image_alternative_simple_text4119 = new BitSet(new long[]{0xFFFFFFFFBC0D7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_extension_markup_in_extension4212 = new BitSet(new long[]{0xFFFFFFFF7EFF7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_extension_handler_in_extension4215 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_blanks_in_extension4218 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_extension_statement_in_extension4221 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_extension_markup_in_extension4225 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_extension_handler4236 = new BitSet(new long[]{0xFFFFFFFF7EFF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_escaped_in_extension_handler4269 = new BitSet(new long[]{0xFFFFFFFF7EFF7FF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_set_in_extension_statement4283 = new BitSet(new long[]{0xFFFFFFFFFEFFFFF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_escaped_in_extension_statement4304 = new BitSet(new long[]{0xFFFFFFFFFEFFFFF2L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_STAR_in_onestar4326 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ESCAPE_in_escaped4348 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_STAR_in_escaped4351 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_STAR_in_escaped4354 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ESCAPE_in_escaped4361 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_newline_in_paragraph_separator4385 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_EOF_in_paragraph_separator4393 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_blanks_in_whitespaces4405 = new BitSet(new long[]{0x0000000080008002L});
	public static final BitSet FOLLOW_newline_in_whitespaces4409 = new BitSet(new long[]{0x0000000080008002L});
	public static final BitSet FOLLOW_BLANKS_in_blanks4422 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_newline_in_text_lineseparator4432 = new BitSet(new long[]{0x0000000080000002L});
	public static final BitSet FOLLOW_blanks_in_text_lineseparator4437 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EOF_in_text_lineseparator4445 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NEWLINE_in_newline4455 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STAR_in_bold_markup4465 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_STAR_in_bold_markup4468 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ITAL_in_ital_markup4478 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EQUAL_in_heading_markup4488 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_POUND_in_list_ordelem_markup4498 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STAR_in_list_unordelem_markup4508 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_newline_in_list_elemseparator4518 = new BitSet(new long[]{0x0000000080000002L});
	public static final BitSet FOLLOW_blanks_in_list_elemseparator4523 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EOF_in_list_elemseparator4531 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_newline_in_end_of_list4541 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EOF_in_end_of_list4546 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PIPE_in_table_cell_markup4556 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PIPE_in_table_headercell_markup4566 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_EQUAL_in_table_headercell_markup4569 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_newline_in_table_rowseparator4579 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EOF_in_table_rowseparator4584 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NOWIKI_OPEN_in_nowiki_open_markup4594 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NOWIKI_CLOSE_in_nowiki_close_markup4604 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DASH_in_horizontalrule_markup4614 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_DASH_in_horizontalrule_markup4617 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_DASH_in_horizontalrule_markup4620 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_DASH_in_horizontalrule_markup4623 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LINK_OPEN_in_link_open_markup4633 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LINK_CLOSE_in_link_close_markup4643 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PIPE_in_link_description_markup4653 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IMAGE_OPEN_in_image_open_markup4663 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IMAGE_CLOSE_in_image_close_markup4673 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PIPE_in_image_alternative_markup4683 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EXTENSION_in_extension_markup4693 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FORCED_LINEBREAK_in_forced_linebreak4703 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NOWIKI_OPEN_in_synpred1318 = new BitSet(new long[]{0xFFFFFFFFFFFF7FF0L,0x0000000000000FFFL});
	public static final BitSet FOLLOW_set_in_synpred1321 = new BitSet(new long[]{0x0000000000000002L});

}