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

import java.lang.ref.SoftReference;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.GroupUtil;

/**
 * Provides the default strategy for creating new content when new CMS content is 
 * imported into a Layout set from a LAR.  The default strategy implemented by
 * this class is to return the first user in the database that is a member of the
 * specified group.  If the group contains no users, the system administrator will
 * be the author, and the article will NOT be marked as "approved."
 * <p>In addition, community and guest permissions will always be added to newly
 * created items.
 *   
 * @see com.liferay.portlet.journal.lar.DefaultJournalContentPortletDataHandler
 * @see com.liferay.portlet.journal.lar.NewJournalContentCreationStrategy
 * @author Joel Kozikowski
 */
public class DefaultNewJournalContentCreationStrategy implements
        NewJournalContentCreationStrategy {

    public String getAuthorUserId(String companyId, String groupId, Object newCMSObject) throws Exception {
        User user = getFirstUser(groupId);
        if (user != null) {
             return user.getUserId();   
        }
        else {
             return null;
        }
    }

    public String getAuthorUserName(String companyId, String groupId, Object newCMSObject) throws Exception {
        User user = getFirstUser(groupId);
        if (user != null) {
             return user.getFullName();
        }
        else {
             return null;
        }
    }
    
    public String getApprovalUserName(String companyId, String groupId, Object newCMSObject) throws Exception {
        User user = getFirstUser(groupId);
        if (user != null) {
             return user.getFullName();   
        }
        else {
             return null;
        }
    }

    public String getApprovalUserId(String companyId, String groupId, Object newCMSObject) throws Exception {
        User user = getFirstUser(groupId);
        if (user != null) {
             return user.getUserId();   
        }
        else {
             return null;
        }
    }

    private static String lastGroupId = null;
    private static SoftReference lastUserFound = null;
    
    private synchronized User getFirstUser(String groupId) throws Exception {
        User firstUser;
        if (groupId.equals(lastGroupId) && lastUserFound != null && lastUserFound.get() != null) {
            firstUser = (User)lastUserFound.get();
            // System is asking for the same group id as last time!    
            _log.debug("Returning cached first user for group id " + groupId + ": " + firstUser.getFullName() + "( " + firstUser.getUserId() + ")");

        }
        else {
            List userList = GroupUtil.getUsers(groupId);
            firstUser = (User)userList.get(0);
            _log.debug("First user for group id " + groupId + " is " + firstUser.getFullName() + "( " + firstUser.getUserId() + ")");
            lastGroupId = groupId;
            lastUserFound = new SoftReference(firstUser);
        }
        
        return firstUser;
    }
    
    private static Log _log = LogFactory.getLog(DefaultNewJournalContentCreationStrategy.class);

    
    public boolean addCommunityPermissions(String companyId, String groupId, Object newCMSObject) throws Exception {
        return true;
    }

    public boolean addGuestPermissions(String companyId, String groupId, Object newCMSObject) throws Exception {
        return true;
    }
}
