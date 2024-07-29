package BUMIL.Secondhand_Library.domain.book.APIClient;

import BUMIL.Secondhand_Library.domain.book.Repository.BookRepository;
import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Slf4j
@Service
public class LibraryAPIClient {

    private final BookRepository bookRepository;
    private final AladdinAPIClient aladdinAPIClient;
    private final ThreadPoolTaskExecutor bookApiTaskExecutor;
    private final String apiKey;
    private final WebClient libraryWebClient;

    @Autowired
    public LibraryAPIClient(
            @Qualifier("libraryWebClient") WebClient webClient,
            @Value("${library.api.key}") String apiKey,
            @Qualifier("bookApiTaskExecutor") ThreadPoolTaskExecutor bookApiTaskExecutor,
            BookRepository bookRepository,
            AladdinAPIClient aladdinAPIClient) {
        this.libraryWebClient = webClient;
        this.apiKey = apiKey;
        this.bookApiTaskExecutor = bookApiTaskExecutor;
        this.bookRepository = bookRepository;
        this.aladdinAPIClient = aladdinAPIClient;
    }

    public CompletableFuture<List<BookEntity>> initializePopularBooks() {
        if (bookRepository.count() != 0) {
            return CompletableFuture.completedFuture(null);
        }

        List<BookEntity> bookEntities = new ArrayList<>();
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        LocalDate startYear = LocalDate.of(currentYear, 1, 1);

        try {
            String responseBody = libraryWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("authKey", apiKey)
                            .queryParam("startDt", startYear)
                            .queryParam("endDt", today)
                            .queryParam("pageSize", "200")
                            .queryParam("format", "json")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONObject responseObj = jsonResponse.getJSONObject("response");

            if (!jsonResponse.has("response") || !responseObj.has("docs")) {
                log.info("No response or docs found.");
                return CompletableFuture.completedFuture(bookEntities);
            }

            JSONArray items = responseObj.getJSONArray("docs");
            Set<String> titles = new HashSet<>();

            List<CompletableFuture<BookEntity>> futures = new ArrayList<>();

            for (int i = 0; i < items.length(); i++) {
                JSONObject docObj = items.getJSONObject(i).getJSONObject("doc");
                String bookName = docObj.getString("bookname");

                if (!titles.contains(bookName)) {
                    titles.add(bookName);
                    futures.add(CompletableFuture.supplyAsync(() -> {
                        try {

                            return processBook(bookName, docObj).get();
                        } catch (InterruptedException | ExecutionException e) {
                            log.error("Error processing book {}", bookName, e);
                            return null;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }, bookApiTaskExecutor));
                }
            }

            // Collect all the results
            return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                    .thenApply(v -> {
                        futures.forEach(future -> {
                            try {
                                BookEntity bookEntity = future.get();
                                if (bookEntity != null) {
                                    bookEntities.add(bookEntity);
                                }
                            } catch (InterruptedException | ExecutionException e) {
                                log.error("Error getting book entity", e);
                            }
                        });
                        return bookEntities;
                    });

        } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(bookEntities);
        }
    }

    private CompletableFuture<BookEntity> processBook(String bookName, JSONObject docObj) throws IOException {

        return aladdinAPIClient.initializeBookInfo(bookName)
                .thenApply(part -> {
                    if (part == null) {
                        log.error("Failed to fetch book details for {}", bookName);
                        return null;
                    }
                    return BookEntity.builder()
                            .bookName(docObj.getString("bookname"))
                            .author(docObj.getString("authors"))
                            .pubDate(docObj.getString("publication_year"))
                            .description(part[0])
                            .coverImg(docObj.getString("bookImageURL"))
                            .kdc(docObj.getString("class_no"))
                            .item_ID(Integer.parseInt(part[1]))
                            .price(Integer.parseInt(part[2]))
                            .build();
                }).exceptionally(e -> {
                    log.error("Error processing book {}", bookName, e);
                    return null;
                });
    }

    public List<BookEntity> searchPopularBooks(String sex, String age, String location, String interest){
        List<BookEntity> newlyRecommendedBooks = new ArrayList<>(); //추천되었지만 디비에 존재하지않는 도서들
        List<BookEntity> finalBookList = new ArrayList<>();//반환될 도서들

        LocalDate today = LocalDate.now(); //서버 시작일 기준 날짜
        int currentYear = today.getYear(); //서비 시작일 기준 년도
        LocalDate startYear = LocalDate.of(currentYear,1,1); //

        try {
            String responseBody = libraryWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("authKey", apiKey)
                            .queryParam("startDt", startYear)
                            .queryParam("endDt", today)
                            .queryParam("gender",sex)
                            .queryParam("age",age)
                            .queryParam("region",location)
                            .queryParam("kdc",interest)
                            .queryParam("pageSize", "200") //200개 조회
                            .queryParam("format", "json")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JSONObject jsonResponse = new JSONObject(responseBody);

            if (!jsonResponse.has("response")) {
                System.out.println("No response found.");
            }

            JSONObject responseObj = jsonResponse.getJSONObject("response");

            if (!responseObj.has("docs")) {
                System.out.println("No docs found.");
            }

            JSONArray items = responseObj.getJSONArray("docs");
            Set<String> titles = new HashSet<>(); // Set to keep track of unique titles

            for (int i = 0; i < items.length(); i++) {
                if (finalBookList.size() == 10) break; //10개의 도서만 추출
                
                JSONObject docObj = items.getJSONObject(i).getJSONObject("doc");
                String bookName = docObj.getString("bookname");
                // 중복된 도서가 나올 수 있기 때문에 set으로 중복되지 않은 도서만 추가한다.
                if (!titles.contains(bookName)){
                    titles.add(bookName);
                    BookEntity bookEntity = BookEntity.builder()
                            .bookName(docObj.getString("bookname"))
                            .author(docObj.getString("authors"))
                            .pubDate(docObj.getString("publication_year"))
                            .coverImg( docObj.getString("bookImageURL"))
                            .kdc(docObj.getString("class_no")) // Convert to Long if necessary
                            .build();

                    //이미 DB에 존재하는인지 확인하고 중복된 데이터 방지
                    if (bookRepository.existsByBookName(bookName)){
                        finalBookList.add(bookEntity);
                    }else{
                        newlyRecommendedBooks.add(bookEntity);
                        finalBookList.add(bookEntity);
                    }
                }
            }
            List<BookEntity> savedBooks =   bookRepository.saveAll(newlyRecommendedBooks);
            return savedBooks;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}