import React, {useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";

import {Genre} from "../domain/Genre";
import {GenreRepository} from "../repositories/GenreRepository";
import {UpdateGenreDto} from "../dto/UpdateGenreDto";
import TextInputControl from "../components/TextInputControl";

interface GenreForm {
    name: string
}

export default function GenreFormPage() {
    const navigate = useNavigate();
    const genreRepo = new GenreRepository();
    const {genreId} = useParams()

    const [genre, setGenre] = useState<Genre>()
    const [formState, setFormState] = useState<GenreForm>({name: ""});

    useEffect(() => {
        const loadData = async () => {
            try {
                const currentGenre = await genreRepo.get(genreId!);
                setGenre(currentGenre);
                setFormState({name: currentGenre.name})
            } catch (e) {
                return navigate("/404")
            }

        }
        loadData()
    }, [])

    const handleSubmit = async (e: any) => {
        e.preventDefault();
        if (genre !== undefined) {
            console.log(genre, formState)
            await genreRepo.update(genre.id, new UpdateGenreDto(formState.name))
            navigate("/genres")
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <TextInputControl fieldName="name" fieldLabel="Название" defaultValue={formState.name} onChange={(e: any) => setFormState({...formState, 'name': e.target.value})}  errorMsg={""}/>
            <ul className="inlineList">
                <li><input type="submit" value="Сохранить" /></li>
                <li><Link to={`/genres`} >Вернуться</Link></li>
            </ul>

        </form>
    )

}
