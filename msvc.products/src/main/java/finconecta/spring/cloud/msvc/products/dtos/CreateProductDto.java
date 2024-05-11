package finconecta.spring.cloud.msvc.products.dtos;

import jakarta.validation.constraints.NotBlank;

public class CreateProductDto {
    private Long id;
    
    @NotBlank
    private String code;
    
    @NotBlank
	private String name;
    
    @NotBlank
    private String serial;
    
    @NotBlank
    private String barCode;
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}    
}
