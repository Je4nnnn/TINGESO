package com.example.m7_usuarios.service;

import com.example.m7_usuarios.dto.LoginRequest;
import com.example.m7_usuarios.dto.LoginResponse;
import com.example.m7_usuarios.entity.Usuario;
import com.example.m7_usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario registrar(Usuario u) {
        if (repository.findByUsername(u.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username ya existe");
        }
        if (u.getRol() == null) u.setRol("EMPLEADO");
        return repository.save(u);
    }

    public LoginResponse login(LoginRequest req) {
        Optional<Usuario> opt = repository.findByUsername(req.getUsername());
        if (opt.isPresent() && opt.get().getPassword().equals(req.getPassword())) {
            Usuario u = opt.get();
            // Generating dummy token as a placeholder
            String token = "dummy-token-" + u.getId() + "-" + System.currentTimeMillis();
            return new LoginResponse(token, u.getRol(), u.getId());
        }
        throw new IllegalArgumentException("Credenciales inválidas");
    }
}
