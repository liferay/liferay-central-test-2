create index Address_ix_C_C on Address (companyId, className);
create index Address_ix_C_C_C on Address (companyId, className, classPK);
create index Address_ix_C_C_C_M on Address (companyId, className, classPK, mailing);
create index Address_ix_C_C_C_P on Address (companyId, className, classPK, primary_);
create index Address_ix_CompanyId on Address (companyId);
create index Address_ix_UserId on Address (userId);

create index BlogsCategory_ix_ParentCategor on BlogsCategory (parentCategoryId);

create index BlogsEntry_ix_CategoryId on BlogsEntry (categoryId);
create index BlogsEntry_ix_CompanyId on BlogsEntry (companyId);
create index BlogsEntry_ix_GroupId on BlogsEntry (groupId);

create index BookmarksEntry_ix_FolderId on BookmarksEntry (folderId);

create index BookmarksFolder_ix_G_P on BookmarksFolder (groupId, parentFolderId);
create index BookmarksFolder_ix_GroupId on BookmarksFolder (groupId);

create index CalEvent_ix_G_R on CalEvent (groupId, repeating);
create index CalEvent_ix_G_T on CalEvent (groupId, type_);
create index CalEvent_ix_GroupId on CalEvent (groupId);

create index Contact_ix_CompanyId on Contact_ (companyId);

create index Country_ix_Active on Country (active_);

create index DLFileEntry_ix_FolderId on DLFileEntry (folderId);

create index DLFileRank_ix_F_N on DLFileRank (folderId, name);
create index DLFileRank_ix_UserId on DLFileRank (userId);

create index DLFileVersion_ix_F_N on DLFileVersion (folderId, name);

create index DLFolder_ix_CompanyId on DLFolder (companyId);
create index DLFolder_ix_G_P on DLFolder (groupId, parentFolderId);
create index DLFolder_ix_GroupId on DLFolder (groupId);
create index DLFolder_ix_P_N on DLFolder (parentFolderId, name);

create index EmailAddress_ix_C_C on EmailAddress (companyId, className);
create index EmailAddress_ix_C_C_C on EmailAddress (companyId, className, classPK);
create index EmailAddress_ix_C_C_C_P on EmailAddress (companyId, className, classPK, primary_);
create index EmailAddress_ix_CompanyId on EmailAddress (companyId);
create index EmailAddress_ix_UserId on EmailAddress (userId);

create index GroupRel_ix_C_C on GroupRel (className, classPK);

create index Group_ix_C_C_C on Group_ (companyId, className, classPK);
create index Group_ix_C_F on Group_ (companyId, friendlyURL);

create index IGFolder_ix_G_P on IGFolder (groupId, parentFolderId);
create index IGFolder_ix_GroupId on IGFolder (groupId);

create index IGImage_ix_FolderId on IGImage (folderId);

create index JournalArticle_ix_C_A on JournalArticle (companyId, articleId);
create index JournalArticle_ix_C_A_A on JournalArticle (companyId, articleId, approved);
create index JournalArticle_ix_C_S on JournalArticle (companyId, structureId);
create index JournalArticle_ix_C_T on JournalArticle (companyId, templateId);
create index JournalArticle_ix_CompanyId on JournalArticle (companyId);
create index JournalArticle_ix_GroupId on JournalArticle (groupId);

create index JournalContentSearch_ix_C_A on JournalContentSearch (companyId, articleId);
create index JournalContentSearch_ix_L_O on JournalContentSearch (layoutId, ownerId);
create index JournalContentSearch_ix_O_A on JournalContentSearch (ownerId, articleId);
create index JournalContentSearch_ix_OwnerI on JournalContentSearch (ownerId);

create index JournalStructure_ix_GroupId on JournalStructure (groupId);

create index JournalTemplate_ix_C_S on JournalTemplate (companyId, structureId);
create index JournalTemplate_ix_GroupId on JournalTemplate (groupId);

create index Layout_ix_O_F on Layout (ownerId, friendlyURL);
create index Layout_ix_O_P on Layout (ownerId, parentLayoutId);
create index Layout_ix_OwnerId on Layout (ownerId);

create index ListType_ix_Type on ListType (type_);

create index MBCategory_ix_G_P on MBCategory (groupId, parentCategoryId);
create index MBCategory_ix_GroupId on MBCategory (groupId);

create index MBDiscussion_ix_C_C on MBDiscussion (className, classPK);

create index MBMessageFlag_ix_T_M on MBMessageFlag (topicId, messageId);
create index MBMessageFlag_ix_T_U on MBMessageFlag (topicId, userId);
create index MBMessageFlag_ix_TopicId on MBMessageFlag (topicId);
create index MBMessageFlag_ix_UserId on MBMessageFlag (userId);

create index MBMessage_ix_T_P on MBMessage (threadId, parentMessageId);
create index MBMessage_ix_ThreadId on MBMessage (threadId);
create index MBMessage_ix_TopicId on MBMessage (topicId);

create index MBThread_ix_TopicId on MBThread (topicId);

