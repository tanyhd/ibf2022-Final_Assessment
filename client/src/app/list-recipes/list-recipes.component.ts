import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Recipe } from '../models';
import { RecipesService } from '../recipes.service';

@Component({
  selector: 'app-list-recipes',
  templateUrl: './list-recipes.component.html',
  styleUrls: ['./list-recipes.component.css']
})
export class ListRecipesComponent implements OnInit {

  constructor(private router: Router, private recipesService: RecipesService) { }

  searchTerm!: string;
  recipesList: Recipe[] = []


  ngOnInit(): void {

  }

  searchRecipe(searchTerm: string) {
    this.recipesService.getRecipe(searchTerm)
      .then(data => {
        data.forEach(element => {
          this.recipesList.push(element as Recipe)
        })
      });
  }


}
