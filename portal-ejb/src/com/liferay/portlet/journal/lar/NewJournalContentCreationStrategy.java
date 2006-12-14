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

/**
 * An interface defining how newly created content should be added to the journal CMS
 * when imported from a Liferay Archive File. A class implementing this inteface
 * should be specified in <i>portal-ext.properties</i> file under the
 * <b>journal.new.content.strategy.class</b> property.
 * 
 * @author Joel Kozikowski
 * @see com.liferay.portlet.journal.lar.DefaultJournalContentPortletDataHandler
 */
public interface NewJournalContentCreationStrategy {

    /**
     * Returns the author Id to assign to newly created content. If NULL is returned,
     * the original author of the exported content will be used.
     * @param companyId The id of the company the group belongs to
     * @param groupId The id of the group that the CMS content is being added to
     * @param newCMSObject The object being created. Will be an instance of JournalArticle, JournalStructure or JournalTemplate.
     * @return the author's internal user Id, or null to use the original author.
     */
    public String getAuthorUserId(String companyId, String groupId, Object newCMSObject) throws Exception;


    /**
     * The <i>name</i> of the user authoring the content.  If <code>getAuthorUserId()</code>
     * returns null, this method is ignored.
     * @param companyId The id of the company the group belongs to
     * @param groupId The id of the group that the CMS content is being added to
     * @param newCMSObject The object being created. Will be an instance of JournalArticle, JournalStructure or JournalTemplate.
     * @return the author's readable name. Must not be NULL if getAuthorUserId() does not return null.
     */
    public String getAuthorUserName(String companyId, String groupId, Object newCMSObject) throws Exception;
    
    
    /**
     * Returns the user Id to specify approval of newly created content. If NULL
     * is returned, the article will not be marked as approved.
     * @param companyId The id of the company the group belongs to
     * @param groupId The id of the group that the CMS content is being added to
     * @param newCMSObject The object being created. Will be an instance of JournalArticle, JournalStructure or JournalTemplate.
     * @return the approver's internal user Id, or NULL if the article should not be approved.
     */
    public String getApprovalUserId(String companyId, String groupId, Object newCMSObject) throws Exception;

    /**
     * The <i>name</i> of the user approving the content.  If <code>getApprovalUserId()</code>
     * returns null, this method is ignored.
     * @param companyId The id of the company the group belongs to
     * @param groupId The id of the group that the CMS content is being added to
     * @param newCMSObject The object being created. Will be an instance of JournalArticle, JournalStructure or JournalTemplate.
     * @return the approver's readable name. Must not be NULL if getApprovalUserId() does not return null.
     */
    public String getApprovalUserName(String companyId, String groupId, Object newCMSObject) throws Exception;

    
    /**
     * Returns TRUE if the default community permissions should be added when the specified newCMSOBject
     * is created.
     * @param companyId The id of the company the group belongs to
     * @param groupId The id of the group that the CMS content is being added to
     * @param newCMSObject The object being created. Will be an instance of JournalArticle, JournalStructure or JournalTemplate.
     * @return TRUE if default community permissions should be added to the CMS object.
     */
    public boolean addCommunityPermissions(String companyId, String groupId, Object newCMSObject) throws Exception;

    
    /**
     * Returns TRUE if the default guest permissions should be added when the specified newCMSOBject
     * is created.
     * @param companyId The id of the company the group belongs to
     * @param groupId The id of the group that the CMS content is being added to
     * @param newCMSObject The object being created. Will be an instance of JournalArticle, JournalStructure or JournalTemplate.
     * @return TRUE if default guest permissions should be added to the CMS object.
     */
    public boolean addGuestPermissions(String companyId, String groupId, Object newCMSObject) throws Exception;
    
}
