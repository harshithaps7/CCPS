package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {
    PetRepository petRepository;
    CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public Pet savePet(Pet pet, Long customerId) {
        Pet newPet = new Pet();
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new ResourceNotFoundException("No Customer Exist for ID : " + customerId));
        pet.setOwnerId(customer);
        newPet = petRepository.save(pet);
        //Now need to associate pet to Customer
        //System.out.println("Adding pet : " + newPet.getName() + " : " + newPet.getId());
        //System.out.println("Returning after Saving Pet : " + newPet.getId());
        customer.addOnePet(newPet);
        customerRepository.save(customer);
        return newPet;
    }

    public Pet getPetById(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pet not exist for ID : " + id));
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(long ownerId) {
        Customer customer = customerRepository.findById(ownerId).orElseThrow(()-> new ResourceNotFoundException("No Customer Exist for ID : " + ownerId));
        return petRepository.findByCustomer(customer);
    }
}
