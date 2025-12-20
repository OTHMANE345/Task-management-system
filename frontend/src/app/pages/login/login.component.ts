import { Component, inject } from '@angular/core';
import { CustomInputComponent } from "../../components/custom-input/custom-input.component";
import { CustomTitleComponent } from "../../components/custom-title/custom-title.component";
import { Router } from '@angular/router';
import { FormBuilder, FormControl, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CustomInputComponent, CustomTitleComponent, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  private fb = inject(FormBuilder);

   loginForm = this.fb.group({
    email: [''],
    password: ['']
  })

  constructor(private readonly router: Router) {}

  getControl(name: string){
    return this.loginForm.get(name) as FormControl;
  }

  onSubmit(): void {
    console.log("login data", this.loginForm.value);
    const email = this.loginForm.value.email as string;
    const password = this.loginForm.value.password as string;
  }

 
}
