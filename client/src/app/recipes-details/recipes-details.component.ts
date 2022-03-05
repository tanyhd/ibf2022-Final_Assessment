import { Component, OnInit } from '@angular/core';
import { Recipe } from '../models';

@Component({
  selector: 'app-recipes-details',
  templateUrl: './recipes-details.component.html',
  styleUrls: ['./recipes-details.component.css']
})
export class RecipesDetailsComponent implements OnInit {

  recipe!: Recipe
  numberOfIngredients: number = 0

  constructor() { }

  ngOnInit(): void {
    this.recipe = history.state.data.recipe
    this.numberOfIngredients = this.recipe.ingredients.length
    console.log(this.recipe)
    console.log(this.recipe.label)
  }



}
