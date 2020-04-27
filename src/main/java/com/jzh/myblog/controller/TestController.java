package com.jzh.myblog.controller;

import com.jzh.myblog.entity.Tests;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.service.VisitorService;
import com.jzh.myblog.util.ResultUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/12 19:34
 * @description
 */
//@Slf4j
@Controller
@RequestMapping("test")
public class TestController {

//    @ResponseBody
//    @PostMapping("repeatSubmit")
//    public Result repeatSubmit() {
//        return ResultUtil.success();
//    }

    /**
     * test
     */
//    @GetMapping("")
//    public String test() {
//        return "test";
//    }

//    @ResponseBody
//    @GetMapping("getLock")
//    public void getLock() {
//        synchronized(VisitorService.VISITOR) {
//            System.out.println(VisitorService.VISITOR + "1");
//        }
//    }
//
//    @ResponseBody
//    @GetMapping("get")
//    public void get() {
//        System.out.println(VisitorService.VISITOR + "2");
//    }
//
//    @ResponseBody
//    @GetMapping("heap")
//    public void heap() {
//        List<Tests> list=new ArrayList<Tests>();
//        while(true){
//            list.add(new Tests());
//        }
//    }

    @ResponseBody
    @GetMapping("test")
    public String test(@RequestParam("test") String test) {
        System.out.println(test);
        return test;
    }

//    @GetMapping("getTest")
//    public String getTest(@ModelAttribute @Validated Tests tests) {
//        System.out.println(tests);
//        return "test";
//    }

//    @GetMapping("/user")
//    public String getUser() {
//        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(principal);
//        System.out.println(String.valueOf(principal));
//        System.out.println(principal.getUsername());
//        System.out.println(principal.getPassword());
//        return null;
//    }

//    private static final ThreadLocal<String> t = new ThreadLocal<>();
//
//    @GetMapping("set")
//    public String set(@RequestParam("str") String str) {
//        t.set(str);
//        System.out.println(str);
//        System.out.println(t.get());
//        return t.get();
//    }
//
//    @GetMapping("get")
//    public String get() {
//        System.out.println(Thread.currentThread());
//        System.out.println(t.get());
//        return t.get();
//    }


//    @Autowired
//    private TestService testService;
//
//    @GetMapping("user")
//    @AuthorityCheck(RoleEnum.ROLE_USER)
//    public void user() {
//        System.out.println("user");
//    }
//
//    @GetMapping("admin")
//    @AuthorityCheck(RoleEnum.ROLE_ADMIN)
//    public void admin() {
//        System.out.println("admin");
//    }
//
//    @GetMapping("superAdmin")
//    @AuthorityCheck(RoleEnum.ROLE_SUPERADMIN)
//    public void superAdmin() {
//        System.out.println("super admin");
//    }
//
//    @GetMapping("page")
//    public Result page(PageDTO pageDto) {
//        Page<Tests> page = new Page<>();
//        page.setCurrent(pageDto.getPageNum());
//        page.setSize(pageDto.getRows());
//
//        page = testService.page(page);
//
//        return ResultUtil.success(
////                PageVO.builder()
////                        .rows(page.getSize())
////                        .pageNum(page.getCurrent())
////                        .pages(page.getPages())
////                        .total(page.getTotal())
////                        .data(page.getRecords())
////                        .build()
//        );
//    }
//
//    @GetMapping("get")
//    public Result getSessionTest(HttpSession session) {
//        System.out.println(session.getAttribute("test"));
//        return ResultUtil.success(session.getId());
//    }
//
//    @GetMapping("put")
//    public Result putSessionTest(HttpSession session) {
//        session.setAttribute("test", "tesasdasdt");
//        return ResultUtil.success();
//    }
}
