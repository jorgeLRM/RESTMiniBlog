package com.mitocode.service;

import java.util.List;

public interface IService<T>{

	int registrar(T t) throws Exception;	
	int modificar(T t) throws Exception;
	List<T> listar() throws Exception;
	T listarPorId(T t) throws Exception;
}
