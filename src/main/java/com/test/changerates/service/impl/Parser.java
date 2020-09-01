package com.test.changerates.service.impl;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
public class Parser {
    private String source;
    private Double usdgbp;
    private Double usdeur;
    private Double usdcad;
    private Double usdpln;

    public Double getUsdgbp() {
        return usdgbp;
    }

    public Double getUsdeur() {
        return usdeur;
    }

    public Double getUsdcad() {
        return usdcad;
    }

    public Double getUsdpln() {
        return usdpln;
    }

    public void getCurrencies() {
        HttpGet get = new HttpGet("http://apilayer.net/api/live?access_key=104287103654d40e2c5d55449f134488");
        doParse(get);
    }

    public void getCurrencies(String date) {
        HttpGet get = new HttpGet("http://api.currencylayer.com/historical?access_key=104287103654d40e2c5d55449f134488&date="+ date);
        doParse(get);
    }

    private void doParse(HttpGet httpGet) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response =  httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));
            source = exchangeRates.getString("source");
            usdgbp = exchangeRates.getJSONObject("quotes").getDouble("USDGBP");
            usdeur = exchangeRates.getJSONObject("quotes").getDouble("USDEUR");
            usdcad = exchangeRates.getJSONObject("quotes").getDouble("USDCAD");
            usdpln = exchangeRates.getJSONObject("quotes").getDouble("USDPLN");
            response.close();
        } catch (IOException | ParseException | JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
