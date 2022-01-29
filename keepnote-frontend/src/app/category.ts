import {Note} from './note';
export class Category {

    categoryId: string;	
    categoryName: string;	
	categoryDescription: string;
    categoryCreatedBy: string;
    categoryCreationDate: Date;
    notes:Set<Note>;

    constructor(categoryName:string=""){
        this.categoryName = categoryName;
        this.categoryDescription = categoryName;
    }
}

