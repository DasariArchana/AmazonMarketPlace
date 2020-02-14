package com.cleo.mws.util;

import java.util.HashMap;
import java.util.Map;

import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersRequest;

public class OrderServiceHelper {

	public static ListOrdersRequest createListOrderRequest(Map<String, String> paramMap, int maxResultsPerPage,
			String sellerId, String mwsAuthToken) {
		
		ListOrdersRequest request = new ListOrdersRequest();
        //String sellerId = "A2P4V014Y1N8XJ";
        request.setSellerId(sellerId);
        //String mwsAuthToken = "amzn.mws.deb6d797-477b-9b7e-3530-4c406d911620";
        request.setMWSAuthToken(mwsAuthToken);
       
        try {
        	if(paramMap.containsKey("createdAfter")) request.setCreatedAfter(CommonUtils.convertToGregorianCalendar(paramMap.get("createdAfter")));
        	if(paramMap.containsKey("createdBefore")) request.setCreatedBefore(CommonUtils.convertToGregorianCalendar(paramMap.get("createdBefore")));
        	if(paramMap.containsKey("lastUpdatedAfter")) request.setLastUpdatedAfter(CommonUtils.convertToGregorianCalendar(paramMap.get("lastUpdatedAfter")));
        	if(paramMap.containsKey("lastUpdatedBefore")) request.setLastUpdatedBefore(CommonUtils.convertToGregorianCalendar(paramMap.get("lastUpdatedBefore")));
           
        } catch(Exception e){
            e.printStackTrace();
        }
        
        if(paramMap.containsKey("marketplaceId")) request.setMarketplaceId(CommonUtils.convertToList(paramMap.get("marketplaceId")));
        if(maxResultsPerPage > 0) request.setMaxResultsPerPage(maxResultsPerPage);
        if(paramMap.containsKey("orderStatus")) request.setOrderStatus(CommonUtils.convertToList(paramMap.get("orderStatus")));
        if(paramMap.containsKey("fulfillmentChannel")) request.setFulfillmentChannel(CommonUtils.convertToList(paramMap.get("fulfillmentChannel")));
        if(paramMap.containsKey("paymentMethod")) request.setPaymentMethod(CommonUtils.convertToList(paramMap.get("paymentMethod")));
        if(paramMap.containsKey("buyerEmail")) request.setBuyerEmail(paramMap.get("buyerEmail"));
        if(paramMap.containsKey("sellerOrderId")) request.setSellerOrderId(paramMap.get("sellerOrderId"));
        if(paramMap.containsKey("tFMShipmentStatus")) request.setTFMShipmentStatus(CommonUtils.convertToList(paramMap.get("tFMShipmentStatus")));
        if(paramMap.containsKey("easyShipShipmentStatus")) request.setEasyShipShipmentStatus(CommonUtils.convertToList(paramMap.get("easyShipShipmentStatus")));
        return request;
	}

	public static Map<String, String> buildQueryParamsMap(String createdAfter, String createdBefore, String lastUpdatedAfter,
			String lastUpdatedBefore, String orderStatus, String marketplaceId, String fulfillmentChannel,
			String paymentMethod, String buyerEmail, String sellerOrderId, int maxResultsPerPage,
			String tFMShipmentStatus, String easyShipShipmentStatus) {
		
		Map<String, String> paramMap = new HashMap<>();
		//Validation Parameters
		if(createdAfter != null) addToMap(paramMap, "createdAfter", createdAfter);
		if(createdBefore != null) addToMap(paramMap, "createdBefore", createdBefore);
		if(lastUpdatedAfter != null) addToMap(paramMap, "lastUpdatedAfter", lastUpdatedAfter);
		if(lastUpdatedBefore != null) addToMap(paramMap, "lastUpdatedBefore", lastUpdatedBefore);
		if(buyerEmail != null) addToMap(paramMap, "buyerEmail", buyerEmail);
		
		//Other Parameters
		if(orderStatus != null) addToMap(paramMap, "orderStatus", orderStatus);
		if(marketplaceId != null) addToMap(paramMap, "marketplaceId", marketplaceId);
		if(fulfillmentChannel != null) addToMap(paramMap, "fulfillmentChannel", fulfillmentChannel);
		if(paymentMethod != null) addToMap(paramMap, "paymentMethod", paymentMethod);
		if(sellerOrderId != null) addToMap(paramMap, "sellerOrderId", sellerOrderId);
		if(tFMShipmentStatus != null) addToMap(paramMap, "tFMShipmentStatus", tFMShipmentStatus);
		if(easyShipShipmentStatus != null) addToMap(paramMap, "easyShipShipmentStatus", easyShipShipmentStatus);
		
		return paramMap;
	}


	private static void addToMap(Map<String, String> paramMap, String key, String value) {
		paramMap.put(key, value);
	}
	
	public static void main(String args []) {
		/*String inputDateString = "2011-11-06T14:34:16.679+0200";
		try {
		    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		    Date inputDate = format.parse(inputDateString);         
		    System.out.println(inputDate);
		    GregorianCalendar gregCalendar = new GregorianCalendar();
		    gregCalendar.setTime(inputDate);
		    XMLGregorianCalendar xmlGregCalender = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalendar);
		    System.out.println(xmlGregCalender);
		} catch(Exception e){
		    e.printStackTrace();
		}
		
		String str = "abc, cde";
		List<String> list = convertToList(str);
		System.out.println(list.get(0));
		System.out.println(list.get(1));*/
	}
}
