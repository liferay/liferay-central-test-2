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

package com.liferay.source.formatter.checkstyle.util;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class DetailASTUtil {

	public static DetailAST findTypeAST(DetailAST methodAST, String name) {
		List<DetailAST> localVariableDefASTList =
			DetailASTUtil.getAllChildTokens(
				methodAST, TokenTypes.VARIABLE_DEF, true);

		DetailAST typeAST = _findTypeAST(localVariableDefASTList, name);

		if (typeAST != null) {
			return typeAST;
		}

		List<DetailAST> parameterDefASTList = DetailASTUtil.getParameterDefs(
			methodAST);

		typeAST = _findTypeAST(parameterDefASTList, name);

		if (typeAST != null) {
			return typeAST;
		}

		DetailAST classAST = methodAST.getParent();

		while (classAST != null) {
			List<DetailAST> globalVariableDefASTList =
				DetailASTUtil.getAllChildTokens(
					classAST, TokenTypes.VARIABLE_DEF, false);

			typeAST = _findTypeAST(globalVariableDefASTList, name);

			if (typeAST != null) {
				return typeAST;
			}

			classAST = classAST.getParent();
		}

		return null;
	}

	public static List<DetailAST> getAllChildTokens(
		DetailAST detailAST, int tokenType, boolean recursive) {

		return _getAllChildTokens(detailAST, tokenType, recursive, null);
	}

	public static List<DetailAST> getMethodCalls(
		DetailAST detailAST, String className, String methodName) {

		List<DetailAST> list = new ArrayList<>();

		List<DetailAST> methodCallASTList = getAllChildTokens(
			detailAST, TokenTypes.METHOD_CALL, true);

		for (DetailAST methodCallAST : methodCallASTList) {
			DetailAST dotAST = methodCallAST.findFirstToken(TokenTypes.DOT);

			if (dotAST == null) {
				continue;
			}

			List<DetailAST> nameASTList = getAllChildTokens(
				dotAST, TokenTypes.IDENT, false);

			if (nameASTList.size() != 2) {
				continue;
			}

			DetailAST classNameAST = nameASTList.get(0);
			DetailAST methodNameAST = nameASTList.get(1);

			String methodCallClassName = classNameAST.getText();
			String methodCallMethodName = methodNameAST.getText();

			if (methodCallClassName.equals(className) &&
				methodCallMethodName.equals(methodName)) {

				list.add(methodCallAST);
			}
		}

		return list;
	}

	public static List<DetailAST> getParameterDefs(DetailAST detailAST) {
		List<DetailAST> list = new ArrayList<>();

		if ((detailAST.getType() != TokenTypes.CTOR_DEF) &&
			(detailAST.getType() != TokenTypes.METHOD_DEF)) {

			return list;
		}

		DetailAST parametersAST= detailAST.findFirstToken(
			TokenTypes.PARAMETERS);

		return getAllChildTokens(
			parametersAST, TokenTypes.PARAMETER_DEF, false);
	}

	public static List<String> getParameterNames(DetailAST detailAST) {
		List<String> parameterNames = new ArrayList<>();

		for (DetailAST parameterDefinitionAST : getParameterDefs(detailAST)) {
			DetailAST identAST = parameterDefinitionAST.findFirstToken(
				TokenTypes.IDENT);

			parameterNames.add(identAST.getText());
		}

		return parameterNames;
	}

	public static boolean isCollection(DetailAST detailAST) {
		if (detailAST.getType() != TokenTypes.VARIABLE_DEF) {
			return false;
		}

		DetailAST typeAST = detailAST.findFirstToken(TokenTypes.TYPE);

		DetailAST typeArgumentsAST = typeAST.findFirstToken(
			TokenTypes.TYPE_ARGUMENTS);

		if (typeArgumentsAST == null) {
			return false;
		}

		DetailAST nameAST = typeAST.findFirstToken(TokenTypes.IDENT);

		String name = nameAST.getText();

		if (name.matches(".*(Collection|List|Map|Set)")) {
			return true;
		}

		return false;
	}

	private static DetailAST _findTypeAST(
		List<DetailAST> defASTList, String name) {

		for (DetailAST defAST : defASTList) {
			DetailAST nameAST = defAST.findFirstToken(TokenTypes.IDENT);

			String curName = nameAST.getText();

			if (curName.equals(name)) {
				return defAST.findFirstToken(TokenTypes.TYPE);
			}
		}

		return null;
	}

	private static List<DetailAST> _getAllChildTokens(
		DetailAST detailAST, int tokenType, boolean recursive,
		List<DetailAST> list) {

		if (list == null) {
			list = new ArrayList<>();
		}

		DetailAST childAST = detailAST.getFirstChild();

		while (childAST != null) {
			if (childAST.getType() == tokenType) {
				list.add(childAST);
			}

			if (recursive) {
				list = _getAllChildTokens(childAST, tokenType, recursive, list);
			}

			childAST = childAST.getNextSibling();
		}

		return list;
	}

}