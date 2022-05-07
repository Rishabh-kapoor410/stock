package com.stocks.controller;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stocks.entity.vo.Response;
import com.stocks.entity.vo.StocksVO;
import com.stocks.service.StockService;

@RestController
@RequestMapping("/stockQuote")
public class StockController {
		
	
	static Logger logger = Logger.getLogger(StockController.class);
	static Marker confidentialMarker = MarkerFactory.getMarker("StockController");
	
	public static final String success = "Success";
	
	@Autowired
	StockService stockService;
	
	@PostMapping("/addQuote")
	
	public Response<?> postUser( @RequestBody StocksVO stocksVO) throws SQLIntegrityConstraintViolationException {
		Collection<StocksVO> colStockVO = new ArrayList<>();
		try {

			StocksVO rsltStockVO = stockService.save(stocksVO);
			if (rsltStockVO.getCompanyName()!= null) {
				colStockVO.add(rsltStockVO);
				return new Response<StocksVO>("stock Saved Successfull", colStockVO);
			}
			return new Response<StocksVO>("User Save Unsuccessfull", Collections.EMPTY_LIST);
		}catch(ConstraintViolationException e) {
			logger.info(confidentialMarker + ",Exception in the class{} ," + e.getLocalizedMessage());
			return new Response<String>("Duplicate stockQuote "+ stocksVO.getStockQuote());
		}
		
		catch (Exception e) {
			logger.info(confidentialMarker + ",Exception in the class{} ," + e.getLocalizedMessage());
			return new Response<String>("User Save Unsuccessfull " + e.getLocalizedMessage());
		}

	}
	
	@GetMapping()
	public Response<?> getStock() {
		Collection<StocksVO> getStockVO = new ArrayList<>();
		try {

			List<StocksVO> rsltStockVO = stockService.get();
			if (rsltStockVO != null) {
				//getStockVO.add(rsltStockVO);
				return new Response<StocksVO>("stock fetched Successfull", rsltStockVO);
			}
			return new Response<StocksVO>("stock fetch Unsuccessfull", Collections.EMPTY_LIST);
		} catch (Exception e) {
			logger.info(confidentialMarker + ",Exception in the class{} ," + e.getLocalizedMessage());
			return new Response<StocksVO>("stock fetch Unsuccessfull" + e.getLocalizedMessage(), Collections.EMPTY_LIST);
		}

	}


	@GetMapping("/queryQuote")
	public Response<?> getStocks(@RequestParam(value = "symbols") List<String> symbols) {
		Collection<StocksVO> getStockVO = new ArrayList<>();
		try {

			List<StocksVO> rsltStockVO = stockService.getbysymbol(symbols);
			if (rsltStockVO != null) {
				//getStockVO.add(rsltStockVO);
				return new Response<StocksVO>("stock fetched Successfull", rsltStockVO);
			}
			return new Response<StocksVO>("stock fetch Unsuccessfull", Collections.EMPTY_LIST);
		} catch (Exception e) {
			logger.info(confidentialMarker + ",Exception in the class{} ," + e.getLocalizedMessage());
			return new Response<StocksVO>("stock fetch Unsuccessfull" + e.getLocalizedMessage(), Collections.EMPTY_LIST);
		}

	}

	
}
