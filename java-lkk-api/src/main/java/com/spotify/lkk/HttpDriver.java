package com.spotify.lkk;

public interface HttpDriver {
	public void post(String uri, String body) throws HttpDriverException;
}
