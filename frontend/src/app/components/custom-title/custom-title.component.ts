import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-custom-title',
  standalone: true,
  imports: [],
  templateUrl: './custom-title.component.html',
  styleUrl: './custom-title.component.css'
})
export class CustomTitleComponent {
@Input() title : string = 'test title';
}
