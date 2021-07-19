package com.challenge.banktransactions.acceptance;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.http.client.ClientHttpResponse;

public class ResponseResults {
    private final ClientHttpResponse theResponse;
    private final String body;

    ResponseResults(final ClientHttpResponse response) throws IOException {
        this.theResponse = response;
        final InputStream bodyInputStream = response.getBody();
        // final StringWriter stringWriter = new StringWriter();
        // IOUtils.copy(bodyInputStream, stringWriter);
        this.body = new String(bodyInputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    ClientHttpResponse getTheResponse() {
        return theResponse;
    }

    String getBody() {
        return body;
    }
}
