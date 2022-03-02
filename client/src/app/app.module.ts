import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ListRecipesComponent } from './list-recipes/list-recipes.component';
import { RecipesDetailsComponent } from './recipes-details/recipes-details.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import {WebcamModule} from 'ngx-webcam';
import { HomeComponent } from './home/home.component';
import { RecipesService } from './recipes.service';
import { FlexLayoutModule } from '@angular/flex-layout';
import { LoginComponent } from './login/login.component';
import { ImageSearchComponent } from './image-search/image-search.component';
import { MealPlannerComponent } from './meal-planner/meal-planner.component';


const  appRoutes: Routes = [
  { path: "", component: HomeComponent},
  { path: "listRecipe", component: ListRecipesComponent},
  { path: "recipesDetail", component: RecipesDetailsComponent},
  { path: "imageSearch", component: ImageSearchComponent},
  { path: "mealPlanner", component: MealPlannerComponent},
  { path: "login", component: LoginComponent},
]

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ListRecipesComponent,
    RecipesDetailsComponent,
    LoginComponent,
    ImageSearchComponent,
    MealPlannerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes),
    WebcamModule,
    FlexLayoutModule
  ],
  providers: [RecipesService],
  bootstrap: [AppComponent]
})
export class AppModule { }
