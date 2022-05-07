package com.stocks.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stocks.entity.vo.StocksVO;

@Service
public interface StockService {

	StocksVO save(StocksVO stockVO);

	List<StocksVO> get();

	List<StocksVO> getbysymbol(List<String> symbols);
	
}
