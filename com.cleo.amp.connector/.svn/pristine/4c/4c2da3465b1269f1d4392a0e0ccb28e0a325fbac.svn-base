package com.cleo.mws.util;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.mws.model.SubmitFeedRequest;

public class FeedServiceHelper {

	public static SubmitFeedRequest buildSubmitFeedRequest(String sellerId, String mwsAuthToken, 
			Map<String, String> paramMap, boolean purgeAndReplace) {
		SubmitFeedRequest feedRequest = new SubmitFeedRequest();
		feedRequest.setMWSAuthToken(mwsAuthToken);
		feedRequest.setMerchant(sellerId);
		if(purgeAndReplace) feedRequest.setPurgeAndReplace(purgeAndReplace);
		if(paramMap.containsKey("feedType")) feedRequest.setFeedType(paramMap.get("feedType"));
		if(paramMap.containsKey("contentMD5Value")) feedRequest.setContentMD5(paramMap.get("contentMD5Value"));
		if(paramMap.containsKey("marketplaceId")) feedRequest.setMarketplaceIdList(CommonUtils.convertToIdList(paramMap.get("marketplaceId")));
		//if(paramMap.containsKey("contentMD5Value")) feedRequest.set(paramMap.get("contentMD5Value"));
		
		return feedRequest;
	}
	
	public static Map<String, String> buildQueryParamsMap(String feedType, 
			String marketplaceId, boolean purgeAndReplace, String contentMD5Value, String 
			amazonOrderId, String documentType) {
		
		Map<String, String> paramMap = new HashMap<>();
		
		//Validation Parameters
		if(feedType != null) addToMap(paramMap, "feedType", feedType);
		if(marketplaceId != null) addToMap(paramMap, "marketplaceId", marketplaceId);
		if(contentMD5Value != null) addToMap(paramMap, "contentMD5Value", contentMD5Value);
		if(amazonOrderId != null) addToMap(paramMap, "amazonOrderId", amazonOrderId);
		if(documentType != null) addToMap(paramMap, "documentType", documentType);
		return paramMap;
	}
	
	private static void addToMap(Map<String, String> paramMap, String key, String value) {
		paramMap.put(key, value);
	}
}
