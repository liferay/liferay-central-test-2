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

package com.liferay.adaptive.media.processor;

/**
 * @author Adolfo Pérez
 */
public class MediaProcessorRuntimeException extends RuntimeException {

	public MediaProcessorRuntimeException() {
	}

	public MediaProcessorRuntimeException(String s) {
		super(s);
	}

	public MediaProcessorRuntimeException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public MediaProcessorRuntimeException(Throwable throwable) {
		super(throwable);
	}

	public static final class InvalidConfiguration
		extends MediaProcessorRuntimeException {

		public InvalidConfiguration() {
		}

		public InvalidConfiguration(String s) {
			super(s);
		}

		public InvalidConfiguration(String s, Throwable throwable) {
			super(s, throwable);
		}

		public InvalidConfiguration(Throwable throwable) {
			super(throwable);
		}

	}

	public static final class IOException
		extends MediaProcessorRuntimeException {

		public IOException() {
		}

		public IOException(String s) {
			super(s);
		}

		public IOException(String s, Throwable throwable) {
			super(s, throwable);
		}

		public IOException(Throwable throwable) {
			super(throwable);
		}

	}

	public static final class MediaPropertyFormatException
		extends MediaProcessorRuntimeException {

		public MediaPropertyFormatException() {
		}

		public MediaPropertyFormatException(String s) {
			super(s);
		}

		public MediaPropertyFormatException(String s, Throwable throwable) {
			super(s, throwable);
		}

		public MediaPropertyFormatException(Throwable throwable) {
			super(throwable);
		}

	}

}