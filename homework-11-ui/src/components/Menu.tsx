import {Link} from "react-router-dom";
import React from "react";

export default function Menu() {
    return (
        <div>
            <ul className="inlineList">
                <li key="genre"><Link to={`/genres`}>Жанры</Link></li>
                <li key="author"><Link to={`/authors`}>Авторы</Link></li>
                <li key="book"><Link to={`/books`}>Книги</Link></li>
            </ul>
        </div>
    )
}