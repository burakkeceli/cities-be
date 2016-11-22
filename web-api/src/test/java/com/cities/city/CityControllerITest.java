package com.cities.city;

import com.cities.base.AbstractBaseControllerITest;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
public class CityControllerITest extends AbstractBaseControllerITest {

    @Test
    public void testCityController() throws Exception {
        mockMvc.perform(get("/city/liked"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
