package com.srccodes.testcases;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.Assert;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.StringEntity;


public class ProductsGetTests {
	
//Using Data Provider create products with data you want
	
  @Test
//Get product price sorted on descending order
  public void getProductPriceDescending() throws Exception {
      CloseableHttpClient client = HttpClients.createDefault();
      HttpGet httpGet = new HttpGet("http://localhost:3030/products?price[$lte]=1");      

      CloseableHttpResponse response = client.execute(httpGet);
      
      String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
      
      System.out.println("Response body: " + responseBody);
      
      Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
      
      client.close();	  
  }
  
  @Test
//Get wrong product error id
  public void getWrongProductIdError() throws Exception {
      CloseableHttpClient client = HttpClients.createDefault();
      HttpGet httpGet = new HttpGet("http://localhost:3030/products/123");      

      CloseableHttpResponse response = client.execute(httpGet);
      
      String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
      
      System.out.println("Response body: " + responseBody);
      
      Assert.assertEquals(response.getStatusLine().getStatusCode(), 404);
      
      client.close();	  
  }
  
  @BeforeMethod
  public void beforeMethod() {
  }

  @AfterMethod
  public void afterMethod() {
  }

}
