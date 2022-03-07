import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Recipe } from '../models';

@Component({
  selector: 'app-recipes-details',
  templateUrl: './recipes-details.component.html',
  styleUrls: ['./recipes-details.component.css']
})
export class RecipesDetailsComponent implements OnInit {

  recipe!: Recipe
  numberOfIngredients: number = 0
  returnPath: string = ""
  saveRecipeList: Recipe[] = []

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.returnPath = history.state.data
    console.log(this.returnPath)
    this.recipe = JSON.parse(window.sessionStorage.getItem("tempRecipe") || "")

    try {
      this.saveRecipeList = JSON.parse(window.sessionStorage.getItem("saveRecipeList") || "")
    } catch(e) {console.log(e)}

    this.numberOfIngredients = this.recipe.ingredients.length
    console.log(this.recipe)
    console.log(this.recipe.label)
  }

  back() {
    this.router.navigate(['/' + this.returnPath]);
  }

  saveRecipe() {
    let temp = 0
    for(let i = 0; i < this.saveRecipeList.length; i++) {
      if (this.saveRecipeList[i].label === this.recipe.label) {
        temp += 1
      }
    }
    if(temp == 0) {
      this.saveRecipeList.push(this.recipe)
    }
    if (window.sessionStorage.getItem("userInfo") != null) {
      alert("Recipe saved");
      window.sessionStorage.setItem("saveRecipeList", JSON.stringify(this.saveRecipeList))
    } else {
      alert("Login/Signup to save recipe");
    }

  }

}
