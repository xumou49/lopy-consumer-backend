package com.lopy.service.biz.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lopy.common.auth.AuthContext;
import com.lopy.common.dto.card.UserCardListDTO;
import com.lopy.common.form.card.UserCardForm;
import com.lopy.common.query.UserCardQuery;
import com.lopy.common.vo.card.UserCardVO;
import com.lopy.dao.UserCardDAO;
import com.lopy.entity.UserCard;
import com.lopy.service.biz.intf.UserCardService;
import com.lopy.service.stripe.StripeServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service("userCardService")
public class UserCardServiceImpl extends ServiceImpl<UserCardDAO, UserCard> implements UserCardService {

    @Autowired
    private StripeServiceImpl stripeServiceImpl;

    private UserCardVO toUserCardVO(UserCard userCard) {
        UserCardVO userCardVO = new UserCardVO();
        BeanUtils.copyProperties(userCard, userCardVO);
        return userCardVO;
    }

    @Override
    public List<UserCardVO> listByQuery(UserCardListDTO userCardListDTO) {
        UserCardQuery userCardQuery = UserCardQuery.build(userCardListDTO);
        return baseMapper.selectByQuery(userCardQuery).stream().map(this::toUserCardVO).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserCardForm userCardForm) {
        UserCardForm ucf = stripeServiceImpl.addCreditCard(userCardForm);
        baseMapper.save(ucf);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Long userId = AuthContext.getUserId();
        // implement here
    }
}