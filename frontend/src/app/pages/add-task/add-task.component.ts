import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, ReactiveFormsModule } from '@angular/forms';
import { CustomTitleComponent } from "../../components/custom-title/custom-title.component";
import { CustomInputComponent } from "../../components/custom-input/custom-input.component";
import { Router } from '@angular/router';
import { CustomImageUploadObjectComponent } from "../../components/custom-image-upload-object/custom-image-upload-object.component";
import { CustomTextareaComponent } from "../../components/custom-textarea/custom-textarea.component";
import { CustomSelectComponent } from "../../components/custom-select/custom-select.component";
import { Task } from 'zone.js/lib/zone-impl';
import { TaskService } from '../../services/task.service';
import { CustomMessageComponent } from "../../components/custom-message/custom-message.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-task',
  standalone: true,
  imports: [ReactiveFormsModule, CustomTitleComponent, CustomInputComponent, CustomImageUploadObjectComponent, CustomTextareaComponent, CustomSelectComponent, CustomMessageComponent, CommonModule],
  templateUrl: './add-task.component.html',
  styleUrl: './add-task.component.css'
})
export class AddTaskComponent {
  showMessage: boolean = false;
  type: string = "success";
  message: string = "Task added successfully"
private readonly fb = inject(FormBuilder);
image!: any;
isLoading: boolean = false;
options :any[] = [
  'High',
  'Medium',
  'Low'
]

taskForm = this.fb.group(
  {
    name: [''],
    duration: [''],
    description: [''],
    priority: []
  }
)

constructor(private readonly router : Router, private taskService: TaskService){}

getControl(name: string){
  return this.taskForm.get(name) as FormControl;
}
handleOnFileSelect(file: any){
  this.image = file;
  console.log("image data in parent", file)

}

onSubmit(): void {
  console.log('task form data', this.taskForm.value)
  const name = this.taskForm.value.name as string;
  const duration = this.taskForm.value.duration as string;
  const priority = this.taskForm.value.priority  ;
  const description = this.taskForm.value.description as string;
  this.isLoading = true;
  this.taskService.addTask(name, description, priority, 'NotYet', this.image, duration).subscribe({
    next : (res) => {
    
    console.log('Task added successfully', res);
    setTimeout(() => {
        this.isLoading = false;
      }, 500)
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
      console.error('Error adding task', err);
      setTimeout(() => {
        this.isLoading = false;
      }, 500)
       this.type = "error";
        this.message = "error";
        this.showMessage = true
        setTimeout(
          () => {
            this.showMessage = false;
          }, 2000
        )

    }, 
    complete: () => {
      setTimeout(() => {
        this.isLoading = false;
      }, 500)
    }
  })
}

}
