
export class UpdateBookDto {
    title: string;
    year: string;
    authorId: number;
    genreId: number;

    constructor(title: string, year: string, authorId: number, genreId: number) {
        this.title = title;
        this.year = year;
        this.authorId = authorId;
        this.genreId = genreId;
    }
}