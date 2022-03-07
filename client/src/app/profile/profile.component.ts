import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LineItem, Recipe, UserInfo } from '../models';
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
  recipesList: Recipe[] = []
  recipesListAsString: string = ""

  constructor(private router: Router, private fb: FormBuilder, private userService: UserService) { }

  ngOnInit(): void {
    if (window.sessionStorage.getItem("userInfo") === null) {
    } else {
      this.userInfo = JSON.parse(window.sessionStorage.getItem("userInfo") || "")
      if(this.userInfo.recipeListString != "") {
        this.recipesListAsString = this.userInfo.recipeListString.substring(1, this.userInfo.recipeListString.length - 1)
        for(let i=0; i < this.recipesListAsString.length; i++) {
          this.recipesListAsString = this.recipesListAsString.replace("\\", "")
        }
      }

      if (window.sessionStorage.getItem("saveRecipeList")?.length == 0 || window.sessionStorage.getItem("saveRecipeList") === null) {
        try {
          window.sessionStorage.setItem("saveRecipeList", JSON.stringify(JSON.parse(this.recipesListAsString)))
        } catch(e) {}
      }
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

    if (this.recipesList.length == 0) {
      try {
        this.recipesList = JSON.parse(window.sessionStorage.getItem("saveRecipeList") || "")
      } catch(e) {}
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
    window.sessionStorage.setItem("saveRecipeList", JSON.stringify(this.recipesList))
    let tempUserInfo = { username: this.userInfo.username, name: this.userInfo.name, email: this.userInfo.email, lineItem: listOfItemToUpdate,  recipeListString: JSON.stringify(window.sessionStorage.getItem("saveRecipeList"))}
    window.sessionStorage.setItem("userInfo", JSON.stringify(tempUserInfo))
    this.userService.updateUser(tempUserInfo)
      .then(result => {
        console.log(result)
        alert("Information updated");
      }).catch(error => {
        console.log(error.message)})
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

  tempRecipe(recipe: Recipe) {
    window.sessionStorage.setItem("tempRecipe", JSON.stringify(recipe))
  }

  remove(recipe: Recipe) {
    let index = 0
    for(let i = 0; i < this.recipesList.length; i++) {
      if(this.recipesList[i].label === recipe.label) {
        index = i
      }
    }
    this.recipesList.splice(index, 1)
    window.sessionStorage.setItem("saveRecipeList", JSON.stringify(this.recipesList))
    console.log(index)
  }
}
