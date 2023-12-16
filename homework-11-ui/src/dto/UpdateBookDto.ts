
export class UpdateBookDto {
    title: string;
    year: string;
    authorId: string;
    genreId: string;

    constructor(title: string, year: string, authorId: string, genreId: string) {
        this.title = title;
        this.year = year;
        this.authorId = authorId;
        this.genreId = genreId;
    }
}