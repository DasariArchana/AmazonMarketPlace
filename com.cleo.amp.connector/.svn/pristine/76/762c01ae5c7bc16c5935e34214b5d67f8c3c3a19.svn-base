package com.cleo.mws.validation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.amazonaws.mws.model.Error;
import com.amazonaws.mws.model.ErrorResponse;

import static com.cleo.mws.constant.MWSConstants.INVALID_PARAMETER_VALUE;

public class OrderValidator {


	public static ErrorResponse validateListOrders(Map<String, String> orderMap) {
		
		ErrorResponse errorResponse = new ErrorResponse();
		List<Error> errors = new ArrayList<>();
	
		validateDateTypeFields(orderMap, errors);
		
		if(orderMap.containsKey("buyerEmail")) {
			if(!CommonValidator.isValidEmail(orderMap.get("buyerEmail"))){
				errors.add(CommonValidator.generateError(INVALID_PARAMETER_VALUE, "Not a valid Email Address."));
			}
				
		}
		
		if(!errors.isEmpty()) errorResponse.setError(errors);	
		
		return errorResponse;
	}
	
	private static void validateDateTypeFields(Map<String, String> orderMap,
			List<Error> errors) {
		String createdAfter = orderMap.get("createdAfter");
		String lastUpdatedAfter = orderMap.get("lastUpdatedAfter");
		
		if(createdAfter != null && lastUpdatedAfter != null) {
			errors.add(CommonValidator.generateError(INVALID_PARAMETER_VALUE, "Both CreatedAfter and LastUpdatedAfter cannot be specified."));
		}
		
		if(createdAfter == null && lastUpdatedAfter == null) {
			errors.add(CommonValidator.generateError(INVALID_PARAMETER_VALUE, "Either CreatedAfter or LastUpdatedAfter needs be specified."));
		}
		
		try {
			if(createdAfter != null) CommonValidator.validateISO8601DateFormat(createdAfter);
			if(lastUpdatedAfter != null) CommonValidator.validateISO8601DateFormat(lastUpdatedAfter);
		} catch (ParseException e) {
			errors.add(CommonValidator.generateError(INVALID_PARAMETER_VALUE, "Not a valid ISO8601 Date format. Date should be in yyyy-MM-ddTHH:mm:ss.sssZ format."));
		}
	}
	

}
