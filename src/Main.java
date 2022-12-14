import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

public class Main {
    public static void main(String[] args) throws IOException {

        final int PUERTO = 9009;
        HttpServer httpd = HttpServer.create(new InetSocketAddress(PUERTO),0);
        HttpContext ctx = httpd.createContext("/");
        ctx.setHandler(Main::gestionarSolicitud);
        httpd.start();
    }
    private static void gestionarSolicitud(HttpExchange exchange) throws IOException{
        final int CODIGO_RESPUESTA = 200;
        String contenido = "Respuesta desde el Servidor Http";
        mostrarInformacionSolicitud(exchange);
        exchange.sendResponseHeaders(CODIGO_RESPUESTA,contenido.getBytes().length);
        OutputStream os = exchange.getResponseBody();

        os.write(contenido.getBytes());
        os.close();
    }

    private static void mostrarInformacionSolicitud(HttpExchange exchange){

        System.out.println("Encabezados:");
        exchange.getRequestHeaders().entrySet().forEach(System.out::println);

        System.out.println();

        System.out.println("Métodos: " + exchange.getRequestMethod());

        System.out.println();

        System.out.println("Query:");
        URI uri = exchange.getRequestURI();
        System.out.println(uri.getQuery());
    }
}