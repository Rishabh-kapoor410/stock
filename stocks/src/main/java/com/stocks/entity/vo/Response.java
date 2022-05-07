package com.stocks.entity.vo;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response<T> {
	
	@JsonProperty("message")
	private String message; 
	
	@JsonProperty("stocks")
	private Collection<T> stocks;

	
	public Response(String message, Collection<T> stocks) {
		super();
		this.message = message;
		this.stocks = stocks;
	}

	public Response(String message) {
		this.message = message;
	}

}