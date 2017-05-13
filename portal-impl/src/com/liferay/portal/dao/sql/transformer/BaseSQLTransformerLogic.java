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

package com.liferay.portal.dao.sql.transformer;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manuel de la Peña
 * @author Brian Wing Shun Chan
 */
public abstract class BaseSQLTransformerLogic implements SQLTransformerLogic {

	public BaseSQLTransformerLogic(DB db) {
		_db = db;
	}

	@Override
	public Function<String, String>[] getFunctions() {
		return _functions;
	}

	protected Function<String, String> getBitwiseCheckFunction() {
		Pattern pattern = getBitwiseCheckPattern();

		return (String sql) -> replaceBitwiseCheck(pattern.matcher(sql));
	}

	protected Pattern getBitwiseCheckPattern() {
		return Pattern.compile("BITAND\\(\\s*(.+?)\\s*,\\s*(.+?)\\s*\\)");
	}

	protected Function<String, String> getBooleanFunction() {
		return (String sql) -> StringUtil.replace(
			sql, new String[] {"[$FALSE$]", "[$TRUE$]"},
			new String[] {_db.getTemplateFalse(), _db.getTemplateTrue()});
	}

	protected Function<String, String> getCastClobTextFunction() {
		Pattern pattern = getCastClobTextPattern();

		return (String sql) -> replaceCastClobText(pattern.matcher(sql));
	}

	protected Pattern getCastClobTextPattern() {
		return Pattern.compile(
			"CAST_CLOB_TEXT\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
	}

	protected Function<String, String> getCastLongFunction() {
		Pattern pattern = getCastLongPattern();

		return (String sql) -> replaceCastLong(pattern.matcher(sql));
	}

	protected Pattern getCastLongPattern() {
		return Pattern.compile(
			"CAST_LONG\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
	}

	protected Function<String, String> getCastTextFunction() {
		Pattern pattern = getCastTextPattern();

		return (String sql) -> replaceCastText(pattern.matcher(sql));
	}

	protected Pattern getCastTextPattern() {
		return Pattern.compile(
			"CAST_TEXT\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
	}

	protected Function<String, String> getInstrFunction() {
		Pattern pattern = getInstrPattern();

		return (String sql) -> replaceInstr(pattern.matcher(sql));
	}

	protected Pattern getInstrPattern() {
		return Pattern.compile(
			"INSTR\\(\\s*(.+?)\\s*,\\s*(.+?)\\s*\\)", Pattern.CASE_INSENSITIVE);
	}

	protected Function<String, String> getIntegerDivisionFunction() {
		Pattern pattern = getIntegerDivisionPattern();

		return (String sql) -> replaceIntegerDivision(pattern.matcher(sql));
	}

	protected Pattern getIntegerDivisionPattern() {
		return Pattern.compile(
			"INTEGER_DIV\\(\\s*(.+?)\\s*,\\s*(.+?)\\s*\\)",
			Pattern.CASE_INSENSITIVE);
	}

	protected Function<String, String> getModFunction() {
		Pattern pattern = getModPattern();

		return (String sql) -> replaceMod(pattern.matcher(sql));
	}

	protected Pattern getModPattern() {
		return Pattern.compile(
			"MOD\\(\\s*(.+?)\\s*,\\s*(.+?)\\s*\\)", Pattern.CASE_INSENSITIVE);
	}

	protected Function<String, String> getNullDateFunction() {
		return (String sql) -> StringUtil.replace(sql, "[$NULL_DATE$]", "NULL");
	}

	protected Function<String, String> getSubstrFunction() {
		Pattern pattern = getSubstrPattern();

		return (String sql) -> replaceSubstr(pattern.matcher(sql));
	}

	protected Pattern getSubstrPattern() {
		return Pattern.compile(
			"SUBSTR\\(\\s*(.+?)\\s*,\\s*(.+?)\\s*,\\s*(.+?)\\s*\\)",
			Pattern.CASE_INSENSITIVE);
	}

	protected String replaceBitwiseCheck(Matcher matcher) {
		return matcher.replaceAll("($1 & $2)");
	}

	protected String replaceCastClobText(Matcher matcher) {
		return replaceCastText(matcher);
	}

	protected String replaceCastLong(Matcher matcher) {
		return matcher.replaceAll("$1");
	}

	protected String replaceCastText(Matcher matcher) {
		return matcher.replaceAll("$1");
	}

	protected String replaceInstr(Matcher matcher) {
		return matcher.replaceAll("CHARINDEX($2, $1)");
	}

	protected String replaceIntegerDivision(Matcher matcher) {
		return matcher.replaceAll("$1 / $2");
	}

	protected String replaceMod(Matcher matcher) {
		return matcher.replaceAll("$1 % $2");
	}

	protected String replaceSubstr(Matcher matcher) {
		return matcher.replaceAll("SUBSTRING($1, $2, $3)");
	}

	protected void setFunctions(Function... functions) {
		_functions = functions;
	}

	private final DB _db;
	private Function[] _functions;

}