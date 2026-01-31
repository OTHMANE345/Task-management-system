import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
 token : any;

  ngOnInit(): void {
    this.token = localStorage.getItem('token');
  }
  constructor(private router : Router){}
  toTasks(){
    this.router.navigate(['/layout/tasks']);
  }
  toHome(){
    this.router.navigate(['/']);
  }

  toRegister(){
    this.router.navigate(['/signup']);
  }
  toLogin(){
    this.router.navigate(['/login']);
  }
}
