package com.example.projeto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.projeto.service.PessoaService;

@Controller
@RequestMapping("/compras")
public class CompraController {

    private final PessoaService pessoaService;

    public CompraController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("pessoas", pessoaService.listarPessoas());
        model.addAttribute("pessoaSelecionada", null);
        return "compras/index";
    }

    @GetMapping("/{id}")
    public String pessoaProdutos(@PathVariable Long id, Model model) {
        var pessoas = pessoaService.listarPessoas();
        var pessoaSelecionada = pessoaService.buscarPorId(id).orElse(null);
        model.addAttribute("pessoas", pessoas);
        model.addAttribute("pessoaSelecionada", pessoaSelecionada);
        return "compras/index";
    }
}