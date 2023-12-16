export class Author {
    id: string;
    firstName: string;
    lastName: string;

    constructor(id: string, firstName: string, lastName: string) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    setId(value: string) {
        this.id = value;
    }

    setFirstName(value: string) {
        this.firstName = value;
    }

    setLastName(value: string) {
        this.lastName = value;
    }


}