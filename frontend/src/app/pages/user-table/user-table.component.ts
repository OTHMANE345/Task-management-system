import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-table',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-table.component.html',
  styleUrl: './user-table.component.css'
})
export class UserTableComponent {
constructor(private userService: UserService) { }
   users: any[] = [];
 
   ngOnInit(){
     this.userService.getAllUsers().subscribe({
       next: (res) => {
        this.users = res;
       }, 
       error : (err) => {
         console.error('Error fetching users', err);
       }
     }
     ) 
   }
}
