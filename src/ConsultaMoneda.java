import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaMoneda {

    // Este método recibe la moneda base (ej: "USD") y devuelve nuestro Record listo
    public MonedaOmdb buscarMoneda(String monedaBase) {

        // 1. Construimos la dirección exacta.
        // Le pedimos a Java que busque una variable de entorno con este nombre exacto
        String apiKey = System.getenv("EXCHANGE_API_KEY");

        // Construimos la URL inyectando la variable de forma segura
        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + monedaBase);

        // 2. Preparamos el "cartero" (Client) y la "carta" (Request)
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        try {
            // 3. Enviamos la petición y esperamos el JSON de respuesta
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            // 4. Usamos Gson para transformar ese texto JSON directamente en nuestro Record
            return new Gson().fromJson(response.body(), MonedaOmdb.class);

        } catch (Exception e) {
            // Si el internet falla o la API no responde, atrapamos el error
            throw new RuntimeException("No encontré esa moneda. Error: " + e.getMessage());
        }
    }
}