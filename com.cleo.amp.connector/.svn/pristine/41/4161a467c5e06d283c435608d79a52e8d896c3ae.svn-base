package com.cleo.mws.service.config;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceClient;
import com.amazonaws.mws.MarketplaceWebServiceConfig;

public class AMPWebServiceFeedConfig {

	final String appName = "AMPConnector";
    final String appVersion = "2009-01-01";
    final String serviceUrl = "https://mws.amazonservices.com/";
    
    private String accessKey, secretKey;

	public AMPWebServiceFeedConfig(String accessKey, String secretKey) {
		super();
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}
	
	public MarketplaceWebService getMarketplaceWebServiceClientObject() {
		MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();
		config.setServiceURL(serviceUrl);
		
		MarketplaceWebService service = new MarketplaceWebServiceClient(
				accessKey, secretKey, appName, appVersion, config);
		
		return service;
	}
}
