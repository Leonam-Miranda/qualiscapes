package io.github.qualiscapes.controller;

import io.github.qualiscapes.exception.ExcelProcessingException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class GlobalExceptionHandler {

    public String handleExcelError(ExcelProcessingException ex, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/";
    }
}
