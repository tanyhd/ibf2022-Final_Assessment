import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Recipe, UserInfo } from '../models';
import { UserService } from '../user.services';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form!: FormGroup
  userInfo!: UserInfo

  constructor(private fb: FormBuilder, private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: this.fb.control("", [Validators.required, Validators.email]),
      password: this.fb.control("", [Validators.required, Validators.minLength(8)])
    })
  }

  login() {
    this.userService.userLogin(this.form.value.email, this.form.value.password)
      .then(result => {
        this.userInfo = result as UserInfo
        window.sessionStorage.setItem("userInfo", JSON.stringify(this.userInfo))
        this.router.navigate(['/profile'])
      }).catch(error => {
        console.log(error.message)
        alert("Wrong email/password")
      })
  }

}
