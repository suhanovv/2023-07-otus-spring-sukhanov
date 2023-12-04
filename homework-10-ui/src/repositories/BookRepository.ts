import {Book} from "../domain/Book";
import {UpdateBookDto} from "../dto/UpdateBookDto";
import {CreateBookDto} from "../dto/CreateBookDto";

export class BookRepository {
    async getAll(): Promise<Book[]> {
        const response = await fetch("/api/book", {
            method: "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });
        return await response.json() as Book[];
    }

    async get(id: number): Promise<Book> {
        const response = await fetch(`/api/book/${id}`, {
            method: "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });
        if (response.ok) {
            return await response.json() as Book;
        }

        return Promise.reject(response)
    }

    async create(book: CreateBookDto) {
        const response = await fetch("/api/book", {
            method: "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(book)
        });
        if (response.ok)  {

            return await response.json()
        }
        return Promise.reject(response)
    }

    async remove(id: number): Promise<void> {
        await fetch(`/api/book/${id}`, {
            method: "DELETE",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        })
        return;
    }

    async update(bookId: number, book: UpdateBookDto): Promise<Book> {
        const response = await fetch(`/api/book/${bookId}`, {
            method: "PUT",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(book)
        });
        if (response.ok)  {
            return response.json()
        }
        return Promise.reject(response)
    }
}
