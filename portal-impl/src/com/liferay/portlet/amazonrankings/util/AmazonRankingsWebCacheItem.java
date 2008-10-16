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

package com.liferay.portlet.amazonrankings.util;

import com.amazonaws.a2s.AmazonA2S;
import com.amazonaws.a2s.AmazonA2SClient;
import com.amazonaws.a2s.AmazonA2SException;
import com.amazonaws.a2s.AmazonA2SLocale;
import com.amazonaws.a2s.model.Item;
import com.amazonaws.a2s.model.ItemAttributes;
import com.amazonaws.a2s.model.ItemLookupRequest;
import com.amazonaws.a2s.model.ItemLookupResponse;
import com.amazonaws.a2s.model.Items;
import com.amazonaws.a2s.model.Offer;
import com.amazonaws.a2s.model.OfferListing;
import com.amazonaws.a2s.model.OfferSummary;
import com.amazonaws.a2s.model.Offers;
import com.amazonaws.a2s.model.Price;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webcache.WebCacheException;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portlet.amazonrankings.model.AmazonRankings;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * <a href="AmazonRankingsWebCacheItem.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AmazonRankingsWebCacheItem implements WebCacheItem {

	public AmazonRankingsWebCacheItem(String isbn) {
		_isbn = isbn;
	}

	public Object convert(String key) throws WebCacheException {
		String isbn = _isbn;

		AmazonRankings amazonRankings = null;

		try {
			AmazonA2S a2service = new AmazonA2SClient(
				AmazonRankingsUtil.getAmazonAccessKeyId(),
				AmazonRankingsUtil.getAmazonAssociateTag(), AmazonA2SLocale.US);

			ItemLookupRequest itemLookupRequest = getItemLookupRequest(isbn);

			ItemLookupResponse itemLookupResponse = a2service.itemLookup(
				itemLookupRequest);

			Item item = getItem(itemLookupResponse);

			if (!item.isSetItemAttributes()) {
				throw new AmazonA2SException("Item attributes is not set");
			}

			ItemAttributes itemAttributes = item.getItemAttributes();

			String productName = itemAttributes.getTitle();
			String catalog = StringPool.BLANK;

			String[] authors = null;

			if (itemAttributes.isSetAuthor()) {
				List<String> authorsList = itemAttributes.getAuthor();

				authorsList.toArray(new String[authorsList.size()]);
			}

			Date releaseDate = null;
			String releaseDateAsString = null;

			if (itemAttributes.isSetPublicationDate()) {
				releaseDateAsString = itemAttributes.getPublicationDate();

				SimpleDateFormat dateFormat = null;

				if (releaseDateAsString.length() > 7) {
					dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
				}
				else {
					dateFormat = new SimpleDateFormat("yyyy-MM", Locale.US);
				}

				releaseDate = GetterUtil.getDate(
					releaseDateAsString, dateFormat);
			}

			String manufacturer = itemAttributes.getManufacturer();

			String smallImageURL = null;

			if (item.isSetSmallImage()) {
				smallImageURL = item.getSmallImage().getURL();
			}

			String mediumImageURL = null;

			if (item.isSetMediumImage()) {
				mediumImageURL = item.getMediumImage().getURL();
			}

			String largeImageURL = null;

			if (item.isSetLargeImage()) {
				largeImageURL = item.getLargeImage().getURL();
			}

			double listPrice = 0;
			double ourPrice = 0;
			double usedPrice = 0;
			double collectiblePrice = 0;
			double thirdPartyNewPrice = 0;

			listPrice = getPrice(itemAttributes.getListPrice());

			OfferListing offerListing = getOfferListing(item);

			if (offerListing != null) {
				ourPrice = getPrice(offerListing.getPrice());
			}

			if (item.isSetOfferSummary()) {
				OfferSummary offerSummary = item.getOfferSummary();

				usedPrice = getPrice(offerSummary.getLowestUsedPrice());
				collectiblePrice = getPrice(
					offerSummary.getLowestCollectiblePrice());
				thirdPartyNewPrice = getPrice(
					offerSummary.getLowestNewPrice());

			}

			int salesRank = GetterUtil.getInteger(item.getSalesRank());
			String media = StringPool.BLANK;

			String availability = null;

			if (offerListing != null) {
				availability = offerListing.getAvailability();
			}

			amazonRankings = new AmazonRankings(
				isbn, productName, catalog, authors, releaseDate,
				releaseDateAsString, manufacturer, smallImageURL,
				mediumImageURL, largeImageURL, listPrice, ourPrice, usedPrice,
				collectiblePrice, thirdPartyNewPrice, salesRank, media,
				availability);
		}
		catch (Exception e) {
			throw new WebCacheException(isbn + " " + e.toString());
		}

		return amazonRankings;
	}

	public long getRefreshTime() {
		return _REFRESH_TIME;
	}

	protected Item getItem(ItemLookupResponse itemLookupResponse)
		throws Exception {

		List<Items> itemsList = itemLookupResponse.getItems();

		if (itemsList.isEmpty()) {
			throw new AmazonA2SException("Items list is empty");
		}

		Items items = itemsList.get(0);

		List<Item> itemList = items.getItem();

		if (itemList.isEmpty()) {
			throw new AmazonA2SException("Item list is empty");
		}

		return itemList.get(0);
	}

	protected ItemLookupRequest getItemLookupRequest(String isbn) {
		ItemLookupRequest itemLookupRequest = new ItemLookupRequest();

		// ISBN

		List<String> itemId = new ArrayList<String>();

		itemId.add(isbn);

		itemLookupRequest.setItemId(itemId);

		// Response group

		List<String> responseGroup = new ArrayList<String>();

		responseGroup.add("Images");
		responseGroup.add("ItemAttributes");
		responseGroup.add("Offers");
		responseGroup.add("SalesRank");

		itemLookupRequest.setResponseGroup(responseGroup);

		return itemLookupRequest;
	}

	protected OfferListing getOfferListing(Item item) {
		Offers offers = item.getOffers();

		List<Offer> offersList = offers.getOffer();

		if (offersList.isEmpty()) {
			return null;
		}

		Offer offer = offersList.get(0);

		List<OfferListing> offerListings = offer.getOfferListing();

		if (offerListings.isEmpty()) {
			return null;
		}

		return offerListings.get(0);
	}

	protected double getPrice(Price price) {
		if (price == null) {
			return 0;
		}

		return price.getAmount() * 0.01;
	}

	private static final long _REFRESH_TIME = Time.MINUTE * 20;

	private String _isbn;

}