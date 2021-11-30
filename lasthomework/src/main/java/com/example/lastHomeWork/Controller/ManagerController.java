package com.example.lastHomeWork.Controller;

import com.example.lastHomeWork.Service.ManagerService;
import com.example.lastHomeWork.Service.User_InfoService;
import com.example.lastHomeWork.entity.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.List;


@Controller
public class ManagerController {
    @Autowired private ManagerService managerService;
    @Autowired private User_InfoService user_infoService;

    @RequestMapping("Manager_login")
    public String Manager_login(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("manager_id"));
        String pwd = request.getParameter("manager_pwd");
        Manager manager = managerService.getAManager(id,pwd);
        request.getSession().setAttribute("manager",manager);
        if(manager!=null) {
            List<Privilege_Info> privilege_infoList =
                    managerService.getPrivilegeByManager_privilege(manager.getManager_privilege());
            model.addAttribute("privilege_infoList", privilege_infoList);
            model.addAttribute("manager", manager);
            return "Manager_info/Manager_main";
        }else
            return "redirect:show_ManagerLogin";
    }

    @RequestMapping("show_shop")
    public String show_shop(HttpServletRequest request,Model model){
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        List<order_info>  order_infos = managerService.getOrder_info(shop_id);
        List<Shop_Info> shop_infoList = user_infoService.FoundAllShop_Info();
        Shop_Info shop_info = new Shop_Info();
        for(Shop_Info s:shop_infoList){
            if(s.getShop_id()==shop_id){
                shop_info = s;
            }
        }
        Manager manager = (Manager)request.getSession().getAttribute("manager");
        model.addAttribute("manager",manager);
        model.addAttribute("shop_info",shop_info);
        model.addAttribute("order_infos",order_infos);

        return "Manager_info/view_shopOrder_form";
    }

    @RequestMapping("show_editManager")
    public String show_editManager(HttpServletRequest request,Model model){
        List<Manager> managers = managerService.selectAllManager();
        model.addAttribute("managers",managers);
        Manager manager = (Manager)request.getSession().getAttribute("manager");
        model.addAttribute("manager",manager);
        return "Manager_info/Manager_view";
    }

    @RequestMapping("showFood_Order")
    public String showFood_Order(HttpServletRequest request,Model model){
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        int order_id = Integer.parseInt(request.getParameter("order_id"));
        String user_name = request.getParameter("user_name");
        List<Shop_Info> shop_infoList = user_infoService.FoundAllShop_Info();
        Shop_Info shop_info = new Shop_Info();
        for(Shop_Info s:shop_infoList){
            if(s.getShop_id()==shop_id){
                shop_info = s;
                break;
            }
        }

        List<Food_Order> food_orders = managerService.getFoodOrder(order_id);
        for(Food_Order fs:food_orders){
            fs.setUser_name(user_name);
        }
        Manager manager = (Manager)request.getSession().getAttribute("manager");
        model.addAttribute("manager",manager);
        model.addAttribute("food_orders",food_orders);
        model.addAttribute("shop_info",shop_info);
        return "Manager_info/Food_Order";
    }

    @RequestMapping("show_food")
    public String show_food(HttpServletRequest request,Model model){
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        List<Food_Info> food_infos = user_infoService.getAllFoodByShop_id(shop_id);

        List<Shop_Info> shop_infoList = user_infoService.FoundAllShop_Info();
        Shop_Info shop_info = new Shop_Info();
        for(Shop_Info s:shop_infoList){
            if(s.getShop_id()==shop_id){
                shop_info = s;
                break;
            }
        }
        Manager manager = (Manager)request.getSession().getAttribute("manager");
        model.addAttribute("manager",manager);
        model.addAttribute("food_infos",food_infos);
        model.addAttribute("shop_info",shop_info);
        return "Manager_info/foodInShop";
    }

    @RequestMapping("editFood")
    public String editFood(HttpServletRequest request,Model model){
        int food_id = Integer.parseInt(request.getParameter("food_id"));
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        Food_Info foodInfo = user_infoService.getFoodByID(food_id);
        Manager manager = (Manager)request.getSession().getAttribute("manager");
        model.addAttribute("manager",manager);
        model.addAttribute("foodInfo",foodInfo);
        model.addAttribute("shop_id",shop_id);
        return "Manager_info/editFood";
    }

    @RequestMapping("addFood")
    public String addFood(HttpServletRequest request,Model model){
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        Food_Info foodInfo = new Food_Info();
        foodInfo.setFood_id(0);
        model.addAttribute("shop_id",shop_id);
        model.addAttribute("foodInfo",foodInfo);
        Manager manager = (Manager)request.getSession().getAttribute("manager");
        model.addAttribute("manager",manager);
        return "Manager_info/editFood";
    }

    @RequestMapping("editOrAddFood")
    public String editOrAddFood(HttpServletRequest request, RedirectAttributes attributes){
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        int food_id = Integer.parseInt(request.getParameter("food_id"));
        int price = Integer.parseInt(request.getParameter("price"));
        int food_number = Integer.parseInt(request.getParameter("food_number"));
        String food_name = request.getParameter("food_name");
        if(food_id!=0){
           managerService.ManagerUpdateFoodByFood_id(food_id,food_name,price,food_number);
        }else {
            managerService.InsertFood(food_name,price,food_number,shop_id);
        }

        attributes.addAttribute("shop_id",shop_id);
        return "redirect:show_food";
    }

    @RequestMapping("deleteOneFood")
    public String deleteOneFood(HttpServletRequest request,RedirectAttributes attributes){
        int food_id = Integer.parseInt(request.getParameter("food_id"));
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        managerService.deleteOneFood(food_id);
        attributes.addAttribute("shop_id",shop_id);
        return "redirect:show_food";
    }

    @RequestMapping("edit_shopInformation")
    public String edit_shopInformation(HttpServletRequest request,Model model){
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        List<Shop_Info> shop_infos = user_infoService.FoundAllShop_Info();
        Shop_Info shop_info = new Shop_Info();
        for(Shop_Info s:shop_infos){
            if(s.getShop_id()==shop_id)
                shop_info=s;
        }
        model.addAttribute("shop_info",shop_info);
        Manager manager = (Manager)request.getSession().getAttribute("manager");
        model.addAttribute("manager",manager);
        return "Manager_info/Shop_Information";
    }

    @RequestMapping("edit_shop_information")
    public String edit_shop_information(HttpServletRequest request,RedirectAttributes attributes){
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        String shop_name = request.getParameter("shop_name");
        String location = request.getParameter("location");
        managerService.updateShop_information(shop_id,shop_name,location);
        attributes.addAttribute("shop_id",shop_id);
        return "redirect:show_shop";
    }

    @RequestMapping("show_addManager")
    public String show_addManager(HttpServletRequest request, Model model){
        List<Privilege_Info> privilege_infoList = managerService.getAllPrivilege();
        model.addAttribute("privilege_infoList",privilege_infoList);
        Manager manager = (Manager)request.getSession().getAttribute("manager");
        model.addAttribute("manager",manager);
        return "Manager_info/addManager";
    }

    @RequestMapping("add_Manager")
    public String add_Manager(HttpServletRequest request){
        String manager_name = request.getParameter("manager_name");
        String manager_password = request.getParameter("manager_password");
        String[] manager_privilege = request.getParameterValues("manager_privilege");
        managerService.InsertManager(manager_name,manager_password,manager_privilege);
        return "redirect:show_editManager";
    }

    @RequestMapping("editManager")
    public String editManager(HttpServletRequest request, Model model){
        int manager_id = Integer.parseInt(request.getParameter("manager_id"));
        Manager manager = managerService.selectManagerByID(manager_id);
        List<Privilege_Info> privilege_infoList = managerService.getAllPrivilege();
        model.addAttribute("manager",manager);
        model.addAttribute("privilege_infos",privilege_infoList);
        return "Manager_info/editManager";
    }

    @RequestMapping("edit_Manager")
    public String edit_Manager(HttpServletRequest request){
        int manager_id = Integer.parseInt(request.getParameter("manager_id"));
        String[] manager_privilege = request.getParameterValues("manager_privilege");
        managerService.updateManager_privilege(manager_id,manager_privilege);
        return "redirect:show_editManager";
    }

    @RequestMapping("deleteManager")
    public String deleteManager(HttpServletRequest request){
        int manager_id = Integer.parseInt(request.getParameter("manager_id"));
        Manager manager = (Manager)request.getSession().getAttribute("manager");
        if(manager.getManager_id()!=manager_id) {
            managerService.deleteManager(manager_id);
        }
            return "redirect:show_editManager";
    }
}
