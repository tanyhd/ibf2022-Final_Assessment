export interface Recipe{
  label: string;
  imageUrl: string;
  servings: number;
  totalTime: number;
  calories: number;
  caloriesPerServing: number;
  ingredients: Ingredients[]
  source: string
  url: string
}

export class User {
  constructor(
    public username: string,
    public name: string,
    public email: string,
    public password: string
  ) {}
}

export interface Message {
  message: string;
}

export interface Ingredients {
  quantity: number;
  measure: string;
  foodId: string;
  imageUrl: string;
  food: string;
}


export interface UserInfo {
  username: string;
  name: string;
  email: string;
  lineItem: LineItem[];
}

export interface LineItem {
  name: string;
  quantity: number;
}
