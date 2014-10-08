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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class SourceFormatter {

	public static void main(String[] args) {
		try {
			SourceFormatter sourceFormatter = SourceFormatterUtil.create(
				false, false, true, true);

			sourceFormatter.format();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SourceFormatter(
			boolean useProperties, boolean throwException, boolean printErrors,
			boolean autoFix)
		throws Exception {

		_useProperties = useProperties;
		_throwException = throwException;
		_printErrors = printErrors;
		_autoFix = autoFix;
	}

	public void format() throws Exception {
		Thread thread1 = new Thread () {

			@Override
			public void run() {
				try {
					List<SourceProcessor> sourceProcessors =
						new ArrayList<SourceProcessor>();

					sourceProcessors.add(
						CSSSourceProcessor.class.newInstance());
					sourceProcessors.add(
						FTLSourceProcessor.class.newInstance());
					sourceProcessors.add(
						JSPSourceProcessor.class.newInstance());
					sourceProcessors.add(JSSourceProcessor.class.newInstance());
					sourceProcessors.add(
						PropertiesSourceProcessor.class.newInstance());
					sourceProcessors.add(SHSourceProcessor.class.newInstance());
					sourceProcessors.add(
						SQLSourceProcessor.class.newInstance());
					sourceProcessors.add(
						TLDSourceProcessor.class.newInstance());

					for (SourceProcessor sourceProcessor : sourceProcessors) {
						_runSourceProcessor(sourceProcessor);
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

		};

		Thread thread2 = new Thread () {

			@Override
			public void run() {
				try {
					List<SourceProcessor> sourceProcessors =
						new ArrayList<SourceProcessor>();

					sourceProcessors.add(
						JavaSourceProcessor.class.newInstance());
					sourceProcessors.add(
						XMLSourceProcessor.class.newInstance());

					for (SourceProcessor sourceProcessor : sourceProcessors) {
						_runSourceProcessor(sourceProcessor);
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

		};

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();

		if (_throwException) {
			if (!_errorMessages.isEmpty()) {
				throw new Exception(StringUtil.merge(_errorMessages, "\n"));
			}

			if (_firstSourceMismatchException != null) {
				throw _firstSourceMismatchException;
			}
		}
	}

	public Tuple format(String fileName) throws Exception {
		SourceProcessor sourceProcessor = null;

		if (fileName.endsWith(".testjava")) {
			sourceProcessor = JavaSourceProcessor.class.newInstance();
		}
		else if (fileName.endsWith(".testsql")) {
			sourceProcessor = SQLSourceProcessor.class.newInstance();
		}
		else if (fileName.endsWith(".testxml")) {
			sourceProcessor = XMLSourceProcessor.class.newInstance();
		}

		if (sourceProcessor == null) {
			return null;
		}

		String newContent = sourceProcessor.format(
			fileName, _useProperties, _printErrors, _autoFix);

		return new Tuple(newContent, sourceProcessor.getErrorMessages());
	}

	private void _runSourceProcessor(SourceProcessor sourceProcessor)
		throws Exception {

		sourceProcessor.format(_useProperties, _printErrors, _autoFix);

		_errorMessages.addAll(sourceProcessor.getErrorMessages());

		if (_firstSourceMismatchException == null) {
			_firstSourceMismatchException =
				sourceProcessor.getFirstSourceMismatchException();
		}
	}

	private boolean _autoFix;
	private Set<String> _errorMessages = new LinkedHashSet<String>();
	private SourceMismatchException _firstSourceMismatchException;
	private boolean _printErrors;
	private boolean _throwException;
	private boolean _useProperties;

}