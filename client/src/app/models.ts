export interface Recipe{

  label: string;
  imageUrl: string;
  servings: number;
  totalTime: number;
  calories: number;
  caloriesPerServing: number;
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
