import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-custom-message',
  standalone: true,
  imports: [],
  templateUrl: './custom-message.component.html',
  styleUrl: './custom-message.component.css'
})
export class CustomMessageComponent {
@Input() message: string = "message test";
@Input() type: string = "success";
}
