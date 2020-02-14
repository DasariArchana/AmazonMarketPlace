/******************************************************************************* 
 *  Copyright 2008-2012 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  
 *  You may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at: http://aws.amazon.com/apache2.0
 *  This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 *  CONDITIONS OF ANY KIND, either express or implied. See the License for the 
 *  specific language governing permissions and limitations under the License.
 * ***************************************************************************** 
 * 
 *  Marketplace Web Service Java Library
 *  API Version: 2009-01-01
 * 
 */



package com.amazonaws.mws.model;

public class ResponseHeaderMetadata {
	private String requestId;
	private String responseContext;
	private String timestamp;
	
	/** The max quota allowed for a quota period */
	private String quotaMax = null;

	/** Quota remaining within this quota period */
	private String quotaRemaining = null;

	/** Time that this quota period ends */
	private String quotaResetsAt = null;

	public ResponseHeaderMetadata() {
	}

	public ResponseHeaderMetadata(String requestId, String responseContext, String timestamp) {
		this.requestId = requestId;
		this.responseContext = responseContext;
		this.timestamp = timestamp;
	}

	/** Value constructor. */
	public ResponseHeaderMetadata(String requestId, String responseContext, String timestamp, String quotaMax,
			String quotaRemaining, String quotaResetsAt) {
		this.quotaMax = quotaMax;
		this.quotaRemaining = quotaRemaining;
		this.quotaResetsAt = quotaResetsAt;
		this.requestId = requestId;
		this.responseContext = responseContext;
		this.timestamp = timestamp;
	}

	public String getRequestId() {
		return requestId;
	}

	public String getResponseContext() {
		return responseContext;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getQuotaMax() {
		return quotaMax;
	}

	public void setQuotaMax(String quotaMax) {
		this.quotaMax = quotaMax;
	}

	public String getQuotaRemaining() {
		return quotaRemaining;
	}

	public void setQuotaRemaining(String quotaRemaining) {
		this.quotaRemaining = quotaRemaining;
	}

	public String getQuotaResetsAt() {
		return quotaResetsAt;
	}

	public void setQuotaResetsAt(String quotaResetsAt) {
		this.quotaResetsAt = quotaResetsAt;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("requestId : " + requestId + "\n");
		sb.append("responseContext : " + responseContext + "\n");
		sb.append("timestamp : " + timestamp + "\n");
		sb.append("quotaMax : " + quotaMax + "\n");
		sb.append("quotaRemaining : " + quotaRemaining + "\n");
		sb.append("quotaResetsAt : " + quotaResetsAt);
		return sb.toString();
	}
	
	/**
    *
    * JSON fragment representation of this object
    *
    * @return JSON fragment for this object. Name for outer
    * object expected to be set by calling method. This fragment
    * returns inner properties representation only
    *
    */
   protected String toJSONFragment() {
       StringBuffer json = new StringBuffer();
       boolean first = true;
       if (requestId != null) {
           if (!first) json.append(", ");
           json.append(quoteJSON("RequestId"));
           json.append(" : ");
           json.append(quoteJSON(getRequestId()));
           first = false;
       }
       if (timestamp != null) {
           if (!first) json.append(", ");
           json.append(quoteJSON("timestamp"));
           json.append(" : ");
           json.append(quoteJSON(getTimestamp()));
           first = false;
       }
       if (quotaMax != null) {
           if (!first) json.append(", ");
           json.append(quoteJSON("quotaMax"));
           json.append(" : ");
           json.append(quoteJSON(getQuotaMax()));
           first = false;
       }
       if (quotaRemaining != null) {
           if (!first) json.append(", ");
           json.append(quoteJSON("quotaRemaining"));
           json.append(" : ");
           json.append(quoteJSON(getQuotaRemaining()));
           first = false;
       }
       if (quotaResetsAt != null) {
           if (!first) json.append(", ");
           json.append(quoteJSON("quotaResetsAt"));
           json.append(" : ");
           json.append(quoteJSON(getQuotaResetsAt()));
           first = false;
       }
       return json.toString();
   }

   /**
    *
    * Quote JSON string
    */
   private String quoteJSON(String string) {
       StringBuffer sb = new StringBuffer();
       sb.append("\"");
       int length = string.length();
       for (int i = 0; i < length; ++i) {
           char c = string.charAt(i);
           switch (c) {
           case '"':
               sb.append("\\\"");
               break;
           case '\\':
               sb.append("\\\\");
               break;
           case '/':
               sb.append("\\/");
               break;
           case '\b':
               sb.append("\\b");
               break;
           case '\f':
               sb.append("\\f");
               break;
           case '\n':
               sb.append("\\n");
               break;
           case '\r':
               sb.append("\\r");
               break;
           case '\t':
               sb.append("\\t");
               break;
           default:
               if (c <  ' ') {
                   sb.append("\\u" + String.format("%03x", Integer.valueOf(c)));
               } else {
               sb.append(c);
           }
       }
       }
       sb.append("\"");
       return sb.toString();
   }
}
