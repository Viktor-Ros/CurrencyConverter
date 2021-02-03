package com.example.repo;

import com.example.models.HistoryConversion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface HistoryConversionRepository  extends CrudRepository<HistoryConversion, Long> {

//    @Query(value = "SELECT c FROM history_conversion WHERE c.history_rec LIKE '%' || :keyword || '%'")
//    public Iterable<HistoryConversion> search(@Param("keyword") String keyword);
}
