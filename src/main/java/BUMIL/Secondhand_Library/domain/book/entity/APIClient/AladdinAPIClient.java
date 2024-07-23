package BUMIL.Secondhand_Library.domain.book.entity.APIClient;

import BUMIL.Secondhand_Library.domain.book.entity.Repository.BookRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Service
public class AladdinAPIClient {

    @Autowired
    private BookRepository bookRepository;

    @Value("${aladdin.api.key}")
    private String apiKey;

    private String apiUri = "https://www.aladin.co.kr/ttb/api/ItemSearch.aspx"; //도서 조회 url



    public void searchBooks(String bookName) throws IOException {
        WebClient webClient = WebClient.builder()
                .baseUrl(apiUri)
                .build();

        try{
            String responseBody = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("ttbkey", apiKey)
                            .queryParam("Query", bookName)
                            .queryParam("MaxResults", 5)
                            .queryParam("SearchTarget", "Book")
                            .queryParam("output", "js")
                            .queryParam("Version", "20131101")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JSONObject jsonResponse = new JSONObject(responseBody); //json 파싱

            if (jsonResponse.has("errorMessage")) {
                System.out.println("Error: " + jsonResponse.getString("errorMessage"));
                return;
            }

            // Check if items are present
            if (!jsonResponse.has("item")) {
                System.out.println("No items found.");
                return;
            }

            JSONArray items = jsonResponse.getJSONArray("item");

            for (int i = 0; i < items.length(); i++) {
                JSONObject book = items.getJSONObject(i);
                System.out.println("Title: " + book.getString("title"));
                System.out.println("Author: " + book.getString("author"));
                System.out.println("Publication Date: " + book.getString("pubDate"));
                System.out.println("Description: " + book.optString("description", "No description available"));
                System.out.println("Item ID: " + book.getInt("itemId"));
                System.out.println("ISBN: " + book.getString("isbn"));
                System.out.println("Price (Sales): " + book.getInt("priceSales"));
                System.out.println("Price (Standard): " + book.getInt("priceStandard"));
                System.out.println("Link: " + book.getString("link"));
                System.out.println("Cover: " + book.getString("cover"));
                System.out.println("\n============================== 중고 서적 확인 =====================================");
                System.out.println("https://www.aladin.co.kr/shop/UsedShop/wuseditemall.aspx?ItemId=" + book.getInt("itemId"));
                System.out.println();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
