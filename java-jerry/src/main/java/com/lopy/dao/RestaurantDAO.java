package com.lopy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lopy.common.dto.restaurant.RestaurantDTO;
import com.lopy.common.query.RestaurantQuery;
import org.apache.ibatis.annotations.Mapper;
import com.lopy.entity.Restaurant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RestaurantDAO extends BaseMapper<Restaurant> {

    IPage<Restaurant> selectByPageAndQuery(@Param("page") Page<Restaurant> page, @Param("query") RestaurantQuery restaurantQuery);

    List<Restaurant> selectByQuery(@Param("query") RestaurantQuery restaurantQuery);

    List<Restaurant> selectByPromotion(String startDate, String endDate);
}
