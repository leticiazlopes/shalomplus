package br.ifpb.shalomplus.controller;

import br.ifpb.shalomplus.model.Secretario;
import br.ifpb.shalomplus.repository.SecretarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/secretarios")
public class SecretarioController {

    @Autowired
    private SecretarioRepository secretarioRepository;

    private boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("tipoUsuario"));
    }

    @GetMapping
    public ModelAndView listar(HttpSession session) {
        if (!isAdmin(session))
            return new ModelAndView("redirect:/auth");

        ModelAndView mv = new ModelAndView("secretario/listar");
        mv.addObject("secretarios", secretarioRepository.findAll());
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novo(HttpSession session) {
        if (!isAdmin(session))
            return new ModelAndView("redirect:/auth");

        return new ModelAndView("secretario/form");
    }

    @PostMapping("/salvar")
    public String salvar(Secretario secretario, HttpSession session) {
        if (!isAdmin(session))
            return "redirect:/auth";

        secretarioRepository.save(secretario);
        return "redirect:/secretarios";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session))
            return new ModelAndView("redirect:/auth");

        ModelAndView mv = new ModelAndView("secretario/form");
        mv.addObject("secretario", secretarioRepository.findById(id).orElse(null));
        return mv;
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session))
            return "redirect:/auth";

        secretarioRepository.deleteById(id);
        return "redirect:/secretarios";
    }
}
