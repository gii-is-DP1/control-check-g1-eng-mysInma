package org.springframework.samples.petclinic.vacination;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VaccinationController {

    @Autowired
    VaccinationService vaccinationService;

    @Autowired
    private PetService petService;

    //Ejercicio 9
    @ModelAttribute("vaccines")
    public Collection<Vaccine> populateVaccines(){
        return vaccinationService.getAllVaccines();
    }

    @ModelAttribute("pets")
    public Collection<Pet> populatePets() {
        return petService.findAllPets();
    }


    @GetMapping(value = "/vaccination/create") //Esto para que vaya a la url que nos dan
    public String initCreationForm(Map<String, Object> model){
        Vaccination vaccination = new Vaccination();
        model.put("vaccination", vaccination);
        return "vaccination/createOrUpdateVaccinationForm";
    }

        //Ejercicio 10
        @PostMapping(value = "/vaccination/create")
        public String processCreationForm(@Valid Vaccination v, BindingResult result) {
            if (result.hasErrors()) {
                return "vaccination/createOrUpdateVaccinationForm";
            } else {
                try {
                    vaccinationService.save(v);
                    return "welcome";
                } catch (UnfeasibleVaccinationException e) {
                    result.rejectValue("vaccinatedPet", "bad request",
                            "La mascota seleccionada no puede recibir la vacuna especificada");
                    return "vaccination/createOrUpdateVaccinationForm";
                }
            }
        }
    
    
}
