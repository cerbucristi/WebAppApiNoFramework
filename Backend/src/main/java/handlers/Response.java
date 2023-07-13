package handlers;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private int status;
    private Map<String, String> headers;
    private String body;

    public Response(int status) {
        this.status = status;
        this.headers = new HashMap<>();
        this.body = "";
    }

    public Response(int status, Map<String, String> headers, String body) {
        this.status = status;
        this.headers = headers;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public static Response ok() {
        return new Response(200);
    }

    public static Response created() {
        return new Response(201);
    }

    public static Response noContent() {
        return new Response(204);
    }
}