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

package com.liferay.adaptive.media;

/**
 * @author Adolfo Pérez
 */
public class AdaptiveMediaProcessorRuntimeException extends RuntimeException {

	public AdaptiveMediaProcessorRuntimeException() {
	}

	public AdaptiveMediaProcessorRuntimeException(String s) {
		super(s);
	}

	public AdaptiveMediaProcessorRuntimeException(
		String s, Throwable throwable) {

		super(s, throwable);
	}

	public AdaptiveMediaProcessorRuntimeException(Throwable throwable) {
		super(throwable);
	}

	public static final class AdaptiveMediaAttributeFormatException
		extends AdaptiveMediaProcessorRuntimeException {

		public AdaptiveMediaAttributeFormatException() {
		}

		public AdaptiveMediaAttributeFormatException(String s) {
			super(s);
		}

		public AdaptiveMediaAttributeFormatException(
			String s, Throwable throwable) {

			super(s, throwable);
		}

		public AdaptiveMediaAttributeFormatException(Throwable throwable) {
			super(throwable);
		}

	}

	public static final class InvalidConfiguration
		extends AdaptiveMediaProcessorRuntimeException {

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
		extends AdaptiveMediaProcessorRuntimeException {

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

	public static final class UnsupportedEncodingException
		extends AdaptiveMediaProcessorRuntimeException {

		public UnsupportedEncodingException() {
		}

		public UnsupportedEncodingException(String s) {
			super(s);
		}

		public UnsupportedEncodingException(String s, Throwable throwable) {
			super(s, throwable);
		}

		public UnsupportedEncodingException(Throwable throwable) {
			super(throwable);
		}

	}

}