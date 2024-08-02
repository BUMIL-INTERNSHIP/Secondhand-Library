package BUMIL.Secondhand_Library.domain.quote.Repository;

import BUMIL.Secondhand_Library.domain.quote.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<QuoteEntity, Long> {
}
