import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor{

    intercept(req: HttpRequest<any>,
        next: HttpHandler): Observable<HttpEvent<any>>{

            const idToken = localStorage.getItem("jwt");
            // console.log("AuthInterceptor: " + idToken)
            if(idToken){
                const cloned = req.clone({
                    headers: req.headers.set("Authorization",
                    "Bearer " + idToken)
                });
                // req = req.clone({
                //     setHeaders: {
                //       Authorization: `Bearer ${localStorage.getItem("jwt")}`
                //     }
                //   });
                // return next.handle(req);
                  return next.handle(cloned);
            }
            else {
                return next.handle(req);
            }

        }
}
