package com.example.lastHomeWork.mapper;

import com.example.lastHomeWork.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ManagerMapper {
    Manager getAManager(int manager_id,String manager_password);

    Privilege_Info getPrivilegeByManager_privilege(int privilege_id);

    List<order_info> getOrder_info(int shop_id);

    List<Food_Order> getFoodOrder(int order_id);

    void ManagerUpdateFoodByFood_id(int food_id,String food_name,int price,int food_number);

    void InsertFood(String food_name,int price,int food_number,int shop_id);

    void deleteOneFood(int food_id);

    void updateShop_information(int shop_id,String shop_name,String location);

    List<Manager> selectAllManager();

    List<Privilege_Info> getAllPrivilege();

    void InsertManager(String manager_name,String manager_password,String manager_privilege);

    Manager selectManagerByID(int manager_id);

    void deleteManager(int manager_id);

    void updateManager_privilege(int manager_id,String manager_privilege);

    List<order_info> getOrder_infoByUser_id(int user_id);

    List<Food_Info> getFoodShop_cart(int order_id);
}
