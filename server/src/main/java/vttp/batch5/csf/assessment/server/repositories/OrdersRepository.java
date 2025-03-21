package vttp.batch5.csf.assessment.server.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp.batch5.csf.assessment.server.models.Menu;


@Repository
public class OrdersRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  // TODO: Task 2.2
  // You may change the method's signature
  // Write the native MongoDB query in the comment below
  //
  //  Native MongoDB query here
  //  db.getCollection("menus").find({s})
  // .sort({ name: 1 })

  public List<Menu> getMenu() {
    // SortOperation sortOps = new SortOperation(Sort.by(Sort.Direction.ASC, "name"));
    Query query = new Query().with(Sort.by(Sort.Direction.ASC, "name"));
    List<Menu> menuList = mongoTemplate.find(query, Menu.class, "menus");
    System.out.println("list of menus : " + menuList);
    return menuList;

   
  }

  // TODO: Task 4
  // Write the native MongoDB query for your access methods in the comment below
  //
  //  Native MongoDB query here
  
}
