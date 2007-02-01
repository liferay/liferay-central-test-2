create index IX_93D5AD4E on Address (companyId);
create index IX_4F4BDD05 on Address (companyId, className);
create index IX_DB84CC7E on Address (companyId, className, classPK);
create index IX_845FAC7D on Address (companyId, className, classPK, mailing);
create index IX_E47E614F on Address (companyId, className, classPK, primary_);
create index IX_5BC8B0D4 on Address (userId);

create index IX_C49DD10C on BlogsCategory (parentCategoryId);

create index IX_B0608DF4 on BlogsEntry (categoryId);
create index IX_72EF6041 on BlogsEntry (companyId);
create index IX_81A50303 on BlogsEntry (groupId);

create index IX_443BDC38 on BookmarksEntry (folderId);

create index IX_7F703619 on BookmarksFolder (groupId);
create index IX_967799C0 on BookmarksFolder (groupId, parentFolderId);

create index IX_12EE4898 on CalEvent (groupId);
create index IX_4FDDD2BF on CalEvent (groupId, repeating);
create index IX_FCD7C63D on CalEvent (groupId, type_);

create index IX_66D496A3 on Contact_ (companyId);

create index IX_25D734CD on Country (active_);

create index IX_24A846D1 on DLFileEntry (folderId);

create index IX_40B56512 on DLFileRank (folderId, name);
create index IX_EED06670 on DLFileRank (userId);

create index IX_E56EC6AD on DLFileShortcut (folderId);
create index IX_CA2708A2 on DLFileShortcut (toFolderId, toName);

create index IX_9CD91DB6 on DLFileVersion (folderId, name);

create index IX_A74DB14C on DLFolder (companyId);
create index IX_F2EA1ACE on DLFolder (groupId);
create index IX_49C37475 on DLFolder (groupId, parentFolderId);
create index IX_51556082 on DLFolder (parentFolderId, name);

create index IX_1BB072CA on EmailAddress (companyId);
create index IX_A9801209 on EmailAddress (companyId, className);
create index IX_C161FBFA on EmailAddress (companyId, className, classPK);
create index IX_F5B365CB on EmailAddress (companyId, className, classPK, primary_);
create index IX_7B43CD8 on EmailAddress (userId);

create index IX_5849ABF2 on Group_ (companyId, className, classPK);
create index IX_5BDDB872 on Group_ (companyId, friendlyURL);
create index IX_5AA68501 on Group_ (companyId, name);

create index LIFERAY_001 on Groups_Permissions (permissionId);

create index IX_206498F8 on IGFolder (groupId);
create index IX_1A605E9F on IGFolder (groupId, parentFolderId);

create index IX_4438CA80 on IGImage (folderId);

create index IX_DFF98523 on JournalArticle (companyId);
create index IX_29BD22DA on JournalArticle (companyId, groupId, articleId);
create index IX_A1D78A45 on JournalArticle (companyId, groupId, articleId, approved);
create index IX_A0C28B17 on JournalArticle (companyId, groupId, structureId);
create index IX_EC743CD0 on JournalArticle (companyId, groupId, templateId);
create index IX_9356F865 on JournalArticle (groupId);

create index IX_4D73E06F on JournalContentSearch (companyId, groupId, articleId);
create index IX_972C13BA on JournalContentSearch (groupId);
create index IX_ABEEA675 on JournalContentSearch (layoutId, ownerId);
create index IX_F09DD5EE on JournalContentSearch (ownerId);
create index IX_4A642025 on JournalContentSearch (ownerId, groupId, articleId);

create index IX_47EF5658 on JournalStructure (companyId, structureId);
create index IX_B97F5608 on JournalStructure (groupId);

create index IX_AB3E5F05 on JournalTemplate (companyId, groupId, structureId);
create index IX_D7A3867A on JournalTemplate (companyId, templateId);
create index IX_77923653 on JournalTemplate (groupId);

