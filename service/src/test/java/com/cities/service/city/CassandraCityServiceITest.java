package com.cities.service.city;


import com.cities.helper.BaseTestHelper;
import com.cities.model.city.City;
import com.cities.model.user.User;
import com.cities.test.AbstractBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CassandraCityServiceITest extends AbstractBaseTest {

    @Autowired
    private BaseTestHelper baseTestHelper;
    @Autowired
    private CassandraCityService cassandraCityService;

    @Test
    public void shouldAddUserToCityLikedList() {
        // given
        User user = baseTestHelper.saveUserWithUserRole("userName", UUID.randomUUID().toString());
        City city = baseTestHelper.saveCity(1, UUID.randomUUID().toString());

        // when
        cassandraCityService.saveUserInfoWhoLikesCity(user.getId(), city.getId());

        // then
        Map<Integer, String> userMap = cassandraCityService.getUserListWhoLikeCity(city.getId());
        assertThat(userMap).containsEntry(user.getId(), user.getUsername());
    }
}
