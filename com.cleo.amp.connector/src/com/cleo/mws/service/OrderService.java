package com.cleo.mws.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

import com.amazonaws.mws.model.ErrorResponse;
import com.amazonservices.mws.client.MwsException;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersClient;
import com.amazonservices.mws.orders._2013_09_01.model.GetOrderRequest;
import com.amazonservices.mws.orders._2013_09_01.model.GetOrderResponse;
import com.amazonservices.mws.orders._2013_09_01.model.GetServiceStatusRequest;
import com.amazonservices.mws.orders._2013_09_01.model.GetServiceStatusResponse;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsByNextTokenRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsByNextTokenResponse;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsResponse;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersByNextTokenRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersByNextTokenResponse;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersResponse;
import com.amazonservices.mws.orders._2013_09_01.model.ResponseHeaderMetadata;
import com.cleo.mws.service.config.AMPWebServiceOrderConfig;
import com.cleo.mws.util.OrderServiceHelper;
import com.cleo.mws.validation.CommonValidator;
import com.cleo.mws.validation.OrderValidator;
import com.extol.base.util.uuid.IUUID;
import com.extol.ebi.api.core.operation.ApiConfigurationLoader;
import com.extol.ebi.api.customconnector.core.config.AMPClientConfiguration;
import com.extol.ebi.api.customconnector.core.configloader.*;
import com.extol.ebi.api.customconnector.core.http.impl.request.AbstractEntityRequest;
import com.extol.ebi.api.customconnector.core.http.interfaces.IRuntimeCustomConfigurationWrapper;
import com.extol.ebi.api.customconnector.core.http.interfaces.Operation;

import com.extol.ebi.factory.EBIFactoryManager;
import com.extol.storage.IStorageID;
import com.extol.storage.exceptions.StorageException;

import config.ClientConfigWrapper;

public class OrderService extends AbstractEntityRequest implements Operation {

	private static Logger logger = Logger.getLogger(OrderService.class.getSimpleName());
	private IRuntimeCustomConfigurationWrapper wrapper = null;
	/*
	 * String sellerId = "A2P4V014Y1N8XJ"; String mwsAuthToken =
	 * "amzn.mws.deb6d797-477b-9b7e-3530-4c406d911620"; String accessKey =
	 * "AKIAJHWI4C3JGUIMD2QQ"; String secretKey =
	 * "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";
	 */

	public Response listOrders(String sellerId, String mwsAuthToken, String accessKey, String secretKey,
			String serviceURL,

			String createdAfter, String createdBefore, String lastUpdatedAfter, String lastUpdatedBefore,
			String orderStatus, String marketplaceId, String fulfillmentChannel, String paymentMethod,
			String buyerEmail, String sellerOrderId, int maxResultsPerPage, String tFMShipmentStatus,
			String easyShipShipmentStatus) {

		MarketplaceWebServiceOrdersClient client = AMPWebServiceOrderConfig.getClient(accessKey, secretKey);

		Map<String, String> paramMap = OrderServiceHelper.buildQueryParamsMap(createdAfter, createdBefore,
				lastUpdatedAfter, lastUpdatedBefore, orderStatus, marketplaceId, fulfillmentChannel, paymentMethod,
				buyerEmail, sellerOrderId, maxResultsPerPage, tFMShipmentStatus, easyShipShipmentStatus);
		ErrorResponse errorResponse = null;
		errorResponse = OrderValidator.validateListOrders(paramMap);

		if (errorResponse.isSetError()) {
			logger.log(Level.SEVERE, errorResponse.toJSON());
			return Response.status(Status.BAD_REQUEST).entity(errorResponse.toJSON()).build();
		}

		// Create a ListOrdersRequest Object.
		ListOrdersRequest request = OrderServiceHelper.createListOrderRequest(paramMap, maxResultsPerPage, sellerId,
				mwsAuthToken);

		// Call the service.
		try {
			ListOrdersResponse response = client.listOrders(request);
			String responseJson = response.toJSON();
			logger.fine(responseJson);

			return Response.status(Status.OK).entity(responseJson).build();
		} catch (MwsException e) {
			logError(e.getMessage());
			return createMWSErrorResponse(e);
		}
	}

