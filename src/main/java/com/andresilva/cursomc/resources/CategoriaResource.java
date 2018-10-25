package com.andresilva.cursomc.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categorias") // na boa pratica o rest coloca-se o verbo do end point no plural
public class CategoriaResource {
	
	// na boa prtica do rest deve-se identificar correctamente os verbos http para cada operação que queremos neste caso o Get 
	@RequestMapping(method=RequestMethod.GET)
	public String listar() {
		return "REST esta a funcionar";
	}
}
