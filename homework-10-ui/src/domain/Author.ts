export class Author {
    id: number;
    firstName: string;
    lastName: string;

    constructor(id: number, firstName: string, lastName: string) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    setId(value: number) {
        this.id = value;
    }

    setFirstName(value: string) {
        this.firstName = value;
    }

    setLastName(value: string) {
        this.lastName = value;
    }


}