package com.cities.city;

import com.cities.city.model.CityCommentDto;
import com.cities.city.model.CityDto;
import com.cities.comment.model.CommentDto;
import com.cities.model.city.City;
import com.cities.service.city.CityService;
import com.cities.service.comment.RelationalCommentService;
import com.cities.service.comment.model.CassandraCommentModel;
import com.cities.service.user.UserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.cities.util.TimeUtil.convertDefaultTimeFormatter;

@Component
public class CityLogic {

    @Autowired
    private CityService cityService;
    @Autowired
    private RelationalCommentService relationalCommentService;
    @Autowired
    private UserService userService;

    public List<CityDto> getCityDtoList(List<City> cityList) {
        List<CityDto> cityDtoList = new ArrayList<>();
        for (City city : cityList) {
            CityDto cityDto = getCityDto(city);
            cityDtoList.add(cityDto);
        }
        return cityDtoList;
    }

    public CityDto getCityDto(City city) {
        CityDto cityDto = new CityDto();
        cityDto.setName(city.getName());
        cityDto.setId(city.getId());
        cityDto.setLatitude(city.getLatitude());
        cityDto.setLongitude(city.getLongitude());
        cityDto.setWikiUrl(city.getWikiUrl());
        cityDto.setCountryId(city.getCountryId());
        return cityDto;
    }

    public List<CityCommentDto> getCityCommentDtoList(List<CassandraCommentModel> cassandraCommentModelList) {
        List<CityCommentDto> cityCommentDtoList = new ArrayList<>();
        for (CassandraCommentModel cassandraCommentModel : cassandraCommentModelList) {
            CityCommentDto cityCommentDto = new CityCommentDto();
            cityCommentDto.setUserId(cassandraCommentModel.getUserId());
            cityCommentDto.setUserName(cassandraCommentModel.getUserName());
            cityCommentDto.setCityId(cassandraCommentModel.getCityId());
            cityCommentDto.setComment(getCommentDto(cassandraCommentModel));
            cityCommentDtoList.add(cityCommentDto);
        }
        return cityCommentDtoList;
    }

    private CommentDto getCommentDto(CassandraCommentModel cassandraCommentModel) {
        CommentDto commentDto = new CommentDto();
        DateTime createdTime = new DateTime(cassandraCommentModel.getCreatedTime().timestamp());
        commentDto.setCreatedTime(convertDefaultTimeFormatter(createdTime));
        commentDto.setId(cassandraCommentModel.getCommentId());
        commentDto.setText(cassandraCommentModel.getCommentText());
        return commentDto;
    }
}
