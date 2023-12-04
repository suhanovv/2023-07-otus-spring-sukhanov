import React, {useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";

import {Book} from "../domain/Book";
import {Genre} from "../domain/Genre";
import {Author} from "../domain/Author";
import {BookRepository} from "../repositories/BookRepository";
import {AuthorRepository} from "../repositories/AuthorRepository";
import {GenreRepository} from "../repositories/GenreRepository";
import {UpdateBookDto} from "../dto/UpdateBookDto";
import TextInputControl from "../components/TextInputControl";
import SelectControl from "../components/SelectControl";
import {CreateBookDto} from "../dto/CreateBookDto";

interface BookForm {
    title: string
    year: string
    authorId: number
    genreId: number
}

interface FormErrors {
    title: string
    year: string
    authorId: string
    genreId: string
}

export default function BookFormPage() {
    const navigate = useNavigate();

    const bookRepo = new BookRepository();
    const authorRepo = new AuthorRepository();
    const genreRepo = new GenreRepository();

    const {bookId} = useParams()

    const [authors, setAuthors] = useState<Author[]>([])
    const [genres, setGenres] = useState<Genre[]>([])
    const [book, setBook] = useState<Book>();

    const [formState, setFormState] = useState<BookForm>(
        {title: "", year: "", authorId: 0, genreId: 0}
    )
    const [formErrors, setFormErrors] = useState<FormErrors>(
        {title: "", year: "", authorId: "", genreId: ""}
    )

    const [globalError, setGlobalError] = useState("")

    useEffect(() => {
        const loadData = async () => {
            const authorsList = await authorRepo.getAll()
            const genresList = await genreRepo.getAll()
            setAuthors(authorsList)
            setGenres(genresList)
            let currentBook = undefined
            try {
                currentBook = bookId ? await bookRepo.get(parseInt(bookId)) : undefined
            } catch (e) {
                return navigate("/404")
            }

            if (currentBook) {
                setBook(currentBook)
                setFormState({
                    title: currentBook.title,
                    year: currentBook.year,
                    authorId: currentBook.author.id,
                    genreId: currentBook.genre.id})
            } else {
                let firstGenre = genresList.at(0)
                let firstAuthor = authorsList.at(0)
                if (firstAuthor && firstGenre) {
                    setFormState({...formState, genreId: firstGenre.id, authorId: firstAuthor.id})
                }

            }
        }
        loadData()
    }, []);

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (book !== undefined) {
            await bookRepo.update(
                book.id, new UpdateBookDto(
                    formState.title,
                    formState.year,
                    formState.authorId,
                    formState.genreId)
            )
            navigate("/books")
        } else {
            try {
                await bookRepo.create(
                    new CreateBookDto(
                        formState.title,
                        formState.year,
                        formState.authorId,
                        formState.genreId)
                )
                navigate("/books")
            } catch (e: any) {
                if (e.status === 400) {
                    setFormErrors(await e.json())
                } else {
                    setGlobalError("Ошибка сервера")
                }
            }
        }
    }

    return (
        <>
            {globalError ? <h1 className={"errors"}>{globalError}</h1> : null}
            <form onSubmit={handleSubmit}>
                <TextInputControl fieldName="title" fieldLabel="Название" defaultValue={formState.title} errorMsg={formErrors.title} onChange={(e: any) => setFormState({...formState, 'title': e.target.value})} />
                <TextInputControl fieldName="year" fieldLabel="Год" defaultValue={formState.year} errorMsg={formErrors.year} onChange={(e: any) => setFormState({...formState, 'year': e.target.value})} />
                <SelectControl fieldName="authorId" fieldLabel="Автор" defaultValue={formState.authorId} data={authors} optionText={(i: Author) => i.lastName + ' ' + i.firstName } onChange={(e: any) => setFormState({...formState, 'authorId': parseInt(e.target.value)})} />
                <SelectControl fieldName="genreId" fieldLabel="Жанр" defaultValue={formState.genreId} data={genres} optionText={(i: Genre) => i.name } onChange={(e: any) => setFormState({...formState, 'genreId': parseInt(e.target.value)})} />
                <ul className="inlineList">
                    <li><input type="submit" value="Сохранить" /></li>
                    <li><Link to={`/books`} >Вернуться</Link></li>
                </ul>
            </form>
        </>
    )
}