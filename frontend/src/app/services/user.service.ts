import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly apiUrl: string = 'http://localhost:8083/user/';
  constructor(
    private readonly http: HttpClient
  ) { }

  singUp(userName: string, phone: string, email: string, password: string): Observable<any>{
    const body = {
      name: userName,
      contactNumber: phone,
      email: email,
      password: password
    };
    return this.http.post<any>(this.apiUrl + 'singup', body);
  }

  login(email: string, password: string): Observable<any>{
    const body = {
      email: email,
      password: password
    };
    return this.http.post<any>(this.apiUrl + 'login', body)
  }

  getAllUsers(): Observable<any>{
    return this.http.get<any>(this.apiUrl + 'admin')
  }
}
