package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.country.CountryDto;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class Countrycontroller {


    @MockBean
    private CountryService countryService;


    @SpyBean
    private CountryController countryController;

    @Test
    public void getCountriesTest ()  {
        CountryDto countryDto = new CountryDto("one","two");
        CountryDto countryDto2 = new CountryDto("two","four");

        List<CountryDto> list = new ArrayList<>();
        list.add(countryDto2);
        list.add(countryDto);

        when(countryService.getAllCountriesDtos())
                .thenReturn(list);

        ResponseEntity<List<CountryDto>> responseEntity = countryController.getCountries();

        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).size());
    }
}
