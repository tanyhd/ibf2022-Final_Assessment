import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { lastValueFrom } from "rxjs";
import { Recipe } from "./models";

@Injectable()
export class RecipesService {

  constructor(private http: HttpClient) {}

  getRecipe(searchTerm: string) {
    return(lastValueFrom(
      this.http.get<Recipe[]>(`http://localhost:8080/api/recipes/${searchTerm}`)
    ))
  }

  getRecipeFromRequirement(requirement: string) {
    return(lastValueFrom(
      this.http.get<Recipe[]>(`http://localhost:8080/api/recipes/meal/${requirement}`)
    ))
  }

  postImageToSearch_Food(formData: FormData) {
    return(lastValueFrom(
      this.http.post<any>("http://localhost:8080/api/getFoodLabel", formData)
    ))
  }
}
