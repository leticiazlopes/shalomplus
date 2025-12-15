package br.ifpb.shalomplus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ifpb.shalomplus.model.Aluno;
import br.ifpb.shalomplus.repository.AlunoRepository;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/aluno") // singular, padronizando a rota
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    // Verifica se o usuário é admin
    private boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("tipoUsuario"));
    }

    // Listar alunos
    @GetMapping("/listar")
    public ModelAndView listar(HttpSession session) {
        if (!isAdmin(session)) return new ModelAndView("redirect:/auth");

        ModelAndView mv = new ModelAndView("aluno/listar"); // Template: aluno/listar.html
        mv.addObject("alunos", alunoRepository.findAll());
        return mv;
    }

    // Formulário novo aluno
    @GetMapping("/novo")
    public ModelAndView novo(HttpSession session) {
        if (!isAdmin(session)) return new ModelAndView("redirect:/auth");

        ModelAndView mv = new ModelAndView("aluno/form"); // Template: aluno/form.html
        mv.addObject("aluno", new Aluno());
        return mv;
    }

    // Salvar novo aluno ou editar
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Aluno aluno, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/auth";

        alunoRepository.save(aluno);
        return "redirect:/aluno/listar";
    }

    // Editar aluno
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return new ModelAndView("redirect:/auth");

        ModelAndView mv = new ModelAndView("aluno/form");
        mv.addObject("aluno", alunoRepository.findById(id).orElse(new Aluno()));
        return mv;
    }

    // Excluir aluno
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/auth";

        alunoRepository.deleteById(id);
        return "redirect:/aluno/listar";
    }
}
