package com.teste.globalweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.teste.globalweb.model.Convidado;
import com.teste.globalweb.repository.Convidados;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/convidados")
public class ConvidadosController {

	@Autowired
	private Convidados convidados;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("ListaConvidados");
		
		modelAndView.addObject("convidados", convidados.findAll());
		modelAndView.addObject(new Convidado());
		return modelAndView;
	}
	
	@PostMapping
	public String salvar(Convidado convidado, String uri, RedirectAttributes redirectAttributes) {
		if (convidado.getDataSaida().isBefore(convidado.getDataChegada())) {
			redirectAttributes.addFlashAttribute("erro", "A data de saída deve ser maior ou igual à de chegada!");
			return "redirect:" + uri;
		}
		long diferencaDias = convidado.getDataChegada().datesUntil(convidado.getDataSaida()).count();
		if (diferencaDias < 1) {
			diferencaDias = 1;
		}
		long valorTotal = diferencaDias * convidado.getValorDia();
		convidado.setValorTotal(valorTotal);
		this.convidados.save(convidado);
		return "redirect:/convidados";
	}

	@GetMapping("/editar/{id}")
	public String editarConvidado(@PathVariable Long id, Model model) {
		Convidado convidado = this.convidados.findById(id).orElseThrow();
		model.addAttribute("convidado", convidado);
		return "EditarConvidado";
	}

	@PostMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) {
		this.convidados.deleteById(id);
		return "redirect:/convidados";
	}

}
