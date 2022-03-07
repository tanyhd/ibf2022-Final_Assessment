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
import { MealPlannerComponent } from './meal-planner/meal-planner.component';
import { SignupComponent } from './signup/signup.component';
import { UserService } from './user.services';
import { MessageResponseComponent } from './message-response/message-response.component';
import { BackgroundComponent } from './background/background.component';
import { ProfileComponent } from './profile/profile.component';

const  appRoutes: Routes = [
  { path: "", component: HomeComponent},
  { path: "listRecipe", component: ListRecipesComponent},
  { path: "recipesDetail", component: RecipesDetailsComponent},
  { path: "mealPlanner", component: MealPlannerComponent},
  { path: "login", component: LoginComponent},
  { path: "signup", component: SignupComponent},
  { path: "profile", component: ProfileComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ListRecipesComponent,
    RecipesDetailsComponent,
    LoginComponent,
    MealPlannerComponent,
    SignupComponent,
    MessageResponseComponent,
    BackgroundComponent,
    ProfileComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes, {scrollPositionRestoration: 'enabled'}),
    WebcamModule,
    FlexLayoutModule
  ],
  providers: [RecipesService, UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
