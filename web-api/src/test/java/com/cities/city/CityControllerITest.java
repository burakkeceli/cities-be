package com.cities.city;

import com.cities.base.AbstractBaseControllerITest;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static com.cities.constant.ApiConstants.Urls.CITY;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CityControllerITest extends AbstractBaseControllerITest {

    @Test
    public void shouldGetAllCitiesWithoutAnyPermission() throws Exception {


        MockHttpServletRequestBuilder request = get(CITY);
        buildRequest(request);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, equalTo(APPLICATION_JSON_UTF8_VALUE)))
                .andReturn();
    }
}
