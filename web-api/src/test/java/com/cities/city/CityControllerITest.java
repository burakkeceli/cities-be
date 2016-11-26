package com.cities.city;

import com.cities.base.AbstractBaseControllerITest;
import org.junit.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CityControllerITest extends AbstractBaseControllerITest {

    @Test
    public void testCityController() throws Exception {

        mockMvc.perform(get("/city/liked").with(user(getUserToRequest())))
                .andExpect(status().isOk());
    }
}
