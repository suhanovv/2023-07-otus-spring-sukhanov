import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {GenreRepository} from "../repositories/GenreRepository";
import {Genre} from "../domain/Genre";

export default function GenresListPage() {
    const genreRepo = new GenreRepository()
    const [genres, setGenres] = useState<Genre[]>([])

    useEffect(() => {
        genreRepo.getAll().then((data: Genre[]) => setGenres(data))
    }, [])

    return (
        <>
            <table>
                <thead>
                    <tr>
                        <th key="id">Id</th>
                        <th key="name">Название</th>
                        <th key="actions">Actions</th>
                    </tr>
                </thead>
                <tbody>
                {genres.map(genre => {
                    return (
                        <tr key={genre.id}>
                            <td>{genre.id}</td>
                            <td>{genre.name}</td>
                            <td>
                                <ul className="inlineList">
                                    <li key="edit"><Link to={`/genre/${genre.id}`}>Edit</Link></li>
                                </ul>
                            </td>
                        </tr>
                    )
                })}
                </tbody>
            </table>
        </>
    )
}