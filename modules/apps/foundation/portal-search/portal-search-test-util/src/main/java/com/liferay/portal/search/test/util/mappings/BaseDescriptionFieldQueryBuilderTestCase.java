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

package com.liferay.portal.search.test.util.mappings;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.internal.analysis.DescriptionFieldQueryBuilder;
import com.liferay.portal.search.internal.analysis.SimpleKeywordTokenizer;

import java.util.Arrays;

/**
 * @author André de Oliveira
 * @author Rodrigo Paulino
 */
public abstract class BaseDescriptionFieldQueryBuilderTestCase
	extends BaseFieldQueryBuilderTestCase {

	@Override
	protected FieldQueryBuilder createFieldQueryBuilder() {
		return new DescriptionFieldQueryBuilder() {
			{
				keywordTokenizer = new SimpleKeywordTokenizer();
			}
		};
	}

	@Override
	protected String getField() {
		return Field.DESCRIPTION;
	}

	protected void testBasicWordMatches() throws Exception {
		addDocument("LOOKing for DOCUments");
		addDocument("this is a test for description");
		addDocument("Description Test");
		addDocument("TESTING THE DESCRIPTION");

		assertSearch("description", 3);
		assertSearch("for", 2);
		assertSearch("looking", 1);

		assertSearch("\"a\"", 1);
		assertSearch("\"description\"", 3);
		assertSearch("\"LOOKING\"", 1);
		assertSearch("\"TEST\"", 2);
		assertSearch("\"testing\"", 1);

		assertSearchNoHits("look");

		assertSearchNoHits("\"look\"");
	}

	protected void testExactMatchBoost() throws Exception {
		addDocument("one two three four five six seven eight");
		addDocument("one two three four five six");
		addDocument("three four five six seven");
		addDocument("one two four five seven eight");

		assertSearch(
			"one two four five seven eight",
			Arrays.asList(
				"one two four five seven eight",
				"one two three four five six seven eight",
				"one two three four five six", "three four five six seven"));
		assertSearch(
			"three four five six seven",
			Arrays.asList(
				"three four five six seven",
				"one two three four five six seven eight",
				"one two three four five six",
				"one two four five seven eight"));

		assertSearch(
			"\"one two\" \"three four\" \"five six\"",
			Arrays.asList(
				"one two three four five six",
				"one two three four five six seven eight"));
		assertSearch(
			"\"three four\" five \"six seven\"",
			Arrays.asList(
				"three four five six seven",
				"one two three four five six seven eight"));
	}

	protected void testMultiwordPhrasePrefixes() throws Exception {
		addDocument("Name Tags");
		addDocument("Names Tab");
		addDocument("Tag Names");
		addDocument("Tabs Names Tags");

		assertSearch("\"name ta*\"", 1);
		assertSearch("\"name tag*\"", 1);
		assertSearch("\"name tags*\"", 1);
		assertSearch("\"names ta*\"", 2);
		assertSearch("\"names tab*\"", 1);
		assertSearch("\"names tag*\"", 1);
		assertSearch("\"names tags*\"", 1);
		assertSearch("\"tabs name*\"", 1);
		assertSearch("\"tabs names ta*\"", 1);
		assertSearch("\"tabs names tag*\"", 1);
		assertSearch("\"tabs names tags*\"", 1);
		assertSearch("\"tabs names*\"", 1);
		assertSearch("\"tag na*\"", 1);
		assertSearch("\"tag name*\"", 1);
		assertSearch("\"tag names*\"", 1);

		assertSearchNoHits("\"name tab*\"");
		assertSearchNoHits("\"name tabs*\"");
		assertSearchNoHits("\"names tabs*\"");
		assertSearchNoHits("\"tab na*\"");
		assertSearchNoHits("\"tab names*\"");
		assertSearchNoHits("\"tabs na ta*\"");
		assertSearchNoHits("\"tabs name ta*\"");
		assertSearchNoHits("\"tags na ta*\"");
		assertSearchNoHits("\"tags names tabs*\"");
		assertSearchNoHits("\"tags names*\"");
		assertSearchNoHits("\"zz na*\"");
		assertSearchNoHits("\"zz name*\"");
		assertSearchNoHits("\"zz names*\"");
		assertSearchNoHits("\"zz ta*\"");
		assertSearchNoHits("\"zz tab*\"");
		assertSearchNoHits("\"zz tabs*\"");
		assertSearchNoHits("\"zz tag*\"");
		assertSearchNoHits("\"zz tags*\"");
	}

	protected void testMultiwordPrefixes() throws Exception {
		addDocument("Name Tags");
		addDocument("Names Tab");
		addDocument("Tag Names");
		addDocument("Tabs Names Tags");

		assertSearch("name ta", 1);
		assertSearch("name tab", 2);
		assertSearch("name tabs", 2);
		assertSearch("name tag", 2);
		assertSearch("name tags", 2);
		assertSearch("names ta", 3);
		assertSearch("names tab", 3);
		assertSearch("names tabs", 3);
		assertSearch("names tag", 3);
		assertSearch("names tags", 4);
		assertSearch("tab na", 1);
		assertSearch("tab names", 3);
		assertSearch("tabs na ta", 1);
		assertSearch("tabs names tags", 4);
		assertSearch("tabs names", 3);
		assertSearch("tag na", 1);
		assertSearch("tag name", 2);
		assertSearch("tag names", 3);
		assertSearch("tags na ta", 2);
		assertSearch("tags names tabs", 4);
		assertSearch("tags names", 4);
		assertSearch("zz name", 1);
		assertSearch("zz names", 3);
		assertSearch("zz tab", 1);
		assertSearch("zz tabs", 1);
		assertSearch("zz tag", 1);
		assertSearch("zz tags", 2);

		assertSearchNoHits("zz na");
		assertSearchNoHits("zz ta");
	}

	protected void testNull() throws Exception {
		addDocument("null");
		addDocument("anulled");
		addDocument("The word null is in this sentence");
		addDocument("Ultimate Nullifier");
		addDocument("llun");

		assertSearch(
			"null", Arrays.asList("null", "The word null is in this sentence"));
	}

	protected void testNumbers() throws Exception {
		addDocument("Description with 1 number");
		addDocument("Description with NO numbers");
		addDocument("4ever");

		assertSearch("1", 1);
		assertSearch("4ever", 1);
		assertSearch("4EVER", 1);

		assertSearch("\"1\"", 1);
		assertSearch("\"4ever\"", 1);
		assertSearch("\"4EVER\"", 1);

		assertSearchNoHits("4");

		assertSearchNoHits("\"4\"");

		assertSearchNoHits("FOREVER");
	}

	protected void testPhrasePrefixes() throws Exception {
		addDocument("Nametag");
		addDocument("NA-META-G");
		addDocument("Tag Name");
		addDocument("TAG1");

		assertSearch("\"me*\"", 1);
		assertSearch("\"meta\"", 1);
		assertSearch("\"namet*\"", 1);
		assertSearch("\"Ta*\"", 2);
		assertSearch("\"tag\"", 1);

		assertSearch("\"*me*\"", 1);
		assertSearch("\"*met*\"", 1);
		assertSearch("\"*namet*\"", 1);
		assertSearch("\"*Ta*\"", 2);

		assertSearchNoHits("\"met\"");
		assertSearchNoHits("\"Namet\"");
		assertSearchNoHits("\"Ta\"");

		assertSearchNoHits("\"*me\"");
		assertSearchNoHits("\"*namet\"");
		assertSearchNoHits("\"*Ta\"");
	}

	protected void testPhrasePrefixRequiresTrailingStar() throws Exception {
		addDocument("Nametag");
		addDocument("NA-META-G");
		addDocument("Tag Name");
		addDocument("TAG1");

		assertSearch("\"NAM*\"", 2);
		assertSearch("\"*nam*\"", 2);

		assertSearchNoHits("\"Nam\"");
		assertSearchNoHits("\"*NAM\"");
	}

	protected void testPhrases() throws Exception {
		addDocument("Names of Tags");
		addDocument("More names of tags here");

		assertSearch("\"HERE\"", 1);
		assertSearch("\"more\"   names   \"here\"", 1);
		assertSearch("\"More\"", 1);
		assertSearch("\"more\"", 1);
		assertSearch("\"names of tags\"", 2);
		assertSearch("\"NAmes\" \"TAGS\"", 2);
		assertSearch("\"names\" MORE \"tags\"", 1);
		assertSearch("\"names\" of \"tAgs\"", 2);
		assertSearch("\"Tags here\"", 1);
		assertSearch("\"Tags\" here", 1);
		assertSearch("\"TAGS\"", 2);

		assertSearch("\"   more   \"   tags   \"   here   \"", 1);

		assertSearchNoHits("\"more\" other \"here\"");
		assertSearchNoHits("\"name\" of \"tags\"");
	}

	protected void testStopwords() throws Exception {
		addDocument("Names of Tags");
		addDocument("More names of tags");

		assertSearch("of", 2);

		assertSearch("Names of tags", 2);
		assertSearch("tags names", 2);
	}

	protected void testWordPrefixes() throws Exception {
		addDocument("Nametag");
		addDocument("NA-META-G");
		addDocument("Tag Name");
		addDocument("TAG1");

		assertSearch("Na", 1);
		assertSearch("NA*", 1);
		assertSearch("tag", 1);

		assertSearch("*NA", 1);
		assertSearch("*NA*", 1);

		assertSearchNoHits("me");
		assertSearchNoHits("me*");
		assertSearchNoHits("met");
		assertSearchNoHits("Namet");
		assertSearchNoHits("namet*");
		assertSearchNoHits("Ta");
		assertSearchNoHits("Ta*");

		assertSearchNoHits("*me");
		assertSearchNoHits("*me*");
		assertSearchNoHits("*met*");
		assertSearchNoHits("*namet");
		assertSearchNoHits("*namet*");
		assertSearchNoHits("*Ta");
		assertSearchNoHits("*Ta*");
	}

}