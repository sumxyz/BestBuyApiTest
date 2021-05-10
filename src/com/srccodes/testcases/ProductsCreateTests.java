package com.srccodes.testcases;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.Assert;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.StringEntity;


public class ProductsCreateTests {
	
  @Test
//Happy Path product create request
  public void createProduct() throws Exception {
      CloseableHttpClient client = HttpClients.createDefault();
      HttpPost httpPost = new HttpPost("http://localhost:3030/products");

      String json = "{\"name\": \"New Product\",\n"
      		+ "	\"type\": \"Hard Good\",\n"
      		+ "	\"upc\": \"12345676\",\n"
      		+ "	\"price\": 99.99,\n"
      		+ "	\"description\": \"This is a placeholder request for creating a new product.\",\n"
      		+ "	\"model\": \"NP12345\"}";
      
      StringEntity entity = new StringEntity(json);
      httpPost.setEntity(entity);
      httpPost.setHeader("Accept", "application/json");
      httpPost.setHeader("Content-type", "application/json");

      CloseableHttpResponse response = client.execute(httpPost);
      
      String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
      responseBody = responseBody.replaceAll("\\{", " ");
      responseBody = responseBody.replaceAll("\\}", " ");
      String expectedResult = "\"name\":\"New Product\",\"type\":\"Hard Good\",\"upc\":\"12345676\",\"price\":99.99,\"description\":\"This is a placeholder request for creating a new product.\",\"model\":\"NP12345\"";
      
      System.out.println("Response body: " + responseBody);
      System.out.println("Expected body: " + expectedResult);
      
      Assert.assertEquals(response.getStatusLine().getStatusCode(), 201);
      Assert.assertTrue(responseBody.contains(expectedResult));
      
      client.close();	  
  }
  
  @Test
  //Wrong price in product create request
  public void createProductWrongPrice() throws Exception {
      CloseableHttpClient client = HttpClients.createDefault();
      HttpPost httpPost = new HttpPost("http://localhost:3030/products");

      String json = "{\"name\": \"New Product\",\n"
        		+ "	\"type\": \"Hard Good\",\n"
          		+ "	\"upc\": \"12345676\",\n"
          		+ "	\"price\": 99.999,\n"
          		+ "	\"description\": \"This is a placeholder request for creating a new product.\",\n"
          		+ "	\"model\": \"NP12345\"}";
      
      StringEntity entity = new StringEntity(json);
      httpPost.setEntity(entity);
      httpPost.setHeader("Accept", "application/json");
      httpPost.setHeader("Content-type", "application/json");

      CloseableHttpResponse response = client.execute(httpPost);
      
      Assert.assertEquals(response.getStatusLine().getStatusCode(), 400);
      
      client.close();	  
  }
  
  @Test
  //Missing model field in product create request
  public void createProductMissingModelField() throws Exception {
      CloseableHttpClient client = HttpClients.createDefault();
      HttpPost httpPost = new HttpPost("http://localhost:3030/products");

      String json = "{\"name\": \"New Product\",\n"
        		+ "	\"type\": \"Hard Good\",\n"
          		+ "	\"upc\": \"12345676\",\n"
          		+ "	\"price\": 99.99,\n"
          		+ "	\"description\": \"This is a placeholder request for creating a new product.\"}";
      
      StringEntity entity = new StringEntity(json);
      httpPost.setEntity(entity);
      httpPost.setHeader("Accept", "application/json");
      httpPost.setHeader("Content-type", "application/json");

      CloseableHttpResponse response = client.execute(httpPost);
      
      Assert.assertEquals(response.getStatusLine().getStatusCode(), 400);
      
      client.close();	  
  }
  
  //Other test cases are :
  //1) Check boundaries for each field. 
  //2) Pass $ in Price field which is an integer. E.g. $99.9 - will return 400 response
  //3) https request
  
  @BeforeMethod
  public void beforeMethod() {
  }

  @AfterMethod
  public void afterMethod() {
  }

}
