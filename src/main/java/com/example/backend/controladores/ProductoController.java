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
import com.example.backend.entidades.Producto;
import com.example.backend.entidades.Proveedor;
import com.example.backend.excepciones.ResourceNotFoundException;


import com.example.backend.repositorios.ProductoRepository;
import com.example.backend.repositorios.ProveedorRepository;

@RestController
@RequestMapping("/producto")

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE }, allowedHeaders = "*")
public class ProductoController {
	@Autowired
	private ProductoRepository productoRepository;

	@Autowired
	private ProveedorRepository proveedorRepository;

	@GetMapping
	public List<Producto> obtenerProducto() {
		return productoRepository.findAll();
	}

	// Metodo para listar el id
	@GetMapping("/{id}")
	public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
		Producto producto = productoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));

		return ResponseEntity.ok(producto);
	}

	// Método para obtener las categorías activadas
	@GetMapping("/activadas")
	public List<Producto> obtenerProductoActivadas() {
		return productoRepository.findByEstadoIsTrue();
	}

	// Metodo para listar las categorias desactivadas
	@GetMapping("/desactivadas")
	public List<Producto> obtenerProductoDesactivadas() {
		return productoRepository.findByEstadoIsFalse();
	}

	@PostMapping("/")
	public ResponseEntity<Producto> agregarProducto(@RequestParam String nombre,

			@RequestParam String precio, @RequestParam String descripcion, @RequestParam int stock,@RequestParam String ubicacion,
			@RequestParam(value = "estado", required = false) Boolean estado, @RequestParam Long proveedorId) { 
		if (estado == null) {
			estado = true; // Establecer estado en true por defecto
		}

		Proveedor proveedor = proveedorRepository.findById(proveedorId)
				.orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado: " + proveedorId));

		Producto producto = new Producto(nombre, precio, descripcion, ubicacion,stock,  estado, proveedor);
		Producto productoGuardado = productoRepository.save(producto);

		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(productoGuardado.getProductoId()).toUri()).body(productoGuardado);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestParam String nombre,
			@RequestParam String precio, @RequestParam String descripcion, @RequestParam int stock,@RequestParam String ubicacion,
			@RequestParam Long proveedorId) {

		Producto productoExistente = productoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));

		// Actualizar los atributos del producto existente
		productoExistente.setNombre(nombre);
		productoExistente.setPrecio(precio);
		productoExistente.setDescripcion(descripcion);
		productoExistente.setStock(stock);
		productoExistente.setUbicacion(ubicacion);
		Proveedor proveedor = proveedorRepository.findById(proveedorId)
				.orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado: " + proveedorId));

		productoExistente.setProveedor(proveedor);

		Producto productoActualizado = productoRepository.save(productoExistente);

		return ResponseEntity.ok(productoActualizado);

	}

	// activar categoria

	@PostMapping("/activar/{id}")
	public ResponseEntity<Map<String, String>> activarProducto(@PathVariable Long id) {
		Optional<Producto> productoOptional = productoRepository.findById(id);
		if (productoOptional.isPresent()) {
			Producto producto = productoOptional.get();
			producto.setEstado(true);
			productoRepository.save(producto);

			Map<String, String> response = new HashMap<>();
			response.put("mensaje", "Producto desactivar con éxito");

			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// desactivar cateogria
	@PostMapping("/desactivar/{id}")
	public ResponseEntity<Map<String, String>> desactivarProducto(@PathVariable Long id) {
		Optional<Producto> productoOptional = productoRepository.findById(id);
		if (productoOptional.isPresent()) {
			Producto producto = productoOptional.get();
			producto.setEstado(false);
			productoRepository.save(producto);

			Map<String, String> response = new HashMap<>();
			response.put("mensaje", "Producto desactivar con éxito");

			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
