package com.qakki.rpc.transport.http;

import com.qakki.rpc.Peer;
import com.qakki.rpc.Request;
import com.qakki.rpc.Response;
import com.qakki.rpc.transport.TransportClient;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * http
 * @author qakki
 * @date 2021/1/16 6:05 下午
 */
public class HTTPTransportClient implements TransportClient {

    private String url;

    @Override
    public void connect(Peer peer) {
        this.url = "http://" + peer.getHost() + ":" + peer.getPort();
    }

    @Override
    public Response write(Request request) {
        return null;
    }

    //    @Override
    public byte[] write(byte[] dataBytes) {
        HttpURLConnection httpUrlConnection = null;
        try {
            ByteArrayInputStream data = new ByteArrayInputStream(dataBytes);
            httpUrlConnection = (HttpURLConnection) new URL(url).openConnection();
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setRequestMethod("POST");

            httpUrlConnection.connect();
            IOUtils.copy(data, httpUrlConnection.getOutputStream());

            int resultCode = httpUrlConnection.getResponseCode();
            if (resultCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpUrlConnection.getInputStream();
                return IOUtils.readFully(inputStream, inputStream.available());
            }
            InputStream errorStream = httpUrlConnection.getErrorStream();
            return IOUtils.readFully(errorStream, errorStream.available());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {

    }

}
