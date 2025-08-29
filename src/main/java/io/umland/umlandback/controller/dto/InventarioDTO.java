package io.umland.umlandback.controller.dto;


public class InventarioDTO {
    private Long id;
    private Long usuarioId;
    private Long itemId;
    private Integer quantidade;

    public InventarioDTO() {} // Jackson precisa do construtor vazio

    public InventarioDTO(Long id, Long usuarioId, Long itemId, Integer quantidade) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.itemId = itemId;
        this.quantidade = quantidade;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}
