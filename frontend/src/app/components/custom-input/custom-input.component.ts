import { Component, forwardRef, Input } from '@angular/core';
import { FormControl, NG_VALUE_ACCESSOR, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-custom-input',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './custom-input.component.html',
  styleUrl: './custom-input.component.css',
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => CustomInputComponent),
    multi: true
  }]
})
export class CustomInputComponent {
 @Input() label : string = '';
//  @Input() placeholder : string = '';
  @Input() type : string = '';
  @Input() placeholder : string = '';
  @Input() control : FormControl = new FormControl();
  @Input() isGray : boolean = false;
}
