package com.cities.city;

import com.cities.city.model.CityCommentDto;
import com.cities.city.model.CityDto;
import com.cities.comment.model.CommentDto;
import com.cities.model.city.City;
import com.cities.model.comment.Comment;
import com.cities.model.user.User;
import com.cities.service.city.CityService;
import com.cities.service.comment.CommentService;
import com.cities.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Component
public class CityLogic {

    @Autowired
    private CityService cityService;
    @Autowired
    private CommentService commentService;
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

    public List<CityCommentDto> getCityCommentDtoList(Map<Integer, Integer> cityCommentMap) {
        List<CityCommentDto> cityCommentDtoList = new ArrayList<>();
        for (Entry<Integer, Integer> entry : cityCommentMap.entrySet()){
            City city = cityService.getCityById(entry.getKey());
            Comment comment = commentService.getCommentById(entry.getValue());
            User user = userService.getUserById(comment.getUserId());
            cityCommentDtoList.add(getCityCommentDto(city, comment, user));
        }
        return cityCommentDtoList;
    }

    private CityCommentDto getCityCommentDto(City city, Comment comment, User user) {
        CommentDto commentDto = getCommentDto(comment);
        return getCityCommentDto(city, user, commentDto);
    }

    private CityCommentDto getCityCommentDto(City city, User user, CommentDto commentDto) {
        CityCommentDto cityCommentDto = new CityCommentDto();
        cityCommentDto.setUserId(user.getId());
        cityCommentDto.setCityId(city.getId());
        cityCommentDto.setUserName(user.getUsername());
        cityCommentDto.setCityName(city.getName());
        cityCommentDto.setComment(commentDto);
        return cityCommentDto;
    }

    private CommentDto getCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCreateTime(comment.getCreateTime());
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        return commentDto;
    }
}
