package sia.tacocloud;

import java.util.List;

import org.hibernate.validator.constraints.CreditCardNumber;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import lombok.Data;

@Data
public class TacoOrder {

	@NotBlank(message = "Необходимо указать имя")
    private String deliveryName;

	@NotBlank(message = "Необходимо указать улицу")
    private String deliveryStreet;

	@NotBlank(message = "Необходимо указать город")
    private String deliveryCity;
    
	@NotBlank(message = "Необходимо указать район")
	private String deliveryState;
    
	@NotBlank(message = "Необходимо указать ZipCode")
	private String deliveryZip;
    
	@CreditCardNumber(message = "Неправильный формат номера кредитной карты")
	private String ccNumber;
    
	@Pattern(regexp = "^(0[1-9]|1[1-2])([\\/])([2-9][0-9])$", message = "Неправильный формат даты ММ/ГГ")
	private String ccExpiration;
    
	@Digits(integer = 3, fraction = 0, message = "Неправильный формат CVV")
	private String ccCVV;
    
	private List<Taco> tacos = new ArrayList<>();
    
    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }
}
