package com.github.diegonighty.http.request.types;

import com.github.diegonighty.http.exception.FailedConnectionException;
import com.github.diegonighty.http.response.HttpResponse;
import com.github.diegonighty.http.response.WrappedNotSerializedResponse;
import com.github.diegonighty.http.serialization.RequestSerializer;
import com.github.diegonighty.http.util.StatusCode;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

public class WrappedHttpPostRequest<T> implements HttpPostRequest<T> {

	private final HttpURLConnection connection;
	private RequestSerializer<T> serializer;

	private T object;

	public WrappedHttpPostRequest(HttpURLConnection connection) {
		this.connection = connection;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HttpPostRequest<T> setSerializer(RequestSerializer<T> serializer) {
		this.serializer = serializer;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HttpPostRequest<T> setObject(T object) {
		this.object = object;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HttpResponse<Integer> execute() throws FailedConnectionException, IOException {
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

		connection.connect();

		try (BufferedOutputStream stream = new BufferedOutputStream(connection.getOutputStream())) {
			serializer.serialize(object, stream);
		}

		if (!StatusCode.isSuccessful(connection.getResponseCode())) {
			throw new FailedConnectionException("Server is not responding", connection.getResponseCode());
		}

		return new WrappedNotSerializedResponse(connection.getResponseCode());
	}

}
