import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, ReactiveFormsModule } from '@angular/forms';
import { CustomInputComponent } from "../../components/custom-input/custom-input.component";
import { Router } from '@angular/router';
import { CustomTitleComponent } from "../../components/custom-title/custom-title.component";

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [ReactiveFormsModule, CustomInputComponent, CustomTitleComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
private fb = inject(FormBuilder);

   userForm = this.fb.group({
    username: [''],
    email: [''],
    phone: [''],
    password: [''],
  })

  constructor(private readonly router: Router) {}

  getControl(name: string){
    return this.userForm.get(name) as FormControl;
  }

  onSubmit(): void {
    console.log("profile data", this.userForm.value);
  }
}
