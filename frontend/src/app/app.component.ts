import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CustomInputComponent } from "./components/custom-input/custom-input.component";
import { CustomTitleComponent } from "./components/custom-title/custom-title.component";
import { CustomItemComponent } from "./components/custom-item/custom-item.component";
import { CustomImageUploadComponent } from "./components/custom-image-upload/custom-image-upload.component";
import { CustomImageUploadObjectComponent } from "./components/custom-image-upload-object/custom-image-upload-object.component";
import { CustomTextareaComponent } from "./components/custom-textarea/custom-textarea.component";
import { TaskTableComponent } from "./pages/task-table/task-table.component";
import { TaskDetailsComponent } from "./pages/task-details/task-details.component";
import { UserTableComponent } from "./pages/user-table/user-table.component";
import { TasksComponent } from "./pages/tasks/tasks.component";
import { LoginComponent } from "./pages/login/login.component";
import { SignupComponent } from "./pages/signup/signup.component";
import { AddTaskComponent } from "./pages/add-task/add-task.component";
import { SidebarComponent } from "./components/sidebar/sidebar.component";
import { NavbarComponent } from "./components/navbar/navbar.component";
import { FooterComponent } from "./components/footer/footer.component";
import { ProfileComponent } from "./pages/profile/profile.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CustomInputComponent, CustomTitleComponent, CustomItemComponent, CustomImageUploadComponent, CustomImageUploadObjectComponent, CustomTextareaComponent, TaskTableComponent, TaskDetailsComponent, UserTableComponent, TasksComponent, LoginComponent, SignupComponent, AddTaskComponent, SidebarComponent, NavbarComponent, FooterComponent, ProfileComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
}
