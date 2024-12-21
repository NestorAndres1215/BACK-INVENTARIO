package com.example.backend.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.backend.entidades.Proveedor;
import com.example.backend.excepciones.ResourceNotFoundException;
import com.example.backend.repositorios.ProveedorRepository;

@RestController
@RequestMapping("/proveedor")

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE }, allowedHeaders = "*")
public class ProveedorController {

	@Autowired
	private ProveedorRepository proveedorRepository;

	// Metodo Listar
	@GetMapping
	public List<Proveedor> obtenerProveedor() {
		return proveedorRepository.findAll();
	}

	// Metodo Listar por Id
	@GetMapping("/{id}")
	public ResponseEntity<Proveedor> obtenerProveedorPorId(@PathVariable Long id) {
		Proveedor proveedor = proveedorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Proveedir no encontrado: " + id));
		return ResponseEntity.ok(proveedor);
	}

	// Metodo Listar desactivadas
	@GetMapping("/desactivadas")
	public List<Proveedor> obtenerProveedorDesactivadas() {
		return proveedorRepository.findByEstadoIsFalse();
	}

	// Metodo Listar activadas
	@GetMapping("/activadas")
	public List<Proveedor> obtenerProveedorActivadas() {
		return proveedorRepository.findByEstadoIsTrue();
	}

	// crear proveedor
	@PostMapping("/")
	public ResponseEntity<Proveedor> agregarProveedor(@RequestParam String nombre, @RequestParam String Ruc,
			@RequestParam String Direccion, @RequestParam String Telefono, @RequestParam String Email,
			@RequestParam(value = "estado", required = false) Boolean estado) {
		// Verificar si el estado se proporcionó en la solicitud
		if (estado == null) {
			estado = true; // Establecer estado en true por defecto
		}

		Proveedor proveedor = new Proveedor(nombre, Ruc, Direccion, Telefono, Email, estado);
		Proveedor proveedorGuardada = proveedorRepository.save(proveedor);
		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(proveedorGuardada.getProveedorId()).toUri()).body(proveedorGuardada);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Proveedor> actualizarProveedor(@PathVariable Long id, @RequestParam String nombre,
			@RequestParam String ruc, @RequestParam String direccion, @RequestParam String telefono,
			@RequestParam String email) {
		// Verificar si el proveedor existe en la base de datos
		Optional<Proveedor> proveedorOptional = proveedorRepository.findById(id);
		if (!proveedorOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		// Obtener el proveedor existente de la base de datos
		Proveedor proveedorExistente = proveedorOptional.get();
		// Actualizar los datos del proveedor
		proveedorExistente.setNombre(nombre);
		proveedorExistente.setRuc(ruc);
		proveedorExistente.setDireccion(direccion);
		proveedorExistente.setTelefono(telefono);
		proveedorExistente.setEmail(email);

		// Guardar los cambios en la base de datos
		Proveedor proveedorActualizada = proveedorRepository.save(proveedorExistente);
		return ResponseEntity.ok(proveedorActualizada);
	}

	// activar categoria

	@PostMapping("/activar/{id}")
	public ResponseEntity<Map<String, String>> activarProveedor(@PathVariable Long id) {
		Optional<Proveedor> proveedorOptional = proveedorRepository.findById(id);
		if (proveedorOptional.isPresent()) {
			Proveedor proveedor = proveedorOptional.get();
			proveedor.setEstado(true);
			proveedorRepository.save(proveedor);

			Map<String, String> response = new HashMap<>();
			response.put("mensaje", "Proveedor activar con éxito");

			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// desactivar cateogria
	@PostMapping("/desactivar/{id}")
	public ResponseEntity<Map<String, String>> desactivarProveedor(@PathVariable Long id) {
		Optional<Proveedor> proveedorOptional = proveedorRepository.findById(id);
		if (proveedorOptional.isPresent()) {
			Proveedor proveedor = proveedorOptional.get();
			proveedor.setEstado(false);
			proveedorRepository.save(proveedor);

			Map<String, String> response = new HashMap<>();
			response.put("mensaje", "Proveedor desactivar con éxito");

			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.notFound().build();
		}
	}



	
}
