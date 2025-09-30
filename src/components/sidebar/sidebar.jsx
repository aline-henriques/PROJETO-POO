import { Link } from "react-router-dom";
import styles from "../sidebar/sidebar.module.css"
import BotaoVoltar from "../BotãoVoltar/BotaoVoltar";

function Sidebar() {
    return(
        <aside className={styles.container}> 
            <BotaoVoltar />
        </aside>
    );
} 

export default Sidebar;