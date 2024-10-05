package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.Entity.CountryEntity;
import ar.edu.utn.frc.tup.lciii.dtos.country.CountryDto;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

        @Autowired
        private final CountryRepository countryRepository;

        @Autowired
        private final RestTemplate restTemplate;

        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }

        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */
        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .code((String) countryData.get("cca3"))
                        .region((String) countryData.get("region"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .continent((Map<String, String>) countryData.get("continent"))
                        .borders((List<String>) countryData.get("borders"))
                        .build();
        }


        private CountryDto mapToDTO(Country country) {
                return new CountryDto(country.getCode(), country.getName());
        }

        public List<CountryDto> getAllCountriesDtos() {
                List<CountryDto> dtos = new ArrayList<>();
                List<Country> responce = getAllCountries();

                for (Country count : responce){
                        CountryDto countryDto = mapToDTO(count);
                        dtos.add(countryDto);
                }

                return dtos;
        }

        public List<CountryDto> getCountriesWithParam(String param) {

                List<CountryDto> dtosFiltered = new ArrayList<>();
                if (param == null || param.isEmpty()) {
                        return null;
                } else if (param.equals("name")) {
                        for (CountryDto country : getAllCountriesDtos()) {
                                if (country.getName().equals(param)) {
                                        dtosFiltered.add(country);
                                }
                        }
                } else {
                        for (CountryDto country : getAllCountriesDtos()) {
                                if (country.getName().equals(param)) {
                                        dtosFiltered.add(country);
                                }
                        }
                }
                return dtosFiltered;
        }

        public List<CountryDto> getAllInContinent(String continent) {
                List<CountryDto> dtosFiltered = new ArrayList<>();
                List<Country> responce = getAllCountries();
                for (Country country : responce) {
                       if (country.getContinent().containsValue(continent.toUpperCase())) {
                               CountryDto countryDto = mapToDTO(country);
                               dtosFiltered.add(countryDto);
                       }
                }
                return dtosFiltered;
        }

        public List<CountryDto> getAllInLanguage(String language) {
                List<CountryDto> dtosFiltered = new ArrayList<>();
                List<Country> responce = getAllCountries();
                for (Country country : responce) {
                        if (country.getLanguages().containsValue(language)) {
                                CountryDto countryDto = mapToDTO(country);
                                dtosFiltered.add(countryDto);
                        }
                }
                return dtosFiltered;
        }


        public CountryDto getMost() {
                List<Country> responce = getAllCountries();
                CountryDto dto = new CountryDto();
                int counter=0;

                for (Country country : responce) {
                        if (counter<country.getBorders().size()){
                                dto = mapToDTO(country);
                                counter = country.getBorders().size();
                        }
                }
                return dto;
        }

        public List<CountryDto> postCountries(int amount) {
                List<Country> responce = getAllCountries();
                List<CountryDto> dtos = new ArrayList<>();


                Collections.shuffle(responce);

                for (int i = 0; i < amount; i++) {
                        Country country = responce.get(i);
                        dtos.add(mapToDTO(country));
                        CountryEntity newCountry = new CountryEntity();
                        newCountry.setArea(country.getArea());
                        newCountry.setName(country.getName());
                        newCountry.setCode(country.getCode());
                        newCountry.setPopulation(country.getPopulation());

                        countryRepository.save(newCountry);
                }

                return dtos;
        }
}