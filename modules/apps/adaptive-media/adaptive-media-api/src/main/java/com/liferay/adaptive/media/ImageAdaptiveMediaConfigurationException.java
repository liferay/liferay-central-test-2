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
		DuplicateImageAdaptiveMediaConfigurationEntryException
			extends ImageAdaptiveMediaConfigurationException {

		public DuplicateImageAdaptiveMediaConfigurationEntryException() {
		}

		public DuplicateImageAdaptiveMediaConfigurationEntryException(
			String s) {

			super(s);
		}

		public DuplicateImageAdaptiveMediaConfigurationEntryException(
			String s, Throwable throwable) {

			super(s, throwable);
		}

		public DuplicateImageAdaptiveMediaConfigurationEntryException(
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
		InvalidStateImageAdaptiveMediaConfigurationEntryException
			extends ImageAdaptiveMediaConfigurationException {

		public InvalidStateImageAdaptiveMediaConfigurationEntryException() {
		}

		public InvalidStateImageAdaptiveMediaConfigurationEntryException(
			String s) {

			super(s);
		}

		public InvalidStateImageAdaptiveMediaConfigurationEntryException(
			String s, Throwable throwable) {

			super(s, throwable);
		}

		public InvalidStateImageAdaptiveMediaConfigurationEntryException(
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
		NoSuchImageAdaptiveMediaConfigurationEntryException
			extends ImageAdaptiveMediaConfigurationException {

		public NoSuchImageAdaptiveMediaConfigurationEntryException() {
		}

		public NoSuchImageAdaptiveMediaConfigurationEntryException(String s) {
			super(s);
		}

		public NoSuchImageAdaptiveMediaConfigurationEntryException(
			String s, Throwable throwable) {

			super(s, throwable);
		}

		public NoSuchImageAdaptiveMediaConfigurationEntryException(
			Throwable throwable) {

			super(throwable);
		}

	}

}