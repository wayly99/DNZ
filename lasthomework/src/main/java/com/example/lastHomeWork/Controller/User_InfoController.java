package com.example.lastHomeWork.Controller;

import com.example.lastHomeWork.Service.User_InfoService;
import com.example.lastHomeWork.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class User_InfoController {

    @Autowired private User_InfoService user_infoService;

    @RequestMapping("show_login")
    public String showLogin(){
        return "User_info/User_login";
    }

    @RequestMapping("showRegister")
    public String showRegister(){
        return "User_info/register";
    }

    @RequestMapping("show_invest")
    public String show_invest(HttpServletRequest request,Model model){
        int vip_id = Integer.parseInt(request.getParameter("vip_id"));
        model.addAttribute("vip_id",vip_id);
        return "User_info/invest";
    }

    @RequestMapping("invest")
    public String invest(HttpServletRequest request,Model model){

        int vip_id = Integer.parseInt(request.getParameter("vip_id"));
        int accounts = Integer.parseInt(request.getParameter("accounts"));

        Vip_Info vip_info = user_infoService.getVip_InfoByVip_id(vip_id);
        vip_info.setAccount(vip_info.getAccount()+accounts);
        user_infoService.updateVip(vip_info);
        User_Info user_info = (User_Info) request.getSession().getAttribute("user_info");
        model.addAttribute("user_info",user_info);
        return "User_info/finishAccounts";
    }

    @RequestMapping("show_ManagerLogin")
    public String showManagerLogin(){
        return "Manager_info/Manager_login";
    }

    @RequestMapping("register")
    public String InsertUser(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");
        user_infoService.InsertVip(id);
        int vip_id = user_infoService.selectVipByUser_id(id);
        boolean flag=user_infoService.InsertUser(id,name,pwd,vip_id);
        if(flag)
            return "redirect:show_login";
        else
            return "error";
    }

    @RequestMapping("User_login")
    public String showShop(HttpServletRequest request,Model model){
        int id = Integer.parseInt(request.getParameter("user_id"));
        String pwd = request.getParameter("user_pwd");
        User_Info user_info = user_infoService.checkLogin(id,pwd);
        user_info.setUser_id(id);
        user_info.setPassword(pwd);
        request.getSession().setAttribute("user_info", user_info);
        List<Shop_Info> shop_infoList = user_infoService.FoundAllShop_Info();
        Vip_Info vip_info = user_infoService.getVip_InfoByVip_id(user_info.getVipID());
        model.addAttribute("shop_infoList",shop_infoList);
        model.addAttribute("user_info",user_info);
        model.addAttribute("vip_info",vip_info);
        if(user_info!=null)
            return "User_info/choose_shop";
        else
            return "redirect:show_login";
    }

    @RequestMapping("showFood")
    public String showFood(HttpServletRequest request, Model model){
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        int start_page = Integer.parseInt(request.getParameter("start_page"));
        if(start_page<0){
            start_page = 0;
        }
        List<Food_Info> food_infoList = user_infoService.getFoodByShop_id(shop_id,
                start_page);
        HttpSession session = request.getSession();
        User_Info user_info=(User_Info) session.getAttribute("user_info");
        Vip_Info vip_info = user_infoService.getVip_InfoByVip_id(user_info.getVipID());

        List<shop_Cart> shop_carts = user_infoService.getShop_Cart(user_info.getUser_id());

        if(!shop_carts.isEmpty()) {
            for (int i = 0; i < food_infoList.size(); i++) {
                for(int j = 0;j< shop_carts.size();j++){
                    if(food_infoList.get(i).getFood_id()==shop_carts.get(j).getFood_id()){
                        food_infoList.get(i).setBuy_number(shop_carts.get(j).getBuy_number());
                    }
                }
            }
        }
        model.addAttribute("food_infoList",food_infoList);
        model.addAttribute("shop_id",shop_id);
        model.addAttribute("user_info",user_info);
        model.addAttribute("vip_info",vip_info);
        model.addAttribute("start_page",start_page);
        return "User_info/choose_food";
    }

    @RequestMapping("AddFood")
    public String AddFood(HttpServletRequest request, RedirectAttributes attributes){
        boolean flag = false;
        int food_id = Integer.parseInt(request.getParameter("food_id"));
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        int start_page = Integer.parseInt(request.getParameter("start_page"));
        HttpSession session=request.getSession();
        User_Info user_info = (User_Info)session.getAttribute("user_info");
        List<shop_Cart> shop_carts = user_infoService.getShop_Cart(user_info.getUser_id());

        Food_Info foodInfo = user_infoService.getFoodByID(food_id);
        for(int i = 0;i<shop_carts.size();i++){
            if(shop_carts.get(i).getFood_id()==food_id){
                if(shop_carts.get(i).getBuy_number()<foodInfo.getFood_number()){
                    shop_carts.get(i).setBuy_number(shop_carts.get(i).getBuy_number()+1);
                    user_infoService.updateShop_cart(user_info.getUser_id(),shop_carts.get(i));
                }
                flag = true;
                break;
            }
        }
        if(flag == false){
            foodInfo.setBuy_number(1);
            user_infoService.InsertShop_cart(user_info.getUser_id(),foodInfo);
        }
        attributes.addAttribute("shop_id",shop_id);
        attributes.addAttribute("start_page",start_page);
        return "redirect:showFood";
    }

    @RequestMapping("decreaseFoodInChoose_food")
    public String decreaseFoodInChoose_food(HttpServletRequest request,RedirectAttributes attributes){
        int food_id = Integer.parseInt(request.getParameter("food_id"));
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        int start_page = Integer.parseInt(request.getParameter("start_page"));
        HttpSession session=request.getSession();
        User_Info user_info = (User_Info)session.getAttribute("user_info");
        List<shop_Cart> shop_carts = user_infoService.getShop_Cart(user_info.getUser_id());

        for(int i = 0;i<shop_carts.size();i++){
            if(shop_carts.get(i).getFood_id()==food_id){
                if(shop_carts.get(i).getBuy_number()>1){
                    shop_carts.get(i).setBuy_number(shop_carts.get(i).getBuy_number()-1);
                    user_infoService.updateShop_cart(user_info.getUser_id(),shop_carts.get(i));
                }else {
                    user_infoService.deleteShop_cart(user_info.getUser_id(),shop_carts.get(i));
                }
                break;
            }
        }
        attributes.addAttribute("shop_id",shop_id);
        attributes.addAttribute("start_page",start_page);
        return "redirect:showFood";
    }

    @RequestMapping("decreaseFood")
    public String decreaseFood(HttpServletRequest request){
        HttpSession session=request.getSession();
        int food_id = Integer.parseInt(request.getParameter("food_id"));
        User_Info user_info = (User_Info)session.getAttribute("user_info");
        List<shop_Cart> shop_carts = user_infoService.getShop_Cart(user_info.getUser_id());
        for(int i = 0;i<shop_carts.size();i++){
            if(shop_carts.get(i).getFood_id()==food_id){
                shop_carts.get(i).setBuy_number(shop_carts.get(i).getBuy_number()-1);
                if(shop_carts.get(i).getBuy_number()==0){
                    user_infoService.deleteShop_cart(user_info.getUser_id(),shop_carts.get(i));
                }else {
                    user_infoService.updateShop_cart(user_info.getUser_id(),shop_carts.get(i));
                }
                break;
            }
        }

        return "redirect:buy_food";
    }

    @RequestMapping("buy_food")
    public String Buy_Food(HttpServletRequest request,Model model){
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        int start_page = Integer.parseInt(request.getParameter("start_page"));

        HttpSession session = request.getSession();
        User_Info user_info = (User_Info)session.getAttribute("user_info");

        List<shop_Cart> shop_carts = user_infoService.getShop_Cart(user_info.getUser_id());
        Vip_Info vip_info = user_infoService.getVip_InfoByVip_id(user_info.getVipID());
        List<Food_Info> food_infoList = new ArrayList<>();

        int counts=0;
            for(shop_Cart list:shop_carts){
                Food_Info food_info = user_infoService.getFoodByID(list.getFood_id());
                food_info.setBuy_number(list.getBuy_number());
                food_infoList.add(food_info);
                counts+=food_info.getPrice()*list.getBuy_number();
            }

        model.addAttribute("shop_id",shop_id);
        model.addAttribute("counts",counts);
        model.addAttribute("food_infoList",food_infoList);
        model.addAttribute("vip_info",vip_info);
        model.addAttribute("start_page",start_page);
        return "User_info/accounts";
    }

    @RequestMapping("show_cash")
    public String cash(HttpServletRequest request){
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        int counts = Integer.parseInt(request.getParameter("counts"));
        HttpSession session = request.getSession();
        User_Info user_info = (User_Info) session.getAttribute("user_info");
        List<shop_Cart> shop_carts = user_infoService.getShop_Cart(user_info.getUser_id());
        List<Food_Info> food_infos = user_infoService.getAllFoodByShop_id(shop_id);

        for(Food_Info f:food_infos){
            for(shop_Cart s:shop_carts) {
                if(f.getFood_id()==s.getFood_id()) {
                    f.setFood_number(f.getFood_number() - s.getBuy_number());
                    s.setBuy_number(s.getBuy_number());
                    user_infoService.updateFood(f);
                }
            }
        }

        order_info order_info = new order_info();
        order_info.setUser_id(user_info.getUser_id());
        order_info.setShop_id(shop_id);
        order_info.setTotal(counts);
        order_info.setPayment("支付宝");
        user_infoService.InsertOrder(order_info);
        int order_id =user_infoService.SelectOrderID(order_info);

        for(Food_Info f:food_infos){
            Food_Order food_order = new Food_Order();
            food_order.setFood_id(f.getFood_id());
            food_order.setFood_number(f.getBuy_number());
            food_order.setOrder_id(order_id);
            user_infoService.InsertFoodOrder(food_order);
        }
            user_infoService.clearShop_cart(user_info.getUser_id());
        return "User_info/cash";
    }

    @RequestMapping("vip_accounts")
    public String vip_accounts(HttpServletRequest request,Model model){
        int price = Integer.parseInt(request.getParameter("counts"));
        int vip_id = Integer.parseInt(request.getParameter("vip_id"));
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        Vip_Info vip_info = user_infoService.getVip_InfoByVip_id(vip_id);
        if(vip_info.getIntegral()>100){
            int integral=vip_info.getIntegral()%100;
            int i = vip_info.getAccount()/100;
            vip_info.setIntegral(integral+price);
            price -=i;
        }else {
            vip_info.setIntegral(vip_info.getIntegral()+price);
        }
        HttpSession session = request.getSession();
        User_Info user_info = (User_Info) session.getAttribute("user_info");

        if(vip_info.getAccount()>=price) {
            vip_info.setAccount(vip_info.getAccount() - price);
            user_infoService.updateVip(vip_info);

            List<shop_Cart> shop_carts = user_infoService.getShop_Cart(user_info.getUser_id());

            List<Food_Info> food_infos = user_infoService.getAllFoodByShop_id(shop_id);

            for(Food_Info f:food_infos){
                for(shop_Cart s:shop_carts) {
                    if(f.getFood_id()==s.getFood_id()) {
                        f.setFood_number(f.getFood_number() - s.getBuy_number());
                        s.setBuy_number(s.getBuy_number());
                        user_infoService.updateFood(f);
                    }
                }
            }

            order_info order_info = new order_info();
            order_info.setUser_id(user_info.getUser_id());
            order_info.setShop_id(shop_id);
            order_info.setTotal(price);
            order_info.setPayment("支付宝");
            user_infoService.InsertOrder(order_info);
            int order_id =user_infoService.SelectOrderID(order_info);

            for(Food_Info f:food_infos){
                Food_Order food_order = new Food_Order();
                food_order.setFood_id(f.getFood_id());
                food_order.setFood_number(f.getBuy_number());
                food_order.setOrder_id(order_id);
                user_infoService.InsertFoodOrder(food_order);
            }
            user_infoService.clearShop_cart(user_info.getUser_id());

            model.addAttribute("user_info", user_info);
            return "User_info/finishAccounts";
        }else
            return "User_info/invest";
    }
}
