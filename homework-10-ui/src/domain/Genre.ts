export class Genre {
    id: number;
    name: string;

    constructor(id: number, name: string) {
        this.id = id;
        this.name = name;
    }

    setId(value: number) {
        this.id = value;
    }

    setName(value: string) {
        this.name = value;
    }
}