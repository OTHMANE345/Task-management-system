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

@Component({
  selector: 'app-add-task',
  standalone: true,
  imports: [ReactiveFormsModule, CustomTitleComponent, CustomInputComponent, CustomImageUploadObjectComponent, CustomTextareaComponent, CustomSelectComponent],
  templateUrl: './add-task.component.html',
  styleUrl: './add-task.component.css'
})
export class AddTaskComponent {
private readonly fb = inject(FormBuilder);
image!: any;
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
  this.taskService.addTask(name, description, priority, 'todo', this.image, duration).subscribe({
    next : (res) => {
    console.log('Task added successfully', res);
    this.router.navigate(['/layout/tasks']);
    },
    error : (err) => {
      console.error('Error adding task', err);
    }
  })
}

}
