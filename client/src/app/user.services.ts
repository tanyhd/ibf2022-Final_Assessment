import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { lastValueFrom } from "rxjs";
import { User } from "./models";

@Injectable()
export class UserService {
  constructor(private http: HttpClient) {}

  addNewUser(user: User) {
    return (lastValueFrom(
      this.http.post<any>("http://localhost:8080/api/user/Signup", user)
    ))
  }

  getUser(email: string, password: string) {
    return (lastValueFrom(
      this.http.get<any>(`http://localhost:8080/api/user/login/${email}/${password}`)
    ))
  }
}
