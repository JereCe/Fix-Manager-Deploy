package com.equipo1.fix_manager.controller;

import com.equipo1.fix_manager.model.Persona;
import com.equipo1.fix_manager.service.IPersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonaController {

    @Autowired
    private IPersonaService persoService;


    @GetMapping("/personas/traerlista")
    public List<Persona> getPersonas() {

        return persoService.getPersonas();
    }


    @GetMapping("/personas/traer/{id}")
    public Persona getPersona(@PathVariable Long id){
        return persoService.findPersona(id);
    }


    @PostMapping("/personas/crear")
    public String savePersona(@RequestBody Persona perso){
        persoService.savePersona(perso);
        return "La persona fue creada correctamente";

    }


    @DeleteMapping("/personas/borrar/{id}")
    public String deletePersona(@PathVariable Long id){
        persoService.deletePersona(id);
        return "La persona fue eliminada correctamente";
    }


    @PutMapping("/personas/editar/{id_original}")
    public Persona editPersona(@PathVariable Long id_original,
                               @RequestParam(required = false, name = "id") Long nuevaId,
                               @RequestParam(required = false,name = "nombre")String nuevoNombre,
                               @RequestParam(required = false,name = "apellido")String nuevoApellido,
                               @RequestParam(required = false,name = "edad")int nuevaEdad){

        persoService.editPersona(id_original,nuevaId,nuevoNombre,nuevoApellido,nuevaEdad);


        Persona persona = persoService.findPersona(nuevaId);

        return persona;

    }














}
