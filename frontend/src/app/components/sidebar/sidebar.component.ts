import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {


isClosed: boolean = false;
isAdmin: boolean = false;

constructor(private router: Router){}

ngOnInit(){
  const role = localStorage.getItem('role');
  if(role === "admin"){
    this.isAdmin = true;
  }
}

toggleSidebar() {
  this.isClosed = !this.isClosed;
}

showUsers(){
this.router.navigate(['/layout/users']);
}

showTasks(){
  this.router.navigate(['/layout/tasksList']);
}

addtask(){
    this.router.navigate(['/layout/add-task']);

}

showMyTasks(){
    this.router.navigate(['/layout/tasks']);

}

Logout(){
  localStorage.removeItem('token');
  localStorage.removeItem('role');
   this.router.navigate(['']);
}

}
