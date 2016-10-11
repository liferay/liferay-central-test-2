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
 *
 * @review
 */
public class AdaptiveMediaRuntimeException extends RuntimeException {

	public AdaptiveMediaRuntimeException() {
	}

	public AdaptiveMediaRuntimeException(String s) {
		super(s);
	}

	public AdaptiveMediaRuntimeException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public AdaptiveMediaRuntimeException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Exception thrown when a value serialized as a String cannot be converted
	 * by a {@link AdaptiveMediaAttribute}.
	 *
	 * @review
	 */
	public static final class AdaptiveMediaAttributeFormatException
		extends AdaptiveMediaRuntimeException {

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

	/**
	 * Exception thrown when there is a processor configuration error.
	 *
	 * @review
	 */
	public static final class InvalidConfiguration
		extends AdaptiveMediaRuntimeException {

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

	/**
	 * Exception used to wrap instances {@link java.io.IOException}. This kind
	 * of exception is considered a system error, so it is reasonable to wrap
	 * it inside a RuntimeException
	 *
	 * @review
	 */
	public static final class IOException
		extends AdaptiveMediaRuntimeException {

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

	/**
	 * Exception used to wrap instances {@link
	 * java.io.UnsupportedEncodingException} This kind of exception is
	 * considered a system error, so it is reasonable to wrap it inside a
	 * RuntimeException
	 *
	 * @review
	 */
	public static final class UnsupportedEncodingException
		extends AdaptiveMediaRuntimeException {

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