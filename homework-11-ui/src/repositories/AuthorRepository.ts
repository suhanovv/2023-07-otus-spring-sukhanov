import {Author} from "../domain/Author";
import {UpdateAuthorDto} from "../dto/UpdateAuthorDto";

export class AuthorRepository {
    async getAll(): Promise<Author[]> {
        const response = await fetch("/api/author", {
            method: "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });
        return await response.json();
    }

    async get(id: string): Promise<Author> {
        const response = await fetch(`/api/author/${id}`, {
            method: "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });
        if (response.ok) {
            return await response.json();
        }

        return Promise.reject(response)
    }

    async update(authorId: string, author: UpdateAuthorDto): Promise<Author> {
        const response = await fetch(`/api/author/${authorId}`, {
            method: "PUT",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(author)
        });
        return await response.json();
    }
}
