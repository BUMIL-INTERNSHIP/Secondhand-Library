package BUMIL.Secondhand_Library.domain.book.APIClient;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class AladdinAPIClient {

    private final String apiKey;
    private final WebClient AladdinAwebClient;

    @Autowired
    public AladdinAPIClient(@Qualifier("aladdinWebClient") WebClient webClient,
                            @Value("${aladdin.api.key}") String apiKey) {
        this.AladdinAwebClient = webClient;
        this.apiKey = apiKey;
    }

    public CompletableFuture<String[]> initializeBookInfo(String bookName) {
        String[] bookTitlePart = bookName.trim().split("[/:=]|장편소설");

        log.info("Request query: {}", bookTitlePart[0]);

        return AladdinAwebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("ttbkey", apiKey)
                        .queryParam("Query", bookTitlePart[0])
                        .queryParam("MaxResults", 1)
                        .queryParam("SearchTarget", "Book")
                        .queryParam("output", "js")
                        .queryParam("Version", "20131101")
                        .build())
                .exchangeToMono(response -> {
                    // Log status code
                    log.info("Response status code: {}", response.statusCode());

                    // Read the body if status code is 200 OK
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(String.class)
                                .doOnSuccess(body -> log.debug("Response body: {}", body))
                                .doOnError(error -> log.error("Error reading response body", error));
                    } else {
                        // Handle non-success status codes
                        return response.createException().flatMap(Mono::error);
                    }
                })
                .toFuture()
                .thenApply(responseBody -> {
                    String[] part = new String[3];
                    try {
                        JSONObject jsonResponse = new JSONObject(responseBody);

                        if (jsonResponse.has("errorMessage")) {
                            log.error("Error: {}", jsonResponse.getString("errorMessage"));
                            return new String[]{"Error fetching details", "0", "0"};
                        }

                        if (!jsonResponse.has("item")) {
                            log.info("No items found.");
                            return new String[]{"No details available", "0", "0"};
                        }

                        JSONArray items = jsonResponse.getJSONArray("item");

                        if (items.length() == 1) {
                            JSONObject book = items.getJSONObject(0);
                            log.info("Book details: {}", book.toString());

                            part[0] = book.optString("description", "업데이트 중입니다.");
                            part[1] = String.valueOf(book.optInt("itemId", 0));
                            part[2] = String.valueOf(book.optInt("priceSales", 0));
                        } else {
                            part[0] = "업데이트 중입니다.";
                            part[1] = "0";
                            part[2] = "0";
                        }
                    } catch (Exception e) {
                        log.error("Exception occurred while processing book info", e);
                        return new String[]{"Error", "0", "0"};
                    }
                    return part;
                })
                .exceptionally(e -> {
                    log.error("Error during API call " + e);
                    return new String[]{"Error", "0", "0"};
                });
    }
}