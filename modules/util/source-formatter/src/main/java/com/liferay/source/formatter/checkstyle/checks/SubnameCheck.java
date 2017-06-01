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

package com.liferay.source.formatter.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtility;

/**
 * @author Hugo Huijser
 */
public class SubnameCheck extends AbstractCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.METHOD_DEF, TokenTypes.PARAMETER_DEF,
			TokenTypes.VARIABLE_DEF
		};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String name = nameAST.getText();

		if (detailAST.getType() == TokenTypes.METHOD_DEF) {
			if (name.matches("(^_?sub|.*Sub)[A-Z].*") &&
				!AnnotationUtility.containsAnnotation(detailAST, "Override")) {

				log(detailAST.getLineNo(), _MSG_METHOD_INVALID_NAME, name);
			}
		}
		else if (name.matches("^_?sub[A-Z].*")) {
			if (detailAST.getType() == TokenTypes.PARAMETER_DEF) {
				log(detailAST.getLineNo(), _MSG_PARAMETER_INVALID_NAME, name);
			}
			else {
				log(detailAST.getLineNo(), _MSG_VARIABLE_INVALID_NAME, name);
			}
		}
	}

	private static final String _MSG_METHOD_INVALID_NAME = "method.invalidName";

	private static final String _MSG_PARAMETER_INVALID_NAME =
		"parameter.invalidName";

	private static final String _MSG_VARIABLE_INVALID_NAME =
		"variable.invalidName";

}