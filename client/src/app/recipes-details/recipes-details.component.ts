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

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.returnPath = history.state.data
    console.log(this.returnPath)
    this.recipe = JSON.parse(window.sessionStorage.getItem("tempRecipe") || "")
    this.numberOfIngredients = this.recipe.ingredients.length
    console.log(this.recipe)
    console.log(this.recipe.label)
  }

  back() {
    this.router.navigate(['/' + this.returnPath]);
  }



}
