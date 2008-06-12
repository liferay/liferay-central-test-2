/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.mirage.custom;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.exception.TemplateNotFoundException;
import com.sun.portal.cms.mirage.exception.ValidationException;
import com.sun.portal.cms.mirage.model.custom.Category;
import com.sun.portal.cms.mirage.model.custom.ContentType;
import com.sun.portal.cms.mirage.model.custom.Template;
import com.sun.portal.cms.mirage.service.custom.ContentTypeService;

import java.util.List;

/**
 * <a href="ContentTypeServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Prakash Reddy
 *
 */
public class ContentTypeServiceImpl implements ContentTypeService {

	public void addTemplateToContentType(
			Template template, ContentType contentType)
		throws CMSException, ValidationException {

		throw new UnsupportedOperationException();
	}

	public void assignDefaultTemplate(
			ContentType contentType, Template template)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public boolean checkContentTypeExists(String contentTypeUUID)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public void checkOutTemplate(Template template, ContentType contentType)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public void createContentType(ContentType contentType)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public void deleteContentType(ContentType contentType)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public void deleteTemplateOfContentType(
			ContentType contentType, Template template)
		throws CMSException, TemplateNotFoundException {

		throw new UnsupportedOperationException();
	}

	public void deleteTemplatesOfContentType(
			ContentType contentType, Template[] templatesToBeDeleted)
		throws CMSException, TemplateNotFoundException {

		throw new UnsupportedOperationException();
	}

	public List<Template> getAllVersionsOfTemplate(
			Template template, ContentType contentType)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public List<String> getAvailableContentTypeNames(Category category)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public List<ContentType> getAvailableContentTypes(Category category)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public ContentType getContentTypeByNameAndCategory(
			String contentTypeName, Category category)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public ContentType getContentTypeByUUID(String contentTypeUUID)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public Template getLatestVersionOfTemplate(
			Template template, ContentType contentType)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public Template getTemplateWithUUID(String templateUUID)
		throws TemplateNotFoundException {

		throw new UnsupportedOperationException();
	}

	public boolean isContentTypeEditable(String contentTypeUUID)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public void revertChangesTemplateForTemplate(
			Template template, ContentType contentType)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public void saveNewVersionOfTemplate(
			Template template, ContentType contentType)
		throws CMSException, ValidationException {

		throw new UnsupportedOperationException();
	}

	public void unassignDefaultTemplate(ContentType contentType)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public void updateCategoryOfContentType(ContentType contentType)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public void updateContentType(ContentType contentType)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public void updateTemplateOfContentType(
			Template template, ContentType contentType)
		throws CMSException, ValidationException {

		throw new UnsupportedOperationException();
	}

	public boolean validateTemplate(Template template, ContentType contentType)
		throws ValidationException {

		throw new UnsupportedOperationException();
	}

}