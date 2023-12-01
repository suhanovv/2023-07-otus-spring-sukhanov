import {Author} from "./Author";
import {Genre} from "./Genre";

export class Book {
    id: number;
    title: string;
    year: string;
    author: Author;
    genre: Genre;

    constructor(id: number, title: string, year: string, author: Author, genre: Genre) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.author = author;
        this.genre = genre;
    }
}

export const EmptyBook = new Book(0, "", "",
    new Author(0, "", ""),
    new Genre(0, ""));