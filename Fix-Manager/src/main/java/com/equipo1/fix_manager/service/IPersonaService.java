package com.equipo1.fix_manager.service;

import com.equipo1.fix_manager.model.Persona;

import java.util.List;

public interface IPersonaService {


    public List<Persona> getPersonas();


    public void savePersona(Persona persona);

    public void deletePersona(Long id);


    public Persona findPersona(Long id);


    public void editPersona(long idOriginal , Long idNueva, String nuevoNOmbre,String nuevoApellido, int nuevaEdad);



}
