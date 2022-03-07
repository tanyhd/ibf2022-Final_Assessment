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
  base64textString = "";


  ngOnInit(): void {
    if (this.recipesList.length == 0) {
      try {
        this.recipesList = JSON.parse(window.sessionStorage.getItem("recipesList") || "")
      } catch(e) {}
    }
  }

  async searchRecipe(searchTerm: string) {
    this.recipesList = []
    await this.recipesService.getRecipe(searchTerm)
      .then(data => {
        data.forEach(element => {
          this.recipesList.push(element as Recipe)
        })
      }).catch(error => {
        alert("Unable to find recipe, please search again")
        this.ngOnInit()
      })
    window.sessionStorage.setItem("recipesList", JSON.stringify(this.recipesList))
    console.log(this.recipesList.length)
  }

  onFileSelected(event: any){
    var files = event.target.files;
    var file = files[0];

  if (files && file) {
      var reader = new FileReader();
      reader.onload =this.handleFile.bind(this);
      reader.readAsBinaryString(file);
    }
  }

  handleFile(event: any) {
    var binaryString = event.target.result;
    this.base64textString= "data:image/jpeg;base64,"+ btoa(binaryString);
  }

  async fileUpload() {
    const formData = new FormData();
    formData.set('image', this.dataURItoBlob(this.base64textString))
    this.recipesList = []
    await this.recipesService.postImageToSearch_Food(formData)
      .then(data => {
        data.forEach((element: Recipe) => {
          this.recipesList.push(element as Recipe)
        })
      }).catch(error => {
        alert("Unable to find recipe, please search again")
        this.ngOnInit()
      })
    window.sessionStorage.setItem("recipesList", JSON.stringify(this.recipesList))
    console.log(this.recipesList.length)
  }

  dataURItoBlob(dataURI: string) {
    // convert base64 to raw binary data held in a string
    // doesn't handle URLEncoded DataURIs - see SO answer #6850276 for code that does this
    var byteString = atob(dataURI.split(',')[1]);
    // separate out the mime component
    var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]
    // write the bytes of the string to an ArrayBuffer
    var ab = new ArrayBuffer(byteString.length);
    // create a view into the buffer
    var ia = new Uint8Array(ab);
    // set the bytes of the buffer to the correct values
    for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }
    // write the ArrayBuffer to a blob, and you're done
    var blob = new Blob([ab], {type: mimeString});
    return blob;
  }

  tempRecipe(recipe: Recipe) {
    window.sessionStorage.setItem("tempRecipe", JSON.stringify(recipe))
  }
}
