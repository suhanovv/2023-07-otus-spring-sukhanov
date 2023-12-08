import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {Author} from "../domain/Author";
import {AuthorRepository} from "../repositories/AuthorRepository";

export default function AuthorListPage() {
    const authorRepo = new AuthorRepository()
    const [authors, setAuthors] = useState<Author[]>([])

    useEffect(() => {
        authorRepo.getAll().then((data: Author[]) => setAuthors(data))
    }, [])

    return (
        <table>
            <thead>
                <tr>
                    <th key="id">Id</th>
                    <th key="firstName">Имя</th>
                    <th key="lastName">Фамилия</th>
                    <th key="actions">Actions</th>
                </tr>
            </thead>
            <tbody>
            {authors.map(author => {
                return (
                    <tr key={author.id}>
                        <td>{author.id}</td>
                        <td>{author.firstName}</td>
                        <td>{author.lastName}</td>
                        <td>
                            <ul className="inlineList">
                                <li key="edit"><Link to={`/author/${author.id}`}>Edit</Link></li>
                            </ul>
                        </td>
                    </tr>
                )
            })}
            </tbody>
        </table>
    )
}