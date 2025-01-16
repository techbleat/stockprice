package com.example.stockmarket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Map;

@SpringBootApplication
public class StockMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockMarketApplication.class, args);
    }
}


@RestController
@RequestMapping("/api/stocks")
class StockController {

    @Value("#{${app.custom.map}}")
    private Map<String, String> customMap;

    @GetMapping("/{ticker}")
    public StockResponse getStockPrice(@PathVariable String ticker) {
        try {
            String price = customMap.get (ticker);
            if (price == null ) 
                throw new Exception("Data Not Found");
            return new StockResponse(ticker, "Success", Double.valueOf(price));
        } catch (Exception e) {
            return new StockResponse(ticker, "Error fetching stock data", 0.0);
        }
    }
}

class StockResponse {
    private String ticker;
    private String status;
    private double price;

    public StockResponse(String ticker, String status, double price) {
        this.ticker = ticker;
        this.status = status;
        this.price = price;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

