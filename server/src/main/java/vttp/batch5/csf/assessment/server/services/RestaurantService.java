package vttp.batch5.csf.assessment.server.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.batch5.csf.assessment.server.models.Menu;
import vttp.batch5.csf.assessment.server.repositories.OrdersRepository;
import vttp.batch5.csf.assessment.server.repositories.RestaurantRepository;

@Service
public class RestaurantService {

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private OrdersRepository ordersRepository;

  private static final String PAYMENT_SERVICE = "https://payment-service-production-a75a.up.railway.app/api/payment";
  private final RestTemplate restTemplate = new RestTemplate();

  // TODO: Task 2.2
  // You may change the method's signature
  public List<Menu> getMenu() {
    return ordersRepository.getMenu();
  }

  // TODO: Task 4

  public boolean validateUser(String username, String rawPassword) throws NoSuchAlgorithmException {

    System.out.println("username received for validation : " + username);
    System.out.println("PASSWORD received for validation : " + rawPassword);

    String storedPassword = restaurantRepository.getPasswordByUsername(username);
    return storedPassword != null && storedPassword.equals(hashPassword(rawPassword));

  }

  public String hashPassword(String password) throws NoSuchAlgorithmException {

    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-224");
      byte[] hashedBytes = digest.digest(password.getBytes());
      StringBuilder hexString = new StringBuilder();
      for (byte b : hashedBytes) {
        hexString.append(String.format("%02x", b));
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException ex) {
      return ex.getMessage();
    }

    // try {
    // MessageDigest digest = MessageDigest.getInstance("SHA-224");
    // byte[] hashedBytes = digest.digest(password.getBytes());
    // return bytesToHex(hashedBytes);
    // } catch (NoSuchAlgorithmException ex) {
    // throw new RuntimeException("Error hashing password : ", ex);
    // }
  }

  // private String bytesToHex(byte[] bytes) {
  // StringBuilder hexString = new StringBuilder();
  // for (byte b : bytes) {
  // hexString.append(String.format("%02x", b));
  // }
  // return hexString.toString();
  // }

  // public String postToPaymentApi(String orderId, String payer, double amount) {

  //   try {
  //     // String orderId = UUID.randomUUID().toString().substring(0, 8);
    
  //     JsonObject paymentPayload = Json.createObjectBuilder()
  //     .add("order_id", orderId)
  //     .add("payer", payer)
  //     .add("payee", "KohKaiYing")
  //     .add("payment", amount)
  //     .build();
    
  //     HttpHeaders headers = new HttpHeaders();
  //     headers.setContentType(MediaType.APPLICATION_JSON);
  //     headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));
  //     headers.set("X-Authenticate", payer);
    
  //     HttpEntity<String> requestEntity = new HttpEntity<>(paymentPayload.toString(), headers);
    
  //     ResponseEntity<String> response = restTemplate.exchange(PAYMENT_SERVICE, HttpMethod.POST, requestEntity,String.class);
    
  //     System.out.println("response from post to payment api : " + response);
  //     return response.toString();
  //     } catch (Exception e) {
  //     return e.getMessage().toString();
  //     }
  // }

  public boolean processPayment(String orderId, String payer, double amount) {
  try {
  // String orderId = UUID.randomUUID().toString().substring(0, 8);

  JsonObject paymentPayload = Json.createObjectBuilder()
  .add("order_id", orderId)
  .add("payer", payer)
  .add("payee", "KohKaiYing")
  .add("payment", amount)
  .build();

  HttpHeaders headers = new HttpHeaders();
  headers.setContentType(MediaType.APPLICATION_JSON);
  headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));
  headers.set("X-Authenticate", payer);

  HttpEntity<String> requestEntity = new
  HttpEntity<>(paymentPayload.toString(), headers);

  ResponseEntity<String> response = restTemplate.exchange(PAYMENT_SERVICE, HttpMethod.POST, requestEntity, String.class);

  return response.getStatusCode() == HttpStatus.OK;
  } catch (Exception e) {
  return false;
  }
}
}
