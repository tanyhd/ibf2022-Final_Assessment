import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Recipe } from '../models';
import { RecipesService } from '../recipes.service';

@Component({
  selector: 'app-meal-planner',
  templateUrl: './meal-planner.component.html',
  styleUrls: ['./meal-planner.component.css']
})
export class MealPlannerComponent implements OnInit {

  form! : FormGroup;
  caloriesBreakfast: string = ""
  caloriesSnack: string = ""
  caloriesLunch: string = ""
  caloriesTeatime: string = ""
  caloriesDinner: string = ""
  mealTypeValue:string = ""

  recipesListBreakfast: Recipe[] = []
  recipesListSnack: Recipe[] = []
  recipesListLunch: Recipe[] = []
  recipesListTeatime: Recipe[] = []
  recipesListDinner: Recipe[] = []

  day: number = 1;

  constructor(private router: Router, private recipesService: RecipesService, private http: HttpClient, private fb:FormBuilder) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      diet: this.fb.control(""),
      mealType: this.fb.control(""),
      calories: this.fb.control("")
    })

    try{
      this.recipesListBreakfast = JSON.parse(window.sessionStorage.getItem("recipesBreakfastList") || "")
    } catch(e) {}
    try {
      this.recipesListSnack = JSON.parse(window.sessionStorage.getItem("recipesSnackList") || "")
    } catch(e) {}
    try {
      this.recipesListLunch = JSON.parse(window.sessionStorage.getItem("recipesLunchList") || "")
    } catch(e) {}
    try {
      this.recipesListTeatime = JSON.parse(window.sessionStorage.getItem("recipesTeatimeList") || "")
    } catch(e) {}
    try {
      this.recipesListDinner = JSON.parse(window.sessionStorage.getItem("recipesDinnerList") || "")
    } catch(e) {}
  }

  monday(){this.day=1}
  tuesday(){this.day=2}
  wednesday(){this.day=3}
  thursday(){this.day=4}
  friday(){this.day=5}
  saturday(){this.day=6}
  sunday(){this.day=7}

  async searchRecipeFromRequirement() {
    this.recipesListBreakfast = []
    this.recipesListSnack = []
    this.recipesListLunch = []
    this.recipesListTeatime = []
    this.recipesListDinner = []

    console.log(this.form.value.diet)
    console.log(this.form.value.mealType)

    this.caloriesForEachMeal(this.form.value.mealType)

    let requirement = this.form.value.diet + " Breakfast " + this.caloriesBreakfast;
    await this.recipesService.getRecipeFromRequirement(requirement)
        .then(data => {
          data.forEach(element => {
            this.recipesListBreakfast.push(element as Recipe)
          })
        })
    window.sessionStorage.setItem("recipesBreakfastList", JSON.stringify(this.recipesListBreakfast))
    requirement = this.form.value.diet + " Dinner " + this.caloriesDinner;
    await this.recipesService.getRecipeFromRequirement(requirement)
      .then(data => {
        data.forEach(element => {
          this.recipesListDinner.push(element as Recipe)
        })
      })
    window.sessionStorage.setItem("recipesDinnerList", JSON.stringify(this.recipesListDinner))
    if(this.form.value.mealType == 3) {
      requirement = this.form.value.diet + " Lunch " + this.caloriesLunch;
     await this.recipesService.getRecipeFromRequirement(requirement)
        .then(data => {
          data.forEach(element => {
            this.recipesListLunch.push(element as Recipe)
          })
        })
      window.sessionStorage.setItem("recipesLunchList", JSON.stringify(this.recipesListLunch))
    } else if (this.form.value.mealType == 5) {
      requirement = this.form.value.diet + " Snack " + this.caloriesSnack;
      await this.recipesService.getRecipeFromRequirement(requirement)
        .then(data => {
          data.forEach(element => {
            this.recipesListSnack.push(element as Recipe)
          })
        })
      window.sessionStorage.setItem("recipesSnackList", JSON.stringify(this.recipesListSnack))
      requirement = this.form.value.diet + " Teatime " + this.caloriesTeatime;
      await this.recipesService.getRecipeFromRequirement(requirement)
        .then(data => {
          data.forEach(element => {
            this.recipesListTeatime.push(element as Recipe)
          })
        })
        window.sessionStorage.setItem("recipesTeatimeList", JSON.stringify(this.recipesListTeatime))
        requirement = this.form.value.diet + " Lunch " + this.caloriesLunch;
        await this.recipesService.getRecipeFromRequirement(requirement)
          .then(data => {
            data.forEach(element => {
              this.recipesListLunch.push(element as Recipe)
            })
          })
        window.sessionStorage.setItem("recipesLunchList", JSON.stringify(this.recipesListLunch))
    }

  }

  caloriesForEachMeal(number: number) {
    if (number == 3) {
      this.caloriesBreakfast = "300-400"
      this.caloriesLunch = "500-700"
      this.caloriesDinner = "500-700"
    } else if (number == 5) {
      this.caloriesBreakfast = "300-400"
      this.caloriesSnack = "300-400"
      this.caloriesLunch = "300-400"
      this.caloriesTeatime = "300-400"
      this.caloriesDinner = "300-400"
    } else if (number == 2) {
      this.caloriesBreakfast = "500-700"
      this.caloriesDinner = "500-700"
    }
  }

  tempRecipe(recipe: Recipe) {
    window.sessionStorage.setItem("tempRecipe", JSON.stringify(recipe))
  }

}
