package com.fatec.scel.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.fatec.scel.model.Aluno;
import com.fatec.scel.model.AlunoRepository;

@RestController
@RequestMapping(path = "/aluno")
public class AlunoController {
//insert into aluno values ()
	@Autowired
	private AlunoRepository repository;

	@GetMapping("/consulta")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("consultarAluno");
		modelAndView.addObject("aluno", repository.findAll());
		return modelAndView;
	}

	/**
	 * quando o usuario digita localhost:8080/api/add
	 *
	 * @param aluno
	 * @return o html /CadastraAluno
	 */
	@GetMapping("/cadastrar")
	public ModelAndView cadastraAluno(Aluno aluno) {
		ModelAndView mv = new ModelAndView("cadastrarAluno");
		mv.addObject("aluno", aluno);
		return mv;
	}
	

	@GetMapping("/edit/{isbn}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView mostraFormAdd(@PathVariable("ra") Long ra) {
		ModelAndView modelAndView = new ModelAndView("atualizaAluno");
		modelAndView.addObject("aluno", repository.findByra(ra)); // o repositorio e injetado no controller
		return modelAndView; // addObject adiciona objetos para view
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		repository.deleteById(id);
		ModelAndView modelAndView = new ModelAndView("consultarAluno");
		modelAndView.addObject("aluno", repository.findAll());
		return modelAndView;
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid Aluno aluno, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarAluno");
		if (result.hasErrors()) {
			return new ModelAndView("cadastrarAluno");
		}
		try {
			Aluno jaExiste = null;
			jaExiste = repository.findByra(aluno.getRa());
			if (jaExiste == null) {
				repository.save(aluno);
				modelAndView = new ModelAndView("consultarAluno");
				modelAndView.addObject("aluno", repository.findAll());
				return modelAndView;
			} else {
				return new ModelAndView("cadastrarAluno");
			}
		} catch (Exception e) {
			System.out.println("erro ===> " + e.getMessage());
			return modelAndView; // captura o erro mas nao informa o motivo.
		}
	}

	@PostMapping("/update/{ra}")
	public ModelAndView atualiza(@PathVariable("ra") Long ra, @Valid Aluno aluno, BindingResult result) {
		if (result.hasErrors()) {
			aluno.setRa(ra);
			return new ModelAndView("atualizaAluno");
		}
		Aluno umAluno = repository.findByra(ra);
		umAluno.setNome(aluno.getNome());
		umAluno.setEmail(aluno.getEmail());
		repository.save(umAluno);
		ModelAndView modelAndView = new ModelAndView("consultarAluno");
		modelAndView.addObject("aluno", repository.findAll());
		return modelAndView;
	}
}