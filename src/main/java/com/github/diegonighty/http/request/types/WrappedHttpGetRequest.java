package com.github.diegonighty.http.request.types;

import com.github.diegonighty.http.exception.FailedConnectionException;
import com.github.diegonighty.http.response.HttpResponse;
import com.github.diegonighty.http.response.WrappedHttpResponse;
import com.github.diegonighty.http.serialization.ResponseDeserializer;
import com.github.diegonighty.http.util.StatusCode;

import java.io.IOException;
import java.net.HttpURLConnection;

public final class WrappedHttpGetRequest<T> implements HttpGetRequest<T> {

	private final HttpURLConnection connection;
	private ResponseDeserializer<T> deserializer;

	public WrappedHttpGetRequest(HttpURLConnection connection) {
		this.connection = connection;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HttpGetRequest<T> setResponseDeserializer(ResponseDeserializer<T> deserializer) {
		this.deserializer = deserializer;

		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HttpResponse<T> execute() throws FailedConnectionException, IOException {
		connection.connect();

		if (!StatusCode.isSuccessful(connection.getResponseCode())) {
			throw new FailedConnectionException("Server is not responding", connection.getResponseCode());
		}

		return new WrappedHttpResponse<>(connection.getInputStream(), connection.getResponseCode(), deserializer);
	}

}
