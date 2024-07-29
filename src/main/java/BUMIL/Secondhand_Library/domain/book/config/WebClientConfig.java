package BUMIL.Secondhand_Library.domain.book.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfig {

    @Bean(name = "aladdinWebClient")
    public WebClient aladdinWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl("https://www.aladin.co.kr/ttb/api/ItemSearch.aspx").build(); //알라딘 상품 조회
    }

    @Bean(name = "libraryWebClient")
    public WebClient libraryWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl("http://data4library.kr/api/loanItemSrch").build(); //인기 도서 조회
    }
}
