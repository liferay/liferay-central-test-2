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

import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class MethodNameCheck
	extends com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck {

	@Override
	protected boolean mustCheckName(DetailAST detailAST) {
		List<DetailAST> annotationASTList = DetailASTUtil.getAllChildTokens(
			detailAST, TokenTypes.ANNOTATION, true);

		for (DetailAST annotationAST : annotationASTList) {
			DetailAST nameAST = annotationAST.findFirstToken(TokenTypes.IDENT);

			if (nameAST == null) {
				continue;
			}

			String name = nameAST.getText();

			if (name.equals("Reference")) {
				return false;
			}
		}

		DetailAST modifiersAST = detailAST.findFirstToken(TokenTypes.MODIFIERS);

		return shouldCheckInScope(modifiersAST);
	}

}