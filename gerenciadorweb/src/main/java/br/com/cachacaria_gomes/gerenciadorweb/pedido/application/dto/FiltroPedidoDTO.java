package br.com.cachacaria_gomes.gerenciadorweb.pedido.application.dto;

import br.com.cachacaria_gomes.gerenciadorweb.enums.StatusPedido;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FiltroPedidoDTO {
    private StatusPedido status;
    
    // Formatação para receber o parâmetro via URL
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataInicial;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataFinal;
    
    private String clienteId;
    private BigDecimal valorMinimo;
    private BigDecimal valorMaximo;
    private int pagina = 0;
    private int tamanho = 10;

    // Construtor padrão e Getters/Setters
    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }
    public LocalDateTime getDataInicial() { return dataInicial; }
    public void setDataInicial(LocalDateTime dataInicial) { this.dataInicial = dataInicial; }
    public LocalDateTime getDataFinal() { return dataFinal; }
    public void setDataFinal(LocalDateTime dataFinal) { this.dataFinal = dataFinal; }
    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }
    public BigDecimal getValorMinimo() { return valorMinimo; }
    public void setValorMinimo(BigDecimal valorMinimo) { this.valorMinimo = valorMinimo; }
    public BigDecimal getValorMaximo() { return valorMaximo; }
    public void setValorMaximo(BigDecimal valorMaximo) { this.valorMaximo = valorMaximo; }
    public int getPagina() { return pagina; }
    public void setPagina(int pagina) { this.pagina = pagina; }
    public int getTamanho() { return tamanho; }
    public void setTamanho(int tamanho) { this.tamanho = tamanho; }
}