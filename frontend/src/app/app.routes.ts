import { Routes } from '@angular/router';
import { SignupComponent } from './pages/signup/signup.component';
import { LoginComponent } from './pages/login/login.component';
import { TasksComponent } from './pages/tasks/tasks.component';
import { AddTaskComponent } from './pages/add-task/add-task.component';
import { TaskDetailsComponent } from './pages/task-details/task-details.component';
import { LayoutComponent } from './pages/layout/layout.component';
import { TaskTableComponent } from './pages/task-table/task-table.component';
import { UserTableComponent } from './pages/user-table/user-table.component';

export const routes: Routes = [
  {
    path: '',
    component: SignupComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'layout',
    component: LayoutComponent,
    children: [
      {
        path: 'tasks',
        component: TasksComponent,
      },
      {
        path: 'add-task',
        component: AddTaskComponent,
      },
      {
        path: 'task/:id',
        component: TaskDetailsComponent,
      },
       {
        path: 'tasksList',
        component: TaskTableComponent,
      },
       {
        path: 'users',
        component: UserTableComponent,
      },
    ],
  },
];
