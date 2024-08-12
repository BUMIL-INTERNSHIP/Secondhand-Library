package BUMIL.Secondhand_Library.domain.quote.Repository;

import BUMIL.Secondhand_Library.domain.quote.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuoteRepository extends JpaRepository<QuoteEntity, Long> {

    @Query("SELECT q FROM QuoteEntity q WHERE YEAR(q.quoteDate) = :year AND MONTH(q.quoteDate) = :month")
    List<QuoteEntity> findQuotesByMonth(@Param("year") int year, @Param("month") int month);
}