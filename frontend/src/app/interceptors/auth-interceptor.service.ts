import { HttpEvent, HttpHandler, HttpInterceptor, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authToken = localStorage.getItem('token');
  if(authToken){
    const Req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${authToken}`
        }
      });
    return next(Req);
  }
  return next(req);
}
