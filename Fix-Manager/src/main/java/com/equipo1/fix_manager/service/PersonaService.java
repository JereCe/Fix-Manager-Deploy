package com.equipo1.fix_manager.service;


import com.equipo1.fix_manager.model.Persona;
import com.equipo1.fix_manager.repository.IPersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PersonaService implements IPersonaService {

    @Autowired
    private IPersonaRepository persoRepo;


    @Override
    public List<Persona> getPersonas() {
        List<Persona> listaPersonas = persoRepo.findAll();

        return listaPersonas;
    }

    @Override
    public void savePersona(Persona persona) {

        persoRepo.save(persona);

    }

    @Override
    public void deletePersona(Long id) {

        persoRepo.deleteById(id);

    }

    @Override
    public Persona findPersona(Long id) {
        Persona persona = persoRepo.findById(id).orElse(null);
        return persona;
    }

    @Override
    public void editPersona(long idOriginal, Long idNueva, String nuevoNOmbre, String nuevoApellido, int nuevaEdad) {
        Persona persona = this.findPersona(idOriginal);
        //persona.setId(idNueva);
        persona.setApellido(nuevoApellido);
        persona.setNombre(nuevoNOmbre);
        persona.setEdad(nuevaEdad);

        this.savePersona(persona);
    }
}
