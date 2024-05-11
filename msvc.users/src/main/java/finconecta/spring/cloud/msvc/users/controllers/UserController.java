package finconecta.spring.cloud.msvc.users.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finconecta.spring.cloud.msvc.users.dtos.CreateUserDto;
import finconecta.spring.cloud.msvc.users.dtos.UserDto;
import finconecta.spring.cloud.msvc.users.models.collections.User;
import finconecta.spring.cloud.msvc.users.services.CommonService;
import finconecta.spring.cloud.msvc.users.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
	private static final String VERSION = "/version";
	private static final String USERS_ID = "/{id}";
	@Autowired
	private UserService userService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(VERSION)
	public ResponseEntity<?> getVersion() {
		return ResponseEntity.ok(commonService.getVersion());
	}

	@GetMapping()
	public ResponseEntity<?> getAllUsers() {
		List<UserDto> dto = userService.getAllUsers().stream().map(p -> modelMapper.map(p, UserDto.class))
				.collect(Collectors.toList());
		return ResponseEntity.ok(dto);
	}

	@GetMapping(USERS_ID)
	public ResponseEntity<?> getUser(@PathVariable String id) {
		Optional<User> userOptional = userService.findById(id);
		if (userOptional.isPresent()) {
			return ResponseEntity.ok(modelMapper.map(userOptional.get(), UserDto.class));
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping()
	public ResponseEntity<?> addUser(@Valid @RequestBody CreateUserDto user, BindingResult result) {
		if (result.hasErrors()) {
			return validate(result);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(userService.save(modelMapper.map(user, User.class)), UserDto.class));
	}

	@PutMapping(USERS_ID)
	public ResponseEntity<?> updateUser(@Valid @RequestBody CreateUserDto user, BindingResult result, @PathVariable String id) {
		if (result.hasErrors()) {
			return validate(result);
		}

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(modelMapper.map(userService.update(modelMapper.map(user, User.class), id), UserDto.class));
	}

	@DeleteMapping(USERS_ID)
	public ResponseEntity<?> deleteUser(@PathVariable String id) {
		Optional<User> userOptional = userService.findById(id);
		if (userOptional.isPresent()) {
			userService.delete(id);
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.notFound().build();
	}
	
	private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
		Map<String, String> errors = new HashMap<String, String>();
		result.getFieldErrors().forEach(err -> {
			errors.put(err.getField(), err.getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}
