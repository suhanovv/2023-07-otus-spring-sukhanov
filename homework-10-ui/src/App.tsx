import React from 'react';
import './App.css';
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import Root from "./pages/root";
import BookFormPage from "./pages/BookFormPage";
import AuthorFormPage from './pages/AuthorFormPage';
import BookListPage from './pages/BookListPage';
import AuthorListPage from "./pages/AuthorListPage";
import GenreFormPage from './pages/GenreFormPage';
import GenresListPage from './pages/GenresListPage';
import NotFoundPage from "./pages/NotFoundpage";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Root />,
        children: [
            {
                path: "authors",
                element: <AuthorListPage />,
            },
            {
                path: "author/:authorId",
                element: <AuthorFormPage />
            },
            {
                path: "genres",
                element: <GenresListPage />
            },
            {
                path: "genre/:genreId",
                element: <GenreFormPage />
            },
            {
                path: "books",
                element: <BookListPage />
            },
            {
                path: "book/:bookId",
                element: <BookFormPage />
            },
            {
                path: "book/new",
                element: <BookFormPage />
            },
            {
                path: "404",
                element: <NotFoundPage />
            }
        ]
    },
])

function App() {
  return (
      <RouterProvider router={router} />
  );
}

export default App;
