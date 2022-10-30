package cn.edu.whut.springbear.course.service.user.service.impl;

import cn.edu.whut.springbear.course.common.model.pojo.user.UserInfo;
import cn.edu.whut.springbear.course.common.util.alogrithm.JwtHelper;
import cn.edu.whut.springbear.course.common.util.exception.CourseException;
import cn.edu.whut.springbear.course.service.user.service.UserInfoService;
import cn.edu.whut.springbear.course.service.user.service.WeChatService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Spring-_-Bear
 * @datetime 2022-10-27 18:16
 */
@Service
public class WeChatServiceImpl implements WeChatService {
    @Autowired
    private UserInfoService userInfoService;
    @Value("${wechat.authorizedCallbackUrl}")
    private String authorizedCallbackUrl;
    @Value("${token.signKey}")
    private String signKey;
    @Autowired
    private WxMpService wxMpService;

    @Override
    public String wechatAuthorization(String from) {
        /*
         * 参数说明：
         * redirectURI: 用户同意授权后微信后台重定向的地址
         * scope: 获取用户信息的方式
         * state: 重定向时携带的参数（此处用于记录用于最初请求的页面 url）
         * ================================================================
         * 如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE
         * ================================================================
         */
        // TODO from.replace()
        return wxMpService.oauth2buildAuthorizationUrl(authorizedCallbackUrl, WxConsts.OAUTH2_SCOPE_USER_INFO, from.replace("placeholder", "#"));
    }

    @Override
    public String wechatUserRegister(String code, String state) {
        WxMpUser wxMpUser;
        try {
            // 通过 code 票据换取凭证以读取微信用户信息
            wxMpUser = wxMpService.oauth2getUserInfo(wxMpService.oauth2getAccessToken(code), "zh_CN");
        } catch (WxErrorException e) {
            throw new CourseException(30000, e.getMessage());
        }

        // 根据微信用户的 openId 查询，验证该用户是否存在，不存在则注册
        String openId = wxMpUser.getOpenId();
        UserInfo user = userInfoService.getUserByOpenId(openId);
        if (user == null) {
            // 注册用户
            user = new UserInfo();
            user.setOpenId(openId);
            user.setUnionId(wxMpUser.getUnionId());
            user.setNickName(wxMpUser.getNickname());
            user.setAvatar(wxMpUser.getHeadImgUrl());
            user.setSex(wxMpUser.getSexId());
            user.setProvince(wxMpUser.getProvince());
            userInfoService.save(user);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("uid", user.getOpenId());
        map.put("nickname", user.getName());
        // 根据 openId 和 nickname 生成 token 验证信息，有效期一天
        String token = JwtHelper.createToken(map, signKey, 24 * 60 * 60 * 1000L);
        // TODO remove two lines
        System.out.println(token);
        // 拼接 token 信息至 state 尾
        state = state.indexOf('?') == -1 ? state + "?token=" + token : state + "&token=" + token;
        System.out.println(state);
        return state;
    }
}
