import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-confirmation',
  standalone: false,
  templateUrl: './confirmation.component.html',
  styleUrl: './confirmation.component.css'
})
export class ConfirmationComponent implements OnInit {

  // TODO: Task 5

  orderId!: string;
  paymentId!: string;
  total!: number;
  timestamp!: string;

  private activatedRoute = inject(ActivatedRoute)
 
  ngOnInit(): void {
    const orderReceipt = history.state.orderReceipt;
    
    if (orderReceipt) {
      this.orderId = orderReceipt.orderId;
      this.paymentId = orderReceipt.paymentId;
      this.total = orderReceipt.total;
      this.timestamp = orderReceipt.timestamp;
    }
  }

}
