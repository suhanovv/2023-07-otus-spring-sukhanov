import {Genre} from "../domain/Genre";
import {UpdateGenreDto} from "../dto/UpdateGenreDto";

export class GenreRepository {
    async getAll(): Promise<Genre[]> {
        const response = await fetch("/api/genre", {
            method: "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });
        return await response.json();
    }

    async get(id: string): Promise<Genre> {
        const response = await fetch(`/api/genre/${id}`, {
            method: "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });
        if (response.ok) {
            return await response.json() as Genre;
        }

        return Promise.reject(response)
    }

    async update(genreId: string, genre: UpdateGenreDto): Promise<Genre> {
        const response = await fetch(`/api/genre/${genreId}`, {
            method: "PUT",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(genre)
        });
        if (response.ok)  {
            return response.json()
        }
        return Promise.reject(response)
    }
}
