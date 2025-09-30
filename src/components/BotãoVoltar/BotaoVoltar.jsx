import styles from "../BotãoVoltar/BotaoVoltar.module.css";
import { useNavigate } from "react-router-dom";
import {ArrowBendUpLeft} from "@phosphor-icons/react"
import { useState } from "react";

function BotaoVoltar() {
    
    const navigate = useNavigate();
    const [hover, setHover] = useState(false);

    return(
        <button
            onClick={() => navigate(-1)}
            className={styles.botaoVoltar}
            onMouseEnter={() => setHover(true)}
            onMouseLeave={() => setHover(false)}
        >
            <ArrowBendUpLeft 
                className={styles.icone} 
                color={hover ? "var(--cor-texto-hover)" : "var(--cor-texto-destaque)"} 
            />
        </button>
        );
}

export default BotaoVoltar;