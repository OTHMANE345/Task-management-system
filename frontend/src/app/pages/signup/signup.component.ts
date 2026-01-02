import { Component, inject } from '@angular/core';
import { CustomTitleComponent } from '../../components/custom-title/custom-title.component';
import { CustomInputComponent } from '../../components/custom-input/custom-input.component';
import { FormBuilder, FormControl, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CustomTitleComponent, CustomInputComponent, ReactiveFormsModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css',
})
export class SignupComponent {
  private fb = inject(FormBuilder);

  singUpForm = this.fb.group({
    userName: [''],
    phone: [''],
    email: [''],
    password: [''],
  });

  constructor(private readonly router: Router, private userService: UserService) {}

  getControl(name: string) {
    return this.singUpForm.get(name) as FormControl;
  }

  onSubmit(): void {
    console.log('sing up data', this.singUpForm.value);
    const userName = this.singUpForm.value.userName as string;
    const phone = this.singUpForm.value.phone as string;
    const email = this.singUpForm.value.email as string;
    const password = this.singUpForm.value.password as string;
    this.userService.singUp(userName, phone, email, password).subscribe({
      next: (response) => {
        console.log('Signup successful', response);
        this.router.navigate(['/login']);
      },
      error: (error) => {
        console.error('Signup failed', error);
      }
    });
  }
}
