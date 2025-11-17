import { Link } from "react-router-dom";
import styles from "../sidebar/sidebar.module.css";
import BotaoVoltar from "../BotãoVoltar/BotaoVoltar";
// 1. Importe os Ícones Necessários
import { 
    Warehouse,        
    Users,       
    Package,      
    Receipt       
} from "@phosphor-icons/react";

export default function Sidebar() {
    return (
        <aside className={styles.container}>
            <ul>
                {/* 2. Adicione os Ícones ao lado do texto */}
                <li>
                    <Link to="/GestaoEstoque">
                        <Warehouse size={20} weight="fill" />
                        <span>Gestão Estoque</span>
                    </Link>
                </li>
                <li>
                    <Link to="/GestaoClientes">
                        <Users size={20} weight="fill" />
                        <span>Gestão Clientes</span>
                    </Link>
                </li>
                <li>
                    <Link to="/GestaoProdutos">
                        <Package size={20} weight="fill" />
                        <span>Gestão Produtos</span>
                    </Link>
                </li>
                <li>
                    <Link to="/GestaoPedidos">
                        <Receipt size={20} weight="fill" />
                        <span>Gestão Pedidos</span>
                    </Link>
                </li>
            </ul>

            <div className={styles.BotaoVoltarContainer}>
                <BotaoVoltar />
            </div>
        </aside>
    );
}