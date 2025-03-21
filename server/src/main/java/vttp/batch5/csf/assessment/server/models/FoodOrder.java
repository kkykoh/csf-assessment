package vttp.batch5.csf.assessment.server.models;

import java.util.List;

public class FoodOrder {

    private String username;
    private String password;
    private List<OrderItem> items;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<OrderItem> getItems() {
        return items;
    }
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
    

    
    
}
