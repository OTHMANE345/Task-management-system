import { Component } from '@angular/core';
import { CustomItemComponent } from "../../components/custom-item/custom-item.component";
import { FormsModule } from "@angular/forms";
import { CommonModule } from '@angular/common';
import { TaskService } from '../../services/task.service';

@Component({
  selector: 'app-tasks',
  standalone: true,
  imports: [CustomItemComponent, FormsModule, CommonModule],
  templateUrl: './tasks.component.html',
  styleUrl: './tasks.component.css'
})
export class TasksComponent {

  constructor(private taskService: TaskService) { }
  tasks: any[] = [];

  ngOnInit(){
    this.taskService.getTasks().subscribe({
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
