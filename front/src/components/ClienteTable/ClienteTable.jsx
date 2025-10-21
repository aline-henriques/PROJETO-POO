import React from "react";
import styles from "../TabelaProdutos/ProdutoTable.module.css";

export default function ClienteTable({ clientes, onEdit, onDelete }) {

    const formatarEndereco = (endereco) => {
        if (!endereco || !endereco.rua) return "Endereço não cadastrado";
        const { rua, numero, bairro, cidade, estado } = endereco;
        return `${rua}, ${numero || 'S/N'} - ${bairro || ''} (${cidade}/${estado})`;
    };

    return (
        <div>
            <table className={styles.tabela}>
                <thead>
                    <tr className={styles.theadTr}>
                        <th className={styles.th}>Nome</th>
                        <th className={styles.th}>Email</th>
                        <th className={styles.th}>CPF</th>
                        <th className={styles.th}>Telefone</th>
                        <th className={styles.th}>Endereço Completo</th>
                        <th className={styles.th}>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {clientes.map((c) => (
                        <tr key={c.id} className={styles.tr}>
                            <td className={styles.td}>{c.nome}</td>
                            <td className={styles.td}>{c.email}</td>
                            <td className={styles.td}>{c.cpf}</td>
                            <td className={styles.td}>{c.telefone}</td>
                            <td className={styles.td}>{formatarEndereco(c.endereco)}</td>

                            <td className={styles.td}>
                                <button
                                    className={styles.botaoEditar}
                                    onClick={() => onEdit(c)}
                                >
                                    Editar
                                </button>
                                <button
                                    className={styles.botaoExcluir}
                                    onClick={() => onDelete(c.id)}
                                >
                                    Excluir
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}