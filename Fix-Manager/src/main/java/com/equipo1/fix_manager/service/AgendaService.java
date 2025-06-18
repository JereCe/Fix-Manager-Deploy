package com.equipo1.fix_manager.service;

import com.equipo1.fix_manager.repository.IAgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaService {

    @Autowired
    private IAgendaRepository agendaRepo;
}
