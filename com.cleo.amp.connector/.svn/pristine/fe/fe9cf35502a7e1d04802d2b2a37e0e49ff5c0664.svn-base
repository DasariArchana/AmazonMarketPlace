package com.cleo.mws.service.config;

import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersAsyncClient;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersClient;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersConfig;

public class AMPWebServiceOrderConfig {

    /** The client application name. */
    private static final String appName = "AMPConnector";

    /** The client application version. */
    private static final String appVersion = "2013-09-01";

    /**
     * The endpoint for region service and version.
     */
    private static final String serviceURL = "https://mws.amazonservices.com/";

    /** The client, lazy initialized. Async client is also a sync client. */
    private static MarketplaceWebServiceOrdersAsyncClient client = null;

    /**
     * Get a client connection ready to use.
     *
     * @return A ready to use client connection.
     */
    public static MarketplaceWebServiceOrdersClient getClient(String accessKey, String secretKey) {
        return getAsyncClient(accessKey, secretKey);
    }

    /**
     * Get an async client connection.
     *
     * @return A ready to use client connection.
     */
    public static synchronized MarketplaceWebServiceOrdersAsyncClient getAsyncClient(String accessKey, String secretKey) {
        if (client==null) {
            MarketplaceWebServiceOrdersConfig config = new MarketplaceWebServiceOrdersConfig();
            config.setServiceURL(serviceURL);
            // Set other client connection configurations here.
            client = new MarketplaceWebServiceOrdersAsyncClient(accessKey, secretKey, 
                    appName, appVersion, config, null);
            //client = new MarketplaceWebServiceOrdersMock();
        }
        return client;
    }
}
