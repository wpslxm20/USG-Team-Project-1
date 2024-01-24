import { BrowserRouter as Router } from "react-router-dom"
import Header from "./Header"
import Footer from "./Footer"
import React from "react"

const Layout = () => {
    return (
        <Router>
        <div>
            <Header />
            
            <main>
            </main>

            <Footer />
        </div>
        </Router>
    )
}

export default Layout