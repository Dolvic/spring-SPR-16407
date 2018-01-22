package spring.bugs;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.logging.LogManager;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        try (MockWebServer server = new MockWebServer())
        {
            WebClient webClient = WebClient.create(server.url("/").toString());

            MockResponse response = new MockResponse();
            response.setHeader("Content-Type", "application/json");
            response.setBody("[1, 2, 3, 4]");
            server.enqueue(response);

            webClient.get()
                     .retrieve()
                     .bodyToFlux(Integer.class)
                     .doOnNext(System.out::println)
                     .doOnError(Throwable::printStackTrace)
                     .doOnTerminate(() -> System.out.println("Flux done!"))
                     .blockLast();

            server.enqueue(response);
            webClient.get()
                     .retrieve()
                     .bodyToMono(new ParameterizedTypeReference<List<Integer>>()
                     {
                     })
                     .doOnNext(System.out::println)
                     .doOnError(Throwable::printStackTrace)
                     .doOnTerminate(() -> System.out.println("Mono done!"))
                     .block();
        }
    }
}