	@GET
	@Path("token")
	@Produces(MediaType.APPLICATION_JSON)

	public Response listOrdersByNextToken(String sellerId, String mwsAuthToken, String accessKey, String secretKey,
			String serviceURL, @QueryParam("nextToken") String nextToken) {
		MarketplaceWebServiceOrdersClient client = AMPWebServiceOrderConfig.getClient(accessKey, secretKey);

		// Create a request.
		ListOrdersByNextTokenRequest request = new ListOrdersByNextTokenRequest();
		request.setSellerId(sellerId);
		request.setMWSAuthToken(mwsAuthToken);
		request.setNextToken(nextToken);
		try {
			ListOrdersByNextTokenResponse response = client.listOrdersByNextToken(request);

			ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();

			logInfo("RequestId: " + rhmd.getRequestId());
			logInfo("Timestamp: " + rhmd.getTimestamp());

			String responseJson = response.toJSON();
			logger.fine(responseJson);

			return Response.status(Status.OK).entity(responseJson).build();
		} catch (MwsException e) {
			return createMWSErrorResponse(e);
		}
	}

	public Response getOrder(String sellerId, String mwsAuthToken, String accessKey, String secretKey,
			String serviceURL, String amazonOrderIds) {

		logger.info("Inside gerOrder method...");
		System.out.println("Inside gerOrder method...");
		MarketplaceWebServiceOrdersClient client = AMPWebServiceOrderConfig.getClient(accessKey, secretKey);
		List<String> amazonOrderIdList = Arrays.asList(amazonOrderIds.split(","));

		logger.info("Got client...");
		System.out.println("Inside gerOrder method...");
		// Create new request for GetOrder
		GetOrderRequest request = new GetOrderRequest();
		request.setSellerId(sellerId);
		request.setMWSAuthToken(mwsAuthToken);
		request.setAmazonOrderId(amazonOrderIdList);
		try {
			GetOrderResponse response = client.getOrder(request);
			logger.info("Invoking getOrder SDK...");
			ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
			logger.info("Response:");
			logger.info("RequestId: " + rhmd.getRequestId());
			logger.info("Timestamp: " + rhmd.getTimestamp());

			String responseJson = response.toJSON();
			logger.fine(responseJson);

			return Response.status(Status.OK).entity(responseJson).build();
		} catch (MwsException e) {
			return createMWSErrorResponse(e);
		}
	}

	public String listOrderItems(String amazonOrderId) throws Exception {

		logger.info("Inside List Order Items");
		setRuntimeWrapper(ClientConfigWrapper.getInstance());

		ApiConfigurationLoader loader = new RuntimeCustomConfigurationLoader(wrapper);

		logger.info("is wrapper not null?" + (wrapper != null));

		Object config = loader.loadConfig();
		logger.info("is config not null?" + (config != null));
		ListOrderItemsRequest request = new ListOrderItemsRequest();
		AMPClientConfiguration configuration = null;
		configuration = (AMPClientConfiguration) config;

		request.setSellerId(configuration.getSellerId());
		request.setMWSAuthToken(configuration.getMwsAuthToken());

		request.setAmazonOrderId(amazonOrderId);
		MarketplaceWebServiceOrdersClient client = AMPWebServiceOrderConfig.getClient(configuration.getAccessKey(),
				configuration.getSecretKey());

		try {

			ListOrderItemsResponse response = client.listOrderItems(request);
			ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
			logger.info("Response:");
			logger.info("RequestId: " + rhmd.getRequestId());
			logger.info("Timestamp: " + rhmd.getTimestamp());
			String responseJson = response.toJSON();
			logger.fine(responseJson);
			return responseJson;

		} catch (MwsException e) {
			return createMWSErrorResponseString(e);
		}
	}

