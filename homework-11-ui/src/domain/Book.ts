import {Author} from "./Author";
import {Genre} from "./Genre";

export class Book {
    id: string;
    title: string;
    year: string;
    author: Author;
    genre: Genre;

    constructor(id: string, title: string, year: string, author: Author, genre: Genre) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.author = author;
        this.genre = genre;
    }
}
