package com.spotify.lkk;

public class HttpDriverRemoteException extends HttpDriverException {
    final int code;
    final String reason;

    public HttpDriverRemoteException(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public int getCode() {
		return code;
	}

	public String getReason() {
		return reason;
	}

    @Override
    public String toString() {
        return "HttpDriverRemoteException [" + code + ": " + reason + "]";
    }
}
