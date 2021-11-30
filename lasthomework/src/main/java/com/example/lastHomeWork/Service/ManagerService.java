package com.example.lastHomeWork.Service;

import com.example.lastHomeWork.entity.*;
import com.example.lastHomeWork.mapper.ManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService {
    @Autowired private ManagerMapper managerMapper;

    public Manager getAManager(int manager_id,String manager_password){
        return managerMapper.getAManager(manager_id,manager_password);
    }
    public List<Privilege_Info> getPrivilegeByManager_privilege(String privilege_id){
        String[] privilege_list = privilege_id.split("#");
        List<Privilege_Info> privilege_infoList = new ArrayList<Privilege_Info>();
        for(int i =0;i<privilege_list.length;i++){
            Privilege_Info privilege_info =
                    managerMapper.getPrivilegeByManager_privilege(Integer.parseInt(privilege_list[i]));
            privilege_infoList.add(privilege_info);
        }
        return privilege_infoList;
    }

    public List<order_info> getOrder_info(int shop_id){
        return managerMapper.getOrder_info(shop_id);
    }

    public List<Food_Order> getFoodOrder(int order_id){
        return managerMapper.getFoodOrder(order_id);
    }

    public void ManagerUpdateFoodByFood_id(int food_id,String food_name,int price,int food_number){
        managerMapper.ManagerUpdateFoodByFood_id(food_id,food_name,price,food_number);
    }

    public void InsertFood(String food_name,int price,int food_number,int shop_id){
        managerMapper.InsertFood(food_name,price,food_number,shop_id);
    }

    public void deleteOneFood(int food_id){
        managerMapper.deleteOneFood(food_id);
    }

    public void updateShop_information(int shop_id,String shop_name,String location){
        managerMapper.updateShop_information(shop_id,shop_name,location);
    }

    public List<Manager> selectAllManager(){
        return managerMapper.selectAllManager();
    }

    public List<Privilege_Info> getAllPrivilege(){
        return managerMapper.getAllPrivilege();
    }

    public void InsertManager(String manager_name,String manager_password,String[] manager_privileges){
        String privilege = "";
        for(int i = 0;i<manager_privileges.length;i++){
            if(i==manager_privileges.length-1){
                privilege += manager_privileges[i];
            }else {
                privilege += manager_privileges[i] + "#";
            }
        }
        managerMapper.InsertManager(manager_name,manager_password,privilege);
    }

    public Manager selectManagerByID(int manager_id){
        return managerMapper.selectManagerByID(manager_id);
    }

    public void  deleteManager(int manager_id){
        managerMapper.deleteManager(manager_id);
    }

    public void updateManager_privilege(int manager_id,String[] manager_privileges){
        String privilege = "";
        for(int i = 0;i<manager_privileges.length;i++){
            if(i==manager_privileges.length-1){
                privilege += manager_privileges[i];
            }else {
                privilege += manager_privileges[i] + "#";
            }
        }
        managerMapper.updateManager_privilege(manager_id,privilege);
    }

    public List<order_info> getOrder_infoByUser_id(int user_id){
       return managerMapper.getOrder_infoByUser_id(user_id);
    }

    public List<Food_Info> getFoodShop_cart(int order_id){
        return managerMapper.getFoodShop_cart(order_id);
    }
}
