import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {Book} from "../domain/Book";
import {BookRepository} from "../repositories/BookRepository";

export default function BookListPage() {
    const bookRepo = new BookRepository();
    const [books, setBooks] = useState<Book[]>([]);

    useEffect(() => {
        bookRepo.getAll().then((data: Book[]) => setBooks(data))
    }, [])

    const deleteHandler = (e:any, id: string) => {
        e.preventDefault()
        bookRepo.remove(id).then(() => {
            setBooks(books.filter(i => i.id !== id))
        })
    }

    return (
        <>
            <table>
                <thead>
                    <tr>
                        <th key="id">Id</th>
                        <th key="title">Название</th>
                        <th key="publishYear">Год</th>
                        <th key="author">Автор</th>
                        <th key="genre">Жанр</th>
                        <th key="actions">Actions</th>
                    </tr>
                </thead>
                <tbody>
                {Array.from(books.values()).map(book => {
                    return (
                        <tr key={book.id}>
                            <td>{book.id}</td>
                            <td>{book.title}</td>
                            <td>{book.year}</td>
                            <td>{book.author.lastName + ' ' + book.author.firstName}</td>
                            <td>{book.genre.name}</td>
                            <td>
                                <ul className="inlineList">
                                    <li key="edit"><Link to={`/book/${book.id}`}>Edit</Link></li>
                                    <li key="delete"><Link to={`/books`} onClick={(e => deleteHandler(e, book.id))}>Delete</Link></li>
                                </ul>
                            </td>
                        </tr>
                    )
                })}
                </tbody>
            </table>
            <Link to={`/book/new`}>Новая книга</Link>
        </>
    )
}