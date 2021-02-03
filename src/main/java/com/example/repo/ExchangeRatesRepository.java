package com.example.repo;

import com.example.models.ExchangeRate;
import org.springframework.data.repository.CrudRepository;

public interface ExchangeRatesRepository extends CrudRepository<ExchangeRate, String> {
}
