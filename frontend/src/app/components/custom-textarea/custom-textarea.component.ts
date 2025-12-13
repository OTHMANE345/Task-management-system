import { CommonModule } from '@angular/common';
import { Component, forwardRef, Input } from '@angular/core';
import { FormControl, FormsModule, NG_VALUE_ACCESSOR, ReactiveFormsModule } from '@angular/forms';
import { CustomInputComponent } from '../custom-input/custom-input.component';

@Component({
  selector: 'app-custom-textarea',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './custom-textarea.component.html',
  styleUrl: './custom-textarea.component.css',
   providers: [{
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => CustomInputComponent),
      multi: true
    }]
})
export class CustomTextareaComponent {
@Input() placeholder: string = '';
@Input() control : FormControl = new FormControl();
}
