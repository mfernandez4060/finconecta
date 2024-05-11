package finconecta.spring.cloud.msvc.users.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finconecta.spring.cloud.msvc.users.dtos.UserDto;
import finconecta.spring.cloud.msvc.users.exceptions.EmailAlreadyExistsErrorExcepton;
import finconecta.spring.cloud.msvc.users.exceptions.UserCreateErrorException;
import finconecta.spring.cloud.msvc.users.exceptions.UserNotFoundException;
import finconecta.spring.cloud.msvc.users.models.collections.User;
import finconecta.spring.cloud.msvc.users.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	private static final String NAME = "name";
	@Autowired
	private UserRepository repository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Transactional(readOnly = true)
	public List<User> getAllUsers() {
		return repository.findAll(Sort.by(NAME));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findById(String id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public User save(User user) {
		if (!user.getEmail().isBlank() && this.existsByEmail(user.getEmail())) {
			throw new EmailAlreadyExistsErrorExcepton(user.getEmail());
		}
		try {
			user.setId(null);
			return repository.save(user);
		} catch (Exception e) {
			throw new UserCreateErrorException(modelMapper.map(user, UserDto.class));
		}
	}

	@Override
	@Transactional
	public User update(User user, String id) {
		Optional<User> userOptional = this.findById(id);
		if (userOptional.isPresent()) {
			User userEntity = userOptional.get();

			if (user.getEmail() != null && !user.getEmail().isBlank()
					&& !user.getEmail().equalsIgnoreCase(userEntity.getEmail())
					&& this.existsByEmail(user.getEmail())) {
				throw new EmailAlreadyExistsErrorExcepton(user.getEmail());
			}
			if (user.getName() != null && !user.getName().isBlank())
				userEntity.setName(user.getName());			
			if (user.getPassword() != null && !user.getPassword().isBlank())
				userEntity.setPassword(user.getPassword());
			if (user.getEmail() != null && !user.getEmail().isBlank())
				userEntity.setEmail(user.getEmail());
			try {
				return repository.save(userEntity);
			} catch (Exception e) {
				throw new UserCreateErrorException(modelMapper.map(userEntity, UserDto.class));
			}
		}
		throw new UserNotFoundException(id);
	}

	@Override
	@Transactional
	public void delete(String id) {
		repository.deleteById(id);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsByEmail(String email) {
		return repository.existsByEmail(email);
	}
}
