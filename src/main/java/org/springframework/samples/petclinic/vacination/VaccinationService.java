package org.springframework.samples.petclinic.vacination;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.PetType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VaccinationService {
    @Autowired
    VaccinationRepository vaccinationRepository;

    public List<Vaccination> getAll(){
        return vaccinationRepository.findAll();
    }

    public List<Vaccine> getAllVaccines(){
        return null;
    }

    public Vaccine getVaccine(String typeName) {
        return vaccinationRepository.findVaccineByName(typeName);
    }

    @Transactional(rollbackFor = UnfeasibleVaccinationException.class)
    public Vaccination save(Vaccination p) throws UnfeasibleVaccinationException {
        PetType petType = p.getVaccinatedPet().getType();
        PetType vaccinePetType = p.getVaccine().getPetType();
        if (petType == vaccinePetType)
            return vaccinationRepository.save(p);
        throw new UnfeasibleVaccinationException();
    }
    
    
}
