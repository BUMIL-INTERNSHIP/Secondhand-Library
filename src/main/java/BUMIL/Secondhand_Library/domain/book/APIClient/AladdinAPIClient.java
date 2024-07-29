package BUMIL.Secondhand_Library.domain.book.APIClient;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class AladdinAPIClient {

    private String apiKey;
    private final WebClient AladdinAwebClient;

    @Autowired
    public AladdinAPIClient(@Qualifier("aladdinWebClient") WebClient webClient,
                            @Value("${aladdin.api.key}") String apiKey) {
        this.AladdinAwebClient = webClient;
        this.apiKey = apiKey;
    }

    // Remove @Async if using CompletableFuture from WebClient
    public CompletableFuture<String[]> initializeBookInfo(String bookName) {
        String[] BookTitlePart = bookName.trim().split("[/:=]|장편소설");

        log.info("요청쿼리 : " + BookTitlePart[0]);

        return AladdinAwebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("ttbkey", apiKey)
                        .queryParam("Query", BookTitlePart[0])
                        .queryParam("MaxResults", 1)
                        .queryParam("SearchTarget", "Book")
                        .queryParam("output", "js")
                        .queryParam("Version", "20131101")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .toFuture()
                .thenApply(responseBody -> {
                    String[] part = new String[3];
                    try {
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        if (jsonResponse.has("errorMessage")) {
                            log.info("Error: " + jsonResponse.getString("errorMessage"));
                            return null;
                        }

                        if (!jsonResponse.has("item")) {
                            log.info("No items found.");
                            return null;
                        }

                        JSONArray items = jsonResponse.getJSONArray("item");

                        if (items.length() == 1) {
                            JSONObject book = items.getJSONObject(0);
                            log.info(String.valueOf(items.getJSONObject(0)));
                            part[0] = book.optString("description", "업데이트 중입니다.");
                            int itemId = book.optInt("itemId", -1);
                            int priceSales = book.optInt("priceSales", -1);

                            part[1] = itemId != -1 ? String.valueOf(itemId) : "0";
                            part[2] = priceSales != -1 ? String.valueOf(priceSales) : "0";
                        } else {
                            part[0] = "업데이트 중입니다.";
                            part[1] = "0";
                            part[2] = "0";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return part;
                });
    }
}