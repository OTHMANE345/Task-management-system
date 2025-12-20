import { Component } from '@angular/core';
import { CustomItemComponent } from "../../components/custom-item/custom-item.component";
import { FormsModule } from "@angular/forms";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-tasks',
  standalone: true,
  imports: [CustomItemComponent, FormsModule, CommonModule],
  templateUrl: './tasks.component.html',
  styleUrl: './tasks.component.css'
})
export class TasksComponent {

  tasks: any[] = [];

  ngOnInit(){
    this.tasks = [
      {
        id: 1,
        duration: "22h",
        title: "Task One",
        description: "This is the description for task one."
      },
      {
        id: 2,
        duration: "10h",
        title: "Task Two",
        description: "This is the description for task two."
      },
      {
        id: 3,
        duration: "10h",
        title: "Task Two",
        description: "This is the description for task two."
      },
        {
        id: 4,
        duration: "10h",
        title: "Task Two",
        description: "This is the description for task two."
      },
      {
        id: 5,
        duration: "10h",
        title: "Task Two",
        description: "This is the description for task two."
      }
    ]
  }

}
