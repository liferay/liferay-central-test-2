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
 * @author Sergio González
 */
public class ImageAdaptiveMediaConfigurationException extends Exception {

	public ImageAdaptiveMediaConfigurationException() {
	}

	public ImageAdaptiveMediaConfigurationException(String s) {
		super(s);
	}

	public ImageAdaptiveMediaConfigurationException(
		String s, Throwable throwable) {

		super(s, throwable);
	}

	public ImageAdaptiveMediaConfigurationException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * This exception is raised when a configuration with the same uuid already exists.
	 */
	public static final class
		DuplicateImageAdaptiveMediaConfigurationException
			extends ImageAdaptiveMediaConfigurationException {

		public DuplicateImageAdaptiveMediaConfigurationException() {
		}

		public DuplicateImageAdaptiveMediaConfigurationException(String s) {
			super(s);
		}

		public DuplicateImageAdaptiveMediaConfigurationException(
			String s, Throwable throwable) {

			super(s, throwable);
		}

		public DuplicateImageAdaptiveMediaConfigurationException(
			Throwable throwable) {

			super(throwable);
		}

	}

	/**
	 * This exception is raised when the height value is not valid.
	 */
	public static final class InvalidHeightException
		extends ImageAdaptiveMediaConfigurationException {

		public InvalidHeightException() {
		}

		public InvalidHeightException(String s) {
			super(s);
		}

		public InvalidHeightException(String s, Throwable throwable) {
			super(s, throwable);
		}

		public InvalidHeightException(Throwable throwable) {
			super(throwable);
		}

	}

	public static final class
		InvalidStateImageAdaptiveMediaConfigurationException
			extends ImageAdaptiveMediaConfigurationException {

		public InvalidStateImageAdaptiveMediaConfigurationException() {
		}

		public InvalidStateImageAdaptiveMediaConfigurationException(String s) {
			super(s);
		}

		public InvalidStateImageAdaptiveMediaConfigurationException(
			String s, Throwable throwable) {

			super(s, throwable);
		}

		public InvalidStateImageAdaptiveMediaConfigurationException(
			Throwable throwable) {

			super(throwable);
		}

	}

	/**
	 * This exception is raised when the height value is not valid.
	 */
	public static final class InvalidWidthException
		extends ImageAdaptiveMediaConfigurationException {

		public InvalidWidthException() {
		}

		public InvalidWidthException(String s) {
			super(s);
		}

		public InvalidWidthException(String s, Throwable throwable) {
			super(s, throwable);
		}

		public InvalidWidthException(Throwable throwable) {
			super(throwable);
		}

	}

	/**
	 * This exception is raised when a configuration does not exist.
	 *
	 * @review
	 */
	public static final class
		NoSuchImageAdaptiveMediaConfigurationException
			extends ImageAdaptiveMediaConfigurationException {

		public NoSuchImageAdaptiveMediaConfigurationException() {
		}

		public NoSuchImageAdaptiveMediaConfigurationException(String s) {
			super(s);
		}

		public NoSuchImageAdaptiveMediaConfigurationException(
			String s, Throwable throwable) {

			super(s, throwable);
		}

		public NoSuchImageAdaptiveMediaConfigurationException(
			Throwable throwable) {

			super(throwable);
		}

	}

}