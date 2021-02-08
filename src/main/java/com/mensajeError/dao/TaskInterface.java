package com.mensajeError.dao;

import java.util.List;

import com.mensajeError.entity.Task;

public interface TaskInterface {
	
	public String save(Task task);
	
	public String update(Task task);
	
	public String updateStatus(int idTask);
	 
	public String delete (int idTask);
	
	public List<Task> findByAll();
	
	public List<Task> findById(int idTask);
	
	public String codigosError(String errores);
}
