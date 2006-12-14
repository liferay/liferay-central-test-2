/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.lar;

import javax.portlet.PortletPreferences;


/**
 * A <code>PortletDataHandler</code> is a special class capable of exporting/importing portlet specific data
 * to a Liferay ARchive file (.lar) when a community layout is exported/imported. <code>PortletDataHandeler</code>s
 * are defined by placing a <code>&lt;portlet-data-handler-class&gt;<code> element in the <code>&lt;portlet&gt;</code>
 * section of the <b>WEB-INF/liferay-portlet.xml</b> or <b>WEB-INF/liferay-portlet-ext.xml</b> file.
 * <p><a href="PortletDataHandler.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Raymond Auge
 * @author Joel Kozikowski
 *
 */
public interface PortletDataHandler {

    /**
     * Returns a string of data to be placed in the &lt;portlet-data&gt; section of the LAR file. This
     * data will be passed as the <code>data</code> parameter of <code>importData()</code>
     * @param companyId The id of the company the layout is part of
     * @param groupId The id of the group that owns the layout being exported
     * @param portletId The id of the portlet being exported
     * @param portletPreferences The current preferences for the portlet
     * @return A string of data to be placed in the .LAR. MAY be XML, but not necessarily. NULL
     *    should be returned if no special data is to be written out.
     * @throws PortletDataException
     */
	public String exportData(String companyId, String groupId, String portletId, PortletPreferences portletPreferences)
		throws PortletDataException;

    /**
     * Handles any special processing of the data when the portlet is being imported into a new layout.
     * Can optionally return a modified version of <code>portletPreferences</code> to be saved in the new
     * portlet.
     * @param companyId The id of the company that owns the layout set the portlet is being added to
     * @param groupId The id of the group that owns the layout set the portlet is being added to
     * @param portletId The id of the portlet being added
     * @param portletPreferences The preferences of the new portlet
     * @param data The data that was returned by <code>exportData()</code>
     * @return A modified version of portletPreferences that should be saved, or NULL if the preferences were unmodified
     *   by this data handler.
     * @throws PortletDataException
     */
	public PortletPreferences importData(String companyId, String groupId, String portletId, 
            PortletPreferences portletPreferences, String data)
		throws PortletDataException;

}