import { Component, inject } from '@angular/core';
import { CustomInputComponent } from "../../components/custom-input/custom-input.component";
import { CustomTitleComponent } from "../../components/custom-title/custom-title.component";
import { Router } from '@angular/router';
import { FormBuilder, FormControl, ReactiveFormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { CustomMessageComponent } from "../../components/custom-message/custom-message.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CustomInputComponent, CustomTitleComponent, ReactiveFormsModule, CustomMessageComponent, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  showMessage: boolean = false;
  type: string = "success";
  message: string = "Login with success"
  private fb = inject(FormBuilder);

   loginForm = this.fb.group({
    email: [''],
    password: ['']
  })

  constructor(private readonly router: Router, private userService: UserService) {}

  getControl(name: string){
    return this.loginForm.get(name) as FormControl;
  }

  onSubmit(): void {
    console.log("login data", this.loginForm.value);
    const email = this.loginForm.value.email as string;
    const password = this.loginForm.value.password as string;
    this.userService.login(email, password).subscribe({
      next: (res) => {
        localStorage.setItem('token', res.token);
        localStorage.setItem('role', res.user.role);
         localStorage.setItem('email', res.user.email);
        console.log('Login successful', res);
        
        this.showMessage = true
        setTimeout(
          () => {
            this.showMessage = false;
          }, 2000
        )

        setTimeout(
          () => {
            this.router.navigate(['/layout/tasks']);
          }, 3000
        )
      },
      error : (err) => {
        console.error('Login failed', err);
         this.type = "error";
        this.message = "error";
        this.showMessage = true
        setTimeout(
          () => {
            this.showMessage = false;
          }, 2000
        )
      }
  });
  }

 
}


