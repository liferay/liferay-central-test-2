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
import com.sun.portal.cms.mirage.model.core.User;
import com.sun.portal.cms.mirage.model.custom.Category;
import com.sun.portal.cms.mirage.model.custom.ContentType;
import com.sun.portal.cms.mirage.model.custom.Template;
import com.sun.portal.cms.mirage.service.custom.ContentTypeService;

import java.util.List;

/**
 * <a href="ContentTypeServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class ContentTypeServiceImpl implements ContentTypeService {

	public void addTemplateToContentType(
		Template template, ContentType contentType, User user)
		throws CMSException, ValidationException {
	}

	public void assignDefaultTemplate(
		ContentType contentType, Template template) throws CMSException {
	}

	public boolean checkContentTypeExists(String contentTypeUUID)
		throws CMSException {

		return false;
	}

	public void checkOutTemplate(
			Template template, ContentType contentType, User user)
		throws CMSException {
	}
	public void createContentType(ContentType contentType) throws CMSException {

	}

	public void deleteContentType(ContentType contentType) throws CMSException {
	}

	public void deleteTemplateOfContentType(
			ContentType contentType, Template template)
		throws CMSException, TemplateNotFoundException {
	}

	public void deleteTemplatesOfContentType(
			ContentType contentType, Template[] templatesToBeDeleted)
		throws CMSException, TemplateNotFoundException {
	}

	public List<Template> getAllVersionsOfTemplate(
		Template template, ContentType contentType) throws CMSException {

		return null;
	}

	public List<String> getAvailableContentTypeNames(Category category)
		throws CMSException {

		return null;
	}

	public List<ContentType> getAvailableContentTypes(
		Category category, User user) throws CMSException {

		return null;
	}

	public ContentType getContentTypeByUUID(String contentTypeUUID, User user)
		throws CMSException {

		return null;
	}

	public Template getLatestVersionOfTemplate(
		Template template, ContentType contentType) throws CMSException {

		return null;
	}

	public Template getTemplateWithUUID(String templateUUID)
		throws TemplateNotFoundException {

		return null;
	}

	public boolean isContentTypeEditable(String contentTypeUUID)
		throws CMSException {

		return false;
	}

	public void revertChangesTemplateForTemplate(
		Template checkedOutTemplate, ContentType contentType, User user)
		throws CMSException {
	}

	public void saveNewVersionOfTemplate(
		Template newTemplate, ContentType contentType, User user)
		throws CMSException, ValidationException {
	}

	public void unassignDefaultTemplate(ContentType contentType)
		throws CMSException {
	}

	public void updateCategoryOfContentType(ContentType contentType)
		throws CMSException {
	}

	public void updateContentType(ContentType contentType) throws CMSException {
	}

	public void updateTemplateOfContentType(
			Template template, ContentType contentType, User user)
		throws CMSException, ValidationException {
	}

	public boolean validateTemplate(
			Template template, ContentType testContentType)
		throws ValidationException {

		return false;
	}

}