package com.cleo.mws.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.CancelFeedSubmissionsRequest;
import com.amazonaws.mws.model.CancelFeedSubmissionsResponse;
import com.amazonaws.mws.model.ErrorResponse;
import com.amazonaws.mws.model.GetFeedSubmissionCountRequest;
import com.amazonaws.mws.model.GetFeedSubmissionCountResponse;
import com.amazonaws.mws.model.GetFeedSubmissionListByNextTokenRequest;
import com.amazonaws.mws.model.GetFeedSubmissionListByNextTokenResponse;
import com.amazonaws.mws.model.GetFeedSubmissionListRequest;
import com.amazonaws.mws.model.GetFeedSubmissionListResponse;
import com.amazonaws.mws.model.GetFeedSubmissionResultRequest;
import com.amazonaws.mws.model.GetFeedSubmissionResultResponse;
import com.amazonaws.mws.model.SubmitFeedRequest;
import com.amazonaws.mws.model.SubmitFeedResponse;
import com.cleo.mws.service.config.AMPWebServiceFeedConfig;
import com.cleo.mws.util.CommonUtils;
import com.cleo.mws.util.FeedServiceHelper;
import com.cleo.mws.validation.CommonValidator;
import com.cleo.mws.validation.FeedValidator;

public class FeedService {
	
	private static final Logger logger = Logger.getLogger(FeedService.class.getSimpleName());


	public static Response submitFeed(String sellerId, 
			String mwsAuthToken,
			String accessKey,
			String secretKey,
			String serviceURL,
			String feedType,
			String marketplaceId,
			boolean purgeAndReplace,
			String contentMD5Value,
			String amazonOrderId,
			String documentType,
			
			InputStream feedContent) {
		
		MarketplaceWebService service = new AMPWebServiceFeedConfig(accessKey, secretKey).getMarketplaceWebServiceClientObject();
		SubmitFeedResponse response = null;
		FileInputStream fileInputStream = null;
		try {
			File contentFile =  CommonUtils.createTempFile(feedContent);
			fileInputStream = new FileInputStream(contentFile);
			contentMD5Value = CommonUtils.computeContentMD5Value(fileInputStream);
			//		String contentMD5Value = 
			Map<String, String> paramMap = FeedServiceHelper.buildQueryParamsMap(feedType, marketplaceId, purgeAndReplace, contentMD5Value, amazonOrderId, documentType);
			
			ErrorResponse errorResponse = FeedValidator.validateSubmitFeed(paramMap);
	
			if(errorResponse.isSetError()) {
				logger.log(Level.SEVERE, errorResponse.toJSON());
				return Response.status(Status.BAD_REQUEST).entity(errorResponse.toJSON()).build();
			}
			
			SubmitFeedRequest feedRequest = FeedServiceHelper.buildSubmitFeedRequest(sellerId, 
					mwsAuthToken, paramMap, purgeAndReplace);
			
			
			feedRequest.setFeedContent(fileInputStream);
			response = service.submitFeed(feedRequest);
			System.out.println("Max Quota: " + response.getResponseHeaderMetadata().getQuotaMax());
			System.out.println("Quota Remaining " + response.getResponseHeaderMetadata().getQuotaRemaining());
			logger.info(response.toJSON());
			contentFile.deleteOnExit();
		} catch (MarketplaceWebServiceException e) {
			return createMWSErrorResponse(e);
		} catch (NoSuchAlgorithmException | IOException e) {
			return createErrorResponse(e);
		} finally {
			if(fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {

					logger.severe(e.getMessage());
				}
			}
		}
		// Make the call.
        return Response.status(Status.OK).entity(response.toJSON()).build();
		
	}
	
