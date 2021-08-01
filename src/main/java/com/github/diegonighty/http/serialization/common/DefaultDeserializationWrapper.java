package com.github.diegonighty.http.serialization.common;

import com.github.diegonighty.http.serialization.ResponseDeserializer;

import java.io.IOException;
import java.io.InputStream;

public class DefaultDeserializationWrapper implements ResponseDeserializer<InputStream> {

    @Override
    public InputStream deserialize(InputStream stream) throws IOException {
        return stream;
    }

}
