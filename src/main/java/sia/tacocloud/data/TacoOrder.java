package sia.tacocloud.data;

import java.util.List;

import org.hibernate.validator.constraints.CreditCardNumber;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

@Data
public class TacoOrder implements Serializable{

	@NotBlank(message = "Необходимо указать имя")
	@Size(max=50, message = "Не более 50 символов")
    private String deliveryName;

	@NotBlank(message = "Необходимо указать улицу")
	@Size(max=50, message = "Не более 50 символов")
    private String deliveryStreet;

	@NotBlank(message = "Необходимо указать город")
	@Size(max=50, message = "Не более 50 символов")
    private String deliveryCity;
    
	@NotBlank(message = "Необходимо указать район")
	@Size(max=20, message = "Не более 20 символов")
	private String deliveryState;
    
	@NotBlank(message = "Необходимо указать ZipCode")
	@Size(max=10, message = "Не более 10 символов")
	private String deliveryZip;
    
	// @CreditCardNumber(message = "Неправильный формат номера кредитной карты")
	@Size(max=16, message = "Не более 50 символов")
	private String ccNumber;
    
	@Pattern(regexp = "^(0[1-9]|1[1-2])([\\/])([2-9][0-9])$", message = "Неправильный формат даты ММ/ГГ")
	private String ccExpiration;
    
	@Digits(integer = 3, fraction = 0, message = "Неправильный формат CVV")
	private String ccCVV;
    
	private List<Taco> tacos = new ArrayList<>();

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Date placedAt;
    
    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }
}
