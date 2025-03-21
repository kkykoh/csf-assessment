package vttp.batch5.csf.assessment.server.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Menu {

    private String id;
    private String name;
    private double price;
    private String description;

    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Menu() {}

    public Menu(String id, String name, double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public JsonObject toJson(){
        JsonObjectBuilder json = Json.createObjectBuilder()
				.add("_id", id)
				.add("name", name)
				.add("description", description)
				.add("price", price);

        return json.build();
    }
    
}
