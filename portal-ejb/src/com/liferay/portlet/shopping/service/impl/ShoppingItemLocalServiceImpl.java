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

package com.liferay.portlet.shopping.service.impl;

import com.liferay.counter.service.spring.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.service.impl.ImageLocalUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.amazonrankings.model.AmazonRankings;
import com.liferay.portlet.amazonrankings.util.AmazonRankingsUtil;
import com.liferay.portlet.shopping.DuplicateItemSKUException;
import com.liferay.portlet.shopping.ItemLargeImageNameException;
import com.liferay.portlet.shopping.ItemLargeImageSizeException;
import com.liferay.portlet.shopping.ItemMediumImageNameException;
import com.liferay.portlet.shopping.ItemMediumImageSizeException;
import com.liferay.portlet.shopping.ItemNameException;
import com.liferay.portlet.shopping.ItemSKUException;
import com.liferay.portlet.shopping.ItemSmallImageNameException;
import com.liferay.portlet.shopping.ItemSmallImageSizeException;
import com.liferay.portlet.shopping.NoSuchItemException;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.model.ShoppingItemField;
import com.liferay.portlet.shopping.model.ShoppingItemPrice;
import com.liferay.portlet.shopping.service.persistence.ShoppingCategoryUtil;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldUtil;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemFinder;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceUtil;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemUtil;
import com.liferay.portlet.shopping.service.spring.ShoppingItemLocalService;
import com.liferay.util.FileUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.Http;
import com.liferay.util.PwdGenerator;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.SystemProperties;
import com.liferay.util.Validator;
import com.liferay.util.dao.hibernate.OrderByComparator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="ShoppingItemLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingItemLocalServiceImpl implements ShoppingItemLocalService {

	public void addBookItems(
			String userId, String categoryId, String[] isbns)
		throws PortalException, SystemException {

		try {
			String tmpDir = SystemProperties.get(SystemProperties.TMP_DIR);

			for (int i = 0; (i < isbns.length) && (i < 50); i++) {
				String isbn = isbns[i];

				AmazonRankings amazonRankings =
					AmazonRankingsUtil.getAmazonRankings(isbn);

				if (amazonRankings == null) {
					continue;
				}

				String name = amazonRankings.getProductName();
				String description = StringPool.BLANK;
				String properties = getBookProperties(amazonRankings);

				int minQuantity = 0;
				int maxQuantity = 0;
				double price = amazonRankings.getListPrice();
				double discount = 1 - amazonRankings.getOurPrice() / price;
				boolean suggestedPrice = false;
				boolean taxable = true;
				double shipping = 0.0;
				boolean useShippingFormula = true;

				ShoppingItemPrice itemPrice = ShoppingItemPriceUtil.create(
					StringPool.BLANK);

				itemPrice.setMinQuantity(minQuantity);
				itemPrice.setMaxQuantity(maxQuantity);
				itemPrice.setPrice(price);
				itemPrice.setDiscount(discount);
				itemPrice.setTaxable(taxable);
				itemPrice.setShipping(shipping);
				itemPrice.setUseShippingFormula(useShippingFormula);
				itemPrice.setStatus(ShoppingItemPrice.STATUS_ACTIVE_DEFAULT);

				boolean requiresShipping = true;
				int stockQuantity = 0;
				boolean featured = false;
				Boolean sale = null;

				// Small image

				boolean smallImage = true;
				String smallImageURL = StringPool.BLANK;
				File smallFile = new File(
					tmpDir + File.separatorChar +
					PwdGenerator.getPassword(
						PwdGenerator.KEY1 + PwdGenerator.KEY2, 12) + ".jpg");

				byte[] smallBytes = Http.URLtoByteArray(
					amazonRankings.getSmallImageURL());

				if (smallBytes.length < 1024) {
					smallImage = false;
				}
				else {
					OutputStream out = new FileOutputStream(smallFile);
					out.write(smallBytes);
					out.close();
				}

				// Medium image

				boolean mediumImage = true;
				String mediumImageURL = StringPool.BLANK;
				File mediumFile = new File(
					tmpDir + File.separatorChar +
					PwdGenerator.getPassword(
						PwdGenerator.KEY1 + PwdGenerator.KEY2, 12) + ".jpg");

				byte[] mediumBytes = Http.URLtoByteArray(
					amazonRankings.getMediumImageURL());

				if (mediumBytes.length < 1024) {
					mediumImage = false;
				}
				else {
					OutputStream out = new FileOutputStream(mediumFile);
					out.write(mediumBytes);
					out.close();
				}

				// Large image

				boolean largeImage = true;
				String largeImageURL = StringPool.BLANK;
				File largeFile = new File(
					tmpDir + File.separatorChar +
					PwdGenerator.getPassword(
						PwdGenerator.KEY1 + PwdGenerator.KEY2, 12) + ".jpg");

				byte[] largeBytes = Http.URLtoByteArray(
					amazonRankings.getLargeImageURL());

				if (largeBytes.length < 1024) {
					largeImage = false;
				}
				else {
					OutputStream out = new FileOutputStream(largeFile);
					out.write(largeBytes);
					out.close();
				}

				List itemFields = new ArrayList();

				List itemPrices = new ArrayList();

				itemPrices.add(itemPrice);

				boolean addCommunityPermissions = true;
				boolean addGuestPermissions = true;

				addItem(
					userId, categoryId, isbn, name, description, properties,
					StringPool.BLANK, requiresShipping, stockQuantity, featured,
					sale, smallImage, smallImageURL, smallFile, mediumImage,
					mediumImageURL, mediumFile, largeImage, largeImageURL,
					largeFile, itemFields, itemPrices, addCommunityPermissions,
					addGuestPermissions);

				smallFile.delete();
				mediumFile.delete();
				largeFile.delete();
			}
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public ShoppingItem addItem(
			String userId, String categoryId, String sku, String name,
			String description, String properties, String fieldsQuantities,
			boolean requiresShipping, int stockQuantity, boolean featured,
			Boolean sale, boolean smallImage, String smallImageURL,
			File smallFile, boolean mediumImage, String mediumImageURL,
			File mediumFile, boolean largeImage, String largeImageURL,
			File largeFile, List itemFields, List itemPrices,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addItem(
			userId, categoryId, sku, name, description, properties,
			fieldsQuantities, requiresShipping, stockQuantity, featured, sale,
			smallImage, smallImageURL, smallFile, mediumImage, mediumImageURL,
			mediumFile, largeImage, largeImageURL, largeFile, itemFields,
			itemPrices, new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public ShoppingItem addItem(
			String userId, String categoryId, String sku, String name,
			String description, String properties, String fieldsQuantities,
			boolean requiresShipping, int stockQuantity, boolean featured,
			Boolean sale, boolean smallImage, String smallImageURL,
			File smallFile, boolean mediumImage, String mediumImageURL,
			File mediumFile, boolean largeImage, String largeImageURL,
			File largeFile, List itemFields, List itemPrices,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addItem(
			userId, categoryId, sku, name, description, properties,
			fieldsQuantities, requiresShipping, stockQuantity, featured, sale,
			smallImage, smallImageURL, smallFile, mediumImage, mediumImageURL,
			mediumFile, largeImage, largeImageURL, largeFile, itemFields,
			itemPrices, null, null, communityPermissions, guestPermissions);
	}

	public ShoppingItem addItem(
			String userId, String categoryId, String sku, String name,
			String description, String properties, String fieldsQuantities,
			boolean requiresShipping, int stockQuantity, boolean featured,
			Boolean sale, boolean smallImage, String smallImageURL,
			File smallFile, boolean mediumImage, String mediumImageURL,
			File mediumFile, boolean largeImage, String largeImageURL,
			File largeFile, List itemFields, List itemPrices,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		// Item

		User user = UserUtil.findByPrimaryKey(userId);
		ShoppingCategory category =
			ShoppingCategoryUtil.findByPrimaryKey(categoryId);
		sku = sku.trim().toUpperCase();

		byte[] smallBytes = null;
		byte[] mediumBytes = null;
		byte[] largeBytes = null;

		try {
			smallBytes = FileUtil.getBytes(smallFile);
			mediumBytes = FileUtil.getBytes(mediumFile);
			largeBytes = FileUtil.getBytes(largeFile);
		}
		catch (IOException ioe) {
		}

		Date now = new Date();

		validate(
			user.getCompanyId(), null, sku, name, smallImage, smallImageURL,
			smallFile, smallBytes, mediumImage, mediumImageURL, mediumFile,
			mediumBytes, largeImage, largeImageURL, largeFile, largeBytes);

		String itemId = Long.toString(CounterLocalServiceUtil.increment(
			ShoppingItem.class.getName()));

		ShoppingItem item = ShoppingItemUtil.create(itemId);

		item.setCompanyId(user.getCompanyId());
		item.setUserId(user.getUserId());
		item.setUserName(user.getFullName());
		item.setCreateDate(now);
		item.setModifiedDate(now);
		item.setCategoryId(categoryId);
		item.setSku(sku);
		item.setName(name);
		item.setDescription(description);
		item.setProperties(properties);
		item.setFields(itemFields.size() > 0);
		item.setFieldsQuantities(fieldsQuantities);

		for (int i = 0; i < itemPrices.size(); i++) {
			ShoppingItemPrice itemPrice = (ShoppingItemPrice)itemPrices.get(i);

			if (itemPrice.getStatus() ==
					ShoppingItemPrice.STATUS_ACTIVE_DEFAULT) {

				item.setMinQuantity(itemPrice.getMinQuantity());
				item.setMaxQuantity(itemPrice.getMaxQuantity());
				item.setPrice(itemPrice.getPrice());
				item.setDiscount(itemPrice.getDiscount());
				item.setTaxable(itemPrice.getTaxable());
				item.setShipping(itemPrice.getShipping());
				item.setUseShippingFormula(
					itemPrice.getUseShippingFormula());
			}

			if ((sale == null) && (itemPrice.getDiscount() > 0) &&
				((itemPrice.getStatus() ==
					ShoppingItemPrice.STATUS_ACTIVE_DEFAULT) ||
				(itemPrice.getStatus() == ShoppingItemPrice.STATUS_ACTIVE))) {

				sale = Boolean.TRUE;
			}
		}

		item.setRequiresShipping(requiresShipping);
		item.setStockQuantity(stockQuantity);
		item.setFeatured(featured);
		item.setSale((sale != null) ? sale.booleanValue() : false);
		item.setSmallImage(smallImage);
		item.setSmallImageURL(smallImageURL);
		item.setMediumImage(mediumImage);
		item.setMediumImageURL(mediumImageURL);
		item.setLargeImage(largeImage);
		item.setLargeImageURL(largeImageURL);

		ShoppingItemUtil.update(item);

		// Fields

		for (int i = 0; i < itemFields.size(); i++) {
			ShoppingItemField itemField = (ShoppingItemField)itemFields.get(i);

			String itemFieldId = Long.toString(
				CounterLocalServiceUtil.increment(
					ShoppingItemField.class.getName()));

			itemField.setItemFieldId(itemFieldId);
			itemField.setItemId(itemId);
			itemField.setName(checkItemField(itemField.getName()));
			itemField.setValues(checkItemField(itemField.getValues()));

			ShoppingItemFieldUtil.update(itemField);
		}

		// Prices

		for (int i = 0; (i < itemPrices.size()) && (itemPrices.size() > 1);
				i++) {

			ShoppingItemPrice itemPrice = (ShoppingItemPrice)itemPrices.get(i);

			String itemPriceId = Long.toString(
				CounterLocalServiceUtil.increment(
					ShoppingItemPrice.class.getName()));

			itemPrice.setItemPriceId(itemPriceId);
			itemPrice.setItemId(itemId);

			ShoppingItemPriceUtil.update(itemPrice);
		}

		// Images

		saveImages(
			smallImage, item.getSmallImageId(), smallFile, smallBytes,
			mediumImage, item.getMediumImageId(), mediumFile, mediumBytes,
			largeImage, item.getLargeImageId(), largeFile, largeBytes);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addItemResources(
				category, item, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addItemResources(
				category, item, communityPermissions, guestPermissions);
		}

		return item;
	}

	public void addItemResources(
			String itemId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ShoppingItem item = ShoppingItemUtil.findByPrimaryKey(itemId);
		ShoppingCategory category = item.getCategory();

		addItemResources(
			category, item, addCommunityPermissions, addGuestPermissions);
	}

	public void addItemResources(
			ShoppingCategory category, ShoppingItem item,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			item.getCompanyId(), category.getGroupId(), item.getUserId(),
			ShoppingItem.class.getName(), item.getPrimaryKey().toString(),
			false, addCommunityPermissions, addGuestPermissions);
	}

	public void addItemResources(
			String itemId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ShoppingItem item = ShoppingItemUtil.findByPrimaryKey(itemId);
		ShoppingCategory category = item.getCategory();

		addItemResources(
			category, item, communityPermissions, guestPermissions);
	}

	public void addItemResources(
			ShoppingCategory category, ShoppingItem item,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			item.getCompanyId(), category.getGroupId(), item.getUserId(),
			ShoppingItem.class.getName(), item.getPrimaryKey().toString(),
			communityPermissions, guestPermissions);
	}

	public void deleteItem(String itemId)
		throws PortalException, SystemException {

		ShoppingItem item = ShoppingItemUtil.findByPrimaryKey(itemId);

		deleteItem(item);
	}

	public void deleteItem(ShoppingItem item)
		throws PortalException, SystemException {

		// Fields

		ShoppingItemFieldUtil.removeByItemId(item.getItemId());

		// Prices

		ShoppingItemPriceUtil.removeByItemId(item.getItemId());

		// Images

		ImageLocalUtil.remove(item.getSmallImageId());
		ImageLocalUtil.remove(item.getMediumImageId());
		ImageLocalUtil.remove(item.getLargeImageId());

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			item.getCompanyId(), ShoppingItem.class.getName(),
			Resource.TYPE_CLASS, Resource.SCOPE_INDIVIDUAL,
			item.getPrimaryKey().toString());

		// Item

		ShoppingItemUtil.remove(item.getItemId());
	}

	public void deleteItems(String categoryId)
		throws PortalException, SystemException {

		Iterator itr = ShoppingItemUtil.findByCategoryId(categoryId).iterator();

		while (itr.hasNext()) {
			ShoppingItem item = (ShoppingItem)itr.next();

			deleteItem(item);
		}
	}

	public int getCategoriesItemsCount(List categoryIds)
		throws SystemException {

		return ShoppingItemFinder.countByCategoryIds(categoryIds);
	}

	public List getFeaturedItems(
			String groupId, String categoryId, int numOfItems)
		throws SystemException {

		List featuredItems = ShoppingItemFinder.findByFeatured(
			groupId, new String[] {categoryId}, numOfItems);

		if (featuredItems.size() == 0) {
			List childCategories =
				ShoppingCategoryUtil.findByG_P(groupId, categoryId);

			if (childCategories.size() > 0) {
				String[] categoryIds = new String[childCategories.size()];

				for (int i = 0; i < childCategories.size(); i++) {
					ShoppingCategory childCategory =
						(ShoppingCategory)childCategories.get(i);

					categoryIds[i] = childCategory.getCategoryId();
				}

				featuredItems = ShoppingItemFinder.findByFeatured(
					groupId, categoryIds, numOfItems);
			}
		}

		return featuredItems;
	}

	public ShoppingItem getItem(String itemId)
		throws PortalException, SystemException {

		return ShoppingItemUtil.findByPrimaryKey(itemId);
	}

	public ShoppingItem getItem(String companyId, String sku)
		throws PortalException, SystemException {

		return ShoppingItemUtil.findByC_S(companyId, sku);
	}

	public List getItems(String categoryId) throws SystemException {
		return ShoppingItemUtil.findByCategoryId(categoryId);
	}

	public List getItems(
			String categoryId, int begin, int end, OrderByComparator obc)
		throws SystemException {

		return ShoppingItemUtil.findByCategoryId(categoryId, begin, end, obc);
	}

	public ShoppingItem[] getItemsPrevAndNext(
			String itemId, OrderByComparator obc)
		throws PortalException, SystemException {

		ShoppingItem item = ShoppingItemUtil.findByPrimaryKey(itemId);

		return ShoppingItemUtil.findByCategoryId_PrevAndNext(
			item.getItemId(), item.getCategoryId(), obc);
	}

	public int getItemsCount(String categoryId) throws SystemException {
		return ShoppingItemUtil.countByCategoryId(categoryId);
	}

	public List getSaleItems(
			String groupId, String categoryId, int numOfItems)
		throws SystemException {

		List saleItems = ShoppingItemFinder.findBySale(
			groupId, new String[] {categoryId}, numOfItems);

		if (saleItems.size() == 0) {
			List childCategories =
				ShoppingCategoryUtil.findByG_P(groupId, categoryId);

			if (childCategories.size() > 0) {
				String[] categoryIds = new String[childCategories.size()];

				for (int i = 0; i < childCategories.size(); i++) {
					ShoppingCategory childCategory =
						(ShoppingCategory)childCategories.get(i);

					categoryIds[i] = childCategory.getCategoryId();
				}

				saleItems = ShoppingItemFinder.findBySale(
					groupId, categoryIds, numOfItems);
			}
		}

		return saleItems;
	}

	public List search(
			String groupId, String[] categoryIds, String keywords, int begin,
			int end)
		throws SystemException {

		return ShoppingItemFinder.findByKeywords(
			groupId, categoryIds, keywords, begin, end);
	}

	public int searchCount(
			String groupId, String[] categoryIds, String keywords)
		throws SystemException {

		return ShoppingItemFinder.countByKeywords(
			groupId, categoryIds, keywords);
	}

	public ShoppingItem updateItem(
			String userId, String itemId, String categoryId, String sku,
			String name, String description, String properties,
			String fieldsQuantities, boolean requiresShipping,
			int stockQuantity, boolean featured, Boolean sale,
			boolean smallImage, String smallImageURL, File smallFile,
			boolean mediumImage, String mediumImageURL, File mediumFile,
			boolean largeImage, String largeImageURL, File largeFile,
			List itemFields, List itemPrices)
		throws PortalException, SystemException {

		// Item

		ShoppingItem item = ShoppingItemUtil.findByPrimaryKey(itemId);

		User user = UserUtil.findByPrimaryKey(userId);
		ShoppingCategory category = getCategory(item, categoryId);
		sku = sku.trim().toUpperCase();

		byte[] smallBytes = null;
		byte[] mediumBytes = null;
		byte[] largeBytes = null;

		try {
			smallBytes = FileUtil.getBytes(smallFile);
			mediumBytes = FileUtil.getBytes(mediumFile);
			largeBytes = FileUtil.getBytes(largeFile);
		}
		catch (IOException ioe) {
		}

		validate(
			user.getCompanyId(), itemId, sku, name, smallImage, smallImageURL,
			smallFile, smallBytes, mediumImage, mediumImageURL, mediumFile,
			mediumBytes, largeImage, largeImageURL, largeFile, largeBytes);

		item.setModifiedDate(new Date());
		item.setCategoryId(category.getCategoryId());
		item.setSku(sku);
		item.setName(name);
		item.setDescription(description);
		item.setProperties(properties);
		item.setFields(itemFields.size() > 0);
		item.setFieldsQuantities(fieldsQuantities);

		for (int i = 0; i < itemPrices.size(); i++) {
			ShoppingItemPrice itemPrice = (ShoppingItemPrice)itemPrices.get(i);

			if (itemPrice.getStatus() ==
					ShoppingItemPrice.STATUS_ACTIVE_DEFAULT) {

				item.setMinQuantity(itemPrice.getMinQuantity());
				item.setMaxQuantity(itemPrice.getMaxQuantity());
				item.setPrice(itemPrice.getPrice());
				item.setDiscount(itemPrice.getDiscount());
				item.setTaxable(itemPrice.getTaxable());
				item.setShipping(itemPrice.getShipping());
				item.setUseShippingFormula(
					itemPrice.getUseShippingFormula());
			}

			if ((sale == null) && (itemPrice.getDiscount() > 0) &&
				((itemPrice.getStatus() ==
					ShoppingItemPrice.STATUS_ACTIVE_DEFAULT) ||
				(itemPrice.getStatus() == ShoppingItemPrice.STATUS_ACTIVE))) {

				sale = Boolean.TRUE;
			}
		}

		item.setRequiresShipping(requiresShipping);
		item.setStockQuantity(stockQuantity);
		item.setFeatured(featured);
		item.setSale((sale != null) ? sale.booleanValue() : false);
		item.setSmallImage(smallImage);
		item.setSmallImageURL(smallImageURL);
		item.setMediumImage(mediumImage);
		item.setMediumImageURL(mediumImageURL);
		item.setLargeImage(largeImage);
		item.setLargeImageURL(largeImageURL);

		ShoppingItemUtil.update(item);

		// Fields

		ShoppingItemFieldUtil.removeByItemId(itemId);

		for (int i = 0; i < itemFields.size() && itemFields.size() > 0; i++) {
			ShoppingItemField itemField = (ShoppingItemField)itemFields.get(i);

			String itemFieldId = Long.toString(
				CounterLocalServiceUtil.increment(
					ShoppingItemField.class.getName()));

			itemField.setItemFieldId(itemFieldId);
			itemField.setItemId(itemId);
			itemField.setName(checkItemField(itemField.getName()));
			itemField.setValues(checkItemField(itemField.getValues()));

			ShoppingItemFieldUtil.update(itemField);
		}

		// Prices

		ShoppingItemPriceUtil.removeByItemId(itemId);

		for (int i = 0; i < itemPrices.size() && itemPrices.size() > 1; i++) {
			ShoppingItemPrice itemPrice = (ShoppingItemPrice)itemPrices.get(i);

			String itemPriceId = Long.toString(
				CounterLocalServiceUtil.increment(
					ShoppingItemPrice.class.getName()));

			itemPrice.setItemPriceId(itemPriceId);
			itemPrice.setItemId(itemId);

			ShoppingItemPriceUtil.update(itemPrice);
		}

		// Images

		saveImages(
			smallImage, item.getSmallImageId(), smallFile, smallBytes,
			mediumImage, item.getMediumImageId(), mediumFile, mediumBytes,
			largeImage, item.getLargeImageId(), largeFile, largeBytes);

		return item;
	}

	protected String checkItemField(String value) {
		return StringUtil.replace(
			value,
			new String[] {
				"\"", "&", "'", ".", "=", "|"
			},
			new String[] {
				StringPool.BLANK,
				StringPool.BLANK,
				StringPool.BLANK,
				StringPool.BLANK,
				StringPool.BLANK,
				StringPool.BLANK
			}
		);
	}

	protected String getBookProperties(AmazonRankings amazonRankings) {
		String isbn = amazonRankings.getISBN();

		String authors = StringUtil.merge(amazonRankings.getAuthors(), ", ");

		String publisher =
			amazonRankings.getManufacturer() + "; (" +
			amazonRankings.getReleaseDateAsString() + ")";

		String properties =
			"ISBN=" + isbn + "\nAuthor=" + authors + "\nPublisher=" + publisher;

		return properties;
	}

	protected ShoppingCategory getCategory(ShoppingItem item, String categoryId)
		throws PortalException, SystemException {

		if (!item.getCategoryId().equals(categoryId)) {
			ShoppingCategory oldCategory =
				ShoppingCategoryUtil.findByPrimaryKey(item.getCategoryId());

			ShoppingCategory newCategory =
				ShoppingCategoryUtil.fetchByPrimaryKey(categoryId);

			if ((newCategory == null) ||
				(!oldCategory.getGroupId().equals(newCategory.getGroupId()))) {

				categoryId = item.getCategoryId();
			}
		}

		return ShoppingCategoryUtil.findByPrimaryKey(categoryId);
	}

	protected void saveImages(
		boolean smallImage, String smallImageKey, File smallFile,
		byte[] smallBytes, boolean mediumImage, String mediumImageKey,
		File mediumFile, byte[] mediumBytes, boolean largeImage,
		String largeImageKey, File largeFile, byte[] largeBytes) {

		// Small image

		if (smallImage) {
			if (smallFile != null && smallBytes != null) {
				ImageLocalUtil.put(smallImageKey, smallBytes);
			}
		}
		else {
			ImageLocalUtil.remove(smallImageKey);
		}

		// Medium image

		if (mediumImage) {
			if (mediumFile != null && mediumBytes != null) {
				ImageLocalUtil.put(mediumImageKey, mediumBytes);
			}
		}
		else {
			ImageLocalUtil.remove(mediumImageKey);
		}

		// Large image

		if (largeImage) {
			if (largeFile != null && largeBytes != null) {
				ImageLocalUtil.put(largeImageKey, largeBytes);
			}
		}
		else {
			ImageLocalUtil.remove(largeImageKey);
		}
	}

	protected void validate(
			String companyId, String itemId, String sku, String name,
			boolean smallImage, String smallImageURL, File smallFile,
			byte[] smallBytes, boolean mediumImage, String mediumImageURL,
			File mediumFile, byte[] mediumBytes, boolean largeImage,
			String largeImageURL, File largeFile, byte[] largeBytes)
		throws PortalException, SystemException {

		if (Validator.isNull(sku)) {
			throw new ItemSKUException();
		}

		try {
			ShoppingItem item = ShoppingItemUtil.findByC_S(companyId, sku);

			if (itemId != null) {
				if (!item.getItemId().equals(itemId)) {
					throw new DuplicateItemSKUException();
				}
			}
			else {
				throw new DuplicateItemSKUException();
			}
		}
		catch (NoSuchItemException nsie) {
		}

		if (Validator.isNull(name)) {
			throw new ItemNameException();
		}

		String[] imageExtensions =
			PropsUtil.getArray(PropsUtil.SHOPPING_IMAGE_EXTENSIONS);

		// Small image

		if (smallImage && Validator.isNull(smallImageURL) &&
			smallFile != null && smallBytes != null) {

			String smallImageName = smallFile.getName();

			if (smallImageName != null) {
				boolean validSmallImageExtension = false;

				for (int i = 0; i < imageExtensions.length; i++) {
					if (StringPool.STAR.equals(imageExtensions[i]) ||
						StringUtil.endsWith(
							smallImageName, imageExtensions[i])) {

						validSmallImageExtension = true;

						break;
					}
				}

				if (!validSmallImageExtension) {
					throw new ItemSmallImageNameException(smallImageName);
				}
			}

			long smallImageMaxSize = GetterUtil.getLong(
				PropsUtil.get(PropsUtil.SHOPPING_IMAGE_SMALL_MAX_SIZE));

			if ((smallImageMaxSize > 0) &&
				((smallBytes == null) ||
					(smallBytes.length > smallImageMaxSize))) {

				throw new ItemSmallImageSizeException();
			}
		}

		// Medium image

		if (mediumImage && Validator.isNull(mediumImageURL) &&
			mediumFile != null && mediumBytes != null) {

			String mediumImageName = mediumFile.getName();

			if (mediumImageName != null) {
				boolean validMediumImageExtension = false;

				for (int i = 0; i < imageExtensions.length; i++) {
					if (StringPool.STAR.equals(imageExtensions[i]) ||
						StringUtil.endsWith(
							mediumImageName, imageExtensions[i])) {

						validMediumImageExtension = true;

						break;
					}
				}

				if (!validMediumImageExtension) {
					throw new ItemMediumImageNameException(mediumImageName);
				}
			}

			long mediumImageMaxSize = GetterUtil.getLong(
				PropsUtil.get(PropsUtil.SHOPPING_IMAGE_MEDIUM_MAX_SIZE));

			if ((mediumImageMaxSize > 0) &&
				((mediumBytes == null) ||
					(mediumBytes.length > mediumImageMaxSize))) {

				throw new ItemMediumImageSizeException();
			}
		}

		// Large image

		if (largeImage && Validator.isNull(largeImageURL) &&
			largeFile != null && largeBytes != null) {

			String largeImageName = largeFile.getName();

			if (largeImageName != null) {
				boolean validLargeImageExtension = false;

				for (int i = 0; i < imageExtensions.length; i++) {
					if (StringPool.STAR.equals(imageExtensions[i]) ||
						StringUtil.endsWith(
							largeImageName, imageExtensions[i])) {

						validLargeImageExtension = true;

						break;
					}
				}

				if (!validLargeImageExtension) {
					throw new ItemLargeImageNameException(largeImageName);
				}
			}

			long largeImageMaxSize = GetterUtil.getLong(
				PropsUtil.get(PropsUtil.SHOPPING_IMAGE_LARGE_MAX_SIZE));

			if ((largeImageMaxSize > 0) &&
				((largeBytes == null) ||
					(largeBytes.length > largeImageMaxSize))) {

				throw new ItemLargeImageSizeException();
			}
		}
	}

}