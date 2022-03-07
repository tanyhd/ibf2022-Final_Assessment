import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { lastValueFrom } from "rxjs";
import { User, UserInfo } from "./models";

@Injectable()
export class UserService {
  constructor(private http: HttpClient) {}

  addNewUser(user: User) {
    return (lastValueFrom(
      this.http.post<any>("/api/user/Signup", user)
    ))
  }

  userLogin(email: string, password: string) {
    return (lastValueFrom(
      this.http.post<any>("/api/user/login", {"email": email, "password" : password})
    ))
  }

  updateUser(userInfo: UserInfo) {
    return (lastValueFrom(
      this.http.post<any>("/api/user/update", userInfo)
    ))
  }
}
