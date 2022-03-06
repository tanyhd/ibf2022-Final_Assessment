import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LineItem, UserInfo } from '../models';
import { UserService } from '../user.services';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  userInfo!: UserInfo
  status: string = ""
  form!: FormGroup
  lineItemFormArray!: FormArray
  listOfLineItem: LineItem[] = []

  constructor(private router: Router, private fb: FormBuilder, private userService: UserService) { }

  ngOnInit(): void {
    if (window.sessionStorage.getItem("userInfo") === null) {
    } else {
      this.userInfo = JSON.parse(window.sessionStorage.getItem("userInfo") || "")
      this.status = "login"
    }

    this.lineItemFormArray = this.fb.array([])
    this.form = this.fb.group({
      listOfItems: this.lineItemFormArray
    })
    if(this.status != "") {
      for(let i = 0; i < this.userInfo.lineItem.length; i++) {
        this.listOfLineItem.push(this.userInfo.lineItem[i] as LineItem)
      }
    }

  }

  signOut() {
    window.sessionStorage.clear();
    this.router.navigate(['/'])
  }

  update() {
    const listOfItemToUpdate = JSON.parse(JSON.stringify(this.listOfLineItem));

    for(let i = 0; i < this.lineItemFormArray.length; i++) {
      console.log(this.lineItemFormArray.get(i.toString())!.value.name)
      console.log(this.lineItemFormArray.get(i.toString())!.value.quantity)
      let temp = {name: this.lineItemFormArray.get(i.toString())!.value.name, quantity: this.lineItemFormArray.get(i.toString())!.value.quantity } as LineItem
      listOfItemToUpdate.push(temp)
    }

    let tempUserInfo = { username: this.userInfo.username, name: this.userInfo.name, email: this.userInfo.email, lineItem: listOfItemToUpdate}
    window.sessionStorage.setItem("userInfo", JSON.stringify(tempUserInfo))
    this.userService.updateUser(tempUserInfo)
      .then(result => {
        console.log(result)
      }).catch(error => {console.log(error.message)})
  }

  addInput() {
    const lineItemGroup = this.fb.group({
      name: this.fb.control('', [Validators.required, Validators.minLength(3)]),
      quantity: this.fb.control('', [Validators.required]),
    })

    this.lineItemFormArray.push(lineItemGroup)
  }

  deleteLineItem(itemNumber: number) {
    this.lineItemFormArray.removeAt(itemNumber)
  }

  deleteLineItemList(i: number) {
    this.listOfLineItem.splice(i, 1);
  }
}
