import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserInfo } from '../models';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  userInfo!: UserInfo
  status: string = ""

  constructor(private router: Router) { }

  ngOnInit(): void {
    if (window.sessionStorage.getItem("userInfo") === null) {
    } else {
      this.userInfo = JSON.parse(window.sessionStorage.getItem("userInfo") || "")
      this.status = "login"
    }
  }

  signOut() {
    window.sessionStorage.clear();
    this.router.navigate(['/'])
  }

}
