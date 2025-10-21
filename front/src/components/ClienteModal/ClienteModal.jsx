import React, { useState, useEffect } from "react";
import styles from "../ProdutoModal/ProdutoModal.module.css"

function ClienteModal({ cliente, onClose, onSave }) {
    const [form, setForm] = useState({
        nome: "",
        email: "",
        telefone: "",
        cpf: "",
        endereco: {
            rua: "",
            numero: "",
            bairro: "",
            cidade: "",
            estado: "",
            cep: "",
        },
    });

    useEffect(() => {
        if (cliente) {
            setForm({
                ...cliente,
                endereco: cliente.endereco || {}
            });
        }
    }, [cliente]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm({ ...form, [name]: value });
    };

    const handleEnderecoChange = (e) => {
        const { name, value } = e.target;
        setForm({
            ...form,
            endereco: {
                ...form.endereco,
                [name]: value,
            }
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();


        const url = cliente?.id
            ? `http://localhost:8080/clientes/${cliente.id}`
            : "http://localhost:8080/clientes/";
        const method = cliente?.id ? "PUT" : "POST";

        const payload = form;

        fetch(url, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload),
        })
            .then((res) => {
                if (res.ok) {
                    onSave(payload);
                } else {
                    res.text().then((text) => console.error("Erro:", text));
                    alert("Erro ao salvar cliente");
                }
            })
            .catch((err) => console.error("Erro:", err));
    };

    return (
        <div className={styles.overlay}>
            <div className={styles.modal}>
                <h3 className={styles.titulo}>
                    {cliente?.id ? "Editar Cliente" : "Novo Cliente"}
                </h3>

                <form onSubmit={handleSubmit} className={styles.form}>
                    {/* CAMPOS PESSOAIS */}
                    <input type="text" name="nome" placeholder="Nome" value={form.nome} onChange={handleChange} className={styles.input} required />
                    <input type="email" name="email" placeholder="Email" value={form.email} onChange={handleChange} className={styles.input} required />
                    <input type="text" name="telefone" placeholder="Telefone" value={form.telefone} onChange={handleChange} className={styles.input} />
                    <input type="text" name="cpf" placeholder="CPF" value={form.cpf} onChange={handleChange} className={styles.input} required />

                    <h4 className={styles.subtitulo}>Endereço</h4>
                    {/* CAMPOS ENDEREÇO */}
                    <input type="text" name="rua" placeholder="Rua" value={form.endereco.rua || ""} onChange={handleEnderecoChange} className={styles.input} />
                    <input type="text" name="numero" placeholder="Número" value={form.endereco.numero || ""} onChange={handleEnderecoChange} className={styles.input} />
                    <input type="text" name="bairro" placeholder="Bairro" value={form.endereco.bairro || ""} onChange={handleEnderecoChange} className={styles.input} />
                    <input type="text" name="cidade" placeholder="Cidade" value={form.endereco.cidade || ""} onChange={handleEnderecoChange} className={styles.input} required />
                    <input type="text" name="estado" placeholder="Estado (UF)" value={form.endereco.estado || ""} onChange={handleEnderecoChange} className={styles.input} required />
                    <input type="text" name="cep" placeholder="CEP" value={form.endereco.cep || ""} onChange={handleEnderecoChange} className={styles.input} />

                    <div className={styles.botoes}>
                        <button type="button" className={styles.botaoCancelar} onClick={onClose}>
                            Cancelar
                        </button>
                        <button type="submit" className={styles.botaoSalvar}>
                            Salvar
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default ClienteModal;