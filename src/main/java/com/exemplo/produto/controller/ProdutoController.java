package com.exemplo.produto.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.exemplo.produto.model.Produto;
import com.exemplo.produto.repository.filter.ProdutoFilter;
import com.exemplo.produto.service.ProdutoService;

@Controller
public class ProdutoController {
	
	@Autowired
	private ProdutoService service;
	
	//Tela principal do Crud onde sao listados todos os produtos
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView listarProdutos(ProdutoFilter produtoFilter) {
		ModelAndView mv = new ModelAndView("/listagemProdutos");
		mv.addObject("produtos", service.findByDescricao(produtoFilter.getDescricao()));
		return mv;
	}
	
	@RequestMapping(value="/adicionar", method=RequestMethod.GET)
	public ModelAndView adicionar(Produto produto) {
		ModelAndView mv = new ModelAndView("/cadastroProduto");
		mv.addObject("produto", produto);
		return mv;
	}
	
	//Vai para tela de edição de produtos (mesma tela de adição, contudo é enviado para a view um objeto que já existe)
	@RequestMapping(value="/editar/{id}", method=RequestMethod.GET)
	public ModelAndView editar(@PathVariable("id") Long id) {
		return adicionar(service.findOne(id));
	}
	
	@RequestMapping(value="/deletar/{id}", method=RequestMethod.GET)
	public ModelAndView deletar(@PathVariable("id") Long id) {
		service.delete(id);
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value="/salvar", method=RequestMethod.POST)
	public ModelAndView salvar(@Valid Produto produto, BindingResult result) {
		
		if(result.hasErrors()) {
			return adicionar(produto);
		}
		service.save(produto);
		return new ModelAndView("redirect:/");
	}
}
