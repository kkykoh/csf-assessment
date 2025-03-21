import { Component, inject, OnInit } from '@angular/core';
import { RestaurantService } from '../restaurant.service';
import { Menu } from '../models';
import { Router } from '@angular/router';
// import { OrderService } from '../order.service';

@Component({
  selector: 'app-menu',
  standalone: false,
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent implements OnInit {

  menu: Menu[] = []
  selectedItems: { [id: string]: number } = {}

  // TODO: Task 2

  private router = inject(Router)
  private restaurantService = inject(RestaurantService)
  // private orderService = inject(OrderService)
  // ngOnInit(): void {
  //   this.restaurantService.getMenuItems().then(data => {
  //     console.log(data)
  //     this.menu = data;
  //   }).catch(error => {
  //     console.error(error)
  //   })
  // }

  ngOnInit(): void {
    this.restaurantService.getMenuItems().subscribe(
      (data: Menu[]) => {
        this.menu = data;
        this.selectedItems = {}; 
      },
      (error) => {
        console.error("Error fetching menu:", error);
      }
    );
  }

  // add item
  addItem(item: Menu): void {
    if (this.selectedItems[item.id]) {
      this.selectedItems[item.id]++;
    } else {
      this.selectedItems[item.id] = 1;
    }
  }

  // minus item
  removeItem(item: Menu): void {
    if (this.selectedItems[item.id] && this.selectedItems[item.id] > 0) {
      this.selectedItems[item.id]--;
      if (this.selectedItems[item.id] === 0) {
        delete this.selectedItems[item.id]; 
      }
    }
  }

  getTotalPrice(): number {
    return this.menu.reduce((total, item) => {
      return total + (this.selectedItems[item.id] ? this.selectedItems[item.id] * item.price : 0);
    }, 0);
  }

  getTotalSelectedItems(): number {
    return Object.values(this.selectedItems).reduce((sum, qty) => sum + qty, 0);
  }

  goPlaceOrder(): void {
    // const orderItems = this.menu
    //   .filter(item => this.selectedItems[item.id])
    //   .map(item => ({
    //     id: item.id,
    //     name: item.name,
    //     price: item.price,
    //     quantity: this.selectedItems[item.id]
    //   }))

    // this.router.navigate(['/view2'])
  

  const orderItems = this.menu
    .filter(item => this.selectedItems[item.id])
    .map(item => ({
      id: item.id,
      name: item.name,
      price: item.price,
      quantity: this.selectedItems[item.id]
    }));

  this.restaurantService.setOrder(orderItems);
  this.router.navigate(['/view2']);
  }
}




