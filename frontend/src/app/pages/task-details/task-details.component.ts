import { Component, inject } from '@angular/core';
import { TaskService } from '../../services/task.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-task-details',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './task-details.component.html',
  styleUrl: './task-details.component.css'
})
export class TaskDetailsComponent {
  taskId: any;
  task: any;

  private fb = inject(FormBuilder);
  
     updatetaskForm = this.fb.group({
     status: [''],
    })

 constructor(private taskService: TaskService,
private route: ActivatedRoute
 ){}
 
 ngOnInit() {
  this.taskId = this.route.snapshot.paramMap.get('id');
  this.taskService.getTaskById(this.taskId).subscribe(
     { next : (res) => {
      this.task = res.data;
      this.updatetaskForm.patchValue({
        status: this.task.status
      })
    },
    error: (err) => {
      console.log(err);
    }}
  );
}

updateStatus() {
  this.taskService.updateTask(this.taskId, this.task.name, this.task.description, this.task.priority, this.updatetaskForm.value.status, this.task.image, this.task.duration).subscribe(
    {
      next: (res: any) => {
        console.log('Task updated successfully', res);
        this.task = res.data;
      },
      error: (err: any) => {
        console.error('Error updating task', err);
      }
    }
  )
}

}
