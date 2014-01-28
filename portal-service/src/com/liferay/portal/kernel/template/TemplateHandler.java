/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.template;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Juan Fernández
 */
@ProviderType
public interface TemplateHandler {

	/**
	 * Returns the class name of the template handler.
	 *
	 * @return the class name of the template handler
	 */
	public String getClassName();

	/**
	 * Returns a list of elements containing the information of the portlet
	 * display templates to be installed by default.
	 *
	 * @return a list of elements containing the information of the portlet
	 *         display templates to be installed by default. These templates
	 *         will be installed when registering the portlet
	 * @throws Exception if an exception occurred assembling the default
	 *         template elements
	 */
	public List<Element> getDefaultTemplateElements() throws Exception;

	/**
	 * Returns the name of the template handler.
	 *
	 * @param  locale the locale of the template handler name to get
	 * @return the name of the template handler
	 */
	public String getName(Locale locale);

	/**
	 * Returns the name of the resource the template is associated with.
	 * Permissions on the resource are checked when adding a new template.
	 *
	 * @return the name of the resource
	 */
	public String getResourceName();

	/**
	 * Returns the list of restricted variables that are excluded from the
	 * template context.
	 *
	 * @param  language the language of the template for which the variables are
	 *         restricted
	 * @return the list of restricted variables that are excluded from the
	 *         template context
	 */
	public String[] getRestrictedVariables(String language);

	/**
	 * Returns the content help for the template.
	 *
	 * @param  language the language of the template for which the content help
	 *         applies
	 * @return the content help for the template
	 */
	public String getTemplatesHelpContent(String language);

	/**
	 * Returns the path to the help template.
	 *
	 * @param  language the template's language
	 * @return the path to the help template. This template will be shown as a
	 *         help message when the user creates a new template.
	 */
	public String getTemplatesHelpPath(String language);

	/**
	 * Returns the name of the property in portal.properties that defines the
	 * path to the help of template.
	 *
	 * @return the name of the property in portal.properties that defines the
	 *         path to the help template.
	 */
	public String getTemplatesHelpPropertyKey();

	/**
	 * Returns the map of variable groups that the template displays as hints to
	 * the palette of the template editor.
	 *
	 * @param  classPK the primary key of the entity that defines the specific
	 *         variable groups for the template. For example, the primary key of
	 *         the structure associated to the template.
	 * @param  language the template's language
	 * @param  locale the locale of the variable groups to get
	 * @return the map of variable groups that the template displays as hints to
	 *         the palette of the template editor
	 * @throws Exception if an exception occurred retrieving the template
	 *         variable groups
	 */
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale)
		throws Exception;

}