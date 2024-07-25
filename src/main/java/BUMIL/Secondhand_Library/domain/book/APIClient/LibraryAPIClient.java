package BUMIL.Secondhand_Library.domain.book.APIClient;

import BUMIL.Secondhand_Library.domain.book.Repository.BookRepository;
import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LibraryAPIClient {

    @Autowired
    private BookRepository bookRepository;

    @Value("${library.api.key}")
    private String apiKey;

    private String baseUri = "http://data4library.kr/api/loanItemSrch"; //도서관 정보나루 인기 도서 조회

    private final WebClient webClient;

    public LibraryAPIClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(baseUri).build();
    }

    public List<BookEntity> initializePopularBooks(){
        if (bookRepository.count() != 0) return null;

        List<BookEntity> bookEntities = new ArrayList<>();

        LocalDate today = LocalDate.now(); //서버 시작일 기준 날짜

        int currentYear = today.getYear(); //서비 시작일 기준 년도

        LocalDate startYear = LocalDate.of(currentYear,1,1); //

        try {
            String responseBody = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("authKey", apiKey)
                            .queryParam("startDt", startYear)
                            .queryParam("endDt", today)
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

                JSONObject docObj = items.getJSONObject(i).getJSONObject("doc");
                // 중복된 도서가 나올 수 있기 때문에 set으로 중복되지 않은 도서만 추가한다.
                if (!titles.contains(docObj.getString("bookname"))){
                    String title = docObj.getString("bookname");
                    String author = docObj.getString("authors");
                    String pubDate = docObj.getString("publication_year");
                    String coverImg = docObj.getString("bookImageURL");
                    String kdc = docObj.getString("class_no");
                    titles.add(title);
                    BookEntity bookEntity = BookEntity.builder()
                            .bookName(title)
                            .author(author)
                            .pubDate(pubDate)
                            .coverImg(coverImg)
                            .kdc(kdc) // Convert to Long if necessary
                            .build();
                    bookEntities.add(bookEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookEntities;
    }


    public List<BookEntity> searchPopularBooks(String sex, String age, String location, String interest){
        List<BookEntity> newlyRecommendedBooks = new ArrayList<>(); //추천되었지만 디비에 존재하지않는 도서들
        List<BookEntity> finalBookList = new ArrayList<>();//반환될 도서들

        LocalDate today = LocalDate.now(); //서버 시작일 기준 날짜
        int currentYear = today.getYear(); //서비 시작일 기준 년도
        LocalDate startYear = LocalDate.of(currentYear,1,1); //

        try {
            String responseBody = webClient.get()
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
            bookRepository.saveAll(newlyRecommendedBooks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    return finalBookList;
    }
}
