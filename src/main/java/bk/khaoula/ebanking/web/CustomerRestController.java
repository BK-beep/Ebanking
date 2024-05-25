package bk.khaoula.ebanking.web;

import bk.khaoula.ebanking.dtos.CustomerDto;
import bk.khaoula.ebanking.entities.Customer;
import bk.khaoula.ebanking.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    public List<CustomerDto> customers(){
        return bankAccountService.listCustomers();
    }
    @GetMapping("/search")
    public List<CustomerDto> customers(@RequestParam(value = "kw",defaultValue = "") String keyword){
        return bankAccountService.searchByKeyword(keyword);
    }
    @GetMapping("/customers/{id}")
    public CustomerDto customer(@PathVariable(name = "id") Long id){
        return bankAccountService.getCustomer(id);
    }

    @PostMapping("/customers")
    public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto){
        return bankAccountService.saveCustomer(customerDto);
    }
    @PutMapping("/customers/{id}")
    public CustomerDto updateCustomer(@PathVariable Long id,@RequestBody CustomerDto customerDto){
        return bankAccountService.updateCustomer(id,customerDto);
    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        bankAccountService.deleteCustomer(id);
    }
}