create index IX_1A0B984E on Layout (ownerId);
create index IX_E230D266 on Layout (ownerId, friendlyURL);
create index IX_9AF212B1 on Layout (ownerId, parentLayoutId);

create index IX_A34FBC19 on LayoutSet (companyId, virtualHost);
create index IX_A40B8BEC on LayoutSet (groupId);

create index IX_2932DD37 on ListType (type_);

create index IX_BC735DCF on MBCategory (companyId);
create index IX_BB870C11 on MBCategory (groupId);
create index IX_ED292508 on MBCategory (groupId, parentCategoryId);

create index IX_B628DAD3 on MBDiscussion (className, classPK);

create index IX_3C865EE5 on MBMessage (categoryId);
create index IX_138C7F1E on MBMessage (categoryId, threadId);
create index IX_75B95071 on MBMessage (threadId);
create index IX_A7038CD7 on MBMessage (threadId, parentMessageId);

create index IX_EE1CA456 on MBMessageFlag (topicId);
create index IX_93BF5C9C on MBMessageFlag (topicId, messageId);
create index IX_E1F34690 on MBMessageFlag (topicId, userId);
create index IX_7B2917BE on MBMessageFlag (userId);

create index IX_A00A898F on MBStatsUser (groupId);
create index IX_FAB5A88B on MBStatsUser (groupId, messageCount);
create index IX_847F92B5 on MBStatsUser (userId);

create index IX_CB854772 on MBThread (categoryId);

create index IX_A425F71A on OrgGroupPermission (groupId);
create index IX_6C53DA4E on OrgGroupPermission (permissionId);

create index IX_4A527DD3 on OrgGroupRole (groupId);
create index IX_AB044D1C on OrgGroupRole (roleId);

create index IX_6AF0D434 on OrgLabor (organizationId);

create index IX_834BCEB6 on Organization_ (companyId);
create index IX_E301BDF5 on Organization_ (companyId, name);
create index IX_418E4522 on Organization_ (companyId, parentOrganizationId);

create index IX_326F75BD on PasswordTracker (userId);

create index IX_4D19C2B8 on Permission_ (actionId, resourceId);
create index IX_F090C113 on Permission_ (resourceId);

create index IX_9F704A14 on Phone (companyId);
create index IX_139DA87F on Phone (companyId, className);
create index IX_A074A44 on Phone (companyId, className, classPK);
create index IX_2CAADF95 on Phone (companyId, className, classPK, primary_);
create index IX_F202B9CE on Phone (userId);

create index IX_EC370F10 on PollsChoice (questionId);

create index IX_9FF342EA on PollsQuestion (groupId);

create index IX_12112599 on PollsVote (questionId);
create index IX_FE3220E9 on PollsVote (questionId, choiceId);

create index IX_80CC9508 on Portlet (companyId);

create index IX_8B1E639D on PortletPreferences (layoutId);
create index IX_4A6293E1 on PortletPreferences (layoutId, ownerId);
create index IX_3EAB5A5A on PortletPreferences (ownerId);
create index IX_8E6DA3A1 on PortletPreferences (portletId);

create index IX_EA9B85B2 on RatingsEntry (className, classPK);
create index IX_9941DAEC on RatingsEntry (userId, className, classPK);

create index IX_8366321F on RatingsStats (className, classPK);

create index IX_2D9A426F on Region (active_);
create index IX_16D87CA7 on Region (countryId);
create index IX_11FB3E42 on Region (countryId, active_);

create index IX_722B1A51 on Resource_ (companyId);
create index IX_FF2D206B on Resource_ (companyId, name, typeId, scope);
create index IX_982DFEBE on Resource_ (companyId, name, typeId, scope, primKey);
create index IX_9F23BCBF on Resource_ (companyId, typeId, scope, primKey);
create index IX_8CDC43F6 on Resource_ (name);

