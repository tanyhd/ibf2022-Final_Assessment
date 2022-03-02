import { HttpClient } from '@angular/common/http';
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

  constructor(private router: Router, private recipesService: RecipesService, private http: HttpClient) { }

  searchTerm!: string;
  recipesList: Recipe[] = []


  ngOnInit(): void {

  }

  searchRecipe(searchTerm: string) {
    this.recipesList = []
    this.recipesService.getRecipe(searchTerm)
      .then(data => {
        data.forEach(element => {
          this.recipesList.push(element as Recipe)
        })
      });
    console.log(this.recipesList.length)
  }
}
