package com.spotify.lkk;

import java.lang.reflect.Type;

import org.apache.http.client.methods.HttpUriRequest;

public interface HttpDriver {
    public <R,T> T execute(HttpUriRequest request, R entity, Type expected) throws HttpDriverException;
}
