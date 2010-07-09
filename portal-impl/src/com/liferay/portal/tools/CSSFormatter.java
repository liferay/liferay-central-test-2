/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.FileImpl;

import java.io.File;

/**
 * @author Brian Wing Shun Chan
 */
public class CSSFormatter {

	public static void main(String[] args) {
		if (args.length == 1) {
			new CSSFormatter(new File(args[0]));
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public CSSFormatter(File file) {
		try {
			String content = _fileUtil.read(file);

			content = StringUtil.replace(
				content,
				new String[] {
					"*/\n", "*/ /*", "*/" + StringPool.FOUR_SPACES + "/*"
				},
				new String[] {
					"*/\n\n", "*/\n\n/*", "*/\n\n/*"
				});

			_fileUtil.write(file, content, true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static FileImpl _fileUtil = FileImpl.getInstance();

}