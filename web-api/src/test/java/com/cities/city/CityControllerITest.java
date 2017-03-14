package com.cities.city;

import com.cities.base.AbstractBaseControllerITest;
import com.cities.city.model.CityCommentDto;
import com.cities.comment.model.CommentDto;
import com.cities.helper.BaseTestHelper;
import com.cities.helper.JacksonService;
import com.cities.model.city.City;
import com.cities.model.comment.Comment;
import com.cities.model.country.Country;
import com.cities.model.user.User;
import com.cities.service.comment.CommentService;
import com.cities.user.model.UserDto;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.models.auth.In;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.cities.constant.ApiConstants.Urls.*;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.joda.time.DateTime.now;
import static org.joda.time.DateTimeZone.UTC;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CityControllerITest extends AbstractBaseControllerITest {

    @Autowired
    private BaseTestHelper baseTestHelper;
    @Autowired
    private JacksonService jacksonService;
    @Autowired
    private CommentService commentService;

    @Test
    public void shouldGetAllCitiesWithoutAnyPermission() throws Exception {

        // given
        String countryName = randomUUID().toString();
        String cityName = randomUUID().toString();
        Country country = baseTestHelper.saveCountry(countryName, cityName);
        City city = baseTestHelper.saveCity(country.getId(), cityName);

        // when
        MockHttpServletRequestBuilder request = get(CITY);
        setCommonRequestPart(request);

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
        Country country = baseTestHelper.saveCountry(countryName, cityName);
        City city = baseTestHelper.saveCity(country.getId(), cityName);

        // when
        MockHttpServletRequestBuilder request = get(CITY + "/" + city.getId());
        setCommonRequestPart(request);

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
        setCommonRequestPart(request);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void shouldGetUserListWhoLikedCity() throws Exception {
        // given
        City city = saveCity();

        // and
        String username = UUID.randomUUID().toString();
        User user = baseTestHelper.saveUser(username);

        // and
        baseTestHelper.saveUserWhoLikesCity(city.getId(), user);

        // when
        MockHttpServletRequestBuilder request = get(CITY + "/" + city.getId() + LIKED);
        setCommonRequestPart(request);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, equalTo(APPLICATION_JSON_UTF8_VALUE)))
                .andReturn();

        // then
        String jsonResult = mvcResult.getResponse().getContentAsString();
        List<UserDto> userDtoList = jacksonService.fromJson(jsonResult, new TypeReference<List<UserDto>>() {});
        assertThat(userDtoList).hasSize(1);
        UserDto userDto = userDtoList.get(0);
        assertThat(userDto.getId()).isEqualTo(user.getId());
        assertThat(userDto.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void shouldGetCommentsToACity() throws Exception {
        // given
        City city = saveCity();

        // and
        String username = UUID.randomUUID().toString();
        User user = baseTestHelper.saveUser(username);

        String text = UUID.randomUUID().toString();
        DateTime createTime = now();
        Comment comment = baseTestHelper.saveComment(user.getId(), text, createTime);

        // and
        baseTestHelper.saveCommentOfCity(city.getId(), comment.getId());

        // when
        MockHttpServletRequestBuilder request = get(CITY + "/" + city.getId() + COMMENT);
        setCommonRequestPart(request);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, equalTo(APPLICATION_JSON_UTF8_VALUE)))
                .andReturn();

        // then
        String jsonResult = mvcResult.getResponse().getContentAsString();
        List<CityCommentDto> cityCommentDtoList = jacksonService.fromJson(jsonResult, new TypeReference<List<CityCommentDto>>() {});
        assertThat(cityCommentDtoList).hasSize(1);
        CityCommentDto cityCommentDto = cityCommentDtoList.get(0);
        assertThat(cityCommentDto.getUserId()).isEqualTo(user.getId());
        assertThat(cityCommentDto.getUserName()).isEqualTo(user.getUsername());
        assertThat(cityCommentDto.getCityId()).isEqualTo(city.getId());
        assertThat(cityCommentDto.getCityName()).isEqualTo(city.getName());

        // and
        CommentDto commentDto = cityCommentDto.getComment();
        assertThat(commentDto.getCreateTime().withZone(UTC)).isNotNull();
        assertThat(commentDto.getText()).isEqualTo(text);
    }

    @Test
    public void shouldSaveCommentToACity() throws Exception {
        User user = baseTestHelper.saveUser(randomUUID().toString());
        City city = saveCity();
        String commentText = "Very beautiful city";

        MockHttpServletRequestBuilder request = post(CITY + "/" + city.getId() + COMMENT);
        request.content(commentText);
        setCommonRequestPart(request);

        mockMvc.perform(request.with(user(getUserToRequest(user))))
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, equalTo(APPLICATION_JSON_UTF8_VALUE)))
                .andReturn();

        Map<Integer, Integer> cityCommentMap = commentService.getCommentsOfCity(city.getId());
        Comment comment = commentService.getCommentByCommentId(cityCommentMap.get(city.getId()));
        assertThat(comment.getUserId()).isEqualTo(user.getId());
        assertThat(comment.getText()).isEqualTo(commentText);
    }

    private City saveCity() {
        String cityName = randomUUID().toString();
        Integer countryId = 1;
        return baseTestHelper.saveCity(countryId, cityName);
    }
}
