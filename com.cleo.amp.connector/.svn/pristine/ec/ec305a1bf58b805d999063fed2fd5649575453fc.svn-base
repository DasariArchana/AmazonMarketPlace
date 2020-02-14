package com.cleo.mws.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.EnumUtils;
import com.amazonaws.mws.model.Error;
import com.amazonaws.mws.model.ErrorResponse;

import static com.cleo.mws.constant.MWSConstants.*;


public class FeedValidator {

	
	public static ErrorResponse validateSubmitFeed(Map<String, String> paramMap) {
		ErrorResponse errorResponse = new ErrorResponse();
		List<Error> errors = new ArrayList<>();
		validateFeedTypeField(paramMap.get("feedType"), errors);
		validataMarketPlaceId(paramMap.get("feedType"), paramMap.get("marketPlaceId"), errors);
		return errorResponse;
	}

	private static void validataMarketPlaceId(String feedType, String marketPlaceId, List<Error> errors) {
		if(feedType.equals(FeedTypeEnum._POST_PRODUCT_IMAGE_DATA_) && marketPlaceId == null) {
			errors.add(CommonValidator.generateError(INVALID_PARAMETER_VALUE, "MarketPlaceId is mandatory field is feedType is _POST_PRODUCT_IMAGE_DATA_."));
		}
	}

	private static void validateFeedTypeField(String feedType, List<Error> errors) {
		
		if(!EnumUtils.isValidEnum(FeedTypeEnum.class, feedType))
			errors.add(CommonValidator.generateError(INVALID_PARAMETER_VALUE, "Invalid FeedType"));
	}
}
