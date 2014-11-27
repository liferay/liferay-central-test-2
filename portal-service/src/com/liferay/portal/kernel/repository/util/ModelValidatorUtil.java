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

package com.liferay.portal.kernel.repository.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.ContentReference;
import com.liferay.portal.kernel.repository.model.ModelValidator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portlet.documentlibrary.FileSizeException;

/**
 * @author Adolfo Pérez
 */
public class ModelValidatorUtil {

	public static final <T> ModelValidator<T> compose(
		ModelValidator<T>... modelValidators) {

		return new CompositeModelValidator<>(modelValidators);
	}

	public static final ModelValidator<ContentReference>
		getDefaultFileSizeModelValidator() {

		return _DEFAULT_FILE_SIZE_MODEL_VALIDATOR;
	}

	private static final ModelValidator<ContentReference>
		_DEFAULT_FILE_SIZE_MODEL_VALIDATOR =
			new ModelValidator<ContentReference>() {

				@Override
				public void validate(ContentReference contentReference)
					throws PortalException {

					long maxSize = PrefsPropsUtil.getLong(
						PropsKeys.DL_FILE_MAX_SIZE);

					if ((maxSize > 0) &&
						(contentReference.getSize() > maxSize)) {

						throw new FileSizeException(
							contentReference.getSourceFileName());
					}
				}

			};

	private static class CompositeModelValidator<T>
		implements ModelValidator<T> {

		public CompositeModelValidator(ModelValidator<T>... modelValidators) {
			_modelValidators = modelValidators;
		}

		@Override
		public void validate(T t) throws PortalException {
			for (ModelValidator<T> modelValidator : _modelValidators) {
				modelValidator.validate(t);
			}
		}

		private final ModelValidator<T>[] _modelValidators;

	}

}