create index MBTopic_ix_CategoryId on MBTopic (categoryId);
create index MBTopic_ix_CompanyId on MBTopic (companyId);

create index MailReceipt_ix_CompanyId on MailReceipt (companyId);
create index MailReceipt_ix_UserId on MailReceipt (userId);

create index OrgGroupPermission_ix_Permissi on OrgGroupPermission (permissionId);

create index OrgGroupRole_ix_RoleId on OrgGroupRole (roleId);

create index OrgLabor_ix_OrganizationId on OrgLabor (organizationId);

create index Organization_ix_C_N on Organization_ (companyId, name);
create index Organization_ix_C_P on Organization_ (companyId, parentOrganizationId);
create index Organization_ix_CompanyId on Organization_ (companyId);

create index PasswordTracker_ix_UserId on PasswordTracker (userId);

create index Permission_ix_A_R on Permission_ (actionId, resourceId);
create index Permission_ix_ResourceId on Permission_ (resourceId);

create index Phone_ix_C_C on Phone (companyId, className);
create index Phone_ix_C_C_C on Phone (companyId, className, classPK);
create index Phone_ix_C_C_C_P on Phone (companyId, className, classPK, primary_);
create index Phone_ix_CompanyId on Phone (companyId);
create index Phone_ix_UserId on Phone (userId);

create index PollsChoice_ix_QuestionId on PollsChoice (questionId);

create index PollsQuestion_ix_GroupId on PollsQuestion (groupId);

create index PollsVote_ix_Q_C on PollsVote (questionId, choiceId);
create index PollsVote_ix_QuestionId on PollsVote (questionId);

create index PortletPreferences_ix_L_O on PortletPreferences (layoutId, ownerId);
create index PortletPreferences_ix_LayoutId on PortletPreferences (layoutId);
create index PortletPreferences_ix_OwnerId on PortletPreferences (ownerId);

create index Portlet_ix_CompanyId on Portlet (companyId);

create index Properties_ix_CompanyId on Properties (companyId);

create index Region_ix_Active on Region (active_);
create index Region_ix_C_A on Region (countryId, active_);
create index Region_ix_CountryId on Region (countryId);

create index Resource_ix_C_N_T_S on Resource_ (companyId, name, typeId, scope);
create index Resource_ix_C_N_T_S_P on Resource_ (companyId, name, typeId, scope, primKey);
create index Resource_ix_C_T_S_P on Resource_ (companyId, typeId, scope, primKey);
create index Resource_ix_CompanyId on Resource_ (companyId);

create index Role_ix_C_C_C on Role_ (companyId, className, classPK);
create index Role_ix_CompanyId on Role_ (companyId);

create index ShoppingCart_ix_GroupId on ShoppingCart (groupId);
create index ShoppingCart_ix_UserId on ShoppingCart (userId);

create index ShoppingCategory_ix_G_P on ShoppingCategory (groupId, parentCategoryId);
create index ShoppingCategory_ix_GroupId on ShoppingCategory (groupId);

create index ShoppingCoupon_ix_GroupId on ShoppingCoupon (groupId);

create index ShoppingItemField_ix_ItemId on ShoppingItemField (itemId);

create index ShoppingItemPrice_ix_ItemId on ShoppingItemPrice (itemId);

create index ShoppingItem_ix_C_S on ShoppingItem (companyId, sku);
create index ShoppingItem_ix_CategoryId on ShoppingItem (categoryId);

create index ShoppingOrderItem_ix_OrderId on ShoppingOrderItem (orderId);

create index ShoppingOrder_ix_G_U_PPPS on ShoppingOrder (groupId, userId, ppPaymentStatus);

create index Subscription_ix_C_C_C on Subscription (companyId, className, classPK);
create index Subscription_ix_C_U_C_C on Subscription (companyId, userId, className, classPK);
create index Subscription_ix_UserId on Subscription (userId);

create index UserIdMapper_ix_UserId on UserIdMapper (userId);

create index UserTrackerPath_ix_UserTracker on UserTrackerPath (userTrackerId);

create index UserTracker_ix_CompanyId on UserTracker (companyId);
create index UserTracker_ix_UserId on UserTracker (userId);

create index User_ix_C_EA on User_ (companyId, emailAddress);
create index User_ix_C_P on User_ (companyId, password_);
create index User_ix_C_U on User_ (companyId, userId);
create index User_ix_CompanyId on User_ (companyId);

create index Website_ix_C_C on Website (companyId, className);
create index Website_ix_C_C_C on Website (companyId, className, classPK);
create index Website_ix_C_C_C_P on Website (companyId, className, classPK, primary_);
create index Website_ix_CompanyId on Website (companyId);
create index Website_ix_UserId on Website (userId);

create index WikiNode_ix_CompanyId on WikiNode (companyId);
create index WikiNode_ix_GroupId on WikiNode (groupId);

create index WikiPage_ix_N_H on WikiPage (nodeId, head);
create index WikiPage_ix_N_T on WikiPage (nodeId, title);
create index WikiPage_ix_N_T_H on WikiPage (nodeId, title, head);
create index WikiPage_ix_NodeId on WikiPage (nodeId);