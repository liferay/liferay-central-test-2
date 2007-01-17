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

package com.liferay.portlet.journal.lar;

import com.liferay.portlet.journal.model.JournalArticle;

/**
 * An interface defining how newly created content should be added to the
 * Journal when imported from a LAR file. A class implementing this interface
 * should be specified in <i>portal.properties</i> under the
 * <b>journal.lar.creation.strategy</b> property.
 *
 * <p><a href="JournalCreationStrategy.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Joel Kozikowski
 * 
 */
public interface JournalCreationStrategy {

    /**
     * Gives the content creation strategy an opportunity to transform the content before the new
     * article is saved to the database. Possible use cases include using Velocity to merge in
     * community specific values into the text. Returns the new content to assign to the article. 
     * If NULL is returned, the article content will be added unchanged.
     * @param companyId The id of the company the group belongs to
     * @param groupId The id of the group that the article is being added to
     * @param newArticle The article being created. Current content can be retrieved with newArticle.getContent()
     * @return the transformed content to save in the database (or null if the content should be added unchanged).
     */
    public String getTransformedContent(
            String companyId, long groupId, JournalArticle newArticle) 
        throws Exception;
    
    
	/**
	 * Returns the author's user id to assign to newly created content. If null
	 * is returned, the original author of the exported content will be used.
	 *
	 * @param		companyId the company id of the layout
	 * @param		groupId the group id of the layout
	 * @param		journalObj the new object must be an instance of
	 *				JournalArticle, JournalStructure or JournalTemplate
	 * @return		the author's user id, or null to use the original author
	 */
	public String getAuthorUserId(
			String companyId, long groupId, Object journalObj)
		throws Exception;

	/**
	 * Returns the author's user name. This method should be ignored if
	 * <code>getAuthorUserId()</code> returns null.
	 *
	 * @param		companyId the company id of the layout
	 * @param		groupId the group id of the layout
	 * @param		journalObj the new object must be an instance of
	 *				JournalArticle, JournalStructure or JournalTemplate
	 * @return		the author's user name must not be null if
	 *				<code>getAuthorUserId()</code> does not return null
	 */
	public String getAuthorUserName(
			String companyId, long groupId, Object journalObj)
		throws Exception;

	/**
	 * Returns the approver's user id to assign to newly created content. If
	 * null is returned, the article will not be marked as approved.
	 *
	 * @param		companyId the company id of the layout
	 * @param		groupId the group id of the layout
	 * @param		journalObj the new object must be an instance of
	 *				JournalArticle, JournalStructure or JournalTemplate
	 * @return		the approver's user id, or null if the article should not be
	 *				approved
	 */
	public String getApprovalUserId(
			String companyId, long groupId, Object journalObj)
		throws Exception;

	/**
	 * Returns the approver's user name. This method should be ignored if
	 * <code>getApprovalUserId()</code> returns null.
	 *
	 * @param		companyId the company id of the layout
	 * @param		groupId the group id of the layout
	 * @param		journalObj the new object must be an instance of
	 *				JournalArticle, JournalStructure or JournalTemplate
	 * @return		the approver's user name must not be null if
	 *				<code>getApprovalUserId()</code> does not return null
	 */
	public String getApprovalUserName(
			String companyId, long groupId, Object journalObj)
		throws Exception;

	/**
	 * Returns true if the default community permissions should be added when
	 * the specified journalObj is created.
	 *
	 * @param		companyId the company id of the layout
	 * @param		groupId the group id of the layout
	 * @param		journalObj the new object must be an instance of
	 *				JournalArticle, JournalStructure or JournalTemplate
	 * @return		true if default community permissions should be added to the
	 *				specified journalObj
	 */
	public boolean addCommunityPermissions(
			String companyId, long groupId, Object journalObj)
		throws Exception;

	/**
	 * Returns true if the default guest permissions should be added when the
	 * specified journalObj is created.
	 *
	 * @param		companyId the company id of the layout
	 * @param		groupId the group id of the layout
	 * @param		journalObj the new object must be an instance of
	 *				JournalArticle, JournalStructure or JournalTemplate
	 * @return		true if default guest permissions should be added to the
	 *				specified journalObj
	 */
	public boolean addGuestPermissions(
			String companyId, long groupId, Object journalObj)
		throws Exception;

}