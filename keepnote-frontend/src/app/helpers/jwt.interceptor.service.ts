import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationService } from './../services/authentication.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthenticationService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add authorization header with jwt token if available 
        let currentUser = this.authenticationService.getBearerToken();
        if (currentUser) {
            request = request.clone({
                headers: request.headers.set("Authorization",
                    "Bearer " + currentUser)
            });
        } 

        return next.handle(request);
    }
}