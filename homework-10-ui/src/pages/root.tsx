import React from "react";
import {Outlet} from "react-router-dom";
import Menu from "../components/Menu";


export default function Root() {

    return (
        <>
            <Menu />
            <Outlet />
        </>
    )
}