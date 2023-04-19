
import owner.Owner;
import owner.OwnerNotFoundException;
import owner.OwnerRepository;
import pet.Pet;
import pet.PetRepository;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        OwnerRepository ownerRepository = new OwnerRepository();
        try {
            System.out.println(ownerRepository.getAllOwners());
            System.out.println(ownerRepository.getOwnerByEmail("anna@gmail.com"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (OwnerNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("====Owners between 10 and 40=====");
            ownerRepository.filterOwnerByAge(10, 40).forEach(owner -> System.out.println(owner.getOwnerName() + "-" + owner.getAge()));
            System.out.println("===Create owner===");
            ownerRepository.createOwner(new Owner
                    (null, "Java Owner", 34, "java@gmail.com", null, null));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("===find owner by email and delete by id===");
            Owner foundOwner = ownerRepository.getOwnerByEmail("java@gmail.com");
            System.out.println(ownerRepository.deleteOwner(foundOwner.getId()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("===update existing owner===");
            Owner foundOwner = ownerRepository.getOwnerByEmail("anna@gmail.com");
            foundOwner.setOwnerName("Ann");
            foundOwner.setAge(15);
            ownerRepository.updateOwner(foundOwner);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("====Find all pets===");
        PetRepository petRepository = new PetRepository();
        try {
            System.out.println("All pets " + petRepository.getAllPets());
        } catch (SQLException exception) {
            exception.printStackTrace();

        }

        try {
            System.out.println("====Find by petName====");
            Pet getPetByName = petRepository.getPetByName("Thia");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("====Find by petName====");
            Pet getPetByName = petRepository.getPetByName("Tiah");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("====Update pet====");
            Pet foundPet = petRepository.getPetByName("Jake");
            foundPet.setPetName("Anna-Jake");
            foundPet.setBirthDate("2020-11-04");
            foundPet.setWeight(5);
            foundPet.setOwnerId(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

        try {
            System.out.println("===Find pet by name and delete===");
            Pet foundPet = petRepository.getPetByName("Mary");
            System.out.println(petRepository.deletePet(foundPet.getPetName()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        System.out.println("=====Create pet=====");
        try {
            petRepository.createPet(new Pet
                    (1, "Jarry", "2023-04-01", 1, 1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}