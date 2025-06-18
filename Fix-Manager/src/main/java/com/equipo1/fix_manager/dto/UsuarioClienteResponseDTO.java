package com.equipo1.fix_manager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UsuarioClienteResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String documento;



    public UsuarioClienteResponseDTO(Long id, String nombre, String apellido, String email, String documento) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.documento = documento;
    }


}