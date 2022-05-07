package com.stocks.serviceImpl;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stocks.entity.Stock;
import com.stocks.entity.vo.StocksVO;
import com.stocks.repository.StockRepo;
import com.stocks.service.StockService;

@Service
public class StockServiceImpl implements StockService{
	@Autowired
	StockRepo sRepo;
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");

	public StocksVO save(StocksVO stockVO) {
		Stock stock = new Stock();
		Stock dbStock = sRepo.save(fillStock(stockVO, stock));
		return fillStocksVO(dbStock);
	}

	
	public static StocksVO fillStocksVO(Stock stock) {
		
		StocksVO stockVO = new StocksVO();
		Optional.ofNullable(stock.getStockQuote()).ifPresent(x -> stockVO.setStockQuote(x));
		Optional.ofNullable(stock.getPrice()).ifPresent(x -> stockVO.setPrice(x.toString()));
		Optional.ofNullable(stock.getCompanyName()).ifPresent(x -> stockVO.setCompanyName(x));
		Optional.ofNullable(stock.getCurrency()).ifPresent(x -> stockVO.setCurrency(x));
		Optional.ofNullable(stock.getDate()).ifPresent(x -> stockVO.setDate(x.toString()));
		return stockVO;
	}
	

	public static Stock fillStock(StocksVO stockVO, Stock stock) {
		
		DecimalFormat df = new DecimalFormat("#.##");
		Optional.ofNullable(stockVO.getPrice()).ifPresent(x -> stock.setPrice(Double.parseDouble(df.format(x))));
		Optional.ofNullable(stockVO.getCurrency()).ifPresent(x -> stock.setCurrency(x));
		Optional.ofNullable(stockVO.getCompanyName()).ifPresent(x -> stock.setCompanyName(x));
		Optional.ofNullable(stockVO.getDate()).ifPresent(x -> stock.setDate(LocalDate.parse(x, formatter)));
		Optional.ofNullable(stockVO.getStockQuote()).ifPresent(x -> stock.setStockQuote(x));
		return stock;
	}


	@Override
	public List<StocksVO> get() {
		List<Stock> dbStock = sRepo.findAll();
		List<StocksVO> stockVOList = new ArrayList<>();
		dbStock.stream().forEach(x-> {
			stockVOList.add(fillStocksVO(x));
			
		});
		
		return stockVOList;
	}


	@Override
	public List<StocksVO> getbysymbol(List<String> symbols) {
		List<Stock> dbStock = sRepo.findStocksByStockQuote(symbols);
		List<StocksVO> stockVOList = new ArrayList<>();
		dbStock.stream().forEach(x-> {
			stockVOList.add(fillStocksVO(x));
			
		});
		
		return stockVOList;
	}

}
