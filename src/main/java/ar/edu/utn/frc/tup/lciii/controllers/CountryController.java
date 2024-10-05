package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.country.CountryDto;
import ar.edu.utn.frc.tup.lciii.dtos.country.amountCountry;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CountryController {

    @Autowired
    private final CountryService countryService;

    @GetMapping("/countries")
    public ResponseEntity<List<CountryDto>> getCountries(){
        List<CountryDto> allCountries= countryService.getAllCountriesDtos();
        return ResponseEntity.ok(allCountries);
    }

    @GetMapping("/countries/")
    public  ResponseEntity<List<CountryDto>> getCountriesWithParam(@RequestParam(value = "name", required = false) String name
            , @RequestParam(value = "code",required = false) String code){

        if (name.isEmpty() || code.isEmpty()){
            return ResponseEntity.ok(countryService.getAllCountriesDtos());
        } else if (code.isEmpty()&&!name.isEmpty()) {
            List<CountryDto> allcountries = countryService.getCountriesWithParam(name);
            return ResponseEntity.ok(allcountries);
        } else if (!code.isEmpty()&&name.isEmpty()) {
            List<CountryDto> allcountries = countryService.getCountriesWithParam(code);
            return ResponseEntity.ok(allcountries);
        } else {
            return ResponseEntity.notFound().build();
        }


    }

    @GetMapping("countries/{continent}/continent")
    public ResponseEntity<List<CountryDto>> getCountriesContinent(@PathVariable("continent") String continent){
        List<CountryDto> allCountries= countryService.getAllInContinent(continent);
        return ResponseEntity.ok(allCountries);
    }

    @GetMapping("countries/{language}/language")
    public ResponseEntity<List<CountryDto>> getCountriesLanguage(@PathVariable("language") String language){
        List<CountryDto> allCountries= countryService.getAllInLanguage(language);
        return ResponseEntity.ok(allCountries);
    }

    @GetMapping("countries/most-borders")
    public ResponseEntity<CountryDto> getMostBorderCountries(){
        CountryDto country= countryService.getMost();
        return ResponseEntity.ok(country);
    }

    @PostMapping("/countries")
    public ResponseEntity<List<CountryDto>> postRandom(@RequestBody amountCountry amount){
        if (amount.getAmountOfCountryToSave()>10||amount.getAmountOfCountryToSave()<0){
            return ResponseEntity.badRequest().build();
        } else {
            List<CountryDto> allCountries = countryService.postCountries(amount.getAmountOfCountryToSave());
            return ResponseEntity.ok(allCountries);
        }
    }

}