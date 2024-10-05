package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.country.CountryDto;
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
    public  ResponseEntity<List<CountryDto>> getCountriesWithParam(@RequestParam("name") String name, @RequestParam("code") String code){
        if(name.equals("")&&code.equals("code")){
            List<CountryDto> allcountries = countryService.getCountriesWithParam(code);
            return ResponseEntity.ok(allcountries);
        }else if(name.equals("name")&&code.equals("")) {
            List<CountryDto> allcountries = countryService.getCountriesWithParam(name);
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

}