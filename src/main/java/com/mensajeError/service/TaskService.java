package com.mensajeError.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mensajeError.controller.TaskController;
import com.mensajeError.dao.TaskInterface;
import com.mensajeError.entity.Task;

public class TaskService implements TaskInterface{

	//Atriburo JDBC para realizar la manipulación de datos a traves de la conexión
	private JdbcTemplate jdbcTemplate;
	
	String sql;
	
	public TaskService(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public String save(Task task) {
		// TODO Auto-generated method stub
		try {
			sql = "INSERT INTO task(titulo, descripcion, status) value(?,?,'Pendiente')";
			int db = jdbcTemplate.update(sql, task.getTitulo(), task.getDescripcion());
			
			if(db==0) {
				return codigosError("Error en la base de datos");
			}
			
		}catch(Exception ex) {
			System.out.println("Error service: "+ex);
			return codigosError(ex.toString());
		}
		return null;
		
	}

	@Override
	public String update(Task task) {
		// TODO Auto-generated method stub
		try {
			sql = "UPDATE task SET titulo=?, descripcion=? WHERE idTask=(?)";
			int db = jdbcTemplate.update(sql, task.getTitulo(), task.getDescripcion(), task.getIdTask());
			
			if(db==0) {
				return codigosError("Error en la base de datos");
			}
			
		}catch(Exception ex) {
			System.out.println("Error service: "+ex);
			return codigosError(ex.toString());
		}
		return null;
	}
	
	@Override
	public String updateStatus(int idTask) {
		// TODO Auto-generated method stub
		try {
			sql = "UPDATE task SET status='Terminado'WHERE idTask=(?)";
			int db = jdbcTemplate.update(sql, idTask);
			
			if(db==0) {
				return codigosError("Error en la base de datos");
			}
			
		}catch(Exception ex) {
			System.out.println("Error service: "+ex);
			return codigosError(ex.toString());
		}
		return null;
	}

	@Override
	public String delete(int idTask) {
		// TODO Auto-generated method stub
		try {
			sql = "DELETE FROM task WHERE idTask = ?";
			
			int db = jdbcTemplate.update(sql, idTask);	
			
			if(db==0) {
				return codigosError("Error en la base de datos");
			}
			
		}catch(Exception ex) {
			System.out.println("Error service: "+ex);
			return codigosError(ex.toString());
		}
		return null;
	}

	@Override
	public List<Task> findByAll() {
		sql = "SELECT * FROM task";
		return this.jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Task.class));
	}

	@Override
	public List<Task> findById(int idTask) {
		// TODO Auto-generated method stub
		sql = "SELECT * FROM task WHERE idTask = "+idTask;
		return this.jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Task.class));
	}

	@Override
	public String codigosError(String errores) {
		String[][] codigosError = new String[21][2];
		
		if (errores.indexOf(":") != -1) {
			errores = errores.substring(0, errores.indexOf(":"));
		}
		
		System.out.println("Palabra error: "+errores);
		
		codigosError[0][0]="PDE-01";
		codigosError[0][1]="java.lang.ArithmeticException";
		
		codigosError[1][0]="PDE-02";
		codigosError[1][1]="java.lang.IndexOutOfBoundsException";

		codigosError[2][0]="PDE-03";
		codigosError[2][1]="java.lang.ClassCastException";
		
		codigosError[3][0]="PDE-04";
		codigosError[3][1]="java.lang.NegativeArraySizeException";
		
		codigosError[4][0]="PDE-05";
		codigosError[4][1]="java.lang.NullPointerException";
		
		codigosError[5][0]="PDE-06";
		codigosError[5][1]="java.lang.NumberFormatException";
		
		codigosError[6][0]="PDE-07";
		codigosError[6][1]="java.lang.StringIndexOutOfBounds";
	
		codigosError[7][0]="PDE-08";
		codigosError[7][1]="java.util.EmptyStackException";
		
		codigosError[8][0]="PDE-09";
		codigosError[8][1]="java.lang.SecurityException";
		
		codigosError[9][0]="PDE-10";
		codigosError[9][1]="java.lang.IllegalStateException";
		
		codigosError[10][0]="PDE-11";
		codigosError[10][1]="java.util.ConnectionNotFoundException";
		
		codigosError[11][0]="PDE-12";
		codigosError[11][1]="java.lang.ClassNotFoundException";

		codigosError[12][0]="PDE-13";
		codigosError[12][1]="java.lang.NoClassDefFoundError";
		
		codigosError[13][0]="PDE-14";
		codigosError[13][1]="java.lang.AssertionError";
		
		codigosError[14][0]="PDE-15";
		codigosError[14][1]="java.lang.IllegalAccessException";
		
		codigosError[15][0]="PDE-16";
		codigosError[15][1]="java.lang.OutOfMemoryError";
		
		codigosError[16][0]="PDE-17";
		codigosError[16][1]="java.lang.InstantiationException";
		
		codigosError[17][0]="PDE-18";
		codigosError[17][1]="java.lang.InterruptedException";
		
		codigosError[18][0]="PDE-19";
		codigosError[18][1]="java.lang.ArrayStoreException";
		
		codigosError[19][0]="PDE-20";
		codigosError[19][1]="org.springframework.jdbc.CannotGetJdbcConnectionException";
		
		codigosError[20][0]="PDE-21";
		codigosError[20][1]="Error en la base de datos";
		
		
		for(int i=0;i<21;i++) {
			if(codigosError[i][1].equals(errores)) {
				return codigosError[i][0];
			}
		}
		
		return "Error no localizado :c";
	}

}