	public static Response getFeedSubmissionList(
			String sellerId, 
			String mwsAuthToken,
			String accessKey,
			String secretKey,
			
			String feedSubmissionIdList,
			int maxCount,
			String feedTypeList,
			String feedProcessingStatusList,
			String submittedFromDate,
			String submittedToDate) {
		MarketplaceWebService service = new AMPWebServiceFeedConfig(accessKey, secretKey).getMarketplaceWebServiceClientObject();
		GetFeedSubmissionListResponse response = null;
		try {
			GetFeedSubmissionListRequest request = createFeedSubmissionListRequestObject(sellerId, mwsAuthToken, 
					feedSubmissionIdList, maxCount, feedTypeList,
					feedProcessingStatusList, submittedFromDate, submittedToDate);
			
			response = service.getFeedSubmissionList(request);
			logger.info(response.toJSON());
		} catch (ParseException e) {
			return createErrorResponse(e);
		} catch (MarketplaceWebServiceException e) {
			return createMWSErrorResponse(e);
		}
		// Make the call.
        return Response.status(Status.OK).entity(response.toJSON()).build();
		
	}
	
	public static Response getFeedSubmissionResult(
			String sellerId, 
			String mwsAuthToken,
			String accessKey,
			String secretKey,
			
			@javax.ws.rs.PathParam("feedSubmissionId") String feedSubmissionId) {
		MarketplaceWebService service = new AMPWebServiceFeedConfig(accessKey, secretKey).getMarketplaceWebServiceClientObject();
		GetFeedSubmissionResultRequest request = new GetFeedSubmissionResultRequest(null, sellerId, feedSubmissionId, mwsAuthToken);
		GetFeedSubmissionResultResponse response = null;
		String fileName = "feedSubmissionResult" + feedSubmissionId +".xml";
		try {
			File f = new File(fileName);
			OutputStream processingResult = new FileOutputStream(f);
	        request.setFeedSubmissionResultOutputStream( processingResult );
			response = service.getFeedSubmissionResult(request);
			FileInputStream fin = new FileInputStream(f);
			
			int ch; 
			StringBuilder responseXML = new StringBuilder();
	        while(( ch = fin.read())!=-1) 
	        	responseXML.append((char)ch); 
	          
	        //close the file 
	        response.getGetFeedSubmissionResultResult().setMD5Checksum(responseXML.toString());
	        fin.close(); 
		} catch (MarketplaceWebServiceException e) {
			return createMWSErrorResponse(e);
		} catch (FileNotFoundException e) {
			createErrorResponse(e);
		} catch (IOException e) {
			createErrorResponse(e);
		}
		System.out.println(response);
		logger.info(response.toXML());
		System.out.println("Max Quota: " + response.getResponseHeaderMetadata().getQuotaMax());
		System.out.println("Quota Remaining " + response.getResponseHeaderMetadata().getQuotaRemaining());
		// Make the call.
        return Response.status(Status.OK).entity(response.toJSON()).
        		header("x-mws-quota-max", response.getResponseHeaderMetadata().getQuotaMax()).
        		header("x-mws-quota-remaining", response.getResponseHeaderMetadata().getQuotaRemaining()).
        		header("x-mws-quota-resetsOn", response.getResponseHeaderMetadata().getQuotaResetsAt()).build();
		
	}

	public static Response getFeedSubmissionListByNextToken(
			String sellerId, 
			String mwsAuthToken,
			String accessKey,
			String secretKey,
			
			String nextToken) {
		MarketplaceWebService service = new AMPWebServiceFeedConfig(accessKey, secretKey).getMarketplaceWebServiceClientObject();
		GetFeedSubmissionListByNextTokenRequest request = new GetFeedSubmissionListByNextTokenRequest(
				null, sellerId, nextToken, mwsAuthToken);
		GetFeedSubmissionListByNextTokenResponse  response = null;
		try {
			// Make the call.
			response = service.getFeedSubmissionListByNextToken(request);
		} catch (MarketplaceWebServiceException e) {
			return createMWSErrorResponse(e);
		}
		
		
        return Response.status(Status.OK).entity(response.toJSON()).build();
		
	}
	
