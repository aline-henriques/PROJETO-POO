import React from 'react';
import styles from './HomePage.module.css';
import Navbar from '../../components/navbar/navbar';

export default function HomePage() {


    const produtosOferta = [1, 2];
    const produtosMaisVendidos = [3, 4];


    const ProductCard = ({ title }) => (
        <div className={styles.productCard}>
            <div className={styles.imagePlaceholder}>🖼️</div>
            <p className={styles.productName}>NOME</p>
            <p className={styles.productPrice}>PREÇO</p>
        </div>
    );

    return (
        
        <div className={styles.pageContainer}>


            <Navbar />
            <div className={styles.mainCarousel}>
                <div className={styles.imagePlaceholderLarge}>🖼️</div>
                <div className={styles.carouselDots}>
                    <span className={styles.dot}></span>
                    <span className={`${styles.dot} ${styles.activeDot}`}></span>
                    <span className={styles.dot}></span>
                    <span className={styles.dot}></span>
                </div>
            </div>

            <section className={styles.productSection}>
                <h2 className={styles.sectionTitle}>OFERTA RELÂMPAGO</h2>
                <div className={styles.productList}>
                    <button className={styles.arrow}>{'<'}</button>
                    {produtosOferta.map((id) => (
                        <ProductCard key={id} title={`Oferta ${id}`} />
                    ))}
                    <button className={styles.arrow}>{'>'}</button>
                </div>
            </section>

            <section className={styles.productSection}>
                <h2 className={styles.sectionTitle}>OS MAIS VENDIDOS!</h2>
                <div className={styles.productList}>
                    <button className={styles.arrow}>{'<'}</button>
                    {produtosMaisVendidos.map((id) => (
                        <ProductCard key={id} title={`Vendidos ${id}`} />
                    ))}
                    <button className={styles.arrow}>{'>'}</button>
                </div>
                <div className={styles.carouselDots}>
                    <span className={`${styles.dot} ${styles.activeDot}`}></span>
                    <span className={styles.dot}></span>
                    <span className={styles.dot}></span>
                </div>
            </section>

        </div>
    );
}