package com.spotify.lkk;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class DefaultHttpDriver implements HttpDriver {
	private static final int DEFAULT_PORT = 80;

	private final HttpClient client;
	private final HttpContext context;

	public DefaultHttpDriver(String host, int port, String username, String password) {
		HttpHost targetHost = new HttpHost(host, port, "http"); 
		
		this.client = setupClient(targetHost, username, password); 
		this.context = setupContext(targetHost);
	}
	
	private HttpClient setupClient(HttpHost host, String username, String password)
	{
		DefaultHttpClient client = new DefaultHttpClient();
		AuthScope scope = new AuthScope(host);
		
		Credentials credentials = new UsernamePasswordCredentials(username, password);
		
		client.getCredentialsProvider().setCredentials(scope, credentials);
		
		return client;
	}
	
	private HttpContext setupContext(HttpHost targetHost) {
		AuthCache authCache = new BasicAuthCache();
		
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(targetHost, basicAuth);

		BasicHttpContext context = new BasicHttpContext();

		context.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		return context;
	}
	
	private HttpResponse execute(HttpUriRequest request) throws HttpDriverException {
		final HttpResponse response;

		request.addHeader("Accept", "application/json; */*;");
		
		try {
			response = this.client.execute(request, context);
		} catch (ClientProtocolException e) {
			throw new HttpDriverException(e);
		} catch (IOException e) {
			throw new HttpDriverException(e);
		}
		
		final StatusLine status = response.getStatusLine();
		
		if (status.getStatusCode() % 100 != 2) {
			throw new HttpDriverRemoteException(status.getStatusCode(), status.getReasonPhrase());
		}
		
		return response;
	}
	
	@Override
	public void post(String uri, String body) throws HttpDriverException {
		final HttpPost post = new HttpPost();
		final HttpResponse response = execute(post);
	}
}