	public static Response getFeedSubmissionCount(
			String sellerId, 
			String mwsAuthToken,
			String accessKey,
			String secretKey,
			
			String feedTypeList,
			String feedProcessingStatusList,
			String submittedFromDate,
			String submittedToDate) {
		MarketplaceWebService service = new AMPWebServiceFeedConfig(accessKey, secretKey).getMarketplaceWebServiceClientObject();
		GetFeedSubmissionCountResponse response = null;
		try {
			GetFeedSubmissionCountRequest request = createFeedSubmissionCountRequestObject(sellerId, mwsAuthToken, feedTypeList, feedProcessingStatusList,
					submittedFromDate, submittedToDate);
			// Make the call.
			response = service.getFeedSubmissionCount(request);
		} catch (MarketplaceWebServiceException e) {
			return createMWSErrorResponse(e);
		} catch (ParseException e) {
			createErrorResponse(e);
		}
		
		return Response.status(Status.OK).entity(response.toJSON()).build();
		
	}
	
	public static Response cancelFeedSubmissions(
			String sellerId, 
			String mwsAuthToken,
			String accessKey,
			String secretKey,
			String feedTypeList,
			String feedSubmissionIdList,
			String submittedFromDate,
			String submittedToDate) {
		
		MarketplaceWebService service = new AMPWebServiceFeedConfig(accessKey, secretKey).getMarketplaceWebServiceClientObject();
		CancelFeedSubmissionsResponse response = null;
		try {
			CancelFeedSubmissionsRequest request = createCancelFeedSubmissionRequestObject(sellerId, mwsAuthToken, feedSubmissionIdList, feedTypeList, 
					submittedFromDate, submittedToDate);
					
			// Make the call.
			response = service.cancelFeedSubmissions(request);
		} catch (ParseException e) {
			return createErrorResponse(e);
		} catch (MarketplaceWebServiceException e) {
			createMWSErrorResponse(e);
		}
		return Response.status(Status.OK).entity(response.toJSON()).build();
		
	}

	private static CancelFeedSubmissionsRequest createCancelFeedSubmissionRequestObject(String sellerId, String mwsAuthToken,
			String feedTypeList, String feedSubmissionIdList, String submittedFromDate, String submittedToDate) throws ParseException {
		return new CancelFeedSubmissionsRequest(null, sellerId, CommonUtils.convertToIdList(feedSubmissionIdList), CommonUtils.convertToTypeList(feedTypeList), 
				CommonUtils.convertToGregorianCalendar(submittedFromDate), CommonUtils.convertToGregorianCalendar(submittedToDate), mwsAuthToken);
	}
	
	
	private static GetFeedSubmissionListRequest createFeedSubmissionListRequestObject(String sellerId, String mwsAuthToken,
			String feedSubmissionIdList, int maxCount, String feedTypeList, String feedProcessingStatusList,
			String submittedFromDate, String submittedToDate) throws ParseException {
		return new GetFeedSubmissionListRequest
				(null, sellerId, CommonUtils.convertToIdList(feedSubmissionIdList), maxCount, CommonUtils.convertToTypeList(feedTypeList), 
						CommonUtils.convertToStatusList(feedProcessingStatusList), 
						CommonUtils.convertToGregorianCalendar(submittedFromDate), CommonUtils.convertToGregorianCalendar(submittedToDate),
						mwsAuthToken);
	}
	
	private static GetFeedSubmissionCountRequest createFeedSubmissionCountRequestObject(String sellerId, String mwsAuthToken,
			String feedTypeList, String feedProcessingStatusList, String submittedFromDate, String submittedToDate) throws ParseException {
		return new GetFeedSubmissionCountRequest(null, sellerId, 
				CommonUtils.convertToTypeList(feedTypeList), CommonUtils.convertToStatusList(feedProcessingStatusList), 
				CommonUtils.convertToGregorianCalendar(submittedFromDate), CommonUtils.convertToGregorianCalendar(submittedToDate), mwsAuthToken);
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
		return Response.status(Status.BAD_REQUEST).entity(errorResponse.toJSON()).build();
	}
	
	private static Response createMWSErrorResponse(MarketplaceWebServiceException e) {
		List<com.amazonaws.mws.model.Error> errors = new ArrayList<>();
		errors.add(CommonValidator.generateError(e.getErrorType(), e.getErrorCode(), e.getMessage()));
		return getErrorResponse(errors);
	}
}
