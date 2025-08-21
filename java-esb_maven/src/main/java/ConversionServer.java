import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.nio.charset.StandardCharsets;

public class ConversionServer {

    private static final ObjectMapper jsonMapper = new ObjectMapper();
    private static final XmlMapper xmlMapper = new XmlMapper();

    public static void main(String[] args) {

        // Whip up a server using undertow
        Undertow server = Undertow.builder()
                .addHttpListener(8083, "0.0.0.0")
                .setHandler(new RoutingHandler())
                .build();

        server.start();
        System.out.println("Server started at http://localhost:8083");
    }

    static class RoutingHandler implements HttpHandler {
        @Override
        public void handleRequest(HttpServerExchange exchange) throws Exception {
            String path = exchange.getRequestPath();
            if (path.equals("/xml-to-json") && exchange.getRequestMethod().equalToString("POST")) {
                handleXmlToJson(exchange);
            } else if (path.equals("/json-to-xml") && exchange.getRequestMethod().equalToString("POST")) {
                handleJsonToXml(exchange);
            } else {
                exchange.setStatusCode(404);
                exchange.getResponseSender().send("Not Found");
            }
        }
    }

    // === XML → JSON ===
    private static void handleXmlToJson(HttpServerExchange exchange) {
        exchange.getRequestReceiver().receiveFullString((ex, body) -> {
            try {
                Customer customer = xmlMapper.readValue(body, Customer.class);
                String json = jsonMapper.writeValueAsString(customer);

                ex.setStatusCode(200);
                ex.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
                ex.getResponseSender().send(json);
            } catch (Exception e) {
                ex.setStatusCode(400);
                ex.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
                ex.getResponseSender().send("{\"error\":\"Invalid XML\"}");
            }
        }, StandardCharsets.UTF_8);
    }

    // === JSON → XML ===
    private static void handleJsonToXml(HttpServerExchange exchange) {
        exchange.getRequestReceiver().receiveFullString((ex, body) -> {
            try {
                Customer customer = jsonMapper.readValue(body, Customer.class);
                String xml = xmlMapper.writeValueAsString(customer);

                ex.setStatusCode(200);
                ex.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/xml");
                ex.getResponseSender().send(xml);
            } catch (Exception e) {
                ex.setStatusCode(400);
                ex.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
                ex.getResponseSender().send("{\"error\":\"Invalid JSON\"}");
            }
        }, StandardCharsets.UTF_8);
    }

}
