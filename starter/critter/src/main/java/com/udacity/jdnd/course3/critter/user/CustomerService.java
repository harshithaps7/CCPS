package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {
    CustomerRepository customerRepository;
    PetService petService;

    public CustomerService(CustomerRepository customerRepository, PetService petService) {
        this.customerRepository = customerRepository;
        this.petService = petService;
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();

    }

    public Customer getOwnerByPetId(long petId) {
        Long customerId = petService.getPetById(petId).getOwnerId();
        return customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("No Customer found for Pets Owner ID : " + customerId + ", Pet Id is : " + petId));
    }
}
