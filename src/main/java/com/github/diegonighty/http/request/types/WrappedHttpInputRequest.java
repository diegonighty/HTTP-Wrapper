package com.github.diegonighty.http.request.types;

import com.github.diegonighty.http.exception.FailedConnectionException;
import com.github.diegonighty.http.response.HttpResponse;
import com.github.diegonighty.http.response.WrappedNotSerializedResponse;
import com.github.diegonighty.http.serialization.RequestSerializer;
import com.github.diegonighty.http.util.StatusCode;

import java.io.IOException;
import java.net.HttpURLConnection;

public class WrappedHttpInputRequest<T> implements HttpInputRequest<T> {

	private final HttpURLConnection connection;
	private RequestSerializer<T> serializer;

	private T object;

	public WrappedHttpInputRequest(HttpURLConnection connection) {
		this.connection = connection;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HttpInputRequest<T> setSerializer(RequestSerializer<T> serializer) {
		this.serializer = serializer;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HttpInputRequest<T> setObject(T object) {
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

		serializer.serialize(object, connection.getOutputStream());

		if (!StatusCode.isSuccessful(connection.getResponseCode())) {
			throw new FailedConnectionException("Server is not responding", connection.getResponseCode());
		}

		return new WrappedNotSerializedResponse(connection.getResponseCode());
	}

}