	public static Response listOrderItemsByNextToken(String sellerId, String mwsAuthToken, String accessKey,
			String secretKey, String serviceURL, String nextToken) {
		MarketplaceWebServiceOrdersClient client = AMPWebServiceOrderConfig.getClient(accessKey, secretKey);

		ListOrderItemsByNextTokenRequest request = new ListOrderItemsByNextTokenRequest();
		request.setSellerId(sellerId);
		request.setMWSAuthToken(mwsAuthToken);
		request.setNextToken(nextToken);
		try {
			ListOrderItemsByNextTokenResponse response = client.listOrderItemsByNextToken(request);
			ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
			logger.info("Response:");
			logger.info("RequestId: " + rhmd.getRequestId());
			logger.info("Timestamp: " + rhmd.getTimestamp());

			String responseJson = response.toJSON();
			logger.fine(responseJson);

			return Response.status(Status.OK).entity(responseJson).build();
		} catch (MwsException e) {
			return createMWSErrorResponse(e);
		}

	}

	public static Response getServiceStatus(String sellerId, String mwsAuthToken, String accessKey, String secretKey,
			String serviceURL) {
		MarketplaceWebServiceOrdersClient client = AMPWebServiceOrderConfig.getClient(accessKey, secretKey);

		GetServiceStatusRequest request = new GetServiceStatusRequest();
		request.setSellerId(sellerId);
		request.setMWSAuthToken(mwsAuthToken);
		try {
			GetServiceStatusResponse response = client.getServiceStatus(request);
			ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
			logger.info("Response:");
			logger.info("RequestId: " + rhmd.getRequestId());
			logger.info("Timestamp: " + rhmd.getTimestamp());
			logger.info("Max Quota: " + rhmd.getQuotaMax());
			logger.info("Remaining Quota: " + rhmd.getQuotaRemaining());
			logger.info("Reset Quota time: " + rhmd.getQuotaResetsAt());
			String responseJson = response.toJSON();
			logger.fine(responseJson);

			return Response.status(Status.OK).entity(responseJson).build();
		} catch (MwsException e) {
			return createMWSErrorResponse(e);
		}
	}

	private static Response createErrorResponse(Exception e) {

		logger.log(Level.SEVERE, e.getMessage());
		List<com.amazonaws.mws.model.Error> errors = new ArrayList<>();
		errors.add(CommonValidator.generateError("InvalidParameterValue", e.getMessage()));
		return getErrorResponse(errors);
	}

	private static Response getErrorResponse(List<com.amazonaws.mws.model.Error> errors) {

		ErrorResponse errorResponse = new ErrorResponse(errors, "");
		logger.log(Level.SEVERE, errorResponse.toJSON());
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse.toJSON()).build();
	}

	private static Response createMWSErrorResponse(MwsException e) {
		List<com.amazonaws.mws.model.Error> errors = new ArrayList<>();
		errors.add(CommonValidator.generateError(e.getErrorType(), e.getErrorCode(), e.getMessage()));
		return getErrorResponse(errors);
	}

	private static String createMWSErrorResponseString(MwsException e) {
		List<com.amazonaws.mws.model.Error> errors = new ArrayList<>();
		errors.add(CommonValidator.generateError(e.getErrorType(), e.getErrorCode(), e.getMessage()));
		ErrorResponse errorResponse = new ErrorResponse(errors, "");
		return errorResponse.toJSON();
	}
	
	private void logInfo(String message) {
		logger.info(message);
	}

	private void logError(String message) {
		logger.severe(message);
	}

	@Override
	protected HttpRequestBase initHttpMethod() {
		return new HttpGet();
	}

	@Override
	public void setRuntimeWrapper(IRuntimeCustomConfigurationWrapper wrapper) {
		this.wrapper = wrapper;
	}

}
