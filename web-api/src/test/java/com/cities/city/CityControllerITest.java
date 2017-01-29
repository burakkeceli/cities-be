package com.cities.city;

import com.cities.base.AbstractBaseControllerITest;
import com.cities.helper.BaseTestHelper;
import com.cities.helper.JacksonService;
import com.cities.model.city.City;
import com.cities.model.country.Country;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static com.cities.constant.ApiConstants.Urls.CITY;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CityControllerITest extends AbstractBaseControllerITest {

    @Autowired
    private BaseTestHelper helper;
    @Autowired
    private JacksonService jacksonService;

    @Test
    public void shouldGetAllCitiesWithoutAnyPermission() throws Exception {

        // given
        String countryName = randomUUID().toString();
        String cityName = randomUUID().toString();
        Country country = helper.createCountry(countryName);
        City city = helper.createCity(cityName, countryName);

        // when
        MockHttpServletRequestBuilder request = get(CITY);
        buildRequest(request);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, equalTo(APPLICATION_JSON_UTF8_VALUE)))
                .andReturn();

        // then
        String jsonResult = mvcResult.getResponse().getContentAsString();
        List<City> cityList = jacksonService.fromJson(jsonResult, new TypeReference<List<City>>() {});
        assertThat(cityList).contains(city);
    }

    @Test
    public void shouldGetCityByIdWithoutAnyPermission() throws Exception {

        // given
        String countryName = randomUUID().toString();
        String cityName = randomUUID().toString();
        Country country = helper.createCountry(countryName);
        City city = helper.createCity(cityName, countryName);

        // when
        MockHttpServletRequestBuilder request = get(CITY + "/" + city.getId());
        buildRequest(request);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, equalTo(APPLICATION_JSON_UTF8_VALUE)))
                .andReturn();

        // then
        String jsonResult = mvcResult.getResponse().getContentAsString();
        List<City> cityList = jacksonService.fromJson(jsonResult, new TypeReference<List<City>>() {});
        assertThat(cityList).contains(city);
    }

    @Test
    public void shouldReturn404WhenCityNotFound() throws Exception {
        // given
        Integer randomId = -1;
        // when
        MockHttpServletRequestBuilder request = get(CITY + "/" + randomId);
        buildRequest(request);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
