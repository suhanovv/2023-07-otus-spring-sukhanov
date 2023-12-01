import React, {useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";

import {Author} from "../domain/Author";
import {AuthorRepository} from "../repositories/AuthorRepository";
import {UpdateAuthorDto} from "../dto/UpdateAuthorDto";
import TextInputControl from "../components/TextInputControl";


interface AuthorForm {
    firstName: string
    lastName: string
}

export default function AuthorFormPage() {
    const navigate = useNavigate();
    const {authorId} = useParams()
    const authorRepo = new AuthorRepository();

    const [author, setAuthor] = useState<Author>();
    const [formState, setFormState] = useState<AuthorForm>({firstName: "", lastName: ""})

    useEffect(() => {
        const loadData = async () => {
            try {
                const currentAuthor = await authorRepo.get(parseInt(authorId!));
                setAuthor(currentAuthor)
                setFormState({firstName: currentAuthor.firstName, lastName: currentAuthor.lastName})
            } catch (e) {
                navigate("/404")
            }
        }
        loadData()
    }, [])

    const handleSubmit = async (e: any) => {
        e.preventDefault();
        if (author) {
            await authorRepo.update(author.id, new UpdateAuthorDto(formState.firstName, formState.lastName))
            navigate("/authors")
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <TextInputControl fieldName="lastName" fieldLabel="Фамилия" defaultValue={formState.lastName} onChange={(e: any) => setFormState({...formState, 'lastName': e.target.value})} errorMsg={""} />
            <TextInputControl fieldName="firstname" fieldLabel="Имя" defaultValue={formState.firstName} onChange={(e: any) => setFormState({...formState, 'firstName': e.target.value})} errorMsg={""} />
            <ul className="inlineList">
                <li><input type="submit" value="Сохранить" /></li>
                <li><Link to={`/authors`} >Вернуться</Link></li>
            </ul>

        </form>
    )

}
