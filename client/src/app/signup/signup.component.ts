import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Message, User } from '../models';
import { UserService } from '../user.services';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  form!: FormGroup
  returnMessage!: Message;

  constructor(private fb: FormBuilder, private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      username: this.fb.control("", [Validators.required, Validators.minLength(3)]),
      name: this.fb.control("", [Validators.required, Validators.minLength(3)]),
      email: this.fb.control("", [Validators.required, Validators.email]),
      password: this.fb.control("", [Validators.required, Validators.minLength(8)])
    })
  }

  submitForm() {
    let newUser = new User(this.form.value.username,
                    this.form.value.name,
                    this.form.value.email,
                    this.form.value.password)
    console.log(newUser)
    this.userService.addNewUser(newUser).then(result => {
      console.log(result as Message)
      this.returnMessage = result as Message
      this.router.navigate(['/'])
      alert("Account create, please login")
    }). catch(error => {
      console.log(error.message)
      alert("Email has been taken")

      this.ngOnInit()
    })

    console.log(this.returnMessage)
  }

}
