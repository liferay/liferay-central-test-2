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