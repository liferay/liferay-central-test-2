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

package com.liferay.ant.bnd.plugin;

import aQute.bnd.header.OSGiHeader;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Instruction;
import aQute.bnd.osgi.Instructions;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Resource;
import aQute.bnd.service.AnalyzerPlugin;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Raymond Augé
 */
public class SassResourcePlugin implements AnalyzerPlugin {

	@Override
	public boolean analyzeJar(Analyzer analyzer) throws Exception {
		Parameters header = OSGiHeader.parseHeader(analyzer.getProperty(_SASS));

		if (header.isEmpty()) {
			return false;
		}

		Instructions instructions = new Instructions(header);

		Jar jar = analyzer.getJar();

		Map<String, Resource> resources = jar.getResources();

		Set<String> keySet = new HashSet<String>(resources.keySet());

		for (String key : keySet) {
			for (Instruction instruction : instructions.keySet()) {
				if (key.contains("/.sass-cache/") && instruction.matches(key)) {
					if (instruction.isNegated()) {
						break;
					}

					Resource resource = jar.remove(key);

					if (resource != null) {
						jar.putResource(
							key.replace("/.sass-cache/", "/"), resource, true);
					}
				}
			}
		}

		return false;
	}

	private static final String _SASS = "-sass";

}