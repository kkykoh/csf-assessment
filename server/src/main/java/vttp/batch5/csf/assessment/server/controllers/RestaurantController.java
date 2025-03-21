package vttp.batch5.csf.assessment.server.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.core.MediaType;
import vttp.batch5.csf.assessment.server.models.Menu;
import vttp.batch5.csf.assessment.server.models.OrderItem;
import vttp.batch5.csf.assessment.server.services.RestaurantService;

import java.io.StringReader;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("")
public class RestaurantController {

  @Autowired
  private RestaurantService restaurantService;

  // TODO: Task 2.2
  // You may change the method's signature

  @GetMapping("/api/menu")
  public ResponseEntity<List<Menu>> getMenus() {
    List<Menu> getMenuList = restaurantService.getMenu();
    // JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
    // getMenuList.forEach(menu -> jsonArrayBuilder.add(menu.toJson()));

    // JsonArray jsonArray = jsonArrayBuilder.build();

    return ResponseEntity.ok().body(getMenuList);
    // return ResponseEntity.ok("{}");
  }

  // TODO: Task 4
  // Do not change the method's signature
  @PostMapping("/api/food_order")
  public ResponseEntity<String> postFoodOrder(@RequestBody String payload) {
       
    JsonReader jsonReader = Json.createReader(new StringReader(payload));
    JsonObject jsonObject = jsonReader.readObject();

    String username = jsonObject.getString("username", "");
    String password = jsonObject.getString("password", "");
    System.out.println("username: " + username);
    System.out.println("password : " + password);

  
    try {
      boolean isValidUser = restaurantService.validateUser(username, password);
      if (!isValidUser) {
        return ResponseEntity.status(HttpStatusCode.valueOf(401))
            .body("{ \"message\": \"invalid username and/or password\" }");
      }
    } catch (NoSuchAlgorithmException e) {  
      e.printStackTrace();
    }
    // List<OrderItem> itemsList = new ArrayList<>();
    // JsonArray postItemsArray = jsonObject.getJsonArray("items");
    // for (int i = 0; i < itemsArray.size(); i++) {
    //   JsonObject itemObject = postItemsArray.getJsonObject(i);

    //   OrderItem orderItem = new OrderItem();
    //   orderItem.setId(itemObject.getString("id"));
    //         orderItem.setPrice(itemObject.getJsonNumber("price").doubleValue());
    //         orderItem.setQuantity(itemObject.getInt("quantity"));
    //         itemsList.add(orderItem);
    // JsonArray itemArray = Json.createArrayBuilder()
    
    // .
    JsonObject foodOrderJson = Json.createObjectBuilder()
    .add("username", username)
    .add("password", password)
    .add("items", jsonObject.getJsonArray("items"))
    .build();


    // for validation successful
    // generate randon 8 character string for orderID
    String orderId = UUID.randomUUID().toString().substring(0, 8);

    double grandTotal = jsonObject.getJsonArray("items")
        .stream()
        .mapToDouble(item -> ((JsonObject) item).getJsonNumber("price").doubleValue()
            * ((JsonObject) item).getInt("quantity"))
        .sum();


        // ResponseEntity<String> paymentResponse = restaurantService.postToPaymentApi(orderId, username, grandTotal);

        // if (paymentResponse.getStatusCode() != HttpStatus.OK) {
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        //         .body("{ \"message\": \"Payment processing failed\" }");
        // }
    
    boolean paymentSuccess = restaurantService.processPayment(orderId, username, grandTotal);
    JsonObject paymentJson = Json.createObjectBuilder()
        .add("order_id", orderId)
        .add("payer", username)
        .add("payee", "KohKaiYing")
        .add("payment", grandTotal)
        .build();

    // JsonObject paymentReceipt = Json.createObjectBuilder()
    // .add("orderId", orderId)
    // .add(("paymentId"), "current hardcoded")
    // .add("total",grandTotal)
    // // .add("timestamp", time)
    // .build();
    return ResponseEntity.ok().body(foodOrderJson.toString());
    // return ResponseEntity.ok().body(paymentReceipt.toString());
    // ResponseEntity<String> paymentResponse = restaurantService.postToPaymentApi(paymentJson.toString());

    // Return payment response to the frontend
    // return paymentResponse;

  }

  @PostMapping(path = "/api/payment", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON) 
  public ResponseEntity<String> postToPayment(@RequestBody String payload) {
     JsonReader jsonReader = Json.createReader(new StringReader(payload));
     JsonObject jsonObject = jsonReader.readObject();
 
     String username = jsonObject.getString("username", "");
     String password = jsonObject.getString("password", "");
     System.out.println("username: " + username);
     System.out.println("password : " + password);
 
  
 
     // for validation successful
     // generate randon 8 character string for orderID
     String orderId = UUID.randomUUID().toString().substring(0, 8);
 
     double grandTotal = jsonObject.getJsonArray("items")
         .stream()
         .mapToDouble(item -> ((JsonObject) item).getJsonNumber("price").doubleValue()
             * ((JsonObject) item).getInt("quantity"))
         .sum();
 
 
         // ResponseEntity<String> paymentResponse = restaurantService.postToPaymentApi(orderId, username, grandTotal);
 
         // if (paymentResponse.getStatusCode() != HttpStatus.OK) {
         //     return ResponseEntity.status(HttpStatus.BAD_REQUEST)
         //         .body("{ \"message\": \"Payment processing failed\" }");
         // }
     
     boolean paymentSuccess = restaurantService.processPayment(orderId, username, grandTotal);
     JsonObject paymentJson = Json.createObjectBuilder()
         .add("order_id", orderId)
         .add("payer", username)
         .add("payee", "KohKaiYing")
         .add("payment", grandTotal)
         .build();
 
     // JsonObject paymentReceipt = Json.createObjectBuilder()
     // .add("orderId", orderId)
     // .add(("paymentId"), "current hardcoded")
     // .add("total",grandTotal)
     // // .add("timestamp", time)
     // .build();
     return ResponseEntity.ok().body(paymentJson.toString());
     
  }
  
}
