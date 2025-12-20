import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-custom-select',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './custom-select.component.html',
  styleUrl: './custom-select.component.css'
})
export class CustomSelectComponent {
@Input() options : any[] = [
  'Hight ',
  'Medium',
  'Low'
];
@Input() label: string = 'Select option';
@Input() control : FormControl = new FormControl();
@Input() isGray: boolean = false;
}