create index IX_449A10B9 on Role_ (companyId);
create index IX_ED284C69 on Role_ (companyId, className, classPK);
create index IX_EBC931B8 on Role_ (companyId, name);

create index LIFERAY_002 on Roles_Permissions (permissionId);

create index IX_D3AA1BC9 on SRFrameworkVersion (companyId);
create index IX_4369008B on SRFrameworkVersion (groupId);
create index IX_2DB6DADE on SRFrameworkVersion (groupId, active_);

create index IX_297FCFE1 on SRLicense (active_);
create index IX_4DFB798A on SRLicense (active_, recommended);

create index IX_78E10320 on SRProductEntry (companyId);
create index IX_370FD1A2 on SRProductEntry (groupId);
create index IX_93BA67DC on SRProductEntry (groupId, userId);

create index IX_E31BA620 on SRProductVersion (productEntryId);

create index IX_C28B41DC on ShoppingCart (groupId);
create index IX_54101CC8 on ShoppingCart (userId);

create index IX_5F615D3E on ShoppingCategory (groupId);
create index IX_1E6464F5 on ShoppingCategory (groupId, parentCategoryId);

create index IX_3251AF16 on ShoppingCoupon (groupId);

create index IX_C8EACF2E on ShoppingItem (categoryId);
create index IX_1C717CA6 on ShoppingItem (companyId, sku);

create index IX_6D5F9B87 on ShoppingItemField (itemId);

create index IX_EA6FD516 on ShoppingItemPrice (itemId);

create index IX_119B5630 on ShoppingOrder (groupId, userId, ppPaymentStatus);

create index IX_B5F82C7A on ShoppingOrderItem (orderId);

create index IX_E00DE435 on Subscription (companyId, className, classPK);
create index IX_FC7B066F on Subscription (companyId, userId, className, classPK);
create index IX_54243AFD on Subscription (userId);

create index IX_5018A7B on TagsAsset (companyId, className, classPK);

create index IX_10563688 on TagsEntry (companyId, name);

create index IX_C134234 on TagsProperty (companyId);
create index IX_EB974D08 on TagsProperty (companyId, key_);
create index IX_5200A629 on TagsProperty (entryId);
create index IX_F505253D on TagsProperty (entryId, key_);

create index IX_524FEFCE on UserGroup (companyId);
create index IX_23EAD0D on UserGroup (companyId, name);
create index IX_69771487 on UserGroup (companyId, parentUserGroupId);

create index IX_1B988D7A on UserGroupRole (groupId);
create index IX_887A2C95 on UserGroupRole (roleId);
create index IX_887BE56A on UserGroupRole (userId);
create index IX_4D040680 on UserGroupRole (userId, groupId);

create index IX_E60EA987 on UserIdMapper (userId);

create index IX_29BA1CF5 on UserTracker (companyId);
create index IX_E4EFBA8D on UserTracker (userId);

create index IX_14D8BCC0 on UserTrackerPath (userTrackerId);

create index IX_3A1E834E on User_ (companyId);
create index IX_615E9F7A on User_ (companyId, emailAddress);
create index IX_765A87C6 on User_ (companyId, password_);
create index IX_9782AD88 on User_ (companyId, userId);

create index LIFERAY_003 on Users_Permissions (permissionId);

create index IX_96F07007 on Website (companyId);
create index IX_66A45CAC on Website (companyId, className);
create index IX_5233F8B7 on Website (companyId, className, classPK);
create index IX_82125A48 on Website (companyId, className, classPK, primary_);
create index IX_F75690BB on Website (userId);

create index IX_5D6FE3F0 on WikiNode (companyId);
create index IX_B480A672 on WikiNode (groupId);

create index IX_C8A9C476 on WikiPage (nodeId);
create index IX_E7F635CA on WikiPage (nodeId, head);
create index IX_997EEDD2 on WikiPage (nodeId, title);
create index IX_E745EA26 on WikiPage (nodeId, title, head);