import { HttpEvent, HttpHandler, HttpInterceptor, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';



// @Injectable()
// export class AuthInterceptorService implements HttpInterceptor{

//   constructor() { }
//   intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
//     const authToekn = localStorage.getItem('token');
//     if(authToekn){
//       const Req = req.clone({
//         setHeaders: {
//           Authorization: `Bearer ${authToekn}`
//         }
//       });
//       return next.handle(Req);
//     }
//     return next.handle(req);
//   }
// }

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
