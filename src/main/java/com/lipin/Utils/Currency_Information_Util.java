package com.lipin.Utils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.*;

public class Currency_Information_Util {
    public static String apiKey = "f98d3204-a19e-48ef-97a9-3da7d140a03c";
    public static String start = "1";//
    public static String limit = "5000";//要搜尋的虛擬貨幣比數
    public static String convert = "USD";//金額要轉成哪個國家

    LinkedHashMap<String,ArrayList> CryptocurrenciesInMap = new LinkedHashMap<>();


    //拿取獲取的Cryptocurrenciesjson檔,重新解析存入map中
    public LinkedHashMap<String,ArrayList> getCryptocurrencies(String CryptocurrenciesJson){
        //格式化數字,讓數字每三位數增加一個逗點
//        NumberFormat numberFormat = NumberFormat.getNumberInstance();
//        numberFormat.setMaximumFractionDigits(6);
        if (responseStatusCode == 401){
        JSONObject jo1 = new JSONObject(CryptocurrenciesJson);//取得json檔
        JSONArray ja1 = jo1.getJSONArray("data");//解析json檔
        for (int i =0; i<ja1.length(); i++){
            ArrayList cryptocurrenciesInArrayLsit = new ArrayList<>();

            JSONObject jo2 = ja1.getJSONObject(i);
            JSONObject jo3 = jo2.getJSONObject("quote");
            JSONObject jo4 = jo3.getJSONObject(convert);

            String name = jo2.getString("name");//名稱
            String symbol = jo2.getString("symbol");//虛擬貨幣簡寫
            BigInteger market_cap = jo4.optBigDecimal("market_cap",BigDecimal.ZERO)
                    .toBigInteger();//市值:去小數點
            /**
             * BigDecimal.setScale =  選擇要無條件捨去'RoundingMode.FLOOR'的位置,
             * 在這裡小數點第六位數後無條件捨去
             * stripTrailingZeros = 去小數點後尾數的0
             */
            BigDecimal price = jo4.optBigDecimal("price",BigDecimal.ZERO)
                    .setScale(6,RoundingMode.FLOOR);//價格,小數點第六位
            BigInteger circulating_supply = jo2.optBigDecimal("circulating_supply",BigDecimal.ZERO)
                    .toBigInteger();//流通供給量:去小數點

            BigInteger volume_24h = jo4.optBigDecimal("volume_24h",BigDecimal.ZERO)
                    .toBigInteger();//交易量:去小點

            //一小內的百分比變化,小數點第2位,四捨五入
            BigDecimal percent_change_1h = jo4.optBigDecimal("percent_change_1h",BigDecimal.ZERO)
                    .setScale(2, RoundingMode.HALF_UP);
            BigDecimal percent_change_24h = jo4.optBigDecimal("percent_change_24h",BigDecimal.ZERO)
                    .setScale(2, RoundingMode.HALF_UP);
            //7天內的百分比變化,小數點第2位,四捨五入
            BigDecimal percent_change_7d = jo4.optBigDecimal("percent_change_7d",BigDecimal.ZERO)
                    .setScale(2, RoundingMode.HALF_UP);


            cryptocurrenciesInArrayLsit.add(name);
            cryptocurrenciesInArrayLsit.add(symbol);
            cryptocurrenciesInArrayLsit.add(market_cap);
            cryptocurrenciesInArrayLsit.add(price);
            cryptocurrenciesInArrayLsit.add(circulating_supply);
            cryptocurrenciesInArrayLsit.add(volume_24h);
            cryptocurrenciesInArrayLsit.add(percent_change_1h);
            cryptocurrenciesInArrayLsit.add(percent_change_24h);
            cryptocurrenciesInArrayLsit.add(percent_change_7d);

            CryptocurrenciesInMap.put(name,cryptocurrenciesInArrayLsit);

        }

        System.out.println(CryptocurrenciesInMap.get("XRP"));
        }

        return CryptocurrenciesInMap;
    }



    //獲得虛擬貨幣Json檔案
    public String getCryptocurrenciesJson(String apiKey,String start,String limit,String convert){
        String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
        paratmers.add(new BasicNameValuePair("start",start));
        paratmers.add(new BasicNameValuePair("limit",limit));
        paratmers.add(new BasicNameValuePair("convert",convert));

        try {
            //抓取網頁中虛擬貨幣的json格式
            String result = makeAPICall(uri, paratmers,apiKey);
            return result;

        } catch (IOException e) {
            System.out.println("Error: cannont access content - " + e.toString());

            return "Error: cannont access content - " + e.toString();
        } catch (URISyntaxException e) {
            System.out.println("Error: Invalid URL " + e.toString());
            return "Error: Invalid URL " + e.toString();
        }
    }



    public static int responseStatusCode;

    public String makeAPICall(String uri, List<NameValuePair> parameters,String apiKey)
            throws URISyntaxException, IOException {
        String response_content = "";

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", apiKey);

        CloseableHttpResponse response = client.execute(request);

        try {
            System.out.println("Currency_Information_Util.makeAPICall:"+response.getStatusLine());
            responseStatusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }

        return response_content;
    }

}
