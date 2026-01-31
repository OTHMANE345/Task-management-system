import { Component } from '@angular/core';
import { TaskService } from '../../services/task.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-task-table',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './task-table.component.html',
  styleUrl: './task-table.component.css'
})
export class TaskTableComponent {
 constructor(private taskService: TaskService) { }
   tasks: any[] = [];
 
   ngOnInit(){
     this.taskService.getTasksForAdmin().subscribe({
       next: (res) => {
        this.tasks = res.data;
       }, 
       error : (err) => {
         console.error('Error fetching tasks', err);
       }
     }
     ) 
   }
}
