package finconecta.spring.cloud.msvc.users.dtos;

import jakarta.validation.constraints.NotBlank;

public class CreateUserDto {
	
	private String id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String email;
	
    @NotBlank
    private String password;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getname() {
		return name;
	}
	public void setname(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
