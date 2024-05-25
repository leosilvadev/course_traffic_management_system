package com.github.leosilvadev.detectorapp.support.compression;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.GZIPOutputStream;

public class CompressingClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(
            final HttpRequest request,
            final byte[] body,
            final ClientHttpRequestExecution execution
    ) throws IOException {
        final var compressedBody = compress(body);
        final var httpHeaders = request.getHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_ENCODING, "gzip");
        httpHeaders.add(HttpHeaders.ACCEPT_ENCODING, "gzip");
        httpHeaders.replace(HttpHeaders.CONTENT_LENGTH, List.of(String.valueOf(compressedBody.length)));
        return execution.execute(request, compressedBody);
    }

    private byte[] compress(byte[] body) throws IOException {
        final var outputStream = new ByteArrayOutputStream();
        try (final var gzipOutputStream = new GZIPOutputStream(outputStream)) {
            gzipOutputStream.write(body);
        }
        return outputStream.toByteArray();
    }
}
