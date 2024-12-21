package com.example.backend.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.excepciones.ResourceNotFoundException;
import com.example.backend.modelo.Usuario;
import com.example.backend.modelo.UsuarioRol;
import com.example.backend.repositorios.UsuarioRepository;
import com.example.backend.repositorios.UsuarioRolRepository;


@RestController
@RequestMapping("/usuariorol")

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE }, allowedHeaders = "*")
public class UsuarioRolController {


	  private final UsuarioRolRepository usuarioRolRepository;
	  @Autowired
	  private  UsuarioRepository usuarioRepository;
	  
	  
	  @Autowired
	  public UsuarioRolController(UsuarioRolRepository usuarioRolRepository) {
	    this.usuarioRolRepository = usuarioRolRepository;
	 
	  }

	  @GetMapping
	  public ResponseEntity<List<UsuarioRol>> obtenerTodosLosUsuarioRoles() {
	    List<UsuarioRol> usuarioRoles = usuarioRolRepository.findAll();
	    return new ResponseEntity<>(usuarioRoles, HttpStatus.OK);
	  }
	  

	  @GetMapping("/admin/activadas")
	  public ResponseEntity<List<UsuarioRol>> obtenerAdminactivadas() {
	      List<UsuarioRol> usuariosAdminHabilitados = usuarioRolRepository.findByRolRolNombreAndUsuarioEnabled("ADMIN", true);
	      return new ResponseEntity<>(usuariosAdminHabilitados, HttpStatus.OK);
	  }
	  @GetMapping("/admin/desactivadas")
	  public ResponseEntity<List<UsuarioRol>> obtenerAdmindesactivadas() {
	      List<UsuarioRol> usuariosAdminHabilitados = usuarioRolRepository.findByRolRolNombreAndUsuarioEnabled("ADMIN", false);
	      return new ResponseEntity<>(usuariosAdminHabilitados, HttpStatus.OK);
	  }
	  @GetMapping("/normal/activadas")
	  public ResponseEntity<List<UsuarioRol>> obtenerNormalactivadas() {
	      List<UsuarioRol> usuariosAdminHabilitados = usuarioRolRepository.findByRolRolNombreAndUsuarioEnabled("NORMAL", true);
	      return new ResponseEntity<>(usuariosAdminHabilitados, HttpStatus.OK);
	  }
	  @GetMapping("/normal/desactivadas")
	  public ResponseEntity<List<UsuarioRol>> obtenerNormaldesactivadas() {
	      List<UsuarioRol> usuariosAdminHabilitados = usuarioRolRepository.findByRolRolNombreAndUsuarioEnabled("NORMAL", false);
	      return new ResponseEntity<>(usuariosAdminHabilitados, HttpStatus.OK);
	  }
	  
	  @DeleteMapping("/desactivar/{id}")
	  public ResponseEntity<String> desactivarUsuario(@PathVariable Long id) {
	      Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

	      if (usuarioOptional.isPresent()) {
	          Usuario usuario = usuarioOptional.get();
	          usuario.setEnabled(false);
	          usuarioRepository.save(usuario);
	          return new ResponseEntity<>("Usuario desactivado exitosamente.", HttpStatus.OK);
	      } else {
	          return new ResponseEntity<>("No se encontró un usuario con el ID proporcionado.", HttpStatus.NOT_FOUND);
	      }
	  }
	  @DeleteMapping("/activar/{id}")
	  public ResponseEntity<String> activarUsuario(@PathVariable Long id) {
	      Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

	      if (usuarioOptional.isPresent()) {
	          Usuario usuario = usuarioOptional.get();
	          usuario.setEnabled(true);
	          usuarioRepository.save(usuario);
	          return new ResponseEntity<>("Usuario activado exitosamente.", HttpStatus.OK);
	      } else {
	          return new ResponseEntity<>("No se encontró un usuario con el ID proporcionado.", HttpStatus.NOT_FOUND);
	      }
	  }
	  @GetMapping("/{id}")
	    public ResponseEntity<?> obtenerUsuarioRolPorId(@PathVariable Long id) {
	        try {
	            // Buscar el UsuarioRol por su ID en la base de datos
	            UsuarioRol usuarioRol = usuarioRolRepository.findById(id)
	                    .orElseThrow(() -> new ResourceNotFoundException("UsuarioRol no encontrado: " + id));

	            // Devolver el UsuarioRol encontrado como respuesta
	            return ResponseEntity.ok(usuarioRol);
	        } catch (ResourceNotFoundException e) {
	            // Manejar la excepción si el UsuarioRol no se encuentra
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        } catch (Exception e) {
	            // Manejar otras excepciones
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener el UsuarioRol");
	        }
	    }

	  

	    @PutMapping("/{id}")
	    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
	        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
	        
	        if (usuarioOptional.isPresent()) {
	            Usuario usuarioExistente = usuarioOptional.get();
	            
	            // Actualiza los campos del usuario existente con los datos del usuario actualizado
	            usuarioExistente.setNombre(usuarioActualizado.getNombre());
	            usuarioExistente.setApellido(usuarioActualizado.getApellido());
	            usuarioExistente.setApellido(usuarioActualizado.getApellido());
	            usuarioExistente.setEmail(usuarioActualizado.getEmail());
	            usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
	            usuarioExistente.setDni(usuarioActualizado.getDni());
	            usuarioExistente.setDireccion(usuarioActualizado.getDireccion());
	            usuarioExistente.setUsername(usuarioActualizado.getUsername());
	            
	            
	     
	            // Puedes agregar lógica adicional para actualizar otros campos aquí

	            // Guarda el usuario actualizado en la base de datos
	            usuarioRepository.save(usuarioExistente);
	            
	            return new ResponseEntity<>(usuarioExistente, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	    

	  
}
