package com.example.projeto.controller;

import java.util.List;  
import org.springframework.http.ResponseEntity;  
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;  
import org.springframework.web.bind.annotation.RequestBody;   
import org.springframework.ui.Model;

import com.example.projeto.service.ProdutoService;
import com.example.projeto.service.PessoaService;
import com.example.projeto.model.Produto;
import com.example.projeto.model.Pessoa;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController{
    
    private final ProdutoService produtoService;
    private final PessoaService pessoaService;

    public ProdutoController(ProdutoService produtoService, PessoaService pessoaService){
        this.produtoService = produtoService;
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public List<Produto> listarProdutos(){
        return produtoService.listarProdutos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProduto(@PathVariable Long id){
        return produtoService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Produto criarProduto(@RequestBody Produto produto){
        // Supondo que o produto venha com o campo pessoa.id preenchido
        Pessoa pessoa = null;
        if (produto.getPessoa() != null && produto.getPessoa().getId() != null) {
            pessoa = pessoaService.buscarPorId(produto.getPessoa().getId()).orElse(null);
        }
        return produtoService.salvarProduto(produto, pessoa);
    }

    @PostMapping("/pessoa/{pessoaId}")
    public ResponseEntity<Produto> criarProdutoParaPessoa(@PathVariable Long pessoaId, @RequestBody Produto produto){
        Pessoa pessoa = pessoaService.buscarPorId(pessoaId).orElseThrow();
        Produto novoProduto = produtoService.salvarProduto(produto, pessoa);
        return ResponseEntity.ok(novoProduto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id){
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cadastrar")
    public String exibirForm(Model model) {
        //model.addAttribute("produto", new Produto());
        // ...outros atributos se necessário
        return "pessoas/form";
    }
}