package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.dtos.country.CountryDto;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CountryServiceTest {
    @Autowired
    private CountryService countryService;

    @Test
    public void getDtosTest(){
        int amount = countryService.getAllCountries().size();

        List<CountryDto> contries = countryService.getAllCountriesDtos();

        assertEquals(amount,contries.size());

    }

    @Test
    public void postTest(){
        int amount = 2;

        List<CountryDto> contries = countryService.postCountries(amount);

        assertEquals(amount,contries.size());
    }
}
