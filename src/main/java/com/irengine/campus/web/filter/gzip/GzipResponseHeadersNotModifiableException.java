package com.irengine.campus.web.filter.gzip;

import javax.servlet.ServletException;

public class GzipResponseHeadersNotModifiableException extends ServletException {
	
	private static final long serialVersionUID = 751843360167499035L;

	public GzipResponseHeadersNotModifiableException(String message) {
        super(message);
    }
}
