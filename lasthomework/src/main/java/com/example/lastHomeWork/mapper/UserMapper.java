package com.example.lastHomeWork.mapper;

import com.example.lastHomeWork.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    User_Info checkLogin(@Param("user_id") int user_id,
                         @Param("password") String password);

    void InsertUser(@Param("id") int id,
                    @Param("user_name") String user_name,
                    @Param("password") String password,
                    @Param("vip_id") int vip_id);

    List<Shop_Info> FoundAllShop_Info();

    Vip_Info getVip_InfoByVip_id(@Param("id")int id);

    List<Food_Info> SelectAllFood(int shop_id,int start_page);

    Food_Info getFoodByID(@Param("food_id")int food_id);

    void updateVip(Vip_Info vip_info);

    void updateFood(Food_Info food_info);

    void InsertOrder(order_info order_info);

    int selectOrderID(order_info order_info);

    void InsertFoodOrder(Food_Order food_order);

    void InsertVip(int user_id);

    int selectVipByUser_id(int user_id);

    List<shop_Cart> getShop_Cart(int user_id);

    void InsertShop_cart(int user_id,int food_id,int shop_id,int buy_number);

    void updateShop_cart(int user_id,int food_id,int buy_number);

    void deleteShop_cart(int user_id,int food_id);

    List<Food_Info> getAllFoodByShop_id(int shop_id);

    void clearShop_cart(int user_id);

    List<shop_Cart> getShop_CartAndPicture(int user_id);
}
