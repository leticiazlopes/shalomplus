package br.ifpb.shalomplus.controller;

import br.ifpb.shalomplus.model.Profissional;
import br.ifpb.shalomplus.repository.ProfissionalRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profissionais")
public class ProfissionalController {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    private boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("tipoUsuario"));
    }

    @GetMapping
    public ModelAndView listar(HttpSession session) {
        if (!isAdmin(session))
            return new ModelAndView("redirect:/auth");

        ModelAndView mv = new ModelAndView("profissional/listar");
        mv.addObject("profissionais", profissionalRepository.findAll());
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novo(HttpSession session) {
        if (!isAdmin(session))
            return new ModelAndView("redirect:/auth");

        return new ModelAndView("profissional/form");
    }

    @PostMapping("/salvar")
    public String salvar(Profissional profissional, HttpSession session) {
        if (!isAdmin(session))
            return "redirect:/auth";

        profissionalRepository.save(profissional);
        return "redirect:/profissionais";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session))
            return new ModelAndView("redirect:/auth");

        ModelAndView mv = new ModelAndView("profissional/form");
        mv.addObject("profissional", profissionalRepository.findById(id).orElse(null));
        return mv;
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session))
            return "redirect:/auth";

        profissionalRepository.deleteById(id);
        return "redirect:/profissionais";
    }
}
