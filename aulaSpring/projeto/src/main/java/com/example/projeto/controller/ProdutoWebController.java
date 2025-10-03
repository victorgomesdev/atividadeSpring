package com.example.projeto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.projeto.model.Produto;
import com.example.projeto.service.PessoaService;
import com.example.projeto.service.ProdutoService;

@Controller
@RequestMapping("/produtos")
public class ProdutoWebController {

    private final ProdutoService produtoService;
    private final PessoaService pessoaService;

    public ProdutoWebController(ProdutoService produtoService, PessoaService pessoaService) {
        this.produtoService = produtoService;
        this.pessoaService = pessoaService;
    }

    @GetMapping("/cadastrar/{pessoaId}")
    public String exibirForm(@PathVariable Long pessoaId, Model model) {
        var pessoa = pessoaService.buscarPorId(pessoaId).orElseThrow();
        model.addAttribute("pessoa", pessoa);
        model.addAttribute("produto", new Produto());
        return "produtos/form";
    }

    @PostMapping("/cadastrar/{pessoaId}")
    public String cadastrar(@PathVariable Long pessoaId, @ModelAttribute Produto produto, RedirectAttributes ra) {
        var pessoa = pessoaService.buscarPorId(pessoaId).orElseThrow();
        produtoService.salvarProduto(produto, pessoa);
        ra.addFlashAttribute("success", "Produto cadastrado!");
        return "redirect:/compras/" + pessoaId;
    }
}