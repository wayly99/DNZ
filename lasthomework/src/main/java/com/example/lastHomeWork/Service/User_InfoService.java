package com.example.lastHomeWork.Service;

import com.example.lastHomeWork.entity.*;
import com.example.lastHomeWork.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class User_InfoService {
    @Autowired private UserMapper userMapper;

    public User_Info checkLogin(int user_id,String password){
        return userMapper.checkLogin(user_id,password);
    }

    public boolean InsertUser(int id,String name,String password,int vip_id){
        userMapper.InsertUser(id,name,password,vip_id);
        return true;
    }

    public List<Shop_Info> FoundAllShop_Info(){
        return userMapper.FoundAllShop_Info();
    }

    public Vip_Info getVip_InfoByVip_id(int id){
        return userMapper.getVip_InfoByVip_id(id);
    }

    public List<Food_Info> getFoodByShop_id(int shop_id,int start_page){
        return userMapper.SelectAllFood(shop_id,start_page);
    }
    public List<Food_Info> getAllFoodByShop_id(int shop_id){
        return userMapper.getAllFoodByShop_id(shop_id);
    }

    public Food_Info getFoodByID(int food_id){
        return userMapper.getFoodByID(food_id);
    }

    public void updateVip(Vip_Info vip_info){
        userMapper.updateVip(vip_info);
    }

    public void updateFood(Food_Info  food_info){
        userMapper.updateFood(food_info);
    }

    public void InsertOrder(order_info order_info){
        userMapper.InsertOrder(order_info);
    }

    public int SelectOrderID(order_info order_info){
        return userMapper.selectOrderID(order_info);
    }

    public void InsertFoodOrder(Food_Order food_order){
        userMapper.InsertFoodOrder(food_order);
    }

    public void InsertVip(int user_id){
        userMapper.InsertVip(user_id);
    }

    public int selectVipByUser_id(int user_id){
        return userMapper.selectVipByUser_id(user_id);
    }

    public List<shop_Cart> getShop_Cart(int user_id){
        return userMapper.getShop_Cart(user_id);
    }

    public void InsertShop_cart(int user_id,Food_Info food_info){
        userMapper.InsertShop_cart(user_id,food_info.getFood_id(),food_info.getShop_id(),food_info.getBuy_number());
    }

    public void updateShop_cart(int user_id,shop_Cart shop_cart){
        userMapper.updateShop_cart(user_id,shop_cart.getFood_id(),shop_cart.getBuy_number());
    }

    public void deleteShop_cart( int user_id,shop_Cart shop_cart){
        userMapper.deleteShop_cart(user_id,shop_cart.getFood_id());
    }

    public void clearShop_cart(int user_id){
        userMapper.clearShop_cart(user_id);
    }

    public List<shop_Cart> getShop_CartAndPicture(int user_id){
        return userMapper.getShop_CartAndPicture(user_id);
    }
}
