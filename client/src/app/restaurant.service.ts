import { HttpClient } from "@angular/common/http"
import { Injectable, inject } from "@angular/core"
import { firstValueFrom, Observable } from "rxjs"
import { Menu, orderItems } from "./models"

@Injectable()
export class RestaurantService {

  menu!: Menu[]
  private orderItems: orderItems[] = [];

  private httpClient = inject(HttpClient)
  // TODO: Task 2.2
  // You change the method's signature but not the name
  // getMenuItems(): Promise<Menu[]> {
  //   return firstValueFrom(this.httpClient.get<Menu[]>("/api/menu"))
  // }
  getMenuItems(): Observable<Menu[]> {
    return this.httpClient.get<Menu[]>("/api/menu")
  }

  // TODO: Task 3.2
  
    setOrder(items: orderItems[]) {
      this.orderItems = items;
    }
  
    getOrder() {
      return this.orderItems;
    }
}
