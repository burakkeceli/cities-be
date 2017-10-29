package com.cities.country;

import com.cities.base.AbstractBaseControllerITest;
import com.cities.helper.BaseTestHelper;
import com.cities.helper.JacksonService;
import com.cities.model.country.Country;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static com.cities.constant.ApiConstants.Urls.COUNTRY;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CountryControllerITest extends AbstractBaseControllerITest {

    @Autowired
    private BaseTestHelper helper;
    @Autowired
    private JacksonService jacksonService;

    @DisplayName("Should get all countries without any permission")
    @Test
    public void shouldGetAllCountriesWithoutAnyPermission() throws Exception {

        // given
        String countryName = randomUUID().toString();
        String capitalName = randomUUID().toString();
        Country country = helper.saveCountry(countryName, capitalName);

        // when
        MockHttpServletRequestBuilder request = get(COUNTRY);
        setCommonRequestPart(request);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        // then
        String jsonResult = mvcResult.getResponse().getContentAsString();
        List<Country> countryList = jacksonService.fromJson(jsonResult, new TypeReference<List<Country>>() {});
        assertThat(countryList).contains(country);
    }

    @Test
    public void shouldGetCityByIdWithoutAnyPermission() throws Exception {

        // given
        String countryName = randomUUID().toString();
        String cityName = randomUUID().toString();
        Country country = helper.saveCountry(countryName, cityName);

        // when
        MockHttpServletRequestBuilder request = get(COUNTRY + "/" + country.getId());
        setCommonRequestPart(request);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        // then
        String jsonResult = mvcResult.getResponse().getContentAsString();
        List<Country> countryList = jacksonService.fromJson(jsonResult, new TypeReference<List<Country>>() {});
        assertThat(countryList).contains(country);
    }

    @Test
    public void shouldReturn404WhenCityNotFound() throws Exception {
        // given
        Integer randomId = -1;
        // when
        MockHttpServletRequestBuilder request = get(COUNTRY + "/" + randomId);
        setCommonRequestPart(request);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
