import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
 

   private readonly apiUrl: string = 'http://localhost:8083/task/';
    constructor(
      private readonly http: HttpClient
    ) { }

    getTasks(): Observable<any> {
      return this.http.get<any>(this.apiUrl);
    }

    getTasksForAdmin(): Observable<any> {
      return this.http.get<any>(this.apiUrl+'admin');
    }

    addTask(name: string, description: string, priority: any, status: string, image:any, duration: any): Observable<any> {
      const body = {
        name: name,
        description: description,
        priority: priority,
        status: status,
        image: image,
        duration: duration
      };
      return this.http.post<any>(this.apiUrl, body);
    }

  getTaskById(taskId: any): Observable<any>{
    return this.http.get<any>(this.apiUrl + taskId) 
  }

   updateTask(taskId: any,name: string, description: string, priority: any, status: any, image:any, duration: any): Observable<any> {
      const body = {
        name: name,
        description: description,
        priority: priority,
        status: status,
        image: image,
        duration: duration
      };
      return this.http.put<any>(this.apiUrl + taskId, body);
    }
}
