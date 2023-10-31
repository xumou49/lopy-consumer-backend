package com.lopy.controller;

import com.lopy.common.constant.CommonConstant;
import com.lopy.common.dto.restaurant.RestaurantDTO;
import com.lopy.common.vo.RespVO;
import com.lopy.entity.Restaurant;
import com.lopy.service.biz.intf.RestaurantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "Restaurant API")
@RestController
@RequestMapping(CommonConstant.API.V1_PATH + "/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/list")
    public RespVO<Void> list(){
        return RespVO.ok();
    }

    @GetMapping("/info")
    public RespVO<Restaurant> info(@RequestParam Long id){
		Restaurant restaurant = restaurantService.getById(id);
        return RespVO.ok(restaurant);
    }

    @PutMapping("/save")
    public RespVO<Void> save(@RequestBody RestaurantDTO restaurantDTO){
        return RespVO.ok();
    }

    @PutMapping("/update")
    public RespVO<Void> update(@RequestBody RestaurantDTO restaurantDTO){
        return RespVO.ok();
    }

    @PutMapping("/delete")
    public RespVO<Void> delete(@RequestBody List<Long> ids){
        return RespVO.ok();
    }

}
