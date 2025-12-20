import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-custom-item',
  standalone: true,
  imports: [],
  templateUrl: './custom-item.component.html',
  styleUrl: './custom-item.component.css'
})
export class CustomItemComponent {
@Input() task : any;
}
