import { Component, inject } from '@angular/core';
import { TaskService } from '../../services/task.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { CustomMessageComponent } from "../../components/custom-message/custom-message.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-task-details',
  standalone: true,
  imports: [ReactiveFormsModule, CustomMessageComponent, CommonModule],
  templateUrl: './task-details.component.html',
  styleUrl: './task-details.component.css'
})
export class TaskDetailsComponent {
  taskId: any;
  task: any;
  showMessage: boolean = false;
  type: string = "success";
  message: string = "Status updated successfully"

  private fb = inject(FormBuilder);
  
     updatetaskForm = this.fb.group({
      status: ['']
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
        this.showMessage = true
        setTimeout(
          () => {
            this.showMessage = false;
          }, 2000
        )
      },
      error: (err: any) => {
        console.error('Error updating task', err);
        this.type = "error";
        this.message = "error";
        this.showMessage = true
        setTimeout(
          () => {
            this.showMessage = false;
          }, 2000
        )
      }
    }
  )
}

}
