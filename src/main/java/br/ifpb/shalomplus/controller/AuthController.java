package br.ifpb.shalomplus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ifpb.shalomplus.model.Aluno;
import br.ifpb.shalomplus.model.Profissional;
import br.ifpb.shalomplus.model.Secretario;
import br.ifpb.shalomplus.repository.AlunoRepository;
import br.ifpb.shalomplus.repository.ProfissionalRepository;
import br.ifpb.shalomplus.repository.SecretarioRepository;
import jakarta.servlet.http.HttpSession;
import br.ifpb.shalomplus.repository.AdministradorRepository;
import br.ifpb.shalomplus.model.Administrador;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AlunoRepository alunoRepository;
    private final ProfissionalRepository profissionalRepository;
    private final SecretarioRepository secretarioRepository;
    private final AdministradorRepository administradorRepository;

    public AuthController(
            AlunoRepository alunoRepository,
            ProfissionalRepository profissionalRepository,
            SecretarioRepository secretarioRepository, AdministradorRepository administradorRepository) {
        this.alunoRepository = alunoRepository;
        this.profissionalRepository = profissionalRepository;
        this.secretarioRepository = secretarioRepository;
        this.administradorRepository = administradorRepository;
    }

    @GetMapping
    public ModelAndView getForm(ModelAndView model) {
        model.setViewName("auth/login");
        return model;
    }

    @PostMapping
    public ModelAndView valide(
            @RequestParam String login,
            @RequestParam String senha,
            HttpSession session,
            ModelAndView model,
            RedirectAttributes redirectAttts) {

        Administrador admin = administradorRepository.findByLogin(login);

        if (admin != null && admin.getSenha().equals(senha)) {
            session.setAttribute("usuario", admin);
            session.setAttribute("tipoUsuario", "ADMIN");
            return new ModelAndView("redirect:/home");
        }

        Aluno aluno = isAlunoValido(login, senha);
        if (aluno != null) {
            session.setAttribute("usuario", aluno);
            session.setAttribute("tipoUsuario", "ALUNO");
            model.setViewName("redirect:/home");
            return model;
        }

        // Psicólogo
        Profissional profissional = isProfissionalValido(login, senha);
        if (profissional != null) {
            session.setAttribute("usuario", profissional);
            session.setAttribute("tipoUsuario", "PROFISSIONAL");
            model.setViewName("redirect:/home");
            return model;
        }

        // Secretário
        Secretario secretario = isSecretarioValido(login, senha);
        if (secretario != null) {
            session.setAttribute("usuario", secretario);
            session.setAttribute("tipoUsuario", "SECRETARIO");
            model.setViewName("redirect:/home");
            return model;
        }

        // Login inválido
        redirectAttts.addFlashAttribute("mensagem", "Login e/ou senha inválidos!");
        model.setViewName("redirect:/auth");
        return model;
    }

    @GetMapping("/logout")
    public ModelAndView logout(ModelAndView mav, HttpSession session) {
        session.invalidate();
        mav.setViewName("redirect:/auth");
        return mav;
    }

    private Aluno isAlunoValido(String matricula, String senha) {
        Aluno alunoBD = alunoRepository.findByMatricula(matricula);
        if (alunoBD != null && alunoBD.getSenha().equals(senha)) {
            return alunoBD;
        }
        return null;
    }

    private Profissional isProfissionalValido(String cr, String senha) {
        Profissional profBD = profissionalRepository.findByCr(cr);
        if (profBD != null && profBD.getSenha().equals(senha)) {
            return profBD;
        }
        return null;
    }

    private Secretario isSecretarioValido(String cpf, String senha) {
        Secretario secretarioBD = secretarioRepository.findByCpf(cpf);
        if (secretarioBD != null && secretarioBD.getSenha().equals(senha)) {
            return secretarioBD;
        }
        return null;
    }
}
