import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouteConfigLoadEnd, Router } from '@angular/router';
// import { OrderService } from '../order.service';
import { Menu, orderItems } from '../models';
import { HttpClient } from '@angular/common/http';
import { RestaurantService } from '../restaurant.service';

@Component({
  selector: 'app-place-order',
  standalone: false,
  templateUrl: './place-order.component.html',
  styleUrl: './place-order.component.css'
})
export class PlaceOrderComponent implements OnInit {

  // TODO: Task 3
  // constructor()

  private fb = inject(FormBuilder)
  private router = inject(Router)
  private restaurantService = inject(RestaurantService)

  private HttpClient = inject(HttpClient)

  protected form!: FormGroup
  orderItems: orderItems[] = [];

  
  // orderItems: string[] = []
  menu: Menu[] = []

  ngOnInit(): void {
    this.form = this.createForm()
    this.orderItems = this.restaurantService.getOrder();
  }
  private createForm(): FormGroup {
    return this.fb.group({
      username: this.fb.control<string>('', [ Validators.required]), 
      password: this.fb.control<string>('', [ Validators.required ])
    })
  }

  confirmOrder(): void {
    if (this.form.invalid) {
      return
    }
    // const order:Order = {
    //   username: this.form.value.name,
    //   password: this.form.value.password, 
    //   items: {
    //     orderItems: []
    //   }
    // }

    const order = {
      username: this.form.value.username,
      password: this.form.value.password,
      items: this.orderItems.map(item => ({
        id: item.id, 
        price: item.price, 
        quantity: item.quantity
      }))
    }

    console.log('Order Confirmed', order);

    this.HttpClient.post('/api/food_order', order).subscribe(
      (response) => {
        console.info('order received for posting :', response)
        // to route to order confirmation if need
        // this.router.navigate(['/view3']);
      }, (error) => {
        console.log('error message for posting order : ', error)
      }
    )
    
  }

  backToView1(): void {
    this.form.reset()
    this.router.navigate(['/'])
  }

  getTotalPrice(): number {
    return this.orderItems.reduce((total, item) => {
      return total + (item.price * item.quantity);
    }, 0);
  }


}
