package com.cleo.mws.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.mws.model.Error;

public class CommonValidator {

	final static String TYPE = "SDK";
	
	public static boolean validateISO8601DateFormat(String date) throws ParseException {
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		df1.parse(date);
		return true;
	}
	
	public static boolean isValidEmail(String email) {
		String regex = "^(.+)@(.+)$";
		 
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		
		return matcher.matches();
	}
	
	public static Error generateError(String type, String code, String message) {
		Error error = new Error();
		error.setType(type);
		error.setCode(code);
		error.setMessage(message);
		return error;
	}
	
	public static Error generateError(String code, String message) {
		
		return generateError(TYPE, code, message );
	}
}
