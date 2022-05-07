package com.stocks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stocks.entity.Stock;

@Repository
public interface StockRepo extends JpaRepository<Stock, Long> {
	
	
	@Query("SELECT s FROM stock s WHERE s.stockQuote IN (:symbols)")
	public List<Stock> findStocksByStockQuote(@Param("symbols") List<String> symbols);
}
