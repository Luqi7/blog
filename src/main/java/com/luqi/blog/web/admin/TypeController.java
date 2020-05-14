package com.luqi.blog.web.admin;

import com.luqi.blog.po.Type;
import com.luqi.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String list(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC)
                                   Pageable pageable, Model model){
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input() {
        return "admin/types_input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/types_input";
    }

    @PostMapping("/types")
    public String post(Type type, BindingResult result, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null){
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if(result.hasErrors()){
            return "admin/types_input";
        }
        Type type2 = typeService.saveType(type);
        if(type2 == null){
            attributes.addFlashAttribute("message", "新增失败！");
        }else{
            attributes.addFlashAttribute("message", "新增成功！");

        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editPost(Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        Type t = typeService.getTypeByName(type.getName());
        if(t != null){
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if(result.hasErrors()){
            return "admin/types_input";
        }
        Type type2 = typeService.updateType(id, type);
        if(type2 == null){
            attributes.addFlashAttribute("message", "更新失败！");
        }else{
            attributes.addFlashAttribute("message", "更新成功！");

        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功！");
        return "redirect:/admin/types";
    }
}
