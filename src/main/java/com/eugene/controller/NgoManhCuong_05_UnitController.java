package com.eugene.controller;

import com.eugene.domain.NgoManhCuong_05_Unit;
import com.eugene.repository.NgoManhCuong_05_CourseRepository;
import com.eugene.repository.NgoManhCuong_05_UnitRepository;
import com.eugene.validator.NgoManhCuong_05_UnitNameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.groups.Default;

/**
 * Created by Ngô Mạnh Cường on 12/5/2016.
 */

/*Controller cho bài học*/
@Controller
@RequestMapping("/courses/{courseId}/weeks/{weekId}")
public class NgoManhCuong_05_UnitController {

  private final NgoManhCuong_05_UnitRepository unitRepository;
  private final NgoManhCuong_05_CourseRepository courseRepository;
  private final NgoManhCuong_05_UnitNameValidator unitNameValidator;

  @Autowired
  public NgoManhCuong_05_UnitController(NgoManhCuong_05_UnitRepository unitRepository, NgoManhCuong_05_CourseRepository courseRepository, NgoManhCuong_05_UnitNameValidator unitNameValidator) {
    this.unitRepository = unitRepository;
    this.courseRepository = courseRepository;
    this.unitNameValidator = unitNameValidator;
  }

  /*Gọi trang thêm bài học*/
  @RequestMapping(value = "units/new")
  public String addUnit(Model model, @PathVariable Integer courseId, @PathVariable Integer weekId) {
    NgoManhCuong_05_Unit unit = new NgoManhCuong_05_Unit();
    unit.setCourse(courseRepository.findOne(courseId.longValue()));
    unit.setUnitWeek(weekId);
    model.addAttribute("unit", unit);
    return "courses/units/new";
  }

  /*Thêm bài học*/
  @RequestMapping(value = "units/add", method = RequestMethod.POST)
  public String addUnit(@Validated @ModelAttribute("unit") NgoManhCuong_05_Unit unit, BindingResult bindingResult) {
    unitNameValidator.validate(unit, bindingResult);
    if (bindingResult.hasErrors()) {
      return "courses/units/new";
    }
    unitRepository.save(unit);
    return "redirect:/courses/{courseId}/weeks/{weekId}";
  }

  /*Xóa bài học*/
  //  TODO: Make response body when deleting fail
  @RequestMapping(value="delete/{unitId}", method=RequestMethod.DELETE,
    produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public void deleteUnit(@PathVariable Integer unitId) {
    unitRepository.delete(unitId.longValue());
  }

  /*Gọi trang sửa bài học*/
  @RequestMapping(value = "units/{unitId}/edit")
  public String editUnit(@PathVariable Integer courseId, @PathVariable Integer unitId, Model model) {
    NgoManhCuong_05_Unit unit = unitRepository.findOne(unitId.longValue());
    model.addAttribute("unit", unit);
    return "courses/units/edit";
  }

  /*Sửa bài học*/
  @RequestMapping(value = "units/{unitId}/edit", method = RequestMethod.POST)
  public String editUnitPost(@ModelAttribute("unit") @Validated({Default.class}) NgoManhCuong_05_Unit unit,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      return "/courses/units/edit";
    }
    unitRepository.save(unit);
    redirectAttributes.addFlashAttribute("message", "Course was successfully updated!!");
    return "redirect:/courses/{courseId}/weeks/{weekId}";
  }
}
