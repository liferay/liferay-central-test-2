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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.mirage.service;

import com.sun.portal.cms.mirage.model.custom.Category;
import com.sun.portal.cms.mirage.model.custom.ContentType;
import com.sun.portal.cms.mirage.model.custom.OptionalCriteria;
import com.sun.portal.cms.mirage.model.custom.Template;
import com.sun.portal.cms.mirage.model.custom.UpdateCriteria;
import com.sun.portal.cms.mirage.model.search.SearchCriteria;
import com.sun.portal.cms.mirage.service.custom.ContentTypeService;

import java.util.List;

/**
 * <a href="ContentTypeServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Prakash Reddy
 * @author Karthik Sudarshan
 *
 */
public class ContentTypeServiceImpl implements ContentTypeService {

	public void addTemplateToContentType(
		Template template, ContentType contentType) {

		throw new UnsupportedOperationException();
	}

	public void assignDefaultTemplate(
		ContentType contentType, Template template) {

		throw new UnsupportedOperationException();
	}

	public boolean checkContentTypeExists(String contentTypeUUID) {
		throw new UnsupportedOperationException();
	}

	public void checkOutTemplate(Template template, ContentType contentType) {
		throw new UnsupportedOperationException();
	}

	public int contentTypeSearchCount(
		Category category, SearchCriteria searchCriteria) {

		throw new UnsupportedOperationException();
	}

	public void createContentType(ContentType contentType) {
		throw new UnsupportedOperationException();
	}

	public void deleteContentType(ContentType contentType) {
		throw new UnsupportedOperationException();
	}

	public void deleteTemplateOfContentType(
		ContentType contentType, Template template) {

		throw new UnsupportedOperationException();
	}

	public void deleteTemplatesOfContentType(
		ContentType contentType, Template[] templatesToBeDeleted) {

		throw new UnsupportedOperationException();
	}

	public List<Template> getAllVersionsOfTemplate(
		Template template, ContentType contentType) {

		throw new UnsupportedOperationException();
	}

	public List<String> getAvailableContentTypeNames(Category category) {
		throw new UnsupportedOperationException();
	}

	public List<ContentType> getAvailableContentTypes(Category category) {
		throw new UnsupportedOperationException();
	}

	public ContentType getContentType(ContentType contentType) {
		throw new UnsupportedOperationException();
	}

	public ContentType getContentType(
		ContentType contentType, OptionalCriteria optionalCriteria) {

		throw new UnsupportedOperationException();
	}

	public ContentType getContentTypeByNameAndCategory(
		String contentTypeName, Category category) {

		throw new UnsupportedOperationException();
	}

	public ContentType getContentTypeByUUID(String contentTypeUUID) {
		throw new UnsupportedOperationException();
	}

	public Template getLatestVersionOfTemplate(
		Template template, ContentType contentType) {

		throw new UnsupportedOperationException();
	}

	public Template getTemplate(Template template, OptionalCriteria criteria) {
		throw new UnsupportedOperationException();
	}

	public List<Template> getTemplates(
		ContentType contentType, Template template, OptionalCriteria criteria) {

		throw new UnsupportedOperationException();
	}

	public int getTemplatesCount(
		ContentType contentType, Template template, OptionalCriteria criteria) {

		throw new UnsupportedOperationException();
	}

	public Template getTemplateWithUUID(String templateUUID) {
		throw new UnsupportedOperationException();
	}

	public boolean isContentTypeEditable(String contentTypeUUID) {
		throw new UnsupportedOperationException();
	}

	public void revertChangesTemplateForTemplate(
		Template template, ContentType contentType) {

		throw new UnsupportedOperationException();
	}

	public void saveNewVersionOfTemplate(
		Template template, ContentType contentType) {

		throw new UnsupportedOperationException();
	}

	public List<ContentType> searchContentTypes(SearchCriteria searchCriteria) {
		throw new UnsupportedOperationException();
	}

	public List<ContentType> searchContentTypesByCategory(
		Category category, SearchCriteria searchCriteria) {

		throw new UnsupportedOperationException();
	}

	public List<Template> searchTemplates(SearchCriteria searchCriteria) {
		throw new UnsupportedOperationException();
	}

	public int searchTemplatesCount(SearchCriteria searchCriteria) {
		throw new UnsupportedOperationException();
	}

	public List<Template> searchTemplatesOfContentType(
		ContentType contentType, SearchCriteria criteria) {

		throw new UnsupportedOperationException();
	}

	public void unassignDefaultTemplate(ContentType contentType) {
		throw new UnsupportedOperationException();
	}

	public void updateCategoryOfContentType(ContentType contentType) {
		throw new UnsupportedOperationException();
	}

	public void updateContentType(ContentType contentType) {
		throw new UnsupportedOperationException();
	}

	public void updateContentType(
		ContentType contentType, UpdateCriteria updateCriteria) {

		throw new UnsupportedOperationException();
	}

	public void updateTemplateOfContentType(
		Template template, ContentType contentType) {

		throw new UnsupportedOperationException();
	}

	public void updateTemplateOfContentType(
		Template template, ContentType contentType, UpdateCriteria criteria) {

		throw new UnsupportedOperationException();
	}

	public boolean validateTemplate(
		Template template, ContentType contentType) {

		throw new UnsupportedOperationException();
	}

}