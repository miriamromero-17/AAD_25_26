package com.miriam.str_consumer.service;

import com.miriam.str_consumer.model.Mensaje;
import com.miriam.str_consumer.repository.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    public Mensaje guardarMensaje(String contenido) {
        Mensaje mensaje = new Mensaje(contenido);
        return mensajeRepository.save(mensaje);
    }
}