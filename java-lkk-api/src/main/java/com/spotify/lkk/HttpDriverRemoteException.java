package com.spotify.lkk;

public class HttpDriverRemoteException extends HttpDriverException {
	final int code;
	final String reason;
	
	public HttpDriverRemoteException(int code, String reason) {
		this.code = code;
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "HttpDriverRemoteException [" + code + ": " + reason + "]";
	}
}
