package com.example.lastHomeWork.Controller;

import com.example.lastHomeWork.Service.ManagerService;
import com.example.lastHomeWork.Service.User_InfoService;
import com.example.lastHomeWork.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class User_WX_Controller {
    @Autowired private User_InfoService user_infoService;
    @Autowired private ManagerService managerService;

    @RequestMapping("WX_User_login")
    public User_Info WX_User_login(HttpServletRequest request) {

        int user_id = Integer.parseInt(request.getParameter("user_id"));
        String user_pwd = request.getParameter("user_pwd");
        User_Info user_info = user_infoService.checkLogin(user_id, user_pwd);
        user_info.setUser_id(user_id);
        user_info.setPassword(user_pwd);
        return user_info;

    }

    @RequestMapping("WX_Vip_login")
    public Vip_Info WX_Vip_login(HttpServletRequest request) {

        int user_id = Integer.parseInt(request.getParameter("user_id"));
        int vip_id = user_infoService.selectVipByUser_id(user_id);
        Vip_Info vip_info = user_infoService.getVip_InfoByVip_id(vip_id);
        return vip_info;

    }

    @RequestMapping("WX_shop_cart")
    public List<shop_Cart> WX_shop_cart(HttpServletRequest request) {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        List<shop_Cart> list = user_infoService.getShop_CartAndPicture(user_id);
        return list;
    }

    @RequestMapping("WX_addFoodNumber")
    public void WX_addFoodNumber(HttpServletRequest request) {
        int food_id = Integer.parseInt(request.getParameter("food_id"));
        int buy_number = Integer.parseInt(request.getParameter("buy_number"));
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        if (buy_number > 1) {
            shop_Cart shop_cart = new shop_Cart();
            shop_cart.setFood_id(food_id);
            shop_cart.setBuy_number(buy_number);
            user_infoService.updateShop_cart(user_id, shop_cart);
        } else {
            Food_Info foodInfo = new Food_Info();
            foodInfo.setFood_id(food_id);
            foodInfo.setBuy_number(buy_number);
            foodInfo.setShop_id(shop_id);
            user_infoService.InsertShop_cart(user_id, foodInfo);
        }
    }

    @RequestMapping("WX_decreaseFoodNumber")
    public void WX_decreaseFoodNumber(HttpServletRequest request) {
        int food_id = Integer.parseInt(request.getParameter("food_id"));
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        int number = Integer.parseInt(request.getParameter("number"));
        shop_Cart shop_cart = new shop_Cart();
        shop_cart.setFood_id(food_id);
        if (number == 0)
            user_infoService.deleteShop_cart(user_id, shop_cart);
        else{
            shop_cart.setBuy_number(number);
            user_infoService.updateShop_cart(user_id, shop_cart);
        }
    }

    @RequestMapping("WX_shop_info")
    public List<Shop_Info> WX_shop_info() {
        return user_infoService.FoundAllShop_Info();
    }

    @RequestMapping("WX_food_info")
    public List<Food_Info> WX_food_info(HttpServletRequest request) {
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        List<shop_Cart> shop_carts = user_infoService.getShop_Cart(user_id);
        List<Food_Info> food_infos = user_infoService.getAllFoodByShop_id(shop_id);
        for (Food_Info f : food_infos) {
            for (shop_Cart s : shop_carts) {
                if (f.getFood_id() == s.getFood_id()) {
                    f.setBuy_number(s.getBuy_number());
                }
            }
        }
        return food_infos;
    }

    @RequestMapping("WX_clear_shop_cart")
    public void WX_clear_shop_cart(HttpServletRequest request) {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        user_infoService.clearShop_cart(user_id);
    }

    @RequestMapping("WX_buy_food")
    public void WX_buy_food(HttpServletRequest request) {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        List<shop_Cart> shop_carts = user_infoService.getShop_CartAndPicture(user_id);
        int vip_id = user_infoService.selectVipByUser_id(user_id);
        Vip_Info vip_info = user_infoService.getVip_InfoByVip_id(vip_id);
        int price = 0;
        for (shop_Cart s : shop_carts) {
            price += s.getPrice() * s.getBuy_number();
        }
        if (vip_info.getIntegral() > 100) {
            int integral = vip_info.getIntegral() % 100;
            int i = vip_info.getAccount() / 100;
            vip_info.setIntegral(integral + price);
            price -= i;
        } else {
            vip_info.setIntegral(vip_info.getIntegral() + price);
        }
        if (vip_info.getAccount() >= price) {
            vip_info.setAccount(vip_info.getAccount() - price);
            user_infoService.updateVip(vip_info);

            List<Food_Info> food_infos = user_infoService.getAllFoodByShop_id(shop_id);

            for (Food_Info f : food_infos) {
                for (shop_Cart s : shop_carts) {
                    if (f.getFood_id() == s.getFood_id()) {
                        f.setFood_number(f.getFood_number() - s.getBuy_number());
                        s.setBuy_number(s.getBuy_number());
                        user_infoService.updateFood(f);
                    }
                }
            }

            order_info order_info = new order_info();
            order_info.setUser_id(user_id);
            order_info.setShop_id(shop_id);
            order_info.setTotal(price);
            order_info.setPayment("手机App支付");
            user_infoService.InsertOrder(order_info);
            int order_id = user_infoService.SelectOrderID(order_info);

            for (Food_Info f : food_infos) {
                Food_Order food_order = new Food_Order();
                food_order.setFood_id(f.getFood_id());
                food_order.setFood_number(f.getBuy_number());
                food_order.setOrder_id(order_id);
                user_infoService.InsertFoodOrder(food_order);
            }
            user_infoService.clearShop_cart(user_id);
        }
    }

    @RequestMapping("WX_Show_order")
    public List<order_info> WX_Show_order(HttpServletRequest request){
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        List<order_info> order_infos = managerService.getOrder_infoByUser_id(user_id);
        return order_infos;
    }

    @RequestMapping("WX_Show_order_data")
    public List<Food_Info> WX_Show_order_data(HttpServletRequest request){
        int order_id = Integer.parseInt(request.getParameter("order_id"));
        List<Food_Info> food_orders = managerService.getFoodShop_cart(order_id);
        return food_orders;
    }
}
