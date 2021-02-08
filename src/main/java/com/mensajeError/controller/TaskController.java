package com.mensajeError.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mensajeError.dao.TaskInterface;
import com.mensajeError.entity.Task;

@Controller
public class TaskController {

	@Autowired
	private TaskInterface taskInterface;
	
	@GetMapping("/")
	public String task(@ModelAttribute("task") Task task, BindingResult result, Model model) {
		try {
			List<Task> listaCTRL = taskInterface.findByAll();
			model.addAttribute("listaTL",listaCTRL);
			return "views/index";
		}catch(Exception ex) {
			String mensaje = taskInterface.codigosError(ex.toString());
			System.out.println("Error controller: "+mensaje);
			model.addAttribute("mensaje", mensaje);
			return "error/error";
		}
	}
	
	@GetMapping("/task-get/{idTask}")
	public String taskGet(@ModelAttribute("taskUpdate")Task task, @PathVariable("idTask") Integer idTask, Model model) {
		try {
			
			List<Task> listaGET = taskInterface.findById(idTask);
			task.setTitulo(listaGET.get(0).getTitulo());
			task.setDescripcion(listaGET.get(0).getDescripcion());
			task.setIdTask(listaGET.get(0).getIdTask());
			
			//model.addAttribute("idTask", listaGET.get(0).getIdTask());
			//model.addAttribute("titulo", listaGET.get(0).getTitulo());
			//model.addAttribute("descripcion", listaGET.get(0).getDescripcion());
			return"views/registro";
		}catch(Exception ex) {
			System.out.println("1. Error controller: "+ex);
			String mensaje = taskInterface.codigosError(ex.toString());
			System.out.println("2. Error controller: "+mensaje);
			model.addAttribute("mensaje", mensaje);
			return "error/error";
		}
	}
	
	@PostMapping("/task-create")
	public String taskCreate(Task task, Model model) {
		try {
			String mensaje = taskInterface.save(task);
			if(mensaje == null) {
				return "redirect:/";
			}else {
				model.addAttribute("mensaje", mensaje);
				return "error/error";
			}
		}catch(Exception ex) {
			String mensaje = taskInterface.codigosError(ex.toString());
			System.out.println("Error controller: "+mensaje);
			model.addAttribute("mensaje", mensaje);
			return "error/error";
		}
	}
	
	@PostMapping("/task-update")
	public String taskUpdate(Task task, Model model) {
		try {
			//taskInterface.update(task);
			String mensaje = taskInterface.update(task);
			if(mensaje == null) {
				return "redirect:/";
			}else {
				model.addAttribute("mensaje", mensaje);
				return "error/error";
			}
		}catch(Exception ex) {
			String mensaje = taskInterface.codigosError(ex.toString());
			System.out.println("Error controller: "+mensaje);
			model.addAttribute("mensaje", mensaje);
			return "error/error";
		}
		
	}
	
	@GetMapping("/task-updateStatus/{idTask}")
	public String taskUpdateStatus(@PathVariable("idTask") Integer idTask, Model model) {
		//taskInterface.updateStatus(idTask);
		try {
			String mensaje = taskInterface.updateStatus(idTask);
			if(mensaje == null) {
				return "redirect:/";
			}else {
				model.addAttribute("mensaje", mensaje);
				return "error/error";
			}
		}catch(Exception ex) {
			String mensaje = taskInterface.codigosError(ex.toString());
			System.out.println("Error controller: "+mensaje);
			model.addAttribute("mensaje", mensaje);
			return "error/error";
		}
		
	}
	
	@GetMapping("/task-delete/{idTask}")
	public String taskDelete(@PathVariable("idTask") Integer idTask, Model model) {
		try {
			String mensaje = taskInterface.delete(idTask);
			if(mensaje == null) {
				return "redirect:/";
			}else {
				model.addAttribute("mensaje", mensaje);
				return "error/error";
			}
		}catch(Exception ex) {
			String mensaje = taskInterface.codigosError(ex.toString());
			System.out.println("Error controller: "+mensaje);
			model.addAttribute("mensaje", mensaje);
			return "error/error";
		}
	}
	
	@GetMapping("/error")
	public String pageError(Model model) {
		model.addAttribute("mensaje", "Hola ¿cómo estás?");
		return "error/error";
	}
	
	@GetMapping("/edit")
	public String pageError() {
		return "views/registro";
	}
}
